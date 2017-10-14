package cn.mobilizer.channel.ctTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.ctTask.po.CtTaskData;

@Repository
public class CtTaskDataDao extends MyBatisDao{
	public CtTaskDataDao() {
		super("CT_TASK_DATA");
	}
	
	public List<CtTaskData> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
	
	public String insert(CtTaskData ctTaskData){
		super.insert ("insert", ctTaskData);
		return ctTaskData.getCtTaskDataId();
	}
	
	public String insertColgateCtTaskData(CtTaskData ctTaskData){
		super.insert ("insertColgateCtTaskData", ctTaskData);
		return ctTaskData.getCtTaskDataId();
	}
	
	public CtTaskData selectByPrimaryKey(String id){
		return super.get("selectByPrimaryKey",id);
	}
	
	public CtTaskData selectDataByPopIdAndTaskId(Map<String, Object> parames){
		return super.get("selectDataByPopIdAndTaskId", parames);
	}
	
	public int update(CtTaskData ctTaskData){
		return super.update("updateByPrimaryKeySelective", ctTaskData); 
	}
	
	public int deleteByPrimaryKey(String ctTaskDataId){
		return super.update("deleteByPrimaryKey", ctTaskDataId);
	}
}