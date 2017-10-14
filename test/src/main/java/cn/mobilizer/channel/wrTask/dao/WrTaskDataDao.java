package cn.mobilizer.channel.wrTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.wrTask.po.WrTaskData;
import cn.mobilizer.channel.wrTask.vo.WrTaskFinishCount;

@Repository
public class WrTaskDataDao extends MyBatisDao{
	
	public WrTaskDataDao(){
		super("WR_TASK_DATA");
	}
	
	public List<WrTaskData> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
	
    public int deleteByPrimaryKey(String wrTaskDataId){
    	return super.delete("deleteByPrimaryKey", wrTaskDataId);
    }

    public int insert(WrTaskData record){
    	return super.insert("insert", record);
    }

    public int insertSelective(WrTaskData record){
    	return super.insert("insertSelective", record);
    }

    public WrTaskData selectByPrimaryKey(String wrTaskDataId){
    	return super.get("selectByPrimaryKey", wrTaskDataId);
    }

    public int updateByPrimaryKeySelective(WrTaskData record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(WrTaskData record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public List<WrTaskFinishCount> getWrTaskDataByUserId(Map<String, Object> parames){
    	return super.queryForList("getWrTaskDataByUserId", parames);
    }
    
    public Integer getWrTaskDataCountByUserId(Map<String, Object> parames){
    	return super.get("getWrTaskDataCountByUserId", parames);
    }
}