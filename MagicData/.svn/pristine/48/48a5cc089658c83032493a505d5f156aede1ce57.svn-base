package com.integrity.system.auth.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.firebirdsql.jdbc.parser.JaybirdSqlParser.returningClause_return;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.system.auth.bean.Role;
import com.integrity.system.auth.bean.UserRoleRel;

public class RoleDao extends HibernateDaoSupport{

	/**
	 * 根据角色id删除资源关系
	 * @param roleId
	 */
	public void deleteRolePerRel(final Long roleId) {
		final String sql = "delete from rolePermissionRel where roleId = :roleId";
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				return arg0.createSQLQuery(sql).setParameter("roleId", roleId).executeUpdate();
			}
		});
	}

	/**
	 * 分页查询角色
	 * @param page
	 * @param roleName
	 * @return
	 */
	public List<Role> getRoleList(PageModel<Role> page, final String roleName) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from Role where 1=1 "; 
			if (StringUtils.isNotBlank(roleName)) {
				hql += " and roleName like ? ";
			}
			
			Query query = session.createQuery(hql);
			if (StringUtils.isNotBlank(roleName)) {
				query.setString(0, "%"+roleName+"%");
			}
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			return query.list(); 
			}
		}); 
		return list;
	}

	/**
	 * 获取角色总数
	 * @param roleName
	 * @return
	 */
	public Integer getRowCountByCondition(String roleName) {
		String hql = "select count(*) from Role where 1=1 "; 
		Number num = null;
		if (StringUtils.isNotBlank(roleName)) {
			hql += " and roleName like ? ";
			num = (Number) getHibernateTemplate().find(hql,"%"+roleName+"%").iterator().next();
		}else {
			num = (Number) getHibernateTemplate().find(hql).iterator().next();
		}
		return num.intValue();
	}

	/**
	 * 根据id数组删除角色
	 * @param idarr
	 */
	public void deleteRoleByIds(final String[] idarr) {
		final String sql="delete from role where id in (:ids)"; 
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				return arg0.createSQLQuery(sql).setParameterList("ids", idarr).executeUpdate();
			}
		});
	}
	
	/**
	 * 根据角色id数组删除角色资源关联
	 * @param idarr
	 */
	public void deleteRelByRoleIds(final String[] idarr) {
		final String sql="delete from rolePermissionRel where roleId in (:ids)"; 
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				return arg0.createSQLQuery(sql).setParameterList("ids", idarr).executeUpdate();
			}
		});
	}

	/**
	 * 根据角色id数组查询是否有用户关联
	 * @param idarr
	 * @return
	 */
	public int findUserRelByRoleids(final String[] idarr) {
		final String sql="select * from userRoleRel where roleId in (:ids)"; 
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			Query query = session.createSQLQuery(sql); 
			query.setParameterList("ids", idarr);
			return query.list(); 
			}
		}); 
		return list.size();
	}

	/**
	 * 获取全部角色
	 * @return
	 */
	public List<Role> getAllRole() {
		return getHibernateTemplate().loadAll(Role.class);
	}

	/**
	 * 根据userid 获取用户角色关系
	 * @param userId
	 * @return
	 */
	public List<UserRoleRel> getRelByUserId(Long userId) {
		String hql = "from UserRoleRel where userId = ?";
		return getHibernateTemplate().find(hql,userId);
	}

	/**
	 * 根据名称查询角色数
	 * @param roleName
	 * @return
	 */
	public int getCountByName(String roleName) {
		String hql = "from Role where roleName = ?";
		return getHibernateTemplate().find(hql,roleName).size();
	}

}
