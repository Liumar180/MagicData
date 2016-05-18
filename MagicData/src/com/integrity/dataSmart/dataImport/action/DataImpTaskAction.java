package com.integrity.dataSmart.dataImport.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrity.dataSmart.dataImport.bean.DataImportTask;
import com.integrity.dataSmart.dataImport.pojo.DataImportTaskDetail;
import com.integrity.dataSmart.dataImport.service.DataImpTaskService;
import com.integrity.dataSmart.impAnalyImport.main.insertMailData;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.integrity.login.util.PageInfo;
import com.opensymphony.xwork2.ActionSupport;

public class DataImpTaskAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
	private static final int DEFAULT_BUFFER_SIZE = 0;

	private Logger logger = Logger.getLogger(DataImpTaskAction.class);
	HttpServletRequest request = ServletActionContext.getRequest();

	/**
	 * 数据导入任务基本Service
	 */
	private DataImpTaskService dataImpTaskService;
	

	/**
	 * 任务Id(用于任务执行)
	 */
	private String taskId;
	private Integer isSave;//任务保存 -1表示保存失败
	private String isStart;//任务执行
	private DataImportTask dataimporttask;
	
	//任务列表分页数据
	private List<DataImportTask> dataRows;
	private PageInfo curPage;
	private Boolean result;
	
	private Map<String,Object> taskValueMaxJsonResult;
	
	private DataImportTaskDetail taskDetail;
	

	public Map<String, Object> getTaskValueMaxJsonResult() {
		return taskValueMaxJsonResult;
	}

	public void setTaskValueMaxJsonResult(Map<String, Object> taskValueMaxJsonResult) {
		this.taskValueMaxJsonResult = taskValueMaxJsonResult;
	}

	/**
	 * 数据导入页面
	 * @return
	 */
	public String viewImportPage(){
		
		return SUCCESS;
	}
	
	/**
	 * 数据导入任务列表
	 * @return
	 */
	public String findImportTaskList() {
		dataRows= dataImpTaskService.findImportTasks(curPage);
		return SUCCESS;
	}
	/**
	 * 保存任务
	 * @return
	 */
	public String saveTask(){
		isSave = -1;
		HttpSession session = ServletActionContext.getRequest().getSession();
		String username = (String) session.getAttribute("username");//用户名
		String taskName = request.getParameter("taskName");//任务名
	    String jsonStr = request.getParameter("jsonStr");
	    String taskType =request.getParameter("taskType");//任务类型
	    String taskDesc =request.getParameter("taskDesc");//任务描述
	    Date createTime = new Date(System.currentTimeMillis());
		//String realpath = ServletActionContext.getServletContext().getRealPath("/")+"images"+File.separator+"uploadJsonFile"+File.separator+"json"+System.currentTimeMillis()+".json";//获取服务器路径
		//boolean upload = StringOrFileUtils.string2File(jsonStr, realpath);
		//if(upload){
		DataImportTask diTask = new DataImportTask();
		diTask.setTaskName(taskName);
		diTask.setXmlPath(jsonStr);
		diTask.setBorntime(createTime);
		diTask.setRunStatus(0);
		diTask.setTaskDesc(taskDesc);
		diTask.setCreatePerson(username);
		if(taskType != null && !"".equals(taskType)){
			diTask.setTaskType(Integer.parseInt(taskType));
		}else{
			diTask.setTaskType(1);
		}
		if(taskId != null && !"".equals(taskId)){
			diTask.setId(Integer.parseInt(taskId));
			boolean isUpdate= dataImpTaskService.updateTask(diTask);
			if(isUpdate){isSave = Integer.parseInt(taskId);}
		}else{
			isSave = dataImpTaskService.saveDataImpTask(diTask);
		}
		//}else{isSave = "false";}
		return SUCCESS;
	}
	/**
	 * 开始执行任务
	 * @return
	 */
	public String startTask(){
		result = false;
		String url = null;
		String driverClass = null;
		String jdbcUrl = null;
		String user = null;
		String password = null;
		InputStream inputStream = null;
		Properties p = new Properties();
		try {
			inputStream = insertMailData.class.getClassLoader().getResourceAsStream("../config/jdbc/jdbc.properties");
			p.load(inputStream);
			url = "http://"+p.getProperty("spark.rIp")+"/v1/submissions/create";
			driverClass = p.getProperty("hibernate.driverClass");
			jdbcUrl = p.getProperty("hibernate.jdbcUrl");
			user = p.getProperty("hibernate.user");
			password = p.getProperty("hibernate.password");
		} catch (Exception e) {
			System.out.println("spark.rIp init error ");
		}finally{
				try {
					if(inputStream!=null)
						inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> environmentVariables = new HashMap<String,String>();
		environmentVariables.put("SPARK_ENV_LOADED", "1");
		Map<String,String> sparkProperties = new HashMap<String,String>();
		sparkProperties.put("spark.jars", "file:/usr/local/newbigdata/spark-1.6.0-bin-without-hadoop/app/TitanSpark_youjar15.jar");
		sparkProperties.put("spark.driver.supervise", "false");
		sparkProperties.put("spark.app.name", "CopyofsparkToTitan");
		sparkProperties.put("spark.eventLog.enabled", "false");
		sparkProperties.put("spark.submit.deployMode", "client");                                                                              
		sparkProperties.put("spark.master", "spark://testdocker:7077");
		map.put("action", "CreateSubmissionRequest");
		map.put("appArgs", new String[]{taskId,driverClass,jdbcUrl,user,password});
		map.put("appResource", "file:/usr/local/newbigdata/spark-1.6.0-bin-without-hadoop/app/TitanSpark_youjar15.jar");
		map.put("clientSparkVersion", "1.6.1");
		map.put("environmentVariables", environmentVariables);
		map.put("mainClass", "sparkToTitan.CopyofsparkToTitan");
		map.put("sparkProperties", sparkProperties);
		ObjectMapper mapper = JacksonMapperUtil.getObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e2) {
			e2.printStackTrace();
		}
        DefaultHttpClient httpClient = new DefaultHttpClient();
        if(url != null){
        	HttpPost httpPost = new HttpPost(url);
        	httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        	httpPost.setHeader("Accept", APPLICATION_JSON); 
        	StringEntity se = null;
        	try {
        		se = new StringEntity(json);
        	} catch (UnsupportedEncodingException e1) {
        		e1.printStackTrace();
        	}
        	se.setContentType(CONTENT_TYPE_TEXT_JSON);
        	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        	httpPost.setEntity(se);
        
        try {
        	long startTime = System.currentTimeMillis();  
        	HttpResponse response = httpClient.execute(httpPost);
            long endTime = System.currentTimeMillis();  
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("statusCode:" + statusCode);
            logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
            if (statusCode != HttpStatus.SC_OK) {  
                logger.error("Method failed:" + response.getStatusLine());  
            }else{
            	result = true;
            } 
            // Read the response body  
            EntityUtils.toString(response.getEntity());  
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        }else{
        	logger.error("Message:请配置正确的spark.rIp参数");
        }
		return SUCCESS;
	}
	/**
	 * @return
	 * 获取回显数据
	 */
	public String editTask(){
		if(taskId != null && !"".equals(taskId)){
			dataimporttask = dataImpTaskService.toUpdateTaskById(Integer.parseInt(taskId));
		}
		return SUCCESS;
		
	}
	/**
	 * 任务详细信息
	 * @return
	 */
	public String showTaskDetail() {
		taskDetail= dataImpTaskService.findTaskDetail(taskId);
		return SUCCESS;
	}
	
	/**
	 * 判断是否存在正在执行的任务
	 * true 有正在执行
	 */
	public String judgeExecute() {
		result = dataImpTaskService.judgeExecute();
		return SUCCESS;
	}
	
	/**
	 * 删除任务
	 */
	public String deleteTaskById() {
		try {
			dataImpTaskService.deleteDTaskById(taskId);
		} catch (Exception e) {
			logger.error("删除任务异常taskId："+taskId,e);
		}
		return SUCCESS;
	}
	
	/**
	 * 执行任务
	 * @return
	 */
	public String executeTask() {
		String webRealPath = ServletActionContext.getServletContext().getRealPath("/");
		DataImportTask dt = null;
		if (null != taskId && !"".equals(taskId)) {
			dt = dataImpTaskService.getDITaskById(Long.parseLong(taskId));
		}
		if(null!=dt){
			if(null!=dt.getTaskType()&&1==dt.getTaskType()){
				result = dataImpTaskService.startJob(taskId,webRealPath);
			}else{
				result = dataImpTaskService.startEmlJob(dt,webRealPath);
			}
		}
		return SUCCESS;
	}

	public void setDataImpTaskService(DataImpTaskService dataImpTaskService) {
		this.dataImpTaskService = dataImpTaskService;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<DataImportTask> getDataRows() {
		return dataRows;
	}

	public void setDataRows(List<DataImportTask> dataRows) {
		this.dataRows = dataRows;
	}

	public PageInfo getCurPage() {
		return curPage;
	}

	public void setCurPage(PageInfo curPage) {
		this.curPage = curPage;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}
	
	public DataImportTaskDetail getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(DataImportTaskDetail taskDetail) {
		this.taskDetail = taskDetail;
	}

	public Integer getIsSave() {
		return isSave;
	}

	public void setIsSave(Integer isSave) {
		this.isSave = isSave;
	}

	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}
	public DataImportTask getDataimporttask() {
		return dataimporttask;
	}
	public void setDataimporttask(DataImportTask dataimporttask) {
		this.dataimporttask = dataimporttask;
	}
	/*
	 * 获得任务进度
	 */
	public String taskValueMax(){
		/*
		 * 通过数据库获得进度
		 */
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		DataImportTask bean = dataImpTaskService.getDITaskById(Long.valueOf(id));
		taskValueMaxJsonResult = new HashMap<String,Object>();
		taskValueMaxJsonResult.put("totality",bean.getTotality());
		taskValueMaxJsonResult.put("importCount",bean.getImportCount());
		taskValueMaxJsonResult.put("isFinish",bean.getRunStatus());
		/*原先通过properties获得进度
		InputStream inputStream = null;
		taskValueMaxJsonResult = new HashMap<String,Object>();
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("../config/dataImport/processBar.properties");
			Properties p = new Properties();
			p.load(inputStream);
			taskValueMaxJsonResult.put("totality",p.getProperty("totality").trim());
			taskValueMaxJsonResult.put("importCount",p.getProperty("importCount").trim());
			taskValueMaxJsonResult.put("isFinish",p.getProperty("isFinish").trim());
		} catch (Exception e) {
			logger.error("进度条配置文件读取错误!", e);
		}finally{
				try {
					if(inputStream!=null)
						inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}*/
		return SUCCESS;
	}
	
}
