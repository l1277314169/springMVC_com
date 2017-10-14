/**
 * 
 */
package cn.mobilizer.channel.survey.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackDetail;

@Repository
public class MsiSurveyFeedbackDetailDao extends MyBatisDao {
	
	public MsiSurveyFeedbackDetailDao() {
		super("MSI_SURVEY_FEEDBACK_DETAIL");
	}
	
	public Integer insertMsiSurveyFeedbackDetailBylist(List<MsiSurveyFeedbackDetail> list) {
		return super.insert("insertMsiSurveyFeedbackDetailBylist", list);
	}
	
	public List<MsiSurveyFeedbackDetail> findMsiSurveyFeedbackDetailListByFeedbackId(String feedbackId) {
		return super.queryForList("findMsiSurveyFeedbackDetailListByFeedbackId", feedbackId);
	}
	
	public Integer insertMsiSurveyFeedback(MsiSurveyFeedbackDetail msiSurveyFeedbackDetail) {
		return super.insert("insertSelective", msiSurveyFeedbackDetail);
	}
	
	public Integer updateMsiSurveyFeedbackDetail(MsiSurveyFeedbackDetail msiSurveyFeedbackDetail) {
		return super.update("updateByPrimaryKeySelective", msiSurveyFeedbackDetail);
	}
	
	public Integer deleteByFeedbackId(String feedbackId) {
		return super.update("deleteByFeedbackId", feedbackId);
	}
	
	public MsiSurveyFeedbackDetail getMsiSurveyFeedbackDetailByParams(Map<String, Object> param){
		List<MsiSurveyFeedbackDetail> ls = super.queryForList("getMsiSurveyFeedbackDetailByParams", param);
		if(ls != null && ls.size() > 0 ){
			return ls.get(0);
		}
		return null;
	}
	
}
