package com.integrity.lawCase.caseManage.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.caseManage.pojo.WorkAllocationPojo;
import com.integrity.lawCase.caseManage.service.CaseManageService;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.exportLaw.util.ExportExcel;
import com.opensymphony.xwork2.ActionSupport;

public class ExportAllExcelAjaxAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private String fileNameD;
	private String currentTimeStr;
	private CaseObject caseObject;
	private PageModel<WorkAllocation> allocationPage;
	private String fileNameStr;
	
	private InputStream downloadFileStream;
	
	private CaseManageService caseManageService;
	
	String webPath = ServletActionContext.getServletContext().getRealPath("/");
	private ServletContext sc = ServletActionContext.getServletContext();
	Map<String,String> type_property = (Map<String, String>) sc.getAttribute(ConstantManage.TYPE_PROPERTY);
	
	/**
	 * 导出工作配置
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String exportAllocationListPage(){
		Long id = caseObject.getId();
		List<WorkAllocationPojo> wrList = caseManageService.findAllocationByCaseIdExcel(id, allocationPage,type_property);
		String[] headers = {"对象类型","对象名称", "方案配置情况", "负责人","创建时间","效果反馈","备注"};
		ExportExcel<WorkAllocationPojo> ex = new ExportExcel<WorkAllocationPojo>();
		OutputStream out = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date();
	    String dateStr = sdf.format(date);
	    CaseObject cO = caseManageService.findCaseById(id);
	    currentTimeStr = dateStr;
	    fileNameStr = cO.getCaseName()+"WA"+currentTimeStr+".xls";
		String exportPath = webPath+"images"+File.separator+"caseExcelModel"+File.separator+"exWorkAllocation"+File.separator+fileNameStr;
		try {
			out = new FileOutputStream(exportPath);
			ex.exportExcel(dateStr+"工作配置导出",headers,wrList, out,"yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			e.printStackTrace();	
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String downloadAllocationExcel() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Long id = caseObject.getId();
		CaseObject cO = caseManageService.findCaseById(id);
		fileNameStr = cO.getCaseName() +"WA"+ currentTimeStr + ".xls";
		downloadFileStream = ServletActionContext.getServletContext().getResourceAsStream("images"+File.separator+"caseExcelModel"+File.separator+"exWorkAllocation"+File.separator + fileNameStr);
		try {
			String agent = request.getHeader("User-Agent").toLowerCase();
			//Firefox
			if (null != agent && -1 != agent.indexOf("firefox")) {
				fileNameD = new String(fileNameStr.getBytes("UTF-8"), "ISO-8859-1");
			//其他
			} else{
				fileNameD = URLEncoder.encode(fileNameStr, "UTF-8");
			}
//			fileNameD = new String(fileNameStr.getBytes("UTF8"),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getFileNameD() {
		return fileNameD;
	}

	public void setFileNameD(String fileNameD) {
		this.fileNameD = fileNameD;
	}
	public CaseObject getCaseObject() {
		return caseObject;
	}
	public void setCaseObject(CaseObject caseObject) {
		this.caseObject = caseObject;
	}
	public CaseManageService getCaseManageService() {
		return caseManageService;
	}
	public void setCaseManageService(CaseManageService caseManageService) {
		this.caseManageService = caseManageService;
	}

	public PageModel<WorkAllocation> getAllocationPage() {
		return allocationPage;
	}

	public void setAllocationPage(PageModel<WorkAllocation> allocationPage) {
		this.allocationPage = allocationPage;
	}

	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}

	public void setDownloadFileStream(InputStream downloadFileStream) {
		this.downloadFileStream = downloadFileStream;
	}

	public String getFileNameStr() {
		return fileNameStr;
	}

	public void setFileNameStr(String fileNameStr) {
		this.fileNameStr = fileNameStr;
	}

	public String getCurrentTimeStr() {
		return currentTimeStr;
	}

	public void setCurrentTimeStr(String currentTimeStr) {
		this.currentTimeStr = currentTimeStr;
	}

}
