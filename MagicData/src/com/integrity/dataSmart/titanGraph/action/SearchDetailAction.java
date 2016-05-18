package com.integrity.dataSmart.titanGraph.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.MenuKey;
import com.integrity.dataSmart.titanGraph.service.SearchDetailService;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.opensymphony.xwork2.ActionSupport;

public class SearchDetailAction extends ActionSupport{
	private Logger logger = Logger.getLogger(SearchDetailAction.class);
	private static final long serialVersionUID = 1L;
	private SearchDetailService searchDetailService;
	private String content;
	private long personId;
	private String types;
//	private long startTime;
//	private long endTime;
	private long nodeIndex;
	private long maxIndex;
	private String property;
	private long connectId;
	private long connectIndex;
	private String ids;
	private InputStream inputStream;
	
	private int pageNo;//页数
	private int pageSize;//每页条数
	/**高级查询条件**/
	private String allText;//全文
	private String titleOfSearch;//主题
	private String mailAddressOfSearch;//邮箱
	private String contectOfSearch;//正文
	private String filenameOfSearch;//附件名称
	private String filecontectOfSearch;//附件正文
	private String hotwordOfSearch;//热词
	private String tagOfSearch;//标签
	private String folderOfSearch;//文件夹
	private String datefromSearch;//开始时间
	private String datetoSearch;//结束时间
	private String emlPath;//emlPath
	/**简历数据查询条件**/
	private String VertexID;
	
	private String searchJson;
	private HttpSession hs = ServletActionContext.getRequest().getSession();
	
	/**
	 * 根据条件查询对象（全文）
	 * @return
	 */
	public String findObjByFull(){
		try {
			if(StringUtils.isNotBlank(content)){
				String root =  searchDetailService.getObjByFull(hs,content.trim());
				if (root!=null && !root.equals("null")) {
					inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
					initMenu();
				}else{
					inputStream = new ByteArrayInputStream("no".getBytes("utf-8"));
				}
			}
		} catch (Exception e) {
			logger.error("根据条件查询对象（全文）",e);
		}
		return SUCCESS;
	}
	/**
	 * 根据条件查询对象(多条件)
	 * @return
	 */
	public String findObjByMoreProperty(){
		try {
			Map<String, Map<String, String>> searchMap= new HashMap<String, Map<String, String>>();
			searchMap = JacksonMapperUtil.getObjectMapper().readValue(searchJson, searchMap.getClass());
			if (MapUtils.isNotEmpty(searchMap)) {
				String root =  searchDetailService.getObjByMoreProperty(hs, searchMap);
				if (root!=null && !root.equals("null")) {
					inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
					initMenu();
				}else{
					inputStream = new ByteArrayInputStream("no".getBytes("utf-8"));
				}
			}
	
		} catch (Exception e) {
			logger.error("根据条件查询对象(多条件)",e);
		}
		return SUCCESS;
	}
	/**
	 * 根据条件查询对象
	 * @return
	 */
	public String findObj(){
		try {
			String root =  searchDetailService.getObjByProperty(hs, property,content);
			if (root!=null && !root.equals("null")) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
				initMenu();
			}else{
				inputStream = new ByteArrayInputStream("no".getBytes("utf-8"));
			}
	
		} catch (Exception e) {
			logger.error("根据条件查询对象",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据id查询详细
	 * @return
	 */
	public String findDetailById(){
		try {
			String root =  searchDetailService.getDetailById(hs,personId,nodeIndex,maxIndex, property,content);
			if (root!=null) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
			}
		} catch (Exception e) {
			logger.error("根据id查询详细",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据属性查询详细信息展示菜单
	 * @return
	 *//*
	public String viewMenu(){
		//add by HanX start 查询成功显示菜单
//		hs.setAttribute(MenuKey.MENU_SHOW_FLAGKEY,MenuKey.MENU_SHOW_FLAG);
		//add by HanX end
		try {
			String root =  searchDetailService.getVertexByProperty(property,content);
			if (root!=null) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
				//add by HanX start 查询成功显示菜单
				hs.setAttribute(MenuKey.MENU_SHOW_FLAGKEY,MenuKey.MENU_SHOW_FLAG);
				//add by HanX end
			}
		} catch (Exception e) {
			//add by HanX start 查询成功显示菜单
//			hs.setAttribute(MenuKey.MENU_SHOW_FLAGKEY,MenuKey.MENU_SHOW_FLAG);
			//add by HanX end
			logger.error("根据属性查询详细信息异常",e);
		}

		return SUCCESS;
	}*/
	
	/**
	 * 根据人物ID查询关联事件
	 * @return
	 */
	public String findEventById(){
		try {
			String[] type = null;
			if (StringUtils.isNotBlank(types)) {
				type = types.split(",");
			}
			String root = searchDetailService.getEventById(hs,personId,type,nodeIndex,maxIndex);
			if (root!=null) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
			}
		} catch (Exception e) {
			logger.error("根据人物ID查询关联事件异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据人物ID查询网络关系
	 * @return
	 */
	public String findRelativeById(){
		try {
			String root =  searchDetailService.getRelativeById(hs,personId,nodeIndex,maxIndex);
			if (root!=null) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
			}
		} catch (Exception e) {
			logger.error("根据人物ID查询网络关系异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据人物ID查询群组关系
	 * @return
	 */
	public String findGroupRelativeById(){
		try {
			String root =  searchDetailService.findGroupRelativeById(personId);
			if (root!=null) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
			}
		} catch (Exception e) {
			logger.error("根据人物ID查询群组关系异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据人物ID查询人物关联关系
	 * @return
	 */
	public String findConnectByIds(){
		try {
			String root = searchDetailService.getConnectByIds(hs,personId,connectId,nodeIndex,connectIndex,maxIndex);
			if (root!=null) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
			}
		} catch (Exception e) {
			logger.error("根据人物ID查询人物关联关系异常",e);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 根据人物ID、时间查询地图所需数据
	 * @return
	 */
	public String findMapData(){
		try {
			
			String root = searchDetailService.getMapData(personId);
			if (root!=null) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
			}
		} catch (Exception e) {
			logger.error("据人物ID、时间查询地图所需数据异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改节点ID缓存
	 * @return
	 */
	public String modifyIdCache(){
		try {
			String[] newIds = null;
			if (StringUtils.isNotBlank(ids)) {
				newIds = ids.split(",");
			}
			if (newIds != null) {
				searchDetailService.modifyIdCache(hs, newIds);
//				searchDetailService.modifyIdCache(hs, personId);
			}
		} catch (Exception e) {
			logger.error("修改节点ID缓存异常",e);
		}
		return null;
	}
	
	/**
	 * 清空节点ID缓存
	 * @return
	 */
	public String emptyIdCache(){
		try {
			hs.setAttribute("nodeIdList", new ArrayList<Object>());
		} catch (Exception e) {
			logger.error("修改节点ID缓存异常",e);
		}
		return null;
	}
	
	/**
	 * 删除节点ID缓存
	 * @return
	 */
	public String deleteIdCache(){
		try {
			String[] deteleids = null;
			if (StringUtils.isNotBlank(ids)) {
				deteleids = ids.split(",");
			}
			if (deteleids != null) {
				searchDetailService.deleteIdCache(hs, deteleids);
			}
		} catch (Exception e) {
			logger.error("删除节点ID缓存异常",e);
		}
		return null;
	}
	
	/**
	 * 根据条件查询邮件,分页
	 * 
	 * @return
	 */
	public String findEmailsFullText() {
		try {
			String emails = searchDetailService.getEmailsByCondition(pageNo,
					pageSize, allText, titleOfSearch, mailAddressOfSearch,
					contectOfSearch, filenameOfSearch, filecontectOfSearch,
					hotwordOfSearch, tagOfSearch, folderOfSearch,
					datefromSearch, datetoSearch);
			if (emails != null) {
				inputStream = new ByteArrayInputStream(emails.getBytes("utf-8"));
			}
		} catch (Exception e) {
			logger.error("根据条件查询邮件", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据查询的邮件ids展示图
	 * @return
	 */
	public String findMailRelativeByIds(){
		try {
			String[] emailIds = null;
			if (StringUtils.isNotBlank(ids)) {
				emailIds = ids.split(",");
			}
			String root =  searchDetailService.getMailRelativeByIds(hs,emailIds,maxIndex);
			if (root!=null) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
				hs.setAttribute(MenuKey.MENU_SHOW_FLAGKEY,MenuKey.MENU_SHOW_FLAG);
			}
		} catch (Exception e) {
			logger.error("根据查询的邮件ids展示图 异常",e);
		}
		return SUCCESS;
	}
	
	
	public String findTreeJson(){
		try {
			String root =  searchDetailService.getTreeJson();
			if (root!=null) {
				inputStream = new ByteArrayInputStream(root.getBytes("utf-8"));
			}
	
		} catch (Exception e) {
			logger.error("查询邮件目录树异常",e);
		}
		return SUCCESS;
	}
	/**
	 * 通过VertexID获取简历信息
	 * @return
	 */
	public String queryResumeByVid(){
		if (StringUtils.isNotEmpty(VertexID)) {
			try {
				String 	resume = searchDetailService.queryResumeByVid(VertexID);
				if (resume!=null) {
					inputStream = new ByteArrayInputStream(resume.getBytes("utf-8"));}
			  } catch (Exception e) {
				logger.error("查询简历信息发生异常", e);
			 }
		}
		return SUCCESS;
	}
	
	/**
	 * 初始化菜单
	 */
	private void initMenu(){
		hs.setAttribute(MenuKey.MENU_SHOW_FLAGKEY,MenuKey.MENU_SHOW_FLAG);
	}
	
	
	public void setSearchDetailService(SearchDetailService searchDetailService) {
		this.searchDetailService = searchDetailService;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public long getNodeIndex() {
		return nodeIndex;
	}

	public void setNodeIndex(long nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public long getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(long maxIndex) {
		this.maxIndex = maxIndex;
	}

	public long getConnectId() {
		return connectId;
	}

	public void setConnectId(long connectId) {
		this.connectId = connectId;
	}

	public long getConnectIndex() {
		return connectIndex;
	}

	public void setConnectIndex(long connectIndex) {
		this.connectIndex = connectIndex;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getAllText() {
		return allText;
	}

	public void setAllText(String allText) {
		this.allText = allText;
	}

	public String getTitleOfSearch() {
		return titleOfSearch;
	}

	public void setTitleOfSearch(String titleOfSearch) {
		this.titleOfSearch = titleOfSearch;
	}

	public String getMailAddressOfSearch() {
		return mailAddressOfSearch;
	}

	public void setMailAddressOfSearch(String mailAddressOfSearch) {
		this.mailAddressOfSearch = mailAddressOfSearch;
	}

	public String getContectOfSearch() {
		return contectOfSearch;
	}

	public void setContectOfSearch(String contectOfSearch) {
		this.contectOfSearch = contectOfSearch;
	}

	public String getFilenameOfSearch() {
		return filenameOfSearch;
	}

	public void setFilenameOfSearch(String filenameOfSearch) {
		this.filenameOfSearch = filenameOfSearch;
	}

	public String getFilecontectOfSearch() {
		return filecontectOfSearch;
	}

	public void setFilecontectOfSearch(String filecontectOfSearch) {
		this.filecontectOfSearch = filecontectOfSearch;
	}

	public String getHotwordOfSearch() {
		return hotwordOfSearch;
	}

	public void setHotwordOfSearch(String hotwordOfSearch) {
		this.hotwordOfSearch = hotwordOfSearch;
	}

	public String getTagOfSearch() {
		return tagOfSearch;
	}

	public void setTagOfSearch(String tagOfSearch) {
		this.tagOfSearch = tagOfSearch;
	}

	public String getFolderOfSearch() {
		return folderOfSearch;
	}

	public void setFolderOfSearch(String folderOfSearch) {
		this.folderOfSearch = folderOfSearch;
	}

	public String getDatefromSearch() {
		return datefromSearch;
	}

	public void setDatefromSearch(String datefromSearch) {
		this.datefromSearch = datefromSearch;
	}

	public String getDatetoSearch() {
		return datetoSearch;
	}

	public void setDatetoSearch(String datetoSearch) {
		this.datetoSearch = datetoSearch;
	}

	public String getEmlPath() {
		return emlPath;
	}

	public void setEmlPath(String emlPath) {
		this.emlPath = emlPath;
	}

	public String getVertexID() {
		return VertexID;
	}

	public void setVertexID(String vertexID) {
		VertexID = vertexID;
	}
	
	public String getSearchJson() {
		return searchJson;
	}
	public void setSearchJson(String searchJson) {
		this.searchJson = searchJson;
	}


}
