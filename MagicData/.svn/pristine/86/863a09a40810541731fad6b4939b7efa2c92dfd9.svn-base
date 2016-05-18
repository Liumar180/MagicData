package com.integrity.dataSmart.impAnalyImport.analyticalmail;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.POITextExtractor;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class ReadFileUtil {
//word
	 @SuppressWarnings("resource")
	public String getDocument(String url) throws Exception {
		String suffix=url.substring(url.lastIndexOf(".")+1);
		InputStream is=null;
		OPCPackage opcPackage=null;
		POIXMLTextExtractor extractor=null;
		ExcelExtractor extractor1=null;
		HSSFWorkbook wb=null;
		BufferedReader bufferReader=null;
		XSSFWorkbook wb1=null;
		PDDocument document=null;
		InputStreamReader inputReaderTXT = null;
        BufferedReader bufferReaderTXT = null;
        InputStream inputStreamTXT =null;
		try {
				if("doc".equals(suffix)){ 
					 is = new FileInputStream(new File(url));
		            
					WordExtractor ex = new WordExtractor(is);
		             String text2003 = ex.getText();
		             is.close();
		             ex.close();
		             return text2003;
				}else if("docx".equals(suffix)){ 
					try {
					 opcPackage = POIXMLDocument.openPackage(url);
					 extractor = new XWPFWordExtractor(opcPackage);
		             String text2007 = extractor.getText();
		             
		             return text2007;
					}catch(Exception e){
						e.printStackTrace();
						System.err.println("附件 docx格式解析错误。");
						System.out.println("附件 docx格式解析错误。");
					}finally{
						if(opcPackage!=null){
							opcPackage.close();
						}
			           //  extractor.close();
					}
				}else if("xls".equals(suffix)){
					try {
						 is = new FileInputStream(new File(url));
						 wb=new HSSFWorkbook(new POIFSFileSystem(is));
						 
						 extractor1=new ExcelExtractor(wb);
						extractor1.setFormulasNotResults(false);
						extractor1.setIncludeSheetNames(true);
				        String s =extractor1.getText();
				        
				        return s;
					} catch (NotOLE2FileException e) {
						e.printStackTrace();
						System.err.println("附件 xls格式解析错误。");
						System.out.println("附件 xls格式解析错误。");
						is = new FileInputStream(new File(url));
				         bufferReader = new BufferedReader(new InputStreamReader(is));
					        try
					        {
					        	
					            // 读取一行
					            String line = "";
					            StringBuffer strBuffer = new StringBuffer();
					                 
					            while ((line = bufferReader.readLine()) != null)
					            {
					                strBuffer.append(line);
					            } 
					             return strBuffer.toString();
					        }
					        catch (Exception ex)
					        {
					        	ex.printStackTrace(System.out);
					        }
					        finally
					        {
					        	if(is!=null){
					        		is.close();
					        	}
					        	if(bufferReader!=null){
					        		bufferReader.close();
					        	}
					        	
					        }
						
					}finally{
						if(is!=null){
			        		is.close();
			        	}
			        	if(extractor1!=null){
			        		extractor1.close();
			        	}
					}
					
				}else if("xlsx".equals(suffix)){
					if (getfilesize(url)<10240) {
						is = new FileInputStream(new File(url));
						wb1 = new XSSFWorkbook(is); 
						StringBuffer  result=new StringBuffer();
						int sheetNum = wb1.getNumberOfSheets();  
						
						for (int i = 0; i < sheetNum; i++) {
							Sheet childSheet = wb1.getSheetAt(i);  
							int rowNum = childSheet.getLastRowNum(); 
							result.append("\n"+childSheet.getSheetName()+"\n");
							if(childSheet.getRow(0)!=null||rowNum>0){
							for (int j = 0; j <= rowNum; j++) { 
								Row row = childSheet.getRow(j);  
								while(row==null){
									row = childSheet.getRow(++j);
								}
								int cellNum = row.getLastCellNum();  
								for (int k = 0; k < cellNum; k++) { 
									if(row.getCell(k)==null){
										while(row.getCell(k)==null){
											++k;
											if(row.getCell(k)!=null){
												result.append("\n"+row.getCell(k).toString());
											}
										}		 
									}else{
										result.append("\t"+row.getCell(k).toString());
									}
								}  
							} 
						} 
					}
						is.close();wb1.close();
						return result.toString();
					}else {
						System.err.println("此xlsx附件大于1M不解析！url:"+url);
						System.out.println("此xlsx附件大于1M不解析！url:"+url);
						return "";
					}

				}else if("ppt".equals(suffix)){
					StringBuffer content = new StringBuffer("");  
					  try{  
					   SlideShow ss = new SlideShow(new HSLFSlideShow(url));//is 为文件的InputStream，建立SlideShow  
					   Slide[] slides = ss.getSlides();//获得每一张幻灯片 
					   for(int i=0;i<slides.length;i++){  
					   TextRun[] t = slides[i].getTextRuns();//为了取得幻灯片的文字内容，建立TextRun  
					    for(int j=0;j<t.length;j++){  
					     content.append(t[j].getText());//这里会将文字内容加到content中去  
					    }  
					    content.append(slides[i].getTitle());  
					   }  
					   
					  }catch(Exception ex){  
						  ex.printStackTrace();
						  System.err.println("附件 ppt格式解析错误。");
						  System.out.println("附件 ppt格式解析错误。");
					  }  
					  
					  return content.toString();
				}else if("pptx".equals(suffix)){
					POITextExtractor extractor2 = ExtractorFactory.createExtractor(new File(url));
					String text=extractor2.getText();
					extractor2.close();
					return text;
				}else if("pdf".equals(suffix)){
					          document = null;  
					         try {  
					             is = new FileInputStream(url);  
					             PDFParser parser = new PDFParser(is);  
					             parser.parse();  
					             document = parser.getPDDocument();  
					             PDFTextStripper stripper = new PDFTextStripper();  
					             return stripper.getText(document);  
					         } catch (FileNotFoundException e) {  
					             e.printStackTrace(System.out);  
					             System.err.println("没有找到响应的pdf文件。");
					             System.out.println("没有找到响应的pdf文件。");
					         } catch (IOException e) {  
					             e.printStackTrace(System.out); 
					             System.err.println("附件 pdf格式解析错误。");
					             System.out.println("附件 pdf格式解析错误。");
					         } finally {  
					             if (is != null) {  
					                 try {  
					                     is.close();  
					                } catch (IOException e) {  
					                     e.printStackTrace(System.out);  
					                 }  
					             }  
					             if (document != null) {  
					                 try {  
					                     document.close();  
					                 } catch (IOException e) {  
					                     e.printStackTrace(System.out);  
					                 }  
					             }  
					         }  

				}else if("txt".equals(suffix)){
				        try
				        {
				             inputStreamTXT = new FileInputStream(url);
				            inputReaderTXT = new InputStreamReader(inputStreamTXT);
				            bufferReaderTXT = new BufferedReader(inputReaderTXT);
				            // 读取一行
				            String line = null;
				            StringBuffer strBuffer = new StringBuffer();
				                 
				            while ((line = bufferReaderTXT.readLine()) != null)
				            {
				                strBuffer.append(line);
				            } 
				             return strBuffer.toString();
				        }
				        catch (Exception e)
				        {
				        	e.printStackTrace(System.out);
				        }
				        finally
				        {
				        	if(inputReaderTXT!=null){
				        		inputReaderTXT.close();
				        	}
				        	if(bufferReaderTXT!=null){
				        		bufferReaderTXT.close();
				        	}
				        	if(inputStreamTXT!=null){
				        		inputStreamTXT.close();
				        	}
				        }

				}else if("rtf".equals(suffix)){
					try { 
						DefaultStyledDocument styledDoc = new DefaultStyledDocument();
						new RTFEditorKit().read(new FileInputStream(url), styledDoc, 0);
						String rtfString=new String(styledDoc.getText(0,styledDoc.getLength()).getBytes("ISO8859_1"));
						return rtfString;
						} catch (IOException e) {
							e.printStackTrace(System.out);
						} catch (BadLocationException e) {
							e.printStackTrace(System.out);}
				}
				
	         } catch (Exception e) {
	             e.printStackTrace(System.out);
	             System.err.println("附件 解析错误。");
	             System.out.println("附件 解析错误。");
	         }
		return "";

		}

	/**
     * 去掉字符串右边的空格
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
     public static String rightTrim(String str) {
       if (str == null) {
           return "";
       }
       int length = str.length();
       for (int i = length - 1; i >= 0; i--) {
           if (str.charAt(i) != 0x20) {
              break;
           }
           length--;
       }
       return str.substring(0, length);
    }
     public static long getfilesize(String url){
    	  FileInputStream fis= null;  
    	  Long l=(long) 0;
    	    try{  
    	        File f= new File(url);  
    	        fis= new FileInputStream(f);
    	        l=(long)fis.available()/1024;
    	    }catch(Exception e){  
    	    } finally{  
    	        if (null!=fis){  
    	            try {  
    	                fis.close();  
    	            } catch (IOException e) {  
    	            }  
    	        }  
    	    }  
    	    return l;
     }
     
	public static void main(String args[]){
		try {
			System.out.println(new ReadFileUtil().getDocument("C:\\Users\\flrhkd\\Desktop\\aaaaa\\FS-31.1.2014.xlsx"));
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		//System.out.println(getfilesize("C:\\Users\\flrhkd\\Desktop\\aaaaa\\FinancialModelv9.2.6.xls"));
	}
	
}
