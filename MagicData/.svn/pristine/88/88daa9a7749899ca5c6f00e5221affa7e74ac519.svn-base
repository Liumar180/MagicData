package com.integrity.dataSmart.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class testHotelDemo {
	public static void main(String[] args) {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		 System.out.println("开始连接数据库...");
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 System.out.println("数据库已连接");
		 
		Iterable<Vertex> G1 =  graph.getVertices("idcard","341223198404274536");
		 Iterator<Vertex> as = G1.iterator();
		 while(as.hasNext()){
		 Vertex  karry = (Vertex) as.next();
		 if(karry.getProperty("type").equals("Person")){
			 Iterable<Vertex> sha=  karry.query().labels(new String[]{"stay"}).vertices();//同住人
			 Iterator<Vertex> shares =sha.iterator();
			 while (shares.hasNext()) {
				 Vertex stay = shares.next();
				 Set<String> sets = stay.getPropertyKeys();
					for (String key : sets) {
						System.out.println("key:"+key+"|value:"+stay.getProperty(key));
					}
					Iterable<Vertex> shareperson=  stay.query().labels(new String[]{"cohabit"}).vertices();//同住人
				Iterator<Vertex> is = shareperson.iterator();
				while(is.hasNext()){
					System.out.println("同住人");
					Vertex stays = is.next();
					 Set<String> set = stays.getPropertyKeys();
						for (String key : set) {
							System.out.println("key:"+key+"|value:"+stays.getProperty(key));
						}
				}
			
			 }
			 }
		 }
		 //graph.commit();
		 graph.shutdown();
		 
		 
	}

}
