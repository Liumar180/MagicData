package com.integrity.dataSmart.titanGraph.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.integrity.dataSmart.titanGraph.bean.DataStatistics;
import com.integrity.dataSmart.titanGraph.dao.DataShowDao;

public class DataShowServiceImp implements DataShowService {
	private DataShowDao dataShowDao;
	@Override
	public List<DataStatistics> searchDatas(String dataType,String labelName,String area) {
		// TODO Auto-generated method stub
		return dataShowDao.searchDatas(dataType, labelName, area);
	}

	@Override
	public BigDecimal statisticsAcountsByFileds(String dataType,
			String labelName, String area) {
		// TODO Auto-generated method stub
		return dataShowDao.statisticsAcountsByFileds(dataType, labelName, area);
	}
	@Override
	public void saveDatas(DataStatistics datas) {
		dataShowDao.saveDatas(datas);
	}

	public DataShowDao getDataShowDao() {
		return dataShowDao;
	}
	
	public void setDataShowDao(DataShowDao dataShowDao) {
		this.dataShowDao = dataShowDao;
	}

	

}
