package com.integrity.dataSmart.dataImport.bean;

import java.beans.Transient;

public class TDatabase {

	private long id;
	private String connectionName;//连接名称
	private String connectionDB;//数据库类型
	private String connectionServerName;//服务ip
	private String connectionDbName;//数据库名称
	private String connectionPort;//端口号
	private String connectionDBUserName;
	private String connectionDBPassword;
	private String connectionDBPasswordBak;
	private String connectionDriverName;//驱动类
	private String connectionServerUrl;//jdbc URL
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getConnectionName() {
		return connectionName;
	}
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
	public String getConnectionDB() {
		return connectionDB;
	}
	public void setConnectionDB(String connectionDB) {
		this.connectionDB = connectionDB;
	}
	public String getConnectionDbName() {
		return connectionDbName;
	}
	public void setConnectionDbName(String connectionDbName) {
		this.connectionDbName = connectionDbName;
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
	public String getConnectionDBPasswordBak() {
		return connectionDBPasswordBak;
	}
	public void setConnectionDBPasswordBak(String connectionDBPasswordBak) {
		this.connectionDBPasswordBak = connectionDBPasswordBak;
	}
	public String getConnectionDriverName() {
		return connectionDriverName;
	}
	public void setConnectionDriverName(String connectionDriverName) {
		this.connectionDriverName = connectionDriverName;
	}
	public String getConnectionServerName() {
		return connectionServerName;
	}
	public void setConnectionServerName(String connectionServerName) {
		this.connectionServerName = connectionServerName;
	}
	@Transient
	public String getConnectionServerUrl() {
		return connectionServerUrl;
	}
	public void setConnectionServerUrl(String connectionServerUrl) {
		this.connectionServerUrl = connectionServerUrl;
	}
	
	
	
}
