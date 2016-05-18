package com.integrity.dataSmart.timeLine.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.integrity.dataSmart.common.DataType;

public class MonthXCategory {
	/*
	 * public static List<String> getDataXcategory(List<Map<String, Object>>
	 * list) throws Exception { List<String> xcategory = null; for (int i = 0; i
	 * < list.size(); i++) { xcategory = new ArrayList<String>(); Map<String,
	 * Object> vervex = list.get(i);
	 * 
	 * Map<String, Object> vervexPro = (Map<String, Object>) vervex
	 * .get("_properties"); String eventtime = (String)
	 * vervexPro.get("eventtime"); int year=0; if(eventtime!=null){ String dates
	 * = TimeBdzd.getDatetoStr(eventtime); year = TimeBdzd.getDateYear(dates); }
	 * 
	 * xcategory.add(year + "-" + 1); xcategory.add(year + "-" + 2);
	 * xcategory.add(year + "-" + 3); xcategory.add(year + "-" + 4);
	 * xcategory.add(year + "-" + 5); xcategory.add(year + "-" + 6);
	 * xcategory.add(year + "-" + 7); xcategory.add(year + "-" + 8);
	 * xcategory.add(year + "-" + 9); xcategory.add(year + "-" + 10);
	 * xcategory.add(year + "-" + 11); xcategory.add(year + "-" + 12);
	 * 
	 * // System.out.println(month); }
	 * 
	 * return xcategory; }
	 */

	// 传入String时间类型
	public static List<String> getDataXcategory(String ydates) throws Exception {
		List<String> xcategory = new ArrayList<String>();
		int year = 0;
		year = TimeBdzd.getDateYear(ydates);
		xcategory.add(year + "-" + 1);
		xcategory.add(year + "-" + 2);
		xcategory.add(year + "-" + 3);
		xcategory.add(year + "-" + 4);
		xcategory.add(year + "-" + 5);
		xcategory.add(year + "-" + 6);
		xcategory.add(year + "-" + 7);
		xcategory.add(year + "-" + 8);
		xcategory.add(year + "-" + 9);
		xcategory.add(year + "-" + 10);
		xcategory.add(year + "-" + 11);
		xcategory.add(year + "-" + 12);
		return xcategory;
	}

	// 传入任意时间
	public static List<String> getMonthXcategory(String ydates,int yearcha)
			throws Exception {
		List<String> xcategory = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dt = sdf.parse(ydates);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
           
		rightNow.add(Calendar.MONTH, 0);
		Date dt0 = rightNow.getTime();
		String reStr0 = sdf.format(dt0);
		xcategory.add(reStr0);

		rightNow.add(Calendar.MONTH, 1);
		Date dt1 = rightNow.getTime();
		String reStr1 = sdf.format(dt1);
		xcategory.add(reStr1);

		rightNow.add(Calendar.MONTH, 1);
		Date dt2 = rightNow.getTime();
		String reStr2 = sdf.format(dt2);
		xcategory.add(reStr2);

		rightNow.add(Calendar.MONTH, 1);
		Date dt3 = rightNow.getTime();
		String reStr3 = sdf.format(dt3);
		xcategory.add(reStr3);

		rightNow.add(Calendar.MONTH, 1);
		Date dt4 = rightNow.getTime();
		String reStr4 = sdf.format(dt4);
		xcategory.add(reStr4);

		rightNow.add(Calendar.MONTH, 1);
		Date dt5 = rightNow.getTime();
		String reStr5 = sdf.format(dt5);
		xcategory.add(reStr5);

		rightNow.add(Calendar.MONTH, 1);
		Date dt6 = rightNow.getTime();
		String reStr6 = sdf.format(dt6);
		xcategory.add(reStr6);

		rightNow.add(Calendar.MONTH, 1);
		Date dt7 = rightNow.getTime();
		String reStr7 = sdf.format(dt7);
		xcategory.add(reStr7);

		rightNow.add(Calendar.MONTH, 1);
		Date dt8 = rightNow.getTime();
		String reStr8 = sdf.format(dt8);
		xcategory.add(reStr8);

		rightNow.add(Calendar.MONTH, 1);
		Date dt9 = rightNow.getTime();
		String reStr9 = sdf.format(dt9);
		xcategory.add(reStr9);

		rightNow.add(Calendar.MONTH, 1);
		Date dt10 = rightNow.getTime();
		String reStr10 = sdf.format(dt10);
		xcategory.add(reStr10);

		rightNow.add(Calendar.MONTH, 1);
		Date dt11 = rightNow.getTime();
		String reStr11 = sdf.format(dt11);
		xcategory.add(reStr11);

		return xcategory;
	}
	
	// 传入任意时间获取十二个月
	public static List<String> getMonthXcategory1(String ydates)
			throws Exception {
		List<String> xcategory = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dt = sdf.parse(ydates);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
           
		rightNow.add(Calendar.MONTH, 0);
		Date dt0 = rightNow.getTime();
		String reStr0 = sdf.format(dt0);
		xcategory.add(reStr0);

		rightNow.add(Calendar.MONTH, 1);
		Date dt1 = rightNow.getTime();
		String reStr1 = sdf.format(dt1);
		xcategory.add(reStr1);

		rightNow.add(Calendar.MONTH, 1);
		Date dt2 = rightNow.getTime();
		String reStr2 = sdf.format(dt2);
		xcategory.add(reStr2);

		rightNow.add(Calendar.MONTH, 1);
		Date dt3 = rightNow.getTime();
		String reStr3 = sdf.format(dt3);
		xcategory.add(reStr3);

		rightNow.add(Calendar.MONTH, 1);
		Date dt4 = rightNow.getTime();
		String reStr4 = sdf.format(dt4);
		xcategory.add(reStr4);

		rightNow.add(Calendar.MONTH, 1);
		Date dt5 = rightNow.getTime();
		String reStr5 = sdf.format(dt5);
		xcategory.add(reStr5);

		rightNow.add(Calendar.MONTH, 1);
		Date dt6 = rightNow.getTime();
		String reStr6 = sdf.format(dt6);
		xcategory.add(reStr6);

		rightNow.add(Calendar.MONTH, 1);
		Date dt7 = rightNow.getTime();
		String reStr7 = sdf.format(dt7);
		xcategory.add(reStr7);

		rightNow.add(Calendar.MONTH, 1);
		Date dt8 = rightNow.getTime();
		String reStr8 = sdf.format(dt8);
		xcategory.add(reStr8);

		rightNow.add(Calendar.MONTH, 1);
		Date dt9 = rightNow.getTime();
		String reStr9 = sdf.format(dt9);
		xcategory.add(reStr9);

		rightNow.add(Calendar.MONTH, 1);
		Date dt10 = rightNow.getTime();
		String reStr10 = sdf.format(dt10);
		xcategory.add(reStr10);

		rightNow.add(Calendar.MONTH, 1);
		Date dt11 = rightNow.getTime();
		String reStr11 = sdf.format(dt11);
		xcategory.add(reStr11);
		
		return xcategory;
	}
	
	/*
	 * 传入任意的时间日期,获取相应月份
	 */
	public static List<String> getDatetoMonthLong21(String ydate)throws ParseException {
		List<String> xcategory1 = new ArrayList<String>();
		List<String> xcategory2 = new ArrayList<String>();
		List<String> xcategory3 = new ArrayList<String>();
		List<String> xcategory4 = new ArrayList<String>();
		int day = 1;
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Integer year = TimeBdzd.getDateYeardd(ydate);
		Integer month = TimeBdzd.getDateMonth(ydate);
		calendar.set(year, month - 1, day);
		int maxday = calendar.getActualMaximum(Calendar.DATE);
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date dt = sdf.parse(ydate);
		if (maxday == 31) {
			  for(int i=0;i<maxday;i++){
					Calendar rightNow = Calendar.getInstance();
					rightNow.setTime(dt);
				   rightNow.add(Calendar.DAY_OF_YEAR,i);
				   Date dt1 = rightNow.getTime();
				   String reStr1 = sdf.format(dt1);
				  xcategory1.add(reStr1);
			     }
		      }
		if (maxday == 30) {
			for(int i=0;i<maxday;i++){
					Calendar rightNow = Calendar.getInstance();
					rightNow.setTime(dt);
					rightNow.add(Calendar.DAY_OF_YEAR,i);
				Date dt2 = rightNow.getTime();
				String reStr2 = sdf.format(dt2);
				xcategory2.add(reStr2);
			 }
		    }
			
		if (maxday == 29) {
			for(int i=0;i<maxday;i++){
					Calendar rightNow = Calendar.getInstance();
					rightNow.setTime(dt);
					rightNow.add(Calendar.DAY_OF_YEAR,1);
				Date dt3 = rightNow.getTime();
				String reStr3 = sdf.format(dt3);
				xcategory3.add(reStr3);
			 }
		    }
			
		if (maxday == 28) {
			for(int i=0;i<maxday;i++){
				Calendar rightNow = Calendar.getInstance();
				rightNow.setTime(dt);
				rightNow.add(Calendar.DAY_OF_YEAR,i);
				Date dt4 = rightNow.getTime();
				String reStr4 = sdf.format(dt4);
				xcategory4.add(reStr4);
			 }
		   }
		if (maxday == 31) {
			return xcategory1;
		} else if (maxday == 30) {
			return xcategory2;
		} else if (maxday == 28) {
			return xcategory4;
		} else {
			return xcategory3;
		}

	}
	
	// 传入任意时间，获取指定长度月份
		public static List<String> getMonthXcategory11(String ydates,int yearValue)
				throws Exception {
			List<String> xcategory = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date dt = sdf.parse(ydates);
			 for(int i=0;i<yearValue;i++){
					Calendar rightNow = Calendar.getInstance();
					rightNow.setTime(dt);
			        rightNow.add(Calendar.MONTH,i);//月份加值
			        Date dtt=rightNow.getTime();
			        String reStr = sdf.format(dtt);
			        xcategory.add(reStr);
			    }
			return xcategory;
		}
		// 传入任意时间，获取指定长度的日期
				public static List<String> getMonthXcategory21(String ydates,int yearValue)
						throws Exception {
					List<String> xcategory = new ArrayList<String>();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date dt = sdf.parse(ydates);
					 for(int i=0;i<yearValue;i++){
							Calendar rightNow = Calendar.getInstance();
							rightNow.setTime(dt);
					        rightNow.add(Calendar.DATE,i);//日期加值
					        Date dtt=rightNow.getTime();
					        String reStr = sdf.format(dtt);
					        xcategory.add(reStr);
					    }
					return xcategory;
				}
			// 传入任意时间，获取指定长度的小时（Hours）
			public static List<String> getMonthXcategory22(String days,int dayValue)
					throws Exception {
				List<String> xcategory = new ArrayList<String>();
				SimpleDateFormat sdf = new SimpleDateFormat(DataType.DATEFORMATSTR);
				Date dt = sdf.parse(days);
				 for(int i=0;i<=dayValue;i++){
						Calendar rightNow = Calendar.getInstance();
						rightNow.setTime(dt);
				        rightNow.add(Calendar.HOUR,i);//小时加值
				        int hourOfDay = rightNow.get(Calendar.HOUR_OF_DAY);
				       /* Date dtt=rightNow.getTime();
				        String reStr = sdf.format(dtt);
				        Date dtts = sdf.parse(reStr);
				        int hours = dtts.getHours();
				        if (dt.before(dtts)) {
				        	System.out.println(dt+"----"+dtts);
				        }*/
				        /*  if(hourOfDay==0 && i==dayValue){
				    	    hourOfDay=24;
				         }*/
				    	  String hour=hourOfDay+"";
				    	  if(hour.length()==1){
				    		  hour="0"+hour;
				    	    }
				    	  if(i!=dayValue){
				            xcategory.add(hour+":"+"00");
				            }
				       
				    }
				return xcategory;
			}
}
