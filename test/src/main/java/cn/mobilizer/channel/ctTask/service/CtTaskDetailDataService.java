package cn.mobilizer.channel.ctTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.ctTask.po.CtTaskDetailData;

public interface CtTaskDetailDataService{
	
	public List<CtTaskDetailData> selectByParams(Map<String,Object> param);
	
	public Integer selectByParamCount(Map<String,Object> param);
	
	public String insert(CtTaskDetailData ctTaskDetailData);
}
