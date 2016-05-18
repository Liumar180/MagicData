package com.integrity.system.auth.bean;

import java.util.Date;

public class Role {
	
	private long id;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private Date createTime;
	public long getId() {
		return id;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getDescription() {
		return description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
