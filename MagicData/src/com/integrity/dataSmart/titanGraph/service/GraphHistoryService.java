package com.integrity.dataSmart.titanGraph.service;

import java.sql.SQLException;
import java.util.List;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.titanGraph.bean.GraphHistory;
import com.integrity.dataSmart.titanGraph.dao.GraphHistoryDao;

public class GraphHistoryService {
	private GraphHistoryDao graphHistoryDao;

	public void setGraphHistoryDao(GraphHistoryDao graphHistoryDao) {
		this.graphHistoryDao = graphHistoryDao;
	}

	/**
	 * 获取分页历史版本
	 * @param page
	 * @param graphHistory 
	 * @return
	 * @throws SQLException 
	 */
	public PageModel<GraphHistory> getHisPageModel(PageModel<GraphHistory> pageModel, GraphHistory graphHistory) throws SQLException {
		List<GraphHistory> lst = graphHistoryDao.getHistoryList(pageModel,graphHistory);
		pageModel.setTotalRecords(graphHistoryDao.getRowCount());
		pageModel.setTotalPage(pageModel.getTotalPages());
		pageModel.setList(lst);
		/*List<GraphHistoryObject> l = new ArrayList<GraphHistoryObject>();
		GraphHistoryObject gho = null;
		for(GraphHistory gh:lst){
			gho = new GraphHistoryObject();
			gho.setId(gh.getId());
			gho.setName(gh.getName());
			gho.setEdges(gh.getEdges());
			gho.setNodes(gh.getNodes());
//			gho.setImage(gh.getImage().getBytes(1, (int)gh.getImage().length()).toString());
			gho.setTime(gh.getTime());
			l.add(gho);
		}
		PageModel<GraphHistoryObject> pm = new PageModel<GraphHistoryObject>();
		pm.setList(l);
		pm.setPageNo(pageModel.getPageNo());
		pm.setPageSize(pageModel.getPageSize());
		pm.setSidx(pageModel.getSidx());
		pm.setSord(pageModel.getSord());
		pm.setTotalPage(pageModel.getTotalPage());
		pm.setTotalRecords(pageModel.getTotalRecords());
		return pm;*/
		return pageModel;
	}

	public void saveGraphHistory(GraphHistory graphHistory) {
		graphHistoryDao.getHibernateTemplate().save(graphHistory);
	}

	public void deleteGh(String id) {
		try{
			long idL = Long.parseLong(id);
			GraphHistory gh = (GraphHistory)graphHistoryDao.getHibernateTemplate().get(GraphHistory.class, idL);
			graphHistoryDao.getHibernateTemplate().delete(gh);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
}
