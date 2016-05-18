package com.integrity.system.auth.bean;

import java.util.Date;

public class AuthUser {
	
	private Long id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 状态：0 启用，1 禁用
	 */
	private Integer status;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	public Long getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public Integer getStatus() {
		return status;
	}
	public String getDescription() {
		return description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}
