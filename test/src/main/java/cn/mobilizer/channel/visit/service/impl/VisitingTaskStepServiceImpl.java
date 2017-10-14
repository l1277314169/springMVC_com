package cn.mobilizer.channel.visit.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.visit.dao.VisitingTaskStepDao;
import cn.mobilizer.channel.visit.po.VisitingTaskStep;
import cn.mobilizer.channel.visit.service.VisitingTaskStepService;

@Service
public class VisitingTaskStepServiceImpl implements VisitingTaskStepService {
	private static final Log LOG = LogFactory.getLog(VisitingTaskStepServiceImpl.class);
	
	@Autowired
	private VisitingTaskStepDao visitingTaskStepDao;

	@Override
	public List<VisitingTaskStep> queryVisitingTaskStepList(Map<String, Object> params) throws BusinessException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VisitingTaskStep> getVisitingTaskStepListByVtIds(String visitingTaskIds) throws BusinessException{
		List<VisitingTaskStep> ls = null;
		try {
			ls = visitingTaskStepDao.getVisitingTaskStepListByVtIds(visitingTaskIds);
		} catch(BusinessException e){
			LOG.error("method getVisitingTaskStepListByVtIds error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}
	
}
