package com.integrity.dataSmart.timeLine.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.integrity.dataSmart.particple.code.Participles;
import com.integrity.dataSmart.timeLine.service.EchartsBarService;
import com.integrity.dataSmart.timeLine.util.TimeBdzd;

public class EchartsBarAction {

	private Logger logger = Logger.getLogger(EchartsBarAction.class);
	private static final long serialVersionUID = 1L;
	private EchartsBarService echartsBarService;
	private String month;
	private String lastYear;
	private String nextYear;
	private String startm;
	private String endtm;
	private long id;
	private String types;
	private String eventTimes;
	private String dragTime;
	private Integer yearcha;
	private long emid;
//	private String hadfile;
	private InputStream inputStream;
	private String fileName;
	private long vertexId;
	private String days;
	private String contentList;
	//private String contentList;
	
	public String queryAllNodes(){
		String[] type = null;
		if (StringUtils.isNotBlank(types)) {
			types = types.replaceAll(" ", "");
			type = types.split(",");
		}
		String strd = null;
		try {
			strd = echartsBarService.getPageAllNodeEvents(type, eventTimes,dragTime);
			if (strd!=null) {
				inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
		} catch (Exception e) {
			logger.error("查询页面所有节点事件异常", e);
		}
		return "success";
	}
	/**
	 * 按ById年统计多类事件
	 * @param Id
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public String queryYearsById() {
		String[] type = null;
		
		if (StringUtils.isNotBlank(types)) {
			type = types.split(",");
		}
		
		String strd = null;
		try {
			strd = echartsBarService.getPageAllNodeEvents(type, eventTimes,dragTime);
			if (strd!=null) {
				inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
		} catch (Exception e) {
			logger.error("根据节点id查询页面所有关联节点事件异常", e);
		}
		 //System.out.println("action年多ById-----="+strd);
		return "success";
	}

	/**
	 * 按月统计单类事件
	 * 
	 * @param month
	 * @param Id
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public String queryMonth() {
		String mhfriday = month + "-" + "01";
		Integer maxday = 0;
		String mhendday = null;
		String[] type = null;
		if (StringUtils.isNotBlank(types)) {
			type = types.split(",");
		}
		String strd = null;
		if (StringUtils.isNotBlank(month) && month.length() < 10) {
			try {
				maxday = TimeBdzd.getMonthMaxDatedd(month);
				mhendday = month + "-" + maxday;
				} catch (ParseException e) {
					logger.error("时间转化时发生异常", e);
				}
			try {
				strd = echartsBarService.getVertexMonthEvents(mhfriday,mhendday, id, type);//type);
				if (strd!=null) {
					inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
			} catch (Exception e) {
				logger.error("查询月访问量发生异常", e);
			}
		}
		//System.out.println("action------------月-----="+strd);
		return "success";
	}

	/**
	 * 按月统计单类事件
	 * 
	 * @param days
	 * @param Id
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public String queryDays() {
		String[] type = null;
		if (StringUtils.isNotBlank(types)) {
			types = types.replaceAll(" ", "");
			type = types.split(",");
		}
		if (StringUtils.isNotBlank(days)) {
			days = days + " " + "00" + ":" + "00" + ":" + "00";
		}
		
		String strd = null;
		try {
			strd = echartsBarService.getVertexDayEvents(days,type, eventTimes);
			if (strd!=null) {
				inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
		   } catch (Exception e) {
			logger.error("查询月访问量发生异常", e);
		   }
		   //System.out.println("action------------日-----="+strd);
		return "success";
	}

	/**
	 * 查看邮件详细信息
	 * 
	 * @param emid
	 * @return
	 * @throws Exception
	 */
	public String queryEmailById() {
		if (emid != 0) {
			try {
				String 	strd = echartsBarService.getEmailById(emid);
				if (strd!=null) {
					inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
				//System.out.println("打印邮件-------------"+strd);
			  } catch (Exception e) {
				logger.error("查询邮件信息发生异常", e);
			 }
		}
		return "success";
	}
	/**
	 * 通过主题获取keywords
	 * @return
	 */
	public String getFilterkeywords(){
		try {
			List<String> s = Participles.getKeyWordsList(contentList.replace("null", " "), 10);
			StringBuffer keyword=new StringBuffer();
			if(s==null){
				inputStream = new ByteArrayInputStream("".getBytes("utf-8"));
			}else{
				for (String string : s) {
					keyword.append(string+"##");
				}	
				Gson gson = new Gson();
				String jsonStr = gson.toJson(keyword.toString());
				inputStream = new ByteArrayInputStream(jsonStr.getBytes("utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("通过主题获取keywords异常", e);
		}
		return "success";
	}

	/**
	 * 下载邮件附件信息
	 * 
	 * @param emid
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public String downLoadEmail() throws UnsupportedEncodingException {
		// System.out.println("进来了--==="+fileName+"---------------"+vertexId);
//		String hadfile = "hdfs://titan176:9000/attachfiles/solr.xml";
		HttpServletRequest request = ServletActionContext.getRequest();
		String localPath = ServletActionContext.getServletContext().getRealPath("/emailFiles");
		String confgPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/config/hadoopConfg/core-site.xml");
		Configuration conf = new Configuration();
		conf.addResource(new Path(confgPath));
		String hdfsname = conf.get("fs.default.name");
		String hdfsfilepath = hdfsname+"/attachfiles/"+vertexId+"/"+fileName;
		try {
			FileSystem localFS = FileSystem.getLocal(conf);
			FileSystem hadoopFS = FileSystem.get(conf);
			Path hadPath = new Path(hdfsfilepath);
			FSDataOutputStream fsOut = localFS.create(new Path(localPath + "/"+ hadPath.getName()));
			FSDataInputStream fsIn = hadoopFS.open(hadPath);
			byte[] buf = new byte[1024];
			int readbytes = 0;
			while ((readbytes = fsIn.read(buf)) > 0) {
				fsOut.write(buf, 0, readbytes);
				fsOut.flush();
			}
			fsIn.close();
			fsOut.close();
		  } catch (IOException e) {
			  logger.error("邮件附件下载发生异常", e);
		 }
//		fileName = hadPath.getName();
		inputStream = ServletActionContext.getServletContext().getResourceAsStream("/emailFiles/" + fileName);
		try {
			String agent = request.getHeader("User-Agent").toLowerCase();
			//Firefox
			if (null != agent && -1 != agent.indexOf("firefox")) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			//其他
			} else{
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
//			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
//			fileName = new String(fileName.getBytes("UTF8"),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error("中文进行转化时发生异常", e);
		}
		return "downloadok";
	}

	
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

	public EchartsBarService getEchartsBarService() {
		return echartsBarService;
	}
	public void setEchartsBarService(EchartsBarService echartsBarService) {
		this.echartsBarService = echartsBarService;
	}

	public String getLastYear() {
		return lastYear;
	}
	public void setLastYear(String lastYear) {
		this.lastYear = lastYear;
	}

	public String getNextYear() {
		return nextYear;
	}
	public void setNextYear(String nextYear) {
		this.nextYear = nextYear;
	}

	public String getStartm() {
		return startm;
	}
	public void setStartm(String startm) {
		this.startm = startm;
	}

	public String getEndtm() {
		return endtm;
	}
	public void setEndtm(String endtm) {
		this.endtm = endtm;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}

	public Integer getYearcha() {
		return yearcha;
	}
	public void setYearcha(Integer yearcha) {
		this.yearcha = yearcha;
	}

	public long getEmid() {
		return emid;
	}
	public void setEmid(long emid) {
		this.emid = emid;
	}
	
	public void setEventTimes(String eventTimes) {
		this.eventTimes = eventTimes;
	}

/**
	public String getHadfile() {
		return hadfile;
	}
	public void setHadfile(String hadfile) {
		this.hadfile = hadfile;
	}
**/
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public long getVertexId() {
		return vertexId;
	}
	public void setVertexId(long vertexId) {
		this.vertexId = vertexId;
	}

	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}


	public String getContentList() {
		return contentList;
	}

	public void setContentList(String contentList) {
		this.contentList = contentList;
	}
	public void setDragTime(String dragTime) {
		this.dragTime = dragTime;
	}
	
	
	/**
	 * 查询上条事件
	 * @param lastYear
	 * @param Id
	 * @param types
	 * @return
	 * @throws Exception
	 */
/*	public String queryLastYears() {
		String strd = null;
		String[] type = null;
		modify by hanxue start
		if (StringUtils.isNotBlank(types)) {
			type = types.split(",");
		}
		if (StringUtils.isEmpty(types)) {
			type = DataType.ALLTYPE;
		}
		 
		String[] searchType = DataType.ALLTYPE;
		Arrays.sort(searchType);
		if (StringUtils.isNotBlank(types)) {
			type = types.split(",");
			String lastType = type[type.length-1];
			int indexNum = searchType.length-1;
			for(int i = 0;i<searchType.length;i++){
				if(searchType[i].equals(lastType)){
					indexNum = i;
					break;
				}
			}
			for(int j = indexNum;j<(searchType.length-1);j++){
				String tempStr = searchType[j];
				searchType[j] = searchType[j+1];
				searchType[j+1] = tempStr;
			}
		}
		
		 modify by hanxue end
		 
		
		if (StringUtils.isNotBlank(lastYear) && lastYear.length() < 8) {
			int lasty = 0;
			int lastm = 0;
			try {
				lasty = TimeBdzd.getDateYeardd(lastYear);
				lastm = TimeBdzd.getDateMonthdd(lastYear) - yearcha;
			} catch (ParseException e1) {
				logger.error("时间转化发生异常", e1);
			}
			String strd1 = lasty + "-" + lastm;
			try {
				strd = echartsBarService.getLastYearEvents(strd1, id, searchType,yearcha);//(strd1, id, type,yearcha);
				if (strd!=null) {
					inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
			} catch (Exception e) {
				logger.error("查询年访问量发生异常", e);
			}
		}
		if (StringUtils.isNotBlank(lastYear) && lastYear.length() > 8 && lastYear.length() < 14) {
			int monthYear = 0;
			int monthMth = 0;
			try {
				monthYear = TimeBdzd.getDateYear(lastYear);
				monthMth = TimeBdzd.getDateMonth(lastYear);
			} catch (ParseException e1) {
				logger.error("时间转化发生异常", e1);
			}
			int dam = monthMth - 1;
			String strd2 = monthYear + "-" + dam;
			int monMaxDate = 0;
			try {
				monMaxDate = TimeBdzd.getMonthMaxDatedd(strd2);
			} catch (ParseException e1) {
				logger.error("时间转化发生异常", e1);
			}
			String monfriday = strd2 + "-" + "01";
			String monendday = strd2 + "-" + monMaxDate;
			try {
				strd = echartsBarService.getVertexMonthEvents(monfriday,monendday, id,searchType); //type);
				if (strd!=null) {
					inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
			} catch (Exception e) {
				logger.error("查询月访问量发生异常", e);
			}
		}
		if (StringUtils.isNotBlank(lastYear) && lastYear.length() > 14) {
			try {
				strd = echartsBarService.getVertexDayEvents(lastYear, id,searchType);// type);
				if (strd!=null) {
					inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
			} catch (Exception e) {
				logger.error("查询月访问量发生异常", e);
			}
		}
		 //System.out.println("action-----------------last----------="+strd);
		return "success";
	}*/

	/**
	 * 查询下条事件
	 * @param nextYear
	 * @param Id
	 * @param types
	 * @return
	 * @throws Exception
	 */
/*	public String queryNextYears() {
		String strd = null;
		String[] type = null;
		modify by hanxue start
		if (StringUtils.isNotBlank(types)) {
			type = types.split(",");
		}
		if (StringUtils.isEmpty(types)) {
			type = DataType.ALLTYPE;
		}
		 
		String[] searchType = DataType.ALLTYPE;
		Arrays.sort(searchType);
		if (StringUtils.isNotBlank(types)) {
			type = types.split(",");
			String lastType = type[type.length-1];
			int indexNum = searchType.length-1;
			for(int i = 0;i<searchType.length;i++){
				if(searchType[i].equals(lastType)){
					indexNum = i;
					break;
				}
			}
			for(int j = indexNum;j<(searchType.length-1);j++){
				String tempStr = searchType[j];
				searchType[j] = searchType[j+1];
				searchType[j+1] = tempStr;
			}
		}
		
		 modify by hanxue end
		 
		
		if (StringUtils.isNotBlank(nextYear) && nextYear.length() < 8) {
			int nexty = 0;
			int nextm = 0;
			try {
				nexty = TimeBdzd.getDateYeardd(nextYear);
				nextm = TimeBdzd.getDateMonthdd(nextYear) + 1;
			} catch (ParseException e1) {
				logger.error("时间转化发生异常", e1);
			}
			String strd1 = nexty + "-" + nextm;
			try {
				strd = echartsBarService.getNextYearEvents(strd1, id,searchType,yearcha);// type,yearcha);
				if (strd!=null) {
					inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
			} catch (Exception e) {
				logger.error("查询年访问量发生异常", e);
			}
		}
		if (StringUtils.isNotBlank(nextYear) && nextYear.length() > 8 && nextYear.length() < 14) {
			int monthYear = 0;
			int monthMth = 0;
			try {
				monthYear = TimeBdzd.getDateYear(nextYear);
				monthMth = TimeBdzd.getDateMonth(nextYear);
			} catch (ParseException e1) {
				logger.error("时间转化发生异常", e1);
			}
			int dam = monthMth + 1;
			String strd2 = monthYear + "-" + dam;
			int monMaxDate = 0;
			try {
				monMaxDate = TimeBdzd.getMonthMaxDatedd(strd2);
			} catch (ParseException e1) {
				logger.error("时间转化发生异常", e1);
			}
			String monfriday = strd2 + "-" + "01";
			String monendday = strd2 + "-" + monMaxDate;
			try {
				strd = echartsBarService.getVertexMonthEvents(monfriday,monendday, id,searchType);// type);
				if (strd!=null) {
					inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
			} catch (Exception e) {
				logger.error("查询月访问量发生异常", e);
			}
		}
		if (StringUtils.isNotBlank(nextYear) && nextYear.length() > 14) {
			try {
				strd = echartsBarService.getVertexDayEvents(nextYear, id,searchType);// type);
				if (strd!=null) {
					inputStream = new ByteArrayInputStream(strd.getBytes("utf-8"));}
			} catch (Exception e) {
				logger.error("查询月访问量发生异常", e);
			}
		}
		 //System.out.println("action------------next---------="+strd);
		return "success";
	}*/

}
