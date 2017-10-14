package cn.mobilizer.channel.api.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.api.po.VisitingCount;
import cn.mobilizer.channel.api.po.VisitingDetail;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class ReportDao extends MyBatisDao {
	public ReportDao() {
		super("REPORT");
	}
	
	public List<VisitingCount> getVisitingCount(int clientId,int clientUserId,String startTime,String endTime){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("clientUserId", clientUserId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return super.queryForList("queryVisitingCount", params);
	}
	
	public List<VisitingDetail> getVisitingDetail(int clientId,int clientUserId,String startTime,String endTime){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("clientUserId", clientUserId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return super.queryForList("queryVisitingDetail", params);
	}

}
