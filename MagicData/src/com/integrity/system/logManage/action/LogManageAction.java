package com.integrity.system.logManage.action;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.system.logManage.bean.LogObject;
import com.integrity.system.logManage.service.LogManageService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LogManageAction extends ActionSupport{
	private ObjectMapper mapper = JacksonMapperUtil.getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); 
	private InputStream inputStream;
	private Long id;
	private String userName; //用户名称
	private Date creatTime;//创建时间
	private String operationType;//操作类型
	private String operation;//操作
	private Date endTime;//
	private int pageNo;
	private int pageSize;
	public PageModel<LogObject> pageModel;
	private LogManageService logManageService;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private ServletContext sc = ServletActionContext.getServletContext();
	Map<String,Map<String,String>> allMap = (Map<String, Map<String, String>>) sc.getAttribute(ConstantManage.DATADICTIONARY);
	Map<String,String> OPERATIONTYPE = allMap.get(ConstantManage.OPERATIONTYPE);//方向
	@JSON(serialize=false)
	public LogManageService getLogManageService() {
		return logManageService;
	}
	public void setLogManageService(LogManageService logManageService) {
		this.logManageService = logManageService;
	}
	@JSON(serialize=false)
	public PageModel<LogObject> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<LogObject> pageModel) {
		this.pageModel = pageModel;
	}
	@JSON(serialize=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}@JSON(serialize=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}@JSON(serialize=false)
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}@JSON(serialize=false)
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}@JSON(serialize=false)
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}@JSON(serialize=false)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@JSON(serialize=false)
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	@JSON(serialize=false)
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@JSON(serialize=false)
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String logManageIndex(){
		request.getSession().setAttribute("OPERATIONTYPE",OPERATIONTYPE);
		return SUCCESS;
	}
	public String getLogList(){
		LogObject logObject=new LogObject();
		logObject.setCreatTime(creatTime);
		logObject.setEndTime(endTime);
		logObject.setOperation(operation);
		logObject.setOperationType(operationType);//OPERATIONTYPE.get(key)
		logObject.setUserName(userName);
		pageModel=new PageModel<LogObject>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		pageModel=logManageService.getAllLogObject(pageModel,logObject);
		if(pageModel.getList().size()==0){
			pageModel.setPageNo(pageModel.getTotalPage());
			pageModel =logManageService.getAllLogObject(pageModel,logObject);
		}
		List<LogObject> list=new ArrayList<LogObject>();
		for (LogObject lo : pageModel.getList()) {
			String type="";
			for (String ty : lo.getOperationType().split(",")) {
				type+=OPERATIONTYPE.get(ty)+"/";
			}
			lo.setOperationType(type.substring(0,type.length()-1));
			list.add(lo);
		}
		pageModel.setList(list);
		request.getSession().setAttribute("pageModel",pageModel);
		String json = null;
		try {
			json = mapper.writeValueAsString(pageModel);
			if (json != null) {
				inputStream = new ByteArrayInputStream(json.getBytes("utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
