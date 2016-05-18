package com.integrity.dataSmart.titanGraph.service;

import java.math.BigDecimal;
import java.util.List;

import com.integrity.dataSmart.titanGraph.bean.DataStatistics;

public interface  DataShowService {
	public List<DataStatistics> searchDatas(String dataType,String labelName,String area);
	public BigDecimal statisticsAcountsByFileds(String dataType,String labelName,String area);
	public void saveDatas(DataStatistics datas);

}
