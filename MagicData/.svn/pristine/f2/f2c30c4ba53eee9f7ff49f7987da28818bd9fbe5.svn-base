package com.integrity.lawCase.exportLaw.service;

import java.util.ArrayList;
import java.util.List;

import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.service.CaseManageService;
import com.integrity.lawCase.exportLaw.bean.CaseObjBean;

public class ExportLCExcelService {

	private CaseManageService cmService;

	public CaseManageService getCmService() {
		return cmService;
	}

	public void setCmService(CaseManageService cmService) {
		this.cmService = cmService;
	}

	public List<CaseObjBean> getExportList(String ids) {
		List<CaseObjBean> cobList = new ArrayList<CaseObjBean>();
		String[] idsArray =  ids.split(",");
		for (int i = 0; i < idsArray.length; i++) {
			CaseObject co = cmService.findCaseById(Long.valueOf(ids.split(",")[i]));
			CaseObjBean cob = coToCob(co);
			cobList.add(cob);
		}
		return cobList;
	}

	private CaseObjBean coToCob(CaseObject co) {
		CaseObjBean cob = new CaseObjBean();
		cob.setCaseName(co.getCaseName());
		cob.setDateStr(co.getDateStr());
		cob.setCaseLevelName(co.getCaseLevelName());
		cob.setCaseLeader(co.getCaseLeader());
		cob.setCaseSupervisor(co.getCaseSupervisor());
		cob.setCaseUserNames(co.getCaseUserNames());
		cob.setCaseStatusName(co.getCaseStatusName());
		cob.setDirectionName(co.getDirectionName());
		cob.setCaseAim(co.getCaseAim());
		cob.setMemo(co.getMemo());
		return cob;
	}
	
	
	
	

}
