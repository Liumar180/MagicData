package com.integrity.dataSmart.dataImport.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;

import com.integrity.dataSmart.pojo.TianmaoData;
import com.integrity.dataSmart.util.DateFormat;
import com.integrity.dataSmart.util.WritelogContents;
import com.integrity.dataSmart.util.solr.Email2SolrUtil;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author liuBf
 * 天猫数据导入程序
 *
 */
public class ImpTianMaoData {
	public static void main(String[] args) {
		
	 BaseConfiguration baseConfiguration = new BaseConfiguration();
	 baseConfiguration.setProperty("storage.backend", "hbase");
	 baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
	 baseConfiguration.setProperty("storage.tablename","titan");
	 System.out.println("Open Data");
	 TitanGraph graph = TitanFactory.open(baseConfiguration);
	 System.out.println("数据库已连接");
	 
	 /**********************数据入库*******************************/
	 long st = System.currentTimeMillis();
	 		String startTime= DateFormat.transferLongToDate(st);
	 		System.out.println("开始时间："+startTime);
	 		int count = 0;
	 		int rows = 2000;//377703397
	 		for (int i = 0; i < 377703397; i+=rows) {
				count += inertdata(graph, i,rows);
				WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\solrInsertLog.txt","已入库数据："+count+" 条");
			}
	 		long el = System.currentTimeMillis();
	 		String endTime= DateFormat.transferLongToDate(el);
	 		System.out.println("结束时间："+endTime);
	 		long longs = el-st;
	 		System.out.println("录入数量:"+count+"个；总用时："+longs/1000+"秒");
		    graph.shutdown();
	}
	
	public static int inertdata(TitanGraph graph,int start,int rows){
		List<TianmaoData> datas= Email2SolrUtil.getTmDataFromSolr("lb_info", start,rows);
	     String uid = "";//uid
	     String username = "";//用户名
	     String password = "";//密码
	     String epassword = "";//邮箱密码
	     String createtime = "";//创建时间
	     String domain = "";//域地址
	     String regip = "";//注册Ip
	     String email = "";//邮箱
	     String lastvisit ="";//最后访问时间
	     String lastip = "";//最后访问ip
	     String phonenum = "";//电话
	     String model = "";
	     String question = "";//问题
	     String place = "";//地点
	     String address = "";
	     String numid = "";//QQ号
	 	 String name = "";//姓名
	 	 String idcard = "";//身份证号
	 	 String country = "";//国家

	 	 for (int d = 0; d < datas.size(); d++) {
	 		address = datas.get(d).getAddress();
	 		domain = datas.get(d).getDomain();
	 		email = datas.get(d).getEmail();
	 		name = datas.get(d).getName();
	 		password = datas.get(d).getPassword();
	 		phonenum = datas.get(d).getPhone();
	 		numid = datas.get(d).getQq();
	 		username = datas.get(d).getUsername();
	 		 
	 		Object accountIte = null;
			if(domain != null && !"".equals(domain) && username != null && !"".equals(username)){
				accountIte = graph.query().has("domain",domain).has("username",username).vertices();
			}else if(domain != null && !"".equals(domain)){
			    accountIte = graph.query().has("domain",domain).vertices();
			}else if(username != null && !"".equals(username)){
			    accountIte = graph.query().has("username",username).vertices();
			}
					
			Vertex tmp = null;
			boolean t =  false;
			if(accountIte != null){
				t = ((Iterable) accountIte).iterator().hasNext();
			}
	 		while(t){
 				tmp = (Vertex)((Iterable)accountIte).iterator().next();

 				Set s = tmp.getPropertyKeys();
 				if(s != null){
	 				for(int i=0;i<s.size();i++){
	 					if(tmp.getProperty("type").toString().equals("Account")){
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
	 				e.printStackTrace();
	 				System.out.println("不存在人物对象"+e.getMessage());
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
 					boolean exist = false;//account
 					boolean phoneExist = false;// 电话
 					boolean emailExist = false;//邮件email
 					boolean loactionExist = false;//地址
 					boolean qqExist = false;//qq
 					for (Iterator iterator = ((Iterable)childs).iterator(); iterator.hasNext();) {
 						Vertex v = (Vertex) iterator.next();
 						if(v != null){
 							if("Account".equals(v.getProperty("type"))){
				              if(domain != null && !"".equals(domain) && username != null && !"".equals(username)){
			                     if(v.getProperty("username").equals(username) && v.getProperty("domain").equals(domain)){
									exist = true;
								 }
				
				              }
				              if(username != null && !"".equals(username)){
			                     if(v.getProperty("username").equals(username)){
		 							exist = true;
			 					 }
				
				              }
					 							
 							}
					 }
 						
 					if(email!=null && !"".equals(email)){
 							if("Email".equals(v.getProperty("type")) && v.getProperty("email").equals(email)){
 								emailExist=true;
 							}
 						}
 					if(numid!=null && !"".equals(numid)){
						if("IM".equals(v.getProperty("type")) && v.getProperty("numid").equals(numid)){
							qqExist=true;
						}
					}

                     if(phonenum!=null && !"".equals(phonenum)){
 							if("Phone".equals(v.getProperty("type")) && v.getProperty("phonenum").equals(phonenum)){
 								phoneExist=true;
 							}
 						}
 					if(address!=null && !"".equals(address)){
 						     String ads= v.getProperty("address");
 							if("Location".equals(v.getProperty("type")) && ads.trim().equals(address)){
 								loactionExist=true;
 							}
 						}
 					}

 					if(!emailExist){//邮箱
 						if(email!=null && !"".equals(email)){
 							Vertex emailtmp = graph.addVertex(null);
 							emailtmp.setProperty("type","Email");
 							emailtmp.setProperty("email",email);
 							if(epassword != null && !"".equals(epassword)){
                             emailtmp.setProperty("password", epassword);
                             }
 							graph.addEdge(null,person,emailtmp,"own");
 						}		
 					}
 					if(!qqExist){//QQ
 						if(numid!=null && !"".equals(numid)){
 							Vertex qqtmp = graph.addVertex(null);
 							qqtmp.setProperty("type","IM");
 							qqtmp.setProperty("domain","QQ");
 							qqtmp.setProperty("numid", numid);
 							graph.addEdge(null,person,qqtmp,"own");
 						}		
 					}

                   if(!phoneExist){//电话
 						if(phonenum!=null && !"".equals(phonenum)){
 							Vertex phonetmp = graph.addVertex(null);
 							phonetmp.setProperty("type","Phone");
 							phonetmp.setProperty("phonenum",phonenum);
                             if(model != null && !"".equals(model)){
                             phonetmp.setProperty("model",model);
                        
 							}
 							graph.addEdge(null,person,phonetmp,"own");
 						}		
 					}
 					if(!loactionExist){//地点
 						if(address!=null && !"".equals(address)){
 							Vertex placetmp = graph.addVertex(null);
 							placetmp.setProperty("type","Location");
 							placetmp.setProperty("address",address);
                             if(place != null && !"".equals(place)){
                             placetmp.setProperty("place",place);
                        
 							}
 							graph.addEdge(null,person,placetmp,"own");
 						}		
 					}


 					if(!exist){
 						//如果属性不相等的话则新增对象
 						Vertex account2 = graph.addVertex(null);
 						account2.setProperty("type","Account");
 						if(domain!=null && !"".equals(domain)){
 							account2.setProperty("domain",domain);
 						}
 						
 						if(uid!=null && !"".equals(uid)){
 							account2.setProperty("uid",uid);
 						}
 				
 						if(username!=null && !"".equals(username)){
 							account2.setProperty("username",username);//用户名
 						}
 				
 						if(password!=null && !"".equals(password)){
 							account2.setProperty("password",password);//密码
 						}

 						if(email!=null && !"".equals(email)){
 							account2.setProperty("email",email);//邮件
 						}
 				
 						if(regip!=null && !"".equals(regip)){
 							account2.setProperty("regip",regip);//注册ip
 						}
 						if(createtime!=null && !"".equals(createtime)){
 							account2.setProperty("createtime",createtime);//注册时间
 						}
 						if(question!=null && !"".equals(question)){
 							account2.setProperty("question",question);//问题
 						}					
 					
 				

 						if(lastvisit!=null && !"".equals(lastvisit)){
 							Vertex event = graph.addVertex(null);
 							event.setProperty("type","LoginEvent");
 							
 							if(domain!=null && !"".equals(domain)){
 								event.setProperty("domain",domain);
 							}
 							
 							if(username!=null && !"".equals(username)){
 								event.setProperty("username",username);
 							}
 							if(lastip!=null && !"".equals(lastip)){
 								event.setProperty("ip",lastip);
 							}
 							
 							event.setProperty("time",lastvisit);
 							graph.addEdge(null,person,event,"login").setProperty("eventtime",lastvisit);
 						}
 						graph.addEdge(null,person,account2,"own");
 											
 					}else{
 						Object eventIte = person.query().labels(new String[]{"login"}).vertices();
 						boolean eventExist = false;
 						for (Iterator iterator = ((Iterable)accountIte).iterator(); iterator.hasNext();) {
 							Vertex v = (Vertex) iterator.next();
                             if(domain != null){
                                if(domain.equals(v.getProperty("domain")) && lastvisit == v.getProperty("time")){
 								eventExist=true;
 							               }
                                       }
 						}
 						
 						if(!eventExist){
 						if(lastvisit!=null && !"".equals(lastvisit)){
 							Vertex event = graph.addVertex(null);
 							event.setProperty("type","LoginEvent");
                             if(domain != null && domain != ""){
                             event.setProperty("domain",domain);
                             }
 							if(username != null && username != ""){
 							event.setProperty("username",username);
                              }
 							if(lastip!=null && !"".equals(lastip)){
 								event.setProperty("ip",lastip);
 							}
 							
 							event.setProperty("time",lastvisit);
 							graph.addEdge(null,person,event,"login").setProperty("eventtime",lastvisit);
 							}
 						}
 					}

 				}
 				
 			}else{
 				System.out.println("最新导入数据");
 				Vertex person2 = graph.addVertex(null);//人物对象
 				person2.setProperty("type","Person");
 				if(name != null && !name.equals("")){
 				person2.setProperty("name",name);
 				}
 				if(idcard != null && !idcard.equals("")){
 				person2.setProperty("idcard",idcard);
 				}
 				if(country != null && !country.equals("")){
 				person2.setProperty("country",country);
 				}
 				
 				Vertex account3 = graph.addVertex(null);//account事件
 				account3.setProperty("type","Account");
                 if(domain != null && !"".equals(domain)){
                   account3.setProperty("domain",domain);
                 }
 				
 				if(uid!=null && !"".equals(uid)){
 					account3.setProperty("uid",uid);
 				}
 				
 				if(username!=null && !"".equals(username)){
 					account3.setProperty("username",username);
 				}
 				
 				if(password!=null && !"".equals(password)){
 					account3.setProperty("password",password);
 				}


 				if(email!=null && !"".equals(email)){
 					account3.setProperty("email",email);
 				}
 				
 				if(createtime!=null && !"".equals(createtime)){
 					account3.setProperty("createtime",createtime);
 				}

 				if(regip!=null && !"".equals(regip)){
 					account3.setProperty("regip",regip);
 				}
 				if(question!=null && !"".equals(question)){
 					account3.setProperty("question",question);//问题
 				}
 				if(phonenum!=null && !"".equals(phonenum)){
 					account3.setProperty("phonenum",phonenum);//电话
 				}
 				//qq
 				if(numid!=null && !"".equals(numid)){
						Vertex qqtmp = graph.addVertex(null);
						qqtmp.setProperty("type","IM");
						qqtmp.setProperty("domain","QQ");
						qqtmp.setProperty("numid", numid);
						graph.addEdge(null,person2,qqtmp,"own");
					}

 				//邮件
 				if(email != null && !email.equals("")){
 				Vertex emailtmp2 = graph.addVertex(null);
 				emailtmp2.setProperty("type","Email");
                 emailtmp2.setProperty("email",email);
                 if(epassword != null && !epassword.equals("")){
                 emailtmp2.setProperty("password", epassword);
                 }
 				
 				graph.addEdge(null,person2,emailtmp2,"own");
 				}	

                //电话
 				if(phonenum!=null && !"".equals(phonenum)){
 							Vertex phonetmp1 = graph.addVertex(null);
 							phonetmp1.setProperty("type","Phone");
 							phonetmp1.setProperty("phonenum",phonenum);
                             if(model != null && !"".equals(model)){
                             phonetmp1.setProperty("model",model);
 							}
 							graph.addEdge(null,person2,phonetmp1,"own");
 						}		
 				//地点
 				if(address!=null && !"".equals(address)){
 							Vertex placetmp = graph.addVertex(null);
 							placetmp.setProperty("type","Location");
 							placetmp.setProperty("address",address);
                             if(place != null && !"".equals(place)){
                             placetmp.setProperty("place",place);
                        
 							}
 							graph.addEdge(null,person2,placetmp,"own");
 						}		
 				
                 //登录事件
 				if(lastvisit!=null && !"".equals(lastvisit)){
 					Vertex event = graph.addVertex(null);
 					event.setProperty("type","LoginEvent");
                 if(domain != null && !"".equals(domain)){
                 event.setProperty("domain",domain);
                 }
 					
                if(username != null && !username.equals("")){
                event.setProperty("username",username);
                }
                if(lastip!=null && !"".equals(lastip)){
						event.setProperty("ip",lastip);
					}	
                if(lastvisit!= null && !"".equals(lastvisit)){
                event.setProperty("time",lastvisit);
                }
 					graph.addEdge(null,person2,event,"login").setProperty("eventtime",lastvisit);
 				}
 				graph.addEdge(null,person2,account3,"own");

 				
 				
 			}
 	        graph.commit();  
	 	 }
	 	
 		return datas.size();
	}
}
