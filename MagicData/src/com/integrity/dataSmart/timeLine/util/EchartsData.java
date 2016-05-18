package com.integrity.dataSmart.timeLine.util;

import java.util.ArrayList;
import java.util.List;

public class EchartsData {

	public List<String> title = new ArrayList<String>();
	public List<String> legend = new ArrayList<String>();
	public List<String> xcategory = new ArrayList<String>();// x轴数据
	public List<Long> series = new ArrayList<Long>();// 生成曲线图数据

	public EchartsData() {
		super();
	}

	public EchartsData(List<String> title, List<String> legend,
			List<String> xcategory, List<Long> series) {
		super();
		this.title = title;
		this.legend = legend;
		this.xcategory = xcategory;
		this.series = series;
	}

	public List<String> getTitle() {
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public List<String> getLegend() {
		return legend;
	}

	public void setLegend(List<String> legend) {
		this.legend = legend;
	}

	public List<String> getXcategory() {
		return xcategory;
	}

	public void setXcategory(List<String> xcategory) {
		this.xcategory = xcategory;
	}

	public List<Long> getSeries() {
		return series;
	}

	public void setSeries(List<Long> series) {
		this.series = series;
	}

	@Override
	public String toString() {
		return "EchartsData [title=" + title + ", legend=" + legend
				+ ", xcategory=" + xcategory + ", series=" + series + "]";
	}

}
