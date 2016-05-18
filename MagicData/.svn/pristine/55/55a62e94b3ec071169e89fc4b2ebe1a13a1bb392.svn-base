package com.integrity.system.export.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WriteToWordService {
	// 删除问题
	/**
	 * 对象自带格式化方法
	 * 
	 * @param templatePath
	 * @param targetPath
	 * @param dataMap
	 * @throws Exception
	 */
	public void writeToWord(String templatePath, String targetPath,
			Map<String, String> dataMap, Map<String, String> picDataMap,
			Map<Integer, List<String[]>> tableDataMap) throws Exception {
		InputStream is = new FileInputStream(templatePath);

		/*
		 * HWPFDocument doc = new HWPFDocument(is);// office2003 Range range =
		 * doc.getRange(); if (null != dataMap && dataMap.size() > 0) { for
		 * (Entry<String, String> dataEntry : dataMap.entrySet()) {
		 * range.replaceText(dataEntry.getKey(), dataEntry.getValue()); } }
		 */

		XWPFDocument doc = new XWPFDocument(is);
		if (null != dataMap && dataMap.size() > 0) {
			// 替换段落里面的变量
			this.replaceInPara(doc, dataMap);
			// 替换表格里面的变量
			this.replaceInTable(doc, dataMap);
		}
		if (null != picDataMap && picDataMap.size() > 0) {
			// 替换段落里面的变量图片
			this.replacePicInPara(doc, picDataMap);
			// 替换表格里面的变量图片
			this.replacePicInTable(doc, picDataMap);
		}
		if (null != tableDataMap && tableDataMap.size() > 0) {
			// 填充表格数据
			this.fillTableWithWord(doc, tableDataMap);
		}

		File exportFile = new File(targetPath);
		if (!exportFile.exists()) {
			exportFile.createNewFile();
		}
		OutputStream os = new FileOutputStream(targetPath);
		// 把doc输出到输出流中
		doc.write(os);
		try {
			if (os != null) {
				os.close();
			}
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 合并doc文件
	 * 
	 * @param docList
	 */
	public void joinInWordFiles(List<String> imgList,List<String> sourceDocList, String targetPath)
			throws Exception {

		OutputStream os = new FileOutputStream(targetPath);
		XWPFDocument destDoc = new XWPFDocument();

		if(null!=imgList&&imgList.size()>0){
			for(String imgSrc:imgList){
				XWPFParagraph imgpr = destDoc.createParagraph();
				XWPFRun newRun = imgpr.createRun();
				int format = getPicFormat(imgSrc);
				newRun.addBreak();
				newRun.addPicture(new FileInputStream(imgSrc), format,
						imgSrc, Units.toEMU(450), Units.toEMU(200));
				imgpr.addRun(newRun);
				// newRun.addBreak(BreakType.PAGE);
			}
		}
		
		for (String tempPath : sourceDocList) {
			InputStream is = new FileInputStream(tempPath);
			XWPFDocument sourceDoc = new XWPFDocument(is);

			for (IBodyElement bodyElement : sourceDoc.getBodyElements()) {

				BodyElementType elementType = bodyElement.getElementType();

				if (elementType.name().equals("PARAGRAPH")) {
					XWPFParagraph pr = (XWPFParagraph) bodyElement;
					copyStyle(sourceDoc, destDoc, sourceDoc.getStyles()
							.getStyle(pr.getStyleID()));
					destDoc.createParagraph();
					int pos = destDoc.getParagraphs().size() - 1;
					destDoc.setParagraph(pr, pos);
				} else if (elementType.name().equals("TABLE")) {
					XWPFTable table = (XWPFTable) bodyElement;
					destDoc.createTable();
					int pos = destDoc.getTables().size() - 1;
					destDoc.setTable(pos, table);
					copyStyle(sourceDoc, destDoc, sourceDoc.getStyles()
							.getStyle(table.getStyleID()));
				}
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		destDoc.write(os);
		try {
			if (os != null) {
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制表格样式
	 * @param srcDoc
	 * @param destDoc
	 * @param style
	 */
	private void copyStyle(XWPFDocument srcDoc, XWPFDocument destDoc,
			XWPFStyle style) {
		if (destDoc == null || style == null)
			return;

		if (destDoc.getStyles() == null) {
			destDoc.createStyles();
		}

		List<XWPFStyle> usedStyleList = srcDoc.getStyles().getUsedStyleList(
				style);
		for (XWPFStyle xwpfStyle : usedStyleList) {
			destDoc.getStyles().addStyle(xwpfStyle);
		}
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 */
	private void replaceInPara(XWPFDocument doc, Map<String, String> params) {
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			this.replaceInPara(para, params);
		}
	}

	/**
	 * 替换段落里面的变量图片
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 * @throws Exception
	 */
	private void replacePicInPara(XWPFDocument doc, Map<String, String> params)
			throws Exception {
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			this.replacePicInPara(para, params);
		}
	}

	/**
	 * 替换表格里面的变量
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 */
	private void replaceInTable(XWPFDocument doc, Map<String, String> params) {
		Iterator<XWPFTable> iterator = doc.getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paras;
		while (iterator.hasNext()) {
			table = iterator.next();
			rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					paras = cell.getParagraphs();
					for (XWPFParagraph para : paras) {
						this.replaceInPara(para, params);
					}
				}
			}
		}
	}

	/**
	 * 替换表格里面的变量图片
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 * @throws Exception
	 */
	private void replacePicInTable(XWPFDocument doc, Map<String, String> params)
			throws Exception {
		Iterator<XWPFTable> iterator = doc.getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paras;
		while (iterator.hasNext()) {
			table = iterator.next();
			rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					paras = cell.getParagraphs();
					for (XWPFParagraph para : paras) {
						this.replacePicInPara(para, params);
					}
				}
			}
		}
	}

	/**
	 * 填充表格中内容
	 */
	public void fillTableWithWord(XWPFDocument doc,
			Map<Integer, List<String[]>> params) throws Exception {

		List<XWPFTable> xwpfTableList = doc.getTables();
		if (null != xwpfTableList) {
			for (int i = 0; i < xwpfTableList.size(); i++) {
				if (params.containsKey(i)) {
					XWPFTable table = xwpfTableList.get(i);
					// 获得第一行的行头
					XWPFTableRow tableRowOne = table.getRow(0);
					int cellNum = tableRowOne.getTableCells().size();// 获得列的个数
					List<String[]> cellDatas = params.get(i);
					for (String[] tempStrs : cellDatas) {
						XWPFTableRow tableNewRow = table.createRow();
						int strLen = tempStrs.length;
						if (strLen > 0) {
							for (int j = 0; j < cellNum; j++) {
								if (j >= strLen) {
									tableNewRow.getCell(j).setText("");
								} else {
									tableNewRow.getCell(j).setText(tempStrs[j]);
								}
							}
						}
						// tableOneRow3.addNewTableCell().setText("31");
					}
				}
			}
		}
	}

	/**
	 * 正则匹配字符串
	 */
	private Matcher matcherNormal(String str) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}",
				Pattern.CASE_INSENSITIVE);// 忽略大小写匹配
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param para
	 *            要替换的段落
	 * @param params
	 *            参数
	 */
	private void replaceInPara(XWPFParagraph para, Map<String, String> params) {
		List<XWPFRun> runs;
		Matcher matcher;
		String paraText = para.getParagraphText().trim();
		if (this.matcherNormal(paraText).find()) {// 已出现参数变量
			boolean replaceflag = false;// 替换标识符
			runs = para.getRuns();
			for (int i = 0; i < runs.size(); i++) {
				XWPFRun run = runs.get(i);
				String runText = run.toString();
				matcher = this.matcherNormal(runText);
				if (matcher.find()) {
					String replaceValue = "";
					if (params.containsKey(runText)) {
						if (null != params.get(runText)) {
							replaceValue = params.get(runText);
						}
					}
					runText = replaceValue;
					/*
					 * while ((matcher = this.matcherNormal(runText)).find()) {
					 * runText = matcher.replaceFirst(String.valueOf(params
					 * .get(matcher.group(1)))); }
					 */
					// 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
					// 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
					para.removeRun(i);
					para.insertNewRun(i).setText(runText);
					replaceflag = true;
				}
			}
			if (!replaceflag) {// 如果替换失败
				int startIndex = paraText.indexOf("${");
				int endIndex = paraText.indexOf("}", startIndex);
				String beforeText = paraText.substring(0, startIndex);
				String endText = paraText.substring(endIndex + 1,
						paraText.length());
				String middParamText = paraText.substring(startIndex,
						endIndex + 1);
				if (params.containsKey(middParamText)) {
					String replaceValue = "";
					if (null != params.get(middParamText)) {
						replaceValue = params.get(middParamText);
					}
					do {
						para.removeRun(0);
					} while (para.getRuns().size() > 0);
					para.insertNewRun(0).setText(beforeText);
					para.insertNewRun(1).setText(replaceValue);
					para.insertNewRun(2).setText(endText);
				}
			}
		}
	}

	/**
	 * 替换段落里面的变量图片
	 * 
	 * @param para
	 *            要替换的段落
	 * @param params
	 *            参数
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 */
	private void replacePicInPara(XWPFParagraph para, Map<String, String> params)
			throws Exception {
		List<XWPFRun> runs;
		Matcher matcher;
		String paraText = para.getParagraphText();
		if (this.matcherNormal(paraText).find()) {
			boolean replaceflag = false;
			runs = para.getRuns();// 要注意此处的分组
			for (int i = 0; i < runs.size(); i++) {
				XWPFRun run = runs.get(i);
				String runText = run.toString();
				matcher = this.matcherNormal(runText);
				if (matcher.find()) {
					String replaceValue = "";
					if (params.containsKey(runText)) {
						if (null != params.get(runText)) {
							replaceValue = params.get(runText);
						}
					}
					runText = replaceValue;
					para.removeRun(i);
					XWPFRun newRun = para.insertNewRun(i);
					int format = getPicFormat(runText);
					// newRun.setText(runText);
					newRun.addBreak();
					newRun.addPicture(new FileInputStream(runText), format,
							runText, Units.toEMU(88), Units.toEMU(110)); // 88x110pixels
					// newRun.addBreak(BreakType.PAGE);
					replaceflag = true;
				}
				if (!replaceflag) {// 如果替换失败
					int startIndex = paraText.indexOf("${");
					int endIndex = paraText.indexOf("}", startIndex);
					String beforeText = paraText.substring(0, startIndex);
					String endText = paraText.substring(endIndex + 1,
							paraText.length());
					String middParamText = paraText.substring(startIndex,
							endIndex + 1);
					if (params.containsKey(middParamText)) {
						String replaceValue = "";
						if (null != params.get(middParamText)) {
							replaceValue = params.get(middParamText);
						}
						do {
							para.removeRun(0);
						} while (para.getRuns().size() > 0);

						File tempPic = new File(replaceValue);
						if(tempPic.exists()){
							para.insertNewRun(0).setText(beforeText);
							XWPFRun newRun = para.insertNewRun(1);
							int format = getPicFormat(replaceValue);
							newRun.addBreak();
							newRun.addPicture(new FileInputStream(replaceValue),
									format, runText, Units.toEMU(88),
									Units.toEMU(110));
							para.insertNewRun(2).setText(endText);
						}
					}
				}
			}
		}
	}

	/**
	 * 获得图片类型
	 * 
	 * @param imgFile
	 * @return
	 */
	private int getPicFormat(String imgFile) {
		imgFile = imgFile.toLowerCase();
		int format;

		if (imgFile.endsWith(".emf"))
			format = XWPFDocument.PICTURE_TYPE_EMF;
		else if (imgFile.endsWith(".wmf"))
			format = XWPFDocument.PICTURE_TYPE_WMF;
		else if (imgFile.endsWith(".pict"))
			format = XWPFDocument.PICTURE_TYPE_PICT;
		else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg"))
			format = XWPFDocument.PICTURE_TYPE_JPEG;
		else if (imgFile.endsWith(".png"))
			format = XWPFDocument.PICTURE_TYPE_PNG;
		else if (imgFile.endsWith(".dib"))
			format = XWPFDocument.PICTURE_TYPE_DIB;
		else if (imgFile.endsWith(".gif"))
			format = XWPFDocument.PICTURE_TYPE_GIF;
		else if (imgFile.endsWith(".tiff"))
			format = XWPFDocument.PICTURE_TYPE_TIFF;
		else if (imgFile.endsWith(".eps"))
			format = XWPFDocument.PICTURE_TYPE_EPS;
		else if (imgFile.endsWith(".bmp"))
			format = XWPFDocument.PICTURE_TYPE_BMP;
		else if (imgFile.endsWith(".wpg"))
			format = XWPFDocument.PICTURE_TYPE_WPG;
		else {
			format = XWPFDocument.PICTURE_TYPE_JPEG;
		}
		return format;
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
}
