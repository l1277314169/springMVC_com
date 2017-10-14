package cn.mobilizer.channel.survey.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiQuestionGroup;

@Repository
public class MsiQuestionGroupDao extends MyBatisDao{
	
	public MsiQuestionGroupDao() {
		super("MSI_QUESTION_GROUP");
	}
	
    public int deleteByPrimaryKey(Integer questionGroupId){
    	return super.delete("deleteByPrimaryKey", questionGroupId);
    }

    public int insert(MsiQuestionGroup record){
    	return super.insert("insert", record);
    }

    public int insertSelective(MsiQuestionGroup record){
    	return super.insert("insertSelective", record);
    }

    public MsiQuestionGroup selectByPrimaryKey(Integer questionGroupId){
    	return super.get("selectByPrimaryKey", questionGroupId);
    }

    public int updateByPrimaryKeySelective(MsiQuestionGroup record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(MsiQuestionGroup record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public List<MsiQuestionGroup> findEntitysByMsiSurveyId(Map<String,Object> params){
    	return super.queryForList("findEntitysByMsiSurveyId", params);
    }
}