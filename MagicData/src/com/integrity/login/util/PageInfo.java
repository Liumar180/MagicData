package com.integrity.login.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页信息排序字段
 */
public class PageInfo {

	/** 每页记录数 */
	private int pageSize;
	
	/** 当前页号 */
	private int pageNo;
	
	/** 总页数 */
	private int totalPages;
	
	/** 总记录数 */
	private int totalRecords;
	
	/**add by HanXue 排序字段名称*/
	private String sidx;
	
	/**add by HanXue 升序asc或降序desc*/
	private String sord;
	
	/**add by HanXue 数据列表*/
	private List dataList;
	
	public PageInfo() {
		super();
	}
	
	/**
	 * 直接构造分页信息对象
	 * @param pageNo
	 * @param pageSize
	 */
	public PageInfo(int pageNo,int pageSize){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	
	public PageInfo(int pageSize, int pageNo, int totalRecords,
			String sidx, String sord) {
		super();
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.totalRecords = totalRecords;
		this.sidx = sidx;
		this.sord = sord;
	}

	/**
	 * @return Returns the pageNo.
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo The pageNo to set.
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return Returns the totalPages.
	 */
	public int getTotalPages() {
		return totalPages;
	}
	/**
	 * @param totalPages The totalPages to set.
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	/**
	 * @return Returns the totalRecords.
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

}
