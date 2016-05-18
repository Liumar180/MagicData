package com.integrity.dataSmart.test.caseData;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.configuration.BaseConfiguration;

import com.integrity.dataSmart.common.DataType;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * 新疆案例 
 *
 */
public class XinJiangData {
	
	public static SimpleDateFormat sf = new SimpleDateFormat(DataType.DATEFORMATSTR);

	public static void main(String[] args) throws ParseException {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		baseConfiguration.setProperty("storage.backend", "hbase");
		baseConfiguration.setProperty("storage.hostname", "192.168.40.11");
		baseConfiguration.setProperty("storage.tablename", "titan");
		System.out.println("连接数据库...");
		long s1 = System.currentTimeMillis();
		TitanGraph graph = TitanFactory.open(baseConfiguration);
		long e1 = System.currentTimeMillis();
		System.out.println("数据库连接成功：" + (e1 - s1) / 1000 + "秒");

//		addXinJiangData(graph);

		System.out.println("ok");
		graph.commit();
		graph.shutdown();

	}
	
	/**
	 * 新疆案例 （人物添加不涉及邮件部分）
	 * @param graph
	 * @throws ParseException
	 */
	public static void addXinJiangData(TitanGraph graph) throws ParseException{
		Vertex unknow = graph.addVertex(null);
		unknow.setProperty("type", "Person");
		unknow.setProperty("name", "艾迪亚吐尔逊");
		unknow.setProperty("idcard", "652928199011062553");
		
		Vertex mobile = graph.addVertex(null);
		mobile.setProperty("type", "Phone");
		mobile.setProperty("model", "blackberry");
		mobile.setProperty("phonenum", "18399666928");
		
		Vertex im = graph.addVertex(null);
		im.setProperty("type", "IM");
		im.setProperty("domain", "qq");
		im.setProperty("numid", "113434445");
		
		Vertex email = graph.addVertex(null);
		email.setProperty("type", "Email");
		email.setProperty("email", "aidiyatusixun@gmail.com");
		
		Vertex email2 = graph.addVertex(null);
		email2.setProperty("type", "Email");
		email2.setProperty("email", "admin@qingfeng.com");
		
		Vertex account2 = graph.addVertex(null);
		account2.setProperty("type", "Account");
		account2.setProperty("domain", "qingfeng");
		account2.setProperty("uid", "17333877");
		account2.setProperty("username", "admin");
		account2.setProperty("password", "aidiyatusixun");
		account2.setProperty("email", "admin@qingfeng.com");
		
		graph.addEdge(null, unknow, email, "own");
		graph.addEdge(null, unknow, email2, "own");
		graph.addEdge(null, unknow, account2, "own");
		graph.addEdge(null, unknow, mobile, "own");
		graph.addEdge(null, unknow, im, "own");
		
		Vertex p1 = graph.addVertex(null);
		p1.setProperty("type", "Person");
		p1.setProperty("name", "司马夜");
		Vertex e1 = graph.addVertex(null);
		e1.setProperty("type", "Email");
		e1.setProperty("email", "sima163@163.com");
		graph.addEdge(null, p1, e1, "own");
		
		Vertex p2 = graph.addVertex(null);
		p2.setProperty("type", "Person");
		p2.setProperty("name", "阿朴杜");
		Vertex e2 = graph.addVertex(null);
		e2.setProperty("type", "Email");
		e2.setProperty("email", "1315657889@qq.com");
		graph.addEdge(null, p2, e2, "own");
		
		Vertex p3 = graph.addVertex(null);
		p3.setProperty("type", "Person");
		p3.setProperty("name", "阿力木");
		Vertex e3 = graph.addVertex(null);
		e3.setProperty("type", "Email");
		e3.setProperty("email", "alimu@sina.com");
		graph.addEdge(null, p3, e3, "own");
		
		Vertex p4 = graph.addVertex(null);
		p4.setProperty("type", "Person");
		p4.setProperty("name", "莫哈默德");
		Vertex e4 = graph.addVertex(null);
		e4.setProperty("type", "Email");
		e4.setProperty("email", "mohamode@gmail.com");
		graph.addEdge(null, p4, e4, "own");
		
		Vertex p5 = graph.addVertex(null);
		p5.setProperty("type", "Person");
		p5.setProperty("name", "买买提");
		Vertex e5 = graph.addVertex(null);
		e5.setProperty("type", "Email");
		e5.setProperty("email", "maimiatimail@126.com");
		graph.addEdge(null, p5, e5, "own");
		
		Vertex p6 = graph.addVertex(null);
		p6.setProperty("type", "Person");
		p6.setProperty("name", "阿凡提");
		Vertex e6 = graph.addVertex(null);
		e6.setProperty("type", "Email");
		e6.setProperty("email", "maifanti@sina.com");
		graph.addEdge(null, p6, e6, "own");
		
		Vertex p7 = graph.addVertex(null);
		p7.setProperty("type", "Person");
		p7.setProperty("name", "啊夏娜");
		Vertex e7 = graph.addVertex(null);
		e7.setProperty("type", "Email");
		e7.setProperty("email", "axianali@126.com");
		graph.addEdge(null, p7, e7, "own");
		
		Vertex p8 = graph.addVertex(null);
		p8.setProperty("type", "Person");
		p8.setProperty("name", "木拉提");
		Vertex e8 = graph.addVertex(null);
		e8.setProperty("type", "Email");
		e8.setProperty("email", "mulatimail@163.com");
		graph.addEdge(null, p8, e8, "own");
		
		Vertex p9 = graph.addVertex(null);
		p9.setProperty("type", "Person");
		p9.setProperty("name", "阿依加玛丽");
		Vertex e9 = graph.addVertex(null);
		e9.setProperty("type", "Email");
		e9.setProperty("email", "ayimajiamali@gmail.com");
		graph.addEdge(null, p9, e9, "own");
		
	}
	
}
