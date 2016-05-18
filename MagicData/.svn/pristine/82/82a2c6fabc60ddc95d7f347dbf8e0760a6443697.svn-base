package com.integrity.dataSmart.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class modifyGroupNick {
	public static void main(String[] args) {
		TitanGraph graph = TitanConnection.getTitaConnection();
		int r = 200000;
		List<Map<String,String>> ss = null;//9747675
		for(int k = 10000000; k <= 10400000; k += r){
		//if(true){
			ss = modifyGroupNick.getQlistDatas(k, k-r);
		
		long st = System.currentTimeMillis();
		String startTime = DateFormat.transferLongToDate(st);
		System.out.println("开始时间：" + startTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\modifyGroupNick1127.txt", "开始时间："+startTime);
		for(int i=0;i<ss.size();i++){
			modifyGroupNick.QQList(ss.get(i), graph);
		}
		long et = System.currentTimeMillis();
		String endTime = DateFormat.transferLongToDate(et);
		System.out.println("结束时间：" + endTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\modifyGroupNick1127.txt", "结束时间："+endTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\modifyGroupNick1127.txt", "本次录入数据量："+ss.size()+"个");
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\modifyGroupNick1127.txt", "总用时："+(et-st)/1000+"秒");
		}
		graph.shutdown();
	}

	/**
	 * @param numid
	 * @param friendMark
	 * @param graph
	 * 添加qq备注信息
	 */
	@SuppressWarnings("null")
	public static void QQList(Map<String,String> maps,TitanGraph graph){
		String numid=maps.get("numid");
		String memberremark=maps.get("memberremark");
		System.out.println(numid +"---"+ memberremark);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\modifyGroupNick1127.txt", "QQ号="+numid);
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
				if(memberremark != null && !memberremark.equals("")){
					sets.add(memberremark);
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
				if(memberremark != null && !memberremark.equals("")){
					sets.add(memberremark);
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
	}/*else{
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
		
		
	}*/
			graph.commit();

	}
	

	/**
	 * @param counts数据总数
	 * @param rows 查询开始行数
	 * @return
	 */
	public static List<Map<String,String>> getQlistDatas(Integer counts,Integer rows) {
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\modifyGroupNick1127.txt", "查询总记录数："+counts+"开始查询行数："+rows+"区间-》"+rows+"---"+counts);
		
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

    String sql = "SELECT c.NUMID,c.MEMBERREMARK  FROM ( SELECT s.NUMID,s.MEMBERREMARK, ROWNUM RN "
    		+"FROM (SELECT n.MEMBERNUM numid,n.MEMBERREMARK memberremark from TB_RNS_QQ_GROUPLIST n"
     +" where ROWNUM <="+counts+") s) c WHERE RN >"+rows;
    
    try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					Map<String, String> maps = new HashMap<String, String>();
					maps.put("numid", rs.getString("NUMID"));
					maps.put("memberremark", rs.getString("MEMBERREMARK"));
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
