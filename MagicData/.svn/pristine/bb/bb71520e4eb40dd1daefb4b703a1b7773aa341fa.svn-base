package com.integrity.dataSmart.pwdCrack.action;

import com.integrity.dataSmart.pwdCrack.service.PwdTaskService;
import com.opensymphony.xwork2.ActionSupport;

public class PwdTaskAddAction  extends ActionSupport{

	private PwdTaskService ptService;
	
	/**
	 * 返回的执行信息
	 */
	private String returnResult;
	
	/**
	 * 新增信息用
	 */
	private PwdTaskForm ptForm;
	
	public String addPwdTask(){
		if(null==ptForm){
			returnResult = "请选择正确的任务构建信息";
			return SUCCESS;
		}
		String formUuid = ptForm.getUuid();
		String formUserName = ptForm.getUserName();
		String formPwdEncrypt = ptForm.getPwdEncrypt();
		if(null==formUuid||"".equals(formUuid.trim())){
			returnResult = "密码破解任务【uuid】为空";
			return SUCCESS;
		}
		if(null==formUserName||"".equals(formUserName.trim())){
			returnResult = "密码破解任务【用户名】为空";
			return SUCCESS;
		}
		if(null==formPwdEncrypt||"".equals(formPwdEncrypt.trim())){
			returnResult = "密码破解任务【密码密文】为空";
			return SUCCESS;
		}
		//判断该任务是否存在
		boolean exsitFlag = ptService.getExsitPwdTask(formUuid, formUserName, formPwdEncrypt);
		if(exsitFlag){
			returnResult = "该密码的破解任务已经存在";
			return SUCCESS;
		}else{
			ptService.addPwdTask(formUuid, formUserName, formPwdEncrypt);
			returnResult = "添加密码【"+"formPwdEncrypt"+"】的破解任务";
			return SUCCESS;
		}
	}

	public String getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}

	public PwdTaskForm getPtForm() {
		return ptForm;
	}

	public void setPtForm(PwdTaskForm ptForm) {
		this.ptForm = ptForm;
	}

	public void setPtService(PwdTaskService ptService) {
		this.ptService = ptService;
	}
	
	
}
