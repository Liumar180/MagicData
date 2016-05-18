package com.integrity.system.auth.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.system.auth.bean.Role;
import com.integrity.system.auth.service.RoleService;
import com.opensymphony.xwork2.ActionSupport;

public class RoleAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(RoleAction.class);
	private RoleService roleService;
	private Role role;
	private String perIds;
	private PageModel<Role> pageModel;
	private String ids;
	private boolean flag;
	private List<Map<String,String>> treeData;
	private Long userId;
	private HttpServletRequest request = ServletActionContext.getRequest();
	
	/**
	 * 角色管理页面
	 * @return
	 */
	public String viewRolePage(){
		return SUCCESS;
	}
	
	/**
	 * 角色添加页面
	 * @return
	 */
	public String viewAddRolePage(){
		return SUCCESS;
	}
	
	/**
	 * 角色列表
	 * @return
	 */
	public String findRoleList(){
		try {
			String roleName = "";
			if (role!=null) {
				roleName = role.getRoleName();
			}
			pageModel = roleService.getRolePageModel(pageModel,roleName);
		} catch (Exception e) {
			logger.error("查询角色列表异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 角色详细页面
	 * @return
	 */
	public String viewDetailRolePage(){
		try {
			Long id = role.getId();
			role = roleService.findRoleById(id);
			request.setAttribute("role", role);
			request.setAttribute("detailFlag", true);
		} catch (Exception e) {
			logger.error("查询角色异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 验证角色名称不能重复
	 * @return
	 */
	public String validateRoleName(){
		try {
			String roleName = "";
			if (role!=null) {
				roleName = role.getRoleName();
			}
			flag = roleService.validateRoleName(roleName);
		} catch (Exception e) {
			logger.error("根据名称查询角色异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 角色添加
	 * @return
	 */
	public String saveRole(){
		try {
			roleService.saveRole(role,perIds);
		} catch (Exception e) {
			logger.error("角色添加异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 角色编辑页面
	 * @return
	 */
	public String viewUpdateRolePage(){
		try {
			Long id = role.getId();
			role = roleService.findRoleById(id);
			request.setAttribute("role", role);
		} catch (Exception e) {
			logger.error("查询角色异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 角色编辑
	 * @return
	 */
	public String updateRole(){
		try {
			roleService.updateRole(role,perIds);
		} catch (Exception e) {
			logger.error("角色编辑异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除角色(多个)
	 */
	public String deleteRoles(){
		try {
			flag = roleService.deleteRoles(ids);
		} catch (Exception e) {
			logger.error("删除角色(多个)异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取角色树
	 * @return
	 */
	public String loadRoleTree(){
		try {
			treeData = roleService.getRoleTree(userId);
		} catch (Exception e) {
			logger.error("获取资源树异常",e);
		}
		return SUCCESS;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPerIds() {
		return perIds;
	}

	public void setPerIds(String perIds) {
		this.perIds = perIds;
	}

	public PageModel<Role> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<Role> pageModel) {
		this.pageModel = pageModel;
	}

	public String getIds() {
		return ids;
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

	public List<Map<String, String>> getTreeData() {
		return treeData;
	}

	public Long getUserId() {
		return userId;
	}

	public void setTreeData(List<Map<String, String>> treeData) {
		this.treeData = treeData;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
}
