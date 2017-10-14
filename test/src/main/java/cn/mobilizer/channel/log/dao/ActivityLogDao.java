package cn.mobilizer.channel.log.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.log.po.UserActivityLog;

@Repository
public class ActivityLogDao extends MyBatisDao{

	public ActivityLogDao() {
		super("USER_ACTIVITY_LOG");
	}
	
	public Integer insert(UserActivityLog log){
		super.insert ("insertSelective", log);
		return log.getLogId();
	}
	
	public Integer update(UserActivityLog log){
		return super.update("updateByPrimaryKeySelective", log);
	}

	public List<UserActivityLog> selectBylogTime(Map<String, Object> params){
		return super.queryForList("selectBylogTime",params);
	}
}
