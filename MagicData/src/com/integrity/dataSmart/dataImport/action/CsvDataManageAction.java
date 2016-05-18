package com.integrity.dataSmart.dataImport.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.dataImport.bean.CsvData;
import com.integrity.dataSmart.dataImport.service.CsvDataService;
import com.integrity.dataSmart.dataImport.util.CsvProcessHDFS;
import com.opensymphony.xwork2.ActionSupport;

public class CsvDataManageAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(CsvDataManageAction.class);
	
	/**csv上传*/
	private File csv;
	private String csvFileName;
	
	//文件编码
	private String fileEncoding;
	//分隔符
	private String fileSeparator;
	//是否有表头
	private boolean titleFlag;
	
	private CsvDataService csvDataService;
	
	//返回结果
	private Map<String,Object> result = null;
	//样本数据
	private Map<String,List<Map<String,String>>> finalMap = null;
	
	private CsvData csvData;
	
	//每页多少条记录  
    private Integer pageSize;  
    //第几页  
    private Integer pageNo;
    
    private PageModel<CsvData> pageModel;
	
	public static String hdfsConfigPath =  CsvDataManageAction.class.getClassLoader().getResource("../config/hadoopConfg/core-site.xml").toString();
	
	/**
	 * 上传csv文件到hdsf,并把该记录保存到数据库
	 * @return String
	 */
	public String csvUpload(){
		result = new HashMap<String,Object>();
		boolean flag = false;
		List<CsvData> list = csvDataService.findCsvByFileName(csvFileName);
		if(list!=null&&list.size()>0){
			result.put("success", false);
			result.put("message", "该文件已存在，请重新选择！");
			return SUCCESS;
		}
		try {
			if(list!=null&&list.size()>0){
				result.put("success", false);
				result.put("message", "该文件已存在，请重新选择！");
				return SUCCESS;
			}
			flag = CsvProcessHDFS.getInstance(hdfsConfigPath).sendFile(csv,csvFileName);
			if(flag){
				//把数据存到表里边
				CsvData csvData = new CsvData();
				csvData.setFileName(csvFileName);
				csvData.setFileEncoding(fileEncoding);
				csvData.setFileSeparator(fileSeparator.trim());
				csvData.setTitleFlag(titleFlag);
				csvData.setUploadTime(new Date());
				csvDataService.csvSave(csvData);
				result.put("success", true);
			}
		} catch (IOException e) {
			result.put("success",false);
			result.put("message", "csv上传异常,请重试!");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取csv已存在文件列表
	 * @return
	 */
	public String findCsvFileList(){
		pageModel = new PageModel<CsvData>();
		if(pageNo==null){
			pageNo=0;
		}
		if(pageSize==null){
			pageSize=10;
		}
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		try {
			pageModel = csvDataService.getCsvFilePageModel(pageModel,csvData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取5条样本数据
	 * @return 
	 */
	public String findCsvSampleData(){
		//List<CsvData> list = csvDataService.findCsvByFileName(csvFileName);
		result = new HashMap<String,Object>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		CsvData csvData = csvDataService.findCsvById(id);
		if(csvData==null){
			result.put("success", false);
			result.put("message", "该文件不存在！");
			result.put("finalMap", new HashMap<String,List<Map<String,String>>>());
			return SUCCESS;
		}
		csvFileName = csvData.getFileName();
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		finalMap = new HashMap<String,List<Map<String,String>>>();
		BufferedReader br = null;
		try {
			CsvProcessHDFS instance = CsvProcessHDFS.getInstance(hdfsConfigPath);
			Configuration config = instance.getConfig();
			FileSystem fs = FileSystem.get(config);
			String hadPath = instance.gethadPath();
			Path path = new Path(hadPath+"/csvfiles/"+csvFileName);
			if(fs.isFile(path)){
				FSDataInputStream inputStream = fs.open(path);
				br = new BufferedReader(new InputStreamReader(inputStream,csvData.getFileEncoding()));
				String line = null;
				int index=0;
				String[] headers = null;
				if(csvData.getTitleFlag()){
					//有表头
					while(null!=(line=br.readLine())){
						index++;
						if(index==1){
							headers = line.split(csvData.getFileSeparator());
						}else{
							String[] strs = line.split(csvData.getFileSeparator());
							Map<String,String> m = new LinkedHashMap<String,String>();
							for(int i=0;i<strs.length;i++){
								String header = "";
								String str = "";
								if((headers[i]!=null&&headers[i].startsWith("\""))||(strs[i]!=null&&strs[i].startsWith("\""))){
									//去除双引号
									if(headers[i]!=null&&headers[i].startsWith("\"")){
										header = headers[i].substring(1,headers[i].length()-1);
									}
									if(strs[i]!=null&&strs[i].startsWith("\"")){
										str = strs[i].substring(1,strs[i].length()-1);
									}
								}else{
									header = headers[i];
									str = strs[i];
								}
								m.put(header, str);
							}
							dataList.add(m);
						}
						if(index==6){
							break;
						}
					}
				}else{
					//没表头
					while(null!=(line=br.readLine())){
						index++;
						String[] strs = line.split(csvData.getFileSeparator());
						Map<String,String> m = new LinkedHashMap<String,String>();
						for(int i=0;i<strs.length;i++){
							String str = "";
							if(strs[i]!=null&&strs[i].startsWith("\"")){
								str = strs[i].substring(1,strs[i].length()-1);
							}else{
								str = strs[i];
							}
							m.put(i+"", str);
						}
						dataList.add(m);
						if(index==5){
							break;
						}
					}
				}
				finalMap.put("rows", dataList);
				result.put("success", true);
				result.put("message", "获取样本数据成功！");
				result.put("finalMap", finalMap);
			}else{
				result.put("success", false);
				result.put("message", "该文件不存在！");
				result.put("finalMap", new HashMap<String,List<Map<String,String>>>());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 通过id查询csvData
	 * @return
	 */
	public String findCsvDataById(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		CsvData csvData = csvDataService.findCsvById(id);
		result = new HashMap<String,Object>();
		result.put("csvData", csvData);
		return SUCCESS;
	}
	
	/**
	 * 删除csv文件（包括删除表里的记录和删除hdfs上的文件）
	 * @return
	 */
	public String deleteCsvData(){
		result = new HashMap<String,Object>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		CsvData csvData = csvDataService.findCsvById(id);
		if(csvData==null){
			result.put("success", false);
			result.put("message", "该文件不存在！");
		}else{
			try{
				result = csvDataService.deleteCsvData(hdfsConfigPath,csvData);
			}catch(Exception e){
				result.put("success", false);
				result.put("message", "文件删除异常，请重试！");
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 修改csvData
	 * @return
	 */
	public String updateCsvData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		CsvData csvData = csvDataService.findCsvById(id);
		csvData.setFileEncoding(fileEncoding);
		csvData.setFileSeparator(fileSeparator);
		csvData.setTitleFlag(titleFlag);
		csvDataService.updateCsvData(csvData);
		return SUCCESS;
	}
	
	
	@JSON(serialize=false)
	public File getCsv() {
		return csv;
	}
	public void setCsv(File csv) {
		this.csv = csv;
	}
	@JSON(serialize=false)
	public String getCsvFileName() {
		return csvFileName;
	}
	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}
	@JSON(serialize=false)
	public String getFileEncoding() {
		return fileEncoding;
	}
	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}
	public Map<String, Object> getResult() {
		return result;
	}
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	@JSON(serialize=false)
	public boolean getTitleFlag() {
		return titleFlag;
	}
	public void setTitleFlag(boolean titleFlag) {
		this.titleFlag = titleFlag;
	}
	@JSON(serialize=false)
	public CsvDataService getCsvDataService() {
		return csvDataService;
	}
	public void setCsvDataService(CsvDataService csvDataService) {
		this.csvDataService = csvDataService;
	}
	@JSON(serialize=false)
	public String getFileSeparator() {
		return fileSeparator;
	}
	public void setFileSeparator(String fileSeparator) {
		this.fileSeparator = fileSeparator;
	}
	@JSON(serialize=false)
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@JSON(serialize=false)
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	@JSON(serialize=false)
	public PageModel<CsvData> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<CsvData> pageModel) {
		this.pageModel = pageModel;
	}
	@JSON(serialize=false)
	public CsvData getCsvData() {
		return csvData;
	}
	public void setCsvData(CsvData csvData) {
		this.csvData = csvData;
	}
	@JSON(serialize=false)
	public Map<String, List<Map<String, String>>> getFinalMap() {
		return finalMap;
	}
	public void setFinalMap(Map<String, List<Map<String, String>>> finalMap) {
		this.finalMap = finalMap;
	}
	
}
