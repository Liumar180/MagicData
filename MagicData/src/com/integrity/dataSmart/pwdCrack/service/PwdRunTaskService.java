package com.integrity.dataSmart.pwdCrack.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.integrity.dataSmart.pwdCrack.bean.PwdTask;
import com.integrity.dataSmart.pwdCrack.dao.PwdTaskDao;

/**
 * 密码破解任务执行Service
 * @author HanXue
 *
 */
public class PwdRunTaskService {
	
	private Logger logger = Logger.getLogger(PwdRunTaskService.class);

	/** 最大允许线程数 */
	public final static int MAX_RUNNING_COUNT = 1;
	
	/**Windows系统标识*/
	public final static String WINDOWS_SYSTEM_FLAG="W";
	
	/**Linux系统标识*/
	public final static String LINUX_SYSTEM_FLAG="L";
	
	/**彩虹表配置文件名*/
	public final static String PT_PROFILE_NAME="pwdCrackConfig.properties";
	
	/**彩虹表配置文件属性Key-启动的线程数*/
	public final static String PT_PROKEY_THREAD_COUNT="threadCount";
	
	/**彩虹表配置文件属性Key-彩虹表文件路径*/
	public final static String PT_PROKEY_RTABLE_PATH="rainbowTablesPath";
	
	/**彩虹表配置文件属性Key-破解工具windows下路径*/
	public final static String PT_PROKEY_RCRACKTOOLS_WINPATH="rcrackWinPath";
	
	/**彩虹表配置文件属性Key-系统类型*/
	public final static String PT_PROKEY_SYSTEM_TYPE="systemType";
	
	public final static String DATA_PATH = "MagicData\\data";
	
	private PwdTaskDao pwdTaskDao;
	
	public void autoRunPwdTask(){
		//获得当前正在运行的任务数
		logger.info("pwdAutoCrack Running...");
		String dataPath = System.getProperty("user.dir").replace("bin", "webapps")+File.separator+DATA_PATH;
		Integer runningCount = getRunningCount();
		if(null!=runningCount&&runningCount<PwdRunTaskService.MAX_RUNNING_COUNT){
			int preRunCount = PwdRunTaskService.MAX_RUNNING_COUNT - runningCount;
			//执行最早的未执行任务
			String unRunHql = "from PwdTask t where t.runStatus = ? order by t.createTime asc";
			List<?> resultList = pwdTaskDao.findByHQL(unRunHql, PwdTaskService.UNRUN);
			if(null!=resultList&&resultList.size()>0){
				//执行任务 
				for(int i =0;i<preRunCount;i++){
					Object tempObj = resultList.get(i);
					if(null!=tempObj){
						PwdTask tempPt = (PwdTask)tempObj;
						updateRunPT(tempPt,dataPath);
					}
				}
			}
		}

	}
	
	/**
	 * 单个执行任务
	 */
	public void updateRunPT(PwdTask pt,String dataPath){
		//建立运行线程
		//1.线程名称 2.在同名线程中保存运行记录 3.计算进度保存到任务中4 如果出现错误需要该状态为执行失败5 执行完成改状态为成功
		if(null!=pt){
			//设置任务运行状态为运行中
			pt.setRunStatus(PwdTaskService.RUNNING);
			pwdTaskDao.updatePwdTask(pt);
			pwdTaskDao.sessionFlush();
			Map<String,String> configMap =  getPwdConfig(dataPath);
			PwdTaskRunThread ptrThread = new PwdTaskRunThread(pt.getId().toString(), configMap.get(PT_PROKEY_THREAD_COUNT), 
					configMap.get(PT_PROKEY_RTABLE_PATH), configMap.get(PT_PROKEY_RCRACKTOOLS_WINPATH),
					configMap.get(PT_PROKEY_SYSTEM_TYPE),pwdTaskDao);
			Thread t = new Thread(ptrThread);
			t.setName("Thread-"+pt.getUuid()+"-"+pt.getUserName());
			t.start();
			//t.run();
		}
	}
	
	/**
	 * 获得配置信息
	 * @param dataPath
	 * @return
	 */
	private Map<String,String> getPwdConfig(String dataPath){
		Map<String,String> configMap = new HashMap<String,String>();
		Properties ipLLProperties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(dataPath+File.separator+PwdRunTaskService.PT_PROFILE_NAME);
			ipLLProperties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(null!=ipLLProperties&&ipLLProperties.size()>0){
			String os = System.getProperty("os.name");
			if(os.startsWith("win")|| os.startsWith("Win") ){
				configMap.put(PwdRunTaskService.PT_PROKEY_SYSTEM_TYPE , PwdRunTaskService.WINDOWS_SYSTEM_FLAG);
				configMap.put(PwdRunTaskService.PT_PROKEY_RCRACKTOOLS_WINPATH , ipLLProperties.getProperty(PwdRunTaskService.PT_PROKEY_RCRACKTOOLS_WINPATH));
			}else{
				configMap.put(PwdRunTaskService.PT_PROKEY_SYSTEM_TYPE , PwdRunTaskService.LINUX_SYSTEM_FLAG);
			}
			configMap.put(PwdRunTaskService.PT_PROKEY_THREAD_COUNT , ipLLProperties.getProperty(PwdRunTaskService.PT_PROKEY_THREAD_COUNT));
			configMap.put(PwdRunTaskService.PT_PROKEY_RTABLE_PATH , ipLLProperties.getProperty(PwdRunTaskService.PT_PROKEY_RTABLE_PATH));
		}
		
		return configMap;
	}
	
	/**
	 * 获得正在执行的密码破解任务数目
	 * @return
	 */
	public Integer getRunningCount(){
		String countHql = "from PwdTask t where t.runStatus = "+PwdTaskService.RUNNING;
		Integer runningCount = pwdTaskDao.getAllCount(countHql);
		return runningCount;
	}

	public PwdTaskDao getPwdTaskDao() {
		return pwdTaskDao;
	}

	public void setPwdTaskDao(PwdTaskDao pwdTaskDao) {
		this.pwdTaskDao = pwdTaskDao;
	}

}
