package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.VisitingTaskDetailData;
@Repository
public class VisitingTaskDetailDataDao extends MyBatisDao{
	
	public VisitingTaskDetailDataDao() {
		super("VISITING_TASK_DETAIL_DATA");
	}
	
	public List<VisitingTaskDetailData> findVisitTaskStepByDataId(Map<String, Object> params) {
		return super.queryForList("findVisitTaskStepByDataId", params);
	}
	
	public List<VisitingTaskDetailData> getTaskStepDataByParameter(Map<String, Object> params) {
		return super.queryForList("getTaskStepDataByParameter", params);
	}
}
