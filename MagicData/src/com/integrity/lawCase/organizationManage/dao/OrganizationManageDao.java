package com.integrity.lawCase.organizationManage.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.system.auth.bean.AuthUser;



public class OrganizationManageDao  extends HibernateDaoSupport {
		
	/**
	 * 分页查询组织
	 * @param page
	 * @return
	 */
	public List<Organizationobject> getOrgList(PageModel<Organizationobject> page) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		String sordTempStr = "";
		if(null!=page.getSidx()&&!"".equals( page.getSidx().trim())&&null!=page.getSord()&&!"".equals( page.getSord().trim())){
			sordTempStr = page.getSidx() + " "+page.getSord();
		}
		final String sordStr = sordTempStr;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from Organizationobject t"; 
			if(null!=sordStr&&!"".equals(sordStr.trim())){
				hql += " order by t."+sordStr;
			}
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
	 * 组织记录总数
	 * @return
	 */
	public Integer getRowCount(){
		String hql="select count(*) from Organizationobject";
		Number num = (Number) getHibernateTemplate().find(hql).iterator().next();
		return num.intValue();
	}
	
	/**
	 * 分页查询组织（卡片带条件）
	 * @param page
	 * @param direction 方向
	 * @param level 等级
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public List<Organizationobject> getOrgCardByCondition(PageModel<Organizationobject> page,
			final String direction, final String level, final Date start, final Date end) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		String sordTempStr = "";
		if(null!=page.getSidx()&&!"".equals( page.getSidx().trim())&&null!=page.getSord()&&!"".equals( page.getSord().trim())){
			sordTempStr = page.getSidx() + " "+page.getSord();
		}
		final String sortStr = sordTempStr;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from Organizationobject where 1=1 "; 
			if (StringUtils.isNotBlank(direction)) {
				hql += " and orgDirectionCodes like '%"+direction+"%' ";
			}
			if (StringUtils.isNotBlank(level)) {
				hql += " and orgImportLevel = '"+level+"' ";
			}
			if (start!=null && end != null) {
				hql += " and createTime between ? and ?";
			}
			if(null!=sortStr&&!"".equals(sortStr.trim())){
				hql += sortStr;
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
	 * 组织记录总数（卡片带条件）
	 * @param direction 方向
	 * @param level 等级
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public Integer getOrgCardCountByCond(String direction, String level,
			Date start, Date end) {
		String hql="select count(*) from Organizationobject where 1=1 ";
		if (StringUtils.isNotBlank(direction)) {
			hql += " and orgDirectionCodes like '%"+direction+"%' ";
		}
		if (StringUtils.isNotBlank(level)) {
			hql += " and orgImportLevel = '"+level+"' ";
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
	 * 分页查询组织（列表带条件）
	 * @param page
	 * @param condition
	 * @param conditionValue
	 * @return
	 */
	public List<Organizationobject> getOrgListByCondition(PageModel<Organizationobject> page,final String condition, final String conditionValue) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
				String hql = "from Organizationobject where "+condition+" like ?";//" = ?";
				
				if("orgDutyPersonIds".equals(condition)){
					String useHql = "from AuthUser u where u.userName like ?";
					Query userQuery = session.createQuery(useHql); 
					userQuery.setString(0, "%"+conditionValue+"%");
					List userList = userQuery.list();
					if(null!=userList&&userList.size()>0){
						StringBuilder userIds = new StringBuilder();
						String userCon = "";
						for(Object tempUser:userList){
							AuthUser au = (AuthUser)tempUser;
							userIds.append(" t.orgDutyPersonIds like '"+au.getId()+",%' ");
							userIds.append("or");
							userIds.append(" t.orgDutyPersonIds like '%,"+au.getId()+"' ");
							userIds.append("or");
							userIds.append(" t.orgDutyPersonIds like '"+au.getId()+"' ");
							userIds.append("or");
							userIds.append(" t.orgDutyPersonIds like '%,"+au.getId()+",%' ");
							userIds.append("or");
						}
						if(userIds.length()>0){
							userCon = userIds.substring(0, userIds.length()-2);
						}
						hql = "from Organizationobject t where" + userCon;
					}else{
						return null;
					}
				}
				Query query = session.createQuery(hql); 
				if(!"orgDutyPersonIds".equals(condition)){
					query.setString(0, "%"+conditionValue+"%");
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
	 * 组织记录总数（列表带条件）
	 * @param condition
	 * @param conditionValue
	 * @return
	 */
	public Integer getOrgListCountByCond(String condition,String conditionValue) {
		String hql="select count(*) from Organizationobject where "+condition+" = ?";
		Number num = (Number) getHibernateTemplate().find(hql,conditionValue).iterator().next();
		return num.intValue();
	}
	/**
	 * 根据id查询组织
	 * @param id
	 * @return
	 */
	public Organizationobject findOrgById(Long id) {
		Object tempObj = getHibernateTemplate().get(Organizationobject.class, id);
		if(null!=tempObj){
			return (Organizationobject)tempObj;
		}else{
			return null;
		}
	}

	/**
	 * 根据id删除组织
	 * @param id
	 */
	public void deleteOrg(Long id) {
		Organizationobject tempOrg = findOrgById(id);
		if(null!=tempOrg){
			getHibernateTemplate().delete(tempOrg);
		}
	}
	
	/**
	 * 删除多个组织
	 * @param idarr id数组
	 */
	public void deteleOrgByIds(final String[] idarr) {
		final String sql="delete from organizationobject where id in (:ids)"; 
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				return arg0.createSQLQuery(sql).setParameterList("ids", idarr).executeUpdate();
			}
		});
	}
	
	/**
	 * 根据name查询组织
	 */
	public List<Organizationobject> findOrgByFitName(String name) {
		String hql="from Organizationobject t where t.orgCName = '"+name.trim()+"'";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 根据name模糊查询组织
	 */
	public List<Organizationobject> findOrgByName(String name) {
		String hql="from Organizationobject where orgCName like ?";
		return getHibernateTemplate().find(hql,"%"+name+"%");
	}
	

	/**
	 * 根据name模糊查询组织(根据id去除自身)
	 */
	public List<Organizationobject> findOrgByNameNoId(Long id, String name) {
		String hql="from Organizationobject where id != ? and orgCName like ?";
		return getHibernateTemplate().find(hql,new Object[]{id,"%"+name+"%"});
	}
}
