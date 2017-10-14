package cn.mobilizer.channel.image.service;

import java.util.Date;
import java.util.Map;

import cn.mobilizer.channel.image.po.ExecTimeLog;

public interface ExecTimeLogService {

	
	public ExecTimeLog getLastExecuted(Map<String, Object> params);
	
	public Date selectNow();
	
	public void insertSelective(Map<String, Object> params);
}
