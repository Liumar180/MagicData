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
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Liubf
 *
 */
public class ImpQQgroups {
	public static void main(String[] args) {
		TitanGraph graph = TitanConnection.getTitaConnection();
		int r = 200000;//2732790091
		List<Map<String,String>> ss = null;//33600000---33800000
		for(int k=33800000; k<=40000000; k +=r){
		//if(true){
			ss = ImpQQgroups.getGrouplistDatas(k,k-r);
		
		long st = System.currentTimeMillis();
		String startTime = DateFormat.transferLongToDate(st);
		System.out.println("开始时间：" + startTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\G0217.txt", "开始时间："+startTime);
		for(int i=0;i<ss.size();i++){
			ImpQQgroups.QQgroups(ss.get(i), graph);
		}
		long et = System.currentTimeMillis();
		String endTime = DateFormat.transferLongToDate(et);
		System.out.println("结束时间：" + endTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\G0217.txt", "结束时间："+endTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\G0217.txt", "本次录入数据量："+ss.size()+"个");
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\G0217.txt", "总用时："+(et-st)/1000+"秒");
		}
		graph.shutdown();
		
		
	}

	/**
	 * @param maps
	 * @param graph
	 */
	public static void QQgroups(Map<String,String> maps,TitanGraph graph){
		String numid = maps.get("numid");//群成员QQ号
		String memberMark = maps.get("memberMark");//在群内昵称
		String domain ="QQ"; 
		String groupnum = maps.get("groupnum");//群号
		
		String ctime = maps.get("createtime");
		Long createtime = 0L;
		if(ctime != null && !ctime.equals("")){
			createtime = Long.valueOf(ctime);
		}
		
		String ownerqq = maps.get("ownerqq");
		String groupname = maps.get("groupname");
		String groupdesc = maps.get("groupdesc");
		String grouptype = "2";//群组类型
		String nickname = memberMark;//群昵称
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\G0217.txt", "数据numid="+numid);
		 boolean exception = false;
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
			Set<String> sets1 = tmp.getPropertyKeys();
			 for (String key : sets1) {
				System.out.println("key:"+key+"|value:"+tmp.getProperty(key));
			}
			Set<String> s = tmp.getPropertyKeys();
	       if(s != null){
	    	   System.out.println("存在当前qq:"+numid);
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
					if(nickname != null && !nickname.equals("")){
						sets.add(nickname);
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
					if(nickname != null && !nickname.equals("")){
						sets.add(nickname);
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
				
				
				Vertex group =null;
				try{
				Iterable<Vertex> groups = graph.query().has("groupnum",groupnum).vertices();
				
				Iterator<Vertex> gs = groups.iterator();
				if(gs.hasNext()){
					group = gs.next();
								
			if(group !=null){
				
				if(group.getProperty("groupnum").equals(groupnum) && group.getProperty("type").equals("Group")){
                    boolean isTrue = true;
					Iterator<Vertex> coh = tmp.query().labels(new String[]{"group"}).vertices().iterator();
					while(coh.hasNext()){
						Vertex GROUP = coh.next();
						if(GROUP == group){
							System.out.println("存在group边");
							isTrue = false;
						}
					}
					if(isTrue){
						System.out.println("不存在重复的边");
						Edge e = graph.addEdge(null, group, tmp, "group");
						e.setProperty("grouptype", grouptype);
						if(nickname != null && !nickname.equals("")){
							e.setProperty("nickname", nickname);
						}
					}
				}else{
					Vertex QGroup = graph.addVertex(null);
					QGroup.setProperty("type", "Group");
					QGroup.setProperty("groupnum", groupnum);
					if(ownerqq != null && !ownerqq.equals("")){
						QGroup.setProperty("ownerqq", ownerqq);
					}
					if(groupname != null && !groupname.equals("")){
						QGroup.setProperty("groupname", groupname);
					}
					if(groupdesc != null && !groupdesc.equals("")){
						QGroup.setProperty("groupdesc", groupdesc );
					}
					if(createtime != null && !createtime.equals("")){
						QGroup.setProperty("createtime", createtime );
					}
					
					Edge e = graph.addEdge(null, QGroup, tmp, "group");
					e.setProperty("grouptype", grouptype);
					if(nickname != null && !nickname.equals("")){
						e.setProperty("nickname", nickname);
					}
				}

			}else{

				Vertex QGroup = graph.addVertex(null);
				QGroup.setProperty("type", "Group");
				QGroup.setProperty("groupnum", groupnum);
				if(ownerqq != null && !ownerqq.equals("")){
					QGroup.setProperty("ownerqq", ownerqq);
				}
				if(groupname != null && !groupname.equals("")){
					QGroup.setProperty("groupname", groupname);
				}
				if(groupdesc != null && !groupdesc.equals("")){
					QGroup.setProperty("groupdesc", groupdesc );
				}
				if(createtime != null && !createtime.equals("")){
					QGroup.setProperty("createtime", createtime );
				}
				
				Edge e = graph.addEdge(null, QGroup, tmp, "group");
				e.setProperty("grouptype", grouptype);
				if(nickname != null && !nickname.equals("")){
					e.setProperty("nickname", nickname);
				}
			
			}
		}else{

			Vertex QGroup = graph.addVertex(null);
			QGroup.setProperty("type","Group");
			QGroup.setProperty("groupnum", groupnum);
			if(ownerqq != null && !ownerqq.equals("")){
				QGroup.setProperty("ownerqq", ownerqq);
			}
			if(groupname != null && !groupname.equals("")){
				QGroup.setProperty("groupname", groupname);
			}
			if(groupdesc != null && !groupdesc.equals("")){
				QGroup.setProperty("groupdesc", groupdesc );
			}
			if(createtime != null && !createtime.equals("")){
				QGroup.setProperty("createtime", createtime );
			}
			
			Edge e = graph.addEdge(null,QGroup,tmp,"group");
			e.setProperty("grouptype", grouptype);
			if(nickname != null && !nickname.equals("")){
				e.setProperty("nickname", nickname);
			}
					
				
		}
				
		
		}catch(java.util.NoSuchElementException e){
		e.printStackTrace();
		}
		
			}
		
	
		
    }
	}else{
		System.out.println("新加数据"+numid);
		Vertex Qq3 = null;
		if(numid != null && !"".equals(numid)){
			System.out.println("添加qq对象");
			Vertex personNn = graph.addVertex(null);
			personNn.setProperty("type", "Person");
			
			
			Qq3 = graph.addVertex(null);//QQ事件
			Qq3.setProperty("type","IM");
			if(domain != null && !"".equals(domain)){
				Qq3.setProperty("domain",domain);
			}
			if(numid!=null && !"".equals(numid)){
				Qq3.setProperty("numid",numid);
			}
			if(nickname != null && !nickname.equals("")){
				String[] mark=new String[1];
				mark[0] = nickname;
				Qq3.setProperty("nickname", mark);
			}
			
			graph.addEdge(null, personNn, Qq3, "own");
			
		}
		System.out.println("groupnum: "+groupnum);
		if(groupnum != null && !groupnum.equals("")){
		System.out.println("************");
		

		Vertex group =null;
		try{
		@SuppressWarnings("unchecked")
		Iterable<Vertex> groups = graph.query().has("groupnum",groupnum).vertices();
		Iterator<Vertex> gs1 = groups.iterator();
		if(gs1.hasNext()){
			group = gs1.next();
			
			if(group !=null){
				
				if(group.getProperty("groupnum").equals(groupnum) && group.getProperty("type").equals("Group")){

					if(Qq3 != null){
					Edge e = graph.addEdge(null, group, Qq3, "group");
					e.setProperty("grouptype", grouptype);
					if(nickname != null && !nickname.equals("")){
						e.setProperty("nickname", nickname);
					}
					}
				}else{
					Vertex QGroup = graph.addVertex(null);
					QGroup.setProperty("type", "Group");
					QGroup.setProperty("groupnum", groupnum);
					if(ownerqq != null && !ownerqq.equals("")){
						QGroup.setProperty("ownerqq", ownerqq);
					}
					if(groupname != null && !groupname.equals("")){
						QGroup.setProperty("groupname", groupname);
					}
					if(groupdesc != null && !groupdesc.equals("")){
						QGroup.setProperty("groupdesc", groupdesc );
					}
					if(createtime != null && !createtime.equals("")){
						QGroup.setProperty("createtime", createtime );
					}
					if(Qq3 != null){
						Edge e = graph.addEdge(null, QGroup, Qq3, "group");
						e.setProperty("grouptype", grouptype);
						if(nickname != null && !nickname.equals("")){
							e.setProperty("nickname", nickname);
						}
					}
				}

			}else{

				Vertex QGroup = graph.addVertex(null);
				QGroup.setProperty("type", "Group");
				QGroup.setProperty("groupnum", groupnum);
				if(ownerqq != null && !ownerqq.equals("")){
					QGroup.setProperty("ownerqq", ownerqq);
				}
				if(groupname != null && !groupname.equals("")){
					QGroup.setProperty("groupname", groupname);
				}
				if(groupdesc != null && !groupdesc.equals("")){
					QGroup.setProperty("groupdesc", groupdesc );
				}
				if(createtime != null && !createtime.equals("")){
					QGroup.setProperty("createtime", createtime );
				}
				if(Qq3 != null){
					Edge e = graph.addEdge(null, QGroup, Qq3, "group");
					e.setProperty("grouptype", grouptype);
					if(nickname != null && !nickname.equals("")){
						e.setProperty("nickname", nickname);
					}
					
				}
			
			}
		}else{
System.out.println("添加新的群组。。");
				Vertex QGroup = graph.addVertex(null);
				QGroup.setProperty("type","Group");
				
				QGroup.setProperty("groupnum", groupnum);
				if(ownerqq != null && !ownerqq.equals("")){
					QGroup.setProperty("ownerqq", ownerqq);
				}
				if(groupname != null && !groupname.equals("")){
					QGroup.setProperty("groupname", groupname);
				}
				if(groupdesc != null && !groupdesc.equals("")){
					QGroup.setProperty("groupdesc", groupdesc );
				}
				if(createtime != null && !createtime.equals("")){
					QGroup.setProperty("createtime", createtime );
				}
				if(Qq3 != null){
					Edge e = graph.addEdge(null,QGroup,Qq3,"group");
					e.setProperty("grouptype", grouptype);
					if(nickname != null && !nickname.equals("")){
						e.setProperty("nickname", nickname);
					}
					
				}
				
			
			}
			
			}catch(java.util.NoSuchElementException e){
			e.printStackTrace();
			}
		}
		}
         graph.commit();

	}
	
	/**
	 * @param counts数据总数
	 * @param rows 查询开始行数
	 * @return
	 */
	public static List<Map<String,String>> getGrouplistDatas(Integer counts,Integer rows) {
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\G0217.txt", "查询总记录数："+counts+"开始查询行数："+rows+"区间-》"+rows+"---"+counts);
		
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

    String sql = "SELECT c.* FROM ( SELECT s.GROUPNUM,s.GROUP_NAME,s.GROUP_DESC,s.MEMBERNUM,s.MEMBERREMARK,s.OWNER_QQ,s.CREATE_TIME, ROWNUM RN "
				+" FROM (SELECT  ROWNUM,g.GROUPNUM,g.MEMBERNUM,g.MEMBERREMARK,v.CREATE_TIME,"
				+" v.GROUP_NAME,v.OWNER_QQ,v.GROUP_DESC from TB_RNS_QQ_GROUPLIST g "
				+" LEFT JOIN TB_RNS_QQ_GROUP_DESC v on g.GROUPNUM = v.GROUPNUM"
				+" WHERE ROWNUM <= "+counts+") s) c WHERE RN >"+rows;
    try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();

				while(rs.next()){
					Map<String, String> maps = new HashMap<String, String>();
					maps.put("numid", rs.getString("MEMBERNUM"));
					maps.put("memberMark", rs.getString("MEMBERREMARK"));
					maps.put("groupnum", rs.getString("GROUPNUM"));
					maps.put("createtime", rs.getString("CREATE_TIME"));
					maps.put("ownerqq", rs.getString("OWNER_QQ"));
					maps.put("groupname", rs.getString("GROUP_NAME"));
					maps.put("groupdesc", rs.getString("GROUP_DESC"));
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

