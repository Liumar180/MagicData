package com.integrity.lawCase.exportLaw.action;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.integrity.lawCase.exportLaw.bean.CaseObjBean;
import com.integrity.lawCase.exportLaw.service.ExportLCExcelService;
import com.integrity.lawCase.exportLaw.util.ExportExcel;
import com.opensymphony.xwork2.ActionSupport;

public class ExportLCExcelAjaxAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(ExportLCAjaxAction.class);
	
	/** 导出的对象id集合*/
	private String ids;
	
	private String currentTimeStr;
	
	private ExportLCExcelService exportLCExcelService;
	
	private String fileNameD;
	
	private InputStream downloadFileStream;
	
	String webPath = ServletActionContext.getServletContext().getRealPath("/");
	
	public String exportLawObjs(){
		List<CaseObjBean> cobList = new ArrayList<CaseObjBean>();
		cobList = exportLCExcelService.getExportList(ids);
		ExportExcel<CaseObjBean> ex = new ExportExcel<CaseObjBean>();
		OutputStream out = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date();
	    String dateStr = sdf.format(date);
	    currentTimeStr = dateStr;
		String modelPath = webPath+"images"+File.separator+"caseExcelModel"+File.separator+"caseModel.xls";
		String exportPath = webPath+"images"+File.separator+"caseExcelModel"+File.separator+"excase"+File.separator+"案件"+dateStr+".xls";
		try {
			out = new FileOutputStream(exportPath);
			ex.exportExcel(dateStr+"案件导出",modelPath,cobList, out,"yyyy-MM-dd HH:mm:ss");
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
	
	public String downloadLCExel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileNameStr = "案件"+currentTimeStr+".xls";
		try {
			try {
				downloadFileStream = ServletActionContext.getServletContext().getResourceAsStream("images"+File.separator+"caseExcelModel"+File.separator+"excase"+File.separator + fileNameStr);
				String agent = request.getHeader("User-Agent").toLowerCase();
				//Firefox
				if (null != agent && -1 != agent.indexOf("firefox")) {
					fileNameD = new String(fileNameStr.getBytes("UTF-8"), "ISO-8859-1");
				//其他
				} else{
					fileNameD = URLEncoder.encode(fileNameStr, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	@JSON(serialize=false)
	public ExportLCExcelService getExportLCExcelService() {
		return exportLCExcelService;
	}

	public void setExportLCExcelService(ExportLCExcelService exportLCExcelService) {
		this.exportLCExcelService = exportLCExcelService;
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

	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}

	public void setDownloadFileStream(InputStream downloadFileStream) {
		this.downloadFileStream = downloadFileStream;
	}

	
}
