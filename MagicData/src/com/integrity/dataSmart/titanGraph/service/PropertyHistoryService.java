package com.integrity.dataSmart.titanGraph.service;

import java.util.Date;
import java.util.List;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.titanGraph.bean.PropertyHistory;
import com.integrity.dataSmart.titanGraph.dao.PropertyHistoryDao;

public class PropertyHistoryService {
	private PropertyHistoryDao propertyHistoryDao;
	private SearchDetailService searchDetailService;

	public void setPropertyHistoryDao(PropertyHistoryDao propertyHistoryDao) {
		this.propertyHistoryDao = propertyHistoryDao;
	}

	public void setSearchDetailService(SearchDetailService searchDetailService) {
		this.searchDetailService = searchDetailService;
	}

	/**
	 * 保存历史属性
	 * @param propertyHistory
	 */
	public void saveHistoryProperty(PropertyHistory propertyHistory) {
		//修改titian属性
		searchDetailService.updateVertex(propertyHistory);
		//添加历史记录
		propertyHistory.setUpdateTime(new Date());
		propertyHistoryDao.getHibernateTemplate().save(propertyHistory);
	}

	/**
	 * 获取分页历史记录
	 * @param page
	 * @param propertyHistory 
	 * @return
	 */
	public PageModel<PropertyHistory> getHisPageModel(PageModel<PropertyHistory> page, PropertyHistory propertyHistory) {
		List<PropertyHistory> lst = propertyHistoryDao.getHistoryList(page,propertyHistory);
		page.setTotalRecords(propertyHistoryDao.getRowCount(propertyHistory));
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
	}
	

}
