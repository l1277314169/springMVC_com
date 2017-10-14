package cn.mobilizer.channel.specialTask.service;



import java.util.List;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.specialTask.vo.SpecialTaskVO;

public interface SpecialTaskService {
	
	/**
	 * 新增专项任务
	 * @param specialTask
	 * @return
	 * @throws BusinessException
	 */
	public String  addSpecialTask(SpecialTaskVO specialTaskVO)throws BusinessException;
	
	/**
	 * 修改专项任务
	 * @param specialTask
	 * @return
	 * @throws BusinessException
	 */
	public int updateSpecialTask(SpecialTaskVO specialTaskVO)throws BusinessException;
	
	/**
	 * 
	 * @param parameter
	 * @return
	 * @throws BusinessException
	 */
	public SpecialTaskVO findSpecialTaskByParmas(Integer clientId,String specialTaskId)throws BusinessException;
	
	/**
	 * 任务列表
	 * @param parameter
	 * @return
	 * @throws BusinessException
	 */
	public List<SpecialTaskVO> specialTaskList(Integer clientId,Integer clientUserId)throws BusinessException;
}
