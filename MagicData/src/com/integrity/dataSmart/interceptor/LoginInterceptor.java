package com.integrity.dataSmart.interceptor;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.system.logManage.bean.LogObject;
import com.integrity.system.logManage.service.LogManageService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(LoginInterceptor.class);
	private static String AJAX_SESSION_TIME_OUT = null;//session失效
	private static String LICENSE_EXPIRED = null;//证书过期
	
	static  
    { 
		AJAX_SESSION_TIME_OUT = "ajaxSessionTimeOut";
		LICENSE_EXPIRED = "licenseExpired";
    }

	private LogManageService logManageService;

	public LogManageService getLogManageService() {
		return logManageService;
	}

	public void setLogManageService(LogManageService logManageService) {
		this.logManageService = logManageService;
	}

	Properties pps = new Properties();
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected String doIntercept(ActionInvocation Invacotion) throws Exception {
		ServletContext prop = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		//验证授权 
		try {
			boolean applicationFalg = (boolean) prop.getAttribute("solrandtitan");
			/*if (!applicationFalg) {
				session.removeAttribute("username");
			    session.removeAttribute("permissionList");
				if(isAjaxRequest(request)){
					PrintWriter printWriter = response.getWriter();
					printWriter.print(LICENSE_EXPIRED);
					printWriter.flush();
					printWriter.close();
					return null;
				}else{
					return "auth";
				}
			}*/
		} catch (Exception e) {
			logger.error("applicationFalg!!!", e);
			return "auth";
		}
		
		Properties prodatas = (Properties) prop.getAttribute("logUrlTypes");
		String username = (String)session.getAttribute("username");
		String url = getRequestURL(request);
		/*
		 * System.out.println("请求路径："+url); Map<String, String[]> mapss =
		 * request.getParameterMap(); if (mapss != null && !mapss.isEmpty()) {
		 * for(Entry<String , String[]> entry : mapss.entrySet()){ String k =
		 * entry.getKey(); String Va =
		 * StringUtils.join(entry.getValue()).replace(",", "");
		 * System.out.println("参数："+k+"="+Va); } }
		 */
		boolean isTrue = false;
		boolean isNull = true;
		String value = ":";
		if (username != null) {
			// 生成日志
			if (prodatas.getProperty(url) != null) {
				String pros = prodatas.getProperty(url);
				String[] arrays = pros.split(";");
				try {
					if (arrays.length == 3 && arrays[1] != null) {
						isNull = false;
						Map<String, String[]> maps = request.getParameterMap();
						int k = 0;
						String[] cs = arrays[1].split(",");
						if (cs[0].toLowerCase().indexOf("id") != -1&& maps.get(cs[0]) != null) {
							boolean isno = StringUtils.isNotEmpty(maps.get(cs[0])[0]);
							boolean zero = !maps.get(cs[0])[0].equals("0");
							if (isno && zero) {
								k = 1;
								isTrue = true;
							}
						}
						for (int i = k; i < cs.length; i++) {
							if (maps != null && !maps.isEmpty()) {
								for (Entry<String, String[]> entry : maps.entrySet()) {
									if (cs[i].equals(entry.getKey())) {
										String V = StringUtils.join(entry.getValue()).replace(",","");
										String K = entry.getKey();
										if (K.lastIndexOf(".") != -1) {
											K = K.substring(K.lastIndexOf(".") + 1);
										}
										if (StringUtils.isNotEmpty(V)) {
											value += K + "-" + V + " ";
										}
									}
								}
							}
						}
						saveLog(url, username.toString(), isTrue, isNull, value);
					} else {
						saveLog(url, username.toString(), isTrue, isNull, value);
					}
				} catch (Exception e) {
					logger.error("日志记录异常！", e);
				}
			}
			return Invacotion.invoke();
		} else {
			if(isAjaxRequest(request)){
				PrintWriter printWriter = response.getWriter();
				printWriter.print(AJAX_SESSION_TIME_OUT);
				printWriter.flush();
				printWriter.close();
				return null;
			}else{
				return "login";
			}
		}
	}

	public String getRequestURL(HttpServletRequest request) {
		String url = request.getRequestURI().substring(
				request.getContextPath().length() + 1,
				request.getRequestURI().length() - 7); // 请求协议 http 或 https
		return url;
	}
	
	public boolean isAjaxRequest(HttpServletRequest request) {  
        String header = request.getHeader("X-Requested-With");  
        if (header != null && "XMLHttpRequest".equals(header))  
            return true;  
        else  
            return false;  
	}

	public void saveLog(String url, String username, boolean is, boolean nu,
			String value) {
		ServletContext prop = ServletActionContext.getServletContext();
		Properties prodatas = (Properties) prop.getAttribute("logUrlTypes");
		LogObject logObject = new LogObject();
		if (nu) {
			logObject.setOperation(prodatas.getProperty(url).split(";")[1]
					+ value);
		} else {
			logObject.setOperation(prodatas.getProperty(url).split(";")[2]
					+ value);
		}
		if (is) {
			String opt = prodatas.getProperty(url).split(";")[0];
			if (opt.equals("3") || opt.equals("4")) {
				logObject.setOperationType("operationType00"
						+ prodatas.getProperty(url).split(";")[0]);
			} else {
				logObject.setOperationType("operationType002");
			}

		} else {
			logObject.setOperationType("operationType00"
					+ prodatas.getProperty(url).split(";")[0]);
		}
		logObject.setUserName(username);
		logObject.setCreatTime(new Date());
		logManageService.savelog(logObject);
	}

}
