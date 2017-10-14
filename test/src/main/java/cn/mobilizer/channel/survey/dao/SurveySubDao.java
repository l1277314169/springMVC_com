package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.SurveySub;

@Repository
public class SurveySubDao extends MyBatisDao{
   
	public SurveySubDao(){
		super("SURVEYSUB");
	}
	
	public List<SurveySub> getSurveySubByBlockId(Map<String, Object> params){
		return super.queryForList("getSurveySubByBlockId",params);
	}
	
	public List<SurveySub> getSurveySubBySurveyId(Map<String, Object> params){
		return super.queryForList("getSurveySubBySurveyId",params);
	}
	
	public List<String> getSurveySubIdByBlockId(Map<String,Object> params){
		return super.queryForList("getSurveySubIdByBlockId",params);
	}
	
}