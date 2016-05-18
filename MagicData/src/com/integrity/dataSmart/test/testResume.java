package com.integrity.dataSmart.test;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class testResume {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		 System.out.println("开始连接数据库...");
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 System.out.println("数据库已连接");
		 
		 Iterable<Vertex> G1 =  graph.getVertices("email","Kim Alexander");
		 Iterator<Vertex> as = G1.iterator();
		 while(as.hasNext()){
		 Vertex  karry = as.next();
		 Set<String> set = karry.getPropertyKeys();
			for (String key : set) {
				System.out.println("key:"+key+"|value:"+karry.getProperty(key));
			}
		 if(karry.getProperty("type").equals("Email")){
			 
			 Iterator<Vertex> ops=  karry.query().labels(new String[]{"own"}).vertices().iterator();
			 while (ops.hasNext()) {
				Vertex s = ops.next();
				Set<String> sets = s.getPropertyKeys();
				for (String key : sets) {
					System.out.println("类型："+s.getProperty("type"));
					System.out.println("key:"+key+"|value:"+s.getProperty(key));
				}
			}
			 
		 }
		
			 }
		 
		 graph.shutdown();

	}

}
