package com.integrity.dataSmart.dataImport.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.integrity.dataSmart.dataImport.bean.TDatabase;
import com.integrity.dataSmart.dataImport.service.DataImpAddService;
import com.integrity.dataSmart.dataImport.service.DataManageService;
import com.integrity.dataSmart.dataImport.util.TitanStructureUtil;
import com.opensymphony.xwork2.ActionSupport;

public class DataManageAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private DataImpAddService dataImpAddService;
	private DataManageService dataManageService;
	
	private String dbConnId;
	
	Map<TDatabase, Map<String, String>> connSource = null;
	
	Map<String, String> tableFields = null;
	
	Map<String,Map<String,String>> outerM = null;
	
	Map<String,Object> result = null;
	
	Map<String,List<Map<String,String>>> finalList = null;
	
	List<Map<String,Object>> titanList = null;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession hs = request.getSession();
	
	/**
	 * 根据dbConnId获取数据库的表和表结构
	 * @param dbConnId
	 * @return String
	 */
	public String findAllTables(){
		try {
			String lastDbConnId = (String)hs.getAttribute("dbConnId");
			if(lastDbConnId!=null&&lastDbConnId.equals(dbConnId)){
				connSource = (Map<TDatabase, Map<String, String>>)hs.getAttribute("connSource");
			}
			if(connSource==null){
				connSource = dataImpAddService.getSourceTables(Long.parseLong(dbConnId));
			}
			outerM = new HashMap<String,Map<String,String>>();
			Map<String,String> innerM = null;
			if (null != connSource && connSource.size() > 0) {
				Set<TDatabase> connSet = connSource.keySet();
				if (null != connSet && connSet.size() > 0&& null != (connSet.iterator().next())) {
					TDatabase conn = connSet.iterator().next();
					tableFields = connSource.get(conn);
					Set<String> ks = tableFields.keySet();
				    for(String s:ks){
				    	String str = tableFields.get(s);
				    	String[] strArr = str.split(";");
				    	innerM = new HashMap<String,String>();
				    	for(int i=0;i<strArr.length;i++){
				    		String[] strArr2 = strArr[i].split(":");
				    		innerM.put(strArr2[0], strArr2[1]);
				    	}
				    	outerM.put(s, innerM);
				    }
				    hs.setAttribute("dbConnId", dbConnId);
					hs.setAttribute("connSource", connSource);
					hs.setAttribute("conn", conn);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取表的前5条记录
	 * @param tableName
	 * @return String
	 */
	public String queryTopFiveRecords(){
		TDatabase conn = (TDatabase)hs.getAttribute("conn");
		try {
			String tableName = request.getParameter("tableName");
			if(tableName!=null&&!"".equals(tableName)){
				result = dataManageService.getRecords(conn,tableName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据前台传的SQL获取表的前5条记录
	 * @param tableName
	 * @return String
	 */
	public String queryTopFiveRecordsBySQL(){
		TDatabase conn = (TDatabase)hs.getAttribute("conn");
		try {
			String sql = request.getParameter("sql");
			String column = request.getParameter("column");
			if(sql!=null&&!"".equals(sql)&&column!=null&&!"".equals(column)){
				result = dataManageService.getRecordsBySQL(conn,sql,column);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取Titan结构
	 * @return
	 */
	public String getTitanStructure(){
		titanList = TitanStructureUtil.getTitanStructure();
		return SUCCESS;
	}
	
	@JSON(serialize=false)
	public DataImpAddService getDataImpAddService() {
		return dataImpAddService;
	}

	public void setDataImpAddService(DataImpAddService dataImpAddService) {
		this.dataImpAddService = dataImpAddService;
	}

	@JSON(serialize=false)
	public DataManageService getDataManageService() {
		return dataManageService;
	}

	public void setDataManageService(DataManageService dataManageService) {
		this.dataManageService = dataManageService;
	}

	@JSON(serialize=false)
	public String getDbConnId() {
		return dbConnId;
	}

	public void setDbConnId(String dbConnId) {
		this.dbConnId = dbConnId;
	}

	@JSON(serialize=false)
	public Map<TDatabase, Map<String, String>> getConnSource() {
		return connSource;
	}

	public void setConnSource(Map<TDatabase, Map<String, String>> connSource) {
		this.connSource = connSource;
	}

	@JSON(serialize=false)
	public Map<String, String> getTableFields() {
		return tableFields;
	}

	public void setTableFields(Map<String, String> tableFields) {
		this.tableFields = tableFields;
	}

	public Map<String, Map<String, String>> getOuterM() {
		return outerM;
	}

	public void setOuterM(Map<String, Map<String, String>> outerM) {
		this.outerM = outerM;
	}

	public Map<String, List<Map<String, String>>> getFinalList() {
		return finalList;
	}

	public void setFinalList(Map<String, List<Map<String, String>>> finalList) {
		this.finalList = finalList;
	}

	public List<Map<String, Object>> getTitanList() {
		return titanList;
	}

	public void setTitanList(List<Map<String, Object>> titanList) {
		this.titanList = titanList;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

}
