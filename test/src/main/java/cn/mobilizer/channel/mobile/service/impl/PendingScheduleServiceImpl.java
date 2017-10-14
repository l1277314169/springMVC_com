package cn.mobilizer.channel.mobile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.mobile.controller.CallPlanningApproveController;
import cn.mobilizer.channel.mobile.dao.PendingScheduleDao;
import cn.mobilizer.channel.mobile.po.PendingSchedule;
import cn.mobilizer.channel.mobile.service.PendingScheduleService;
import cn.mobilizer.channel.util.Constants;
@Service
public class PendingScheduleServiceImpl implements PendingScheduleService {
	@Autowired
	private PendingScheduleDao pendingScheduleDao;
	@Override
	public List<PendingSchedule> findPendingScheduleByclientUserId(Map<String, Object> searchParams) throws BusinessException {
		
		return pendingScheduleDao.queryPendingScheduleByClientUserId(searchParams);
	}
	@Override
	public int updatePendingSchedule(PendingSchedule pendingSchedule) throws BusinessException {
		
		return pendingScheduleDao.update(pendingSchedule);
	}
	
	public List<PendingSchedule> findPendingScheduleByParams(Map<String, Object> searchParams)throws BusinessException {
		return pendingScheduleDao.findPendingSchedule(searchParams);
	}
	@Override
	public int updateStatus(Map<String, Object> searchParams) throws BusinessException {
		
		return pendingScheduleDao.updatePendingSchedule(searchParams);
	}
	@Override
	public Integer updatePendingScheduleStatus(Integer clientUserId,
			Integer clientId) throws BusinessException {
		try {
			Map<String,Object> searchParams=new HashMap<String,Object>();
			searchParams.put("clientUserId", clientUserId);
			searchParams.put("clientId", clientId);
			
			List<Object> listWeekDate=CallPlanningApproveController.getWeekDate();
			searchParams.put("startDate", listWeekDate.get(0));
			searchParams.put("endDate", listWeekDate.get(6));
			List<PendingSchedule> ps=pendingScheduleDao.findPendingSchedule(searchParams);
			if(ps != null && !ps.isEmpty()){
				String ids = "";
				for (PendingSchedule pendingSchedule : ps) {
					ids += pendingSchedule.getScheduleId()+",";
				}
				searchParams.put("ids", ids);
				searchParams.put("status", Constants.NO_STATUS);
				pendingScheduleDao.updatePendingSchedule(searchParams);
				return Constants.CHECK_SUCCESS;
			}else{
				return Constants.CHECK_FALSE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.CHECK_FALSE;
		}
	}

}
