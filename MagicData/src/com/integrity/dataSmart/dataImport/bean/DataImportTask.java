package com.integrity.dataSmart.dataImport.bean;

import java.math.BigInteger;
import java.util.Date;

/**
 * 数据库导入任务表对应实体对象
 * @author HanXue
 *
 */

@SuppressWarnings("serial")
public class DataImportTask implements java.io.Serializable {

	// Fields

	private Integer id;
	private String createPerson;
	/**
	 * 数据导入任务名称
	 */
	private String taskName;
	
	/**
	 * 数据导入任务类型
	 */
	private Integer taskType;
	
	/**
	 * 上传的文件完整路径
	 */
	private String uploadFile;

	/**
	 * 关联的数据库连接外键ID
	 */
	private Long dataConnId;
	
	/**
	 * 源类型，0表示表名，1表示SQL语句
	 */
	private Integer sourceType;

	/**
	 * 源表名
	 */
	private String sourceName;

	/**
	 * 自定义SQL语句
	 */
	private String sqlOrder;

	/**
	 * 目标数据集名称
	 */
	private String targetName;

	/**
	 * 源数据包含的所有字段名称，逗号分隔
	 */
	private String sourceFields;

	/**
	 * 源数据包含的所有字段的类型名称，逗号分隔
	 */
	private String sourceTypes;

	/**
	 * 源数据中选择导入的字段名称，逗号分隔
	 */
	private String checkSourceFields;

	/**
	 * 源数据中选择导入的字段的类型名称，逗号分隔
	 */
	private String checkSourceTypes;

	/**
	 * 目标数据中选择对应导入的字段名称，逗号分隔，与sourceFields依序列一一对应
	 */
	private String checkTargetFields;
	
	/**
	 * 目标数据中选择对应导入字段的类型名称，逗号分隔
	 */
	private String checkTargetTypes;
	
	/**
	 * 导入任务生成的XML文件保存路径
	 */
	private String xmlPath;
	
	/**
	 * 导入任务创建时间
	 */
	private Date borntime;
	
	/**
	 * 导入任务的执行情况
	 * DataImpAddService.RUNSTATUS_UNRUN = 0表示未执行
	 * DataImpAddService.RUNSTATUS_RUNNING = 1表示正在执行
	 * DataImpAddService.RUNSTATUS_FINISH = 2表示执行完成
	 * DataImpAddService.RUNSTATUS_ERROR = -1表示执行时出现错误
	 */
	private Integer runStatus;
	
	/**
	 * totality 总条数
	 */
	private BigInteger totality;
	
	/**
	 * importCount 已经导入条数
	 */
	private Long importCount;
	
	private String taskDesc;
	
	private String dateStr;
	
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	// Constructors

	/** default constructor */
	public DataImportTask() {
	}

	public DataImportTask(Integer id,String createPerson, String taskName, Integer taskType,
			Date borntime, Integer runStatus,String taskDesc) {
		super();
		this.id = id;
		this.createPerson = createPerson;
		this.taskName = taskName;
		this.taskType = taskType;
		this.borntime = borntime;
		this.runStatus = runStatus;
		this.taskDesc = taskDesc;
	}
	
	public DataImportTask(Integer id, String createPerson,String taskName, Integer taskType,
			String uploadFile, Long dataConnId, Integer sourceType,
			String sourceName, String sqlOrder, String targetName,
			String sourceFields, String sourceTypes, String checkSourceFields,
			String checkSourceTypes, String checkTargetFields,
			String checkTargetTypes, String xmlPath, Date borntime,
			Integer runStatus, BigInteger totality, Long importCount, String dateStr,String taskDesc) {
		super();
		this.id = id;
		this.createPerson = createPerson;
		this.taskName = taskName;
		this.taskType = taskType;
		this.uploadFile = uploadFile;
		this.dataConnId = dataConnId;
		this.sourceType = sourceType;
		this.sourceName = sourceName;
		this.sqlOrder = sqlOrder;
		this.targetName = targetName;
		this.sourceFields = sourceFields;
		this.sourceTypes = sourceTypes;
		this.checkSourceFields = checkSourceFields;
		this.checkSourceTypes = checkSourceTypes;
		this.checkTargetFields = checkTargetFields;
		this.checkTargetTypes = checkTargetTypes;
		this.xmlPath = xmlPath;
		this.borntime = borntime;
		this.runStatus = runStatus;
		this.totality = totality;
		this.importCount = importCount;
		this.dateStr = dateStr;
		this.taskDesc = taskDesc;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public String getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}
	public Long getDataConnId() {
		return dataConnId;
	}
	public void setDataConnId(Long dataConnId) {
		this.dataConnId = dataConnId;
	}
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getSqlOrder() {
		return sqlOrder;
	}
	public void setSqlOrder(String sqlOrder) {
		this.sqlOrder = sqlOrder;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getSourceFields() {
		return sourceFields;
	}
	public void setSourceFields(String sourceFields) {
		this.sourceFields = sourceFields;
	}
	public String getSourceTypes() {
		return sourceTypes;
	}
	public void setSourceTypes(String sourceTypes) {
		this.sourceTypes = sourceTypes;
	}
	public String getCheckSourceFields() {
		return checkSourceFields;
	}
	public void setCheckSourceFields(String checkSourceFields) {
		this.checkSourceFields = checkSourceFields;
	}
	public String getCheckSourceTypes() {
		return checkSourceTypes;
	}
	public void setCheckSourceTypes(String checkSourceTypes) {
		this.checkSourceTypes = checkSourceTypes;
	}
	public String getCheckTargetFields() {
		return checkTargetFields;
	}
	public void setCheckTargetFields(String checkTargetFields) {
		this.checkTargetFields = checkTargetFields;
	}
	public String getCheckTargetTypes() {
		return checkTargetTypes;
	}
	public void setCheckTargetTypes(String checkTargetTypes) {
		this.checkTargetTypes = checkTargetTypes;
	}
	public String getXmlPath() {
		return xmlPath;
	}
	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	public Date getBorntime() {
		return borntime;
	}
	public void setBorntime(Date borntime) {
		this.borntime = borntime;
	}
	public Integer getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}
	public BigInteger getTotality() {
		return totality;
	}
	public void setTotality(BigInteger totality) {
		this.totality = totality;
	}
	public Long getImportCount() {
		return importCount;
	}
	public void setImportCount(Long importCount) {
		this.importCount = importCount;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	
}