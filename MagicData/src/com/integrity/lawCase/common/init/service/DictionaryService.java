package com.integrity.lawCase.common.init.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.integrity.lawCase.common.bean.DictionaryTable;
import com.integrity.lawCase.common.init.dao.DictionaryDao;

public class DictionaryService{

	private DictionaryDao dictionaryDao;

	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
	
	/**
	 * 查询字典表
	 * @return 转换后的map
	 */
	public Map<String,Map<String,String>> getDictionaryMap(){
		List<DictionaryTable> lst = dictionaryDao.findDicByPid(0L);
		Map<String,Map<String,String>> allMap = new HashMap<String,Map<String,String>>();
		Map<String,String> stairMap = new LinkedHashMap<String,String>();
		allMap.put("stair_dictionary", stairMap);
		for (DictionaryTable dic : lst) {
			String code = dic.getCode();
			String name = dic.getName();
			stairMap.put(code, name);
			Long id = dic.getId();
			List<DictionaryTable> secondList = dictionaryDao.findDicByPid(id);
			Map<String,String> secondMap = new LinkedHashMap<String,String>();
			for (DictionaryTable dictionaryTable : secondList) {
				String code2 = dictionaryTable.getCode();
				String name2 = dictionaryTable.getName();
				secondMap.put(code2, name2);
			}
			allMap.put(code, secondMap);
		}
		return allMap;
	}
}
