package com.integrity.system.reset.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.system.reset.service.ResetService;
import com.opensymphony.xwork2.ActionSupport;

public class ResetAction extends ActionSupport{

	private Logger logger = Logger.getLogger(ResetAction.class);
	private static final long serialVersionUID = 1L;
	private ResetService resetService;
	private boolean flag;
	
	/**
	 * 回复出厂页面
	 * @return
	 */
	public String viewResetPage(){
		return SUCCESS;
	}
	
	/**
	 * 恢复出厂设置
	 * @return
	 */
	public String resetData(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String username = (String) request.getSession().getAttribute("username");
			if ("admin".equals(username)) {
				resetService.resetData();
				flag = true;
				logger.error("用户：admin---回复出厂设置---！");
			}else {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			logger.error("恢复出厂设置异常",e);
		}
		return SUCCESS;
	}
	
	
	
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setResetService(ResetService resetService) {
		this.resetService = resetService;
	}
	

}
