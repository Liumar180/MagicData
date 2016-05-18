package com.integrity.dataSmart.impAnalyImport.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.BaseConfiguration;

import com.integrity.dataSmart.impAnalyImport.bean.DataType;
import com.integrity.dataSmart.impAnalyImport.bean.Email;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author liuBf
 * 通过文件夹解析，删除数据（TITAN）
 *
 */
public class deleteDataTitan {
	static Long counts = 0L;
	public static void main(String[] args) {

		BaseConfiguration baseConfiguration = new BaseConfiguration();
		baseConfiguration.setProperty("storage.backend", "hbase");
		baseConfiguration.setProperty("storage.hostname", "10.16.202.185");
		baseConfiguration.setProperty("storage.tablename", "titan");
		System.out.println("Open DataBase");
		TitanGraph graph = TitanFactory.open(baseConfiguration);
		EmailHandler.init();
		System.out.println("初始化完成");
		/********************** 数据入库 *******************************/
		long s = System.currentTimeMillis();
		String path = "D:\\邮件数据安全测评\\数据\\emlTrue\\aflcio.org_aknipper@aflcio.org";// eml路径
		//D:\\邮件数据安全测评\\数据\\emlTrue\\aflcio.org\\Exchange Jmcdonal
		try {
			File file = new File(path);
			String rootPath = file.getPath();
			if (file.isDirectory()) {
				File[] lst = file.listFiles();
				System.out.println("打开文件夹："+rootPath);
				for (File file2 : lst) {
					recursionFileDir(graph, file2, rootPath);
				}
			} else {
				System.err.println("error 解析路径有误，"+path+"是文件");
			}

		} catch (Exception e) {
			System.err.println("error 解析路径有误："+path);
			e.printStackTrace(System.out);
		}
		graph.shutdown();
		EmailHandler.shutDown();
		
		System.out.println("EmailHandler 关闭");
		long e = System.currentTimeMillis();
		System.out.println("删除用时："+((e-s)/1000)+"秒;共删除"+counts+"条数据");
	}
	/**
	 * 递归需导入的文件夹
	 * @param graph
	 * @param file 目录
	 * @param rootPath 本地eml根路径
	 * @param attachfilepath 附件存储路径
	 */
	private static void recursionFileDir(TitanGraph graph ,File file,String rootPath){
		if (file.isDirectory()) {
			System.out.println("进入文件夹："+file.getPath());
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					recursionFileDir(graph,file2, rootPath);
				}else{
					String emlpath = file2.getPath();
					String last = emlpath.substring(emlpath.length()-3);
					if(last.equals("eml")){
						System.out.println("删除开始："+emlpath);
					deleteEmail(graph, emlpath);
					}
				}
			}
		}else {
			String emlpath = file.getPath();
			String last = emlpath.substring(emlpath.length()-3);
			if(last.equals("eml")){
			System.out.println("删除开始："+emlpath);
			deleteEmail(graph, emlpath);
			}
		}
	}

	private static void deleteEmail(TitanGraph graph ,String emlpath){
		/***解析eml**/
		Email mail = EmailHandler.analyticalEML(emlpath, "D:\\nativeData","D:\\nativeData");
			String email = mail.getFromMail();//邮箱地址
			
			String from = mail.getFromMail();//发件人
			List<String> to  = new ArrayList<String>();
			List<String> cc  = new ArrayList<String>();
			if(mail.getToMails() != null){
				to = mail.getToMails();//收件人邮箱
			}
			if(mail.getCopyToMails() != null){
				cc = mail.getCopyToMails();//抄送人
			}
			
			
			System.out.println("发件："+from);
			System.out.println("收件人："+to);
			System.out.println("抄送人："+cc);
			
			to.add(from);
			to.addAll(cc);
			
			
		for(int i=0;i<to.size();i++){
			if(!to.get(i).equals("")){
				for(int j=0;j<5;j++){
			 Iterable<Vertex> G =  graph.getVertices("email",to.get(i));//1151694121@qq.com
			 if(G.iterator().hasNext()){
			 Vertex  karry = G.iterator().next();
			 Set<String> set = karry.getPropertyKeys();
			 for (String key : set) {
				System.out.println("key:"+key+"|value:"+karry.getProperty(key));
			}
			 if(karry.getProperty("type").equals("Email")){
			//关联节点和边删除
			 Iterator<Vertex> ite = karry.query().labels(DataType.ALLLABEL).vertices().iterator();
			 while (ite.hasNext()) {
				 graph.removeVertex(ite.next());
			}
			 Iterator<Edge> it3 = karry.query().labels(DataType.ALLLABEL).edges().iterator();
			 while (it3.hasNext()) {
				 graph.removeEdge(it3.next());
			}
			 karry.remove();
			 graph.commit();
			  }
		   }
			 System.out.println("删除------"+to.get(i));
			 }
				counts++; 
			}
		}
	}

}
