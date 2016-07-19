package com.dao;

import com.po.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PermissionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> findPermissionList(Integer id);

    List<Permission> findUrlList(Integer id);
}