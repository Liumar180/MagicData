package com.integrity.system.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.login.action.Encryption;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.bean.Permission;
import com.integrity.system.auth.bean.RolePermissionRel;
import com.integrity.system.auth.bean.UserRoleRel;
import com.integrity.system.auth.dao.PermissionDao;
import com.integrity.system.auth.dao.RoleDao;
import com.integrity.system.auth.dao.UserDao;


public class UserService {
	private final String PASSWORDSTR = "6!e@9#a1fbZdA4C51e";

	private UserDao userDao;
	private RoleDao roleDao;
	private PermissionDao permissionDao;

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public PageModel<AuthUser> getUserPageModel(PageModel<AuthUser> page,String userName) {
		List<AuthUser> lst = userDao.getUserList(page,userName);
		page.setTotalRecords(userDao.getRowCountByCondition(userName));
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
	}
	
	/**
	 * 添加用户并关联资源
	 * @param user
	 * @param roleIds
	 * @throws Exception 
	 */
	public void saveUser(AuthUser user,String roleIds) throws Exception{
		user.setUserName(user.getUserName().trim());
		//密码加密
		user.setPassword(Encryption.Encrypt(user.getPassword().trim()));
		user.setStatus(0);
		user.setCreateTime(new Date());
		userDao.getHibernateTemplate().save(user);
		Long userId = user.getId();
		saveUserRoleRel(userId, roleIds);
	}
	
	/**
	 * 根据id查询用户（后台查询，不转换密码）
	 * @param id
	 * @return
	 */
	public AuthUser findUserById2Back(Long id) {
		AuthUser user = (AuthUser) userDao.getHibernateTemplate().get(AuthUser.class, id);
		return user;
	}
	
	/**
	 * 根据id查询用户(页面显示，隐藏密码)
	 * @param id
	 * @return
	 */
	public AuthUser findUserById(Long id) {
		AuthUser user = (AuthUser) userDao.getHibernateTemplate().get(AuthUser.class, id);
		if(null!=user){
			user.setPassword(PASSWORDSTR);
		}
		return user;
	}
	
	/**
	 * 用户编辑并关联角色
	 * @param user
	 * @param roleIds
	 * @throws Exception 
	 */
	public void updateUser(AuthUser user, String roleIds) throws Exception {
		//密码加密
		String pagePassword = user.getPassword();
		if (PASSWORDSTR.equals(pagePassword)) {
			pagePassword = findUserById2Back(user.getId()).getPassword();
		}else {
			pagePassword = Encryption.Encrypt(user.getPassword().trim());
		}
		user.setPassword(pagePassword);
		user.setModifyTime(new Date());
//		userDao.getHibernateTemplate().update(user);
		userDao.getHibernateTemplate().merge(user);
		Long userId = user.getId();
		userDao.deleteUserPerRel(userId);
		saveUserRoleRel(userId, roleIds);
	}
	
	/**
	 * 修改用户状态
	 * @param user
	 */
	public void updateUserStatus(AuthUser user) {
		Integer status = user.getStatus();
		user = findUserById2Back(user.getId());
		user.setStatus(status==0?1:0);
		user.setModifyTime(new Date());
		userDao.getHibernateTemplate().merge(user);
	}
	
	/**
	 * 根据ids删除用户
	 * @param ids
	 */
	public void deleteUsers(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idarr = ids.split(",");
			//删除用户
			userDao.deleteUserByIds(idarr);
			//删除用户角色关系
			userDao.deleteRelByUserIds(idarr);
		}
	}
	
	/**
	 * 验证用户名称不能重复
	 * @param userName
	 * @return
	 */
	public boolean validateUserName(String userName) {
		int count = userDao.getCountByName(userName);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据用户名密码查询状态为启用状态的用户
	 * @param user
	 * @return
	 */
	public AuthUser findUser(AuthUser user) {
		String userName = user.getUserName();
		String password = user.getPassword();
		if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
			return userDao.findUser(userName, password);
		}else {
			return null;
		}
	}
	
	/**
	 * 根据userid获取资源列表
	 * @param userId
	 * @return
	 */
	public List<String> findPermissionListByUserId(Long userId) {
		List<UserRoleRel> relList = roleDao.getRelByUserId(userId);
		List<String> list = new ArrayList<String>();
		List<Long> roleIds = new ArrayList<Long>();
		List<Long> perIds = new ArrayList<Long>();
		if (relList != null) {
			for (UserRoleRel urr : relList) {
				roleIds.add(urr.getRoleId());
			}
		}
		if (roleIds.size() == 0) {
			return list;
		}
		List<RolePermissionRel> rolePerRelList = permissionDao.getRelByRoleIds(roleIds);
		if (rolePerRelList != null) {
			for (RolePermissionRel rpr : rolePerRelList) {
				perIds.add(rpr.getPerId());
			}
		}
		if (perIds.size() == 0) {
			return list;
		}
		List<Permission> pers = permissionDao.getPermissionByperIds(perIds);
		for (Permission permission : pers) {
			list.add(permission.getPermission());
		}
		return list;
	}
	
	/**
	 * 验证密码是否正确
	 * @param userName
	 * @param oldPwd
	 * @return
	 * @throws Exception 
	 */
	public boolean validateUserPwd(String userName, String oldPwd) throws Exception {
		AuthUser user = userDao.findUser(userName, Encryption.Encrypt(oldPwd));
		if (user == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 跟据用户名修改密码
	 * @param userName
	 * @param newPwd
	 * @return
	 * @throws Exception 
	 */
	public boolean updateUserPassword(String userName, String newPwd) throws Exception {
		AuthUser user = userDao.findUserByUserName(userName);
		if (user == null) {
			return false;
		}
		user.setPassword(Encryption.Encrypt(newPwd));
		user.setModifyTime(new Date());
		userDao.getHibernateTemplate().update(user);
		return true;
	}
	/**
	 * 查询全部user
	 */
	public List<AuthUser> findAllUser() {
		return userDao.findAllUser();
	}
	
	/******************************************************************************************/
	
	/**
	 * 保存用户角色而关系
	 * @param userId
	 * @param roleIds
	 */
	private void saveUserRoleRel(Long userId,String roleIds){
		if (StringUtils.isNotBlank(roleIds)) {
			String[] roleIdArr = roleIds.split(",");
			for (String str : roleIdArr) {
				Long roleId = Long.parseLong(str);
				UserRoleRel urr = new UserRoleRel();
				urr.setUserId(userId);
				urr.setRoleId(roleId);
				userDao.getHibernateTemplate().save(urr);
			}
		}
	}
	
}
