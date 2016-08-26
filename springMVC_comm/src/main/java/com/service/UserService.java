package com.service;


import com.comm.CustomException;
import com.po.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhonger on 2016/7/11.
 */
public interface UserService {
    public int addUser(User user) throws CustomException;
    public int updateUser(User user);
    public int deleteUser(int userId);
    public User findUserById(int userId);
    public User findUserName(String userName) throws SQLException;
    public List<User> queryUserlist(Map<String,Object> parameterMap);
    public int countUser(Map<String,Object> parameterMap);

}
