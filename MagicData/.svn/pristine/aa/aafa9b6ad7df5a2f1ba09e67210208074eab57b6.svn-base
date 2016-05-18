package com.integrity.dataSmart.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrity.dataSmart.util.DateFormat;
import com.integrity.dataSmart.util.WritelogContents;
import com.integrity.dataSmart.util.importData.OracleUtils;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class modifyQQlist {

	public static void main(String[] args) {
		TitanGraph graph = TitanConnection.getTitaConnection();
		int r = 400000;
		List<Map<String,String>> ss = null;
		//for(int k=400000; k<=9200000; k +=r){
		if(true){
			ss = modifyQQlist.getQQlistDatas(95704, 0);
		
		long st = System.currentTimeMillis();
		String startTime = DateFormat.transferLongToDate(st);
		System.out.println("开始时间：" + startTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqdedSHANCHU.txt", "开始时间："+startTime);
		for(int i=0;i<ss.size();i++){
			modifyQQlist.deleQqsP(ss.get(i), graph);
		}
		long et = System.currentTimeMillis();
		String endTime = DateFormat.transferLongToDate(et);
		System.out.println("结束时间：" + endTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqdedSHANCHU.txt", "结束时间："+endTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqdedSHANCHU.txt", "本次删除数据量："+ss.size()+"个");
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqdedSHANCHU.txt", "总用时："+(et-st)/1000+"秒");
		}
		graph.shutdown();
		
		
	}

	/**
	 * @param maps
	 * @param graph
	 */
	public static void QQList(Map<String,String> maps,TitanGraph graph){
		String numid =maps.get("numid");//QQ号
		
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqded.txt", "数据numid="+numid);
		 
		 Object QqIte = null;
			if(numid != null && !"".equals(numid)){
				QqIte = graph.query().has("numid",numid).vertices();
				}
						Vertex tmp = null;
						boolean t =  false;
						if(QqIte != null){
							t = ((Iterable) QqIte).iterator().hasNext();
				}
				if(t){
					tmp = (Vertex)((Iterable)QqIte).iterator().next();
					Set<String> s = tmp.getPropertyKeys();
	       if(s != null){
	    	  boolean iis =  tmp.getProperty("type").equals("IM");
	    	  if(iis){
	    		  Vertex cperson = null;
	    		  try {
	    			  cperson = (Vertex)tmp.query().labels(new String[]{"own"}).vertices().iterator().next();
				} catch (Exception e) {
					System.out.println("没有人物对象");
					WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqdedSHANCHU.txt", "没有人物对象"+numid);
					Vertex ppp = graph.addVertex(null);
					ppp.setProperty("type", "Person");
					
					graph.addEdge(null, ppp, tmp, "own");
				}
	    	      if(cperson != null){
	    	    	  Set<String> sets1 = tmp.getPropertyKeys();
						 for (String key : sets1) {
							System.out.println("key:"+key+"|value:"+tmp.getProperty(key));
						}
	    	      }
	    	  }
	    	  
	       }
				}
		         graph.commit();

	}
	
	/**
	 * @param maps
	 * @param graph
	 * 删除添加重复的人
	 */
	public static void deleQqsP(Map<String,String> maps,TitanGraph graph){
		 String numid = maps.get("numid");
		 WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqdedSHANCHU.txt", "数据numid="+numid);
		 Iterable<Vertex> G1 =  graph.getVertices("numid",numid);
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
			 System.out.println("---------------->"+number);
			 if(number > 1 & number <= 2){
				 Iterator<Vertex> ops1=  karry.query().labels(new String[]{"own"}).vertices().iterator();
				 int k = 0;
				 while(ops1.hasNext()){
					 
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
			 }else if(number >2){

				 Iterator<Vertex> ops1=  karry.query().labels(new String[]{"own"}).vertices().iterator();
				 int k = 0;
				 while(ops1.hasNext()){
					 
					Vertex s1 = ops1.next();
					String nickname = s1.getProperty("nickname");
					if(nickname == null){
						k = k+1;
                     if(k<=2){
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
		
	}
	
	/**
	 * @param counts数据总数
	 * @param rows 查询开始行数
	 * @return
	 */
	public static List<Map<String,String>> getQQlistDatas(Integer counts,Integer rows) {
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqdedSHANCHU.txt", "查询总记录数："+counts+"开始查询行数："+rows+"区间-》"+rows+"---"+counts);
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<Map<String,String>> ls = new ArrayList<Map<String,String>>();
		try {
			conn = OracleUtils.getOrclConnection();
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

    String sql = "SELECT c.QQNUM  FROM ( SELECT s.QQNUM, ROWNUM RN "
     +" FROM (SELECT DISTINCT(n.QQNUM) qqnum from TB_RNS_QQ_NAME n where ROWNUM <= "+counts+") s) c WHERE RN >"+rows;
    try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					Map<String, String> maps = new HashMap<String, String>();
					maps.put("numid", rs.getString("QQNUM"));
					ls.add(maps);
				}
				conn.commit();
			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally{
				OracleUtils.free(rs, ps, conn);
			}
			return ls;

}







}
