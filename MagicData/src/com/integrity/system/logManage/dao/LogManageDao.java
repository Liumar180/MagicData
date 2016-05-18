package com.integrity.system.logManage.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.system.logManage.bean.LogObject;


public class LogManageDao extends HibernateDaoSupport{
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Calendar rightNow = Calendar.getInstance();
	/**
	 * 分页查询
	 * @param page
	 * @param l2
	 * @return
	 */
	public List<LogObject> getAllLogObject(PageModel<LogObject> page,LogObject l2){
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		final LogObject l=l2;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			StringBuffer hql = new StringBuffer();
			hql.append("from LogObject where 1=1");
			if(l.getUserName()!=null&&!"".equals(l.getUserName())){
				hql.append(" and  username like '%"+l.getUserName().replaceAll("'", "\"")+"%'");
			}
			if(l.getOperation()!=null&&!"".equals(l.getOperation())){
				hql.append(" and  operation like '%"+l.getOperation().replaceAll("'", "\"")+"%'");
			}
			if(l.getOperationType()!=null&&!"".equals(l.getOperationType())){
				hql.append(" and  operationtype like '%"+l.getOperationType().replaceAll("'", "\"")+"%'");
			}
			if(l.getCreatTime()!=null&&!"".equals(l.getCreatTime())){
				hql.append(" and  creatTime>='"+simpleDateFormat.format(l.getCreatTime())+"'");
			}
			if(l.getEndTime()!=null&&!"".equals(l.getEndTime())){
				hql.append(" and  creatTime<='"+simpleDateFormat.format(l.getEndTime())+"'");
			}
			
			hql.append(" Order By creatTime Desc");
			Query query = session.createQuery(hql.toString()); 
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		return list;
	}
	/**
	 * 案件记录总数
	 * @return
	 */
	public int findRowCount(LogObject l){
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from LogObject where 1=1 ");
		if(l.getUserName()!=null&&!"".equals(l.getUserName())){
			hql.append(" and  username like '%"+l.getUserName().replaceAll("'", "\"")+"%'");
		}
		if(l.getOperation()!=null&&!"".equals(l.getOperation())){
			hql.append(" and  operation like '%"+l.getOperation().replaceAll("'", "\"")+"%'");
		}
		if(l.getOperationType()!=null&&!"".equals(l.getOperationType())){
			hql.append(" and  operationtype like '%"+l.getOperationType().replaceAll("'", "\"")+"%'");
		}
		if(l.getCreatTime()!=null&&!"".equals(l.getCreatTime())){
			hql.append(" and  creatTime>='"+simpleDateFormat.format(l.getCreatTime())+"'");
		}
		if(l.getEndTime()!=null&&!"".equals(l.getEndTime())){
			hql.append(" and  creatTime<='"+simpleDateFormat.format(l.getEndTime())+"'");
		}
		hql.append(" Order By creatTime Desc");
		Number num = (Number) getHibernateTemplate().find(hql.toString()).iterator().next();
		return  num.intValue();
	}
	public void saveLog(LogObject l){
		getHibernateTemplate().save(l);
	}
}
