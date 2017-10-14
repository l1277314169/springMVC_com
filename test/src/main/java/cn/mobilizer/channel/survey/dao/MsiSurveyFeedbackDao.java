/**
 * 
 */
package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedback;
import cn.mobilizer.channel.survey.po.MsiSurveyFinishCount;

@Repository
public class MsiSurveyFeedbackDao extends MyBatisDao {
	
	public MsiSurveyFeedbackDao() {
		super("MSI_SURVEY_FEEDBACK");
	}
	
	public Integer queryMsiSurveyFeedbackCount(Map<String, Object> params){
		return super.get("queryTotalCount", params);
	}
	
	public List<MsiSurveyFeedback> queryMsiSurveyFeedbackList(Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}
	
	public String addMsiSurveyFeedback(MsiSurveyFeedback msiSurveyFeedback) {
		super.insert("insertSelective", msiSurveyFeedback);
		return msiSurveyFeedback.getFeedbackId();
	}
	
	public MsiSurveyFeedback findMsiSurveyFeedbackById(String feedbackId) {
		return super.get("findByPrimaryKey", feedbackId);
	}
	
	public Integer updateMsiSurveyFeedback(MsiSurveyFeedback msiSurveyFeedback) {
		return super.update("updateByPrimaryKeySelective", msiSurveyFeedback);
	}
	
	public List<MsiSurveyFeedback> getEntityByCycleDate(Map<String, Object> params){
		return super.queryForList("getEntityByCycleDate",params);
	}
	
	public int deleteByPrimaryKey(String feedBackId){
		return super.update("deleteByPrimaryKey", feedBackId);
	}
	
	public List<?> exportMsiSurveyFeedback(Map<String, Object> params){
		List<?> list = super.getList("exportMsiSurveyFeedback",params);
		return list;
	}
	
	public String getMsiSurveyIdsByparams(Integer clientId){
		return super.get("getMsiSurveyIdsByparams",clientId);
	}
	
	public MsiSurveyFinishCount getMsiSurveyFeedbackCountByStoreId(Map<String,Object> params){
		return super.get("getMsiSurveyFeedbackCountByStoreId",params);
	}
	
	public MsiSurveyFeedback findApprovalDataByStoreIdAndDataType(Map<String, Object> params){
		return super.get("findApprovalDataByStoreIdAndDataType", params);
	}
	
	public MsiSurveyFeedback findMsiSurveyFeedbackByStoreId(Map<String, Object> params){
		return super.get("findMsiSurveyFeedbackByStoreId", params);
	}
	
	public List<MsiSurveyFeedback> findAppleExamFeedbackByStoreId(Map<String, Object> params){
		return super.queryForList("findAppleExamFeedbackByStoreId", params);
	}
	
	public Integer queryAppleTotalCount(Map<String, Object> params){
		return super.get("queryAppleTotalCount", params);
	}
	
	public List<MsiSurveyFeedback> findAppleListByParams(Map<String, Object> params) {
		return super.queryForList("findAppleListByParams", params);
	}
}
