package com.integrity.dataSmart.timeLine.util;

import org.apache.commons.configuration.BaseConfiguration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;

public class DataBaseUtil {
	private static DataBaseUtil dataBaseUtil = new DataBaseUtil();
	private  TitanGraph graph = null;
	private DataBaseUtil(){
		try {
			BaseConfiguration baseConfiguration = new BaseConfiguration();
			 baseConfiguration.setProperty("storage.backend", "hbase");
			 baseConfiguration.setProperty("storage.hostname", "192.168.20.174");
			 baseConfiguration.setProperty("storage.tablename","titan");
			 graph = TitanFactory.open(baseConfiguration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static DataBaseUtil getInstance(){
		if (dataBaseUtil == null) {
			dataBaseUtil = new DataBaseUtil();
		}
		return dataBaseUtil;
	}
	/**
	 * 获取titan client
	 * @return RexsterClient
	 */
	public  TitanGraph getDataBase(){
		return graph;
	}
	
	/**
	 * 不能手动调用
	 */
	public void shutdown(){
		if (graph != null) graph.shutdown();
	}
	
	
}
