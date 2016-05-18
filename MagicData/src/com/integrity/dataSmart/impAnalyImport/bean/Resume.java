package com.integrity.dataSmart.impAnalyImport.bean;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author liuBf
 * 简历信息
 */
public class Resume {
	@Field("vertexID")
	private String vertexID;//节点ID
	@Field("informationID")
	private String informationID;
	@Field("type")
	private String type;
	@Field("keyWords")
	private String keyWords;
	@Field("title")
	private String title;
	@Field("description")
	private String description;
	@Field("areaCode")
	private String areaCode;
	@Field("city")
	private String city;
	@Field("creationDate")
	private String creationDate;
	@Field("crDate")
	private String uTCDate;//创建时间毫秒
	@Field("approvalDate")
	private String approvalDate;
	@Field("countryType")
	private String countryType;
	@Field("Private")
	private String Private;
	@Field("state")
	private String state;
	@Field("zip")
	private String zip;
	@Field("beginDate")
	private String beginDate;
	@Field("modifyDate")
	private String modifyDate;
	@Field("moDate")
	private String uTMDate;//修改时间毫秒
	public String getVertexID() {
		return vertexID;
	}
	public void setVertexID(String vertexID) {
		this.vertexID = vertexID;
	}
	public String getInformationID() {
		return informationID;
	}
	public void setInformationID(String informationID) {
		this.informationID = informationID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getuTCDate() {
		return uTCDate;
	}
	public void setuTCDate(String uTCDate) {
		this.uTCDate = uTCDate;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getCountryType() {
		return countryType;
	}
	public void setCountryType(String countryType) {
		this.countryType = countryType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getuTMDate() {
		return uTMDate;
	}
	public void setuTMDate(String uTMDate) {
		this.uTMDate = uTMDate;
	}
	public String getPrivate() {
		return Private;
	}
	public void setPrivate(String private1) {
		Private = private1;
	}
	
	

}
