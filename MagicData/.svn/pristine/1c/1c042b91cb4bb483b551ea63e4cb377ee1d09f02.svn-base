package com.integrity.dataSmart.dataImport.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowBuffer;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.StringObjectId;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.selectvalues.SelectMetadataChange;
import org.pentaho.di.trans.steps.selectvalues.SelectValuesMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassDef;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassDef.ClassType;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassMeta;

import com.integrity.dataSmart.dataImport.bean.DataImportTask;
import com.integrity.dataSmart.dataImport.bean.TDatabase;
import com.integrity.dataSmart.dataImport.dao.DataImpTaskDao;
import com.integrity.dataSmart.dataImport.pojo.DataImpAddForm;
import com.integrity.dataSmart.dataImport.pojo.DataImpAddPojo;
import com.integrity.dataSmart.map.util.DataImportLogicUtils;

/**
 * 新增数据导入任务Service
 * @author HanXue
 * 
 */
public class DataImpAddService {
	
	private Logger logger = Logger.getLogger(DataImpAddService.class);

	/**
	 * .eml数据导入配置文件路径
	 */
	public static final String EML_CONF_FILE_PATH =  "WEB-INF"
			+ File.separator + "config" + File.separator + "dataImport" + File.separator
			+"emlImportConfig.properties";
	
	/**
	 * .eml数据导入配置FTP上传目录-Key
	 */
	public static final String EML_FTP_UPLOAD_PATH_KEY = "ftpUploadPath";
	/**
	 * 数据库导入时生产的XML保存的文件夹名称
	 */
	public static final String DATA_IMPORT_DBXML_DIRNAME ="MagicData";

	/**
	 * 目标数据源配置文件保存路径
	 */
	public final static String TARGET_DATA_COFIG_PATH = "WEB-INF"
			+ File.separator + "config" + File.separator + "dataImport" + File.separator
			+ "tagetDataConfig.properties";
	/**
	 * 目标数据源配置文件 - KEY目标数据源包含的字段
	 */
	public final static String TARGET_DATA_NAME = "name";
	
	/**
	 * 目标数据源配置文件 - KEY目标数据源包含的字段对应的数据类型
	 */
	public final static String TARGET_DATA_TYPE = "type";
	
	/**
	 * 目标数据源配置文件 - KEY目标数据源包含字段对应的数据类型对应的pantahoo插件类型
	 */
	public final static String TARGET_DATA_TYPENUM = "typeNum";
	
	/**
	 * 数据导入任务执行的状态标识 - 未执行=0
	 */
	public final static Integer RUNSTATUS_UNRUN = 0;
	
	/**
	 * 数据导入任务执行的状态标识 - 正在执行=1
	 */
	public final static Integer RUNSTATUS_RUNNING = 1;
	
	/**
	 * 数据导入任务执行的状态标识 - 执行完成=2
	 */
	public final static Integer RUNSTATUS_FINISH = 2;
	
	/**
	 * 数据导入任务执行的状态标识 - 执行时出现错误=-1
	 */
	public final static Integer RUNSTATUS_ERROR = -1;
	
	/**
	 * 数据导入任务在SQL语句条件下Session内Map中源数据的默认表名
	 */
	public final static String SQL_TABLE_NAME = "sqlTable";
	
	/**
	 * 数据导入任务统一日期格式
	 */
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 数据导入任务数据库操作DAO
	 */
	private DataImpTaskDao dbImpTaskDao;

	/**
	 * 数据连接操作Service
	 */
	private DBService dbService;

	
	/**
	 * 获得指定数据库连接中所有的表及表的字段信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public Map<TDatabase,Map<String, String>> getSourceTables(long connId) throws Exception {
		Map<TDatabase,Map<String, String>> connSource = new HashMap<TDatabase,Map<String, String>>();
		Map<String, String> sourceTablesMap = new HashMap<String, String>();
		TDatabase conn = dbService.findDB(connId);
		if (null == conn)
			return connSource;
		try {
			KettleEnvironment.init();
			TransMeta transMeta = new TransMeta();
			DatabaseMeta dm = new DatabaseMeta(conn.getConnectionName(),
					conn.getConnectionDB(), DatabaseMeta.dbAccessTypeCode[0],
					conn.getConnectionServerName(),
					conn.getConnectionDbName(), conn.getConnectionPort(),
					conn.getConnectionDBUserName(),
					conn.getConnectionDBPasswordBak());
			List<DatabaseMeta> dmList = new ArrayList<DatabaseMeta>();
			dmList.add(dm);
			transMeta.setDatabases(dmList);
			Database db = new Database(transMeta, dm);
			db.connect();
			String[] tableNames = db.getTablenames();
			for (int i = 0; i < tableNames.length; i++) {
				try{
					//List<ValueMetaInterface> valueList = db.getTableFields(tableNames[i]).getValueMetaList();
					RowBuffer rb = new RowBuffer(db.getTableFields(tableNames[i]));
					List<ValueMetaInterface> valueList = rb.getRowMeta()
							.getValueMetaList();
					StringBuffer tempValStr = new StringBuffer();
					// String[] typeCodess = ValueMetaInterface.typeCodes;// 数据类型列表
					for (int j = 0; j < valueList.size(); j++) {
						ValueMetaInterface vm = valueList.get(j);
						tempValStr
								.append(vm.getName().trim() + ":"
										+ vm.getOriginalColumnTypeName().trim())// typeCodess[vm.getType()]
								.append(";");
					}
					sourceTablesMap.put(tableNames[i].trim(), tempValStr.toString().trim());
				}catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					continue;
				}
			}
			connSource.put(conn, sourceTablesMap);
			db.disconnect();
		} catch (KettleException e) {
			e.printStackTrace();
		}
		return connSource;
	}
	
	/**
	 * 判断数据导入任务的任务名称是否存在
	 */
	public boolean findTaskNameExist(String taskName,String taskId) {
		boolean result = dbImpTaskDao.checkTaskNameExist(taskName,taskId);
		return result;
	}

	/**
	 * 测试SQL语句执行结果
	 * 
	 * @return
	 * @throws Exception 
	 */
	public RowMetaAndData getOneRowBySQL(long connId, String sqlStr) throws Exception{
		RowMetaAndData rowData = null;
		TDatabase conn = dbService.findDB(connId);
		if (null == conn)
			return rowData;
		try {
			KettleEnvironment.init();
			TransMeta transMeta = new TransMeta();
			DatabaseMeta dm = new DatabaseMeta(conn.getConnectionName(),
					conn.getConnectionDB(), DatabaseMeta.dbAccessTypeCode[0],
					conn.getConnectionServerName(),
					conn.getConnectionDbName(), conn.getConnectionPort(),
					conn.getConnectionDBUserName(),
					conn.getConnectionDBPasswordBak()) ;
			dm.setConnectSQL(sqlStr);
			List<DatabaseMeta> dmList = new ArrayList<DatabaseMeta>();
			dmList.add(dm);
			transMeta.setDatabases(dmList);
			Database db = new Database(transMeta, dm);
			db.connect();
			rowData = db.getOneRow(sqlStr);
			db.disconnect();
		} catch (KettleException e) {
			e.printStackTrace();
		}
		return rowData;
	}

	/**
	 * 获得SQL语句条件下的源字段信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> getSourceFieldsForSQL(Long connId,
			String sqlStr) throws Exception {
		RowMetaAndData rowData = null;
		try {
			rowData = getOneRowBySQL(connId, sqlStr);
			return createSourceFieldsForSQL(rowData);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 整合SQL语句条件下的源字段信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> createSourceFieldsForSQL(RowMetaAndData rowData){
		Map<String, String> sourceFieldsMap = new HashMap<String, String>();
		try {
			if(null!=rowData){
				List<ValueMetaInterface> valueMetaList = rowData.getRowMeta()
						.getValueMetaList();
				StringBuffer tempValStr = new StringBuffer();
				for (ValueMetaInterface vm : valueMetaList) {
					sourceFieldsMap.put(vm.getName(), vm.getOriginalColumnTypeName());
					tempValStr.append(
							vm.getName() + ":" + vm.getOriginalColumnTypeName())
							.append(";");
				}
				sourceFieldsMap.put(SQL_TABLE_NAME, tempValStr.toString());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return sourceFieldsMap;
	}

	/**
	 * 获得本地目标数据源字段- 字段类型字符串对应Map
	 * 
	 * @return
	 */
	public Map<String, String> getTargetTables(String dataPath,
			String targetName) {
		Map<String, String> targetTablesMap = new HashMap<String, String>();
		Properties targetDataProperties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(dataPath + File.separator
					+ TARGET_DATA_COFIG_PATH);
			targetDataProperties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null != targetDataProperties && targetDataProperties.size() > 0) {
			String dataFiledsName = targetDataProperties.getProperty(targetName
					+ "." + TARGET_DATA_NAME);
			if (null != dataFiledsName && !"".equals(dataFiledsName)
					&& (dataFiledsName.trim()).length() > 0) {
				String dataFieldsType = targetDataProperties
						.getProperty(targetName + "." + TARGET_DATA_TYPE);
				String[] nameList = dataFiledsName.split(",");
				String[] typeList = dataFieldsType.split(",");
				if (null != nameList && nameList.length > 0) {
					for (int i = 0; i < nameList.length; i++) {
						targetTablesMap.put(nameList[i], typeList[i]);
					}
				}
			}
		}
		return targetTablesMap;
	}
	
	
	/**
	 * 获得本地目标数据源字段名 - 字段类型数字代码的对应Map
	 * 
	 * @return
	 */
	public Map<String, String> getTargetTablesNum(String dataPath,
			String targetName) {
		Map<String, String> targetTablesMap = new HashMap<String, String>();
		Properties targetDataProperties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(dataPath + File.separator
					+ TARGET_DATA_COFIG_PATH);
			targetDataProperties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null != targetDataProperties && targetDataProperties.size() > 0) {
			String dataFiledsName = targetDataProperties.getProperty(targetName
					+ "." + TARGET_DATA_NAME);
			if (null != dataFiledsName && !"".equals(dataFiledsName)
					&& (dataFiledsName.trim()).length() > 0) {
				String dataFieldsTypeNum = targetDataProperties
						.getProperty(targetName + "." + TARGET_DATA_TYPENUM);
				String[] nameList = dataFiledsName.split(",");
				String[] typeNumList = dataFieldsTypeNum.split(",");
				if (null != nameList && nameList.length > 0) {
					for (int i = 0; i < nameList.length; i++) {
						targetTablesMap.put(nameList[i], typeNumList[i]);
					}
				}
			}
		}
		return targetTablesMap;
	}

	/**
	 * 获得上传文件下的所有子目录(不包含文件)
	 * @return
	 */
	public Map<String,String> getUploadFiles(String ftpUploadPath){
		Map<String,String> fileMap = new HashMap<String,String>();
		String fileMapValue = ftpUploadPath.replaceAll("\\\\","|");
		fileMap.put(".\\", fileMapValue);
		getFiles(ftpUploadPath,fileMap,ftpUploadPath);
		return fileMap;
	}
	
	/**
	 * 获得FTP上传目录配置
	 */
	public String getEmlFtpUploadPath(String webPath){
		String resultStr = "";
		Properties emlConfProperties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(webPath + File.separator + EML_CONF_FILE_PATH);
			emlConfProperties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null != emlConfProperties && emlConfProperties.size() > 0) {
			resultStr = emlConfProperties.getProperty(EML_FTP_UPLOAD_PATH_KEY);
		}
		return resultStr;
	}
		
	/**
	 * 递归文件夹下的所有子目录(不包含文件)
	 * @param filePath
	 * @param fileMap
	 */
	private void getFiles(String filePath,Map<String,String> fileMap,String rootPath){
		File root = new File(filePath);
		File[] files = root.listFiles();
		for(File file:files){     
			if(file.isDirectory()){
				String fileAbsolutePath  = file.getAbsolutePath();
				String fileMapValue = fileAbsolutePath.replaceAll("\\\\","|");
				String fileMapKey = fileAbsolutePath.substring(rootPath.length(),fileAbsolutePath.length());
				fileMap.put("."+fileMapKey, fileMapValue);
		    	getFiles(file.getAbsolutePath(),fileMap,rootPath);
		     }
		}
	}
	
	/**
	 * 保存或更新eml任务
	 * @param taskId
	 * @param addForm
	 * @param updateFlag
	 */
	public void saveOrUpdateDEmlTask(Integer taskId,DataImpAddForm addForm,String updateFlag){
		DataImportTask obj = null;
		//如果是更新操作 从数据库中取得对象
		if("1".equals(updateFlag)){
			obj = getDITaskById(new Long(taskId));
		}else{
			obj = new DataImportTask();
		}
		obj.setTaskName(addForm.getTaskName());
		obj.setTaskType(addForm.getTaskType());
		obj.setUploadFile(addForm.getUploadFile());
		obj.setRunStatus(RUNSTATUS_UNRUN);
		if(!"1".equals(updateFlag)){
			obj.setBorntime(new Date());
		}
		dbImpTaskDao.saveDataImpTask(obj);
	}
	/**
	 * 存储新增或更新的数据库连接导入任务
	 * @param updateFlag 为“0”表示新增 为“1”表示更新 
	 * @return
	 * @throws Exception 
	 */
	public boolean saveOrUpdateDImpTask(DataImpAddPojo dataPojo, DataImpAddForm addForm,
			String dataPath,String updateFlag) throws Exception {
		DataImportTask obj = null;
		//如果是更新操作 从数据库中取得对象
		if("1".equals(updateFlag)){
			obj = getDITaskById(new Long(dataPojo.getTaskId()));
		}else{
			obj = new DataImportTask();
		}
		obj.setTaskName(dataPojo.getTaskName());
		obj.setDataConnId(dataPojo.getDbConnId());
		obj.setTaskType(dataPojo.getTaskType());
		boolean sqlFlag = dataPojo.getSqlFlag();
		if (sqlFlag) {
			obj.setSourceType(1);
		} else {
			obj.setSourceType(0);
		}
		obj.setSourceName(dataPojo.getSourceTable().trim());
		String tempSqlOrder = dataPojo.getSqlText();

		tempSqlOrder = tempSqlOrder.replaceAll("\\n", " ");
		tempSqlOrder = tempSqlOrder.replaceAll("\\t", " ");
		tempSqlOrder = tempSqlOrder.replaceAll("\\r", " ");
		obj.setSqlOrder(tempSqlOrder);
		obj.setTargetName(dataPojo.getTargetTable().trim());
		// 设置所有源字段
		Map<String, String> sourceFieldsMap = dataPojo.getSourceFields();
		String sourceFields = "";
		String sourceTypes = "";
		for (Entry<String, String> temp : sourceFieldsMap.entrySet()) {
			sourceFields = sourceFields + temp.getKey().trim() + ",";
			sourceTypes = sourceTypes + temp.getValue().trim() + ",";
		}
		obj.setSourceFields(sourceFields.substring(0, sourceFields.length() - 1));
		obj.setSourceTypes(sourceTypes.substring(0, sourceTypes.length() - 1));

		String checkSFields = addForm.getCheckSourceFields();
		String[] checkTempSFields = checkSFields.split(",");
		obj.setCheckSourceFields(checkSFields);
		String checkSType = "";
		if (null != checkTempSFields && checkTempSFields.length > 0) {
			for (String tempStr : checkTempSFields) {
				checkSType = checkSType + sourceFieldsMap.get(tempStr.trim()).trim() + ",";
			}
			obj.setCheckSourceTypes(checkSType.substring(0,
					checkSType.length() - 1));
		} else {
			obj.setCheckSourceTypes(checkSType);
		}

		Map<String, String> targetFieldsMap = dataPojo.getTargetFields();
		String checkTFields = addForm.getCheckTargetFields();
		String[] checkTempTFields = checkTFields.split(",");
		obj.setCheckTargetFields(checkTFields);
		String checkTType = "";
		if (null != checkTempTFields && checkTempTFields.length > 0) {
			for (String tempStr : checkTempTFields) {
				checkTType = checkTType + targetFieldsMap.get(tempStr.trim()).trim()
						+ ",";
			}
			obj.setCheckTargetTypes(checkTType.substring(0,
					checkTType.length() - 1));
		} else {
			obj.setCheckTargetTypes(checkTType);
		}
		obj.setRunStatus(DataImpAddService.RUNSTATUS_UNRUN);
		obj.setBorntime(new Date(System.currentTimeMillis()));
		//首续步骤需要id先保存
		obj.setXmlPath("url");
		dbImpTaskDao.saveDataImpTask(obj);
		//创建XML文件
		String xmlPath = makeDImpXml(dataPath, obj);
		//如果是更新操作 删除原文件
		if("1".equals(updateFlag)){
			File oldFile = new File(obj.getXmlPath());
			oldFile.deleteOnExit();
			obj.setXmlPath(xmlPath);
		}else{
			obj.setXmlPath(xmlPath);
		}
		if(null!=xmlPath&&!"".equals(xmlPath)){
			dbImpTaskDao.saveDataImpTask(obj);
			return true;
		}else{
			//如果生成有问题删除任务
			dbImpTaskDao.deleteDTaskById(obj.getId()+"");;
			return false;
		}
	}

	/**
	 * 生成数据导入任务的XML文件
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String makeDImpXml(String dataPath, DataImportTask taskObj) throws Exception {
		int rootIndex = dataPath.indexOf(DATA_IMPORT_DBXML_DIRNAME);
		String rootPath = dataPath.substring(0, rootIndex)+DATA_IMPORT_DBXML_DIRNAME+File.separator
				+ "MagicDataDataImport" + File.separator;
		File dirFile = new File(rootPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		String xmlPath = "";
		TDatabase conn = dbService.findDB(taskObj.getDataConnId());
		if (null == conn)
			return xmlPath;
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String xmlFileName = taskObj.getTaskName()
				+ sf.format(taskObj.getBorntime()) + ".xml";
		xmlPath = rootPath + xmlFileName;
		try {

			KettleEnvironment.init();

			TransMeta transMeta = new TransMeta();
			transMeta.setName(taskObj.getTaskName());
			StringObjectId sId = new StringObjectId(taskObj.getTaskName());
			transMeta.setObjectId(sId);
			DatabaseMeta dm = new DatabaseMeta(conn.getConnectionName(),
					conn.getConnectionDB(), DatabaseMeta.dbAccessTypeCode[0],
					conn.getConnectionServerName(),
					conn.getConnectionDbName(), conn.getConnectionPort(),
					conn.getConnectionDBUserName(),
					conn.getConnectionDBPasswordBak());
			List<DatabaseMeta> dmList = new ArrayList<DatabaseMeta>();
			dmList.add(dm);
			// 关联数据库连接
			transMeta.setDatabases(dmList);
			// 表输出
			TableInputMeta tableInputMeta = new TableInputMeta();
			tableInputMeta.setDatabaseMeta(dm);
			String sqlStr = "";
			Integer sqLFlag = taskObj.getSourceType();
			if (sqLFlag == 0) {
				sqlStr = "select * from " + taskObj.getSourceName().trim();
			} else {
				sqlStr = taskObj.getSqlOrder().trim();
			}

			tableInputMeta.setSQL(sqlStr);
			StepMeta tableInputStep = new StepMeta("表输出", tableInputMeta);
			transMeta.addStep(tableInputStep);

			// 字段选择
			SelectValuesMeta selectValuesMeta = new SelectValuesMeta();
			selectValuesMeta.setParentStepMeta(tableInputStep);
			String checkSourceFileds = taskObj.getCheckSourceFields();
			String[] checkSourceFList = checkSourceFileds.split(",");
			String[] checkTargetFList = taskObj.getCheckTargetFields().split(",");
			String sourceFileds = taskObj.getSourceFields();
			selectValuesMeta.setSelectName(new String[0]);
			// selectValuesMeta.setSelectName(checkSourceFList);
			selectValuesMeta.setSelectingAndSortingUnspecifiedFields(false);
			boolean delFlag = true;
			if(checkSourceFList.length>= sourceFileds.split(",").length){
				delFlag = false;
			}
			if(delFlag){
				String[] sourceFList = sourceFileds.split(",");
			    for (String tempCheckStr : checkSourceFList) {
			    	tempCheckStr = tempCheckStr.trim();
			    	for(int m=0;m<sourceFList.length;m++){
			    		if(tempCheckStr.equals(sourceFList[m].trim())){
			    			sourceFList[m] = "";
			    		}
			    	}
				}
				List<String> delList = new ArrayList<String>();
				for (String delStr : sourceFList) {
					if (null != delStr && !"".equals(delStr.trim())) {
						boolean exsitsFlag = false;
						for(String checkSourceField:checkSourceFList){
							if(null!=checkSourceField&&!"".equals(checkSourceField)){
								if(delStr.trim().equals(checkSourceField.trim())){
									exsitsFlag = true;
								}
							}
						}
						if(!exsitsFlag){
							delList.add(delStr);
						}
					}
				}
				if (delList.size() > 0) {
					String[] deleteTList = new String[delList.size()];
					for (int i = 0; i < delList.size(); i++) {
						deleteTList[i] = delList.get(i);
					}
					// 需要删除的字段
					selectValuesMeta.setDeleteName(deleteTList);
				}
			}else{
				String[] deleteTList = new String[0];
				selectValuesMeta.setDeleteName(deleteTList);
			}
			
			// { "-", "Number", "String", "Date", "Boolean", "Integer",
			// "BigNumber", "Serializable", "Binary", }
			Map<String,String> typeNums = getTargetTablesNum(dataPath,taskObj.getTargetName());
			SelectMetadataChange[] selChanges = new SelectMetadataChange[checkSourceFList.length];
			for (int j = 0; j < checkSourceFList.length; j++) {
				String tempField = checkSourceFList[j].trim();
				String tempTargetField = checkTargetFList[j].trim();
				String tempNum = typeNums.get(tempTargetField).trim();
				if(null==tempNum||"".equals(tempNum)){
					tempNum = "2";
				}
				//System.out.println(tempNum);
				SelectMetadataChange tempChange = new SelectMetadataChange(
						selectValuesMeta);
				tempChange.setName(tempField);
				tempChange.setRename(tempTargetField);
				tempChange.setType(Integer.parseInt(tempNum));// 需要取得类型列表
				tempChange.setEncoding("UTF-8");
				// change2.setDateFormatLenient(true);
				selChanges[j] = tempChange;
			}
			selectValuesMeta.setMeta(selChanges);
			StepMeta selectValuesStep = new StepMeta("字段选择", selectValuesMeta);
			transMeta.addStep(selectValuesStep);

			UserDefinedJavaClassMeta javaMeta = new UserDefinedJavaClassMeta();
			String source = DataImportLogicUtils.seleLogicCode(dataPath,taskObj.getTargetName(),taskObj.getId());
			if(null==source||"".equals(source)){
				source = "public boolean processRow(StepMetaInterface smi, StepDataInterface sdi)"
				        +"throws KettleException {"
				        + "return true;"
						+"}";
			}
			UserDefinedJavaClassDef userDef = new UserDefinedJavaClassDef(ClassType.TRANSFORM_CLASS, "TitanDef", source);
			List<UserDefinedJavaClassDef> defList = new ArrayList<UserDefinedJavaClassDef>();
			defList.add(userDef);
			javaMeta.setParentStepMeta(selectValuesStep);
			javaMeta.replaceDefinitions(defList);
			StepMeta javaStep = new StepMeta("Java脚本",javaMeta);
			transMeta.addStep(javaStep);
			// 步骤
			TransHopMeta tableToSel = new TransHopMeta(tableInputStep,
					selectValuesStep, true);
			TransHopMeta selToJava = new TransHopMeta(selectValuesStep,javaStep,true);
			transMeta.addTransHop(tableToSel);
			transMeta.addTransHop(selToJava);
			transMeta.writeXML(xmlPath);
		} catch (KettleException e) {
			xmlPath = "";
			e.printStackTrace();
		}
		return xmlPath;
	}
	
	/**
	 * 根据任务名称获得导入任务内容(用于数据库新增完成后回显)
	 * @param taskName
	 * @return
	 */
	public DataImportTask getDITaskByName(String taskName){
		DataImportTask dit = dbImpTaskDao.getDITaskByName(taskName);
		Date createTime = dit.getBorntime();
		String createTimeString = sf.format(createTime);
		int status = dit.getRunStatus();
		String statusStr = "";
		if(DataImpAddService.RUNSTATUS_RUNNING.intValue() == status){
			statusStr = "正在执行";
		}else if(DataImpAddService.RUNSTATUS_UNRUN.intValue() == status){
			statusStr = "未执行";
		}else if(DataImpAddService.RUNSTATUS_FINISH.intValue() == status){
			statusStr = "执行完成";
		}else if(DataImpAddService.RUNSTATUS_ERROR.intValue() == status){
			statusStr = "执行时出现错误";
		}
		//存于此处方便页面显示
		dit.setSourceName(createTimeString);
		dit.setCheckTargetTypes(statusStr);
		return dit;
	}
	
	/**
	 * 根据任务ID获得导入任务内容
	 * @return
	 */
	public DataImportTask getDITaskById(Long id){
		DataImportTask dit = dbImpTaskDao.getDITaskById(id);
		return dit;
	}

	public DataImpTaskDao getDbImpTaskDao() {
		return dbImpTaskDao;
	}

	public void setDbImpTaskDao(DataImpTaskDao dbImpTaskDao) {
		this.dbImpTaskDao = dbImpTaskDao;
	}

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	
}
