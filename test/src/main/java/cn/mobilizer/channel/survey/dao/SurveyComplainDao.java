package cn.mobilizer.channel.survey.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.SurveyComplain;

@Repository
public class SurveyComplainDao extends MyBatisDao{
	
	public SurveyComplainDao() {
		super("SURVEY_COMPLAIM");
	}
	
	public int deleteByPrimaryKey(String complainId){
		return super.delete("deleteByPrimaryKey", complainId);
	}

    public int insert(SurveyComplain record){
    	return super.insert("insert", record);
    }

    public int insertSelective(SurveyComplain record){
    	return super.insert("insertSelective", record);
    }

    public SurveyComplain selectByPrimaryKey(String complainId){
    	return super.get("selectByPrimaryKey", complainId);
    }

    public int updateByPrimaryKeySelective(SurveyComplain record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(SurveyComplain record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public SurveyComplain getEntityByFeedbackId(String feedbackId){
    	return super.get("getEntityByFeedbackId",feedbackId);
    }
}