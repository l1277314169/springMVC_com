package cn.mobilizer.channel.visit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.visit.dao.VisitingParameterDao;
import cn.mobilizer.channel.visit.dao.VisitingTaskDetailDataDao;
import cn.mobilizer.channel.visit.po.VisitingParameter;
import cn.mobilizer.channel.visit.service.VisitingParameterService;


@Service
public class VisitingParameterServiceImpl implements VisitingParameterService {
	private static final Log LOG = LogFactory.getLog(VisitingParameterServiceImpl.class);
	
	@Autowired
	private VisitingParameterDao visitingParameterDao;
	@Override
	public List<VisitingParameter> getVisitingParameterListByStepId(Integer visitingTaskStepId,Integer clientId) throws BusinessException{
		List<VisitingParameter> ls = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("visitingTaskStepId", visitingTaskStepId);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			ls = visitingParameterDao.getVisitingParameterListByStepId(params);
		} catch(BusinessException e){
			LOG.error("method getVisitingParameterListByStepId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}
	
}
