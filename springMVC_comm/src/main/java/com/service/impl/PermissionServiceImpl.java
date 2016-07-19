package com.service.impl;

import com.dao.PermissionDao;
import com.po.Permission;
import com.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuhonger on 2016/7/14.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private  PermissionDao permissionDao;

    public List<Permission> selectPermissionByUserId(Integer id) {
        return permissionDao.findPermissionList(id);
    }

    public List<Permission> selectUrllist(Integer id){
        return permissionDao.findUrlList(id);
    }
}
