package com.integrity.dataSmart.impAnalyImport.hdfs;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.integrity.dataSmart.impAnalyImport.bean.Email;

public class ProcessHDFS {

	private static Configuration conf = null;
	private static String confpath = null;
	private static FileSystem localFS = null;
	private static FileSystem hadoopFS = null;
	private static ProcessHDFS instance;  
	

	/**
	public ProcessHDFS(String path) {
		conf = new Configuration();
		this.confpath = path;
		// conf.addResource(new Path("/hadoop/etc/hadoop/core-site.xml"));
		conf.addResource(new Path(path));
	}**/

	public ProcessHDFS() {
		
	}
	
	public static ProcessHDFS getInstance(String path) throws IOException{
		if(instance==null){
			conf = new Configuration();
			confpath = path;
			conf.addResource(new Path(path));
			localFS = FileSystem.getLocal(conf);
			hadoopFS = FileSystem.get(conf);
			instance = new ProcessHDFS();
		}
		return instance;
	}
	
	public ProcessHDFS(Configuration conf) {
		this.conf = conf;
	}

	public void open(String path) throws IOException {
		conf = new Configuration();
		this.confpath = path;
		conf.addResource(new Path(path));
		localFS = FileSystem.getLocal(conf);
		hadoopFS = FileSystem.get(conf);
	}

	public static void close() throws IOException {
		if(instance !=null){
			localFS.close();
			hadoopFS.close();
		}
		
	}

	/**
	 * 
	 * @param vertexId 节点ID
	 * @param localfile 文件本地路径
	 * @return
	 */
	public boolean sendFile(Email email) {
		
		List<String> attachfiles = email.getLocalPaths();
		String path = conf.get("fs.default.name");
		for (Iterator iterator = attachfiles.iterator(); iterator.hasNext();) {
			String localfile = (String) iterator.next();
			File file = new File(localfile);
			String fileName = file.getName();
			System.out.println("上传本地附件："+fileName);
			if (!file.isFile()) {
				System.out.println("上传附件不是文件："+file.getName());
				continue;
			}
			String tmppath  = path+"/attachfiles/"+email.getVertexID()+"/"+fileName;
			FSDataOutputStream fsOut = null;
			FSDataInputStream fsIn = null;
			try {
				fsOut = hadoopFS.create(new Path(tmppath));
				fsIn = localFS.open(new Path(localfile));
				byte[] buf = new byte[1024];
				int readbytes = 0;
				while ((readbytes = fsIn.read(buf)) > 0) {
					fsOut.write(buf, 0, readbytes);
				}
				
				fsOut.flush();
				System.out.println("本地附件上传完成："+fileName);
				/**
				 * FileStatus[] hadfiles= hadoopFS.listStatus(hadPath);
				 * for(FileStatus fs :hadfiles){ System.out.println(fs.toString());
				 * }
				 **/
			} catch (IOException e) {
				System.out.println("本地附件上传异常："+localfile);
				e.printStackTrace(System.out);
			}finally{
				if(fsOut!=null){
					try {
						fsOut.close();
					} catch (IOException e) {
						e.printStackTrace(System.out);
					}
				}
				if(fsIn!=null){
					try {
						fsIn.close();
					} catch (IOException e) {
						e.printStackTrace(System.out);
					}
				}
			}
		}
		
		return true;
	}

	public boolean delFile(String hadfile) {
		try {

			FileSystem hadoopFS = FileSystem.get(conf);
			Path hadPath = new Path(hadfile);
			Path p = hadPath.getParent();
			boolean rtnval = hadoopFS.delete(hadPath, true);

			FileStatus[] hadfiles = hadoopFS.listStatus(p);
			for (FileStatus fs : hadfiles) {
				System.out.println(fs.toString());
			}
			return rtnval;
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return false;
	}

	public boolean downloadFile(String hadfile, String localPath) {

		try {
			FileSystem localFS = FileSystem.getLocal(conf);
			FileSystem hadoopFS = FileSystem.get(conf);
			Path hadPath = new Path(hadfile);

			FSDataOutputStream fsOut = localFS.create(new Path(localPath + "/"
					+ hadPath.getName()));
			FSDataInputStream fsIn = hadoopFS.open(hadPath);
			byte[] buf = new byte[1024];
			int readbytes = 0;
			while ((readbytes = fsIn.read(buf)) > 0) {
				fsOut.write(buf, 0, readbytes);
			}
			fsIn.close();
			fsOut.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return false;
	}

}
