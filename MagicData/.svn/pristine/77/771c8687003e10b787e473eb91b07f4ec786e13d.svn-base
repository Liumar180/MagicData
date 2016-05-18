package com.integrity.login.dao;

import java.util.List;
import java.util.Map;

import com.integrity.login.bean.User;
import com.integrity.login.util.PageInfo;

public interface UserDao {
     User findUser(User user);
     int findCount();
     int saveUser(User user);
     List<User> findAllUser(PageInfo page,String username);
     List<User> findAllUser();
     int deleteUser(int id);
     int modifyPwd(String username,String oldPwd,String newPwd);
     int modifyStatus(int id,int status);
     User findUserById(User user);
}
