package com.integrity.lawCase.common.init.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.lawCase.common.bean.DictionaryTable;

public class DictionaryDao extends HibernateDaoSupport{
	
	/**
	 * 根据pid查询字典表
	 * @param pid
	 * @return
	 */
	public List<DictionaryTable> findDicByPid(Long pid){
		String hql = "from DictionaryTable where pid=?";
		List<DictionaryTable> lst = getHibernateTemplate().find(hql,pid);
		return lst;
	}
	/**
	 * 根据name查询字典表
	 * @param pid
	 * @return
	 */
	public DictionaryTable findDicByName(String name){
		DictionaryTable dt = null;
		String hql = "from DictionaryTable where name=?";
		List<DictionaryTable> lst = getHibernateTemplate().find(hql,name);
		if(lst!=null&&lst.size()>0){
			dt = lst.get(0);
		}
		return dt;
	}
	
	/**
	 * 根据name和pid查询字典表
	 * @param pid
	 * @return
	 */
	public DictionaryTable findDicByNameAndPid(String name,Long pid){
		DictionaryTable dt= null;
		String hql = "from DictionaryTable where name=? and pid=?";
		List<DictionaryTable> lst = getHibernateTemplate().find(hql,new Object[]{name,pid});
		if(lst!=null&lst.size()>0){
			dt = lst.get(0);
		}
		return dt;
	}


}
