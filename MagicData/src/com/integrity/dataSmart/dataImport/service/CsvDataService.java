package com.integrity.dataSmart.dataImport.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.dataImport.bean.CsvData;
import com.integrity.dataSmart.dataImport.dao.CsvDataDao;
import com.integrity.dataSmart.dataImport.util.CsvProcessHDFS;

public class CsvDataService {
	
	private Logger logger = Logger.getLogger(CsvDataService.class);
	
	private CsvDataDao csvDataDao;
	
	public void csvSave(CsvData csvData){
		csvDataDao.save(csvData);
	} 

	public PageModel<CsvData> getCsvFilePageModel(PageModel<CsvData> pageModel,CsvData csvData) {
		List<CsvData> lst = csvDataDao.getCsvFileList(pageModel,csvData);
		pageModel.setTotalRecords(csvDataDao.getRowCount());
		pageModel.setTotalPage(pageModel.getTotalPages());
		pageModel.setList(lst);
		return pageModel;
	}
	
	public CsvData findCsvById(String id){
		CsvData csvData = csvDataDao.findCsvById(id);
		return csvData;
	}

	public CsvDataDao getCsvDataDao() {
		return csvDataDao;
	}

	public void setCsvDataDao(CsvDataDao csvDataDao) {
		this.csvDataDao = csvDataDao;
	}

	public List<CsvData> findCsvByFileName(String csvFileName) {
		List<CsvData> list = csvDataDao.findCsvByFileName(csvFileName.trim());
		return list;
	}

	public Map<String, Object> deleteCsvData(String hdfsConfigPath,CsvData csvData) {
		Map<String,Object> result = new HashMap<String,Object>();
		csvDataDao.deleteCsvData(csvData);
		try {
			CsvProcessHDFS.getInstance(hdfsConfigPath).delFile(csvData.getFileName());
			result.put("success", true);
			result.put("message", "文件删除成功！");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void updateCsvData(CsvData csvData) {
		csvDataDao.updateCsvData(csvData);
		
	}
	
}
