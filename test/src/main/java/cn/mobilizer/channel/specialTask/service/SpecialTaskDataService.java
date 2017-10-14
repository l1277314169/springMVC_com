package cn.mobilizer.channel.specialTask.service;

import java.util.List;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.specialTask.po.SpecialTaskObject;

public interface SpecialTaskDataService {
	/**
	 * 任务执行对象列表,已经开始的任务
	 * @param clientId
	 * @param specialTaskId
	 * @throws BusinessException
	 */
	public List<SpecialTaskObject> findSpecialTaskDataObje(Integer clientId,String specialTaskId, Byte objectType,Integer clientPositionId)throws BusinessException;
	
}
