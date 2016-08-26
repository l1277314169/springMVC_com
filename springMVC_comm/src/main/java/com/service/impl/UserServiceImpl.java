package com.service.impl;


import com.dao.UserDao;
import com.po.User;
import com.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhonger on 2016/7/11.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    public int addUser(User user) {
        String salt = "12345";
        String password = null;
        try {
            password = new Md5Hash(user.getPassword(), salt).toString();
        } catch (Exception e) {
          e.printStackTrace();
        }
        user.setPassword(password);
        user.setSalt(salt);
        return userDao.insertSelective(user);
    }

    public int updateUser(User user) {
        String salt = "hello";
        String password = null;
        try {
            password = new Md5Hash(user.getPassword(), salt).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setPassword(password);
        user.setSalt(salt);
        return userDao.updateByPrimaryKeySelective(user);
    }

    public int deleteUser(int userId) {
        return userDao.deleteByPrimaryKey(userId);
    }

    public User findUserById(int userId) {
        return null;
    }

    public User findUserName(String userName){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("username",userName);
        return userDao.selectByPrimaryName(map);
    }

    public int deleteUser(Integer UserId) {
        return 0;
    }

    public List<User> queryUserlist(Map<String, Object> parameterMap) {
            return userDao.selectUserList(parameterMap);
    }

    @Override
    public int countUser(Map<String, Object> parameterMap) {
        return userDao.countUserList(parameterMap);
    }


}
