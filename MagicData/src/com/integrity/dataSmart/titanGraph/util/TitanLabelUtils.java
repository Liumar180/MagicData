package com.integrity.dataSmart.titanGraph.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TitanLabelUtils {
	
	public static String getNickname(String[] values){
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
	
	public static void addLinkHandler(long source,long target,String relation,String direction,List<Map<String,Object>> edgeList){
		Map<String, Object> linksMap = new HashMap<String, Object>();
		linksMap.put("source", source);
		linksMap.put("target", target);
		linksMap.put("relation", relation);
		linksMap.put("direction", direction);
		linksMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
		edgeList.add(linksMap);
	}
	public static void addLinkNickNameHandler(long source,long target,String nickname,String direction,List<Map<String,Object>> edgeList){
		Map<String, Object> linksMap = new HashMap<String, Object>();
		linksMap.put("source", source);
		linksMap.put("target", target);
		linksMap.put("relation", "nickname:"+nickname);
		linksMap.put("direction", direction);
		linksMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
		edgeList.add(linksMap);
	}

}
