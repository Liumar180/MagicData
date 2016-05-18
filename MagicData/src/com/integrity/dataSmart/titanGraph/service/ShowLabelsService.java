package com.integrity.dataSmart.titanGraph.service;

import java.util.List;

import com.integrity.dataSmart.titanGraph.dao.SaveShowLabelsDao;

public class ShowLabelsService {

	SaveShowLabelsDao saveShowLabelsDao;

	public void getShow(String id,List<String> labels){
		saveShowLabelsDao.saveShowLabels(id,labels);
	}
	
	public SaveShowLabelsDao getSaveShowLabelsDao() {
		return saveShowLabelsDao;
	}

	public void setSaveShowLabelsDao(SaveShowLabelsDao saveShowLabelsDao) {
		this.saveShowLabelsDao = saveShowLabelsDao;
	}
	

}
