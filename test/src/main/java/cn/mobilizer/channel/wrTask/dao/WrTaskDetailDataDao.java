package cn.mobilizer.channel.wrTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.wrTask.po.WrTaskDetailData;

@Repository
public class WrTaskDetailDataDao extends MyBatisDao{
	
	public WrTaskDetailDataDao(){
		super("WR_TASK_DETAIL_DATA");
	}
	
	public List<WrTaskDetailData> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
	
    public int deleteByPrimaryKey(String wrTaskDetailId){
    	return super.delete("deleteByPrimaryKey", wrTaskDetailId);
    }

    public int insert(WrTaskDetailData record){
    	return super.insert("insert", record);
    }

    public int insertSelective(WrTaskDetailData record){
    	return super.insert("insertSelective", record);
    }

    public WrTaskDetailData selectByPrimaryKey(String wrTaskDetailId){
    	return super.get("selectByPrimaryKey", wrTaskDetailId);
    }

    public int updateByPrimaryKeySelective(WrTaskDetailData record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(WrTaskDetailData record){
    	return super.update("updateByPrimaryKey", record);
    }
}