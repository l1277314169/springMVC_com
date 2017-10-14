package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.Survey;
import cn.mobilizer.channel.survey.vo.SurveyImageVo;
import cn.mobilizer.channel.survey.vo.SurveyListVo;

@Repository
public class SurveyDao extends MyBatisDao {
   
	public SurveyDao(){
		super("SURVEY");
	}
	
	public List<SurveyListVo> selectSurveyListVo(Map<String, Object> parameters){
		return super.getList("selectSurveyListVo",parameters);
	}
	
	public Integer selectAllItems(Map<String, Object> parameters){
		return super.get("selectAllItems",parameters);
	}
	
	public List<Survey> getSurveyByName(Map<String, Object> parameters){
		return super.getList("getSurveyByName",parameters);
	}
	
	public Survey getSurvey(Map<String, Object> parameters){
		return super.get("selectByPrimaryKey",parameters);
	}
	
	public Survey getNewSurvey(Integer clientId){
		return super.get("getNewSurvey",clientId);
	}
	
	public Integer getSurveyCycle(Map<String, Object> params){
		return super.get("getSurveyCycle",params);
	}
	
	public List<SurveyImageVo> selectSurveyImages(Map<String, Object> params){
		return super.getList("selectSurveyImages",params);
	}
}