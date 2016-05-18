package com.integrity.dataSmart.dataImport.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;

/**
 * 获取Titan结构工具类
 * @author RenSx
 *
 */
public class TitanStructureUtil {
	private static Logger logger = Logger.getLogger(TitanStructureUtil.class);
	//titan结构存放位置
	private static String savePathStru = TitanStructureUtil.class.getResource("/").getFile().replace("/classes", "")+"config/titan/titanStru.txt";
	//默认显示属性存放位置
	private static String savePathAttr = TitanStructureUtil.class.getResource("/").getFile().replace("/classes", "")+"config/titan/titanDefaultAttr.txt";
	//配置别名存放位置
	private static String savePathAlias = TitanStructureUtil.class.getResource("/").getFile().replace("/classes", "")+"config/titan/titanStruAlias.txt";
	//父节点类型存放位置
	private static String savePathParentType = TitanStructureUtil.class.getResource("/").getFile().replace("/classes", "")+"config/titan/titanStruParentType.txt";
	
	/**
	 * 事件节点合并规则初始化
	 * @return
	 */
	public static List<Map<String,List<String>>> createMergeRules(){
		List<Map<String,List<String>>> mergeRulesList = new ArrayList<Map<String,List<String>>>();
		//电话事件
		Map<String,List<String>> CallEventMap = new HashMap<String,List<String>>();
		List<String> CallEventList = new ArrayList<String>();
		CallEventList.add("from");
		CallEventList.add("to");
		CallEventMap.put("CallEvent", CallEventList);
		mergeRulesList.add(CallEventMap);
		//邮件事件
		Map<String,List<String>> EmailEventMap = new HashMap<String,List<String>>();
		List<String> EmailEventList = new ArrayList<String>();
		EmailEventList.add("from");
		EmailEventList.add("to");
		EmailEventMap.put("CallEvent", EmailEventList);
		mergeRulesList.add(EmailEventMap);
		//住宿事件
		Map<String,List<String>> StayEventMap = new HashMap<String,List<String>>();
		List<String> StayEventList = new ArrayList<String>();
		StayEventList.add("hotelid");
		StayEventList.add("orderno");
		StayEventList.add("hotelflag");
		StayEventMap.put("StayEvent",StayEventList);
		return mergeRulesList;
	}
	
	/**
	 * 初始化节点父类型
	 * @return
	 */
	public static Map<String,List<String>> createParentType(){
		Map<String,List<String>> m = new LinkedHashMap<String,List<String>>();
		List<String> person = new ArrayList<String>();
		person.add(DataType.PERSON);
		m.put(DataType.PERSON_PARENT_TYPE, person);
		List<String> event = new ArrayList<String>();
		event.add(DataType.EVENTCALL);
		event.add(DataType.EVENTEMAIL);
		event.add(DataType.EVENTLOGIN);
		event.add(DataType.EVENTSTAY);
		m.put(DataType.EVENT_PARENT_TYPE, event);
		List<String> own = new ArrayList<String>();
		own.add(DataType.PHONE);
		own.add(DataType.RESUME);
		own.add(DataType.ACCOUNT);
		own.add(DataType.EMAIL);
		own.add(DataType.LOCATION);
		own.add(DataType.IMDOMAIN_QQ);
		m.put(DataType.OWN_PARENT_TYPE, own);
		List<String> im = new ArrayList<String>();
		im.add(DataType.IMDOMAIN_QQ);
		m.put(DataType.IM_PARENT_TYPE, im);
		List<String> group = new ArrayList<String>();
		group.add(DataType.GROUP_QQ);
		m.put(DataType.GROUP_PARENT_TYPE, group);
		try {
			File f = new File(savePathParentType);
			if(!f.exists()){
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(m);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	} 
	
	/**
	 * 获取节点父类型
	 * @return
	 */
	public static Map<String, List<String>> getParentType(){
		File f = new File(savePathParentType);
		if(!f.exists()){
			createParentType();
		}
		ObjectInputStream ois = null;
		Map<String,List<String>> m = new LinkedHashMap<String,List<String>>();
		try{
			ois = new ObjectInputStream(new FileInputStream(f));
			m = (Map<String, List<String>>)ois.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	/**
	 * 添加子节点到父节点类型下面（更新父节点类型）
	 * @param vertexName
	 * @param vertexNameParent
	 * @return
	 */
	public static Map<String, List<String>> addChildrenTypeToParentType(String vertexName, String vertexNameParent) {
		Map<String,List<String>> m = new LinkedHashMap<String,List<String>>();
		ObjectInputStream ois = null;
		try{
			File f = new File(savePathParentType);
			if(!f.exists()){
				getParentType();
			}
			ois = new ObjectInputStream(new FileInputStream(f));
			m = (Map<String, List<String>>)ois.readObject();
			Set<String> keySet = m.keySet();
			for(String type:keySet){
				if(type.equals(vertexNameParent)){
					m.get(type).add(vertexName);
				}
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(m);
			oos.close();
			//更新父节点类型缓存
			ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			sc.setAttribute("parentType", JacksonMapperUtil.getObjectMapper().writeValueAsString(m));
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}
		
	/**
	 * 初始化默认显示属性
	 * @return
	 */
	public static Map<String, List<String>> createDefaultShow(){
		//默认显示属性
		Map<String, List<String>> showAttr = new LinkedHashMap<String,List<String>>();
		//Person
		List<String> attr_Person = new ArrayList<String>();
		attr_Person.add("name");
		attr_Person.add("idcard");
		showAttr.put("Person", attr_Person);
		//Phone
		List<String> attr_Phone = new ArrayList<String>();
		attr_Phone.add("phonenum");
		attr_Phone.add("model");
		showAttr.put("Phone", attr_Phone);
		//Resume
		List<String> attr_Resume = new ArrayList<String>();
		attr_Resume.add("title");
		attr_Resume.add("keywords");
		showAttr.put("Resume", attr_Resume);
		//Account
		List<String> attr_Account = new ArrayList<String>();
		attr_Account.add("username");
		attr_Account.add("phonenum");
		showAttr.put("Account", attr_Account);
		//Email
		List<String> attr_Email = new ArrayList<String>();
		attr_Email.add("email");
		showAttr.put("Email", attr_Email);
		//Location
		List<String> attr_Location = new ArrayList<String>();
		attr_Location.add("address");
		attr_Location.add("place");
		showAttr.put("Location", attr_Location);
		//IM
		List<String> attr_IM = new ArrayList<String>();
		attr_IM.add("numid");
		attr_IM.add("nickname");
		showAttr.put("QQ", attr_IM);
		//Group
		List<String> attr_Group = new ArrayList<String>();
		attr_Group.add("groupnum");
		attr_Group.add("groupname");
		showAttr.put("QqGroup", attr_Group);
		//CallEvent
		List<String> attr_CallEvent = new ArrayList<String>();
		attr_CallEvent.add("from");
		attr_CallEvent.add("to");
		showAttr.put("CallEvent", attr_CallEvent);
		//LoginEvent
		List<String> attr_LoginEvent = new ArrayList<String>();
		attr_LoginEvent.add("domain");
		attr_LoginEvent.add("username");
		showAttr.put("LoginEvent", attr_LoginEvent);
		//EmailEvent
		List<String> attr_EmailEvent = new ArrayList<String>();
		attr_EmailEvent.add("title");
		showAttr.put("EmailEvent", attr_EmailEvent);
		//StayEvent
		List<String> attr_StayEvent = new ArrayList<String>();
		attr_StayEvent.add("hotelname");
		showAttr.put("StayEvent", attr_StayEvent);
		try {
			File f = new File(savePathAttr);
			if(!f.exists()){
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(showAttr);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return showAttr;
	}
	
	/**
	 * 获取默认显示属性
	 * @return
	 */
	public static Map<String, List<String>> getDefaultShow(){
		File f = new File(savePathAttr);
		if(!f.exists()){
			createDefaultShow();
		}
		ObjectInputStream ois = null;
		Map<String, List<String>> showAttr = new HashMap<String,List<String>>();
		try{
			ois = new ObjectInputStream(new FileInputStream(f));
			showAttr = (Map<String, List<String>>)ois.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return showAttr;
	}
	
	/**
	 * 修改默认显示属性
	 * @param showArr
	 * @return
	 */
	public static Map<String,List<String>> updateDefaultShow(String showArr){
		Map<String, List<String>> showAttr = new HashMap<String,List<String>>();
		try{
			showAttr = JacksonMapperUtil.getObjectMapper().readValue(showArr, showAttr.getClass());
			File f = new File(savePathAttr);
			if(f.exists()){
				f.delete();
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(showAttr);
			oos.close();
			//更新默认显示属性缓存
			ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			sc.setAttribute("pageViewConfig", JacksonMapperUtil.getObjectMapper().writeValueAsString(showAttr));
		}catch(Exception e){
			e.printStackTrace();
		}
		return showAttr;
	}
	
	
	/**
	 * 初始化别名
	 * @return
	 */
	public static Map<String, Map<String, String>> createTitanAlias(){
		//别名
		Map<String, Map<String, String>> aliasAll = new LinkedHashMap<String,Map<String, String>>();
		Map<String, String> PersonAlias = new LinkedHashMap<String,String>();
		PersonAlias.put("Person", "人物");
		PersonAlias.put("name", "姓名");
		PersonAlias.put("idcard", "身份证号");
		PersonAlias.put("country", "国籍");
		PersonAlias.put("sex", "性别");
		PersonAlias.put("birthday", "生日");
		aliasAll.put("Person", PersonAlias);
		Map<String, String> PhoneAlias = new LinkedHashMap<String,String>();
		PhoneAlias.put("Phone", "手机");
		PhoneAlias.put("model", "型号");
		PhoneAlias.put("phonenum", "手机号");
		aliasAll.put("Phone", PhoneAlias);
		Map<String, String> ResumeAlias = new LinkedHashMap<String,String>();
		ResumeAlias.put("Resume", "简历");
		ResumeAlias.put("title", "标题");
		ResumeAlias.put("keywords", "关键字");
		ResumeAlias.put("time", "时间");
		aliasAll.put("Resume", ResumeAlias);
		Map<String, String> AccountAlias = new LinkedHashMap<String,String>();
		AccountAlias.put("Account", "网络账号");
		AccountAlias.put("domain", "域名");
		AccountAlias.put("uid", "用户id");
		AccountAlias.put("username", "用户名");
		AccountAlias.put("password", "密码");
		AccountAlias.put("email", "邮箱");
		AccountAlias.put("createtime", "创建时间");
		AccountAlias.put("phonenum", "手机号");
		AccountAlias.put("regip", "注册IP");
		AccountAlias.put("question", "密保问题");
		aliasAll.put("Account", AccountAlias);
		Map<String, String> EmailAlias = new LinkedHashMap<String,String>();
		EmailAlias.put("Email", "邮箱");
		EmailAlias.put("email", "邮箱");
		EmailAlias.put("password", "密码");
		aliasAll.put("Email", EmailAlias);
		Map<String, String> LocationAlias = new LinkedHashMap<String,String>();
		LocationAlias.put("Location", "地址");
		LocationAlias.put("address", "地址");
		LocationAlias.put("place", "位置");
		aliasAll.put("Location", LocationAlias);
		Map<String, String> IMAlias = new LinkedHashMap<String,String>();
		IMAlias.put("QQ", "QQ");
		IMAlias.put("numid", "qq号码");
		IMAlias.put("nickname", "昵称");
		aliasAll.put("QQ", IMAlias);
		Map<String, String> GroupAlias = new LinkedHashMap<String,String>();
		GroupAlias.put("QqGroup", "群组");
		GroupAlias.put("groupnum", "群号");
		GroupAlias.put("createtime", "创建时间");
		GroupAlias.put("ownerqq", "群主账号");
		GroupAlias.put("groupname", "群名");
		GroupAlias.put("groupdesc", "群描述");
		aliasAll.put("QqGroup", GroupAlias);
		Map<String, String> CallEventAlias = new LinkedHashMap<String,String>();
		CallEventAlias.put("CallEvent", "电话事件");
		CallEventAlias.put("from", "主叫号码");
		CallEventAlias.put("to", "被叫号码");
		CallEventAlias.put("time", "时间");
		CallEventAlias.put("long", "时长");
		aliasAll.put("CallEvent", CallEventAlias);
		Map<String, String> LoginEventAlias = new LinkedHashMap<String,String>();
		LoginEventAlias.put("LoginEvent", "登录事件");
		LoginEventAlias.put("domain", "域名");
		LoginEventAlias.put("username", "用户名");
		LoginEventAlias.put("ip", "登录IP");
		LoginEventAlias.put("time", "时间");
		aliasAll.put("LoginEvent", LoginEventAlias);
		Map<String, String> EmailEventAlias = new LinkedHashMap<String,String>();
		EmailEventAlias.put("EmailEvent", "邮件事件");
		EmailEventAlias.put("title", "邮件名");
		EmailEventAlias.put("content", "邮件内容");
		EmailEventAlias.put("from", "发件人");
		EmailEventAlias.put("to", "收件人");
		EmailEventAlias.put("time", "时间");
		EmailEventAlias.put("messageId", "消息ID");
		aliasAll.put("EmailEvent", EmailEventAlias);
		Map<String, String> StayEventAlias = new LinkedHashMap<String,String>();
		StayEventAlias.put("StayEvent", "住宿事件");
		StayEventAlias.put("hotelid", "酒店编号");
		StayEventAlias.put("hotelname", "酒店名称");
		StayEventAlias.put("orderno", "订单号");
		StayEventAlias.put("arrivedate", "入住时间");
		StayEventAlias.put("departuredate", "离开时间");
		StayEventAlias.put("roomno", "房间号");
		StayEventAlias.put("hotelflag", "酒店标识");
		aliasAll.put("StayEvent", StayEventAlias);
		try {
			File f = new File(savePathAlias);
			if(!f.exists()){
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(aliasAll);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aliasAll;
	}
	
	/**
	 * 获取别名
	 * @return
	 */
	public static Map<String, Map<String, String>> getTitanAlias(){
		File f = new File(savePathAlias);
		if(!f.exists()){
			createTitanAlias();
		}
		ObjectInputStream ois = null;
		Map<String, Map<String, String>> aliasAll = new HashMap<String,Map<String,String>>();
		try{
			ois = new ObjectInputStream(new FileInputStream(f));
			aliasAll = (Map<String, Map<String, String>>)ois.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aliasAll;
	}
	
	/**
	 * 修改titan属性别名
	 * @param aliasAll
	 * @return
	 */
	public static boolean updateTitanAlias(String aliasAll) {
		Map<String, Map<String, String>> aliasAllMap = new LinkedHashMap<String,Map<String, String>>();
		try{
			aliasAllMap = JacksonMapperUtil.getObjectMapper().readValue(aliasAll, aliasAllMap.getClass());
			File f = new File(savePathAlias);
			if(f.exists()){
				f.delete();
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(aliasAllMap);
			oos.close();
			//修改属性别名缓存
			ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			sc.setAttribute("aliasAll", JacksonMapperUtil.getObjectMapper().writeValueAsString(aliasAllMap));
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 获取可查询属性(从最初结构的vertex获取)
	 * @return
	 */
	public static Map<String,List<String>> findQueryableMap(){
		List<Map<String, Object>> list = getTitanStructure();
		Map<String,List<String>> qMap = new LinkedHashMap<String,List<String>>();
		Map<String, Object> vertexs = list.get(0);
		for(String key : vertexs.keySet()){
			List<Map<String,Object>> l = (List<Map<String,Object>>)vertexs.get(key);
			List<String> qList = new ArrayList<String>();
			for(int i=0;i<l.size();i++){
				Map<String, Object> map = l.get(i);
				String queryable = (String)map.get("queryable");
				if("1".equals(queryable)){
					qList.add((String)map.get("name"));
				}
			}
			if(qList.size()>0){
				qMap.put(key, qList);
			}
		}
		return qMap;
	}
	
	
	/**
	 * 初始化titan结构
	 * @return
	 */
	public static List<Map<String,Object>> createTitanStructure(){
		/*  list中有两个元素 
	 	第一个为点的Map(key为节点的类型（type），value为节点的属性Map（key为属性名称，value为属性值类型标识；如果没有属性则为空map）)
	 	第二个为边的Map(key为边的标签（label），value为边的属性Map（key为属性名称，value为属性值类型标识；如果没有属性则为空map）)
		*/
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//点集合
		Map<String, Object> vertex = new LinkedHashMap<String,Object>();
		//边集合
		Map<String, Object> edge = new LinkedHashMap<String,Object>();
		
		list.add(vertex);
		list.add(edge);

		//Person
		List<Map<String,Object>> vertex_Person = new ArrayList<Map<String,Object>>();
		Map<String,Object> name = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(name, "name", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Person.add(name);
		Map<String,Object> idcard = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(idcard, "idcard", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Person.add(idcard);
		Map<String,Object> country = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(country, "country", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Person.add(country);
		Map<String,Object> sex = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(sex, "sex", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Person.add(sex);
		Map<String,Object> birthday = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(birthday,"birthday", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Person.add(birthday);
		Map<String,Object> type = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(type,DataType.PERSON_PARENT_TYPE, DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Person.add(type);
		vertex.put("Person",vertex_Person);
		//Phone
		List<Map<String,Object>> vertex_Phone = new ArrayList<Map<String,Object>>();
		Map<String,Object> model = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(model,"model", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Phone.add(model);
		Map<String,Object> phonenum = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(phonenum,"phonenum", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Phone.add(phonenum);
		vertex.put("Phone", vertex_Phone);
		//Resume
		List<Map<String,Object>> vertex_Resume = new ArrayList<Map<String,Object>>();
		Map<String,Object> title = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(title, "title", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Resume.add(title);
		Map<String,Object> keywords = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(keywords, "keywords", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Resume.add(keywords);
		Map<String,Object> time = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(time, "time", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Resume.add(time);
		vertex.put("Resume", vertex_Resume);
		//Account
		List<Map<String,Object>> vertex_Account = new ArrayList<Map<String,Object>>();
		Map<String,Object> domain = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(domain, "domain", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Account.add(domain);
		Map<String,Object> uid = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(uid, "uid", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Account.add(uid);
		Map<String,Object> username = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(username, "username", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Account.add(username);
		Map<String,Object> password = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(password, "password", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Account.add(password);
		Map<String,Object> email = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(email, "email", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Account.add(email);
		Map<String,Object> createtime = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(createtime, "createtime", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Account.add(createtime);
		Map<String,Object> phone = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(phone, "phonenum", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Account.add(phone);
		Map<String,Object> regip = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(regip, "regip", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Account.add(regip);
		Map<String,Object> question = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(question, "question", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Account.add(question);
		vertex.put("Account", vertex_Account);
		//Email
		List<Map<String,Object>> vertex_Email = new ArrayList<Map<String,Object>>();
		Map<String,Object> email1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(email1, "email", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Email.add(email1);
		Map<String,Object> password1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(password1, "password", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Email.add(password1);
		vertex.put("Email", vertex_Email);
		//Location
		List<Map<String,Object>> vertex_Location = new ArrayList<Map<String,Object>>();
		Map<String,Object> address = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(address, "address", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Location.add(address);
		Map<String,Object> place = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(place, "place", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Location.add(place);
		vertex.put("Location", vertex_Location);
		//IM
		List<Map<String,Object>> vertex_IM = new ArrayList<Map<String,Object>>();
		/*Map<String,Object> domain1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(domain1, "domain", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue(),"im标识");
		vertex_IM.add(email1);*/
		Map<String,Object> numid = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(numid, "numid", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_IM.add(numid);
		Map<String,Object> nickname = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(nickname, "nickname", DataTypeTitan.ARRAY.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_IM.add(nickname);
		vertex.put("QQ", vertex_IM);
		//Group
		List<Map<String,Object>> vertex_Group = new ArrayList<Map<String,Object>>();
		Map<String,Object> groupnum = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(groupnum, "groupnum", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Group.add(groupnum);
		Map<String,Object> createtime1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(createtime1, "createtime", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Group.add(createtime1);
		Map<String,Object> ownerqq = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(ownerqq, "ownerqq", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Group.add(ownerqq);
		Map<String,Object> groupname = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(groupname, "groupname", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_Group.add(groupname);
		Map<String,Object> groupdesc = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(groupdesc, "groupdesc", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_Group.add(groupdesc);
		vertex.put("QqGroup",vertex_Group);
		//CallEvent
		List<Map<String,Object>> vertex_CallEvent = new ArrayList<Map<String,Object>>();
		Map<String,Object> from = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(from, "from", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_CallEvent.add(from);
		Map<String,Object> to = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(to, "to", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_CallEvent.add(to);
		Map<String,Object> time1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(time1, "time", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_CallEvent.add(time1);
		Map<String,Object> Long = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(Long, "long", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_CallEvent.add(Long);
		Map<String,Object> type1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(type1,DataType.EVENT_PARENT_TYPE, DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_CallEvent.add(type1);
		vertex.put("CallEvent",vertex_CallEvent);
		//LoginEvent
		List<Map<String,Object>> vertex_LoginEvent = new ArrayList<Map<String,Object>>();
		Map<String,Object> domain2 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(domain2, "domain", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_LoginEvent.add(domain2);
		Map<String,Object> username1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(username1, "username", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_LoginEvent.add(username1);
		Map<String,Object> ip = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(ip, "ip", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_LoginEvent.add(ip);
		Map<String,Object> time2 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(time2, "time", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_LoginEvent.add(time2);
		Map<String,Object> type2 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(type2,DataType.EVENT_PARENT_TYPE, DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_LoginEvent.add(type2);
		vertex.put("LoginEvent",vertex_LoginEvent);
		//EmailEvent
		List<Map<String,Object>> vertex_EmailEvent = new ArrayList<Map<String,Object>>();
		Map<String,Object> title1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(title1, "title", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_EmailEvent.add(title1);
		Map<String,Object> content = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(content, "content", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_EmailEvent.add(content);
		Map<String,Object> from1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(from1, "from", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_EmailEvent.add(from1);
		Map<String,Object> to1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(to1, "to", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_EmailEvent.add(to1);
		Map<String,Object> time3 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(time3, "time", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_EmailEvent.add(time3);
		Map<String,Object> messageId = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(messageId, "messageId", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_EmailEvent.add(messageId);
		Map<String,Object> type3 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(type3,DataType.EVENT_PARENT_TYPE, DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_EmailEvent.add(type3);
		vertex.put("EmailEvent", vertex_EmailEvent);
		//StayEvent
		List<Map<String,Object>> vertex_StayEvent = new ArrayList<Map<String,Object>>();
		Map<String,Object> hotelid = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(hotelid, "hotelid", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_StayEvent.add(hotelid);
		Map<String,Object> hotelname = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(hotelname, "hotelname", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_StayEvent.add(hotelname);
		Map<String,Object> orderno = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(orderno, "orderno", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_StayEvent.add(orderno);
		Map<String,Object> arrivedate = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(arrivedate, "arrivedate", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_StayEvent.add(arrivedate);
		Map<String,Object> departuredate = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(departuredate, "departuredate", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_StayEvent.add(departuredate);
		Map<String,Object> roomno = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(roomno, "roomno", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_StayEvent.add(roomno);
		Map<String,Object> hotelflag = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(hotelflag, "hotelflag", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsTrue());
		vertex_StayEvent.add(hotelflag);
		Map<String,Object> type4 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(type4,DataType.EVENT_PARENT_TYPE, DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		vertex_StayEvent.add(type4);
		vertex.put("StayEvent", vertex_StayEvent);
		//own
		List<Map<String,Object>> edge_own = new ArrayList<Map<String,Object>>();
		Map<String,Object> own = new HashMap<String,Object>();
		/*TitanStructureUtil.toSetMapValue(own, "own", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsTrue(),DataTypeTitan.INDEX.getIsFalse());*/
		edge_own.add(own);
		edge.put("own",edge_own);
		//callfrom
		List<Map<String,Object>> edge_callfrom = new ArrayList<Map<String,Object>>();
		Map<String,Object> eventtime = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(eventtime, "eventtime", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_callfrom.add(eventtime);
		edge.put("callfrom",edge_callfrom);
		//callto
		List<Map<String,Object>> edge_callto = new ArrayList<Map<String,Object>>();
		Map<String,Object> eventtime1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(eventtime1, "eventtime", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_callto.add(eventtime1);
		edge.put("callto",edge_callto);
		//login
		List<Map<String,Object>> edge_login = new ArrayList<Map<String,Object>>();
		Map<String,Object> eventtime2 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(eventtime2, "eventtime", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_login.add(eventtime2);
		edge.put("login", edge_login);
		//emailfrom
		List<Map<String,Object>> edge_emailfrom = new ArrayList<Map<String,Object>>();
		Map<String,Object> eventtime3 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(eventtime3, "eventtime", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_emailfrom.add(eventtime3);
		edge.put("emailfrom", edge_emailfrom);
		//emailto
		List<Map<String,Object>> edge_emailto = new ArrayList<Map<String,Object>>();
		Map<String,Object> eventtime4 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(eventtime4, "eventtime", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_emailto.add(eventtime4);
		edge.put("emailto", edge_emailto);
		//emailcc
		List<Map<String,Object>> edge_emailcc = new ArrayList<Map<String,Object>>();
		Map<String,Object> eventtime5 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(eventtime5, "eventtime", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_emailcc.add(eventtime5);
		edge.put("emailcc", edge_emailcc);
		//relational
		List<Map<String,Object>> edge_relational = new ArrayList<Map<String,Object>>();
		Map<String,Object> relationtype = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(relationtype, "relationtype", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_relational.add(relationtype);
		Map<String,Object> nickname1 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(nickname1, "nickname", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_relational.add(nickname1);
		edge.put("relational", edge_relational);
		//stay
		List<Map<String,Object>> edge_stay = new ArrayList<Map<String,Object>>();
		Map<String,Object> eventtime6 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(eventtime6, "eventtime", DataTypeTitan.LONG.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_stay.add(eventtime6);
		edge.put("stay", edge_stay);
		//cohabit
		List<Map<String,Object>> edge_cohabit = new ArrayList<Map<String,Object>>();
		Map<String,Object> cohabit = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(cohabit, "cohabit", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_cohabit.add(cohabit);
		edge.put("cohabit", edge_cohabit);
		//group
		List<Map<String,Object>> edge_group = new ArrayList<Map<String,Object>>();
		Map<String,Object> grouptype = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(grouptype, "grouptype", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsTrue(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_group.add(grouptype);
		Map<String,Object> nickname2 = new HashMap<String,Object>();
		TitanStructureUtil.toSetMapValue(nickname2, "nickname", DataTypeTitan.STRING.getValue(), DataTypeTitan.STRONG.getIsFalse(),DataTypeTitan.INDEX.getIsFalse(),DataTypeTitan.CARDINALITY.getSingle(),DataTypeTitan.QUERYABLE.getIsFalse());
		edge_group.add(nickname2);
		edge.put("group",edge_group);
		try {
			File f = new File(savePathStru);
			if(!f.exists()){
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(list);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			String json = JacksonMapperUtil.getObjectMapper().writeValueAsString(list);
			list = JacksonMapperUtil.getObjectMapper().readValue(json, list.getClass());
		}  catch (Exception e) {
			e.printStackTrace();
		}*/
		/*try{
			String json = JacksonMapperUtil.getObjectMapper().writeValueAsString(vertex_Person);
			System.out.println(json);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		return list;
	}
	
	public static void toSetMapValue(Map<String,Object> map,String name,Object type,String strong,String index,String cardinality,String queryable) {
		map.put("name", name);//名称
		map.put("type", type);//类型
		map.put("strong",strong);//是否是强属性(1:是,0:否)
		map.put("index", index);//titan索引(1:是,0:否)
		map.put("cardinality", cardinality);//基数(三个值single,set,list)
		map.put("queryable", queryable);//是否可查询
	}
	
	/**
	 * 获取titan结构
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> getTitanStructure(){
		File f = new File(savePathStru);
		if(!f.exists()){
			createTitanStructure();
		}
		ObjectInputStream ois = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			ois = new ObjectInputStream(new FileInputStream(f));
			list = (List<Map<String,Object>>)ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 添加titan节点，传递参数为fullJson（整个json串）
	 * @param fullJson
	 * @param result
	 * @return boolean
	 */
	public static boolean updateTitanVertexFull(String fullJson) {
		File f = new File(savePathStru);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			if(f.exists()){
				f.delete();
				f.createNewFile();
			}
			list = JacksonMapperUtil.getObjectMapper().readValue(fullJson, list.getClass());
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(list);
			oos.close();
			
			try {
				ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
				//更新titan结构缓存
				sc.setAttribute("titanStr", JacksonMapperUtil.getObjectMapper().writeValueAsString(list));
				Map<String,List<String>> qMap = new LinkedHashMap<String,List<String>>();
				Map<String, Object> vertexs = list.get(0);
				for(String key : vertexs.keySet()){
					List<Map<String,Object>> l = (List<Map<String,Object>>)vertexs.get(key);
					List<String> qList = new ArrayList<String>();
					for(int i=0;i<l.size();i++){
						Map<String, Object> map = l.get(i);
						String queryable = (String)map.get("queryable");
						if("1".equals(queryable)){
							qList.add((String)map.get("name"));
						}
					}
					qMap.put(key, qList);
				}
				//更新可查询属性缓存
				sc.setAttribute("queryableMap", JacksonMapperUtil.getObjectMapper().writeValueAsString(qMap));
			} catch (JsonProcessingException e) {
				logger.error("修改页面显示配置异常", e);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 添加vertex节点
	 * @param vertexName
	 * @param vertexJson
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> addTitanVertex(String vertexName,String vertexJson){
		List<Map<String,Object>> l = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			//先把前台传回来的json转换为list节点
			list = JacksonMapperUtil.getObjectMapper().readValue(vertexJson, list.getClass());
			File f = new File(savePathStru);
			if(!f.exists()){
				f.createNewFile();
			}
			if(f.length()>0){
				//文件原来就存在，把文件反序列化为l,然后把添加的节点加到l里
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				l = (List<Map<String,Object>>)ois.readObject();
				Map<String, Object> map = l.get(0);
				map.put(vertexName, list);
				ois.close();
			}else{
				//文件是新建的，new一个l，把添加的节点放到l里
				l = new ArrayList<Map<String,Object>>();
				Map<String, Object> map = new HashMap<String,Object>();
				map.put(vertexName, list);
				l.add(map);
			}
			//把新的集合l序列化放到config下
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(l);
			oos.close();
			ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			//更新titan结构缓存
			List<Map<String, Object>> titanStructure = TitanStructureUtil.getTitanStructure();
			sc.setAttribute("titanStr", JacksonMapperUtil.getObjectMapper().writeValueAsString(titanStructure));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	/**
	 * 修改vertex节点（其实跟添加一样）
	 * @param vertexName
	 * @param vertexJson
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> updateTitanVertex(String vertexName,String vertexJson){
		List<Map<String,Object>> l = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try{
			list = JacksonMapperUtil.getObjectMapper().readValue(vertexJson, list.getClass());
			File f = new File(savePathStru);
			if(!f.exists()){
				f.createNewFile();
			}
			if(f.length()>0){
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				l = (List<Map<String,Object>>)ois.readObject();
				Map<String,Object> map = l.get(0);
				map.put(vertexName, list);
				ois.close();
			}else{
				l = new ArrayList<Map<String,Object>>();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put(vertexName, list);
				l.add(map);
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(l);
			oos.close();
			ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			//更新titan结构缓存
			List<Map<String, Object>> titanStructure = TitanStructureUtil.getTitanStructure();
			sc.setAttribute("titanStr", JacksonMapperUtil.getObjectMapper().writeValueAsString(titanStructure));
		}catch(Exception e){
			e.printStackTrace();
		}
		return l;
	}
	/**
	 * 删除titan节点
	 * @param vertexName
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> deleteTitanVertex(String vertexName){
		List<Map<String,Object>> l = null;
		try{
			File f = new File(savePathStru);
			if(!f.exists()){
				f.createNewFile();
			}
			if(f.length()>0){
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				l = (List<Map<String,Object>>)ois.readObject();
				Map<String,Object> map = l.get(0);
				List<Map<String, Object>> list = (List<Map<String, Object>>)map.get(vertexName);
				if(list!=null){
					map.remove(vertexName);
				}
				ois.close();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(l);
			oos.close();
			ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			//更新titan结构缓存
			List<Map<String, Object>> titanStructure = TitanStructureUtil.getTitanStructure();
			sc.setAttribute("titanStr", JacksonMapperUtil.getObjectMapper().writeValueAsString(titanStructure));
		}catch(Exception e){
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * 添加边
	 * @param edgeName
	 * @param edgeJson
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> addTitanEdge(String edgeName,String edgeJson){
		List<Map<String,Object>> l = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			//先把前台传回来的json转换为list节点
			list = JacksonMapperUtil.getObjectMapper().readValue(edgeJson, list.getClass());
			File f = new File(savePathStru);
			if(!f.exists()){
				f.createNewFile();
			}
			if(f.length()>0){
				//文件原来就存在，把文件反序列化为l,然后把添加的节点加到l里
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				l = (List<Map<String,Object>>)ois.readObject();
				Map<String, Object> map = l.get(1);
				map.put(edgeName, list);
				ois.close();
			}else{
				//文件是新建的，new一个l，把添加的节点放到l里
				l = new ArrayList<Map<String,Object>>();
				Map<String, Object> map = new HashMap<String,Object>();
				map.put(edgeName, list);
				l.add(map);
			}
			//把新的集合l序列化放到config下
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(l);
			oos.close();
			ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			//更新titan结构缓存
			List<Map<String, Object>> titanStructure = TitanStructureUtil.getTitanStructure();
			sc.setAttribute("titanStr", JacksonMapperUtil.getObjectMapper().writeValueAsString(titanStructure));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * 修改边（其实跟添加一样）
	 * @param vertexName
	 * @param vertexJson
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> updateTitanEdge(String edgeName,String vertexJson){
		List<Map<String, Object>> l = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try{
			list = JacksonMapperUtil.getObjectMapper().readValue(vertexJson, list.getClass());
			File f = new File(savePathStru);
			if(!f.exists()){
				f.createNewFile();
			}
			if(f.length()>0){
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				l = (List<Map<String, Object>>)ois.readObject();
				Map<String,Object> map = l.get(1);
				map.put(edgeName, list);
				ois.close();
			}else{
				l = new ArrayList<Map<String,Object>>();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put(edgeName, list);
				l.add(map);
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(l);
			oos.close();
			ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			//更新titan结构缓存
			List<Map<String, Object>> titanStructure = TitanStructureUtil.getTitanStructure();
			sc.setAttribute("titanStr", JacksonMapperUtil.getObjectMapper().writeValueAsString(titanStructure));
		}catch(Exception e){
			e.printStackTrace();
		}
		return l;
	}
	/**
	 * 删除titan边
	 * @param vertexName
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> deleteTitanEdge(String edgeName){
		List<Map<String,Object>> l = null;
		try{
			File f = new File(savePathStru);
			if(!f.exists()){
				f.createNewFile();
			}
			if(f.length()>0){
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				l = (List<Map<String,Object>>)ois.readObject();
				Map<String, Object> map = l.get(1);
				List<Map<String, Object>> list = (List<Map<String, Object>>)map.get(edgeName);
				if(list!=null){
					map.remove(edgeName);
				}
				ois.close();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(l);
			oos.close();
			ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			//更新titan结构缓存
			List<Map<String, Object>> titanStructure = TitanStructureUtil.getTitanStructure();
			sc.setAttribute("titanStr", JacksonMapperUtil.getObjectMapper().writeValueAsString(titanStructure));
		}catch(Exception e){
			e.printStackTrace();
		}
		return l;
	}
	
	public static void main(String[] args) {
		getTitanStructure();
	}

}

enum DataTypeTitan{
	LONG("long", "java.lang.Long"),
	STRING("string","java.lang.String"),
	CHARACTER("character","java.lang.Character"),
	BOOLEAN("boolean","java.lang.Boolean"),
	BYTE("byte","java.lang.Byte"),
	SHORT("short","java.lang.Short"),
	INTEGER("integer","java.lang.Integer"),
	FLOAT("float","java.lang.Float"),
	ARRAY("array","java.lang.String[]"),
	
	STRONG("1","0"),//true false
	INDEX("1","0"),//true false
	CARDINALITY("1","2","3"),//single,set,list
	QUERYABLE("1","0");//true false
	
	private String name;
	private String value;
	private String isTrue;
	private String isFalse;
	
	private String single;
	private String set;
	private String list;
	
	private DataTypeTitan(String single,String set,String list){
		this.single = single;
		this.set = set;
		this.list = list;
	}

	private DataTypeTitan(String name, String value) {
		this.name = name;
		this.value = value;
		this.isTrue = name;
		this.isFalse = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(String isTrue) {
		this.isTrue = isTrue;
	}

	public String getIsFalse() {
		return isFalse;
	}

	public void setIsFalse(String isFalse) {
		this.isFalse = isFalse;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}
	
}
