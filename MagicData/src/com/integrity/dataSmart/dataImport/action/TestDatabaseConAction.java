package com.integrity.dataSmart.dataImport.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.TransMeta;

import com.integrity.dataSmart.dataImport.bean.DataImportTask;
import com.integrity.dataSmart.dataImport.bean.ImportTablesAndPropertys;
import com.integrity.dataSmart.dataImport.bean.TDatabase;
import com.integrity.dataSmart.dataImport.pojo.DataImpAddForm;
import com.integrity.dataSmart.dataImport.service.DBService;
import com.integrity.dataSmart.dataImport.service.DataImpAddService;
import com.integrity.dataSmart.dataImport.service.DataImpTaskService;
import com.integrity.dataSmart.dataImport.service.DatabaseXML;
import com.integrity.dataSmart.titanGraph.action.SearchDetailAction;
import com.integrity.login.action.Encryption;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class TestDatabaseConAction extends ActionSupport {
	private Logger logger = Logger.getLogger(SearchDetailAction.class);
	private String connectionName;// 连接名
	private String connectionDB;// 数据库
	private String connectionDbName;
	private String connectionServerName;// 主机名称
	private String connectionPort;// 端口
	private String connectionDBUserName;// 账号
	private String connectionDBPassword;// 密码
	private long id;
	private String dbid;
	private String json;
	private String urlDB;
	DatabaseXML databaseXML = new DatabaseXML();
	private DBService dBService;
	private InputStream inputStream;
	private String start = "{\"total\":\"" + 0 + "\",\"rows\":[{";
	private String end = "]}";
	StringBuffer jsonbuffer = new StringBuffer();
	Connection con = null;
	PreparedStatement psm = null;
	ResultSet rs = null;
	int limitsql;
	/**
	 * 导入新增用Session内对象
	 */
	/*private DataImpAddPojo dataImpAddPojo;*/

	/**
	 * 导入新增用Session内Key
	 */
	private static String dataImpSessKey = "DataImport_Add_Task";

	/**
	 * 导入新增用表单
	 */
	private DataImpAddForm addForm;
	/** 导入数据库 配置页面 */
	/**
	 * 新增数据导入的Service
	 */
	private DataImpAddService dataImpAddService;
	
	private DataImpTaskService dataImpTaskService;
	/**
	 * 测试数据库连接
	 * DERBY, AS, INTERBASE, INFINIDB, DBASE, EXASOL4, EXTENDB, FIREBIRD, GENERIC, GREENPLUM, SQLBASE, H2, HYPERSONIC, DB2, INFOBRIGHT, INFORMIX, INGRES, VECTORWISE, CACHE, KINGBASEES, LucidDB, SAPDB, MONETDB, MSACCESS, MSSQL, MSSQLNATIVE, MYSQL, NEOVIEW, NETEZZA, ORACLE, ORACLERDB, POSTGRESQL, REMEDY-AR-SYSTEM, SAPR3, SQLITE, SYBASE, SYBASEIQ, TERADATA, UNIVERSE, VERTICA, VERTICA5
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "unused" })
	public String testDBCon(){
		// String[] connection= databaseXML.getURLDriver(connectionDB);
		try {
			KettleEnvironment.init();
			TransMeta transMeta = new TransMeta();
			if(connectionDBPassword.equals("QwR2Qae3QfdgKJyy3IO")){
				try {
					connectionDBPassword=dBService.findDB(id).getConnectionDBPasswordBak();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("密码转码问题：",e);
				}
			}
			DatabaseMeta dm = new DatabaseMeta(connectionName, connectionDB,
					DatabaseMeta.dbAccessTypeCode[0], connectionServerName,
					connectionDbName, connectionPort,
					connectionDBUserName, connectionDBPassword);
			List<DatabaseMeta> dmList = new ArrayList<DatabaseMeta>();
			dmList.add(dm);
			transMeta.setDatabases(dmList);
			Database db = new Database(transMeta, dm);
			db.connect();
			if (db != null) {
				try {
					inputStream = new ByteArrayInputStream(
							"{\"total\":\"0\",\"rows\":[{\"result\":\"连接成功！\"}]}"
									.getBytes("utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					logger.error("编码问题：",e);
				}
				return SUCCESS;
			} else {
				return ERROR;
			}
		} catch (KettleException e) {
			e.printStackTrace();
			System.out.println("连接失败");
			logger.error("数据库连接失败：",e);
			return ERROR;
		}

	}

	/**
	 * @throws Exception 
	 * @throws UnsupportedEncodingException
	 *             保存数据库连接
	 * @return
	 * @throws
	 */
	public String saveDBCon(){
		List<TDatabase> dbs=dBService.getDBbyConnName(connectionName);
		if(dbs.size()>0){
			return ERROR;
		}
		TDatabase database = new TDatabase();
		database.setConnectionDB(connectionDB);
		database.setConnectionDBPassword(connectionDBPassword);
		database.setConnectionDbName(connectionDbName);
		database.setConnectionDBUserName(connectionDBUserName);
		database.setConnectionName(connectionName);
		database.setConnectionPort(connectionPort);
		database.setConnectionServerName(connectionServerName);
		try {
			dBService.save(database);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("密码转码问题：",e);
		}
		try {
			inputStream = new ByteArrayInputStream(
					"{\"total\":\"0\",\"rows\":[{}]}".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("编码问题：",e);
		}
		return SUCCESS;
	}

	/**
	 * @throws Exception 
	 * @throws UnsupportedEncodingException
	 *             修改数据库连接
	 * @return
	 * @throws
	 */
	public String editDBCon(){
		List<DataImportTask> dit=dataImpTaskService.getDITaskByDataConnId(id);
		List<DataImportTask> dit2=new ArrayList<DataImportTask>();
		for (DataImportTask dataImportTask : dit) {
			if(dataImportTask.getRunStatus()==1){
				return ERROR;
			}
			if(dataImportTask.getRunStatus()==0){
				dit2.add(dataImportTask);
			}
		}
		String dbConnNm;
		try {
			dbConnNm = dBService.findDB(id).getConnectionName();
			if(!connectionName.equals(dbConnNm)){
				List<TDatabase> dbs=dBService.getDBbyConnName(connectionName);
				if(dbs.size()>0){
					return ERROR;
				}
			}
			if(connectionDBPassword.equals("QwR2Qae3QfdgKJyy3IO")){
					connectionDBPassword=dBService.findDB(id).getConnectionDBPasswordBak();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("密码转码问题：",e1);
		}
		TDatabase database = new TDatabase();
		database.setConnectionDB(connectionDB);
		database.setConnectionDBPassword(connectionDBPassword);
		database.setConnectionDbName(connectionDbName);
		database.setConnectionDBUserName(connectionDBUserName);
		database.setConnectionName(connectionName);
		database.setConnectionPort(connectionPort);
		database.setConnectionServerName(connectionServerName);
		database.setId(id);
		try {
			dBService.edit(database);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("密码转码问题：",e);
		}
		try {
			inputStream = new ByteArrayInputStream(
					"{\"total\":\"0\",\"rows\":[{}]}".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("编码问题：",e);
		}
		return SUCCESS;
	}

	/**
	 * @throws UnsupportedEncodingException
	 *             删除数据库连接
	 * @return
	 * @throws
	 */
	@SuppressWarnings("static-access")
	public String deleteDBCon(){
		List<DataImportTask> dit=dataImpTaskService.getDITaskByDataConnId(id);
		List<DataImportTask> dit2=new ArrayList<DataImportTask>();
		for (DataImportTask dataImportTask : dit) {
			if(dataImportTask.getRunStatus()==1){
				return ERROR;
			}
			if(dataImportTask.getRunStatus()==0){
				dit2.add(dataImportTask);
			}
		}	
		if(dit2.size()>0){
			dataImpTaskService.deleteDTaskList(dit2);
		}
		
		TDatabase database = new TDatabase();
		database.setConnectionDB(connectionDB);
		database.setConnectionDBPassword(connectionDBPassword);
		database.setConnectionDbName(connectionDbName);
		database.setConnectionDBUserName(connectionDBUserName);
		database.setConnectionName(connectionName);
		database.setConnectionPort(connectionPort);
		database.setConnectionServerName(connectionServerName);
		database.setId(id);
		try {
			dBService.delete(database);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除TDabase问题：",e);
		}
		try {
			inputStream = new ByteArrayInputStream(
					"{\"total\":\"0\",\"rows\":[{}]}".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("编码问题：",e);
		}
		return SUCCESS;
	}

	/**
	 * @throws Exception 
	 * @throws UnsupportedEncodingException
	 *             获取数据库连接
	 * @return
	 * @throws
	 */
	public String getDBCon() {

		@SuppressWarnings("unchecked")
		List<TDatabase> dBList = dBService.findAllDB("TDatabase");
		StringBuffer json = new StringBuffer();
		for (TDatabase database : dBList) {
			json.append("{\"id\":\"" + database.getId()
					+ "\",\"connectionDB\":\"" + database.getConnectionDB()
					+ "\",\"connectionDBPassword\":\""
					+ "QwR2Qae3QfdgKJyy3IO"//database.getConnectionDBPasswordBak()
					+ "\",\"connectionDbName\":\""
					+ database.getConnectionDbName()
					+ "\",\"connectionDBUserName\":\""
					+ database.getConnectionDBUserName()
					+ "\",\"connectionName\":\"" + database.getConnectionName()
					+ "\",\"connectionServerName\":\""
					+ database.getConnectionServerName()
					+ "\",\"connectionPort\":\"" + database.getConnectionPort()
					+ "\"},");
		}
		try {
			inputStream = new ByteArrayInputStream(
					(start
							+ json.toString().substring(1,
									json.toString().length() - 1) + end)
							.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("编码问题：",e);
		}
		return SUCCESS;
	}

	/**
	 * @throws Exception 
	 * @throws UnsupportedEncodingException
	 *             获取数据库连接
	 * @return
	 * @throws
	 */
	public String getDB(){
		TDatabase dB;
		StringBuffer json = new StringBuffer();
		try {
			dB = dBService.findDB(id);
		
		
		json.append("{\"id\":\"" + dB.getId() + "\",\"connectionDB\":\""
				+ dB.getConnectionDB() + "\",\"connectionDBPassword\":\""
				+  "QwR2Qae3QfdgKJyy3IO"//dB.getConnectionDBPasswordBak()
				+ "\",\"connectionDbName\":\""
				+ dB.getConnectionDbName()
				+ "\",\"connectionDBUserName\":\""
				+ dB.getConnectionDBUserName() + "\",\"connectionName\":\""
				+ dB.getConnectionName() + "\",\"connectionServerName\":\""
				+ dB.getConnectionServerName() + "\",\"connectionPort\":\""
				+ dB.getConnectionPort() + "\"},");
		} catch (Exception e1) {
			logger.error("密码转码问题：",e1);
			e1.printStackTrace();
		}
		try {
			inputStream = new ByteArrayInputStream(
					(start
							+ json.toString().substring(1,
									json.toString().length() - 1) + end)
							.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("编码问题：",e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/** 导入数据库 表选择 */

	/**
	 * @throws UnsupportedEncodingException
	 *             获取数据库 表
	 * @return
	 * @throws
	 */
	public String getDBTables(){
		List<TDatabase> dBList = dBService.findAllDB("Database");
		StringBuffer json = new StringBuffer();
		for (TDatabase database : dBList) {
			json.append("{\"id\":\"" + database.getId()
					+ "\",\"connectionName\":\"" + database.getConnectionName()
					+ "\",\"connectionDB\":\"" + database.getConnectionDB()
					+ "\"},");
		}
		try {
			inputStream = new ByteArrayInputStream(
					(start
							+ json.toString().substring(1,
									json.toString().length() - 1) + end)
							.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("编码问题：",e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @throws UnsupportedEncodingException
	 *             获取数据库 表
	 * @return
	 * @throws
	 */
	public String getDBPropertyByTable(){
		List<ImportTablesAndPropertys> importTablesAndPropertys = dBService
				.findAllDB("ImportTablesAndPropertys");
		StringBuffer json = new StringBuffer();
		for (ImportTablesAndPropertys database : importTablesAndPropertys) {
			json.append("{\"id\":\"" + database.getId() + "\",\"dbid\":\""
					+ database.getDbid() + "\",\"connectionName\":\""
					+ database.getConnectionName()
					+ "\",\"connectionDBTableName\":\""
					+ database.getConnectionDBTableName()
					+ "\",\"connectionDB\":\"" + database.getConnectionDB()
					+ "\"},");
		}
		try {
			inputStream = new ByteArrayInputStream(
					(start
							+ json.toString().substring(1,
									json.toString().length() - 1) + end)
							.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("编码问题：",e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @throws Exception 
	 * @throws KettleException 
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 *             获取数据库所有的表
	 * @return
	 * @throws
	 */
	public String importGetAllTables(){
		TDatabase dbase;
		try {
			dbase = dBService.findDB(id);
		
			try {
				KettleEnvironment.init();
			} catch (KettleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TransMeta transMeta = new TransMeta();
			DatabaseMeta dm = new DatabaseMeta(dbase.getConnectionName(), dbase.getConnectionDB(),
					DatabaseMeta.dbAccessTypeCode[0], dbase.getConnectionServerName(),
					dbase.getConnectionDbName(), dbase.getConnectionPort(),
					dbase.getConnectionDBUserName(), dbase.getConnectionDBPassword());
			List<DatabaseMeta> dmList = new ArrayList<DatabaseMeta>();
			dmList.add(dm);
			transMeta.setDatabases(dmList);
			Database db = new Database(transMeta, dm);
			db.connect();
			 
				//获得表名
			String[] tNames = db.getTablenames();
			for (String string : tNames) {
				jsonbuffer.append("{\"connectionDBTableName\":\""
						+ string + "\",\"id\":\""
						+ dbase.getId() + "\",\"connectionName\":\""
						+ dbase.getConnectionName()
						+ "\",\"connectionDB\":\""
						+ dbase.getConnectionDB() + "\"},");
			}		
			inputStream = new ByteArrayInputStream(
					(start
							+ jsonbuffer.toString().substring(1,
									jsonbuffer.toString().length() - 1) + end)
							.getBytes("utf-8"));
			db.disconnect();
		} catch (Exception e) {
			logger.error("密码转码问题：",e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}


	/**
	 * 登入选择表页面
	 * 
	 * @return
	 */
	public String databaseConnectedTableselect() {
		// clear data
		dBService.clearData("ImportTablesAndPropertys");
		return SUCCESS;
	}

	/**
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 *             获取数据库所有的表
	 * @return
	 * @throws
	 */
	public String importSelectTables(){
		ImportTablesAndPropertys importTablesAndPropertys = new ImportTablesAndPropertys();
		importTablesAndPropertys.setConnectionDB(connectionDB);
		importTablesAndPropertys.setConnectionName(connectionName);
		importTablesAndPropertys.setDbid(dbid);
		String dbtablename[] = connectionDbName.split(";");
		// clear data
		dBService.clearData("ImportTablesAndPropertys");
		for (String string : dbtablename) {
			importTablesAndPropertys.setConnectionDBTableName(string);
			try {
				dBService.save(importTablesAndPropertys);
			} catch (Exception e) {
				logger.error("密码转码问题：",e);
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	public String demoEdit(){
		return SUCCESS;
	}
	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
	public String getConnectionServerName() {
		return connectionServerName;
	}

	public void setConnectionServerName(String connectionServerName) {
		this.connectionServerName = connectionServerName;
	}

	public String getConnectionPort() {
		return connectionPort;
	}

	public void setConnectionPort(String connectionPort) {
		this.connectionPort = connectionPort;
	}

	public String getConnectionDBUserName() {
		return connectionDBUserName;
	}

	public void setConnectionDBUserName(String connectionDBUserName) {
		this.connectionDBUserName = connectionDBUserName;
	}

	public String getConnectionDBPassword() {
		return connectionDBPassword;
	}

	public void setConnectionDBPassword(String connectionDBPassword) {
		this.connectionDBPassword = connectionDBPassword;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getConnectionDB() {
		return connectionDB;
	}

	public void setConnectionDB(String connectionDB) {
		this.connectionDB = connectionDB;
	}

	public DBService getdBService() {
		return dBService;
	}

	public void setdBService(DBService dBService) {
		this.dBService = dBService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getDbid() {
		return dbid;
	}

	public void setDbid(String dbid) {
		this.dbid = dbid;
	}


/*	public DataImpAddPojo getDataImpAddPojo() {
		return dataImpAddPojo;
	}

	public void setDataImpAddPojo(DataImpAddPojo dataImpAddPojo) {
		this.dataImpAddPojo = dataImpAddPojo;
	}*/

	public static String getDataImpSessKey() {
		return dataImpSessKey;
	}

	public static void setDataImpSessKey(String dataImpSessKey) {
		TestDatabaseConAction.dataImpSessKey = dataImpSessKey;
	}

	public DataImpAddForm getAddForm() {
		return addForm;
	}

	public void setAddForm(DataImpAddForm addForm) {
		this.addForm = addForm;
	}

	public DataImpAddService getDataImpAddService() {
		return dataImpAddService;
	}

	public void setDataImpAddService(DataImpAddService dataImpAddService) {
		this.dataImpAddService = dataImpAddService;
	}

	public int getLimitsql() {
		return limitsql;
	}

	public void setLimitsql(int limitsql) {
		this.limitsql = limitsql;
	}

	public DataImpTaskService getDataImpTaskService() {
		return dataImpTaskService;
	}

	public void setDataImpTaskService(DataImpTaskService dataImpTaskService) {
		this.dataImpTaskService = dataImpTaskService;
	}

	public String getConnectionDbName() {
		return connectionDbName;
	}

	public void setConnectionDbName(String connectionDbName) {
		this.connectionDbName = connectionDbName;
	}
}
