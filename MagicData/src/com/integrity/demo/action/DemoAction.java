package com.integrity.demo.action;

import org.apache.log4j.Logger;

import com.integrity.demo.bean.MyBean;
import com.integrity.demo.service.DemoService;
import com.opensymphony.xwork2.ActionSupport;

public class DemoAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(DemoAction.class);

	private String name;
	private int age;
	
	private DemoService<MyBean> demoService;
	
	public String excute(){
		try {
			MyBean myBean = new MyBean();
			myBean.setName(name);
			myBean.setAge(age);
			demoService.save(myBean);
		} catch (Exception e) {
			logger.error("异常",e);
		}
		return SUCCESS;
	}
	
	
	public void setDemoService(DemoService<MyBean> demoService) {
		this.demoService = demoService;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
