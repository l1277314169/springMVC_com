package cn.mobilizer.channel.ctTask.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.ctTask.dao.CtTaskDetailDataDao;
import cn.mobilizer.channel.ctTask.po.CtTaskDetailData;
import cn.mobilizer.channel.ctTask.service.CtTaskDetailDataService;

@Service
public class CtTaskDetailDataServiceImpl implements CtTaskDetailDataService {

	
	@Autowired
	private CtTaskDetailDataDao ctTaskDetailDataDao;

	@Override
	public List<CtTaskDetailData> selectByParams(Map<String, Object> param) {
		return ctTaskDetailDataDao.selectByParams(param);
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param) {
		return ctTaskDetailDataDao.selectByParamCount(param);
	}

	@Override
	public String insert(CtTaskDetailData ctTaskDetailData) {
		return ctTaskDetailDataDao.insert(ctTaskDetailData);
	}

}
