package cn.mobilizer.channel.promotion.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.promotion.po.SurveyStoreDisplay;

@Repository
public class SurveyStoreDisplayDao extends MyBatisDao{
	
	public SurveyStoreDisplayDao() {
		super("SURVEY_STORE_DISPLAY");
	}
	
    public int deleteByPrimaryKey(Integer dataId){
    	return super.delete("deleteByPrimaryKey", dataId);
    };

    public int insert(SurveyStoreDisplay record){
    	return super.insert("insert", record);
    }

    public int insertSelective(SurveyStoreDisplay record){
    	return super.insert("insertSelective", record);
    }

    public SurveyStoreDisplay selectByPrimaryKey(Integer dataId){
    	return super.get("selectByPrimaryKey", dataId);
    }

    public int updateByPrimaryKeySelective(SurveyStoreDisplay record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(SurveyStoreDisplay record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public SurveyStoreDisplay selectEntityByPlanCode(Integer clientId,String planCode){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("clientId", clientId);
    	params.put("planCode", planCode);
    	return super.get("selectEntityByPlanCode", params);
    }
    
    public Integer selectCountByPlancode(Integer clientId,String planCode){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("clientId", clientId);
    	params.put("planCode", planCode);
    	return super.get("selectCountByPlancode", params);
    }
    
    public void batchSaveSurveyStoreDisplay(List<SurveyStoreDisplay> surveyStoreDisplays){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("surveyStoreDisplays", surveyStoreDisplays);
    	super.insert("batchSaveSurveyStoreDisplay", params);
    }
    
    public List<SurveyStoreDisplay> selectByParams(Map<String,Object> params){
    	return super.queryForList("selectByParams",params);
    }
    
    public Integer selectByParamCount(Map<String,Object> params){
    	return super.get("selectByParamCount",params);
    }
    
    public void updateDisplayType(Map<String, Object> params){
    	super.update("updateDisplayType", params);
    }
}