package com.integrity.dataSmart.menu.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.MenuKey;
import com.integrity.dataSmart.menu.pojo.MenuPojo;
import com.integrity.dataSmart.menu.service.MenuManagerService;
import com.opensymphony.xwork2.ActionSupport;


public class MenuManageAction extends ActionSupport{
	
	private List<MenuPojo> menuList;
	private MenuManagerService menuManagerService;
	private static String dataPath = ServletActionContext.getServletContext().getRealPath("/data/");
	private static String type;
	private String showMenuFlag;
	private InputStream inputStream;
	private List<String> imgNames;

	public String getMenuShowFlag(){
		HttpSession hs = ServletActionContext.getRequest().getSession();
		if(null!=hs.getAttribute(MenuKey.MENU_SHOW_FLAGKEY)){
			showMenuFlag = hs.getAttribute(MenuKey.MENU_SHOW_FLAGKEY).toString();
		}else{
			showMenuFlag = MenuKey.MENU_UNSHOW_FLAG;
		}
		this.setShowMenuFlag(showMenuFlag);
		/*修改json传输方式*/
		try {
			inputStream = new ByteArrayInputStream(showMenuFlag.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getMenuData(){
		menuList = menuManagerService.getMenuList(dataPath, type);
		ServletActionContext.getRequest().setAttribute("menuList", menuList);
		this.setMenuList(menuList);
		HttpServletRequest request = ServletActionContext.getRequest();
		String webRealPath = ServletActionContext.getServletContext().getRealPath("/");
		imgNames = menuManagerService.getImpNames(webRealPath);
		ServletActionContext.getRequest().setAttribute("imgNames", imgNames);
		return SUCCESS;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<MenuPojo> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuPojo> menuList) {
		this.menuList = menuList;
	}

	public MenuManagerService getMenuManagerService() {
		return menuManagerService;
	}

	public void setMenuManagerService(MenuManagerService menuManagerService) {
		this.menuManagerService = menuManagerService;
	}

	public String getShowMenuFlag() {
		return showMenuFlag;
	}

	public void setShowMenuFlag(String showMenuFlag) {
		this.showMenuFlag = showMenuFlag;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getImgNames() {
		return imgNames;
	}

	public void setImgNames(List<String> imgNames) {
		this.imgNames = imgNames;
	}
	
	
}
