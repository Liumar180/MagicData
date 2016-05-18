package com.integrity.lawCase.hostManage.service;

import java.util.List;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.hostManage.bean.DomainsituationObject;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.hostManage.bean.LoopholesObject;
import com.integrity.lawCase.hostManage.dao.HostManageDao;
import com.integrity.lawCase.relation.service.AllRelationService;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;

/**
 * @author liuBf
 *
 */
public class HostManageService{
	private HostManageDao hostManageDao;
	private AllRelationService allRelationService;
	public List<HostsObject> findHostCards(int pageNo, int pageSize,HostsObject hosts) {
		return hostManageDao.findHostCards(pageNo, pageSize,hosts);
	}
	public List<HostsObject> findHostByName(String name,String id) {
		return hostManageDao.findHostByName(name,id);
	}
	/**
	 * 添加主机并关联
	 * @param hostObject 最新添加主机对象
	 * @param rootType 主关联类型
	 * @param rootId 主关联id
	 */
	public void saveHostRelation(HostsObject hostsObject, String rootType,String rootId) {
		Long id = (Long) hostManageDao.getHibernateTemplate().save(hostsObject);
		allRelationService.addOrUpdateRelation(rootId, rootType, id, ConstantManage.HOSTOBJECTTYPE);
	}

	/**
	 * 根据当前节点的id及类型获得所有关联关系
	 * 
	 * @param typeId
	 *            当前节点ID
	 * @param type
	 *            当前节点的类型，类型取自ConstantManage内的类型常量
	 * @return AllRelationWapper包含节点的所有关联信息
	 */

	public AllRelationWapper findRelation(Long id, String type) {
		return allRelationService.getAllRelationForType(id+"", type);
		
	}
	/**
	 * @param rootId
	 * @param rootType
	 * 删除当前对象（主机） 所有关联
	 */
	public void delAllRelactions(String rootIds,String rootType){
		allRelationService.delAllRelationByIds(rootIds, rootType);
	}
	/**
	 * @param rootId
	 * @param rootType
	 * @param relId
	 * @param relType
	 * 删除 单一关联对象
	 */
	public void delSingleRelation(String rootId,String rootType,String relId,String relType){
		allRelationService.delRelation(rootId, rootType, relId, relType);
	}
	public PageModel<HostsObject> searchHostList(PageModel<HostsObject> page,HostsObject hosts){
		List<HostsObject> lst = hostManageDao.searchHostlist(page,hosts);
		if(lst.size() == 0){
			int p = page.getPageNo();
			if(p != 0){
				page.setPageNo(p-1);
			}
			lst = hostManageDao.searchHostlist(page,hosts);
		}
		page.setTotalRecords(hostManageDao.Acounts(hosts));
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
		
	}
	public void saveHosts(HostsObject ho){
		hostManageDao.save(ho);
	}
	public void saveDomain(DomainsituationObject domain){
		hostManageDao.savedomain(domain);
	}
	public void saveLoophole(LoopholesObject loophole){
		hostManageDao.saveLoophole(loophole);
	}
	public void delHosts(List<String> ids){
		hostManageDao.delHosts(ids);
	}
	public void delDomain(Long id){
		hostManageDao.delDomain(id);
	}
	public void delDomainByhostId(List<String> ids){
		hostManageDao.delDomainByhostId(ids);
	}
	public void delLoopholes(Long id){
		hostManageDao.delLoopholes(id);
	}
	public void delLoopholesByhostId(List<String> ids){
		hostManageDao.delLoopholesByhostId(ids);
	}
	public HostsObject forUpdateHosts(Long id){
		return hostManageDao.findDetails(id);
	}
	public void updateHosts(HostsObject ho){
		hostManageDao.update(ho);
	}
	public DomainsituationObject forUpdateDomain(Long id){
		return hostManageDao.findDomainByid(id);
	}
	public void updateDoamins(DomainsituationObject domain){
		hostManageDao.updateDomain(domain);
	}
	public LoopholesObject forUpdateLoopholes(Long id){
		return hostManageDao.findLoopholesByid(id);
	}
	public void updateLoopholes(LoopholesObject loopholes){
		hostManageDao.updateLoopholes(loopholes);
	}
	public HostsObject searchDetails(Long id){
		return hostManageDao.findDetails(id);
	}
	public List<DomainsituationObject> findDmainList(Long hostId){
		return hostManageDao.findDomainList(hostId);
	}
	public List<LoopholesObject> findLoopholesList(Long hostId){
		return hostManageDao.findLoopholesList(hostId);
	}
	public Integer TotalRecords(HostsObject hosts){
		return hostManageDao.Acounts(hosts);
	}
	public void setHostManageDao(HostManageDao hostManageDao) {
		this.hostManageDao = hostManageDao;
	}
	public void setAllRelationService(AllRelationService allRelationService) {
		this.allRelationService = allRelationService;
	}
	
	
}
