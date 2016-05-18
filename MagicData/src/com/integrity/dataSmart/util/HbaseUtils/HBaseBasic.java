package com.integrity.dataSmart.util.HbaseUtils;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONException;

import com.integrity.dataSmart.pojo.TianmaoData;
import com.integrity.dataSmart.util.jsonUtil.JsonGetBeanUtil;

  
/**
 * @author Liubf
 * Hbase数据库操作
 *
 */
public class HBaseBasic {  
    private static HBaseConfiguration hbaseConfig=null;  
    static{  
        Configuration config=new Configuration();  
        config.set("hbase.zookeeper.quorum","192.168.40.10");  
        config.set("hbase.zookeeper.property.clientPort", "2181");  
        hbaseConfig=new HBaseConfiguration(config);  
    }  
      
    /** 
     * get方式，通过rowKey查询 
     * @param tablename 
     * @param rowKey 
     * @throws IOException 
     * @throws JSONException 
     */  
    public static String selectByRowKey(String tablename,String rowKey) throws IOException{  
        HTable table=new HTable(hbaseConfig,tablename); 
        Get g = new Get(Bytes.toBytes(rowKey)); 
        Result r=table.get(g);  
        String domain = "";
        for(KeyValue kv:r.raw()){
        	try {
				TianmaoData t = JsonGetBeanUtil.getTianmaoByJson(new String(kv.getValue()),true);
				if (t == null) {
					return domain;
				}
				domain = t.getDomain();
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
		return domain;
    }  
      
    /** 
     * get方式，通过rowKey、column查询 
     * @param tablename 
     * @param rowKey 
     * @param column 
     * @throws IOException 
     */  
    public static void selectByRowKeyColumn(String tablename,String rowKey,String column) throws IOException{  
        HTable table=new HTable(hbaseConfig,tablename);  
        Get g = new Get(Bytes.toBytes(rowKey));  
      Result r=table.get(g);  
        for(KeyValue kv:r.raw()){  
           System.out.println("value: "+new String(kv.getValue()));  
        }  
    }  
      
      
    public static void selectByFilter(String tablename,List<String> arr) throws IOException{  
        HTable table=new HTable(hbaseConfig,tablename);  
        FilterList filterList = new FilterList();  
        Scan s1 = new Scan();  
        for(String v:arr){ // 各个条件之间是“与”的关系  
            String [] s=v.split(",");  
            filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(s[0]),  
                                                             Bytes.toBytes(s[1]),  
                                                             CompareOp.EQUAL,Bytes.toBytes(s[2])  
                                                             )  
            );  
            // 添加下面这一行后，则只返回指定的cell，同一行中的其他cell不返回  
//          s1.addColumn(Bytes.toBytes(s[0]), Bytes.toBytes(s[1]));  
        }  
        s1.setFilter(filterList);  
        ResultScanner ResultScannerFilterList = table.getScanner(s1);  
        for(Result rr=ResultScannerFilterList.next();rr!=null;rr=ResultScannerFilterList.next()){  
            for(KeyValue kv:rr.list()){ 
                System.out.println("row : "+new String(kv.getRow()));  
                System.out.println("value : "+new String(kv.getValue()));  
            }  
        }  
  }  
 

}
