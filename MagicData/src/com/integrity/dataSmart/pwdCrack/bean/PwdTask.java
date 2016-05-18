package com.integrity.dataSmart.pwdCrack.bean;

import java.util.Date;

/**
 * 密码破解任务表对应类
 * @author HanXue
 *
 */
public class PwdTask {

	/**
	 * 任务ID
	 */
	private Integer id;
	
	/**
	 * 节点UUID
	 */
	private String uuid;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 加密的密码
	 */
	private String pwdEncrypt;
	
	/**
	 * 解密后的密码
	 */
	private String pwdCrack;
	
	/**
	 * 任务创建时间
	 */
	private Date createTime;
	
	/**
	 *任务的执行情况
	 */
	private Integer runStatus;
	
	/**
	 * 执行进度(用于正在执行的进度)
	 */
	private Integer runPercent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwdEncrypt() {
		return pwdEncrypt;
	}

	public void setPwdEncrypt(String pwdEncrypt) {
		this.pwdEncrypt = pwdEncrypt;
	}

	public String getPwdCrack() {
		return pwdCrack;
	}

	public void setPwdCrack(String pwdCrack) {
		this.pwdCrack = pwdCrack;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}

	public Integer getRunPercent() {
		return runPercent;
	}

	public void setRunPercent(Integer runPercent) {
		this.runPercent = runPercent;
	}

}
