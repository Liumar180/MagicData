package com.integrity.lawCase.organizationManage.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.organizationManage.service.OrgInfoManageService;
import com.opensymphony.xwork2.ActionSupport;

public class OrgInfoManagerAjaxAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrgInfoManagerAjaxAction.class);
	
	private OrgInfoManageService orgInfoManageService;
	private String searchType;
	private PageModel<Organizationobject> pageModel;
	private String condJsonStr;
	private String cardPageNo;
	private String cardPageSize;

	/**
	 * 获得组织卡片或列表的分页数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOrgList() {
		if (null == pageModel) {
			pageModel = new PageModel<Organizationobject>();
			if(null==cardPageNo){
				pageModel.setPageNo(1);
			}else{
				pageModel.setPageNo(Integer.parseInt(cardPageNo));
			}
			if(null==cardPageSize){
				pageModel.setPageSize(10);
			}else{
				pageModel.setPageSize(Integer.parseInt(cardPageSize));
			}
		}
		Map<String, String> condMap = new HashMap<String, String>();
		if (null != condJsonStr&&!"".equals(condJsonStr.trim())) {
			JSONArray tempJsonArray = JSONArray.fromObject(condJsonStr);
			if (null != tempJsonArray) {
				List<?> tempJsonList = JSONArray.toList(tempJsonArray);
				if (null != tempJsonList) {
					for (Object tempObj : tempJsonList) {
						if (null != tempObj) {
							JSONObject jasonObject = JSONObject.fromObject(tempObj);
							Map<String, Object> tempMap = (Map<String, Object>) jasonObject;
							for (Entry<String, Object> entry : tempMap.entrySet()) {
								condMap.put(entry.getKey(),entry.getValue().toString());
							}
						}
					}
				}
			}
		}

		try {
			pageModel = orgInfoManageService.getOrgPageModel(searchType,pageModel, condMap);
			ServletContext sc = ServletActionContext.getServletContext();
			//获取字典表信息
			Object tempDataObj = sc.getAttribute(ConstantManage.DATADICTIONARY);
			if(null!= tempDataObj){
				Map<String,Map<String,String>> dicMap = (Map<String, Map<String, String>>) tempDataObj;
				List<Organizationobject> dataList = pageModel.getList();
				orgInfoManageService.filledOrgListDic(dataList,dicMap);
			}
		} catch (Exception e) {
			logger.error("查询组织列表异常", e);
		}
		return SUCCESS;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public PageModel<Organizationobject> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<Organizationobject> pageModel) {
		this.pageModel = pageModel;
	}

	public String getCondJsonStr() {
		return condJsonStr;
	}

	public void setCondJsonStr(String condJsonStr) {
		this.condJsonStr = condJsonStr;
	}

	public String getCardPageNo() {
		return cardPageNo;
	}

	public void setCardPageNo(String cardPageNo) {
		this.cardPageNo = cardPageNo;
	}

	public String getCardPageSize() {
		return cardPageSize;
	}

	public void setCardPageSize(String cardPageSize) {
		this.cardPageSize = cardPageSize;
	}

	public void setOrgInfoManageService(
			OrgInfoManageService orgInfoManageService) {
		this.orgInfoManageService = orgInfoManageService;
	}

}
