package com.integrity.dataSmart.pwdCrack.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.pwdCrack.bean.PwdTask;
import com.integrity.login.util.PageInfo;

/**
 * @author HanXue
 * 密码破解任务DAO
 */
public class PwdTaskDao  extends HibernateDaoSupport{
	
	/**
	 * 新增
	 * @param pt
	 */
	public void addPwdTask(PwdTask pt){
		this.getHibernateTemplate().save(pt);
	}
	
	/**
	 * 更新
	 * @param pt
	 */
	public void updatePwdTask(PwdTask pt){
		this.getHibernateTemplate().update(pt);
	}
	
	/**
	 * 通过HQL语句查找
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<?> findByHQL(String hql, Object... values) {
		return getHibernateTemplate().find(hql, values);
	}
	/**
	 * 通过对象删除
	 * @param pt
	 */
	public void deletePwdTask(PwdTask pt){
		this.getHibernateTemplate().delete(pt);
	}
	
	/**
	 * 通过ID删除
	 * @param id
	 */
	public void deletePwdTaskById(String id){
		final String hql = "delete from PwdTask t where t.id = "+id;
		getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						return query.executeUpdate();
					}
		});
	}
	
	public PwdTask findById(String id) {
		String hql= "from PwdTask t where t.id = "+id;
		List<?> objList = getHibernateTemplate().find(hql);
		if(null==objList||objList.size()<1){
			return null;
		}else{
			return (PwdTask)(objList.get(0));
		}
	}
	
	public void sessionFlush(){
		this.getHibernateTemplate().flush();
	}
	
	public void doInitialize(Object obj){
		this.getHibernateTemplate().initialize(obj);
	}

	/**
	 * 获得记录总数
	 * @param hql
	 * @param values
	 * @return
	 */
	public int getAllCount(final String hql,final Object... values) {
		
		int allCount = 0;
		List<?> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				Query query = session.createQuery(hql);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				List<?> result = query.list();
				return result;
			}
		});
		if(null!=list){
			allCount=list.size();
		}
		return allCount;
	}
	

	/**
	 * 分页查询列表
	 * @param hql
	 * @param currentPageInfo
	 * @param values
	 * @return
	 */
	public PageInfo gotoPage(String hql,PageInfo currentPageInfo, Object... values) {
		
		int pageSize = 15; 
		int pageNo=1;
		int pageCount = 1;
		int allCount=0;
		
		int currPageSize = 0;
		int currPageIndex =0;
		String orderSidx = "";
		String orderSord = "";

		if(null!=currentPageInfo){
			currPageSize = currentPageInfo.getPageSize();
			currPageIndex = currentPageInfo.getPageNo();
			orderSidx = currentPageInfo.getSidx();
			orderSord = currentPageInfo.getSord();
		}

		if(currPageSize>0){
			pageSize=currPageSize;
		}
		if (currPageIndex <= 0) {
			currPageIndex = 1;
		} else {
			allCount= getAllCount(hql,values); 
			if(allCount>0){
				if(pageSize==Integer.MAX_VALUE){
					pageCount=1;
				}else{
					pageCount=(int)((allCount +pageSize - 1)/pageSize);
				}
			}
			if (allCount > currPageIndex * pageSize) {
				pageNo = currPageIndex;
			}else{
				pageNo=pageCount;
			}
		}
		String orderStr = "";
		if(null!=orderSord&&!"".equals(orderSord)&&null!=orderSidx&&!"".equals(orderSidx)){
			orderStr = " order by " + currentPageInfo.getSidx() +" " + currentPageInfo.getSord();
		}else{
			orderStr = " order by runStatus desc,createTime desc";//默认情况下未执行的新建任务排在前面
		}
		List<PwdTask> itemList = pagedQuery(hql+orderStr,pageNo,pageSize,values);
		
		currentPageInfo.setPageNo(pageNo);
		currentPageInfo.setPageSize(pageSize);
		currentPageInfo.setTotalPages(pageCount);
		currentPageInfo.setTotalRecords(allCount);
		currentPageInfo.setDataList(itemList);
		currentPageInfo.setSidx(orderSidx);
		currentPageInfo.setSord(orderSord);
		
		return currentPageInfo;
	}

	private List<PwdTask> pagedQuery(final String hql, int currentPage,
			final int pageSize, final Object... values) {

		if (currentPage == 0) {
			currentPage = 1;
		}
		final int curPage = currentPage;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				Query query = session.createQuery(hql);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				List result = query.setFirstResult((curPage - 1) * pageSize)
						.setMaxResults(pageSize).list();
				return result;
			}
		});
		return list;
	}
}
