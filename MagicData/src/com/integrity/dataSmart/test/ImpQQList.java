package com.integrity.dataSmart.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrity.dataSmart.util.DateFormat;
import com.integrity.dataSmart.util.WritelogContents;
import com.integrity.dataSmart.util.importData.OracleUtils;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Liubf
 *
 */
public class ImpQQList {

	public static void main(String[] args) {
		TitanGraph graph = TitanConnection.getTitaConnection();
		int r = 200000;
		List<Map<String,String>> ss = null;//115000000
		for(int k = 115200000; k <= 200000000; k += r){
		//if(true){
			ss = ImpQQList.getQlistDatas(115000000,114833401);
		
		long st = System.currentTimeMillis();
		String startTime = DateFormat.transferLongToDate(st);
		System.out.println("开始时间：" + startTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqList0217.txt", "开始时间："+startTime);
		for(int i=0;i<ss.size();i++){
			ImpQQList.QQList(ss.get(i), graph);
		}
		long et = System.currentTimeMillis();
		String endTime = DateFormat.transferLongToDate(et);
		System.out.println("结束时间：" + endTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqList0217.txt", "结束时间："+endTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqList0217.txt", "本次录入数据量："+ss.size()+"个");
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqList0217.txt", "总用时："+(et-st)/1000+"秒");
		}
		graph.shutdown();
	}

	/**
	 * @param numid
	 * @param friendMark
	 * @param graph
	 * 添加qq备注信息
	 */
	public static void QQList(Map<String,String> maps,TitanGraph graph){
		String numid=maps.get("numid");
		String friendMark=maps.get("friendMark");
		
		System.out.println(numid +"----"+ friendMark);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqList0217.txt", "QQ号="+numid);
		boolean exception = false;	
		Object QqIte = null;
			if(numid != null && !"".equals(numid)){
				QqIte = graph.query().has("numid",numid).vertices();//通过qq号查询
			}
					
			Vertex tmp = null;
			boolean t =  false;
			if(QqIte != null){
				t = ((Iterable) QqIte).iterator().hasNext();
			}
			if(t){
				tmp = (Vertex)((Iterable) QqIte).iterator().next();
				Set<String> s = tmp.getPropertyKeys();
				//String name = Arrays.toString((String[]) tmp.getProperty("nickname"));
			 // WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qList.txt", "QQ昵称="+name);
		if(s != null){
			boolean is = false;
			if(tmp.getProperty("type").toString().equals("IM")){
				is = true;
			}
				if(is){
					String[] arrays = null;
					try {
						arrays = tmp.getProperty("nickname");
					} catch (Exception e) {
						exception = true;
						System.out.println("nickname非数组对象");
						String nickN = tmp.getProperty("nickname");
						if(nickN != null && !nickN.equals("")){
							String[] mark=new String[1];
							mark[0] = nickN;
							tmp.setProperty("nickname", mark);
						}
					}
					if(exception){
						String[] arrays1 = tmp.getProperty("nickname");
						Set<String>  sets = new HashSet<String>();
						if(arrays1 != null){
							for(int j=0;j<arrays1.length; j++){
								sets.add(arrays1[j]);
							}
						}
						if(friendMark != null && !friendMark.equals("")){
							sets.add(friendMark);
						}
						List<String> fin1 = new ArrayList<String>();
						if(sets.size() != 0){
						    for(Iterator<String> iterator = sets.iterator();iterator.hasNext();){  
							       fin1.add(iterator.next());
							   }  
						}
						String[] setArray1 = (String[])fin1.toArray(new String[fin1.size()]);
						if(setArray1.length != 0){
							tmp.setProperty("nickname",setArray1);
						}
					}else{
						Set<String>  sets = new HashSet<String>();
						if(arrays != null){
							for(int j=0;j<arrays.length; j++){
								sets.add(arrays[j]);
							}
						}
						if(friendMark != null && !friendMark.equals("")){
							sets.add(friendMark);
						}
						List<String> fin = new ArrayList<String>();
						if(sets.size() != 0){
						    for(Iterator<String> iterator = sets.iterator();iterator.hasNext();){  
							       fin.add(iterator.next());
							   }  
						}
						String[] setArray = (String[])fin.toArray(new String[fin.size()]);
						if(setArray.length != 0){
							tmp.setProperty("nickname",setArray);
						}
					}
					
				
				}
		    }
	}else{
		Vertex persons = graph.addVertex(null);
		persons.setProperty("type", "Person");
		
		Vertex qq = graph.addVertex(null);
		qq.setProperty("type", "IM");
		qq.setProperty("domain", "QQ");
		qq.setProperty("numid", numid);
		if(friendMark != null && !friendMark.equals("")){
			String[] mark=new String[1];
			mark[0] = friendMark;
			qq.setProperty("nickname", mark);
		}
		
		graph.addEdge(null, persons, qq, "own");
		
		
	}
			graph.commit();

	}
	

	/**
	 * @param counts数据总数
	 * @param rows 查询开始行数
	 * @return
	 */
	public static List<Map<String,String>> getQlistDatas(Integer counts,Integer rows) {
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\qqList0217.txt", "查询总记录数："+counts+"开始查询行数："+rows+"区间-》"+rows+"---"+counts);
		
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

    String sql = "SELECT c.QQNUM,c.FRIENDREMARK  FROM ( SELECT s.QQNUM,s.FRIENDREMARK, ROWNUM RN "
     +" FROM (SELECT n.QQNUM qqnum,n.FRIENDREMARK friendremark from TB_RNS_QQ_NAME n where ROWNUM <= "+counts+") s) c WHERE RN >"+rows;
    try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					Map<String, String> maps = new HashMap<String, String>();
					maps.put("numid", rs.getString("QQNUM"));
					maps.put("friendMark", rs.getString("FRIENDREMARK"));
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

