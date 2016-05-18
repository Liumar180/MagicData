package com.integrity.dataSmart.impAnalyImport.util;

public class ReplaceUtil {
	/**
	 * html页面相关转义
	 * @param target
	 * @return
	 */
	public static String replace(String target){
		if(target!=null){
			target = target.replaceAll("&","&amp;");
			target = target.replaceAll("<","&lt;");
			target = target.replaceAll(">","&gt;");
			target = target.replaceAll("'","&#39;");
			target = target.replaceAll("\"","&quot;");
		}
	
		return target;
	}
	
	/**
	 * html页面相关转义
	 * @param target
	 * @return
	 */
	public static String fileNameReplace(String target){
		if(target!=null){
			target = target.replace("?","");
			target = target.replace("？","");
			target = target.replace("<","");
			target = target.replace(">","");
			target = target.replace("\\","");
			target = target.replace("/","");
			target = target.replace("\"","");
			target = target.replace(":","");
			target = target.replace("*","");
			target = target.replace("|","");
		}
	
		return target;
	}
	
	public static void main(String[] args) {
		String str = "aa.a&a\"\\a:a?aa*a/a<aa>aa'|aa???？:aa";
		
//		String resultStr = ReplaceUtil.replace(str);
		String resultStr = ReplaceUtil.fileNameReplace(str);
		
		System.out.println(resultStr);
	}
}
