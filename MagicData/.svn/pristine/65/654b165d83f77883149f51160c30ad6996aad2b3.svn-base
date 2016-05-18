package com.integrity.demo.daoImpl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.demo.bean.MyBean;
import com.integrity.demo.dao.DemoDao;

public class DemoDaoImpl extends HibernateDaoSupport implements DemoDao<MyBean> {

	@Override
	public MyBean find(long id) {
		return (MyBean) getHibernateTemplate().load(MyBean.class, id);
	}

	@Override
	public void save(MyBean t) {
		getHibernateTemplate().save(t);
	}

}
