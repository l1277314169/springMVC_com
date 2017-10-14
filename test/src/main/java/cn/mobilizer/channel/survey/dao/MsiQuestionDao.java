/**
 * 
 */
package cn.mobilizer.channel.survey.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiQuestion;
import cn.mobilizer.channel.survey.vo.MsiQuestionVO;

@Repository
public class MsiQuestionDao extends MyBatisDao {
	
	public MsiQuestionDao() {
		super("MSI_QUESTION");
	}
	
	public List<MsiQuestionVO> getMsiQuestionListByMsiSurveyId(Integer msiSurveyId) {
		return super.getList("getMsiQuestionListByMsiSurveyId", msiSurveyId);
	}
	
	public List<MsiQuestionVO> getMsiQuestionsByMsiSurveyIdAndParentId(Map<String, Object> params){
		return super.getList("getMsiQuestionsByMsiSurveyIdAndParentId", params);
	}
	
	public List<MsiQuestionVO> getMsiQuestionListWithChecked(Map<String, Object> params) {
		return super.getList("getMsiQuestionListWithChecked", params);
	}
	
	public List<MsiQuestionVO> getAppleMsiQuestionListWithChecked(Map<String, Object> params){
		return super.getList("getAppleMsiQuestionListWithChecked", params);
	}
	
	public List<MsiQuestion> getAppleExamMsiQuestionByMsiSurveyId(Integer msiSurveyId){
		return super.getList("getAppleExamMsiQuestionByMsiSurveyId", msiSurveyId);
	}
	
	public List<MsiQuestionVO> getAppleExamMsiQuestionAndAnswerByMsiSurveyId(Integer msiSurveyId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("msiSurveyId", msiSurveyId);
		return super.getList("getAppleExamMsiQuestionAndAnswerByMsiSurveyId", params);
	}
	
	public Integer getAppleExamMsiQuestionCountByMsiSurveyId(Integer msiSurveyId){
		return super.get("getAppleExamMsiQuestionCountByMsiSurveyId", msiSurveyId);
	}
	
	public String getMsiQuestionIdsByFeedbackId(String feedbackId){
		return super.get("getMsiQuestionIdsByFeedbackId", feedbackId);
	}
	
	public List<MsiQuestionVO> getMsiQuestionListWithCheckedByIds(Map<String,Object> params){
		return super.getList("getMsiQuestionListWithCheckedByIds", params);
	}
}
