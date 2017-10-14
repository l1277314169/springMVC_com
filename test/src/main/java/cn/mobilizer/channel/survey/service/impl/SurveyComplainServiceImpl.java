package cn.mobilizer.channel.survey.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.dao.SurveyComplainDao;
import cn.mobilizer.channel.survey.dao.SurveyFeedbackDao;
import cn.mobilizer.channel.survey.po.SurveyComplain;
import cn.mobilizer.channel.survey.po.SurveyFeedback;
import cn.mobilizer.channel.survey.service.SurveyComplainService;
import cn.mobilizer.channel.util.Constants;

@Service
public class SurveyComplainServiceImpl implements SurveyComplainService{
	
	@Autowired
	private SurveyComplainDao surveyComplainDao;
	@Autowired
	private SurveyFeedbackDao surveyFeedbackDao;

	@Override
	public int insert(SurveyComplain surveyComplain) throws BusinessException {
		return surveyComplainDao.insertSelective(surveyComplain);
	}

	@Override
	public int update(SurveyComplain surveyComplain) throws BusinessException {
		return surveyComplainDao.updateByPrimaryKeySelective(surveyComplain);
	}

	@Override
	public void saveSurveyComplain(String feedbackId,String aproveContent,Byte status,Integer clientUserId,Integer clientId) throws BusinessException {
		SurveyComplain querySurveyComplain = surveyComplainDao.getEntityByFeedbackId(feedbackId);
		if(querySurveyComplain==null){
			SurveyComplain surveyComplain = new SurveyComplain();
			surveyComplain.setComplainId(UUID.randomUUID().toString());
			surveyComplain.setFeedbackId(feedbackId);
			surveyComplain.setReplyContent(aproveContent);
			surveyComplain.setReplyBy(clientUserId);
			surveyComplain.setStatus(status);
			surveyComplain.setClientId(clientId);
			surveyComplainDao.insert(surveyComplain);
		}else{
			querySurveyComplain.setReplyContent(aproveContent);
			querySurveyComplain.setReplyBy(clientUserId);
			querySurveyComplain.setStatus(status);
			querySurveyComplain.setClientId(clientId);
			surveyComplainDao.updateByPrimaryKeySelective(querySurveyComplain);
		}
		SurveyFeedback surveyFeedback = surveyFeedbackDao.selectByPrimaryKey(feedbackId);
		surveyFeedback.setStatus(status);
		surveyFeedbackDao.updateByPrimaryKeySelective(surveyFeedback);
	}

	@Override
	public void updateAproveStatus(String feedbackId) throws BusinessException {
		SurveyComplain querySurveyComplain = surveyComplainDao.getEntityByFeedbackId(feedbackId);
		if(querySurveyComplain!=null){
			querySurveyComplain.setIsDelete(Constants.IS_DELETE_TRUE);
			surveyComplainDao.updateByPrimaryKeySelective(querySurveyComplain);
		}
		SurveyFeedback surveyFeedback = surveyFeedbackDao.selectByPrimaryKey(feedbackId);
		surveyFeedback.setStatus(Constants.APPLE_SURVEY_STATUS_SAVE);
		surveyFeedbackDao.updateByPrimaryKeySelective(surveyFeedback);
	}
	
}
