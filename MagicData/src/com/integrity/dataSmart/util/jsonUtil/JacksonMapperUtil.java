package com.integrity.dataSmart.util.jsonUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * jackson 工具
 * 获取ObjectMapper
 *
 */
public class JacksonMapperUtil {

	private static final ObjectMapper om = new ObjectMapper();
	private JacksonMapperUtil(){};
	public static ObjectMapper getObjectMapper(){
		return om;
	}
}
