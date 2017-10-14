package cn.mobilizer.channel.visit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.visit.po.VisitingTaskDetailData;


/**
 * 
 * @author yeeda.tian
 *
 */
public interface VisitingTaskDetailDataService {
	
	/**
	 * 通过visitingTaskDataId 查询拜访明细数据
	 * @param visitingTaskDataId 
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public List<VisitingTaskDetailData> findVisitTaskStepByDataId(String visitingTaskDataId, Integer clientId) throws BusinessException;
	
	/**
	 * 通过人员/对象/日期等参数获取该用户某一天某个对象的拜访数据
	 * @param clientUserId
	 * @param popId
	 * @param popType
	 * @param visitDate
	 * @param visitingTaskStepId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, HashMap<String, String>> getTaskStepDataByParameter(Integer clientUserId, String popId, Byte popType, String visitDate, Integer visitingTaskStepId, Byte stepType, Integer clientId, Byte taskType) throws BusinessException;

}
