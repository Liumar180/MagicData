package com.integrity.dataSmart.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.firebirdsql.jdbc.parser.JaybirdSqlParser.returningClause_return;

/**
 * @author LiuBf
 * 判断字符串是否有顺序或是否是连续相同的字符
 */
public class SameOrOrderStringUtils {
    /** 
     * 判断是否是由同一字符构成的，true代表由同一字符构成的 false反之 
     */  
    public static boolean isSameSymbol(String s){  
        boolean flag=false;  
        //当s为空字符串或者null,认为不是由同一字符构成的  
        if(StringUtils.isEmpty(s)){  
            return flag;  
        }  
        //当只有一个字符的时候，认为由同一字符构成  
        if(1==s.length()){  
            flag=true;  
            return flag;  
        }  
        char chacter=s.charAt(0);  
        for(int i=1;i<=s.length()-1;i++){  
            if(chacter!=s.charAt(i)){  
                flag=false;  
                return flag;  
            }  
        }  
        flag=true;  
        return flag;  
    }  
    /** 
     * 判断是否有顺序 true代表有顺序 false反之 
     */  
   
    public static boolean isOrdered(String s){
    	 //当s为空字符串或者null,认为不是由同一字符构成的  
         if(StringUtils.isEmpty(s)){  
             return false;  
         }  
         //当只有一个字符的时候，认为由同一字符构成  
         if(1==s.length()){  
             return false;  
         }  
         List<Integer> temp1= new ArrayList<Integer>();  
         List<Integer> temp2= new ArrayList<Integer>(); 
         if(Integer.valueOf(s.substring(0,1))-Integer.valueOf(s.substring(1,2))==1){
        	 for(int i=0;i<s.length();i++){
                 temp1.add(Integer.valueOf(s.substring(i,i+1)));  
             }  
         }else if(Integer.valueOf(s.substring(0,1))-Integer.valueOf(s.substring(1,2))==-1){
        	 for(int i=0;i<s.length();i++){
                 temp2.add(Integer.valueOf(s.substring(i,i+1)));  
             } 
         }else{
        	 return false;
         }
         if(temp1.size()>0){
	         for (int i = 0; i < temp1.size(); i++) {
	        	 if(i!=temp1.size()-1){
		        	 if(temp1.get(i)-temp1.get(i+1)!=1){
		        		 return false;
		        	 }
	        	 }
			}
         }
         if(temp2.size()>0){
	         for (int i = 0; i < temp2.size(); i++) {
	        	 if(i!=temp2.size()-1){
	        		 if(temp2.get(i)-temp2.get(i+1)!=-1){
		        		 return false; 
		        	 }
	        	 }
			}
         }
    	 return true;
    }
public static void main(String[] args) {
	System.out.println(isOrdered("1123456"));
	System.out.println(isOrdered("654321"));
	//System.out.println(isOrdered("6534321"));
	
}
}
