package com.controller;

import com.comm.BaseController;
import com.comm.CustomException;
import com.comm.ResultMessage;
import com.comm.page.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.po.Role;
import com.po.User;
import com.service.RoleService;
import com.service.UserService;
import com.utils.ResourceUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by liuhonger on 2016/3/29.
 */
@Controller(value = "userController")
@RequestMapping(value = "/user")
public class UserController extends BaseController{

    private static final Log LOG = LogFactory.getLog(UserController.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/query")
    public  String query(Model model, Page page)  {

        System.out.println(ResultMessage.ADD_SUCCESS_RESULT);
        System.out.println(ResultMessage.ADD_SUCCESS_RESULT.getCode());
        ResultMessage.ADD_SUCCESS_RESULT.setCode("bb");
        System.out.println(ResultMessage.ADD_SUCCESS_RESULT.getCode());

        Map<String,Object> parameperMap =new HashMap<String, Object>();
    //parameperMap.put("username","zhangsan");
        int countUserlist =  userService.countUser(parameperMap);
        page.setTotalResult(countUserlist);
        int showCount = page.getShowCount()==0?10:page.getShowCount();
        page.setShowCount(showCount);
        int courrentPage = page.getCurrentPage()==0?1:page.getCurrentPage();
        parameperMap.put("_start",(courrentPage-1)*showCount);
        parameperMap.put("_size",showCount);
        List<User> userList = userService.queryUserlist(parameperMap);
         try {
            model.addAttribute("userlist",userList);
             model.addAttribute("flag",true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user";
    }
    @RequestMapping(value="/addUser")
    @ResponseBody
    public Object add(User user) throws CustomException {
        int userId =userService.addUser(user);
        if(userId > 0){
            return ResultMessage.ADD_SUCCESS_RESULT;
        }else{
            return ResultMessage.DELETE_FAIL_RESULT;
        }
    }

    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public Object update (User user){
        int userId = userService.updateUser(user);
        if(userId > 0){
            return ResultMessage.UPDATE_SUCCESS_RESULT;
        }else{
            return ResultMessage.DELETE_FAIL_RESULT;
        }
    }
    @RequestMapping(value = "/deleteUser")
    @ResponseBody
    public  Object delete(int userId){
        int id = userService.deleteUser(userId);
        if(id > 0){
            return ResultMessage.DELETE_SUCCESS_RESULT;
        }else{
            return ResultMessage.DELETE_FAIL_RESULT;
        }
    }
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response){
        String templatePath = "/WEB-INF/template/aa.xlsx";
        String outputFile="aa_"+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xlsx";
        Map<String,Object> beans = new HashMap<String,Object>();
        List<User> userList = userService.queryUserlist(null);
        beans.put("userList", userList);
        ResourceUtil.exportXLS(beans, templatePath,outputFile,response);
    }

    public void show(final  String a){
        System.out.println();

    }





}
