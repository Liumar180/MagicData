package com.integrity.dataSmart.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;

import com.integrity.dataSmart.common.DataType;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class deletePersons {
	public static void main(String[] args) {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		 System.out.println("开始连接数据库...");
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 System.out.println("数据库已连接");
		 
		 Iterable<Vertex> G1 =  graph.getVertices("idcard","479963776");
		 Iterator<Vertex> as = G1.iterator();
		 List<Edge> eList = new ArrayList<Edge>();
		 List<Vertex> vList = new ArrayList<Vertex>();
		 if(as.hasNext()){
		 Vertex  karry = as.next();
		 if(karry.getProperty("type").equals("IM")){
			 Iterator<Vertex> ops=  karry.query().labels(new String[]{"own"}).vertices().iterator();
			 int number = 0;
			 while (ops.hasNext()) {
			 Vertex s = ops.next();
			 Set<String> sets = s.getPropertyKeys();
				for (String key : sets) {
					System.out.println("key:"+key+"|value:"+s.getProperty(key));
				}
				 number += 1;
			 }
			 if(number > 1){
				 Iterator<Vertex> ops1=  karry.query().labels(new String[]{"own"}).vertices().iterator();
				 int k = 0;
				 while(ops1.hasNext()){
					 
					System.out.println("person...........");
					Vertex s1 = ops1.next();
					String nickname = s1.getProperty("nickname");
					if(nickname == null){
						k = k+1;
                     if(k==1){
					 Iterator<Edge> it = s1.query().labels("own").edges().iterator();;
					 while (it.hasNext()) {
						 Edge edge = it.next();
							if(!eList.contains(edge)){
								eList.add(edge);
							}
							}
					 if(!vList.contains(s1)) vList.add(s1);
					 for (int j = eList.size()-1; j >-1; j--) {
							graph.removeEdge(eList.get(j));
							}
					 for (int i = vList.size()-1; i >-1 ; i--) {
							graph.removeVertex(vList.get(i));
							}
					 graph.commit();
					}
				 }
					 
					
				 }
			 }
				 
			 }
		 }
		 graph.commit();
		 graph.shutdown();
	}

}
