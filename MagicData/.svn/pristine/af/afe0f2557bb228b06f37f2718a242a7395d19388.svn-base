package com.integrity.lawCase.exportLaw.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.integrity.lawCase.exportLaw.bean.CaseObjBean;

/**
 * 导出Excel工具类
 */
public class ExportExcel<T> {

	public static void main(String[] args) {
		ExportExcel<CaseObjBean> ex = new ExportExcel<CaseObjBean>();
		List<CaseObjBean> dataset = new ArrayList<CaseObjBean>();
		dataset.add(new CaseObjBean("a", "a", "a", "", "a", "a", "a", "a", "a","a"));
		dataset.add(new CaseObjBean("b", "b", "b", "b", "", "b", "b", "b", "b","b"));
		try {
			OutputStream out = new FileOutputStream("E://a.xls");
			ex.exportExcel("测试POI导出EXCEL文档","E:\\caseModel.xls",dataset, out,"yyyy-MM-dd HH:mm:ss");
			out.close();
			JOptionPane.showMessageDialog(null, "导出成功!");
			System.out.println("excel导出成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出数据到指定模板Excel
	 * @param title
	 * @param dataset
	 * @param out
	 * @param pattern
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(String title,String modelPath,Collection<T> dataset, OutputStream out, String pattern) throws IOException {
		InputStream is = new FileInputStream(modelPath);
		// 拿到模板工作薄
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		//拿到模板工作薄的第一个sheet
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 遍历集合数据，产生数据行
		HSSFRow row = null;
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			//利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					if(value==null){
						value = "";
					}
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		workbook.write(out);
	}
	/**
	 * 导出数据为新的Excel文件
	 * @param title
	 * @param dataset
	 * @param out
	 * @param pattern
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	   public void exportExcel(String title, String[] headers,Collection<T> dataset, OutputStream out, String pattern) throws IOException {
	      // 声明一个工作薄
	      HSSFWorkbook workbook = new HSSFWorkbook();
	      // 生成一个表格
	      HSSFSheet sheet = workbook.createSheet(title);
	      // 设置表格默认列宽度为15个字节
	      sheet.setDefaultColumnWidth((short) 20);
	 
	      //产生表格标题行
	      HSSFRow row = sheet.createRow(0);
	      for (short i = 0; i < headers.length; i++) {
	         HSSFCell cell = row.createCell(i);
//	         cell.setCellStyle(style);
	         HSSFRichTextString text = new HSSFRichTextString(headers[i]);
	         cell.setCellValue(text);
	      }
	 
	      //遍历集合数据，产生数据行
	      Iterator<T> it = dataset.iterator();
	      int index = 0;
	      while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T t = (T) it.next();
				//利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				Field[] fields = t.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					HSSFCell cell = row.createCell(i);
					Field field = fields[i];
					String fieldName = field.getName();
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					try {
						Class tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						if(value==null){
							value = "";
						}
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if (value instanceof Date) {
							Date date = (Date) value;
							SimpleDateFormat sdf = new SimpleDateFormat(pattern);
							textValue = sdf.format(date);
						} else {
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
						}
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						if (textValue != null) {
							Pattern p = Pattern.compile("^//d+(//.//d+)?$");
							Matcher matcher = p.matcher(textValue);
							if (matcher.matches()) {
								// 是数字当作double处理
								cell.setCellValue(Double.parseDouble(textValue));
							} else {
								HSSFRichTextString richString = new HSSFRichTextString(textValue);
								cell.setCellValue(richString);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
	        workbook.write(out);
	 
	   }
}
