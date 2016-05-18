package com.integrity.system.auth.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(UserAction.class);
	private UserService userService;
	private AuthUser user;
	private String roleIds;
	private PageModel<AuthUser> pageModel;
	private String ids;
	private boolean flag;
	private String oldPwd;
	private String newPwd;
	private String msg;
	private HttpServletRequest request = ServletActionContext.getRequest();
	
	/**
	 * 用户管理页面
	 * @return
	 */
	public String viewUserPage(){
		return SUCCESS;
	}
	
	/**
	 * 用户添加页面
	 * @return
	 */
	public String viewAddUserPage(){
		request.setAttribute("detailFlag", false);
		return SUCCESS;
	}
	
	/**
	 * 用户列表
	 * @return
	 */
	public String findUserList(){
		try {
			String userName = "";
			if (user!=null) {
				userName = user.getUserName();
			}
			pageModel = userService.getUserPageModel(pageModel,userName);
		} catch (Exception e) {
			logger.error("查询用户列表异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 用户详细页面
	 * @return
	 */
	public String viewDetailUserPage(){
		try {
			Long id = user.getId();
			user = userService.findUserById(id);
			request.setAttribute("user", user);
			request.setAttribute("detailFlag", true);
		} catch (Exception e) {
			logger.error("查询用户异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 验证用户名称不能重复
	 * @return
	 */
	public String validateUserName(){
		try {
			String userName = "";
			if (user!=null) {
				userName = user.getUserName();
			}
			flag = userService.validateUserName(userName);
		} catch (Exception e) {
			logger.error("根据名称查询用户异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 用户添加
	 * @return
	 */
	public String saveUser(){
		try {
			userService.saveUser(user,roleIds);
		} catch (Exception e) {
			logger.error("用户添加异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户编辑页面
	 * @return
	 */
	public String viewUpdateUserPage(){
		try {
			Long id = user.getId();
			user = userService.findUserById(id);
			request.setAttribute("user", user);
			request.setAttribute("detailFlag", false);
		} catch (Exception e) {
			logger.error("查询用户异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户编辑
	 * @return
	 */
	public String updateUser(){
		try {
			userService.updateUser(user,roleIds);
		} catch (Exception e) {
			logger.error("用户编辑异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户启用、禁用
	 * @return
	 */
	public String enabledUserById(){
		try {
			userService.updateUserStatus(user);
		} catch (Exception e) {
			logger.error("用户编辑异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除用户(多个)
	 */
	public String deleteUsers(){
		try {
			userService.deleteUsers(ids);
		} catch (Exception e) {
			logger.error("删除用户(多个)异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改用户密码
	 * @return
	 */
	public String updateUserPassword(){
		try {
			String userName = (String) request.getSession().getAttribute("username");
			flag = userService.validateUserPwd(userName,oldPwd);
			if (!flag) {
				msg = "原密码不正确，请重新填写！";
				return SUCCESS;
			}
			flag = userService.updateUserPassword(userName,newPwd);
			if (!flag) {
				msg = "修改密码失败，请联系管理员！";
				return SUCCESS;
			}
		} catch (Exception e) {
			logger.error("修改用户密码异常",e);
		}
		
		return SUCCESS;
	}
	
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AuthUser getUser() {
		return user;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public PageModel<AuthUser> getPageModel() {
		return pageModel;
	}

	public String getIds() {
		return ids;
	}

	public void setUser(AuthUser user) {
		this.user = user;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public void setPageModel(PageModel<AuthUser> pageModel) {
		this.pageModel = pageModel;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


}
