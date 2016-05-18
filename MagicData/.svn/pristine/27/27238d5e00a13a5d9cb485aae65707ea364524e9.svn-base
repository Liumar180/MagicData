package com.integrity.lawCase.peopleManage.service;

import java.util.ArrayList;
import java.util.List;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.peopleManage.bean.Documentnumberobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.peopleManage.bean.Peoplevirtualobject;
import com.integrity.lawCase.peopleManage.bean.Phonenumberobject;
import com.integrity.lawCase.peopleManage.dao.PeopleManageDao;
import com.integrity.lawCase.relation.service.AllRelationService;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;



public class PeopleManageService{
	private PeopleManageDao peopleManageDao;
	private AllRelationService allRelationService;
	
	public AllRelationService getAllRelationService() {
		return allRelationService;
	}
	public void setAllRelationService(AllRelationService allRelationService) {
		this.allRelationService = allRelationService;
	}
	public PeopleManageDao getPeopleManageDao() {
		return peopleManageDao;
	}
	public void setPeopleManageDao(PeopleManageDao peopleManageDao) {
		this.peopleManageDao = peopleManageDao;
	}
	public void save(Peopleobject peopleobject) {
		peopleManageDao.save(peopleobject);
	}
	public Long saveR(Peopleobject peopleobject) {
		return (Long)peopleManageDao.getHibernateTemplate().save(peopleobject);
	}
	public void edit(Peopleobject peopleobject) {
		peopleManageDao.edit(peopleobject);
	}
	public void delete(long id) {
		peopleManageDao.delete(id);
	}
	public void savePV(Peoplevirtualobject pv) {
		peopleManageDao.savePV(pv);
	}
	public void editPV(Peoplevirtualobject pv) {
		peopleManageDao.editPV(pv);
	}
	public void deletePV(long id) {
		peopleManageDao.deletePV(id);
	}
	public void saveDN(Documentnumberobject pv) {
		peopleManageDao.saveDN(pv);
	}
	public void editDN(Documentnumberobject pv) {
		peopleManageDao.editDN(pv);
	}
	public void deleteDN(long id) {
		peopleManageDao.deleteDN(id);
	}
	public void savePN(Phonenumberobject pv) {
		peopleManageDao.savePN(pv);
	}
	public void editPN(Phonenumberobject pv) {
		peopleManageDao.editPN(pv);
	}
	public void deletePN(long id) {
		peopleManageDao.deletePN(id);
	}
	@SuppressWarnings("rawtypes")
	public List findPeopleCardList(int pageNo,int pageSize,Peopleobject peopleobject) {
		return peopleManageDao.findPeopleCardList(pageNo,pageSize,peopleobject);
	}
	public Peopleobject getPeopleinfoByid(long id){
		return peopleManageDao.getPeopleinfoByid(id);
	}
	public Peoplevirtualobject getPVinfoByid(long id){
		return peopleManageDao.getPVinfoByid(id);
	}
	public List <Peoplevirtualobject>getPVinfoByPoid(long id){
		return peopleManageDao.getPVinfoByPoid(id);
	}
	public Documentnumberobject getDNinfoByid(long id){
		return peopleManageDao.getDNinfoByid(id);
	}
	public List <Documentnumberobject>getDNinfoByPoid(long id){
		return peopleManageDao.getDNinfoByPoid(id);
	}
	public Phonenumberobject getPNinfoByid(long id){
		return peopleManageDao.getPNinfoByid(id);
	}
	public List <Phonenumberobject>getPNinfoByPoid(long id){
		return peopleManageDao.getPNinfoByPoid(id);
	}
	/**
	 * 获取分页VO
	 * @param page
	 * @return
	 */
	public PageModel<Peopleobject> findPeoplePageModel(PageModel<Peopleobject> page, Peopleobject p) {
		List<Peopleobject> lst = peopleManageDao.findPeopleList(page, p);
		page.setTotalRecords(peopleManageDao.findRowCount(p));
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
	}
	
	public int findLastpageCardlist(int pageSize,Peopleobject peopleobject){
		int countPeople=peopleManageDao.findRowCount(peopleobject);
		return countPeople/pageSize;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> searchPname(long id,String name){
		List namList=new ArrayList<String>();
		for (Peopleobject p : peopleManageDao.searchPname(id,name)) {
			namList.add(p.getId()+";"+p.getPocnname());
		}
		return namList;
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
	
	public void savePeopleRelation(String rootId, String rootType,long id) {
		allRelationService.addOrUpdateRelation(rootId, rootType, id, ConstantManage.PEOPLEOBJECTTYPE);
	}
	/**
	 * 根据案件id查询工作配置
	 * @param id
	 * @return
	 */
	public List<WorkAllocation> findWorkAllocationByCaseId(Long caseId) {
		return peopleManageDao.findWorkAllocationByCaseId(caseId);
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
}
