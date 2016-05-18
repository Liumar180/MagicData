package com.integrity.dataSmart.test.caseData;

import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;

import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * 建索引
 *
 */
public class CreateIndex {

	public static void main(String[] args) {
		 BaseConfiguration baseConfiguration = new BaseConfiguration();
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.11");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 System.out.println("连接数据库...");
		 long s1 = System.currentTimeMillis();
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 long e1 = System.currentTimeMillis();
		 System.out.println("数据库连接成功："+(e1-s1)/1000+"秒");
		 
//		 createIndex(graph);
		 
		 graph.commit();
		 System.out.println("业务处理完成。");
		 graph.shutdown();

	}
	
	/**
	 * 新建索引
	 * @param graph
	 */
	public static void createIndex(TitanGraph graph){
		TitanManagement tm = graph.getManagementSystem();
		
		//边索引 
		PropertyKey eventtime = tm.makePropertyKey("eventtime").dataType(Long.class).make();
		tm.buildIndex("byEventtime", Edge.class).addKey(eventtime).buildCompositeIndex();
		
		PropertyKey grouptype = tm.makePropertyKey("grouptype").dataType(String.class).make();
		tm.buildIndex("byGrouptype", Edge.class).addKey(grouptype).buildCompositeIndex();
		
		PropertyKey relationtype = tm.makePropertyKey("relationtype").dataType(String.class).make();
		tm.buildIndex("byRelationtype", Edge.class).addKey(relationtype).buildCompositeIndex();
		
		// 点索引 
		PropertyKey username = tm.makePropertyKey("username").dataType(String.class).make();
		tm.buildIndex("byUsername", Vertex.class).addKey(username).buildCompositeIndex();
		
		PropertyKey password = tm.makePropertyKey("password").dataType(String.class).make();
		tm.buildIndex("byPassword", Vertex.class).addKey(password).buildCompositeIndex();
		
		PropertyKey email = tm.makePropertyKey("email").dataType(String.class).make();
		tm.buildIndex("byEmail", Vertex.class).addKey(email).buildCompositeIndex();
		
		PropertyKey name = tm.makePropertyKey("name").dataType(String.class).make();
		tm.buildIndex("byName", Vertex.class).addKey(name).buildCompositeIndex();
		
		PropertyKey domain = tm.makePropertyKey("domain").dataType(String.class).make();
		tm.buildIndex("byDomain", Vertex.class).addKey(domain).buildCompositeIndex();
		
		PropertyKey idcard = tm.makePropertyKey("idcard").dataType(String.class).make();
		tm.buildIndex("byIdcard", Vertex.class).addKey(idcard).buildCompositeIndex();
		
		PropertyKey phonenum = tm.makePropertyKey("phonenum").dataType(String.class).make();
		tm.buildIndex("byPhonenum", Vertex.class).addKey(phonenum).buildCompositeIndex();
		
		PropertyKey type = tm.makePropertyKey("type").dataType(String.class).make();
		tm.buildIndex("byType", Vertex.class).addKey(type).buildCompositeIndex();
		
		PropertyKey numid = tm.makePropertyKey("numid").dataType(String.class).make();
		tm.buildIndex("byNumid", Vertex.class).addKey(numid).buildCompositeIndex();
		
		PropertyKey groupnum = tm.makePropertyKey("groupnum").dataType(String.class).make();
		tm.buildIndex("byGroupnum", Vertex.class).addKey(groupnum).buildCompositeIndex();
		
		PropertyKey ownerqq = tm.makePropertyKey("ownerqq").dataType(String.class).make();
		tm.buildIndex("byOwnerqq", Vertex.class).addKey(ownerqq).buildCompositeIndex();
		
		PropertyKey groupname = tm.makePropertyKey("groupname").dataType(String.class).make();
		tm.buildIndex("byGroupname", Vertex.class).addKey(groupname).buildCompositeIndex();
		
		//hotelid,orderno,arrivaldate,departuredate,hotelflag酒店
		PropertyKey hotelid = tm.makePropertyKey("hotelid").dataType(String.class).make();
		tm.buildIndex("byHotelid", Vertex.class).addKey(hotelid).buildCompositeIndex();

		PropertyKey orderno = tm.makePropertyKey("orderno").dataType(String.class).make();
		tm.buildIndex("byOrderno", Vertex.class).addKey(orderno).buildCompositeIndex();
		
		PropertyKey arrivaldate = tm.makePropertyKey("arrivaldate").dataType(Long.class).make();
		tm.buildIndex("byArrivaldate", Vertex.class).addKey(arrivaldate).buildCompositeIndex();
		
		PropertyKey departuredate = tm.makePropertyKey("departuredate").dataType(Long.class).make();
		tm.buildIndex("byDeparturedate", Vertex.class).addKey(departuredate).buildCompositeIndex();
		
		PropertyKey hotelflag = tm.makePropertyKey("hotelflag").dataType(String.class).make();
		tm.buildIndex("byHotelflag", Vertex.class).addKey(hotelflag).buildCompositeIndex();

		tm.commit();
		graph.commit();
	}
	
	/**
	 * 查询索引
	 * @param graph
	 */
	public static void searchIndex(TitanGraph graph) {
		 Set<String> set = graph.getIndexedKeys(Vertex.class);
		 System.out.println("indexkey:");
 		 for (String string : set) {
				System.out.println("	"+string);
		 }
// 		 Set<String> set2 = graph.getIndexedKeys(Edge.class);
// 		 for (String string : set2) {
//				System.out.println("indexkey2:"+string);
//		 }
	}

}
