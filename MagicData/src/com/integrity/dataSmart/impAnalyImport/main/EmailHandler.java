package com.integrity.dataSmart.impAnalyImport.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.integrity.dataSmart.impAnalyImport.analyticalmail.AnalyticalEML;
import com.integrity.dataSmart.impAnalyImport.bean.DirectoryTree;
import com.integrity.dataSmart.impAnalyImport.bean.Email;
import com.integrity.dataSmart.impAnalyImport.email2solr.SolrAllUtils;
import com.integrity.dataSmart.impAnalyImport.hdfs.ProcessHDFS;
import com.integrity.dataSmart.impAnalyImport.util.WritelogContents;

/**
 * Email处理工具
 * @author cs
 *
 */
public class EmailHandler {
	public static String hdfsConfigPath =  EmailHandler.class.getClassLoader().getResource("../config/hadoopConfg/core-site.xml").toString();
	public static String dirTtrrType = "DirectoryTree";
	/**
	 * 解析eml，将附件存入本地
	 * @param emlLocalPath 本地eml路径
	 * @param attachfilepath 附件存入本地路径
	 * @return Email对象
	 */
	public static Email analyticalEML(String emlLocalPath,String attachfilepath,String emlPath){
		try {
			AnalyticalEML ae = new AnalyticalEML(emlLocalPath);
			Email email = ae.analyticaleml(attachfilepath,emlPath);
			email.setId(UUID.randomUUID().toString());
			email.setType("EmailEvent");
			return email;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return null;
	}
	
	/**
	 * 插入email路径
	 * @param localPath
	 */
	public static int insertDirTree(String localPath,String writePath){
		if (StringUtils.isNotBlank(localPath)) {
			System.err.println("目录树导入开始");
			System.out.println("目录树导入开始");
			com.integrity.dataSmart.impAnalyImport.util.WritelogContents.writeLogs(writePath,"目录树导入开始");
			try {
				File file = new File(localPath);
				localPath = file.getPath();
				File[] files = file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						recursionFileDir(file2, localPath, "root",writePath);
					}
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
				return -1;
			}
		}else {
			System.err.println("localPath路径为空");
			WritelogContents.writeLogs(writePath,"localPath路径为空");
		}
		return 0;
	}
	
	/**
	 * 插入email路径使用
	 */
	private static void recursionFileDir(File file,String localPath,String pid,String writePath){
		String path = file.getPath();
		System.out.println("进入目录："+path); 
		System.err.println("进入目录："+path);
		WritelogContents.writeLogs(writePath,"进入目录："+path);
		String dir = path.replace(localPath, "");
		dir = dir.replace("\\", "\\\\");
		DirectoryTree node = SolrAllUtils.getDirectoryTreeByName(dir);
		dir = dir.replace("\\\\", "\\");
		String uuid = UUID.randomUUID().toString();
		if (node == null) {
			node = new DirectoryTree();
			node.setId(uuid);
			node.setName(dir);
			node.setType(dirTtrrType);
			node.setPid(pid);
			SolrAllUtils.insertDirectoryTreeToSolr(node);
			System.out.println("存储目录："+dir);
			System.err.println("存储目录："+dir);
			WritelogContents.writeLogs(writePath,"存储目录："+dir);
		}else {
			uuid = node.getId();
		}
		File[] files = file.listFiles();
		for (File file2 : files) {
			if (file2.isDirectory()) {
				recursionFileDir(file2, localPath, uuid,writePath);
			}
		}
	}
	
	/**
	 * 更具messageid 查询邮件是否存在
	 * @param messageID
	 * @return 大于0存在
	 */
	public static int getEmails(String messageID){
		return SolrAllUtils.getEmails(messageID);
	}

	/**
	 * Email存储操作（solr 、hdfs）-- 多个Email
	 * 调用前必须将vertexid赋值
	 * @param list 
	 */
	public static void emailsHandler(List<Email> list){
		if (list != null && list.size()>0) {
			//存入solr
			SolrAllUtils.insertEmailsToSolr(list);
			//附件存入hdfs
			for (Email email : list) {
				if (email != null && StringUtils.isNotBlank(email.getVertexID())) {
					List<String> paths = email.getLocalPaths();
					if (paths != null && paths.size()>0) {
						try {
							ProcessHDFS.getInstance(hdfsConfigPath).sendFile(email);
							//上传完成后删除本地文件
							for (String path : paths) {
								File file = new File(path);
								if (file.exists()) {
									System.out.println("删除本地附件："+path);
									file.delete();
								}
							}
						} catch (IOException e) {
							e.printStackTrace(System.out);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Email存储操作（solr 、hdfs）--单个Email操作
	 * 调用前必须将vertexid赋值
	 * @param email 
	 * return -1异常    1正常
	 */
	public static int emailHandler(Email email){
		if (email != null) {
			//存入solr
			int i = SolrAllUtils.insertEmailToSolr(email);
			if (i==-1) {
				return -1;
			}
			//附件存入hdfs
			if (StringUtils.isNotBlank(email.getVertexID())) {
				List<String> paths = email.getLocalPaths();
				if (paths != null && paths.size()>0) {
					try {
						ProcessHDFS.getInstance(hdfsConfigPath).sendFile(email);
						//上传完成后删除本地文件
						for (String path : paths) {
							File file = new File(path);
							if (file.exists()) {
								file.delete();
							}
						}
					} catch (IOException e) {
						e.printStackTrace(System.out);
					}
				}
			}
			return 1;
		}
		return -1;
	}
	
	/**
	 * 初始化
	 */
	public static void init(){
		try {
			SolrAllUtils.connection();
			ProcessHDFS.getInstance(hdfsConfigPath);
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
	}
	
	/**
	 * 关闭
	 */
	public static void shutDown(){
		try {
			SolrAllUtils.close();
			ProcessHDFS.close();
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
	}
	
}
