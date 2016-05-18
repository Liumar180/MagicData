package com.integrity.dataSmart.test;

import java.util.Iterator;
import java.util.List;

import com.integrity.dataSmart.impAnalyImport.bean.Resume;
import com.integrity.dataSmart.impAnalyImport.bean.ResumeBase;
import com.integrity.dataSmart.test.ImportResumeData.ResumeInfoImpUtils;
import com.integrity.dataSmart.util.DateFormat;
import com.integrity.dataSmart.util.WritelogContents;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author liuBF
 *简历数据录入
 */
public class Internet {
	static Long counts =0L;
	public static void impResumes(List<Resume> resumeList,TitanGraph graph){
		boolean isSolr = false;
		long s = System.currentTimeMillis();
		String startTime= DateFormat.transferLongToDate(s);
		System.out.println("入库开始时间："+startTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\inDatas0222.txt","入库开始时间："+startTime);
		for(Resume resumes:resumeList){
		boolean isResume = false;
		ResumeBase rb= ResumeInfoImpUtils.getResumelistDatas(resumes.getInformationID());
		List<String> phoneList = ResumeInfoImpUtils.findPhoneNum(rb.getContactID());
			
		String uid = "";//uid
		String username = "";
		if(rb.getLogin() != null){
			username = rb.getLogin().trim();
		}
				
		String password = rb.getPasswordHash();//密码
		    
	    String ct = rb.getCreationDate();//账号创建时间
	    Long createtime = 0L;
	    if(ct != null && !ct.equals("") ){
	     createtime = DateFormat.convert2long(rb.getCreationDate(), "yyyy-MM-dd HH:mm:ss");
	    }
	    
	    String domain = "beyond";
		String regip = "";
		String email = "";
		if(rb.getLogin() != null){
			email = rb.getLogin().trim();
		}
		String lt = "";//最后访问时间
		Long lastvisit = 0L;
		if(!lt.equals("")){
			lastvisit = Long.parseLong(lt);
		}
		String lastip = "";
		List<String> phonenum = phoneList;
		String model = "";
		String question ="";
		
		String place = "";//经纬度
		String address = rb.getAddress();//地址
		
		String title = resumes.getTitle();//简历标题
		String keywords = resumes.getKeyWords();
		Long resumetime = DateFormat.convert2long(resumes.getCreationDate(), "yyyy-MM-dd HH:mm:ss");//简历创建时间
		
	    String name =rb.getName();
		String idcard = "";
		String country = rb.getCounty();//国家
			

		Object accountIte = null;
		boolean istrue = false;
		if(email != null && !"".equals(email)){
			accountIte = graph.query().has("email",email).vertices();
		}else if(phonenum != null && phonenum.size() != 0){
			accountIte = graph.query().has("phonenum",phonenum.get(0)).vertices();
		}
			Vertex tmp = null;
			boolean t =  false;
			if(accountIte != null){
				t = ((Iterable) accountIte).iterator().hasNext();
			}
			while(t){
			tmp = (Vertex)((Iterable)accountIte).iterator().next();
		       if(tmp != null){
					if(tmp.getProperty("type").toString().equals("Account")){
					        istrue  = true;
						 }
			            }
						break;
					}
			if(tmp != null && istrue){
				Vertex person =null;
				try{
				person = (Vertex)tmp.query().labels(new String[]{"own"}).vertices().iterator().next();
				}catch(java.util.NoSuchElementException e){
				e.printStackTrace();
				System.out.println("无人物对象");
				//putRow(data.outputRowMeta, r);
				//return true;
				}
				if(person !=null){
					System.out.println("存在人物对象");
					if(name != null && !name.equals("")){
						Object names = person.getProperty("name");
						if(names ==null || names.equals("")){
							person.setProperty("name",name);
						}
					
					}
					if(idcard != null && !idcard.equals("")){
						Object idcards = person.getProperty("idcard");
						if(idcards ==null || idcards.equals("")){
							person.setProperty("idcard",idcard);
						}
					
					}
					if(country != null && !country.equals("")){
						Object countrys = person.getProperty("country");
						if(countrys ==null || countrys.equals("")){
							person.setProperty("country",country);
						}
					
					}
					
			Object childs = person.query().labels(new String[]{"own"}).vertices();
			boolean exist = false;
			boolean phoneExist = false;
			boolean emailExist = false;
			boolean loactionExist = false;
			boolean resumeExist =false;
			for (Iterator iterator = ((Iterable)childs).iterator(); iterator.hasNext();) {
				Vertex v = (Vertex) iterator.next();
		         if(v != null){
		        	 System.out.println("对象类型："+v.getProperty("type"));
					if("Account".equals(v.getProperty("type"))){
	                 if(email != null && !"".equals(email) && username != null && !"".equals(username)){
	                	String emai = v.getProperty("email");
	                	if(emai != null){
	                        if( emai.equals(email)
	                        		&& v.getProperty("username").equals(username)
	                        		&& v.getProperty("domain").equals(domain)){
									exist = true;
								}
	                     }else{
	                    	 exist = true;//为空 不入数据
	                     }

	                          }
			             }
						
					}

					if(email!=null && !"".equals(email)){
							if("Email".equals(v.getProperty("type")) && v.getProperty("email").equals(email)){
								emailExist=true;
							}
						}
	
	                if(phonenum!=null && phonenum.size() != 0){
							if("Phone".equals(v.getProperty("type")) && v.getProperty("phonenum").equals(phonenum.get(0))){
								phoneExist=true;
							}
						}
					if(address!=null && !"".equals(address)){
							if("Location".equals(v.getProperty("type")) && v.getProperty("address").equals(address)){
								loactionExist=true;
							}
						}
					if(title!=null && !"".equals(title)){
						if("Resume".equals(v.getProperty("type"))){
							resumeExist=true;
						}
					}
					}
			if(!resumeExist){
				if(title != null && !title.equals("")){
					Vertex resume = graph.addVertex(null);
					resume.setProperty("type", "Resume");
					if(title != null && !title.equals("")){
						resume.setProperty("title", title);
					}
					if(keywords != null && !keywords.equals("")){
					resume.setProperty("keywords", keywords);
					}
					if(resumetime != null && !resumetime.equals("")){
					resume.setProperty("time", resumetime);
					}
					graph.addEdge(null,person,resume,"own");
					resumes.setVertexID(resume.getId().toString());//设置VertexID值
					isResume  = true;
				}
			}
					if(!emailExist){//邮箱
						System.out.println("Email");
						boolean isEmail1 = false;
						if(email!=null && !"".equals(email)){
					    Iterable e = graph.getVertices("email",email);
					    Iterator es = e.iterator();
					    if(es.hasNext()){
					    	System.out.println("存在人时邮件");
					    	Vertex emails = (Vertex) es.next();
					    	if(emails.getProperty("type").equals("Email")){
					    		isEmail1 = true;
					    	}
					    	if(isEmail1){
					    		if(emails != null){
					    		 Vertex v = (Vertex)emails.query().labels(new String[]{"own"}).vertices().iterator().next();
					    		 if(v != person){
					    			 graph.addEdge(null,person,emails,"own");
					    		 }
					    		}
					    		
					    	}else{
								Vertex emailtmp = graph.addVertex(null);
								emailtmp.setProperty("type","Email");
								emailtmp.setProperty("email",email);
								/*if(password != null && !"".equals(password)){
	                            emailtmp.setProperty("password", password);
	                            }*/
								graph.addEdge(null,person,emailtmp,"own");
							
					    	}
					    }
						
					}
						
						
					}

                  if(!phoneExist){//电话
                	  System.out.println("Phone");
                	  if(phonenum!=null && phonenum.size() != 0){
                		 for(String ps:phonenum){
						boolean isPhone1 = false;
						Iterable e = graph.getVertices("phonenum",ps);
					    Iterator es = e.iterator();
					    if(es.hasNext()){
					    	System.out.println("存在人时电话");
					    	Vertex phones = (Vertex) es.next();
					    	if(phones.getProperty("type").equals("Phone")){
					    		isPhone1 = true;
					    	}
					    	if(isPhone1){
					    		if(phones != null){
					    			Vertex v = (Vertex)phones.query().labels(new String[]{"own"}).vertices().iterator().next();
						    		 if(v != person){
						    			 graph.addEdge(null,person,phones,"own");
						    		 }
					    			
					    		}
					    	}else{
								Vertex phonetmp = graph.addVertex(null);
								phonetmp.setProperty("type","Phone");
								phonetmp.setProperty("phonenum",ps);
	                            if(model != null && !"".equals(model)){
	                            phonetmp.setProperty("model",model);
	                       
								}
								graph.addEdge(null,person,phonetmp,"own");
							}
					    }
                	  }
                  }
								
							
                	  
					}
					if(!loactionExist){//地点
						boolean isLocation1 = false;
						if(address!=null && !"".equals(address)){
							System.out.println("Location");
							/*
							Iterable e = graph.getVertices("address",address);
						    Iterator es = e.iterator();
						    if(es.hasNext()){
						    	System.out.println("存在人时地址");
						    	Vertex locations = (Vertex) es.next();
						    	if(locations.getProperty("type").equals("Location")){
						    		isLocation1 = true;
						    	}
						    	if(isLocation1){
						    		if(locations != null){
						    			Vertex v = (Vertex)locations.query().labels(new String[]{"own"}).vertices().iterator().next();
							    		 if(v != person){
							    			 graph.addEdge(null,person,locations,"own");
							    		 }
						    			
						    		}
						    	}else{
									Vertex placetmp = graph.addVertex(null);
									placetmp.setProperty("type","Location");
									placetmp.setProperty("address",address);
		                            if(place != null && !"".equals(place)){
		                            placetmp.setProperty("place",place);
		                       
									}
									graph.addEdge(null,person,placetmp,"own");
								}							
						    }*/
							//else{

								Vertex placetmp = graph.addVertex(null);
								placetmp.setProperty("type","Location");
								placetmp.setProperty("address",address);
	                            if(place != null && !"".equals(place)){
	                            placetmp.setProperty("place",place);
	                       
								}
								graph.addEdge(null,person,placetmp,"own");
							
						    //}
							
						}
						
					}
					if(!exist){
						System.out.println("Account");
						Vertex account2 = graph.addVertex(null);
						account2.setProperty("type","Account");
						account2.setProperty("domain",domain);
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
						if(createtime!=0L && !"".equals(createtime)){
							account2.setProperty("createtime",createtime);//注册时间
						}
						if(question!=null && !"".equals(question)){
							account2.setProperty("question",question);//问题
						}
				
						graph.addEdge(null,person,account2,"own");

						if(lastip!=null && !"".equals(lastip)){
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
							if(lastvisit!=0L && !"".equals(lastvisit)){
								event.setProperty("time",lastvisit);
							}
							graph.addEdge(null,person,event,"login").setProperty("eventtime",lastvisit);
						}
											
					}
					/*else{
				Object eventIte = person.query().labels(new String[]{"login"}).vertices();
				boolean eventExist = false;
				for (Iterator iterator = ((Iterable)eventIte).iterator(); iterator.hasNext();) {
					Vertex v = (Vertex) iterator.next();
                    if(lastip != null){
                       if(lastip.equals((String)v.getProperty("ip")) && lastvisit == (Long)v.getProperty("time") ){
						eventExist=true;
					               }
                              }

				}
				
						if(!eventExist){
                        if(lastip != null && !lastip.equals("") && lastvisit != 0L){
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
							if(lastvisit!=0L && !"".equals(lastvisit)){
								event.setProperty("time",lastvisit);
							}
							graph.addEdge(null,person,event,"login").setProperty("eventtime",lastvisit);
						}
                       }
					}*/

				}
				
			}else{
				System.out.println("最新简历数据-标题："+resumes.getTitle());
				boolean isEmail = false;
				boolean isPhone = false;
				boolean isLocation = false;
				
				Vertex personNew = null;
                //邮件
				if(email != null && !email.equals("")){
			    Iterable e = graph.getVertices("email",email);
			    Iterator es = e.iterator();
			    if(es.hasNext()){
			    	System.out.println("邮件");
			    	Vertex emails = (Vertex) es.next();
					if(emails !=null){
					if(emails.getProperty("type").equals("Email")){
			    		isEmail = true;
			    	}
					}
			    	
			    	if(isEmail){
			    		if(emails != null){
			    			Vertex em = null;
			    			try{
			    				em= (Vertex)emails.query().labels(new String[]{"own"}).vertices().iterator().next();
			    				}catch(java.util.NoSuchElementException e1){
			    				e1.printStackTrace();
			    				}
			    			
			    			if(em != null && em.getProperty("type").equals("Person")){
			    				personNew = (Vertex)em;
			    				graph.addEdge(null,personNew,emails,"own");
			    			}else{
					    		personNew = graph.addVertex(null);
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
					    		
					    		graph.addEdge(null,personNew,emails,"own");
					    	
			    				
			    			}
			    		}
			    		
			    	}else{
			    		personNew = graph.addVertex(null);
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
			    		
			    		Vertex emailtmp2 = graph.addVertex(null);
			    		emailtmp2.setProperty("type","Email");
			    		emailtmp2.setProperty("email",email);
			    		/*if(password != null && !password.equals("")){
			    			emailtmp2.setProperty("password", password);
			    		}*/
			    		
			    		graph.addEdge(null,personNew,emailtmp2,"own");
			    	}
			    }else{

		    		personNew = graph.addVertex(null);
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
		    		
		    		Vertex emailtmp2 = graph.addVertex(null);
		    		emailtmp2.setProperty("type","Email");
		    		emailtmp2.setProperty("email",email);
		    		/*if(password != null && !password.equals("")){
		    			emailtmp2.setProperty("password", password);
		    		}*/
		    		
		    		graph.addEdge(null,personNew,emailtmp2,"own");
		    	
			    	
			    }
				
				}else{
					personNew = graph.addVertex(null);
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
					
				}
				
				
				//电话
			if(phonenum!=null && phonenum.size() != 0){
				for(String ps:phonenum){
				Iterable e = graph.getVertices("phonenum",ps);
			    Iterator es = e.iterator();
			    if(es.hasNext()){
			    	System.out.println("电话");
			    	Vertex phones = (Vertex) es.next();
			    	if(phones.getProperty("type").equals("Phone")){
			    		isPhone = true;
			    	}
			    	if(isPhone){
			    		if(phones != null){
			    			graph.addEdge(null,personNew,phones,"own");
			    		}
			    	}else{
			    		Vertex phonetmp1 = graph.addVertex(null);
			    		phonetmp1.setProperty("type","Phone");
			    		phonetmp1.setProperty("phonenum",ps);
			    		if(model != null && !"".equals(model)){
			    			phonetmp1.setProperty("model",model);
			    			
			    		}
			    		graph.addEdge(null,personNew,phonetmp1,"own");
			    	}
			    }else{
		    		Vertex phonetmp1 = graph.addVertex(null);
		    		phonetmp1.setProperty("type","Phone");
		    		phonetmp1.setProperty("phonenum",ps);
		    		if(model != null && !"".equals(model)){
		    			phonetmp1.setProperty("model",model);
		    		}
		    		graph.addEdge(null,personNew,phonetmp1,"own");
		    	
			    }
			}	
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
				
				if(createtime!=0L && !"".equals(createtime)){
					account3.setProperty("createtime",createtime);
				}

				if(regip!=null && !"".equals(regip)){
					account3.setProperty("regip",regip);
				}
				if(question!=null && !"".equals(question)){
					account3.setProperty("question",question);//问题
				}
				if(phonenum!=null && phonenum.size() != 0){
					account3.setProperty("phonenum",phonenum.get(0));//电话
				}
				graph.addEdge(null,personNew,account3,"own");

				
				//地点
			if(address!=null && !"".equals(address)){
/*				Iterable e = graph.getVertices("address",address);
			    Iterator es = e.iterator();
			    if(es.hasNext()){
			    	System.out.println("地址");
			    	Vertex locations = (Vertex) es.next();
			    	if(locations.getProperty("type").equals("Location")){
			    		isLocation = true;
			    	}
			    	if(isLocation){
			    		if(locations != null){
			    			graph.addEdge(null,personNew,locations,"own");
			    		}
			    	}else{
			    		Vertex placetmp = graph.addVertex(null);
			    		placetmp.setProperty("type","Location");
			    		placetmp.setProperty("address",address);
			    		if(place != null && !"".equals(place)){
			    			placetmp.setProperty("place",place);
			    			
			    		}
			    		graph.addEdge(null,personNew,placetmp,"own");
			    		
			    	}							
			    }*/
				//else{
		    		Vertex placetmp = graph.addVertex(null);
		    		placetmp.setProperty("type","Location");
		    		placetmp.setProperty("address",address);
		    		if(place != null && !"".equals(place)){
		    			placetmp.setProperty("place",place);
		    		}
		    		graph.addEdge(null,personNew,placetmp,"own");
			   // }
				
			}
		//简历
		if(title != null && !title.equals("")){
			Vertex resume = graph.addVertex(null);
			resume.setProperty("type", "Resume");
			if(title != null && !title.equals("")){
				resume.setProperty("title", title);
			}
			if(keywords != null && !keywords.equals("")){
			resume.setProperty("keywords", keywords);
			}
			if(resumetime != null && !resumetime.equals("")){
			resume.setProperty("time", resumetime);
			}
			graph.addEdge(null,personNew,resume,"own");
			resumes.setVertexID(resume.getId().toString());//设置VertexID值
			isResume = true;
		}	
        //登录事件
/*		if(lastip!=null && !"".equals(lastip)){
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
           if(lastvisit!= 0L && !"".equals(lastvisit)){
           event.setProperty("time",lastvisit);
           }
					
				graph.addEdge(null,personNew,event,"login").setProperty("eventtime",lastvisit);
			}*/
				
			}
			++counts;
				graph.commit();
			System.out.println("本次录入总数："+resumeList.size()+"| 录入进度：第 "+counts+"条数据");
			WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\inDatas0222.txt",
					"简历信息ID ："+resumes.getInformationID()+" -标题：-"+resumes.getTitle());
			//solr 存入
			if(isResume){
				isSolr = true;
				System.out.println("---------solr-------------");
				resumes.setType("Resume");
				resumes.setuTCDate(com.integrity.dataSmart.impAnalyImport.util.DateFormat.solrDate(resumes.getCreationDate()));//创建时间
				resumes.setuTMDate(com.integrity.dataSmart.impAnalyImport.util.DateFormat.solrDate(resumes.getModifyDate()));//修改时间
				ResumeInfoImpUtils.insertResumeToSolr(resumes);
			}
			}
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\inDatas0222.txt", "本次录入数量 ："+counts);
		long ss = System.currentTimeMillis();
		String endTime= DateFormat.transferLongToDate(ss);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\inDatas0222.txt","本次入库结束时间："+endTime);
		WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\inDatas0222.txt","本次入库耗时："+(ss - s)/1000+" 秒");
		System.out.println("本次入库结束时间："+endTime);
		System.out.println("本次入库耗时："+(ss - s)/1000+" 秒");
		if(isSolr){
			ResumeInfoImpUtils.closeResumeSolr();
		}

	}

}
