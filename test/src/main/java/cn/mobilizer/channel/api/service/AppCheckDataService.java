package cn.mobilizer.channel.api.service;

import java.util.List;

import java.text.ParseException;

import cn.mobilizer.channel.api.po.AppDataChecklist;
import cn.mobilizer.channel.exception.BusinessException;

public interface AppCheckDataService {
	
	/**
	 * 按日期取出该用户的统计数据，通过循环tlist,比对数据是否一致，返回不一致的数据
	 * @param clientId
	 * @param clientUserId
	 * @param tlist
	 * @return
	 * @throws BusinessException
	 */
	public List<AppDataChecklist> saveCheckTableCount(Integer clientId, Integer clientUserId, String checkDate, String tableNames, List<AppDataChecklist> tlist) throws BusinessException,ParseException;
}
