package com.integrity.lawCase.caseManage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.bean.WorkRecord;
import com.integrity.lawCase.caseManage.pojo.WorkRecordPojo;
import com.integrity.lawCase.caseManage.service.CaseManageService;
import com.integrity.lawCase.exportLaw.util.ExportExcel;
import com.opensymphony.xwork2.ActionSupport;

public class ExportWRExcelAjaxAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private String currentTimeStr;
	private String fileNameD;
	private CaseObject caseObject;
	private String fileNameStr;
	private PageModel<WorkRecord> recordPage;
	private InputStream downloadFileStream;
	
	private CaseManageService caseManageService;
	
	String webPath = ServletActionContext.getServletContext().getRealPath("/");
	
	/**
	 * 导出工作记录
	 * @return
	 */
	public String exportRecordListPage(){
		Long id = caseObject.getId();
		List<WorkRecordPojo> wrList = caseManageService.findWorkRecordByCaseIdExcel(id, recordPage);
		String[] headers = { "标题", "内容", "创建时间"};
		ExportExcel<WorkRecordPojo> ex = new ExportExcel<WorkRecordPojo>();
		OutputStream out = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date();
	    String dateStr = sdf.format(date);
	    CaseObject cO = caseManageService.findCaseById(id);
	    currentTimeStr = dateStr;
	    fileNameStr = cO.getCaseName()+"WR"+currentTimeStr+".xls";
		String exportPath = webPath+"images"+File.separator+"caseExcelModel"+File.separator+"exWorkRecord"+File.separator+fileNameStr;
		try {
			out = new FileOutputStream(exportPath);
			ex.exportExcel(dateStr+"工作记录导出",headers,wrList, out,"yyyy-MM-dd HH:mm:ss");
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
	
	public String downloadWorkRecordExcel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Long id = caseObject.getId();
		CaseObject cO = caseManageService.findCaseById(id);
		fileNameStr = cO.getCaseName() +"WR" + currentTimeStr + ".xls";
		downloadFileStream = ServletActionContext.getServletContext().getResourceAsStream("images"+File.separator+"caseExcelModel"+File.separator+"exWorkRecord"+File.separator + fileNameStr);
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
	
	public String getCurrentTimeStr() {
		return currentTimeStr;
	}

	public void setCurrentTimeStr(String currentTimeStr) {
		this.currentTimeStr = currentTimeStr;
	}

	public String getFileNameD() {
		return fileNameD;
	}

	public void setFileNameD(String fileNameD) {
		this.fileNameD = fileNameD;
	}
	public PageModel<WorkRecord> getRecordPage() {
		return recordPage;
	}
	public void setRecordPage(PageModel<WorkRecord> recordPage) {
		this.recordPage = recordPage;
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

	public String getFileNameStr() {
		return fileNameStr;
	}

	public void setFileNameStr(String fileNameStr) {
		this.fileNameStr = fileNameStr;
	}

	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}

	public void setDownloadFileStream(InputStream downloadFileStream) {
		this.downloadFileStream = downloadFileStream;
	}
	
}
