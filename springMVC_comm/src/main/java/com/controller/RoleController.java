package com.controller;

import com.po.Role;
import com.po.User;
import com.service.RoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhonger on 2016/7/11.
 */
@Controller(value = "roleController")
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    private static final Log LOG = LogFactory.getLog(RoleController.class);
    @RequestMapping(value = "/query")
    public String searchUserlist(Model model){
        Map<String,Object> parameperMap =new HashMap<String, Object>();
        List<Role> rolesList = roleService.queryRolelist(parameperMap);
        model.addAttribute("rolesLIst",rolesList);
        return "role";
    }

    @RequestMapping(value="/addUser")
    public int add(Role role){
        int userId =roleService.addRole(role);
        return userId;
    }

    @RequestMapping(value = "/updateUser")
    public int update (Role role){
        int roleId = roleService.updateRole(role);
        return roleId;
    }

    public  int delete(int role){
        int id = roleService.deleteRole(role);
        return id;
    }
}
