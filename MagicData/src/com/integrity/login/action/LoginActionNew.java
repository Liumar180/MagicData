package com.integrity.login.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class LoginActionNew extends ActionSupport {
	private Logger logger = Logger.getLogger(LoginActionNew.class);
	private static final long serialVersionUID = 1L;
	private UserService userService;
	HttpSession session = ServletActionContext.getRequest().getSession();
	HttpServletRequest request = ServletActionContext.getRequest();

	private String username;
	private String pwd;
	private String confirmPwd;
	private String randNumInput;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getRandNumInput() {
		return randNumInput;
	}

	public void setRandNumInput(String randNumInput) {
		this.randNumInput = randNumInput;
	}
	
	/**
	 * 首页
	 */
	public String viewIndex() {
		return SUCCESS;
	}
	/**
	 * @return
	 * 数据魔方模块
	 */
	public String DataSmart(){
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("../config/postGreSql/postGreSql.properties");
			Properties p = new Properties();
			p.load(inputStream);
			String geoserverMapUrl = p.getProperty("geoserverMapUrl");     
			request.setAttribute("geoserverMapUrl", geoserverMapUrl);
			ServletContext sc = ServletActionContext.getServletContext();
			Map<String,List<String>> map = (Map<String, List<String>>) sc.getAttribute("vertexProperty");
			request.setAttribute("vertexPropertyJson", JacksonMapperUtil.getObjectMapper().writeValueAsString(map));
		} catch (IOException e) {
			logger.error("geoserverMapUrl read error", e);
		}finally{
			try {
				if(inputStream!=null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	/**
	 * 前台用户登录 autor:zhaoqj
	 */
	public String getUserInfo() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			if (username != null && pwd != null
					&& session.getAttribute("rand") != null) {
				map.put("username", username);
				map.put("pwd", pwd);
				AuthUser user = new AuthUser();
				user.setUserName(username);
				try {
					user.setPassword(Encryption.Encrypt(pwd));
				} catch (Exception e) {
					e.printStackTrace();
				}
				String rand = (String) session.getAttribute("rand");
				if (rand.equals(randNumInput)) {
					AuthUser userInfo = userService.findUser(user);
					if (userInfo!=null) {
						List<String> permissionList = userService.findPermissionListByUserId(userInfo.getId());
						session.setAttribute("username", username);
						//获取资源列表
						session.setAttribute("permissionList", permissionList);
						return SUCCESS;
					} else {
						map.put("resultMsg", "用户名或密码错误，如忘记密码，请联系管理员!");
					}
				} else {
					map.put("resultMsg", "验证码错误请重新输入!");
				}
			}else if(session.getAttribute("username") != null){
				return SUCCESS;
			}else {
				map.put("resultMsg", "您还未登录,请登录!");
			}
			map.put("resultjsp", "/MagicData/login.jsp");
			request.setAttribute("resultmap", map);
		} catch (Exception e) {
			logger.error("登录异常", e);
		}
		return "inter";
	}
	


	/**
	 * 退出登录
	 */
	public String quit(){
		session.removeAttribute("username");
	    session.removeAttribute("permissionList");
	    session.invalidate();
		return SUCCESS;
	}
	
}
