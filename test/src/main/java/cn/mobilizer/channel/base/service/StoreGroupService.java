package cn.mobilizer.channel.base.service;


import cn.mobilizer.channel.base.po.StoreGroup;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;


public interface StoreGroupService extends BasicService<StoreGroup>{

	StoreGroup getStoreGroupByStoreGroupId(Integer storeGroupId)throws BusinessException;
}
