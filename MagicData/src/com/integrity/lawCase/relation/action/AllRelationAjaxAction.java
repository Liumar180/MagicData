package com.integrity.lawCase.relation.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.relation.service.AllRelationService;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;
import com.opensymphony.xwork2.ActionSupport;

public class AllRelationAjaxAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	/**服务器data目录路径*/
	private static String dataPath = ServletActionContext.getServletContext().getRealPath("/data/");
	private AllRelationService arService;
	/**根对象ID*/
	private String rootId;
	/**根对象类型*/
	private String rootType;
	/**选中的关联对象ID*/
	private String typeId;
	/**选中的关联对象类型*/
	private String type;
	/**选中的关联对象所关联的所有对象集合*/
	private List<Map<String,String>> subResultList;
	/**需要关联的对象ID集合，逗号分隔，单字符串形如People-7*/
	private String relationIds;
	
	/**
	 * 获得选中的关联对象所关联的所有对象集合
	 * @return
	 */
	public String getAllRelationForOne(){
		AllRelationWapper arWapper = arService.getAllRelationForType(typeId, type);
		List<List<String>> subTempList = arService.getRelationSubTdInfo(dataPath, arWapper);
		subResultList = new ArrayList<Map<String,String>>();
		for(List<String> tempList:subTempList){
			if(null!=tempList&&tempList.size()>0){
				Map<String,String> tempMap = new HashMap<String,String>();
				for(int i =0;i<tempList.size();i++){
					if(i==0){
						tempMap.put("id", tempList.get(0));//0出用于存储id
					}else{
						tempMap.put(String.valueOf(i), tempList.get(i));
					}
				}
				subResultList.add(tempMap);
			}
		}
		/*jsonSubResult = JSONArray.fromObject(subResultList).toString();*/
		return SUCCESS;
	}
	
	/**
	 * 添加或修改关联关系
	 * @return
	 */
	public String addAllRelation(){
		Map<String, String> relationMap = new HashMap<String, String>();
		String caseStr = "";
		String fileStr = "";
		String hostStr = "";
		String orgStr = "";
		String peoStr = "";	
		if(null!=typeId&&!"".equals(typeId)&&null!=type&&!"".equals(type)){
			if(ConstantManage.CASEOBJECTTYPE.equals(type)){
				caseStr = caseStr + typeId;
			}else if(ConstantManage.FILEOBJECTTYPE.equals(type)){
				fileStr = fileStr + typeId;
			}else if(ConstantManage.HOSTOBJECTTYPE.equals(type)){
				hostStr = hostStr+typeId;
			}else if(ConstantManage.ORGANIZATIONOBJECTTYPE.equals(type)){
				orgStr = orgStr + typeId;
			}else if(ConstantManage.PEOPLEOBJECTTYPE.equals(type)){
				peoStr = peoStr + typeId;
			}
		}
		if(null!=relationIds&&!"".equals(relationIds)){
			String[] relationIdsArray = relationIds.split(",");
			for(String tempIds:relationIdsArray){
				if(null!=tempIds&&!"".equals(tempIds)){
					String[] tempArray = tempIds.split("-");
					if(CaseObject.class.getSimpleName().equals(tempArray[0])){
						caseStr = caseStr + tempArray[1] + ",";
					}else if(FilesObject.class.getSimpleName().equals(tempArray[0])){
						fileStr = fileStr+ tempArray[1] + ",";
					}else if(HostsObject.class.getSimpleName().equals(tempArray[0])){
						hostStr = hostStr+ tempArray[1] + ",";
					}else if(Organizationobject.class.getSimpleName().equals(tempArray[0])){
						orgStr = orgStr+ tempArray[1] + ",";
					}else if(Peopleobject.class.getSimpleName().equals(tempArray[0])){
						peoStr = peoStr+ tempArray[1] + ",";
					}
				}
			}
		}
		if(null!=caseStr&&!"".equals(caseStr)){
			if(caseStr.endsWith(",")){
				caseStr = caseStr.substring(0,caseStr.length()-1);
			}
			relationMap.put(ConstantManage.CASEOBJECTTYPE, caseStr);
		}
		if(null!=fileStr&&!"".equals(fileStr)){
			if(fileStr.endsWith(",")){
				fileStr = fileStr.substring(0,fileStr.length()-1);
			}
			relationMap.put(ConstantManage.FILEOBJECTTYPE, fileStr);
		}
		if(null!=hostStr&&!"".equals(hostStr)){
			if(hostStr.endsWith(",")){
				hostStr = hostStr.substring(0,hostStr.length()-1);
			}
			relationMap.put(ConstantManage.HOSTOBJECTTYPE, hostStr);
		}
		if(null!=orgStr&&!"".equals(orgStr)){
			if(orgStr.endsWith(",")){
				orgStr = orgStr.substring(0,orgStr.length()-1);
			}
			relationMap.put(ConstantManage.ORGANIZATIONOBJECTTYPE, orgStr);
		}
		if(null!=peoStr&&!"".equals(peoStr)){
			if(peoStr.endsWith(",")){
				peoStr = peoStr.substring(0,peoStr.length()-1);
			}
			relationMap.put(ConstantManage.PEOPLEOBJECTTYPE, peoStr);
		}
		if(null!=relationMap&&relationMap.size()>0){
			arService.addOrUpdateRelation(rootId, rootType,relationMap);
		}
		return SUCCESS;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public void setArService(AllRelationService arService) {
		this.arService = arService;
	}
	
	public List<Map<String, String>> getSubResultList() {
		return subResultList;
	}

	public void setSubResultList(List<Map<String, String>> subResultList) {
		this.subResultList = subResultList;
	}

	public void setRelationIds(String relationIds) {
		this.relationIds = relationIds;
	}

}
