package com.integrity.dataSmart.dataImport.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.dataImport.bean.ImportTablesAndPropertys;
import com.integrity.dataSmart.dataImport.bean.TDatabase;


public class DBDao  extends HibernateDaoSupport {
	
	@SuppressWarnings({ "rawtypes" })
	public List findAllDB(String database) {
		return    getHibernateTemplate().find("from "+database);
	}
	public TDatabase findDB(long id) {
		List database= getHibernateTemplate().find("from TDatabase where id=?",id);
    	if (database.size()==0) {
			return null;
		} else {
			 return  (TDatabase) database.get(0);
		}
	}
	public TDatabase findDBByConnName(String connectionName) {
		List database= getHibernateTemplate().find("from TDatabase where connectionName=?",connectionName);
    	if (database.size()==0) {
			return null;
		} else {
			 return  (TDatabase) database.get(0);
		}
	}
	public ImportTablesAndPropertys findImportTablesAndPropertys(long id) {
		List database= getHibernateTemplate().find("from ImportTablesAndPropertys where id="+id);
    	if (database.size()==0) {
			return null;
		} else {
			 return  (ImportTablesAndPropertys) database.get(0);
		}
	}
	public void save(TDatabase d) {
		getHibernateTemplate().save(d);
	}
	
	public void edit(TDatabase d) {
		getHibernateTemplate().update(d);
	}
	public void delete(TDatabase d) {
		getHibernateTemplate().delete(d);
	}
	
	public void save(ImportTablesAndPropertys d) {
		getHibernateTemplate().save(d);
	}
	//clear data
	public void clearData(String tablename){
		String sql="delete from "+tablename;
		getHibernateTemplate().bulkUpdate(sql);
	}
	//通过DB，table  获取列明
	@SuppressWarnings("rawtypes")
	public List getcolumnsByDBTab(String sql){
		return getHibernateTemplate().find(sql);
	}
	public List getDBbyConnName(String connname){
		return getHibernateTemplate().find("from TDatabase where connectionName='"+connname+"'");
	}
}
