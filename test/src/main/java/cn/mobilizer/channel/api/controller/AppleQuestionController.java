package cn.mobilizer.channel.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.api.vo.ResultEntity;
import cn.mobilizer.channel.api.vo.ResultEntityWithList;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.survey.po.MsiSurvey;
import cn.mobilizer.channel.survey.po.MsiSurveyFinishCount;
import cn.mobilizer.channel.survey.service.MsiQuestionService;
import cn.mobilizer.channel.survey.service.MsiSurveyFeedbackService;
import cn.mobilizer.channel.survey.service.MsiSurveyService;
import cn.mobilizer.channel.survey.vo.MsiQuestionVO;
import cn.mobilizer.channel.survey.vo.MsiSurveyFeedbackVO;

@RestController
@RequestMapping(value = "/api/appleMsiQuestion")
public class AppleQuestionController {
	
	@Autowired
	private MsiSurveyService msiSurveyService;
	@Autowired
	private MsiQuestionService msiQuestionService;
	@Autowired
	private MsiSurveyFeedbackService msiSurveyFeedbackService;
	
	/**
	 * 随机生成10个题目
	 * @param clientId
	 * @param clientUserId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/getMsiQuestions", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithList<MsiQuestionVO> getMsiQuestions(Integer clientId,String clientUserId,Integer surveyType,String storeId){
		if(clientId == null || StringUtil.isEmptyString(clientUserId) || StringUtil.isEmptyString(storeId) || surveyType == null){
			ResultEntityWithList<MsiQuestionVO> resultEntity = new ResultEntityWithList<MsiQuestionVO>(false);
			resultEntity.setResultMSG("参数绑定错误");
			return resultEntity;
		}
		MsiSurvey msiSurvey = msiSurveyService.findAppleExamMsiSurveyByStoreAndType(clientId, storeId, surveyType);
		List<MsiQuestionVO> msiQuestionVos = null;
		if(msiSurvey!=null){
			msiQuestionVos = msiQuestionService.getAppleExamMsiQuestionByMsiSurveyId(msiSurvey.getMsiSurveyId());
		}else{
			ResultEntityWithList<MsiQuestionVO> resultEntity = new ResultEntityWithList<MsiQuestionVO>(false);
			resultEntity.setResultMSG("没有找到问卷");
			return resultEntity;
		}
		ResultEntityWithList<MsiQuestionVO> resultEntity = new ResultEntityWithList<MsiQuestionVO>(true);
		resultEntity.setDataInfo(msiQuestionVos);
		return resultEntity;
	}
	
	/**
	 * 苹果考试问卷接口
	 * @param msiSurveyFeedbackVO
	 * @return
	 */
	@RequestMapping(value = "/saveMsiSurveyFeedback",method = RequestMethod.POST,produces = "application/json; charset=UTF-8" )
	public ResultEntity saveMsiSurveyFeedback(@RequestBody MsiSurveyFeedbackVO msiSurveyFeedbackVO){
		MsiSurveyFinishCount msiSurveyFinishCount = msiSurveyFeedbackService.getMsiSurveyFeedbackCountByStoreIdAnd(msiSurveyFeedbackVO.getClientId(),msiSurveyFeedbackVO.getStoreId(),msiSurveyFeedbackVO.getMsiSurveyId());
		if(msiSurveyFinishCount.getCount()>=2){
			ResultEntity resultEntity = new ResultEntity(false);
			resultEntity.setResultMSG("该问卷已完成2份");
			return resultEntity;
		}
		if(msiSurveyFeedbackVO != null){
			if(StringUtil.isEmptyString(msiSurveyFeedbackVO.getStoreId())){
				ResultEntity resultEntity = new ResultEntity(false);
				resultEntity.setResultMSG("门店参数不能为空");
				return resultEntity;
			}
			if(msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails()==null || msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails().size()<0){
				ResultEntity resultEntity = new ResultEntity(false);
				resultEntity.setResultMSG("没有填写数据");
				return resultEntity;
			}
		}
		msiSurveyFeedbackService.addAppleExamSurveyFeedback(msiSurveyFeedbackVO.getClientId(),msiSurveyFeedbackVO.getClientUserId(),msiSurveyFeedbackVO);
		return new ResultEntity(true);
	}
	
	/**
	 * 获取苹果考试问卷完成次数
	 * @param clientId
	 * @param storeId
	 * @return
	 */
	@RequestMapping(value = "/getMsiSurveyFinishCountByStoreId", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithList<MsiSurveyFinishCount> getMsiSurveyFeedbackCountByStoreId(Integer clientId,String storeId){
		if(clientId == null || StringUtil.isEmptyString(storeId)){
			ResultEntityWithList<MsiSurveyFinishCount> resultEntity = new ResultEntityWithList<MsiSurveyFinishCount>(false);
			resultEntity.setResultMSG("参数绑定错误");
			return resultEntity;
		}
		List<MsiSurveyFinishCount> msiSurveyFinishCounts = msiSurveyFeedbackService.getMsiSurveyFeedbackCountByStoreId(clientId,storeId);
		ResultEntityWithList<MsiSurveyFinishCount> resultEntity = new ResultEntityWithList<MsiSurveyFinishCount>(true);
		resultEntity.setDataInfo(msiSurveyFinishCounts);
		return resultEntity;
	} 
	
}
