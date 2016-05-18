package com.integrity.system.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.integrity.system.auth.bean.Permission;
import com.integrity.system.auth.bean.RolePermissionRel;
import com.integrity.system.auth.dao.PermissionDao;

public class PermissionService {

	private PermissionDao permissionDao;

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}
	
	/**
	 * 获取资源树json
	 * @param roleId 
	 * @return
	 * @throws JsonProcessingException
	 */
	public List<Map<String,String>> getPermissionTree(Long roleId) throws JsonProcessingException{
		List<Permission> lst = permissionDao.getAllPermission();
		//获取角色资源关联list
		List<RolePermissionRel> relList = permissionDao.getRelByRoleId(roleId);
		List<Long> perIds = new ArrayList<Long>();
		if (relList != null) {
			for (RolePermissionRel rel : relList) {
				perIds.add(rel.getPerId());
			}
		}
		List<Map<String,String>> root = new ArrayList<Map<String,String>>();
		for (Permission permission : lst) {
			Map<String,String> node = new HashMap<String, String>();
			Long pId = permission.getPid();
			Long perId = permission.getId();
			String per = permission.getPermission();
			node.put("pId", pId+"");
			node.put("id", perId+"");
			node.put("permission", permission.getPermission());
			node.put("name", permission.getPermissionName());
			node.put("icon", "images/tree/"+per+".png");
			if (pId == 0) {
				node.put("open", "true");	
			}
			if (perIds.contains(perId)) {
				node.put("checked", "true");	
			}
			root.add(node);
		}
		return root;
	}
	
}
