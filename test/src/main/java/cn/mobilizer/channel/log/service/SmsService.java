package cn.mobilizer.channel.log.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.log.po.Sms;

public interface SmsService {
	
	/**
	 * 
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public int addSms(Map<String,Object> param)throws BusinessException;
	
	public List<Sms> findListByParams(Map<String,Object> param)throws BusinessException;
	
	public int updateByPrimaryKeySelective(Sms sms)throws BusinessException;
	
	public Integer findListByParamsCount(Map<String,Object> param)throws BusinessException;
}
