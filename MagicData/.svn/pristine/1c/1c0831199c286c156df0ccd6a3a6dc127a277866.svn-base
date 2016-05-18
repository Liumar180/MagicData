package com.integrity.dataSmart.impAnalyImport.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;


public class DateFormat {
	
	@SuppressWarnings("deprecation")
	public static String DatetoMsec(String date){
		  System.out.println(new Date(date).getTime());
		return String.valueOf(new Date(date).getTime());
	}	
//	public static String MsectoDate(String Msec){
//		Date d = new Date(Long.valueOf(Msec));
//		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
//		  System.out.println(sdf.format(d));
//		return sdf.format(d);
//	}
	
	public static Date getDateBy(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace(System.out);
		}
		return date;
	}
	public static String getDateBy1(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = "";
		date = sdf.format(new Date(dateStr));
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
	public static String solrDate1(Date date){
//		if (StringUtils.isBlank(date)) {
//			return "";
//		}
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		format.setTimeZone(timeZone);
		String temp = format.format(date);
		//temp.replace("^", "T");
//		System.out.println(temp);
		return temp;
	}
	
	public static String transferLongToDate(Long millSec){
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date= new Date(millSec);
	            return sdf.format(date);
	    }

}
