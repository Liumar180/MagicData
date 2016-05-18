package com.integrity.dataSmart.exportView.action;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.exportView.service.ExportViewService;
import com.integrity.dataSmart.pojo.VertexObject;
import com.integrity.dataSmart.titanGraph.service.SearchDetailService;
import com.integrity.lawCase.util.FilesToZip;
import com.integrity.system.export.service.WriteToWordService;
import com.opensymphony.xwork2.ActionSupport;

public class ExportViewAjaxAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(ExportViewAjaxAction.class);

	/** 导出的对象id集合 */
	private String ids;

	private String currentTimeStr;// 从前台传递的时间字符串

	private SearchDetailService searchDetailService;

	private ExportViewService exportViewService;

	private ServletContext sc = ServletActionContext.getServletContext();
	private String webPath = sc.getRealPath("/");

	public String exportViewObjs() {
		Long beforeThrHour = System.currentTimeMillis()-1*60*60*1000;
		if (null != ids && !"".equals(ids.trim())) {
			File rootDir = new File(webPath + ExportViewService.VIEW_EXPORT_PATH);
			File[] subRootDirs = rootDir.listFiles();
			for(int i=0;i<subRootDirs.length;i++){
				File tempDir = subRootDirs[i];
				if(tempDir.exists()&&tempDir.isDirectory()){
					String tempName = tempDir.getName();
					if(tempName.matches("^[0-9]+$")){
						Long dirLong = Long.parseLong(tempName);
						if(dirLong<beforeThrHour){
							WriteToWordService.deleteDir(tempDir);
						}
					}
				}
			}
			List<String> sourceFileList = new LinkedList<>();
			File currentExportPath = new File(webPath
					+ ExportViewService.VIEW_EXPORT_PATH + File.separator
					+ currentTimeStr);
			if (!currentExportPath.exists() || !currentExportPath.isDirectory()) {
				currentExportPath.mkdirs();
			}
			
			String[] idsArray = ids.split(",");
			if (null != idsArray && idsArray.length > 0) {
				List<VertexObject> voList = searchDetailService
						.findVertexObjects(idsArray);
				List<VertexObject> eventList = new ArrayList<VertexObject>();
				List<VertexObject> noEventList = new ArrayList<VertexObject>();
				for (VertexObject tempVo : voList) {
					if (tempVo.getType().indexOf("Event") > 0) {
						eventList.add(tempVo);
					} else {
						noEventList.add(tempVo);
					}
				}
				// 先单个生成 再合并
				// 生成非事件
				for (int i = 0; i < noEventList.size(); i++) {
					VertexObject vo = voList.get(i);
					String voType = vo.getType();
					String templantFilePath = "";
					String voExportFilePath = currentExportPath
							.getAbsolutePath() + File.separator;
					if (DataType.PERSON.equals(voType)) {
						Map<String, String> baseProMap = vo.getBaseProMap();
						String tempStr = "unknow";
						if(baseProMap.size()>0){
							for(Entry<String,String> tempEntry:baseProMap.entrySet()){
								String firstValue = tempEntry.getValue();
								if(null!=firstValue&&!"".equals(firstValue)){
									tempStr = tempEntry.getValue();
									break;
								}
							}
						}
						/*if(tempStr.equals(DataType.PERSON)){
							tempStr = "unknow";
						}*/
						voExportFilePath = voExportFilePath + DataType.PERSON + 
								+ i +"_"+tempStr+ ".doc";
						templantFilePath = webPath
								+ ExportViewService.PERSON_EXPORT_TEMPLANT;
					} else if (DataType.GROUP.equals(voType)) {
						voExportFilePath = voExportFilePath + DataType.GROUP
								+ i + ".doc";
						templantFilePath = webPath
								+ ExportViewService.GROUP_EXPORT_TEMPLANT;
					} else {
						voExportFilePath = "";
					}
					if (!"".equals(voExportFilePath)) {
						File templantFile = new File(templantFilePath);
						if (!templantFile.exists()) {
							logger.error("缺少导出模板文件");
							return ERROR;
						}
						Map<String, String> dataMap = exportViewService
								.getExportMap(vo);
						Map<Integer, List<String[]>> dataTableMap = exportViewService
								.getExpUnEventTableMap(vo);
						try {
							WriteToWordService wservice = new WriteToWordService();
							wservice.writeToWord(templantFilePath,
									voExportFilePath, dataMap, null,
									dataTableMap);
							sourceFileList.add(voExportFilePath);
						} catch (Exception e) {
							logger.error("图形节点导出-非事件节点doc文件生成失败");
							e.printStackTrace();
							return ERROR;
						}
					}
				}
				// 生成事件
				if (null != eventList && eventList.size() > 0) {
					String templantFilePath = webPath
							+ ExportViewService.EVENT_EXPORT_TEMPLANT;
					String voExportFilePath = currentExportPath
							.getAbsolutePath() + File.separator + "Event.doc";
					File templantFile = new File(templantFilePath);
					if (!templantFile.exists()) {
						logger.error("缺少导出模板文件");
						return ERROR;
					}
					Map<Integer, List<String[]>> dataTableMap = exportViewService
							.getExpEventTableData(eventList);
					try {
						WriteToWordService wservice = new WriteToWordService();
						wservice.writeToWord(templantFile.getAbsolutePath(),
								voExportFilePath, null, null, dataTableMap);
						sourceFileList.add(voExportFilePath);
					} catch (Exception e) {
						logger.error("图形节点导出-事件节点doc文件生成失败");
						e.printStackTrace();
						return ERROR;
					}
				}
				//图片文件列表
				List<String> imgList = new LinkedList<String>();
				imgList.add(currentExportPath+File.separator+currentTimeStr+".png");
				// 合并文件(1.图形2.人员3.群组4.事件)
				String voExportFilePath = currentExportPath.getAbsolutePath()
						+ File.separator + "AllView.doc";
				try {
					WriteToWordService wservice = new WriteToWordService();
					wservice.joinInWordFiles(imgList,sourceFileList, voExportFilePath);
				} catch (Exception e) {
					logger.error("图形节点导出-合并doc文件生成失败");
					e.printStackTrace();
					return ERROR;
				}

			}
			FilesToZip.fileToZip(currentExportPath.getAbsolutePath(),
					currentExportPath.getAbsolutePath(), "Views");
		}
		return SUCCESS;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCurrentTimeStr() {
		return currentTimeStr;
	}

	public void setCurrentTimeStr(String currentTimeStr) {
		this.currentTimeStr = currentTimeStr;
	}

	public void setSearchDetailService(SearchDetailService searchDetailService) {
		this.searchDetailService = searchDetailService;
	}

	public void setExportViewService(ExportViewService exportViewService) {
		this.exportViewService = exportViewService;
	}

}
