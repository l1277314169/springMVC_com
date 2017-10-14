package cn.mobilizer.channel.wrTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.wrTask.po.WrTask;

@Repository
public class WrTaskDao extends MyBatisDao{
	
	public WrTaskDao() {
		super("WR_TASK");
	}
	
	public List<WrTask> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
	
    public int deleteByPrimaryKey(Integer wrTaskId){
		return super.delete("deleteByPrimaryKey", wrTaskId);
	}

    public int insert(WrTask record){
    	return super.insert("insert", record);
    }

    public int insertSelective(WrTask record){
    	return super.insert("insertSelective", record);
    }

    public WrTask selectByPrimaryKey(Integer wrTaskId){
    	return super.get("selectByPrimaryKey", wrTaskId);
    }

    public int updateByPrimaryKeySelective(WrTask record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(WrTask record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public List<WrTask> getWrTaskList(Map<String, Object> params){
    	return super.queryForList("getWrTaskList",params);
    }
    
    
}