package com.integrity.dataSmart.impAnalyImport.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author integrity
 * 
 */
public class FileTools {
	
	public final static String ENCODE_GBK = "gbk";

	public final static String ENCODE_UTF8 = "utf-8";
	
	public final static String PACKET_STRING = "g6s1t4r5e7h2j9k3m8n0oiykpw";
	
	/**
	 * 删除文件
	 * <b>注意，删除之前将所有操作当前文件的流关闭，否则删除失败</b>
	 * 
	 * @param file
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(File file, String filePath) {

		if (null == file && StringUtils.isBlank(filePath)) {
			return false;
		}

		if (null == file) {
			file = new File(filePath);
		}
		
		if (file.isFile() && file.exists()) {
			file.delete();
			// logger.info("文件删除 标记: " + flag + " 路径：" + file.getAbsolutePath());
			return true;
		}

		return false;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePath
	 */
	public static void deleteEmptyDir(String filePath) {
		File file = new File(filePath);
		if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			if (subFiles != null && subFiles.length > 0) {
				for (int i = 0; i < subFiles.length; i++) {
					deleteEmptyDir(subFiles[i].getPath());
				}
			} else {
				file.delete();
			}
		}
	}
	
	/**
	 * 写文件，新文件名称与就文件名称不相同,返回新文件路径
	 * @param file 旧文件
	 * @param filePath 新文件路径
	 * @param fileName 新文件名称和后缀
	 */
	public static String saveFile(File file, String filePath,String fileName) {

		File strorefile = null;
		try {

			InputStream fis = new FileInputStream(file);

			File newFile = new File(filePath); // 判断文件夹是否存在,如果不存在则创建文件夹
			if (!newFile.exists()) {
				newFile.mkdirs(); // 新建目录
			}
			
			if (EventUtil.isWindows()) {
				strorefile = new File(filePath + "\\" + fileName);
			}else{
				strorefile = new File(filePath + "/" + fileName);
			}
			
			BufferedOutputStream bos = null;
			BufferedInputStream bis = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(strorefile));
				bis = new BufferedInputStream(fis);
				int c;
				while ((c = bis.read()) != -1) {
					bos.write(c);
					bos.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				bos.close();
				bis.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return strorefile.getAbsolutePath();
	}

	/**
	 * 写文件 新文件名称与就文件名称相同
	 * 
	 * @param file 旧文件
	 * @param filePath 新文件路径
	 */
	public static void saveFile(File file, String filePath) {

		try {

			InputStream fis = new FileInputStream(file);

			File newFile = new File(filePath); // 判断文件夹是否存在,如果不存在则创建文件夹
			if (!newFile.exists()) {
				newFile.mkdirs(); // 新建目录
			}

			File strorefile = null;
			
			if (EventUtil.isWindows()) {
				strorefile = new File(filePath + "\\" + file.getName());
			}else{
				strorefile = new File(filePath + "/" + file.getName());
			}
			
			BufferedOutputStream bos = null;
			BufferedInputStream bis = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(strorefile));
				bis = new BufferedInputStream(fis);
				int c;
				while ((c = bis.read()) != -1) {
					bos.write(c);
					bos.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				bos.close();
				bis.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将文件流转化成字符串
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String inputStreamByString(File file,String encode){
		InputStream is = null;
		BufferedReader in = null;
		StringBuffer buffer = new StringBuffer();
		try {
			is= new FileInputStream(file);
			in = new BufferedReader(new InputStreamReader(is,encode));
			
			String line = "";
			while ((line = in.readLine()) != null){
				buffer.append(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
				in.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 此方法只提供给 原始流量文件使用 处理packet文件
	 * 
	 * 此处需要过滤，是否图片，如果是图片，不处理
	 * @param file
	 * @param encode
	 * @return
	 */
	public static List<String> inputStreamByStringIsPacket(File file,String encode){
		InputStream is = null;
		BufferedReader in = null;
		StringBuffer request = new StringBuffer();
		StringBuffer response = new StringBuffer();
		StringBuffer context = new StringBuffer();
		boolean typeFlag = true;
		
		try {
			is = new FileInputStream(file);
			in = new BufferedReader(new InputStreamReader(is,encode));
			String line = "";
			int index = 0;
			while (in.ready()){
				line = in.readLine();
				
				if (line.trim().equals(PACKET_STRING)) {
					index ++;
				}
				
				if (0 == index) {

					if (request.length() > 0) {
						request.append("\n");
					}
					
					if (!line.equals(PACKET_STRING)) {
						request.append(line);
					}

				}else if(1 == index){
					
					if (request.length() > 0) {
						request.append("\n");
					}
					
					if (!line.equals(PACKET_STRING)) {
						request.append(line);
					}
					
				}else if(2 == index){
					
					if (response.length() > 0) {
						response.append("\n");
					}
					
					if (line.indexOf("Content-Type") != -1) {
						String[] typeArry = line.split(":");
						if (null != typeArry && typeArry.length == 2) {
							String type = typeArry[1];
							if (type.toLowerCase().indexOf("gif") > 0 ||
								type.toLowerCase().indexOf("png") > 0 || 
								type.toLowerCase().indexOf("jpg") > 0 ||
								type.toLowerCase().indexOf("jpeg") > 0 ||
								type.toLowerCase().indexOf("tga") > 0 ||
								type.toLowerCase().indexOf("exif") > 0 ||
								type.toLowerCase().indexOf("fpx") > 0 ||
								type.toLowerCase().indexOf("svg") > 0 ||
								type.toLowerCase().indexOf("psd") > 0 ||
								type.toLowerCase().indexOf("cdr") > 0 ||
								type.toLowerCase().indexOf("pcd") > 0 ||
								type.toLowerCase().indexOf("dxf") > 0 ||
								type.toLowerCase().indexOf("ufo") > 0 ||
								type.toLowerCase().indexOf("eps") > 0 ||
								type.toLowerCase().indexOf("ai") > 0) {
								
								typeFlag = false;
							}
						}
					}

					if (!line.equals(PACKET_STRING)) {
						response.append(line);
					}
					
				}else if(3 == index){
					
					if (context.length() > 0) {
						context.append("\n");
					}
					
					if (!line.equals(PACKET_STRING)) {
						context.append(line);
					}
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
				in.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		List<String> list = new ArrayList<String>();
		
		if (typeFlag) {
			list.add(request.toString());
			list.add(response.toString());
			list.add(context.toString());
		}
		
		return list;
	}
	
	/**
	 * 获取文件后缀
	 * 
	 * @param file
	 * @return
	 */
	public static String getTypeByFileName(File file){
		
		String fileType = "";
		
		if (file.isFile()) {
			String fileName = file.getName();
			fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		}
		
		return fileType;
	}
	
	/**
	 * 获取文件后缀
	 * 
	 * @param file
	 * @return
	 */
	public static String getTypeByFileName(String fileName){
		
		String fileType = "";
		
		if (StringUtils.isNotBlank(fileName)) {
			fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		
		return fileType;
	}
	
	/**
	 * 判断文件名称以什么字符串开头
	 * 
	 * @param file
	 * @param fileStart
	 * @return
	 */
	public static Integer getFileNameByFlagStart(File file,String fileStart){
		
		Integer flag = -1;
		
		if (file.isFile()) {
			String fileName = file.getName();
			flag = fileName.indexOf(fileStart);
		}
		
		return flag;
	}
}
