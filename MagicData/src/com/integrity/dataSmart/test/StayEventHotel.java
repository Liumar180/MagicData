package com.integrity.dataSmart.test;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.commons.configuration.BaseConfiguration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author integrity
 *酒店数据入库
 *
 *根据 订单Id 酒店Id 查同住人
 *
 *根据 订单id 酒店id 房间号 查询酒店事件
 */
public class StayEventHotel {
	public static void main(String[] args) {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		 baseConfiguration.setProperty("storage.backend", "hbase");
		 baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		 baseConfiguration.setProperty("storage.tablename","titan");
		 System.out.println("Open Data");
		 TitanGraph graph = TitanFactory.open(baseConfiguration);
		 System.out.println("数据库已连接");
		 
		    String hotelname = "北京奥运村店";//酒店名
		    String hotelid  = "010036";//酒店id
			String orderno  = "164919";//订单id
			SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Long arrivaldate =0L;
			 try {
			    arrivaldate = sf.parse("2010/11/18 00:00:00").getTime();
				  } catch (Exception e) {
				   e.printStackTrace();
				  }
		    //String arrivaldate = "";//到店日期
		    //String departuredate = "";//离店日期
		    Long departuredate =0L;
			 try {
				 departuredate = sf.parse("2010/11/19 00:00:00").getTime();
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		    String roomno = "439";//房间号
		    String hotelflag = "0";//酒店标识
		    
			String name = "孙一峰";
			String idcard = "341202198612120214";
			String country = "CHN";
			
			String address = "阜阳市颍州区";
			String place = "";

		//不存在同一房间的人
		Object its = graph.query().has("orderno",orderno).has("hotelid",hotelid).vertices();//查看同住人
		Vertex tmp1 = null;
		boolean isLive = false;
		if(its != null){
		Iterator t1  = ((Iterable)its).iterator();
		while(t1.hasNext()){
			isLive = true;
			try {
				tmp1 = (Vertex) t1.next();
							
		if(tmp1 != null){
		if(!tmp1.getProperty("roomno").equals(roomno)){//不是同房间，但是同一酒店和订单的同住人
			Vertex person3 =null;
			Iterator sa = tmp1.query().labels(new String[]{"stay"}).vertices().iterator();
			while(sa.hasNext()){
				person3 = (Vertex) sa.next();
				if(person3 != null){
			if(!person3.getProperty("idcard").equals(idcard)){
				
					Iterable pers1 =  graph.getVertices("idcard",idcard);
					Iterator pers2 = pers1.iterator();
					if(pers2.hasNext()){
						boolean isTrue = true;
						Vertex pers3 = (Vertex) pers2.next();
						Iterator coh = tmp1.query().labels(new String[]{"cohabit"}).vertices().iterator();
						while(coh.hasNext()){
							Vertex people = (Vertex) coh.next();
							if(people == pers3){
								isTrue = false;
							}
						}
						if(isTrue){
							graph.addEdge(null, tmp1, pers3, "cohabit");//同住人（同房间）
						}
					}else{
						Vertex person4 = graph.addVertex(null);
						person4.setProperty("type", "Person");
						if(name != null && !name.equals("")){
							person4.setProperty("name",name);
						}
						if(idcard != null && !idcard.equals("")){
							person4.setProperty("idcard",idcard);
						}
						if(country != null && !country.equals("")){
							person4.setProperty("country",country);
						}
						
						if(hotelid != null && !"".equals(hotelid)){
							Vertex Hotel = graph.addVertex(null);
							Hotel.setProperty("type","StayEvent");
							if(hotelid!=null && !"".equals(hotelid)){
								Hotel.setProperty("hotelid",hotelid);
							}
							if(hotelname!=null && !"".equals(hotelname)){
								Hotel.setProperty("hotelname","如家 "+hotelname);
							}else{
								Hotel.setProperty("hotelname","如家酒店");
							}
							
							if(orderno!=null && !"".equals(orderno)){
								Hotel.setProperty("orderno",orderno);
							}
							if(arrivaldate!=null && !"".equals(arrivaldate)){
								Hotel.setProperty("arrivaldate",arrivaldate);
							}
							if(departuredate!=null && !"".equals(departuredate)){
								Hotel.setProperty("departuredate",departuredate);
							}
							if(roomno!=null && !"".equals(roomno)){
								Hotel.setProperty("roomno",roomno);
							}
							if(hotelflag!=null && !"".equals(hotelflag)){
								Hotel.setProperty("hotelflag",hotelflag);
							}
						    graph.addEdge(null,person4,Hotel,"stay").setProperty("eventtime", arrivaldate);
							}
						graph.addEdge(null, tmp1, person4, "cohabit");
						
						if(address!=null && !"".equals(address)){
		
								Vertex place3 = graph.addVertex(null);
								place3.setProperty("type","Location");
								place3.setProperty("address",address);
								if(place != null && !"".equals(place)){
									place3.setProperty("place",place);
								}
								graph.addEdge(null,person4,place3,"own");
								
							
							
					    }
						
					}
					
					
				
				
				
			}
						
			}else{//查不到人物对象
				Vertex person5 = graph.addVertex(null);
				person5.setProperty("type", "Person");
				if(name != null && !name.equals("")){
					person5.setProperty("name",name);
				}
				if(idcard != null && !idcard.equals("")){
					person5.setProperty("idcard",idcard);
				}
				if(country != null && !country.equals("")){
					person5.setProperty("country",country);
				}
				
				if(hotelid != null && !"".equals(hotelid)){
					Vertex Hotel = graph.addVertex(null);
					Hotel.setProperty("type","StayEvent");
					if(hotelid!=null && !"".equals(hotelid)){
						Hotel.setProperty("hotelid",hotelid);
					}
					if(hotelname!=null && !"".equals(hotelname)){
						Hotel.setProperty("hotelname","如家 "+hotelname);
					}else{
						Hotel.setProperty("hotelname","如家酒店");
					}
					
					if(orderno!=null && !"".equals(orderno)){
						Hotel.setProperty("orderno",orderno);
					}
					if(arrivaldate!=null && !"".equals(arrivaldate)){
						Hotel.setProperty("arrivaldate",arrivaldate);
					}
					if(departuredate!=null && !"".equals(departuredate)){
						Hotel.setProperty("departuredate",departuredate);
					}
					if(roomno!=null && !"".equals(roomno)){
						Hotel.setProperty("roomno",roomno);
					}
					if(hotelflag!=null && !"".equals(hotelflag)){
						Hotel.setProperty("hotelflag",hotelflag);
					}
				    graph.addEdge(null,person5,Hotel,"stay").setProperty("eventtime", arrivaldate);
					}
				
				if(address!=null && !"".equals(address)){
						
						Vertex place4 = graph.addVertex(null);
						place4.setProperty("type","Location");
						place4.setProperty("address",address);
						if(place != null && !"".equals(place)){
							place4.setProperty("place",place);
						}
						graph.addEdge(null,person5,place4,"own");
					
			    }
				
			}
					
				}
			if(person3 == null){
				//查不到人物对象
				Vertex person5 = graph.addVertex(null);
				person5.setProperty("type", "Person");
				if(name != null && !name.equals("")){
					person5.setProperty("name",name);
				}
				if(idcard != null && !idcard.equals("")){
					person5.setProperty("idcard",idcard);
				}
				if(country != null && !country.equals("")){
					person5.setProperty("country",country);
				}
				
				if(hotelid != null && !"".equals(hotelid)){
					Vertex Hotel = graph.addVertex(null);
					Hotel.setProperty("type","StayEvent");
					if(hotelid!=null && !"".equals(hotelid)){
						Hotel.setProperty("hotelid",hotelid);
					}
					if(hotelname!=null && !"".equals(hotelname)){
						Hotel.setProperty("hotelname","如家 "+hotelname);
					}else{
						Hotel.setProperty("hotelname","如家酒店");
					}
					
					if(orderno!=null && !"".equals(orderno)){
						Hotel.setProperty("orderno",orderno);
					}
					if(arrivaldate!=null && !"".equals(arrivaldate)){
						Hotel.setProperty("arrivaldate",arrivaldate);
					}
					if(departuredate!=null && !"".equals(departuredate)){
						Hotel.setProperty("departuredate",departuredate);
					}
					if(roomno!=null && !"".equals(roomno)){
						Hotel.setProperty("roomno",roomno);
					}
					if(hotelflag!=null && !"".equals(hotelflag)){
						Hotel.setProperty("hotelflag",hotelflag);
					}
				    graph.addEdge(null,person5,Hotel,"stay").setProperty("eventtime", arrivaldate);
					}
				
				if(address!=null && !"".equals(address)){

						Vertex place4 = graph.addVertex(null);
						place4.setProperty("type","Location");
						place4.setProperty("address",address);
						if(place != null && !"".equals(place)){
							place4.setProperty("place",place);
						}
						graph.addEdge(null,person5,place4,"own");

					
			    }
				
			
			}
				
			}else{
				//同房间
				Iterator sa = tmp1.query().labels(new String[]{"stay"}).vertices().iterator();
				Vertex person = (Vertex) sa.next();
				if(person !=null){
					if(!person.getProperty("idcard").equals(idcard)){
						Iterable pers1 =  graph.getVertices("idcard",idcard);
						Iterator pers2 = pers1.iterator();
						if(pers2.hasNext()){

							boolean isTrue = true;
							Vertex pers3 = (Vertex) pers2.next();
							Iterator coh = tmp1.query().labels(new String[]{"cohabit"}).vertices().iterator();
							while(coh.hasNext()){
								Vertex people = (Vertex) coh.next();
								if(people == pers3){
									isTrue = false;
								}
							}
							if(isTrue){
								graph.addEdge(null, tmp1, pers3, "cohabit");//同住人（同房间）
							}
						
						}else{
							Vertex person1 = graph.addVertex(null);
							person1.setProperty("type", "Person");
							if(name != null && !name.equals("")){
								person1.setProperty("name",name);
							}
							if(idcard != null && !idcard.equals("")){
								person1.setProperty("idcard",idcard);
							}
							if(country != null && !country.equals("")){
								person1.setProperty("country",country);
							}
							
							graph.addEdge(null, person1, tmp1, "stay").setProperty("eventtime", arrivaldate);
							
							graph.addEdge(null, tmp1, person1, "cohabit");//同住人（同房间）
							

							if(address!=null && !"".equals(address)){

									Vertex place1 = graph.addVertex(null);
									place1.setProperty("type","Location");
									place1.setProperty("address",address);
									if(place != null && !"".equals(place)){
										place1.setProperty("place",place);
									}
									graph.addEdge(null,person1,place1,"own");

								
						    }
						}
						
					}

				}else{//查不到人物对象
						Vertex person2 = graph.addVertex(null);
						person2.setProperty("type", "Person");
						if(name != null && !name.equals("")){
							person2.setProperty("name",name);
						}
						if(idcard != null && !idcard.equals("")){
							person2.setProperty("idcard",idcard);
						}
						if(country != null && !country.equals("")){
							person2.setProperty("country",country);
						}
						graph.addEdge(null, person2, tmp1, "stay").setProperty("eventtime", arrivaldate);
						
						if(address!=null && !"".equals(address)){

								Vertex place2 = graph.addVertex(null);
								place2.setProperty("type","Location");
								place2.setProperty("address",address);
								if(place != null && !"".equals(place)){
									place2.setProperty("place",place);
								}
								graph.addEdge(null,person2,place2,"own");

							
							
					    }
						
					}
				if(person == null){
					//查不到人物对象
					Vertex person2 = graph.addVertex(null);
					person2.setProperty("type", "Person");
					if(name != null && !name.equals("")){
						person2.setProperty("name",name);
					}
					if(idcard != null && !idcard.equals("")){
						person2.setProperty("idcard",idcard);
					}
					if(country != null && !country.equals("")){
						person2.setProperty("country",country);
					}
					graph.addEdge(null, person2, tmp1, "stay").setProperty("eventtime", arrivaldate);
					
					if(address!=null && !"".equals(address)){

							Vertex place2 = graph.addVertex(null);
							place2.setProperty("type","Location");
							place2.setProperty("address",address);
							if(place != null && !"".equals(place)){
								place2.setProperty("place",place);
							}
							graph.addEdge(null,person2,place2,"own");
						
				    }
							
				}
			}
			
		}else{
								
			if(idcard != null && !idcard.equals("")){
				Object pp= graph.query().has("idcard",idcard).vertices();
				if(((Iterable) pp).iterator().hasNext()){
					Vertex perLive  = (Vertex) ((Iterable) pp).iterator().next();
					
					Iterator<Vertex> s = perLive.query().labels(new String[]{"own"}).vertices().iterator();
					boolean loactionExist = false;
					while(s.hasNext()){
						Vertex v = s.next();
						if(address!=null && !"".equals(address)){
							String add  = (String)v.getProperty("address");
							if("Location".equals(v.getProperty("type")) && add.trim().equals(address.trim())){
								loactionExist=true;
							}
						}
					}
					if(!loactionExist){
						if(address!=null && !"".equals(address)){
						Vertex placetmp = graph.addVertex(null);
						placetmp.setProperty("type","Location");
						placetmp.setProperty("address",address);
						if(place != null && !"".equals(place)){
							placetmp.setProperty("place",place);
						}
						graph.addEdge(null,perLive,placetmp,"own");
				}
						}
					
					if(hotelid != null && !"".equals(hotelid)){
						Vertex Hotel = graph.addVertex(null);
						Hotel.setProperty("type","StayEvent");
						if(hotelid!=null && !"".equals(hotelid)){
							Hotel.setProperty("hotelid",hotelid);
						}
						if(hotelname!=null && !"".equals(hotelname)){
							Hotel.setProperty("hotelname","如家 "+hotelname);
						}else{
							Hotel.setProperty("hotelname","如家酒店");
						}
						
						if(orderno!=null && !"".equals(orderno)){
							Hotel.setProperty("orderno",orderno);
						}
						if(arrivaldate!=null && !"".equals(arrivaldate)){
							Hotel.setProperty("arrivaldate",arrivaldate);
						}
						if(departuredate!=null && !"".equals(departuredate)){
							Hotel.setProperty("departuredate",departuredate);
						}
						if(roomno!=null && !"".equals(roomno)){
							Hotel.setProperty("roomno",roomno);
						}
						if(hotelflag!=null && !"".equals(hotelflag)){
							Hotel.setProperty("hotelflag",hotelflag);
						}
					    graph.addEdge(null,perLive,Hotel,"stay").setProperty("eventtime", arrivaldate);
						}
					
					
				}else{
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
					
					if(address!=null && !"".equals(address)){

							Vertex placetmp = graph.addVertex(null);
							placetmp.setProperty("type","Location");
							placetmp.setProperty("address",address);
							if(place != null && !"".equals(place)){
								placetmp.setProperty("place",place);
							}
							graph.addEdge(null,personNew,placetmp,"own");
							
						
						
					}
								
					if(hotelid != null && !"".equals(hotelid)){
						Vertex Hotel = graph.addVertex(null);
						Hotel.setProperty("type","StayEvent");
						if(hotelid!=null && !"".equals(hotelid)){
							Hotel.setProperty("hotelid",hotelid);
						}
						if(hotelname!=null && !"".equals(hotelname)){
							Hotel.setProperty("hotelname","如家 "+hotelname);
						}else{
							Hotel.setProperty("hotelname","如家酒店");
						}
						if(orderno!=null && !"".equals(orderno)){
							Hotel.setProperty("orderno",orderno);
						}
						if(arrivaldate!=null && !"".equals(arrivaldate)){
							Hotel.setProperty("arrivaldate",arrivaldate);
						}
						if(departuredate!=null && !"".equals(departuredate)){
							Hotel.setProperty("departuredate",departuredate);
						}
						if(roomno!=null && !"".equals(roomno)){
							Hotel.setProperty("roomno",roomno);
						}
						if(hotelflag!=null && !"".equals(hotelflag)){
							Hotel.setProperty("hotelflag",hotelflag);
						}
					    graph.addEdge(null,personNew,Hotel,"stay").setProperty("eventtime", arrivaldate);
						}
				}
			}
		}
							
			} catch (java.util.NoSuchElementException e) {
				System.out.println("查询异常");
				
			}
					}
		if(!isLive){
			
			if(idcard != null && !idcard.equals("")){
				Object pp= graph.query().has("idcard",idcard).vertices();
				if(((Iterable) pp).iterator().hasNext()){
					Vertex perLive  = (Vertex) ((Iterable) pp).iterator().next();
					
					Iterator<Vertex> s = perLive.query().labels(new String[]{"own"}).vertices().iterator();
					boolean loactionExist = false;
					while(s.hasNext()){
						Vertex v = s.next();
						if(address!=null && !"".equals(address)){
							String add  = (String)v.getProperty("address");
							if("Location".equals(v.getProperty("type")) && add.trim().equals(address.trim())){
								loactionExist=true;
							}
						}
					}
					if(!loactionExist){
						if(address!=null && !"".equals(address)){
						Vertex placetmp = graph.addVertex(null);
						placetmp.setProperty("type","Location");
						placetmp.setProperty("address",address);
						if(place != null && !"".equals(place)){
							placetmp.setProperty("place",place);
						}
						graph.addEdge(null,perLive,placetmp,"own");
				}
						}
					
					if(hotelid != null && !"".equals(hotelid)){
						Vertex Hotel = graph.addVertex(null);
						Hotel.setProperty("type","StayEvent");
						if(hotelid!=null && !"".equals(hotelid)){
							Hotel.setProperty("hotelid",hotelid);
						}
						if(hotelname!=null && !"".equals(hotelname)){
							Hotel.setProperty("hotelname","如家 "+hotelname);
						}else{
							Hotel.setProperty("hotelname","如家酒店");
						}
						
						if(orderno!=null && !"".equals(orderno)){
							Hotel.setProperty("orderno",orderno);
						}
						if(arrivaldate!=null && !"".equals(arrivaldate)){
							Hotel.setProperty("arrivaldate",arrivaldate);
						}
						if(departuredate!=null && !"".equals(departuredate)){
							Hotel.setProperty("departuredate",departuredate);
						}
						if(roomno!=null && !"".equals(roomno)){
							Hotel.setProperty("roomno",roomno);
						}
						if(hotelflag!=null && !"".equals(hotelflag)){
							Hotel.setProperty("hotelflag",hotelflag);
						}
					    graph.addEdge(null,perLive,Hotel,"stay").setProperty("eventtime", arrivaldate);
						}
					
					
				}else{
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
					
					if(address!=null && !"".equals(address)){

							Vertex placetmp = graph.addVertex(null);
							placetmp.setProperty("type","Location");
							placetmp.setProperty("address",address);
							if(place != null && !"".equals(place)){
								placetmp.setProperty("place",place);
							}
							graph.addEdge(null,personNew,placetmp,"own");
							
						
						
					}
								
					if(hotelid != null && !"".equals(hotelid)){
						Vertex Hotel = graph.addVertex(null);
						Hotel.setProperty("type","StayEvent");
						if(hotelid!=null && !"".equals(hotelid)){
							Hotel.setProperty("hotelid",hotelid);
						}
						if(hotelname!=null && !"".equals(hotelname)){
							Hotel.setProperty("hotelname","如家 "+hotelname);
						}else{
							Hotel.setProperty("hotelname","如家酒店");
						}
						if(orderno!=null && !"".equals(orderno)){
							Hotel.setProperty("orderno",orderno);
						}
						if(arrivaldate!=null && !"".equals(arrivaldate)){
							Hotel.setProperty("arrivaldate",arrivaldate);
						}
						if(departuredate!=null && !"".equals(departuredate)){
							Hotel.setProperty("departuredate",departuredate);
						}
						if(roomno!=null && !"".equals(roomno)){
							Hotel.setProperty("roomno",roomno);
						}
						if(hotelflag!=null && !"".equals(hotelflag)){
							Hotel.setProperty("hotelflag",hotelflag);
						}
					    graph.addEdge(null,personNew,Hotel,"stay").setProperty("eventtime", arrivaldate);
						}
				}
			}
		
		
		
		}
				}
				
		graph.commit();

	    graph.shutdown();
	}

}
