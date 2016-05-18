package com.integrity.dataSmart.timeLine.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.timeLine.util.TimeBdzd;
import com.integrity.dataSmart.util.titan.TitanGraphUtil;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;

public class EchartsBarDao {
	// private Logger logger = Logger.getLogger(this.getClass());
	private TitanGraph graph = TitanGraphUtil.getInstance().getTitanGraph();

	public Map<String, List<Long>> getPageAllNodeEvents(String[] types, String eventTimesStr,String[] betweenTimes) throws Exception {
		Map<String, List<Long>> mapps = new LinkedHashMap<String, List<Long>>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (String key : types) {
			mapps.put(key, new ArrayList<Long>());
		}
		Long minEventTime = 0l;
		Long maxEventTime = 0l;
		Long bStartTime = 0l;
		Long bEndTime = 0l;
		if(null!=betweenTimes){
			bStartTime = sf.parse(betweenTimes[0].trim()).getTime();
			bEndTime = sf.parse(betweenTimes[1].trim()).getTime();
		}
		String[] eventTimes = eventTimesStr.split(",");
		for(String eventTime:eventTimes){
			eventTime = eventTime.trim();
			for(int a = 0; a < types.length; a++){
				String tempEql = types[a]+"=";
				int indexInt = eventTime.indexOf(tempEql);				
				if(indexInt!=-1){
					int length = tempEql.length();
					String currentTimeStr = eventTime.substring(length,eventTime.length());
					Long currentTime = sf.parse(currentTimeStr).getTime();
					if(null!=betweenTimes&&bStartTime!=0&&bEndTime!=0){
						if(currentTime<bStartTime){
							continue;
						}else if(currentTime>bEndTime){
							continue;
						}
					}
					if(minEventTime==0l){
						minEventTime = currentTime;
					}else if(currentTime.compareTo(minEventTime)<0){
						minEventTime = currentTime;
					}
					if(maxEventTime==0l){
						maxEventTime = currentTime;
					}else if(currentTime.compareTo(maxEventTime)>0){
						maxEventTime = currentTime;
					}
					mapps.get(types[a]).add(currentTime);
				}
			}
		}
		List<Long> timeList = new ArrayList<Long>();
		timeList.add(minEventTime);
		timeList.add(maxEventTime);
		mapps.put("time",timeList);
		return mapps;
	}
	/**
	 * 按Id和类型查询多个年事件数
	 * @param Id
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<Long>> getVertexYearEventsById(long id,String[] types, String firsttime, int valuecha) throws Exception {
		Map<String, List<Long>> mapps = new LinkedHashMap<String, List<Long>>();
		for (String key : types) {
			mapps.put(key, new ArrayList<Long>());
		}
		Long minEventTime = 0l;
		Long maxEventTime = 0l;
		Vertex vert = graph.getVertex(id);
		for (int a = 0; a < types.length; a++) {
			String[] labelByType = DataType.getLabelByType(types[a]);
			Iterable<Vertex> results = vert.query().labels(labelByType).vertices();
			for (Vertex result : results) {
				Long currentTime = (Long)result.getProperty("time");
				if(minEventTime==0l){
					minEventTime = currentTime;
				}else if(currentTime.compareTo(minEventTime)<0){
					minEventTime = currentTime;
				}
				if(maxEventTime==0l){
					maxEventTime = currentTime;
				}else if(currentTime.compareTo(maxEventTime)>0){
					maxEventTime = currentTime;
				}
				System.out.println(types[a]+":"+new Date(currentTime));
				mapps.get(types[a]).add(currentTime);
	        }
			/*long Ccounts = vert.query().labels(labelByType).count();
		    mapps.get(types[a]).add(Ccounts);*/
			
		}
		List<Long> timeList = new ArrayList<Long>();
		timeList.add(minEventTime);
		timeList.add(maxEventTime);
		mapps.put("time",timeList);
		/*if (firsttime.length() < 8) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date dt = sdf.parse(firsttime);
			for (int i = 0; i < valuecha; i++) {
				Calendar rightNow = Calendar.getInstance();
				Calendar rightNow1 = Calendar.getInstance();
				rightNow.setTime(dt);
				rightNow.add(Calendar.MONTH, i);
				Date dtt = rightNow.getTime();
				String reStrt = sdf.format(dtt);
				rightNow1.setTime(dt);
				rightNow1.add(Calendar.MONTH, i + 1);
				Date dtt1 = rightNow1.getTime();
				String reStrt1 = sdf.format(dtt1);
				String tfridate = reStrt + "-" + "01";
				String tendate = reStrt1 + "-" + "01";
				//System.out.println(tfridate+"  "+tendate);
				long Jfridatel = 0;
				long Jenddatel = 0;
				if (StringUtils.isNotBlank(tfridate)&& StringUtils.isNotBlank(tendate)) {
					Jfridatel = TimeBdzd.getStrDatetoLong(tfridate);
					Jenddatel = TimeBdzd.getStrDatetoLong(tendate) - 1;
				}
				for (int a = 0; a < types.length; a++) {
					String[] labelByType = DataType.getLabelByType(types[a]);
					long Ccounts = vert.query().labels(labelByType).interval("eventtime", Jfridatel, Jenddatel).count();
					mapps.get(types[a]).add(Ccounts);
				}
			  }
		   }*/
		/*if (firsttime.length() > 8 && firsttime.length() < 14) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = df.parse(firsttime);
			long startDay = 0;
			long endDay = 0;
			for (int i = 0; i < valuecha; i++) {
				Calendar rightNow = Calendar.getInstance();
				Calendar rightNow1 = Calendar.getInstance();
				rightNow.setTime(dt);
				rightNow.add(Calendar.DATE, i);
				Date dtt = rightNow.getTime();
				String reStrt = df.format(dtt);
				startDay = TimeBdzd.getStrDatetoLong(reStrt);
				rightNow1.setTime(dt);
				rightNow1.add(Calendar.DATE, i + 1);
				Date dtt1 = rightNow1.getTime();
				String reStrt1 = df.format(dtt1);
				endDay = TimeBdzd.getStrDatetoLong(reStrt1) - 1;
				//System.out.println(reStrt+"  "+reStrt1);
				for (int j = 0; j < types.length; j++) {
					String[] labelByType = DataType.getLabelByType(types[j]);
					long Ccount = vert.query().labels(labelByType).interval("eventtime", startDay, endDay).count();
					mapps.get(types[j]).add(Ccount);
				}
			}
		}*/
		/*if (firsttime.length() > 14) {
			DateFormat df = new SimpleDateFormat(DataType.DATEFORMATSTR);
			Date dt = df.parse(firsttime);
			long startHour = 0;
			long endHour = 0;
			for (int i = 0; i < valuecha; i++) {
				Calendar rightNow = Calendar.getInstance();
				Calendar rightNow1 = Calendar.getInstance();
				rightNow.setTime(dt);
				rightNow.add(Calendar.HOUR_OF_DAY, i);
				Date dtt = rightNow.getTime();
				String reStrt = df.format(dtt);
				startHour = TimeBdzd.getStrDatetoLongl(reStrt);
				rightNow1.setTime(dt);
				rightNow1.add(Calendar.HOUR_OF_DAY, i + 1);
				Date dtt1 = rightNow1.getTime();
				String reStrt1 = df.format(dtt1);
				endHour = TimeBdzd.getStrDatetoLongl(reStrt1) - 1;
				for (int j = 0; j < types.length; j++) {
					String[] labelByType = DataType.getLabelByType(types[j]);
					long Ccount = vert.query().labels(labelByType).interval("eventtime", startHour, endHour).count();
					mapps.get(types[j]).add(Ccount);
				}
			}
		}*/
		return mapps;
	}

	/**
	 * 查询上一年多个年事件数
	 * @param lastY
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<Long>> getLastYearEvents(String lastY, long id,String[] types, Integer yearcha) throws Exception {
		Map<String, List<Long>> mapps = new  LinkedHashMap<String, List<Long>>();
		for (String key : types) {
			mapps.put(key, new ArrayList<Long>());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dt = sdf.parse(lastY);
		Vertex vert = graph.getVertex(id);
		for (int i = 0; i < yearcha; i++) {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.MONTH, i);
			Date dtt = rightNow.getTime();
			String reStrt = sdf.format(dtt);
			Calendar rightNow1 = Calendar.getInstance();
			rightNow1.setTime(dt);
			rightNow1.add(Calendar.MONTH, i + 1);
			Date dtt1 = rightNow1.getTime();
			String reStrt1 = sdf.format(dtt1);
			// System.out.println("last-----"+reStrt);
			String tfridate = reStrt + "-" + "01";
			String tendate = reStrt1 + "-" + "01";
			long Jfridatel = 0;
			long Jenddatel = 0;
			if (StringUtils.isNotBlank(tfridate)&& StringUtils.isNotBlank(tendate)) {
				Jfridatel = TimeBdzd.getStrDatetoLong(tfridate);
				Jenddatel = TimeBdzd.getStrDatetoLong(tendate) - 1;
			}
			for (int a = 0; a < types.length; a++) {
				String[] labelByType = DataType.getLabelByType(types[a]);
				long Ccounts = vert.query().labels(labelByType).interval("eventtime", Jfridatel, Jenddatel).count();
				mapps.get(types[a]).add(Ccounts);
			}
		}
		return mapps;

	}

	/**
	 * 查询下一多个年事件数
	 * 
	 * @param nextY
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<Long>> getNextYearEvents(String nextY, long id,String[] types, Integer yearcha) throws Exception {
		Map<String, List<Long>> mapps = new  LinkedHashMap<String, List<Long>>();
		for (String key : types) {
			mapps.put(key, new ArrayList<Long>());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dt = sdf.parse(nextY);
		Vertex vert = graph.getVertex(id);
		for (int i = 0; i < yearcha; i++) {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.MONTH, i);
			Date dtt = rightNow.getTime();
			String reStrt = sdf.format(dtt);
			Calendar rightNow1 = Calendar.getInstance();
			rightNow1.setTime(dt);
			rightNow1.add(Calendar.MONTH, i + 1);
			Date dtt1 = rightNow1.getTime();
			String reStrt1 = sdf.format(dtt1);
			// System.out.println("next-----"+reStrt);
			String tfridate = reStrt + "-" + "01";
			String tendate = reStrt1 + "-" + "01";
			long Jfridatel = 0;
			long Jenddatel = 0;
			if (StringUtils.isNotBlank(tfridate)&& StringUtils.isNotBlank(tendate)) {
				Jfridatel = TimeBdzd.getStrDatetoLong(tfridate);
				Jenddatel = TimeBdzd.getStrDatetoLong(tendate) - 1;
			}
			for (int a = 0; a < types.length; a++) {
				String[] labelByType = DataType.getLabelByType(types[a]);
				long Ccounts = vert.query().labels(labelByType).interval("eventtime", Jfridatel, Jenddatel).count();
				mapps.get(types[a]).add(Ccounts);
			}
		}
		return mapps;
	}

	/**
	 * 查询月EventEmail节点
	 * @param monthfriday
	 * @param monthendday
	 * @return
	 * 
	 */
	public Map<String, List<Long>> getVertexMonthEventEmail(String monthfriday,String monthendday, long id, String[] types) throws Exception {
		Map<String, List<Long>> mapps = new  LinkedHashMap<String, List<Long>>();
		for (String key : types) {
			mapps.put(key, new ArrayList<Long>());
		}
		Vertex vert = graph.getVertex(id);
		int sdyear = TimeBdzd.getDateYear(monthfriday);
		int sdmounth = TimeBdzd.getDateMonth(monthfriday);
		int sddate = TimeBdzd.getDates(monthfriday);
		int edyear = TimeBdzd.getDateYear(monthendday);
		int edmounth = TimeBdzd.getDateMonth(monthendday);
		int eddate = TimeBdzd.getDates(monthendday);

		Calendar start = Calendar.getInstance();
		start.set(sdyear, sdmounth - 1, sddate);
		Long startTIme = start.getTimeInMillis();
		Calendar end = Calendar.getInstance();
		end.set(edyear, edmounth - 1, eddate);
		Long endTime = end.getTimeInMillis();

		Long oneDay = 1000 * 60 * 60 * 24l;
		Long time = startTIme;
		long dateks = 0;
		long datejs = 0;
		while (time <= endTime) {
			Date d = new Date(time);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String datt = df.format(d);
			if (StringUtils.isNotBlank(datt)) {
				dateks = TimeBdzd.getStrDatetoLong(datt);
			}
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(d);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			Date dt1 = rightNow.getTime();
			String reStr1 = df.format(dt1);
			//System.out.println(datt+"  "+reStr1);
			if (StringUtils.isNotBlank(reStr1)) {
				datejs = TimeBdzd.getStrDatetoLong(reStr1) - 1;
			}
			time += oneDay;
			for (int i = 0; i < types.length; i++) {
				String[] labelByType = DataType.getLabelByType(types[i]);
				long Ccounts = vert.query().labels(labelByType).interval("eventtime", dateks, datejs).count();
				mapps.get(types[i]).add(Ccounts);
			}
		}
		return mapps;
	}

	/**
	 * 查询日Events
	 * @param day
	 * @return
	 * 
	 */
	public Map<String, List<Long>> getVertexDayEvents(String day, long id,
			String[] types) throws Exception {
		Map<String, List<Long>> mapps = new  LinkedHashMap<String, List<Long>>();
		for (String key : types) {
			mapps.put(key, new ArrayList<Long>());
		 }
		Vertex vert = graph.getVertex(id);
		int sdyear = TimeBdzd.getDateYearl(day);
		int sdmounth = TimeBdzd.getDateMonthl(day);
		int sddate = TimeBdzd.getDatesl(day);
		int edyear = TimeBdzd.getDateYearl(day);
		int edmounth = TimeBdzd.getDateMonthl(day);
		int eddate = TimeBdzd.getDatesl(day);

		Calendar start = Calendar.getInstance();
		start.set(sdyear, sdmounth - 1, sddate, 00, 00, 00);
		long startTIme = start.getTimeInMillis();
		Calendar end = Calendar.getInstance();
		end.set(edyear, edmounth - 1, eddate, 23, 59, 59);
		long endTime = end.getTimeInMillis();

		long oneHour = 1000 * 60 * 60;
		long time = startTIme;
		long dateks = 0;
		long datejs = 0;
		while (time <= endTime) {
			Date d = new Date(time);
			DateFormat df = new SimpleDateFormat(DataType.DATEFORMATSTR);
			String datt = df.format(d);
			if (StringUtils.isNotBlank(datt)) {
				dateks = TimeBdzd.getStrDatetoLongl(datt);
			}
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(d);
			rightNow.add(Calendar.HOUR_OF_DAY, 1);
			Date dt1 = rightNow.getTime();
			String reStr1 = df.format(dt1);
			if (StringUtils.isNotBlank(reStr1)) {
				datejs = TimeBdzd.getStrDatetoLongl(reStr1) - 1;
			}
			//System.out.println(datt +"  " + reStr1);
			time += oneHour;
			for (int i = 0; i < types.length; i++) {
				String[] labelByType = DataType.getLabelByType(types[i]);
				long Ccounts = vert.query().labels(labelByType).interval("eventtime", dateks, datejs).count();
				mapps.get(types[i]).add(Ccounts);
			 }
		}
		return mapps;
	}

}
