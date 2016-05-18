package com.integrity.dataSmart.impAnalyImport.util;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Liubf
 *
 */
public class WritelogContents{   
	
	/**
	 * @param fileName 文件路径名
	 * @param content 需要写入的内容
	 */
	public static void writeLogs(String fileName, String content){   
        FileWriter writer = null;  
        try {     
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
            writer = new FileWriter(fileName, true);     
            writer.write(content+"\r\n");       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    }

}   
