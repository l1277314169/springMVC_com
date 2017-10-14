package cn.mobilizer.channel.survey.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.po.Survey;
import cn.mobilizer.channel.survey.po.SurveyBlock;
import cn.mobilizer.channel.survey.po.SurveyFeedback;
import cn.mobilizer.channel.survey.po.SurveyFeedbackDetail;
import cn.mobilizer.channel.survey.po.SurveyParameter;
import cn.mobilizer.channel.survey.vo.SurveyExportVo;
import cn.mobilizer.channel.survey.vo.SurveyImageVo;
import cn.mobilizer.channel.survey.vo.SurveyListVo;
import cn.mobilizer.channel.survey.vo.SurveyVo;

public interface SurveyService {

	public List<SurveyListVo> selectSurveyListVo(Map<String, Object> parameters) throws BusinessException;
	
	public Integer selectAllItems(Map<String, Object> parameters) throws BusinessException;
	
	public List<SurveyParameter> selectBysubSurveyId(Map<String, Object> params) throws BusinessException;
	
	public List<SurveyVo> getSurveyVos(Map<String, Object> params) throws Exception;
	
	public Map<Integer, Integer> getSurveyParameterBySurveyId(Map<String, Object> params) throws BusinessException;
	
	public List<Survey> getSurveyByName(Map<String, Object> params) throws BusinessException;
	
	public Survey getSurvey(Map<String, Object> params) throws BusinessException;
	
	public void saveSurveyFeedback(SurveyFeedback surveyFeedback) throws BusinessException;
	
	public List<SurveyBlock> selectBySurveyId(Map<String, Object> params) throws BusinessException;
	
	public SurveyBlock selectBySurveyBlockPrimaryKey(Map<String, Object> params) throws BusinessException;
	
	public Integer insertSurveyFeedbackDetail(List<SurveyFeedbackDetail> details,String feedbackId,Integer blockId,Integer clientId) throws BusinessException;
	
	public Integer insertAppleSurveyFeedbackDetail(List<SurveyFeedbackDetail> details,String feedbackId,Integer blockId,Integer clientId) throws BusinessException;
	
	public List<SurveyFeedbackDetail> getSurveyFeedbackDetail(Map<String, Object> parameters) throws BusinessException;
	
	public void deleteSurvey(String feedbackId) throws BusinessException;
	
	/**
	 * 获取最新的问卷
	 * @param surveyId
	 * @return
	 * @throws BusinessException
	 */
	public Survey getNewSurvey(Integer clientId) throws BusinessException;
	
	public boolean getSurveyCycle(Map<String, Object> params) throws BusinessException;
	
	
	public List<SurveyExportVo> getSurveyExportList(Integer surveyId,String feedbackId,Integer clientId) throws Exception;
	
	
	public List<SurveyImageVo> selectSurveyImages(Map<String, Object> params) throws BusinessException;
	
	public void saveColgateData(List<SurveyFeedbackDetail> Datablock3,List<SurveyFeedbackDetail> Datablock4,List<SurveyFeedbackDetail> Datablock5,List<SurveyFeedbackDetail> Datablock6,String feedbackId,Integer clientId,String storeNo,String visitor,Date feedbackDate,Integer surveyId,Integer clientUserId);
	
}
