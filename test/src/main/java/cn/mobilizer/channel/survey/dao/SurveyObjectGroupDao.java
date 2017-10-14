package cn.mobilizer.channel.survey.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.SurveyObjectGroup;

@Repository
public class SurveyObjectGroupDao  extends MyBatisDao{
   
	public SurveyObjectGroupDao(){
		super("SURVEYOBJECTGROUP");
	}
	
	public List<SurveyObjectGroup> selectSurveyObjectBySubSurveyId(Integer subSurveyId){
		return super.queryForList("selectSurveyObjectBySubSurveyId",subSurveyId);
	}
	
}