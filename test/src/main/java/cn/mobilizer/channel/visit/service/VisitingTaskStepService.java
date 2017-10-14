package cn.mobilizer.channel.visit.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.visit.po.VisitingTaskStep;


/**
 * 
 * @author yeeda.tian
 *
 */
public interface VisitingTaskStepService {
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<VisitingTaskStep> queryVisitingTaskStepList(Map<String, Object> params) throws BusinessException;
	
	/**
	 * 根据多个拜访任务id查询对应的所有步骤
	 * @param visitingTaskIds id之间用","隔开
	 * @return
	 * @throws BusinessException
	 */
	public List<VisitingTaskStep> getVisitingTaskStepListByVtIds(String visitingTaskIds) throws BusinessException;
	
}
