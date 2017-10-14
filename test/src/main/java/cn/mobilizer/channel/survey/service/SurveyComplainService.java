package cn.mobilizer.channel.survey.service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.po.SurveyComplain;

public interface SurveyComplainService {
	
	public int insert(SurveyComplain surveyComplain) throws BusinessException;
	
	public int update(SurveyComplain surveyComplain) throws BusinessException;
	
	public void saveSurveyComplain(String feedbackId,String aproveContent,Byte status,Integer clientUserId,Integer clientId) throws BusinessException;

	public void updateAproveStatus(String feedbackId) throws BusinessException;
}
