package cn.mobilizer.channel.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.survey.dao.SurveyFeedbackDao;
import cn.mobilizer.channel.survey.po.SurveyFeedback;
import cn.mobilizer.channel.survey.service.SurveyFeedbackService;

@Service
public class SurveyFeedbackServiceImpl implements SurveyFeedbackService {

	@Autowired
	private SurveyFeedbackDao surveyFeedbackDao;
	
	@Override
	public void update(SurveyFeedback surveyFeedback) {
		surveyFeedbackDao.updateByPrimaryKeySelective(surveyFeedback);
	}

	@Override
	public SurveyFeedback selectByPrimaryKey(String feedbackId) {
		return surveyFeedbackDao.selectByPrimaryKey(feedbackId); 
	}

	@Override
	public SurveyFeedback getSurveyFeedbackByStoreId(String storeId) {
		return surveyFeedbackDao.getSurveyFeedbackByStoreId(storeId);
	}
	 
}
