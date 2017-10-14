package cn.mobilizer.channel.visit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.VisitingTaskData;
import cn.mobilizer.channel.visit.vo.VisitTaskDataReportVO;
@Repository
public class VisitTaskDataDao extends MyBatisDao{
	
	public VisitTaskDataDao() {
		super("VISITING_TASK_DATA");
	}
	
	public List<VisitTaskDataReportVO> getVisitTaskDataReport(Map<String, Object> params){
		return super.queryForList("visitingTaskDataReport", params);
	}
	
	public Integer getVisitTaskDataReportCount(Map<String, Object> params){
		return super.get("visitingTaskDataReportCount", params);
	}
	
	public List<VisitingTaskData> findVisitObjectsByUserAndDate(Map<String, Object> params){
		return super.queryForList("findVisitObjectsByUserAndDate", params);
	}
	
	public List<VisitingTaskData> selectVisitTaskDataByParam(Map<String, Object> params){
		return super.queryForList("selectVisitTaskDataByParam", params);
	}
}
