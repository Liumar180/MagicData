package com.integrity.dataSmart.test;

import java.util.Iterator;

import org.apache.commons.configuration.BaseConfiguration;

import com.integrity.dataSmart.common.DataType;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author liuBf
 * 用来删除对象
 *
 */
public class Testcs {
	public static void main(String[] args) {
		 BaseConfiguration baseConfiguration = new BaseConfiguration();
		 System.out.println("开始连接数据库...");
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 System.out.println("数据库已连接");

     for(int i1=0;i1<10;i1++){
			 Iterable<Vertex> lisiX = graph.getVertices("name", "刘宝芬");// 12w12w12@qq.com
			 if(lisiX.iterator().hasNext()){
			 Vertex lisi = lisiX.iterator().next();
			 if(lisi.getProperty("type").equals("Person")){
			 Iterator<Vertex> it2 = lisi.query().labels(DataType.ALLLABEL).vertices().iterator();;
			 while (it2.hasNext()) {
				 graph.removeVertex(it2.next());
				}
			 
			 Iterator<Edge> it4 = lisi.query().labels(DataType.ALLLABEL).edges().iterator();;
			 while (it4.hasNext()) {
				 graph.removeEdge(it4.next());
				}
			 lisi.remove();
			 graph.commit();
			 System.out.println("删除1");
			 }
				 }
					 }
     /*
		 
		 for(int i1=0;i1<10;i1++){
			 
			 Iterable<Vertex> lisiX = graph.getVertices("name", "kill");// 12w12w12@qq.com
			 if(lisiX.iterator().hasNext()){
			 Vertex lisi = lisiX.iterator().next();
			 if(lisi.getProperty("type").equals("Person")){
			 Iterator<Vertex> it2 = lisi.query().labels(DataType.ALLLABEL).vertices().iterator();;
			 while (it2.hasNext()) {
				 graph.removeVertex(it2.next());
				}
			 
			 Iterator<Edge> it4 = lisi.query().labels(DataType.ALLLABEL).edges().iterator();;
			 while (it4.hasNext()) {
				 graph.removeEdge(it4.next());
				}
			 lisi.remove();
			 graph.commit();
			 System.out.println("删除2");
			 }
				 }
					 }
		 
		 for(int i=0;i<10;i++){
		 Iterable<Vertex> G =  graph.getVertices("numid","1151694121");
		 if(G.iterator().hasNext()){
		 Vertex  karry = G.iterator().next();
		 Set<String> set = karry.getPropertyKeys();
		 for (String key : set) {
			System.out.println("key:"+key+"|value:"+karry.getProperty(key));
		}
		 if(karry.getProperty("type").equals("IM")){
		//关联节点和边删除
		 Iterator<Vertex> ite = karry.query().labels(DataType.ALLLABEL).vertices().iterator();
		 while (ite.hasNext()) {
			 graph.removeVertex(ite.next());
		}
		 Iterator<Edge> it3 = karry.query().labels(DataType.ALLLABEL).edges().iterator();
		 while (it3.hasNext()) {
			 graph.removeEdge(it3.next());
		}
		 karry.remove();
		 graph.commit();
		 System.out.println("删除3");
		 }
		 }
		 }
		 
		 for(int i=0;i<10;i++){
			 Iterable<Vertex> G =  graph.getVertices("numid","111111");
			 if(G.iterator().hasNext()){
			 Vertex  karry = G.iterator().next();
			 Set<String> set = karry.getPropertyKeys();
			 for (String key : set) {
				System.out.println("key:"+key+"|value:"+karry.getProperty(key));
			}
			 if(karry.getProperty("type").equals("IM")){
			//关联节点和边删除
			 Iterator<Vertex> ite = karry.query().labels(DataType.ALLLABEL).vertices().iterator();
			 while (ite.hasNext()) {
				 graph.removeVertex(ite.next());
			}
			 Iterator<Edge> it3 = karry.query().labels(DataType.ALLLABEL).edges().iterator();
			 while (it3.hasNext()) {
				 graph.removeEdge(it3.next());
			}
			 karry.remove();
			 graph.commit();
			 System.out.println("删除4");
			 }
			 }
			 }
		 
	     for(int i1=0;i1<10;i1++){
				 Iterable<Vertex> lisiX = graph.getVertices("groupnum", "1234");// 12w12w12@qq.com
				 if(lisiX.iterator().hasNext()){
				 Vertex lisi = lisiX.iterator().next();
				 if(lisi.getProperty("type").equals("StayEvent")){
				 Iterator<Vertex> it2 = lisi.query().labels(DataType.ALLLABEL).vertices().iterator();;
				 while (it2.hasNext()) {
					 graph.removeVertex(it2.next());
					}
				 
				 Iterator<Edge> it4 = lisi.query().labels(DataType.ALLLABEL).edges().iterator();;
				 while (it4.hasNext()) {
					 graph.removeEdge(it4.next());
					}
				 lisi.remove();
				 graph.commit();
				 System.out.println("删除5");
				 }
					 }
						 }
	     
	     
	     
	     


		 *//******************//*
		//关联节点和边删除
		  List<Vertex> vList = new ArrayList<Vertex>();
		  List<Edge> eList = new ArrayList<Edge>();
		 Vertex datas = graph.getVertices("name","陈夕群").iterator().next();
		 
		 if(datas.getProperty("type").equals("Person")){
		 Iterator<Vertex> ite = datas.query().labels("stay").vertices().iterator();
		 while (ite.hasNext()) {
		 Vertex vv = ite.next();
		 if(!vList.contains(vv)) vList.add(vv);
		//  vv.remove();
		//  graph.removeVertex(vv);
		}
		 Iterator<Vertex> ites = datas.query().labels("cohabit").vertices().iterator();
		 while (ites.hasNext()) {
			 Vertex vv = ites.next();
			 if(!vList.contains(vv)) vList.add(vv);
			//  vv.remove();
			//  graph.removeVertex(vv);
			}
		 Iterator<Edge> it3 = datas.query().labels("cohabit").edges().iterator();
		 while (it3.hasNext()) {
		 Edge edge = it3.next();
		if(eList.contains(edge))eList.add(edge);
		}
		 
		 Iterator<Edge> it3s = datas.query().labels("stay").edges().iterator();
		 while (it3s.hasNext()) {
		 Edge edge = it3s.next();
		if(eList.contains(edge))eList.add(edge);
		}
		//  lilan.remove();
		 if(!vList.contains(datas)) vList.add(datas);
	}
		 for (int i = vList.size()-1; i >-1 ; i--) {
			 System.out.println("删除3232");
		graph.removeVertex(vList.get(i));
		}
		 for (int j = eList.size()-1; j >-1; j--) {
			 System.out.println("删除2323");
		graph.removeEdge(eList.get(j));
		}*/
		graph.commit();

	
		 
		 graph.shutdown();
	}
}
