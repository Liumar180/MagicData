package com.integrity.dataSmart.map.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PostGreSqlConnectionUtil {
	private Logger logger = Logger.getLogger(PostGreSqlConnectionUtil.class);
	private String url = "";
	private String username = "";
	private String password = "";
	private String driver = "";
	public static PostGreSqlConnectionUtil connectionUtil = new PostGreSqlConnectionUtil();
	
	private PostGreSqlConnectionUtil(){
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("../config/postGreSql/postGreSql.properties");
			Properties p = new Properties();
			p.load(inputStream);
			url = p.getProperty("url");     
			username = p.getProperty("username");
			password = p.getProperty("password");
			driver = p.getProperty("driver");  
		} catch (IOException e) {
			logger.error("PostGreSqlConnectionUtil init error", e);
		}finally{
			try {
				if(inputStream!=null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static PostGreSqlConnectionUtil getInstance(){
		if (connectionUtil == null) {
			connectionUtil = new PostGreSqlConnectionUtil();
		}
		return connectionUtil;
	}

	public Connection conn() throws Exception {
		Connection connre = null;
		try {
			Class.forName(driver);
			connre = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			logger.error("PostGreSql getConnection error", e);
		}
		return connre;
	}
	
	public void closeConnection(Connection connre){
			try {
				if(connre != null)
					connre.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
