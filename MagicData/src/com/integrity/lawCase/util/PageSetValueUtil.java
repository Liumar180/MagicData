package com.integrity.lawCase.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.relation.pojo.RelationAction;
import com.integrity.system.auth.bean.AuthUser;

public class PageSetValueUtil {
	
	private static Logger logger = Logger.getLogger(PageSetValueUtil.class);
	/**
	 * 多选下拉框值初始化
	 * @param request
	 * @param userList 用户集合
	 * @param direction 方向映射map
	 */
	public static void selectMoreSet(HttpServletRequest request,List<AuthUser> userList,Map<String,String> direction){
		try {
			//多选下拉框使用
			List<String> userIds = new ArrayList<String>();
			List<String> userNames = new ArrayList<String>();
			for (AuthUser user : userList) {
				userIds.add(user.getId()+"");
				userNames.add(user.getUserName());
			}
			String userIdsJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(userIds);
			request.setAttribute("userIds", userIdsJson);
			String userNamesJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(userNames);
			request.setAttribute("userNames", userNamesJson);
			
			Set<Entry<String, String>> set = direction.entrySet();
			List<String> directionKeys = new ArrayList<String>();
			List<String> directionTexts = new ArrayList<String>();
			for (Entry<String, String> entry : set) {
				directionKeys.add(entry.getKey());
				directionTexts.add(entry.getValue());
			}
			String directionKeysJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(directionKeys);
			request.setAttribute("directionKeys", directionKeysJson);
			String directionTextsJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(directionTexts);
			request.setAttribute("directionTexts", directionTextsJson);
		} catch (JsonProcessingException e) {
			logger.error("json转换异常",e);
		}
	}
	
	/**
	 * 关联跳转连接解析为对象
	 * @param request
	 * @param map 配置文件映射
	 * @param rootId 关联对象id
	 * @param rootType 关联对象类型
	 */
	public static RelationAction relationActionSet(Map<String,String> map, String rootType, String rootId){
		RelationAction ra = new RelationAction();
		Set<Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> entry : set) {
			String key = entry.getKey();
			String[] arr = key.split("_");
			if (arr.length == 2) {
				String prefix = arr[0];
				String suffix = arr[1];
				if (ConstantManage.RELATIONACTION.equals(suffix)) {
					String value = entry.getValue()+"?rootId="+rootId+"&rootType="+rootType;
					switch (prefix) {
					case ConstantManage.CASEOBJECTTYPE:
						ra.setCase_relationAction(value);
						break;
					case ConstantManage.FILEOBJECTTYPE:
						ra.setFile_relationAction(value);
						break;
					case ConstantManage.HOSTOBJECTTYPE:
						ra.setHost_relationAction(value);
						break;
					case ConstantManage.ORGANIZATIONOBJECTTYPE:
						ra.setOrganization_relationAction(value);
						break;
					case ConstantManage.PEOPLEOBJECTTYPE:
						ra.setPeople_relationAction(value);
						break;
					default:
						break;
					}
				}else if (ConstantManage.DETAILACTION.equals(suffix)) {
					String value = entry.getValue()+rootId;
					switch (prefix) {
					case ConstantManage.CASEOBJECTTYPE:
						ra.setCase_detailAction(value);
						break;
					case ConstantManage.FILEOBJECTTYPE:
						ra.setFile_detailAction(value);
						break;
					case ConstantManage.HOSTOBJECTTYPE:
						ra.setHost_detailAction(value);
						break;
					case ConstantManage.ORGANIZATIONOBJECTTYPE:
						ra.setOrganization_detailAction(value);
						break;
					case ConstantManage.PEOPLEOBJECTTYPE:
						ra.setPeople_detailAction(value);
						break;
					default:
						break;
					}
				}
			}
		}
		return ra;
	}
}
