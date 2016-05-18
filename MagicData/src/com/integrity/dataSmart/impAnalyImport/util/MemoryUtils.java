package com.integrity.dataSmart.impAnalyImport.util;

/**
 * @author Liubf
 *
 */
public class MemoryUtils {
	/**
     * 获取当前jvm的内存信息
     * @return
     */    
    public static void toMemoryInfo() {
       Runtime runtime = Runtime.getRuntime ();
       int freeMemory = ( int ) (runtime.freeMemory() / 1024 / 1024);
       int totalMemory = ( int ) (runtime.totalMemory() / 1024 / 1024);
       //return freeMemory + "M/" + totalMemory + "M(free/total)" ;
       System.out.println("*****MemoryInfo*******"+freeMemory + "M/" + totalMemory + "M(free/total)");
    }


}
