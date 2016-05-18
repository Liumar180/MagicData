package com.integrity.dataSmart.impAnalyImport.email2solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;

import com.integrity.dataSmart.impAnalyImport.bean.DirectoryTree;

public class TreeTest {

	public static void main(String[] args) {
		SolrAllUtils.connection();
		DirectoryTree tree = new DirectoryTree();
		tree.setId("erer-123-2222");
		tree.setPid("root");
		tree.setType("DirectoryTree");
		tree.setName("\\a\\b\\c");
//		DirectoryTree tree2 = new DirectoryTree();
//		tree2.setId("erer2");
//		tree2.setPid("erer");
//		tree2.setType("treeType");
//		tree2.setName("树根2");
		
		UpdateResponse response = null;
		try {
			response = SolrAllUtils.server.addBean(tree);
			int i = response.getStatus();
			System.out.println(i);
//			response = Email2SolrUtil.server.addBean(tree2);
//			int j = response.getStatus();
//			System.out.println(j);
			SolrAllUtils.server.commit();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} 
		
		SolrQuery query = new SolrQuery();
	    query.setQuery( "name:\\aaa\\bbb\\ccc AND type:DirectoryTree" );
	    QueryResponse rsp = null;
		try {
			rsp = SolrAllUtils.server.query( query );
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}  
	    
	    List<DirectoryTree> beans = rsp.getBeans(DirectoryTree.class);
	    for (DirectoryTree directoryTree : beans) {
	    	System.out.println(directoryTree.toString());
			
		}
		
		SolrAllUtils.close();

	}

}
