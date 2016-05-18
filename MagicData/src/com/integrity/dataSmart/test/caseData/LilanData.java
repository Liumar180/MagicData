package com.integrity.dataSmart.test.caseData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.titanGraph.pojo.Email;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * lilan案例
 *
 */
public class LilanData {
	
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) throws ParseException {
		 BaseConfiguration baseConfiguration = new BaseConfiguration();
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.11");
//		 baseConfiguration.setProperty("storage.hostname", "10.16.202.185");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 System.out.println("连接数据库...");
		 long s1 = System.currentTimeMillis();
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 long e1 = System.currentTimeMillis();
		 System.out.println("数据库连接成功："+(e1-s1)/1000+"秒");
		 
		 System.out.println("+++++++++++++++++++");
		 System.out.println("开始");
		
//		 addData(graph);
		 
//		 solrAddData(graph);
		 
		 graph.commit();
		 System.out.println("完成");
		 graph.shutdown();

	}
	
	/**
	 * 添加lilan案例邮件数据（在基础数据之后执行）
	 * @param graph
	 */
	public static void solrAddData(TitanGraph graph){
		 Iterator<Vertex> iterator = graph.getVertices("name", "lilan").iterator();
		 while (iterator.hasNext()) {
			 Vertex vv = iterator.next();
			 String idcard = vv.getProperty("idcard");
			 String country = vv.getProperty("country");
			 if ("China".equals(country)&&"130631118303121543".equals(idcard)) {
				 Set<String> set1 = vv.getPropertyKeys();
		 		 System.out.println("==================");
		 		 for (String string : set1) {
		 			System.out.println(string+"-->"+vv.getProperty(string));
		 		 }
		 		 Iterable<Vertex> it = vv.query().labels(DataType.EMAILLABEL).vertices();
		 		 
		 		List<Vertex> result = null;
				try {
					result = new ArrayList<Vertex>();
					result.addAll(IteratorUtils.toList(it.iterator()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (Vertex vertex : result) {
					Set<String> set2 = vertex.getPropertyKeys();
			 		 System.out.println("==================");
			 		 System.out.println("id:"+vertex.getId());
			 		 for (String string : set2) {
			 			System.out.println(string+"-->"+vertex.getProperty(string));
			 		 }
				}
				insertEmailToSolr(result);
				break;
			}
			
		}
		 
	}
	
	/**
	 * 根据邮件节点集合插入solr
	 * @param v
	 */
	private static void insertEmailToSolr(List<Vertex> v){
//		String url = "http://10.16.202.185/solr/collection1";
		String url = "http://192.168.40.11/solr/core0";
		HttpSolrServer server = null;
		try {
			List<Email> bs = new ArrayList<Email>();
			server = new HttpSolrServer(url);
			for (Vertex vertex : v) {
				Email email = new Email();
				email.setFrom(vertex.getProperty("from")+"");
				ArrayList<String> tos = new ArrayList<String>();
				tos.add(vertex.getProperty("to")+"");
				email.setTos(tos);
				email.setTitle(vertex.getProperty("title")+"");
				email.setContent(vertex.getProperty("content")+"");
				
				String time = vertex.getProperty("time")+"";
				Long t = Long.parseLong(time);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateTime = simpleDateFormat.format(t);
				email.setSendtime(dateTime);
				
				email.setVertexID(vertex.getId()+"");
				email.setId(UUID.randomUUID().toString());
				email.setType("EmailEvent");
				bs.add(email);
			}
			UpdateResponse response = server.addBeans(bs);
			server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (server != null) {
				server.shutdown();
			}
		}
	}
	
	/**
	 * 添加lilan案例基础数据
	 * @param graph
	 */
	public static void addData(TitanGraph graph) throws ParseException{

		// 添加lilan
		Vertex lilan = graph.addVertex(null);
		lilan.setProperty("type", "Person");
		lilan.setProperty("name", "lilan");
		lilan.setProperty("idcard", "130631118303121543");
		lilan.setProperty("country", "China");

		Vertex mobile = graph.addVertex(null);
		mobile.setProperty("type", "Phone");
		mobile.setProperty("model", "blackberry");
		mobile.setProperty("phonenum", "13326717078");

		Vertex email = graph.addVertex(null);
		email.setProperty("type", "Email");
		email.setProperty("email", "lilan@sina.com");
		email.setProperty("password", "llaa123");

		graph.addEdge(null, lilan, mobile, "own");
		graph.addEdge(null, lilan, email, "own");

		// 添加cuiyan
		Vertex cuiyan = graph.addVertex(null);
		cuiyan.setProperty("type", "Person");
		cuiyan.setProperty("name", "cuiyan");
		cuiyan.setProperty("idcard", "139631118309021333");
		cuiyan.setProperty("country", "China");

		Vertex mobile2 = graph.addVertex(null);
		mobile2.setProperty("type", "Phone");
		mobile2.setProperty("phonenum", "13103117678");

		Vertex email2 = graph.addVertex(null);
		email2.setProperty("type", "Email");
		email2.setProperty("email", "cuicui@163.com");
		email2.setProperty("password", "123qwe");

		Vertex location2 = graph.addVertex(null);
		location2.setProperty("type", "Location");
		location2.setProperty("address", "北京市某小区3号楼201");
		location2.setProperty("place", "39f,22f");

		Vertex account2 = graph.addVertex(null);
		account2.setProperty("type", "Account");
		account2.setProperty("domain", "autohome");
		account2.setProperty("uid", "17333877");
		account2.setProperty("username", "cuicuiauto");
		account2.setProperty("password", "weida");
		account2.setProperty("email", "cuicui@163.com");

		graph.addEdge(null, cuiyan, mobile2, "own");
		graph.addEdge(null, cuiyan, email2, "own");
		graph.addEdge(null, cuiyan, location2, "own");
		graph.addEdge(null, cuiyan, account2, "own");

		// 添加wangliang
		Vertex wangliang = graph.addVertex(null);
		wangliang.setProperty("type", "Person");
		wangliang.setProperty("name", "wangliang");
		wangliang.setProperty("idcard", "130635118008221765");
		wangliang.setProperty("country", "China");

		Vertex mobile3 = graph.addVertex(null);
		mobile3.setProperty("type", "Phone");
		mobile3.setProperty("phonenum", "13837210806");

		graph.addEdge(null, wangliang, mobile3, "own");

		// 添加cuijie
		Vertex cuijie = graph.addVertex(null);
		cuijie.setProperty("type", "Person");
		cuijie.setProperty("name", "cuijie");
		cuijie.setProperty("idcard", "13011111111112");
		cuijie.setProperty("country", "China");

		Vertex mobile4 = graph.addVertex(null);
		mobile4.setProperty("type", "Phone");
		mobile4.setProperty("phonenum", "13077886789");

		Vertex location4 = graph.addVertex(null);
		location4.setProperty("type", "Location");
		location4.setProperty("address", "云南省昆明市森林亿城小区5号楼302");
		location4.setProperty("place", "102.72f,25.05f");

		graph.addEdge(null, cuijie, mobile4, "own");
		graph.addEdge(null, cuijie, location4, "own");

		// wangliang --> lilan
		long time = sf.parse("2015-06-24 10:05:03").getTime();
		Vertex CallEvent = graph.addVertex(null);
		CallEvent.setProperty("type", "CallEvent");
		CallEvent.setProperty("from", "13837210806");
		CallEvent.setProperty("to", "13326717078");
		CallEvent.setProperty("time", time);
		CallEvent.setProperty("long", 30L);

		graph.addEdge(null, wangliang, CallEvent, "callfrom").setProperty(
				"eventtime", time);
		graph.addEdge(null, CallEvent, lilan, "callto").setProperty(
				"eventtime", time);

		long time2 = sf.parse("2015-06-24 13:20:12").getTime();
		Vertex CallEvent2 = graph.addVertex(null);
		CallEvent2.setProperty("type", "CallEvent");
		CallEvent2.setProperty("from", "13326717078");
		CallEvent2.setProperty("to", "13837210806");
		CallEvent2.setProperty("time", time2);
		CallEvent2.setProperty("long", 230L);

		// 添加电话关系
		graph.addEdge(null, lilan, CallEvent2, "callfrom").setProperty(
				"eventtime", time2);
		graph.addEdge(null, CallEvent2, wangliang, "callto").setProperty(
				"eventtime", time2);

		// cuiyan --> lilan
		long time3 = sf.parse("2015-06-24 15:10:03").getTime();
		Vertex CallEvent3 = graph.addVertex(null);
		CallEvent3.setProperty("type", "CallEvent");
		CallEvent3.setProperty("from", "13103117678");
		CallEvent3.setProperty("to", "13326717078");
		CallEvent3.setProperty("time", time3);
		CallEvent3.setProperty("long", 58L);

		graph.addEdge(null, cuiyan, CallEvent3, "callfrom").setProperty(
				"eventtime", time3);
		graph.addEdge(null, CallEvent3, lilan, "callto").setProperty(
				"eventtime", time3);

		long time4 = sf.parse("2015-06-23 21:10:03").getTime();
		Vertex CallEvent4 = graph.addVertex(null);
		CallEvent4.setProperty("type", "CallEvent");
		CallEvent4.setProperty("from", "13103117678");
		CallEvent4.setProperty("to", "13326717078");
		CallEvent4.setProperty("time", time4);
		CallEvent4.setProperty("long", 32L);

		graph.addEdge(null, cuiyan, CallEvent4, "callfrom").setProperty(
				"eventtime", time4);
		graph.addEdge(null, CallEvent4, lilan, "callto").setProperty(
				"eventtime", time4);
		long time5 = sf.parse("2015-06-20 19:40:20").getTime();
		Vertex CallEvent5 = graph.addVertex(null);
		CallEvent5.setProperty("type", "CallEvent");
		CallEvent5.setProperty("from", "13103117678");
		CallEvent5.setProperty("to", "13326717078");
		CallEvent5.setProperty("time", time5);
		CallEvent5.setProperty("long", 455L);

		graph.addEdge(null, cuiyan, CallEvent5, "callfrom").setProperty(
				"eventtime", time5);
		graph.addEdge(null, CallEvent5, lilan, "callto").setProperty(
				"eventtime", time5);
		long time7 = sf.parse("2015-06-19 21:11:12").getTime();
		Vertex CallEvent7 = graph.addVertex(null);
		CallEvent7.setProperty("type", "CallEvent");
		CallEvent7.setProperty("from", "13103117678");
		CallEvent7.setProperty("to", "13326717078");
		CallEvent7.setProperty("time", time7);
		CallEvent7.setProperty("long", 234L);

		graph.addEdge(null, cuiyan, CallEvent7, "callfrom").setProperty(
				"eventtime", time7);
		graph.addEdge(null, CallEvent7, lilan, "callto").setProperty(
				"eventtime", time7);

		// lilan-->cuiyan
		long time8 = sf.parse("2015-06-24 14:50:21").getTime();
		Vertex CallEvent8 = graph.addVertex(null);
		CallEvent8.setProperty("type", "CallEvent");
		CallEvent8.setProperty("from", "13326717078");
		CallEvent8.setProperty("to", "13103117678");
		CallEvent8.setProperty("time", time8);
		CallEvent8.setProperty("long", 13L);

		graph.addEdge(null, lilan, CallEvent8, "callfrom").setProperty(
				"eventtime", time8);
		graph.addEdge(null, CallEvent8, cuiyan, "callto").setProperty(
				"eventtime", time8);
		long time9 = sf.parse("2015-06-23 17:20:50").getTime();
		Vertex CallEvent9 = graph.addVertex(null);
		CallEvent9.setProperty("type", "CallEvent");
		CallEvent9.setProperty("from", "13326717078");
		CallEvent9.setProperty("to", "13103117678");
		CallEvent9.setProperty("time", time9);
		CallEvent9.setProperty("long", 323L);

		graph.addEdge(null, lilan, CallEvent9, "callfrom").setProperty(
				"eventtime", time9);
		graph.addEdge(null, CallEvent9, cuiyan, "callto").setProperty(
				"eventtime", time9);
		long time0 = sf.parse("2015-06-20 08:50:33").getTime();
		Vertex CallEvent0 = graph.addVertex(null);
		CallEvent0.setProperty("type", "CallEvent");
		CallEvent0.setProperty("from", "13326717078");
		CallEvent0.setProperty("to", "13103117678");
		CallEvent0.setProperty("time", time0);
		CallEvent0.setProperty("long", 456L);

		graph.addEdge(null, lilan, CallEvent0, "callfrom").setProperty(
				"eventtime", time0);
		graph.addEdge(null, CallEvent0, cuiyan, "callto").setProperty(
				"eventtime", time0);

		// cuiyan-->cuijie
		long time11 = sf.parse("2015-06-26 20:18:19").getTime();
		Vertex CallEvent11 = graph.addVertex(null);
		CallEvent11.setProperty("type", "CallEvent");
		CallEvent11.setProperty("from", "13103117678");
		CallEvent11.setProperty("to", "13077886789");
		CallEvent11.setProperty("time", time11);
		CallEvent11.setProperty("long", 654L);

		graph.addEdge(null, cuiyan, CallEvent11, "callfrom").setProperty(
				"eventtime", time11);
		graph.addEdge(null, CallEvent11, cuijie, "callto").setProperty(
				"eventtime", time11);

		long time12 = sf.parse("2015-06-25 12:11:12").getTime();
		Vertex CallEvent12 = graph.addVertex(null);
		CallEvent12.setProperty("type", "CallEvent");
		CallEvent12.setProperty("from", "13103117678");
		CallEvent12.setProperty("to", "13077886789");
		CallEvent12.setProperty("time", time12);
		CallEvent12.setProperty("long", 222L);

		graph.addEdge(null, cuiyan, CallEvent12, "callfrom").setProperty(
				"eventtime", time12);
		graph.addEdge(null, CallEvent12, cuijie, "callto").setProperty(
				"eventtime", time12);

		// 邮件 cuiyan-->lilan
		long time13 = sf.parse("2015-06-21 20:31:17").getTime();
		Vertex EmailEvent = graph.addVertex(null);
		EmailEvent.setProperty("type", "EmailEvent");
		EmailEvent.setProperty("from", "cuicui@163.com");
		EmailEvent.setProperty("to", "lilan@sina.com");
		EmailEvent.setProperty("title", "我想你了");
		EmailEvent.setProperty("content", "很长时间没见你，非常想念你。");
		EmailEvent.setProperty("time", time13);

		graph.addEdge(null, cuiyan, EmailEvent, "emailfrom").setProperty(
				"eventtime", time13);
		graph.addEdge(null, EmailEvent, lilan, "emailto").setProperty(
				"eventtime", time13);

		long time14 = sf.parse("2015-06-22 12:11:12").getTime();
		Vertex EmailEvent4 = graph.addVertex(null);
		EmailEvent4.setProperty("type", "EmailEvent");
		EmailEvent4.setProperty("from", "lilan@sina.com");
		EmailEvent4.setProperty("to", "cuicui@163.com");
		EmailEvent4.setProperty("title", "分开");
		EmailEvent4.setProperty("content",
				"我想了很多很多，想到我们刚刚在一起的时候，和现在。真的差很多，不知道是失去了恋爱开始的感觉。");
		EmailEvent4.setProperty("time", time14);

		graph.addEdge(null, lilan, EmailEvent4, "emailfrom").setProperty(
				"eventtime", time14);
		graph.addEdge(null, EmailEvent4, cuiyan, "emailto").setProperty(
				"eventtime", time14);

		long time15 = sf.parse("2015-06-22 21:23:18").getTime();
		Vertex EmailEvent5 = graph.addVertex(null);
		EmailEvent5.setProperty("type", "EmailEvent");
		EmailEvent5.setProperty("from", "cuicui@163.com");
		EmailEvent5.setProperty("to", "lilan@sina.com");
		EmailEvent5.setProperty("title", "来我这吧");
		EmailEvent5.setProperty("content", "到底什么时候来我这？");
		EmailEvent5.setProperty("time", time15);

		graph.addEdge(null, cuiyan, EmailEvent5, "emailfrom").setProperty(
				"eventtime", time15);
		graph.addEdge(null, EmailEvent5, lilan, "emailto").setProperty(
				"eventtime", time15);

		long time16 = sf.parse("2015-06-24 10:50:21").getTime();
		Vertex EmailEvent6 = graph.addVertex(null);
		EmailEvent6.setProperty("type", "EmailEvent");
		EmailEvent6.setProperty("from", "cuicui@163.com");
		EmailEvent6.setProperty("to", "lilan@sina.com");
		EmailEvent6.setProperty("title", "见面聊聊吧");
		EmailEvent6.setProperty("content", "还是见面聊聊吧，下午三点我们中山公园见！");
		EmailEvent6.setProperty("time", time16);

		graph.addEdge(null, cuiyan, EmailEvent6, "emailfrom").setProperty(
				"eventtime", time16);
		graph.addEdge(null, EmailEvent6, lilan, "emailto").setProperty(
				"eventtime", time16);

		// 添加登录事件对象
		long time17 = sf.parse("2015-06-26 10:50:21").getTime();
		Vertex loginEvent = graph.addVertex(null);
		loginEvent.setProperty("type", "LoginEvent");
		loginEvent.setProperty("domain", "autohome");
		loginEvent.setProperty("username", "cuicuiauto");
		loginEvent.setProperty("ip", "116.52.147.50");
		loginEvent.setProperty("time", time17);

		graph.addEdge(null, cuiyan, loginEvent, "login").setProperty(
				"eventtime", time17);

		long time18 = sf.parse("2015-06-26 20:33:46").getTime();
		Vertex loginEvent2 = graph.addVertex(null);
		loginEvent2.setProperty("type", "LoginEvent");
		loginEvent2.setProperty("domain", "autohome");
		loginEvent2.setProperty("username", "cuicuiauto");
		loginEvent2.setProperty("ip", "116.52.147.50");
		loginEvent2.setProperty("time", time18);

		graph.addEdge(null, cuiyan, loginEvent2, "login").setProperty(
				"eventtime", time18);

	}
	
}
