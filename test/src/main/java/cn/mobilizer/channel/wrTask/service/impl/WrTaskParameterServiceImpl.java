package cn.mobilizer.channel.wrTask.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.dao.WrTaskParameterDao;
import cn.mobilizer.channel.wrTask.po.WrTaskParameter;
import cn.mobilizer.channel.wrTask.service.WrTaskParameterService;

@Service
public class WrTaskParameterServiceImpl implements WrTaskParameterService {
	
	@Autowired
	private WrTaskParameterDao wrTaskParameterDao;

	@Override
	public List<WrTaskParameter> selectByParams(Map<String, Object> param)throws BusinessException {
		return wrTaskParameterDao.selectByParams(param);
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param)throws BusinessException {
		return wrTaskParameterDao.selectByParamCount(param);
	}

}
