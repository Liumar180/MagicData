package com.integrity.system.auth.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.integrity.login.action.Encryption;

public class AuthDataRecordUtil {
	private static Logger logger = Logger.getLogger(AuthDataRecordUtil.class);
	/**
	 * 获取上次时间
	 * @return
	 * @throws Exception
	 */
	public static String getLastTime() throws Exception{
		InputStream inputStream = null;
		try {
			File f = new File(AuthDataRecordUtil.class.getResource("/").getFile().replace("/classes", "")+"config/solrandtitian.properties"); 
		    inputStream = new FileInputStream(f);
			Properties p = new Properties();
			p.load(inputStream);
			String time = p.getProperty("aabbcc");
			if (StringUtils.isBlank(time)) {
				throw new Exception();
			}
			return Encryption.Decrypt(time.trim());
		} catch (IOException e) {
			logger.error("读取lasttime 异常", e);
			throw new IOException();
		}finally{
			if (inputStream != null) {
				inputStream.close();
			}
		}
		
	}
	/**
	 * 设置当前时间
	 * @param time
	 * @throws Exception
	 */
	public static void setLastTime(String time) throws Exception {
		if (StringUtils.isBlank(time)) {
			throw new Exception();
		}
		String temp = Encryption.Encrypt(time);
		InputStream inputStream = null;
		try {
			File f = new File(AuthDataRecordUtil.class.getResource("/").getFile().replace("/classes", "")+"config/solrandtitian.properties"); 
		    inputStream = new FileInputStream(f);
			Properties p = new Properties();
			p.load(inputStream);
			p.put("aabbcc", temp);
			p.store(new FileOutputStream(f), "aabbcc");
		} catch (IOException e) {
			logger.error("读取lasttime 异常", e);
			throw new IOException();
		}finally{
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
