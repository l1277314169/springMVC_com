package com.dao;

import com.po.ShopAddress;

public interface ShopAddressMapper {
    int deleteByPrimaryKey(String id);

    int insert(ShopAddress record);

    int insertSelective(ShopAddress record);

    ShopAddress selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ShopAddress record);

    int updateByPrimaryKey(ShopAddress record);
}