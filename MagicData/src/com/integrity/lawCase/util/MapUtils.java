package com.integrity.lawCase.util;

import java.util.Map;

public class MapUtils{
	/**
	 * @param value
	 * @param map
	 * @return
	 * 根据value 获取key值
	 */
	public static String getKeyByValue(String value, Map<String, String> map) { 
	String str=""; 
	for (String key : map.keySet()) { 
	String tvalue=map.get(key); 
		if (value.equals(tvalue)) {
			str +=key +" ";
		} 
	}
	return str; 
	} 
}

