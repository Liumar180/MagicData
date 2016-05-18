package com.integrity.lawCase.exportLaw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.dao.CaseManageDao;
import com.integrity.lawCase.common.bean.DictionaryTable;
import com.integrity.lawCase.common.init.dao.DictionaryDao;

public class ImportLCService {
	private CaseManageDao caseManageDao;
	private DictionaryDao dictionaryDao;

	public CaseManageDao getCaseManageDao() {
		return caseManageDao;
	}

	public void setCaseManageDao(CaseManageDao caseManageDao) {
		this.caseManageDao = caseManageDao;
	}

	public DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
	
	/**
	 * 案件入库
	 * @param list
	 * @return
	 */
	public Map<String, Object> addCases(List<CaseObject> list) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<CaseObject> failList = new ArrayList<CaseObject>();
		List<CaseObject> l = formCasesList(list);
		int successNum = 0;
		int failNum = 0;
		for(int i=0;i<l.size();i++){
			CaseObject c = l.get(i);
			caseNameTrim(c);
			if(!"".equals(c.getCaseName())&&c.getCreateTime()!=null){
				List<CaseObject> coList = caseManageDao.findCaseByNameExact(c.getCaseName());
				if(coList!=null&&coList.size()>0){
					failNum++;
					failList.add(c);
				}else{
					caseManageDao.getHibernateTemplate().save(c);
				}
			}else{
				failNum++;
				failList.add(c);
			}
		}
		successNum = list.size()-failNum;
		map.put("success", true);
		map.put("message", "共"+list.size()+"条记录，"+"成功导入"+successNum+"条，失败"+failNum+"条。");
		map.put("failList",failList);
		return map;
	}
	
	/**
	 * 构造案件集合
	 * @param list
	 * @return
	 */
	private List<CaseObject> formCasesList(List<CaseObject> list) {
		List<CaseObject> l = new ArrayList<CaseObject>();
		DictionaryTable dt1 = dictionaryDao.findDicByName("重要级别");
		Long pid1 = dt1.getId();
		DictionaryTable dt2 = dictionaryDao.findDicByName("案件状态");
		Long pid2 = dt2.getId();
		DictionaryTable dt3 = dictionaryDao.findDicByName("所属方向");
		Long pid3 = dt3.getId();
		for(CaseObject c : list){
			String caseLevelName = c.getCaseLevelName();
			String caseStatusName = c.getCaseStatusName();
			String directionName = c.getDirectionName();
			DictionaryTable dt = dictionaryDao.findDicByNameAndPid(caseLevelName,pid1);
			if(dt!=null){
				c.setCaseLevel(dt.getCode());
			}
			dt = dictionaryDao.findDicByNameAndPid(caseStatusName,pid2);
			if(dt!=null){
				c.setCaseStatus(dt.getCode());
			}
			dt = dictionaryDao.findDicByNameAndPid(directionName,pid3);
			if(dt!=null){
				c.setDirectionCode(dt.getCode());
			}
			l.add(c);
		}
		return l;
	}

	/**
	 * 去除名称首尾空格
	 * @param caseObject
	 */
	private void caseNameTrim(CaseObject caseObject){
		caseObject.setCaseName(caseObject.getCaseName().trim());
	}

	

}
