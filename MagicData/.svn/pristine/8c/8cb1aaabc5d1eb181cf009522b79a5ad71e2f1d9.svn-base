package com.integrity.dataSmart.pwdCrack.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.integrity.dataSmart.pwdCrack.bean.PwdTask;
import com.integrity.dataSmart.pwdCrack.dao.PwdTaskDao;
import com.integrity.login.util.PageInfo;

public class PwdTaskService {

	private PwdTaskDao pwdTaskDao;
	public final static Integer RUNNING = 2;
	public final static Integer UNRUN = 1;
	public final static Integer RUNFINISH = 0;
	public final static Integer RUNERROR = -1;
	
	/**
	 * 获得所有密码破解任务的分页列表
	 */
	public PageInfo getPTListByPage(PageInfo currentPageInfo){
		String hql = "from PwdTask";
		PageInfo resultPw = pwdTaskDao.gotoPage(hql, currentPageInfo);
		return resultPw;
	}
	
	/**
	 * 根据ID获得任务破解任务对象
	 * @param id
	 * @return
	 */
	public PwdTask getPwdTaskById(String id){
		return pwdTaskDao.findById(id);
	}
	
	/**
	 * 根据ID删除任务
	 * @param id
	 */
	public void deletePwdTaskById(String id){
		pwdTaskDao.deletePwdTaskById(id);
	}
	
	/**
	 * 根据选中的多个ID删除多个任务（正在执行的任务不能够删除）
	 * @param ids
	 */
	public boolean deletePwdTaskList(String[] ids){
		boolean runningFlag = false;
		if(null!=ids&&ids.length>0){
			for(String id:ids){
				PwdTask pt = pwdTaskDao.findById(id);
				if(null!=pt&&pt.getRunStatus() != PwdTaskService.RUNNING){
					pwdTaskDao.deletePwdTask(pt);
				}else{
					runningFlag = true;
				}
			}
		}	
		return runningFlag;
	}
	
	/**
	 * 判断此任务是否已经存在
	 * @param uuid
	 * @param userName
	 * @param pwdEncrypt
	 */
	public boolean getExsitPwdTask(String uuid,String userName,String pwdEncrypt){
		String hql = "from PwdTask t where t.uuid = '"+uuid+"' and t.userName = '"+userName+"' and t.pwdEncrypt ='"+pwdEncrypt+"'";
		List<?> resultList = pwdTaskDao.findByHQL(hql);
		if(null!=resultList&&resultList.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 新增密码破解任务
	 * @param uuid
	 * @param userName
	 * @param pwdEncrypt
	 */
	public void addPwdTask(String uuid,String userName,String pwdEncrypt){
		PwdTask pt = new PwdTask();
		pt.setUuid(uuid);
		pt.setUserName(userName);
		pt.setPwdEncrypt(pwdEncrypt);
		pt.setRunStatus(PwdTaskService.UNRUN);
		pt.setCreateTime(new Date(System.currentTimeMillis()));
		try{
			pwdTaskDao.addPwdTask(pt);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public PwdTaskDao getPwdTaskDao() {
		return pwdTaskDao;
	}

	public void setPwdTaskDao(PwdTaskDao pwdTaskDao) {
		this.pwdTaskDao = pwdTaskDao;
	}
	
}
