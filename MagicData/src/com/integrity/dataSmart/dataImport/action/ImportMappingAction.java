package com.integrity.dataSmart.dataImport.action;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.dataImport.bean.ImportMapping;
import com.integrity.dataSmart.dataImport.service.ImportMappingService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 数据导入映射模板维护action
 * @author RenSx
 * 
 */
public class ImportMappingAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(ImportMappingAction.class);
	private ImportMappingService importMappingService;
	private PageModel<ImportMapping> pageModel;
	private ImportMapping importMapping;
	private boolean flag = false;
	
	//每页多少条记录  
    private Integer pageSize;  
    //第几页  
    private Integer pageNo;
    
    /**
	 * 获取数据导入映射模板列表
	 * @return
	 */
	public String findImportMappingList(){
		pageModel = new PageModel<ImportMapping>();
		if(pageNo==null){
			pageNo=0;
		}
		if(pageSize==null){
			pageSize=10;
		}
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		try {
			pageModel = importMappingService.getIMappingPageModel(pageModel,importMapping);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 添加数据导入映射模板
	 */
	public String saveImportMapping(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();		
			if(importMapping==null){
				importMapping = new ImportMapping();
			}
			String name = request.getParameter("name");
			String nodes = request.getParameter("nodes");
			String edges = request.getParameter("edges");
			String image = request.getParameter("image");
			String desc = request.getParameter("desc");
			importMapping.setEdges(edges);
			importMapping.setNodes(nodes);
			importMapping.setName(name);
			importMapping.setImage(image);
			importMapping.setDesc(desc);
			importMapping.setTime(new Date());
			importMappingService.saveImportMapping(importMapping);
			flag = true;
		} catch (Exception e) {
			logger.error("添加数据导入映射模板异常",e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String deleteImportMapping(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		try{
			importMappingService.deleteIM(id);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public ImportMappingService getImportMappingService() {
		return importMappingService;
	}

	public void setImportMappingService(ImportMappingService importMappingService) {
		this.importMappingService = importMappingService;
	}

	public PageModel<ImportMapping> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<ImportMapping> pageModel) {
		this.pageModel = pageModel;
	}

	public ImportMapping getImportMapping() {
		return importMapping;
	}

	public void setImportMapping(ImportMapping importMapping) {
		this.importMapping = importMapping;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
    
}
