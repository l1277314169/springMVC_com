package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.SurveyObject;

@Repository
public class SurveyObjectDao extends MyBatisDao {
 
	public SurveyObjectDao(){
		super("SURVEYOBJECT");
	}
	
	public List<SurveyObject> selectBySubSurveyId(Map<String, Object> params){
		return super.queryForList("selectBySubSurveyId",params);
	}
	
	public List<SurveyObject> selectObjectBySubSurveyId(Map<String, Object> params){
		return super.queryForList("selectObjectBySubSurveyId",params);
	}
	
	public List<SurveyObject> selectSurveyObject(Map<String, Object> params){
		return super.queryForList("selectSurveyObject",params);
	}
	
	public List<SurveyObject> selectSurveyObjectList(Map<String, Object> params){
		return super.queryForList("selectSurveyObjectList",params);
	}
	
	public List<SurveyObject> selectSurveyObjectByObjectNo(Map<String, Object> params){
		return super.queryForList("selectSurveyObjectByObjectNo",params);
	}
	
	public List<SurveyObject> selectSurveyObjectByNameAndGroupId(Map<String,Object> params){
		return super.queryForList("selectSurveyObjectByNameAndGroupId",params);
	}
	
	public List<SurveyObject> selectSurveyObjectBySubSurveyId(Map<String, Object> params){
		return super.queryForList("selectSurveyObjectBySubSurveyId",params);
	}
	
	public List<SurveyObject> selectAllSurveyObjectsByClientId(Integer clientId){
		return super.getList("selectAllSurveyObjectsByClientId",clientId);
	}
}