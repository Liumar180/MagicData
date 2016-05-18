package com.integrity.lawCase.exportLaw.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.hostManage.bean.DomainsituationObject;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.hostManage.bean.LoopholesObject;
import com.integrity.lawCase.hostManage.service.HostManageService;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.peopleManage.bean.Documentnumberobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.peopleManage.bean.Peoplevirtualobject;
import com.integrity.lawCase.peopleManage.bean.Phonenumberobject;
import com.integrity.lawCase.peopleManage.service.PeopleManageService;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.service.UserService;

public class ExportLCService {

	public final static String CASE_EXPORT_PATH = "images" + File.separator
			+ "exportFile" + File.separator + "caseM" + File.separator
			+ "excase";
	public final static String CASE_EXPORT_TEMPLANT = "images" + File.separator
			+ "exportFile" + File.separator + "caseM" + File.separator
			+ "CaseEx.doc";

	public final static String ORGAN_EXPORT_PATH = "images" + File.separator
			+ "exportFile" + File.separator + "organizationM" + File.separator
			+ "exorganization";
	public final static String ORGAN_EXPORT_TEMPLANT = "images"
			+ File.separator + "exportFile" + File.separator + "organizationM"
			+ File.separator + "OrganizationEx.doc";

	public final static String FILEOBJ_EXPORT_PATH = "images" + File.separator
			+ "exportFile" + File.separator + "fileM" + File.separator
			+ "exfile";
	public final static String FILEOBJ_EXPORT_TEMPLANT = "images"
			+ File.separator + "exportFile" + File.separator + "fileM"
			+ File.separator + "FileEx.doc";

	public final static String PEOPLE_EXPORT_PATH = "images" + File.separator
			+ "exportFile" + File.separator + "peopleM" + File.separator
			+ "expeople";
	public final static String PEOPLE_EXPORT_TEMPLANT = "images"
			+ File.separator + "exportFile" + File.separator + "peopleM"
			+ File.separator + "PeopleEx.doc";

	public final static String HOST_EXPORT_PATH = "images" + File.separator
			+ "exportFile" + File.separator + "hostM" + File.separator
			+ "exhost";
	public final static String HOST_EXPORT_TEMPLANT = "images" + File.separator
			+ "exportFile" + File.separator + "hostM" + File.separator
			+ "HostEx.doc";

	private UserService userService;
	private PeopleManageService pmService;
	private HostManageService hmService;

	private Map<String, Map<String, String>> allMap;

	private String webPath;

	private Map<String, String> direction;
	private Map<String, String> level;
	private Map<String, String> caseStatus;
	private Map<String, String> orgStatus;
	private Map<String, String> controlStatus;
	private Map<String, String> pNation;
	private Map<String, String> pPersonStatus;
	private Map<String, String> pCountry;
	private Map<String, String> accountType;
	private Map<String, String> controlMeasures;
	private Map<String, String> dcType;
	private Map<String, String> phoneType;
	private Map<String,String> hostStatus;
	private Map<String,String> hostType;

	/**
	 * 初始化静态数据
	 */
	public void initData() {
		direction = allMap.get(ConstantManage.DIRECTION);// 方向
		level = allMap.get(ConstantManage.LEVEL);// 等级
		caseStatus = allMap.get(ConstantManage.CASESTATUS);// 状态
		orgStatus = allMap.get(ConstantManage.ORGSTATUS);
		controlStatus = allMap.get(ConstantManage.CONTROLSTATUS);
		pNation = allMap.get(ConstantManage.NATION);// 民族
		pPersonStatus = allMap.get(ConstantManage.PERSONSTATUS);// 状态
		pCountry = allMap.get(ConstantManage.COUNTRY);// 国籍
		accountType = allMap.get(ConstantManage.ACCOUNTTPYE);// 控制类型
		controlMeasures = allMap.get(ConstantManage.CONTROLMEASURES);// 控制策略
		dcType = allMap.get(ConstantManage.DCTYPE);// 证件类型
		phoneType = allMap.get(ConstantManage.PHONETYPE);// 电话类型
		hostStatus = allMap.get(ConstantManage.HOSTSTATUS);//状态
		hostType = allMap.get(ConstantManage.HOSTTYPE);//主机类别
	}

	/**
	 * 获得案件数据
	 */
	public Map<String, String> getExportMap(CaseObject pramaObj) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> dataMap = new HashMap<String, String>();
		StringBuffer levelStrTemp = new StringBuffer();
		StringBuffer caseStasStrTemp = new StringBuffer();
		StringBuffer directionStrTemp = new StringBuffer();
		dataMap.put("${caseAim}", exUtilS(pramaObj.getCaseAim()));
		dataMap.put("${caseLeader}", exUtilS(pramaObj.getCaseLeader()));
		for (String object : pramaObj.getCaseLevel().split(",")) {
			levelStrTemp.append(level.get(object)==null?"":(level.get(object) + ","));
		}
		dataMap.put("${caseLevel}", levelStrTemp.toString());
		dataMap.put("${caseName}", exUtilS(pramaObj.getCaseName()));
		for (String object : pramaObj.getCaseStatus().split(",")) {
			caseStasStrTemp.append(caseStatus.get(object)==null?"":(caseStatus.get(object) + ","));
		}
		dataMap.put("${caseStatus}", caseStasStrTemp.toString());
		dataMap.put("${caseSupervisor}", exUtilS(pramaObj.getCaseSupervisor()));
		dataMap.put("${caseUserNames}", exUtilS(pramaObj.getCaseUserNames()));
		String createTimeStr = pramaObj.getCreateTime()==null?"":sf.format(pramaObj.getCreateTime());
		dataMap.put("${createTime}", createTimeStr);
		for (String object : pramaObj.getDirectionCode().split(",")) {
			directionStrTemp.append(direction.get(object) == null ? ""
					: (direction.get(object) + ","));
		}
		dataMap.put("${directionCode}", directionStrTemp.toString());
		dataMap.put("${memo}", exUtilS(pramaObj.getMemo()));
		return dataMap;
	}

	/**
	 * 获得案件图片数据
	 */
	public Map<String, String> getExportPicMap(CaseObject pramaObj) {
		Map<String, String> dataMap = new HashMap<String, String>();
		String defaultImgPath = webPath + "images" + File.separator + "lawCase"
				+ File.separator + "moren" + File.separator + "case.jpg";
		dataMap.put("${caseImage}", defaultImgPath);
		return dataMap;
	}

	/**
	 * 获得组织数据
	 */
	public Map<String, String> getExportMap(Organizationobject pramaObj) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> dataMap = new HashMap<String, String>();
		StringBuffer directionStrTemp = new StringBuffer();
		StringBuffer personStrTemp = new StringBuffer();
		String createTimeStr = "";
		if(null!=pramaObj.getCreateTime()){
			createTimeStr = sf.format(pramaObj.getCreateTime());
		}
		dataMap.put("${createTime}", createTimeStr);
		dataMap.put("${orgAlias}", exUtilS(pramaObj.getOrgAlias()));
		dataMap.put("${orgCName}", exUtilS(pramaObj.getOrgCName()));
		
		dataMap.put("${orgControlStatus}",pramaObj.getOrgControlStatus()==null?"":
				controlStatus.get(pramaObj.getOrgControlStatus()));
		dataMap.put("${orgDescription}", exUtilS(pramaObj.getOrgDescription()));
		for (String object : pramaObj.getOrgDirectionCodes().split(",")) {
			directionStrTemp.append(direction.get(object) == null ? ""
					: direction.get(object) + ",");
		}
		dataMap.put("${orgDirectionCodes}", directionStrTemp.toString());
		if (pramaObj.getOrgDutyPersonIds() != null
				&& pramaObj.getOrgDutyPersonIds() != ""
				&& !"".equals(pramaObj.getOrgDutyPersonIds())) {
			for (String object : pramaObj.getOrgDutyPersonIds().split(",")) {
				Long id = Long.valueOf(object);
				AuthUser tempUser = userService.findUserById(id);
				if (tempUser != null) {
					personStrTemp.append(tempUser.getName()
							+ ",");
				}
			}
		}
		dataMap.put("${orgDutyPersonIds}", personStrTemp.toString());
		dataMap.put("${orgEName}", exUtilS(pramaObj.getOrgEName()));
		dataMap.put("${orgImportLevel}",
				level.get(pramaObj.getOrgImportLevel()));
		dataMap.put("${orgInputPersonID}", pramaObj.getOrgInputPersonId());
		dataMap.put("${orgLocation}", exUtilS(pramaObj.getOrgLocation()));
		dataMap.put("${orgRemark}", exUtilS(pramaObj.getOrgRemark()));
		dataMap.put("${orgSpell}", exUtilS(pramaObj.getOrgSpell()));
		dataMap.put("${orgStatus}", pramaObj.getOrgStatus()==null?"":orgStatus.get(pramaObj.getOrgStatus()));
		return dataMap;
	}

	/**
	 * 获得组织图片数据
	 */
	public Map<String, String> getExportPicMap(Organizationobject pramaObj) {
		Map<String, String> dataMap = new HashMap<String, String>();
		String defaultImgPath = webPath + "images" + File.separator + "lawCase"
				+ File.separator + "moren" + File.separator;
		String objImg = pramaObj.getOrgImage();
		if (null != objImg && !"".equals(objImg)) {
			dataMap.put("${orgImage}", defaultImgPath + objImg);
		} else {
			dataMap.put("${orgImage}", defaultImgPath + "organaization.jpg");
		}
		return dataMap;
	}

	/**
	 * 获得文件数据
	 */
	public Map<String, String> getExportMap(FilesObject pramaObj) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> dataMap = new HashMap<String, String>();
		StringBuffer directionStrTemp = new StringBuffer();
		String createTimeStr = pramaObj.getCreateTime()==null?"":sf.format(pramaObj.getCreateTime());
		dataMap.put("${createTime}", createTimeStr);
		dataMap.put("${fileMD5}", pramaObj.getFileMD5());
		dataMap.put("${fileName}", exUtilS(pramaObj.getFileName()));
		dataMap.put("${responsiblePerson}",
				exUtilS(pramaObj.getResponsiblePerson()));
		for (String object : pramaObj.getDirection().split(",")) {
			directionStrTemp.append(direction.get(object) == null ? ""
					: (direction.get(object) + ","));
		}
		dataMap.put("${direction}", directionStrTemp.toString());
		return dataMap;
	}

	/**
	 * 获得人员数据
	 */
	public Map<String, String> getExportMap(Peopleobject pramaObj) {
		Map<String, String> dataMap = new HashMap<String, String>();
		StringBuffer directionStrTemp = new StringBuffer();

		dataMap.put("${pocnname}", exUtilS(pramaObj.getPocnname()));
		dataMap.put("${poalias}", exUtilS(pramaObj.getPoalias()));
		dataMap.put("${poenname}", exUtilS(pramaObj.getPoenname()));
		dataMap.put("${ponamespell}", exUtilS(pramaObj.getPonamespell()));
		dataMap.put("${posex}", pramaObj.getPosex());
		for (String object : pramaObj.getPodirectionof().split(",")) {
			if (direction.get(object) != null) {
				directionStrTemp.append(direction.get(object)==null?"":(direction.get(object)+ ","));
			}
		}
		dataMap.put("${podirectionof}", directionStrTemp.toString());
		dataMap.put("${ponational}", pNation.get(pramaObj.getPonational()));
		dataMap.put("${pocountry}", pCountry.get(pramaObj.getPocountry()));
		dataMap.put("${poimportantlevel}",
				level.get(pramaObj.getPoimportantlevel())==null?"":level.get(pramaObj.getPoimportantlevel()));
		dataMap.put("${polocation}", exUtilS(pramaObj.getPolocation()));
		dataMap.put("${pohukou}", exUtilS(pramaObj.getPohukou()));
		dataMap.put("${podutyman}", pramaObj.getPodutyman());
		dataMap.put("${popersonstatus}",
				pPersonStatus.get(pramaObj.getPopersonstatus())==null?"":pPersonStatus.get(pramaObj.getPopersonstatus()));
		dataMap.put("${pocontrolstatus}",
				controlStatus.get(pramaObj.getPocontrolstatus())==null?"":controlStatus.get(pramaObj.getPocontrolstatus()));
		dataMap.put("${podescription}", exUtilS(pramaObj.getPodescription()));
		return dataMap;
	}

	/**
	 * 获得人员表格填充数据
	 */
	public Map<Integer, List<String[]>> getExportPTableMap(Long pId) {
		Map<Integer, List<String[]>> tableDataMap = new HashMap<Integer, List<String[]>>();
		// 虚拟身份
		ArrayList<String[]> pvTableDatas = new ArrayList<String[]>();
		List<Peoplevirtualobject> pvList = pmService.getPVinfoByPoid(pId);
		for (Peoplevirtualobject peoplevirtualobject : pvList) {
			String aType = accountType.get(peoplevirtualobject.getPvaccounttype());
			String cStatus =  controlStatus.get(peoplevirtualobject.getPvcontrolstatus());
			String conMes = controlMeasures.get(peoplevirtualobject.getPvcontrolmeasures());
			String[] field = {
					                    aType==null?"":aType,
					                    peoplevirtualobject.getPvusername(),
					                    peoplevirtualobject.getPvpassword(),
					                    cStatus==null?"":cStatus,
					                    conMes==null?"":conMes,
					                    exUtilS(peoplevirtualobject.getPvremark())
					                   };
			pvTableDatas.add(field);
		}
		tableDataMap.put(1,pvTableDatas);//放在第2个表格,第一表格序列为0
		
		// 证件
		ArrayList<String[]> dnTableDatas = new ArrayList<String[]>();
		List<Documentnumberobject> dnList = pmService.getDNinfoByPoid(pId);
		for (Documentnumberobject documentnumberobject : dnList) {
			String dcTypeStr = dcType.get(documentnumberobject.getDntype());
			String[] field = { 
					                     dcTypeStr==null?"":dcTypeStr,
					                     documentnumberobject.getDnnumber(),
					                     exUtilS(documentnumberobject.getDnremark())
					                    };
			dnTableDatas.add(field);
		}
		tableDataMap.put(2,dnTableDatas);//放在第3个表格
		// 电话
		ArrayList<String[]> pnTableDatas = new ArrayList<String[]>();
		List<Phonenumberobject> pnList = pmService.getPNinfoByPoid(pId);
		for (Phonenumberobject phonenumberobject : pnList) {
			String pTypeStr = phoneType.get(phonenumberobject.getPntype());
			String[] field = { 
					                     pTypeStr==null?"":pTypeStr,
					                     phonenumberobject.getPnnumber(),
					                     exUtilS(phonenumberobject.getPnremark()) 
					                    };
			pnTableDatas.add(field);
		}
		tableDataMap.put(3,pnTableDatas);//放在第4个表格

		return tableDataMap;
	}

	/**
	 * 获得人员图片数据
	 */
	public Map<String, String> getExportPicMap(Peopleobject pramaObj) {
		Map<String, String> dataMap = new HashMap<String, String>();
		String defaultImgPath = webPath + "images" + File.separator + "lawCase"
				+ File.separator + "moren" + File.separator
				+ ConstantManage.PEOPLEOBJECTTYPE + ".jpg";
		String objImg = pramaObj.getPoimage();
		String fullObjImg = webPath
				+ "images"
				+ File.separator
				+ objImg.substring(objImg.indexOf("images/") + 7)
						.replace("/", "\\").replace("\\", File.separator);
		if (null != objImg && !"".equals(objImg)) {
			dataMap.put("${poimage}", fullObjImg);
		} else {
			dataMap.put("${poimage}", defaultImgPath);
		}
		return dataMap;
	}

	/**
	 * 获得主机数据
	 */
	public Map<String, String> getExportMap(HostsObject pramaObj) {
		Map<String, String> dataMap = new HashMap<String, String>();
		StringBuffer directionStrTemp = new StringBuffer();

		 dataMap.put("${controlState}",controlStatus.get(pramaObj.getControlState()));
		 dataMap.put("${descriptionContents}",exUtilS(pramaObj.getDescriptionContents()));
		 for (String object : pramaObj.getDirections().split(",")) {
			 directionStrTemp.append(direction.get(object) == null ? ""
						: direction.get(object) + ",");
			}
		 dataMap.put("${directions}",directionStrTemp.toString());
		 dataMap.put("${hostIp}",pramaObj.getHostIp());
		 dataMap.put("${hostName}",exUtilS(pramaObj.getHostName()));
		 dataMap.put("${hostState}",hostStatus.get(pramaObj.getHostState())==null?"":hostStatus.get(pramaObj.getHostState()));
		 dataMap.put("${hostType}",hostType.get(pramaObj.getHostType())==null?"":hostType.get(pramaObj.getHostType()));
		 dataMap.put("${importantLevel}",level.get(pramaObj.getImportantLevel())==null?"":level.get(pramaObj.getImportantLevel()));
		 dataMap.put("${location}",exUtilS(pramaObj.getLocation()));
		 dataMap.put("${macAddress}",exUtilS(pramaObj.getMacAddress()));
		 dataMap.put("${operateSystem}",exUtilS(pramaObj.getOperateSystem()));
		 dataMap.put("${installationService}",exUtilS(pramaObj.getInstallationService()));
		 dataMap.put("${provider}",exUtilS(pramaObj.getProvider()));
		 dataMap.put("${responsiblePerson}",exUtilS(pramaObj.getResponsiblePerson()));
		return dataMap;
	}

	/**
	 * 获得主机表格填充数据
	 */
	public Map<Integer, List<String[]>> getExportHTableMap(Long hId) {
		Map<Integer, List<String[]>> tableDataMap = new HashMap<Integer, List<String[]>>();
		// 域名情况
		ArrayList<String[]> doTableDatas = new ArrayList<String[]>();
		List<DomainsituationObject> doList = hmService.findDmainList(hId);
		for (DomainsituationObject dObject : doList) {
			String[] field = {
					                     exUtilS(dObject.getDomain()),
					                     dObject.getRegTime(),
					                     dObject.getExpiredTime(), 
					                     exUtilS(dObject.getDomainResolution()), 
					                     exUtilS(dObject.getDomainResolutionServices()), 
					                     exUtilS(dObject.getDomainServiceProvider()), 
					                     exUtilS(dObject.getDatabaseType()), 
					                     exUtilS(dObject.getServiceType()), 
					                     exUtilS(dObject.getDevelopeLanguage())
					                    };
			doTableDatas.add(field);
		}
		tableDataMap.put(1,doTableDatas);//放在第2个表格,第一表格序列为0
		
		//漏洞情况
		ArrayList<String[]> loTableDatas = new ArrayList<String[]>();
		List<LoopholesObject> loList = hmService.findLoopholesList(hId);
		for (LoopholesObject lobject : loList) {
			String[] field = {
					                     exUtilS(lobject.getVulnerabilityDescription()), 
					                     exUtilS(lobject.getMethods()), 
					                     exUtilS(lobject.getUseTools()),
					                     exUtilS(lobject.getAuthorityType()),
					                     exUtilS(lobject.getHiddenDoor()),
					                     exUtilS(lobject.getRemarks())
					                    };
			loTableDatas.add(field);
		}
		tableDataMap.put(2,loTableDatas);//放在第3个表格

		return tableDataMap;
	}

	/**
	 * 获得关联数据
	 */
	public Map<String, String> getExportMap(AllRelationWapper allRelation) {
		Map<String, String> dataMap = new HashMap<String, String>();
		if (allRelation != null) {
			StringBuffer caseListStr = new StringBuffer();
			if (allRelation.getCaseList() != null
					&& allRelation.getCaseList().size() > 0) {
				for (CaseObject co1 : allRelation.getCaseList()) {
					caseListStr.append(exUtilS(co1.getCaseName()) + "	");
				}
			}
			dataMap.put("${castlist}", caseListStr.toString());
			// 相关组织
			StringBuffer orgListStr = new StringBuffer();
			if (allRelation.getOrganList() != null
					&& allRelation.getOrganList().size() > 0) {
				for (Organizationobject oo : allRelation.getOrganList()) {
					orgListStr.append(exUtilS(oo.getOrgCName()) + "	");
				}
			}
			dataMap.put("${organazationlist}", orgListStr.toString());
			// 相关人员
			StringBuffer peopleListStr = new StringBuffer();
			if (allRelation.getPeopleList() != null
					&& allRelation.getPeopleList().size() > 0) {
				for (Peopleobject p : allRelation.getPeopleList()) {
					peopleListStr.append(exUtilS(p.getPocnname()) + "	");
				}
			}
			dataMap.put("${peoplelist}", peopleListStr.toString());
			// 相关文件
			StringBuffer fileListStr = new StringBuffer();
			if (allRelation.getFileList() != null
					&& allRelation.getFileList().size() > 0) {
				for (FilesObject f : allRelation.getFileList()) {
					fileListStr.append(exUtilS(f.getFileName()) + "	");
				}
			}
			dataMap.put("${filelist}", fileListStr.toString());
			// 相关主机
			StringBuffer hostListStr = new StringBuffer();
			if (allRelation.getHostList() != null
					&& allRelation.getHostList().size() > 0) {
				for (HostsObject h : allRelation.getHostList()) {
					hostListStr.append(exUtilS(h.getHostName()) + "	");
				}
			}
			dataMap.put("${hostlist}", hostListStr.toString());
		} else {
			dataMap.put("${castlist}", "");
			dataMap.put("${organazationlist}", "");
			dataMap.put("${peoplelist}", "");
			dataMap.put("${filelist}", "");
			dataMap.put("${hostlist}", "");
		}
		return dataMap;
	}

	// 处理图片字符串内容
	private String exUtilS(String string) {
		if(null==string){
			return "";
		}
		if (string.lastIndexOf(".jpg") != -1) {
			return string.substring(0, string.lastIndexOf(".jpg")) + ". jpg";
		} else if (string.lastIndexOf(".gif") != -1) {
			return string.substring(0, string.lastIndexOf(".gif")) + ". gif";
		} else if (string.lastIndexOf(".bmp") != -1) {
			return string.substring(0, string.lastIndexOf(".bmp")) + ". bmp";
		} else {
			return string;
		}
	}

	public Map<String, Map<String, String>> getAllMap() {
		return allMap;
	}

	public void setAllMap(Map<String, Map<String, String>> allMap) {
		this.allMap = allMap;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setPmService(PeopleManageService pmService) {
		this.pmService = pmService;
	}

	public void setHmService(HostManageService hmService) {
		this.hmService = hmService;
	}
	

}
