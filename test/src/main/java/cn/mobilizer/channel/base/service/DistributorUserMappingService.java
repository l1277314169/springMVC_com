package cn.mobilizer.channel.base.service;

import java.util.Map;

import cn.mobilizer.channel.base.po.DistributorUserMapping;

public interface DistributorUserMappingService {
	/**
	 * 
	 * @return
	 */
	public DistributorUserMapping findDistributorUserMapping(Map<String,Object> param);
	
}
