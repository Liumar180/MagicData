package com.integrity.system.reset.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ResetDao extends HibernateDaoSupport{

	/**
	 * 清空表数据
	 * @return
	 */
	public void dropData() {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,SQLException {
				List list = session.createSQLQuery("select table_name from information_schema.tables where table_schema='magicdata'").list();
				if (CollectionUtils.isNotEmpty(list)) {
					for (Object object : list) {
						String tableName = object.toString();
						if (!"dictionaryTable".equalsIgnoreCase(tableName)) {
							session.createSQLQuery("truncate table "+tableName).executeUpdate();
						}
					}
				}
				return true;
			}
		});
	}

	/**
	 * 执行初始化说起来
	 * @param sql 初始化sql
	 * @return
	 */
	public void initData(final List<String> sqls) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,SQLException {
				for (String sql : sqls) {
					session.createSQLQuery(sql).executeUpdate();
				}
				return true;
			}
		});
	}

}
