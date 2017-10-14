package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.SurveyParameter;

@Repository
public class SurveyParameterDao extends MyBatisDao{
    
	public SurveyParameterDao(){
		super("SURVEYPARAMETER");
	}
	
	public List<SurveyParameter> selectBysubSurveyId(Map<String, Object> params){
		return super.getList("selectBysubSurveyId",params);
	}
	
}