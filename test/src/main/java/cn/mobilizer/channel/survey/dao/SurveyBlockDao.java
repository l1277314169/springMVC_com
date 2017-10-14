package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.SurveyBlock;

@Repository
public class SurveyBlockDao extends MyBatisDao {

	public SurveyBlockDao(){
		super("SURVEYBLOCK");
	}
	
	public List<SurveyBlock> selectBySurveyId(Map<String, Object> params){
		
		return super.queryForList("selectBySurveyId",params);
	}
	
	public SurveyBlock selectByPrimaryKey(Map<String, Object> params){
		
		return super.get("selectByPrimaryKey",params);
	}
	
}
