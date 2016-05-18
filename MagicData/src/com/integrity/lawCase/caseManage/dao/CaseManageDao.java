package com.integrity.lawCase.caseManage.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.cassandra.cli.CliParser.newColumnFamily_return;
import org.apache.commons.lang.StringUtils;
import org.firebirdsql.jdbc.parser.JaybirdSqlParser.nullValue_return;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.caseManage.bean.WorkRecord;

public class CaseManageDao extends HibernateDaoSupport{

	/**
	 * 分页查询案件
	 * @param page
	 * @return
	 */
	public List<CaseObject> getCaseList(PageModel<CaseObject> page) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from CaseObject"; 
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
	 * 案件记录总数
	 * @return
	 */
	public Integer getRowCount(){
		String hql="select count(*) from CaseObject";
		Number num = (Number) getHibernateTemplate().find(hql).iterator().next();
		return num.intValue();
	}
	
	/**
	 * 分页查询案件（卡片带条件）
	 * @param page
	 * @param direction 方向
	 * @param level 等级
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public List<CaseObject> getCaseListByCondition(PageModel<CaseObject> page,
			final String direction, final String level, final Date start, final Date end) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from CaseObject where 1=1 "; 
			if (StringUtils.isNotBlank(direction)) {
				hql += " and directionCode like '%"+direction+"%' ";
			}
			if (StringUtils.isNotBlank(level)) {
				hql += " and caseLevel = '"+level+"' ";
			}
			if (start!=null && end != null) {
				hql += " and createTime between ? and ?";
			}
			Query query = session.createQuery(hql); 
			if (start!=null && end != null) {
				query.setDate(0, start);
				query.setDate(1, end);
			}
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		return list;
	}

	/**
	 * 案件记录总数（卡片带条件）
	 * @param direction 方向
	 * @param level 等级
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public Integer getRowCountByCondition(String direction, String level,
			Date start, Date end) {
		String hql="select count(*) from CaseObject where 1=1 ";
		if (StringUtils.isNotBlank(direction)) {
			hql += " and directionCode like '%"+direction+"%' ";
		}
		if (StringUtils.isNotBlank(level)) {
			hql += " and caseLevel = '"+level+"' ";
		}
		if (start!=null && end != null) {
			hql += " and createTime between ? and ?";
		}
		Number num = 0;
		if (start!=null && end != null) {
			num = (Number) getHibernateTemplate().find(hql,new Date[]{start,end}).iterator().next();
		}else {
			num = (Number) getHibernateTemplate().find(hql).iterator().next();
		}
		
		return num.intValue();
	}
	
	/**
	 * 分页查询案件（列表带条件）
	 * @param page
	 * @param condition
	 * @param conditionValue
	 * @return
	 */
	public List<CaseObject> getCaseListByCondition(PageModel<CaseObject> page,final String condition, final String conditionValue) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from CaseObject where "+condition+" like ?";
			Query query = session.createQuery(hql); 
			query.setString(0, "%"+conditionValue+"%");
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		return list;
	}

	/**
	 * 案件记录总数（列表带条件）
	 * @param condition
	 * @param conditionValue
	 * @return
	 */
	public Integer getRowCountByCondition(String condition,String conditionValue) {
		String hql="select count(*) from CaseObject where "+condition+" like ?";
		Number num = (Number) getHibernateTemplate().find(hql,"%"+conditionValue+"%").iterator().next();
		return num.intValue();
	}

	/**
	 * 根据id查询案件
	 * @param id
	 * @return
	 */
	public CaseObject findCaseById(Long id) {
		return (CaseObject) getHibernateTemplate().get(CaseObject.class, id);
	}

	/**
	 * 根据id删除案件
	 * @param id
	 */
	public void deleteCase(Long id) {
		getHibernateTemplate().delete(findCaseById(id));
		
	}
	
	/**
	 * 删除多个案件
	 * @param idarr id数组
	 */
	public void deleteCaseByIds(final String[] idarr) {
		final String sql="delete from caseobject where id in (:ids)"; 
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				return arg0.createSQLQuery(sql).setParameterList("ids", idarr).executeUpdate();
			}
		});
	}
	
	/**
	 * 根据案件id查询工作记录
	 * @param recordPage 
	 * @param id
	 * @return
	 */
	public List<WorkRecord> findWorkRecordByCaseId(final Long caseId, PageModel<WorkRecord> page) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql="from WorkRecord where caseId=? order by createTime desc";
			Query query = session.createQuery(hql); 
			query.setLong(0, caseId);
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		return list;
	}
	
	/**
	 * 根据案件id查询工作记录
	 * @param recordPage 
	 * @param id
	 * @return
	 */
	public List<WorkRecord> findWorkRecordByCaseId(final Long caseId,final int pageNo,final int pageSize) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql="from WorkRecord where caseId=? order by createTime desc";
			Query query = session.createQuery(hql); 
			query.setLong(0, caseId);
			query.setFirstResult(0); 
			query.setMaxResults(pageNo*pageSize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		return list;
	}
	
	public List<WorkAllocation> findAllocationByCaseId(final Long caseId,final Integer pageNo, final Integer pageSize) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql="from WorkAllocation where caseId=? order by createTime desc";
			Query query = session.createQuery(hql); 
			query.setLong(0, caseId);
			query.setFirstResult(0); 
			query.setMaxResults(pageNo*pageSize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		return list;
	}
	
	/**
	 * 根据案件id查询工作记录总记录数
	 * @param caseId
	 * @return
	 */
	public Integer getWorkRecordRowCount(Long caseId) {
		String hql="select count(*) from WorkRecord where caseId=?";
		Number num = (Number) getHibernateTemplate().find(hql,caseId).iterator().next();
		return num.intValue();
	}

	/**
	 * 根据案件id查询工作配置
	 * @param allocationPage 
	 * @param id
	 * @return
	 */
	public List<WorkAllocation> findWorkAllocationByCaseId(final Long caseId, PageModel<WorkAllocation> page) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql="from WorkAllocation where caseId=? order by createTime desc";
			Query query = session.createQuery(hql); 
			query.setLong(0, caseId);
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		return list;
	}
	
	/**
	 * 根据案件id查询工作配置总记录数
	 * @param caseId
	 * @return
	 */
	public Integer getWorkAllocationRowCount(Long caseId) {
		String hql="select count(*) from WorkAllocation where caseId=?";
		Number num = (Number) getHibernateTemplate().find(hql,caseId).iterator().next();
		return num.intValue();
	}

	/**
	 * 根据记录id查询记录
	 * @param id
	 * @return
	 */
	public WorkRecord findRecordById(Long id) {
		return (WorkRecord) getHibernateTemplate().get(WorkRecord.class, id);
	}
	
	/**
	 * 根据id删除工作记录
	 * @param id
	 */
	public void deleteWorkRecord(Long id) {
		getHibernateTemplate().delete(findRecordById(id));
		
	}

	/**
	 * 根据配置id查询配置
	 * @param id
	 * @return
	 */
	public WorkAllocation findAllocationById(Long id) {
		return (WorkAllocation) getHibernateTemplate().get(WorkAllocation.class, id);
	}
	
	/**
	 * 根据id删除工作配置
	 * @param id
	 */
	public void deleteWorkAllocation(Long id) {
		getHibernateTemplate().delete(findAllocationById(id));
		
	}
	
	/**
	 * 查询全部案件
	 * @return
	 */
	public List<Object> findCases() {
		String hql="from CaseObject";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 查询全部文件
	 * @return
	 */
	public List<Object> findFiles() {
		String hql="from FilesObject";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 查询全部人员
	 * @return
	 */
	public List<Object> findPeoples() {
		String hql="from Peopleobject";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 查询全部主机
	 * @return
	 */
	public List<Object> findHosts() {
		String hql="from HostsObject";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 查询全部组织
	 * @return
	 */
	public List<Object> findOrgs() {
		String hql="from Organizationobject";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 根据name模糊查询案件
	 */
	public List<CaseObject> findCaseByName(String name) {
		String hql="from CaseObject where caseName like ?";
		return getHibernateTemplate().find(hql,"%"+name+"%");
	}
	
	/**
	 * 根据name精确查询案件
	 */
	public List<CaseObject> findCaseByNameExact(String name) {
		String hql="from CaseObject where caseName = ?";
		return getHibernateTemplate().find(hql,name);
	}
	

	/**
	 * 根据name模糊查询案件(根据id去除自身)
	 */
	public List<CaseObject> findCaseByName(Long id, String name) {
		String hql="from CaseObject where id != ? and caseName like ?";
		return getHibernateTemplate().find(hql,new Object[]{id,"%"+name+"%"});
	}

}
