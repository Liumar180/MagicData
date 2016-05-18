package com.integrity.lawCase.organizationManage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.organizationManage.service.OrgEditService;
import com.integrity.lawCase.organizationManage.service.OrgInfoManageService;
import com.integrity.lawCase.relation.service.AllRelationService;
import com.opensymphony.xwork2.ActionSupport;

public class OrgEditManagerAjaxAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrgEditManagerAjaxAction.class);
	
	private OrgEditService orgEditService;
	/**关联关系Service*/
	private AllRelationService allRelationService;
	private String id;
	private String ids;
	private String rootId;
	private String rootType;
	private String relationType;
	private String relationId;
	private Organizationobject orgObj;
	private List<Organizationobject> orgList;
	private OrgInfoManageService orgInfoManageService;
	/**图片上传*/
	private File orgImg;
	private String orgImgFileName;
	private String orgImgFileContentType;
	private String imgTimeNum;
	/**用于组织重名判断*/
	private String checkName;
	private String checkNameId;
	private String nameExsitFlag;
	/**操作结果*/
	private String resultMess;

	/**
	 * 删除多个组织及其关联对象(用于列表删除)
	 * @return
	 */
	public String deleteOrgs(){
		try {
			orgEditService.deleteOrgs(ids);
		} catch (Exception e) {
			logger.error("删除多个组织及其关联对象异常",e);
		}
		return SUCCESS;
	}

	/**
	 * 删除关联对象
	 */
	public String deleteOrgRelation(){
		try {
			allRelationService.delRelation(id, ConstantManage.ORGANIZATIONOBJECTTYPE, relationId, relationType);
		} catch (Exception e) {
			logger.error("删除组织的关联对象异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 检查组织重名
	 */
	public String checkOrgNameExsit(){
		boolean exsitFlag = orgInfoManageService.checkOrgNameExsit(checkName,checkNameId);
		if(exsitFlag){
			nameExsitFlag = "T";
		}else{
			nameExsitFlag = "F";
		}
		return SUCCESS;
	}
	
	/**
	 * 根据name模糊查询案件
	 */
	public String findOrgsByName(){
		try {
			String name = orgObj.getOrgCName();
			if(null!=name&&!"".equals(name.trim())){
				name = name.trim();
				if(null!=rootId){
					Long rootLongId = Long.parseLong(rootId);
					if(rootLongId!=0&&null!=rootType&&!ConstantManage.ORGANIZATIONOBJECTTYPE.equals(rootType)){
						orgList = orgInfoManageService.findOrgByName(name, rootLongId);
					}else{
						orgList = orgInfoManageService.findOrgByName(name);
					}
				}else{
					orgList = orgInfoManageService.findOrgByName(name);
				}
			}
		} catch (Exception e) {
			logger.error("查询重复组织名称异常",e);
		}
		return SUCCESS;
	}

	
	/**
	 * 添加组织
	 */
	public String saveOrg(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();		
			HttpSession hs = request.getSession();
			String currentUserName = String.valueOf(hs.getAttribute("username"));
			orgObj.setOrgInputPersonId(currentUserName);//保存录入人
			
			String orgImg = orgObj.getOrgImage();
			if(null!=orgImg&&!"".equals(orgImg.trim())){
				String subFile = orgImg.substring(orgImg.indexOf("."), orgImg.length());
				String newFileName = "Org"+imgTimeNum+subFile;
				orgObj.setOrgImage(newFileName);
			}
			
			String orgName = orgObj.getOrgCName();
			boolean exsitFlag = orgInfoManageService.checkOrgNameExsit(orgName,null);
			if(!exsitFlag){
				orgEditService.saveOrg(orgObj);
			}else{
				resultMess = "该名称的组织已经存在；";
			}
		} catch (Exception e) {
			logger.error("添加组织异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改组织对象
	 */
	public String updateOrg(){
		try {
			String orgImg = orgObj.getOrgImage();
			if(null!=orgImg&&!"".equals(orgImg.trim())){
				String subFile = orgImg.substring(orgImg.indexOf("."), orgImg.length());
				String newFileName = "Org"+imgTimeNum+subFile;
				orgObj.setOrgImage(newFileName);
			}
			String orgName = orgObj.getOrgCName();
			boolean exsitFlag = orgInfoManageService.checkOrgNameExsit(orgName,String.valueOf(orgObj.getId()));
			if(!exsitFlag){
				orgEditService.updateOrg(orgObj);
			}else{
				resultMess = "该名称的组织已经存在；";
			}
		} catch (Exception e) {
			logger.error("修改组织异常",e);
		}
		return SUCCESS;
	}

	/**
	 * 上传组织图片
	 * @return
	 */
	public String saveOrgImg(){
		try{
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
		}catch (Exception e) {
			logger.error("添加照片异常",e);
		}
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public void setOrgEditService(OrgEditService orgEditService) {
		this.orgEditService = orgEditService;
	}

	public void setAllRelationService(AllRelationService allRelationService) {
		this.allRelationService = allRelationService;
	}

	public Organizationobject getOrgObj() {
		return orgObj;
	}

	public void setOrgObj(Organizationobject orgObj) {
		this.orgObj = orgObj;
	}

	public List<Organizationobject> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Organizationobject> orgList) {
		this.orgList = orgList;
	}

	public void setOrgInfoManageService(OrgInfoManageService orgInfoManageService) {
		this.orgInfoManageService = orgInfoManageService;
	}

	/*public File getOrgImg() {
		return orgImg;
	}*/

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

	public String getImgTimeNum() {
		return imgTimeNum;
	}

	public void setImgTimeNum(String imgTimeNum) {
		this.imgTimeNum = imgTimeNum;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public void setCheckNameId(String checkNameId) {
		this.checkNameId = checkNameId;
	}

	public String getNameExsitFlag() {
		return nameExsitFlag;
	}

	public String getResultMess() {
		return resultMess;
	}

	
}
