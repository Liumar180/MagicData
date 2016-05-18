package com.integrity.dataSmart.timeLine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Series {
	public String name;
	public String type;
	public String barCategoryGap;
	public Integer barWidth;
	
	public Map<String, Map<String, List<String>>> itemStyle;
	public List<Long> data = new ArrayList<Long>();
	public Series() {
		super();
	}
	public Series(String name, String type, String barCategoryGap,
			Integer barWidth, Map<String, Map<String, List<String>>> itemStyle,
			List<Long> data) {
		super();
		this.name = name;
		this.type = type;
		this.barCategoryGap = barCategoryGap;
		this.barWidth = barWidth;
		this.itemStyle = itemStyle;
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBarCategoryGap() {
		return barCategoryGap;
	}
	public void setBarCategoryGap(String barCategoryGap) {
		this.barCategoryGap = barCategoryGap;
	}
	public Integer getBarWidth() {
		return barWidth;
	}
	public void setBarWidth(Integer barWidth) {
		this.barWidth = barWidth;
	}
	public Map<String, Map<String, List<String>>> getItemStyle() {
		return itemStyle;
	}
	public void setItemStyle(Map<String, Map<String, List<String>>> itemStyle) {
		this.itemStyle = itemStyle;
	}
	public List<Long> getData() {
		return data;
	}
	public void setData(List<Long> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Series [name=" + name + ", type=" + type + ", barCategoryGap="
				+ barCategoryGap + ", barWidth=" + barWidth + ", itemStyle="
				+ itemStyle + ", data=" + data + "]";
	}
	

}
