package com.integrity.lawCase.exportLaw.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.integrity.lawCase.caseManage.bean.CaseObject;
/**
 * 读取Excel文件工具类
 * @author RenSx
 *
 */
public class ReadExcelUtil {
	
	public static boolean isLCModelLegalFlag = true;//是否是案件管理合法模板标记
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static void main(String[] args) throws Exception {
		/*readLCExcel("");*/
		UUID u = UUID.randomUUID();
		String uStr = u.toString();
		System.out.println(uStr);
	}
	
	/**
	 * 读取案件excel
	 * @param path
	 * @return List<CaseObject>
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<CaseObject> readLCExcel(String path) throws Exception{
		List<CaseObject> list = new ArrayList<CaseObject>();
		DataInputStream input=new DataInputStream(new BufferedInputStream((new FileInputStream(path)))); 
		String suffix = path.substring(path.lastIndexOf(".")+1);
		Workbook workbook = null;
		if("xls".equals(suffix)){
			workbook = new HSSFWorkbook(input);
		}else if("xlsx".equals(suffix)){
			workbook = new XSSFWorkbook(input);
		}
		CaseObject c = null;
		for(int numSheet=0;numSheet<workbook.getNumberOfSheets();numSheet++){
			
			Sheet sheet = workbook.getSheetAt(numSheet);
			if(sheet==null){
				continue;
			}
			//先判断是不是咱的模板
			Row r = sheet.getRow(0);
			Cell cell = r.getCell(12);
			String value = getValue(cell);
			if(value==null||!"a157c781d-3f10-45ad-ba99-7823f0ddd5f3".equals(value)){
				isLCModelLegalFlag = false;
				return null;
			}
			for(int rowNum=1;rowNum<=sheet.getLastRowNum();rowNum++){
				Row row = sheet.getRow(rowNum);
				if(row!=null){
					c = new CaseObject();
					Cell caseName = row.getCell(0);//案件名称
					String caseNameStr = getValue(caseName);
					c.setCaseName(caseNameStr);
					Cell createTime = row.getCell(1);//创建时间
					String createTimeStr = getValue(createTime);
					if(!"".equals(createTimeStr)){
						Date date = sdf.parse(createTimeStr);
						c.setCreateTime(date);
					}
					Cell caseLevelName = row.getCell(2);//案件级别
					String caseLevelNameStr = getValue(caseLevelName);
					c.setCaseLevelName(caseLevelNameStr);
					Cell caseLeader = row.getCell(3);//案件负责人
					String caseLeaderStr = getValue(caseLeader);
					c.setCaseLeader(caseLeaderStr);
					Cell caseSupervisor = row.getCell(4);//案件督办人
					String caseSupervisorStr = getValue(caseSupervisor);
					c.setCaseSupervisor(caseSupervisorStr);
					Cell caseUserNames = row.getCell(5);//案件人员
					String caseUserNamesStr = getValue(caseUserNames);
					c.setCaseUserNames(caseUserNamesStr);
					Cell caseStatusName = row.getCell(6);//案件状态名字
					String caseStatusNameStr = getValue(caseStatusName);
					c.setCaseStatusName(caseStatusNameStr);
					Cell directionCodeName = row.getCell(7);//所属方向名称
					String directionCodeNameStr = getValue(directionCodeName);
					c.setDirectionName(directionCodeNameStr);
					Cell caseAim = row.getCell(8);//案件目标
					String caseAimStr = getValue(caseAim);
					c.setCaseAim(caseAimStr);
					Cell memo = row.getCell(9);//备注
					String memoStr = getValue(memo);
					c.setMemo(memoStr);
					if("".equals(caseNameStr)&&"".equals(createTimeStr)&&"".equals(caseLevelNameStr)&&"".equals(caseLeaderStr)&&"".equals(caseSupervisorStr)&&"".equals(caseUserNamesStr)&&"".equals(caseStatusNameStr)&&"".equals(directionCodeNameStr)&&"".equals(caseAimStr)&&"".equals(memoStr)){
						continue;
					}
					list.add(c);
				}
			}
		}
		return list;
	}
	
    private static String getValue(Cell name) {
    	 if(name==null){
    		 return "";
    	 }else if(name.getCellType()==Cell.CELL_TYPE_BOOLEAN){
    		 return String.valueOf(name.getBooleanCellValue());
    	 }else if(name.getCellType()==Cell.CELL_TYPE_FORMULA){
    		 return String.valueOf(name.getCellFormula());
    	 }else if(name.getCellType()==Cell.CELL_TYPE_NUMERIC){
    		 if(HSSFDateUtil.isCellDateFormatted(name)){
    			 double d = name.getNumericCellValue();
    			 Date date = HSSFDateUtil.getJavaDate(d);
    			 return sdf.format(date);
    		 }
    	 }
    	 return String.valueOf(name.getStringCellValue());
    }   

}
