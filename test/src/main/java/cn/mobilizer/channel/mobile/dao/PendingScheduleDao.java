package cn.mobilizer.channel.mobile.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.mobile.po.PendingSchedule;
@Repository
public class PendingScheduleDao extends MyBatisDao{
	public PendingScheduleDao(){
		super("PENDING_SCHEDULE");
	}
	
	public List<PendingSchedule> queryPendingScheduleByClientUserId(Map<String, Object> searchParams){
		return super.queryForList("queryPendingScheduleByClientUserId", searchParams);
	}
	public int update(PendingSchedule PendingSchedule){
		
		return super.update("updateByPrimaryKeySelective", PendingSchedule);
	}
	
	public List<PendingSchedule> findPendingSchedule(Map<String, Object> searchParams){
		return super.queryForList("findPendingSchedule", searchParams);
	}
	
	public int updatePendingSchedule(Map<String, Object> searchParams){
		return super.update("updatePendingSchedule", searchParams);
	}
}
