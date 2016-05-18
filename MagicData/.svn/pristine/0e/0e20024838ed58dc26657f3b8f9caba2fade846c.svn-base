package com.integrity.dataSmart.dataImport.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.dataImport.bean.CsvData;

public class CsvDataDao extends HibernateDaoSupport {
	
	private Logger logger = Logger.getLogger(CsvDataDao.class);
	
	/**
	 * 保存csv
	 * @param csvData
	 */
	public void save(CsvData csvData) {
		getHibernateTemplate().save(csvData);
	}
	
	/**
	 * 获取分页数据csv
	 * @param pageModel
	 * @param csvData 
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<CsvData> getCsvFileList(PageModel<CsvData> pageModel,CsvData csvData) {
		final int pageNo = pageModel.getPageNo();
		final int pagesize = pageModel.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from CsvData order by uploadTime desc"; 
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
		String hql="select count(*) from CsvData";
		Number num = (Number) getHibernateTemplate().find(hql).iterator().next();
		return num.intValue();
	}
	
	/**
	 * 通过文件名称获取csv文件列表
	 * @param csvFileName
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<CsvData> findCsvByFileName(String csvFileName) {
		HibernateTemplate h = getHibernateTemplate();
		String hql = "from CsvData where fileName='"+csvFileName+"'";
	    List<CsvData> list=h.find(hql);
		return list;
	}
	
	/**
	 * 通过id获取csvData
	 * @param id
	 * @return
	 */
	public CsvData findCsvById(String id) {
		HibernateTemplate h = getHibernateTemplate();
		String hql = "from CsvData where id='"+id+"'";
	    List<CsvData> list=h.find(hql);
	    if(list!=null&&list.size()>0){
	    	return list.get(0);
	    }
		return null;
	}
	
	/**
	 * 删除csv
	 * @param csvData
	 */
	public boolean deleteCsvData(CsvData csvData) {
		HibernateTemplate h = getHibernateTemplate();
		h.delete(csvData);
		return true;
	}
	
	/**
	 * 修改csvData
	 * @param csvData
	 */
	public void updateCsvData(CsvData csvData) {
		HibernateTemplate h = getHibernateTemplate();
		h.update(csvData);
	}
	
}
