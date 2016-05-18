package com.integrity.system.auth.listener;

import javax.servlet.ServletContextEvent;

import com.integrity.system.auth.service.MagicLicService;

public class MagicLicThread implements Runnable {

	private ServletContextEvent event;
	
	public MagicLicThread(ServletContextEvent event) {
		super();
		this.event = event;
	}

	public void run() {
		do{
			//获得本地项目路径
			event.getServletContext().setAttribute("solrandtitan", false);
			String projectPath= event.getServletContext().getRealPath("/");
			boolean result = MagicLicService.licVerify(projectPath);
			event.getServletContext().setAttribute("solrandtitan", result);
			try {
				Thread.sleep(60*60*1000L);
//				wait(60*60*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while(true);
	}

}
