package com.integrity.dataSmart.particple.code;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.jna.Library;
import com.sun.jna.Native;
/**
 * @author liuBf
 *
 */
public class Participles {
	private static Logger logger = Logger.getLogger(Participles.class);
	// 定义接口CLibrary，继承自com.sun.jna.Library
		public interface CLibrary extends Library {
			// 定义并初始化接口的静态变量
			URL s = Participles.class.getClassLoader().getResource("");
			String path = s.toString().substring(6,s.toString().indexOf("/WEB-INF"));

			CLibrary Instance = (CLibrary) Native.loadLibrary(
					path+"/nlpir/win64/NLPIR", CLibrary.class);
			
			public int NLPIR_Init(String sDataPath, int encoding,
					String sLicenceCode);
					
			public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

			public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,
					boolean bWeightOut);
			public String NLPIR_GetNewWords(String sLine, int nMaxKeyLimit,
					boolean bWeightOut);
			public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit,
					boolean bWeightOut);
			public int NLPIR_AddUserWord(String sWord);//add by qp 2008.11.10
			public int NLPIR_DelUsrWord(String sWord);//add by qp 2008.11.10
			public String NLPIR_GetLastErrorMsg();
			public void NLPIR_Exit();
		}
		// 定义接口CLibraryLinux，继承自com.sun.jna.Library
		public interface CLibraryLinux extends Library {
			// 定义并初始化接口的静态变量
			CLibraryLinux InstanceLinux = (CLibraryLinux) Native.loadLibrary(
					"NLPIR", CLibraryLinux.class);
			public int NLPIR_Init(String sDataPath, int encoding,
					String sLicenceCode);
					
			public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

			public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,
					boolean bWeightOut);
			public String NLPIR_GetNewWords(String sLine, int nMaxKeyLimit,
					boolean bWeightOut);
			public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit,
					boolean bWeightOut);
			public int NLPIR_AddUserWord(String sWord);//add by qp 2008.11.10
			public int NLPIR_DelUsrWord(String sWord);//add by qp 2008.11.10
			public String NLPIR_GetLastErrorMsg();
			public void NLPIR_Exit();
		}

		/**
		 * @param str 需要获取关键词的字符串
		 * @param num 最多获取关键词个数
		 * @return list
		 */
		public static List<String> getKeyWordsList(String str,int num){
			URL s = Participles.class.getClassLoader().getResource("");
			String p = new File("").getAbsolutePath();
			String linuxPath = s.toString().substring(s.toString().indexOf("/webapps"),s.toString().indexOf("/WEB-INF"));
			String argu = p.substring(0,p.length()-4)+linuxPath+"/nlpir";
			if(Participles.isWindowsSys()){
				String path = s.toString().substring(6,s.toString().indexOf("/WEB-INF"));
				argu = path+"/nlpir";
			}
			List<String> keyWordsList = null;
			int charset_type = 1;
			int init_flag = 0;
			if(Participles.isWindowsSys()){
				try {
					
				init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");//windows7 64
				
				} catch (Exception e) {
				logger.error("Windows 分词初始化失败"+e.getMessage());
				}
				String nativeBytes = null;

				if (0 == init_flag) {
					nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
					System.err.println("初始化失败！fail reason is "+nativeBytes);
					return null;
				}
				str = str.replaceAll(" ", "");
				String nativeStr = CLibrary.Instance.NLPIR_GetKeyWords(str+"   ", num,false);
				String regex = " ";
				String words = nativeStr.replaceAll(regex, "#").replaceAll("###", "#");
				keyWordsList = Arrays.asList(words.split("#"));
			}else{
				try {
					init_flag = CLibraryLinux.InstanceLinux.NLPIR_Init(argu, charset_type, "0");//Linux 64
				} catch (Exception e) {
					logger.error("Linux 分词初始化失败；"+e.getMessage());
				}
				String nativeBytes = null;
				if (0 == init_flag) {
					nativeBytes = CLibraryLinux.InstanceLinux.NLPIR_GetLastErrorMsg();
					System.err.println("初始化失败！fail reason is "+nativeBytes);
					return null;
				}
				str = str.replaceAll(" ", "");
				String nativeStr = CLibraryLinux.InstanceLinux.NLPIR_GetKeyWords(str+"   ", num,false);
				String regex = " ";
				String words = nativeStr.replaceAll(regex, "#").replaceAll("###", "#");
				keyWordsList = Arrays.asList(words.split("#"));
			}
			
			return keyWordsList;
		}
		/**
		 * @return true
		 * 判断是否为windows系统
		 */
		private static boolean isWindowsSys(){
	       String p = System.getProperty("os.name");
	       return p.toLowerCase().indexOf("windows") >= 0 ? true : false;
	    }

}
 
