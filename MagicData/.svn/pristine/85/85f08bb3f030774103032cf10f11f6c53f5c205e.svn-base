package com.integrity.dataSmart.impAnalyImport.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 此类提供给VPN二期数据解析使用的公共类，其中某些地方可能与实际业务关联。
 * 
 * @author LiFeng
 *
 * @time 2015年9月17日 下午5:30:43
 * 
 */
public class EventUtil {
	
	/**
	 * 格式化网卡信息
	 * 
	 * @param mac
	 * @return
	 */
	public static String formatmac(String mac){
		
		if (StringUtils.isBlank(mac)) {
			return "";
		}
		
		mac = mac.replace("-", "").replace(":", "").toLowerCase();
		
		return mac;
		
	}
	
	/**
	 * 判断IP格式和范围
	 */
	public static  boolean isIP(String addr) {
		
		if (addr.length() < 7 || addr.length() > 15 || StringUtils.isBlank(addr)) {
			return false;
		}
		
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(addr);

		boolean ipAddress = mat.find();

		return ipAddress;
	}
	
	/**
	 * 根据原始文件，找到对应的packet文件完整路径
	 * <b>packet文件和原始文件保存在统一目录下。只是文件名称不同</b>
	 * @param file 原始文件
	 * @param packetName packet文件名称
	 */
	public static File readPacketFile(File file,String packetName){
		// 获取文件目录
		String fileName = file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(file.getName())) + packetName; 
		File packetfile = new File(fileName);
		
		return packetfile;
	}
	
	
	/**
	 * 判断当前系统是否为windows
	 * 
	 * @return
	 */
	public static boolean isWindows(){
		
		Properties pro = System.getProperties();
		
		if((pro.getProperty("os.name").toLowerCase()).indexOf("windows") > -1){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * 邮件附件重命名并保存到指定目录下，返回路径
	 * 
	 * @param file 附件文件
	 * @param path 附件保存根路径
	 * @param eventid 事件id
	 * @param uuid 唯一标识
	 * @param fileName 新文件名称
	 * @return
	 */
	public static String attachFilePath(File file,String path,String eventid,String uuid,String fileName){
		String newFilePath = "";
		
		if (!file.isFile()) {
			return newFilePath;
		}
		
		if (isWindows()) {
			newFilePath = path + eventid + "\\" + uuid;
		}else{
			newFilePath = path + eventid + "/" + uuid;
		}

		newFilePath = FileTools.saveFile(file, newFilePath,fileName);
		
		return newFilePath;
	}
	
	/**
	 * 重新组装文件路径
	 * 
	 * @param filePath
	 * @param prefix
	 * @return
	 */
	public static String newFilePath(String filePath,String prefix){
		String oldFilePath = "";
		if (isWindows()) {
			oldFilePath = filePath.substring(filePath.indexOf("\\"),filePath.length());
		}else{
			oldFilePath = filePath.substring(filePath.indexOf("/"),filePath.length());
		}
		return prefix + oldFilePath;
	}
	
	/**
	 * copy文件
	 * 
	 * @param file
	 * @param path
	 */
	public static void saveFile(File file,String path){
		
		String filePath = file.getAbsolutePath();
		
		String oldFilePath = "";
		
		if (isWindows()) {
			oldFilePath = filePath.substring(filePath.indexOf("\\"),filePath.lastIndexOf("\\") + 1);
		}else{
			oldFilePath = filePath.substring(filePath.indexOf("/"),filePath.lastIndexOf("/") + 1);
		}
		
		FileTools.saveFile(file,  path + oldFilePath);
	}
	
	/**
	 * 截取文件名称，获取用户名称和sessionid数组
	 * 
	 * @param fileName
	 * @return
	 */
	public static String[] splitFileName(String fileName){
		String[] arry = fileName.split("_");
		return arry;
	}
	
	public static String[] splitFileNameMod(String fileName){
		String[] arry = fileName.split("-");
		return arry;
	}
	
	
	 /**
	  * 修改字符串编码方式
	  * 
	 * @param str
	 * @param newCharset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String changeCharset(String str, String newCharset) {
			  
		try {
			if (str != null) {
				 //用默认字符编码解码字符串。
				 byte[] bs = str.getBytes();
				 //用新的字符编码生成字符串
				 return new String(bs, newCharset);
			 }
		} catch (Exception e) {
			
		}
		 
		 return str;
	 }
	/**
	 * 根据时区转化日期
	 * 
	 * @param sourceDate
	 * @param sourceTimeZone
	 * @param targetTimeZone
	 * @return
	 */
	public static Date dateTransformBetweenTimeZone(Date sourceDate, TimeZone sourceTimeZone,TimeZone targetTimeZone) {
		Long targetTime = sourceDate.getTime() - sourceTimeZone.getRawOffset() + targetTimeZone.getRawOffset();
		return new Date(targetTime);
	}
}