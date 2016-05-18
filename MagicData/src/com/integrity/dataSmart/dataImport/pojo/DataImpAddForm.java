/**
 * 
 */
package com.integrity.dataSmart.dataImport.pojo;

/**
 * @author HanXue
 *
 */
public class DataImpAddForm {

	/**任务名称*/
	private String taskName;
	/** 数据导入任务类型*/
	private Integer taskType;
	/**上传的文件完整路径*/
	private String uploadFile;
	/**数据库连接ID*/
	private String dbConnId;
	/**数据库连接名称*/
	private String dbConnName;
	/**源表*/
	private String sourceTable;
	/**目标表*/
	private String targetTable;
	/**sql语句标识*/
	private String sqlFlag;
	/**sql语句内容*/
	private String sqlText;
	/**sql语句目标表*/
	private String sqlTargetTable;
	/**选择的源字段名称*/
	private String checkSourceFields;
	/**选择的目标字段名称*/
	private String checkTargetFields;
	
	
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
	public String getDbConnId() {
		return dbConnId;
	}
	public void setDbConnId(String dbConnId) {
		this.dbConnId = dbConnId;
	}
	public String getDbConnName() {
		return dbConnName;
	}
	public void setDbConnName(String dbConnName) {
		this.dbConnName = dbConnName;
	}
	public String getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}
	public String getTargetTable() {
		return targetTable;
	}
	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}
	public String getSqlFlag() {
		return sqlFlag;
	}
	public void setSqlFlag(String sqlFlag) {
		this.sqlFlag = sqlFlag;
	}
	public String getSqlText() {
		return sqlText;
	}
	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}
	public String getSqlTargetTable() {
		return sqlTargetTable;
	}
	public void setSqlTargetTable(String sqlTargetTable) {
		this.sqlTargetTable = sqlTargetTable;
	}
	public String getCheckSourceFields() {
		return checkSourceFields;
	}
	public void setCheckSourceFields(String checkSourceFields) {
		this.checkSourceFields = checkSourceFields;
	}
	public String getCheckTargetFields() {
		return checkTargetFields;
	}
	public void setCheckTargetFields(String checkTargetFields) {
		this.checkTargetFields = checkTargetFields;
	}
}
