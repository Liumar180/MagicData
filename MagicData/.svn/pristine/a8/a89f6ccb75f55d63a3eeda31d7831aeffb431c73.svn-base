package com.integrity.dataSmart.util.titan;

import java.io.IOException;

import com.tinkerpop.rexster.client.RexsterClient;
import com.tinkerpop.rexster.client.RexsterClientFactory;
/**
 * titan client工具类（临时）
 *
 */
public class ClientUtil {
	
	private static ClientUtil clientUtil = new ClientUtil();
	private RexsterClient client = null;
	private ClientUtil(){
		try {
//			client = RexsterClientFactory.open("192.168.20.174", "titangraph");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static ClientUtil getInstance(){
		if (clientUtil == null) {
			clientUtil = new ClientUtil();
		}
		return clientUtil;
	}
	/**
	 * 获取titan client
	 * @return RexsterClient
	 */
	public RexsterClient getClient(){
		return client;
	}
	
	/**
	 * 不能手动调用
	 */
	public void close(){
		try {
			if (client != null) client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
