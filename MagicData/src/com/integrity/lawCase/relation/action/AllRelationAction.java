package com.integrity.lawCase.relation.action;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.integrity.lawCase.caseManage.service.CaseManageService;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.fileManage.service.FileManageService;
import com.integrity.lawCase.hostManage.service.HostManageService;
import com.integrity.lawCase.organizationManage.service.OrgInfoManageService;
import com.integrity.lawCase.peopleManage.service.PeopleManageService;
import com.integrity.lawCase.relation.service.AllRelationService;
import com.opensymphony.xwork2.ActionSupport;

public class AllRelationAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private static String dataPath = ServletActionContext.getServletContext().getRealPath("/data/");
	
	private AllRelationService arService;
	private CaseManageService cmService;
	private FileManageService fmService;
	private HostManageService hmService;
	private OrgInfoManageService omService;
	private PeopleManageService pmService;
	/**根对象ID*/
	private String rootId;
	/**根对象类型*/
	private String rootType;
	/**选中的关联对象ID*/
	private String typeId;
	/**选中的关联对象类型*/
	private String type;
	/**上部标题栏列表*/
	private List<String> tdLabel;
	/**上部标题栏对应field列表*/
	private List<String> tdInfo;
	/**上部typeId对应的关联对象*/
	private Object connObj = null;
	/**下部标题栏对应jqGridColName*/
	private String subJqColName = "";
	/**下部标题栏对应jqGridColModel*/
	private String subJqColModel = "";
	
	
	/**
	 * 初始化关联页面，获得选择的关联对象信息
	 * @return
	 */
	public String getTypeObj(){
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletContext sc = ServletActionContext.getServletContext();
		Object tempDataObj = sc.getAttribute(ConstantManage.DATADICTIONARY);
		List<List<String>> tdLabelInfo = arService.getRelationTdLabAndInfo(dataPath, type);
		tdLabel = tdLabelInfo.get(0);
		tdInfo = tdLabelInfo.get(1);
		List<String> subLabel = tdLabelInfo.get(2);
		if(ConstantManage.CASEOBJECTTYPE.equals(type)){
			connObj = cmService.findCaseById(Long.parseLong(typeId));
		}else if(ConstantManage.FILEOBJECTTYPE.equals(type)){
			connObj = fmService.getFileinfoByid(Long.parseLong(typeId));
		}else if(ConstantManage.HOSTOBJECTTYPE.equals(type)){
			connObj = hmService.searchDetails(Long.parseLong(typeId));
		}else if(ConstantManage.ORGANIZATIONOBJECTTYPE.equals(type)){
			if(null!= tempDataObj){
				Map<String,Map<String,String>> dicMap = (Map<String, Map<String, String>>) tempDataObj;
				connObj = omService.findFilledOrgById(Long.parseLong(typeId),dicMap);
			}
		}else if(ConstantManage.PEOPLEOBJECTTYPE.equals(type)){
			connObj = pmService.getPeopleinfoByid(Long.parseLong(typeId));
		}else{
			connObj = null;
		}
		subJqColName = "[";
		subJqColModel = "[";
		if(null!=subLabel){
			int subTdWidth = 100/(subLabel.size());
			subJqColName = subJqColName + "'',";
			subJqColModel = subJqColModel +"{name:'id',hidden:true},";
			for(int i = 0;i<subLabel.size();i++){
				subJqColName = subJqColName + "'"+subLabel.get(i)+"',";
				subJqColModel = subJqColModel +"{name:'"+(i+1)+"', width:'"+subTdWidth+"%',align:'center'},";
			}
			subJqColName = subJqColName.substring(0, subJqColName.length()-1);
			subJqColModel = subJqColModel.substring(0, subJqColModel.length()-1);
		}
		subJqColName = subJqColName+"]";
		subJqColModel = subJqColModel+"]";
		
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

	public List<String> getTdLabel() {
		return tdLabel;
	}

	public List<String> getTdInfo() {
		return tdInfo;
	}

	public Object getConnObj() {
		return connObj;
	}

	public void setConnObj(Object connObj) {
		this.connObj = connObj;
	}

	public void setArService(AllRelationService arService) {
		this.arService = arService;
	}

	public void setCmService(CaseManageService cmService) {
		this.cmService = cmService;
	}

	public void setFmService(FileManageService fmService) {
		this.fmService = fmService;
	}

	public void setHmService(HostManageService hmService) {
		this.hmService = hmService;
	}

	public void setOmService(OrgInfoManageService omService) {
		this.omService = omService;
	}

	public void setPmService(PeopleManageService pmService) {
		this.pmService = pmService;
	}

	public String getSubJqColName() {
		return subJqColName;
	}

	public String getSubJqColModel() {
		return subJqColModel;
	}

}
