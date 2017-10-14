package cn.mobilizer.channel.survey.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.dao.MsiSurveyComplainDao;
import cn.mobilizer.channel.survey.po.MsiSurveyComplain;
import cn.mobilizer.channel.survey.service.MsiSurveyComplainService;
import cn.mobilizer.channel.survey.vo.MsiQuestionVO;

/**
 * @author yeeda.tian
 *
 */
@Service
public class MsiSurveyComplainServiceImpl implements MsiSurveyComplainService{
	
	@Autowired
	private MsiSurveyComplainDao msiSurveyComplainDao;

	@Override
	public List<MsiSurveyComplain> getEntityByFeedbackId(String feedbackId) {
		return msiSurveyComplainDao.getEntityByFeedbackId(feedbackId);
	}

	@Override
	public MsiSurveyComplain getEntityByFeedbackIdAndMsiQuestionId(String feedbackId,Integer msiQuestionId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("feedbackId", feedbackId);
		params.put("msiQuestionId", msiQuestionId);
		return msiSurveyComplainDao.getEntityByFeedbackIdAndMsiQuestionId(params);
	}

	@Override
	public String insertSelective(MsiSurveyComplain MsiSurveyComplain) {
		return msiSurveyComplainDao.insertSelective(MsiSurveyComplain);
	}

	@Override
	public Integer updateMsiSurveyComplain(MsiSurveyComplain MsiSurveyComplain) {
		return msiSurveyComplainDao.updateMsiSurveyComplain(MsiSurveyComplain);
	}

	@Override
	public void updateMsiSurveyComplainStatus(Integer clientId,Integer clientUserId, String complainId, Byte status) throws BusinessException {
		MsiSurveyComplain msiSurveyComplain = msiSurveyComplainDao.selectByPrimaryKey(complainId);
		if(msiSurveyComplain!=null){
			msiSurveyComplain.setReplyBy(clientUserId);
			msiSurveyComplain.setStatus(status);
			msiSurveyComplainDao.updateMsiSurveyComplain(msiSurveyComplain);
		}
	}
	
}
