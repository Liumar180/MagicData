package com.integrity.dataSmart.dataImport.service;

import java.sql.SQLException;
import java.util.List;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.dataImport.bean.ImportMapping;
import com.integrity.dataSmart.dataImport.dao.ImportMappingDao;

public class ImportMappingService {
	private ImportMappingDao importMappingDao;

	public ImportMappingDao getImportMappingDao() {
		return importMappingDao;
	}

	public void setImportMappingDao(ImportMappingDao importMappingDao) {
		this.importMappingDao = importMappingDao;
	}

	/**
	 * 获取分页数据导入映射模板
	 * @param page
	 * @param importMapping
	 * @return
	 * @throws SQLException 
	 */
	public PageModel<ImportMapping> getIMappingPageModel(PageModel<ImportMapping> pageModel, ImportMapping importMapping) throws SQLException {
		List<ImportMapping> lst = importMappingDao.getMappingList(pageModel,importMapping);
		pageModel.setTotalRecords(importMappingDao.getRowCount());
		pageModel.setTotalPage(pageModel.getTotalPages());
		pageModel.setList(lst);
		return pageModel;
	}

	public void saveImportMapping(ImportMapping importMapping) {
		importMappingDao.getHibernateTemplate().save(importMapping);
	}

	public void deleteIM(String id) {
		try{
			long idL = Long.parseLong(id);
			ImportMapping im = (ImportMapping)importMappingDao.getHibernateTemplate().get(ImportMapping.class, idL);
			importMappingDao.getHibernateTemplate().delete(im);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
}
