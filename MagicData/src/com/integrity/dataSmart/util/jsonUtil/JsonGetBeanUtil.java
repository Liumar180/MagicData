package com.integrity.dataSmart.util.jsonUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.integrity.dataSmart.pojo.TianmaoData;
import com.integrity.dataSmart.util.SameOrOrderStringUtils;

/**
 * @author integrity
 * 
 * 将solr中部分有效数据解析
 *
 */
public class JsonGetBeanUtil {
	
	/**
     * 将Json对象转换成Map
     * 
     * @param jsonObject
     *            json对象
     * @return Map对象
     * @throws JSONException
     */
    public static Map toMap(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        
        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;

    }
    /**
     * 天猫json解析
     * @param jsonString  
     * @param flag 是否是只获取domain 
     * @return
     * @throws JSONException
     */
    public  static TianmaoData getTianmaoByJson(String jsonString,Boolean flag) throws JSONException{
    	TianmaoData tianmaoData= new TianmaoData();
		 Map<String,String> map = toMap(new JSONObject(jsonString).toString());
		 if (flag) {
	     	tianmaoData.setDomain(map.get("内容简介"));
	     	return tianmaoData;
		 }
		 Set<String> set = map.keySet();
	        for (String key : set) {
	        	//lobster_info 数据信息
	        	 if("lobster_info".equals(key)){
		            	if(map.get(key).indexOf("{")>-1){
			            	 Map<String,String> map1 = toMap(new JSONObject(map.get(key)).toString());
			            	 for (String key1 : map1.keySet()) {  
			            		 if("info_type".equals(key1)){
			            			 String infoType = map1.get(key1);
			            			 if (infoType != null) {
			            				 infoType = infoType.replaceAll(" ", "");
			            				 if ("电子商务".equals(infoType)) {
			            					 tianmaoData.setInfo_type(infoType);
			            				 }else {
			            					 return null;
											
										}
			            				 
			            			 }
			            		 }
			            		 if("info_rowid".equals(key1)){
			     	        		if(map1.get(key1)=="b20489219934cf43acd8700ce3b60ded" || "b20489219934cf43acd8700ce3b60ded".equals(map1.get(key1))){
			     	        			return null;
			     	        		}else {
			     	        			tianmaoData.setInfo_rowid(map1.get(key1));
									}
			     	        	 }
			            		 if("info_table".equals(key1)){
			            			 tianmaoData.setInfo_table(map1.get(key1));
			            		 }
			            	 }
		            	}
		            }
	        	
	        	//电话
	            if("手机".equals(key)||"电话".equals(key)){
	            	if(map.get(key).indexOf("{")>-1){
		            	 Map<String,String> map1 = toMap(new JSONObject(map.get(key)).toString());
		            	 for (String key1 : map1.keySet()) {  
		            		 if("value".equals(key1)){
		            			 Pattern p=Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9\\s*\\-]+\\)?-?)?[0-9\\s*\\-]{7,8}");          
		            		       Matcher m=p.matcher(map1.get(key1));    
		            		       if(m.matches()){  
		            		    	   if(!SameOrOrderStringUtils.isSameSymbol(map1.get(key1).substring(map1.get(key1).length()-7))&&!SameOrOrderStringUtils.isOrdered(map1.get(key1).substring(map1.get(key1).length()-7))){
		            		    		   tianmaoData.setPhone(map1.get(key1)); 
		            		    	   }
		            		       }  
		            		 }
		            	 }
	            	}
	            }
	            //姓名
	            if("姓名".equals(key)){
	            	tianmaoData.setName(map.get(key));
	            }
	            if("内容简介".equals(key)){
	            	tianmaoData.setDomain(map.get(key));
	            }
	            //Email
	            if("Email".equals(key)){
	            	if(map.get(key).indexOf("{")>-1){
	            		Map<String,String> map1 = toMap(new JSONObject(map.get(key)).toString());
		            	 for (String key1 : map1.keySet()) {  
		            		 if("value".equals(key1)){
		            			Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");   
	            		        Matcher m = p.matcher(map1.get(key1));   
	            		        while (m.find()){
		            		        tianmaoData.setEmail(map1.get(key1));
		            		        break;
		            		    }
		            		 }
		            	 }
	            	}
	            }
	            //QQ
	            if("QQ".equals(key)){
	            	if(map.get(key).indexOf("{")>-1){
		            	 Map<String,String> map1 = toMap(new JSONObject(map.get(key)).toString());
		            	 for (String key1 : map1.keySet()) {  
		            		 if("value".equals(key1)){
		            			 tianmaoData.setQq(map1.get(key1));
		            		 }
		            	 }
	            	}
	            }
	          //用户名
	            if("用户名".equals(key)||"天猫账号".equals(key)){
	            	if(map.get(key).indexOf("{")>-1){
		            	 Map<String,String> map1 = toMap(new JSONObject(map.get(key)).toString());
		            	 for (String key1 : map1.keySet()) {  
		            		 if("value".equals(key1)){
		            			 tianmaoData.setUsername(map1.get(key1));
		            		 }
		            	 }
	            	}
	            }
	          //密码
	            if("密码".equals(key)){
	            	if(map.get(key).indexOf("{")>-1){
		            	 Map<String,String> map1 = toMap(new JSONObject(map.get(key)).toString());
		            	 for (String key1 : map1.keySet()) {  
		            		 if("value".equals(key1)){
		            			 tianmaoData.setPassword(map1.get(key1));
		            		 }
		            	 }
	            	}
	            }
	          //地址
	            if("地址".equals(key)||"所在地".equals(key)){
	            	if(map.get(key).indexOf("{")>-1){
		            	 Map<String,String> map1 = toMap(new JSONObject(map.get(key)).toString());
		            	 for (String key1 : map1.keySet()) {  
		            		 if("value".equals(key1)){
		            			 tianmaoData.setAddress(map1.get(key1));
		            		 }
		            	 }
	            	}
	            }
	          
	        }  
    	return tianmaoData;
    }
	 public static void main(String[] args) {
		 String jsonString ="{\"电话\": {\"code\": \"mobile\", \"attr\": \"mobile\", \"value\": \"2212113456789\"}, \"lobster_info\": {\"info_type\": \"电子商务\", \"info_table\": \"lb_info\", \"info_rowid\": \"24ab78fd37130d5366a7fc20ced790d5\"},\"Email\":{\"code\":\"email\",\"value\": \"fanyouzhen@tom.com\"}}";
//		 String jsonString1 ="{\"电话\": {\"code\": \"mobile\", \"attr\": \"mobile\", \"value\": \"15210334947\"}, \"QQ\": {\"code\": \"qq\", \"value\": \"573776982\"}, \"lobster_info\": {\"info_type\": \"互联网数据\", \"info_table\": \"lb_info\", \"info_rowid\": \"b20489219934cf43acd8700ce3b60ded\"}, \"Email\": \"zxczc\", \"姓名\": \"杨帆\"}";
//		 String jsonString2 ="{\"电话\": {\"code\": \"mobile\", \"attr\": \"mobile\", \"value\": \"15210334962\"}, \"地址\": {\"attr\": \"address\", \"value\": \"北京 北京市 丰台区 六里桥太平桥路尚西泊图永琪美容美发\"}, \"lobster_info\": {\"info_type\": \"电子商务\", \"info_table\": \"lb_info\", \"info_rowid\": \"c2ca4f729876b6645c36c837af82503b\"}, \"姓名\": \"刘爽\"}";
//		 String jsonString3 ="{\"电话\": {\"code\": \"mobile\", \"attr\": \"mobile\", \"value\": \"13682019876\"}, \"QQ\": {\"code\": \"qq\", \"value\": \"243914062\"}, \"lobster_info\": {\"info_type\": \"互联网数据\", \"info_table\": \"lb_info\", \"info_rowid\": \"80ecfa630165093ada2f7bf3e406a0c3\"}, \"Email\": \"\", \"姓名\": \"刘紫鑫㊣Lawrence\"}";
		 try {
				TianmaoData tianmaoData=getTianmaoByJson(jsonString,false);
				System.out.println(tianmaoData);
			} catch (JSONException e) {
				e.printStackTrace();
			}
//	        System.out.println("22228888888".substring("22228888888".length()-7));
//	        System.out.println(SameOrOrderStringUtils.isSameSymbol("123456"));
//	        System.out.println(SameOrOrderStringUtils.isSameSymbol("8765432"));
		 //String str="13112341234,010-12456789,01012456789,(010)12456789,0086 1012456789,+86 1012456789";  
//		 String str="11111111111121";  
//		 
//		 System.out.println(SameOrOrderStringUtils.isSameSymbol(str));
//	       Pattern p=Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9\\s*\\-]+\\)?-?)?[0-9\\s*\\-]{7,8}");      
	     //  Pattern p1=Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?\\s)?[0-9]{7,8}");
	       //Pattern p2=Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4}\\s)?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
	     //  Pattern p2=Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
//	       Matcher m=p.matcher(str); 
//	       if(m.matches()){
//	    	   System.out.println(m.group());
//	       }
//	       Matcher m=p.matcher(str);    
//	       if(m.matches()){  
//	           System.out.println(m.group());           
//	       }  
	}
}
