package com.integrity.dataSmart.exportView.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.exportView.service.ExportViewService;
import com.opensymphony.xwork2.ActionSupport;

public class ExportViewToImgAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(ExportViewToImgAction.class);
	
	/**图片上传*/
	private String viewImg;
	private String currentTimeStr;
	
	private ServletContext sc = ServletActionContext.getServletContext();
	private String webPath = sc.getRealPath("/");
	
	public String saveViewImg(){
		try{
			String imgDir = webPath+ExportViewService.VIEW_EXPORT_PATH + File.separator
					+ currentTimeStr;
			File imgDirFile = new File(imgDir); // 判断文件夹是否存在,如果不存在则创建文件夹
	        if (!imgDirFile.exists()) {
	        	imgDirFile.mkdirs();
	        }
			if(null!=viewImg){
				String encodingPrefix = "base64,";
				int contentStartIndex = viewImg.indexOf(encodingPrefix) + encodingPrefix.length();
				byte[] imageData = Base64.decodeBase64(viewImg.substring(contentStartIndex));
				File saveImg = new File(imgDir,currentTimeStr+".png");
				 if(!saveImg.exists()){
			        	saveImg.createNewFile();
			        	OutputStream os = new FileOutputStream(saveImg);	        
				        for(int i = 0;i<imageData.length;i++){
				        	os.write(imageData[i]);
				        }
				        os.close();
			        }
			}
		} catch (Exception e) {
			logger.error("生成关联关系图片异常",e);
		}
		return SUCCESS;
	}
	
	public String getViewImg() {
		return viewImg;
	}
	public void setViewImg(String viewImg) {
		this.viewImg = viewImg;
	}
	public String getCurrentTimeStr() {
		return currentTimeStr;
	}
	public void setCurrentTimeStr(String currentTimeStr) {
		this.currentTimeStr = currentTimeStr;
	}
	
	
}
