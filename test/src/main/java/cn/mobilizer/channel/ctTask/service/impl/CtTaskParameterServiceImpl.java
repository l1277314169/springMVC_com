package cn.mobilizer.channel.ctTask.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.ctTask.dao.CtTaskParameterDao;
import cn.mobilizer.channel.ctTask.po.CtTaskParameter;
import cn.mobilizer.channel.ctTask.service.CtTaskParameterService;
import cn.mobilizer.channel.util.Constants;

@Service
public class CtTaskParameterServiceImpl implements CtTaskParameterService {

	@Autowired
	private CtTaskParameterDao ctTaskParameterDao;

	@Override
	public List<CtTaskParameter> selectByParams(Integer clientId,Integer ctTaskId) {
		Map<String,Object> parameterParam = new HashMap<String, Object>();
		parameterParam.put("clientId", clientId);
		parameterParam.put("ctTaskId", ctTaskId);
		parameterParam.put("isDelete", Constants.IS_DELETE_FALSE);
		parameterParam.put("_orderby", "sequence");
		parameterParam.put("_order", "asc");
		return ctTaskParameterDao.selectByParams(parameterParam);
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param) {
		return ctTaskParameterDao.selectByParamCount(param);
	}

	@Override
	public Integer insert(CtTaskParameter ctTaskParameter) {		 
		return ctTaskParameterDao.insert(ctTaskParameter);
	}

	@Override
	public CtTaskParameter selectByPrimaryKey(Integer ctTaskParameterId) {
		return ctTaskParameterDao.selectByPrimaryKey(ctTaskParameterId);
	}	 

}
