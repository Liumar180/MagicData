package com.integrity.dataSmart.util.importData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.firebirdsql.jdbc.parser.JaybirdSqlParser.returningClause_return;

public class JDBCutil {
	
	public Connection JDBCcon(Connection con,String urlDB,String connectionDBUserName,String connectionDBPassword) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
 		
 		 return con = DriverManager.getConnection(urlDB,connectionDBUserName,connectionDBPassword);
	}
	
	public void getJDBCDriverManager(String connectionDB,String[] connection) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		if("Sybase".equals(connectionDB)||"Sybase"==connectionDB||"Derby"==connectionDB||"Derby".equals(connectionDB)){
			Class.forName(connection[1]).newInstance();  
		 }else{
			Class.forName(connection[1]) ;  
		 }
	}
	public String  getUrlDB(String[] connection,String connectionDB,String connectionServierName,String connectionPort,String connectionDBTableName){
		 if("Mysql".equals(connectionDB)||"HadoopHive".equals(connectionDB)||"DB2".equals(connectionDB)||"Postgresql".equals(connectionDB)||"Greenplum".equals(connectionDB)||"Derby".equals(connectionDB)){
	    	return connection[0]+connectionServierName+":"+connectionPort+"/"+connectionDBTableName;
	     }else if("Oracle".equals(connectionDB)){
	    	 return connection[0]+connectionServierName+":"+connectionPort+":"+connectionDBTableName;
	     }else if("SQLServer".equals(connectionDB)){
	    	 return connection[0]+connectionServierName+":"+connectionPort+";DatabaseName="+connectionDBTableName;
	     }else if("AS400".equals(connectionDB)){
	    	 return connection[0]+connectionServierName+";naming=sql;errors=full";
	     }else if("Sybase".equals(connectionDB)){
	    	 return connection[0]+connectionServierName+":"+connectionPort+"?ServiceName="+connectionDBTableName;
	     }else if("Firebird".equals(connectionDB)||"H2".equals(connectionDB)){
	    	 return connection[0]+connectionServierName+"/"+connectionPort+":"+connectionDBTableName;
	     }else if("Impala".equals(connectionDB)){
	    	 return connection[0]+connectionServierName+":"+connectionPort+"/;auth=noSasl";
	     }else if("Sqlite".equals(connectionDB)){
	    	 return connection[0]+connectionServierName;
	     }else {
			return "";
		}
	}
}
