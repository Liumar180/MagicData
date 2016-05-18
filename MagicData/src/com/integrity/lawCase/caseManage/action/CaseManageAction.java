/**
 * 
 */
package com.integrity.lawCase.caseManage.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.caseManage.bean.WorkRecord;
import com.integrity.lawCase.caseManage.service.CaseManageService;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.relation.pojo.RelationAction;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;
import com.integrity.lawCase.util.PageSetValueUtil;
import com.integrity.lawCase.util.TransformYears;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class CaseManageAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CaseManageAction.class);
	private CaseManageService caseManageService;
	private UserService userService;
	private CaseObject caseObject;
	private PageModel<CaseObject> pageModel;
	private List<CaseObject> caseList;
	private String ids;
	private WorkRecord record;
	private WorkAllocation allocation;
	private List<WorkRecord> recordList;
	private List<WorkAllocation> allocationList;
	private Object typeObj;
	private List<Object> typeObjects;
	private String rootType;
	private String rootId;
	private String relationType;
	private String relationId;
	private String condition;
	private String conditionValue;
	private String skipFlag;
	private PageModel<WorkRecord> recordPage;
	private PageModel<WorkAllocation> allocationPage;
	Map<String,String> result = null;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private ServletContext sc = ServletActionContext.getServletContext();
	//获取字典表信息
	Map<String,Map<String,String>> allMap = (Map<String, Map<String, String>>) sc.getAttribute(ConstantManage.DATADICTIONARY);
	Map<String,String> direction = allMap.get(ConstantManage.DIRECTION);//方向
	Map<String,String> level = allMap.get(ConstantManage.LEVEL);//等级
	Map<String,String> caseStatus = allMap.get(ConstantManage.CASESTATUS);//状态
	Map<String,String> objectMap = ConstantManage.getObjectMap();
	Map<String,String> type_property = (Map<String, String>) sc.getAttribute(ConstantManage.TYPE_PROPERTY);

	/**
	 * 案件主页面
	 * @return
	 */
	public String caseMangeIndex(){
		return SUCCESS;
	}
	
	/**
	 * 案件管理页面
	 * @return
	 */
	public String viewCaseMange(){
		try {
			List<Integer> years = TransformYears.getYearsBySeveral();
			request.setAttribute("years", years);
			pageModel = new PageModel<>();
			pageModel.setPageNo(1);
			pageModel.setPageSize(50);
			pageModel = caseManageService.getCasePageModel(pageModel);
			casePropertySwitch(pageModel);
			request.setAttribute("pageModel", pageModel);
			
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
		} catch (Exception e) {
			logger.error("查询案件列表异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 案件列表
	 * @return
	 */
	public String findCaseList(){
		try {
			if (caseObject!=null) {
				pageModel = caseManageService.getCasePageModel(pageModel,caseObject);
			}else if (StringUtils.isNotBlank(condition) && StringUtils.isNotBlank(conditionValue)) {
				pageModel = caseManageService.getCasePageModel(pageModel,condition,conditionValue);
			}else {
				pageModel = caseManageService.getCasePageModel(pageModel);
			}
			casePropertySwitch(pageModel);
		} catch (Exception e) {
			logger.error("查询案件列表异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 案件详情页面
	 * @return
	 */
	public String viewCaseDetail(){
		try {
			/*案件基本信息*/
			Long id = caseObject.getId();
			caseObject = caseManageService.findCaseById(id);
			//属性转换
			casePropertySwitch(caseObject);
			request.setAttribute("caseObject", caseObject);
			/*工作记录*/
			recordPage = new PageModel<WorkRecord>();
			recordPage.setPageNo(1);
			recordPage.setPageSize(5);
			recordPage = caseManageService.findWorkRecordByCaseId(id,recordPage);
			request.setAttribute("recordPage", recordPage);
			/*工作配置*/
			allocationPage = new PageModel<WorkAllocation>();
			allocationPage.setPageNo(1);
			allocationPage.setPageSize(5);
			allocationPage = caseManageService.findWorkAllocationByCaseId(id,allocationPage,type_property);
			request.setAttribute("allocationPage", allocationPage);
			/*关联对象*/
			request.setAttribute("rootType", ConstantManage.CASEOBJECTTYPE);
			//跳转链接
			RelationAction relationAction = PageSetValueUtil.relationActionSet(type_property,ConstantManage.CASEOBJECTTYPE,id+"");
			request.setAttribute("relationAction", relationAction);
			//关联对象详细
			AllRelationWapper allRelation = caseManageService.findRelation(id, ConstantManage.CASEOBJECTTYPE);
			request.setAttribute("allRelation", allRelation);
			//关联对象json
			allRelation = clearDesc(allRelation);
			String allRelationJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(allRelation);
			request.setAttribute("allRelationJson", allRelationJson);
			//对象类型
			Map<String, String> typeMap = ConstantManage.getObjectTypeMap();
			request.setAttribute("typeMap", typeMap);
		} catch (Exception e) {
			logger.error("根据id查询案件异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 案件添加页面
	 * @return
	 */
	public String viewCaseAdd(){
		try {
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
			
			//多选下拉框使用
			PageSetValueUtil.selectMoreSet(request,userList,direction);
			
			//关联对象相关
			if(StringUtils.isNotBlank(rootId)){
				request.setAttribute("rootId", rootId);
				request.setAttribute("caseType", ConstantManage.CASEOBJECTTYPE);
				request.setAttribute("rootType", rootType);
			}
		} catch (Exception e) {
			logger.error("查询user列表异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 案件导入页面
	 * @return
	 */
	public String viewCaseImport(){
		try {
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
			
		} catch (Exception e) {
			logger.error("查询user列表异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加案件（关联）
	 */
	public String saveCaseRelation(){
		try {
			caseManageService.saveCaseRelation(caseObject,rootType,rootId);
			//跳转链接
			RelationAction relationAction = PageSetValueUtil.relationActionSet(type_property,rootType,rootId);
			request.setAttribute("relationAction", relationAction);
			request.setAttribute("rootType", rootType);
		} catch (Exception e) {
			logger.error("添加案件(关联)异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 案件详情页面
	 * @return
	 */
	public String caseRelationDetail(){
		try {
			/*案件基本信息*/
			Long id = caseObject.getId();
			caseObject = caseManageService.findCaseById(id);
			//属性转换
			casePropertySwitch(caseObject);
			request.setAttribute("caseObject", caseObject);
		} catch (Exception e) {
			logger.error("根据id查询案件异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加案件
	 */
	public String saveCase(){
		try {
			String caseName = caseObject.getCaseName();
			List<CaseObject> coList = caseManageService.findCaseByNameExact(caseName);
			if(coList!=null&&coList.size()>0){
				result = new HashMap<String,String>();
				result.put("repeatFlag", "true");
				result.put("message", "案件名称已存在，请重新输入！");
			}else{
				result = new HashMap<String,String>();
				result.put("repeatFlag", "false");
				caseManageService.saveCase(caseObject);
			}
		} catch (Exception e) {
			logger.error("添加案件异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据案件名称精确查询是否有同名案件
	 */
	public String findCaseByNameExact(){
		String caseName = caseObject.getCaseName();
		List<CaseObject> coList = caseManageService.findCaseByNameExact(caseName);
		if(coList!=null&&coList.size()>0){
			result = new HashMap<String,String>();
			result.put("repeatFlag", "true");
			result.put("message", "案件名称已存在，请重新输入！");
		}else{
			result = new HashMap<String,String>();
			result.put("repeatFlag", "false");
		}
		return SUCCESS;
	}
	
	/**
	 * 修改案件页面
	 */
	public String viewCaseUpdate(){
		try {
			request.setAttribute("skipFlag", skipFlag);
			Long id = caseObject.getId();
			caseObject = caseManageService.findCaseById(id);
			//属性转换
			casePropertySwitch(caseObject);
			request.setAttribute("caseObject", caseObject);
			
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
			
			//多选下拉框使用
			PageSetValueUtil.selectMoreSet(request,userList,direction);
		} catch (Exception e) {
			logger.error("修改案件页面异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改案件对象
	 */
	public String updateCase(){
		try {
			String caseName = caseObject.getCaseName();
			String caseNameOrg = request.getParameter("caseNameOrg");
			if(caseName!=null&&caseName.equals(caseNameOrg)){
				result = new HashMap<String,String>();
				result.put("repeatFlag", "false");
				caseManageService.updateCase(caseObject);
			}else{
				List<CaseObject> coList = caseManageService.findCaseByNameExact(caseName);
				if(coList!=null&&coList.size()>0){
					result = new HashMap<String,String>();
					result.put("repeatFlag", "true");
					result.put("message", "案件名称已存在，请重新输入！");
				}else{
					result = new HashMap<String,String>();
					result.put("repeatFlag", "false");
					caseManageService.updateCase(caseObject);
				}
			}
		} catch (Exception e) {
			logger.error("修改案件异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除案件对象
	 */
	public String deleteCase(){
		try {
			Long id = caseObject.getId();
			caseManageService.deleteCase(id);
		} catch (Exception e) {
			logger.error("删除案件异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 案件删除关联对象
	 */
	public String deleteCaseRelationObj(){
		try {
			caseManageService.deleteCaseRelationObj(rootId,rootType,relationId,relationType);
		} catch (Exception e) {
			logger.error("案件删除关联对象异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除案件对象(多个)
	 */
	public String deleteCases(){
		try {
			caseManageService.deleteCases(ids);
		} catch (Exception e) {
			logger.error("删除案件对象(多个)异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 工作记录添加页面
	 * @return
	 */
	public String viewWorkRecordAdd(){
		try {
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
			request.setAttribute("record", record);
		} catch (Exception e) {
			logger.error("工作记录添加页面  - 查询user列表异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加记录
	 */
	public String saveRecord(){
		try {
			caseManageService.saveRecord(record);
		} catch (Exception e) {
			logger.error("添加记录异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改记录页面
	 */
	public String viewRecordUpdate(){
		try {
			Long id = record.getId();
			record = caseManageService.findRecordById(id);
			request.setAttribute("record", record);
			
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
		} catch (Exception e) {
			logger.error("修改记录页面异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改记录对象
	 */
	public String updateRecord(){
		try {
			caseManageService.updateRecord(record);
		} catch (Exception e) {
			logger.error("修改记录异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除记录
	 */
	public String deleteWorkRecord(){
		try {
			Long id = record.getId();
			caseManageService.deleteWorkRecord(id);
		} catch (Exception e) {
			logger.error("删除工作记录异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询工作记录列表
	 */
	public String findRecordListPage(){
		try {
			Long id = caseObject.getId();
			recordPage = caseManageService.findWorkRecordByCaseId(id,recordPage);
		} catch (Exception e) {
			logger.error("查询工作记录列表异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询工作配置列表
	 */
	public String findAllocationListPage(){
		try {
			Long id = caseObject.getId();
			allocationPage = caseManageService.findWorkAllocationByCaseId(id,allocationPage,type_property);
		} catch (Exception e) {
			logger.error("查询工作配置列表异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据类型获取对象集合
	 * @return
	 */
	public String findObjectsByType(){
		/*try {
			String type = allocation.getType();
			typeObjects = caseManageService.getObjectsByType(allocation.getCaseId(),type);
		} catch (Exception e) {
			logger.error("根据类型获取对象集合异常",e);
		}*/
		return SUCCESS;
	}
	
	/**
	 * 工作配置添加页面
	 * @return
	 */
	public String viewWorkAllocationAdd(){
		try {
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
			request.setAttribute("objectMap", objectMap);
			request.setAttribute("type_property", type_property);
			String type_propertyJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(type_property);
			request.setAttribute("type_propertyJson", type_propertyJson);
			request.setAttribute("allocation", allocation);
			
			//新增是默认选择案件对象
			String type = ConstantManage.CASEOBJECTTYPE;
			request.setAttribute("typePro", type_property.get(type));
//			AllRelationWapper arWapper = caseManageService.findRelation(allocation.getCaseId(),type);
//			request.setAttribute("allRelation", arWapper);
			
		} catch (Exception e) {
			logger.error("工作配置添加页面 - 查询user列表异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加配置
	 */
	public String saveAllocation(){
		try {
			caseManageService.saveAllocation(allocation);
		} catch (Exception e) {
			logger.error("添加配置异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改配置页面
	 */
	public String viewAllocationUpdate(){
		try {
			Long id = allocation.getId();
			allocation = caseManageService.findAllocationById(id);
			request.setAttribute("allocation", allocation);
			
			request.setAttribute("objectMap", objectMap);
			
			String type = allocation.getType();
			
//			AllRelationWapper arWapper = caseManageService.findRelation(allocation.getCaseId(),type);
//			request.setAttribute("allRelation", arWapper);
			
			request.setAttribute("typePro", type_property.get(type));
			request.setAttribute("type_property", type_property);
			String type_propertyJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(type_property);
			request.setAttribute("type_propertyJson", type_propertyJson);
			
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
		} catch (Exception e) {
			logger.error("修改配置页面异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改配置对象
	 */
	public String updateAllocation(){
		try {
			caseManageService.updateAllocation(allocation);
		} catch (Exception e) {
			logger.error("修改配置异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除配置
	 */
	public String deleteWorkAllocation(){
		try {
			Long id = allocation.getId();
			caseManageService.deleteWorkAllocation(id);
		} catch (Exception e) {
			logger.error("删除工作配置异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据name模糊查询案件
	 */
	public String findCaseByName(){
		try {
			String name = caseObject.getCaseName();
			Long id = caseObject.getId();
			caseList = caseManageService.findCaseByName(name,id);
		} catch (Exception e) {
			logger.error("修改配置异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 案件对象属性转换(分页对象)
	 * @param caseObject
	 */
	private void casePropertySwitch(PageModel<CaseObject> pageModel){
		List<CaseObject> lst = pageModel.getList();
		if (lst != null && lst.size() > 0) {
			for (CaseObject caseObject : lst) {
				//属性转换
				casePropertySwitch(caseObject);
			}
		}
	}
	
	/**
	 * 案件对象属性转换
	 * @param caseObject
	 */
	private void casePropertySwitch(CaseObject caseObject){
		caseObject.setCaseLevelName(level.get(caseObject.getCaseLevel()));
		caseObject.setCaseStatusName(caseStatus.get(caseObject.getCaseStatus()));
		String direct = caseObject.getDirectionCode();
		StringBuffer temp = new StringBuffer();
		if (StringUtils.isNotBlank(direct)) {
			String[] arr = direct.split(",");
			for (String code : arr) {
				temp.append(direction.get(code)+",");
			}
			String directNames = temp.toString();
			caseObject.setDirectionName(directNames.substring(0,directNames.length()-1));
		}
	}
	
	/**
	 * 去除关联对象的描述信息
	 * 主要是去除影响前台json转换的暂时无用字段
	 * @param allRelation
	 * @return
	 */
	private AllRelationWapper clearDesc(AllRelationWapper allRelation) {
		List<CaseObject> cases = allRelation.getCaseList();
		if(CollectionUtils.isNotEmpty(cases)){
			for (CaseObject caseObject : cases) {
				caseObject.setCaseAim("");
				caseObject.setMemo("");
			}
		}
		List<Organizationobject> orgs = allRelation.getOrganList();
		if (CollectionUtils.isNotEmpty(orgs)) {
			for (Organizationobject org : orgs) {
				org.setOrgRemark("");
				org.setOrgDescription("");
			}
		}
		List<Peopleobject> peoples = allRelation.getPeopleList();
		if (CollectionUtils.isNotEmpty(peoples)) {
			for (Peopleobject people : peoples) {
				people.setPodescription("");
			}
		}
		List<HostsObject> hosts = allRelation.getHostList();
		if (CollectionUtils.isNotEmpty(hosts)) {
			for (HostsObject host : hosts) {
				host.setInstallationService("");
				host.setDescriptionContents("");
			}
		}
		return allRelation;
	}

	public PageModel<CaseObject> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<CaseObject> pageModel) {
		this.pageModel = pageModel;
	}

	public void setCaseManageService(CaseManageService caseManageService) {
		this.caseManageService = caseManageService;
	}

	public CaseObject getCaseObject() {
		return caseObject;
	}

	public void setCaseObject(CaseObject caseObject) {
		this.caseObject = caseObject;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public WorkRecord getRecord() {
		return record;
	}

	public WorkAllocation getAllocation() {
		return allocation;
	}

	public void setRecord(WorkRecord record) {
		this.record = record;
	}

	public void setAllocation(WorkAllocation allocation) {
		this.allocation = allocation;
	}

	public List<WorkRecord> getRecordList() {
		return recordList;
	}

	public List<WorkAllocation> getAllocationList() {
		return allocationList;
	}

	public void setRecordList(List<WorkRecord> recordList) {
		this.recordList = recordList;
	}

	public void setAllocationList(List<WorkAllocation> allocationList) {
		this.allocationList = allocationList;
	}

	public Object getTypeObj() {
		return typeObj;
	}

	public void setTypeObj(Object typeObj) {
		this.typeObj = typeObj;
	}

	public List<Object> getTypeObjects() {
		return typeObjects;
	}

	public void setTypeObjects(List<Object> typeObjects) {
		this.typeObjects = typeObjects;
	}

	public List<CaseObject> getCaseList() {
		return caseList;
	}

	public void setCaseList(List<CaseObject> caseList) {
		this.caseList = caseList;
	}

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getRelationType() {
		return relationType;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getCondition() {
		return condition;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public String getSkipFlag() {
		return skipFlag;
	}

	public void setSkipFlag(String skipFlag) {
		this.skipFlag = skipFlag;
	}

	public PageModel<WorkRecord> getRecordPage() {
		return recordPage;
	}

	public PageModel<WorkAllocation> getAllocationPage() {
		return allocationPage;
	}

	public void setRecordPage(PageModel<WorkRecord> recordPage) {
		this.recordPage = recordPage;
	}

	public void setAllocationPage(PageModel<WorkAllocation> allocationPage) {
		this.allocationPage = allocationPage;
	}

	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}
	
}
