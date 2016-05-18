package com.integrity.dataSmart.dataImport.service;

import com.integrity.dataSmart.dataImport.bean.DataImportTask;
import com.integrity.dataSmart.dataImport.dao.DataImpTaskDao;
import com.integrity.dataSmart.impAnalyImport.main.insert;

public class DImpTaskEmlRunThread  implements Runnable {

	private String uploadFilePath;
	private String localPath;
	private String fullLogFilePath;
	private DataImportTask dt;
	private DataImpTaskDao dbImpTaskDao;

	public DImpTaskEmlRunThread(String uploadFilePath, String localPath,
			String fullLogFilePath, DataImportTask dt,
			DataImpTaskDao dbImpTaskDao) {
		super();
		this.uploadFilePath = uploadFilePath;
		this.localPath = localPath;
		this.fullLogFilePath = fullLogFilePath;
		this.dt = dt;
		this.dbImpTaskDao = dbImpTaskDao;
	}

	@Override
	public void run() {
		try{
			insert.emlInsertUtil(uploadFilePath, localPath, fullLogFilePath,dt.getId().toString(),dt.getTotality().toString());
			dt.setRunStatus(DataImpAddService.RUNSTATUS_FINISH);
			dbImpTaskDao.updateDataImpTask(dt);
		}catch(Exception e){
			dt.setRunStatus(DataImpAddService.RUNSTATUS_ERROR);
			dbImpTaskDao.updateDataImpTask(dt);
			e.printStackTrace();
		}
	}
}
