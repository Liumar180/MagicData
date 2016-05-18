package com.integrity.dataSmart.titanGraph.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.titanGraph.bean.GraphHistory;
import com.integrity.dataSmart.titanGraph.pojo.GraphHistoryObject;

public class GraphHistoryDao extends HibernateDaoSupport {

	/**
	 * 获取分页历史版本
	 * @param page
	 * @param graphHistory 
	 * @return
	 */
	public List<GraphHistory> getHistoryList(PageModel<GraphHistory> pageModel,GraphHistory graphHistory) {
		final int pageNo = pageModel.getPageNo();
		final int pagesize = pageModel.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from GraphHistory order by time desc"; 
			Query query = session.createQuery(hql); 
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		return list;
	}

	/**
	 * 获取总记录数
	 * @return
	 */
	public Integer getRowCount() {
		String hql="select count(*) from GraphHistory";
		Number num = (Number) getHibernateTemplate().find(hql).iterator().next();
		return num.intValue();
	}
	
	

}
