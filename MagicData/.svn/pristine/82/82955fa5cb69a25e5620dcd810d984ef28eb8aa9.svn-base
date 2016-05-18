package com.integrity.dataSmart.util.titan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.log4j.Logger;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
/**
 *  TitanGraph工具类（临时）
 *
 */
public class TitanGraphUtil {
	private Logger logger = Logger.getLogger(TitanGraphUtil.class);
	private static TitanGraphUtil titanGraphUtil = new TitanGraphUtil();
	
	private TitanGraph graph = null;
	private TitanGraphUtil(){
		InputStream inputStream = null;
		try {
			BaseConfiguration baseConfiguration = new BaseConfiguration();

			inputStream = this.getClass().getClassLoader().getResourceAsStream("../config/titan/titanConfig.properties");
			Properties p = new Properties();
			p.load(inputStream);
			String INDEX_NAME = p.getProperty("solr.INDEX_NAME");
			baseConfiguration.setProperty("storage.backend", p.getProperty("storage.backend"));
			baseConfiguration.setProperty("storage.hostname", p.getProperty("storage.hostname"));
			baseConfiguration.setProperty("storage.tablename", p.getProperty("storage.tablename"));
			/*baseConfiguration.setProperty("index." + INDEX_NAME + ".backend", p.getProperty("solr.backend"));
			baseConfiguration.setProperty("index." + INDEX_NAME + ".solr.mode", p.getProperty("solr.mode"));
			baseConfiguration.setProperty("index." + INDEX_NAME + ".solr.zookeeper-url", p.getProperty("solr.zookeeper-url"));*/
			graph = TitanFactory.open(baseConfiguration);
		} catch (Exception e) {
			logger.error("TitanGraph init error", e);
		}finally{
				try {
					if(inputStream!=null)
						inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	public static TitanGraphUtil getInstance(){
		if (titanGraphUtil == null) {
			titanGraphUtil = new TitanGraphUtil();
		}
		return titanGraphUtil;
	}
	/**
	 * 获取TitanGraph
	 * @return
	 */
	public TitanGraph getTitanGraph(){
		return graph;
	}
	
	/**
	 * 不能手动调用
	 */
	public void shutdown(){
		try {
			if (graph != null) graph.shutdown();;
		} catch (Exception e) {
			logger.error("graph.shutdown() error", e);
		}
	}

}
