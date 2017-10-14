package cn.mobilizer.channel.visit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.visit.dao.SpecialEventDao;
import cn.mobilizer.channel.visit.po.SpecialEvent;
import cn.mobilizer.channel.visit.service.SpecialEventService;
@Service
public class SpecialEventServiceImpl implements SpecialEventService {
		@Autowired
	private SpecialEventDao specialEventDao;
	@Override
	public List<Map<String, Object>> findSpecialEventList(
			Map<String, Object> parameters) throws BusinessException {
		return specialEventDao.findSpecialEventList(parameters);
	}
	@Override
	public Integer findSpecialEventCount(Map<String, Object> parameters)
			throws BusinessException {
		return specialEventDao.querySpecialEventCount(parameters);
	}

}
