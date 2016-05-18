package com.integrity.dataSmart.impAnalyImport.email2solr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;

import com.integrity.dataSmart.impAnalyImport.bean.Email;
import com.integrity.dataSmart.impAnalyImport.main.EmailHandler;

public class Test {

	public static void main(String[] args) {
//		deleteEmail("<18123049592_1428993397_125294117@a2plmmworker07.prod.iad2.gdg>");
//		getEmails("a");
		SolrAllUtils.connection();
/*		File file = new File("C:\\Users\\cs\\Desktop\\emls1");
		String attachfilepath = "C:\\Users\\cs\\Desktop\\bbbbb";
		if (file.isDirectory()) {
			File[] arr = file.listFiles();
			for (File file2 : arr) {
				String emllocalpath = file2.getPath();
				String emlpath = "";
				System.out.println(emllocalpath);
				Email email = EmailHandler.analyticalEML(emllocalpath, attachfilepath,emlpath);
				email.setVertexID("123123");
				List<String> list = new ArrayList<>();
				list.add("小苹果");
				list.add("李光");
				email.setShowLabel(list);
				email.setEmlPath("\\a\\b\\c");
				Email2SolrUtil.insertEmailToSolr(email);
			}
		}*/
	
		int count = Test.add();
		SolrAllUtils.close();
	}
	
	public static int add(){
		Email email = new Email();
		email.setId("123");
		email.setType("EmailEvent");
		email.setFrom("cs222@126.com");
		ArrayList<String> tos = new ArrayList<String>();
		tos.add("zhang222@163.com");
		tos.add("wang334@5163.com");
		tos.add("li334@163.com");
		email.setTos(tos);
		email.setTitle("筷子兄弟111");
		email.setContent("你是我的小呀小苹果，怎么爱你都不嫌多。111");
		email.setVertexID("123133451111");
		email.setMessageID("111114");
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("小苹果");
		keywords.add("爱你");
		keywords.add("133");
		email.setKeywords(keywords);
		
//		List<String> receiveds = new ArrayList<String>();
//		receiveds.add("1.1.1.1#1631111");
//		receiveds.add("2.2.2.2#sina1234");
//		email.setReceiveds(receiveds);
		
		ArrayList<String> attachfilenames = new ArrayList<String>();
		attachfilenames.add("aaa1111.doc");
		attachfilenames.add("bbb41234.xml");
		email.setAttachfilenames(attachfilenames);
		
		
		List<Email> list = new ArrayList<Email>();
		list.add(email);
		Integer stauts = SolrAllUtils.insertEmailsToSolr(list);
		
		System.out.println("stauts12:"+stauts);
		return stauts;
	}
	
	public static List<Email> getEmails(String all) throws IOException{
		SolrAllUtils.connection();
		HttpSolrServer server = SolrAllUtils.server;
		SolrQuery query = new SolrQuery();
	    query.setQuery( "all:"+all+"  AND type:EmailEvent" );
//	    query.setQuery( "type:EmailEvent" );
//	    query.setQuery("all:cs3345@126.com");
//	    query.addSort( "price", SolrQuery.ORDER.asc );
	    QueryResponse rsp = null;
		try {
			rsp = server.query( query );
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}  
	    
	    List<Email> beans = rsp.getBeans(Email.class);
	    System.out.println(beans.size());
	    SolrAllUtils.close();
	    return beans;
	}
	
	public static int deleteEmail(String messageID){
		SolrAllUtils.connection();
		HttpSolrServer server = SolrAllUtils.server;

	    UpdateResponse rsp = null;
		try {
			rsp = server.deleteByQuery( "all:"+ messageID);// delete 
			server.commit();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}  
		int status = rsp.getStatus();
	    System.out.println(status);
	    SolrAllUtils.close();
	    return status;
	}

}
