package com.integrity.dataSmart.common.page;

import java.util.List;

/**
 * @author Liubf
 *
 * @param <E>
 */
public class PageModel<E> {  
    //结果集  
    private List<E> list;  
    //查询记录数  
    private Integer totalRecords;  
    //每页多少条记录  
    private Integer pageSize;  
    //第几页  
    private Integer pageNo;  
    //总页数
    private Integer totalPage;
    
    private String sidx;
    
    private String sord;
    
    public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	/** 
     * 总页数 
     * @return 
     */  
    public int getTotalPages(){  
        return ((totalRecords+pageSize-1)/pageSize);  
          
    }  
    /** 
     * 取得首页 
     * @return 
     */  
    public int getTopPageNo(){  
        return 1;  
    }  
    /** 
     * 上一页 
     * @return 
     */  
    public int getPreviousPageNo(){  
        if(pageNo<=1){  
            return 1;  
        }  
        return pageNo-1;  
    }  
    /** 
     * 下一页 
     * @return 
     */  
    public int getNextPageNo() {  
        if (pageNo >= getBottomPageNo()) {  
            return getBottomPageNo();  
        }  
        return pageNo + 1;    
    }  
    /** 
     * 取得尾页 
     * @return 
     */  
    public int getBottomPageNo(){  
        return getTotalPages();  
    }  
  
}  
