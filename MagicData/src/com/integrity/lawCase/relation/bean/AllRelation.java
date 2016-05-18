package com.integrity.lawCase.relation.bean;

public class AllRelation {

	private Long id;
	
	/**
	 * 关联类型  从ConstantManage中取值
	 */
	private String type;

	/**
	 * 关联的主id
	 */
	private Long typeId;
	
	/**
	 * 案件ids
	 */
	private String caseIds;
	
	/**
	 * 文件ids
	 */
	private String fileIds;
	
	/**
	 * 主机ids
	 */
	private String hostIds;
	
	/**
	 * 组织ids
	 */
	private String organizationIds;
	
	/**
	 * 人员ids
	 */
	private String peopleIds;
	
	//备用字段
	private String backup1;
	private String backup2;
	private Integer backup3;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getCaseIds() {
		return caseIds;
	}
	public void setCaseIds(String caseIds) {
		this.caseIds = caseIds;
	}
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	public String getHostIds() {
		return hostIds;
	}
	public void setHostIds(String hostIds) {
		this.hostIds = hostIds;
	}
	public String getOrganizationIds() {
		return organizationIds;
	}
	public void setOrganizationIds(String organizationIds) {
		this.organizationIds = organizationIds;
	}
	public String getPeopleIds() {
		return peopleIds;
	}
	public void setPeopleIds(String peopleIds) {
		this.peopleIds = peopleIds;
	}
	public String getBackup1() {
		return backup1;
	}
	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}
	public String getBackup2() {
		return backup2;
	}
	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}
	public Integer getBackup3() {
		return backup3;
	}
	public void setBackup3(Integer backup3) {
		this.backup3 = backup3;
	}
	
	
}
