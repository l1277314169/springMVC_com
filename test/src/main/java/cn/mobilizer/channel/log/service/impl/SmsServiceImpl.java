package cn.mobilizer.channel.log.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.log.dao.SmsDao;
import cn.mobilizer.channel.log.po.Sms;
import cn.mobilizer.channel.log.service.SmsService;
@Service
public class SmsServiceImpl implements SmsService{
	@Autowired
	private SmsDao smsDao;
	
	public int addSms(Map<String, Object> param) throws BusinessException {
		return smsDao.insert(param);
	}

	@Override
	public List<Sms> findListByParams(Map<String, Object> param)
			throws BusinessException {		 
		return smsDao.findListByParams(param);
	}

	@Override
	public int updateByPrimaryKeySelective(Sms sms) throws BusinessException {
		return smsDao.updateByPrimaryKeySelective(sms);
	}

	@Override
	public Integer findListByParamsCount(Map<String, Object> param)
			throws BusinessException {		 
		return smsDao.findListByParamsCount(param);
	}
	
}
