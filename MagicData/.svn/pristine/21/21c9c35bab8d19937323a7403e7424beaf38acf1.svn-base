package com.integrity.login.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.DataType;
import com.integrity.login.bean.User;
import com.integrity.login.dao.UserDao;
import com.integrity.login.util.PageInfo;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	public User findUser(User user) {
		Connection conn = this.getConn();
		String query = "select * from user where status=0 and username=? and password=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setRoleType(rs.getInt("roleType"));
			}else{
				user=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(conn, pstmt, rs);
		}
       return user;
	}

	public User findUserById(User user) {
		Connection conn = this.getConn();
		String query = "select * from user where status=0 and id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, user.getId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setRoleType(rs.getInt("roleType"));
			}else{
				user=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(conn, pstmt, rs);
		}
       return user;
	}

	public int saveUser(User user) {
		Connection conn = this.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql1="select * from user where username='"+user.getUserName()+"'";
		String sql = "insert into user(username,password,roleType,cdate,mdate) values(?,?,?,?,?)";
		String nowTime = new SimpleDateFormat(DataType.DATEFORMATSTR).format(new Date());
		int flag = 0;
		try {
			pstmt = conn.prepareStatement(sql1);
			rs=pstmt.executeQuery();
			if(rs.next()){
				flag=-1;
			}
			if(flag!=-1){
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getUserName());
				pstmt.setString(2, user.getPassword());
				pstmt.setInt(3, user.getRoleType());
				pstmt.setTimestamp(4,Timestamp.valueOf(nowTime));
				pstmt.setTimestamp(5, Timestamp.valueOf(nowTime));
				flag = pstmt.executeUpdate();
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return flag;
	}

	@Override
	public int findCount() {
		Connection conn = this.getConn();
		String query = "select count(id) from user";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int i = 0;
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				i=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(conn, pstmt, rs);
		}
		return i;
	}
	public List<User> findAllUser(PageInfo page,String username) {
		Connection conn = this.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		try {
			String sql1;
			String sql;
			if(username!=null){
				sql="select count(*) total from user where username like '%"+username+"%'";
				sql1 = "select  *  from user where username like '%"+username+"%' limit "
						+ ((page.getPageNo() - 1) * page.getPageSize()) + ","
						+ page.getPageSize() + "";
			}else{
				sql="select count(*) total from user";
				sql1 = "select  *  from user limit "
						+ ((page.getPageNo() - 1) * page.getPageSize()) + ","
						+ page.getPageSize() + "";
			}
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);
			int pageCount = 1;
			int rowCount = 0;
			if (rs.next()) {
				rowCount =  rs.getInt("total");
				if (rowCount % page.getPageSize() == 0) {
					pageCount = rowCount / page.getPageSize();
				} else {
					pageCount = rowCount / page.getPageSize() + 1;
				}
			}
			rs = pstmt.executeQuery(sql1);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("username"));
				user.setPhone(rs.getString("phone"));
				user.setNickName(rs.getString("nickname"));
				user.setName(rs.getString("name"));
				user.setRoleType(rs.getInt("roleType"));
				user.setStatus(rs.getInt("status"));
				user.setCdate(rs.getTimestamp("cdate"));
				userList.add(user);
			}

			// 设置分页信息
			if (page != null) {
				page.setTotalRecords(rowCount);
				page.setTotalPages(pageCount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(conn, pstmt, rs);  
		}

		return userList;
	}


	public int deleteUser(int id) {
		Connection conn = this.getConn();
		PreparedStatement pstmt = null;
		String sql="delete from user where id="+id;
		int falg = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			falg = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return falg;
	}

	public int modifyPwd(String username,String oldPwd, String newPwd) {
		Connection conn = this.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="select  * from user where username='"+username+"' and password='"+oldPwd+"'";
		int i = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(!rs.next()) {
				i=-1;
			}
			if(i!=-1){
				String sql1="update user set password='"+newPwd+"' where username='"+username+"'";
				pstmt = conn.prepareStatement(sql1);
			    i=pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(conn, pstmt, rs);
		}
		return i;
	}
	
	public Connection getConn() {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Connection conn = session.connection();
		return conn;
	}

	public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			conn.close();
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int modifyStatus(int id, int status) {
		Connection conn = this.getConn();
		PreparedStatement pstmt = null;
		String sql="update user set status="+status +" where id="+id;
		int flag=-1;
		try {
			pstmt = conn.prepareStatement(sql);
			flag=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<User> findAllUser() {
		return getHibernateTemplate().loadAll(User.class);
	}
}
