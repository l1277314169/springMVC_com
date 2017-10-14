package cn.mobilizer.channel.log.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.log.dao.DataErrorLogDao;
import cn.mobilizer.channel.log.po.DataErrorLog;

@Service
public class DataErrorLogService {
	@Autowired
	private DataErrorLogDao dataErrorLogDao;
	
	
	public int insert(DataErrorLog elog){
		int id = 0;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);  
			id = dataErrorLogDao.insert(elog);
			//切换到业务数据库
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		} catch (Exception e) {
			e.printStackTrace();
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		
		return id;
	}
	
	public int update(DataErrorLog log){
		int count = 0;
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);  
			count = dataErrorLogDao.update(log);
			//切换到业务数据库
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		} catch (Exception e) {
			e.printStackTrace();
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return count;
	}
	
	public List<DataErrorLog> findByClientUserIdAndFileName(Integer clientUserId, String fileName){
		List<DataErrorLog> elist = new ArrayList<DataErrorLog>();
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);  
			elist = dataErrorLogDao.findByClientUserIdAndFileName(clientUserId, fileName);
			//切换到业务数据库
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		} catch (Exception e) {
			e.printStackTrace();
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		} 
		return elist;
	}

}
