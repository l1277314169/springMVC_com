package cn.mobilizer.channel.ctTask.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.ctTask.po.CtTask;
import cn.mobilizer.channel.ctTask.vo.ExportCtTaskDataSerchVo;
import cn.mobilizer.channel.ctTask.vo.StoreTask;
import cn.mobilizer.channel.ctTask.vo.TaskCycle;
import cn.mobilizer.channel.exception.BusinessException;

public interface CtTaskService{
	
	/**分页查询
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public List<CtTask> selectByParams(Map<String,Object> param) throws BusinessException;
	
	public Integer selectByParamCount(Map<String,Object> param) throws BusinessException;
	
	public CtTask getCtTask(Integer ctTaskId) throws BusinessException;
	
	public Integer insert(CtTask ctTask) throws BusinessException;
	
	public List<CtTask> getTaskList(Map<String, Object> params) throws BusinessException;
	
	/** 
	 * 判断Date是否在任务周期内，Date为空时默认为当前时间
	 */
	public void getValidity(CtTask ctTask,Date date) throws BusinessException;
	
	public List<CtTask> selectTaskByPosition(Map<String,Object> params) throws BusinessException;

	/**
	 * 通过ctTask周期类型计算当前时间的周期开始时间
	 * @param ctTask
	 * @return Date
	 */
	public Date getBeginValidity(CtTask ctTask) throws BusinessException;
	
	/**
	 * 从2015-1-5 开始根据不同周期类型获取至今的所有周、半月、年的时间段
	 * @param ctTask
	 * @return List<TaskCycle>
	 */
	public List<TaskCycle> getTaskCycelList(Integer taskId) throws BusinessException;
	
	/**
	 * 查询周期任务历史数据门店 
	 * @param storeTask
	 * @param pageStart
	 * @param pageSize
	 * @return
	 */
	public List<StoreTask> selectTaskDataStore(StoreTask storeTask,Long pageStart,Integer pageSize,Integer clientId);
	
	public Integer selectTaskDataStoreCount(StoreTask storeTask,Integer clientId);
	
	/**
	 * 周期任务门店列表计算周期时间
	 * @param ctTask
	 * @param date
	 */
	public void getTaskCycle(CtTask ctTask,Date date);
	
	public Date getTaskCycleMaxDate(Byte cycleType);
	
	/**
	 * 周期任务数据导出
	 * @param clientId
	 * @param userIds
	 * @param structureIds
	 * @param deptIds
	 * @param userName
	 * @param positionIds
	 * @param startTime
	 * @param endTime
	 * @param ctTaskId
	 * @param visitType
	 * @return
	 */
	public List<?> exportCtTaskData(ExportCtTaskDataSerchVo exportCtTaskDataSerchVo);
	
	/**
	 * 龙妮佳客户只需要当前周期才能修改
	 * @param storeTask
	 * @param ctTask
	 */
	public void getValidityDate(StoreTask storeTask,CtTask ctTask);
}
