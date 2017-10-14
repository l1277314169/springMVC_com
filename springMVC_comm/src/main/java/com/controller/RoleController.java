package com.controller;

import com.comm.pagination.Page;
import com.po.Role;
import com.service.RoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhonger on 2016/7/11.
 */
@Controller()
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    private String aa;

    private static final Log LOG = LogFactory.getLog(RoleController.class);
    @RequestMapping(value = "/query")
    public String searchUserlist(Model model,Integer page,Integer pageSize,HttpServletRequest request){
        Integer currentPage = page == null ? 1 : page;
        Integer currentPageSize = pageSize == null? 10 : pageSize;
String re = request.getParameter("name");
        Map<String,Object> parameperMap =new HashMap<String, Object>();
        int totalCount = roleService.serlectRoleCount(parameperMap);
        Page pageParam = Page.page(totalCount, currentPageSize, currentPage);
        parameperMap.put("_start",pageParam.getStartRows());
        parameperMap.put("_size",pageParam.getPageSize());
        List<Role> rolesList = roleService.queryRolelist(parameperMap);
        pageParam.buildUrl(request);
        pageParam.setItems(rolesList);
        // 存储分页结果
        model.addAttribute("pageParam", pageParam);
        return "role";
    }

    @RequestMapping(value="/addUser")
    public int add(Role role){
        int userId =roleService.addRole(role);
        return userId;
    }

    @RequestMapping(value = "/updateUser")
    public int update (Role role){
        int totalCount = roleService.serlectRoleCount(null);
        int roleId = roleService.updateRole(role);
        return roleId;
    }

    public  int delete(int role){
        int id = roleService.deleteRole(role);
        return id;
    }


    public static void main(String[] args) {
        RoleController roleController = new RoleController();
        System.out.println(roleController);
        String s=new String("aa");
        String b=new String("aa");
        System.out.println(s);
        System.out.println(b);
        System.out.println(s==b);
    }

}
