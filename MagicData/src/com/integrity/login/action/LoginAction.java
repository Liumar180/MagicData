package com.integrity.login.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.login.bean.User;
import com.integrity.login.service.UserService;
import com.integrity.login.util.PageInfo;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private Logger logger = Logger.getLogger(LoginAction.class);
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
		Map<String, String> map = new HashMap<String, String>();
		if (username != null && pwd != null
				&& session.getAttribute("rand") != null) {
			User user = new User();
			user.setUserName(username);
			try {
				user.setPassword(Encryption.Encrypt(pwd));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String rand = (String) session.getAttribute("rand");
			if (rand.equals(randNumInput)) {
				User userInfo = userService.findUser(user);
				if (userInfo!=null) {
					session.setAttribute("username", username);
					session.setAttribute("roleType", userInfo.getRoleType());
					return SUCCESS;
				} else {
					map.put("resultMsg", "用户名或密码错误请重新登录!");
				}
			} else {
				map.put("resultMsg", "验证码错误请重新登录!");
			}
		}else if(session.getAttribute("username") != null){
			return SUCCESS;
		}else {
			map.put("resultMsg", "您还未登录,请登录!");
		}
		map.put("resultjsp", "/MagicData/login.jsp");
		request.setAttribute("resultmap", map);
		return "inter";
	}
	
	/**
	 * 注册业务处理 autor zhaoqj
	 */
	public String register() {
		String rand = (String) session.getAttribute("rand");
		if(!rand.equals(randNumInput)){
			Map<String, String> map = new HashMap<String, String>();
			map.put("resultMsg", "验证码错误，请重新确认!");
			map.put("resultjsp", "getAllUser.action");
			request.setAttribute("resultmap", map);
			return ERROR;
		}
		User user = new User();
		user.setUserName(this.username);
		try {
			user.setPassword(Encryption.Encrypt(this.pwd));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = userService.findCount();
		if (count <= 0) {
			user.setRoleType(1);
		} else {
			user.setRoleType(2);
		}
		Map<String, String> map = new HashMap<String, String>();
		HttpServletRequest request = ServletActionContext.getRequest();
		int flag = userService.saveUser(user);
		if (flag > 0) {
			map.put("resultMsg", "注册成功!");
			map.put("resultjsp", "getAllUser.action");
			request.setAttribute("resultmap", map);
			return ERROR;
		} else {
			if (flag == -1) {
				map.put("resultMsg", "用户名重复,请使用其他用户名注册!");
			} else {
				map.put("resultMsg", "注册失败，请联系管理员!");
			}
			map.put("resultjsp", "getAllUser.action");
			request.setAttribute("resultmap", map);
			return ERROR;
		}
	}

	/**
	 * 查询用户列表信息 author zhaoqj
	 */
	public String getAllUser() {
		// 接收pageNow参数
		String s_pageNow = request.getParameter("pageNo");
		String pageSizeInfo = request.getParameter("pageSize");
		String username = null;
		if (request.getParameter("username") != null) {
			username = request.getParameter("username");
		}
		int pageNo = 1;
		if (s_pageNow != null && !"".equals(s_pageNow)) {
			pageNo = Integer.parseInt(s_pageNow);
		}
		int pageSize = 10;
		if (pageSizeInfo != null && !"".equals(pageSizeInfo)) {
			pageSize = Integer.parseInt(pageSizeInfo);
		}
		PageInfo page = new PageInfo(pageNo, pageSize);

		List<User> userlist = userService.findAllUser(page, username);

		request.setAttribute("userlist", userlist);

		request.setAttribute("pageInfo", page);

		request.setAttribute("usernameInfo", username);

		return SUCCESS;
	}

	/**
	 * 查询用户列表信息 author zhaoqj
	 */
	public String deleteUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String idString = request.getParameter("id");
		int flag = userService.deleteUser(Integer.parseInt(idString));
		return SUCCESS;
	}

	/**
	 * 修改密码 author zhaoqj
	 * 
	 * @throws Exception
	 */
	public String modifyPwd(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String username = (String) session.getAttribute("username");
		Map<String, String> map = new HashMap<String, String>();
		if(username!=null && !"".equals(username)){
			int flag = 0;
			try {
				flag = userService.modifyPwd(username, Encryption.Encrypt(oldPwd),
						Encryption.Encrypt(newPwd));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag > 0) {
				map.put("resultMsg", "修改密码成功！");
				map.put("resultjsp", "index.action");
				request.setAttribute("resultmap", map);
				return "inter";
			} else {
				map.put("resultMsg", "您输入的原密码不正确！");
			}
		}else{
			map.put("resultMsg", "您还未登录，请登录");
		}
		map.put("resultjsp", "index.action");
		request.setAttribute("resultmap", map);
		return "inter";
	}
	/**
	 * 退出登录
	 */
	public String quit(){
		session.removeAttribute("username");
	    session.removeAttribute("roleType");
		return SUCCESS;
	}
	/**
	 * 修改状态
	 */
    public String modifyStatus(){
    	String status=request.getParameter("status");
    	String id=request.getParameter("id");
    	userService.modifyStatus(Integer.parseInt(id), Integer.parseInt(status));
    	return SUCCESS;
    }
}
