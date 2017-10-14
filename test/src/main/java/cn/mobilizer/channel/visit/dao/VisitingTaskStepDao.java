package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.visit.po.VisitingTaskStep;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

/**
 * 
 * @author yeeda.tian
 *
 */
@Repository
public class VisitingTaskStepDao extends MyBatisDao {
	
	public VisitingTaskStepDao() {
		super("VISITING_TASK_STEP");
	}
	
	public List<VisitingTaskStep> queryVisitingTaskStepList(Map<String, Object> params){
		return super.queryForList("findListByParams", params);
	}
	
	public int insert(VisitingTaskStep visitingTaskStep){
		super.insert ("insertSelective", visitingTaskStep);
		int result = visitingTaskStep.getVisitingTaskStepId ();
		return result;
	}
	
	public int delete(Integer visitingTaskStepId){
		return super.delete ("deleteByPrimaryKey", visitingTaskStepId);
	}
	
	public int update(VisitingTaskStep visitingTaskStep){
		return super.update ("updateByPrimaryKeySelective", visitingTaskStep);
	}
	
	public List<VisitingTaskStep> getVisitingTaskStepListByVtIds(String visitingTaskIds){
		return super.queryForList("getVisitingTaskStepListByVtIds", visitingTaskIds);
	}
	
	public List<VisitingTaskStep> getVisitingTaskStepListByVtId(Integer visitingTaskId){
		return super.queryForList("getVisitingTaskStepListByVtId", visitingTaskId);
	}
}
