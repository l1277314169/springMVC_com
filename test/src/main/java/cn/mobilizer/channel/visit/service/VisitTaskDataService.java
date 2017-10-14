package cn.mobilizer.channel.visit.service;

import java.util.List;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.visit.po.CallPlanning;
import cn.mobilizer.channel.visit.po.VisitingTask;
import cn.mobilizer.channel.visit.po.VisitingTaskData;
import cn.mobilizer.channel.visit.vo.VisitTaskDataReportVO;

public interface VisitTaskDataService {
	
	/**
	 * 
	 * @param clientId
	 * @param subordinates
	 * @param subStructureId
	 * @param deptIds
	 * @param userName
	 * @param positionId
	 * @param visitingDate
	 * @param taskType
	 * @param visitedFlag
	 * @param start
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public List<VisitTaskDataReportVO> getVisitTaskDataReport(Integer clientId,String subordinates, String subStructureId, String deptIds, String userName, Integer positionId, String visitingDate,Byte taskType, Integer visitedFlag, Long start,Integer size) throws BusinessException;
	
	public Integer getVisitTaskDataReportCount(Integer clientId, String userName, String subStructureId, Integer positionId, String subordinates, Byte visitType, String visitingDate, Integer visitedFlag);
	
	/**
	 * 查询某一天某一个用户的拜访数据
	 * @param clientId
	 * @param clientUserId
	 * @param clientPositionId
	 * @param visitDate
	 * @return
	 */
	public List<VisitingTaskData> findVisitObjectsByUserAndDate(Integer clientId, Integer clientUserId, Integer clientPositionId, String visitDate) throws BusinessException;
	
	/**
	 * 根据用户信息和对象信息-获取任务
	 * @param clientUserId
	 * @param clientPositionId
	 * @param clientId
	 * @param objectId
	 * @param objectType
	 * @param visitDate
	 * @return
	 * @throws BusinessException
	 */
	public List<VisitingTask> getTasksByUserAndObject(Integer clientUserId, Integer clientPositionId, Integer clientId, String objectId, Byte objectType, String visitDate, Byte taskType) throws BusinessException;

	/**
	 * 获取进出店的详细数据
	 * @param clientUserId
	 * @param popId
	 * @param popType
	 * @param visitDate
	 * @param taskType
	 * @return
	 * @throws BusinessException
	 */
	public VisitingTaskData selectVisitTaskDataByParam(Integer clientId, Integer clientUserId, String popId, Byte popType, String visitDate, Byte taskType, Store store) throws BusinessException;
	
	
}
