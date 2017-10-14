package cn.mobilizer.channel.visit.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.visit.po.SpecialEvent;

public interface SpecialEventService {
	
	public List<Map<String, Object>> findSpecialEventList(Map<String, Object> parameters)throws BusinessException;
	
	public Integer findSpecialEventCount(Map<String, Object> parameters)throws BusinessException;
}
