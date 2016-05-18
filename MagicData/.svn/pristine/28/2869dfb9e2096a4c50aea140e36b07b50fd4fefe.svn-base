package com.integrity.dataSmart.test.caseData;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.configuration.BaseConfiguration;

import com.integrity.dataSmart.common.DataType;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;
/**
 * FBI案例
 *
 */
public class FBIData {
	
	public static SimpleDateFormat sf = new SimpleDateFormat(DataType.DATEFORMATSTR);

	public static void main(String[] args) throws ParseException {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		baseConfiguration.setProperty("storage.backend", "hbase");
		baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		baseConfiguration.setProperty("storage.tablename", "titan");
		System.out.println("连接数据库...");
		long s1 = System.currentTimeMillis();
		TitanGraph graph = TitanFactory.open(baseConfiguration);
		long e1 = System.currentTimeMillis();
		System.out.println("数据库连接成功：" + (e1 - s1) / 1000 + "秒");

//		addData(graph);

		System.out.println("ok");
		graph.commit();
		graph.shutdown();

	}
	
	/**
	 * 添加FBI案例数据
	 * @param graph
	 * @throws ParseException
	 */
	public static void addData(TitanGraph graph) throws ParseException{
		
		Vertex unknow = graph.addVertex(null);
		unknow.setProperty("type", "Person");
		unknow.setProperty("name", "Unknow");
		
		Vertex email = graph.addVertex(null);
		email.setProperty("type", "Email");
		email.setProperty("email", "xxx@gmail.com");
		
		Vertex account2 = graph.addVertex(null);
		account2.setProperty("type", "Account");
		account2.setProperty("domain", "military");
		account2.setProperty("uid", "17333877");
		account2.setProperty("username", "xxxmili");
		account2.setProperty("password", "1QAZ9IJN");
		account2.setProperty("email", "xxx@gmail.com");
		
		graph.addEdge(null, unknow, email, "own");
		graph.addEdge(null, unknow, account2, "own");

		
		long time2 = sf.parse("2015-03-01 12:01:46").getTime();
		
		Vertex loginEvent = graph.addVertex(null);
		loginEvent.setProperty("type", "LoginEvent");
		loginEvent.setProperty("domain", "gmail.com");
		loginEvent.setProperty("username", "xxx@gmail.com");
		loginEvent.setProperty("ip", "116.52.147.50");
		loginEvent.setProperty("time", time2);

		graph.addEdge(null, unknow, loginEvent, "login").setProperty(
				"eventtime", time2);
		
		
		Vertex paula = graph.addVertex(null);
		paula.setProperty("type", "Person");
		paula.setProperty("name", "PaulaBroadWell");
		
		Vertex email2 = graph.addVertex(null);
		email2.setProperty("type", "Email");
		email2.setProperty("email", "paulabroadwell@yahoo.com");

		long time3 = sf.parse("2012-05-20 10:05:01").getTime();
		Vertex account3 = graph.addVertex(null);
		account3.setProperty("type", "Account");
		account3.setProperty("domain", "Stratfor");
		account3.setProperty("username", "paulabroadwell");
		account3.setProperty("email", "paulabroadwell@yahoo.com");
		account3.setProperty("password", "vsKLVg8L");
		account3.setProperty("createtime", time3);
		
		graph.addEdge(null, paula, email2, "own");
		graph.addEdge(null, paula, account3, "own");
		
		long time4 = sf.parse("2015-03-01 12:10:10").getTime();
		Vertex loginEvent2 = graph.addVertex(null);
		loginEvent2.setProperty("type", "LoginEvent");
		loginEvent2.setProperty("domain", "yahoo.com");
		loginEvent2.setProperty("username", "paulabroadwell@yahoo.com");
		loginEvent2.setProperty("ip", "116.52.147.50");
		loginEvent2.setProperty("time", time4);
		
		graph.addEdge(null, paula, loginEvent2, "login").setProperty(
				"eventtime", time4);
		
		Vertex david = graph.addVertex(null);
		david.setProperty("type", "Person");
		david.setProperty("name", "DavidPetraeus");
		
		Vertex email3 = graph.addVertex(null);
		email3.setProperty("type", "Email");
		email3.setProperty("email", "davidpetr@cia.gov");

		
		
		long time5 = sf.parse("2010-02-10 10:00:00").getTime();
		Vertex account4 = graph.addVertex(null);
		account4.setProperty("type", "Account");
		account4.setProperty("domain", "CIA.gov");
		account4.setProperty("username", "david");
		account4.setProperty("email", "davidpetr@cia.gov");
		account4.setProperty("password", "1QAZ9IJN");
		account4.setProperty("createtime", time5);
		graph.addEdge(null, david, email3, "own");
		graph.addEdge(null, david, account4, "own");
		
	}
	
}
