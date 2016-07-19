package com.service.impl;

import com.dao.UserDao;
import com.po.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhonger on 2016/7/11.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public int addUser(User user) {
        return 0;
    }

    public int updateUser(User user) {
        return 0;
    }

    public int deleteUser(int userId) {
        return 0;
    }

    public User findUserById(int userId) {
        return null;
    }

    public User findUserName(String userName) {
        return userDao.selectByPrimaryName(userName);
    }

    public int deleteUser(Integer UserId) {
        return 0;
    }

    public List<User> queryUserlist(Map<String, Object> parameterMap) {
        return userDao.selectUserList(parameterMap);
    }

}
