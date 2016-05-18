package com.integrity.lawCase.caseManage.pojo;

import java.util.Date;

public class WorkAllocationPojo {
	
	/**
	 * 对象类型  从ConstantManage中取值
	 */
	private String typeName;
	/**
	 * 具体对象主要属性值
	 */
	private String mainValue;
	/**
	 * 方案配置情况
	 */
	private String allocation;
	
	/**
	 * 负责人
	 */
	private String principal;
	
	/**
	 * 创建时间
	 */
	private String dateStr;
	
	/**
	 * 效果反馈
	 */
	private String result;
	
	/**
	 * 备注
	 */
	private String memo;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getMainValue() {
		return mainValue;
	}

	public void setMainValue(String mainValue) {
		this.mainValue = mainValue;
	}

	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
}
