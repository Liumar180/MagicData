package com.integrity.dataSmart.test.tmailDataSearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.integrity.dataSmart.pojo.TianmaoData;
import com.integrity.dataSmart.util.HbaseUtils.HBaseBasic;
import com.integrity.dataSmart.util.jsonUtil.JsonGetBeanUtil;
import com.integrity.dataSmart.util.solr.Email2SolrUtil;

public class TmailDataSearchByCsv {

	public static HttpSolrServer server = null;
	
	public static String solrUrls = "http://192.168.40.10:8983/solr/lob_2014042815";
	
	public static String hbseTableName = "lb_info";
	
	public static String tableHead = "手机号,姓名,地址,邮箱,用户名";

	
	/**
	 * 获得.csv文件中的电话条件列表
	 * @param csvFileFullPath
	 * @return
	 */
	public List<String> getCsvPhoneConds(String csvFileFullPath) {
		List<String> phoneList = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					csvFileFullPath));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if(null!=line&&!"".equals(line)){
					String item[] = line.split(",");
					if(null!=item&&item.length>0){
						String phoneStr = item[0];
						if(null!=phoneStr&&!"".equals(phoneStr)){
							phoneList.add(phoneStr.trim());
							//System.out.println(item[0]);
						}
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return phoneList;
	}
	
	/**
	 * 获得符合查询条件的数据
	 * @param phoneList
	 * @return
	 */
	public Map<String,List<String>> getTmailDataByPhones(List<String> phoneList){
		Map<String,List<String>> resultMap = new LinkedHashMap<String,List<String>>();
		if(null!=phoneList&&phoneList.size()>0){
			Email2SolrUtil.connections();
			if(server == null){
				server = new HttpSolrServer(solrUrls);
			}
			try{
				int i=0;
				for(String phoneStr:phoneList){
					if(null!=phoneStr&&!"".equals(phoneStr)){
						//查询符合条件的数据
						List<TianmaoData> datas = new ArrayList<TianmaoData>();
						SolrQuery query = new SolrQuery();
					    query.setQuery( "text:"+phoneStr );
					    query.setStart(0);
					    query.setRows(100);
					    QueryResponse rsp = server.query( query );
						SolrDocumentList list = rsp.getResults();
						for (SolrDocument solrDocument : list) {
							Object object = solrDocument.getFirstValue("data");
							TianmaoData tianmaoData=JsonGetBeanUtil.getTianmaoByJson(object.toString(),false);
							if (tianmaoData == null) continue;
							String rowid = tianmaoData.getInfo_rowid();//数据源id
							String domain = HBaseBasic.selectByRowKey(hbseTableName, rowid);
							if(domain != null && !domain.equals("")){
								tianmaoData.setDomain(domain);
							}
							String tmailPhoneStr = tianmaoData.getPhone();
							if(null!=tmailPhoneStr&&tmailPhoneStr.trim().equals(phoneStr.trim())){
								datas.add(tianmaoData);
							}
						}
						List<String> subTmailData = new ArrayList<String>();
						if(datas.size()>0){
							List<String> nameList =  new ArrayList<String>();
							List<String> qqList = new ArrayList<String>();
							List<String> addressList = new ArrayList<String>();
							List<String> emailList = new ArrayList<String>();
							List<String> usernameList = new ArrayList<String>();
							List<String> pwdList = new ArrayList<String>();
							for(TianmaoData tempData : datas){
								String name = tempData.getName();
								if(null!= name&&!"".equals(name.trim())&&!nameList.contains(name.trim())){
									nameList.add(name.trim());
								}
								/*String qq = tempData.getQq();
								if(null!= qq&&!"".equals(qq.trim())&&!qqList.contains(qq.trim())){
									qqList.add(qq.trim());
								}*/
								String address =  tempData.getAddress();
								if(null!= address&&!"".equals(address.trim())&&!addressList.contains(address.trim())){
									addressList.add(address.trim());
								}
								String email = tempData.getEmail();
								if(null!= email&&!"".equals(email.trim())&&!emailList.contains(email.trim())){
									emailList.add(email.trim());
								}
								String username = tempData.getUsername();
								if(null!= username&&!"".equals(username.trim())&&!usernameList.contains(username.trim())){
									usernameList.add(username.trim());
								}
								/*String pwd = tempData.getPassword();
								if(null!= pwd&&!"".equals(pwd.trim())&&!pwdList.contains(pwd.trim())){
									pwdList.add(pwd.trim());
								}*/
							}
							String tempStr = getListValue(nameList) + ","
				                      // + getListValue(qqList) + ","
				                       + getListValue(addressList) + ","
				                       + getListValue(emailList) + ","
				                       + getListValue(usernameList);// + ","
				                      // + getListValue(pwdList);
							subTmailData.add(tempStr);
							/*
							for(TianmaoData tempData : datas){
								String name =  tempData.getName();
								String qq = tempData.getQq();
								String address = tempData.getAddress();
								String email = tempData.getEmail();
								String username = tempData.getUsername();
								String pwd = tempData.getPassword();
								if(!((null==name||"".equals(name.trim()))
										&&(null==qq||"".equals(qq.trim()))
										&&(null==address||"".equals(address.trim()))
										&&(null==email||"".equals(email.trim()))
										&&(null==username||"".equals(username.trim()))
										&&(null==pwd||"".equals(pwd.trim())))){
									String tempStr = getBlankValue(name) + ","
						                       + getBlankValue(qq) + ","
						                       + getBlankValue(address) + ","
						                       + getBlankValue(email) + ","
						                       + getBlankValue(username) + ","
						                       +getBlankValue(pwd);
									subTmailData.add(tempStr);
								}
							}
							*/
						}
						i++;
						if(i%1000==0){
							System.out.println("已处理"+i+"条");
						}	
						resultMap.put(phoneStr, subTmailData);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				Email2SolrUtil.close();
			}
		}
		return resultMap;  
	}
	
	private String getBlankValue(String temp){
		if(null==temp||"".equals(temp.trim())){
			return "";
		}else{
			return temp;
		}
	}
	
	private String getListValue(List<String> strList){
		if(null!=strList&&strList.size()>0){
			String resultStr = "";
			if(strList.size()==1){
				return strList.get(0);
			}else{
				for(String temp:strList){
					if(null!=temp&&!"".equals(temp)){
						resultStr = resultStr + temp +"; ";
					}
				}
				return resultStr;
			}
		}else{
			return "";
		}
	}
	
	public String createResultCsv(Map<String,List<String>> resultMap,String csvFileFullPath){
		File csvFile = new File(csvFileFullPath);
		String csvFileName = csvFile.getName();
		int fileNameIndex = csvFileName.toUpperCase().indexOf(".CSV");
		String csvResultFile = csvFile.getParent() + csvFileName.substring(0, fileNameIndex)+"_Result.csv";
		//System.out.println(csvResultFile);
		if(null!=resultMap&&resultMap.size()>0){
			File resultFile = new File(csvResultFile);
			try {
				if(!resultFile.exists()){
					resultFile.createNewFile();
				}
				BufferedWriter resultWriter = new BufferedWriter(new FileWriter(resultFile));
		        resultWriter.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));// 加上BOM
		        resultWriter.write(tableHead);
				resultWriter.newLine();
				for(Entry<String,List<String>> mapEntry : resultMap.entrySet()){
					String key = mapEntry.getKey();
					String bankKey = key.replaceAll(".", " ");
					//System.out.println("|" + bankKey + "|");
					List<String> value = mapEntry.getValue();
					if(null!=value&&value.size()>0){
						for(int i = 0;i<value.size();i++){
							String subValue = value.get(i);
							if(i==0){
								resultWriter.write(key+","+subValue);
							}else{
								resultWriter.write(bankKey+","+subValue);
							}
							resultWriter.newLine();
						}
					}else{
						resultWriter.write(key);
						resultWriter.newLine();
					}
				}
				resultWriter.flush();
				resultWriter.close();
				return csvResultFile;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return csvFileFullPath;
	}
	
	public static void main(String args[]){
		TmailDataSearchByCsv tc = new TmailDataSearchByCsv();
		String csvFileFullPath = "D:"+File.separator+"20160224.csv";
		List<String> phoneList = tc.getCsvPhoneConds(csvFileFullPath);
		/*Map<String,List<String>> tmailResultMap = new HashMap<String,List<String>>();
		List<String> aa = new ArrayList<String>();
		aa.add("ads,aderre");
		aa.add("bds,bderre");
		tmailResultMap.put("13718298650", aa);*/
		Map<String,List<String>> tmailResultMap = tc.getTmailDataByPhones(phoneList);
		String resultFullPath = tc.createResultCsv(tmailResultMap, csvFileFullPath);
		System.out.println(new Date(System.currentTimeMillis())+" : "+resultFullPath);//16/53 4min
	}
}
