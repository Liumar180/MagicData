package com.integrity.lawCase.exportLaw.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.service.CaseManageService;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.exportLaw.service.ExportLCService;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.fileManage.service.FileManageService;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.hostManage.service.HostManageService;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.organizationManage.service.OrgInfoManageService;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.peopleManage.service.PeopleManageService;
import com.integrity.lawCase.relation.service.AllRelationService;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;
import com.integrity.lawCase.util.FilesToZip;
import com.integrity.system.export.service.WriteToWordService;
import com.opensymphony.xwork2.ActionSupport;

public class ExportLCAjaxAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(ExportLCAjaxAction.class);
	
	/** 导出的对象id集合*/
	private String ids;
	
	/** 导出的对象的类型：案例，文件等*/
	private String lawCaseType;
	
	private String currentTimeStr;
	
	
	private CaseManageService cmService;
	private FileManageService fmService;
	private HostManageService hmService;
	private OrgInfoManageService omService;
	private PeopleManageService pmService;
	private AllRelationService arService;

	/**导出案例Service对象*/
	private ExportLCService exportLCService;
	
	private ServletContext sc = ServletActionContext.getServletContext();
	private String webPath = sc.getRealPath("/");
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, String>> allMap = (Map<String, Map<String, String>>) sc
			.getAttribute(ConstantManage.DATADICTIONARY);
	

	public String exportLawObjs() {
		exportLCService.setWebPath(webPath);
		exportLCService.setAllMap(allMap);
		exportLCService.initData();
		boolean resultFlag = true;
		if (null != ids && !"".equals(ids.trim())) {
			if (ConstantManage.CASEOBJECTTYPE.equals(lawCaseType)) {
				resultFlag = exportCase();
			} else if (ConstantManage.FILEOBJECTTYPE.equals(lawCaseType)) {
				resultFlag = exportFileObj();
			} else if (ConstantManage.HOSTOBJECTTYPE.equals(lawCaseType)) {
				resultFlag = exportHostObj();
			} else if (ConstantManage.ORGANIZATIONOBJECTTYPE
					.equals(lawCaseType)) {
				resultFlag = exportOrgan();
			} else if (ConstantManage.PEOPLEOBJECTTYPE.equals(lawCaseType)) {
				resultFlag = exportPeopleObj();
			}
		}
		if(!resultFlag){
			return ERROR;
		}else{
			return SUCCESS;
		}
	}

	private boolean exportCase(){
		File templantFile = new File(webPath
				+ ExportLCService.CASE_EXPORT_TEMPLANT);
		if (!templantFile.exists()) {
			logger.error("缺少案例导出模板文件");
			return false;
		} else {
			currentTimeStr = String.valueOf(System.currentTimeMillis());
			File currenExportPath = new File(webPath
					+ ExportLCService.CASE_EXPORT_PATH
					+ File.separator + currentTimeStr);
			if (!currenExportPath.exists()
					|| !currenExportPath.isDirectory()) {
				currenExportPath.mkdirs();
			}
			String[] idsArray =  ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				CaseObject co = cmService.findCaseById(Long.valueOf(ids
						.split(",")[i]));
				
				String coExportFilePath = currenExportPath
						.getAbsolutePath()+ File.separator  + ConstantManage.CASEOBJECTTYPE + co.getId() + ".doc";
				
				Map<String, String> dataMap = exportLCService
						.getExportMap(co);
				
				AllRelationWapper allRelation = cmService.findRelation(
						Long.valueOf(idsArray[i]),
						ConstantManage.CASEOBJECTTYPE);
				Map<String, String> relationDataMap = exportLCService
						.getExportMap(allRelation);
				
				dataMap.putAll(relationDataMap);
				
				Map<String, String> picDataMap = exportLCService
						.getExportPicMap(co);
				
				try {
					WriteToWordService wservice = new WriteToWordService();
					wservice.writeToWord(
							templantFile.getAbsolutePath(),
							coExportFilePath, dataMap,picDataMap,null);
				} catch (Exception e) {
					logger.error(ConstantManage.CASEOBJECTTYPE+":id" + co.getId() + " - "
							+ co.getCaseName() + "，doc文件生成失败");
					e.printStackTrace();
					return false;
				}
			}
			FilesToZip.fileToZip(currenExportPath.getAbsolutePath(),
					currenExportPath.getAbsolutePath(),"Cases");
		}
		return true;
	}
	
	private boolean exportOrgan(){
		File templantFile = new File(webPath
				+ ExportLCService.ORGAN_EXPORT_TEMPLANT);
		if (!templantFile.exists()) {
			logger.error("缺少组织导出模板文件");
			return false;
		} else {
			currentTimeStr = String.valueOf(System.currentTimeMillis());
			File currenExportPath = new File(webPath
					+ ExportLCService.ORGAN_EXPORT_PATH
					+ File.separator + currentTimeStr);
			if (!currenExportPath.exists()
					|| !currenExportPath.isDirectory()) {
				currenExportPath.mkdirs();
			}
			String[] idsArray =  ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				Organizationobject orgObj = omService.findOrgById(Long.valueOf(ids
						.split(",")[i]));
				String coExportFilePath = currenExportPath
						.getAbsolutePath()+ File.separator  + ConstantManage.ORGANIZATIONOBJECTTYPE + orgObj.getId() + ".doc";
				Map<String, String> dataMap = exportLCService
						.getExportMap(orgObj);
				AllRelationWapper allRelation = arService.getAllRelationForType(idsArray[i],
						ConstantManage.ORGANIZATIONOBJECTTYPE);
				Map<String, String> relationDataMap = exportLCService
						.getExportMap(allRelation);
				dataMap.putAll(relationDataMap);
				Map<String, String> picDataMap = exportLCService
						.getExportPicMap(orgObj);
				try {
					WriteToWordService wservice = new WriteToWordService();
					wservice.writeToWord(
							templantFile.getAbsolutePath(),
							coExportFilePath, dataMap,picDataMap,null);
				} catch (Exception e) {
					logger.error(ConstantManage.ORGANIZATIONOBJECTTYPE +":id" + orgObj.getId() + " - "
							+ orgObj.getOrgCName() + "，doc文件生成失败");
					e.printStackTrace();
					return false;
				}
			}
			FilesToZip.fileToZip(currenExportPath.getAbsolutePath(),
					currenExportPath.getAbsolutePath(),"Organizations");
		}
		return true;
	}

	private boolean exportFileObj(){
		File templantFile = new File(webPath
				+ ExportLCService.FILEOBJ_EXPORT_TEMPLANT);
		if (!templantFile.exists()) {
			logger.error("缺少文件导出模板文件");
			return false;
		} else {
			currentTimeStr = String.valueOf(System.currentTimeMillis());
			File currenExportPath = new File(webPath
					+ ExportLCService.FILEOBJ_EXPORT_PATH
					+ File.separator + currentTimeStr);
			if (!currenExportPath.exists()
					|| !currenExportPath.isDirectory()) {
				currenExportPath.mkdirs();
			}
			String[] idsArray =  ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				FilesObject fileObj = fmService.getFileinfoByid(Long.valueOf(ids.split(",")[i]));
				String coExportFilePath = currenExportPath
						.getAbsolutePath()+ File.separator  + ConstantManage.FILEOBJECTTYPE + fileObj.getId() + ".doc";
				Map<String, String> dataMap = exportLCService
						.getExportMap(fileObj);
				AllRelationWapper allRelation = arService.getAllRelationForType(idsArray[i],
						ConstantManage.FILEOBJECTTYPE);
				Map<String, String> relationDataMap = exportLCService
						.getExportMap(allRelation);
				dataMap.putAll(relationDataMap);
				try {
					WriteToWordService wservice = new WriteToWordService();
					wservice.writeToWord(
							templantFile.getAbsolutePath(),
							coExportFilePath, dataMap,null,null);
				} catch (Exception e) {
					logger.error(ConstantManage.FILEOBJECTTYPE +":id" + fileObj.getId() + " - "
							+ fileObj.getFileName() + "，doc文件生成失败");
					e.printStackTrace();
					return false;
				}
			}
			FilesToZip.fileToZip(currenExportPath.getAbsolutePath(),
					currenExportPath.getAbsolutePath(),"Files");
		}
		return true;
	}
	
	private boolean exportPeopleObj(){
		File templantFile = new File(webPath
				+ ExportLCService.PEOPLE_EXPORT_TEMPLANT);
		if (!templantFile.exists()) {
			logger.error("缺少人员导出模板文件");
			return false;
		} else {
			currentTimeStr = String.valueOf(System.currentTimeMillis());
			File currenExportPath = new File(webPath
					+ ExportLCService.PEOPLE_EXPORT_PATH
					+ File.separator + currentTimeStr);
			if (!currenExportPath.exists()
					|| !currenExportPath.isDirectory()) {
				currenExportPath.mkdirs();
			}
			String[] idsArray =  ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				Peopleobject peopleObj = pmService.getPeopleinfoByid(Long.valueOf(ids.split(",")[i]));
				String coExportFilePath = currenExportPath
						.getAbsolutePath()+ File.separator  + ConstantManage.PEOPLEOBJECTTYPE + peopleObj.getId() + ".doc";
				Map<String, String> dataMap = exportLCService
						.getExportMap(peopleObj);
				AllRelationWapper allRelation = arService.getAllRelationForType(idsArray[i],
						ConstantManage.PEOPLEOBJECTTYPE);
				Map<String, String> relationDataMap = exportLCService
						.getExportMap(allRelation);
				dataMap.putAll(relationDataMap);
				Map<String, String> picDataMap = exportLCService
						.getExportPicMap(peopleObj);
				Map<Integer, List<String[]>> tableDataMap = exportLCService
						.getExportPTableMap(peopleObj.getId());
				try {
					WriteToWordService wservice = new WriteToWordService();
					wservice.writeToWord(
							templantFile.getAbsolutePath(),
							coExportFilePath, dataMap,picDataMap,tableDataMap);
				} catch (Exception e) {
					logger.error(ConstantManage.PEOPLEOBJECTTYPE +":id" + peopleObj.getId() + " - "
							+ peopleObj.getPocnname() + "，doc文件生成失败");
					e.printStackTrace();
					return false;
				}
			}
			FilesToZip.fileToZip(currenExportPath.getAbsolutePath(),
					currenExportPath.getAbsolutePath(),"Peopels");
		}
		return true;
	}

	private boolean exportHostObj(){
		File templantFile = new File(webPath
				+ ExportLCService.HOST_EXPORT_TEMPLANT);
		if (!templantFile.exists()) {
			logger.error("缺少主机导出模板文件");
			return false;
		} else {
			currentTimeStr = String.valueOf(System.currentTimeMillis());
			File currenExportPath = new File(webPath
					+ ExportLCService.HOST_EXPORT_PATH
					+ File.separator + currentTimeStr);
			if (!currenExportPath.exists()
					|| !currenExportPath.isDirectory()) {
				currenExportPath.mkdirs();
			}
			String[] idsArray =  ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				HostsObject hostObj = hmService.searchDetails(Long.valueOf(ids.split(",")[i]));
				String coExportFilePath = currenExportPath
						.getAbsolutePath()+ File.separator  + ConstantManage.HOSTOBJECTTYPE + hostObj.getId() + ".doc";
				Map<String, String> dataMap = exportLCService
						.getExportMap(hostObj);
				AllRelationWapper allRelation = arService.getAllRelationForType(idsArray[i],
						ConstantManage.HOSTOBJECTTYPE);
				Map<String, String> relationDataMap = exportLCService
						.getExportMap(allRelation);
				dataMap.putAll(relationDataMap);
				Map<Integer, List<String[]>> tableDataMap = exportLCService
						.getExportHTableMap(hostObj.getId());
				try {
					WriteToWordService wservice = new WriteToWordService();
					wservice.writeToWord(
							templantFile.getAbsolutePath(),
							coExportFilePath, dataMap,null,tableDataMap);
				} catch (Exception e) {
					logger.error(ConstantManage.HOSTOBJECTTYPE +":id" + hostObj.getId() + " - "
							+ hostObj.getHostName() + "，doc文件生成失败");
					e.printStackTrace();
					return false;
				}
			}
			FilesToZip.fileToZip(currenExportPath.getAbsolutePath(),
					currenExportPath.getAbsolutePath(),"Hosts");
		}
		return true;
	}
	
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	public String getCurrentTimeStr() {
		return currentTimeStr;
	}


	public void setCurrentTimeStr(String currentTimeStr) {
		this.currentTimeStr = currentTimeStr;
	}


	public void setLawCaseType(String lawCaseType) {
		this.lawCaseType = lawCaseType;
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


	public void setArService(AllRelationService arService) {
		this.arService = arService;
	}


	public void setExportLCService(ExportLCService exportLCService) {
		this.exportLCService = exportLCService;
	}

	
}
