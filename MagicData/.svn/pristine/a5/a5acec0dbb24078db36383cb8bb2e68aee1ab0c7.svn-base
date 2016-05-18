package com.integrity.dataSmart.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.BaseConfiguration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * 慎用！！！
 * 审查好，再执行。
 * 根据属性删除人物节点（包括人物自身属性，不包括事件）
 *
 */
public class RemoveVertexAndEdge {

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

		System.out.println("+++++++++++++++++++");
		System.out.println("开始");

//		String pro = "email";
//		String[] arr = new String[]{"Kathleen Hatfield","Tonie Keene",
//				"Valerie Castaneda","Darla Dunn","Gail Fritz",
//				"Evelyn Baskervill","Anna Howell","Keasha Daniels"};
//		String[] arr = new String[]{"kkimalexander@aol.com"};
//		removeVertexByOwn(graph, pro, arr);
		
		removeVertex(graph, "name", new String[]{"刘宝芬","周欢"});
		graph.commit();
		System.out.println("完成");
		graph.shutdown();

	}
	
	
	/**
	 * 根据人物节点自身属性删除
	 * @param graph
	 * @param pro 人物节点自身属性
	 * @param values 值数组
	 */
	public static void removeVertex(TitanGraph graph,String pro,String[] values){
		List<Vertex> vList = new ArrayList<Vertex>();
		List<Edge> eList = new ArrayList<Edge>();
		for (String value : values) {
			Iterable<Vertex> it = graph.getVertices(pro, value);
			for (Vertex vertex : it) {
				Iterable<Vertex> it2 = vertex.query().labels("own").vertices();
				for (Vertex vertex2 : it2) {
					System.out.println(vertex2);
					if (!vList.contains(vertex2))
						vList.add(vertex2);
				}
				Iterable<Edge> iterable = vertex.query().labels("own").edges();
				for (Edge edge : iterable) {
					System.out.println(edge);
					if (eList.contains(edge))
						eList.add(edge);
				}
				System.out.println(vertex);
				if (!vList.contains(vertex))
					vList.add(vertex);
			}
		}
		for (int i = vList.size() - 1; i > -1; i--) {
			graph.removeVertex(vList.get(i));
		}
		for (int j = eList.size() - 1; j > -1; j--) {
			graph.removeEdge(eList.get(j));
		}
		
		graph.commit();
	}

	/**
	 * 根据own属性删除
	 * @param graph
	 * @param pro own节点属性名
	 * @param values 值数组
	 */
	public static void removeVertexByOwn(TitanGraph graph, String pro,String[] values) {
		List<Vertex> vList = new ArrayList<Vertex>();
		List<Edge> eList = new ArrayList<Edge>();
		for (String value : values) {
			Iterable<Vertex> it = graph.getVertices(pro, value);
			for (Vertex vertex : it) {
				System.out.println("查询到"+pro+"节点:"+vertex);
				Iterable<Vertex> it2 = vertex.query().labels("own").vertices();
				for (Vertex personV : it2) {
					boolean flag = false;
					if (vList.contains(personV)) {
						flag = true;
						break;
					}
					if (flag)
						continue;
					
					Iterable<Vertex> relIterable = personV.query().labels("own").vertices();
					for (Vertex vertex2 : relIterable) {
						System.out.println(vertex2);
						if (!vList.contains(vertex2))
							vList.add(vertex2);
					}

					Iterable<Edge> iterable = personV.query().labels("own").edges();
					for (Edge edge : iterable) {
						System.out.println(edge);
						if (eList.contains(edge))
							eList.add(edge);
					}
					System.out.println(personV);
					if (!vList.contains(personV))
						vList.add(personV);
				}
			}
		}
		for (int i = vList.size() - 1; i > -1; i--) {
			graph.removeVertex(vList.get(i));
		}
		for (int j = eList.size() - 1; j > -1; j--) {
			graph.removeEdge(eList.get(j));
		}
		
		graph.commit();
	}
}
