package com.integrity.dataSmart.titanGraph.dao;

import java.util.List;

import com.integrity.dataSmart.util.solr.Email2SolrUtil;

public class SaveShowLabelsDao {
	
	public void saveShowLabels(String id,List<String> labels){
	 Email2SolrUtil.connection();
     Email2SolrUtil.getEmailById(id,labels);
	 Email2SolrUtil.close();
     
  }
}