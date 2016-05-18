package com.integrity.lawCase.caseManage.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.util.DateFormat;
import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.caseManage.bean.WorkRecord;
import com.integrity.lawCase.caseManage.dao.CaseManageDao;
import com.integrity.lawCase.caseManage.pojo.WorkAllocationPojo;
import com.integrity.lawCase.caseManage.pojo.WorkRecordPojo;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.relation.service.AllRelationService;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;
import com.integrity.lawCase.util.TransformYears;

public class CaseManageService {

	private CaseManageDao caseManageDao;
	private AllRelationService allRelationService;
	//日期格式化串
	public static final String DATEFORMATSTR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATEFORMATSTR_SHORT = "yyyy-MM-dd";

	public void setCaseManageDao(CaseManageDao caseManageDao) {
		this.caseManageDao = caseManageDao;
	}
	
	public void setAllRelationService(AllRelationService allRelationService) {
		this.allRelationService = allRelationService;
	}


	/**
	 * 根据类型、id获取对象
	 * @param type
	 * @param typeId
	 * @return
	 */
	public Object getObjectByTypeId(String type,String typeId){
		Long id = Long.parseLong(typeId);
		Object obj = null;
		if(ConstantManage.CASEOBJECTTYPE.equals(type)){
			obj = findCaseById(id);
		}else if(ConstantManage.FILEOBJECTTYPE.equals(type)){
			obj = caseManageDao.getHibernateTemplate().get(FilesObject.class, id);
		}else if(ConstantManage.HOSTOBJECTTYPE.equals(type)){
			obj = caseManageDao.getHibernateTemplate().get(HostsObject.class, id);
		}else if(ConstantManage.ORGANIZATIONOBJECTTYPE.equals(type)){
			obj = caseManageDao.getHibernateTemplate().get(Organizationobject.class, id);
		}else if(ConstantManage.PEOPLEOBJECTTYPE.equals(type)){
			obj = caseManageDao.getHibernateTemplate().get(Peopleobject.class, id);
		}else{
			obj = null;
		}
		return obj;
	}
	
	/**
	 * 根据类型获取对象集合
	 * @param type
	 * @return
	 */
	public List<Object> getObjectsByType(String type){
		List<Object> objs = null;
		if(ConstantManage.CASEOBJECTTYPE.equals(type)){
			objs = caseManageDao.findCases();
		}else if(ConstantManage.FILEOBJECTTYPE.equals(type)){
			objs = caseManageDao.findFiles();
		}else if(ConstantManage.HOSTOBJECTTYPE.equals(type)){
			objs = caseManageDao.findHosts();
		}else if(ConstantManage.ORGANIZATIONOBJECTTYPE.equals(type)){
			objs = caseManageDao.findOrgs();
		}else if(ConstantManage.PEOPLEOBJECTTYPE.equals(type)){
			objs = caseManageDao.findPeoples();
		}else{
			objs = null;
		}
		return objs;
	}

	/**
	 * 获取分页VO
	 * @param page
	 * @return
	 */
	public PageModel<CaseObject> getCasePageModel(PageModel<CaseObject> page) {
		List<CaseObject> lst = caseManageDao.getCaseList(page);
		if(lst.size() == 0){
			int p = page.getPageNo();
			if(p != 0){
				page.setPageNo(p-1);
			}
			lst = caseManageDao.getCaseList(page);
		}
		for (CaseObject caseObject : lst) {
			caseObject.setDateStr(DateFormat.transferDate2String(caseObject.getCreateTime(), DATEFORMATSTR_SHORT));
		}
		page.setTotalRecords(caseManageDao.getRowCount());
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
	}
	
	/**
	 * 获取分页VO（卡片带条件）
	 * @param page
	 * @return
	 */
	public PageModel<CaseObject> getCasePageModel(PageModel<CaseObject> page, CaseObject caseObject) {
		String direction = caseObject.getDirectionCode();
		String year = caseObject.getBy1();
		Date start = null;
		Date end = null;
		if (StringUtils.isNotBlank(year)) {
			start = TransformYears.getYearStartTime(Integer.parseInt(year));
			end = TransformYears.getYearEndTime(Integer.parseInt(year));
		}
		String level = caseObject.getCaseLevel();
		List<CaseObject> lst = caseManageDao.getCaseListByCondition(page,direction,level,start,end);
		page.setTotalRecords(caseManageDao.getRowCountByCondition(direction,level,start,end));
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
	}
	
	/**
	 * 获取分页VO（列表带条件）
	 * @param pageModel
	 * @param condition
	 * @param conditionValue
	 * @return
	 */
	public PageModel<CaseObject> getCasePageModel(PageModel<CaseObject> page, String condition,String conditionValue) {
		List<CaseObject> lst = caseManageDao.getCaseListByCondition(page,condition,conditionValue);
		page.setTotalRecords(caseManageDao.getRowCountByCondition(condition,conditionValue));
		page.setTotalPage(page.getTotalPages());
		for (CaseObject caseObject : lst) {
			caseObject.setDateStr(DateFormat.transferDate2String(caseObject.getCreateTime(), DATEFORMATSTR_SHORT));
		}
		page.setList(lst);
		return page;
	}

	/**
	 * 根据id查询案件
	 * @param caseObject
	 */
	public CaseObject findCaseById(Long id) {
		CaseObject caseObject = caseManageDao.findCaseById(id);
		if (caseObject != null) {
			caseObject.setDateStr(DateFormat.transferDate2String(caseObject.getCreateTime(), DATEFORMATSTR_SHORT));
		}
		return caseObject;
	}

	/**
	 * 添加案件
	 * @param caseObject
	 */
	public void saveCase(CaseObject caseObject) {
		caseNameTrim(caseObject);
		caseManageDao.getHibernateTemplate().save(caseObject);
	}

	/**
	 * 修改案件
	 * @param caseObject
	 */
	public void updateCase(CaseObject caseObject) {
		caseNameTrim(caseObject);
		caseManageDao.getHibernateTemplate().update(caseObject);
	}

	/**
	 * 根据id删除案件
	 * @param id
	 */
	public void deleteCase(Long id) {
		caseManageDao.deleteCase(id);
		allRelationService.delAllRelationById(id+"", ConstantManage.CASEOBJECTTYPE);
	}

	/**
	 * 删除案件多个
	 * @param ids
	 */
	public void deleteCases(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idarr = ids.split(",");
			caseManageDao.deleteCaseByIds(idarr);
			allRelationService.delAllRelationByIds(ids, ConstantManage.CASEOBJECTTYPE);
		}
	}

	/**
	 * 根据案件id查询工作记录
	 * @param recordPage 
	 * @param id
	 * @return
	 */
	public PageModel<WorkRecord> findWorkRecordByCaseId(Long caseId, PageModel<WorkRecord> recordPage) {
		List<WorkRecord> list = caseManageDao.findWorkRecordByCaseId(caseId,recordPage);
		for (WorkRecord workRecord : list) {
			workRecord.setDateStr(DateFormat.transferDate2String(workRecord.getCreateTime(), DATEFORMATSTR));
		}
		recordPage.setTotalRecords(caseManageDao.getWorkRecordRowCount(caseId));
		recordPage.setTotalPage(recordPage.getTotalPages());
		recordPage.setList(list);
		return recordPage;
	}
	
	public List<WorkRecordPojo> findWorkRecordByCaseIdExcel(Long caseId,PageModel<WorkRecord> recordPage){
		List<WorkRecord> list = caseManageDao.findWorkRecordByCaseId(caseId, recordPage.getPageNo(), recordPage.getPageSize());
		List<WorkRecordPojo> listPojo = new ArrayList<WorkRecordPojo>();
		listPojo = transferToWorkRecordPojoList(list);
		return listPojo;
	}
	
	/**
	 * 转换工作记录list为pojolist
	 * @param list
	 * @return
	 */
	public List<WorkRecordPojo> transferToWorkRecordPojoList(List<WorkRecord> list){
		List<WorkRecordPojo> listPojo = new ArrayList<WorkRecordPojo>();
		for(WorkRecord workRecord : list){
			WorkRecordPojo wrp = new WorkRecordPojo();
			wrp.setTitle(workRecord.getTitle());
			wrp.setConent(workRecord.getConent());
			wrp.setDateStr(DateFormat.transferDate2String(workRecord.getCreateTime(), DATEFORMATSTR));
			listPojo.add(wrp);
		}
		return listPojo;
	}
	
	public List<WorkAllocation> assembleAllocationList(List<WorkAllocation> lst, Map<String, String> type_property){
		Map<String,String> objectMap = ConstantManage.getObjectMap();
		for (WorkAllocation allocation : lst) {
			allocation.setDateStr(DateFormat.transferDate2String(allocation.getCreateTime(), DATEFORMATSTR));
			String type = allocation.getType();
			allocation.setTypeName(objectMap.get(type));
			String proName = type_property.get(type);
			Object obj= getObjectByTypeId(type, allocation.getObjectId()+"");
			//设置配置对象的主要属性值
			try {
				Class clazz = obj.getClass();  
				PropertyDescriptor pd = new PropertyDescriptor(proName, clazz);  
				Method getMethod = pd.getReadMethod();//获得get方法  
				Object o = getMethod.invoke(obj);//执行get方法返回一个Object  
				allocation.setMainValue(o.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return lst;
	}

	/**
	 * 根据案件id查询工作配置
	 * @param allocationPage 
	 * @param type_property 
	 * @param id
	 * @return
	 */
	public PageModel<WorkAllocation> findWorkAllocationByCaseId(Long caseId, PageModel<WorkAllocation> allocationPage, Map<String, String> type_property) {
		List<WorkAllocation> lst = caseManageDao.findWorkAllocationByCaseId(caseId,allocationPage);
		lst = assembleAllocationList(lst,type_property);
		allocationPage.setTotalRecords(caseManageDao.getWorkAllocationRowCount(caseId));
		allocationPage.setTotalPage(allocationPage.getTotalPages());
		allocationPage.setList(lst);
		return allocationPage;
	}
	
	public List<WorkAllocationPojo> findAllocationByCaseIdExcel(Long caseId,PageModel<WorkAllocation> allocationPage,Map<String, String> type_property){
		List<WorkAllocation> list = caseManageDao.findAllocationByCaseId(caseId, allocationPage.getPageNo(), allocationPage.getPageSize());
		list = assembleAllocationList(list,type_property);
		List<WorkAllocationPojo> pojoList = transferToWorkAllocationPojoList(list);
		return pojoList;
	}
	
	public List<WorkAllocationPojo> transferToWorkAllocationPojoList(List<WorkAllocation> list){
		List<WorkAllocationPojo> pojoList = new ArrayList<WorkAllocationPojo>();
		for(WorkAllocation wa : list){
			WorkAllocationPojo wap = new WorkAllocationPojo();
			wap.setTypeName(wa.getTypeName());
			wap.setMainValue(wa.getMainValue());
			wap.setAllocation(wa.getAllocation());
			wap.setPrincipal(wa.getPrincipal());
			wap.setDateStr(wa.getDateStr());
			wap.setResult(wa.getResult());
			wap.setMemo(wa.getMemo());
			pojoList.add(wap);
		}
		return pojoList;
	}

	/**
	 * 保存记录
	 * @param record
	 */
	public void saveRecord(WorkRecord record) {
		record.setCreateTime(new Date());
		caseManageDao.getHibernateTemplate().save(record);
	}

	/**
	 * 根据记录id查询记录
	 * @param id
	 * @return
	 */
	public WorkRecord findRecordById(Long id) {
		return caseManageDao.findRecordById(id);
	}
	
	/**
	 * 修改记录
	 * @param record
	 */
	public void updateRecord(WorkRecord record) {
		caseManageDao.getHibernateTemplate().update(record);
	}
	
	/**
	 * 根据id删除工作记录
	 * @param id
	 */
	public void deleteWorkRecord(Long id) {
		caseManageDao.deleteWorkRecord(id);
	}

	/**
	 * 保存配置
	 */
	public void saveAllocation(WorkAllocation allocation) {
		allocation.setCreateTime(new Date());
		caseManageDao.getHibernateTemplate().save(allocation);
	}

	/**
	 * 根据配置id查询配置
	 * @param id
	 * @return
	 */
	public WorkAllocation findAllocationById(Long id) {
		return caseManageDao.findAllocationById(id);
	}

	/**
	 * 修改配置
	 * @param allocation
	 */
	public void updateAllocation(WorkAllocation allocation) {
		caseManageDao.getHibernateTemplate().update(allocation);
	}
	
	/**
	 * 根据id删除工作配置
	 * @param id
	 */
	public void deleteWorkAllocation(Long id) {
		caseManageDao.deleteWorkAllocation(id);
	}

	/**
	 * 根据name模糊查询案件
	 * @param name
	 * @param id 自身id，用于查询去除自身
	 * @return
	 */
	public List<CaseObject> findCaseByName(String name, Long id) {
		return caseManageDao.findCaseByName(id,name);
	}
	
	/**
	 * 根据name精确查询案件
	 * @param name
	 * @param id 自身id，用于查询去除自身
	 * @return
	 */
	public List<CaseObject> findCaseByNameExact(String name) {
		return caseManageDao.findCaseByName(name);
	}

	/**
	 * 添加案件并关联
	 * @param caseObject
	 * @param rootType 主关联类型
	 * @param rootId 主关联id
	 */
	public void saveCaseRelation(CaseObject caseObject, String rootType,String rootId) {
		Long id = (Long) caseManageDao.getHibernateTemplate().save(caseObject);
		allRelationService.addOrUpdateRelation(rootId, rootType, id, ConstantManage.CASEOBJECTTYPE);
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
	 * 案件删除关联对象
	 * @param rootId
	 * @param rootType
	 * @param relationId
	 * @param relationType
	 */
	public void deleteCaseRelationObj(String rootId, String rootType,
			String relationId, String relationType) {
		allRelationService.delRelation(rootId, rootType, relationId, relationType);
	}
	
	/**
	 * 去除名称首尾空格
	 * @param caseObject
	 */
	private void caseNameTrim(CaseObject caseObject){
		caseObject.setCaseName(caseObject.getCaseName().trim());
	}

}
