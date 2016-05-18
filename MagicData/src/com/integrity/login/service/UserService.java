package com.integrity.login.service;

import java.util.List;

import com.integrity.login.bean.User;
import com.integrity.login.util.PageInfo;

public interface UserService {
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
