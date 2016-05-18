package com.integrity.demo.serviceImpl;

import com.integrity.demo.bean.MyBean;
import com.integrity.demo.dao.DemoDao;
import com.integrity.demo.service.DemoService;

public class DemoServiceImpl implements DemoService<MyBean> {
	
	private DemoDao<MyBean> demoDao;

	public void setDemoDao(DemoDao<MyBean> demoDao) {
		this.demoDao = demoDao;
	}

	@Override
	public MyBean find(long id) {
		return demoDao.find(id);
	}

	@Override
	public void save(MyBean t) {
		try {
			demoDao.save(t);
		} catch (Exception e) {
			throw e; 
		}
		
	}

}
