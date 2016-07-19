package com.controller;

import com.po.User;
import com.service.UserService;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhonger on 2016/3/29.
 */
@Controller(value = "userController")
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
@RequestMapping(value = "/query")
    public  String query(Model model){
        Map<String,Object> parameperMap =new HashMap<String, Object>();
        List<User> userList = userService.queryUserlist(parameperMap);
        if(userList !=  null && !userList.isEmpty()){
            //JSONArray jsonArray=JSONArray.fromObject(userList);
            model.addAttribute("userList",userList);
        }
        return "user";
    }
    @RequestMapping(value="/addUser")
    public int add(User user){
        int userId =userService.addUser(user);
        return userId;
    }

    @RequestMapping(value = "/updateUser")
    public int update (User user){
    int userId = userService.updateUser(user);
        return userId;
    }

    public  int delete(int userId){
        int id = userService.deleteUser(userId);
        return id;
    }


}
