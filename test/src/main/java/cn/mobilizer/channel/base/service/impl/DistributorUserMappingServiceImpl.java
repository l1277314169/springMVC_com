package cn.mobilizer.channel.base.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.DistributorUserMappingDao;
import cn.mobilizer.channel.base.po.DistributorUserMapping;
import cn.mobilizer.channel.base.service.DistributorUserMappingService;
@Service
public class DistributorUserMappingServiceImpl implements DistributorUserMappingService {
	@Autowired
	private DistributorUserMappingDao distributorUserMappingDao;
	@Override
	public DistributorUserMapping findDistributorUserMapping(Map<String, Object> params) {
		
		return distributorUserMappingDao.getDistributor(params);
	}

}
