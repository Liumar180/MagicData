package com.integrity.dataSmart.util.importData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
/**
 * @author liuBf
 *
 */
public class JdbcUtils {
	 /**
	 * @return
	 * @throws IOException
	 * 获取数据库连接
	 */
	static public Connection getConnection() throws IOException{
		 InputStream in = null;
		 Connection conn = null;
			String driver = "";
	        String passwrod = "";
	        String userName = "";
	        String url = "";
			String path = "config/jdbc/jdbc.properties";
			try {
				String p1 = JdbcUtils.class.getResource("/").toString();
				String d1 = p1.substring(5,p1.length()-8);
				in = new FileInputStream(new File(d1+path));
				Properties p = new Properties();
				p.load(in);
				url = p.getProperty("hibernate.jdbcUrl");     
				userName = p.getProperty("hibernate.user");
				passwrod = p.getProperty("hibernate.password");
				driver = p.getProperty("hibernate.driverClass"); 
			} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}finally{
					try {
						if(in!=null)
							in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
            try {
				Class.forName(driver);
				try {
					conn = DriverManager.getConnection(url, userName,passwrod);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
            return conn;
	}
	/**
	 * @return
	 * @throws IOException
	 * 连接sqlserver
	 */
	static public Connection getSqlServerConnection() throws IOException{
		 InputStream in = null;
		 Connection conn = null;
			String driver = "";
	        String passwrod = "";
	        String userName = "";
	        String url = "";
			try {
				url = "jdbc:sqlserver://192.168.40.21:1433; DatabaseName=beyondALL";     
				userName = "sa";
				passwrod = "server109";
				driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; 
			} catch (Exception e1) {
					e1.printStackTrace();
				}finally{
					try {
						if(in!=null)
							in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
           try {
				Class.forName(driver);
				try {
					conn = DriverManager.getConnection(url, userName,passwrod);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
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

