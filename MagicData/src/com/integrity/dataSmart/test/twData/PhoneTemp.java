package com.integrity.dataSmart.test.twData;

public class PhoneTemp {
	 private String model;//运营商
	 private String phonenum;//电话号码（from）
	 private String to;//被叫电话号码
	 private String name;//主叫姓名
	 private String idcard;//主叫身份证号
	 private String country;//主叫国籍
	 private String address;//主叫地址
	 private String tname;//被叫姓名
	 private String tidcard;//被叫身份证号
	 private String tcountry;//被叫国籍
	 private Long time;//打电话时间(eventtime)
	 private Long timelong;//时长
	 
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getTidcard() {
		return tidcard;
	}
	public void setTidcard(String tidcard) {
		this.tidcard = tidcard;
	}
	public String getTcountry() {
		return tcountry;
	}
	public void setTcountry(String tcountry) {
		this.tcountry = tcountry;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Long getTimelong() {
		return timelong;
	}
	public void setTimelong(Long timelong) {
		this.timelong = timelong;
	}
	 

}
