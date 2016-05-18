package com.integrity.system.dataType.bean;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class DataObject implements Serializable{
	private Long id;
	private String name;
	private String details;
	private String property;
	private Date createTime;//创建时间
	private String remarkes1;
	private String remarkes2;
	private Integer remarkes3;

	public DataObject() {
	}

	public DataObject(Long id,String name, String details,String property,Date createTime,String remarkes1, String remarkes2,Integer remarkes3) {
		this.id = id;
		this.name = name;
		this.details = details;
		this.property = property;
		this.createTime = createTime;
		this.remarkes1 = remarkes1;
		this.remarkes2 = remarkes2;
		this.remarkes3 = remarkes3;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemarkes1() {
		return remarkes1;
	}
	public void setRemarkes1(String remarkes1) {
		this.remarkes1 = remarkes1;
	}
	public String getRemarkes2() {
		return remarkes2;
	}
	public void setRemarkes2(String remarkes2) {
		this.remarkes2 = remarkes2;
	}
	public Integer getRemarkes3() {
		return remarkes3;
	}
	public void setRemarkes3(Integer remarkes3) {
		this.remarkes3 = remarkes3;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}


}
