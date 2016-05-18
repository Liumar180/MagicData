package com.integrity.dataSmart.common.page;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.integrity.dataSmart.util.importData.JdbcUtils;

public class PageDemo { 
    public static PageModel findAdmins(int pageNo,int pageSize){ 
        Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        String sql="select * from user limit ?,?"; 
        PageModel pageModel=null; 
        PreparedStatement pstm=null; 
        ResultSet rs=null; 
        Admin admin=null; 
        List<Admin> list=new ArrayList<Admin>(); 
        try { 
            pstm=conn.prepareStatement(sql); 
            pstm.setInt(1, (pageNo-1)*pageSize); 
            pstm.setInt(2,pageSize); 
            rs=pstm.executeQuery();; 
            while(rs.next()){ 
                admin=new Admin(); 
                admin.setId(rs.getInt("id")); 
                admin.setUsername(rs.getString("username")); 
                admin.setPassword(rs.getString("password")); 
                list.add(admin); 
            } 
            ResultSet rs2=pstm.executeQuery("select count(*) from user"); 
            int total=0; 
            if(rs2.next()){ 
                total=rs2.getInt(1); 
            } 
            pageModel=new PageModel(); 
            pageModel.setPageNo(pageNo); 
            pageModel.setPageSize(pageSize); 
            pageModel.setTotalRecords(total); 
            pageModel.setList(list); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }finally{ 
        	JdbcUtils.free(rs, pstm, conn);
        } 
        return pageModel;
    } 
       
    public static void main(String[] args) {
        PageModel pageModel=PageDemo.findAdmins(1,2); 
        List<Admin> list=pageModel.getList(); 
        System.out.println(list.size());
        for(Admin a:list){ 
            System.out.print("ID:"+a.getId()+",用户名:"+a.getUsername()+",密码:"+a.getPassword()); 
            System.out.println(); 
        } 
        System.out.print("当前页:"+pageModel.getPageNo()+" "); 
        System.out.print("共"+pageModel.getTotalPages()+"页  "); 
        System.out.print("首页:"+pageModel.getTopPageNo()+" "); 
        System.out.print("上一页:"+pageModel.getPreviousPageNo()+" "); 
        System.out.print("下一页:"+pageModel.getNextPageNo()+" "); 
        System.out.print("尾页:"+pageModel.getBottomPageNo()+" "); 
        System.out.print("共"+pageModel.getTotalRecords()+"条记录"); 
        System.out.println(); 
    } 
   
}
