package cn.mobilizer.channel.image.dao;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.image.po.ExecTimeLog;

@Repository
public class ExecTimeLogDao extends MyBatisDao {

	public ExecTimeLogDao(){
		super("EXECTIMELOG");
	}
	
	public ExecTimeLog selectExecTimeLog(Map<String, Object> params){
		return super.get("selectExecTimeLog",params);
	}
	
	public Date selectNow(){
		return super.get("selectNow");
	}
	
	public void insertSelective(Map<String, Object> params){
		super.update("insertSelective", params);
	}
}
