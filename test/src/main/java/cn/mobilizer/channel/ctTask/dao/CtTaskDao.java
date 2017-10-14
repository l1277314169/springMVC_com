package cn.mobilizer.channel.ctTask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.ctTask.po.CtTask;
import cn.mobilizer.channel.ctTask.vo.StoreTask;

@Repository
public class CtTaskDao extends MyBatisDao{
	public CtTaskDao() {
		super("CT_TASK");
	}
	
	public List<CtTask> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}

	
	public CtTask selectByPrimaryKey(Integer ctTaskId){
		return super.get("selectByPrimaryKey",ctTaskId);
	}
	
	public List<CtTask> selectTaskList(Map<String, Object> params){		
		return super.queryForList("selectTaskList",params);
	}
	
	public int insert(CtTask ctTask){
		super.insert ("insert", ctTask);
		return ctTask.getCtTaskId();
	} 
	
	public List<CtTask> selectTaskByPosition(Map<String,Object> params){
		return super.queryForList("selectTaskByPosition",params);
	}
	
	public List<StoreTask> selectTaskStoreByClinetPositionId(Map<String, Object> params){
		return super.queryForList("selectTaskStoreByClinetPositionId",params);
	}
	
	public Integer selectTaskStoreCountByPositionId(Map<String, Object> params){
		return super.get("selectTaskStoreCountByPositionId", params);
	}
	
	public List<StoreTask> selectTaskDataStore(Map<String, Object> params){
		return super.queryForList("selectTaskDataStore", params);
	}
	
	public List<StoreTask> selectTaskDataStoreForDay(Map<String, Object> params){
		return super.queryForList("selectTaskDataStoreForDay", params);
	}
	
	public Integer selectTaskDataStoreCount(Map<String, Object> params){
		return super.get("selectTaskDataStoreCount", params);
	}
	
	public Integer selectTaskDataStoreDayCount(Map<String, Object> params){
		return super.get("selectTaskDataStoreDayCount", params);
	}
	
	public List<?> exportCtTaskData(Map<String, Object> params){
		List<?> list = super.getList("exportCtTaskData",params);
		return list;
	}
	
	public List<StoreTask> selectTaskDataStoreForReportData(Map<String, Object> params){
		return super.queryForList("selectTaskDataStoreForReportData",params);
	}
	
	public Integer selectTaskDataStoreCountForReportData(Map<String, Object> params){
		return super.get("selectTaskDataStoreCountForReportData",params);
	}
	
	public List<StoreTask> selectTaskDataStoreDayForReportData(Map<String, Object> params){
		return super.queryForList("selectTaskDataStoreDayForReportData",params);
	}
	
	public List<StoreTask> selectTaskDataStoreForDay2(Map<String, Object> params){
		return super.queryForList("selectTaskDataStoreForDay2", params);
	}
	
	public Integer selectTaskDataStoreDayCount2(Map<String, Object> params){
		return super.get("selectTaskDataStoreDayCount2", params);
	}
	
	
}