package com.integrity.lawCase.exportLaw.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.service.CaseManageService;
import com.integrity.lawCase.exportLaw.service.ImportLCService;
import com.integrity.lawCase.exportLaw.util.ReadExcelUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ImportLCAjaxAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(ImportLCAjaxAction.class);
	
	/**excel上传*/
	private File excel;
	private String excelFileName;
	
	private CaseManageService cmService;
	private ImportLCService importLCService;
	
	private Map<String,Object> dataMap;
	
	/**
	 * 导入案件到数据库
	 * @return String
	 */
	public String importLawObjs(){
		String excelDir = ServletActionContext.getServletContext().getRealPath(File.separator+"images"+File.separator+"uploadExcelTemp"+File.separator);
		String finalPath = excelDir+File.separator+excelFileName;
		try{
			File excelDirFile = new File(excelDir); // 判断文件夹是否存在,如果不存在则创建文件夹
	        if (!excelDirFile.exists()) {
	        	excelDirFile.mkdirs();
	        }
			if(null!=excel){
				String[] fileFit = new String[] { ".xls",".xlsx" };
				boolean fitFlag = false;
				for (int i = 0; i < fileFit.length; i++) {
	                if (excelFileName.toLowerCase().endsWith(fileFit[i])) {
	                	fitFlag = true;
	                    break;
	                }	
	            }
				if(fitFlag){
					InputStream is = new FileInputStream(excel);
			        File saveExcel = new File(excelDir,excelFileName);
			        if(saveExcel.exists()){
			        	//如果文件存在先删除
			        	saveExcel.delete();
			        }
		        	saveExcel.createNewFile();
		        	OutputStream os = new FileOutputStream(saveExcel);	        
			        byte[] buffer = new byte[1024];
			        int length = 0;
		            while ((length = is.read(buffer)) != -1) {
		                os.write(buffer, 0, length);
		            }
			        os.close();
			        is.close();
				}else{
					dataMap = new HashMap<String,Object>();
					dataMap.put("success", false);
					dataMap.put("message", "该文件不是excel文件，请下载本站模板后重试！");
					return SUCCESS;
				}
			}
			
			//入库
			if(finalPath!=null&&!"".equals(finalPath)){
				List<CaseObject> list = ReadExcelUtil.readLCExcel(finalPath);
				if(ReadExcelUtil.isLCModelLegalFlag){
					//如果是合法模板，就入库
					dataMap = importLCService.addCases(list);
				}else{
					dataMap = new HashMap<String,Object>();
					dataMap.put("success", false);
					dataMap.put("message", "该excel文件使用的不是本站模板，请先下载本站模板！");
					return SUCCESS;
				}
				
			}
			File f = new File(finalPath);
			if(f.exists()){
				f.delete();
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	@JSON(serialize=false)
	public File getExcel() {
		return excel;
	}

	public void setExcel(File excel) {
		this.excel = excel;
	}

	@JSON(serialize=false)
	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	@JSON(serialize=false)
	public CaseManageService getCmService() {
		return cmService;
	}

	public void setCmService(CaseManageService cmService) {
		this.cmService = cmService;
	}

	@JSON(serialize=false)
	public ImportLCService getImportLCService() {
		return importLCService;
	}

	public void setImportLCService(ImportLCService importLCService) {
		this.importLCService = importLCService;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	

}
