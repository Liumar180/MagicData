package com.integrity.dataSmart.timeLine.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.integrity.dataSmart.common.DataType;

public class TimeBdzd {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdfs = new SimpleDateFormat(DataType.DATEFORMATSTR);
	public static void main(String[] args) throws Throwable {
		// Date date = new Date();
		// date.setYear(date.getYear()-1);
		//Long strd = getStrDatetoLong("2015-06-02");
		//System.out.println(strd);
		// System.out.println(sdf.format(date));
		//String datestrl= getLongDatetoStr("1427760000000");//long毫秒转成的字符串
		//System.out.println(datestrl);

		// String strd="2015-06-07";

		// Integer year=getDateYear(strd);
		// System.out.println(year);

		// Integer mon=getDateMth(strd);
		// System.out.println(mon);

		// Integer week=getDateweek(strd);
		// System.out.println(week);

		// Integer dates=getDates(strd);
		// System.out.println(dates);

		// Integer strd=getMonthMaxDate("2015-02");
		// System.out.println(strd);

	}

	 // long时间字符串型转为yyyy-MM-dd字符串时间
	public static String getLongDatetoStr(String timel) {
		Date date = new Date(Long.parseLong(timel.trim()));
		String dateString = sdf.format(date);
		// System.out.println(dateString);
		return dateString;
	}

	// yyyy-MM-dd字符串时间转化为long毫秒时间字符串
	public static long getStrDatetoLong(String strd) throws ParseException {
		Date dt = sdf.parse(strd);
		return dt.getTime();// 获得的是long毫秒
	}
	
	     // yyyy-MM-dd HH:mm:ss字符串时间转化为long毫秒时间字符串
		public static long getStrDatetoLongl(String strd) throws ParseException {
			 Date dt = sdfs.parse(strd);
			return dt.getTime();// 获得的是long毫秒
		}
		
	// 根据yyyy-MM-dd HH:mm:ss字符串时间获取所在年
	public static int getDateYearl(String strd) throws ParseException {
		Date date = sdfs.parse(strd);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	 }
	
	// 根据yyyy-MM-dd字符串时间获取所在年
		public static int getDateYear(String strd) throws ParseException {
			Date date = sdf.parse(strd);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.YEAR);
		 }
	// 根据yyyy-MM字符串时间获取所在年（备用）
	public static int getDateYeardd(String strd) throws ParseException {
		SimpleDateFormat sdfy = new SimpleDateFormat("yyyy-MM");
		Date date = sdfy.parse(strd);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	// 根据yyyy-MM-dd HH:mm:ss字符串时间获取所在月
		public static int getDateMonthl(String strd) throws ParseException {
			Date date = sdfs.parse(strd);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MONTH) + 1;
		}
		
		
	// 根据yyyy-MM-dd字符串时间获取所在月
	public static int getDateMonth(String strd) throws ParseException {
		Date date = sdf.parse(strd);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	// 根据yyyy-MM字符串时间获取所在月（备用）
	public static int getDateMonthdd(String strd) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		Date date = sdf1.parse(strd);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	// 根据yyyy-MM-dd字符串时间获取所在周（备用）
	public static int getDateweek(String strd) throws ParseException {
		Date date = sdf.parse(strd);
		String str[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获取时间
		System.out.println("今天是:" + str[day - 1]);// 通过数组把周几输出

		return day;

	}
	
	// 根据yyyy-MM-dd HH:mm:ss字符串时间获取所在日
		public static int getDatesl(String strd) throws ParseException {
			Date date = sdfs.parse(strd);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.DATE);
		}
		
	// 根据yyyy-MM-dd字符串时间获取所在日
	public static int getDates(String strd) throws ParseException {
		Date date = sdf.parse(strd);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	// 根据yyyy-MM-dd字符串时间获取这个时间本月的最大天数
	public static int getMonthMaxDates(String strd) throws ParseException {
		Date date = sdf.parse(strd);
		int day = 1;
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		Integer year = cal.get(Calendar.YEAR);
		Integer month = cal.get(Calendar.MONTH) + 1;
		cal.set(year, month - 1, day);
		int maxday = cal.getActualMaximum(Calendar.DATE);
		return maxday;
	}

	// 根据yyyy-MM字符串时间获取这个时间本月的最大天数
	public static int getMonthMaxDatedd(String strd) throws ParseException {
		SimpleDateFormat sdfy = new SimpleDateFormat("yyyy-MM");
		Date date = sdfy.parse(strd);
		int day = 1;
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		Integer year = cal.get(Calendar.YEAR);
		Integer month = cal.get(Calendar.MONTH) + 1;
		cal.set(year, month - 1, day);
		int maxday = cal.getActualMaximum(Calendar.DATE);
		return maxday;
	 }
	
	 // long时间字符串型转为yyyy-MM-dd HH:mm:ss字符串时间
		public static String getLongDatetoStrl(String timel) {
			Date date = new Date(Long.parseLong(timel.trim()));
			String dateString = sdfs.format(date);
			// System.out.println(dateString);
			return dateString;
		}
	          // 根据yyyy-MM-dd HH:mm:ss字符串时间获取所在小时
			public static int getHoursl(String strd) throws ParseException {
				Date date = sdfs.parse(strd);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				return cal.get(Calendar.HOUR_OF_DAY);
			}
				
}
