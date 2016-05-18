package com.integrity.dataSmart.map.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.dataImport.bean.DataImportTask;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author LiuBf
 *数据导入工具，不同类型数据导入，选择入库代码
 */
public class DataImportLogicUtils {
	/**
	 * @param type 数据类型（account、email、phone）
	 * @return 执行code
	 * 
	 */
	public static String seleLogicCode(String realPath,String type,Integer taskId){
		
		String code = "";
		if(!type.equals("") || !type.equals("null")){
			if(type.equals(DataType.IMPORTDATAACCOUNT)){
				code = readTxtFile(realPath+File.separator+"txt/account.txt",taskId);
			}else if(type.equals(DataType.IMPORTDATAEMAIL)){
				code = readTxtFile(realPath+File.separator+"txt/email.txt",taskId);
			}else if(type.equals(DataType.IMPORTDATAPHONE)){
				code = readTxtFile(realPath+File.separator+"txt/phone.txt",taskId);
			}else if(type.equals(DataType.IMPORTDATAQQ)){
				code = readTxtFile(realPath+File.separator+"txt/qq.txt",taskId);
			}else if(type.equals(DataType.IMPORTDATAHOTEL)){
				code = readTxtFile(realPath+File.separator+"txt/hotels.txt",taskId);
			}
			
		}
		return code;
	}
	
	 /**
	 * @param filePath 读取文件内容
	 */
	public static String readTxtFile(String filePath,Integer taskId){
		String str ="";
	        try {
	                String encoding="utf-8";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){ //判断文件是否存在
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                    	if(lineTxt.equals("TaskId")){
	                    		str += "int taskId = "+taskId+";\n";
	                    		lineTxt = "";
	                    	}
	                        str += lineTxt+"\n";
	                    }
	                    read.close();
	        }else{
	            System.out.println("找不到指定的文件");
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	        }
			return str;
	     
	    }
	
}
