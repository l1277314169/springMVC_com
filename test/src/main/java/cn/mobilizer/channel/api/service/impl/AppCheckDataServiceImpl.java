package cn.mobilizer.channel.api.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.api.dao.AppDataChecklistDao;
import cn.mobilizer.channel.api.po.AppDataChecklist;
import cn.mobilizer.channel.api.service.AppCheckDataService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class AppCheckDataServiceImpl implements AppCheckDataService{
	
	private static final Log LOG = LogFactory.getLog(AppCheckDataServiceImpl.class);
	
	@Autowired
	private AppDataChecklistDao appDataChecklistDao;
	
	@Override
	public List<AppDataChecklist> saveCheckTableCount(Integer clientId, Integer clientUserId, String checkDate, String tableNames, List<AppDataChecklist> tlist) throws BusinessException, ParseException{
		Date date = StringUtil.isNotEmptyString(checkDate) ? DateUtil.getDateByStr(checkDate, DateUtil.dateFormat) : DateUtil.getYestoday();
		List<AppDataChecklist> reList = new ArrayList<AppDataChecklist>();
		try {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId", clientId);
				params.put("clientUserId",clientUserId);
				params.put("startDate", DateUtil.getDayStart(date));
				params.put("endDate", DateUtil.getDayEnd(date));
				
				/**从tables中获取需要检测的表，并分别查出需要比对的数据**/
				Map<String,String> visitCheckMap = new HashMap<String,String>();
				Map<String,String> planningCheckMap = new HashMap<String,String>();
				Map<String,String> ctTaskDataMap = new HashMap<String,String>();
				for ( String tableName : tableNames.split(",")) {
					if(tableName.equals("visiting_task_data")){
						Map<String,Map<String,Integer>> tp_map = appDataChecklistDao.getVisitMasterDateCount(params);
						if(tp_map !=null && tp_map.size() >0) {
							for ( Iterator it = tp_map.values().iterator() ; it.hasNext() ; ) {
								Map<Object,Object> in_map = (Map<Object, Object>) it.next();
//								visitCheckMap.putAll(in_map);
								visitCheckMap.put(in_map.get("id").toString(), in_map.get("value").toString());
							}
						}
					} else if(tableName.equals("call_planning")){
						Map<String,Map<String,Integer>> tp_map = appDataChecklistDao.getPlanningMasterDate(params);
						if(tp_map !=null && tp_map.size() >0) {
							for ( Iterator it = tp_map.values().iterator() ; it.hasNext() ; ) {
								Map<Object,Object> in_map = (Map<Object, Object>) it.next();
								planningCheckMap.put(in_map.get("id").toString(), in_map.get("value").toString());
//								planningCheckMap.putAll(in_map);
							}
						}
					} else if(tableName.equals("ct_task_data")){
						Map<String,Map<String,Integer>> tp_map = appDataChecklistDao.getCtTaskData(params);
						if(tp_map !=null && tp_map.size() >0) {
							for ( Iterator it = tp_map.values().iterator() ; it.hasNext() ; ) {
								Map<Object,Object> in_map = (Map<Object, Object>) it.next();
								ctTaskDataMap.put(in_map.get("id").toString(), in_map.get("value").toString());
//								planningCheckMap.putAll(in_map);
							}
						}
					}
				}
				
				/**循环上传的list逐条比对,比对有问题的直接返回，比对完后入库**/
				for ( AppDataChecklist appDataChecklist : tlist ) {
					appDataChecklist.setClientUserId(clientUserId);
					appDataChecklist.setClientId(clientId);
					String pkDataId = appDataChecklist.getPkDataId();
					Integer slaveTableRows = appDataChecklist.getSlaveTableRows();
					
					if(appDataChecklist.getTableName().equals("visiting_task_data")){
						if(visitCheckMap !=null && visitCheckMap.size() >0) {
							if(visitCheckMap.get(pkDataId) != null && (visitCheckMap.get(pkDataId).equals(slaveTableRows.toString()))) {
								appDataChecklist.setStatus(Constants.DATA_CHECK_STATUS_2);
							} else {
								appDataChecklist.setStatus(Constants.DATA_CHECK_STATUS_3);
								reList.add(appDataChecklist);
							}
						} else {
							appDataChecklist.setStatus(Constants.DATA_CHECK_STATUS_3);
							reList.add(appDataChecklist);
						}
					} else if(appDataChecklist.getTableName().equals("call_planning")){
						if(planningCheckMap !=null && planningCheckMap.size() >0) {
							if(planningCheckMap.get(pkDataId) != null && (planningCheckMap.get(pkDataId).equals(slaveTableRows.toString()))) {
								appDataChecklist.setStatus(Constants.DATA_CHECK_STATUS_2);
							} else {
								appDataChecklist.setStatus(Constants.DATA_CHECK_STATUS_3);
								reList.add(appDataChecklist);
							}
						} else {
							appDataChecklist.setStatus(Constants.DATA_CHECK_STATUS_3);
							reList.add(appDataChecklist);
						}
					} else if(appDataChecklist.getTableName().equals("ct_task_data")){
						if(ctTaskDataMap !=null && ctTaskDataMap.size() >0) {
							if(ctTaskDataMap.get(pkDataId) != null && (ctTaskDataMap.get(pkDataId).equals(slaveTableRows.toString()))) {
								appDataChecklist.setStatus(Constants.DATA_CHECK_STATUS_2);
							} else {
								appDataChecklist.setStatus(Constants.DATA_CHECK_STATUS_3);
								reList.add(appDataChecklist);
							}
						} else {
							appDataChecklist.setStatus(Constants.DATA_CHECK_STATUS_3);
							reList.add(appDataChecklist);
						}
					}
				}
				
				/**上传数据入库**/
				insertAppDataChecklistByList(tlist);
		} catch (BusinessException e) {
			LOG.error("method checkTableCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_COMPARE);
		}
		
		return reList;
	}

	public void insertAppDataChecklistByList(List<AppDataChecklist> tlist) throws BusinessException{
			appDataChecklistDao.inertAll(tlist);
	}
}
