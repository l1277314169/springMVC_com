package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.exception.BusinessException;


public interface OptionsService {
	
	public List<Options> findOptionsByOptionName(String optionName, Integer clientId) throws BusinessException;
	
	public List<Options> queryOptions(Map<String, Object> params)throws BusinessException;

	public String getOptionText(String optionName, Byte workTimeType, int clientId)throws BusinessException;

	public String getLeaveTypeText(String optionName, int leaveType, int clientId)throws BusinessException;
	
	public Byte getOptionValue(String optionName,String optionText,int clientId)throws BusinessException;
	
	public List<Options> selectOptionsList(Map<String,Object> paramrter) throws BusinessException;
	
	public Map<String,Options> selectOptionsListbyYue(Integer clientId) throws BusinessException;

	public Map<String,Options> selectOptionsListbyType(Integer clientId) throws BusinessException;
}
