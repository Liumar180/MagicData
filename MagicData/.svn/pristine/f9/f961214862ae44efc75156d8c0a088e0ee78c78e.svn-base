package com.integrity.login.serviceImpl;

import java.util.List;

import com.integrity.demo.bean.MyBean;
import com.integrity.login.bean.User;
import com.integrity.login.dao.UserDao;
import com.integrity.login.service.UserService;
import com.integrity.login.util.PageInfo;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User findUser(User user) {
		return userDao.findUser(user);
	}
	
	public User findUserById(User user){
		return userDao.findUserById(user);
	}

	public int saveUser(User user) {
	 return userDao.saveUser(user);
	}
	public int findCount() {
		return userDao.findCount();
	}
	public List<User> findAllUser(PageInfo page,String username){
		return userDao.findAllUser(page,username);
	}
	public int deleteUser(int id) {
		return userDao.deleteUser(id);
	}
	public int modifyPwd(String username, String oldPwd, String newPwd) {
		return userDao.modifyPwd(username, oldPwd, newPwd);
	}
	public int modifyStatus(int id, int status) {
		return userDao.modifyStatus(id, status);
	}
	/**
	 * 查询全部user
	 */
	@Override
	public List<User> findAllUser() {
		return userDao.findAllUser();
	}
}
