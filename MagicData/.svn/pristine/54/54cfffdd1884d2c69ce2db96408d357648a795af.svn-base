package com.integrity.dataSmart.util.importData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Liubf
 *
 */
public class OracleUtils {
	/**
	 * @return 连接oracle 数据库
	 * @throws IOException
	 */
	static public Connection getOrclConnection() throws IOException{
		 InputStream in = null;
		 Connection conn = null;
			String driver = "";
	        String passwrod = "";
	        String userName = "";
	        String url = "";
			String path = "config/jdbc/jdbc.properties";
				String p1 = JdbcUtils.class.getResource("/").toString();
				String d1 = p1.substring(5,p1.length()-8);
				in = new FileInputStream(new File(d1+path));
				Properties p = new Properties();
				p.load(in);
				url = p.getProperty("orcl.jdbcUrl");     
				userName = p.getProperty("orcl.user");
				passwrod = p.getProperty("orcl.password");
				driver = p.getProperty("orcl.driverClass"); 
			
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				try {
					conn = DriverManager.getConnection(url, userName,passwrod);
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					if(in != null){
						in.close();
					}
				}
           return conn;
	}
    /**
     * @param resultset
     * @param ps
     * @param conn
     * 释放资源
     */
    public static void free(ResultSet resultset,PreparedStatement ps,Connection conn)  
    {  
        try{  
        if(resultset!=null)  
            resultset.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try  
            {  
                if(ps!=null)  
                    ps.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
            finally  
            {  
                if(conn!=null)  
                    try {  
                        conn.close();  
                    } catch (SQLException e) {  
                        e.printStackTrace();  
                    }  
            }  
        } 
    }

}
