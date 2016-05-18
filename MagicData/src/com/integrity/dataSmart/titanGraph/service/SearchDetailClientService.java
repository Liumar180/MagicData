package com.integrity.dataSmart.titanGraph.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.titanGraph.dao.SearchDetailClientDao;
import com.integrity.dataSmart.util.TestDataUtil;

public class SearchDetailClientService {

	private SearchDetailClientDao searchDetailDao;
	
	//直方图需要过滤的属性
	private String[] filterArr = {"id","type","image","eventtime","content","to","title","from","long"};

	public void setSearchDetailDao(SearchDetailClientDao searchDetailDao) {
		this.searchDetailDao = searchDetailDao;
	}
	
	/**
	 * 根据姓名获取人物详细信息的json数据
	 * @param name
	 * @return
	 */
	public String getVertexByName(String name){
		List<Map<String, Object>> lst = searchDetailDao.getVertexByName(name);
		
		//先使用测试数据
//		List<Map<String, Object>> lst = TestDataUtil.getDataPerson();
		return getDetailData(lst);

	}
	
	/**
	 * 根据ID和类型获取人物相关事件的json数据
	 * @param personId
	 * @param type 
	 * @return
	 */
	public String getEventById(String personId, String type) {
		List<Map<String, Object>> lst = null;
		if (StringUtils.isNotBlank(type)) {
			switch (type) {
			case DataType.ALL:
				lst = searchDetailDao.getEventById(personId);
				break;
			case DataType.EVENTCALL:
				lst = searchDetailDao.getEventByIdType(personId, DataType.CALLFROM);
				break;
			case DataType.EVENTEMAIL:
				lst = searchDetailDao.getEventByIdType(personId, DataType.EMAILFROM);
				break;
			default:
				lst = searchDetailDao.getEventByIdType(personId, type);
				break;
			}
		}
		//先使用测试数据
//		List<Map<String, Object>> lst = TestDataUtil.getDataRelationEvent();
		return getRelationData(lst);
	}
	
	/**
	 * 根据人物ID查询网络关系
	 * @param personId
	 * @return
	 */
	public String getRelativeById(String personId) {
		//先使用测试数据
		List<Map<String, Object>> lst = TestDataUtil.getDataRelative();
		return getRelationData(lst);
	}
	
	/**
	 * 转换人物详细信息</br>
	 * 根据查询结果转换
	 * @param list 查询结果
	 * @return d3展示所需数据格式
	 */
	public String getDetailData(List<Map<String, Object>> list){
		
		if (list!=null&&list.size()>0) {
			
			//直方图统计所需list
			List<Map<String,Object>> pillarList = new ArrayList<Map<String,Object>>();
			Map<String,Object> pillarMap = new HashMap<String,Object>();
			pillarList.add(pillarMap);
			
			Map<String, Object> vervex = list.get(0);
			String id = vervex.get("_id")+"";
			
			Map<String, Object> vervexPro = (Map<String, Object>) vervex.get("_properties");
			String nodeName = (String) vervexPro.get("name");
			
			Map<String, Object> root = new HashMap<String,Object>();
			root.put("id", id);
			
			List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
			root.put("nodes", nodeList);
			root.put("edges", edgeList);
			
			//先加入人节点，index--0
			Map<String,Object> personMap = new HashMap<String,Object>();
			personMap.put("name", nodeName);
			personMap.put("image", "img/Person.png");
			nodeList.add(personMap);
			pillarMap.put("name", nodeName);
			
			int i=0;
			Set<Entry<String, Object>> set = vervexPro.entrySet();
			for (Entry<String, Object> entry : set) {
				String key = entry.getKey();
				String value = entry.getValue()+"";
				if (!"name".equals(key)&&!"type".equals(key)) {
					i++;
					Map<String,Object> nodeMap = new HashMap<String,Object>();
					nodeMap.put("name", value);
					nodeMap.put("image", "img/"+key+".png");//节点的type属性对应图片名称
					nodeList.add(nodeMap);
					Map<String,Object> linksMap = new HashMap<String,Object>();
					linksMap.put("source", 0);
					linksMap.put("target", i);
					linksMap.put("relation", key);
					
					edgeList.add(linksMap);
					
					pillarMap.put(key, value);
				}
			}
			//对象的其他属性
			if (list.size()>1) {
				for (int j = 1; j < list.size(); j++) {
					Map<String, Object> n = list.get(j);
					String type = n.get("type")+"";
					if ("Location".equals(type)) {
						Map<String,Object> nodeMap = new HashMap<String,Object>();
						nodeMap.put("name", n.get("address"));
						nodeMap.put("image", "img/Location.png");
						nodeList.add(nodeMap);
						Map<String,Object> linksMap = new HashMap<String,Object>();
						linksMap.put("source", 0);
						linksMap.put("target", ++i);
						linksMap.put("relation", "Location");
						edgeList.add(linksMap);
						
						pillarMap.put(type, n.get("address"));
						
					}else if ("Mobile".equals(type)) {
						Map<String,Object> nodeMap = new HashMap<String,Object>();
						nodeMap.put("name", n.get("num"));
						nodeMap.put("image", "img/Mobile.png");
						nodeList.add(nodeMap);
						Map<String,Object> linksMap = new HashMap<String,Object>();
						linksMap.put("source", 0);
						linksMap.put("target", ++i);
						linksMap.put("relation", "Mobile");
						edgeList.add(linksMap);
						
						pillarMap.put(type, n.get("num"));
						
					}else if ("Account".equals(type)) {
						Map<String,Object> nodeMap = new HashMap<String,Object>();
						nodeMap.put("name", n.get("uid"));
						nodeMap.put("image", "img/Account.png");
						nodeList.add(nodeMap);
						Map<String,Object> linksMap = new HashMap<String,Object>();
						linksMap.put("source", 0);
						linksMap.put("target", ++i);
						linksMap.put("relation", "Account");
						edgeList.add(linksMap);

						pillarMap.put(type, n.get("uid"));
						
					}else if ("IM".equals(type)) {
						Map<String,Object> nodeMap = new HashMap<String,Object>();
						nodeMap.put("name", n.get("numid"));
						nodeMap.put("image", "img/"+n.get("domain")+".png");
						nodeList.add(nodeMap);
						Map<String,Object> linksMap = new HashMap<String,Object>();
						linksMap.put("source", 0);
						linksMap.put("target", ++i);
						linksMap.put("relation", "IM");
						edgeList.add(linksMap);

						pillarMap.put(type, n.get("numid"));
						
					}
					
				}
			}
			//加入直方图统计信息
			root.put("countMap", getPillarData(pillarList));
			return getJson(root);
		}
		return null;
	}
	
	/**
	 * 转换人物关联事件
	 * 根据查询结果转换
	 * @param list 查询结果
	 * @return d3展示所需数据格式
	 */
	public String getRelationData(List<Map<String, Object>> list){
		if (list!=null&&list.size()>0) {
			Map<String, Object> root = new HashMap<String,Object>();
			
			List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
			root.put("nodes", nodeList);
			root.put("edges", edgeList);
			
			int i=0;
			for (Map<String, Object> mapV : list) {
				String id = mapV.get("_id")+"";
				
				Map<String, Object> map = (Map<String, Object>) mapV.get("_properties");
				String type = map.get("type")+"";
				Set<Entry<String, Object>> set = map.entrySet();
				Map<String,Object> nodeMap = new HashMap<String,Object>();
				for (Entry<String, Object> entry : set) {
					String key = entry.getKey();
					String value = entry.getValue()+"";
					nodeMap.put(key, value);
				}
				nodeMap.put("id", id);
				nodeMap.put("image", "img/"+type+".png");//节点的type属性对应图片名称
				nodeList.add(nodeMap);
				if (i!=0) {
					Map<String,Object> linksMap = new HashMap<String,Object>();
					linksMap.put("source", 0);
					linksMap.put("target", i);
					
					switch (type) {
					case DataType.EVENTCALL:
						linksMap.put("relation", DataType.CALLFROM);
						break;
					case DataType.EVENTEMAIL:
						linksMap.put("relation", DataType.EMAILFROM);
						break;
					default:
						linksMap.put("relation", type);
						break;
					}
					
					edgeList.add(linksMap);
				}
				
				//需要二次查询的类型
				switch (type) {
				case DataType.EVENTCALL:
					i +=twiceQuery(nodeMap, nodeList, edgeList, DataType.CALLTO, i);
					break;
				case DataType.EVENTEMAIL:
					i +=twiceQuery(nodeMap, nodeList, edgeList, DataType.EMAILTO, i);
					break;
				default:
					break;
				}
				i++;
			}
			
			//加入直方图统计信息
			root.put("countMap", getPillarData(nodeList));
			return getJson(root);
		}
		return null;
	}
	
	/**
	 * 二次查询关系
	 * @param map 需二次查询的节点
	 * @param nodeList
	 * @param edgeList
	 * @param type 节点类型
	 * @param i nodeList最大的index
	 * @return
	 */
	private int twiceQuery(Map<String, Object> map,List<Map<String,Object>> nodeList,List<Map<String,Object>> edgeList,String type,int i){
		int count = 0;
		String id = (String) map.get("id");
		List<Map<String, Object>> reList = searchDetailDao.searchRelationByIdType(id,type);
		//使用测试数据
//		List<Map<String, Object>> reList = TestDataUtil.getDataRelationEvent(type);
		
		if (reList!=null && reList.size()>0) {
			for (Map<String, Object> mapV : reList) {
				String _id = mapV.get("_id")+"";
				
				Map<String, Object> reMap = (Map<String, Object>) mapV.get("_properties");
				Set<Entry<String, Object>> reSet = reMap.entrySet();
				Map<String,Object> reNodeMap = new HashMap<String,Object>();
				for (Entry<String, Object> entry : reSet) {
					String key = entry.getKey();
					String value = entry.getValue()+"";
					reNodeMap.put(key, value);
				}
				reNodeMap.put("id", _id);
				//节点的type属性对应图片名称
				reNodeMap.put("image", "img/"+reMap.get("type")+".png");
				nodeList.add(reNodeMap);
				
				Map<String,Object> reLinksMap = new HashMap<String,Object>();
				reLinksMap.put("source", i);
				reLinksMap.put("target", i + ++count);
				reLinksMap.put("relation", type);
				edgeList.add(reLinksMap);
			}
		}
		return count;
	}
	
	/**
	 * 统计直方图信息
	 * @param nodeList
	 * @return
	 */
	public Map<String, Object> getPillarData(List<Map<String,Object>> nodeList){
		Map<String, Object> countMap = new HashMap<String, Object>();
		
		for (Map<String, Object> map : nodeList) {
			Set<Entry<String, Object>> set = map.entrySet();
			for (Entry<String, Object> entry : set) {
				String key = entry.getKey();
				if (!Arrays.asList(filterArr).contains(key)) {
					
					String value = entry.getValue()+"";
					if (countMap.containsKey(key)) {
						Map<String, Object> proMap = (Map<String, Object>) countMap.get(key);
						proMap.put(key, 1+Integer.parseInt(proMap.get(key)+""));
						if (proMap.containsKey(value)) {
							proMap.put(value, 1+Integer.parseInt(proMap.get(value)+""));
						}else {
							proMap.put(value, 1);
						}
					}else {
						Map<String, Object> proMap = new HashMap<String, Object>();
						proMap.put(value, 1);
						proMap.put(key, 1);
						countMap.put(key, proMap);
					}
				}
			}
		}
		return countMap;
	}
	
	
	private  String getJson(Map<String, Object> root){

		return JSONObject.toJSONString(root);
	}

	
}
