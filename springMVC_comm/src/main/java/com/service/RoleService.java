package com.service;

import com.po.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhonger on 2016/7/11.
 */
public interface RoleService {
    public int addRole(Role role);
    public int updateRole(Role role);
    public int deleteRole(Integer Role);
    public Role findRoleId(Integer RoleId);
    public List<Role> queryRolelist(Map<String,Object> parameterMap);
    public int serlectRoleCount(Map<String,Object> parameterMap);
}
