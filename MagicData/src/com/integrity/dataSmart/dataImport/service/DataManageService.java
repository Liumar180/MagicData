package com.integrity.dataSmart.dataImport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowBuffer;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.TransMeta;

import com.integrity.dataSmart.dataImport.bean.TDatabase;
import com.integrity.dataSmart.dataImport.dao.DBDao;

public class DataManageService {
	private DBDao dBDao;

	public DBDao getdBDao() {
		return dBDao;
	}

	public void setdBDao(DBDao dBDao) {
		this.dBDao = dBDao;
	}
	
	/**
	 * 获得指定数据库连接中指定表的前5条数据
	 * @param conn
	 * @param tableName
	 * @return List<Map<String,String>>
	 * @throws Exception
	 */
	public Map<String, Object> getRecords(TDatabase conn, String tableName) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,List<Map<String,String>>> finalMap = new HashMap<String,List<Map<String,String>>>();
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		List<Object[]> list = new ArrayList<Object[]>();
		if (null==conn){
			result.put("success", "false");
			result.put("message", "数据源为空，请重试！");
			result.put("finalMap", finalMap);
			return result;
		}else {
			try {
				KettleEnvironment.init();
				TransMeta transMeta = new TransMeta();
				DatabaseMeta dm = new DatabaseMeta(conn.getConnectionName(),
						conn.getConnectionDB(), DatabaseMeta.dbAccessTypeCode[0],
						conn.getConnectionServerName(),
						conn.getConnectionDbName(), conn.getConnectionPort(),
						conn.getConnectionDBUserName(),
						conn.getConnectionDBPasswordBak());
				List<DatabaseMeta> dmList = new ArrayList<DatabaseMeta>();
				dmList.add(dm);
				transMeta.setDatabases(dmList);
				Database db = new Database(transMeta, dm);
				db.connect();
				RowMetaInterface tableFields = db.getTableFields(tableName);
				RowBuffer rb = new RowBuffer(tableFields);
				List<ValueMetaInterface> valueList = rb.getRowMeta().getValueMetaList();
				List<String> titleList = new ArrayList<String>();
				int columnNum = valueList.size();
				for (int j = 0; j < valueList.size(); j++) {
					ValueMetaInterface vm = valueList.get(j);
					titleList.add(vm.getName());
				}
				String sql = "select * from "+tableName;
				list = db.getRows(sql, 5);
				
				Map<String,String> m = null;
				for(int i=0;i<list.size();i++){
					Object[] o = list.get(i);
					m = new LinkedHashMap<String,String>();
					for(int j=0;j<columnNum;j++){
						String t = titleList.get(j);
						if(o[j]==null){
							m.put(t, "");
						}else{
							m.put(t, o[j].toString());
						}
					}
					dataList.add(m);
				}
				finalMap.put("rows", dataList);
				db.disconnect();
				result.put("success", "true");
				result.put("message", "查询数据成功！");
				result.put("finalMap", finalMap);
			} catch (KettleException e) {
				e.printStackTrace();
				result.put("success", "false");
				result.put("message", e.getMessage());
				result.put("finalMap", new HashMap<String,List<Map<String,String>>>());
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 根据SQL获得指定数据库连接中的记录
	 * @param conn
	 * @param sql
	 * @return List<Map<String,String>>
	 * @throws Exception
	 */
	public Map<String, Object> getRecordsBySQL(TDatabase conn, String sql,String column) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,List<Map<String,String>>> finalMap = new HashMap<String,List<Map<String,String>>>();
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		List<Object[]> list = new ArrayList<Object[]>();
		if (null==conn){
			result.put("success", "false");
			result.put("message", "数据源为空，请重试！");
			result.put("finalMap", finalMap);
			return result;
		}else {
			try {
				KettleEnvironment.init();
				TransMeta transMeta = new TransMeta();
				DatabaseMeta dm = new DatabaseMeta(conn.getConnectionName(),
						conn.getConnectionDB(), DatabaseMeta.dbAccessTypeCode[0],
						conn.getConnectionServerName(),
						conn.getConnectionDbName(), conn.getConnectionPort(),
						conn.getConnectionDBUserName(),
						conn.getConnectionDBPasswordBak());
				List<DatabaseMeta> dmList = new ArrayList<DatabaseMeta>();
				dmList.add(dm);
				transMeta.setDatabases(dmList);
				Database db = new Database(transMeta, dm);
				db.connect();
				list = db.getRows(sql, 5);
				Map<String,String> m = null;
				String[] col = column.split(",");
				for(int i=0;i<list.size();i++){
					Object[] o = list.get(i);
					m = new LinkedHashMap<String,String>();
					for(int j=0;j<col.length;j++){
						String t = col[j];
						if(o[j]==null){
							m.put(t, "");
						}else{
							m.put(t, o[j].toString());
							
						}
					}
					dataList.add(m);
				}
				finalMap.put("rows", dataList);
				db.disconnect();
				result.put("success", "true");
				result.put("message", "查询数据成功！");
				result.put("finalMap", finalMap);
			} catch (KettleException e) {
				e.printStackTrace();
				result.put("success", "false");
				result.put("message", e.getMessage());
				result.put("finalMap", new HashMap<String,List<Map<String,String>>>());
				return result;
			}
		}
		return result;
	}

}
