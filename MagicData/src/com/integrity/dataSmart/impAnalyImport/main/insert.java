package com.integrity.dataSmart.impAnalyImport.main;

import com.integrity.dataSmart.impAnalyImport.util.DateFormat;

public class insert {
	public static void main(String[] args) {
		long st = System.currentTimeMillis();
		String startTime = DateFormat.transferLongToDate(st);
		System.out.println("开始时间：" + startTime);
		String eml = "D:\\emlTrue";
		String filePath = "D:\\nativeData";
		String writePath = "D:\\MagicDataImportTool\\terseLog1.txt";
		String id = "28";
		String [] data = {eml,filePath,writePath,id};
		insertMailData.main(data);
		long et = System.currentTimeMillis();
		String endTime = DateFormat.transferLongToDate(et);
		System.out.println("结束时间：" + endTime);
		System.out.println("总用时："+(et-st)/1000 +"秒");
	}
	/**
	 * eml 文件解析入库执行
	 * @param emlPath  要导入的eml文件的路径
	 * @param localFile 存入本地的附件路径
	 * @param logPath 导入日志路径
	 * @return
	 */
	public static String emlInsertUtil(String emlPath,String localFile,String logPath,String taskId,String total){
		long st = System.currentTimeMillis();
		String startTime = DateFormat.transferLongToDate(st);
		System.out.println("开始时间：" + startTime);
		String [] data = {emlPath,localFile,logPath,taskId,total};
		insertMailData.main(data);
		long et = System.currentTimeMillis();
		String endTime = DateFormat.transferLongToDate(et);
		System.out.println("结束时间：" + endTime);
		System.out.println("总用时："+(et-st)/1000 +"秒");
	
		return "数据录入完成";
	}

}
