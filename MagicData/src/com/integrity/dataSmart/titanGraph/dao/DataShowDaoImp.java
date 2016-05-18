package com.integrity.dataSmart.titanGraph.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.titanGraph.bean.DataStatistics;

public class DataShowDaoImp extends HibernateDaoSupport implements DataShowDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<DataStatistics> searchDatas(String dataType,String labelName,String area) {
		String hql = "";
		hql="from DataStatistics as h where 1=1";
		if(dataType != null && !"null".equals(dataType) && !"".equals(dataType)){
    		hql +=" and h.dataType="+dataType;
    	}
		if(labelName != null && !"null".equals(labelName) && !"".equals(labelName)){
			hql +=" and h.labelName="+labelName; 
    	}
		if(area != null && !"null".equals(area) && !"".equals(area)){
			hql +=" and h.area="+area; 
    	}
        List<DataStatistics> list = getHibernateTemplate().find(hql);
        return list;
	}

	@Override
	public void saveDatas(DataStatistics datas) {
		getHibernateTemplate().save(datas);
	}

	@Override
	public BigDecimal statisticsAcountsByFileds(String dataType,
			String labelName, String area) {
		String hql = "";
		hql="select SUM(h.dataAcount) acount from DataStatistics as h where 1=1";
		if(dataType != null && !"null".equals(dataType) && !"".equals(dataType)){
    		hql +=" and h.dataType="+dataType;
    	}
		if(labelName != null && !"null".equals(labelName) && !"".equals(labelName)){
			hql +=" and h.labelName="+labelName; 
    	}
		if(area != null && !"null".equals(area) && !"".equals(area)){
			hql +=" and h.area="+area; 
    	}
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		SQLQuery query = session.createSQLQuery(hql);
		BigDecimal number = (BigDecimal) query.uniqueResult();
		return number;
	}
	

}
