package cn.mobilizer.channel.survey.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.po.MsiSurveyComplain;

/**
 * @author liuYong
 *
 */
public interface MsiSurveyComplainService {
	
	public String insertSelective(MsiSurveyComplain MsiSurveyComplain);
	
	public Integer updateMsiSurveyComplain(MsiSurveyComplain MsiSurveyComplain);
	 
	/**
	 * 根据问卷查询申诉
	 * @param params
	 * @return
	 */
	public List<MsiSurveyComplain> getEntityByFeedbackId(String feedbackId);
	
	/**
	 * 根据问卷和问题查询申诉
	 * @param params
	 * @return
	 */
	public MsiSurveyComplain getEntityByFeedbackIdAndMsiQuestionId(String feedbackId,Integer msiQuestionId);
	
	/**
	 * 改变申诉状态
	 * @param clientId
	 * @param clientUserId
	 * @param complainId
	 * @param status
	 */
	public void updateMsiSurveyComplainStatus(Integer clientId, Integer clientUserId,String complainId,Byte status) throws BusinessException;
}
