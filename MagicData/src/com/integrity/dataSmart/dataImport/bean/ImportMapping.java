package com.integrity.dataSmart.dataImport.bean;

import java.util.Date;

public class ImportMapping {
	
    /**
	 * id
	 */
	private Long id;
	/**
	 * 历史版本名称
	 */
	private String name;
	/**
	 * 关系
	 */
	private String desc;
	/**
	 * 结点
	 */
	private String nodes;
	/**
	 * 边
	 */
	private String edges;
	/**
	 * 图片
	 */
	private String image;
	/**
	 * 版本时间
	 */
	private Date time;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getNodes() {
		return nodes;
	}
	public void setNodes(String nodes) {
		this.nodes = nodes;
	}
	public String getEdges() {
		return edges;
	}
	public void setEdges(String edges) {
		this.edges = edges;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
