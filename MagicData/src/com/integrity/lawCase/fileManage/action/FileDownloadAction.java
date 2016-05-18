package com.integrity.lawCase.fileManage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.integrity.lawCase.fileManage.service.FileManageService;
import com.opensymphony.xwork2.ActionSupport;

public class FileDownloadAction extends ActionSupport{
	
	private Long id;//文件id
	private String annexUrl;
	private String annexName;
	private String fileNameD;
	private String type;
	private String parentId;
	private FileManageService fileManageService;
	private String subDir;
	
	public String downloadFileAnalysis(){
		return SUCCESS;
	}
	public InputStream getDownloadFileStreamAnalysis(){
		String directory ="";
		if(id!=null){
			 directory = "/images/uploadFile/"+fileManageService.getFileinfoByid(id).getAnnexName()+"/";
		}else{
			 directory = "/images/exportFile/"+type+"M/ex"+type+"/";
			 if(null!=subDir&&!"".equals(subDir.trim())&&!"null".equals(subDir.trim())){
				 directory = directory + subDir +"/";
			 }
		}
		String targetDirectory = ServletActionContext.getServletContext().getRealPath(directory);
		try {
			try {
				fileNameD = URLDecoder.decode(fileNameD, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return new FileInputStream(new File(targetDirectory, fileNameD));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String downloadFile(){
		String filename = null;
		try {
			filename = fileManageService.getFileinfoByid(Long.parseLong(parentId)).getFileName();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(filename!= null){
			return SUCCESS;
		}else if(id != null){
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	public InputStream getDownloadFileStream(){
		String directory ="";
		String filename =null;
		if(id!=null){
			String annexName = fileManageService.getFileinfoByid(id).getAnnexName();
			String annexUrl = fileManageService.getFileinfoByid(id).getAnnexUrl();
			String url = annexUrl.substring(annexUrl.lastIndexOf(annexName),annexUrl.lastIndexOf("/"));
			directory = "/images/uploadFile/"+url;
		}else if(parentId !=null){
			try {
				filename = fileManageService.getFileinfoByid(Long.parseLong(parentId)).getFileName();
				String aName = fileManageService.getFileinfoByid(Long.parseLong(parentId)).getAnnexName();
				String aUrl = fileManageService.getFileinfoByid(Long.parseLong(parentId)).getAnnexUrl();
				String purl = aUrl.substring(aUrl.lastIndexOf(aName),aUrl.lastIndexOf("/"));
				directory = "/images/uploadFile/"+purl;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else{
			 directory = "/images/exportFile/"+type+"M/ex"+type+"/";
			 if(null!=subDir&&!"".equals(subDir.trim())&&!"null".equals(subDir.trim())){
				 directory = directory + subDir +"/";
			 }
		}
		String targetDirectory = ServletActionContext.getServletContext().getRealPath(directory);
		
		try {
			try {
				if(fileNameD != null){
					fileNameD = URLDecoder.decode(fileNameD, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(parentId !=null){
				if(filename != null){
					fileNameD = filename;
					return new FileInputStream(new File(targetDirectory, fileNameD));
				}
			}else{
				return new FileInputStream(new File(targetDirectory, fileNameD));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	@JSON(serialize=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public String getFileNameD() {
		try {
			fileNameD = new String(fileNameD.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fileNameD;
	}
	public void setFileNameD(String fileNameD) {
		this.fileNameD = fileNameD;
	}@JSON(serialize=false)
	public String getAnnexUrl() {
		return annexUrl;
	}
	public void setAnnexUrl(String annexUrl) {
		this.annexUrl = annexUrl;
	}@JSON(serialize=false)
	public String getAnnexName() {
		return annexName;
	}
	public void setAnnexName(String annexName) {
		this.annexName = annexName;
	}@JSON(serialize=false)
	public FileManageService getFileManageService() {
		return fileManageService;
	}
	public void setFileManageService(FileManageService fileManageService) {
		this.fileManageService = fileManageService;
	}@JSON(serialize=false)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubDir() {
		return subDir;
	}
	public void setSubDir(String subDir) {
		this.subDir = subDir;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
