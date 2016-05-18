package com.integrity.dataSmart.titanGraph.action;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import com.integrity.dataSmart.titanGraph.bean.DataStatistics;
import com.integrity.dataSmart.titanGraph.service.DataShowService;
import com.opensymphony.xwork2.ActionSupport;

public class DataShowAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private DataShowService dataShowService;
	private List<DataStatistics> root;
	private BigDecimal numbers;
    private String dataType;//数据类型
    private String labelName;//数据名称、标签
    private String area;//地域
    private Logger logger = Logger.getLogger(DataShowAction.class);
	/**
	 * @return
	 * 获取数据集合
	 */
	public String searchDatas(){
		if(dataType != null){
			dataType = dataType.trim();
		}
		if(labelName != null){
			labelName = labelName.trim();
		}
		if(area != null){
			area = area.trim();
		}
		try {
			root = dataShowService.searchDatas(dataType, labelName, area);
		} catch (Exception e) {
			logger.error("数据获取异常!");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String statisticsAcountsByFileds(){
		if(dataType != null){
			dataType = dataType.trim();
		}
		if(labelName != null){
			labelName = labelName.trim();
		}
		if(area != null){
			area = area.trim();
		}
		try {
		numbers = dataShowService.statisticsAcountsByFileds(dataType, labelName, area);
		} catch (Exception e) {
			logger.error("数据量统计异常!");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public DataShowService getDataShowService() {
		return dataShowService;
	}

	public void setDataShowService(DataShowService dataShowService) {
		this.dataShowService = dataShowService;
	}
	public List<DataStatistics> getRoot() {
		return root;
	}
	public void setRoot(List<DataStatistics> root) {
		this.root = root;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	public BigDecimal getNumbers() {
		return numbers;
	}
	public void setNumbers(BigDecimal numbers) {
		this.numbers = numbers;
	}

}
