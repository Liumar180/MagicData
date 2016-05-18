package com.integrity.dataSmart.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试数据
 * @author cs
 *
 */
public class TestDataUtil {
	
	public static List<Map<String, Object>> getDataPerson(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "奥巴马");
		map.put("address", "海淀区软件园");
		map.put("identity", "6102777989710992990");
		map.put("country", "中国");
		map.put("phone", "13146897765");
		map.put("account", "woshiqignfeng");
		map.put("email", "integrity@integrity.com");
		map.put("qq", "121121121");
		map.put("type", "person");
		
		list.add(map);
		return list;
	}

	public static List<Map<String, Object>> getDataRelationEvent(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "奥巴马");
		map.put("address", "海淀区软件园");
		map.put("identity", "6102777989710992990");
		map.put("country", "中国");
		map.put("phone", "13146897765");
		map.put("account", "woshiqignfeng");
		map.put("email", "integrity@integrity.com");
		map.put("qq", "121121121");
		map.put("type", "person");
		
		list.add(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("name", "网站");
		map1.put("time", "2015-06-01");
		map1.put("type", "www");
		
		list.add(map1);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("name", "论坛");
		map1.put("time", "2015-02-13");
		map2.put("type", "bbs");
		
		list.add(map2);
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("name", "邮件");
		map3.put("time", "2014-03-17");
		map3.put("from", "integrity@integrity.com");
		map3.put("to", "xiaosan@integrity.com");
		map3.put("type", "email");
		
		list.add(map3);
		
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("name", "电话");
		map4.put("time", "2014-03-17");
		map4.put("from", "13146897765");
		map4.put("to", "15011000099");
		map4.put("type", "call");
		
		list.add(map4);
		return list;
	}
	
	public static List<Map<String, Object>> getDataRelationEvent(String type){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		if ("email".equals(type)) {
			map.put("name", "张三");
			map.put("address", "海南");
			map.put("identity", "6102666989710992890");
			map.put("country", "中国");
			map.put("phone", "13146897765");
			map.put("email", "xiaosan@integrity.com");
			map.put("qq", "5675312");
			map.put("type", "person");
		}else if ("call".equals(type)) {
			map.put("name", "赵四");
			map.put("address", "中国东北");
			map.put("identity", "6102888989710992567");
			map.put("country", "中国");
			map.put("phone", "15011000099");
			map.put("email", "integrity12@integrity.com");
			map.put("qq", "5677899");
			map.put("type", "person");
		}
		list.add(map);
		return list;
		
	}

	public static List<Map<String, Object>> getDataRelative() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map0 = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "奥巴马");
		map.put("address", "海淀区软件园");
		map.put("identity", "6102777989710992990");
		map.put("country", "中国");
		map.put("phone", "13146897765");
		map.put("account", "woshiqignfeng");
		map.put("email", "integrity@integrity.com");
		map.put("qq", "121121121");
		map.put("type", "Person");
		
		map0.put("_id", 123);
		map0.put("_properties", map);
		list.add(map0);
		
		Map<String, Object> map01 = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("name", "网友：ABC");
		map1.put("type", "Person");
		map01.put("_id", 1234);
		map01.put("_properties", map1);
		list.add(map01);
		
		Map<String, Object> map02 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("name", "朋友：韩梅梅");
		map2.put("type", "Person");
		map02.put("_id", 1235);
		map02.put("_properties", map2);
		list.add(map02);
		
		Map<String, Object> map03 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("name", "对象：小三");
		map3.put("type", "Person");
		map03.put("_id", 1236);
		map03.put("_properties", map3);
		list.add(map03);
		
		Map<String, Object> map04 = new HashMap<String, Object>();
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("name", "朋友：小四");
		map4.put("type", "Person");
		map04.put("_id", 1237);
		map04.put("_properties", map4);
		list.add(map04);
		return list;
	}
}
