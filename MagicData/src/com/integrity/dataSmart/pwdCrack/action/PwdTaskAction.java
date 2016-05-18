package com.integrity.dataSmart.pwdCrack.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.pwdCrack.bean.PwdTask;
import com.integrity.dataSmart.pwdCrack.service.PwdRunTaskService;
import com.integrity.dataSmart.pwdCrack.service.PwdTaskService;
import com.integrity.login.util.PageInfo;
import com.opensymphony.xwork2.ActionSupport;

public class PwdTaskAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private PwdTaskService ptService;
	private PwdRunTaskService prtService;
	
	private static String dataPath = ServletActionContext.getServletContext().getRealPath("/data/");
	/**
	 * 分页对象
	 */
	private PageInfo pi;
	
	/** jqgrid排序*/
	private String sidx;
	
	/** jqgrid排序*/
	private String sord;
	
	/**
	 * 需要删除的ids
	 */
	private String delids;
	
	/**
	 * 需要运行的任务ID
	 */
	private String runId;
	
	/**
	 * 返回的执行信息
	 */
	private String returnResult;
	
	private String pwdPencent;
	
	/**
	 * 跳转至任务列表页面
	 * @return
	 */
	public String getPTList(){
		return SUCCESS;
	}
	
	/**
	 * 获得任务分页列表
	 * @return
	 */
	public String getPTListByPage(){
		pi.setSidx(sidx);
		pi.setSord(sord);
		pi = ptService.getPTListByPage(pi);
		return SUCCESS;
	}
	
	/**
	 * 删除任务
	 * @return
	 */
	public String deletePTById(){
		if(null!=delids&&!"".equals(delids)){
			String[] delidsArray = delids.split(",");
			if(null!=delidsArray&&delidsArray.length>0){
				boolean runningFlag = ptService.deletePwdTaskList(delidsArray);
				if(runningFlag){
					returnResult = "删除成功(除正在执行的任务外)";
				}else{
					returnResult = "删除成功";
				}
			}else{
				returnResult = "请正确选择一个或多个密码破解任务";
			}
		}else{
			returnResult = "请正确选择一个或多个密码破解任务";
		}
		return SUCCESS;
	}
	
	/**
	 * 运行密码破解任务
	 * @return
	 */
	public String runPwdTask(){
		PwdTask pt = ptService.getPwdTaskById(runId);
		//检查任务在数据库中是否存在
		if(null==pt){
			returnResult = "该破解任务不存在";
			return SUCCESS;
		}
		// 检查当前运行的任务数是否已达到最大线程数
		Integer runningCount = prtService.getRunningCount();
		if(null==runningCount||runningCount>PwdRunTaskService.MAX_RUNNING_COUNT){
			returnResult = "当前运行破解任务的线程数已达到最大";
			return SUCCESS;
		}
		prtService.updateRunPT(pt,dataPath);
		returnResult = "SUCCESS";
		return SUCCESS;
	}
	
	public String  getRunningPencent(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		pwdPencent = "";
		if(null!=id){
			PwdTask pt = ptService.getPwdTaskById(id);
			if(null!=pt){
				Integer runStatus = pt.getRunStatus();
				if(runStatus.intValue()==PwdTaskService.RUNERROR){
					pwdPencent = "-1";
				}else{
					Integer  pInt = pt.getRunPercent();
					if(null==pInt){
						pwdPencent = "0";
					}else{
						pwdPencent = pt.getRunPercent().toString();
					}
				}
			}
		}
		return SUCCESS;
	}

	public void setPtService(PwdTaskService ptService) {
		this.ptService = ptService;
	}

	public void setPrtService(PwdRunTaskService prtService) {
		this.prtService = prtService;
	}

	public PageInfo getPi() {
		return pi;
	}

	public void setPi(PageInfo pi) {
		this.pi = pi;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getDelids() {
		return delids;
	}

	public void setDelids(String delids) {
		this.delids = delids;
	}

	public String getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public String getPwdPencent() {
		return pwdPencent;
	}

	public void setPwdPencent(String pwdPencent) {
		this.pwdPencent = pwdPencent;
	}
	
	
}
