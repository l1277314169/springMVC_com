package cn.mobilizer.channel.ctTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.ctTask.po.CtTaskObject;
import cn.mobilizer.channel.ctTask.vo.CtTaskDataSerchVo;

public interface CtTaskObjectService{
	
	public List<CtTaskObject> selectByParams(Integer clientId,Integer ctTaskId);
	
	public Integer selectByParamCount(Map<String,Object> param);
	
	public List<CtTaskObject> selectTaskObjectBySku(CtTaskDataSerchVo ctTaskDataSerchVoe);
	
	public Integer selectTaskObjectCountBySku(CtTaskDataSerchVo ctTaskDataSerchVo);
}
