package com.integrity.dataSmart.titanGraph.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.integrity.dataSmart.util.titan.ClientUtil;
import com.tinkerpop.rexster.client.RexsterClient;

public class SearchDetailClientDao {
	private Logger logger = Logger.getLogger(this.getClass());
	private RexsterClient client = ClientUtil.getInstance().getClient();
	
	/**
	 * 根据名称查询节点
	 * @param name
	 * @return
	 */
	public List<Map<String,Object>> getVertexByName(String name){
//		RexsterClient client = null;
		List<Map<String,Object>> result = null;
		List<Map<String,Object>> resultRel = null;
		try {
//			client = RexsterClientFactory.open("192.168.20.174", "titangraph");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("name",name);
			result = client.execute("g.V('name',name)",params);
			
			resultRel = client.execute("g.V('name',name).out('own').map",params);
			result.addAll(resultRel);
			//System.out.println(result.get(0).toString());
			
		} catch (Exception e) {
			logger.error("根据名称查询节点异常",e);
		}finally{
			try {
//				if (client != null) client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 根据已知节点id查询所有关系
	 * @param personId 
	 * @return
	 */
	public List<Map<String, Object>> getEventById(String personId) {
//		RexsterClient client = null;
		List<Map<String,Object>> result = null;
		List<Map<String,Object>> resultRel = null;
		try {
//			client = RexsterClientFactory.open("192.168.20.174", "titangraph");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", personId);
			params.put("label","own");
			String cmd = "g.v(id)";
			result = client.execute(cmd,params);
			
			String cmd2 = "g.v(id).outE.filter{it.label!=label}.inV";
			resultRel = client.execute(cmd2,params);
			
			result.addAll(resultRel);
			
		}  catch (Exception e) {
			logger.error("根据已知节点id查询所有关系异常",e);
		}finally{
			try {
//				if (client != null) client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 根据已知节点id查询所有关系
	 * @param personId 
	 * @param type 
	 * @return
	 */
	public List<Map<String, Object>> getEventByIdType(String personId, String type) {
//		RexsterClient client = null;
		List<Map<String,Object>> result = null;
		List<Map<String,Object>> resultRel = null;
		try {
//			client = RexsterClientFactory.open("192.168.20.174", "titangraph");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", personId);
			params.put("label","own");
			params.put("type",type);
			String cmd = "g.v(id)";
			result = client.execute(cmd,params);
			
			String cmd2 = "g.v(id).outE.filter{it.label!=label}.filter{it.label==type}.inV";
			resultRel = client.execute(cmd2,params);
			
			result.addAll(resultRel);
			
		}  catch (Exception e) {
			logger.error("根据已知节点id查询所有关系异常",e);
		}finally{
			try {
//				if (client != null) client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 根据已知节点id和关系标签查询相关关系(二次查询)
	 * @param id 
	 * @param type
	 * @return
	 */
	public List<Map<String,Object>> searchRelationByIdType(String id, String type){
//		RexsterClient client = null;
		List<Map<String,Object>> result = null;
		try {
//			client = RexsterClientFactory.open("192.168.20.174", "titangraph");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", id);
			params.put("lable", type);
			String cmd = "g.v(id).in(lable)";
			result = client.execute(cmd,params);
		}  catch (Exception e) {
			logger.error("根据已知节点id和关系标签查询相关关系异常",e);
		}finally{
			try {
//				if (client != null) client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
