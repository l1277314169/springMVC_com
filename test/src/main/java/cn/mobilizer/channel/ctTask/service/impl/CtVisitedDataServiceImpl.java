package cn.mobilizer.channel.ctTask.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.ctTask.dao.CtVisitedDataDao;
import cn.mobilizer.channel.ctTask.po.CtVisitedData;
import cn.mobilizer.channel.ctTask.service.CtVisitedDataService;

@Service
public class CtVisitedDataServiceImpl implements CtVisitedDataService {

	@Autowired
	private CtVisitedDataDao ctVisitedDataDao;

	@Override
	public List<CtVisitedData> selectByParams(Map<String, Object> param) {
		return ctVisitedDataDao.selectByParams(param);
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param) {
		return ctVisitedDataDao.selectByParamCount(param);
	}
		 
}
