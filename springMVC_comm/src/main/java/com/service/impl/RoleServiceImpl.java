package com.service.impl;

import com.dao.RoleDao;
import com.po.Role;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhonger on 2016/7/11.
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;

    public int addRole(Role role) {
        return 0;
    }

    public int updateRole(Role role) {
        return 0;
    }

    public int deleteRole(Integer Role) {
        return 0;
    }

    public Role findRoleId(Integer RoleId) {
        return null;
    }

    public List<Role> queryRolelist(Map<String, Object> parameterMap) {
        return null;
    }
}
