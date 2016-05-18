package com.integrity.system.logManage.service;

import java.util.List;




import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.system.logManage.bean.LogObject;
import com.integrity.system.logManage.dao.LogManageDao;



public class LogManageService {
	private LogManageDao logManageDao;

	public LogManageDao getLogManageDao() {
		return logManageDao;
	}

	public void setLogManageDao(LogManageDao logManageDao) {
		this.logManageDao = logManageDao;
	}
	
	public PageModel<LogObject> getAllLogObject(PageModel<LogObject> page,LogObject l2){
		List<LogObject> lst = logManageDao.getAllLogObject(page,l2);
		page.setTotalRecords(logManageDao.findRowCount(l2));
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
	}
	public void savelog(LogObject logObject){
		logManageDao.saveLog(logObject);
	}
}
