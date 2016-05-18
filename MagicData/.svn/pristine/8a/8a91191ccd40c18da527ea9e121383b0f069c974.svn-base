package com.integrity.lawCase.organizationManage.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.organizationManage.dao.OrganizationManageDao;
import com.integrity.lawCase.organizationManage.wapper.OrgSearchPreWapper;
import com.integrity.lawCase.util.TransformYears;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.service.UserService;

/**
 * 组织信息查询Service
 * @author HanXue
 */
public class OrgInfoManageService{
	
	/**组织查询条件包装类OrgSearchConWapper的类型-卡片类查询条件*/
	public static final String SEARCH_CONDITON_CARD = "orgCard";
	/**组织查询条件包装类OrgSearchConWapper的类型-列表类查询条件*/
	public static final String SEARCH_CONDITON_LIST = "orgList";
	
	
	/**组织数据DAO*/
	private OrganizationManageDao orgDao;
	/**用户Service*/
	private UserService userService;
		
	/**
	 * 获得组织列表查询条件
	 * @param searchType 查询条件类型 取自OrganizationManageService中定义的常量
	 * @return
	 */
	public OrgSearchPreWapper getSearchCondition(String searchType,Map<String,Map<String,String>> dicMap){
		OrgSearchPreWapper orgSWapper = new OrgSearchPreWapper();
		orgSWapper.setWapperType(searchType);
		Map<String,String> listConditionMap = new LinkedHashMap<String,String>();
		listConditionMap.put("orgCName", "组织名称");
		listConditionMap.put("orgEName", "英文名称");
		listConditionMap.put("orgDirectionCodes", "所属方向");
		listConditionMap.put("orgControlStatus", "控制状态");
		listConditionMap.put("orgImportLevel", "重要等级");
		listConditionMap.put("orgDutyPersonIds", "负责人");
		orgSWapper.setListConditionMap(listConditionMap);
		if(null!=dicMap){
			Map<String,String> directionMap = dicMap.get(ConstantManage.DIRECTION);//所属方向
			Map<String,String> controlStatusMap = dicMap.get(ConstantManage.CONTROLSTATUS);//控制状态
			Map<String,String> levelMap = dicMap.get(ConstantManage.LEVEL);//等级
			Map<String,Map<String,String>> listCondSubMap = new LinkedHashMap<String,Map<String,String>>();
			listCondSubMap.put("orgCName", null);
			listCondSubMap.put("orgEName", null);
			listCondSubMap.put("orgDirectionCodes", directionMap);
			listCondSubMap.put("orgControlStatus", controlStatusMap);
			listCondSubMap.put("orgImportLevel", levelMap);
			listCondSubMap.put("orgDutyPersonIds", null);
			orgSWapper.setListSubConditionMap(listCondSubMap);
		}
		Map<String,String> cardConditionMap = new LinkedHashMap<String,String>();
		cardConditionMap.put(ConstantManage.DIRECTION,"按方向");
		cardConditionMap.put(ConstantManage.YEAR,"按年份");
		cardConditionMap.put(ConstantManage.LEVEL,"按级别");
		orgSWapper.setCardConditionMap(cardConditionMap);
		Map<String,String> directionMap = dicMap.get(ConstantManage.DIRECTION);//方向
		Map<String,String> levelMap = dicMap.get(ConstantManage.LEVEL);//等级
		Map<String,Map<String,String>> cardCondSubMap = new LinkedHashMap<String,Map<String,String>>();
		cardCondSubMap.put(ConstantManage.DIRECTION, directionMap);
		cardCondSubMap.put(ConstantManage.LEVEL, levelMap);
		List<Integer> yearList = TransformYears.getYearsBySeveral();
		Map<String,String> yearMap = new HashMap<String,String>();
		for(Integer yearInt:yearList){
			yearMap.put(yearInt.toString(), yearInt.toString());
		}
		cardCondSubMap.put(ConstantManage.YEAR, yearMap);
		orgSWapper.setCardSubConditionMap(cardCondSubMap);
		return orgSWapper;
	}
	
	/**
	 * 获得卡片、列表、条件卡片及条件列表的分页列表
	 * @param searchType
	 * @param page
	 * @param condMap
	 * @return
	 */
	public PageModel<Organizationobject> getOrgPageModel(String searchType,PageModel<Organizationobject> page,Map<String,String> condMap){
		if(null!=searchType){
			Integer pageCount = 0;
			if(null==condMap||condMap.size()<1){
				//无查询条件
				List<Organizationobject> lst = orgDao.getOrgList(page);
				pageCount = orgDao.getRowCount();
				page.setList(lst);
			}else{
				//有查询条件
				if(OrgInfoManageService.SEARCH_CONDITON_LIST.equals(searchType.trim())){
					//列表查询
					Entry<String,String> condEntry = condMap.entrySet().iterator().next();
					String condKey = condEntry.getKey();
					String condValue = condEntry.getValue();
					List<Organizationobject> lst = orgDao.getOrgListByCondition(page, condKey, condValue);
					pageCount = orgDao.getOrgListCountByCond(condKey,condValue);
					page.setList(lst);
				}else if(OrgInfoManageService.SEARCH_CONDITON_CARD.equals(searchType.trim())){
					String direction = condMap.get(ConstantManage.DIRECTION);
					String level = condMap.get(ConstantManage.LEVEL);
					String year = condMap.get(ConstantManage.YEAR);
					Date start = null;
					Date end = null;
					if (StringUtils.isNotBlank(year)) {
						start = TransformYears.getYearStartTime(Integer.parseInt(year));
						end = TransformYears.getYearEndTime(Integer.parseInt(year));
					}
					List<Organizationobject> lst = orgDao.getOrgCardByCondition(page,direction,level,start,end);
					pageCount = orgDao.getOrgCardCountByCond(direction,level,start,end);
					page.setList(lst);
				}
			}
			Integer pageSize = page.getPageSize();
			if(null==pageSize){
				pageSize = 10;
			}
			if(null==pageCount||pageCount==0){
				page.setTotalRecords(0);
				page.setTotalPage(0);
			}else{
				page.setTotalRecords(pageCount);
				int totalPages = pageCount%pageSize==0?( pageCount/pageSize):( pageCount/pageSize+1);
				page.setTotalPage(totalPages);
			}
		}
		return page;
	}
	
	/**
	 * 填充组织对象数组中所有对象的某些字段对应的字典表信息
	 * @param orgList
	 * @param dicMap
	 */
	public void filledOrgListDic(List<Organizationobject> orgList,Map<String,Map<String,String>> dicMap){
		if(null!=orgList&&null!=dicMap){
			for(Organizationobject orgObj:orgList){
				this.filledOrgDic(orgObj, dicMap);
			}
		}
	}
	
	/**
	 * 填充组织对象中某些字段对应的字典表信息
	 */
	public void filledOrgDic(Organizationobject orgObj,Map<String,Map<String,String>> dicMap){
		//所属方向
		Map<String,String> directionMap = dicMap.get(ConstantManage.DIRECTION);
		orgObj.setOrgDirectionStr("");
		if(null!=directionMap&&directionMap.size()>0){
			String directionIds = orgObj.getOrgDirectionCodes();
			StringBuilder directionSTemp = new StringBuilder();
			if(null!=directionIds&&!"".equals(directionIds)){
				String[] dirArr = directionIds.split(",");
				for (String dirCode : dirArr) {
					directionSTemp.append(directionMap.get(dirCode)+",");
				}
				String directionStr = directionSTemp.toString();
				orgObj.setOrgDirectionStr(directionStr.substring(0,directionStr.length()-1));
			}
		}
		
		//组织状态(共用人员状态)
		Map<String,String> orgStatusMap = dicMap.get(ConstantManage.ORGSTATUS);
		orgObj.setOrgStatusStr(orgStatusMap.get(orgObj.getOrgStatus()));
		//控制状态
		Map<String,String> contrlStatusMap = dicMap.get(ConstantManage.CONTROLSTATUS);
		if(null!=contrlStatusMap&&contrlStatusMap.size()>0){
			orgObj.setOrgControlStatusStr(contrlStatusMap.get(orgObj.getOrgControlStatus()));
		}
		//重要等级
		Map<String,String> levelMap = dicMap.get(ConstantManage.LEVEL);
		if(null!=levelMap&&levelMap.size()>0){
			String levelStr = levelMap.get(orgObj.getOrgImportLevel());
			if(null!=levelStr){
			    orgObj.setOrgImportLevelStr(levelStr);
			}else{
				orgObj.setOrgImportLevelStr("");
			}
		}
		//录入人
		String inUserId = orgObj.getOrgInputPersonId();
		if(null!=inUserId&&!"".equals(inUserId.trim())){
			inUserId = inUserId.trim();
			if(inUserId.matches("[0-9]+")){
				AuthUser inputUser = userService.findUserById(Long.parseLong(inUserId.trim()));
				if(null!=inputUser){
					orgObj.setOrgInputPersonStr(inputUser.getUserName());
				}else{
					orgObj.setOrgInputPersonStr(inUserId);
				}
			}else{
				orgObj.setOrgInputPersonStr(inUserId);
			}
		}
		//负责人
		List<AuthUser> allUserList = userService.findAllUser();
		if(null!=allUserList&&allUserList.size()>0){
			String dutyPersonIds = orgObj.getOrgDutyPersonIds();
			StringBuilder dutyPersonSTemp = new StringBuilder();
			if(null!=dutyPersonIds&&!"".equals(dutyPersonIds)){
				String[] dutyArr = dutyPersonIds.split(",");
				for (String dutyId : dutyArr) {
					//boolean findflag = false;
					for(AuthUser tempUser:allUserList){
						if(dutyId.equals(String.valueOf(tempUser.getId()))){
							dutyPersonSTemp.append(tempUser.getUserName()+",");
							//findflag=true;
							break;
						}
					}		
					/*if(!findflag){
						dutyPersonSTemp.append("用户已删除,");
					}*/
				}
				String dutyUserStr = dutyPersonSTemp.toString();
				if(null!=dutyUserStr&&!"".equals(dutyUserStr)&&dutyUserStr.length()>1){
					orgObj.setOrgDutyPersonStr(dutyUserStr.substring(0,dutyUserStr.length()-1));
				}else{
					orgObj.setOrgDutyPersonStr("");
				}
			}
		}
	}
	
	/**
	 * 根据id查询案件
	 */
	public Organizationobject findFilledOrgById(Long id,Map<String,Map<String,String>> dicMap) {
		Organizationobject obj = orgDao.findOrgById(id);
		if(null!=obj){
			this.filledOrgDic(obj, dicMap);
		}
		return obj;
	}
	
	/**
	 * 根据id查询案件并填充
	 */
	public Organizationobject findOrgById(Long id) {
		return orgDao.findOrgById(id);
	}
	
	/**
	 * 根据name查询组织
	 * @param name
	 * @return
	 */
	public boolean checkOrgNameExsit(String checkName,String checkNameId) {
		List<Organizationobject> resultList = orgDao.findOrgByFitName(checkName);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()>1){
				return true;
			}else{
				if(null!=checkNameId&&(resultList.get(0).getId()==Long.parseLong(checkNameId))){
					return false;
				}else{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据name模糊查询组织
	 * @param name
	 * @return
	 */
	public List<Organizationobject> findOrgByName(String name) {
		return orgDao.findOrgByName(name);
	}
	
	/**
	 * 根据id,name模糊查询组织
	 * @param name
	 * @param id 自身id，用于查询去除自身
	 * @return
	 */
	public List<Organizationobject> findOrgByName(String name, Long id) {
		return orgDao.findOrgByNameNoId(id, name);
	}
	
	public void setOrgDao(OrganizationManageDao orgDao) {
		this.orgDao = orgDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
}
