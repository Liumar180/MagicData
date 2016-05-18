package com.integrity.lawCase.caseManage.bean;

import java.util.Date;

public class CaseObject {
	
	private Long id;
	
	/**
	 * 案件名称
	 */
	private String caseName;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 案件级别
	 */
	private String caseLevel;
	/**
	 * 案件级别name
	 */
	private String caseLevelName;
	
	/**
	 * 案件负责人
	 */
	private String caseLeader;
	
	/**
	 * 案件督办人
	 */
	private String caseSupervisor;
	
	/**
	 * 案件人员
	 */
	private String caseUserNames;
	
	/**
	 * 案件人员ids
	 */
	private String caseUserIds;
	
	/**
	 * 案件状态
	 */
	private String caseStatus;
	/**
	 * 案件状态name
	 */
	private String caseStatusName;
	
	/**
	 * 方向代码
	 */
	private String directionCode;
	
	/**
	 * 所属方向name
	 */
	private String directionName;
	/**
	 * 案件目标
	 */
	private String caseAim;
	/**
	 * 备注
	 */
	private String memo;
	
	//备用字段
	private String by1;
	private String by2;
	private Integer by3;
	private String dateStr;
	
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public Long getId() {
		return id;
	}
	public String getCaseName() {
		return caseName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getCaseLevel() {
		return caseLevel;
	}
	public String getCaseLevelName() {
		return caseLevelName;
	}
	public String getCaseLeader() {
		return caseLeader;
	}
	public String getCaseSupervisor() {
		return caseSupervisor;
	}
	public String getCaseUserNames() {
		return caseUserNames;
	}
	public String getCaseUserIds() {
		return caseUserIds;
	}
	public String getCaseStatus() {
		return caseStatus;
	}
	public String getCaseStatusName() {
		return caseStatusName;
	}
	public String getDirectionCode() {
		return directionCode;
	}
	public String getDirectionName() {
		return directionName;
	}
	public String getCaseAim() {
		return caseAim;
	}
	public String getMemo() {
		return memo;
	}
	public String getBy1() {
		return by1;
	}
	public String getBy2() {
		return by2;
	}
	public Integer getBy3() {
		return by3;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCaseLevel(String caseLevel) {
		this.caseLevel = caseLevel;
	}
	public void setCaseLevelName(String caseLevelName) {
		this.caseLevelName = caseLevelName;
	}
	public void setCaseLeader(String caseLeader) {
		this.caseLeader = caseLeader;
	}
	public void setCaseSupervisor(String caseSupervisor) {
		this.caseSupervisor = caseSupervisor;
	}
	public void setCaseUserNames(String caseUserNames) {
		this.caseUserNames = caseUserNames;
	}
	public void setCaseUserIds(String caseUserIds) {
		this.caseUserIds = caseUserIds;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	public void setCaseStatusName(String caseStatusName) {
		this.caseStatusName = caseStatusName;
	}
	public void setDirectionCode(String directionCode) {
		this.directionCode = directionCode;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
	public void setCaseAim(String caseAim) {
		this.caseAim = caseAim;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setBy1(String by1) {
		this.by1 = by1;
	}
	public void setBy2(String by2) {
		this.by2 = by2;
	}
	public void setBy3(Integer by3) {
		this.by3 = by3;
	}
	
}
