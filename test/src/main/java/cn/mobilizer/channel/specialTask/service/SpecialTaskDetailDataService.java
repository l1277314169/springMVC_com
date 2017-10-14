package cn.mobilizer.channel.specialTask.service;


import java.util.List;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.specialTask.po.SpecialTaskData;
import cn.mobilizer.channel.specialTask.po.SpecialTaskDetailData;
import cn.mobilizer.channel.specialTask.po.SpecialTaskParameter;

public interface SpecialTaskDetailDataService {
	/**
	 * 任务执行对象的详细数据 用于主管查看对象执行的详细
	 * @param clientId
	 * @param popId
	 * @param specialTaskId
	 * @return
	 * @throws BusinessException
	 */
	public List<SpecialTaskParameter> findDetailDataByObjId(Integer clientId,String specialTaskId, Byte objectType, String popId, Integer clientPositionId,Integer clientUserId,String specialTaskTataId)throws BusinessException;
}
