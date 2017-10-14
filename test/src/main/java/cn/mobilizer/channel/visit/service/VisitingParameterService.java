package cn.mobilizer.channel.visit.service;

import java.util.List;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.visit.po.VisitingParameter;


/**
 * 
 * @author yeeda.tian
 *
 */
public interface VisitingParameterService {
	
	
	/**
	 * 根据拜访步骤id查询拜访数据明细(参数定义)
	 * @param visitingTaskStepId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public List<VisitingParameter> getVisitingParameterListByStepId(Integer visitingTaskStepId, Integer clientId) throws BusinessException;
	
}
