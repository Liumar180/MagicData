package com.integrity.dataSmart.common.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.integrity.dataSmart.dataImport.util.TitanStructureUtil;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.integrity.dataSmart.util.titan.TitanGraphUtil;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.common.init.service.DictionaryService;

public class TitanConnectListener implements ServletContextListener {
	private Logger logger = Logger.getLogger(TitanConnectListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//没有启用client的方式
//		ClientUtil.getInstance().close();
		
		TitanGraphUtil.getInstance().shutdown();
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext sc = arg0.getServletContext();
		initDictionary(sc);
		initLogProperties(sc);
		initObjectMap(sc);
		initVertexProperty(sc);
		initSwfProperties(sc);
		initViewConfig(sc);
	}
	
	
	/**
	 * 初始化字典表
	 * @param sc
	 */
	private void initDictionary(ServletContext sc) {
		try {
			ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
			DictionaryService dic = (DictionaryService) ac.getBean("dictionaryService");
			Map<String,Map<String,String>> allMap = dic.getDictionaryMap();
			
			sc.setAttribute(ConstantManage.DATADICTIONARY, allMap);
		} catch (Exception e) {
			logger.error("初始化字典表异常", e);
		}
	}
	
	/**
	 * 初始化日志配置文件
	 * @param sc
	 */
	private void initLogProperties(ServletContext sc) {
		Properties pps = new Properties();
		try {
			pps.load(new FileInputStream(sc.getRealPath("WEB-INF/config/logManage/urlTypes.properties")));
			
			sc.setAttribute("logUrlTypes", pps);
		} catch (FileNotFoundException e1) {
			logger.error("初始化日志文件异常", e1);
		} catch (IOException e1) {
			logger.error("初始化日志文件流异常", e1);
		}
	}
	/**
	 * @param sc
	 * 初始化 swf 转换器 执行文件exe
	 */
	private void initSwfProperties(ServletContext sc) {
		Properties pps = new Properties();
		try {
			pps.load(new FileInputStream(sc.getRealPath("WEB-INF/config/SwfTools/swf.properties")));
			
			sc.setAttribute("swfUrlexe", pps);
		} catch (FileNotFoundException e1) {
			logger.error("初始化swf 转换器 执行文件exe异常", e1);
		} catch (IOException e1) {
			logger.error("初始化swf 转换器 执行文件exe流异常", e1);
		}
	}
	/**
	 * 初始化对象类型对应的具体属性名映射
	 * @param sc
	 */
	private void initObjectMap(ServletContext sc) {
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("../config/lawCase/type_property.properties");
			Properties p = new Properties();
			p.load(inputStream);
			Map<String,String> map = new HashMap<String, String>();
			Set<Entry<Object, Object>> set = p.entrySet();
			for (Entry<Object, Object> entry : set) {
				map.put(entry.getKey().toString(), entry.getValue().toString());
			}
			sc.setAttribute(ConstantManage.TYPE_PROPERTY, map);
		} catch (Exception e) {
			logger.error("初始化对象类型对应的具体属性名映射异常", e);
		}finally{
			try {
				if(inputStream!=null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化图过滤器属性
	 * @param sc
	 */
	private void initVertexProperty(ServletContext sc) {
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("../config/titan/vertexProperty.properties");
			Properties p = new Properties();
			p.load(inputStream);
			Map<String,List<String>> map = new LinkedHashMap<String, List<String>>();
			String types = p.getProperty("type");
			String[] typeArr = types.split(",");
			Set<Entry<Object, Object>> set = p.entrySet();
			List<String> typeList = new ArrayList<String>();
			for (String type : typeArr) {
				typeList.add(type);
				List<String> lst = new ArrayList<String>();
				for (Entry<Object, Object> entry : set) {
					String key = entry.getKey().toString();
					if (key.startsWith(type)) {
						String value = entry.getValue().toString();
						String[] valueArr = value.split(",");
						for (String pro : valueArr) {
							lst.add(pro);
						}
						break;
					}
				}
				map.put(type, lst);
			}
			sc.setAttribute("vertexType", typeList);
			sc.setAttribute("vertexProperty", map);
		} catch (Exception e) {
			logger.error("初始化图过滤器属性异常", e);
		}finally{
			try {
				if(inputStream!=null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化页面显示配置
	 * @param sc
	 */
	private void initViewConfig(ServletContext sc){
		try {
			//titan结构
			List<Map<String, Object>> titanStructure = TitanStructureUtil.getTitanStructure();
			sc.setAttribute("titanStr", JacksonMapperUtil.getObjectMapper().writeValueAsString(titanStructure));
			//默认显示值
			Map<String, List<String>> showAttr = TitanStructureUtil.getDefaultShow();
			sc.setAttribute("pageViewConfig", JacksonMapperUtil.getObjectMapper().writeValueAsString(showAttr));
			//可查询属性
			Map<String, List<String>> queryableMap = TitanStructureUtil.findQueryableMap();
			sc.setAttribute("queryableMap", JacksonMapperUtil.getObjectMapper().writeValueAsString(queryableMap));
			//属性别名
			Map<String, Map<String, String>> aliasAll = TitanStructureUtil.getTitanAlias();
			sc.setAttribute("aliasAll", JacksonMapperUtil.getObjectMapper().writeValueAsString(aliasAll));
			//父节点类型
			Map<String, List<String>> parentType = TitanStructureUtil.getParentType();
			sc.setAttribute("parentType", JacksonMapperUtil.getObjectMapper().writeValueAsString(parentType));
		} catch (JsonProcessingException e) {
			logger.error("初始化页面显示配置异常", e);
		}
	}
}
