package cn.mobilizer.channel.log.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.log.po.DataErrorLog;
@Repository
public class DataErrorLogDao extends MyBatisDao {
	public DataErrorLogDao() {
		super("DATA_ERROR_LOG");
	}
	
	public Integer insert(DataErrorLog log){
		super.insert ("insertSelective", log);
		return log.getId();
	}
	
	public Integer update(DataErrorLog log){
		return super.update("updateByPrimaryKeySelective", log);
	}
	
	public List<DataErrorLog> findByClientUserIdAndFileName(Integer clientUserId, String fileName){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientUserId", clientUserId);
		params.put("fileName", fileName);
		return super.queryForList("findByClientUserIdAndFileName", params);
	}
}
