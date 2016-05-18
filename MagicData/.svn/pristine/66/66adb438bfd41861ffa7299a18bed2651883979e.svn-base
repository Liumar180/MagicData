package com.integrity.dataSmart.test.twPerson;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.lang.StringUtils;

import com.integrity.dataSmart.test.twData.ParseHtmlMain;
import com.integrity.dataSmart.test.twData.PersonTemp;
import com.integrity.dataSmart.test.twData.PhoneTemp;
import com.integrity.dataSmart.util.DateFormat;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class DataInfoUtils {
	public static void main(String[] args) {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		System.out.println("开始连接数据库...");
		baseConfiguration.setProperty("storage.backend", "hbase");
		baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		baseConfiguration.setProperty("storage.tablename","titan");
		TitanGraph graph = TitanFactory.open(baseConfiguration);
		System.out.println("数据库已连接");
		List<PersonTemp> PersonList = ParseHtmlMain.getTWPerson_student();
		DataInfoUtils.importPersonTW(PersonList, graph);
		//List<PhoneTemp> phones = ParseHtmlMain.getCalldetail();
		//DataInfoUtils.ImportPhone(phones, graph);
		
/*		Iterable addIte = graph.query().has("name","MS LIU LAI MEI").vertices();
		Vertex person = (Vertex) addIte.iterator().next();

		Iterable<Vertex> add = person.query().labels(new String[]{"own"}).vertices();
		Vertex ss = add.iterator().next();
		if(ss.getProperty("type").equals("Location")){
			Set<String> sets = ss.getPropertyKeys();
			for (String key : sets) {
				System.out.println("key:"+key+"|value:"+ss.getProperty(key));
			}
		}else{
		    	Vertex placetmp = graph.addVertex(null);
		    	placetmp.setProperty("type","Location");
		    	placetmp.setProperty("address","FT A 18/F BLK C GRANDEUR GARDENS");
		    	graph.addEdge(null,person,placetmp,"own");
		}
		graph.commit();
		graph.shutdown();*/
		
	}
	static int pcounts = 0;
	/**
	 * @param pers
	 * 人员信息导入
	 */
	public static void importPersonTW(List<PersonTemp> pers,TitanGraph graph){
		long st = System.currentTimeMillis();
		String startTime= DateFormat.transferLongToDate(st);
		System.out.println("开始时间："+startTime);
		for(PersonTemp personInfo:pers){
		
		String name = personInfo.getName();//名字
		String idcard = personInfo.getIdcard();//身份证
		String country = personInfo.getCountry();//国家
		String address = personInfo.getAddress();//地址
			/**
			 * 关系类型存 "2"
			 */
		String masterIdcard = personInfo.getMasterIdcard();//户主身份证
		String masterName = personInfo.getMasterName();//户主姓名
			/**
			 * 关系类型存 "3"
			 */
		String fatherName = personInfo.getFatherName();//父亲名
			/**
			 * 关系类型存 "4"
			 */
		String motherName = personInfo.getMotherName();//母亲名
		String spouseName = personInfo.getSpouseName();//配偶名
	    Object PersonIte = personInfo;
		boolean istrue = false;
		if(idcard != null && !"".equals(idcard)){
			PersonIte = graph.query().has("idcard",idcard).vertices();
			}
				Vertex tmp = null;
				boolean t =  false;
				if(PersonIte != null){
					t = ((Iterable) PersonIte).iterator().hasNext();
				}
				while(t){
				tmp = (Vertex)((Iterable)PersonIte).iterator().next();
			       if(tmp != null){
					if(tmp.getProperty("type").toString().equals("Person")){
					        istrue  = true;
						 }
			            }
						break;
				}
			if(tmp != null && istrue){
				System.out.println("存在人物对象");
				if(name != null && !name.equals("")){
				Object names = tmp.getProperty("name");
				if(names ==null || names.equals("")){
						tmp.setProperty("name",name);
					}
				}
				if(idcard != null && !idcard.equals("")){
					Object idcards = tmp.getProperty("idcard");
					if(idcards ==null || idcards.equals("")){
						tmp.setProperty("idcard",idcard);
					}
				}
				if(country != null && !country.equals("")){
					Object countrys = tmp.getProperty("country");
					if(countrys ==null || countrys.equals("")){
						tmp.setProperty("country",country);
					}
				}
				Object relations = tmp.query().labels(new String[]{"relational"}).vertices();
				boolean isMaster = false;
				boolean isfather = false;
				boolean ismother = false;
				boolean isspouse = false;
				for (Iterator iterator = ((Iterable)relations).iterator(); iterator.hasNext();){
					Vertex v = (Vertex) iterator.next();
					if(masterName!=null && !"".equals(masterName)){
						if("Person".equals(v.getProperty("type"))){
							if(masterName.equals(v.getProperty("name"))){
								isMaster=true;
							}
						}
					 }
					if(fatherName!=null && !"".equals(fatherName)){
						if("Person".equals(v.getProperty("type"))){
							if(fatherName.equals(v.getProperty("name"))){
								isfather=true;
							}
					    }
				    }
					if(motherName!=null && !"".equals(motherName)){
						if("Person".equals(v.getProperty("type"))){
							if(motherName.equals(v.getProperty("name"))){
								ismother=true;
							}
					   }  
				    }
					if(spouseName!=null && !"".equals(spouseName)){
						if("Person".equals(v.getProperty("type"))){
							if(spouseName.equals(v.getProperty("name"))){
								isspouse=true;
							}
					   }  
				    }
				}
				if(!isMaster){
					//查户主信息
					Object mPerson = null;
		    		if(masterIdcard != null && !masterIdcard.equals("")){
		    			mPerson = graph.query().has("idcard",masterIdcard).vertices();
		    		}
		    		boolean t1 =  false;
		    		Vertex tmp1 = null;
		    		boolean istrue1 = false;
					if(mPerson != null){
						t1 = ((Iterable) mPerson).iterator().hasNext();
					}
					while(t1){
					tmp1 = (Vertex)((Iterable)mPerson).iterator().next();
				       if(tmp1 != null){
						if(tmp1.getProperty("type").toString().equals("Person")){
						        istrue1  = true;
							 }
				            }
							break;
					}
					if(tmp1 != null && istrue1){
						//和户主关系
						graph.addEdge(null,tmp,tmp1,"relational").setProperty("relationtype", "2");
					}else{
						Vertex mper = graph.addVertex(null);
						mper.setProperty("type","Person");
						if(masterName != null && !masterName.equals("")){
						mper.setProperty("name",masterName);
						}
						
						graph.addEdge(null,tmp,mper,"relational").setProperty("relationtype", "2");
						
					}
				}
				if(!isfather){
					if(fatherName != null && !fatherName.equals("")){
						Vertex father = graph.addVertex(null);
						father.setProperty("type","Person");
						father.setProperty("name", fatherName);
						graph.addEdge(null, tmp, father,"relational").setProperty("relationtype", "3");
					}
				}
				if(!ismother){
					if(motherName != null && !motherName.equals("")){
						Vertex mother = graph.addVertex(null);
						mother.setProperty("type","Person");
						mother.setProperty("name", motherName);
						graph.addEdge(null, tmp, mother,"relational").setProperty("relationtype", "4");
					}
				}
				if(!isspouse){
					if(spouseName != null && !StringUtils.isBlank(spouseName)){
						Vertex spouse = graph.addVertex(null);
						spouse.setProperty("type","Person");
						spouse.setProperty("name", spouseName);
						graph.addEdge(null, tmp, spouse,"relational").setProperty("relationtype", "5");
					}
				}
				
			    Object childs = tmp.query().labels(new String[]{"own"}).vertices();
				boolean loactionExist = false;
				for (Iterator iterator = ((Iterable)childs).iterator(); iterator.hasNext();) {
				Vertex v = (Vertex) iterator.next();
					if(address!=null && !"".equals(address)){
							if("Location".equals(v.getProperty("type")) && v.getProperty("address").equals(address)){
								loactionExist=true;
							}
						}
					}
					if(!loactionExist){//地点
						if(address!=null && !"".equals(address)){
								Vertex placetmp = graph.addVertex(null);
								placetmp.setProperty("type","Location");
								placetmp.setProperty("address",address);
	                            
								graph.addEdge(null,tmp,placetmp,"own");
						}
					}
					
				}else{
				System.out.println("最新导入数据：");
				Vertex personNew = graph.addVertex(null);
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
				/********父亲********/
				if(fatherName != null && !fatherName.equals("")){
					Vertex father = graph.addVertex(null);
					father.setProperty("type","Person");
					father.setProperty("name", fatherName);
					graph.addEdge(null, personNew, father,"relational").setProperty("relationtype", "3");
				}
				/*******母亲*********/
				if(motherName != null && !motherName.equals("")){
					Vertex mother = graph.addVertex(null);
					mother.setProperty("type","Person");
					mother.setProperty("name", motherName);
					graph.addEdge(null, personNew, mother,"relational").setProperty("relationtype", "4");
					
				}
				/********配偶********/
				if(spouseName != null && !spouseName.equals("")){
					Vertex spouse = graph.addVertex(null);
					spouse.setProperty("type","Person");
					spouse.setProperty("name", spouseName);
					graph.addEdge(null, personNew, spouse,"relational").setProperty("relationtype", "5");
					
				}
				/*********户主关系************/
				//查户主信息
				Object mPerson = null;
	    		if(masterIdcard != null && !masterIdcard.equals("")){
	    			mPerson = graph.query().has("idcard",masterIdcard).vertices();
	    		
	    		boolean t1 =  false;
	    		Vertex tmp1 = null;
	    		boolean istrue1 = false;
				if(mPerson != null){
					t1 = ((Iterable) mPerson).iterator().hasNext();
				}
				while(t1){
				tmp1 = (Vertex)((Iterable)mPerson).iterator().next();
			       if(tmp1 != null){
					if(tmp1.getProperty("type").toString().equals("Person")){
					        istrue1  = true;
						 }
			            }
						break;
				}
				if(tmp1 != null && istrue1){
					//和户主关系
					graph.addEdge(null,personNew,tmp1,"relational").setProperty("relationtype", "2");
				}else{
					Vertex mper = graph.addVertex(null);
					mper.setProperty("type","Person");
					if(masterName != null && !masterName.equals("")){
					mper.setProperty("name",masterName);
					}
					graph.addEdge(null,personNew,mper,"relational").setProperty("relationtype", "2");
					
				}
	    		}
				/*****************************/
				if(address!=null && !"".equals(address)){
			    	Vertex placetmp = graph.addVertex(null);
			    	placetmp.setProperty("type","Location");
			    	placetmp.setProperty("address",address);
			    	graph.addEdge(null,personNew,placetmp,"own");
				   }		
				}
			pcounts++;
		        graph.commit();
				
	  }
		System.out.println("Titan Commit...");
		long el = System.currentTimeMillis();
		String endTime= DateFormat.transferLongToDate(el);
		System.out.println("结束时间："+endTime);
		System.out.println("录入人员信息数据："+pcounts+" 条");
		graph.shutdown();
	}
	static int Pcounts = 0;
	/**
	 * @param phoneInfo
	 * 电话数据导入
	 */
	public static void ImportPhone(List<PhoneTemp> phoneInfo,TitanGraph graph){
		 long st = System.currentTimeMillis();
		 String startTime= DateFormat.transferLongToDate(st);
		 System.out.println("开始时间："+startTime);
		 for(PhoneTemp pt:phoneInfo){
		 String model = pt.getModel();
		 String phonenum=pt.getPhonenum();
		 String from =pt.getPhonenum();
		 String to =pt.getTo();
	     String name =pt.getName();
		 String idcard =pt.getIdcard();
	     String country =pt.getCountry();
		 String tname =pt.getTname();
		 String tidcard =pt.getTidcard();
		 String tcountry =pt.getTcountry();
		 Long time = pt.getTime();
		 Long eventtime = pt.getTime();//事件时间
		 Long l = pt.getTimelong();
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
			Pcounts ++;
	        graph.commit();
	     }
		 long el = System.currentTimeMillis();
		 String endTime= DateFormat.transferLongToDate(el);
		 System.out.println("结束时间："+endTime);
		 System.out.println("录入电话数据总数"+Pcounts);
         graph.shutdown();
	}

}
