package com.integrity.dataSmart.util.solr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.integrity.dataSmart.pojo.TianmaoData;
import com.integrity.dataSmart.titanGraph.pojo.DirectoryTree;
import com.integrity.dataSmart.titanGraph.pojo.Email;
import com.integrity.dataSmart.util.HbaseUtils.HBaseBasic;
import com.integrity.dataSmart.util.jsonUtil.JsonGetBeanUtil;


/**
 * Email2Solr工具
 *
 */
public class Email2SolrUtil {
	
	private static Logger logger = Logger.getLogger(Email2SolrUtil.class);
	public static String solrUrl = null;
	public static HttpSolrServer server = null;
	
	public static String solrUrls = "http://192.168.40.10:8983/solr/lob_2014042815";
	/**
	 * 读取solrURL
	 */
	static{
		try {
//			InputStream inputStream = Email2SolrUtil.class.getClassLoader().getResourceAsStream("../config/solr/solrConfig.properties");
			File f = new File(Email2SolrUtil.class.getResource("/").getFile().replace("/classes", "")+"config/solr/solrConfig.properties"); 
		    InputStream inputStream = new FileInputStream(f);
			Properties p = new Properties();
			p.load(inputStream);
			solrUrl = p.getProperty("solr.hostname");
			//p.getProperty("solrs.hostname");
		} catch (IOException e) {
			logger.error("读取solrURL 异常", e);
		}
	}
	/**
	 * 插入Email集合  
	 * @param list
	 * @return 如果异常返回-1
	 */
	public static int insertEmailsToSolr(List<Email> list){
		
		try {
			if(server == null){
				server = new HttpSolrServer(solrUrl);
			}
			UpdateResponse response = server.addBeans(list);
			server.commit();
			return response.getStatus();
		} catch (Exception e) {
			logger.error("插入Email集合 异常", e);
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
				server = new HttpSolrServer(solrUrl);
			}
			UpdateResponse response = server.addBean(email);
			server.commit();
			return response.getStatus();
		} catch (Exception e) {
			logger.error("插入Email 异常", e);
		}
		return -1;
	}
	
	/**
	 * 根据messageid 查询邮件是否存在
	 * @param messageID
	 * @return 大于0存在
	 */
	public static int getEmails(String messageID){
		if(server == null){
			server = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "messageID:"+messageID );
	    QueryResponse rsp = null;
		try {
			rsp = server.query( query );
		} catch (Exception e) {
			logger.error("根据messageid 查询邮件异常", e);
		}  
	    
	    List<Email> beans = rsp.getBeans(Email.class);
	    return beans.size();
	}
	
	/**
	 * 全文检索邮件(不分页)
	 * @param conent
	 * @return 
	 */
	public static List<Email> getEmailsFullText(String conent){
		if(server == null){
			server = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "type:EmailEvent AND all:"+conent );
	    query.setStart(0);
	    query.setRows(10000);
	    QueryResponse rsp = null;
		try {
			rsp = server.query( query );
		} catch (Exception e) {
			logger.error("全文检索邮件(不分页)异常", e);
		}  
	    
	    List<Email> beans = rsp.getBeans(Email.class);
	    return beans;
	}
	
	/**
	 * 全文检索邮件（分页）
	 * @param conent 查询内容
	 * @param start 页数
	 * @param rows 行数
	 * @return
	 */
	public static List<Email> getEmailsFullText(String conent,int start,int rows){
		if(server == null){
			server = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "type:EmailEvent AND all:"+conent );
	    query.setStart(start);
	    query.setRows(rows);
	    QueryResponse rsp = null;
		try {
			rsp = server.query( query );
		} catch (Exception e) {
			logger.error("全文检索邮件（分页）异常", e);
		}  
	    
	    List<Email> beans = rsp.getBeans(Email.class);
	    return beans;
	}
	
	/**
	 * 根据query语句查询邮件分页
	 * @param condition
	 * @param start
	 * @param rows
	 * @return
	 */
	public static List<Email> getEmailsByCondition(String condition,
			int start, int rows) {
		if(server == null){
			server = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery(condition);
	    query.setStart(start*rows);
	    query.setRows(rows);
	    QueryResponse rsp = null;
		try {
			rsp = server.query( query );
		} catch (Exception e) {
			logger.error("根据query语句查询邮件分页）异常", e);
		}  
	    
	    List<Email> beans = rsp.getBeans(Email.class);
	    return beans;
	}
	
	
	/**
	 * 根据节点id查询邮件
	 * @param vertexId
	 * @return 
	 */
	public static Email getEmailByVid(long vertexId){
		if(server == null){
			server = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "vertexID:"+vertexId );
	    QueryResponse rsp = null;
		try {
			rsp = server.query( query );
		} catch (Exception e) {
			logger.error("根据节点id查询邮件异常", e);
		}  
	    
	    List<Email> beans = rsp.getBeans(Email.class);
	    if (beans!=null&&beans.size()>0) {
			return beans.get(0);
		}
	    return null;
	}
	
	/**
	 * 根据emailid修改邮件标签
	 * @param id
	 * @return Email
	 * 
	 */
	public static void getEmailById(String id,List<String> labels){
		Email2SolrUtil.connection();
		if(server == null){
			server = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery("vertexID:"+id);
	    QueryResponse rsp = null;
		try {
			rsp = server.query(query);
			List<Email> beans = rsp.getBeans(Email.class);
			if (beans!=null&&beans.size()>0) {
				Email eM =  beans.get(0);
				if(labels != null){
				eM.setShowLabel(labels);
				//更新
				Email2SolrUtil.insertEmailToSolr(eM);
				}
			}
		} catch (Exception e) {
			logger.error("根据emailid修改邮件标签异常", e);
		}finally{
			Email2SolrUtil.close();
		}  
	}
	
	/**
	 * 根据pid查询目录对象
	 * @param name
	 * @return 
	 */
	public static List<DirectoryTree> getDirectoryTreeByName(String pid){
		if(server == null){
			server = new HttpSolrServer(solrUrl);
		}
		SolrQuery query = new SolrQuery();
	    query.setQuery( "type:DirectoryTree AND pid:"+pid );
	    query.setStart(0);
	    query.setRows(10000);
	    query.setSort("name", ORDER.asc);
	    QueryResponse rsp = null;
		try {
			rsp = server.query( query );
		} catch (Exception e) {
			e.printStackTrace();
		}  
	    
	    List<DirectoryTree> beans = rsp.getBeans(DirectoryTree.class);
	    if (beans!= null && beans.size()>0) {
			return beans;
		}
	    return null;
	}
	
	/**
	 * 递归拼树对象
	 * @param root
	 * @param pid
	 * @return
	 */
	public static List<Map<String,String>> recursionSearchTree(List<Map<String,String>> root,String pid){
		List<DirectoryTree> beans = getDirectoryTreeByName(pid);
		if (beans!= null && beans.size()>0) {
			for (DirectoryTree dt : beans) {
				Map<String,String> node = new HashMap<String, String>();
				node.put("id", dt.getId());
				node.put("pId", dt.getPid());
				node.put("name", dt.getName());
				root.add(node);
				recursionSearchTree(root, dt.getId());
			}
		}
		return root ;
		
	}
	
	/**
	 * solr 连接
	 */
	public static void connection(){
		try {
			server = new HttpSolrServer(solrUrl);
		} catch (Exception e) {
			logger.error("solr 连接异常", e);
		}
	}
	public static void connections(){
		try {
			server = new HttpSolrServer(solrUrls);
		} catch (Exception e) {
			logger.error("solr 连接异常", e);
		}
	}
	
	/**
	 * solr 关闭连接
	 */
	public static void close(){
		try {
			if (server != null) {
				server.shutdown();
			}
		} catch (Exception e) {
			logger.error("solr 关闭连接异常", e);
		}
	}

	public static List<TianmaoData> getTmDataFromSolr(String HbseTableName,int start,int end){
		Email2SolrUtil.connections();
		if(server == null){
			server = new HttpSolrServer(solrUrls);
		}
		List<TianmaoData> datas = new ArrayList<TianmaoData>();
		SolrQuery query = new SolrQuery();
	    query.setQuery( "*:*" );
	    query.setStart(start);
	    query.setRows(end);
	    QueryResponse rsp = null;
	    /**dele**/
	   // Set<String> set = new HashSet<String>();
		try {
			rsp = server.query( query );
			SolrDocumentList list = rsp.getResults();
			for (SolrDocument solrDocument : list) {
				Object object = solrDocument.getFirstValue("data");
				TianmaoData tianmaoData=JsonGetBeanUtil.getTianmaoByJson(object.toString(),false);
				if (tianmaoData == null) continue;
				String rowid = tianmaoData.getInfo_rowid();//数据源id
				String domain = HBaseBasic.selectByRowKey(HbseTableName, rowid);
				if(domain != null && !domain.equals("")){
					tianmaoData.setDomain(domain);
				}
				datas.add(tianmaoData);
				/**dele**/
/*				Map<String,String> map1 = JsonGetBeanUtil.toMap(new JSONObject(object.toString()).toString());
				Collection<String> coll = solrDocument.getFieldNames();
				 coll = map1.keySet();
				for (String string : coll) {
					set.add(string);
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Email2SolrUtil.close();
/*			for (String str : set) {
				System.out.println(str);
				WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\solrLog.txt", "...."+str);
				
				WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\solrLog.txt", "<--------------------->");
			}*/
		}
		return datas;  
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//getTmDataFromSolr("lb_info",173830000, 1000000);
		/*for (int i = 250000000; i < 400000000; i+=200000) {
			System.out.println("------------>"+i);
			WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\solrLog.txt", "<-----------"+i+"---------->");
			getTmDataFromSolr("lb_info",i, 2000);
		}*/
		
		String cp = "config/solr/solrConfig.properties"; 
		InputStream inputStream = Email2SolrUtil.class.getClassLoader().getResourceAsStream("../config/solr/solrConfig.properties");
		
		  //当前类的绝对路径 
        System.out.println(Email2SolrUtil.class.getResource("/").getFile().replace("/classes", "")+cp); 
        //指定CLASSPATH文件的绝对路径 
//        System.out.println(Email2SolrUtil.class.getResource(cp).getFile()); 
        //指定CLASSPATH文件的绝对路径 
        File f = new File(Email2SolrUtil.class.getResource("/").getFile().replace("/classes", "")+cp); 
        System.out.println(f.getPath()); 
        Properties p = new Properties();
        p.load(new FileInputStream(f));
        solrUrl = p.getProperty("solr.hostname");
        System.out.println(solrUrl);
	}

}
