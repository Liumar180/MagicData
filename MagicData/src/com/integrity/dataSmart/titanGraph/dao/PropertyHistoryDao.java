package com.integrity.dataSmart.titanGraph.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.titanGraph.bean.PropertyHistory;

public class PropertyHistoryDao extends HibernateDaoSupport{

	/**
	 * 获取分页历史记录
	 * @param page
	 * @param propertyHistory 
	 * @return
	 */
	public List<PropertyHistory> getHistoryList(PageModel<PropertyHistory> page, PropertyHistory propertyHistory) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		final Long vertexId = propertyHistory.getVertexId();
		final String property = propertyHistory.getProperty();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from PropertyHistory where vertexId = ? and property = ?"; 
			Query query = session.createQuery(hql); 
			query.setLong(0, vertexId);
			query.setString(1, property);
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
	 * @param propertyHistory 
	 * @return
	 */
	public Integer getRowCount(PropertyHistory propertyHistory) {
		Long vertexId = propertyHistory.getVertexId();
		String property = propertyHistory.getProperty();
		String hql="select count(*) from PropertyHistory where vertexId = ? and property = ?";
		Number num = (Number) getHibernateTemplate().find(hql,new Object[]{vertexId,property}).iterator().next();
		return num.intValue();
	}

}
