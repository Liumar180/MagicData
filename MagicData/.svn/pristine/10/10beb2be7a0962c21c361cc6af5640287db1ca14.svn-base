package com.integrity.system.auth.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.system.auth.service.MagicLicService;
import com.integrity.system.auth.service.PermissionService;
import com.opensymphony.xwork2.ActionSupport;

public class PermissionAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(PermissionAction.class);
	private PermissionService permissionService;
	private List<Map<String,String>> treeData;
	private Long roleId;
	
	/**license上传*/
	private File license;
	private String licenseFileName;
	private Map<String,Object> dataMap;
	
	/**
	 * 获取资源树
	 * @return
	 */
	public String loadPermissionTree(){
		try {
			treeData = permissionService.getPermissionTree(roleId);
		} catch (Exception e) {
			logger.error("获取资源树异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 上传license
	 * @return String
	 */
	public String licenseUpload(){
		String licenseDir = ServletActionContext.getServletContext().getRealPath(File.separator);
		try{
			File licenseDirFile = new File(licenseDir); // 判断文件夹是否存在,如果不存在则创建文件夹
	        if (!licenseDirFile.exists()) {
	        	licenseDirFile.mkdirs();
	        }
			if(null!=license){
				boolean fitFlag = false;
                if (licenseFileName.toLowerCase().endsWith("lic")) {
                	fitFlag = true;
                }	
				if(fitFlag){
					InputStream is = new FileInputStream(license);
			        File saveLicense = new File(licenseDir,licenseFileName);
			        if(saveLicense.exists()){
			        	//如果文件存在先删除
			        	saveLicense.delete();
			        }
			        saveLicense.createNewFile();
		        	OutputStream os = new FileOutputStream(saveLicense);	        
			        byte[] buffer = new byte[1024];
			        int length = 0;
		            while ((length = is.read(buffer)) != -1) {
		                os.write(buffer, 0, length);
		            }
			        os.close();
			        is.close();
			        dataMap = new HashMap<String,Object>();
			        dataMap.put("success", true);
			        dataMap.put("message", "上传成功！");
			        //验证授权
			        ServletContext sc = ServletActionContext.getServletContext();
			        String projectPath= sc.getRealPath("/");
					boolean result = MagicLicService.licVerify(projectPath);
					sc.setAttribute("solrandtitan", result);
				}else{
					dataMap = new HashMap<String,Object>();
					dataMap.put("success", false);
					dataMap.put("message", "该文件不是license文件，请重新上传！");
					return SUCCESS;
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public List<Map<String, String>> getTreeData() {
		return treeData;
	}

	public void setTreeData(List<Map<String, String>> treeData) {
		this.treeData = treeData;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public File getLicense() {
		return license;
	}

	public void setLicense(File license) {
		this.license = license;
	}

	public String getLicenseFileName() {
		return licenseFileName;
	}

	public void setLicenseFileName(String licenseFileName) {
		this.licenseFileName = licenseFileName;
	}

	
	
}
