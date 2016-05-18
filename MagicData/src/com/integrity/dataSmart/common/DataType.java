package com.integrity.dataSmart.common;

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
	public static final String RESUME = "Resume";
	
	public static final String PERSON_PARENT_TYPE = "Person";//人物类型
	public static final String EVENT_PARENT_TYPE = "Event";//事件类型
	public static final String OWN_PARENT_TYPE = "Own";//拥有类型
	public static final String IM_PARENT_TYPE = "IM";//社交类型
	public static final String GROUP_PARENT_TYPE = "Group";//群组类型
	
	
	/* IM的domain类型 */
	public static final String IMDOMAIN_QQ = "QQ";
	
	/* QQ群组类型  */
	public static final String GROUP_QQ = "QqGroup";
	
	/* 事件类型 */
	public static final String EVENTCALL = "CallEvent";
	public static final String EVENTEMAIL = "EmailEvent";
	public static final String EVENTLOGIN = "LoginEvent";
	public static final String EVENTSTAY = "StayEvent";//住宿事件
	//add by hanxue start
	public static final String EVENTBBS = "BBSEvent";//帖子
	public static final String EVENTMEDICAL = "MedicalEvent";//医疗
	//add by hanxue end
	public static final String ALL = "all";
	
	/* 边label */
	public static final String EMAILFROM = "emailfrom";
	public static final String EMAILTO = "emailto";
	public static final String CALLFROM = "callfrom";
	public static final String CALLTO = "callto";
	public static final String LOGIN = "login";
	public static final String EMAILCC = "emailcc";
	public static final String GROUP = "group";
	public static final String RELATIONAL = "relational";
	public static final String STAY = "stay";//住宿
	public static final String COHABIT = "cohabit";//同住人
	
	
	public static final String NETWORK = "network";
	
	
	//全部需要查询的事件类型
	public static final String[] ALLTYPE = {EVENTCALL,EVENTEMAIL,EVENTLOGIN,EVENTSTAY,EVENTBBS,EVENTMEDICAL};
	
	//map需要查询的事件类型
	public static final String[] MAPTYPE = {EVENTLOGIN};
		
	//全部事件需要查询的label
	public static final String[] ALLLABEL = {EMAILFROM,EMAILTO,CALLFROM,CALLTO,LOGIN,EMAILCC,STAY};
	//电话的事件label
	public static final String[] CALLLABEL = {CALLFROM,CALLTO};
	//邮件的事件label
	public static final String[] EMAILLABEL = {EMAILFROM,EMAILTO,EMAILCC};
	
	//直方图需要过滤的属性
	public static String[] FILTERARR = {"id","type","image","eventtime","content","to","title","from","long"};
	//查询--own属性
	public static String[] OWNPROPERTY = {"username","email","phonenum","numid","resume"};
		
	//默认查询相对时间
	public static final Long TIMEZONE = 365*24*60*60*1000L;
	//日期格式化串
	public static final String DATEFORMATSTR = "yyyy-MM-dd HH:mm:ss";
	
	
	public static final String CALLCOLOR = "#00BC5B";
	public static final String EMAILCOLOR = "#00E2F2";
	public static final String LOGINCOLOR = "#FBD308";
	public static final String STAYCOLOR = "#f358f5";
	//add by hanxue
	public static final String BBSCOLOR = "#0E02FC";
	public static final String MEDICALCOLOR = "#FC1A03";
	
	
	//导入数据源分类
	public static final String IMPORTDATAEMAIL = "email";
	public static final String IMPORTDATAACCOUNT = "account";
	public static final String IMPORTDATAPHONE = "phone";
	public static final String IMPORTDATAQQ = "qq";
	public static final String IMPORTDATAHOTEL = "hotels";
	
	//导入数据源全部分类
	public static final String[] ALLIMPORTDATATYPE = {IMPORTDATAEMAIL,IMPORTDATAACCOUNT,IMPORTDATAPHONE,IMPORTDATAQQ,IMPORTDATAHOTEL};

	//关系类型
	public static final String RELATIONTYPE_QQ = "1";//QQ好友
	public static final String RELATIONTYPE_QQNAME = "QQ好友";
	
	public static final String RELATIONTYPE_MASTER = "2";//户主
	public static final String RELATIONTYPE_MASTERNAME = "户主";
	
	public static final String RELATIONTYPE_FATHER = "3";//父亲
	public static final String RELATIONTYPE_FATHERNAME = "父亲";
	
	public static final String RELATIONTYPE_MOTHER = "4";//母亲
	public static final String RELATIONTYPE_MOTHERNAME = "母亲";
	
	public static final String RELATIONTYPE_SPOUSE = "5";//配偶
	public static final String RELATIONTYPE_SPOUSENAME = "配偶";
	
	//群组类型
	public static final String GROUPTYPE_QQ = "2";//QQ群
	public static final String GROUPTYPE_QQNAME = "QQ群";
	
	//酒店类别
	public static final String HOTELFLAG_RUJIA = "0";//如家
	public static final String HOTELFLAG_RUJIANAME = "如家酒店";
	
	/**
	 * 根据酒店类别获取酒店名称
	 * @param type
	 * @return
	 */
	public static String getHotelNameByType(String type){
		String name = "";
		if (StringUtils.isNotBlank(type)) {
			switch (type) {
			case HOTELFLAG_RUJIA:
				name = HOTELFLAG_RUJIANAME;
				break;
			default:
				break;
			}
		}
		return name;
	}
	
	/**
	 * 根据群组类型获取群组名称
	 * @param type
	 * @return
	 */
	public static String getGroupNameByType(String type){
		String name = "";
		if (StringUtils.isNotBlank(type)) {
			switch (type) {
			case GROUPTYPE_QQ:
				name = GROUPTYPE_QQNAME;
				break;
			default:
				break;
			}
		}
		return name;
	}
	
	/**
	 * 根据关系类型获取名称
	 * @param type
	 * @return
	 */
	public static String getRelationNameByType(String type){
		String name = "";
		if (StringUtils.isNotBlank(type)) {
			switch (type) {
			case RELATIONTYPE_QQ:
				name = RELATIONTYPE_QQNAME;
				break;
			case RELATIONTYPE_MASTER:
				name = RELATIONTYPE_MASTERNAME;
				break;
			case RELATIONTYPE_FATHER:
				name = RELATIONTYPE_FATHERNAME;
				break;
			case RELATIONTYPE_MOTHER:
				name = RELATIONTYPE_MOTHERNAME;
				break;
			case RELATIONTYPE_SPOUSE:
				name = RELATIONTYPE_SPOUSENAME;
				break;
			default:
				break;
			}
		}
		return name;
	}
	
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
			case EVENTSTAY:
				labels = new String[1];
				labels[0] = STAY;
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
