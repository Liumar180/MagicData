package com.integrity.dataSmart.common.dataCore;

/**
 * @author liubf
 * 使用装饰者模式实现动态加载数据源
 *
 */
public class DatabaseContextHolder {
	public static final String DATA_SOURCE_ONE = "dataSource";  
    public static final String DATA_SOURCE_TWO = "dataSourceOracle";  
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
	  
    public static void setCustomerType(String customerType) {  
        contextHolder.set(customerType);  
    }  
  
    public static String getCustomerType() {  
        return contextHolder.get();  
    }  
  
    public static void clearCustomerType() {  
        contextHolder.remove();  
    }  

}
