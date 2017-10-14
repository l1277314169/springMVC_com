package cn.mobilizer.channel.visit.service;

import java.util.List;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.visit.po.VisitingTaskStepObject;
import cn.mobilizer.channel.visit.vo.VisitObject;


/**
 * 
 * @author yeeda.tian
 *
 */
public interface VisitingTaskStepObjectService {
	
	/**
	 * 通过步骤id\步骤类型 获取对象数据
	 * @param visitingTaskStepId
	 * @param stepType
	 * @param clientId
	 * @return
	 */
	public List<VisitingTaskStepObject> getVisitingObjectListByStep(Integer clientUserId, Integer visitingTaskStepId, Byte stepType, Integer clientId, Byte selectType, String popId, Byte popType, String visitDate) throws BusinessException;
	
}
