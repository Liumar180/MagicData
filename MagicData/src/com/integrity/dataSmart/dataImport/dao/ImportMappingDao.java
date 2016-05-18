package com.integrity.dataSmart.dataImport.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.dataImport.bean.ImportMapping;

public class ImportMappingDao extends HibernateDaoSupport {

	/**
	 * 获取分页数据导入映射模板
	 * @param page
	 * @param graphHistory 
	 * @return
	 */
	public List<ImportMapping> getMappingList(PageModel<ImportMapping> pageModel,ImportMapping importMapping) {
		final int pageNo = pageModel.getPageNo();
		final int pagesize = pageModel.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from ImportMapping order by time desc"; 
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
		String hql="select count(*) from ImportMapping";
		Number num = (Number) getHibernateTemplate().find(hql).iterator().next();
		return num.intValue();
	}
	
	

}
