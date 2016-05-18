package com.integrity.dataSmart.test;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;

import com.thinkaurelius.titan.core.Cardinality;
import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Vertex;

public class TestDEMO1 {

	public static void main(String[] args) {
		 BaseConfiguration baseConfiguration = new BaseConfiguration();
		 System.out.println("开始连接数据库...");
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.11");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 System.out.println("数据库已连接");
		 TitanTransaction action = graph.newTransaction();
		 TitanManagement tm = graph.getManagementSystem();
		 PropertyKey numid = null;
		 PropertyKey nickname = null;
		 PropertyKey type = null;
		 if(!tm.containsPropertyKey("numid")){
			 numid = tm.makePropertyKey("numid").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsPropertyKey("nickname")){
			 nickname = tm.makePropertyKey("nickname").dataType(String.class).cardinality ( Cardinality.SET ).make();
		 }
		 if(!tm.containsPropertyKey("type")){
			 type = tm.makePropertyKey("type").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 tm.commit();
		
 /*       TitanVertex titangraph = action.addVertexWithLabel("QQ");
		 titangraph.setProperty("numid", "10088");
		 titangraph.setProperty("type", "IM");
		 titangraph.addProperty("nickname", "netwweew");
		 titangraph.addProperty("nickname", "netwweew1554");
		 action.commit();*/
		 action = graph.newTransaction();
		 Iterable<Vertex> iter = graph.query().has("numid", "10088").vertices();
		 Iterator<Vertex> ss = iter.iterator();
		 while(ss.hasNext()){
			 Vertex list = ss.next();
			 Set<String> sets = list.getPropertyKeys();
			 for (String key : sets) {
					System.out.println("key:"+key+"|value:"+list.getProperty(key));
				}
			 
		 }
		 //action.commit();
		 graph.shutdown();
	
	}
	
}
