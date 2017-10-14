package cn.mobilizer.channel.ctTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.ctTask.po.CtTaskParameter;

public interface CtTaskParameterService{
	
	public List<CtTaskParameter> selectByParams(Integer clientId,Integer ctTaskId);
	
	public Integer selectByParamCount(Map<String,Object> param);
	
	public Integer insert(CtTaskParameter ctTaskParameter);
	
	public CtTaskParameter selectByPrimaryKey(Integer ctTaskParameterId);

}
