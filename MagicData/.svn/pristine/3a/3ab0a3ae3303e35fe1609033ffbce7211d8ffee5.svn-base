package com.integrity.system.reset.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.integrity.system.reset.dao.ResetDao;

public class ResetService {

	private Logger logger = Logger.getLogger(this.getClass());
	private ResetDao resetDao;

	public void setResetDao(ResetDao resetDao) {
		this.resetDao = resetDao;
	}
	
	

	/**
	 * 回复出厂设置
	 * @return
	 */
	public void resetData() {
		//清空表数据
		resetDao.dropData();
		
		//执行初始化sql
		List<String> sqls = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream("../config/init.txt"), "UTF-8")
			);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (StringUtils.isNotBlank(line)) {
					sqls.add(line);
				}
			}
		} catch (Exception e) {
			logger.error("读取初始化sql文件异常",e);
		}finally{
			try {
				if (br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		resetDao.initData(sqls);
	}
	
	
}
