package com.integrity.login.daoImpl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.login.bean.User;
import com.integrity.login.dao.UserDao;
import com.integrity.login.util.PageInfo;

/**
 * 
 * @author weishuai
 * 根据原先UserDaoImpl.java，把数据源变为spring session管理的方法查询
 * 
 */
public class UserDaoImplNew extends HibernateDaoSupport implements UserDao{

	@Override
	public User findUser(User user) {
		String hql = "from User where status=0 and username=? and password=?";
		List users = getHibernateTemplate().find(hql, new Object[]{user.getUserName(),user.getPassword()});
		if(users!=null&&users.size()!=0){
			User u = (User)users.get(0);
			user.setRoleType(u.getRoleType());
			return user;
		}else{
			return null;
		}
	}
	
	public User findUserById(User user) {
		String hql = "from User where status=0 and id=?";
		List users = getHibernateTemplate().find(hql, new Object[]{user.getId()});
		if(users!=null&&users.size()!=0){
			User u = (User)users.get(0);
			u.setRoleType(u.getRoleType());
			return u;
		}else{
			return null;
		}
	}

	@Override
	public int findCount() {
		String query = "select count(*) from User";
		Integer count = ((Long)getHibernateTemplate().find(query).listIterator().next()).intValue();
		return count.intValue();
	}

	@Override
	public int saveUser(User user) {
		String se="select count(*) from User where username=?";
		Integer count = ((Long)getHibernateTemplate().find(se,user.getUserName()).listIterator().next()).intValue();
		if(count!=0){
			return -1;
		}else{
			Timestamp now = new Timestamp(new Date().getTime());
			user.setCdate(now);
			user.setMdate(now);
			user.setSex("男");
			getHibernateTemplate().save(user);
			return 1;
		}
	}

	@Override
	public List<User> findAllUser(PageInfo page, String username) {
		List result = null;
		int rowCount = 0;
		if(username!=null&&username.length()!=0){
			String count="select count(*)  from User where username like ?";
			String select = "select * from user where username like ? limit ?,?";
			rowCount = ((Long)getHibernateTemplate().find(count,"%"+username+"%").listIterator().next()).intValue();
			SQLQuery createSQLQuery = getSession().createSQLQuery(select);
			createSQLQuery.setString(0, "%"+username+"%");
			createSQLQuery.setInteger(1, (page.getPageNo() - 1) * page.getPageSize());
			createSQLQuery.setInteger(2, page.getPageSize());
			result = createSQLQuery.list();
		}else{
			String count="select count(*)  from User";
			String select = "select * from user limit ?,?";
			rowCount = ((Long)getHibernateTemplate().find(count).listIterator().next()).intValue();
			SQLQuery createSQLQuery = getSession().createSQLQuery(select);
			createSQLQuery.setInteger(0, (page.getPageNo() - 1) * page.getPageSize());
			createSQLQuery.setInteger(1, page.getPageSize());
			result = createSQLQuery.list();
		}
		int pageCount = 1;
		if (rowCount % page.getPageSize() == 0) {
			pageCount = rowCount / page.getPageSize();
		} else {
			pageCount = rowCount / page.getPageSize() + 1;
		}
		if (page != null) {
			page.setTotalRecords(rowCount);
			page.setTotalPages(pageCount);
		}
		List<User> users = new ArrayList<User>();
		if(result!=null&&result.size()!=0){
			for(int i = 0 ; i < result.size() ; i++) {
				Object[] objects = (Object[]) result.get(i);
				User user = new User();
				long id = ((BigInteger) objects[0]).longValue();
				String usernameU = (String) objects[1];
				String phone = (String) objects[3];
				String nickname = (String) objects[4];
				String name =  (String) objects[5];
				int roleType = (int) objects[7];
				int status = (int) objects[8] ;
				Timestamp cdate = null;
				
				user.setId(id);
				user.setUserName(usernameU);
				user.setPhone(phone);
				user.setNickName(nickname);
				user.setName(name);
				user.setRoleType(roleType);
				user.setStatus(status);
				user.setCdate(cdate);
				users.add(user);
			}
		}
		return users;
	}

	@Override
	public int deleteUser(int id) {
		User user = new User();
		user.setId(id);
		user = (User) getHibernateTemplate().get(User.class, new Long(id));
		getHibernateTemplate().delete(user);
		return 0;
	}

	@Override
	public int modifyPwd(String username, String oldPwd, String newPwd) {
		int i = 0;//没查到-1  更改为1  没改到为0
		String hql = "from User where username=? and password=?";
		List users = getHibernateTemplate().find(hql, new Object[]{username,oldPwd});
		if(users!=null&&users.size()!=0){
			User user = (User) users.get(0);
			user.setPassword(newPwd);
			getHibernateTemplate().update(user);
			i = 1;
		}else{
			i = -1;
		}
		return i;
	}

	@Override
	public int modifyStatus(int id, int status) {
		User user = (User) getHibernateTemplate().get(User.class, new Long(id));
		user.setStatus(status);
		getHibernateTemplate().update(user);
		return 0;
	}

	/**
	 * 查询全部user
	 */
	@Override
	public List<User> findAllUser() {
		return getHibernateTemplate().loadAll(User.class);
	}

}
