package com.integrity.dataSmart.dataImport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.BaseConfiguration;

import com.integrity.dataSmart.pojo.TianmaoData;
import com.integrity.dataSmart.util.DateFormat;
import com.integrity.dataSmart.util.WritelogContents;
import com.integrity.dataSmart.util.solr.Email2SolrUtil;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class ImportTianMaoData {
	public static void main(String[] args) {

		BaseConfiguration baseConfiguration = new BaseConfiguration();
		baseConfiguration.setProperty("storage.backend", "hbase");
		baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		baseConfiguration.setProperty("storage.tablename", "titan");
		System.out.println("Open Data");
		TitanGraph graph = TitanFactory.open(baseConfiguration);
		System.out.println("数据库已连接");

		/********************** 数据入库 *******************************/
		long st = System.currentTimeMillis();
		String startTime = DateFormat.transferLongToDate(st);
		System.out.println("开始时间：" + startTime);
		int count = 0;
		int rows = 50000;// 377703397    16042
		for (int i = 235350000; i < 377703397; i += rows) {
			long start = System.currentTimeMillis();
			String startDate = DateFormat.transferLongToDate(start);
			WritelogContents.writeLogs(
					"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
					startDate+"  正在入库：从" + i + "到 "+(i+rows)+";");
			long s = System.currentTimeMillis();
			int num=0;
			try {
				num = inertdata(graph, i, rows);
			} catch (Exception e1) {
				WritelogContents.writeLogs(
						"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
						"本次入库 异常：" + e1.getMessage());
				e1.printStackTrace();
			}
			count += num;
			long e = System.currentTimeMillis();
			String eDate = DateFormat.transferLongToDate(e);
			WritelogContents.writeLogs(
					"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
					eDate + "  本次入库 "+num+" 条，时间："+(e - s)/ 1000 + "秒"+"-->已入库数据：" + count + " 条");
		}
		long el = System.currentTimeMillis();
		String endTime = DateFormat.transferLongToDate(el);
		System.out.println("结束时间：" + endTime);
		WritelogContents.writeLogs(
				"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
				"结束时间：" + endTime);
		long longs = el - st;
		System.out.println("录入数量:" + count + "个；总用时：" + longs / 1000 + "秒");
		WritelogContents.writeLogs(
				"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
				"录入数量:" + count + "个；总用时：" + longs / 1000 + "秒");
		graph.shutdown();
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	public static int inertdata(TitanGraph graph, int start, int rows) {
		int count = 0;
		long s = System.currentTimeMillis();
		List<TianmaoData> datas = Email2SolrUtil.getTmDataFromSolr("lb_info",start, rows);
		long e = System.currentTimeMillis();
		String endTime = DateFormat.transferLongToDate(e);
		WritelogContents.writeLogs(
				"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
				endTime+"  本次查询出 " + datas.size() + " 条数据;用时："+(e-s)/1000+"秒。");
		for (int d = 0; d < datas.size(); d++) {
			//电话、姓名、QQ、地址、Email、用户名、密码
			TianmaoData data = datas.get(d);
			String address = data.getAddress();
			String domain = data.getDomain();
			String email = data.getEmail();
			String name = data.getName();
			String password = data.getPassword();
			String phonenum = data.getPhone();
			String numid = data.getQq();
			String username = data.getUsername();
			
			if (isBank(username) && isBank(email) && isBank(numid) && isBank(phonenum)) {
				System.out.println("跳过，数据属性为空："+data.toString());
//				WritelogContents.writeLogs(
//						"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
//						"跳过，数据属性为空："+data.toString());
				continue;
			}
			count++;
			Vertex accountObj = null;
			Vertex emailObj = null;
			Vertex qqObj = null;
			Vertex phoneObj = null;
			
			List<Vertex> person_accountObjs = new ArrayList<Vertex>();
			List<Vertex> person_emailObjs = new ArrayList<Vertex>();
			List<Vertex> person_qqObjs = new ArrayList<Vertex>();
			List<Vertex> person_phoneObjs = new ArrayList<Vertex>();
			//查询账号相关的点
			if (isNotBank(domain) && isNotBank(username)) {
				Iterable<Vertex> it = graph.query().has("username",username).vertices();
				accountObj = setValue(it, accountObj, person_accountObjs,"domain",domain);
//				if (person_accountObjs.size()>1) {
//					WritelogContents.writeLogs(
//							"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
//							"属性"+username+" | "+domain+"  own出的人共 " + person_accountObjs.size() + " 条");
//				}
			}
			if (isNotBank(email)) {
				Iterable<Vertex> it = graph.query().has("email",email).vertices();
				emailObj = setValue(it, emailObj, person_emailObjs,"type","Email");
//				if (person_emailObjs.size()>1) {
//					WritelogContents.writeLogs(
//							"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
//							"属性"+email+" |  Email own出的人共 " + person_emailObjs.size() + " 条");
//				}
			}
			if (isNotBank(numid)) {
				Iterable<Vertex> it = graph.query().has("numid",numid).vertices();
				qqObj = setValue(it, qqObj, person_qqObjs,"type","IM");
//				if (person_qqObjs.size()>1) {
//					WritelogContents.writeLogs(
//							"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
//							"属性"+numid+" |  IM own出的人共 " + person_qqObjs.size() + " 条");
//				}
			}
			if (isNotBank(phonenum)) {
				Iterable<Vertex> it = graph.query().has("phonenum",phonenum).vertices();
				phoneObj = setValue(it, phoneObj, person_phoneObjs,"type","Phone");
//				if (person_phoneObjs.size()>1) {
//					WritelogContents.writeLogs(
//							"C:\\Users\\cs\\Desktop\\setkey\\2log.txt",
//							"属性"+phonenum+" |  Phone own出的人共 " + person_phoneObjs.size() + " 条");
//				}
			}
			
			//处理账号相关的点
			if (listIsBank(person_accountObjs)) {//账号没有
				if (listIsBank(person_emailObjs)) {//邮箱没有
					if (listIsBank(person_qqObjs)) {//qq没有
						if (listIsBank(person_phoneObjs)) {//账号没有  邮箱没有   qq没有   电话没有
							//添加账号
							Vertex accountV = null;
							accountV = addAccount(graph, accountV, username, domain, phonenum, email, password);
							
							//添加邮箱
							Vertex emailV = null;
							emailV = addEmail(graph, emailV, email);
							
							//添加qq
							Vertex qqV = null;
							qqV = addQQ(graph, qqV, numid);
							
							//添加电话
							Vertex phoneV = null;
							phoneV = addPhone(graph, phoneV, phonenum);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//添加人
							Vertex personV = null;
							personV = addPerson(graph, personV, name);
							
							//添加关系
							if (isNotNull(accountV)) graph.addEdge(null, personV, accountV, "own");
							if (isNotNull(emailV)) graph.addEdge(null, personV, emailV, "own");
							if (isNotNull(qqV)) graph.addEdge(null, personV, qqV, "own");
							if (isNotNull(phoneV)) graph.addEdge(null, personV, phoneV, "own");
							if (isNotNull(locationV)) graph.addEdge(null, personV, locationV, "own");
						}else {//账号没有  邮箱没有   qq没有   电话有
							//添加账号
							Vertex accountV = null;
							accountV = addAccount(graph, accountV, username, domain, phonenum, email, password);
							
							//添加邮箱
							Vertex emailV = null;
							emailV = addEmail(graph, emailV, email);
							
							//添加qq
							Vertex qqV = null;
							qqV = addQQ(graph, qqV, numid);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							for (Vertex vertex : person_phoneObjs) {
								//添加关系
								if (isNotNull(accountV)) graph.addEdge(null, vertex, accountV, "own");
								if (isNotNull(emailV)) graph.addEdge(null, vertex, emailV, "own");
								if (isNotNull(qqV)) graph.addEdge(null, vertex, qqV, "own");
								if (isNotNull(locationV)) graph.addEdge(null, vertex, locationV, "own");
							}
						}
					}else {//qq 有
						if (listIsBank(person_phoneObjs)) {//账号没有  邮箱没有   qq有   电话没有
							//添加账号
							Vertex accountV = null;
							accountV = addAccount(graph, accountV, username, domain, phonenum, email, password);
							
							//添加邮箱
							Vertex emailV = null;
							emailV = addEmail(graph, emailV, email);
							
							//添加电话
							Vertex phoneV = null;
							phoneV = addPhone(graph, phoneV, phonenum);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							for (Vertex vertex : person_qqObjs) {
								//添加关系
								if (isNotNull(accountV)) graph.addEdge(null, vertex, accountV, "own");
								if (isNotNull(emailV)) graph.addEdge(null, vertex, emailV, "own");
								if (isNotNull(phoneV)) graph.addEdge(null, vertex, phoneV, "own");
								if (isNotNull(locationV)) graph.addEdge(null, vertex, locationV, "own");
							}
						}else {//账号没有  邮箱没有   qq有   电话有
							//添加账号
							Vertex accountV = null;
							accountV = addAccount(graph, accountV, username, domain, phonenum, email, password);
							
							//添加邮箱
							Vertex emailV = null;
							emailV = addEmail(graph, emailV, email);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_qqV : person_qqObjs) {
								long id = Long.parseLong(person_qqV.getId()+"");
								List<Long> person_qqV_owns = new ArrayList<Long>();
								ownMap.put(id, person_qqV_owns);
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(accountV) && notContains(person_phoneV_owns, accountV.getId())){
										graph.addEdge(null, person_phoneV, accountV, "own");
									}
									if (isNotNull(emailV) && notContains(person_phoneV_owns, emailV.getId())) {
										graph.addEdge(null, person_phoneV, emailV, "own");
									}
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(accountV) && notContains(person_qqV_owns, accountV.getId())){
											graph.addEdge(null, person_qqV, accountV, "own");
										}
										if (isNotNull(emailV) && notContains(person_qqV_owns, emailV.getId())){ 
											graph.addEdge(null, person_qqV, emailV, "own");
										}
										if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())){
											graph.addEdge(null, person_qqV, locationV, "own");
										}
										
										if (notContains(person_qqV_owns, phoneObj.getId())){
											graph.addEdge(null, person_qqV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, qqObj.getId())){
											graph.addEdge(null, person_phoneV, qqObj, "own");
										}
									}
								}
							}
						}
					}
				}else {//邮箱有
					if (listIsBank(person_qqObjs)) {//qq没有
						if (listIsBank(person_phoneObjs)) {//账号没有  邮箱有   qq没有   电话没有
							//添加账号
							Vertex accountV = null;
							accountV = addAccount(graph, accountV, username, domain, phonenum, email, password);
							
							//添加qq
							Vertex qqV = null;
							qqV = addQQ(graph, qqV, numid);
							
							//添加电话
							Vertex phoneV = null;
							phoneV = addPhone(graph, phoneV, phonenum);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//添加关系
							for (Vertex personV : person_emailObjs) {
								if (isNotNull(accountV)) graph.addEdge(null, personV, accountV, "own");
								if (isNotNull(qqV)) graph.addEdge(null, personV, qqV, "own");
								if (isNotNull(phoneV)) graph.addEdge(null, personV, phoneV, "own");
								if (isNotNull(locationV)) graph.addEdge(null, personV, locationV, "own");
							}
						}else {//账号没有  邮箱有   qq没有   电话有
							//添加账号
							Vertex accountV = null;
							accountV = addAccount(graph, accountV, username, domain, phonenum, email, password);
							
							//添加qq
							Vertex qqV = null;
							qqV = addQQ(graph, qqV, numid);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_emailV : person_emailObjs) {
								long id = Long.parseLong(person_emailV.getId()+"");
								List<Long> person_emailV_owns = new ArrayList<Long>();
								ownMap.put(id, person_emailV_owns);
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(accountV) && notContains(person_phoneV_owns, accountV.getId())){
										graph.addEdge(null, person_phoneV, accountV, "own");
									}
									if (isNotNull(qqV) && notContains(person_phoneV_owns, qqV.getId())) {
										graph.addEdge(null, person_phoneV, qqV, "own");
									}
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(accountV) && notContains(person_emailV_owns, accountV.getId())){
											graph.addEdge(null, person_emailV, accountV, "own");
										}
										if (isNotNull(qqV) && notContains(person_emailV_owns, qqV.getId())){ 
											graph.addEdge(null, person_emailV, qqV, "own");
										}
										if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())){
											graph.addEdge(null, person_emailV, locationV, "own");
										}
										
										if (notContains(person_emailV_owns, phoneObj.getId())){
											graph.addEdge(null, person_emailV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, emailObj.getId())){
											graph.addEdge(null, person_phoneV, emailObj, "own");
										}
										
										//交叉关联
										/*Vertex accountObj = null;
										Vertex emailObj = null;
										Vertex qqObj = null;
										Vertex phoneObj = null;*/
									}
								}
							}
						}
					}else {//qq有
						if (listIsBank(person_phoneObjs)) {//账号没有  邮箱有   qq有   电话没有
							//添加账号
							Vertex accountV = null;
							accountV = addAccount(graph, accountV, username, domain, phonenum, email, password);
							
							//添加电话
							Vertex phoneV = null;
							phoneV = addPhone(graph, phoneV, phonenum);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_emailV : person_emailObjs) {
								long id = Long.parseLong(person_emailV.getId()+"");
								List<Long> person_emailV_owns = new ArrayList<Long>();
								ownMap.put(id, person_emailV_owns);
								for (Vertex person_qqV : person_qqObjs) {
									long person_qq_id = Long.parseLong(person_qqV.getId()+"");
									List<Long> person_qqV_owns = ownMap.get(person_qq_id);
									if (person_qqV_owns == null) {
										person_qqV_owns = new ArrayList<Long>();
										ownMap.put(person_qq_id, person_qqV_owns);
									}
									
									//添加关系
									if (isNotNull(accountV) && notContains(person_qqV_owns, accountV.getId())){
										graph.addEdge(null, person_qqV, accountV, "own");
									}
									if (isNotNull(phoneV) && notContains(person_qqV_owns, phoneV.getId())) {
										graph.addEdge(null, person_qqV, phoneV, "own");
									}
									if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())) {
										graph.addEdge(null, person_qqV, locationV, "own");
									}
									
									if (id == person_qq_id) {
									}else {
										//添加关系
										if (isNotNull(accountV) && notContains(person_emailV_owns, accountV.getId())){
											graph.addEdge(null, person_emailV, accountV, "own");
										}
										if (isNotNull(phoneV) && notContains(person_emailV_owns, phoneV.getId())){ 
											graph.addEdge(null, person_emailV, phoneV, "own");
										}
										if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())){
											graph.addEdge(null, person_emailV, locationV, "own");
										}
										
										if (notContains(person_emailV_owns, qqObj.getId())){
											graph.addEdge(null, person_emailV, qqObj, "own");
										}
										if (notContains(person_qqV_owns, emailObj.getId())){
											graph.addEdge(null, person_qqV, emailObj, "own");
										}
									}
								}
							}
						}else {//账号没有  邮箱有   qq有   电话有
							//添加账号
							Vertex accountV = null;
							accountV = addAccount(graph, accountV, username, domain, phonenum, email, password);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_emailV : person_emailObjs) {
								long id = Long.parseLong(person_emailV.getId()+"");
								List<Long> person_emailV_owns = new ArrayList<Long>();
								ownMap.put(id, person_emailV_owns);
								//邮箱、qq交叉关联
								for (Vertex person_qqV : person_qqObjs) {
									long person_qq_id = Long.parseLong(person_qqV.getId()+"");
									List<Long> person_qqV_owns = ownMap.get(person_qq_id);
									if (person_qqV_owns == null) {
										person_qqV_owns = new ArrayList<Long>();
										ownMap.put(person_qq_id, person_qqV_owns);
									}
									
									//添加关系
									if (isNotNull(accountV) && notContains(person_qqV_owns, accountV.getId())){
										graph.addEdge(null, person_qqV, accountV, "own");
									}
									if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())) {
										graph.addEdge(null, person_qqV, locationV, "own");
									}
									
									if (id == person_qq_id) {
									}else {
										//添加关系
										if (isNotNull(accountV) && notContains(person_emailV_owns, accountV.getId())){
											graph.addEdge(null, person_emailV, accountV, "own");
										}
										if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())){
											graph.addEdge(null, person_emailV, locationV, "own");
										}
										
										if (notContains(person_emailV_owns, qqObj.getId())){
											graph.addEdge(null, person_emailV, qqObj, "own");
										}
										if (notContains(person_qqV_owns, emailObj.getId())){
											graph.addEdge(null, person_qqV, emailObj, "own");
										}
									}
								}
								
								//邮箱、电话交叉关联
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(accountV) && notContains(person_phoneV_owns, accountV.getId())){
										graph.addEdge(null, person_phoneV, accountV, "own");
									}
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(accountV) && notContains(person_emailV_owns, accountV.getId())){
											graph.addEdge(null, person_emailV, accountV, "own");
										}
										if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())){
											graph.addEdge(null, person_emailV, locationV, "own");
										}
										
										if (notContains(person_emailV_owns, phoneObj.getId())){
											graph.addEdge(null, person_emailV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, emailObj.getId())){
											graph.addEdge(null, person_phoneV, emailObj, "own");
										}
									}
								}
							}
							
							//qq、电话交叉关联
							for (Vertex person_qqV : person_qqObjs) {
								long id = Long.parseLong(person_qqV.getId()+"");
								List<Long> person_qqV_owns = ownMap.get(id);
								if (person_qqV_owns == null) {
									person_qqV_owns = new ArrayList<Long>();
									ownMap.put(id, person_qqV_owns);
								}
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(accountV) && notContains(person_phoneV_owns, accountV.getId())){
										graph.addEdge(null, person_phoneV, accountV, "own");
									}
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(accountV) && notContains(person_qqV_owns, accountV.getId())){
											graph.addEdge(null, person_qqV, accountV, "own");
										}
										if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())){
											graph.addEdge(null, person_qqV, locationV, "own");
										}
										
										if (notContains(person_qqV_owns, phoneObj.getId())){
											graph.addEdge(null, person_qqV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, qqObj.getId())){
											graph.addEdge(null, person_phoneV, qqObj, "own");
										}
									}
								}
							}
						}
					}
				}
			}else {//账号有
				if (listIsBank(person_emailObjs)) {//邮箱没有
					if (listIsBank(person_qqObjs)) {//qq没有
						if (listIsBank(person_phoneObjs)) {//账号有  邮箱没有   qq没有   电话没有
							//添加邮箱
							Vertex emailV = null;
							emailV = addEmail(graph, emailV, email);
							
							//添加qq
							Vertex qqV = null;
							qqV = addQQ(graph, qqV, numid);
							
							//添加电话
							Vertex phoneV = null;
							phoneV = addPhone(graph, phoneV, phonenum);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							for (Vertex vertex : person_accountObjs) {
								//添加关系
								if (isNotNull(emailV)) graph.addEdge(null, vertex, emailV, "own");
								if (isNotNull(qqV)) graph.addEdge(null, vertex, qqV, "own");
								if (isNotNull(phoneV)) graph.addEdge(null, vertex, phoneV, "own");
								if (isNotNull(locationV)) graph.addEdge(null, vertex, locationV, "own");
							}
						}else {//账号有  邮箱没有   qq没有   电话有
							//添加邮箱
							Vertex emailV = null;
							emailV = addEmail(graph, emailV, email);
							
							//添加qq
							Vertex qqV = null;
							qqV = addQQ(graph, qqV, numid);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_accountV : person_accountObjs) {
								long id = Long.parseLong(person_accountV.getId()+"");
								List<Long> person_accountV_owns = new ArrayList<Long>();
								ownMap.put(id, person_accountV_owns);
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(emailV) && notContains(person_phoneV_owns, emailV.getId())){
										graph.addEdge(null, person_phoneV, emailV, "own");
									}
									if (isNotNull(qqV) && notContains(person_phoneV_owns, qqV.getId())) {
										graph.addEdge(null, person_phoneV, qqV, "own");
									}
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(emailV) && notContains(person_accountV_owns, emailV.getId())){
											graph.addEdge(null, person_accountV, emailV, "own");
										}
										if (isNotNull(qqV) && notContains(person_accountV_owns, qqV.getId())){ 
											graph.addEdge(null, person_accountV, qqV, "own");
										}
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, phoneObj.getId())){
											graph.addEdge(null, person_accountV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, accountObj.getId())){
											graph.addEdge(null, person_phoneV, accountObj, "own");
										}
									}
								}
							}
						}
					}else {//qq有
						if (listIsBank(person_phoneObjs)) {//账号有  邮箱没有   qq有   电话没有
							//添加邮箱
							Vertex emailV = null;
							emailV = addEmail(graph, emailV, email);
							
							//添加电话
							Vertex phoneV = null;
							phoneV = addPhone(graph, phoneV, phonenum);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_accountV : person_accountObjs) {
								long id = Long.parseLong(person_accountV.getId()+"");
								List<Long> person_accountV_owns = new ArrayList<Long>();
								ownMap.put(id, person_accountV_owns);
								for (Vertex person_qqV : person_qqObjs) {
									long person_qq_id = Long.parseLong(person_qqV.getId()+"");
									List<Long> person_qqV_owns = ownMap.get(person_qq_id);
									if (person_qqV_owns == null) {
										person_qqV_owns = new ArrayList<Long>();
										ownMap.put(person_qq_id, person_qqV_owns);
									}
									
									//添加关系
									if (isNotNull(emailV) && notContains(person_qqV_owns, emailV.getId())){
										graph.addEdge(null, person_qqV, emailV, "own");
									}
									if (isNotNull(phoneV) && notContains(person_qqV_owns, phoneV.getId())) {
										graph.addEdge(null, person_qqV, phoneV, "own");
									}
									if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())) {
										graph.addEdge(null, person_qqV, locationV, "own");
									}
									
									if (id == person_qq_id) {
									}else {
										//添加关系
										if (isNotNull(emailV) && notContains(person_accountV_owns, emailV.getId())){
											graph.addEdge(null, person_accountV, emailV, "own");
										}
										if (isNotNull(phoneV) && notContains(person_accountV_owns, phoneV.getId())){ 
											graph.addEdge(null, person_accountV, phoneV, "own");
										}
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, qqObj.getId())){
											graph.addEdge(null, person_accountV, qqObj, "own");
										}
										if (notContains(person_qqV_owns, accountObj.getId())){
											graph.addEdge(null, person_qqV, accountObj, "own");
										}
									}
								}
							}
						}else {//账号有  邮箱没有   qq有   电话有
							//添加邮箱
							Vertex emailV = null;
							emailV = addEmail(graph, emailV, email);
							
							//添加qq
							Vertex qqV = null;
							qqV = addQQ(graph, qqV, numid);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_accountV : person_accountObjs) {
								long id = Long.parseLong(person_accountV.getId()+"");
								List<Long> person_accountV_owns = new ArrayList<Long>();
								ownMap.put(id, person_accountV_owns);
								//账号、电话交叉关联
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(emailV) && notContains(person_phoneV_owns, emailV.getId())){
										graph.addEdge(null, person_phoneV, emailV, "own");
									}
									if (isNotNull(qqV) && notContains(person_phoneV_owns, qqV.getId())) {
										graph.addEdge(null, person_phoneV, qqV, "own");
									}
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(emailV) && notContains(person_accountV_owns, emailV.getId())){
											graph.addEdge(null, person_accountV, emailV, "own");
										}
										if (isNotNull(qqV) && notContains(person_accountV_owns, qqV.getId())){ 
											graph.addEdge(null, person_accountV, qqV, "own");
										}
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, phoneObj.getId())){
											graph.addEdge(null, person_accountV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, accountObj.getId())){
											graph.addEdge(null, person_phoneV, accountObj, "own");
										}
									}
								}
								//账号、qq交叉关联
								for (Vertex person_qqV : person_qqObjs) {
									long person_qq_id = Long.parseLong(person_qqV.getId()+"");
									List<Long> person_qqV_owns = ownMap.get(person_qq_id);
									if (person_qqV_owns == null) {
										person_qqV_owns = new ArrayList<Long>();
										ownMap.put(person_qq_id, person_qqV_owns);
									}
									
									//添加关系
									if (isNotNull(emailV) && notContains(person_qqV_owns, emailV.getId())){
										graph.addEdge(null, person_qqV, emailV, "own");
									}
									if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())) {
										graph.addEdge(null, person_qqV, locationV, "own");
									}
									
									if (id == person_qq_id) {
									}else {
										//添加关系
										if (isNotNull(emailV) && notContains(person_accountV_owns, emailV.getId())){
											graph.addEdge(null, person_accountV, emailV, "own");
										}
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, qqObj.getId())){
											graph.addEdge(null, person_accountV, qqObj, "own");
										}
										if (notContains(person_qqV_owns, accountObj.getId())){
											graph.addEdge(null, person_qqV, accountObj, "own");
										}
									}
								}
							}
							//qq、电话交叉关联
							for (Vertex person_qqV : person_qqObjs) {
								long id = Long.parseLong(person_qqV.getId()+"");
								List<Long> person_qqV_owns = ownMap.get(id);
								if (person_qqV_owns == null) {
									person_qqV_owns = new ArrayList<Long>();
									ownMap.put(id, person_qqV_owns);
								}
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(emailV) && notContains(person_phoneV_owns, emailV.getId())) {
										graph.addEdge(null, person_phoneV, emailV, "own");
									}
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(emailV) && notContains(person_qqV_owns, emailV.getId())){ 
											graph.addEdge(null, person_qqV, emailV, "own");
										}
										if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())){
											graph.addEdge(null, person_qqV, locationV, "own");
										}
										
										if (notContains(person_qqV_owns, phoneObj.getId())){
											graph.addEdge(null, person_qqV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, qqObj.getId())){
											graph.addEdge(null, person_phoneV, qqObj, "own");
										}
									}
								}
							}
						}
					}
				}else {//邮箱有
					if (listIsBank(person_qqObjs)) {//qq没有
						if (listIsBank(person_phoneObjs)) {//账号有  邮箱有   qq没有   电话没有
							//添加电话
							Vertex phoneV = null;
							phoneV = addPhone(graph, phoneV, phonenum);
							
							//添加qq
							Vertex qqV = null;
							qqV = addQQ(graph, qqV, numid);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_accountV : person_accountObjs) {
								long id = Long.parseLong(person_accountV.getId()+"");
								List<Long> person_accountV_owns = new ArrayList<Long>();
								ownMap.put(id, person_accountV_owns);
								for (Vertex person_emailV : person_emailObjs) {
									long person_email_id = Long.parseLong(person_emailV.getId()+"");
									List<Long> person_emailV_owns = ownMap.get(person_email_id);
									if (person_emailV_owns == null) {
										person_emailV_owns = new ArrayList<Long>();
										ownMap.put(person_email_id, person_emailV_owns);
									}
									
									//添加关系
									if (isNotNull(phoneV) && notContains(person_emailV_owns, phoneV.getId())){
										graph.addEdge(null, person_emailV, phoneV, "own");
									}
									if (isNotNull(qqV) && notContains(person_emailV_owns, qqV.getId())) {
										graph.addEdge(null, person_emailV, qqV, "own");
									}
									if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())) {
										graph.addEdge(null, person_emailV, locationV, "own");
									}
									
									if (id == person_email_id) {
									}else {
										//添加关系
										if (isNotNull(phoneV) && notContains(person_accountV_owns, phoneV.getId())){
											graph.addEdge(null, person_accountV, phoneV, "own");
										}
										if (isNotNull(qqV) && notContains(person_accountV_owns, qqV.getId())){ 
											graph.addEdge(null, person_accountV, qqV, "own");
										}
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, emailObj.getId())){
											graph.addEdge(null, person_accountV, emailObj, "own");
										}
										if (notContains(person_emailV_owns, accountObj.getId())){
											graph.addEdge(null, person_emailV, accountObj, "own");
										}
									}
								}
							}
						}else {//账号有  邮箱有   qq没有   电话有
							//添加qq
							Vertex qqV = null;
							qqV = addQQ(graph, qqV, numid);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_accountV : person_accountObjs) {
								long id = Long.parseLong(person_accountV.getId()+"");
								List<Long> person_accountV_owns = new ArrayList<Long>();
								ownMap.put(id, person_accountV_owns);
								//账号、邮箱交叉关联
								for (Vertex person_emailV : person_emailObjs) {
									long person_email_id = Long.parseLong(person_emailV.getId()+"");
									List<Long> person_emailV_owns = ownMap.get(person_email_id);
									if (person_emailV_owns == null) {
										person_emailV_owns = new ArrayList<Long>();
										ownMap.put(person_email_id, person_emailV_owns);
									}
									
									//添加关系
									if (isNotNull(qqV) && notContains(person_emailV_owns, qqV.getId())) {
										graph.addEdge(null, person_emailV, qqV, "own");
									}
									if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())) {
										graph.addEdge(null, person_emailV, locationV, "own");
									}
									
									if (id == person_email_id) {
									}else {
										//添加关系
										if (isNotNull(qqV) && notContains(person_accountV_owns, qqV.getId())){ 
											graph.addEdge(null, person_accountV, qqV, "own");
										}
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, emailObj.getId())){
											graph.addEdge(null, person_accountV, emailObj, "own");
										}
										if (notContains(person_emailV_owns, accountObj.getId())){
											graph.addEdge(null, person_emailV, accountObj, "own");
										}
									}
								}
								//账号、电话交叉关联
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(qqV) && notContains(person_phoneV_owns, qqV.getId())) {
										graph.addEdge(null, person_phoneV, qqV, "own");
									}
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(qqV) && notContains(person_accountV_owns, qqV.getId())){ 
											graph.addEdge(null, person_accountV, qqV, "own");
										}
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, phoneObj.getId())){
											graph.addEdge(null, person_accountV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, accountObj.getId())){
											graph.addEdge(null, person_phoneV, accountObj, "own");
										}
									}
								}
							}
							
							//邮箱、电话交叉关联
							for (Vertex person_emailV : person_emailObjs) {
								long id = Long.parseLong(person_emailV.getId()+"");
								List<Long> person_emailV_owns = ownMap.get(id);
								if (person_emailV_owns == null) {
									person_emailV_owns = new ArrayList<Long>();
									ownMap.put(id, person_emailV_owns);
								}
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(qqV) && notContains(person_phoneV_owns, qqV.getId())) {
										graph.addEdge(null, person_phoneV, qqV, "own");
									}
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(qqV) && notContains(person_emailV_owns, qqV.getId())){ 
											graph.addEdge(null, person_emailV, qqV, "own");
										}
										if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())){
											graph.addEdge(null, person_emailV, locationV, "own");
										}
										
										if (notContains(person_emailV_owns, phoneObj.getId())){
											graph.addEdge(null, person_emailV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, emailObj.getId())){
											graph.addEdge(null, person_phoneV, emailObj, "own");
										}
									}
								}
							}
						}
					}else {//qq有
						if (listIsBank(person_phoneObjs)) {//账号有  邮箱有   qq有   电话没有
							//添加电话
							Vertex phoneV = null;
							phoneV = addPhone(graph, phoneV, phonenum);
							
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_accountV : person_accountObjs) {
								long id = Long.parseLong(person_accountV.getId()+"");
								List<Long> person_accountV_owns = new ArrayList<Long>();
								ownMap.put(id, person_accountV_owns);
								//账号、邮箱交叉关联
								for (Vertex person_emailV : person_emailObjs) {
									long person_email_id = Long.parseLong(person_emailV.getId()+"");
									List<Long> person_emailV_owns = ownMap.get(person_email_id);
									if (person_emailV_owns == null) {
										person_emailV_owns = new ArrayList<Long>();
										ownMap.put(person_email_id, person_emailV_owns);
									}
									
									//添加关系
									if (isNotNull(phoneV) && notContains(person_emailV_owns, phoneV.getId())){
										graph.addEdge(null, person_emailV, phoneV, "own");
									}
									if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())) {
										graph.addEdge(null, person_emailV, locationV, "own");
									}
									
									if (id == person_email_id) {
									}else {
										//添加关系
										if (isNotNull(phoneV) && notContains(person_accountV_owns, phoneV.getId())){
											graph.addEdge(null, person_accountV, phoneV, "own");
										}
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, emailObj.getId())){
											graph.addEdge(null, person_accountV, emailObj, "own");
										}
										if (notContains(person_emailV_owns, accountObj.getId())){
											graph.addEdge(null, person_emailV, accountObj, "own");
										}
									}
								}
								//账号、qq交叉关联
								for (Vertex person_qqV : person_qqObjs) {
									long person_qq_id = Long.parseLong(person_qqV.getId()+"");
									List<Long> person_qqV_owns = ownMap.get(person_qq_id);
									if (person_qqV_owns == null) {
										person_qqV_owns = new ArrayList<Long>();
										ownMap.put(person_qq_id, person_qqV_owns);
									}
									
									//添加关系
									if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())) {
										graph.addEdge(null, person_qqV, locationV, "own");
									}
									
									if (id == person_qq_id) {
									}else {
										//添加关系
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, qqObj.getId())){
											graph.addEdge(null, person_accountV, qqObj, "own");
										}
										if (notContains(person_qqV_owns, accountObj.getId())){
											graph.addEdge(null, person_qqV, accountObj, "own");
										}
									}
								}
							}
							//邮箱、qq交叉关联
							for (Vertex person_emailV : person_emailObjs) {
								long id = Long.parseLong(person_emailV.getId()+"");
								List<Long> person_emailV_owns = ownMap.get(id);
								if (person_emailV_owns == null) {
									person_emailV_owns = new ArrayList<Long>();
									ownMap.put(id, person_emailV_owns);
								}
								for (Vertex person_qqV : person_qqObjs) {
									long person_qq_id = Long.parseLong(person_qqV.getId()+"");
									List<Long> person_qqV_owns = ownMap.get(person_qq_id);
									if (person_qqV_owns == null) {
										person_qqV_owns = new ArrayList<Long>();
										ownMap.put(person_qq_id, person_qqV_owns);
									}
									
									//添加关系
									if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())) {
										graph.addEdge(null, person_qqV, locationV, "own");
									}
									
									if (id == person_qq_id) {
									}else {
										//添加关系
										if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())){
											graph.addEdge(null, person_emailV, locationV, "own");
										}
										
										if (notContains(person_emailV_owns, qqObj.getId())){
											graph.addEdge(null, person_emailV, qqObj, "own");
										}
										if (notContains(person_qqV_owns, emailObj.getId())){
											graph.addEdge(null, person_qqV, emailObj, "own");
										}
									}
								}
							}
						}else {//账号有  邮箱有   qq有   电话有
							//添加地址
							Vertex locationV = null;
							locationV = addLocation(graph, locationV, address);
							
							//防止重复添加关系的临时缓存
							Map<Long,List<Long>> ownMap = new HashMap<>();
							
							for (Vertex person_accountV : person_accountObjs) {
								long id = Long.parseLong(person_accountV.getId()+"");
								List<Long> person_accountV_owns = new ArrayList<Long>();
								ownMap.put(id, person_accountV_owns);
								//账号、邮箱交叉关联
								for (Vertex person_emailV : person_emailObjs) {
									long person_email_id = Long.parseLong(person_emailV.getId()+"");
									List<Long> person_emailV_owns = ownMap.get(person_email_id);
									if (person_emailV_owns == null) {
										person_emailV_owns = new ArrayList<Long>();
										ownMap.put(person_email_id, person_emailV_owns);
									}
									
									//添加关系
									if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())) {
										graph.addEdge(null, person_emailV, locationV, "own");
									}
									
									if (id == person_email_id) {
									}else {
										//添加关系
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, emailObj.getId())){
											graph.addEdge(null, person_accountV, emailObj, "own");
										}
										if (notContains(person_emailV_owns, accountObj.getId())){
											graph.addEdge(null, person_emailV, accountObj, "own");
										}
									}
								}
								//账号、qq交叉关联
								for (Vertex person_qqV : person_qqObjs) {
									long person_qq_id = Long.parseLong(person_qqV.getId()+"");
									List<Long> person_qqV_owns = ownMap.get(person_qq_id);
									if (person_qqV_owns == null) {
										person_qqV_owns = new ArrayList<Long>();
										ownMap.put(person_qq_id, person_qqV_owns);
									}
									
									//添加关系
									if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())) {
										graph.addEdge(null, person_qqV, locationV, "own");
									}
									
									if (id == person_qq_id) {
									}else {
										//添加关系
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, qqObj.getId())){
											graph.addEdge(null, person_accountV, qqObj, "own");
										}
										if (notContains(person_qqV_owns, accountObj.getId())){
											graph.addEdge(null, person_qqV, accountObj, "own");
										}
									}
								}
								//账号、电话交叉关联
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(locationV) && notContains(person_accountV_owns, locationV.getId())){
											graph.addEdge(null, person_accountV, locationV, "own");
										}
										
										if (notContains(person_accountV_owns, phoneObj.getId())){
											graph.addEdge(null, person_accountV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, accountObj.getId())){
											graph.addEdge(null, person_phoneV, accountObj, "own");
										}
									}
								}
							}
							//邮箱跟qq和电话交叉关联
							for (Vertex person_emailV : person_emailObjs) {
								long id = Long.parseLong(person_emailV.getId()+"");
								List<Long> person_emailV_owns = ownMap.get(id);
								if (person_emailV_owns == null) {
									person_emailV_owns = new ArrayList<Long>();
									ownMap.put(id, person_emailV_owns);
								}
								//邮箱、qq交叉关联
								for (Vertex person_qqV : person_qqObjs) {
									long person_qq_id = Long.parseLong(person_qqV.getId()+"");
									List<Long> person_qqV_owns = ownMap.get(person_qq_id);
									if (person_qqV_owns == null) {
										person_qqV_owns = new ArrayList<Long>();
										ownMap.put(person_qq_id, person_qqV_owns);
									}
									
									//添加关系
									if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())) {
										graph.addEdge(null, person_qqV, locationV, "own");
									}
									
									if (id == person_qq_id) {
									}else {
										//添加关系
										if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())){
											graph.addEdge(null, person_emailV, locationV, "own");
										}
										
										if (notContains(person_emailV_owns, qqObj.getId())){
											graph.addEdge(null, person_emailV, qqObj, "own");
										}
										if (notContains(person_qqV_owns, emailObj.getId())){
											graph.addEdge(null, person_qqV, emailObj, "own");
										}
									}
								}
								//邮箱、电话
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(locationV) && notContains(person_emailV_owns, locationV.getId())){
											graph.addEdge(null, person_emailV, locationV, "own");
										}
										
										if (notContains(person_emailV_owns, phoneObj.getId())){
											graph.addEdge(null, person_emailV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, emailObj.getId())){
											graph.addEdge(null, person_phoneV, emailObj, "own");
										}
									}
								}
							}
							//qq、电话交叉关联
							for (Vertex person_qqV : person_qqObjs) {
								long id = Long.parseLong(person_qqV.getId()+"");
								List<Long> person_qqV_owns = ownMap.get(id);
								if (person_qqV_owns == null) {
									person_qqV_owns = new ArrayList<Long>();
									ownMap.put(id, person_qqV_owns);
								}
								for (Vertex person_phoneV : person_phoneObjs) {
									long person_phone_id = Long.parseLong(person_phoneV.getId()+"");
									List<Long> person_phoneV_owns = ownMap.get(person_phone_id);
									if (person_phoneV_owns == null) {
										person_phoneV_owns = new ArrayList<Long>();
										ownMap.put(person_phone_id, person_phoneV_owns);
									}
									
									//添加关系
									if (isNotNull(locationV) && notContains(person_phoneV_owns, locationV.getId())) {
										graph.addEdge(null, person_phoneV, locationV, "own");
									}
									
									if (id == person_phone_id) {
									}else {
										//添加关系
										if (isNotNull(locationV) && notContains(person_qqV_owns, locationV.getId())){
											graph.addEdge(null, person_qqV, locationV, "own");
										}
										
										if (notContains(person_qqV_owns, phoneObj.getId())){
											graph.addEdge(null, person_qqV, phoneObj, "own");
										}
										if (notContains(person_phoneV_owns, qqObj.getId())){
											graph.addEdge(null, person_phoneV, qqObj, "own");
										}
									}
								}
							}
						}
					}
				}
			}
			if((d+1)%100==0){
				graph.commit();
			}
			if (d+1 == datas.size()) {
				graph.commit();
			}
		}
//		graph.commit();
		return count;
	}
	
	/**
	 * 根据own点查询person
	 * @param it
	 * @param obj
	 * @param personList
	 * @param type 
	 * @param value 
	 */
	public static Vertex setValue(Iterable<Vertex> it,Vertex obj,List<Vertex> personList, String type, String value){
		for (Vertex vertex : it) {
			String vtype = vertex.getProperty(type);
			if (value.equals(vtype)) {
				obj = vertex;
				Iterable<Vertex> ownIt = vertex.query().labels("own").vertices();
				for (Vertex preson : ownIt) {
					if (!personList.contains(preson)) {//防止重复添加关联
						personList.add(preson);
					}
						
				}
				break;
			}
		}
		return obj;
	}
	
	/**
	 * 添加 Account
	 * @param graph
	 * @param accountV
	 * @param username
	 * @param domain
	 * @param phonenum
	 * @param email
	 * @param password
	 * @return 
	 */
	public static Vertex addAccount(TitanGraph graph,Vertex accountV,String username,String domain,String phonenum,String email,String password){
		if (isNotBank(username)) {
			accountV = graph.addVertex(null);
			accountV.setProperty("type", "Account");
			accountV.setProperty("username", username);
			if (isNotBank(domain)) accountV.setProperty("domain", domain);
			if (isNotBank(password)) accountV.setProperty("password", password);
			if (isNotBank(phonenum)) accountV.setProperty("phonenum", phonenum);
			if (isNotBank(email)) accountV.setProperty("email", email);
		}
		return accountV;
	}
	
	/**
	 * 添加 Email
	 * @param graph
	 * @param emailV
	 * @param email
	 */
	public static Vertex addEmail(TitanGraph graph,Vertex emailV,String email){
		if (isNotBank(email)){
			emailV = graph.addVertex(null);
			emailV.setProperty("type", "Email");
			emailV.setProperty("email", email);
		}
		return emailV;
	}
	
	/**
	 * 添加 qq
	 * @param graph
	 * @param qqV
	 * @param numid
	 */
	public static Vertex addQQ(TitanGraph graph,Vertex qqV,String numid){
		if (isNotBank(numid)){
			qqV = graph.addVertex(null);
			qqV.setProperty("type", "IM");
			qqV.setProperty("domain", "QQ");
			qqV.setProperty("numid", numid);
		}
		return qqV;
	}
	
	/**
	 * 添加电话
	 * @param graph
	 * @param phoneV
	 * @param phonenum
	 */
	public static Vertex addPhone(TitanGraph graph,Vertex phoneV,String phonenum){
		if (isNotBank(phonenum)) {
			phoneV = graph.addVertex(null);
			phoneV.setProperty("type", "Phone");
			phoneV.setProperty("phonenum", phonenum);
		}
		return phoneV;
	}
	
	/**
	 * 添加 地址
	 * @param graph
	 * @param locationV
	 * @param address
	 */
	public static Vertex addLocation(TitanGraph graph,Vertex locationV,String address){
		if (isNotBank(address)) {
			locationV = graph.addVertex(null);
			locationV.setProperty("type", "Location");
			locationV.setProperty("address", address);
		}
		return locationV;
	}
	
	/**
	 * 添加 人
	 * @param graph
	 * @param personV
	 * @param name
	 */
	public static Vertex addPerson(TitanGraph graph,Vertex personV,String name){
		personV = graph.addVertex(null);
		personV.setProperty("type", "Person");
		if (isNotBank(name)) personV.setProperty("name", name);
		return personV;
	}
	
	/**
	 * 判断list元素个数为0
	 * @return
	 */
	public static boolean listIsBank(List<Vertex> list){
		return list.size()==0;
	}
	
	/**
	 * 判断list元素个数大于0
	 * @return
	 */
	public static boolean listIsNotBank(List<Vertex> list){
		return list.size()>0;
	}
	
	/**
	 * 判断list中不存在 这个id
	 * @return
	 */
	public static boolean notContains(List<Long> list,Object id){
		long _id = Long.parseLong(id+"");
		boolean flag = !list.contains(_id);
		if (flag) {
			list.add(_id);
		}
		return flag; 
	}
	
	/**
	 * 判断字符串不为空
	 * @return
	 */
	public static boolean isNotBank(String str){
		return (str != null && !"".equals(str));
	}
	
	/**
	 * 判断字符串为空
	 * @return
	 */
	public static boolean isBank(String str){
		return (str == null || "".equals(str));
	}
	
	/**
	 * 判断对象为null
	 * @return
	 */
	public static boolean isNotNull(Object o){
		return o != null;
	}
}
