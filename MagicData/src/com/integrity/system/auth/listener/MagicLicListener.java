package com.integrity.system.auth.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.integrity.system.auth.listener.MagicLicThread;

public class MagicLicListener  implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent event) {    
		MagicLicThread mt = new MagicLicThread(event);
		Thread t = new Thread(mt);
		t.start();
    }
    public void contextDestroyed(ServletContextEvent event) {}
}
