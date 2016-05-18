package com.integrity.dataSmart.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;


public class DateFormat {
	
	/**
	 * 将日期转换为字符串
	 * @param date 日期
	 * @param str 格式化串
	 * @return
	 */
	public static String transferDate2String(Date date,String str){
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(str);
    	return sdf.format(date);
    }
	
	@SuppressWarnings("deprecation")
	public static String DatetoMsec(String date){
		return String.valueOf(new Date(date).getTime());
	}	
//	public static String MsectoDate(String Msec){
//		Date d = new Date(Long.valueOf(Msec));
//		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		  System.out.println(sdf.format(d));
//		return sdf.format(d);
//	}
	
	public static Date getDateBy(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static String solrDate(String date){
		if (StringUtils.isBlank(date)) {
			return "";
		}
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		format.setTimeZone(timeZone);
		String temp = format.format(getDateBy(date));
		//temp.replace("^", "T");
//		System.out.println(temp);
		return temp;
	}
	public static String transferLongToDate(Long millSec){
		if (millSec == null) {
			return "";
		}
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date= new Date(millSec);
	            return sdf.format(date);
	    }
	 public static long convert2long(String date,String format) {
		  try {
		   if (StringUtils.isNotBlank(date)) {
			   //21/05/2015";
				// "09:36
		    SimpleDateFormat sf = new SimpleDateFormat(format);
		    return sf.parse(date).getTime();
		   }
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		  return 0l;
		 }

}
