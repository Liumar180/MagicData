package com.integrity.lawCase.common.bean;


/**
 * 字典表
 * 分为两级：一级即方向、等级、民族、国籍等
 * 		   二级为一级对应的字典映射
 * 
 * 字典表使用说明：字典表从 ServletContext 中获取，key 为：ConstantManage.DATADICTIONARY
 * 			  获取结果为Map<String,Map<String,String>> 
 * 			key:一级code（在ConstantManage中，也可用ConstantManage.STAIR_DICTIONARY获取字典映射）
 * 			value：一级对应的字典映射（key为code,value为name）								
 *
 */
public class DictionaryTable {
	private Long id;
	
	/**
	 * 字典key
	 */
	private String code;
	
	/**
	 * 字典name
	 */
	private String name;
	
	/**
	 * 上层id
	 */
	private Long pid;
	
	/**
	 * 备注
	 */
	private String memo;

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Long getPid() {
		return pid;
	}

	public String getMemo() {
		return memo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
}
