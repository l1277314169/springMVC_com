package cn.mobilizer.channel.visit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.DistanceUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_POP_TYPE;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.service.ClientRolesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.visit.dao.VisitTaskDataDao;
import cn.mobilizer.channel.visit.dao.VisitingTaskDao;
import cn.mobilizer.channel.visit.po.VisitingTask;
import cn.mobilizer.channel.visit.po.VisitingTaskData;
import cn.mobilizer.channel.visit.service.VisitTaskDataService;
import cn.mobilizer.channel.visit.vo.VisitTaskDataReportVO;
@Service
public class VisitTaskDataServiceImpl implements VisitTaskDataService {
	private static final Log LOG = LogFactory.getLog(VisitTaskDataServiceImpl.class);
	
	@Autowired
	private VisitTaskDataDao visitTaskDataDao;
	@Autowired
	private VisitingTaskDao visitingTaskDao;
	@Autowired
	private ClientRolesService clientRolesService;
	@Autowired
	private StoreDao storeDao;

	@Override
	public List<VisitTaskDataReportVO> getVisitTaskDataReport(Integer clientId,String subordinates, String subStructureId, String deptIds, String userName, 
			Integer positionId, String visitingDate, Byte taskType, Integer visitedFlag, Long start,Integer size) throws BusinessException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("subordinates", subordinates);
			params.put("subStructureId", subStructureId);
			params.put("deptIds", deptIds);
			params.put("userName", userName);
			params.put("positionId", positionId);
			params.put("visitedFlag", visitedFlag);
			params.put("startDate", DateUtil.getDayStart(visitingDate));
			params.put("endDate", DateUtil.getDayEnd(visitingDate));
			params.put("taskType", taskType);
			params.put(Page.COLUNM_START, start);
			params.put(Page.COLUNM_SIZE, size);
			return visitTaskDataDao.getVisitTaskDataReport(params);
		}catch (BusinessException e) {
			LOG.error("method findVisitStorsByUserAndDate error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
	}

	@Override
	public Integer getVisitTaskDataReportCount(Integer clientId, String userName, String subStructureId, Integer positionId, String subordinates, Byte visitType, String visitingDate, Integer visitedFlag) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("userName", userName);
		params.put("subStructureId", subStructureId);
		params.put("positionId", positionId);
		params.put("subordinates", subordinates);
		params.put("visitType", visitType);
		params.put("visitedFlag", visitedFlag);
		params.put("startDate", DateUtil.getDayStart(visitingDate));
		params.put("endDate", DateUtil.getDayEnd(visitingDate));
		return visitTaskDataDao.getVisitTaskDataReportCount(params);
	}

	@Override
	public List<VisitingTaskData> findVisitObjectsByUserAndDate(Integer clientId,Integer clientUserId,Integer clientPositionId,String visitDate) throws BusinessException{
		try {
			/**初始化时间，如果传入的时间为空，默认为当天的前一天**/
			Date inTime = new Date();
			Date sInTime = new Date();
			Date eInTime = new Date();
			if (visitDate == null || visitDate ==""){
				inTime = DateUtil.getBeforeDay (inTime);
				sInTime = DateUtil.getDayStart (inTime);
				eInTime = DateUtil.getDayEnd (inTime);
			} else {
				inTime = DateUtil.toSimpleDate(visitDate);
				sInTime = DateUtil.getDayStart (inTime);
				eInTime = DateUtil.getDayEnd (inTime);
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("clientUserId", clientUserId);
			params.put("clientPositionId", clientPositionId);
			params.put("sInTime", sInTime);
			params.put("eInTime", eInTime);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			
			return visitTaskDataDao.findVisitObjectsByUserAndDate(params);
		}catch (BusinessException e) {
			LOG.error("method findVisitStorsByUserAndDate error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
	}

	@Override
	public List<VisitingTask> getTasksByUserAndObject(Integer clientUserId, Integer clientPositionId, Integer clientId, 
			String objectId, Byte objectType, String visitDate, Byte taskType) throws BusinessException{
		List<VisitingTask> taskList = new ArrayList<VisitingTask> ();
		try {
			String popId = objectId;
			Byte popType = objectType;
			/**判断对象类型**/
			if(popType.equals (VISIT_POP_TYPE.STORE.getKey ())){//如果为门店
				Store store = storeDao.selectByPrimaryKey (popId);
				/**
				 * 获取某人某天某门店对应的任务
				 * 1 直接根据门店和任务的映射表职位 关联 
				**/
				taskList = getTasksByStoreTaskMapping(popId, popType, clientPositionId, clientId, visitDate, taskType);
				if (taskList != null && taskList.size () > 0) {
					return taskList;
				}
				/**2 根据门店分组来获取对应的任务 store和task表中都有group_id**/
				if(store.getStoreGroupId () != null){
					taskList = getTasksByStoreTaskGroup(popId, popType, clientPositionId, clientId, visitDate, taskType);
					if (taskList != null && taskList.size () > 0) {
						return taskList;
					}
				}
				/**3 根据渠道获取任务**/
				if(store.getChannelId () !=null){
					taskList = getTasksByChannel(store.getChannelId (), popType, clientPositionId, clientId, visitDate, taskType);
					if (taskList != null && taskList.size () > 0) {
						return taskList;
					}
				}
				/**4 根据职位、角色分别获取任务然后合并**/
				if(clientPositionId != null) {
					/**获取该用户的角色信息**/
					List<String> ls = clientRolesService.getUserRolesByClientUserId(clientUserId);
					String roleIds = listToString(ls, ",");
//					taskList = getTasksByClientPosition(clientPositionId, popType, clientId, visitDate, taskType);
					taskList = getTasksByClientPositionAndRoles(clientPositionId, popType, clientId, visitDate, taskType, roleIds);
					if (taskList != null && taskList.size () > 0) {
						return taskList;
					}
				}
				 
			}else if(popType.equals (VISIT_POP_TYPE.DISTRIBUTOR.getKey ())){//如果是供应商
				return null;
			}
		}catch (BusinessException e) {
			LOG.error("method getTaskStepByUserAndStore4Date error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return taskList;
	}
	
	public List<VisitingTask> getTasksByStoreTaskMapping(String popId, Byte popType, Integer clientPositionId, Integer clientId, String visitDate, Byte taskType){
		List<VisitingTask> taskList = new ArrayList<VisitingTask> ();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("clientPositionId", clientPositionId);
			params.put("popType", popType);
			params.put("popId", popId);
			params.put("taskType", taskType);
			params.put("startDate", DateUtil.getDayStart (visitDate));
			params.put("endDate", DateUtil.getDayEnd (visitDate));
			taskList = visitingTaskDao.getTasksByStoreTaskMapping(params);
		}catch (BusinessException e) {
			LOG.error("method getTaskStepByStoreTaskMapping error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return taskList;
	}
	
	public List<VisitingTask> getTasksByStoreTaskGroup(String popId, Byte popType, Integer clientPositionId, Integer clientId, String visitDate, Byte taskType){
		List<VisitingTask> taskList = new ArrayList<VisitingTask> ();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("clientPositionId", clientPositionId);
			params.put("popId", popId);
			params.put("popType", popType);
			params.put("taskType", taskType);
			params.put("startDate", DateUtil.getDayStart (visitDate));
			params.put("endDate", DateUtil.getDayEnd (visitDate));
			taskList = visitingTaskDao.getTasksByStoreTaskGroup(params);
		}catch (BusinessException e) {
			LOG.error("method getTasksByStoreTaskGroup error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return taskList;
	}
	
	public List<VisitingTask> getTasksByChannel(Integer channelId, Byte popType, Integer clientPositionId, Integer clientId, String visitDate, Byte taskType){
		List<VisitingTask> taskList = new ArrayList<VisitingTask> ();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("clientPositionId", clientPositionId);
			params.put("channelId", channelId);
			params.put("popType", popType);
			params.put("taskType", taskType);
			params.put("startDate", DateUtil.getDayStart (visitDate));
			params.put("endDate", DateUtil.getDayEnd (visitDate));
			taskList = visitingTaskDao.getTasksByChannel(params);
		}catch (BusinessException e) {
			LOG.error("method getTasksByChannel error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return taskList;
	}
	
	public List<VisitingTask> getTasksByClientPosition(Integer clientPositionId, Byte popType, Integer clientId, String visitDate, Byte taskType){
		List<VisitingTask> taskList = new ArrayList<VisitingTask> ();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("clientPositionId", clientPositionId);
			params.put("popType", popType);
			params.put("taskType", taskType);
			params.put("startDate", DateUtil.getDayStart (visitDate));
			params.put("endDate", DateUtil.getDayEnd (visitDate));
			taskList = visitingTaskDao.getTasksByClientPosition(params);
		}catch (BusinessException e) {
			LOG.error("method getTasksByClientPosition error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return taskList;
	}
	
	public List<VisitingTask> getTasksByClientPositionAndRoles(Integer clientPositionId, Byte popType, Integer clientId, String visitDate, Byte taskType, String roleIds){
		List<VisitingTask> taskList = new ArrayList<VisitingTask> ();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("clientPositionId", clientPositionId);
			params.put("roleIds", roleIds);
			params.put("popType", popType);
			params.put("taskType", taskType);
			params.put("startDate", DateUtil.getDayStart (visitDate));
			params.put("endDate", DateUtil.getDayEnd (visitDate));
			taskList = visitingTaskDao.getTasksByClientPositionAndRoles(params);
		}catch (BusinessException e) {
			LOG.error("method getTasksByClientPosition error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return taskList;
	}

	@Override
	public VisitingTaskData selectVisitTaskDataByParam(Integer clientId ,Integer clientUserId,
			String popId, Byte popType, String visitDate, Byte taskType, Store store) throws BusinessException {
		VisitingTaskData visitingTaskData = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("clientUserId", clientUserId);
			params.put("popId", popId);
			params.put("popType", popType);
			params.put("taskType", taskType);
			params.put("startDate", DateUtil.getDayStart (visitDate));
			params.put("endDate", DateUtil.getDayEnd (visitDate));
			List<VisitingTaskData> ls = visitTaskDataDao.selectVisitTaskDataByParam(params);
			if(ls !=null && ls.size() >0 ) {
				visitingTaskData = ls.get(0);
				//门店坐标不为空，计算坐标距离
				if(store !=null && StringUtils.isNotEmpty(store.getLongitude()) && StringUtils.isNotEmpty(store.getLatitude()) ){
					Double storeLongitude = Double.parseDouble(store.getLongitude());
					Double storeLatitude = Double.parseDouble(store.getLatitude());
					
					Double inLongitude = StringUtils.isNotEmpty(visitingTaskData.getInLongitude()) ? Double.parseDouble(visitingTaskData.getInLongitude()) : null;
					Double inLatitude = StringUtils.isNotEmpty(visitingTaskData.getInLatitude()) ? Double.parseDouble(visitingTaskData.getInLatitude()) : null;
					Double outLongitude = StringUtils.isNotEmpty(visitingTaskData.getOutLongitude()) ? Double.parseDouble(visitingTaskData.getOutLongitude()) : null;
					Double outLatitude = StringUtils.isNotEmpty(visitingTaskData.getOutLatitude()) ? Double.parseDouble(visitingTaskData.getOutLatitude()) : null;
					if(inLongitude !=null && inLatitude !=null){
						Double inRange = DistanceUtil.GetDistance(inLongitude, inLatitude, storeLongitude, storeLatitude);
						visitingTaskData.setInRange(inRange);
					}
					if(outLongitude !=null && outLatitude !=null){
						Double outRange = DistanceUtil.GetDistance(outLongitude, outLatitude, storeLongitude, storeLatitude);
						visitingTaskData.setOutRange(outRange);
					}
				}
			}
		}catch (BusinessException e) {
			LOG.error("method getTasksByClientPosition error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return visitingTaskData;
	}
	
	private String listToString(List<String> list, String sep) {
		if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String str : list) {
            if (flag) {
                result.append(sep==null?",":sep);
            } else {
                flag = true;
            }
            result.append(str);
        }
        return result.toString();
	}
}
