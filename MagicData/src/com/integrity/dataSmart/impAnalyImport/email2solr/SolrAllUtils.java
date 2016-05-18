package com.integrity.dataSmart.impAnalyImport.email2solr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.servlet.SolrRequestParsers;

import com.integrity.dataSmart.impAnalyImport.bean.DirectoryTree;
import com.integrity.dataSmart.impAnalyImport.bean.Email;
import com.integrity.dataSmart.impAnalyImport.bean.Resume;
import com.integrity.dataSmart.impAnalyImport.util.ContentType;
import com.integrity.dataSmart.impAnalyImport.util.FileTools;

/**
 * @author Liubf
 * solr 操作工具类
 *
 */
public class SolrAllUtils {
	private static Logger logger = Logger.getLogger(SolrAllUtils.class);
	public static String solrUrl = null;
	public static String emailsolrUrl = null;
	public static String filePath = SolrAllUtils.class.getClassLoader().getResource("../config/solr/solrConfig.properties").toString();
	public static HttpSolrServer server = null;
	public static HttpSolrServer fileserver = null;
	/**
	 * 读取solrURL
	 */
	static{
		try {
			InputStream inputStream = new FileInputStream(new File(filePath.substring(5)));
			Properties p = new Properties();
			p.load(inputStream);
			solrUrl = p.getProperty("solr.solrname");
			emailsolrUrl = p.getProperty("solr.hostname");
		} catch (IOException e1) {
			e1.printStackTrace(System.out);
		}
	}
	/**
	 * solr 查询方法
	 * 
	 * @param mapParams 查询参数集合
	 * @param start 翻页参数
	 * @param rows 翻页参数
	 * @return
	 */
	public SolrDocumentList querySolrAllList(
			Map<String,String> mapParams,Integer start,Integer rows) {
		if (null == mapParams || mapParams.isEmpty()) {
			return null;
		}
		SolrDocumentList solrList = new SolrDocumentList();
		try {
			SolrQuery solrQuery = new SolrQuery();
			SolrParams params = new MapSolrParams(mapParams);  
			solrQuery.add(params);  
			solrQuery.setHighlight(true);
		    if (null !=  start) {
		    	solrQuery.setStart(start);
			}
		    if (null !=  rows) {
		    	solrQuery.setRows(rows);
			}
		    SolrParams solrParams = SolrRequestParsers.parseQueryString(solrQuery.toString());
		    QueryResponse response = fileserver.query(solrParams);
		    if (null != response && 0 == response.getStatus()) {
		    	solrList = response.getResults();
			}
		} catch (Exception e) {
			logger.info(this.getClass() + ": ", e);
			e.printStackTrace();
		}
		return solrList;
	}
	/**
	 * 根据文件内容检索文件（分页）
	 * @param conent 查询内容
	 * @param start 页数
	 * @param rows 行数
	 * @return
	 */
	public static List<SolrDocument> getFileFullDocment(String info,int start,int rows){
		if(fileserver == null){
			fileserver = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "attr_content:"+info);
	    query.setStart(start);
	    query.setRows(rows);
	    QueryResponse rsp = null;
		try {
			rsp = fileserver.query( query );
		} catch (Exception e) {
			logger.error("检索文件（分页）异常", e);
		}  
	    List<SolrDocument> beans = rsp.getResults();
	    return beans;
	}
	/**
	 * 全文检索简历（分页）
	 * @param conent 查询内容
	 * @param start 页数
	 * @param rows 行数
	 * @return VertexID
	 */
	public static List<String> getVidfromResumeText(String description,int start,int rows){
		if(fileserver == null){
			fileserver = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "type:Resume AND all:"+description );
	    query.setStart(start);
	    query.setRows(rows);
	    QueryResponse rsp = null;
		try {
			rsp = fileserver.query( query );
		} catch (Exception e) {
			logger.error("全文检索简历（分页）异常", e);
		}  
	    
	    List<Resume> beans = rsp.getBeans(Resume.class);
	    List<String> vids = new ArrayList<String>();
	    for(Resume rs:beans){
	    	if(rs.getVertexID() != null){
	    		vids.add(rs.getVertexID());
	    	}
	    }
	    return vids;
	}
	/**
	 * 插入Email集合  
	 * @param list
	 * @return 如果异常返回-1
	 */
	public static int insertEmailsToSolr(List<Email> list){
		try {
			if(server == null){
				server = new HttpSolrServer(emailsolrUrl);
			}
			UpdateResponse response = server.addBeans(list);
			server.commit();
			return response.getStatus();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return -1;
	}
	/**
	 * 插入Email  
	 * @param email
	 * @return 如果异常返回-1
	 */
	public static int insertEmailToSolr(Email email){
		try {
			if(server == null){
				server = new HttpSolrServer(emailsolrUrl);
			}
			UpdateResponse response = server.addBean(email);
			server.commit();
			return response.getStatus();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return -1;
	}
	/**
	 * solr保存文件
	 * 
	 * @param fileName
	 * @param paramsMap（对象属性）
	 */
	public static void indexFilesSolrCell(String fileName, Map<String,String> paramsMap) {  
		try {
			ContentStreamUpdateRequest up  = new ContentStreamUpdateRequest("/update/extract");  
		    if (fileName.lastIndexOf(".") == -1 ) {
		    	// 文件没有后缀
				return;
			}
		    String fileType =  FileTools.getTypeByFileName(fileName);
		   
			String contentType= ContentType.getNameByType(fileType);
			
			if (StringUtils.isBlank(contentType)) {
				logger.error("solr不支持当前保存的文件类型: "+ fileName );
				return;
			}
		    File file = new File(fileName);
		    up.addFile(file, contentType);
		    if (null != paramsMap && !paramsMap.isEmpty()) {
				for (String key : paramsMap.keySet()) {
					 up.setParam(key, paramsMap.get(key));
				}
			}
		    up.setParam("literal.filePath", file.getAbsolutePath());   
		    up.setParam("literal.fileName", file.getName());
		    up.setParam("uprefix", "attr_");
		    up.setParam("fmap.content", "attr_content");
		    up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
		    @SuppressWarnings("unused")
			NamedList<Object> list = fileserver.request(up);
		} catch (Exception e) {
			logger.error("文件在solr中存储异常："+fileName,e);
			e.printStackTrace();
		}
	}
	/**
	 * 根据节点id查询简历
	 * @param vertexId
	 * @return 
	 */
	public static Resume getResumeByVid(String vertexId){
		if(fileserver == null){
			fileserver = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "vertexID:"+vertexId);
	    QueryResponse rsp = null;
		try {
			rsp = fileserver.query( query );
		} catch (Exception e) {
			logger.error("根据节点id查询简历异常", e);
		}  
	    
	    List<Resume> beans = rsp.getBeans(Resume.class);
	    if (beans!=null&&beans.size()>0) {
			return beans.get(0);
		}
	    return null;
	}
	/**
	 * 更具messageid 查询邮件是否存在
	 * @param messageID
	 * @return 大于0存在
	 */
	public static int getEmails(String messageID){
		if(server == null){
			server = new HttpSolrServer(emailsolrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "messageID:\""+messageID+"\"" );
	    QueryResponse rsp = null;
		try {
			rsp = server.query( query );
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}  
	    
	    List<Email> beans = rsp.getBeans(Email.class);
	    return beans.size();
	}
	/**
	 * 根据路径查询目录对象
	 * @param name
	 * @return 
	 */
	public static DirectoryTree getDirectoryTreeByName(String name){
		if(server == null){
			server = new HttpSolrServer(emailsolrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "type:DirectoryTree AND name:\""+name+"\"" );
	    QueryResponse rsp = null;
		try {
			rsp = server.query( query );
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}  
	    
	    List<DirectoryTree> beans = rsp.getBeans(DirectoryTree.class);
	    if (beans!= null && beans.size()>0) {
			return beans.get(0);
		}
	    return null;
	}
	
	/**
	 * 插入DirectoryTree  
	 * @param dir
	 * @return 如果异常返回-1
	 */
	public static int insertDirectoryTreeToSolr(DirectoryTree dir){
		try {
			if(server == null){
				server = new HttpSolrServer(emailsolrUrl);
			}
			UpdateResponse response = server.addBean(dir);
			server.commit();
			return response.getStatus();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return -1;
	}
	/**
	 * 根据条件删除对象
	 * @param queryParams
	 */
	public void deleteByQuery(String queryParams){
		try {
			fileserver.deleteByQuery(queryParams);
		} catch (Exception e) {
			logger.info(this.getClass() + ": 异常信息" + e);
			e.printStackTrace();
		}finally{
			try {
				fileserver.commit();
			} catch (Exception e) {
				logger.info(this.getClass() + ": 异常信息" + e);
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * 删除对象by id
	 * @param id
	 */
	public static void deleteSolrObject(String id){
		try {
			fileserver.deleteById(id);
		} catch (Exception e) {
			logger.info("删除异常" + e);
			e.printStackTrace();
		}finally{
			try {
				fileserver.commit();
			} catch (Exception e) {
				logger.info("删除异常" + e);
				e.printStackTrace();
			} 
		}
	}
	/**
	 * 批量删除对象by ids
	 * @param ids
	 */
	public static void deleteSolrObjectList(List<String> ids) {
		try {
			fileserver.deleteById(ids);
		} catch (Exception e) {
			logger.error("删除数据异常",e);
			e.printStackTrace();
		}finally{
			try {
				fileserver.commit();
			} catch (Exception e) {
				logger.error("删除数据异常",e);
				e.printStackTrace();
			} 
		}
	}
	/**
	 * 清空solr数据
	 */
	public static void clearCoreDataBase(){
		SolrAllUtils.connection();
		try {
			fileserver.deleteByQuery("*:*");
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileserver.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("数据库已清空（谨慎操作）");
		SolrAllUtils.close();
	}
	/**
	 * solr 连接 core2
	 */
	public static void connection(){
		try {
			fileserver = new HttpSolrServer(solrUrl);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	/**
	 * solr 连接 core0
	 */
	public static void connectionEmailsolr(){
		try {
			server = new HttpSolrServer(emailsolrUrl);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	/**
	 * solr 关闭连接 core2
	 */
	public static void close(){
		try {
			if (fileserver != null) {
				fileserver.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	/**
	 * 关闭连接 solr core0
	 */
	public static void closeEmailsolr(){
		try {
			if (server != null) {
				server.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

}
