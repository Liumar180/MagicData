package com.integrity.dataSmart.dataImport.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.integrity.dataSmart.dataImport.bean.TDatabase;
import com.integrity.dataSmart.dataImport.service.DBService;
import com.integrity.dataSmart.dataImport.service.DataImpAddService;
import com.opensymphony.xwork2.ActionSupport;

public class DataImpAddAjaxAction extends ActionSupport{

	private static final long serialVersionUID = 7301508153141666806L;

	/*
	 * 任务Id，用于名称检查
	 */
	private String taskId;
	/*
	 * 任务名称，用于名称检查
	 */
	private String taskName;
	
	/*
	 * 检查结果
	 */
	private String checkResult;
	
	/**
	 * 新增数据导入的Service
	 */
	private DataImpAddService dataImpAddService;
	
	/**
	 * 操作数据库连接的Service
	 */
	private DBService dBService;
	
	/**
	 * 数据库连接列表JSON数据
	 */
	private JSONObject dataBaseJson;
	
	/**
	 *上传文件夹列表JSON数据
	 */
	private JSONObject uploadFileJson;

	/**
	 * 判断任务名是否可以使用
	 * 
	 * @return exception	数据库暂时连接失败
	 * 		   true 		用户名可用
	 * 		   false 		当前用户名已经存在
	 *         null         用户名不能为空
	 */
	public String checkTaskName() {
		if (taskName != null && !"".equals(taskName)) {
			taskName = taskName.trim();
			Boolean resultBoolean = dataImpAddService.findTaskNameExist(taskName,taskId);
			if(resultBoolean){
				checkResult = "true";
			}else{
				checkResult = "false";
			}
		} else {
			checkResult = "null";
		}
		return SUCCESS;
	}
	
	/**
	 * 获得数据库链接
	 * @return
	 */
	public String getDataUrl(){
		@SuppressWarnings("unchecked")
		List<TDatabase> dBList = dBService.findAllDB("TDatabase");
		Map<Long, TDatabase> dataBaseMaps = new HashMap<Long, TDatabase>();
		if (null != dBList && dBList.size() > 0) {
			for (TDatabase tempTDB : dBList) {
				if (null != tempTDB) {
					tempTDB.setConnectionDBPassword("*******");
					dataBaseMaps.put(tempTDB.getId(), tempTDB);
				}
			}
		}
		dataBaseJson = JSONObject.fromObject(dataBaseMaps);
		return SUCCESS;
	}
	
	/**
	 * 获得上传目录连接列表
	 * @return
	 */
	public String getUploadFileDir(){
		String webRealPath = ServletActionContext.getServletContext().getRealPath("/");
		String ftpUploadPath = dataImpAddService.getEmlFtpUploadPath(webRealPath);
		File uploadBasicFile = new File(ftpUploadPath);
		if(!uploadBasicFile.exists()){
			uploadBasicFile.mkdirs();
		}
		if(uploadBasicFile.exists()){
			if(uploadBasicFile.isDirectory()){
				Map<String,String> uploadFileMap = dataImpAddService.getUploadFiles(ftpUploadPath);
				uploadFileJson = JSONObject.fromObject(uploadFileMap);
				return SUCCESS;
			}else{
				return ERROR;
			}
		}else{
			return ERROR;
		}
	}
		
    

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setDataImpAddService(DataImpAddService dataImpAddService) {
		this.dataImpAddService = dataImpAddService;
	}

	public JSONObject getDataBaseJson() {
		return dataBaseJson;
	}

	public void setDataBaseJson(JSONObject dataBaseJson) {
		this.dataBaseJson = dataBaseJson;
	}

	public JSONObject getUploadFileJson() {
		return uploadFileJson;
	}

	public void setUploadFileJson(JSONObject uploadFileJson) {
		this.uploadFileJson = uploadFileJson;
	}

	public void setdBService(DBService dBService) {
		this.dBService = dBService;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
	
}
