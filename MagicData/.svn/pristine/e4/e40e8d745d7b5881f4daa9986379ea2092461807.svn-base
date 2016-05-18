package com.integrity.lawCase.organizationManage.action;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.organizationManage.service.OrgInfoManageService;
import com.integrity.lawCase.organizationManage.wapper.OrgSearchPreWapper;
import com.integrity.lawCase.relation.pojo.RelationAction;
import com.integrity.lawCase.relation.service.AllRelationService;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;
import com.integrity.lawCase.util.PageSetValueUtil;
import com.opensymphony.xwork2.ActionSupport;

public class OrgInfoManagerAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrgInfoManagerAction.class);
	private OrgInfoManageService orgInfoManageService;
	/**用于卡片或列表搜索的包装类*/
	private OrgSearchPreWapper orgSearchPreWapper;
	/**标识是卡片搜索还是列表搜索 取自OrganizationManageService中定义的常量*/
	private String searchType;
	/**关联关系Service*/
	private AllRelationService allRelationService;
	/**用于组织细节页面的显示*/
	private Organizationobject orgObj;
	
	/**
	 * 获得组织管理卡片/列表页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOrgManagerPage(){
		ServletContext sc = ServletActionContext.getServletContext();
		//获取字典表信息
		Object tempDataObj = sc.getAttribute(ConstantManage.DATADICTIONARY);
		if(null!= tempDataObj){
			Map<String,Map<String,String>> dicMap = (Map<String, Map<String, String>>) tempDataObj;
			orgSearchPreWapper = orgInfoManageService.getSearchCondition(searchType,dicMap);
		}else{
			orgSearchPreWapper = new OrgSearchPreWapper();
		}
		return SUCCESS;
	}

	/**
	 * 获得组织详情页面
	 */
	@SuppressWarnings("unchecked")
	public String viewOrgDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletContext sc = ServletActionContext.getServletContext();
		try {
			/*组织基本信息*/
			Long id = orgObj.getId();
			orgObj = orgInfoManageService.findOrgById(id);
			Object tempDataObj = sc.getAttribute(ConstantManage.DATADICTIONARY);
			Map<String,String> type_property = (Map<String, String>) sc.getAttribute(ConstantManage.TYPE_PROPERTY);
			if(null!= tempDataObj){
				Map<String,Map<String,String>> dicMap = (Map<String, Map<String, String>>) tempDataObj;
				orgInfoManageService.filledOrgDic(orgObj, dicMap);//填充文字信息
			}
			request.setAttribute("orgObject", orgObj);
			/*关联对象*/
			request.setAttribute("rootType", ConstantManage.ORGANIZATIONOBJECTTYPE);
			//跳转链接
			RelationAction relationAction = PageSetValueUtil.relationActionSet(type_property,ConstantManage.ORGANIZATIONOBJECTTYPE,id+"");
			request.setAttribute("relationAction", relationAction);
			//关联对象详细
			AllRelationWapper allRelation = allRelationService.getAllRelationForType(id+"", ConstantManage.ORGANIZATIONOBJECTTYPE);
			request.setAttribute("allRelation", allRelation);
			//对象类型
			Map<String, String> typeMap = ConstantManage.getObjectTypeMap();
			request.setAttribute("typeMap", typeMap);
		} catch (Exception e) {
			logger.error("根据id查询组织异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 关联用组织详情yemen
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String orgRelationDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletContext sc = ServletActionContext.getServletContext();
		try {
			Long id = orgObj.getId();
			orgObj = orgInfoManageService.findOrgById(id);
			Object tempDataObj = sc.getAttribute(ConstantManage.DATADICTIONARY);
			if(null!= tempDataObj){
				Map<String,Map<String,String>> dicMap = (Map<String, Map<String, String>>) tempDataObj;
				orgInfoManageService.filledOrgDic(orgObj, dicMap);//填充文字信息
			}
			request.setAttribute("orgObj", orgObj);
		} catch (Exception e) {
			logger.error("根据id查询案件异常",e);
		}
		return SUCCESS;
	}

	public void setOrgInfoManageService(OrgInfoManageService orgInfoManageService) {
		this.orgInfoManageService = orgInfoManageService;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public OrgSearchPreWapper getOrgSearchPreWapper() {
		return orgSearchPreWapper;
	}

	public void setOrgSearchPreWapper(OrgSearchPreWapper orgSearchPreWapper) {
		this.orgSearchPreWapper = orgSearchPreWapper;
	}

	public Organizationobject getOrgObj() {
		return orgObj;
	}

	public void setOrgObj(Organizationobject orgObj) {
		this.orgObj = orgObj;
	}

	public void setAllRelationService(AllRelationService allRelationService) {
		this.allRelationService = allRelationService;
	}

}
