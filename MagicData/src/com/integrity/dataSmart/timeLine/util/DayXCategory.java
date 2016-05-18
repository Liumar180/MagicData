package com.integrity.dataSmart.timeLine.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayXCategory {
	/*
	 * public static void main(String[] args) throws Throwable {
	 * 
	 * String[] strd=getDatetoMonthLong("2015-06"); List<String> list =
	 * java.util.Arrays.asList(strd); System.out.println("xxxxxxxxxx="+list);
	 * 
	 * for(int i=0;i<strd.length;i++){ System.out.println(strd[i]); } }
	 */

	public static String[] getDatetoMonthLong(String timel)
			throws ParseException {
		/*
		 * String str1[] = { "1号", "2号", "3号", "4号", "5号", "6号", "7号", "8号",
		 * "9号", "10号", "11号", "12号", "13号", "14号", "15号", "16号", "17号", "18号",
		 * "19号", "20号", "21号", "22号", "23号", "24号", "25号", "26号", "27号", "28号",
		 * "29号", "30号", "31号" };
		 * 
		 * String str2[] = { "1号", "2号", "3号", "4号", "5号", "6号", "7号", "8号",
		 * "9号", "10号", "11号", "12号", "13号", "14号", "15号", "16号", "17号", "18号",
		 * "19号", "20号", "21号", "22号", "23号", "24号", "25号", "26号", "27号", "28号",
		 * "29号", "30号" };
		 * 
		 * String str3[] = { "1号", "2号", "3号", "4号", "5号", "6号", "7号", "8号",
		 * "9号", "10号", "11号", "12号", "13号", "14号", "15号", "16号", "17号", "18号",
		 * "19号", "20号", "21号", "22号", "23号", "24号", "25号", "26号", "27号", "28号"
		 * };
		 * 
		 * String str4[] = { "1号", "2号", "3号", "4号", "5号", "6号", "7号", "8号",
		 * "9号", "10号", "11号", "12号", "13号", "14号", "15号", "16号", "17号", "18号",
		 * "19号", "20号", "21号", "22号", "23号", "24号", "25号", "26号", "27号", "28号",
		 * "29号" };
		 */

		String str1[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31" };

		String str2[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30" };

		String str3[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28" };

		String str4[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29" };

		int day = 1;
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Integer year = TimeBdzd.getDateYeardd(timel);
		Integer month = TimeBdzd.getDateMonth(timel);
		calendar.set(year, month - 1, day);
		int maxday = calendar.getActualMaximum(Calendar.DATE);

		if (maxday == 31) {
			return str1;
		} else if (maxday == 30) {
			return str2;
		} else if (maxday == 28) {
			return str3;
		} else {
			return str4;
		}

	}

	/*
	 * 传入任意的时间日期
	 */
	public static List<String> getDatetoMonthLong11(String ydate)
			throws ParseException {
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
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		if (maxday == 31) {
			rightNow.add(Calendar.DAY_OF_YEAR, 0);
			Date dt1 = rightNow.getTime();
			String reStr1 = sdf.format(dt1);
			xcategory1.add(reStr1);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2 = rightNow.getTime();
			String reStr2 = sdf.format(dt2);
			xcategory1.add(reStr2);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3 = rightNow.getTime();
			String reStr3 = sdf.format(dt3);
			xcategory1.add(reStr3);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4 = rightNow.getTime();
			String reStr4 = sdf.format(dt4);
			xcategory1.add(reStr4);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt5 = rightNow.getTime();
			String reStr5 = sdf.format(dt5);
			xcategory1.add(reStr5);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt6 = rightNow.getTime();
			String reStr6 = sdf.format(dt6);
			xcategory1.add(reStr6);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt7 = rightNow.getTime();
			String reStr7 = sdf.format(dt7);
			xcategory1.add(reStr7);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt8 = rightNow.getTime();
			String reStr8 = sdf.format(dt8);
			xcategory1.add(reStr8);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt9 = rightNow.getTime();
			String reStr9 = sdf.format(dt9);
			xcategory1.add(reStr9);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt10 = rightNow.getTime();
			String reStr10 = sdf.format(dt10);
			xcategory1.add(reStr10);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt11 = rightNow.getTime();
			String reStr11 = sdf.format(dt11);
			xcategory1.add(reStr11);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt12 = rightNow.getTime();
			String reStr12 = sdf.format(dt12);
			xcategory1.add(reStr12);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt13 = rightNow.getTime();
			String reStr13 = sdf.format(dt13);
			xcategory1.add(reStr13);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt14 = rightNow.getTime();
			String reStr14 = sdf.format(dt14);
			xcategory1.add(reStr14);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt15 = rightNow.getTime();
			String reStr15 = sdf.format(dt15);
			xcategory1.add(reStr15);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt16 = rightNow.getTime();
			String reStr16 = sdf.format(dt16);
			xcategory1.add(reStr16);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt17 = rightNow.getTime();
			String reStr17 = sdf.format(dt17);
			xcategory1.add(reStr17);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt18 = rightNow.getTime();
			String reStr18 = sdf.format(dt18);
			xcategory1.add(reStr18);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt19 = rightNow.getTime();
			String reStr19 = sdf.format(dt19);
			xcategory1.add(reStr19);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt20 = rightNow.getTime();
			String reStr20 = sdf.format(dt20);
			xcategory1.add(reStr20);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt21 = rightNow.getTime();
			String reStr21 = sdf.format(dt21);
			xcategory1.add(reStr21);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt22 = rightNow.getTime();
			String reStr22 = sdf.format(dt22);
			xcategory1.add(reStr22);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt23 = rightNow.getTime();
			String reStr23 = sdf.format(dt23);
			xcategory1.add(reStr23);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt24 = rightNow.getTime();
			String reStr24 = sdf.format(dt24);
			xcategory1.add(reStr24);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt25 = rightNow.getTime();
			String reStr25 = sdf.format(dt25);
			xcategory1.add(reStr25);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt26 = rightNow.getTime();
			String reStr26 = sdf.format(dt26);
			xcategory1.add(reStr26);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt27 = rightNow.getTime();
			String reStr27 = sdf.format(dt27);
			xcategory1.add(reStr27);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt28 = rightNow.getTime();
			String reStr28 = sdf.format(dt28);
			xcategory1.add(reStr28);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt29 = rightNow.getTime();
			String reStr29 = sdf.format(dt29);
			xcategory1.add(reStr29);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt30 = rightNow.getTime();
			String reStr30 = sdf.format(dt30);
			xcategory1.add(reStr30);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt31 = rightNow.getTime();
			String reStr31 = sdf.format(dt31);
			xcategory1.add(reStr31);
		}
		if (maxday == 30) {
			rightNow.add(Calendar.DAY_OF_YEAR, 0);
			Date dt221 = rightNow.getTime();
			String reStr221 = sdf.format(dt221);
			xcategory2.add(reStr221);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt222 = rightNow.getTime();
			String reStr222 = sdf.format(dt222);
			xcategory2.add(reStr222);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt223 = rightNow.getTime();
			String reStr223 = sdf.format(dt223);
			xcategory2.add(reStr223);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt224 = rightNow.getTime();
			String reStr224 = sdf.format(dt224);
			xcategory2.add(reStr224);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt225 = rightNow.getTime();
			String reStr225 = sdf.format(dt225);
			xcategory2.add(reStr225);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt226 = rightNow.getTime();
			String reStr226 = sdf.format(dt226);
			xcategory2.add(reStr226);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt227 = rightNow.getTime();
			String reStr227 = sdf.format(dt227);
			xcategory2.add(reStr227);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt228 = rightNow.getTime();
			String reStr228 = sdf.format(dt228);
			xcategory2.add(reStr228);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt229 = rightNow.getTime();
			String reStr229 = sdf.format(dt229);
			xcategory2.add(reStr229);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2210 = rightNow.getTime();
			String reStr2210 = sdf.format(dt2210);
			xcategory2.add(reStr2210);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2211 = rightNow.getTime();
			String reStr2211 = sdf.format(dt2211);
			xcategory2.add(reStr2211);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2212 = rightNow.getTime();
			String reStr2212 = sdf.format(dt2212);
			xcategory2.add(reStr2212);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2213 = rightNow.getTime();
			String reStr2213 = sdf.format(dt2213);
			xcategory2.add(reStr2213);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2214 = rightNow.getTime();
			String reStr2214 = sdf.format(dt2214);
			xcategory2.add(reStr2214);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2215 = rightNow.getTime();
			String reStr2215 = sdf.format(dt2215);
			xcategory2.add(reStr2215);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2216 = rightNow.getTime();
			String reStr2216 = sdf.format(dt2216);
			xcategory2.add(reStr2216);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2217 = rightNow.getTime();
			String reStr2217 = sdf.format(dt2217);
			xcategory2.add(reStr2217);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2218 = rightNow.getTime();
			String reStr2218 = sdf.format(dt2218);
			xcategory2.add(reStr2218);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2219 = rightNow.getTime();
			String reStr2219 = sdf.format(dt2219);
			xcategory2.add(reStr2219);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2220 = rightNow.getTime();
			String reStr2220 = sdf.format(dt2220);
			xcategory2.add(reStr2220);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2221 = rightNow.getTime();
			String reStr2221 = sdf.format(dt2221);
			xcategory2.add(reStr2221);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2222 = rightNow.getTime();
			String reStr2222 = sdf.format(dt2222);
			xcategory2.add(reStr2222);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2223 = rightNow.getTime();
			String reStr2223 = sdf.format(dt2223);
			xcategory2.add(reStr2223);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2224 = rightNow.getTime();
			String reStr2224 = sdf.format(dt2224);
			xcategory2.add(reStr2224);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2225 = rightNow.getTime();
			String reStr2225 = sdf.format(dt2225);
			xcategory2.add(reStr2225);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2226 = rightNow.getTime();
			String reStr2226 = sdf.format(dt2226);
			xcategory2.add(reStr2226);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2227 = rightNow.getTime();
			String reStr2227 = sdf.format(dt2227);
			xcategory2.add(reStr2227);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2228 = rightNow.getTime();
			String reStr2228 = sdf.format(dt2228);
			xcategory2.add(reStr2228);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2229 = rightNow.getTime();
			String reStr2229 = sdf.format(dt2229);
			xcategory2.add(reStr2229);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt2230 = rightNow.getTime();
			String reStr2230 = sdf.format(dt2230);
			xcategory2.add(reStr2230);
		}
		if (maxday == 29) {
			rightNow.add(Calendar.DAY_OF_YEAR, 0);
			Date dt331 = rightNow.getTime();
			String reStr331 = sdf.format(dt331);
			xcategory3.add(reStr331);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt332 = rightNow.getTime();
			String reStr332 = sdf.format(dt332);
			xcategory3.add(reStr332);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt333 = rightNow.getTime();
			String reStr333 = sdf.format(dt333);
			xcategory3.add(reStr333);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt334 = rightNow.getTime();
			String reStr334 = sdf.format(dt334);
			xcategory3.add(reStr334);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt335 = rightNow.getTime();
			String reStr335 = sdf.format(dt335);
			xcategory3.add(reStr335);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt336 = rightNow.getTime();
			String reStr336 = sdf.format(dt336);
			xcategory3.add(reStr336);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt337 = rightNow.getTime();
			String reStr337 = sdf.format(dt337);
			xcategory3.add(reStr337);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt338 = rightNow.getTime();
			String reStr338 = sdf.format(dt338);
			xcategory3.add(reStr338);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt339 = rightNow.getTime();
			String reStr339 = sdf.format(dt339);
			xcategory3.add(reStr339);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3310 = rightNow.getTime();
			String reStr3310 = sdf.format(dt3310);
			xcategory3.add(reStr3310);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3311 = rightNow.getTime();
			String reStr3311 = sdf.format(dt3311);
			xcategory3.add(reStr3311);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3312 = rightNow.getTime();
			String reStr3312 = sdf.format(dt3312);
			xcategory3.add(reStr3312);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3313 = rightNow.getTime();
			String reStr3313 = sdf.format(dt3313);
			xcategory3.add(reStr3313);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3314 = rightNow.getTime();
			String reStr3314 = sdf.format(dt3314);
			xcategory3.add(reStr3314);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3315 = rightNow.getTime();
			String reStr3315 = sdf.format(dt3315);
			xcategory3.add(reStr3315);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3316 = rightNow.getTime();
			String reStr3316 = sdf.format(dt3316);
			xcategory3.add(reStr3316);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3317 = rightNow.getTime();
			String reStr3317 = sdf.format(dt3317);
			xcategory3.add(reStr3317);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3318 = rightNow.getTime();
			String reStr3318 = sdf.format(dt3318);
			xcategory3.add(reStr3318);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3319 = rightNow.getTime();
			String reStr3319 = sdf.format(dt3319);
			xcategory3.add(reStr3319);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3320 = rightNow.getTime();
			String reStr3320 = sdf.format(dt3320);
			xcategory3.add(reStr3320);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3321 = rightNow.getTime();
			String reStr3321 = sdf.format(dt3321);
			xcategory3.add(reStr3321);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3322 = rightNow.getTime();
			String reStr3322 = sdf.format(dt3322);
			xcategory3.add(reStr3322);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3323 = rightNow.getTime();
			String reStr3323 = sdf.format(dt3323);
			xcategory3.add(reStr3323);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3324 = rightNow.getTime();
			String reStr3324 = sdf.format(dt3324);
			xcategory3.add(reStr3324);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3325 = rightNow.getTime();
			String reStr3325 = sdf.format(dt3325);
			xcategory3.add(reStr3325);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3326 = rightNow.getTime();
			String reStr3326 = sdf.format(dt3326);
			xcategory3.add(reStr3326);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3327 = rightNow.getTime();
			String reStr3327 = sdf.format(dt3327);
			xcategory3.add(reStr3327);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3328 = rightNow.getTime();
			String reStr3328 = sdf.format(dt3328);
			xcategory3.add(reStr3328);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt3329 = rightNow.getTime();
			String reStr3329 = sdf.format(dt3329);
			xcategory3.add(reStr3329);
		}
		if (maxday == 28) {
			rightNow.add(Calendar.DAY_OF_YEAR, 0);
			Date dt441 = rightNow.getTime();
			String reStr441 = sdf.format(dt441);
			xcategory4.add(reStr441);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt442 = rightNow.getTime();
			String reStr442 = sdf.format(dt442);
			/*int dateMth442 = TimeBdzd.getDateMth(reStr442);
		    int dates442 = TimeBdzd.getDates(reStr442);
		    String res442=dateMth442+"-"+dates442;*/
			xcategory4.add(reStr442);
			
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt443 = rightNow.getTime();
			String reStr443 = sdf.format(dt443);
			xcategory4.add(reStr443);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt444 = rightNow.getTime();
			String reStr444 = sdf.format(dt444);
			xcategory4.add(reStr444);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt445 = rightNow.getTime();
			String reStr445 = sdf.format(dt445);
			xcategory4.add(reStr445);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt446 = rightNow.getTime();
			String reStr446 = sdf.format(dt446);
			xcategory4.add(reStr446);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt447 = rightNow.getTime();
			String reStr447 = sdf.format(dt447);
			xcategory4.add(reStr447);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt448 = rightNow.getTime();
			String reStr448 = sdf.format(dt448);
			xcategory4.add(reStr448);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt449 = rightNow.getTime();
			String reStr449 = sdf.format(dt449);
			xcategory4.add(reStr449);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4410 = rightNow.getTime();
			String reStr4410 = sdf.format(dt4410);
			xcategory4.add(reStr4410);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4411 = rightNow.getTime();
			String reStr4411 = sdf.format(dt4411);
			xcategory4.add(reStr4411);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4412 = rightNow.getTime();
			String reStr4412 = sdf.format(dt4412);
			xcategory4.add(reStr4412);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4413 = rightNow.getTime();
			String reStr4413 = sdf.format(dt4413);
			xcategory4.add(reStr4413);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4414 = rightNow.getTime();
			String reStr4414 = sdf.format(dt4414);
			xcategory4.add(reStr4414);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4415 = rightNow.getTime();
			String reStr4415 = sdf.format(dt4415);
			xcategory4.add(reStr4415);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4416 = rightNow.getTime();
			String reStr4416 = sdf.format(dt4416);
			xcategory4.add(reStr4416);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4417 = rightNow.getTime();
			String reStr4417 = sdf.format(dt4417);
			xcategory4.add(reStr4417);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4418 = rightNow.getTime();
			String reStr4418 = sdf.format(dt4418);
			xcategory4.add(reStr4418);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4419 = rightNow.getTime();
			String reStr4419 = sdf.format(dt4419);
			xcategory4.add(reStr4419);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4420 = rightNow.getTime();
			String reStr4420 = sdf.format(dt4420);
			xcategory4.add(reStr4420);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4421 = rightNow.getTime();
			String reStr4421 = sdf.format(dt4421);
			xcategory4.add(reStr4421);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4422 = rightNow.getTime();
			String reStr4422 = sdf.format(dt4422);
			xcategory4.add(reStr4422);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4423 = rightNow.getTime();
			String reStr4423 = sdf.format(dt4423);
			xcategory4.add(reStr4423);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4424 = rightNow.getTime();
			String reStr4424 = sdf.format(dt4424);
			xcategory4.add(reStr4424);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4425 = rightNow.getTime();
			String reStr4425 = sdf.format(dt4425);
			xcategory4.add(reStr4425);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4426 = rightNow.getTime();
			String reStr4426 = sdf.format(dt4426);
			xcategory4.add(reStr4426);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4427 = rightNow.getTime();
			String reStr4427 = sdf.format(dt4427);
			xcategory4.add(reStr4427);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt4428 = rightNow.getTime();
			String reStr4428 = sdf.format(dt4428);
			xcategory4.add(reStr4428);
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
	
	/*
	 * 传入任意的时间日期,获取相应月份
	 */
	public static List<String> getDatetoMonthLong21(String ydate)
			throws ParseException {
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
}
