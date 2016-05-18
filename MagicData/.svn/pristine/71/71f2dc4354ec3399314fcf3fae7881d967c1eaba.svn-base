package com.integrity.dataSmart.dataImport.service;

import java.util.List;

import com.integrity.dataSmart.dataImport.bean.ImportTablesAndPropertys;
import com.integrity.dataSmart.dataImport.bean.TDatabase;
import com.integrity.dataSmart.dataImport.dao.DBDao;
import com.integrity.login.action.Encryption;

public class DBService {

	private DBDao dBDao;

	public DBDao getdBDao() {
		return dBDao;
	}

	public void setdBDao(DBDao dBDao) {
		this.dBDao = dBDao;
	}

	@SuppressWarnings("rawtypes")
	public List findAllDB(String database) {
		List<TDatabase> alldb= dBDao.findAllDB(database);
		for (TDatabase object : alldb) {
			try {
				String url = DatabaseXML.getURLDriver(object.getConnectionDB())[0];
				String driver = DatabaseXML.getURLDriver(object.getConnectionDB())[1];
				String port = object.getConnectionPort();
				String ip = object.getConnectionServerName();
				String table = object.getConnectionDbName();
				String connectionServerUrl = url+ip+":"+port+"/"+table;
				object.setConnectionDriverName(driver);
				object.setConnectionDBPasswordBak(Encryption.Decrypt(object.getConnectionDBPassword()));
				object.setConnectionServerUrl(connectionServerUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return  alldb;
	}
	
	public ImportTablesAndPropertys findImportTablesAndPropertys(long id) {
		return  dBDao.findImportTablesAndPropertys(id);
	}
	public TDatabase findDB(long id) throws Exception {
		TDatabase tDatabase=dBDao.findDB(id);
		tDatabase.setConnectionDBPasswordBak(Encryption.Decrypt(tDatabase.getConnectionDBPassword()));
		return  tDatabase;
	}
	public TDatabase findDBByConnName(String connectionName) throws Exception{
		TDatabase tDatabase=dBDao.findDBByConnName(connectionName);
		String mpwd = tDatabase.getConnectionDBPassword();
		String password = Encryption.Decrypt(mpwd);
		tDatabase.setConnectionDBPasswordBak(password);
		return  tDatabase;
	}
	public List getDBbyConnName(String connname){
		return dBDao.getDBbyConnName(connname);
	}
	public void save(TDatabase t) throws Exception {
		try {
			String pwd = t.getConnectionDBPassword();
			t.setConnectionDBPassword(Encryption.Encrypt(pwd));
			dBDao.save(t);
		} catch (Exception e) {
			throw e; 
		}
	}
	public void edit(TDatabase t) throws Exception {
		try {
			t.setConnectionDBPassword(Encryption.Encrypt(t.getConnectionDBPassword()));
			dBDao.edit(t);
		} catch (Exception e) {
			throw e; 
		}
	}
	public void delete(TDatabase t) throws Exception {
		try {
			dBDao.delete(t);
		} catch (Exception e) {
			throw e; 
		}
	}
	public void save(ImportTablesAndPropertys t) throws Exception {
		try {
			dBDao.save(t);
		} catch (Exception e) {
			throw e; 
		}
	}
	//clear data
	public void clearData(String tablename){
		dBDao.clearData(tablename);
	}
	@SuppressWarnings("rawtypes")
	public List getcolumnsByDBTab(String sql){
		return dBDao.getcolumnsByDBTab(sql);
	}

}
