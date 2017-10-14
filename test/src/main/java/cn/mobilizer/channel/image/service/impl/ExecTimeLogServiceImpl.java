package cn.mobilizer.channel.image.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.image.dao.ExecTimeLogDao;
import cn.mobilizer.channel.image.po.ExecTimeLog;
import cn.mobilizer.channel.image.service.ExecTimeLogService;

@Service
public class ExecTimeLogServiceImpl implements ExecTimeLogService {

	@Autowired
	private ExecTimeLogDao execTimeLogDao;
	
	public ExecTimeLog getLastExecuted(Map<String, Object> params){
		ExecTimeLog log = execTimeLogDao.selectExecTimeLog(params);
		return log;
	}

	@Override
	public Date selectNow() {
		return execTimeLogDao.selectNow();
	}

	@Override
	public void insertSelective(Map<String, Object> params) {
		execTimeLogDao.insertSelective(params);
	}
}