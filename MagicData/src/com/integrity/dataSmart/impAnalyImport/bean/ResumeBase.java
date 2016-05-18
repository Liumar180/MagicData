package com.integrity.dataSmart.impAnalyImport.bean;

/**
 * @author liuBF
 * 简历相关人物关联信息
 *
 */
public class ResumeBase {
	private String contactID;
	private String name;
	private String genderTag;
	private String creationDate;
	private String login;
	private String passwordHash;
	private String address;
	private String county;
	public String getContactID() {
		return contactID;
	}
	public void setContactID(String contactID) {
		this.contactID = contactID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGenderTag() {
		return genderTag;
	}
	public void setGenderTag(String genderTag) {
		this.genderTag = genderTag;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	

}
