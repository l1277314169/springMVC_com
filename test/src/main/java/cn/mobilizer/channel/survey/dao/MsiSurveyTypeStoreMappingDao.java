package cn.mobilizer.channel.survey.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiSurveyTypeStoreMapping;

@Repository
public class MsiSurveyTypeStoreMappingDao extends MyBatisDao{
	
	public MsiSurveyTypeStoreMappingDao(){
		super("MSI_SURVEY_TYPE_STORE_MAPPING");
	}
	
    public int deleteByPrimaryKey(Integer mappingId){
    	return super.delete("deleteByPrimaryKey", mappingId);
    }

    public int insert(MsiSurveyTypeStoreMapping record){
    	return super.insert("insert", record);
    }

    public int insertSelective(MsiSurveyTypeStoreMapping record){
    	return super.insert("insertSelective", record);
    }

    public MsiSurveyTypeStoreMapping selectByPrimaryKey(Integer mappingId){
    	return super.get("selectByPrimaryKey", mappingId);
    }

    public int updateByPrimaryKeySelective(MsiSurveyTypeStoreMapping record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(MsiSurveyTypeStoreMapping record){
    	return super.update("updateByPrimaryKey", record);
    }
}