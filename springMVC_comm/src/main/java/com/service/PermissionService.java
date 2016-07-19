package com.service;

import com.po.Permission;

import java.util.List;

/**
 * Created by liuhonger on 2016/7/14.
 */
public interface PermissionService {

    public List<Permission> selectPermissionByUserId(Integer id);
    public List<Permission> selectUrllist(Integer id);
}
