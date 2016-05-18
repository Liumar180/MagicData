package com.integrity.system.auth.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.system.auth.bean.Permission;
import com.integrity.system.auth.bean.RolePermissionRel;

public class PermissionDao extends HibernateDaoSupport {

	/**
	 * 获取全部资源
	 * @return
	 */
	public List<Permission> getAllPermission() {
		return getHibernateTemplate().loadAll(Permission.class);
	}

	/**
	 * 根据角色id获取角色资源关系
	 * @param roleId
	 * @return
	 */
	public List<RolePermissionRel> getRelByRoleId(Long roleId) {
		String hql="from RolePermissionRel where roleId = ? ";
		return getHibernateTemplate().find(hql,roleId);
	}

	/**
	 * 根据角色id集合获取角色资源关系
	 * @param roleIds
	 * @return
	 */
	public List<RolePermissionRel> getRelByRoleIds(final List<Long> roleIds) {
//		final Long[] idArr = roleIds.toArray(new Long[roleIds.size()]);
		final String sql="from RolePermissionRel where roleId in (:ids)"; 
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			Query query = session.createQuery(sql); 
			query.setParameterList("ids", roleIds);
			return query.list(); 
			}
		}); 
		return list;
	}

	/**
	 * 根据资源id集合获取资源列表
	 * @param perIds
	 * @return
	 */
	public List<Permission> getPermissionByperIds(final List<Long> perIds) {
//		final Long[] idArr = (Long[]) perIds.toArray(new Long[perIds.size()]);
		final String sql="from Permission where id in (:ids)"; 
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			Query query = session.createQuery(sql); 
			query.setParameterList("ids", perIds);
			return query.list(); 
			}
		}); 
		return list;
	}

}
