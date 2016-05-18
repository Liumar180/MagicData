package com.integrity.dataSmart.titanGraph.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.integrity.dataSmart.titanGraph.service.ShowLabelsService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author liuBf
 * 对显示标签的操作
 */
public class ShowLabelsAction extends ActionSupport {
	private Logger logger = Logger.getLogger(ShowLabelsAction.class);
	String Id;
	String labels;
	ShowLabelsService showLabelsService;

	public void saveShowLabels(){
		List<String> lis = new ArrayList<String>();
		if(labels != null && !labels.equals("null") && !labels.equals("")){
			String sta = labels.substring(0,labels.length()-2);
			String ls = sta.replaceAll("  ", ",");
			String[] lab = ls.split("  ");
			lis = Arrays.asList(lab);
			showLabelsService.getShow(Id,lis);
		}else{
			showLabelsService.getShow(Id,lis);
		}
	}
	public String getId() {
		return Id;
	}


	public void setId(String id) {
		Id = id;
	}

	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public ShowLabelsService getShowLabelsService() {
		return showLabelsService;
	}
	public void setShowLabelsService(ShowLabelsService showLabelsService) {
		this.showLabelsService = showLabelsService;
	}
	
}