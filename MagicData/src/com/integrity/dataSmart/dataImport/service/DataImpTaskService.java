package com.integrity.dataSmart.dataImport.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobHopMeta;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.trans.JobEntryTrans;
import org.pentaho.di.job.entry.JobEntryCopy;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.TransMeta.TransformationType;

import sun.security.util.BigInt;

import com.integrity.dataSmart.dataImport.bean.DataImportTask;
import com.integrity.dataSmart.dataImport.bean.TDatabase;
import com.integrity.dataSmart.dataImport.dao.DBDao;
import com.integrity.dataSmart.dataImport.dao.DataImpTaskDao;
import com.integrity.dataSmart.dataImport.pojo.DataImportTaskDetail;
import com.integrity.dataSmart.impAnalyImport.main.insert;
import com.integrity.dataSmart.util.DateFormat;
import com.integrity.login.util.PageInfo;

/**
 * 数据导入任务查询、分页、删除、查看信息、执行等Service
 * 
 * @author HanXue
 * 
 */
public class DataImpTaskService {

	
	
	private Logger logger = Logger.getLogger(DataImpTaskService.class);
	private DataImpTaskDao dbImpTaskDao;
	private DataImpAddService dataImpAddService;
	private DBDao dBDao;

	public void setdBDao(DBDao dBDao) {
		this.dBDao = dBDao;
	}

	/**
	 * eml文件解析时储临时附件的文件夹名称
	 */
	public final static String EML_IMPORT_ACCESSOR_DIRNAME = "MagicDataEmlAcc";
	
	/**
	 * eml文件解析时日志文件的文件夹名称
	 */
	public final static String EML_IMPORT_LOG_DIRNAME = "MagicDataEmlLog";
	
	/**
	 * 正在执行的任务属性配置文件路径
	 */
	public final static String RUNNING_TASK_COFIG_PATH = "WEB-INF"
			+ File.separator + "config" + File.separator + "dataImport"
			+ File.separator + "processBar.properties";

	/**
	 * 正在执行的任务属性配置文件-KEY数据总条数
	 */
	public final static String RUNNING_TASK_TOTAL = "totality";
	
	/**
	 * 正在执行的任务属性配置文件路径-KEY已经导入完成的数据条数
	 */
	public final static String RUNNING_TASK_IMPORT_COUNT = "importCount";
	
	/**
	 * 正在执行的任务属性配置文件路径-KEY导入完成
	 */
	public final static String RUNNING_TASK_FINISHED_FLAG = "isFinish";
	
	/**
	 * 正在执行的任务属性配置文件路径-导入完成状态值
	 */
	public final static String RUNNING_TASK_FINISHED_SUCCESS = "1";
	
	/**
	 * 正在执行的任务属性配置文件路径-导入出现错误状态值
	 */
	public final static String RUNNING_TASK_FINISHED_ERROR = "-1";
	
	//日期格式化串
	public static final String DATEFORMATSTR = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 分页查询导入任务
	 * 
	 * @param page
	 *            分页参数
	 * @return
	 */
	public List<DataImportTask> findImportTasks(PageInfo page) {
		List<DataImportTask> list = dbImpTaskDao.findImportTasks(page);
		for (DataImportTask dataImportTask : list) {
			dataImportTask.setDateStr(DateFormat.transferDate2String(dataImportTask.getBorntime(), DATEFORMATSTR));
		}
		return list;
	}

	/**
	 * 获得数据总数
	 * @param dt
	 * @return
	 */
	private String getDataCount(DataImportTask dt) {
		long connId = dt.getDataConnId();
		int sourceType = dt.getSourceType();
		String dataCount = "-1";
		String sqlStr = "";
		if (sourceType == 0) {
			sqlStr = "select count(*) from " + dt.getSourceName().trim();
		} else {
			String tempSql = dt.getSqlOrder().trim();
			/*String copySql = tempSql.toLowerCase();
			int tempIndex = copySql.indexOf(" from ");
			if(tempIndex>-1){
				sqlStr = "select count(*) " + tempSql.substring(tempIndex);
			}*/
			sqlStr = "select count(1) from ("+tempSql+")  t_"+System.currentTimeMillis();
		}
		if (null != sqlStr && !"".equals(sqlStr)) {
			RowMetaAndData rmAndData;
			try {
				rmAndData = dataImpAddService.getOneRowBySQL(connId,
						sqlStr);
				if (null != rmAndData) {
					Object[] objs = rmAndData.getData();
					if (null != objs && objs.length > 0) {
						dataCount = objs[0] == null ? "-1" : (objs[0].toString().trim());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataCount;
	}

	/**
	 * 写入任务执行属性文件
	 * 
	 * @return
	 */
	public void writeDataPorperties(String key, String value, String dataPath) {
		Properties runningTaskProperties = new Properties();
		key = key.trim();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(dataPath + File.separator
					+ RUNNING_TASK_COFIG_PATH);
			runningTaskProperties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String comments = "#\u8fdb\u5ea6\u6761\u76f8\u5173\u5c5e\u6027"
				+ "\n"
				+ "#totality \u603b\u6761\u6570"
				+ "\n"
				+ "#importCount \totality u5df2\u7ecf\u5bfc\u5165\u6761\u6570"
				+ "\n"
				+ "#isFinish \u662f\u5426\u5bfc\u5165\u5b8c\u6210   0 \u8868\u793a\u672a\u5b8c\u6210;  1 \u8868\u793a\u5df2\u5b8c\u6210; -1 \u8868\u793A\u6267\u884C\u65F6\u51FA\u73B0\u9519\u8BEF";
		if (null != runningTaskProperties && runningTaskProperties.size() > 0) {
			String totalStr = runningTaskProperties
					.getProperty(RUNNING_TASK_TOTAL);
			String importCount = runningTaskProperties
					.getProperty(RUNNING_TASK_IMPORT_COUNT);
			String finishFlag = runningTaskProperties
					.getProperty(RUNNING_TASK_FINISHED_FLAG);
			
			if(key.equals(RUNNING_TASK_TOTAL)){
				totalStr = value;
			}else if(key.equals(RUNNING_TASK_IMPORT_COUNT)){
				importCount = value;
			}else if(key.equals(RUNNING_TASK_FINISHED_FLAG)){
				finishFlag = value;
			}else{
				totalStr = value;
			}
			runningTaskProperties.clear();
			runningTaskProperties.put(RUNNING_TASK_TOTAL, totalStr);
			runningTaskProperties.put(RUNNING_TASK_IMPORT_COUNT, importCount);
			runningTaskProperties.put(RUNNING_TASK_FINISHED_FLAG, finishFlag);
		} else {
			runningTaskProperties.put(RUNNING_TASK_TOTAL, "");
			runningTaskProperties.put(RUNNING_TASK_IMPORT_COUNT, "");
			runningTaskProperties.put(RUNNING_TASK_FINISHED_FLAG, "0");
		}
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(dataPath + File.separator
					+ RUNNING_TASK_COFIG_PATH);
			runningTaskProperties.store(outputStream, comments);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行eml导入任务
	 * 
	 * @param taskId
	 *            任务Id
	 */
	public boolean startEmlJob(DataImportTask dt,String dataPath) {
		String localPath = dataPath + EML_IMPORT_ACCESSOR_DIRNAME + File.separator;
		String logFilePath = dataPath + EML_IMPORT_LOG_DIRNAME + File.separator;
		File localDir = new File(localPath);
		if(!localDir.exists()){
			localDir.mkdirs();
		}
		File logDir = new File(logFilePath);
		if(!logDir.exists()){
			logDir.mkdirs();
		}
		if (dt != null) {
			String tempPath = dt.getUploadFile();
			String sysPoint ="\\"+ File.separator;
			String uploadFilePath = tempPath.replaceAll("\\|", sysPoint);
			if (null == uploadFilePath || "".equals(uploadFilePath)) {
				logger.error("eml上传文件路径不存在");
				return false;
			}else{
				File uploadFile = new File(uploadFilePath);
				if(!uploadFile.exists()){
					logger.error("eml上传文件路径不存在");
					return false;
				}
				String tempFileName = uploadFile.getName();
				String fullLogFilePath = logFilePath + tempFileName + System.currentTimeMillis()/1000+ ".log";//日志文件路径
				File logFile = new File(fullLogFilePath);
				if(!logFile.exists()){
					try {
						logFile.createNewFile();
					} catch (IOException e) {
						dt.setRunStatus(DataImpAddService.RUNSTATUS_ERROR);
						updateTask(dt);
						logger.error("创建导入eml时使用日志文件失败...");
						e.printStackTrace();
					}
				}
				//1.扫描目录文件夹,获得上传文件列表
				List<File> uploadFileList = new ArrayList<File>();
				if(!uploadFile.isDirectory()){//如果是单个文件
					String uploadFileName = uploadFile.getName().toUpperCase();
					if(uploadFileName.endsWith("EML")){
						uploadFileList.add(uploadFile);
					}
				}else{
					getUploadFileList(uploadFile,uploadFileList);
				}
				Integer emlCount = uploadFileList.size();
				if(0==emlCount){
					logger.error("没有找到.eml文件");
					return false;
				}else{
					//更新总数
					dt.setTotality(new BigInteger(emlCount+""));
					dt.setRunStatus(DataImpAddService.RUNSTATUS_RUNNING);
					updateTask(dt);
					try{
						DImpTaskEmlRunThread dThread = new DImpTaskEmlRunThread(uploadFilePath,localPath,fullLogFilePath,dt,dbImpTaskDao);
						Thread t = new Thread(dThread);
						t.setName("Thread-emlImport"+dt.getId()+"-"+dt.getTaskName());
						t.start();
						return true;
					}catch(Exception e){
						dt.setRunStatus(DataImpAddService.RUNSTATUS_ERROR);
						updateTask(dt);
						logger.error("导入eml文件时错误");
						e.printStackTrace();
						return false;
					}
				}
			}
		} else {
			logger.error("该导入任务不存在");
			return false;
		}
	}
	
	/**
	 * 获得上传的文件列表
	 */
    private void getUploadFileList(File uploadFileDir,List<File> uploadFileList){
		File[] files = uploadFileDir.listFiles();
		for(File file:files){     
			if(file.isDirectory()){
				getUploadFileList(file,uploadFileList);
		    }else{
				String fileName = file.getName().toUpperCase();
				if(fileName.endsWith("EML")){
					uploadFileList.add(file);
				}
		    }
		}
    }
	/**
	 * 执行数据导入任务
	 * 
	 * @param taskId
	 *            任务Id
	 */
	public boolean startJob(String taskId,String dataPath) {
		DataImportTask dt = null;
		long st;
		long et;
		Long id = 1L;
		if (null == taskId || "".equals(taskId)) {
			logger.error("taskId格式错误");
			return false;
		} else if (!taskId.matches("[0-9]+")) {
			logger.error("taskId格式错误");
			return false;
		} else {
			id = Long.parseLong(taskId);
			dt = dbImpTaskDao.getDITaskById(id);
		}
		if (dt != null) {
			String filePath = dt.getXmlPath();
			if (null == filePath || "".equals(filePath)) {
				logger.error("xml文件路径记录不存在");
				return false;
			}
			//获得数据总条数
			String dataCountStr = getDataCount(dt);
			long totality = Long.parseLong(dataCountStr);
			dt.setTotality(new BigInteger(totality+""));
			updateTask(dt);
			//写入任务执行文件
//			writeDataPorperties(RUNNING_TASK_TOTAL,dataCountStr,dataPath);
			try {
				st = System.currentTimeMillis();
				String sta= DateFormat.transferLongToDate(st);
				System.out.println("任务执行开始-开始时间："+sta);
				KettleEnvironment.init();
				/*
				 * TransMeta testTransMeta = new TransMeta(filePath);
				 * testTransMeta
				 * .setTransformationType(TransformationType.SingleThreaded
				 * );//设置单线程运行 Trans trans = new Trans(testTransMeta);
				 * trans.prepareExecution(null); trans.startThreads();
				 * logger.info("开始执行任务:"+filePath); trans.waitUntilFinished();
				 */
				try {
					JobMeta jm = new JobMeta();
					JobEntryCopy startCopy = JobMeta.createStartEntry();
					startCopy.setDrawn();
					jm.addJobEntry(startCopy);

					TransMeta testTransMeta = new TransMeta(filePath);
					testTransMeta.setName("test");
					testTransMeta
							.setTransformationType(TransformationType.SingleThreaded);

					JobEntryTrans jt = new JobEntryTrans();
					jt.setTransname("test");
					jt.setFileName(filePath);
					JobEntryCopy transCopy = new JobEntryCopy(jt);
					transCopy.setName("Excute");
					transCopy.setDrawn();
					jm.addJobEntry(transCopy);

					JobHopMeta transHop = new JobHopMeta(startCopy, transCopy);
					jm.addJobHop(transHop);

					Job job = new Job(null, jm);
					//更新任务至正在执行状态
					dt.setRunStatus(DataImpAddService.RUNSTATUS_RUNNING);
					updateTask(dt);
					job.start();
					job.waitUntilFinished();
					Result rs = job.getResult();
					//更新任务至执行完成状态
					dt = dbImpTaskDao.getDITaskById(id);
					dt.setRunStatus(DataImpAddService.RUNSTATUS_FINISH);
					updateTask(dt);
					//加入任务完成标识
//					writeDataPorperties(RUNNING_TASK_FINISHED_FLAG,RUNNING_TASK_FINISHED_SUCCESS,dataPath);
					System.out.println(rs.getNrLinesRead() + "    "
							+ rs.getNrLinesWritten() + "    "
							+ rs.getNrErrors());
				} catch (Exception e) {
//					writeDataPorperties(RUNNING_TASK_FINISHED_FLAG,RUNNING_TASK_FINISHED_ERROR,dataPath);
					//更新任务至执行失败状态
					dt = dbImpTaskDao.getDITaskById(id);
					dt.setRunStatus(DataImpAddService.RUNSTATUS_ERROR);
					updateTask(dt);
					logger.error("执行" + filePath + "任务异常：", e);
					return false;
				}
				et = System.currentTimeMillis();
				String end= DateFormat.transferLongToDate(et);
				System.out.println("任务执行完毕-结束时间："+end);
				System.out.println("总用时："+(et - st)/1000+"秒");
				return true;
			} catch (KettleException e) {
				logger.error("执行" + filePath + "任务异常：", e);
//				writeDataPorperties(RUNNING_TASK_FINISHED_FLAG,RUNNING_TASK_FINISHED_ERROR,dataPath);
				//更新任务至执行失败状态
				dt = dbImpTaskDao.getDITaskById(id);
				dt.setRunStatus(DataImpAddService.RUNSTATUS_ERROR);
				updateTask(dt);
				return false;
			}
			
		} else {
			logger.error("该导入任务不存在");
			return false;
		}
		
	}
	/**
	 * @param diTask 保存任务记录
	 */
	public Integer saveDataImpTask(DataImportTask diTask){
		Integer taskId = -1;
		try {
			taskId = dbImpTaskDao.saveDataImpTask(diTask);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return taskId;
	}
	/**
	 * @param id
	 * @return
	 * 更新回显数据
	 */
	public DataImportTask toUpdateTaskById(Integer id){
		return dbImpTaskDao.findTaskById(id);
	}
	/**
	 * 更新任务
	 * @param dt
	 */
	public boolean updateTask(DataImportTask dt){
		try {
			dbImpTaskDao.updateDataImpTask(dt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据任务名称获得导入任务内容
	 * 
	 * @param taskName
	 * @return
	 */
	public DataImportTask getDITaskByName(String taskName) {
		return dbImpTaskDao.getDITaskByName(taskName);
	}

	public DataImpTaskDao getDbImpTaskDao() {
		return dbImpTaskDao;
	}

	public void setDbImpTaskDao(DataImpTaskDao dbImpTaskDao) {
		this.dbImpTaskDao = dbImpTaskDao;
	}

	public void setDataImpAddService(DataImpAddService dataImpAddService) {
		this.dataImpAddService = dataImpAddService;
	}
	 /**
     * 通过任务Id获得数据导入任务对象
     * @param taskId
     * @return
     */
    public List<DataImportTask> getDITaskByDataConnId(Long dataConnId) {
    	return dbImpTaskDao.getDITaskByDataConnId( dataConnId);
	}
	/**
	 * 删除单个导入任务
	 * @param entity
	 */
	public void deleteDTask(DataImportTask entity) {
		if(null!=entity){
			File f = new File(entity.getXmlPath());
			dbImpTaskDao.deleteDTask(entity);
			if(f.exists()){
				f.delete();
			}
		}
	}
	
	/**
	 * 删除多个导入任务
	 * @param entity
	 */
	public void deleteDTaskList(List<DataImportTask> entityList) {
		List<File> fileList = new ArrayList<File>();
		for (DataImportTask dt : entityList) {
			if(null!=dt&&null!=dt.getTaskType()&&dt.getTaskType()==1){
				File tempFile = new File(dt.getXmlPath());
				fileList.add(tempFile);
			}
		}
		dbImpTaskDao.deleteDTaskList(entityList);
		for(File f:fileList){
			if(f.exists()){
				f.delete();
			}
		}
	}
	
	/**
	 * 根据任务ID删除单个导入任务
	 * @param entity
	 */
	public void deleteDTaskById(String id) {
		DataImportTask dt = dbImpTaskDao.getDITaskById(Long.parseLong(id));
		dbImpTaskDao.deleteDTaskById( id);
		if(null!=dt&&null!=dt.getTaskType()&&dt.getTaskType()==1){
			File f = new File(dt.getXmlPath());
			if(f.exists()){
				f.delete();
			}
		}
	}

	/**
	 * 判断是否存在正在执行的任务
	 */
	public Boolean judgeExecute() {
		int count=dbImpTaskDao.getExecuteTask();
		if (count > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * 通过id获取任务
	 */
	public DataImportTask getDITaskById(Long id) {
		return dbImpTaskDao.getDITaskById(id);
	}

	/**
	 * 任务详细信息
	 * @param taskId
	 * @return
	 */
	public DataImportTaskDetail findTaskDetail(String taskId) {
		DataImportTask task = dbImpTaskDao.getDITaskById(Long.parseLong(taskId));
		long conId = task.getDataConnId();
		TDatabase db = dBDao.findDB(conId);
		DataImportTaskDetail detail = new DataImportTaskDetail();
		if (task != null && db != null) {
			detail.setTaskId(task.getId());
			detail.setCheckSourceFields(task.getCheckSourceFields());
			detail.setCheckTargetFields(task.getCheckTargetFields());
			detail.setDataBaseName(db.getConnectionDBUserName());
			detail.setDbConnId(conId);
			detail.setDbConnName(db.getConnectionName());
			detail.setDbType(db.getConnectionDB());
			detail.setSourceTable(task.getSourceName());
			detail.setSqlFlag(task.getSourceType()==1?true:false);
			detail.setSqlText(task.getSqlOrder());
			detail.setTargetTable(task.getTargetName());
			detail.setTaskName(task.getTaskName());
			detail.setHostName(db.getConnectionServerName());
			detail.setBorntime(task.getBorntime());
			String runStatus = "";
			Integer status = task.getRunStatus();
			switch (status) {
			case 0:
				runStatus = "未执行";
				break;
			case 1:
				runStatus = "正在执行";
				break;
			case 2:
				runStatus = "执行完成";
				break;
			case -1:
				runStatus = "执行时出现错误";
				break;
			default:
				break;
			}
			detail.setRunStatus(runStatus);
			detail.setTotality(task.getTotality());
			detail.setImportCount(task.getImportCount());
		}
		return detail;
	}
}
