package com.integrity.dataSmart.test;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;

import com.integrity.dataSmart.util.DateFormat;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class testPhone {
	//AtomicLong count=new AtomicLong();
	public static void main(String[] args) {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 System.out.println("Open Data");
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 System.out.println("数据库已连接");
		//count.addAndGet(1);
		//Object[] r = getRow();

		//if (r == null) {

			//setOutputDone();

			//return false;
//}


		//r= createOutputRow(r, data.outputRowMeta.size()); 
		 String ab = "2015-05-28 22:07:00";
		 Long startT= DateFormat.convert2long(ab,"yyyy-MM-dd hh:mm:ss");
		long st = System.currentTimeMillis();
		String startTime= DateFormat.transferLongToDate(st);
		System.out.println("开始时间："+startTime);
		
			    String model = "中国电信";
				try {
				//model = get(Fields.In, "model").getString(r);
				} catch (Exception e) {}
			    String phonenum="90320045";
				try {
				//phonenum = get(Fields.In, "phonenum").getString(r);
				} catch (Exception e) {}
			    String from = "90320045";
				try {
				//from = get(Fields.In, "from").getString(r);
				} catch (Exception e) {}
			    String to = "85292395799";//18901230987
				try {
				//to = get(Fields.In, "to").getString(r);
				} catch (Exception e) {}
				String name = "MR CHOW WAI PUI STEEVE";
				try {
				//name = get(Fields.In, "name").getString(r);
				} catch (Exception e) {}
			    String idcard = "22012d274f0962391";
				try {
				//idcard = get(Fields.In, "idcard").getString(r);
				} catch (Exception e) {}
				String country = "Chinas";
				try {
				//country = get(Fields.In, "country").getString(r);
				} catch (Exception e) {}
		        String tname = "";
		        String tidcard = "";
		        String tcountry = "";
		        //发送时间
		        System.out.println("------>"+startT);
		        Long time = startT;
				try {
				//String t = get(Fields.In, "time").getString(r);
		//time = Long.valueOf("t");
				} catch (Exception e) {}
		      	Long eventtime = startT;//事件时间
				try {
				//String e = get(Fields.In, "eventtime").getString(r);
		//eventtime = Long.valueOf("e");
				} catch (Exception e) {}
		      	Long l = 13432L;
				try {
				//String s = get(Fields.In, "long").getString(r);
		//l = Long.valueOf("s");
				} catch (Exception e) {}
				
					Object PhoneIte = null;
							if(phonenum != null && !"".equals(phonenum)){
								PhoneIte =graph.query().has("phonenum",phonenum).vertices();
							}
							Vertex tmp = null;
							boolean t =  false;
							if(PhoneIte != null){t = ((Iterable) PhoneIte).iterator().hasNext();}
					 while(t){
						tmp = (Vertex)((Iterable)PhoneIte).iterator().next();
						Set s = tmp.getPropertyKeys();
		           if(s != null){
						for(int i=0;i<s.size();i++){
							if(tmp.getProperty("type").toString().equals("Phone")){
								break;
							}
						}
		            }
						break;

					}
					
					if(tmp != null){
						Vertex person =null;
						try{
						
						person = (Vertex)tmp.query().labels(new String[]{"own"}).vertices().iterator().next();
						}catch(java.util.NoSuchElementException e){
						e.printStackTrace(System.out);
						//putRow(data.outputRowMeta, r);

						//return true;
						}
						if(person !=null){
							if(name != null && !name.equals("")){
								person.setProperty("name",name);
								}
								if(idcard != null && !idcard.equals("")){
								person.setProperty("idcard",idcard);
								}
								if(country != null && !country.equals("")){
								person.setProperty("country",country);
								}
								
							Object childs = person.query().labels(new String[]{"own"}).vertices();

							boolean phoneEvexist = false;
							for (Iterator iterator = ((Iterable)childs).iterator(); iterator.hasNext();) {
								Vertex v = (Vertex) iterator.next();
								
								if(v != null){
								if("Phone".equals(v.getProperty("type"))){
		                        if(v.getProperty("phonenum").equals(phonenum)){
		                     
		                        	phoneEvexist = true;
									}
								}
		            
							}
						if(!phoneEvexist){
				
							Vertex phoneEveve = null;
								if(phonenum!=null && !"".equals(phonenum)){
									Vertex phonetmp = graph.addVertex(null);
									phonetmp.setProperty("type","Phone");
									phonetmp.setProperty("phonenum",phonenum);
									if(model != null && !"".equals(model)){
										phonetmp.setProperty("model", model);
		                            }
									graph.addEdge(null,person,phonetmp,"own");
								}	
								//电话事件处理
								
								if(to != null && !to.equals("")){

									boolean fromto = false;
									Iterable personto = graph.query().has("phonenum",to).vertices();
									if(personto.iterator().hasNext()){
									Vertex pto = (Vertex) personto.iterator().next();
										
									for (Iterator iterator1 = ((Iterable)personto).iterator(); iterator1.hasNext();) {
										Vertex v1 = (Vertex) iterator1.next();
										if(v1 != null){
										if("Phone".equals(v1.getProperty("type"))){
				                        if(v1.getProperty("phonenum").equals(to)){
				                        	fromto = true;
											}
										}
				                  }
										Object perto = null;
									if(fromto){
										perto = pto.query().labels(new String[]{"own"}).vertices().iterator().next();
										if(perto != null){
											if(phoneEveve == null){
												phoneEveve = graph.addVertex(null);
												 phoneEveve.setProperty("type", "CallEvent");
												 phoneEveve.setProperty("from", from);
												 phoneEveve.setProperty("to", to);
												 phoneEveve.setProperty("time", time);
												 phoneEveve.setProperty("long", l);
												graph.addEdge(null, person, phoneEveve, "callfrom").setProperty("eventtime", eventtime);
												graph.addEdge(null, phoneEveve, (Vertex) perto, "callto").setProperty("eventtime", eventtime);
											}else{
												graph.addEdge(null, phoneEveve, (Vertex) perto, "callto").setProperty("eventtime", eventtime);
											}
										
									} 
									}else{
											  Vertex p = graph.addVertex(null);
											  p.setProperty("type", "Person");
											  
											  
											  Vertex em  = graph.addVertex(null);
											  em.setProperty("type", "Phone");
											  em.setProperty("phonenum", to);
											  graph.addEdge(null, p, em, "own");
											  if(phoneEveve == null){
												  phoneEveve = graph.addVertex(null);
												  phoneEveve.setProperty("type", "CallEvent");
													 phoneEveve.setProperty("from", from);
													 phoneEveve.setProperty("to", to);
													 phoneEveve.setProperty("time", time);
													 phoneEveve.setProperty("long", l);
													graph.addEdge(null, person, phoneEveve, "callfrom").setProperty("eventtime", eventtime);
													graph.addEdge(null, phoneEveve, p, "callto").setProperty("eventtime", eventtime);
											  }else{
													graph.addEdge(null, phoneEveve, p, "callto").setProperty("eventtime", eventtime);
											  }
												 
											  
										  }
										}
									}
									
								}
							}else{
								Vertex phoneEveve = null;
								if(to != null && !to.equals("")){

									Vertex pto = null;	
									Iterable personto = graph.query().has("phonenum",to).vertices();
									
									boolean eventtoExist = false;
									if(personto.iterator().hasNext()){
									pto = (Vertex) personto.iterator().next();
									
									
									
									for (Iterator iterator1 = personto.iterator(); iterator1.hasNext();) {
										Vertex v1 = (Vertex) iterator1.next();
										
			                            if(to != null){
			                               if(to.equals(v1.getProperty("phonenum"))){
			                            	   eventtoExist=true;
										               }
			                                      }
										

									  }
									}
									Object perto = null;
									if(eventtoExist){
									if(to!=null && !"".equals(to)){
										perto = pto.query().labels(new String[]{"own"}).vertices().iterator().next();
										if(perto != null){
											if(phoneEveve == null){
												phoneEveve = graph.addVertex(null);
												phoneEveve.setProperty("type","CallEvent");
												 phoneEveve.setProperty("from", from);
												 phoneEveve.setProperty("to", to);
												 phoneEveve.setProperty("time", time);
												 phoneEveve.setProperty("long", l);
												graph.addEdge(null, person, phoneEveve, "callfrom").setProperty("eventtime", eventtime);
												graph.addEdge(null, phoneEveve, (Vertex) perto, "callto").setProperty("eventtime", eventtime);
											}else{
												graph.addEdge(null, phoneEveve, (Vertex) perto, "callto").setProperty("eventtime", eventtime);
											}
										
										}
										}
									}else{
										Vertex p = graph.addVertex(null);
										p.setProperty("type", "Person");
										if(name != null && !name.equals("")){
											person.setProperty("name",tname);
											}
											if(idcard != null && !idcard.equals("")){
											person.setProperty("idcard",tidcard);
											}
											if(country != null && !country.equals("")){
											person.setProperty("country",tcountry);
											}
											
								if(to != null && !to.equals("")){
										Vertex em  = graph.addVertex(null);
										  em.setProperty("type", "Phone");
										  em.setProperty("phonenum", to);
										  graph.addEdge(null, p, em, "own");
										  }
										  
										if(to!=null && !"".equals(to)){
											if(phoneEveve == null){
												phoneEveve = graph.addVertex(null);
												phoneEveve.setProperty("type","CallEvent");
												 phoneEveve.setProperty("from", from);
												 phoneEveve.setProperty("to", to);
												 phoneEveve.setProperty("time", time);
												 phoneEveve.setProperty("long", l);
												graph.addEdge(null, person, phoneEveve, "callfrom").setProperty("eventtime", eventtime);
												graph.addEdge(null, phoneEveve, p, "callto").setProperty("eventtime", eventtime);
											}else{
												graph.addEdge(null, phoneEveve, p, "callto").setProperty("eventtime", eventtime);
											}
											
											}
										
									}
									
									 
									
								}
							
							}
								
							}
						}
						
					}else{
						System.out.println("--------新增数据--------");
						Vertex phoneEveve = null;//全局邮件时间对象
						Vertex personNew = graph.addVertex(null);//人物对象
						personNew.setProperty("type","Person");
						if(name != null && !name.equals("")){
							personNew.setProperty("name",name);
							}
							if(idcard != null && !idcard.equals("")){
								personNew.setProperty("idcard",idcard);
							}
							if(country != null && !country.equals("")){
								personNew.setProperty("country",country);
							}
						if(phonenum != null && !"".equals(phonenum)){
						Vertex phonetmp = graph.addVertex(null);
						phonetmp.setProperty("type","Phone");
		               
		                phonetmp.setProperty("phonenum",phonenum);
		                
		                if(model != null && !"".equals(model)){
		                  phonetmp.setProperty("model",model);
		                }

						graph.addEdge(null,personNew,phonetmp,"own");
		              }
						if(to != null && !to.equals("")){

							Iterable personto = graph.query().has("phonenum",to).vertices();
							
							Vertex tmpto = null;
							boolean tto =  false;
								if(personto != null){tto = personto.iterator().hasNext();}
						 while(tto){
							 tmpto = (Vertex) personto.iterator().next();
							Set s = tmpto.getPropertyKeys();
			           if(s != null){
							for(int j=0;j<s.size();j++){
								if(tmpto.getProperty("type").toString().equals("Phone")){
									break;
								}
							}
			            }
							break;

						}
						 Vertex per = null;
						 if(tmpto != null){
							  per = (Vertex)tmpto.query().labels(new String[]{"own"}).vertices().iterator().next();
							  
								if(per != null){
								if(phoneEveve == null){
									phoneEveve = graph.addVertex(null);
									 phoneEveve.setProperty("type", "CallEvent");
									 phoneEveve.setProperty("from", from);
									 phoneEveve.setProperty("to", to);
									 phoneEveve.setProperty("time", time);
									 phoneEveve.setProperty("long", l);
									graph.addEdge(null, personNew, phoneEveve, "callfrom").setProperty("eventtime", eventtime);
									graph.addEdge(null, phoneEveve, per, "callto").setProperty("eventtime", eventtime);
									
								}else{
									graph.addEdge(null, phoneEveve, per, "callto").setProperty("eventtime", eventtime);
								}
								 
								}
								}else{
									  Vertex p = graph.addVertex(null);
									  p.setProperty("type", "Person");
									  //被叫人 信息
									  if(tname != null && !tname.equals("")){
											p.setProperty("name",tname);
											}
											if(tidcard != null && !tidcard.equals("")){
											p.setProperty("idcard",tidcard);
											}
											if(tcountry != null && !tcountry.equals("")){
											p.setProperty("country",tcountry);
											}
									  Vertex em  = graph.addVertex(null);
									  em.setProperty("type", "Phone");
									  em.setProperty("phonenum", to);
									  graph.addEdge(null, p, em, "own");
									  if(phoneEveve == null){
										  phoneEveve = graph.addVertex(null);
										  phoneEveve.setProperty("type", "CallEvent");
											 phoneEveve.setProperty("from", from);
											 phoneEveve.setProperty("to", to);
											 phoneEveve.setProperty("time", time);
											 phoneEveve.setProperty("long", l);
											graph.addEdge(null, personNew, phoneEveve, "callfrom").setProperty("eventtime", eventtime);
											graph.addEdge(null, phoneEveve, p, "callto").setProperty("eventtime", eventtime);
									  }else{
											graph.addEdge(null, phoneEveve, p, "callto").setProperty("eventtime", eventtime);
									  }
										 
								}
							
						}
						
					}
					
					System.out.println("Titan Commit...");
					long el = System.currentTimeMillis();
					String endTime= DateFormat.transferLongToDate(el);
					System.out.println("结束时间："+endTime);
					
			        //if((count.get())%100==0){
			              graph.commit();
		            // }
graph.shutdown();

		//putRow(data.outputRowMeta, r);


		//return true;


}


}
