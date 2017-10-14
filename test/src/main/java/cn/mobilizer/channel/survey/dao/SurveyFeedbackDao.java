package cn.mobilizer.channel.survey.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.SurveyFeedback;

@Repository
public class SurveyFeedbackDao extends MyBatisDao{
	
	public SurveyFeedbackDao(){
		super("SURVEYFEEDBACK");
	}
	
	public void insert(SurveyFeedback surveyFeedback){
		super.insert("insert", surveyFeedback);
	}
	
	public void updateByPrimaryKeySelective(SurveyFeedback surveyFeedback){
		super.update("updateByPrimaryKeySelective", surveyFeedback);
	}
	
	public void insertSelective(SurveyFeedback surveyFeedback){
		super.insert("insertSelective", surveyFeedback);
	}
	
	public SurveyFeedback selectByPrimaryKey(String feedbackId){
		return super.get("selectByPrimaryKey", feedbackId);
	}
	
	public SurveyFeedback getSurveyFeedbackByStoreId(String storeId){
		return super.get("getSurveyFeedbackByStoreId", storeId);
	}
	
	public void batchInsertSurveyFeedback(List<SurveyFeedback> surveyFeedbacks){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("surveyFeedbacks", surveyFeedbacks);
		super.insert("batchInsertSurveyFeedback", params);
	}
	
	public SurveyFeedback selectSurveyFeedbackByNo(String feedbackNo){
		return super.get("selectSurveyFeedbackByNo", feedbackNo);
	}
	
	public List<SurveyFeedback> selectSurveyFeedbackByStoreNoAndDate(Map<String, Object> params){
		return super.getList("selectSurveyFeedbackByStoreNoAndDate", params);
	}
	
	public List<SurveyFeedback> selectSurveyFeedbackByDate(Map<String, Object> params){
		return super.getList("selectSurveyFeedbackByDate", params);
	}
	
	public SurveyFeedback selectOneSurveyFeedbackByStoreNoAndDate(Map<String, Object> params){
		return super.get("selectOneSurveyFeedbackByStoreNoAndDate", params);
	}
}