package com.integrity.dataSmart.test.ImportResumeData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;

import com.integrity.dataSmart.impAnalyImport.bean.Resume;
import com.integrity.dataSmart.impAnalyImport.bean.ResumeBase;
import com.integrity.dataSmart.test.Internet;
import com.integrity.dataSmart.test.TitanConnection;
import com.integrity.dataSmart.util.WritelogContents;
import com.integrity.dataSmart.util.importData.JdbcUtils;
import com.thinkaurelius.titan.core.TitanGraph;

/**
 * @author liuBF
 * 简历导入O
 */
public class ResumeInfoImpUtils {
	public static HttpSolrServer server = null;

	public static void main(String[] args) {
		//int r = 50000;
		//for(int k=60000; k<=2000000; k +=r){
			TitanGraph graph = TitanConnection.getTitaConnection();
			List<Resume> rs= ResumeInfoImpUtils.findResumes(50000, 100000);//k-r, k
			Internet.impResumes(rs, graph);
			graph.shutdown();
		//}

	}
	/**
	 * @param start
	 * @param rows
	 * @return
	 * 获取简历人员基本信息
	 */
		public static ResumeBase getResumelistDatas(String InfoID) {
			Connection conn = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			try {
				conn = JdbcUtils.getSqlServerConnection();
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	
	    String sql = "select top 1 tempcolumn=0, "
					+" p.ContactID, p.FirstName,p.LastName,p.GenderTag,p.CreationDate,"
					+" cc.Login,cc.PasswordHash,ar.Address1,ar.City,ar.County from "
					+" Person p,Contact cc,ContactAddressTBL ar"
					+" where p.ContactID = cc.ContactID"
					+" AND p.ContactID = ar.ContactID"
					+" and p.ContactID "
					+" in(SELECT top 1 a.ContactID "
							+" from ContactInformation a where a.InformationID = "+InfoID+")";
	    ResumeBase rBase = new ResumeBase();
	    try {
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					while(rs.next()){
						System.out.println("ContactID:"+rs.getString("ContactID"));
						rBase.setContactID(rs.getString("ContactID"));
						if(rs.getString("FirstName") != null && rs.getString("LastName") != null){
						    rBase.setName(rs.getString("FirstName").trim()+" "+rs.getString("LastName").trim());// FirstName; LastName;
						}else if(rs.getString("FirstName") != null){
							rBase.setName(rs.getString("FirstName").trim());// FirstName
						}else if(rs.getString("LastName") != null){
							rBase.setName(rs.getString("LastName").trim());// LastName;
						}
						if(rs.getString("GenderTag") != null){
							rBase.setGenderTag(rs.getString("GenderTag").trim());
						}
						if(rs.getString("CreationDate") != null){
							rBase.setCreationDate(rs.getString("CreationDate").trim());
						}
						if(rs.getString("Login") != null){
							rBase.setLogin(rs.getString("Login").trim());
						}
						if(rs.getString("PasswordHash") != null){
							rBase.setPasswordHash(rs.getString("PasswordHash").trim());
						}
						if(rs.getString("City") !=null && rs.getString("Address1") != null){
							rBase.setAddress(rs.getString("City").trim()+" "+rs.getString("Address1").trim());// Address1;City;
						}else if(rs.getString("City") !=null){
							rBase.setAddress(rs.getString("City").trim());
						}else if(rs.getString("Address1") != null){
							rBase.setAddress(rs.getString("Address1").trim());// Address1;City;
						}
						if(rs.getString("County") != null){
							rBase.setCounty(rs.getString("County").trim());
						}
					}
					conn.commit();
				} catch (SQLException e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}finally{
					JdbcUtils.free(rs, ps, conn);
				}
				return rBase;
	
		}
		/**
		 * @param contactID
		 * @return
		 * 获取电话号码
		 */
		public static List<String> findPhoneNum(String contactID){
			List<String> phones = new ArrayList<String>();
			Connection conn = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			try {
				
				conn = JdbcUtils.getSqlServerConnection();
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    String sql = "select top 5 p.PhoneNumber "
				+" from ContactPhoneTBL p "
				+" where p.ContactID ="+contactID;
	    try {
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					while(rs.next()){
						phones.add(rs.getString("PhoneNumber").trim());
					}
					conn.commit();
				} catch (SQLException e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}finally{
					JdbcUtils.free(rs, ps, conn);
				}
			return phones;
		}
		
		/**
		 * @param contactID
		 * @return
		 * 获取简历信息
		 */
		public static List<Resume> findResumes(Integer start,Integer rows){
			WritelogContents.writeLogs("C:\\Users\\integrity\\Desktop\\solr\\resumeList0217.txt", "开始查询行数："+start+"---查询行数："+rows);
			List<Resume> resumes = new ArrayList<Resume>();
			Connection conn = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			try {
				conn = JdbcUtils.getSqlServerConnection();
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	
	    String sql = "select "
					+"tt.InformationID,tt.Keywords,tt.Title,tt.Description,tt.AreaCode,tt.City,tt.CountryType,tt.CreationDate,"
					+"tt.ApprovalDate,tt.BeginDate,tt.Private,tt.State,tt.Zip,tt.ModifyDate"
					+" from ("
					+"select row_number()over(order by tempcolumn)temprownumber,*"
					+" from (select top "+rows+" tempcolumn=0,"
					+"q.InformationID,q.Keywords,q.Title,q.Description,q.AreaCode,q.City,q.CountryType,q.CreationDate,"
					+"q.ApprovalDate,q.BeginDate,q.Private,q.State,q.Zip,q.ModifyDate"
					+" from Query q "
					+")t"
					+")tt"
					+" where temprownumber>"+start;
	    try {
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					while(rs.next()){
						Resume r = new Resume();
						r.setInformationID(rs.getString("InformationID"));
						r.setKeyWords(rs.getString("Keywords"));
						r.setTitle(rs.getString("Title"));
						r.setDescription(rs.getString("Description"));
						r.setAreaCode(rs.getString("AreaCode"));
						r.setCity(rs.getString("City"));
						r.setCountryType(rs.getString("CountryType"));
						r.setCreationDate(rs.getString("CreationDate"));
						r.setApprovalDate(rs.getString("ApprovalDate"));
						r.setBeginDate(rs.getString("BeginDate"));
						r.setPrivate(rs.getString("Private"));
						r.setState(rs.getString("State"));
						r.setZip(rs.getString("Zip"));
						r.setModifyDate(rs.getString("ModifyDate"));
						resumes.add(r);
					}
					conn.commit();
				} catch (SQLException e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}finally{
					JdbcUtils.free(rs, ps, conn);
				}
			return resumes;
			
		}
		/**
		 * @param list
		 * 向solr中存储简历信息
		 * @return
		 */
		public static int insertResumeToSolr(Resume resume){
			try {
				if(server == null){
					server = new HttpSolrServer("http://192.168.40.10/solr/core2");
				}
				UpdateResponse response = server.addBean(resume);
				server.commit();
				return response.getStatus();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
			return -1;
		}
		public static int clean(){
			try {
				if(server == null){
					server = new HttpSolrServer("http://192.168.40.10/solr/core2");
				}
				server.deleteByQuery("*:*");
				server.commit();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
			return -1;
		}
		/**
		 * 关闭连接 solr core2
		 */
		public static void closeResumeSolr(){
			try {
				if (server != null) {
					server.shutdown();
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

}
