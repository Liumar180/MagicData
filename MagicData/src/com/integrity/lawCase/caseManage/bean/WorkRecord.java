package com.integrity.lawCase.caseManage.bean;

import java.util.Date;

public class WorkRecord {
	
	private Long id;
	
	/**
	 * 案件id
	 */
	private Long caseId;
	
	/**
	 * 标题
	 */
	private String title;

	
	/**
	 * 内容
	 */
	private String conent;
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	//备用字段
	private String by1;
	private String by2;
	private Integer by3;
	private String dateStr;
	
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public Long getId() {
		return id;
	}
	public Long getCaseId() {
		return caseId;
	}
	public String getTitle() {
		return title;
	}
	public String getConent() {
		return conent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getBy1() {
		return by1;
	}
	public String getBy2() {
		return by2;
	}
	public Integer getBy3() {
		return by3;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setConent(String conent) {
		this.conent = conent;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setBy1(String by1) {
		this.by1 = by1;
	}
	public void setBy2(String by2) {
		this.by2 = by2;
	}
	public void setBy3(Integer by3) {
		this.by3 = by3;
	}
	
	
	
}
