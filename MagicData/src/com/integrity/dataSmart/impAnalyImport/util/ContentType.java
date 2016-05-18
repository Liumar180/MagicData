package com.integrity.dataSmart.impAnalyImport.util;

/**
 * 获取文件类型
 * 
 * @author liubf
 *
 * 2016年3月15日 下午15:12:26
 */
public enum ContentType {
	//加入对SWF、PDF、脚本语言（jsp、ASP等）
	SWF("swf", "application/swf"),
	PDF("pdf", "application/pdf"),
	DOC("doc", "application/msword"), 
	DOCX("docx", "application/msword"),
	XLS("xls", "application/-excel"),
	XLSX("xlsx", "application/-excel"),
	XML("xml", "text/xml"),
	HTM("htm", "text/html"),
	HTML("html", "text/html"),
	CSS("css", "text/css"),
	TXT("txt", "text/plain"),
	//脚本语言
	JSP("jsp","script/jsp"),
	ASP("asp","script/asp"),
	JS("js","script/js"),
	PHP("php","script/php"),
	VBS("vbs","script/vbs"),
	JAVA("java","script/java");
	
	
	private String type;

	private String name;

	private ContentType(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String getTypeByName(String name){
		for (ContentType contentType :ContentType.values()) {
			if (name.equals(contentType.getName())) {
				return contentType.getType();
			}
		}
		return null;
	}

	public static String getNameByType(String type){
		for (ContentType contentType :ContentType.values()) {
			if (type.equals(contentType.getType())) {
				return contentType.getName();
			}
		}
		return null;
	}

}