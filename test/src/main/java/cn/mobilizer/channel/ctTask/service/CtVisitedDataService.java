package cn.mobilizer.channel.ctTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.ctTask.po.CtVisitedData;

public interface CtVisitedDataService{
	
	public List<CtVisitedData> selectByParams(Map<String,Object> param);
	
	public Integer selectByParamCount(Map<String,Object> param);
}
