package com.integrity.system.dataType.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.util.DateFormat;
import com.integrity.system.dataType.bean.DataObject;
import com.integrity.system.dataType.bean.DataType;
import com.integrity.system.dataType.service.DataTypeService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author liuBf
 * 对象管理action
 *
 */
@SuppressWarnings("serial")
public class DataTypeAction extends ActionSupport{
	private Logger logger = Logger.getLogger(DataTypeAction.class);
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	private DataObject dataObject;
	private DataTypeService dataTypeService;
	private InputStream inputStream; 
	PageModel<DataObject> pageModel;
	
	/**
	 * @return
	 * 跳转到表格
	 */
	public String info(){
		return SUCCESS;
	}
	/**
	 * @return
	 * 跳转到添加页面
	 */
	public String addObject(){
		return SUCCESS;
	}
	
	/**
	 * @return
	 * 获取对象数据列表
	 */
	public String searchList(){
		response.setContentType("text/html;charset=utf-8");//解决中文乱码
		DataObject dataObject = new DataObject();
		pageModel = dataTypeService.searchObjList(pageModel, dataObject);
		return SUCCESS;
	}
    /**
     * @return
     * 保存和编辑对象信息
     */
    public String saveObject(){
    	response.setContentType("text/html;charset=UTF-8");//解决中文乱码
    	String id = request.getParameter("oid");
    	String cTime =request.getParameter("cTime");
    	String name = request.getParameter("name");
    	String details = request.getParameter("details");
    	String arrays = request.getParameter("arrays");//属性名
    	String arrays2 = request.getParameter("arrays2");//属性类型
    	String arrays3 = request.getParameter("ischeck");
    	Date dt=new Date();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
    	String nowTime="";
    	nowTime = df.format(dt);
    	if(StringUtils.isNotEmpty(id)){
    		String pids = request.getParameter("pids");
    		String pronames = request.getParameter("pronames");
    		String protypes = request.getParameter("protypes");
    		DataObject ho = new DataObject();
        	ho.setCreateTime(DateFormat.getDateBy(cTime));//创建时间
        	ho.setId(Long.parseLong(id));
        	ho.setName(name);
        	ho.setDetails(details);
    		if(ho != null){
    			dataTypeService.updateDataObject(ho, arrays, arrays2,arrays3, id,pids, pronames, protypes);
    			try {
    				inputStream = new ByteArrayInputStream(String.valueOf(ho.getId()).getBytes("utf-8"));
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    		
    	}else{
    		DataObject ho = new DataObject();
        	ho.setCreateTime(DateFormat.getDateBy(nowTime));//创建时间
        	ho.setName(name);
        	ho.setDetails(details);
        	//存储对象同时 向属性表中添加属性信息
        	if(ho!= null){
        		dataTypeService.saveDataObject(ho,arrays,arrays2,arrays3);
        	try {
        		inputStream = new ByteArrayInputStream(String.valueOf(ho.getId()).getBytes("utf-8"));
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
        }
    		
    	}
		return SUCCESS;
	}
    /**
     * @return
     * 编辑信息回显数据获取
     */
    public String updateObjByid(){
    	String id = request.getParameter("oid");
    	if(StringUtils.isNotEmpty(id)){
    		dataObject = dataTypeService.forUpdateObjs(Long.parseLong(id));
    		List<DataType> pros= dataTypeService.findProList(Long.valueOf(id));
    		request.setAttribute("objects", dataObject);
    		request.setAttribute("lists", pros);
    	}
		return SUCCESS;
	}
    /**
     * 批量删除对象
     */
    public void delObjByid(){
    	String gmpId = request.getParameter("ids");
		String[] ids = gmpId.split(",");
		List<String>  Ids= Arrays.asList(ids);
		try {
			dataTypeService.delObject(Ids);
		} catch (Exception e) {
			logger.error("删除对象异常！"+e.getMessage());
		}
    }
    
    /**
     * 删除对象属性
     */
    public void delProByid(){
    	String gmpId = request.getParameter("ids");
		String[] ids = gmpId.split(",");
		List<String>  Ids= Arrays.asList(ids);
		try {
			dataTypeService.delProByid(Ids);
		} catch (Exception e) {
			logger.error("删除属性异常！"+e.getMessage());
		}
    }

	@JSON(serialize=false)
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	@JSON(serialize=false)
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void setDataTypeService(DataTypeService dataTypeService) {
		this.dataTypeService = dataTypeService;
	}
	@JSON(serialize=false)
	public DataObject getDataObject() {
		return dataObject;
	}

	public void setDataObject(DataObject dataObject) {
		this.dataObject = dataObject;
	}

	public PageModel<DataObject> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<DataObject> pageModel) {
		this.pageModel = pageModel;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	
	

}
