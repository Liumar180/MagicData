package com.integrity.lawCase.organizationManage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.organizationManage.service.OrgEditService;
import com.integrity.lawCase.organizationManage.service.OrgInfoManageService;
import com.integrity.lawCase.relation.pojo.RelationAction;
import com.integrity.lawCase.util.PageSetValueUtil;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class OrgEditManagerAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrgEditManagerAction.class);
	
	private OrgEditService orgEditService;
	private OrgInfoManageService orgInfoManageService;
	private UserService userService;
	/**用于编辑、删除的id参数*/
	private String id;
	private String rootType;
	private Organizationobject orgObj;
	private String rootId;
	private String skipFlag;
	/**图片上传*/
	private File orgImg;
	private String orgImgFileName;
	private String orgImgFileContentType;
	private String imgTimeNum;
	
	
	/**
	 * 删除单个组织及其关联对象(用于卡片删除)
	 * @return
	 */
	public String deleteOrg(){
		if(null!=id&&!"".equals(id.trim())){
			try {
				orgEditService.deleteOrg(Long.parseLong(id));
			} catch (Exception e) {
				logger.error("删除单个组织异常",e);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 跳转至添加组织页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String viewOrgAdd(){
		ServletContext sc = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();		
		Map<String,Map<String,String>> dicMap = (Map<String, Map<String, String>>) sc.getAttribute(ConstantManage.DATADICTIONARY);
		Map<String,String> direction = dicMap.get(ConstantManage.DIRECTION);
		try {
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
			
			//多选下拉框使用
			PageSetValueUtil.selectMoreSet(request,userList,direction);
			
			//关联对象相关
			if(StringUtils.isNotBlank(rootId)){
				request.setAttribute("rootId", rootId);
				request.setAttribute("targetType", ConstantManage.ORGANIZATIONOBJECTTYPE);
				request.setAttribute("rootType", rootType);
			}
		} catch (Exception e) {
			logger.error("查询user列表异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改案件页面
	 */
	@SuppressWarnings("unchecked")
	public String viewOrgUpdate(){
		ServletContext sc = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();		
		Map<String,Map<String,String>> dicMap = (Map<String, Map<String, String>>) sc.getAttribute(ConstantManage.DATADICTIONARY);
		Map<String,String> direction = dicMap.get(ConstantManage.DIRECTION);
		try {
			request.setAttribute("skipFlag", skipFlag);
			orgObj = orgInfoManageService.findOrgById(orgObj.getId());
			orgInfoManageService.filledOrgDic(orgObj, dicMap);
			request.setAttribute("orgObject", orgObj);
			
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
			
			//多选下拉框使用
			PageSetValueUtil.selectMoreSet(request,userList,direction);
		} catch (Exception e) {
			logger.error("修改组织页面异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加组织（关联）
	 */
	@SuppressWarnings("unchecked")
	public String saveOrgRelation(){
		ServletContext sc = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();		
		Map<String,String> type_property = (Map<String, String>) sc.getAttribute(ConstantManage.TYPE_PROPERTY);
		String currentOrgImg = orgObj.getOrgImage();
		if(null!=currentOrgImg&&!"".equals(currentOrgImg.trim())){
			String subFile = currentOrgImg.substring(currentOrgImg.indexOf("."), currentOrgImg.length());
			String newFileName = "Org"+imgTimeNum+subFile;
			orgObj.setOrgImage(newFileName);
		}
		try {
			String imgDir = ServletActionContext.getServletContext().getRealPath("/images/lawCase/uploadImg/organization/");
			File imgDirFile = new File(imgDir); // 判断文件夹是否存在,如果不存在则创建文件夹
	        if (!imgDirFile.exists()) {
	        	imgDirFile.mkdirs();
	        }
			if(null!=orgImg&&null!=orgImgFileName){
				if(null==imgTimeNum&&"".equals(imgTimeNum.trim())){
					imgTimeNum = "";
				}
				String[] fileFit = new String[] { ".jpg",".png",".gif",".jpeg",".bmp" };
				boolean fitFlag = false;
				for (int i = 0; i < fileFit.length; i++) {
	                if (orgImgFileName.toLowerCase().endsWith(fileFit[i])) {
	                	fitFlag = true;
	                    break;
	                }
	            }
				if(fitFlag){
					InputStream is = new FileInputStream(orgImg);
					String subFile = orgImgFileName.substring(orgImgFileName.indexOf("."), orgImgFileName.length());
					String newFileName = "Org"+imgTimeNum+subFile;
			        File saveImg = new File(imgDir,newFileName);
			        if(!saveImg.exists()){
			        	saveImg.createNewFile();
			        	OutputStream os = new FileOutputStream(saveImg);	        
				        byte[] buffer = new byte[1024];
				        int length = 0;
			            while ((length = is.read(buffer)) != -1) {
			                os.write(buffer, 0, length);
			            }
				        os.close();
				        is.close();
			        }
				}
			}
			
			orgEditService.saveOrgRelation(orgObj,rootType,rootId);
			//跳转链接
			RelationAction relationAction = PageSetValueUtil.relationActionSet(type_property,rootType,rootId);
			request.setAttribute("relationAction", relationAction);
			request.setAttribute("rootType", rootType);
		} catch (Exception e) {
			logger.error("添加组织(关联)异常",e);
		}
		return SUCCESS;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public Organizationobject getOrgObj() {
		return orgObj;
	}

	public void setOrgObj(Organizationobject orgObj) {
		this.orgObj = orgObj;
	}

	public void setOrgEditService(OrgEditService orgEditService) {
		this.orgEditService = orgEditService;
	}

	public void setOrgInfoManageService(OrgInfoManageService orgInfoManageService) {
		this.orgInfoManageService = orgInfoManageService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getSkipFlag() {
		return skipFlag;
	}

	public void setSkipFlag(String skipFlag) {
		this.skipFlag = skipFlag;
	}

	public String getImgTimeNum() {
		return imgTimeNum;
	}

	public void setImgTimeNum(String imgTimeNum) {
		this.imgTimeNum = imgTimeNum;
	}

	public File getOrgImg() {
		return orgImg;
	}

	public void setOrgImg(File orgImg) {
		this.orgImg = orgImg;
	}

	public String getOrgImgFileName() {
		return orgImgFileName;
	}

	public void setOrgImgFileName(String orgImgFileName) {
		this.orgImgFileName = orgImgFileName;
	}

	public String getOrgImgFileContentType() {
		return orgImgFileContentType;
	}

	public void setOrgImgFileContentType(String orgImgFileContentType) {
		this.orgImgFileContentType = orgImgFileContentType;
	}

}
