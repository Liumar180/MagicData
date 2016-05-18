package com.integrity.dataSmart.titanGraph.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.titanGraph.bean.PropertyHistory;
import com.integrity.dataSmart.titanGraph.service.PropertyHistoryService;
import com.opensymphony.xwork2.ActionSupport;

public class PropertyHistoryAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(PropertyHistoryAction.class);
	private PropertyHistoryService propertyHistoryService;
	private PageModel<PropertyHistory> pageModel;
	private PropertyHistory propertyHistory;
	
	/**
	 * 历史页面
	 * @return
	 */
	public String viewHistoryPage(){
		try {
			ServletActionContext.getRequest().setAttribute("propertyHistory", propertyHistory);
		} catch (Exception e) {
			logger.error("历史页面异常", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取历史列表
	 * @return
	 */
	public String findHistoryList(){
		pageModel = propertyHistoryService.getHisPageModel(pageModel,propertyHistory);
		return SUCCESS;
	}
	
	/**
	 * 修改节点属性
	 */
	public String saveHistoryProperty(){
		try {
			String userName = (String) ServletActionContext.getRequest().getSession().getAttribute("username");
			propertyHistory.setUserName(userName);
			propertyHistoryService.saveHistoryProperty(propertyHistory);
		} catch (Exception e) {
			logger.error("修改节点属性异常", e);
		}
		return SUCCESS;
	}
	
	
	public void setPropertyHistoryService(PropertyHistoryService propertyHistoryService) {
		this.propertyHistoryService = propertyHistoryService;
	}

	public PageModel<PropertyHistory> getPageModel() {
		return pageModel;
	}
	public PropertyHistory getPropertyHistory() {
		return propertyHistory;
	}
	public void setPageModel(PageModel<PropertyHistory> pageModel) {
		this.pageModel = pageModel;
	}
	public void setPropertyHistory(PropertyHistory propertyHistory) {
		this.propertyHistory = propertyHistory;
	}
	
	
	
}
