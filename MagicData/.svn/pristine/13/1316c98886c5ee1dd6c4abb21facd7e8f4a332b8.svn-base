package com.integrity.dataSmart.dataImport.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.integrity.dataSmart.impAnalyImport.bean.Email;

public class CsvProcessHDFS {

	private static Configuration conf = null;
	private static String confpath = null;
	private static FileSystem localFS = null;
	private static FileSystem hadoopFS = null;
	private static CsvProcessHDFS instance;  
	

	/**
	public ProcessHDFS(String path) {
		conf = new Configuration();
		this.confpath = path;
		// conf.addResource(new Path("/hadoop/etc/hadoop/core-site.xml"));
		conf.addResource(new Path(path));
	}**/

	public CsvProcessHDFS() {
		
	}
	
	public static CsvProcessHDFS getInstance(String path) throws IOException{
		if(instance==null){
			conf = new Configuration();
			confpath = path;
			conf.addResource(new Path(path));
			localFS = FileSystem.getLocal(conf);
			hadoopFS = FileSystem.get(conf);
			instance = new CsvProcessHDFS();
		}
		return instance;
	}
	
	public CsvProcessHDFS(Configuration conf) {
		this.conf = conf;
	}
	
	public Configuration getConfig(){
		return conf;
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
	
	public String gethadPath(){
		return conf.get("fs.default.name");
	}

	/**
	 * 
	 * @param 
	 * @return
	 */
	public boolean sendFile(File csv,String csvFileName) {
		String path = conf.get("fs.default.name");
		String fileName = csvFileName;
		System.out.println("上传本地附件："+fileName);
		String tmppath  = path+"/csvfiles/"+fileName;
		
		FSDataOutputStream fsOut = null;
		FSDataInputStream fsIn = null;
		try {
			fsOut = hadoopFS.create(new Path(tmppath));
			fsIn = localFS.open(new Path(csv.getAbsolutePath()));
			byte[] buf = new byte[1024];
			int readbytes = 0;
			while ((readbytes = fsIn.read(buf)) > 0) {
				fsOut.write(buf, 0, readbytes);
			}
			
			fsOut.flush();
			System.out.println("csv文件上传完成："+fileName);
		} catch (IOException e) {
			System.out.println("csv文件上传异常："+csv.getAbsolutePath());
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
	
		return true;
	}

	public boolean delFile(String fileName) {
		try {
			String path = conf.get("fs.default.name");
			String hadfile = path+"/csvfiles/"+fileName;
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

	public boolean downloadFile(String csvFileName,String localPath) {

		try {
			FileSystem localFS = FileSystem.getLocal(conf);
			FileSystem hadoopFS = FileSystem.get(conf);
			String path = conf.get("fs.default.name");
			Path hadPath = new Path(path+"/csvfiles/"+csvFileName);

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
