package cn.mobilizer.channel.log.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.log.dao.ActivityLogDao;
import cn.mobilizer.channel.log.po.UserActivityLog;

@Service
public class ActivityLogService {
	@Autowired
	private ActivityLogDao activityLogDao;
	
	public int insert(UserActivityLog log){
		int id = 0;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);  
			id = activityLogDao.insert(log);
			//切换到业务数据库
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		} catch (Exception e) {
			e.printStackTrace();
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		
		return id;
	}
	
	public Integer update(UserActivityLog log){
		int count = 0;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);  
			count = activityLogDao.update(log);
			//切换到业务数据库
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		} catch (Exception e) {
			e.printStackTrace();
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return count;
	}
	
	public List<UserActivityLog> selectBylogTime(Map<String, Object> params){
		return activityLogDao.selectBylogTime(params);
	}

}
