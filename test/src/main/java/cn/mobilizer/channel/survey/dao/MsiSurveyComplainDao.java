/**
 * 
 */
package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiSurveyComplain;

@Repository
public class MsiSurveyComplainDao extends MyBatisDao {
	
	public MsiSurveyComplainDao() {
		super("MSI_SURVEY_COMPLAIN");
	}
	
	public String insertSelective(MsiSurveyComplain MsiSurveyComplain) {
		super.insert("insertSelective", MsiSurveyComplain);
		return MsiSurveyComplain.getComplainId();
	}
	
	public Integer updateMsiSurveyComplain(MsiSurveyComplain MsiSurveyComplain) {
		return super.update("updateByPrimaryKeySelective", MsiSurveyComplain);
	}
	
	public List<MsiSurveyComplain> getEntityByFeedbackId(String feedbackId) {
		return super.queryForList("getEntityByFeedbackId", feedbackId);
	}
	
	public MsiSurveyComplain getEntityByFeedbackIdAndMsiQuestionId(Map<String,Object> params) {
		return super.get("getEntityByFeedbackIdAndMsiQuestionId", params);
	}
	
	public MsiSurveyComplain selectByPrimaryKey(String complainId){
		return super.get("selectByPrimaryKey", complainId);
	}
	
}
