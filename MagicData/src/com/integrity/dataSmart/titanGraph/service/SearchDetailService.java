package com.integrity.dataSmart.titanGraph.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.impAnalyImport.bean.Resume;
import com.integrity.dataSmart.impAnalyImport.email2solr.SolrAllUtils;
import com.integrity.dataSmart.map.util.GeoIPUtil;
import com.integrity.dataSmart.map.util.PostGreSqlConnectionUtil;
import com.integrity.dataSmart.pojo.VertexObject;
import com.integrity.dataSmart.titanGraph.bean.PropertyHistory;
import com.integrity.dataSmart.titanGraph.dao.SearchDetailDao;
import com.integrity.dataSmart.titanGraph.pojo.Email;
import com.integrity.dataSmart.util.DateFormat;
import com.integrity.dataSmart.util.email.EmailUtil;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.maxmind.geoip2.record.Location;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class SearchDetailService {
	private Logger logger = Logger.getLogger(this.getClass());
	private ObjectMapper mapper = JacksonMapperUtil.getObjectMapper(); 
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DataType.DATEFORMATSTR);
	private SearchDetailDao searchDetailDao;
	//事件处理分割符
	private String joinString = "^=-=^";
	
	public void setSearchDetailDao(SearchDetailDao searchDetailDao) {
		this.searchDetailDao = searchDetailDao;
	}
	
	/**
	 * 全文查询节点(titan-solr)
	 * @param hs
	 * @param content
	 * @return
	 */
	public String getObjByFull(HttpSession hs, String content) {
		List<Vertex> list = searchDetailDao.getVertexByFull(content);
		return transformData2Node(list);
	}

	/**
	 * 多条件查询(titan-solr)
	 * @param hs
	 * @param searchMap key为节点label，value为属性-值的map
	 * @return
	 */
	public String getObjByMoreProperty(HttpSession hs,Map<String, Map<String, String>> searchMap) {
		List<Vertex> list = searchDetailDao.getVertexByMoreProperty(searchMap);
		return transformData2Node(list);
	}
	
	/**
	 * 转换查询节点集合为json
	 * @param list
	 * @return
	 */
	private String transformData2Node(List<Vertex> list){
		Map<String, Object> root = new HashMap<String,Object>();
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
		root.put("nodes", nodeList);
		root.put("edges", edgeList);
		if (CollectionUtils.isNotEmpty(list)) {
			for (Vertex vertex : list) {
				addNodeHandler(vertex, nodeList, false);
			}
			return getJson(root);
		}
		return null;
	}
	
	/**
	 * 根据条件查询对象
	 */
	public String getObjByProperty(HttpSession hs ,String property, String content) {
		
		List<List<Vertex>> allObjs = null;
		if(Arrays.asList(DataType.OWNPROPERTY).contains(property)){
			if ("resume".equals(property)) {
				//根据简历内容查询简历节点id
				List<String> vidList = SolrAllUtils.getVidfromResumeText(content, 0, 50);
				//根据简历节点id查询人物
				allObjs = searchDetailDao.getVertexByOwnVertexId(vidList);
			}else {
				allObjs = searchDetailDao.getVertexByOwnProperty(property,content);
			}
		}else {
			allObjs = searchDetailDao.getVertexByProperty(property,content);
		}
		return getObjData(hs, allObjs);
	}
	
	/**
	 * 根据id获取人物详细信息的json数据
	 * @param property
	 * @param content 
	 * @return
	 */
	public String getDetailById(HttpSession hs ,long personId, long nodeIndex, long maxIndex ,String property, String content) {
		List<Vertex> lst = searchDetailDao.getVertexsById(personId);
		
		if (StringUtils.isNotBlank(property) && StringUtils.isNotBlank(content)) {
			return getDetailData_first(hs, lst, nodeIndex, maxIndex, property, content);
		}else {
			return getDetailData(hs ,lst,nodeIndex,maxIndex);
		}
	}
	
	/**
	 * 根据ID和类型获取人物相关事件的json数据
	 * @param hs 
	 * @param personId
	 * @param types 
	 * @param endTime 
	 * @param startTime 
	 * @param maxIndex 
	 * @param nodeIndex 
	 * @return
	 */
	public String getEventById(HttpSession hs, long personId, String[] types, long nodeIndex, long maxIndex) {
		List<Vertex> lst = null;
/*		if (startTime == 0 || endTime == 0) {
			endTime = System.currentTimeMillis();
			startTime = endTime-(DataType.TIMEZONE);
		}*/
		if (types!=null&&types.length!=0) {
			String[] labels = DataType.getLabelsByTypes(types);
			lst = searchDetailDao.getEventByIdType(personId,labels);
		}else {
			lst = searchDetailDao.getEventById(personId);
		}
		
		return getRelationData(hs,lst,nodeIndex,maxIndex);
	}
	
	/**
	 * 根据人物ID查询网络关系
	 * @param hs 
	 * @param personId
	 * @param maxIndex 
	 * @param nodeIndex 
	 * @return
	 */
	public String getRelativeById(HttpSession hs, long personId, long nodeIndex, long maxIndex) {
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		List<Object> list = searchDetailDao.getRelativeById(personId);
		if(list != null && list.size() == 4){
			//直接存储关系
			List<Edge> friendEdges = (List<Edge>) list.get(0);
			List<Vertex> friends = (List<Vertex>) list.get(1);
			
			//事件关联关系
			List<Vertex> vdatas = (List<Vertex>) list.get(2);
			List<List<Vertex>> edatas = (List<List<Vertex>>) list.get(3);
			
			Map<String, Object> root = new HashMap<String,Object>();
			List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
			root.put("nodes", nodeList);
			root.put("edges", edgeList);
			
			int count = 0;
			for (int i=0;i<friendEdges.size();i++) {
				Edge edge = friendEdges.get(i);
				Vertex friend = friends.get(i);
				long _id = Long.parseLong(friend.getId()+"");
				String relationtype = edge.getProperty("relationtype");
				String nickname = edge.getProperty("nickname");
				nickname = nickname==null?"":("|"+nickname);
				String relationMsg = DataType.getRelationNameByType(relationtype)+nickname;
				int index = nodeIdList.indexOf(_id);
				if (index>-1) {
					addLinkHandler(nodeIndex, index, "relation："+relationMsg, "out", edgeList);
				}else {
					List<Vertex> lst = searchDetailDao.getVertexsById(_id);
					if (lst!=null&&lst.size()>0) {
						nodeIdList.add(_id);
						addPersonNodeHandler(lst, nodeList);
						addLinkHandler(nodeIndex, maxIndex + ++count, "relation："+relationMsg, "out", edgeList);
					}
				}
			}
			
			for (int i=0;i<vdatas.size();i++) {
				Vertex vertex = vdatas.get(i);
				long _id = Long.parseLong(vertex.getId()+"");
				int index = nodeIdList.indexOf(_id);
				if (index>-1) {
					List<Vertex> relations = (List<Vertex>) edatas.get(i);
					String relationMsg = "";
					for (int j = 0; j < relations.size(); j++) {
						relationMsg += relations.get(j).getProperty("type");
						if (j!=relations.size()-1) {
							relationMsg += ",";
						}
					}
					addLinkHandler(nodeIndex, index, "relation："+relationMsg, "out", edgeList);
				}else {
					List<Vertex> lst = searchDetailDao.getVertexsById(_id);
					if (lst!=null&&lst.size()>0) {
						nodeIdList.add(_id);
						addPersonNodeHandler(lst, nodeList);
						
						List<Vertex> relations = (List<Vertex>) edatas.get(i);
						String relationMsg = "";
						for (int j = 0; j < relations.size(); j++) {
							relationMsg += relations.get(j).getProperty("type");
							if (j!=relations.size()-1) {
								relationMsg += ",";
							}
						}
						addLinkHandler(nodeIndex, maxIndex + ++count, "relation："+relationMsg, "out", edgeList);
					}
				}
			}
			return getJson(root);
		}
		return null;
	}

	/**
	 * 根据人物ID、时间查询地图所需数据
	 * @param personId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String getMapData(long personId) {
/*		if (startTime == 0 || endTime == 0) {
			endTime = System.currentTimeMillis();
			startTime = endTime-(DataType.TIMEZONE);
		}*/
		String[] labels = DataType.getLabelByType(DataType.EVENTLOGIN);
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		List<Vertex> list = searchDetailDao.getEventByIdType(personId, labels);
		list.remove(0);
		GeoIPUtil geoIPUtil = new GeoIPUtil();
		geoIPUtil.getInstance();
		
		PostGreSqlConnectionUtil connectionUtil = PostGreSqlConnectionUtil.getInstance();
		Connection  con = null;
		Statement  st = null;
		ResultSet rs = null;
		String mapJson = "";
		try {
			con = connectionUtil.conn();
			for (Vertex v : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				Set<String> set = v.getPropertyKeys();
				for (String key : set) {
					String value =v.getProperty(key)+"";
					value = transform(key, value);
					map.put(key, value);
					if("ip".equals(key)){
						Location loc = geoIPUtil.getLocation(value);
						Double longitude = loc.getLongitude();
						Double latitude = loc.getLatitude();
						map.put("longitude", longitude);
						map.put("latitude",  latitude);
						
						st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					    rs=st.executeQuery("select name from planet_osm_point where round(st_x(st_transform((planet_osm_point.way),4326))::numeric,4)="+longitude+" and round(st_y(st_transform((planet_osm_point.way),4326))::numeric, 4)="+latitude+";");   
					    String address = "";
					    while(rs.next()){
					    	address = rs.getString("name");
					    	if(StringUtils.isBlank(address)) continue;
					    	break;
					    }
					    map.put("address",  address);
					}
				}
				lst.add(map);
			}
			mapJson = mapper.writeValueAsString(lst);
		} catch (Exception e) {
			logger.error("转换json异常",e);
		}finally{
			 try {
				 if(rs != null)
					 rs.close();
				 if(st != null)
					 st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connectionUtil.closeConnection(con);
		}
       
		return mapJson;
	}

	/**
	 * 查询人物关联关系
	 * @param hs 
	 * @param personId 人物ID
	 * @param connectId 关联人物ID
	 * @param nodeIndex 人物当前索引
	 * @param connectIndex 关联人物当前索引
	 * @param maxIndex 最大索引
	 * @return
	 */
	public String getConnectByIds(HttpSession hs, long personId, long connectId,long nodeIndex, long connectIndex,long maxIndex) {
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		List<List> list = searchDetailDao.getRelationById(personId, connectId);
		if(list != null && list.size() == 2){
			List<Vertex> vdatas = list.get(0);
			List<Edge> edatas = list.get(1);
			Map<String, Object> root = new HashMap<String,Object>();
			
			List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
			root.put("nodes", nodeList);
			root.put("edges", edgeList);
			
			int count = 0;
			int previousIndex = 0;
			for (int i=1;i<vdatas.size()-1;i++) {
				Vertex vertex = vdatas.get(i);
				long _id = Long.parseLong(vertex.getId()+"");;
				int index = nodeIdList.indexOf(_id);
				long source = 0;
				if (i==1) {
					source = nodeIndex;
				}else {
					if(previousIndex >-1){
						source = previousIndex;
					}else {
						source = maxIndex + count;
					}
				}
				String label = edatas.get(i-1).getLabel();
				String relation = "connect："+label;
//				String direction = getDirectionByType(label);
				String direction = "out";//查询关系接口返回数据不对临时处理
				if (index>-1) {
					addLinkHandler(source, index, relation, direction, edgeList);
				}else {
					String type = vertex.getProperty("type");
					if (DataType.PERSON.equals(type)) {
						List<Vertex> lst = searchDetailDao.getVertexsById(_id);
						if (lst!=null&&lst.size()>0) {
							nodeIdList.add(_id);
							addPersonNodeHandler(lst, nodeList);
						}
					}else {
						nodeIdList.add(_id);
						addNodeHandler(vertex, nodeList,false);
					}
					addLinkHandler(source, maxIndex + ++count, relation, direction, edgeList);
				}
				previousIndex = index;
				if (i==vdatas.size()-2) {
					label = edatas.get(i).getLabel();
					relation = "connect："+label;
//					direction = getDirectionByType(label);
					direction = "out";
					addLinkHandler(maxIndex + count, connectIndex, relation, direction, edgeList);
				}
				
				 
			}
			return getJson(root);
		}
		return null;
	}
	
	/**
	 * 修改节点ID缓存
	 */
	public void modifyIdCache(HttpSession hs,String[] newIds){
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		if (nodeIdList == null) {
			nodeIdList = new ArrayList<Object>();
			hs.setAttribute("nodeIdList", nodeIdList);
		}
		nodeIdList.addAll(Arrays.asList(newIds));
	}
	
	/**
	 * 删除节点ID缓存
	 */
	public void deleteIdCache(HttpSession hs,String[] ids){
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		for (String idstr : ids) {
			for (Object id : nodeIdList) {
				if (String.valueOf(id).equals(idstr)) {
					nodeIdList.remove(id);
					break;
				}
			}
		}
	}
	
	/**
	 * 根据条件查询邮件(只全文)
	 * @param content
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public String getEmailsFullText(String content, int pageNo, int pageSize) {
		List<Email> list = searchDetailDao.getEmailsFullText(content,pageNo,pageSize);
		String json = "";
		try {
			json = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			logger.error("转换json异常",e);
		}
//		System.out.println(json);
		return json;
	}
	
	/**
	 * 根据条件查询邮件
	 * @return
	 */
	public String getEmailsByCondition(int pageNo, int pageSize, String all,
			String title, String mailAddress, String content,
			String attachfiles, String attachContents, String keywords,
			String showLabel, String emlPath, String startTime, String endTime) {
		// TODO Auto-generated method stub
		String condition = "";
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(mailAddress)) {
			mailAddress = mailAddress.replaceAll("\"", "");
			sb.append(" (from:\""+mailAddress+"\" OR tos:\""+mailAddress+"\" OR copyTos:\""+mailAddress+"\") AND ");
		}
		if (StringUtils.isNotBlank(all)) {
			all = all.replaceAll("\"", "");
			sb.append(" all:\""+all+"\" AND ");
		}
		if (StringUtils.isNotBlank(title)) {
			title = title.replaceAll("\"", "");
			sb.append(" title:\""+title+"\" AND ");
		}
		if (StringUtils.isNotBlank(content)) {
			content = content.replaceAll("\"", "");
			sb.append(" content:\""+content+"\" AND ");
		}
		if (StringUtils.isNotBlank(attachfiles)) {
			attachfiles = attachfiles.replaceAll("\"", "");
			sb.append(" attachfiles:\""+attachfiles+"\" AND ");
		}
		if (StringUtils.isNotBlank(attachContents)) {
			attachContents = attachContents.replaceAll("\"", "");
			sb.append(" attachContents:\""+attachContents+"\" AND ");
		}
		if (StringUtils.isNotBlank(keywords)) {
			keywords = keywords.replaceAll("\"", "");
			sb.append(" keywords:\""+keywords+"\" AND ");
		}
		if (StringUtils.isNotBlank(showLabel)) {
			showLabel = showLabel.replaceAll("\"", "");
			sb.append(" showLabel:\""+showLabel+"\" AND ");
		}
		if (StringUtils.isNotBlank(emlPath)) {
			
			emlPath = emlPath.replaceAll("\"", "").substring(0, emlPath.length()-1).replace("\\", "\\\\");
			String[] arr = emlPath.split(",");
			//子目录查询处理
			List<String> lst = new ArrayList<String>();
			for (String string : arr) {
				lst.add(string);
			}
			for (int i = 0; i < arr.length; i++) {
				String temp = arr[i];
				for (int j = lst.size()-1; j >-1 ; j--) {
					String str = lst.get(j);
					if (str.startsWith(temp+"\\")) {
						lst.remove(j);
					}
				}
			}
			sb.append("(");
			for (String path : lst) {
//				sb.append(" emlPath:\""+path+"\" ");
				sb.append(" emlPath:"+path.trim().replaceAll(" ", "*")+"* ");
			}
			sb.append(")  AND ");
		}
		
		/* 时间范围查询回吧开始时间前一天的也查出来 ，在此修改 */ //不明状况又好了，注释掉
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		Date date;
		if (StringUtils.isNotBlank(startTime)){
			try {
				date = df.parse(startTime);
				startTime=df.format(new Date(date.getTime() + 1 * 24 * 60 * 60 * 1000));
			} catch (ParseException e1) {
				e1.printStackTrace();
				logger.error("时间加一天异常",e1);
			}
		}*/
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			try {
//				long start = simpleDateFormat.parse(startTime).getTime();
//				long end = simpleDateFormat.parse(endTime).getTime();
				
				String start = DateFormat.solrDate(startTime);
				String end = DateFormat.solrDate(endTime);
				sb.append(" mseDate:["+start+" TO "+end+"] AND ");
			} catch (Exception e) {
				logger.error("时间转换异常",e);
			}
		}else if (StringUtils.isBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			try {
				String end = DateFormat.solrDate(endTime);
				sb.append(" mseDate:[* TO "+end+"] AND ");
			} catch (Exception e) {
				logger.error("时间转换异常",e);
			}
		}else if (StringUtils.isNotBlank(startTime) && StringUtils.isBlank(endTime)) {
			try {
				String start = DateFormat.solrDate(startTime);
				sb.append(" mseDate:["+start+" TO *] AND ");
			} catch (Exception e) {
				logger.error("时间转换异常",e);
			}
		}
		sb.append(" type:EmailEvent");
		condition = sb.toString();
//		System.out.println("con:"+condition);
		List<Email> list = searchDetailDao.getEmailsByCondition(pageNo, pageSize, condition);
		String json = "";
		try {
			json = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			logger.error("转换json异常",e);
		}
//		System.out.println(json);
		return json;
	}
	
	/**
	 * 根据查询的邮件ids展示图
	 * @param hs
	 * @param emailIds
	 * @param maxIndex
	 * @return
	 */
public String getMailRelativeByIds(HttpSession hs, String[] emailIds,long maxIndex) {
		
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		if (nodeIdList == null) {
			nodeIdList = new ArrayList<Object>();
			hs.setAttribute("nodeIdList", nodeIdList);
		}
		
		Map<String, Object> root = new HashMap<String,Object>();
		
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
		root.put("nodes", nodeList);
		root.put("edges", edgeList);
		Map<String, List<Map<String, Object>>> countMap = new HashMap<String, List<Map<String, Object>>>();
		// TODO
		if(emailIds==null){return getJson(root);}
		for (String idStr : emailIds) {
			if (StringUtils.isNotBlank(idStr)) {
				long id = Long.parseLong(idStr);
				Vertex vertex = searchDetailDao.getVertexById(id);
				if (vertex == null) {
					break;
				}
				
				String typeKey = "";
				List<Map<String, Object>> subList = null;
				
//				String uuid = getUUID();
				typeKey = vertex.getProperty("from")+joinString+vertex.getProperty("to").toString().replaceAll(",", ";")/*+joinString+uuid*/;
				int index = nodeIdList.indexOf(typeKey);
				subList = countMap.get(typeKey);
				if(index>-1){
					if (subList == null) {
						
					}else {
						addProMap(vertex, subList);
						maxIndex +=twiceQuery(nodeIdList,id, nodeList, edgeList, DataType.EMAILTO, maxIndex,index,null);
						maxIndex +=twiceQuery(nodeIdList,id, nodeList, edgeList, DataType.EMAILFROM, maxIndex,index,null);
					}
				}else {
					nodeIdList.add(typeKey);
					subList = addEventCountNodeHandler(vertex, nodeList,typeKey);
					countMap.put(typeKey, subList);
					++maxIndex;
					long sourceId = maxIndex;//先入邮件的点
					maxIndex +=twiceQuery(nodeIdList,id, nodeList, edgeList, DataType.EMAILTO, maxIndex,sourceId,null);
					maxIndex +=twiceQuery(nodeIdList,id, nodeList, edgeList, DataType.EMAILFROM, maxIndex,sourceId,null);
				}
			}
		}
		return getJson(root);
	}
	
	/**
	 * 查询邮件目录树
	 * @return
	 */
	public String getTreeJson() {
		List<Map<String,String>> root = new ArrayList<Map<String,String>>();
		root = searchDetailDao.getTreeJson();
		String json = "";
		try {
			json = mapper.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			logger.error("转换json异常",e);
		}
		return json;
	}
	
	/**
	 * 根据人物ID查询群组关系
	 * @param personId 人物id
	 * @return
	 */
	public String findGroupRelativeById(long personId) {
		//用于去重
		List<Object> nodeIdList = new ArrayList<Object>();
		Map<String, Object> root = new HashMap<String,Object>();
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
		root.put("nodes", nodeList);
		root.put("edges", edgeList);
		List<List<Vertex>> groups = searchDetailDao.findGroupRelativeById(personId);
		int source = -1;
		for (int j = 0; j < groups.size(); j++) {
			List<Vertex> list = groups.get(j);
			int size = list.size();
			String ownerqq = "";
			for (int i = 0; i < size; i++) {
				Vertex vertex = list.get(i);
				Object id = vertex.getId();
				Map<String, Object> nodeMap = new HashMap<String, Object>();
				nodeMap.put("id", vertex.getId());
				if (i == 0) {
					ownerqq = vertex.getProperty("ownerqq");
					nodeMap.put("num", vertex.getProperty("groupnum")); 
					nodeMap.put("image", "/scripts/img/QQqun.png");
					nodeMap.put("type", "group");
					try {
						nodeMap.put("createtime", simpleDateFormat.format(new Date(Long.parseLong(vertex.getProperty("createtime ")+"000"))));
					} catch (Exception e) {
						nodeMap.put("createtime", "");
					}
					nodeMap.put("ownerqq", vertex.getProperty("ownerqq"));
					nodeMap.put("groupname", vertex.getProperty("groupname"));
					nodeMap.put("groupdesc", vertex.getProperty("groupdesc "));
					nodeList.add(nodeMap);
					nodeIdList.add(id);
					source = nodeIdList.size()-1;
				}else {
					String numid = vertex.getProperty("numid");
					int index = nodeIdList.indexOf(id);
					if (index > -1) {
					}else {
						nodeMap.put("num", numid); 
						List<String> values = vertex.getProperty("nickname");
						String nickname = getNickname(values);
						nodeMap.put("nickname", nickname);
						nodeMap.put("image", "/scripts/img/qq.png");
						nodeMap.put("type", "qq");
						nodeList.add(nodeMap);
						nodeIdList.add(id);
						index = nodeIdList.size()-1;
					}
					if (numid.equals(ownerqq)) {
						addLinkHandler(source, index, "groupOwner", "out", edgeList);
					}else {
						addLinkHandler(source, index, "qq", "out", edgeList);
					}
				}
			}
		}
		
		return getJson(root);
	}
	
	/**
	 * 修改节点属性
	 * @param propertyHistory
	 */
	public void updateVertex(PropertyHistory propertyHistory) {
		Long id = propertyHistory.getVertexId();
		String property = propertyHistory.getProperty();
		String value = propertyHistory.getValue();
		String oldValue = searchDetailDao.updateVertex(id,property,value);
		propertyHistory.setValue(oldValue);
	}
	
	/**
	 * 根据id数组获取节点对象
	 */
	public List<VertexObject> findVertexObjects(String[] ids){
		List<VertexObject> objs = new ArrayList<VertexObject>();
		try {
			if (ids != null && ids.length > 0) {
				for (String str : ids) {
					Long vertexId = Long.parseLong(str);
					Vertex vertex = searchDetailDao.getVertexById(vertexId);
					String type = vertex.getProperty("type");
					
					VertexObject vObject = new VertexObject();
					vObject.setType(type);
					vObject.setBaseProMap(transformVertex2Map(vertex));
					if(DataType.PERSON.equals(type)){
						List<Map<String, String>> ownProList = new ArrayList<Map<String, String>>();
						List<Vertex> lst = searchDetailDao.getPersonOwnVertexsById(vertex);
						for (Vertex own : lst) {
							ownProList.add(transformVertex2Map(own));
						}
						vObject.setOwnProList(ownProList);
					}
					
					objs.add(vObject);
				}
			}
		} catch (Exception e) {
			logger.error("根据id数组获取节点对象异常",e);
		}
		
		return objs;
	}
	
	
	
	/***********************************************************************************************************/
	
	/**
	 * 转换节点为map
	 * @param vertex
	 * @return
	 */
	private Map<String, String> transformVertex2Map(Vertex vertex){
		Map<String, String> map = new HashMap<String, String>();
		Set<String> set = vertex.getPropertyKeys();
		String type = vertex.getProperty("type");
		for (String key : set) {
			String value = "";
			if (DataType.IM.equals(type) && "nickname".equals(key)) {//qq昵称数组处理
				List<String> values = vertex.getProperty(key);
				value = getNickname(values);
			}else if("time".equals(key) || "createtime".equals(key)) {
				Long time = vertex.getProperty(key);
				value = DateFormat.transferLongToDate(time);
			}else {
				value = vertex.getProperty(key).toString();
			}
			map.put(key, value);
		}
		
		if (DataType.PERSON.equals(type)) {
			if (map.get("name") == null) {
				map.put("name", "");
			}
			if (map.get("idcard") == null) {
				map.put("idcard", "");
			}
			if (map.get("country") == null) {
				map.put("country", "");
			}
		}
		return map;
	}
	
	/**
	 * 转换人物节点
	 * @return
	 */
	private String getObjData(HttpSession hs ,List<List<Vertex>> allObjs) {
		if (allObjs!=null&&allObjs.size()>0) {
			
			Map<String, Object> root = new HashMap<String,Object>();
			
			List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
			root.put("nodes", nodeList);
			root.put("edges", edgeList);
			for (List<Vertex> lst : allObjs) {
				if (lst!=null&&lst.size()>0) {
					addPersonNodeHandler(lst, nodeList);
				}
			}
			return getJson(root);
		}
		return null;
	}
	
	/**
	 * 转换人物详细信息</br>
	 * 根据查询结果转换
	 * @param hs 
	 * @param lst 查询结果
	 * @param maxIndex 
	 * @param nodeIndex 
	 * @return d3展示所需数据格式
	 */
	private String getDetailData(HttpSession hs, List<Vertex> lst, long nodeIndex, long maxIndex){
		//对象的其他属性
		if (lst!=null) {
			List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
			Map<String, Object> root = new HashMap<String,Object>();
			
			List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
			root.put("nodes", nodeList);
			root.put("edges", edgeList);
			int size = lst.size();
			for (int j = 1; j < size; j++) {
				Vertex n = lst.get(j);
				long id = Long.parseLong(n.getId()+"");
				int index = nodeIdList.indexOf(id);
				if (index>-1) {
					//addLinkHandler(nodeIndex, index, type, "out", edgeList);
				}else {
					nodeIdList.add(id);
					propertyHandler(nodeList, edgeList,n,nodeIndex, maxIndex);
					maxIndex++;
				}
				
			}
			return getJson(root);
		}
		return null;
	}
	
	/**
	 * 获取查询条件节点
	 * @param hs
	 * @param lst
	 * @param nodeIndex
	 * @param maxIndex
	 * @param property
	 * @param content
	 * @return
	 */
	private String getDetailData_first(HttpSession hs, List<Vertex> lst, long nodeIndex, long maxIndex,String property, String content){
		//对象的其他属性
		if (lst!=null) {
			List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
			Map<String, Object> root = new HashMap<String,Object>();
			
			List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
			root.put("nodes", nodeList);
			root.put("edges", edgeList);
			int size = lst.size();
			for (int j = 1; j < size; j++) {
				Vertex n = lst.get(j);
				long id = Long.parseLong(n.getId()+"");
				int index = nodeIdList.indexOf(id);
				if (index>-1) {
					//addLinkHandler(nodeIndex, index, type, "out", edgeList);
				}else {
					String type = n.getProperty("type");
					if (DataType.RESUME.equals(type)) {
						//根据简历内容查询简历节点id
						List<String> vidList = SolrAllUtils.getVidfromResumeText(content, 0, 50);
						if (vidList.contains(id+"")) {
							nodeIdList.add(id);
							propertyHandler(nodeList, edgeList,n,nodeIndex, maxIndex);
							return getJson(root);
						}
					}else {
						String value = n.getProperty(property);
						if (StringUtils.isNotBlank(value) && value.equals(content)) {
							nodeIdList.add(id);
							propertyHandler(nodeList, edgeList,n,nodeIndex, maxIndex);
							return getJson(root);
						}
					}
				}
				
			}
			return getJson(root);
		}
		return null;
	}
	
	/**
	 * 人物详细信息使用
	 */
	private void propertyHandler(List<Map<String, Object>> nodeList,List<Map<String, Object>> edgeList, Vertex vertex,long nodeIndex, long maxIndex) {
		addNodeHandler(vertex, nodeList,true);
		String type = vertex.getProperty("type");
		
		addLinkHandler(nodeIndex, ++maxIndex, type, "out", edgeList);

	}
	
	/**
	 * 转换人物关联事件
	 * 根据查询结果转换
	 * @param hs 
	 * @param lst 查询结果
	 * @param maxIndex 
	 * @param nodeIndex 
	 * @return d3展示所需数据格式
	 */
	private String getRelationData(HttpSession hs, List<Vertex> lst, long nodeIndex, long maxIndex){
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		if (lst!=null&&lst.size()>0) {
			Map<String, Object> root = new HashMap<String,Object>();
			
			List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
			root.put("nodes", nodeList);
			root.put("edges", edgeList);
			
			Vertex rootVertex = lst.get(0);
			Long personId = Long.parseLong(rootVertex.getId()+"");
			Map<String, Object> map = getMapByPersonId(personId);//人物属性map
			Map<String, List<Map<String, Object>>> countMap = new HashMap<String, List<Map<String, Object>>>();
			for (int j= 1;j<lst.size();j++) {
				Vertex vertex = lst.get(j);
				String type = vertex.getProperty("type");
				if (type != null) {
					long id = Long.parseLong(vertex.getId()+"");
					String typeKey = "";
					String relation = "";
					String direction = "";
					String label = "";
					long target = 1;
					List<Map<String, Object>> subList = null;
					switch (type) {
					case DataType.EVENTCALL:
						List<String> phonenum = (List<String>) map.get(DataType.PHONE);
						typeKey = vertex.getProperty("from")+joinString+vertex.getProperty("to");
						subList = countMap.get(typeKey);
						if (subList == null) {
							if (phonenum.contains(vertex.getProperty("from"))) {//两个方向的判断
								relation = DataType.CALLFROM;
								direction = "out";
								label = DataType.CALLTO;
							}else {
								relation = DataType.CALLTO;
								direction = "in";
								label = DataType.CALLFROM;
							}
							nodeIdList.add(typeKey);
							subList = addEventCountNodeHandler(vertex, nodeList,typeKey);
							countMap.put(typeKey, subList);
							target = ++maxIndex;
							addLinkHandler(nodeIndex, target, relation, direction, edgeList);
							maxIndex +=twiceQuery(nodeIdList,id, nodeList, edgeList, label, maxIndex,maxIndex,null);
						}else {
							addProMap(vertex, subList);
						}
						break;
					case DataType.EVENTEMAIL:
						List<String> email = (List<String>) map.get(DataType.EMAIL);
						typeKey = vertex.getProperty("from")+joinString+vertex.getProperty("to").toString().replaceAll(",", ";");
						subList = countMap.get(typeKey);
						if (subList == null) {
							if (email.contains(vertex.getProperty("from"))) {
								relation = DataType.EMAILFROM;
								direction = "out";
								label = DataType.EMAILTO;
							}else {
								relation = DataType.EMAILTO;
								direction = "in";
								label = DataType.EMAILFROM;
							}
							nodeIdList.add(typeKey);
							subList = addEventCountNodeHandler(vertex, nodeList,typeKey);
							countMap.put(typeKey, subList);
							target = ++maxIndex;
							addLinkHandler(nodeIndex, target, relation, direction, edgeList);
							maxIndex +=twiceQuery(nodeIdList,id, nodeList, edgeList, label, maxIndex,maxIndex,null);
						}else {
							addProMap(vertex, subList);
						}
						break;
					case DataType.EVENTSTAY:
						typeKey = vertex.getProperty("hotelid")+joinString+vertex.getProperty("orderno")+joinString+vertex.getProperty("hotelflag");
						subList = countMap.get(typeKey);
						if (subList == null) {
							nodeIdList.add(typeKey);
							subList = addEventCountNodeHandler(vertex, nodeList,typeKey);
							countMap.put(typeKey, subList);
							target = ++maxIndex;
							relation = type;
							direction = "out";
							label = DataType.COHABIT;
							addLinkHandler(nodeIndex, target, relation, direction, edgeList);
							maxIndex +=twiceQuery(nodeIdList,id, nodeList, edgeList, label, maxIndex,maxIndex,personId);
						}else {
							addProMap(vertex, subList);
						}
						break;
					default:
						typeKey = personId+joinString+type;
						subList = countMap.get(typeKey);
						if (subList == null) {
							nodeIdList.add(typeKey);
							subList = addEventCountNodeHandler(vertex, nodeList,typeKey);
							countMap.put(typeKey, subList);
							target = ++maxIndex;
							relation = type;
							direction = "out";
							addLinkHandler(nodeIndex, target, relation, direction, edgeList);
						}else {
							addProMap(vertex, subList);
						}
						break;
					}
				}
			}
			return getJson(root);
		}
		return null;
	}
	
	/**
	 * 二次查询关系
	 * @param nodeIdList 
	 * @param id 需二次查询的节点
	 * @param nodeList
	 * @param edgeList
	 * @param type 节点类型
	 * @param maxIndex nodeList最大的index
	 * @param sourceIndex 二次查询时的source
	 * @param personId 人物节点id，不需要去除自身节点的传null
	 * 
	 * @return
	 */
	private int twiceQuery(List<Object> nodeIdList, long id,List<Map<String,Object>> nodeList,List<Map<String,Object>> edgeList,String type,long maxIndex,long sourceIndex, Long personId){
		int count = 0;
		List<Vertex> reList = searchDetailDao.searchRelationByIdType(id,type);
		if (reList!=null && reList.size()>0) {
			for (Vertex vertex : reList) {
				long _id = Long.parseLong(vertex.getId()+"");
				if(personId != null && personId == _id) continue;// 同住人去除自身
				int index = nodeIdList.indexOf(_id);
				if (index>-1) {
					String direction = getDirectionByType(type);
					addLinkHandler(sourceIndex, index, type, direction, edgeList);
				}else {
					List<Vertex> lst = searchDetailDao.getVertexsById(_id);
					if (lst!=null&&lst.size()>0) {
						nodeIdList.add(_id);
						addPersonNodeHandler(lst, nodeList);
						
						String direction = getDirectionByType(type);
						long target = maxIndex + ++count;
						addLinkHandler(sourceIndex, target, type, direction, edgeList);
					}
				}
			}
		}
		return count;
	}
	
	/**
	 * 根据查询label判断方向
	 * @param type
	 * @return
	 */
	private String getDirectionByType(String type) {
		String direction = "";
		if (type.substring(type.length()-2).equals("to") || DataType.COHABIT.equals(type)) {
			direction = "out";
		}else {
			direction = "in";
		}
		return direction;
	}
	
	/**
	 * 添加人物节点(人物节点使用)
	 * @param lst 人物属性集合
	 * @return
	 */
	private void addPersonNodeHandler(List<Vertex> lst,List<Map<String,Object>> nodeList){
		Vertex vertex = lst.get(0);
		long id = Long.parseLong(vertex.getId()+"");
		
		Map<String,Object> personMap = new HashMap<String,Object>();
		personMap.put("id", id);
		personMap.put("uuid", getUUID());//节点标示
		personMap.put("image", "img/"+vertex.getProperty("type")+".png");
		
		Set<String> set = vertex.getPropertyKeys();
		for (String key : set) {
			String value = vertex.getProperty(key)+"";
			personMap.put(key, value);
		}
		//对象的其他属性
		if (lst.size()>1) {
			for (int j = 1; j < lst.size(); j++) {
				Vertex n = lst.get(j);
				String type = n.getProperty("type");
				if(StringUtils.isBlank(type)) continue;
				List<Map<String, Object>> types = (List<Map<String, Object>>) personMap.get(type);
				if (types == null) {
					types = new ArrayList<Map<String, Object>>();
					personMap.put(type, types);
				}
				Map<String,Object> proMap = new HashMap<String,Object>();
				Set<String> proset = n.getPropertyKeys();
				
				//属性编辑时需用到id
				long proId = Long.parseLong(n.getId()+"");
				proMap.put("id", proId);
				
				for (String key : proset) {
					String value = "";
					if (DataType.IM.equals(type) && "nickname".equals(key)) {//qq昵称数组处理
						List<String> values = n.getProperty(key);
						value = getNickname(values);
					}else if("time".equals(key) || "createtime".equals(key)) {
						Long time = n.getProperty(key);
						value = DateFormat.transferLongToDate(time);
					}else {
						value = n.getProperty(key)+"";
					}
					proMap.put(key, value);
				}
				int hash = proMap.hashCode();
				proMap.put("hash", hash);
				
				types.add(proMap);
			}
		}
		
		nodeList.add(personMap);
	}
	
	/**
	 * 添加节点(通用节点使用)
	 * @param flag  是否是人物自身节点own
	 */
	private void addNodeHandler(Vertex vertex,List<Map<String,Object>> nodeList, boolean flag){
		String type = vertex.getProperty("label");
		long id = Long.parseLong(vertex.getId()+"");
		Set<String> set = vertex.getPropertyKeys();
		Map<String,Object> nodeMap = new HashMap<String,Object>();
		for (String key : set) {
			String value = vertex.getProperty(key)+"";
			value = transform(key, value);
			nodeMap.put(key, value);
		}
		nodeMap.put("type", type);
		nodeMap.put("id", id);
		nodeMap.put("uuid", getUUID());
		nodeMap.put("image", "img/"+type+".png");
		if(flag) nodeMap.put("own", "own");//人物own节点添加标示
		nodeList.add(nodeMap);
	}
	
	/**
	 * 添加节点(事件统计节点使用)
	 */
	private List<Map<String,Object>> addEventCountNodeHandler(Vertex vertex,List<Map<String,Object>> nodeList,String countNodeId){
		String type = vertex.getProperty("type");
		long id = Long.parseLong(vertex.getId()+"");
		Set<String> set = vertex.getPropertyKeys();
		Map<String,Object> nodeMap = new HashMap<String,Object>();
		Map<String,Object> proMap = new HashMap<String,Object>();
		List<Map<String,Object>> subList = new ArrayList<Map<String,Object>>();
		for (String key : set) {
			String value = vertex.getProperty(key)+"";
			value = transform(key, value);
			proMap.put(key, value);
		}
		proMap.put("uuid", getUUID());
		proMap.put("image", "img/"+type+".png");
		proMap.put("id", id);
		subList.add(proMap);
		nodeMap.put("subList", subList);
		nodeMap.put("type", type);
		nodeMap.put("id", countNodeId);
		nodeMap.put("uuid", getUUID());
		nodeMap.put("image", "img/"+type+".png");
		nodeList.add(nodeMap);
		return subList;
	}
	
	private String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 给统计节点中添加子节点map(事件统计节点使用)
	 */
	private void addProMap(Vertex vertex,List<Map<String,Object>> subList){
		long id = Long.parseLong(vertex.getId()+"");
		String type = vertex.getProperty("type");
		Set<String> set = vertex.getPropertyKeys();
		Map<String,Object> proMap = new HashMap<String,Object>();
		for (String key : set) {
			String value = vertex.getProperty(key)+"";
			value = transform(key, value);
			proMap.put(key, value);
		}
		proMap.put("uuid", getUUID());
		proMap.put("image", "img/"+type+".png");
		proMap.put("id", id);
		subList.add(proMap);
	}
	
	/**
	 * 转换时间，替换HTML特殊字符
	 * @param key
	 * @param value
	 * @return
	 */
	private String transform(String key,String value) {
		try {
			if (StringUtils.isNotBlank(value)) {
				if ("time".equals(key) || "arrivaldate".equals(key) || "departuredate".equals(key) || "createtime".equals(key)) {
					value = simpleDateFormat.format(new Date(Long.parseLong(value)));
				}else{
					value = EmailUtil.replace(value);
				}
					
			}else {
				value = "";
			}
		} catch (Exception e) {
			logger.error("转换时间，替换HTML特殊字符 异常",e);
		}
		return value;
	}
	
	/**
	 * 添加边
	 */
	private void addLinkHandler(long source,long target,String relation,String direction,List<Map<String,Object>> edgeList){
		Map<String, Object> linksMap = new HashMap<String, Object>();
		if("in".equals(direction)){
			long temp = source;
			source = target;
			target = temp;
		}
		linksMap.put("source", source);
		linksMap.put("target", target);
		linksMap.put("relation", relation);
		linksMap.put("direction", direction);
		linksMap.put("uuid", getUUID());
		edgeList.add(linksMap);
	}
	
	/**
	 * 根据ID获取人物属性map
	 * key为事件类型   value为属性list
	 * @param id
	 * @return
	 */
	private Map<String, Object> getMapByPersonId(Object id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Vertex> lst = searchDetailDao.getVertexsById((long) id);
		for (Vertex vertex : lst) {
			String type = vertex.getProperty("type");
			switch (type) {
			case DataType.EMAIL:
				List<String> emailList = (List<String>) map.get(type);
				if (emailList == null) {
					emailList = new ArrayList<String>();
					map.put(type, emailList);
				}
				emailList.add(vertex.getProperty("email")+"");
				break;
			case DataType.PHONE:
				List<String> phonenumList = (List<String>) map.get(type);
				if (phonenumList == null) {
					phonenumList = new ArrayList<String>();
					map.put(type, phonenumList);
				}
				phonenumList.add(vertex.getProperty("phonenum")+"");
				break;
			default:
				break;
			}
		}
		return map;
	}
	
	/**
	 * json 转换
	 * @param root
	 * @return
	 */
	private  String getJson(Map<String, Object> root){
		String json = null;
		try {
			json = mapper.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			logger.error("转换json异常",e);
		}
		return json;
	}
	
	/**
	 * QQ 昵称转换
	 * @param values
	 * @return
	 */
	private String getNickname(List<String> values){
		if (values == null) {
			return "";
		}
		String nickname = "";
		for (String name : values) {
			nickname += name+" | ";
		}
		if (!"".equals(nickname)) {
			nickname = nickname.substring(0, nickname.length()-3);
		}
		return nickname;
	}
	
	/**
	 * 获取简历信息
	 * @param vid
	 * @return
	 */
	public String queryResumeByVid(String vid){
		Resume re = searchDetailDao.getResumeByVid(vid);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(re);
		return jsonStr;
	}

	
}
