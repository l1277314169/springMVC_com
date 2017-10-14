package cn.mobilizer.channel.visit.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.visit.po.VisitingTask;
import cn.mobilizer.channel.visit.vo.VisitingTaskVO;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface VisitingTaskService {

	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 */
	public Integer queryVisitingTaskCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<VisitingTask> queryVisitingTaskList(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 新增计划任务
	 * @param visitingTaskVO
	 * @param channelId
	 * @throws BusinessException
	 */
	public void saveVisitingTaskVO(VisitingTaskVO visitingTaskVO, Integer channelId) throws BusinessException;
	
	/**
	 * 修改计划任务
	 * @param visitingTaskVO
	 * @param channelId
	 * @throws BusinessException
	 */
	public void updateVisitingTaskVO(VisitingTaskVO visitingTaskVO,Integer channelId) throws BusinessException;
	
	/**
	 * 通过拜访任务的id查询该任务相关的数据
	 * @param visitingTaskId
	 * @throws BusinessException
	 */
	public VisitingTaskVO findVisitingTaskVOByTaskId (Integer visitingTaskId) throws BusinessException;
	
	/**
	 * 通过拜访任务的id查询该任务相关的数据
	 * @param visitingTaskId
	 * @throws BusinessException
	 */
	public VisitingTask findVisitingTaskById (Integer visitingTaskId) throws BusinessException;
	
}
