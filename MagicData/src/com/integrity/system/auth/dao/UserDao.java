package com.integrity.system.auth.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.system.auth.bean.AuthUser;

public class UserDao extends HibernateDaoSupport{
	/**
	 * 根据用户id删除角色关系
	 * @param userId
	 */
	public void deleteUserPerRel(final Long userId) {
		final String sql = "delete from userRoleRel where userId = :userId";
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				return arg0.createSQLQuery(sql).setParameter("userId", userId).executeUpdate();
			}
		});
	}

	/**
	 * 分页查询用户
	 * @param page
	 * @param userName
	 * @return
	 */
	public List<AuthUser> getUserList(PageModel<AuthUser> page, final String userName) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from AuthUser where 1=1 "; 
			if (StringUtils.isNotBlank(userName)) {
				hql += " and userName like ? ";
			}
			
			Query query = session.createQuery(hql);
			if (StringUtils.isNotBlank(userName)) {
				query.setString(0, "%"+userName+"%");
			}
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			return query.list(); 
			}
		}); 
		return list;
	}

	/**
	 * 获取用户总数
	 * @param userName
	 * @return
	 */
	public Integer getRowCountByCondition(String userName) {
		String hql = "select count(*) from AuthUser where 1=1 "; 
		Number num = null;
		if (StringUtils.isNotBlank(userName)) {
			hql += " and userName like ? ";
			num = (Number) getHibernateTemplate().find(hql,"%"+userName+"%").iterator().next();
		}else {
			num = (Number) getHibernateTemplate().find(hql).iterator().next();
		}
		return num.intValue();
	}

	/**
	 * 根据id数组删除用户
	 * @param idarr
	 */
	public void deleteUserByIds(final String[] idarr) {
		final String sql="delete from authUser where id in (:ids)"; 
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				return arg0.createSQLQuery(sql).setParameterList("ids", idarr).executeUpdate();
			}
		});
	}
	
	/**
	 * 根据用户id数组删除用户角色关联
	 * @param idarr
	 */
	public void deleteRelByUserIds(final String[] idarr) {
		final String sql="delete from userRoleRel where userId in (:ids)"; 
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				return arg0.createSQLQuery(sql).setParameterList("ids", idarr).executeUpdate();
			}
		});
	}

	/**
	 * 根据名称获取用户数
	 * @param userName
	 * @return
	 */
	public int getCountByName(String userName) {
		String hql = "from AuthUser where userName = ?";
		return getHibernateTemplate().find(hql,userName).size();
	}
	
	/**
	 * 根据用户名密码查询状态为启用状态的用户
	 * @param userName
	 * @param password
	 * @return
	 */
	public AuthUser findUser(String userName,String password) {
		String hql = "from AuthUser where status=0 and userName=? and password=?";
		List users = getHibernateTemplate().find(hql, new Object[]{userName,password});
		if(users!=null && users.size()!=0){
			AuthUser user = (AuthUser)users.get(0);
			return user;
		}else{
			return null;
		}
	}

	/**
	 * 根据用户名称查询用户
	 * @param userName
	 * @return
	 */
	public AuthUser findUserByUserName(String userName) {
		String hql = "from AuthUser where status=0 and userName=?";
		List users = getHibernateTemplate().find(hql, userName);
		if(users!=null && users.size()!=0){
			AuthUser user = (AuthUser)users.get(0);
			return user;
		}else{
			return null;
		}
	}

	/**
	 * 查询全部user
	 */
	public List<AuthUser> findAllUser() {
		return getHibernateTemplate().loadAll(AuthUser.class);
	}
	
	

	
}
