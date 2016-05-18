package com.integrity.dataSmart.impAnalyImport.util.FileOnlineShow;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.integrity.dataSmart.impAnalyImport.util.ContentType;
import com.integrity.dataSmart.impAnalyImport.util.FileTools;
import com.integrity.lawCase.util.ChineTo16Utils;
/**
 * doc docx格式转换
 * @author Liubf
 * 文本在线预览实现
 */
public class DocConverter {
	private static Logger logger = Logger.getLogger(DocConverter.class);
    private static int environment;//环境1：windows 2:linux(涉及pdf2swf路径问题)
    private String fileString;
    private String outputPath;//输入路径，如果不设置就输出在默认位置（上传文件保存的路径）
    private String fileName;
    private File pdfFile;
    private File swfFile;
    private File docFile;
    static{
    	if(isWindowsSys()){
    		environment = 1;
    	}else{
    		environment = 2;
    	}
    }
    public DocConverter(String fileString,String annexName){
        ini(fileString,annexName);
    }
    /*
     * 重新设置 file
     * @param fileString
     */
    public void setFile(String fileString,String annexName){
        ini(fileString,annexName);
    }
    /**
     * @param fileString
     * 初始化
     */
    private void ini(String fileString,String annexName){
        this.fileString=fileString;
        fileString = fileString.replaceAll("\\\\", "/");
        fileName=fileString.substring(0,fileString.lastIndexOf("."));
        docFile=new File(fileString);
        pdfFile=new File(fileName+".pdf");
        String s = fileName.substring(0,fileName.lastIndexOf(annexName)+annexName.length());
        String e = fileName.substring(fileName.lastIndexOf("/")+1);
		String utf8name = ChineTo16Utils.getUtf8(e);
		utf8name = s+File.separator+utf8name;
        swfFile=new File(utf8name+".swf");
    }
    /**
     * 判断是否是windows操作系统
     * @author Liubf
     * @return true false
     */
    private static boolean isWindowsSys() {
       String p = System.getProperty("os.name");
       return p.toLowerCase().indexOf("windows") >= 0 ? true : false;
    }
    
    /**
     * 转pdf
     * @param serviceIp
     */
    private void doc2pdf(String serviceIp){
        if(docFile.exists()){
            if(!pdfFile.exists()){
                OpenOfficeConnection connection=new SocketOpenOfficeConnection(serviceIp,8100);
                try
                {
                    connection.connect();
                    DocumentConverter converter=new OpenOfficeDocumentConverter(connection);
                    try {
                    	converter.convert(docFile,pdfFile);
                    	//close the connection
					} catch (Exception e) {
						logger.error("上传的文本格式不符合要求！",e);
					}
                }catch(java.net.ConnectException e){
                	logger.error("****swf转换异常，openoffice服务未启动！****",e);
                }catch(com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e){
                    logger.error("****swf转换器异常，读取转换文件失败****",e);
                    throw e;
                }catch(Exception e){
                    e.printStackTrace();
                    throw e;
                }finally {  
                    if (connection != null) {  
                        connection.disconnect();
                        connection = null;  
                    }  

                } 
            }else{
                //System.out.println("****已经转换为pdf，不需要再进行转化****");
            }
        }
        else{
        	logger.error("****swf转换器异常，需要转换的文档不存在，无法转换****");
        }
    }
    /**
     * 转swf
     */
    private void pdf2swf(String exePath){
        Runtime r=Runtime.getRuntime();
        if(!swfFile.exists()){
            if(pdfFile.exists()){
                if(environment==1){//windows环境处理
                    try {
                        Process p=r.exec(exePath+" "+pdfFile.getPath()+" -o "+swfFile.getPath()+" -T 9 -t -s storeallcharacters");
                        loadStream(p.getInputStream());
                        if(pdfFile.exists())
                        {
                            pdfFile.delete();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else if(environment==2){//linux环境处理
                    try {
                    	Process p=r.exec(exePath+" "+pdfFile.getPath()+" -o "+swfFile.getPath()+" -T 9 -t -s storeallcharacters");
                        loadStream(p.getInputStream());
                        if(pdfFile.exists()){
                            pdfFile.delete();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
            	logger.error("****pdf不存在，无法转换****");
            }
        }else{
        	logger.info("****swf已存在不需要转换****");
        }
    }
    static String loadStream(InputStream in){
        int ptr=0;
        in=new BufferedInputStream(in);
        StringBuffer buffer=new StringBuffer();
        try {
			while((ptr=in.read())!=-1)
			{
			    buffer.append((char)ptr);
			}
		} catch (IOException e) {
			logger.error("加载数据流异常",e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return buffer.toString();
    }
    /**
     * @param fileName 文件名（含后缀）
     * @return true false
     * 转换主方法
     */
    public boolean conver(String fileName,String openIp,String exePath){
    	String fileTypes =  FileTools.getTypeByFileName(fileName);
		String contentTypes= ContentType.getNameByType(fileTypes);
		if (StringUtils.isBlank(contentTypes)) {
			logger.error("当前文件类型不支持预览或者源文件不存在: "+ fileName );
			return false;
		}else{
        if(swfFile.exists())
        {
            return true;
        }
        try {
        	if(!"swf".equals(fileTypes)){
        		doc2pdf(openIp);
        	}
        		pdf2swf(exePath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if(swfFile.exists())
        {
            return true;
        }
        else {
            return false;
        }
    }
    }

    /**
     * @return path
     * 返回文件路径
     */
    public String getswfPath(){
        if(swfFile.exists())
        {
            String tempString =swfFile.getPath();
            tempString=tempString.replaceAll("\\\\", "/");
            return tempString;
        }
        else{
            return "";
        }
    }

    /**
     * @param outputPath
     * 设置输出路径
     */
    public void setOutputPath(String outputPath){
        this.outputPath=outputPath;
        if(!outputPath.equals(""))
        {
            String realName=fileName.substring(fileName.lastIndexOf("/"),fileName.lastIndexOf("."));
            if(outputPath.charAt(outputPath.length())=='/')
            {
                swfFile=new File(outputPath+realName+".swf");
            }
            else
            {
                swfFile=new File(outputPath+realName+".swf");
            }
        }
    }
   
}
