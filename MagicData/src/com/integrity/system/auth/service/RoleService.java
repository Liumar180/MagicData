package com.integrity.system.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.system.auth.bean.Role;
import com.integrity.system.auth.bean.RolePermissionRel;
import com.integrity.system.auth.bean.UserRoleRel;
import com.integrity.system.auth.dao.RoleDao;

public class RoleService {

	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	/**
	 * 查询角色列表
	 * @param page
	 * @param roleName
	 * @return
	 */
	public PageModel<Role> getRolePageModel(PageModel<Role> page,String roleName) {
		List<Role> lst = roleDao.getRoleList(page,roleName);
		page.setTotalRecords(roleDao.getRowCountByCondition(roleName));
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
	}
	
	/**
	 * 添加角色并关联资源
	 * @param role
	 * @param perIds
	 */
	public void saveRole(Role role,String perIds){
		role.setRoleName(role.getRoleName().trim());
		role.setCreateTime(new Date());
		roleDao.getHibernateTemplate().save(role);
		Long roleId = role.getId();
		saveRolePerRel(roleId, perIds);
	}
	
	/**
	 * 根据id查询角色
	 * @param id
	 * @return
	 */
	public Role findRoleById(Long id) {
		return (Role) roleDao.getHibernateTemplate().get(Role.class, id);
	}
	
	/**
	 * 角色编辑并关联资源
	 * @param role
	 * @param perIds
	 */
	public void updateRole(Role role, String perIds) {
		roleDao.getHibernateTemplate().update(role);
		Long roleId = role.getId();
		roleDao.deleteRolePerRel(roleId);
		saveRolePerRel(roleId, perIds);
	}
	
	/**
	 * 根据ids删除角色
	 * @param ids
	 */
	public boolean deleteRoles(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idarr = ids.split(",");
			//判断有没有关联的用户
			int count = roleDao.findUserRelByRoleids(idarr);
			if (count>0) {
				return false;
			}
			//删除角色
			roleDao.deleteRoleByIds(idarr);
			//删除角色资源关系
			roleDao.deleteRelByRoleIds(idarr);
		}
		return true;
	}
	
	/**
	 * 获取角色树
	 * @param userId
	 * @return
	 */
	public List<Map<String, String>> getRoleTree(Long userId) {
		List<Role> lst = roleDao.getAllRole();
		//获取用户角色关联list
		List<UserRoleRel> relList = roleDao.getRelByUserId(userId);
		List<Long> roleIds = new ArrayList<Long>();
		if (relList != null) {
			for (UserRoleRel rel : relList) {
				roleIds.add(rel.getRoleId());
			}
		}
		List<Map<String,String>> root = new ArrayList<Map<String,String>>();
		for (Role role : lst) {
			Map<String,String> node = new HashMap<String, String>();
			Long roleId = role.getId();
			node.put("pId", "0");
			node.put("id", roleId+"");
			node.put("name", role.getRoleName());
			node.put("description", role.getDescription());
			node.put("icon", "images/tree/roleManage.png");
			if (roleIds.contains(roleId)) {
				node.put("checked", "true");	
			}
			root.add(node);
		}
		return root;
	}
	
	/**
	 * 验证角色名称不能重复
	 * @param roleName
	 * @return
	 */
	public boolean validateRoleName(String roleName) {
		int count = roleDao.getCountByName(roleName);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	
	
	/******************************************************************************************/
	
	/**
	 * 保存角色资源关系
	 * @param roleId
	 * @param perIds
	 */
	private void saveRolePerRel(Long roleId,String perIds){
		if (StringUtils.isNotBlank(perIds)) {
			String[] peridArr = perIds.split(",");
			for (String str : peridArr) {
				Long perId = Long.parseLong(str);
				RolePermissionRel rpr = new RolePermissionRel();
				rpr.setRoleId(roleId);
				rpr.setPerId(perId);
				roleDao.getHibernateTemplate().save(rpr);
			}
		}
	}

	
}
