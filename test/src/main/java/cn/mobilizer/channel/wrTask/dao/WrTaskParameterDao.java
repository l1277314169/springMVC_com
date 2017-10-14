package cn.mobilizer.channel.wrTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.wrTask.po.WrTaskParameter;

@Repository
public class WrTaskParameterDao extends MyBatisDao{
	
	public WrTaskParameterDao(){
		super("WR_TASK_PARAMETER");
	}
	
	public List<WrTaskParameter> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
	
    public int deleteByPrimaryKey(Integer wrTaskParameterId){
    	return super.delete("deleteByPrimaryKey", wrTaskParameterId);
    }

    public int insert(WrTaskParameter record){
    	return super.insert("insert", record);
    }

    public int insertSelective(WrTaskParameter record){
    	return super.insert("insertSelective", record);
    }

    public WrTaskParameter selectByPrimaryKey(Integer wrTaskParameterId){
    	return super.get("selectByPrimaryKey", wrTaskParameterId);
    }

    public int updateByPrimaryKeySelective(WrTaskParameter record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(WrTaskParameter record){
    	return super.update("updateByPrimaryKey", record);
    }
}