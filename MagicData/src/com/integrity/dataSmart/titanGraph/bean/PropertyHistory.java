package com.integrity.dataSmart.titanGraph.bean;

import java.util.Date;

public class PropertyHistory {
	private Long id;
	
	/**
	 * 节点id
	 */
	private Long vertexId;
	
	/**
	 * 节点类型
	 */
	private String type;
	
	/**
	 * 属性名
	 */
	private String property;
	
	/**
	 * 属性值
	 */
	private String value;

	/**
	 * 修改时间
	 */
	private Date updateTime;
	
	/**
	 * 用户
	 */
	private String userName;

	public Long getId() {
		return id;
	}

	public Long getVertexId() {
		return vertexId;
	}

	public String getType() {
		return type;
	}

	public String getProperty() {
		return property;
	}

	public String getValue() {
		return value;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVertexId(Long vertexId) {
		this.vertexId = vertexId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
