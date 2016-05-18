package com.integrity.dataSmart.timeLine.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.timeLine.dao.EchartsBarDao;
import com.integrity.dataSmart.timeLine.util.BarColors;
import com.integrity.dataSmart.timeLine.util.EchartsData1;
import com.integrity.dataSmart.timeLine.util.MonthXCategory;
import com.integrity.dataSmart.timeLine.util.Series;
import com.integrity.dataSmart.timeLine.util.TimeBdzd;
import com.integrity.dataSmart.titanGraph.dao.SearchDetailDao;
import com.integrity.dataSmart.titanGraph.pojo.Email;

public class EchartsBarService {
	private EchartsBarDao echartsBarDao;

	public void setEchartsBarDao(EchartsBarDao echartsBarDao) {
		this.echartsBarDao = echartsBarDao;
	}

	public String getPageAllNodeEvents(String[] types,String eventTimes,String dragTime){
		EchartsData1 data = null;
		Map<String, List<Long>> result = null;
		List<String> xcategory = new ArrayList<String>();
		List<String> title = new ArrayList<String>();
		List<String> legend = new ArrayList<String>();
		List<Series> seList = new ArrayList<Series>();
		List<Long> dragList = new ArrayList<Long>();
		Map<String, Series> maps = new HashMap<String, Series>();
		title.add("TimeLine");
		Map<String, String> typeColor = BarColors.getTypeColor();
		Map<String, Map<String, Map<String, List<String>>>> mapstl = new LinkedHashMap<String, Map<String, Map<String, List<String>>>>();
		for (String key1 : types) {
			mapstl.put(key1, new HashMap<String, Map<String, List<String>>>());
		}
		for (int a = 0; a < types.length; a++) {
			mapstl.get(types[a]).put("normal",new HashMap<String, List<String>>());
			mapstl.get(types[a]).get("normal").put("color", new ArrayList<String>());
			mapstl.get(types[a]).get("normal").get("color").add(typeColor.get(types[a]));
		}
		String firsttime = null;
		int valuecha = 0;
		try {
			result = echartsBarDao.getPageAllNodeEvents(types,eventTimes,null);
			//获得时间范围
			List<Long> timeList = result.get("time");
			result.remove("time");
			Long minTime = timeList.get(0);
			Long maxTime = timeList.get(1);
			//System.out.println(new Date(minTime));
			//System.out.println(new Date(maxTime));
			Calendar minCal = Calendar.getInstance();
			minCal.setTime(new Date(minTime));
			Calendar maxCal = Calendar.getInstance();
			maxCal.setTime(new Date(maxTime));
			int minYear = minCal.get(Calendar.YEAR);
			int minMonth = minCal.get(Calendar.MONTH);
			int minDay = minCal.get(Calendar.DATE);
			int minHour = minCal.get(Calendar.HOUR_OF_DAY);
			int maxYear = maxCal.get(Calendar.YEAR);
			int maxMonth = maxCal.get(Calendar.MONTH);
			int maxDay = maxCal.get(Calendar.DATE);
			int maxHour = maxCal.get(Calendar.HOUR_OF_DAY);
			//划分x轴时间段
			String dateFormat = "";
			if(maxYear>minYear){
				if((maxYear-minYear)>1){
					valuecha = maxYear-minYear + 1;
					dateFormat = "yyyy";
					firsttime = new SimpleDateFormat("yyyy").format(new Date(minTime));
				}else{
					valuecha = (maxYear-minYear)*12 + (maxMonth-minMonth+1);
					dateFormat = "yyyy-MM";
					firsttime = new SimpleDateFormat("yyyy-MM").format(new Date(minTime));
				}
			}else if(maxYear==minYear&&maxMonth!=minMonth){
				valuecha = maxMonth - minMonth+1;
				dateFormat = "yyyy-MM";
				firsttime = new SimpleDateFormat("yyyy-MM").format(new Date(minTime));
			}else if(maxYear==minYear&&maxMonth==minMonth&&maxDay!=minDay){
				valuecha = maxDay-minDay+1;
				dateFormat = "yyyy-MM-dd";
				firsttime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(minTime));
			}else if(maxYear==minYear&&maxMonth==minMonth&&maxDay==minDay){
				valuecha = maxHour-minHour+1;
				dateFormat = "yyyy-MM-dd HH:mm";
				firsttime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(minTime));
				title.add(new SimpleDateFormat("yyyy-MM-dd").format(new Date(minTime)));
			}else{
				valuecha = maxYear-minYear + 1;
				dateFormat = "yyyy";
				firsttime = new SimpleDateFormat("yyyy").format(new Date(minTime));
			}
			
			//填充柱状图数据
			Set<String> set = result.keySet();
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Map<String, Map<String, List<String>>> map = mapstl.get(key);
				maps.put(key, new Series());
				maps.get(key).setName(key);
				maps.get(key).setType("bar");
				if (firsttime.length() < 8) { //&& valuecha <= 12) {
					//maps.get(key).setBarCategoryGap("50%");//类目间柱形距离,默认为类目间距的20%
					maps.get(key).setBarWidth(10);
				}
				if (firsttime.length() > 8) {
					maps.get(key).setBarCategoryGap("10%");
					maps.get(key).setBarWidth(8);
				}
				maps.get(key).setItemStyle(map);
				//maps.get(key).setData(value);
				//legend.add(key);
			}
			
			//整合count在时间轴上的统计
			if (firsttime.length() < 8) {
				xcategory = MonthXCategory.getMonthXcategory11(firsttime,valuecha);
			}
			if (firsttime.length() > 8 && firsttime.length() < 14) {
				xcategory = MonthXCategory.getMonthXcategory21(firsttime,valuecha);
			}
			if (firsttime.length() > 14) {
				xcategory = MonthXCategory.getMonthXcategory22(firsttime,valuecha);
			}
			int xCateSize = xcategory.size();
			long partTime = 0l;
			if(xCateSize>0){
				partTime = Math.abs((maxTime-minTime)/xCateSize);
			}else{
				partTime = Math.abs((maxTime-minTime));
			}
			SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Series mapSeries = maps.get(key);
				List<Long> timeValue = result.get(key);
				List<Long> dataList = new ArrayList<Long>();
				for(int i=0;i<xCateSize;i++){
					dataList.add(0l);
				}
				long tempTime = 0;
				String dayStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date(minTime));
				if(null!=timeValue&&timeValue.size()>0){
					for(int i=0;i<xCateSize;i++){
						Date xCurrentDate = null;
						Date xNextDate = null;
						if (firsttime.length() > 14) {
							xCurrentDate = sf.parse(dayStr+" "+xcategory.get(i));
						}else{
							xCurrentDate = sf.parse(xcategory.get(i));
						}
						if(i<(xCateSize-1)){
							if (firsttime.length() > 14) {
								xNextDate = sf.parse(dayStr+" "+xcategory.get(i+1));
							}else{
								xNextDate = sf.parse(xcategory.get(i+1));
							}
						}
						tempTime = xCurrentDate.getTime();
						long typeCount = 0;
						for(Long time:timeValue){
							if(i<(xCateSize-1)){
								if(time>=tempTime&&time<xNextDate.getTime()){
									typeCount++;
									if(!legend.contains(key)){
										legend.add(key);
									}
								}
							}else{
								if(time>=tempTime){
									typeCount++;
									if(!legend.contains(key)){
										legend.add(key);
									}
								}
							}
							
						}
						tempTime = tempTime+partTime;
						dataList.set(i, typeCount);
					}
				}
				mapSeries.setData(dataList);
				seList.add(mapSeries);
			}
			data = new EchartsData1(valuecha, title, legend, xcategory, seList);
			if(null!=dragTime&&!"".equals(dragTime)&&!"null".equals(dragTime)){
				SimpleDateFormat dragsf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String[] dragTimes = dragTime.split(",");
				Long bDTimeLong = 0l;
				Long eDTimeLong = 0l;
				long bStart = 0;
				long eEnd = 100;
				try{
					Date bDTime = dragsf.parse(dragTimes[0]);
					Date eDTime = dragsf.parse(dragTimes[1]);
					bDTimeLong = bDTime.getTime();
					eDTimeLong = eDTime.getTime();
				}catch(ParseException e){
					e.printStackTrace();
				}
				if(bDTimeLong!=0&&eDTimeLong!=0){
					if(bDTimeLong>minTime){
						bStart = Math.round(((double)(bDTimeLong-minTime)/(maxTime-minTime))*100);
					}
					if(eDTimeLong>minTime&&eDTimeLong<maxTime){
						eEnd = Math.round(((double)(eDTimeLong-minTime)/(maxTime-minTime))*100);
					}
					dragList.add(bStart);
					dragList.add(eEnd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dataJson = getJson1(data);//"data":[1,1,0,0,0,7]}]}
		if(dragList.size()>0){
			dataJson = dataJson.substring(0, dataJson.length()-1)+",\"dragList\":["+dragList.get(0)+","+dragList.get(1)+"]}";
		}
		return dataJson;
	}
	/**
	 * 按条件查询多件年事件
	 * @param id
	 * @param types
	 * @param startm
	 * @param endtm
	 * @return
	 * 
	 */
	public String getVertexYearEventsById(Long id, String[] types,String startm, String endtm) throws Exception {
		EchartsData1 data = null;
		Map<String, List<Long>> result = null;
		List<String> xcategory = new ArrayList<String>();
		List<String> title = new ArrayList<String>();
		List<String> legend = new ArrayList<String>();
		List<Series> seList = new ArrayList<Series>();
		Map<String, Series> maps = new HashMap<String, Series>();
		title.add("TimeLine");
		Map<String, String> typeColor = BarColors.getTypeColor();
		Map<String, Map<String, Map<String, List<String>>>> mapstl = new LinkedHashMap<String, Map<String, Map<String, List<String>>>>();
		for (String key1 : types) {
			mapstl.put(key1, new HashMap<String, Map<String, List<String>>>());
		}
		for (int a = 0; a < types.length; a++) {
			mapstl.get(types[a]).put("normal",new HashMap<String, List<String>>());
			mapstl.get(types[a]).get("normal").put("color", new ArrayList<String>());
			mapstl.get(types[a]).get("normal").get("color").add(typeColor.get(types[a]));
		}
		String firsttime = null;
		int valuecha = 0;
		//如果时间为空
		/*if (StringUtils.isBlank(startm) && StringUtils.isBlank(endtm)) {
			long timeDefault = DataType.TIMEZONE;
			long endTime = System.currentTimeMillis();
			long stardTime = endTime - timeDefault;
			String endtme = TimeBdzd.getLongDatetoStr(endTime + "");
			firsttime = TimeBdzd.getLongDatetoStr(stardTime + "");
			int xtYear = TimeBdzd.getDateYear(endtme);
			int czYear = TimeBdzd.getDateYear(firsttime);
			int mon1 = TimeBdzd.getDateMonth(endtme);
			int mon2 = TimeBdzd.getDateMonth(firsttime);
			firsttime = czYear + "-" + mon2;
			int monc = mon1 - mon2 + 1;
			int dhc = (xtYear - czYear) * 12 + monc;
			valuecha = java.lang.Math.abs(dhc);
		}
		//2016-04精确到月
		if (StringUtils.isNotBlank(startm) && StringUtils.isNotBlank(endtm)&& startm.length() < 8 && endtm.length() < 8) {
			int xsYear = TimeBdzd.getDateYeardd(startm);
			int jsYear = TimeBdzd.getDateYeardd(endtm);
			int xsMonth = TimeBdzd.getDateMonthdd(startm);
			int jsMonth = TimeBdzd.getDateMonthdd(endtm);
			int moncz = jsMonth - xsMonth + 1;
			int dhc = (jsYear - xsYear) * 12 + moncz;
			valuecha = java.lang.Math.abs(dhc);
			firsttime = startm;
		}
		//精确到天
		if (StringUtils.isNotBlank(startm) && StringUtils.isNotBlank(endtm) && startm.length() > 8 && endtm.length() > 8
				&& startm.length() < 14 && endtm.length() < 14) {
			// title.set(0, "TimeLine");
			int xsday = TimeBdzd.getDates(startm);
			int jsday = TimeBdzd.getDates(endtm);
			int daycz = jsday - xsday + 1;
			valuecha = java.lang.Math.abs(daycz);
			firsttime = startm;
		}
		//精确到小时
		if (StringUtils.isNotBlank(startm) && StringUtils.isNotBlank(endtm)&& startm.length() > 14 && endtm.length() > 14) {
			// title.set(0, "TimeLine");
			int xsHour = TimeBdzd.getHoursl(startm);
			int jsHour = TimeBdzd.getHoursl(endtm);
		        jsHour = jsHour + 1;
			int daycz = jsHour - xsHour;
			valuecha = java.lang.Math.abs(daycz);
			firsttime = startm;
		}*/
		try {
			result = echartsBarDao.getVertexYearEventsById(id, types,firsttime, valuecha);
			//获得时间范围
			List<Long> timeList = result.get("time");
			result.remove("time");
			Long minTime = timeList.get(0);
			Long maxTime = timeList.get(1);
			System.out.println(new Date(minTime));
			System.out.println(new Date(maxTime));
			Calendar minCal = Calendar.getInstance();
			minCal.setTime(new Date(minTime));
			Calendar maxCal = Calendar.getInstance();
			maxCal.setTime(new Date(maxTime));
			int minYear = minCal.get(Calendar.YEAR);
			int minMonth = minCal.get(Calendar.MONTH);
			int minDay = minCal.get(Calendar.DATE);
			int minHour = minCal.get(Calendar.HOUR);
			int maxYear = maxCal.get(Calendar.YEAR);
			int maxMonth = maxCal.get(Calendar.MONTH);
			int maxDay = maxCal.get(Calendar.DATE);
			int maxHour = maxCal.get(Calendar.HOUR);
			//划分x轴时间段
			String dateFormat = "";
			if(maxYear>minYear){
				if((maxYear-minYear)>1){
					valuecha = maxYear-minYear + 1;
					dateFormat = "yyyy";
					firsttime = new SimpleDateFormat("yyyy").format(new Date(minTime));
				}else{
					valuecha = (maxYear-minYear)*12 + (maxMonth-minMonth+1);
					dateFormat = "yyyy-MM";
					firsttime = new SimpleDateFormat("yyyy-MM").format(new Date(minTime));
				}
			}else if(maxYear==minYear&&maxMonth!=minMonth){
				valuecha = maxMonth - minMonth+1;
				dateFormat = "yyyy-MM";
				firsttime = new SimpleDateFormat("yyyy-MM").format(new Date(minTime));
			}else if(maxYear==minYear&&maxMonth==minMonth&&maxDay!=minDay){
				valuecha = maxDay-minDay+1;
				dateFormat = "yyyy-MM-dd";
				firsttime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(minTime));
			}else if(maxYear==minYear&&maxMonth==minMonth&&maxDay==minDay){
				valuecha = maxHour-minHour+1;
				dateFormat = "yyyy-MM-dd HH";
				firsttime = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date(minTime));
			}else{
				valuecha = maxYear-minYear + 1;
				dateFormat = "yyyy";
				firsttime = new SimpleDateFormat("yyyy").format(new Date(minTime));
			}
			
			//填充柱状图数据
			Set<String> set = result.keySet();
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				//List<Long> value = result.get(key);
				Map<String, Map<String, List<String>>> map = mapstl.get(key);
				maps.put(key, new Series());
				maps.get(key).setName(key);
				maps.get(key).setType("bar");
				if (firsttime.length() < 8) { //&& valuecha <= 12) {
					//maps.get(key).setBarCategoryGap("50%");//类目间柱形距离,默认为类目间距的20%
					maps.get(key).setBarWidth(10);
				}
				if (firsttime.length() > 8) {
					maps.get(key).setBarCategoryGap("10%");
					maps.get(key).setBarWidth(8);
				}
				maps.get(key).setItemStyle(map);
				//maps.get(key).setData(value);
				//legend.add(key);
			}
			
			//整合count在时间轴上的统计
			if (firsttime.length() < 8) {
				xcategory = MonthXCategory.getMonthXcategory11(firsttime,valuecha);
			}
			if (firsttime.length() > 8 && firsttime.length() < 14) {
				xcategory = MonthXCategory.getMonthXcategory21(firsttime,valuecha);
			}
			if (firsttime.length() > 14) {
				xcategory = MonthXCategory.getMonthXcategory22(firsttime,valuecha);
			}
			int xCateSize = xcategory.size();
			long partTime = Math.abs((maxTime-minTime)/xCateSize);
			SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Series mapSeries = maps.get(key);
				List<Long> timeValue = result.get(key);
				List<Long> dataList = new ArrayList<Long>();
				for(int i=0;i<xCateSize;i++){
					dataList.add(0l);
				}
				long tempTime = 0;
				if(null!=timeValue&&timeValue.size()>0){
					for(int i=0;i<xCateSize;i++){
						Date xCurrentDate = sf.parse(xcategory.get(i));
						Date xNextDate = null;
						if(i<(xCateSize-1)){
							xNextDate = sf.parse(xcategory.get(i+1));
						}
						tempTime = xCurrentDate.getTime();
						long typeCount = 0;
						for(Long time:timeValue){
							if(i<(xCateSize-1)){
								if(time>=tempTime&&time<xNextDate.getTime()){
									typeCount++;
									if(!legend.contains(key)){
										legend.add(key);
									}
								}
							}else{
								if(time>=tempTime){
									typeCount++;
									if(!legend.contains(key)){
										legend.add(key);
									}
								}
							}
							
						}
						tempTime = tempTime+partTime;
						dataList.set(i, typeCount);
					}
				}
				mapSeries.setData(dataList);
				seList.add(mapSeries);
			}
			data = new EchartsData1(valuecha, title, legend, xcategory, seList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJson1(data);
	}
	
	

	/**
	 * 按年份查询上年事件类型
	 * @param lastY
	 * @param types
	 * @param yearcha
	 * @return
	 */
/*	public String getLastYearEvents(String lastY, Long id, String[] types,Integer yearcha) throws Exception {
		EchartsData1 data = null;
		Map<String, List<Long>> result = null;
		List<String> title = new ArrayList<String>();
		List<String> legend = new ArrayList<String>();
		List<Series> seList = new ArrayList<Series>();
		Map<String, Series> maps = new HashMap<String, Series>();
		title.add("TimeLine");
		Map<String, String> typeColor = BarColors.getTypeColor();
		Map<String, Map<String, Map<String, List<String>>>> mapstl = new  LinkedHashMap<String, Map<String, Map<String, List<String>>>>();
		for (String key1 : types) {
			mapstl.put(key1, new HashMap<String, Map<String, List<String>>>());
		}
		for (int a = 0; a < types.length; a++) {
			mapstl.get(types[a]).put("normal",new HashMap<String, List<String>>());
			mapstl.get(types[a]).get("normal").put("color", new ArrayList<String>());
			mapstl.get(types[a]).get("normal").get("color").add(typeColor.get(types[a]));
		}
		try {
			result = echartsBarDao.getLastYearEvents(lastY, id, types, yearcha);
			Set<String> set = result.keySet();
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				List<Long> value = result.get(key);
				Map<String, Map<String, List<String>>> map = mapstl.get(key);
				maps.put(key, new Series());
				maps.get(key).setName(key);
				maps.get(key).setType("bar");
				maps.get(key).setBarCategoryGap("50%");
				maps.get(key).setBarWidth(10);
				maps.get(key).setItemStyle(map);
				maps.get(key).setData(value);
				legend.add(key);
			}
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Series value = maps.get(key);
				seList.add(value);
			}
			List<String> xcategory = MonthXCategory.getMonthXcategory11(lastY,yearcha);
			data = new EchartsData1(null, title, legend, xcategory, seList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJson1(data);
	}*/

	/**
	 * 按年份查询下年事件类型
	 * 
	 * @param nextY
	 * @param id
	 * @param types
	 * @param yearcha
	 * @return
	 * 
	 */
/*	public String getNextYearEvents(String nextY, Long id, String[] types,Integer yearcha) throws Exception {
		EchartsData1 data = null;
		Map<String, List<Long>> result = null;
		List<String> title = new ArrayList<String>();
		List<String> legend = new ArrayList<String>();
		List<Series> seList = new ArrayList<Series>();
		Map<String, Series> maps = new HashMap<String, Series>();
		title.add("TimeLine");
		Map<String, String> typeColor = BarColors.getTypeColor();
		Map<String, Map<String, Map<String, List<String>>>> mapstl = new  LinkedHashMap<String, Map<String, Map<String, List<String>>>>();
		for (String key1 : types) {
			mapstl.put(key1, new HashMap<String, Map<String, List<String>>>());
		}
		for (int a = 0; a < types.length; a++) {
			mapstl.get(types[a]).put("normal",new HashMap<String, List<String>>());
			mapstl.get(types[a]).get("normal").put("color", new ArrayList<String>());
			mapstl.get(types[a]).get("normal").get("color").add(typeColor.get(types[a]));
		}
		try {
			result = echartsBarDao.getNextYearEvents(nextY, id, types, yearcha);
			Set<String> set = result.keySet();
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				List<Long> value = result.get(key);
				Map<String, Map<String, List<String>>> map = mapstl.get(key);
				maps.put(key, new Series());
				maps.get(key).setName(key);
				maps.get(key).setType("bar");
				maps.get(key).setBarCategoryGap("50%");
				maps.get(key).setBarWidth(10);
				maps.get(key).setItemStyle(map);
				maps.get(key).setData(value);
				legend.add(key);
			}
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Series value = maps.get(key);
				seList.add(value);
			}
			List<String> xcategory = MonthXCategory.getMonthXcategory11(nextY,yearcha);
			data = new EchartsData1(null, title, legend, xcategory, seList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJson1(data);
	}*/

	/**
	 * 按月份查询事件
	 * 
	 * @param id
	 * @param types
	 * @param monthfriday
	 * @param monthendday
	 * @return
	 */
	public String getVertexMonthEvents(String monthfriday, String monthendday,long id, String[] types) throws Exception {
		EchartsData1 data = null;
		List<String> title = new ArrayList<String>();
		List<String> legend = new ArrayList<String>();
		Map<String, List<Long>> result = null;
		List<Series> seList = new ArrayList<Series>();
		Map<String, Series> maps = new HashMap<String, Series>();
		title.add("TimeLine");
		Map<String, String> typeColor = BarColors.getTypeColor();
		Map<String, Map<String, Map<String, List<String>>>> mapstl = new  LinkedHashMap<String, Map<String, Map<String, List<String>>>>();
		for (String key1 : types) {
			mapstl.put(key1, new HashMap<String, Map<String, List<String>>>());
		}
		for (int a = 0; a < types.length; a++) {
			mapstl.get(types[a]).put("normal",new HashMap<String, List<String>>());
			mapstl.get(types[a]).get("normal").put("color", new ArrayList<String>());
			mapstl.get(types[a]).get("normal").get("color").add(typeColor.get(types[a]));
		}
		try {
			result = echartsBarDao.getVertexMonthEventEmail(monthfriday,monthendday, id, types);
			Set<String> set = result.keySet();
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				List<Long> value = result.get(key);
				Map<String, Map<String, List<String>>> map = mapstl.get(key);
				maps.put(key, new Series());
				maps.get(key).setName(key);
				maps.get(key).setType("bar");
				maps.get(key).setBarCategoryGap("10%");
				maps.get(key).setBarWidth(8);
				maps.get(key).setItemStyle(map);
				maps.get(key).setData(value);
				legend.add(key);
			}
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Series value = maps.get(key);
				seList.add(value);
			}
			// 设置X轴值
			List<String> xcategory = MonthXCategory.getDatetoMonthLong21(monthfriday);
			data = new EchartsData1(null, title, legend, xcategory, seList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJson1(data);
	}

	/**
	 * 按天查询事件
	 * 2015-06-20
	 * @return
	 */
	public String getVertexDayEvents(String day,String[] types,String eventTimes)throws Exception {//String day, long id, String[] types
		EchartsData1 data = null;
		List<String> title = new ArrayList<String>();
		List<String> legend = new ArrayList<String>();
		String[] betweenTimes = null;
		String dayNoHMS = "";
		if(null!=day&&!"".equals(day)){
			int theIndex = day.indexOf("00:00:00");
			if(theIndex!=-1){
				dayNoHMS = day.substring(0, theIndex);
				String endDay = dayNoHMS+"23:59:59";
				betweenTimes = new String[]{day,endDay};
			}
		}
		Map<String, List<Long>> result = null;
		List<Series> seList = new ArrayList<Series>();
		Map<String, Series> maps = new HashMap<String, Series>();
		title.add("TimeLine");
		Map<String, String> typeColor = BarColors.getTypeColor();
		Map<String, Map<String, Map<String, List<String>>>> mapstl = new  LinkedHashMap<String, Map<String, Map<String, List<String>>>>();
		for (String key1 : types) {
			mapstl.put(key1, new HashMap<String, Map<String, List<String>>>());
		}
		for (int a = 0; a < types.length; a++) {
			mapstl.get(types[a]).put("normal",new HashMap<String, List<String>>());
			mapstl.get(types[a]).get("normal").put("color", new ArrayList<String>());
			mapstl.get(types[a]).get("normal").get("color").add(typeColor.get(types[a]));
		}
		try {
			result = echartsBarDao.getPageAllNodeEvents(types,eventTimes,betweenTimes);
			result.remove("time");
			Set<String> set = result.keySet();
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				List<Long> value = result.get(key);
				Map<String, Map<String, List<String>>> map = mapstl.get(key);
				maps.put(key, new Series());
				maps.get(key).setName(key);
				maps.get(key).setType("bar");
				maps.get(key).setBarCategoryGap("10%");
				maps.get(key).setBarWidth(8);
				maps.get(key).setItemStyle(map);
				maps.get(key).setData(value);
				if(result.get(key).size()>0){
					legend.add(key);
				}
			}
			
			// 设置X轴坐标值
			List<String> xcategory = MonthXCategory.getMonthXcategory22(day, 24);
			/*for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Series value = maps.get(key);
				seList.add(value);
			}*/
			
			int xCateSize = xcategory.size();
			long partTime = Math.abs((24*60*60*1000)/xCateSize);

			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Series mapSeries = maps.get(key);
				List<Long> timeValue = result.get(key);
				List<Long> dataList = new ArrayList<Long>();
				for(int i=0;i<xCateSize;i++){
					dataList.add(0l);
				}
				long tempTime = 0;
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if(null!=timeValue&&timeValue.size()>0){
					for(int i=0;i<xCateSize;i++){
						Date xCurrentDate = sf.parse(dayNoHMS+xcategory.get(i));
						Date xNextDate = null;
						if(i<(xCateSize-1)){
							xNextDate = sf.parse(dayNoHMS+xcategory.get(i+1));
						}
						tempTime = xCurrentDate.getTime();
						long typeCount = 0;
						for(Long time:timeValue){
							if(i<(xCateSize-1)){
								if(time>=tempTime&&time<xNextDate.getTime()){
									typeCount++;
									if(!legend.contains(key)){
										legend.add(key);
									}
								}
							}else{
								if(time>=tempTime){
									typeCount++;
									if(!legend.contains(key)){
										legend.add(key);
									}
								}
							}
							
						}
						tempTime = tempTime+partTime;
						dataList.set(i, typeCount);
					}
				}
				mapSeries.setData(dataList);
				seList.add(mapSeries);
			}
			data = new EchartsData1(null, title, legend, xcategory, seList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJson1(data);
	}

	private String getJson1(EchartsData1 data) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(data);
		return jsonStr;
	}

	/**
	 * 根据id查询邮件信息
	 * @param id
	 * @return
	 */
	public String getEmailById(long id) throws Exception {
		SearchDetailDao searchDetailDao = new SearchDetailDao();
		Email ed = searchDetailDao.getEmailByID(id);
		// Email ed = TestEchartsData.getEmailById1(id);
//		if (ed.getSendtime() != null) {
//			String lStr = TimeBdzd.getLongDatetoStrl(ed.getSendtime() + "");
//			ed.setSendtime(lStr);
//		}
		Gson gson = new Gson();
		String jsonStr = gson.toJson(ed);
		return jsonStr;
	}

}
