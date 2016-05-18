package com.integrity.dataSmart.exportView.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.pojo.VertexObject;

public class ExportViewService {
	public final static String VIEW_EXPORT_PATH = "images" + File.separator
			+ "exportFile" + File.separator + "viewM" + File.separator
			+ "exview";
	public final static String PERSON_EXPORT_TEMPLANT = "images" + File.separator
			+ "exportFile" + File.separator + "viewM" + File.separator
			+ "PersonEx.doc";
	public final static String GROUP_EXPORT_TEMPLANT = "images" + File.separator
			+ "exportFile" + File.separator + "viewM" + File.separator
			+ "GroupEx.doc";
	public final static String EVENT_EXPORT_TEMPLANT = "images" + File.separator
			+ "exportFile" + File.separator + "viewM" + File.separator
			+ "EventEx.doc";
	public final static String[] ACCOUNT_TITLE = {"domain","uid","username","password","email","createtime","phonenum","regip","question"}; 
	public final static String[] EMAIL_TITLE = {"email","password"}; //{"title","content","from","to"}; 
	public final static String[] PHONE_TITLE = {"model","phonenum"}; 
	public final static String[] LOCAL_TITLE = {"address","place"};
	public final static String[] IM_TITLE = {"domain","numid","nickname"};
	public final static String[] ESTATE_TITLE = {"hotelid","hotelname","orderno","departuredate","roomno","hotelflag"}; 
	public final static String[] EEMAIL_TITLE = {"title","content","from","to","time","messageId"}; 
	public final static String[] ECALL_TITLE = {"from","to","time","long"}; 
	public final static String[] ELOGIN_TITLE = {"domain","username","ip","time"};

	private String webPath;

	/**
	 * 获得非事件替换数据
	 */
	public Map<String, String> getExportMap(VertexObject vo) {
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> dataMap = new HashMap<String, String>();
		Map<String, String> baseMap = vo.getBaseProMap();
		for (Entry<String, String> tempData : baseMap.entrySet()) {
			dataMap.put("${" + tempData.getKey().trim() + "}",
					tempData.getValue());
		}
		return dataMap;
	}

	/**
	 * 获得非事件表格填充数据
	 */
	public Map<Integer, List<String[]>> getExpUnEventTableMap(VertexObject vo) {
		Map<Integer, List<String[]>> tableDataMap = new HashMap<Integer, List<String[]>>();
		List<Map<String, String>> ownList = vo.getOwnProList();
		List<String[]> accountList = new ArrayList<String[]>();
		List<String[]> emailList = new ArrayList<String[]>();
		List<String[]> phoneList = new ArrayList<String[]>();
		List<String[]> locList = new ArrayList<String[]>();
		List<String[]> imList = new ArrayList<String[]>();
		
		for(Map<String, String> tempMap:ownList){
			String subVoType = tempMap.get("type");
			tempMap.remove("type");
			//Collection<String> valueList = tempMap.values();
			List<String> valueList = new ArrayList<String>();
			String[] tempTitle = {};
			if (DataType.ACCOUNT.equals(subVoType)) {
				tempTitle = ACCOUNT_TITLE;
			}else if (DataType.EMAIL.equals(subVoType)) {
				tempTitle = EMAIL_TITLE;
			}else if (DataType.PHONE.equals(subVoType)) {
				tempTitle = PHONE_TITLE;
			}else if (DataType.LOCATION.equals(subVoType)) {
				tempTitle = LOCAL_TITLE;
			}else if (DataType.IM.equals(subVoType)) {
				tempTitle = IM_TITLE;
			}
			for(String tStr:tempTitle){
				String tempValue = tempMap.get(tStr);
				if(null==tempValue||"".equals(tempValue.trim())){
					tempValue = "";
				}
				valueList.add(tempValue);
			}
			if(null!=valueList&&valueList.size()>0){
				String[] field = new String[valueList.size()];
				valueList.toArray(field);
				if (DataType.ACCOUNT.equals(subVoType)) {
					accountList.add(field);
				}else if (DataType.EMAIL.equals(subVoType)) {
					emailList.add(field);
				}else if (DataType.PHONE.equals(subVoType)) {
					phoneList.add(field);
				}else if (DataType.LOCATION.equals(subVoType)) {
					locList.add(field);
				}else if (DataType.IM.equals(subVoType)) {
					imList.add(field);
				}
			}
		}
		
		String voType = vo.getType();
		if (DataType.PERSON.equals(voType)) {
			tableDataMap.put(1, accountList);
			tableDataMap.put(2, emailList);
			tableDataMap.put(3, phoneList);
			tableDataMap.put(4, locList);
			tableDataMap.put(5, imList);
		}else if (DataType.GROUP.equals(voType)) {
			tableDataMap.put(1, imList);
		}
		return tableDataMap;
	}

	/*
	 * 获得事件表格填充数据
	 */
	public Map<Integer, List<String[]>> getExpEventTableData(
			List<VertexObject> voEventList) {
		Map<Integer, List<String[]>> tableDataMap = new HashMap<Integer, List<String[]>>();
		List<String[]> stayList = new ArrayList<String[]>();
		List<String[]> emailList = new ArrayList<String[]>();
		List<String[]> callList = new ArrayList<String[]>();
		List<String[]> loginList = new ArrayList<String[]>();

		for(VertexObject tempVo : voEventList){
			String voType = tempVo.getType();
			//Collection<String> valueList = tempVo.getBaseProMap().values();
			Map<String,String> tempMap = tempVo.getBaseProMap();
			List<String> valueList = new ArrayList<String>();
			String[] tempTitle = {};
			if (DataType.EVENTSTAY.equals(voType)) {
				tempTitle = ESTATE_TITLE;
			}else if (DataType.EVENTEMAIL.equals(voType)) {
				tempTitle = EEMAIL_TITLE;
			}else if (DataType.EVENTCALL.equals(voType)) {
				tempTitle = ECALL_TITLE;
			}else if (DataType.EVENTLOGIN.equals(voType)) {
				tempTitle = ELOGIN_TITLE;
			}
			for(String tStr:tempTitle){
				String tempValue = tempMap.get(tStr);
				if(null==tempValue||"".equals(tempValue.trim())){
					tempValue = "";
				}
				valueList.add(tempValue);
			}
			if(null!=valueList&&valueList.size()>0){
				String[] field = new String[valueList.size()];
				valueList.toArray(field);
				if (DataType.EVENTSTAY.equals(voType)) {
					stayList.add(field);
				}else if (DataType.EVENTEMAIL.equals(voType)) {
					emailList.add(field);
				}else if (DataType.EVENTCALL.equals(voType)) {
					callList.add(field);
				}else if (DataType.EVENTLOGIN.equals(voType)) {
					loginList.add(field);
				}
			}
		}
		tableDataMap.put(0, stayList);
		tableDataMap.put(1, emailList);
		tableDataMap.put(2, callList);
		tableDataMap.put(3, loginList);
		return tableDataMap;
	}

	/**
	 * 获得案件图片数据
	 */
	public Map<String, String> getExportPicMap(String currentTimeStr) {
		Map<String, String> dataMap = new HashMap<String, String>();
		String defaultImgPath = webPath + "images" + File.separator + "lawCase"
				+ File.separator + "moren" + File.separator + "case.jpg";
		dataMap.put("${caseImage}", defaultImgPath);
		return dataMap;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

}
