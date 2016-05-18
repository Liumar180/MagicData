package com.integrity.dataSmart.dataImport.bean;

import java.io.Serializable;
import java.util.Date;

public class CsvData implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件编码
	 */
	private String fileEncoding;
	/**
	 * 分隔符
	 */
	private String fileSeparator;
	/**
	 * 是否有表头
	 */
	private boolean titleFlag;
	/**
	 * 上传时间
	 */
	private Date uploadTime;
	
	/**
	 * 备用字段1
	 */
	private String by1;
	/**
	 * 备用字段2
	 */
	private String by2;
	/**
	 * 备用字段3
	 */
	private Integer by3;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileEncoding() {
		return fileEncoding;
	}
	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}
	public String getFileSeparator() {
		return fileSeparator;
	}
	public void setFileSeparator(String fileSeparator) {
		this.fileSeparator = fileSeparator;
	}
	public boolean getTitleFlag() {
		return titleFlag;
	}
	public void setTitleFlag(boolean titleFlag) {
		this.titleFlag = titleFlag;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getBy1() {
		return by1;
	}
	public void setBy1(String by1) {
		this.by1 = by1;
	}
	public String getBy2() {
		return by2;
	}
	public void setBy2(String by2) {
		this.by2 = by2;
	}
	public Integer getBy3() {
		return by3;
	}
	public void setBy3(Integer by3) {
		this.by3 = by3;
	}
	
	
}
