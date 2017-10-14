package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.visit.po.VisitingTask;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

/**
 * 
 * @author yeeda.tian
 *
 */
@Repository
public class VisitingTaskDao extends MyBatisDao {
	
	public VisitingTaskDao() {
		super("VISITING_TASK");
	}
	
	/**
	 * 
	 * @param param
	 * @return Integer 总记录数
	 */
	public Integer queryVisitingTaskCount(Map<String, Object> params) {
		return super.get("queryTotalCount", params);
	}
	
	public List<VisitingTask> queryVisitingTaskList(Map<String, Object> params){
		return super.queryForList("findListByParams", params);
	}
	
	public int insert(VisitingTask visitingTask){
		super.insert ("insertSelective", visitingTask);
		int result = visitingTask.getVisitingTaskId ();
		return result;
	}
	
	public int delete(Integer visitingTaskId){
		return super.delete ("deleteByPrimaryKey", visitingTaskId);
	}
	
	public int update(VisitingTask visitingTask){
		return super.update ("updateByPrimaryKeySelective", visitingTask);
	}
	
	public VisitingTask fingVisitingTaskById(Integer id){
		return super.get("selectByPrimaryKey",id);
	}
	
	public List<VisitingTask> getTasksByStoreTaskMapping(Map<String, Object> params){
		return super.queryForList("getTasksByStoreTaskMapping",params);
	}
	
	public List<VisitingTask> getTasksByStoreTaskGroup(Map<String, Object> params){
		return super.queryForList("getTasksByStoreTaskGroup",params);
	}
	
	public List<VisitingTask> getTasksByChannel(Map<String, Object> params){
		return super.queryForList("getTasksByChannel",params);
	}
	
	public List<VisitingTask> getTasksByClientPosition(Map<String, Object> params){
		return super.queryForList("getTasksByClientPosition",params);
	}
	
	public List<VisitingTask> getTasksByClientPositionAndRoles(Map<String, Object> params){
		return super.queryForList("getTasksByClientPositionAndRoles",params);
	}
}
