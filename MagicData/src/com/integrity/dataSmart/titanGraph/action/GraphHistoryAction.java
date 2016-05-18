package com.integrity.dataSmart.titanGraph.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.titanGraph.bean.GraphHistory;
import com.integrity.dataSmart.titanGraph.service.GraphHistoryService;
import com.opensymphony.xwork2.ActionSupport;

public class GraphHistoryAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(GraphHistoryAction.class);
	private GraphHistoryService graphHistoryService;
	private PageModel<GraphHistory> pageModel;
	private GraphHistory graphHistory;
	private String isTrue;
	
	/**图片上传*/
	private File graphImg;
	private String graphImgFileName;
	private String graphImgFileContentType;
	private String imgTimeNum;
	
	//每页多少条记录  
    private Integer pageSize;  
    //第几页  
    private Integer pageNo;  
	
	/**
	 * 获取数据分析历史版本列表
	 * @return
	 */
	public String findGraphHistoryList(){
		pageModel = new PageModel<GraphHistory>();
		if(pageNo==null){
			pageNo=0;
		}
		if(pageSize==null){
			pageSize=10;
		}
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
//		PageModel<GraphHistoryObject> pm = new PageModel<GraphHistoryObject>();
		try {
			pageModel = graphHistoryService.getHisPageModel(pageModel,graphHistory);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 上传数据分析图片
	 * @return
	 */
	public String saveGraphHistoryImg(){
		try{
			String imgDir = ServletActionContext.getServletContext().getRealPath("/images/graphHistory/uploadImg/graphImg/");
			System.out.println(imgDir);
			File imgDirFile = new File(imgDir); // 判断文件夹是否存在,如果不存在则创建文件夹
	        if (!imgDirFile.exists()) {
	        	imgDirFile.mkdirs();
	        }
			if(null!=graphImg&&null!=graphImgFileName){
				if(null==imgTimeNum&&"".equals(imgTimeNum.trim())){
					imgTimeNum = "";
				}
				String[] fileFit = new String[] { ".jpg",".png",".gif",".jpeg",".bmp" };
				boolean fitFlag = false;
				for (int i = 0; i < fileFit.length; i++) {
	                if (graphImgFileName.toLowerCase().endsWith(fileFit[i])) {
	                	fitFlag = true;
	                    break;
	                }
	            }
				if(fitFlag){
					InputStream is = new FileInputStream(graphImg);
					String subFile = graphImgFileName.substring(graphImgFileName.indexOf("."), graphImgFileName.length());
					String newFileName = "Graph"+imgTimeNum+subFile;
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
			logger.error("上传照片异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加历史版本
	 */
	public String saveGraphHistory(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();		
			if(graphHistory==null){
				graphHistory = new GraphHistory();
			}
			String name = request.getParameter("name");
			String nodes = request.getParameter("nodes");
			String edges = request.getParameter("edges");
			String image = request.getParameter("image");
			String relation = request.getParameter("relation");
			graphHistory.setEdges(edges);
			graphHistory.setNodes(nodes);
			graphHistory.setName(name);
			graphHistory.setImage(image);
			graphHistory.setRelation(relation);
			graphHistory.setTime(new Date());
			graphHistoryService.saveGraphHistory(graphHistory);
		} catch (Exception e) {
			logger.error("添加数据分析历史版本异常",e);
		}
		return SUCCESS;
	}
	
	public String deleteGraphHistory(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		graphHistoryService.deleteGh(id);
		return SUCCESS;
	}
	
	/**
	 * 同步session数据
	 */
	public String synchronousData(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession hs = ServletActionContext.getRequest().getSession();
		String nodes = request.getParameter("ids");
		String[] ids = nodes.split(",");
		@SuppressWarnings("unchecked")
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		try {
			for(String id:ids){
				int point = nodeIdList.indexOf(id);
				if(point == -1){
					nodeIdList.add(id);
				}
			}
			isTrue = "true";
		} catch (Exception e) {
			logger.error("同步session异常",e);
			isTrue = "false";
		}
		return SUCCESS;
	}
	
	public GraphHistoryService getGraphHistoryService() {
		return graphHistoryService;
	}
	public void setGraphHistoryService(GraphHistoryService graphHistoryService) {
		this.graphHistoryService = graphHistoryService;
	}
	public PageModel<GraphHistory> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<GraphHistory> pageModel) {
		this.pageModel = pageModel;
	}
	public GraphHistory getGraphHistory() {
		return graphHistory;
	}
	public void setGraphHistory(GraphHistory graphHistory) {
		this.graphHistory = graphHistory;
	}

//	public File getGraphImg() {
//		return graphImg;
//	}

	public void setGraphImg(File graphImg) {
		this.graphImg = graphImg;
	}

	public String getGraphImgFileName() {
		return graphImgFileName;
	}

	public void setGraphImgFileName(String graphImgFileName) {
		this.graphImgFileName = graphImgFileName;
	}

	public String getGraphImgFileContentType() {
		return graphImgFileContentType;
	}

	public void setGraphImgFileContentType(String graphImgFileContentType) {
		this.graphImgFileContentType = graphImgFileContentType;
	}

	public String getImgTimeNum() {
		return imgTimeNum;
	}

	public void setImgTimeNum(String imgTimeNum) {
		this.imgTimeNum = imgTimeNum;
	}

	public String getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(String isTrue) {
		this.isTrue = isTrue;
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
