package com.integrity.dataSmart.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.integrity.dataSmart.util.WritelogContents;
import com.integrity.dataSmart.util.importData.OracleUtils;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class deleteRepeat {
	public static void main(String[] args) {
		List<String> ss = deleteRepeat.getDatas(0, 500000);
		TitanGraph graph = TitanConnection.getTitaConnection();
		System.out.println("size:"+ss.size());
		for(int i=0;i<ss.size();i++){
			deleteRepeat.deleteDatas(ss.get(i), graph);
		}
		graph.shutdown();
	}
	/**
	 * @param sRow
	 * @param eRow
	 */
	public static List<String> getDatas(Integer sRow,Integer eRow) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<String> ls = new ArrayList<String>();
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

		String sql = "SELECT DISTINCT(s.QQNUM) numid FROM TB_RNS_QQ_FRIENDLIST s  where ROWNUM <="+eRow; 
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ls.add(rs.getString("numid"));
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
	
	public static void deleteDatas(String numid,TitanGraph graph){

		 /******************/
		//关联节点和边删除
		 WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\deletes.txt", "删除数据numid="+numid);
		  List<Vertex> vList = new ArrayList<Vertex>();
		  List<Edge> eList = new ArrayList<Edge>();
		 Vertex datas = graph.getVertices("numid",numid).iterator().next();
		 
		 if(datas.getProperty("type").equals("IM")){
		 Iterator<Vertex> ite = datas.query().labels("relational").vertices().iterator();
		 while (ite.hasNext()) {
		 Vertex vv = ite.next();
		 if(!vList.contains(vv)) vList.add(vv);
		//  vv.remove();
		//  graph.removeVertex(vv);
		}
		 Iterator<Edge> it3 = datas.query().labels("relational").edges().iterator();
		 while (it3.hasNext()) {
		 Edge edge = it3.next();
		if(!eList.contains(edge))eList.add(edge);
		}
		//  lilan.remove();
		 if(!vList.contains(datas)) vList.add(datas);
	}
		 for (int i = vList.size()-1; i >-1 ; i--) {
		graph.removeVertex(vList.get(i));
		}
		 for (int j = eList.size()-1; j >-1; j--) {
		graph.removeEdge(eList.get(j));
		}
		graph.commit();

	}
	

}
