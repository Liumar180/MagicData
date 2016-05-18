package com.integrity.dataSmart.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class modifyQQnickname {
	public static void main(String[] args) {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		 System.out.println("开始连接数据库...");
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 System.out.println("数据库已连接");

			String numid="1151694121";
			String friendMark="蓝斯、";
			boolean exception = false;	
			Object QqIte = null;
				if(numid != null && !"".equals(numid)){
					QqIte = graph.query().has("numid",numid).vertices();//通过qq号查询
				}
						
				Vertex tmp = null;
			while(((Iterable) QqIte).iterator().hasNext()){
					tmp = (Vertex)((Iterable) QqIte).iterator().next();
					System.out.println("---->"+tmp);
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
					graph.commit();
			    }
		}

		
		 graph.shutdown();
		 }
		 
	}


