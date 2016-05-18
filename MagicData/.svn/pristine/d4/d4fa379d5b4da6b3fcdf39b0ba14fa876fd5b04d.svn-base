package com.integrity.lawCase.relation.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.relation.bean.AllRelation;
import com.integrity.lawCase.relation.dao.AllRelationDao;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;

/**
 * 所有关联关系Service
 * 
 * @author HanXue
 * 
 */
public class AllRelationService {

	private AllRelationDao arDao;

	/** 关联页面当前对象列表表格标题配置文件 Key取自ConstantManage内的类型常量 */
	public final static String RELATION_PAGE_CONFILE = "relationPageConf.properties";
	public final static String RELATION_TYPE_LABELKEY = ".label";
	public final static String RELATION_TYPE_CHOSEKEY = ".chosefields";
	public final static String RELATION_TYPE_SUBLABELKEY = "subLabel";
	public final static String RELATION_TYPE_SUBCHOSEKEY = ".subchosefields";

	/**
	 * 根据当前节点的id及类型获得所有关联关系
	 * 
	 * @param rootId
	 *            当前节点ID
	 * @param rootType
	 *            当前节点的类型，类型取自ConstantManage内的类型常量
	 * @return AllRelationWapper包含节点的所有关联信息
	 */
	@SuppressWarnings("unchecked")
	public AllRelationWapper getAllRelationForType(String rootId, String rootType) {
		AllRelation ar = arDao.getAllRelationByType(rootId, rootType);
		if (null != ar) {
			AllRelationWapper arWapper = new AllRelationWapper();
			arWapper.setArObj(ar);
			String caseIds = ar.getCaseIds();
			if (null != caseIds && !"".equals(caseIds)) {
				List<?> tempCList = arDao.getRelationObjs(
						CaseObject.class.getSimpleName(), caseIds);
				if (null != tempCList) {
					List<CaseObject> caseList = (List<CaseObject>) tempCList;
					arWapper.setCaseList(caseList);
				}
			}
			String fileIds = ar.getFileIds();
			if (null != fileIds && !"".equals(fileIds)) {
				List<?> tempFList = arDao.getRelationObjs(
						FilesObject.class.getSimpleName(), fileIds);
				if (null != tempFList) {
					List<FilesObject> fileList = (List<FilesObject>) tempFList;
					arWapper.setFileList(fileList);
				}
			}
			String hostIds = ar.getHostIds();
			if (null != hostIds && !"".equals(hostIds)) {
				List<?> tempHList = arDao.getRelationObjs(
						HostsObject.class.getSimpleName(), hostIds);
				if (null != tempHList) {
					List<HostsObject> hostList = (List<HostsObject>) tempHList;
					arWapper.setHostList(hostList);
				}
			}
			String orgIds = ar.getOrganizationIds();
			if (null != orgIds && !"".equals(orgIds)) {
				List<?> tempOList = arDao.getRelationObjs(
						Organizationobject.class.getSimpleName(), orgIds);
				if (null != tempOList) {
					List<Organizationobject> orgList = (List<Organizationobject>) tempOList;
					arWapper.setOrganList(orgList);
				}
			}
			String peopleIds = ar.getPeopleIds();
			if (null != peopleIds && !"".equals(peopleIds)) {
				List<?> tempPList = arDao.getRelationObjs(
						Peopleobject.class.getSimpleName(), peopleIds);
				if (null != tempPList) {
					List<Peopleobject> peopleList = (List<Peopleobject>) tempPList;
					arWapper.setPeopleList(peopleList);
				}
			}
			return arWapper;
		}
		return null;
	}

	/**
	 * 判断当前节点与某节点的关联关系是否存在
	 * 
	 * @param rootId
	 *            当前节点ID
	 * @param rootType
	 *            当前节点的类型，类型取自ConstantManage内的类型常量
	 * @param relationId
	 *            关联节点的ID
	 * @param relationType
	 *            关联节点的类型，类型取自ConstantManage内的类型常量
	 * @return 不存在返回false 存在返回true
	 */
	public boolean getRelationExist(String rootId, String rootType,
			Long relationId, String relationType) {
		AllRelation ar = arDao.getAllRelationByType(rootId, rootType);
		if (null != ar) {
			if (ConstantManage.CASEOBJECTTYPE.equals(relationType)) {
				return subGetRelationExist(ar.getCaseIds(), relationId);
			} else if (ConstantManage.FILEOBJECTTYPE.equals(relationType)) {
				return subGetRelationExist(ar.getFileIds(), relationId);
			} else if (ConstantManage.HOSTOBJECTTYPE.equals(relationType)) {
				return subGetRelationExist(ar.getHostIds(), relationId);
			} else if (ConstantManage.ORGANIZATIONOBJECTTYPE
					.equals(relationType)) {
				return subGetRelationExist(ar.getOrganizationIds(), relationId);
			} else if (ConstantManage.PEOPLEOBJECTTYPE.equals(relationType)) {
				return subGetRelationExist(ar.getPeopleIds(), relationId);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 新增或修改某对象的单一关联关系，去重
	 * 
	 * @param rootId
	 *            当前节点ID
	 * @param rootType
	 *            当前节点的类型，类型取自ConstantManage内的类型常量
	 * @param relationId
	 *            关联节点的ID
	 * @param relationType
	 *            关联节点的类型，类型取自ConstantManage内的类型常量
	 */
	public void addOrUpdateRelation(String rootId, String rootType,
			Long relationId, String relationType) {
		Map<String, String> sourceRelationMap = new HashMap<String, String>();
		sourceRelationMap.put(relationType, relationId.toString());
		addOrUpdateRelation(rootId, rootType, sourceRelationMap);
		boolean exsitFlag = getRelationExist(relationId.toString(), relationType, Long.parseLong(rootId), rootType);
		if(!exsitFlag){
			addOrUpdateRelation(relationId.toString(), relationType, Long.parseLong(rootId),relationType);
		}
	}

	/**
	 * 新增或修改某对象的多个关联关系，去重
	 * 
	 * @param rootId
	 *            当前节点ID
	 * @param rootType
	 *            当前节点的类型，类型取自ConstantManage内的类型常量
	 * @param relationMap
	 *            类型为Map<String,String>Type-Ids键值对
	 *            Key为关联节点的类型，类型取自ConstantManage内的类型常量
	 *            Value为该类型下关联的id字符串，各个id以逗号分隔
	 */
	public void addOrUpdateRelation(String rootId, String rootType,
			Map<String, String> relationMap) {
		AllRelation ar = arDao.getAllRelationByType(rootId, rootType);
		if (null != ar) {
			ar = getModifyRelation(ar, relationMap);
			arDao.updateAllRel(ar);
		} else {
			ar = new AllRelation();
			ar.setType(rootType);
			ar.setTypeId(Long.parseLong(rootId));
			ar = getModifyRelation(ar, relationMap);
			arDao.saveAllRel(ar);
		}
		//添加关联
		if(null!=relationMap&&relationMap.size()>0){
			for(Entry<String,String> tempEntry:relationMap.entrySet()){
				String typeKey=  tempEntry.getKey();
				String relationIds = tempEntry.getValue();
				if(null!=relationIds&&!"".equals(relationIds.trim())){
					String[] rIdsArr = relationIds.split(",");
					if(null!=rIdsArr&&rIdsArr.length>0){
						for(String rid:rIdsArr){
							if(null!=rid&&!"".equals(rid)){
								boolean exsitFlag = getRelationExist(rid, typeKey, Long.parseLong(rootId), rootType);
								if(!exsitFlag){
								       addOrUpdateRelation(rid, typeKey, Long.parseLong(rootId), rootType);
								}
							}
						}
		}}}}
	}

	/**
	 * 删除某对象的单一关联关系
	 * 
	 * @param rootId
	 *            当前节点ID
	 * @param rootType
	 *            当前节点的类型，类型取自ConstantManage内的类型常量
	 * @param relationId
	 *            关联节点的ID
	 * @param relationType
	 *            关联节点的类型，类型取自ConstantManage内的类型常量
	 */
	public void delRelation(String rootId, String rootType, String relationId,
			String relationType) {
		Map<String, String> souceRelationMap = new HashMap<String, String>();
		souceRelationMap.put(relationType, relationId);
		delRelation(rootId, rootType, souceRelationMap);
		boolean exsitFlag = getRelationExist(relationId, relationType, Long.parseLong(rootId), rootType);
		if(exsitFlag){
			delRelation(relationId, relationType,rootId, rootType);
		}
	}

	/**
	 * 删除某对象的多个关联关系
	 * 
	 * @param rootId 当前节点ID
	 * @param rootType 当前节点的类型，类型取自ConstantManage内的类型常量
	 * @param relationMap
	 *            类型为Map<String,String>Type-Ids键值对
	 *            Key为关联节点的类型，类型取自ConstantManage内的类型常量
	 *            Value为该类型下关联的id字符串，各个id以逗号分隔
	 */
	public void delRelation(String rootId, String rootType,
			Map<String, String> relationMap) {
		AllRelation ar = arDao.getAllRelationByType(rootId, rootType);
		if (null != ar) {
			ar = getDelRelation(ar, relationMap);
			if ("".equals(ar.getCaseIds()) && "".equals(ar.getFileIds())
					&& "".equals(ar.getHostIds())
					&& "".equals(ar.getOrganizationIds())
					&& "".equals(ar.getPeopleIds())) {
				arDao.delAllRel(ar);
			} else {
				arDao.updateAllRel(ar);
			}
			//删除关联
			if(null!=relationMap&&relationMap.size()>0){
				for(Entry<String,String> tempEntry:relationMap.entrySet()){
					String typeKey=  tempEntry.getKey();
					String relationIds = tempEntry.getValue();
					if(null!=relationIds&&!"".equals(relationIds.trim())){
						String[] rIdsArr = relationIds.split(",");
						if(null!=rIdsArr&&rIdsArr.length>0){
							for(String rid:rIdsArr){
								if(null!=rid&&!"".equals(rid)){
									boolean exsitFlag = getRelationExist(rid, typeKey, Long.parseLong(rootId), rootType);
									if(exsitFlag){
										delRelation(rid, typeKey, rootId, rootType);
									}
								}
							}
			}}}}
		}
	}
	
	/**
	 * 删除某对象的所有关联关系
	 * @param rootId 当前节点ID
	 * @param rootType 当前节点的类型，类型取自ConstantManage内的类型常量
	 */
	public void delAllRelationById(String rootId, String rootType){
		AllRelation ar = arDao.getAllRelationByType(rootId, rootType);
		if (null != ar) {
			arDao.delAllRel(ar);
			delAllRelationSub(ar.getCaseIds(),ConstantManage.CASEOBJECTTYPE, rootId, rootType);
			delAllRelationSub(ar.getHostIds(),ConstantManage.HOSTOBJECTTYPE, rootId, rootType);
			delAllRelationSub(ar.getFileIds(),ConstantManage.FILEOBJECTTYPE, rootId, rootType);
			delAllRelationSub(ar.getOrganizationIds(),ConstantManage.ORGANIZATIONOBJECTTYPE, rootId, rootType);
			delAllRelationSub(ar.getPeopleIds(),ConstantManage.PEOPLEOBJECTTYPE, rootId, rootType);
		}
	}
	
	/**
	 * 删除某对象的所有关联关系
	 * @param rootId 节点ids集合 用逗号分隔
	 * @param rootType 当前节点集合的类型，类型取自ConstantManage内的类型常量
	 */
	public void delAllRelationByIds(String rootIds, String rootType){
		if(null!=rootIds&&!"".equals(rootIds.trim())){
			String[] rootIdsArray = rootIds.split(",");
			if(null!=rootIdsArray&&rootIdsArray.length>0){
				for(String rootId : rootIdsArray){
					if(null!=rootId&&!"".equals(rootId.trim())){ delAllRelationById(rootId,rootType); }
				}
			}
		}
	}

	/**
	 *  删除某对象的所有关联关系子方法 删除关联关系中对应的删除关系
	 * @param ids
	 * @param idsType
	 * @param rootId
	 * @param rootType
	 */
	private void delAllRelationSub(String ids,String idsType,String rootId,String rootType){
		if(null!=ids&&!"".equals(ids.trim())){
			String[] idsArr = ids.split(",");
			if(null!=idsArr&&idsArr.length>0){
				for(String id:idsArr){
					if(null!=id&&!"".equals(id)){
						delRelation(id, idsType, rootId, rootType);
					}
				}
       }}
	}
	
	/**
	 * 获得关联页面当前对象列表表格标题
	 * 
	 * @param dataPath
	 *            项目路径
	 * @param type
	 *            对象类型，类型取自ConstantManage内的类型常量
	 * @return 标题List Field顺序List
	 */
	public List<List<String>> getRelationTdLabAndInfo(String dataPath,
			String type) {
		List<List<String>> resultList = new LinkedList<List<String>>();
		// 关联节点表头(不同类型对象不相同)
		List<String> labelList = new LinkedList<String>();
		// 关联节点表头对应的Field(不同类型对象不相同)
		List<String> infoList = new LinkedList<String>();
		// 关联对象相关的子节点表头(统一)
		List<String> subLabelList = new LinkedList<String>();

		Properties relPageProperties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(dataPath + File.separator
					+ AllRelationService.RELATION_PAGE_CONFILE);
			relPageProperties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null != relPageProperties && relPageProperties.size() > 0) {
			String tdLabel = relPageProperties.getProperty(type
					+ AllRelationService.RELATION_TYPE_LABELKEY);
			if (null != tdLabel && !"".equals(tdLabel)) {
				String[] tdLabelArray = tdLabel.split(",");
				for (String temp : tdLabelArray) {
					labelList.add(temp);
				}
			}
			String choseFields = relPageProperties.getProperty(type
					+ AllRelationService.RELATION_TYPE_CHOSEKEY);
			if (null != choseFields && !"".equals(choseFields)) {
				String[] choseFieldsArray = choseFields.split(",");
				for (String temp : choseFieldsArray) {
					infoList.add(temp);
				}
			}
			String subLabel = relPageProperties
					.getProperty(AllRelationService.RELATION_TYPE_SUBLABELKEY);
			if (null != subLabel && !"".equals(subLabel)) {
				String[] subLabelArray = subLabel.split(",");
				for (String temp : subLabelArray) {
					subLabelList.add(temp);
				}
			}
		}
		resultList.add(labelList);
		resultList.add(infoList);
		resultList.add(subLabelList);
		return resultList;
	}

	/**
	 *  获得关联页面下部列表标题对应的Field所组成的数据集合
	 * @param dataPath 项目路径
	 * @param arWapper
	 * @return List<List<String>> 每个List<String>由一个对象中数据组成 数据顺序同配置文件中配置的Field相同
	 */
	public List<List<String>> getRelationSubTdInfo(String dataPath,
			AllRelationWapper arWapper) {
		List<List<String>> resultList = new LinkedList<List<String>>();	
		// 关联对象相关的子节点表头(统一)
		List<String> subLabelList = new LinkedList<String>();

		Properties relPageProperties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(dataPath + File.separator
					+ AllRelationService.RELATION_PAGE_CONFILE);
			relPageProperties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null != relPageProperties && relPageProperties.size() > 0) {
			String subLabel = relPageProperties
					.getProperty(AllRelationService.RELATION_TYPE_SUBLABELKEY);
			if (null != subLabel && !"".equals(subLabel)) {
				String[] subLabelArray = subLabel.split(",");
				for (String temp : subLabelArray) {
					subLabelList.add(temp);
				}
			}

			//获得不同对象对应的Field
			List<String> subCaseInfoList = getSubTypeInfo(relPageProperties,ConstantManage.CASEOBJECTTYPE);
			List<String> subFileInfoList = getSubTypeInfo(relPageProperties,ConstantManage.FILEOBJECTTYPE);
			List<String> subHostInfoList = getSubTypeInfo(relPageProperties,ConstantManage.HOSTOBJECTTYPE);
			List<String> subOrgInfoList = getSubTypeInfo(relPageProperties,ConstantManage.ORGANIZATIONOBJECTTYPE);
			List<String> subPeoInfoList = getSubTypeInfo(relPageProperties,ConstantManage.PEOPLEOBJECTTYPE);
			
			if(null!=arWapper){
				this.filledSubTypeInfo(resultList, subCaseInfoList, arWapper.getCaseList());
				this.filledSubTypeInfo(resultList, subFileInfoList, arWapper.getFileList());
				this.filledSubTypeInfo(resultList, subHostInfoList, arWapper.getHostList());
				this.filledSubTypeInfo(resultList, subOrgInfoList, arWapper.getOrganList());
				this.filledSubTypeInfo(resultList, subPeoInfoList, arWapper.getPeopleList());			
			}
		}
		return resultList;
	}
	
	/**
	 * 获得不同类型的子field对应列表
	 * @param relPageProperties
	 * @param type
	 * @param subInfoList
	 */
	private List<String> getSubTypeInfo(Properties relPageProperties,String type){
		List<String> subInfoList = new LinkedList<String>();
		String subInfo = relPageProperties
				.getProperty(type+AllRelationService.RELATION_TYPE_SUBCHOSEKEY);
		if (null != subInfo && !"".equals(subInfo)) {
			String[] subInfoArray = subInfo.split(",");
			for (String temp : subInfoArray) {
				subInfoList.add(temp);
			}
		}
		return subInfoList;
	}
	
	/**
	 * 填充子关联数据
	 * @param resultList
	 * @param subInfoList
	 * @param objList
	 */
	private void filledSubTypeInfo(List<List<String>> resultList,List<String> subInfoList,List<?> objList){
		if(null!=objList){
			for(Object obj:objList){
				List<String> tempList = new LinkedList<String>();
				//添加ID在0位置序列处，用于添加关联start
				Class<?> objClass = obj.getClass();
				String className = objClass.getSimpleName();
				String idStr = "";
				try {
					Field idField = obj.getClass().getDeclaredField("id");
					if(null!=idField){
						idField.setAccessible(true);
						Object idValue = idField.get(obj);
						idStr = (idValue.toString());
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				if(null!=idStr&&!"".equals(idStr)){
					tempList.add(className+"-"+idStr);
				}else{
					tempList.add("");
				}
				//添加ID在0位置序列处，用于添加关联end
				for(String fieldInfo:subInfoList){
					if(null!=fieldInfo&&!"".equals(fieldInfo)){
						if("TYPE".equals(fieldInfo.trim())){
							if(obj.getClass().getSimpleName().equals(CaseObject.class.getSimpleName())){
								tempList.add("案件");
							}else if(obj.getClass().getSimpleName().equals(FilesObject.class.getSimpleName())){
								tempList.add("文件");
							}else if(obj.getClass().getSimpleName().equals(HostsObject.class.getSimpleName())){
								tempList.add("主机");
							}else if(obj.getClass().getSimpleName().equals(Organizationobject.class.getSimpleName())){
								tempList.add("组织");
							}else if(obj.getClass().getSimpleName().equals(Peopleobject.class.getSimpleName())){
								tempList.add("人员");
							}else{
								tempList.add("");
							}
						}else{
							try {
								Field tempField = obj.getClass().getDeclaredField(fieldInfo);
								if(null!=tempField){
									tempField.setAccessible(true);
									Object fieldValue = tempField.get(obj);
									if(null==fieldValue){
										tempList.add("");
									}else{
										tempList.add(fieldValue.toString());
									}
								}else{
									tempList.add("");
								}
							}catch (Exception e) {
								tempList.add("");
								e.printStackTrace();
							}
						}
					}else{
						tempList.add("");
					}
				}
				resultList.add(tempList);
			}
		}
	}

	/**
	 * 判断关联节点是否存在子方法
	 * 
	 * @param ids
	 *            某类型下所有关联节点的集合
	 * @param relationId
	 *            需要判断的关联节点ID
	 * @return 不存在返回false 存在返回true
	 */
	private boolean subGetRelationExist(String ids, Long relationId) {
		if (null == ids || "".equals(ids.trim())) {
			return false;
		} else {
			ids = ids.replaceAll(" ", "");
			boolean exsitsFlag = false;
			String[] idsArray = ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				if (null != idsArray[i] && !"".equals(idsArray[i].trim())) {
					idsArray[i] = idsArray[i].trim();
					if (relationId.toString().equals(idsArray[i])) {
						exsitsFlag = true;
						break;
					}
				}
			}
			return exsitsFlag;
		}
	}

	/**
	 * 根据关系节点Map更新当前关系对象，去掉重复关系节点
	 * 
	 * @param ar
	 *            当前关系对象
	 * @param relationMap
	 *            类型为Map<String,String>Type-Ids键值对
	 *            Key为关联节点的类型，类型取自ConstantManage内的类型常量
	 *            Value为该类型下关联的id字符串，各个id以逗号分隔
	 * @return
	 */
	private AllRelation getModifyRelation(AllRelation ar,
			Map<String, String> relationMap) {
		if (null != ar && null != relationMap) {
			for (Entry<String, String> entry : relationMap.entrySet()) {
				String relationType = entry.getKey();
				String relationIds = entry.getValue();
				if (null != relationType && !"".equals(relationType.trim())
						&& null != relationIds
						&& !"".equals(relationIds.trim())) {
					relationIds = relationIds.trim();
					String[] relationIdArray = relationIds.split(",");
					if (null != relationIdArray && relationIdArray.length > 0) {
						for (String relationId : relationIdArray) {
							if (null != relationId && !"".equals(relationId)) {
								if (ConstantManage.CASEOBJECTTYPE
										.equals(relationType)) {
									String caseIds = ar.getCaseIds();
									ar.setCaseIds(getModifyResult(caseIds,
											relationId));
								} else if (ConstantManage.FILEOBJECTTYPE
										.equals(relationType)) {
									String fileIds = ar.getFileIds();
									ar.setFileIds(getModifyResult(fileIds,
											relationId));
								} else if (ConstantManage.HOSTOBJECTTYPE
										.equals(relationType)) {
									String hostIds = ar.getHostIds();
									ar.setHostIds(getModifyResult(hostIds,
											relationId));
								} else if (ConstantManage.ORGANIZATIONOBJECTTYPE
										.equals(relationType)) {
									String orgIds = ar.getOrganizationIds();
									ar.setOrganizationIds(getModifyResult(
											orgIds, relationId));
								} else if (ConstantManage.PEOPLEOBJECTTYPE
										.equals(relationType)) {
									String peopleIds = ar.getPeopleIds();
									ar.setPeopleIds(getModifyResult(peopleIds,
											relationId));
								}
							}
						}
					}
				}
			}
		}
		return ar;
	}

	/**
	 * 获得修改指定关系节点ID后的关系节点字符串
	 * 
	 * @param ids
	 *            当前关系节点字符串
	 * @param modifyRelationId
	 *            需要修改的关系节点ID
	 * @return 修改后的关系节点字符串
	 */
	private String getModifyResult(String ids, String modifyRelationId) {
		String resultStr = "";
		if (null == ids || "".equals(ids.trim())) {
			resultStr = modifyRelationId + "";
		} else {
			ids = ids.replaceAll(" ", "");
			boolean exsitsFlag = false;
			String[] idsArray = ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				if (null != idsArray[i] && !"".equals(idsArray[i].trim())) {
					idsArray[i] = idsArray[i].trim();
					if (modifyRelationId.trim().equals(idsArray[i])) {
						exsitsFlag = true;
						break;
					}
				}
			}
			for (String id : idsArray) {
				if (null != id && !"".equals(id.trim())) {
					resultStr = resultStr + id.trim() + ",";
				}
			}
			if (!exsitsFlag) {
				resultStr = resultStr + modifyRelationId;
			} else {
				resultStr = resultStr.substring(0, resultStr.length() - 1);
			}
		}
		return resultStr;
	}

	/**
	 * 根据需要删除的关系节点Map更新当前关系对象
	 * 
	 * @param ar
	 *            当前关系对象
	 * @param relationMap
	 *            类型为Map<String,String>Type-Ids键值对
	 *            Key为关联节点的类型，类型取自ConstantManage内的类型常量
	 *            Value为该类型下关联的id字符串，各个id以逗号分隔
	 * @return
	 */
	private AllRelation getDelRelation(AllRelation ar,
			Map<String, String> relationMap) {
		if (null != ar && null != relationMap) {
			for (Entry<String, String> entry : relationMap.entrySet()) {
				String relationType = entry.getKey();
				String relationIds = entry.getValue();
				if (null != relationType && !"".equals(relationType.trim())
						&& null != relationIds
						&& !"".equals(relationIds.trim())) {
					relationIds = relationIds.trim();
					String[] relationIdArray = relationIds.split(",");
					if (null != relationIdArray && relationIdArray.length > 0) {
						for (String relationId : relationIdArray) {
							if (null != relationId && !"".equals(relationId)) {
								if (ConstantManage.CASEOBJECTTYPE
										.equals(relationType)) {
									String caseIds = ar.getCaseIds();
									String resultIds = getDeleteIdsResult(
											caseIds, relationId);
									ar.setCaseIds(resultIds);
								} else if (ConstantManage.FILEOBJECTTYPE
										.equals(relationType)) {
									String fileIds = ar.getFileIds();
									String resultIds = getDeleteIdsResult(
											fileIds, relationId);
									ar.setFileIds(resultIds);
								} else if (ConstantManage.HOSTOBJECTTYPE
										.equals(relationType)) {
									String hostIds = ar.getHostIds();
									String resultIds = getDeleteIdsResult(
											hostIds, relationId);
									ar.setHostIds(resultIds);
								} else if (ConstantManage.ORGANIZATIONOBJECTTYPE
										.equals(relationType)) {
									String orgIds = ar.getOrganizationIds();
									String resultIds = getDeleteIdsResult(
											orgIds, relationId);
									ar.setOrganizationIds(resultIds);
								} else if (ConstantManage.PEOPLEOBJECTTYPE
										.equals(relationType)) {
									String peopleIds = ar.getPeopleIds();
									String resultIds = getDeleteIdsResult(
											peopleIds, relationId);
									ar.setPeopleIds(resultIds);
								}
							}
						}
					}
				}
			}
		}
		return ar;
	}

	/**
	 * 获得删除指定关系节点ID后的关系节点字符串
	 * 
	 * @param ids
	 *            当前关系节点字符串
	 * @param delRelationId
	 *            需要删除的关系节点ID
	 * @return 删除后的关系节点字符串
	 */
	private String getDeleteIdsResult(String ids, String delRelationId) {
		if (null!= ids&&!"".equals(ids.trim())) {
			String[] idsArray = ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				if (null != idsArray[i] && !"".equals(idsArray[i].trim())) {
					idsArray[i] = idsArray[i].trim();
					if (delRelationId.trim().equals(idsArray[i])) {
						idsArray[i] = "";
					}
				} else {
					idsArray[i] = "";
				}
			}
			String tempIds = "";
			for (String id : idsArray) {
				if (!"".equals(id)) {
					tempIds = tempIds + id + ",";
				}
			}
			if((tempIds.length()-1)>0){
				return tempIds.substring(0, tempIds.length() - 1);
			}else{
				return "";
			}
		} else {
			return "";
		}
	}

	public AllRelationDao getArDao() {
		return arDao;
	}

	public void setArDao(AllRelationDao arDao) {
		this.arDao = arDao;
	}
}
