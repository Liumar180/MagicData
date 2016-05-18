package com.integrity.dataSmart.impAnalyImport.bean;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 数据类型常量类
 */
public class DataType {
	
	/* 人物类型和人物属性类型 */
	public static final String PERSON = "Person";
	public static final String LOCATION = "Location";
	public static final String PHONE = "Phone";
	public static final String ACCOUNT = "Account";
	public static final String IM = "IM";
	public static final String EMAIL = "Email";
	
	/* 事件类型 */
	public static final String EVENTCALL = "CallEvent";
	public static final String EVENTEMAIL = "EmailEvent";
	public static final String EVENTLOGIN = "LoginEvent";
	public static final String ALL = "all";
	
	/* 边label */
	public static final String EMAILFROM = "emailfrom";
	public static final String EMAILTO = "emailto";
	public static final String CALLFROM = "callfrom";
	public static final String CALLTO = "callto";
	public static final String LOGIN = "login";
	public static final String EMAILCC = "emailcc";
	
	public static final String NETWORK = "network";
	
	
	//全部需要查询的事件类型
	public static final String[] ALLTYPE = {EVENTCALL,EVENTEMAIL,EVENTLOGIN};
	
	//map需要查询的事件类型
	public static final String[] MAPTYPE = {EVENTLOGIN};
		
	//全部需要查询的label
	public static final String[] ALLLABEL = {EMAILFROM,EMAILTO,CALLFROM,CALLTO,LOGIN,EMAILCC};
	//电话的事件label
	public static final String[] CALLLABEL = {CALLFROM,CALLTO};
	//邮件的事件label
	public static final String[] EMAILLABEL = {EMAILFROM,EMAILTO,EMAILCC};
	
	//直方图需要过滤的属性
	public static String[] FILTERARR = {"id","type","image","eventtime","content","to","title","from","long"};
	//查询--own属性
	public static String[] OWNPROPERTY = {"username","email","phonenum","numid"};
		
	//默认查询相对时间
	public static final Long TIMEZONE = 365*24*60*60*1000L/2;
	
	
	public static final String CALLCOLOR = "#00BC5B";
	public static final String EMAILCOLOR = "#00E2F2";
	public static final String LOGINCOLOR = "#FBD308";

	
	/**
	 * 根据类型获取查询所需的label数组
	 * @param type
	 * @return
	 */
	public static String[] getLabelByType(String type){
		String[] labels = null;
		if (StringUtils.isNotBlank(type)) {
			switch (type) {
			case EVENTCALL:
				labels = CALLLABEL;
				break;
			case EVENTEMAIL:
				labels = EMAILLABEL;
				break;
			case EVENTLOGIN:
				labels = new String[1];
				labels[0] = LOGIN;
				break;
			default:
				labels = new String[1];
				labels[0] = type;
				break;
			}
		}
		return labels;
	}
	
	/**
	 * 根据类型数组获取查询所需的label数组
	 * @param types 类型数组
	 * @return
	 */
	public static String[] getLabelsByTypes(String[] types){
		String[] labels = null;
		if (types!=null&&types.length!=0) {
			for (String type : types) {
				String[] label = getLabelByType(type);
				labels = (String[]) ArrayUtils.addAll(labels, label);
			}
		}
		return labels;
	}
	
}
