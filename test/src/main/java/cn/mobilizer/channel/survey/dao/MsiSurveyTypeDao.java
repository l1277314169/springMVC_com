package cn.mobilizer.channel.survey.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.survey.po.MsiSurveyType;

@Repository
public class MsiSurveyTypeDao extends MyBatisDao{
	
	public MsiSurveyTypeDao(){
		super("MSI_SURVEY_TYPE");
	}
	
    public int deleteByPrimaryKey(Integer msiSurveyTypeId){
    	return super.delete("deleteByPrimaryKey", msiSurveyTypeId);
    }

    public int insert(MsiSurveyType record){
    	return super.insert("insert", record);
    }

    public int insertSelective(MsiSurveyType record){
    	return super.insert("insertSelective", record);
    }

    public MsiSurveyType selectByPrimaryKey(Integer msiSurveyTypeId){
    	return super.get("selectByPrimaryKey", msiSurveyTypeId);
    }

    public int updateByPrimaryKeySelective(MsiSurveyType record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(MsiSurveyType record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public MsiSurveyType findByMsiSurveyTypeByStoreId(Integer clientId,String storeId){
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("clientId", clientId);
    	params.put("storeId", storeId);
    	return super.get("findByMsiSurveyTypeByStoreId", params);
    }
}