package cn.mobilizer.channel.survey.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiSurveyParameter;

@Repository
public class MsiSurveyParameterDao extends MyBatisDao{
    
	public MsiSurveyParameterDao() {
		super("MSI_SURVEY_PARAMETER");
	}
	
	public int deleteByPrimaryKey(Integer msiSurveyParameterId){
		return super.delete("deleteByPrimaryKey", msiSurveyParameterId);
	}

    public int insert(MsiSurveyParameter record){
    	return super.insert("insert", record);
    }

    public int insertSelective(MsiSurveyParameter record){
    	return super.insert("insertSelective", record);
    }

    public MsiSurveyParameter selectByPrimaryKey(Integer msiSurveyParameterId){
    	return super.get("selectByPrimaryKey", msiSurveyParameterId);
    }

    public int updateByPrimaryKeySelective(MsiSurveyParameter record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(MsiSurveyParameter record){
    	return super.update("updateByPrimaryKey", record);
    }
}