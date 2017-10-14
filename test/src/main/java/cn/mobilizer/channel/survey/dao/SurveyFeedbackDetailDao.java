package cn.mobilizer.channel.survey.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.SurveyFeedbackDetail;

@Repository
public class SurveyFeedbackDetailDao extends MyBatisDao {

	public SurveyFeedbackDetailDao(){
		super("SURVEYFEEDBACKDETAIL");
	}
	
	public Integer batchInsert(List<SurveyFeedbackDetail> details){
		return super.insert("batchInsert", details);
	}
	
	public List<SurveyFeedbackDetail> getSurveyFeedbackDetail(Map<String, Object> params){
		return super.getList("getSurveyFeedbackDetail",params);
	}
	
	
	public void deleteSurveyFeedbackDetail(Map<String, Object> params){
		super.delete("deleteSurveyFeedbackDetail", params);
	}
	
	public void deleteByFeedbackId(String feedbackId){
		super.update("deleteByFeedbackId", feedbackId);
	}
	
	public List<SurveyFeedbackDetail> selectSurveyFeedbackDetailList(Map<String,Object> params){
		return super.getList("selectSurveyFeedbackDetailList",params);
	}
	
	public void batchInsertSurveyFeedbackDetail(List<SurveyFeedbackDetail> surveyFeedbackDetails){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("surveyFeedbackDetails", surveyFeedbackDetails);
		super.insert("batchInsertSurveyFeedbackDetail", params);
	}
}