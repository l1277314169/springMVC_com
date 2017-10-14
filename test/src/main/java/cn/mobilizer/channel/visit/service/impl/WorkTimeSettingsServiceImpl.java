package cn.mobilizer.channel.visit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.dao.ClientPositionDao;
import cn.mobilizer.channel.base.dao.ClientUserDao;
import cn.mobilizer.channel.base.dao.OptionsDao;
import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.visit.dao.WorkTimeSettingsDao;
import cn.mobilizer.channel.visit.po.WorkTimeSettings;
import cn.mobilizer.channel.visit.service.WorkTimeSettingsService;

@Service
public class WorkTimeSettingsServiceImpl implements WorkTimeSettingsService {
	
	private static final Log LOG = LogFactory.getLog(WorkTimeSettingsServiceImpl.class);
	
	@Autowired
	private WorkTimeSettingsDao  workTimeSettingsDao;
	@Autowired
	private ClientUserDao clientUserDao;
	@Autowired
	private ClientPositionDao clientPositionDao;
	@Autowired
	private OptionsDao optionsDao;
	@Autowired
	private StoreDao storeDao; 
	
	
	@Override
	public List<WorkTimeSettings> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimeSettings getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryWorKTimeSettingsCount(Map<String, Object> parameters) throws BusinessException {
		int count = 0;
		try {
			count = workTimeSettingsDao.queryWorKTimeSettingsCount(parameters);
		} catch (BusinessException e) {
			LOG.error("method queryWorKTimeSettingsCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<WorkTimeSettings> queryWorkTimeSettingsList(
			Map<String, Object> parameters) throws BusinessException {
		List<WorkTimeSettings> list = null;
		try {
			list = workTimeSettingsDao.queryWorkTimeSettingsList(parameters);
		} catch (BusinessException e) {
			LOG.error("method queryWorkTimeSettingsList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public WorkTimeSettings getWorkTimeSettings(String settingId)
			throws BusinessException {
		WorkTimeSettings workTimeSettings = null;
		try {
			workTimeSettings = workTimeSettingsDao.getWorkTimeSettings(settingId);
		} catch (BusinessException e) {
			LOG.error("method getWorkTimeSettings error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return workTimeSettings;
	}

	@Override
	public void updateWorkTimesSetting(WorkTimeSettings workTimeSettings)
			throws BusinessException {
		try {
			workTimeSettingsDao.updateWorkTimesSetting(workTimeSettings);
		} catch (Exception e) {
			LOG.error("method updateWorkTimesSetting error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
	}

	@Override
	public ImportVO addWorkTimeSettings(List<WorkTimeSettings> list)
			throws BusinessException {
		ImportVO ivo = new ImportVO();
		try {
			if(list != null && list.size() > 0){
				workTimeSettingsDao.insertByList(list);
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ivo;
	}

	@Override
	public ResultMessage addImprtWorkTimeSettings(MultipartFile file,
			HttpServletRequest request,Integer clientId, HttpServletResponse resp) throws BusinessException {
		
		List<WorkTimeSettings> workTimeSettingslist = new ArrayList<WorkTimeSettings>();
		ExcelReader reader = new ExcelReader(file);
		List<String[]> values = reader.getAllData(0);
		if(values != null && !values.isEmpty()){
			List<String[]> errorData = new  ArrayList<String[]>();
			
			List<String> errorMessage = new ArrayList<String>();
			
			/**得到长促和促销队的clientPositionId*/
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("promotingGrowth", Constants.PROMOTING_GROWTH);
			params.put("promotingTeam", Constants.PROMOTING_TEAM);
			params.put("optionName", Constants.OPTIONS_NAME_WORK_TYPE);
			List<ClientPosition> clientPositionlist = clientPositionDao.findClientPositionByName(params);
			String 	clientPositionIds = "";
			Integer promotingGrowthId = null;
			Integer promotingTeamId = null;
			if(clientPositionlist != null && !clientPositionlist.isEmpty()){
				for (int i = 0; i < clientPositionlist.size(); i++) {
					if(clientPositionlist.get(i).getPositionName().equals(Constants.PROMOTING_GROWTH)){
						promotingGrowthId = clientPositionlist.get(i).getClientPositionId();
					}else{
						promotingTeamId= clientPositionlist.get(i).getClientPositionId();
					}
					clientPositionIds+= clientPositionlist.get(i).getClientPositionId().toString()+",";
				}
			}
			params.put("clientPositionIds", clientPositionIds);
			params.put("roleName", Constants.MOBI_OPTION_STORE);
			
			/**存在的登录名*/
			Map<String, ClientUser> findClientUserByKeylogName = clientUserDao.findClientUserByKeylogName(params);
			
			/**存在的班次*/
			Map<String, Options> findOptionValueByKey = optionsDao.findOptionValueByKey(params);
			
			/**促销队*/
			params.put("clientPositionId", promotingGrowthId);
		
			int updateSize = 0;
			String[] titles = null;
			if(values != null && values.size() > 0){
				/**表头*/
				titles = values.get(0);
				errorData.add(titles);
				/**列名校验*/
				for (int i = 0; i < titles.length; i++) {
					if(StringUtil.isEmptyString(titles[i])){
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("列名不能为空");
						return rm;
					}
					if(!MobiStringUtils.contains(ImportConstants.WORK_TIME_SETTINGS_TITLE, titles[i])){
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("存在不能识别的列名：" +titles[i]);
						return rm;
					}
				}
			}
			Integer clientPosition = null;
			Integer clientUserId = null;
			
			/**数据校验*/
			for (int i = 1; i < values.size(); i++) {
				WorkTimeSettings wts = new WorkTimeSettings();
				String[] rows = values.get(i);
				String _loginName = rows[1];
				String _work = rows[3];
				String _storeName = rows[7];				
				String _remark = rows[8];
				String _workDate = rows[4];
				if(!(_storeName.equals(""))){
					params.put("storename", _storeName);
					 /**存在的排班门店*/ 
				    String storename = storeDao.findStoreNameValueByKey(params) ; 			    
				     if(storename==null||storename.equals("")){
				    	 errorData.add(rows);
				    	 errorMessage.add("未知的排班门店");
						 continue;				
				    }
				} 
				if(!findClientUserByKeylogName.containsKey(_loginName)){
					errorData.add(rows);
					errorMessage.add("未知的登录名");
					continue;
				}else{
					ClientUser _user = findClientUserByKeylogName.get(_loginName);
					//在职且非禁用，用户为在职状态促销队才导入
					if(_user.getClientPositionId().equals(promotingTeamId) && _user.getStatus().equals(ChannelEnum.CLIENT_USER_STATUS.LZ.getKey()) || _user.getIsActivation().equals(ChannelEnum.CLIENT_USER_ISACTIVATION.JY.getKey())){
						errorData.add(rows);
						errorMessage.add("账号状态异常："+_loginName);
						continue;
					}
					clientUserId =_user.getClientUserId();
					clientPosition = _user.getClientPositionId();
					//在职且无法使用APP状态的用户，用户为在职状态长促才导入
					if(_user.getClientPositionId().equals(promotingGrowthId)){
						if(_user.getStatus() == ChannelEnum.CLIENT_USER_STATUS.ZZ.getKey() && _user.getIsActivation() == ChannelEnum.CLIENT_USER_ISACTIVATION.UN_APP.getKey() ){
							clientPosition = findClientUserByKeylogName.get(_loginName).getClientPositionId();
							clientUserId = findClientUserByKeylogName.get(_loginName).getClientUserId();
						}else{
							errorData.add(rows);
							errorMessage.add("账号状态异常："+_loginName);
							continue;
						}
					}
				}
				wts.setClientUserId(clientUserId);
				if(!findOptionValueByKey.containsKey(_work)){
					errorData.add(rows);
					errorMessage.add("未知的班次");
					continue;
				}
				wts.setWorkTimeType(findOptionValueByKey.get(_work).getOptionValue().byteValue());
			 
			
				/**长促*/
				params.put("storeName", _storeName);
				params.put("loginName", _loginName);
//				params.put("status", ChannelEnum.CLIENT_USER_STATUS.ZZ.getKey());
//				params.put("isActivation", ChannelEnum.CLIENT_USER_ISACTIVATION.UN_APP.getKey());
				Map<String, Store> promotingGrowthMap = storeDao.getStoreNameLoginNameMap(params);
				Map<String, Store>  promotingTeamMap = storeDao.getStoreNameList(params);
				Date workDate = null;
				Date startDate = null;
				Date endDate = null;
				String[] date = null;
				if(_workDate != null && !_workDate.equals("")){
					 date = _workDate.split(" ");
					try {
						workDate = DateUtil.toSimpleDate(_workDate);
					} catch (Exception e) {
						errorData.add(rows);
						errorMessage.add(titles[4]+"日期格式不正确");
						continue;
					}
					if(_work.equals(Constants.WORKTYPE)){
						rows[5] = Constants.TIMEONE;
						rows[6] = Constants.TIMEONE;
					}
					try {
						startDate = DateUtil.toDate(date[0] + " " + rows[5],DateUtil.dateTimeHM);
					} catch (Exception e) {
						errorData.add(rows);
						errorMessage.add(titles[5]+"日期格式不正确");
						continue;
					}
					try {
						endDate = DateUtil.toDate(date[0] + " " + rows[6],DateUtil.dateTimeHM);
					} catch (Exception e) {
						errorData.add(rows);
						errorMessage.add(titles[6]+"日期格式不正确");
						continue;
					}
				}
				
				if(!date[0].equals(DateUtil.formatDate(new Date(), DateUtil.dateFormat))){
					if(startDate.getTime()<new Date().getTime()){
							errorData.add(rows);
							errorMessage.add("工作日期已过期");
							continue;
						}
				}
				if(startDate.getTime()>endDate.getTime()){
					errorData.add(rows);
					errorMessage.add("开始时间大约结束时间");
					continue;
				}else{
					wts.setWorkDate(workDate);
					wts.setStartTime(startDate);
					wts.setEndTime(endDate);
				}
				  /**休息不验证下面的条件*/
//					if(clientPosition != null && clientPosition.equals(promotingTeamId) && !promotingTeamMap.containsKey(_storeName)){
//						errorData.add(rows);
//						errorMessage.add("系统不存在此门店");
//						continue;
//					}
					/**长促判断*/
					if(clientPosition != null && clientPosition.equals(promotingGrowthId) && !promotingGrowthMap.containsKey(_storeName)){
						errorData.add(rows);
						errorMessage.add("该用户不存在此门店");
						continue;
					}

				/**判断排班是否存在*/
				params.put("workDate", _workDate);
				params.put("clientUserId", clientUserId);
				List<WorkTimeSettings> wokSettinglist = workTimeSettingsDao.findWokSettingByDate(params);
				if(wokSettinglist != null && !wokSettinglist.isEmpty()){
					WorkTimeSettings workTimeSettings = wokSettinglist.get(0);
					workTimeSettings.setWorkTimeType(findOptionValueByKey.get(_work).getOptionValue().byteValue());
					workTimeSettings.setStartTime(startDate);
					workTimeSettings.setEndTime(endDate);
					if(clientPosition != null && clientPosition.equals(promotingGrowthId)){
						workTimeSettings.setStoreId(promotingGrowthMap.get(_storeName).getStoreId());
					}else{
						if(promotingTeamMap.containsKey(_storeName)){
							workTimeSettings.setStoreId(promotingTeamMap.get(_storeName).getStoreId());
						}
					}
					/**覆盖*/
					workTimeSettingsDao.updateWorkTimesSetting(workTimeSettings);
					updateSize++;
					continue;
				}
				if(clientPosition != null && clientPosition.equals(promotingGrowthId)){
					wts.setStoreId(promotingGrowthMap.get(_storeName).getStoreId());
				}else{
					if(promotingTeamMap.containsKey(_storeName)){
						wts.setStoreId(promotingTeamMap.get(_storeName).getStoreId());
					}
				}
				wts.setRemark(_remark);
				String uuid = UUID.randomUUID().toString();
				wts.setSettingId(uuid);
				wts.setClientId(clientId);
				workTimeSettingslist.add(wts);
			}
			/**插入排班数据*/
			if (null != workTimeSettingslist && !workTimeSettingslist.isEmpty()) {
				workTimeSettingsDao.insertByList(workTimeSettingslist);
			}
			Map<String, Object> resultMessage = new HashMap<String, Object>();
			resultMessage.put("errorCount", errorData.size() - 1);
			resultMessage.put("successCount", workTimeSettingslist.size()+updateSize);
			if (errorMessage != null && !errorMessage.isEmpty()) {
				String excelName = "errWorkTimeSettingsExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
				reader.importResult(resp,errorData,errorMessage,clientId,excelName);
				resultMessage.put("errDataExcelPath", excelName);
			}
			ResultMessage rm = ResultMessage.IMPORT_SUCCESS_RESULT;
			rm.setAttributes(resultMessage);
			return rm;
		}else{
			ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
			return rm;
		}
	}
}
