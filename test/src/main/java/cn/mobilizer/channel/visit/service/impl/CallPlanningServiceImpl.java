package cn.mobilizer.channel.visit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.dao.ClientUserDao;
import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.dao.StoreUserMappingDao;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.mobile.controller.CallPlanningApproveController;
import cn.mobilizer.channel.mobile.dao.PendingScheduleDao;
import cn.mobilizer.channel.mobile.po.PendingSchedule;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.visit.dao.CallPlanningDao;
import cn.mobilizer.channel.visit.po.CallPlanning;
import cn.mobilizer.channel.visit.service.CallPlanningService;
@Service
public class CallPlanningServiceImpl implements CallPlanningService{
	private static final Log LOG = LogFactory.getLog(CallPlanningServiceImpl.class); 
	
	@Autowired
	private CallPlanningDao callPlanningDao;
	@Autowired
	private PendingScheduleDao pendingScheduleDao;
	@Autowired
	private StoreUserMappingDao storeUserMappingDao;
	@Autowired
	private ClientUserDao clientUserDao;
	@Autowired
	private StoreDao storeDao;

	@Override
	public List<CallPlanning> getCallPlanning(Map<String, Object> pageParam) {
		return callPlanningDao.getCallPlaning(pageParam);
	}

	@Override
	public boolean saveAll(List<CallPlanning> list) {
		return callPlanningDao.saveAll(list);
	}

	@Override
	public List<CallPlanning> findListByParams4union(Integer clientUserId, Integer clientId, String visitDate, Byte taskType) throws BusinessException{
		List<CallPlanning> ls = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			
			/**初始化时间，如果传入的时间为空，默认为当天的前一天**/
			Date callDate = new Date();
			Date startCallDate = null;
			Date endCallDate = null;
			if (visitDate == null || visitDate ==""){
				callDate = DateUtil.getBeforeDay (callDate);
				startCallDate = DateUtil.getDayStart (callDate);
				endCallDate = DateUtil.getDayEnd (callDate);
			} else {
				callDate = DateUtil.toSimpleDate(visitDate);
				startCallDate = DateUtil.getDayStart (callDate);
				endCallDate = DateUtil.getDayEnd (callDate);
			}
			params.put("clientId", clientId);
			params.put("clientUserId", clientUserId);
			params.put("startCallDate", startCallDate);
			params.put("endCallDate", endCallDate);
			params.put("taskType", taskType);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			
			ls = callPlanningDao.findListByParams4union(params);
		} catch(BusinessException e){
			LOG.error("method findListByParams4union error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}
	
	public List<CallPlanning> findListByParams(Map<String, Object> params) throws BusinessException{
		try {
			return callPlanningDao.findListByParams(params);
		} catch(BusinessException e){
			LOG.error("method findCallPlanningListByParams error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
	}

	@Override
	public int queryCallPlanningCount(Map<String, Object> parameters)throws BusinessException {
			int count = 0;
			try {
				count = callPlanningDao.queryCallPlanningCount(parameters);
			} catch (BusinessException e) {
				LOG.error("method queryCallPlanningCount error, ", e);
				throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
			}
		return count;
	}


	@Override
	public CallPlanning getCallPlanningById(String planningId)
			throws BusinessException {
		CallPlanning callPlanning = null;
		try {
			callPlanning = callPlanningDao.getCallPlanningById(planningId);
		} catch (BusinessException e) {
			LOG.error("method getCallPlanningById error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return callPlanning;
	}

	@Override
	public void updateCallPlanning(Map<String, Object> parameters)
			throws BusinessException {
		try {
			callPlanningDao.updateCallPlanning(parameters);
		} catch (BusinessException e) {
			LOG.error("method updateCallPlanning error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
	}


	@Override
	public int addCallPlanning(CallPlanning callPlanning) throws BusinessException{
		int a = 0;
		try {
			a = callPlanningDao.insert(callPlanning);
		} catch (BusinessException e) {
			LOG.error("method updateCallPlanning error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
		return a;
	}

	@Override
	public List<CallPlanning> getCallPlanningByClientUserId(Integer clientUserId,String startDate, String endDate)throws BusinessException {
		List<CallPlanning> list = null;
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("clientUserId", clientUserId);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			list = callPlanningDao.getCallPlanningByClientUserId(params);
		} catch (BusinessException e) {
			LOG.error("method getCallPlanningByClientUserId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<CallPlanning> getCallPlanningByClientUserIdAndCallDate(Integer clientUserId, String callDate,String afterDay) throws BusinessException {
		List<CallPlanning> list = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientUserId", clientUserId);
			params.put("callDate", callDate);
			params.put("afterDay", afterDay);
			list = callPlanningDao.getCallPlanningByClientUserId(params);
		} catch (BusinessException e) {
			LOG.error("method getCallPlanningByClientUserIdAndCallDate error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<CallPlanning> getCallPlanningByClientUserIdAndStoreId(Map<String, Object> parameters) {
		List<CallPlanning> list = null;
		try {
			list = callPlanningDao.getCallPlanningByClientUserIdAndStoreId(parameters);
		} catch (BusinessException e) {
			LOG.error("method getCallPlanningByClientUserIdAndStoreId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public int updateCallStatus(Map<String, Object> parameters) throws BusinessException {
	
		return callPlanningDao.cancelCallPalnning(parameters);
	}

	@Override
	public int updateisdelte(Map<String, Object> parameters) throws BusinessException {
		
		return callPlanningDao.isdeleteCallpalnning(parameters);
	}

	@Override
	public List<CallPlanning> getNotCallPlanning(Map<String, Object> parameters) throws BusinessException {
		
		return callPlanningDao.getNotCallPlaning(parameters);
	}

	@Override
	public Integer deleteCallPlanning(Integer clientId,String callDate,Integer clientUserId,Integer visitType) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("callDate", callDate);
		params.put("clientUserId", clientUserId);
		params.put("visitType", visitType);
		return callPlanningDao.deleteCallpalnning(params);
	}

	@Override
	public Object addCallPlanning() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findCallPlanning(Map<String, Object> params) throws BusinessException {
		
		return callPlanningDao.findCallplanningByMap(params);
	}

	@Override
	public int updateCallPlanning(CallPlanning callPlanning) throws BusinessException {
		
		return callPlanningDao.updateCallPlanning(callPlanning);
	}

	@Override
	public int addCallPlanning(Map<String, Object> params) throws BusinessException {
		
		return callPlanningDao.addCallPlanning(params);
	}

	@Override
	public CallPlanning findCallPlanningIsdelete(Map<String, Object> params) throws BusinessException {
		return callPlanningDao.findCallPlanningIsdelete(params);
	}

	@Override
	public Integer deleteCallPlanningByDay(Map<String, Object> parameters) throws BusinessException{
		return callPlanningDao.deleteCallPlanningByDay(parameters);
	}

	@Override
	public List<CallPlanning> exportCallPlanning(Map<String, Object> parameters)
			throws BusinessException {
		List<CallPlanning> list = null;
		try {
			list = callPlanningDao.findCallPlanningExport(parameters);
		} catch (Exception e) {
			LOG.error("method exportCallPlanning error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public Integer updateCallPlanningStatus(Integer clientUserId, Integer clientId)
			throws BusinessException {
				try {
					//审核通过后 状态改为1-审核通过
					Map<String, Object> searchParams = new HashMap<String, Object>();
					searchParams.put("clientUserId", clientUserId);
					searchParams.put("clientId", clientId);
					List<Object> listWeekDate = CallPlanningApproveController
							.getWeekDate();
					searchParams.put("startDate", listWeekDate.get(0));
					searchParams.put("endDate", listWeekDate.get(6));
					List<PendingSchedule> pendingSchedulelist = pendingScheduleDao
							.queryPendingScheduleByClientUserId(searchParams);
					if (pendingSchedulelist != null
							&& !pendingSchedulelist.isEmpty()) {
						String ids = "";
						for (PendingSchedule pendingSchedule : pendingSchedulelist) {
							ids += pendingSchedule.getScheduleId() + ",";
						}
						searchParams.put("ids", ids);
						searchParams.put("status", Constants.YES_STATUS);
						searchParams.put("Isdelete", Constants.IS_DELETE_FALSE);
						pendingScheduleDao.updatePendingSchedule(searchParams);
						//新增call_planning表
						for (int j = 0; j < pendingSchedulelist.size(); j++) {
							PendingSchedule	pendingSchedule = pendingSchedulelist.get(j);
							CallPlanning call = new CallPlanning();
							String panid = UUID.randomUUID().toString();
							call.setPlanningId(panid);
							call.setClientUserId(clientUserId);
							call.setCallDate(pendingSchedule
									.getCallDate());
							call.setPopId(pendingSchedule.getPopId());
							call.setPopType(Constants.STORE_POP_TYPE);
							call.setSequence(j);
							call.setPlanningType(Constants.MOBI_NOTEMP_PLANNING_TYPE);
							call.setVisitType(Constants.RUN_VISIT_TYPE);
							call.setCallStatus(Constants.NO_CALL_STATUS);
							call.setClientId(clientId);
							if (pendingSchedule.getWorkType().equals(Constants.WEB_WORK_TYPE_NOTSTORE)) {
								call.setWorkType(Constants.WEB_WORK_TYPE_NOTSTORE);
								call.setEnumValue(Integer
										.parseInt(pendingSchedule
												.getSoa()));
								call.setPopId(null);
							}else{
								call.setWorkType(Constants.WEB_WORK_TYPE_STORE);
								call.setEnumValue(null);
							}
							
							/**判断人店关系是否删除*/
							searchParams.put("storeId", pendingSchedule.getPopId());
							searchParams.put("callDate", DateUtil.formatSimpleDate(pendingSchedule.getCallDate()));
							searchParams.put("workType", Constants.WEB_WORK_TYPE_NOTSTORE);
							List<StoreUserMapping> storeUserMappingList = storeUserMappingDao.getStoreUserMappingList(searchParams);
							if(storeUserMappingList == null || storeUserMappingList.isEmpty()){
								continue;
							}
							
							if(pendingSchedule.getWorkType().equals(Constants.WEB_WORK_TYPE_NOTSTORE)){
								/**同一个人 ，同天，不走店，是否已有计划*/
								List<CallPlanning> callPlanninglist = callPlanningDao.getCallPlanningByWorkType(searchParams);
								if(callPlanninglist != null && !callPlanninglist.isEmpty()){
									continue;
								}
							}else{
								/**同一个人 ，同天，同店，是否已有计划*/
								List<CallPlanning> callPlanninglist = callPlanningDao.getCallPlanningByClientUserIdAndStoreId(searchParams);
								if(callPlanninglist != null && !callPlanninglist.isEmpty()){
									continue;
								}
							}
							callPlanningDao.insert(call);
						}
						return Constants.CHECK_SUCCESS;
					}else{
						return Constants.CHECK_FALSE;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return Constants.CHECK_FALSE;
				}
	}

	@Override
	public int isdeleteCallplanning(Integer clientUserId, Integer clientId,
			String storeIds) throws BusinessException {
		try {
			Map<String, Object> propertyMap = new HashMap<String, Object>();
			propertyMap.put("clientUserId", clientUserId);
			propertyMap.put("clientId", clientId);
			propertyMap.put("storeIds", storeIds);
			return callPlanningDao.isdeleteExistCallplanning(propertyMap);
		} catch (Exception e) {
			LOG.error("method isdeleteCallplanning error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
	}

	@Override
	public int isdeleteCallplanning(String clientUserIds, Integer clientId,
			String storeId) throws BusinessException {
		try {
			Map<String, Object> propertyMap = new HashMap<String, Object>();
			propertyMap.put("clientUserIds", clientUserIds);
			propertyMap.put("clientId", clientId);
			propertyMap.put("storeId", storeId);
			return callPlanningDao.batchIsdeleteExistCallplanning(propertyMap);
		} catch (Exception e) {
			LOG.error("method isdeleteCallplanning error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
	}

	@Override
	public ResultMessage addPlannImport(MultipartFile file,
			HttpServletRequest req, HttpServletResponse res, Integer clientId)
			throws BusinessException {
		List<CallPlanning> cps = new ArrayList<CallPlanning>();
		ExcelReader reader = new ExcelReader(file);
		List<String[]> values = reader.getAllData(0);
		
		List<String[]> errorData = new  ArrayList<String[]>();
		List<String> errorMessage = new ArrayList<String>();
		
		Set<String> rowSet = new HashSet<String>();
		
		
		Map<String,Object> parmater = new HashMap<String,Object>();
		parmater.put("clientId", clientId);
		/**查找人员*/
		Map<String,ClientUser> clientUserlist = clientUserDao.findClientUserBylName(parmater);
		
		/**查找门店*/
		List<String> storeSet = storeDao.getStoreIdMap(parmater);
		Set<String> set = new HashSet<String>(storeSet);
		
		List<Store> storelist = storeDao.getStoreIdList(parmater);
		
		String[] titles = values.get(0);
		errorData.add(titles);
		//列名校验
		for (int i = 0; i < titles.length; i++) {
			if(StringUtils.isEmpty(titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("列名不能为空");
				return rm;
			}
			if(!MobiStringUtils.contains(ImportConstants.CTBAT_PLANN_IMPORT, titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("存在不能识别的列名：" +titles[i]);
				return rm;
			}
		}
		
		out:for (int i = 1; i < values.size(); i++) {
			String[] rows = values.get(i);
			String name = rows[0];
			String callOne = rows[1];
			String callTwo = rows[2];
			String callHz = rows[3];
			String callStore = rows[4];
			String sotreAddr = rows[5];
			if(StringUtils.isEmpty(callOne) && StringUtils.isEmpty(callTwo)){
				errorData.add(rows);
				errorMessage.add("拜访日期不能为空");
				continue;
			}
			
			if(!clientUserlist.containsKey(name)){
				errorData.add(rows);
				errorMessage.add("人员不存在");
				continue;
			}
			if(StringUtils.isEmpty(callStore)){
				errorData.add(rows);
				errorMessage.add("门店不能为空");
				continue;
			}
			if(StringUtils.isEmpty(sotreAddr)){
				errorData.add(rows);
				errorMessage.add("门店地址不能为空");
				continue;
			}
			if(!set.contains(callStore+"@"+sotreAddr) ){
				errorData.add(rows);
				errorMessage.add("门店不存在");
				continue;
			}
			parmater.put("callOne", callOne);
			parmater.put("callTwo", callTwo);
			parmater.put("clientUserId", clientUserlist.get(name).getClientUserId());
			String storeId = null;
			for (Store store : storelist) {
				if(store.getStoreNameAndaddr().equals(callStore+"@"+sotreAddr)){
					storeId = store.getStoreId();
					break;
				}
					
			}
			parmater.put("storeId", storeId);
			parmater.put("Isdelete", Constants.IS_DELETE_FALSE);
			StoreUserMapping sum = storeUserMappingDao.getStoreUserMapping(parmater);
			if(sum == null){
				errorData.add(rows);
				errorMessage.add("该人员不属于此门店");
				continue;
			}
			String callDate = callOne+","+callTwo;
			if(callOne == null || callOne.equals("")){
				callDate = callTwo;
			}
			String[] callDateArray = callDate.split(",");
			for (int j = 0; j < callDateArray.length; j++) {
				CallPlanning call = new CallPlanning();
				String uuid = UUID.randomUUID().toString();
				call.setPlanningId(uuid);
				call.setClientUserId(clientUserlist.get(name).getClientUserId());
				call.setPopId(storeId);
				call.setPlanningType(Constants.WEB_PLANNING_TYPE);
				call.setVisitType(Constants.RUN_VISIT_TYPE);
				call.setWorkType(Constants.WEB_WORK_TYPE_STORE);
				call.setCallStatus(Constants.NO_CALL_STATUS);
				call.setClientId(clientId);
				if(!StringUtils.isEmpty(callDateArray[j])){
					callDateArray[j] = callDateArray[j].replaceAll("/", "-");
					boolean validDate = DateUtil.isValidDate(callDateArray[j]);
					if(!validDate){
						errorData.add(rows);
						errorMessage.add(callDateArray[j]+"拜访日期格式不正确");
						continue;
					}
				}
				call.setCallDate(DateUtil.toDate(callDateArray[j],DateUtil.dateFormat));
				if(callDateArray[j] != null && !callDateArray[j].equals("") && DateUtil.toSimpleDate(callDateArray[j]).getTime() < DateUtil.toYMDDate(new Date()).getTime()){
					errorData.add(rows);
					errorMessage.add(callDateArray[j]+"-拜访日期已过期");
					continue;
				}
				parmater.put("callDate", callDateArray[j]);
				List<CallPlanning> callList = callPlanningDao.findCallIsVisit(parmater);
				if(callList != null && !callList.isEmpty()){
					errorData.add(rows);
					errorMessage.add(callDateArray[j]+"-人员该日期存在拜访计划");
					continue;
				}
				StringBuilder builder = new StringBuilder();
				builder.append(name).append(callDateArray[j]).append(callStore).append(sotreAddr);
				if(rowSet.contains(builder.toString())){
					errorData.add(rows);
					errorMessage.add("重复数据不能导入");
					continue out;
				}else{
					cps.add(call);
					rowSet.add(builder.toString());
				}
				
			}
		}
		/**批量新增计划*/
		if(cps != null && !cps.isEmpty()){
			callPlanningDao.inserList(cps);
		}
		Map<String, Object> resultMessage = new HashMap<String, Object>();
		resultMessage.put("errorCount", errorData.size() - 1);
		resultMessage.put("successCount", cps.size());
		if (errorMessage != null && !errorMessage.isEmpty()) {
			String excelName = "errorCtbatExcel_"+System.currentTimeMillis()+".xlsx";
			reader.importResult(res,errorData,errorMessage,clientId,excelName);
			resultMessage.put("errDataExcelPath", excelName);
		}
		ResultMessage rm = ResultMessage.IMPORT_SUCCESS_RESULT;
		rm.setAttributes(resultMessage);
		return rm;
	}
	
}
