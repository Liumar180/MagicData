package com.integrity.system.dataType.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.system.dataType.bean.DataObject;
import com.integrity.system.dataType.bean.DataType;

/**
 * @author liubf
 *
 */
public class DataTypeDao extends HibernateDaoSupport{
	
	@SuppressWarnings("rawtypes")
	public List<DataObject> searchObjlist(PageModel<DataObject> page,final DataObject dataObject) {
		final int pageNo = page.getPageNo();
		final int pageSize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from DataObject h where 1=1 ";
			hql +=" order by createTime desc";
			Query query = session.createQuery(hql); 
			query.setFirstResult((pageNo - 1) * pageSize); 
			query.setMaxResults(pageSize); 
			return query.list();  
			}
		});
		return list; 
		
	}
	
	public Integer Acounts(DataObject datas) {
		String hql = "select count(h.id) from DataObject h where 1=1 ";
		Number num = (Number) getHibernateTemplate().find(hql).iterator().next();
	    return num.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<DataType> findDataList(Long Id){
		String hql = "";
		hql="from DataType as h where h.ObjId="+Id; 
        List<DataType> list = getHibernateTemplate().find(hql);
        return list;
	
	}
	public Long saveDataObject(DataObject ho){
		Long id = (Long) getHibernateTemplate().save(ho);
		return id;
	}
	public void saveTypes(DataType dt){
		getHibernateTemplate().save(dt);
	}
	public void updateTypes(DataType dt){
		getHibernateTemplate().update(dt);
	}
	public void updateDataObject(DataObject ho){
		getHibernateTemplate().update(ho);
	}
	public DataObject findDetails(Long id){
		return (DataObject) getHibernateTemplate().get(DataObject.class, id);
		
	}
	public void delObjects(List<String> ids) {
		String hql="delete from DataObject where id in (:ids)"; 
		Session del = getHibernateTemplate().getSessionFactory().openSession();
		del.createSQLQuery(hql).setParameterList("ids", ids).executeUpdate();
	}
	public void delTypes(List<String> ids) {
		String hql="delete from DataType where ObjId in (:ids)"; 
		Session del = getHibernateTemplate().getSessionFactory().openSession();
		del.createSQLQuery(hql).setParameterList("ids", ids).executeUpdate();
	}
	public void delType(List<String> ids) {
		String hql="delete from DataType where id in (:ids)"; 
		Session del = getHibernateTemplate().getSessionFactory().openSession();
		del.createSQLQuery(hql).setParameterList("ids", ids).executeUpdate();
	}
	

}
