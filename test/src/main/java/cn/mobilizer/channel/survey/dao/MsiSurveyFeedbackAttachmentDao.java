/**
 * 
 */
package cn.mobilizer.channel.survey.dao;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackAttachment;

@Repository
public class MsiSurveyFeedbackAttachmentDao extends MyBatisDao {
	
	public MsiSurveyFeedbackAttachmentDao() {
		super("MSI_SURVEY_FEEDBACK_ATTACHMENT");
	}
	
	public Integer insertMsiSurveyFeedbackAttachmentByArray(Map<String, Object> params) {
		return super.insert("insertMsiSurveyFeedbackAttachmentByArray", params);
	}
	
	public Integer deleteByFeedbackId(Map<String,Object> params) {
		return super.update("deleteByFeedbackId", params);
	}
	
	public MsiSurveyFeedbackAttachment getMsiSurveyFeedbackAttachmentByParams(Map<String, Object> param){
		List<MsiSurveyFeedbackAttachment> ls = super.queryForList("getMsiSurveyFeedbackAttachmentByParams", param);
		if(ls != null && ls.size() > 0 ){
			return ls.get(0);
		}
		return null;
	}
	
	public Integer insertMsiSurveyFeedbackAttachment(MsiSurveyFeedbackAttachment msiSurveyFeedbackAttachment) {
		return super.insert("insertSelective", msiSurveyFeedbackAttachment);
	}
	
	public Integer updateMsiSurveyFeedbackAttachment(MsiSurveyFeedbackAttachment msiSurveyFeedbackAttachment) {
		return super.update("updateByPrimaryKeySelective", msiSurveyFeedbackAttachment);
	}
	
	public List<MsiSurveyFeedbackAttachment> getMsiSurveyFeedbackAttachmentList(Map<String, Object> param) {
		 return super.queryForList("getMsiSurveyFeedbackAttachmentList", param);
	}
}
