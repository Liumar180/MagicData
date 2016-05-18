package com.integrity.system.auth.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.integrity.system.auth.util.AuthDataRecordUtil;
import com.kndy.goo.VerifyLicense;

public class MagicLicService {

	/**
	 * 
	 * @param projectPath 本地项目根路径
	 * @return
	 */
	public static synchronized boolean licVerify(String projectPath){
		
		Logger logger = Logger.getLogger(MagicLicService.class);
		VerifyLicense vLicense = new VerifyLicense();
		//获取参数
		vLicense.setParam("/com/kndy/goo/param.properties");
		boolean rFlag = false;
		//校验证书
		String lastDateStr = null;
		try {
			lastDateStr = AuthDataRecordUtil.getLastTime();
		} catch (Exception e) {
			logger.error("获得服务器上次校验时间失败");
			e.printStackTrace();
			return rFlag;
		}
		if(null==lastDateStr||"".equals(lastDateStr)){
			rFlag = false;
		}else{
			String firstDateStr = "QWEqwe123!@#";
			Date lastDate = null;
			if(firstDateStr.equals(lastDateStr)){
				lastDate = new Date(System.currentTimeMillis());
			}else{
				try{
					lastDate = new Date(Long.parseLong(lastDateStr));
				}catch(NumberFormatException e){
					logger.error("日期转换失败");
					e.printStackTrace();
					return rFlag;
				}
			}
			if(null!=lastDate){
				rFlag = vLicense.verify(projectPath,lastDate);
			}
		}
		if(rFlag){//如果校验成功
			logger.info("License校验成功");
		}else{
			logger.error("License校验失败");
		}
		try {
			if(rFlag){
				AuthDataRecordUtil.setLastTime(String.valueOf(System.currentTimeMillis()));
			}
		} catch (Exception e) {
			logger.error("服务器上次校验时间保存时间失败");
			e.printStackTrace();
		}
		return rFlag;
	}
}
