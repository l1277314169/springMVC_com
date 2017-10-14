package cn.mobilizer.channel.mobile.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.mobile.po.PendingSchedule;

public interface PendingScheduleService {
	/**
	 * 
	 * @return
	 */
	public List<PendingSchedule> findPendingScheduleByclientUserId(Map<String, Object> searchParams) throws BusinessException;
	/**
	 * 
	 * @param pendingSchedule
	 * @return
	 * @throws BusinessException
	 */
	public int updatePendingSchedule(PendingSchedule pendingSchedule)throws BusinessException;
	/**
	 * 
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<PendingSchedule> findPendingScheduleByParams(Map<String, Object> searchParams)throws BusinessException;
	
	/**
	 * 
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	public int updateStatus(Map<String, Object> searchParams)throws BusinessException;
	
	
	public Integer updatePendingScheduleStatus(Integer clientUserId,Integer clientId)throws BusinessException;
		
		
	

}
