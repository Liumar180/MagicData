package com.integrity.lawCase.organizationManage.service;

import org.apache.commons.lang.StringUtils;

import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.organizationManage.dao.OrganizationManageDao;
import com.integrity.lawCase.relation.service.AllRelationService;

/**
 * 组织信息编辑Service(添加、修改、删除)
 * @author HanXue
 */
public class OrgEditService {
	
	/**组织数据DAO*/
	private OrganizationManageDao orgDao;
	/**关联关系Service*/
	private AllRelationService allRelationService;

	/**
	 * 根据id删除组织及其关联关系
	 * @param id
	 */
	public void deleteOrg(Long id) {
		orgDao.deleteOrg(id);
		allRelationService.delAllRelationById(id+"", ConstantManage.ORGANIZATIONOBJECTTYPE);
	}

	/**
	 * 删除多个组织及其关联关系
	 * @param ids
	 */
	public void deleteOrgs(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idarr = ids.split(",");
			orgDao.deteleOrgByIds(idarr);
			allRelationService.delAllRelationByIds(ids, ConstantManage.ORGANIZATIONOBJECTTYPE);
		}
	}

	/**
	 * 添加组织
	 */
	public void saveOrg(Organizationobject orgObj) {
		orgObj.setOrgCName(orgObj.getOrgCName().trim());
		orgDao.getHibernateTemplate().save(orgObj);
	}

	/**
	 * 修改组织
	 */
	public void updateOrg(Organizationobject orgObj) {
		orgObj.setOrgCName(orgObj.getOrgCName().trim());
		orgDao.getHibernateTemplate().update(orgObj);
	}

	/**
	 * 添加组织并关联
	 */
	public void saveOrgRelation(Organizationobject orgObj, String rootType,String rootId) {
		Long id = (Long) orgDao.getHibernateTemplate().save(orgObj);
		allRelationService.addOrUpdateRelation(rootId, rootType, id, ConstantManage.ORGANIZATIONOBJECTTYPE);
	}
	
	public OrganizationManageDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrganizationManageDao orgDao) {
		this.orgDao = orgDao;
	}

	public void setAllRelationService(AllRelationService allRelationService) {
		this.allRelationService = allRelationService;
	}
	
}
