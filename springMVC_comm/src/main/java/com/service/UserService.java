package com.service;


import com.po.User;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhonger on 2016/7/11.
 */
public interface UserService {
    public int addUser(User user);
    public int updateUser(User user);
    public int deleteUser(int userId);
    public User findUserById(int userId);
    public User findUserName(String userName);
    public List<User> queryUserlist(Map<String,Object> parameterMap);

}
