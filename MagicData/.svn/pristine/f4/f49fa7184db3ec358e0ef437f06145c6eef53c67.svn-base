package com.integrity.lawCase.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.integrity.lawCase.common.ConstantManage;

/**
 * 日期转换工具
 *
 */
public class TransformYears {
	private static Logger logger = Logger.getLogger(TransformYears.class);
	private static final SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd"); 
    private static final SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

    /**
     * 根据常量获取最近几年的年份
     * @return
     */
	public static List<Integer> getYearsBySeveral(){
		List<Integer> list = new ArrayList<Integer>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		for (int i = ConstantManage.SEVERAL-1; i >= 0; i--) {
			list.add(year-i);
		}
		return list;
	}
	
	/** 
     * 根据年份获取当年的开始时间，即2015-01-01 00:00:00 
     * @return 
     */ 
    public static Date getYearStartTime(int year) { 
        Calendar c = Calendar.getInstance(); 
        Date date = null; 
        try { 
        	c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, 0); 
            c.set(Calendar.DATE, 1); 
            date = shortSdf.parse(shortSdf.format(c.getTime())); 
        } catch (Exception e) { 
            logger.error("日期转换异常",e);
        } 
        return date; 
    } 

    /** 
     * 根据年份获取当年的结束时间，即2015-12-31 23:59:59 
     * @return 
     */ 
    public static Date getYearEndTime(int year) { 
        Calendar c = Calendar.getInstance(); 
        Date date = null; 
        try { 
        	c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, 11); 
            c.set(Calendar.DATE, 31); 
            date = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"); 
        } catch (Exception e) { 
        	logger.error("日期转换异常",e);
        } 
        return date; 
    } 
	
	public static void main(String[] args) {
		Date date = getYearEndTime(2222);
		System.out.println(longSdf.format(date));
	}
}
