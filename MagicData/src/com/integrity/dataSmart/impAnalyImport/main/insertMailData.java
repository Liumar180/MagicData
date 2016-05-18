package com.integrity.dataSmart.impAnalyImport.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.configuration.BaseConfiguration;

import com.integrity.dataSmart.dataImport.dao.DataImpTaskDao;
import com.integrity.dataSmart.impAnalyImport.bean.Email;
import com.integrity.dataSmart.impAnalyImport.util.DateFormat;
import com.integrity.dataSmart.impAnalyImport.util.WritelogContents;
import com.integrity.dataSmart.util.titan.TitanGraphUtil;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class insertMailData {
	//public static String filePath = ConstantUtil.solrFilePath;
	static Long counts = 0L;
	static List<String> numfail = new ArrayList<String>();//存放每次导入数据时，解析失败的文件信息
	static List<String> numfails = new ArrayList<String>();//存放库中已经存在的文件
	static List<String> Abnormals = new ArrayList<String>();//存放录入异常文件
	static List<String> Overtimes = new ArrayList<String>();//存放录入超时文件
	static int maxTime = 300000;
	static int intervalTime = 1000;
	static int checkTimes = maxTime/intervalTime;
	static String writePath;
	static String total;//导入数据总数
	static boolean isHaveImp = false;
	static String timeout = null;//单位：分钟(超时时间)
	public static void main(String[] args) {
		String path = "";//"D:\\emlTrue";// eml路径
		String attachfilepath ="";//"D:\\nativeData";// 附件存储路径
		String taskId ="";
		if(args.length == 5){
			path = args[0];
			attachfilepath = args[1];
			writePath = args[2];
			taskId = args[3];
			total = args[4];
		}else {
			System.err.println(" 参数个数有误 ,return.");
			WritelogContents.writeLogs(writePath, " 参数个数有误 ,return.");
			return ;
		}
		InputStream inputStream = null;
		Properties p = new Properties();
		try {
			inputStream = insertMailData.class.getClassLoader().getResourceAsStream("../config/titan/titanConfig.properties");
			p.load(inputStream);
			timeout = p.getProperty("timeout.long");
		} catch (Exception e) {
			System.out.println("timeout init error ");
		}finally{
				try {
					if(inputStream!=null)
						inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		if(timeout != null){
			maxTime = Integer.parseInt(timeout)*60*1000;
			checkTimes = maxTime/intervalTime;
		}
		System.out.println("Open DataBase");
		TitanGraph graph = null;
		try {
			graph = TitanGraphUtil.getInstance().getTitanGraph();
		} catch (Exception e2) {
			e2.printStackTrace(System.out);
			System.err.println(" titan open error ,return.");
			WritelogContents.writeLogs(writePath, " titan open error ,return.");
			return ;
		}
		if (graph == null) {
			System.err.println(" titan open error ,return.");
			WritelogContents.writeLogs(writePath, " titan open error ,return.");
			return ;
		}
		EmailHandler.init();
		System.out.println("初始化完成");
		/********************** 数据入库 *******************************/
		long s = System.currentTimeMillis();
		String startTime= DateFormat.transferLongToDate(s);
		WritelogContents.writeLogs(writePath,"入库开始时间："+startTime);
		try {
			File file = new File(path);
			String rootPath = file.getPath();
			if (file.isDirectory()) {
				try {
					int i = EmailHandler.insertDirTree(rootPath,writePath);//先导入目录树
					if (i==-1) {
						System.out.println("目录树导入异常！结束");
						return;
					}
				} catch (Exception e1) {
					e1.printStackTrace(System.out);
					System.out.println("目录树导入异常！结束");
					return;
				}
				System.out.println("目录树导入完成："+rootPath);
				System.err.println("目录树导入完成："+rootPath);
				WritelogContents.writeLogs(writePath,"目录树导入完成："+rootPath);
				File[] lst = file.listFiles();
				System.out.println("打开文件夹："+rootPath);
				System.err.println("打开文件夹："+rootPath);
				WritelogContents.writeLogs(writePath,"打开文件夹："+rootPath);
				for (File file2 : lst) {
					//导入eml
					recursionFileDir(graph, file2, rootPath, attachfilepath,Integer.valueOf(taskId));
				}
			} else {
				System.err.println("error 导入路径有误:"+path+"参数不匹配或无参数");
				WritelogContents.writeLogs(writePath,"error 导入路径有误:"+path+"参数不匹配或无参数");
			}

		} catch (Exception e) {
			System.err.println("error 导入时错误："+e.getMessage());
			WritelogContents.writeLogs(writePath,"error 导入时错误："+e.getMessage());
			e.printStackTrace(System.out);
		}
		graph.shutdown();
		System.out.println("Titan Close...");
		EmailHandler.shutDown();
		
		System.out.println("EmailHandler Close...");
		long e = System.currentTimeMillis();
		String endTime= DateFormat.transferLongToDate(e);
		WritelogContents.writeLogs(writePath,"入库结束时间："+endTime);
		System.out.println("导入完成！总用时："+((e-s)/1000)+"秒；入库数据量："+counts+"条");
		System.err.println("导入完成！总用时："+((e-s)/1000)+"秒；入库数据量："+counts+"条");
		WritelogContents.writeLogs(writePath,"<-------------------------------------------------->");
		WritelogContents.writeLogs(writePath,"导入完成！总用时："+((e-s)/1000)+"秒；入库数据量："+counts+"条;因解析失败跳过的文件数量为:"+numfail.size()+" 个;因库中存在跳过的文件数量为:"+numfails.size()+" 个;因解析eml录入时异常的文件数量为:"+Abnormals.size()+" 个;因eml导入超时的文件数量为:"+Overtimes.size()+"个;");
		WritelogContents.writeLogs(writePath,"具体详情如下：");
		WritelogContents.writeLogs(writePath,"因解析失败跳过的文件数量为:"+numfail.size()+" 个");
		for(int i=0;i<numfail.size();i++){
			WritelogContents.writeLogs(writePath,numfail.get(i));
		}
		WritelogContents.writeLogs(writePath,"因库中存在跳过的文件数量为:"+numfails.size()+" 个");
		for(int i=0;i<numfails.size();i++){
			WritelogContents.writeLogs(writePath,numfails.get(i));
		}
		WritelogContents.writeLogs(writePath,"因解析eml录入时异常的文件数量为:"+Abnormals.size()+" 个");
		for(int i=0;i<Abnormals.size();i++){
			WritelogContents.writeLogs(writePath,Abnormals.get(i));
		}
		WritelogContents.writeLogs(writePath,"因eml导入超时的文件数量为:"+Overtimes.size()+" 个");
		for(int i=0;i<Overtimes.size();i++){
			WritelogContents.writeLogs(writePath,Overtimes.get(i));
		}
		
		WritelogContents.writeLogs(writePath,"<-------------------------------------------------->");
	}
	
	/**
	 * 递归需导入的文件夹
	 * @param graph
	 * @param file 目录
	 * @param rootPath 本地eml根路径
	 * @param attachfilepath 附件存储路径
	 */
	private static void recursionFileDir(
			TitanGraph graph ,File file,String rootPath,String attachfilepath,Integer taskId){
		Long number = 0L;
		if (file.isDirectory()) {
			System.out.println("打开文件夹："+file.getPath());
			System.err.println("打开文件夹："+file.getPath());
			WritelogContents.writeLogs(writePath,"打开文件夹："+file.getPath());
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					recursionFileDir(graph,file2, rootPath,attachfilepath,taskId);
				}else{
					String emlpath = file2.getPath();
					String last = emlpath.substring(emlpath.length()-3);
					if(last.equals("eml")){
						String ppath = file2.getParent();
						System.out.println("解析导入eml开始："+emlpath);
						System.err.println("解析导入eml开始："+emlpath);
						WritelogContents.writeLogs(writePath,"解析导入eml开始："+emlpath);
						String dir = ppath.replace(rootPath, "");
						try {
							ExecutorService ex = Executors.newFixedThreadPool(1);
							Future future = ex.submit(new MyCallable(graph, emlpath, attachfilepath, dir));
							for(int i=0;i<checkTimes;i++){
								Thread.sleep(intervalTime);
								if(future.isDone()){
									ex.shutdownNow();
									number = number + 1;
									break;
								}
								if(i==(checkTimes-1)&&!future.isDone()){
									ex.shutdownNow();
									System.out.println("该eml导入超时："+emlpath);
									System.err.println("该eml导入超时："+emlpath);
									Overtimes.add("eml导入超时的文件："+emlpath);
									WritelogContents.writeLogs(writePath,"该eml导入超时："+emlpath);
								}
							}
//							insertEmail(graph, emlpath, attachfilepath, dir);
//							number = number + 1;
						} catch (Exception e) {
							System.out.println("该eml导入异常："+emlpath);
							System.err.println("该eml导入异常："+emlpath);
							Abnormals.add("解析eml时导入异常的文件："+emlpath);
							WritelogContents.writeLogs(writePath,"该eml导入异常："+emlpath);
							e.printStackTrace(System.out);
						}
					}else{
						System.out.println("该路径："+emlpath+"下不是eml文件");
						System.err.println("该路径："+emlpath+"下不是eml文件");
						WritelogContents.writeLogs(writePath,"该路径："+emlpath+"下不是eml文件");
					}
				}
			}
		}else {
			String emlpath = file.getPath();
			String last = emlpath.substring(emlpath.length()-3);
			if(last.equals("eml")){
				String ppath = file.getParent();
				System.out.println("解析导入eml开始："+emlpath);
				System.err.println("解析导入eml开始："+emlpath);
				WritelogContents.writeLogs(writePath,"解析导入eml开始："+emlpath);
				String dir = ppath.replace(rootPath, "");
				try {
					ExecutorService ex = Executors.newFixedThreadPool(1);
					Future future = ex.submit(new MyCallable(graph, emlpath, attachfilepath, dir));
					for(int i=0;i<checkTimes;i++){
						Thread.sleep(intervalTime);
						if(future.isDone()){
							ex.shutdownNow();
							number = number + 1;
							break;
						}
						if(i==(checkTimes-1)&&!future.isDone()){
							ex.shutdownNow();
							System.out.println("该eml导入超时："+emlpath);
							System.err.println("该eml导入超时："+emlpath);
							Overtimes.add("eml导入超时的文件："+emlpath);
							WritelogContents.writeLogs(writePath,"该eml导入超时："+emlpath);
						}
					}
//					insertEmail(graph, emlpath, attachfilepath, dir);
//					number = number + 1;
				} catch (Exception e) {
					System.out.println("该eml导入异常："+emlpath);
					System.err.println("该eml导入异常："+emlpath);
					Abnormals.add("解析eml时导入异常的文件："+emlpath);
					WritelogContents.writeLogs(writePath,"该eml导入异常："+emlpath);
					e.printStackTrace(System.out);
				}
			}else{
				System.out.println("该路径："+emlpath+"下不是eml文件");
				System.err.println("该路径："+emlpath+"下不是eml文件");
				WritelogContents.writeLogs(writePath,"该路径："+emlpath+"下不是eml文件");
			}
		}
		if(number == 0L&&isHaveImp){
	    	 DataImpTaskDao dao = new DataImpTaskDao();
	    	 dao.toRefreshData(taskId,Long.valueOf(total));
		}else if(number%100==0){
         DataImpTaskDao dao = new DataImpTaskDao();
         dao.toRefreshData(taskId,number);
        }else{
    	 DataImpTaskDao dao = new DataImpTaskDao();
    	 dao.toRefreshData(taskId,counts);
       }
	}
	
	public static class  MyCallable  implements Callable{
		
		private TitanGraph graph = null;
		private String emlpath = "";
		private String attachfilepath = "";
		private String dir = "";

		public MyCallable(TitanGraph graph, String emlpath,
				String attachfilepath, String dir) {
			this.graph = graph;
			this.emlpath = emlpath;
			this.attachfilepath = attachfilepath;
			this.dir = dir;
		}

		@Override
		public Object call() throws Exception {
			insertEmail(graph, emlpath, attachfilepath, dir);
			return null;
		}
		
	}
	
	/**
	 * 导入eml
	 * @param graph titan连接
	 * @param emlpath eml绝对路径
	 * @param attachfilepath 附件存储路径
	 * @param dir eml自身目录
	 */
	private static void insertEmail(TitanGraph graph ,String emlpath,String attachfilepath,String dir){
		long start = System.currentTimeMillis();
		Email mail = EmailHandler.analyticalEML(emlpath, attachfilepath,dir);
		
		if (mail == null) {
			System.out.println("该eml解析失败!-跳过："+emlpath+"此eml文件");
			System.err.println("该eml解析失败!-跳过："+emlpath+"此eml文件");
			numfail.add("因解析失败跳过的文件："+emlpath);
			WritelogContents.writeLogs(writePath,"该eml解析失败!-跳过："+emlpath+"此eml文件");
			return;
		}
	    String password = "";//密码
	    String email="";
	    String title="";
	    String content="";
	    String from = "";
	    List to = new ArrayList<String>();
	    List cc = new ArrayList<String>();
	    if(mail != null){
	    	
	    	email = mail.getFromMail();
	    	
	    	title = mail.getTitle();//主题
	    	
	    	content = mail.getContent();//正文内容
	    	
	    	from = mail.getFromMail();//发件人
	    	if(mail.getToMails()!=null){
	    		to = mail.getToMails();//收件人
	    	}
	    	if(mail.getCopyToMails()!=null){
	    		cc = mail.getCopyToMails();//抄送人
	    	}
	    	
	    }
		
		List toAndcc = new ArrayList<String>();
		toAndcc.addAll(to);
		toAndcc.addAll(cc);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        
        //发送时间
        Long time = 0L;
      	Long eventtime = 0L;//事件时间
		try {
			eventtime = simpleDateFormat.parse(mail.getSendtime()).getTime();
			time = eventtime;
		} catch (ParseException e1) {
			e1.printStackTrace(System.out);
		}
		
		//解析完成后根据messageid判断数据是否存在
		String messageId = mail.getMessageID();
		int stat = EmailHandler.getEmails(messageId);
		if(stat < 1){
		
			if((null!=email) && (!email.equals(""))){
				Object EmailIte = null;
						if(email != null && !"".equals(email)){
							EmailIte =graph.query().has("email",email).vertices();
						}
						Vertex tmp = null;
						boolean t =  false;
						if(EmailIte != null){t = ((Iterable) EmailIte).iterator().hasNext();}
				 while(t){
					tmp = (Vertex)((Iterable)EmailIte).iterator().next();
					Set s = tmp.getPropertyKeys();
	           if(s != null){
					for(int i=0;i<s.size();i++){
						if(tmp.getProperty("type").toString().equals("Email")){
							break;
						}
					}
	            }
					break;

				}
				
				if(tmp != null){
					Vertex person =null;
					try{
					/**存在人物对象**/
					person = (Vertex)tmp.query().labels(new String[]{"own"}).vertices().iterator().next();
					}catch(java.util.NoSuchElementException e){
					e.printStackTrace(System.out);
					
					}
					if(person !=null){
						Object childs = person.query().labels(new String[]{"own"}).vertices();

						boolean emailEvexist = false;
						for (Iterator iterator = ((Iterable)childs).iterator(); iterator.hasNext();) {
							Vertex v = (Vertex) iterator.next();
							
							if(v != null){
							if("Email".equals(v.getProperty("type"))){
	                        if(v.getProperty("email").equals(email)){
	                        	//如果邮箱相同，对邮箱信息进行更新
	                        	emailEvexist = true;
								}
							}
	            
						}
					if(!emailEvexist){//邮箱新增（存在人）
						Vertex emailEveve = null;//全局邮件时间对象
							if(email!=null && !"".equals(email)){
								Vertex emailtmp = graph.addVertex(null);
								emailtmp.setProperty("type","Email");
								emailtmp.setProperty("email",email);
								if(password != null && !"".equals(password)){
	                            emailtmp.setProperty("password", password);
	                            }
								graph.addEdge(null,person,emailtmp,"own");
							}	
							//邮件事件处理
							//邮件事件(to)
							
							if(to != null && to.size() != 0 && !to.get(0).equals("")){
							for(int i=0;i< to.size();i++){
							boolean fromto = false;
							Iterable personto = graph.query().has("email",to.get(i)).vertices();
							if(personto.iterator().hasNext()){
							Vertex pto = (Vertex) personto.iterator().next();
								
							for (Iterator iterator1 = ((Iterable)personto).iterator(); iterator1.hasNext();) {
								Vertex v1 = (Vertex) iterator1.next();
								if(v1 != null){
								if("Email".equals(v1.getProperty("type"))){
		                        if(v1.getProperty("email").equals(to.get(i))){
		                        	//如果相等说明 收件人是存在的
		                        	fromto = true;
									}
								}
		                  }
								Object perto = null;
							if(fromto){
								perto = pto.query().labels(new String[]{"own"}).vertices().iterator().next();
								if(perto != null){
									if(emailEveve == null){
										emailEveve = graph.addVertex(null);
										 emailEveve.setProperty("type", "EmailEvent");
										 if(content != null && !content.equals("")){
											 emailEveve.setProperty("content", content);
										 }
										 if(title != null && !title.equals("")){
											 emailEveve.setProperty("title", title);
										 }
										 emailEveve.setProperty("from", from);
										 emailEveve.setProperty("to", toAndcc);
										 emailEveve.setProperty("time", time);
										 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
										graph.addEdge(null, person, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
										graph.addEdge(null, emailEveve, (Vertex) perto, "emailto").setProperty("eventtime", eventtime);
									}else{
										graph.addEdge(null, emailEveve, (Vertex) perto, "emailto").setProperty("eventtime", eventtime);
									}
								
							} 
							}else{
									  Vertex p = graph.addVertex(null);
									  p.setProperty("type", "Person");
									  
									  Vertex em  = graph.addVertex(null);
									  em.setProperty("type", "Email");
									  em.setProperty("email", to.get(i));
									  graph.addEdge(null, p, em, "own");
									  if(emailEveve == null){
										  emailEveve = graph.addVertex(null);
										  emailEveve.setProperty("type", "EmailEvent");
											 if(content != null && !content.equals("")){
												 emailEveve.setProperty("content", content);
											 }
											 if(title != null && !title.equals("")){
												 emailEveve.setProperty("title", title);
											 }
											 emailEveve.setProperty("from", from);
											 emailEveve.setProperty("to", toAndcc);
											 emailEveve.setProperty("time", time);
											 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
											graph.addEdge(null, person, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
											graph.addEdge(null, emailEveve, p, "emailto").setProperty("eventtime", eventtime);
									  }else{
											graph.addEdge(null, emailEveve, p, "emailto").setProperty("eventtime", eventtime);
									  }
										 
									  
								  }
								}
							}
							}
							}
							//邮件事件(cc)
		                    if(cc != null && cc.size() !=0 && !cc.get(0).equals("")){
		                    for(int i=0;i< cc.size();i++){
		                    Vertex pcc = null;
		                    boolean fromcc = false;
							Iterable personcc = graph.query().has("email",cc.get(i)).vertices();
							if(personcc.iterator().hasNext()){
							pcc = (Vertex) personcc.iterator().next();
							for (Iterator iterator1 = ((Iterable)personcc).iterator(); iterator1.hasNext();) {
								Vertex v1 = (Vertex) iterator1.next();
								if(v1 != null){
								if("Email".equals(v1.getProperty("type"))){
		                        if(v1.getProperty("email").equals(cc.get(i))){
		                        	fromcc = true;
									}
								}
		                  }
								Object percc = null;
							if(fromcc){
								percc = pcc.query().labels(new String[]{"own"}).vertices().iterator().next();
								
								if(percc != null){
									if(emailEveve == null){
										emailEveve = graph.addVertex(null);
										 emailEveve.setProperty("type", "EmailEvent");
										 if(content != null && !content.equals("")){
											 emailEveve.setProperty("content", content);
										 }
										 if(title != null && !title.equals("")){
											 emailEveve.setProperty("title", title);
										 }
										 emailEveve.setProperty("from", from);
										 emailEveve.setProperty("to", toAndcc);
										 emailEveve.setProperty("time", time);
										 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
										graph.addEdge(null, person, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
										graph.addEdge(null, emailEveve, (Vertex) percc, "emailcc").setProperty("eventtime", eventtime);
									}else{
										graph.addEdge(null, emailEveve, (Vertex) percc, "emailcc").setProperty("eventtime", eventtime);
									}
								
								}
								  }else{
									  Vertex p = graph.addVertex(null);
									  p.setProperty("type", "Person");
									  
									  Vertex em  = graph.addVertex(null);
									  em.setProperty("type", "Email");
									  em.setProperty("email", cc.get(i));
									  graph.addEdge(null, p, em, "own");
									  if(emailEveve ==null){
										  emailEveve = graph.addVertex(null);
										  emailEveve.setProperty("type", "EmailEvent");
											 if(content != null && !content.equals("")){
												 emailEveve.setProperty("content", content);
											 }
											 if(title != null && !title.equals("")){
												 emailEveve.setProperty("title", title);
											 }
											 emailEveve.setProperty("from", from);
											 emailEveve.setProperty("to", toAndcc);
											 emailEveve.setProperty("time", time);
											 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
											graph.addEdge(null, person, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
											graph.addEdge(null, emailEveve, p, "emailcc").setProperty("eventtime", eventtime);
									  }else{
											graph.addEdge(null, emailEveve, p, "emailcc").setProperty("eventtime", eventtime);
									  }
										
										
										  
								   }
								  }
		                        }
		                      }
		                    }
							
						}else{
							Vertex emailEveve = null;//更新（存在人）
							if(to != null && to.size() !=0 && !to.get(0).equals("")){
								
							for(int i=0;i<to.size();i++){
							Vertex pto = null;	
							Iterable personto = graph.query().has("email",to.get(i)).vertices();
							
							boolean eventtoExist = false;
							if(personto.iterator().hasNext()){
							pto = (Vertex) personto.iterator().next();
							
							for (Iterator iterator1 = personto.iterator(); iterator1.hasNext();) {
								Vertex v1 = (Vertex) iterator1.next();
	                            if(to.get(i) != null){
	                               if(to.get(i).equals(v1.getProperty("email"))){
	                            	   eventtoExist=true;
								               }
	                                      }
							  }
							}
							Object perto = null;
							if(eventtoExist){
							if(to.get(i)!=null && !"".equals(to.get(i))){
								perto = pto.query().labels(new String[]{"own"}).vertices().iterator().next();
								if(perto != null){
									if(emailEveve == null){
										emailEveve = graph.addVertex(null);
										emailEveve.setProperty("type","EmailEvent");
										if(content != null && !content.equals("")){
											emailEveve.setProperty("content", content);
										 }
										 if(title != null && !title.equals("")){
											 emailEveve.setProperty("title", title);
										 }
										 emailEveve.setProperty("from", from);
										 emailEveve.setProperty("to", toAndcc);
										 emailEveve.setProperty("time", time);
										 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
										graph.addEdge(null, person, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
										graph.addEdge(null, emailEveve, (Vertex) perto, "emailto").setProperty("eventtime", eventtime);
									}else{
										graph.addEdge(null, emailEveve, (Vertex) perto, "emailto").setProperty("eventtime", eventtime);
									}
								
								}
								}
							}else{
								Vertex p = graph.addVertex(null);
								p.setProperty("type", "Person");
								
								Vertex em  = graph.addVertex(null);
								  em.setProperty("type", "Email");
								  em.setProperty("email", to.get(i));
								  graph.addEdge(null, p, em, "own");
								  
								if(to.get(i)!=null && !"".equals(to.get(i))){
									if(emailEveve == null){
										emailEveve = graph.addVertex(null);
										emailEveve.setProperty("type","EmailEvent");
										if(content != null && !content.equals("")){
											emailEveve.setProperty("content", content);
										 }
										 if(title != null && !title.equals("")){
											 emailEveve.setProperty("title", title);
										 }
										 emailEveve.setProperty("from", from);
										 emailEveve.setProperty("to", toAndcc);
										 emailEveve.setProperty("time", time);
										 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
										graph.addEdge(null, person, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
										graph.addEdge(null, emailEveve, p, "emailto").setProperty("eventtime", eventtime);
									}else{
										graph.addEdge(null, emailEveve, p, "emailto").setProperty("eventtime", eventtime);
									}
									
									}
								
							}
							
							 }
							}
							
							
                            if(cc != null && cc.size() != 0 && !cc.get(0).equals("")){
                            for(int i=0;i<cc.size();i++){
                            	Vertex pcc = null;
							Iterable personcc = graph.query().has("email",cc.get(i)).vertices();
							if(personcc.iterator().hasNext()){
								pcc = (Vertex) personcc.iterator().next();
							}
							
							boolean eventccExist = false;
							for (Iterator iterator1 = personcc.iterator(); iterator1.hasNext();) {
								Vertex v1 = (Vertex) iterator1.next();
	                            if(cc.get(i) != null){
	                               if(cc.get(i).equals(v1.getProperty("email"))){
	                            	   eventccExist=true;
								               }
	                                      }
								

							}
							 
							if(eventccExist){
								Object  percc = null;
							if(cc.get(i)!=null && !"".equals(cc.get(i))){
								percc = pcc.query().labels(new String[]{"own"}).vertices().iterator().next();
								 
								if(percc != null){
									if(emailEveve == null){
										emailEveve = graph.addVertex(null);
										emailEveve.setProperty("type","EmailEvent");
										if(content != null && !content.equals("")){
											emailEveve.setProperty("content", content);
										 }
										 if(title != null && !title.equals("")){
											 emailEveve.setProperty("title", title);
										 }
										 emailEveve.setProperty("from", from);
										 emailEveve.setProperty("to", toAndcc);
										 emailEveve.setProperty("time", time);
										 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
										graph.addEdge(null, person, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
										graph.addEdge(null, emailEveve, (Vertex) percc, "emailcc").setProperty("eventtime", eventtime);
									}else{
										graph.addEdge(null, emailEveve, (Vertex) percc, "emailcc").setProperty("eventtime", eventtime);
									}
								
								}
								}
							}else{
								Vertex p = graph.addVertex(null);
								p.setProperty("type", "Person");
								
								Vertex em  = graph.addVertex(null);
								  em.setProperty("type", "Email");
								  em.setProperty("email", cc.get(i));
								  graph.addEdge(null, p, em, "own");
								
								if(cc.get(i)!=null && !"".equals(cc.get(i))){
									if(emailEveve == null){
										emailEveve = graph.addVertex(null);
										emailEveve.setProperty("type","EmailEvent");
										if(content != null && !content.equals("")){
											emailEveve.setProperty("content", content);
										 }
										 if(title != null && !title.equals("")){
											 emailEveve.setProperty("title", title);
										 }
										 emailEveve.setProperty("from", from);
										 emailEveve.setProperty("to", toAndcc);
										 emailEveve.setProperty("time", time);
										 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
										graph.addEdge(null, person, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
										graph.addEdge(null, emailEveve, p, "emailcc").setProperty("eventtime", eventtime);
									}else{
										graph.addEdge(null, emailEveve, p, "emailcc").setProperty("eventtime", eventtime);
									}
									
									
									}
								
								
								
							}
                            }
						}
						
						}
							
						}
					}
					
				}else{
					Vertex emailEveve = null;//全局邮件时间对象
					/*********新加邮箱数据信息*********/
					Vertex personNew = graph.addVertex(null);//人物对象
					personNew.setProperty("type","Person");
					//邮件
					Vertex emailtmp = graph.addVertex(null);
					emailtmp.setProperty("type","Email");
	                if(email != null && !email.equals("")){
	                emailtmp.setProperty("email",email);
	                }
	                if(password != null && !password.equals("")){
	                emailtmp.setProperty("password", password);
	                }
					graph.addEdge(null,personNew,emailtmp,"own");
					//邮件事件(to)
					if(to != null && to.size() !=0 && !to.get(0).equals("")){
					for(int i=0;i<to.size();i++){
					Iterable personto = graph.query().has("email",to.get(i)).vertices();
					
					Vertex tmpto = null;
					boolean tto =  false;
						if(personto != null){tto = personto.iterator().hasNext();}
				 while(tto){
					 tmpto = (Vertex) personto.iterator().next();
					Set s = tmpto.getPropertyKeys();
	           if(s != null){
					for(int j=0;j<s.size();j++){
						if(tmpto.getProperty("type").toString().equals("Email")){
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
						if(emailEveve == null){
							emailEveve = graph.addVertex(null);
							 emailEveve.setProperty("type", "EmailEvent");
							 if(content != null && !content.equals("")){
								 emailEveve.setProperty("content", content);
							 }
							 if(title != null && !title.equals("")){
								 emailEveve.setProperty("title", title);
							 }
							 emailEveve.setProperty("from", from);
							 emailEveve.setProperty("to", toAndcc);
							 emailEveve.setProperty("time", time);
							 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
							graph.addEdge(null, personNew, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
							graph.addEdge(null, emailEveve, per, "emailto").setProperty("eventtime", eventtime);
							
						}else{
							graph.addEdge(null, emailEveve, per, "emailto").setProperty("eventtime", eventtime);
						}
						 
						}
						}else{
							  Vertex p = graph.addVertex(null);
							  p.setProperty("type", "Person");
							  
							  Vertex em  = graph.addVertex(null);
							  em.setProperty("type", "Email");
							  em.setProperty("email", to.get(i));
							  graph.addEdge(null, p, em, "own");
							  if(emailEveve == null){
								  emailEveve = graph.addVertex(null);
								  emailEveve.setProperty("type", "EmailEvent");
									 if(content != null && !content.equals("")){
										 emailEveve.setProperty("content", content);
									 }
									 if(title != null && !title.equals("")){
										 emailEveve.setProperty("title", title);
									 }
									 emailEveve.setProperty("from", from);
									 emailEveve.setProperty("to", toAndcc);
									 emailEveve.setProperty("time", time);
									 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
									graph.addEdge(null, personNew, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
									graph.addEdge(null, emailEveve, p, "emailto").setProperty("eventtime", eventtime);
							  }else{
									graph.addEdge(null, emailEveve, p, "emailto").setProperty("eventtime", eventtime);
							  }
								 
						}
					}
					}
					
					//邮件事件(cc)
					if(cc != null && cc.size() !=0 && !cc.get(0).equals("")){
					for(int i=0;i<cc.size();i++){
					Iterable personcc = graph.query().has("email",cc.get(i)).vertices();
					/****/
					Vertex tmpcc = null;
					boolean tcc =  false;
						if(personcc != null){tcc = personcc.iterator().hasNext();}
				 while(tcc){
					 tmpcc = (Vertex) personcc.iterator().next();
					Set s = tmpcc.getPropertyKeys();
	           if(s != null){
					for(int j=0;j<s.size();j++){
						if(tmpcc.getProperty("type").toString().equals("Email")){
							break;
						}
					}
	            }
					break;

				}
				 Vertex perc = null;
						/****/
					if(tmpcc != null){
						perc = (Vertex)tmpcc.query().labels(new String[]{"own"}).vertices().iterator().next();
						if(perc != null){
							if(emailEveve == null){
								emailEveve = graph.addVertex(null);
								 emailEveve.setProperty("type", "EmailEvent");
								 if(content != null && !content.equals("")){
									 emailEveve.setProperty("content", content);
								 }
								 if(title != null && !title.equals("")){
									 emailEveve.setProperty("title", title);
								 }
								 emailEveve.setProperty("from", from);
								 emailEveve.setProperty("to", toAndcc);
								 emailEveve.setProperty("time", time);
								 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
								graph.addEdge(null, personNew, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
								graph.addEdge(null, emailEveve, perc, "emailcc").setProperty("eventtime", eventtime);
							}else{
								graph.addEdge(null, emailEveve, perc, "emailcc").setProperty("eventtime", eventtime);
							}
						 
						}
						}else{

							Vertex p = graph.addVertex(null);
							p.setProperty("type", "Person");
							
							Vertex em  = graph.addVertex(null);
							  em.setProperty("type", "Email");
							  em.setProperty("email", cc.get(i));
							  graph.addEdge(null, p, em, "own");
							if(emailEveve == null){
								emailEveve = graph.addVertex(null);
								emailEveve.setProperty("type", "EmailEvent");
								 if(content != null && !content.equals("")){
									 emailEveve.setProperty("content", content);
								 }
								 if(title != null && !title.equals("")){
									 emailEveve.setProperty("title", title);
								 }
								 emailEveve.setProperty("from", from);
								 emailEveve.setProperty("to", toAndcc);
								 emailEveve.setProperty("time", time);
								 mail.setVertexID(emailEveve.getId().toString());//设置VertexID值
								graph.addEdge(null, personNew, emailEveve, "emailfrom").setProperty("eventtime", eventtime);
								graph.addEdge(null, emailEveve, p, "emailcc").setProperty("eventtime", eventtime);
							}else{
								graph.addEdge(null, emailEveve, p, "emailcc").setProperty("eventtime", eventtime);
							}
							 
					  
						}
					}
				}
					
				}
		          graph.commit();
		          System.out.println("mail Titan ok..");
		          System.err.println("mail Titan ok..");
		          WritelogContents.writeLogs(writePath,"mail Titan ok..");
		          /******数据提交后 将mail对象存入solr********/
		        int type = EmailHandler.emailHandler(mail);
		        if(type == 1){
		        	counts++;
		        	System.out.println("mail solr ok.. |id:"+mail.getId()+"|vertexid:"+mail.getVertexID());
		        	System.err.println("mail solr ok.. |id:"+mail.getId()+"|vertexid:"+mail.getVertexID());
		        	WritelogContents.writeLogs(writePath,"mail solr ok.. |id:"+mail.getId()+"|vertexid:"+mail.getVertexID());
		        	long e = System.currentTimeMillis();
		        	System.out.println("用时："+(e-start)+"毫秒");
		        	System.err.println("用时："+(e-start)+"毫秒");
		        	WritelogContents.writeLogs(writePath,"用时："+(e-start)+"毫秒");
		        }else{
		        	System.out.println("mail solr Failed.. |id:"+mail.getId()+"|vertexid:"+mail.getVertexID());
		        	System.err.println("mail solr Failed.. |id:"+mail.getId()+"|vertexid:"+mail.getVertexID());
		        	WritelogContents.writeLogs(writePath,"mail solr Failed.. |id:"+mail.getId()+"|vertexid:"+mail.getVertexID());
		        }
		      
			}
		}else{
			isHaveImp = true;
			System.out.println("该eml库中存在!messageId 为:"+messageId+"-跳过此eml文件:"+emlpath);
			System.err.println("该eml库中存在!messageId 为:"+messageId+"-跳过此eml文件:"+emlpath);
			numfails.add("因库中存在而跳过的文件："+emlpath);
			WritelogContents.writeLogs(writePath,"该eml库中存在!messageId 为:"+messageId+"-跳过此eml文件:"+emlpath);
		}
	}
	
}

