package com.integrity.system.dataType.bean;

import java.io.Serializable;

// Generated 2015-11-30 18:47:12 by Hibernate Tools 3.4.0.CR1
/**
 * Casehosts generated by hbm2java
 * 数据源类型管理
 */
@SuppressWarnings("serial")
public class DataType implements Serializable{

	private Long id;
	private String ObjId;//父对象
	private String ProName;//属性名称
	private String ProType;//属性类型
	private String ProNum;//属性号
	private String isIndex;//索引字段
	private String remarkes1;
	private String remarkes2;
	private Integer remarkes3;

	public DataType() {
	}

	public DataType(Long id,String ObjId, String ProName,
			String ProType, String ProNum,String isIndex,String remarkes1, String remarkes2,
			Integer remarkes3) {
		this.id = id;
		this.ObjId = ObjId;
		this.ProName = ProName;
		this.ProType = ProType;
		this.ProNum = ProNum;
		this.isIndex = isIndex;
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
	public String getProName() {
		return ProName;
	}
	public void setProName(String proName) {
		ProName = proName;
	}
	public String getProType() {
		return ProType;
	}

	public void setProType(String proType) {
		ProType = proType;
	}

	public String getProNum() {
		return ProNum;
	}

	public void setProNum(String proNum) {
		ProNum = proNum;
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
	public String getIsIndex() {
		return isIndex;
	}
	public void setIsIndex(String isIndex) {
		this.isIndex = isIndex;
	}
	public String getObjId() {
		return ObjId;
	}
	public void setObjId(String objId) {
		ObjId = objId;
	}
	

	
}
