/**
 * 
 */
package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiSurvey;

@Repository
public class MsiSurveyDao extends MyBatisDao {
	
	public MsiSurveyDao() {
		super("MSI_SURVEY");
	}
	
	public List<MsiSurvey> findMsiSurveyListByStore(Map<String, Object> params) {
		return super.getList("findMsiSurveyListByStore", params);
	}
	
	public List<MsiSurvey> findHistoryMsiSurveyListByStore(Map<String,Object> params){
		return super.queryForList("findHistoryMsiSurveyListByStore",params);
	}
	
	public List<MsiSurvey> findAppleMsiSurveyListByStore(Map<String,Object> params){
		return super.queryForList("findAppleMsiSurveyListByStore",params);
	}
	
	public MsiSurvey selectByPrimaryKey(Integer msiSurveyId){
		return super.get("selectByPrimaryKey",msiSurveyId);
	}
	
	public List<MsiSurvey> findAppleExamMsiSurveyByStoreAndType(Map<String, Object> params){
		return super.getList("findAppleExamMsiSurveyByStoreAndType", params);
	}
	
	public List<MsiSurvey> findAppleMsiSurveyListByType(Map<String, Object> params){
		return super.getList("findAppleMsiSurveyListByType", params);
	}
}
