package com.integrity.lawCase.relation.wapper;

import java.util.List;

import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.relation.bean.AllRelation;

public class AllRelationWapper {
	
	private AllRelation arObj;
	
	private List<CaseObject> caseList;
	
	private List<FilesObject> fileList;
	
	private List<HostsObject> hostList;
	
	private List<Organizationobject> organList;
	
	private List<Peopleobject> peopleList;

	public AllRelation getArObj() {
		return arObj;
	}

	public void setArObj(AllRelation arObj) {
		this.arObj = arObj;
	}

	public List<CaseObject> getCaseList() {
		return caseList;
	}

	public void setCaseList(List<CaseObject> caseList) {
		this.caseList = caseList;
	}

	public List<FilesObject> getFileList() {
		return fileList;
	}

	public void setFileList(List<FilesObject> fileList) {
		this.fileList = fileList;
	}

	public List<HostsObject> getHostList() {
		return hostList;
	}

	public void setHostList(List<HostsObject> hostList) {
		this.hostList = hostList;
	}

	public List<Organizationobject> getOrganList() {
		return organList;
	}

	public void setOrganList(List<Organizationobject> organList) {
		this.organList = organList;
	}

	public List<Peopleobject> getPeopleList() {
		return peopleList;
	}

	public void setPeopleList(List<Peopleobject> peopleList) {
		this.peopleList = peopleList;
	}
	
	
}
