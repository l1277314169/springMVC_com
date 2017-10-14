package cn.mobilizer.channel.survey.service;

import cn.mobilizer.channel.survey.po.SurveyFeedback;

public interface SurveyFeedbackService {
	
	public void update(SurveyFeedback surveyFeedback);
	
	public SurveyFeedback selectByPrimaryKey(String feedbackId);
	
	public SurveyFeedback getSurveyFeedbackByStoreId(String storeId);
}
