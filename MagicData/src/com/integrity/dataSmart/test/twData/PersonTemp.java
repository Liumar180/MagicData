package com.integrity.dataSmart.test.twData;

public class PersonTemp {
	private String name;//名字
	private String idcard;//身份证
	private String country;//国家
	private String address;//地址
	/**
	 * 关系类型存 "2"
	 */
	private String masterIdcard;//户主身份证
	private String masterName;//户主姓名
	/**
	 * 关系类型存 "3"
	 */
	private String fatherName;//父亲名
	/**
	 * 关系类型存 "4"
	 */
	private String motherName;//母亲名
	/**
	 * 关系类型存 "5"
	 */
	private String spouseName;//配偶名
	public String getName() {
		return name;
	}
	public String getIdcard() {
		return idcard;
	}
	public String getCountry() {
		return country;
	}
	public String getAddress() {
		return address;
	}
	public String getMasterIdcard() {
		return masterIdcard;
	}
	public String getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	public String getMasterName() {
		return masterName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setMasterIdcard(String masterIdcard) {
		this.masterIdcard = masterIdcard;
	}
	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	
	

}
