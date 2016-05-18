package com.integrity.dataSmart.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Liubf
 *
 */
public class ProcessUtils {
	
	/**
	 * @param counts 传入的数值
	 * 动态更新properties 文件
	 */
	static public void pro(Long counts){
        Properties prop = new Properties(); 
        String path = "config/dataImport/processBar.properties";
        InputStream in = null; 
        FileOutputStream outputStream = null;
		try {
			String p = ProcessUtils.class.getResource("/").toString();
			String d = p.substring(5,p.length()-8);
	        in = new FileInputStream(new File(d+path));
  			prop.load(in);
  			prop.setProperty("totality",prop.getProperty("totality"));
  			prop.setProperty("importCount", ""+counts);
  			prop.setProperty("isFinish", prop.getProperty("isFinish"));
  		outputStream = new FileOutputStream(new File(d+path));
  	        prop.store(outputStream, "importCount");
  			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(in!=null){
						in.close();
					}
					if(outputStream != null){
						outputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}      
		
	}

}
