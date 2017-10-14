package cn.mobilizer.channel.survey.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.dao.MsiSurveyDao;
import cn.mobilizer.channel.survey.po.MsiSurvey;
import cn.mobilizer.channel.survey.service.MsiSurveyService;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class MsiSurveyServiceImpl implements MsiSurveyService {

	private static final Log LOG = LogFactory.getLog(MsiSurveyServiceImpl.class);

	@Autowired
	private MsiSurveyDao msiSurveyDao;
	@Override
	public Integer queryMsiSurveyCount(Map<String, Object> searchParams) throws BusinessException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MsiSurvey> queryMsiSurveyList(Map<String, Object> searchParams) throws BusinessException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MsiSurvey getMsiSurveyByStoreIdAndClientId(Integer clientId,String storeId) throws BusinessException{
		MsiSurvey msiSurvey = null;
		try {
			if (clientId != null && StringUtil.isNotEmptyString(storeId)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId", clientId);
				params.put("storeId", storeId);
				List<MsiSurvey> ls = msiSurveyDao.findMsiSurveyListByStore(params);
				if(ls != null && ls.size() >0) {
					msiSurvey = ls.get(0);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiSurveyByStoreIdAndClientId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return msiSurvey;
	}

	@Override
	public MsiSurvey findAppleExamMsiSurveyByStoreAndType(Integer clientId,String storeId, Integer msiSurveyType) throws BusinessException {
		MsiSurvey msiSurvey = null;
		try {
			if (clientId != null && StringUtil.isNotEmptyString(storeId)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId", clientId);
				params.put("storeId", storeId);
				params.put("msiSurveyType", msiSurveyType);
				List<MsiSurvey> ls = msiSurveyDao.findAppleExamMsiSurveyByStoreAndType(params);
				if(ls != null && ls.size() >0) {
					msiSurvey = ls.get(0);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiSurveyByStoreIdAndClientId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return msiSurvey;
	}

	@Override
	public MsiSurvey findAppleMsiSurveyListByStore(Integer clientId,String storeId) throws BusinessException {
		MsiSurvey msiSurvey = null;
		try {
			if (clientId != null && StringUtil.isNotEmptyString(storeId)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId", clientId);
				params.put("storeId", storeId);
				List<MsiSurvey> ls = msiSurveyDao.findAppleMsiSurveyListByStore(params);
				if(ls != null && ls.size() >0) {
					msiSurvey = ls.get(0);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiSurveyByStoreIdAndClientId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return msiSurvey;
	}
	
	@Override
	public MsiSurvey findAppleMsiSurveyListByType(Integer clientId,String storeId) throws BusinessException{
		MsiSurvey msiSurvey = null;
		try{
			if (clientId != null && StringUtil.isNotEmptyString(storeId)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId", clientId);
				params.put("storeId", storeId);
				List<MsiSurvey> ls = msiSurveyDao.findAppleMsiSurveyListByType(params);
				if(ls != null && ls.size() >0) {
					msiSurvey = ls.get(0);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method findAppleMsiSurveyListByType error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return msiSurvey;
	}

	@Override
	public List<MsiSurvey> findHistoryMsiSurveyListByStore(String storeId) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		return msiSurveyDao.findHistoryMsiSurveyListByStore(params);
	}

	@Override
	public MsiSurvey selectByPrimaryKey(Integer msiSurveyId) {
		return msiSurveyDao.selectByPrimaryKey(msiSurveyId);
	}
}
