package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.StoreUserMappingDao;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.base.service.StoreUserMappingService;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.visit.dao.CallPlanningDao;
import cn.mobilizer.channel.visit.service.CallPlanningService;


@Service
public class StoreUserMappingServiceImpl implements StoreUserMappingService {
	private static final Log LOG = LogFactory.getLog(StoreUserMappingServiceImpl.class);

	@Autowired
	private StoreUserMappingDao  storeUserMappingDao;
	@Autowired
	private CallPlanningDao callplanningDao;
	
	@Override
	public List<StoreUserMapping> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreUserMapping getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoreUserMapping> getStoreUserMappingByStoreId(Integer clientId,String storeId) throws BusinessException{
		List<StoreUserMapping> ls = null;
		try {
			Map<String, Object> parames = new HashMap<String, Object>();
			parames.put("clientId", clientId);
			parames.put("storeId", storeId);
			ls = storeUserMappingDao.getStoreUserMappingByStoreId(parames);
		} catch (BusinessException e) {
			LOG.error("method getStoreUserMappingByStoreId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}
	
	@Override
	public List<StoreUserMapping> getStoreUserMappingByStoreId(Integer clientId,String storeId, String initData) throws BusinessException{
		List<StoreUserMapping> ls = null;
		try {
			Map<String, Object> parames = new HashMap<String, Object>();
			parames.put("clientId", clientId);
			parames.put("storeId", storeId);
			parames.put("clientPositionIds", initData);
			ls = storeUserMappingDao.getStoreUserMappingByStoreId(parames);
		} catch (BusinessException e) {
			LOG.error("method getStoreUserMappingByStoreId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public void updateClientUser(Integer clientId,Integer clientUserId1, Integer clientUserId2)throws BusinessException {
		try {
			/*批量修改之前，把置换掉的业务员的人店对应关系全部置为1*/
			Map<String, Object> parames = new HashMap<String, Object>();
			parames.put("clientId", clientId);
			parames.put("clientUserId", clientUserId1);
			List <StoreUserMapping> storeUserMappingList = storeUserMappingDao.selectStoreUserMappingByClientUserId(parames);
			for (StoreUserMapping storeUserMapping : storeUserMappingList) {
				storeUserMapping.setIsDelete(Constants.IS_DELETE_TRUE);
				storeUserMapping.setLastUpdateTime(null);
				storeUserMappingDao.updateStoreUserMapping(storeUserMapping);
				/*更换业务员之前，判断映射表中是否存在着删除的对应关系*/
				String storeId = storeUserMapping.getStoreId();
				Map<String, Object> parame = new HashMap<String, Object>();
				parame.put("clientId", clientId);
				parame.put("storeId", storeId);
				parame.put("clientUserId", clientUserId2);
				List<StoreUserMapping> storeUserMappingList1 = storeUserMappingDao.getStoreUserMappingList(parame);
				StoreUserMapping storeUserMapping1 = null;
				if (storeUserMappingList1 != null && storeUserMappingList1.size() > 0) {
					storeUserMapping1 = storeUserMappingList1.get(0);
				}
				if(storeUserMapping1 != null){
					storeUserMapping1.setIsDelete(Constants.IS_DELETE_FALSE);
					storeUserMapping1.setLastUpdateTime(null);
					storeUserMappingDao.updateStoreUserMapping(storeUserMapping1);
				}else{
					StoreUserMapping storeUserMapping2 = new StoreUserMapping();
					String mappingId = UUID.randomUUID ().toString ();
					storeUserMapping2.setClientUserId(clientUserId2);
					storeUserMapping2.setStoreId(storeUserMapping.getStoreId());
					storeUserMapping2.setClientId(clientId);
					storeUserMapping2.setMappingId(mappingId);
					storeUserMapping2.setIsDelete(Constants.IS_DELETE_FALSE);
					storeUserMappingDao.add(storeUserMapping2);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method updateClientUser error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
	}
	
	

	@Override
	public List<StoreUserMapping> getStoreUserMappingByClientUserId(
			Integer clientId, Integer clientUserId) throws BusinessException {
		List<StoreUserMapping> list = null;
		try {
			Map<String,Object> parames = new HashMap<String, Object>();
			parames.put("clientId", clientId);
			parames.put("clientUserId", clientUserId);
			list = storeUserMappingDao.selectStoreUserMappingByClientUserId(parames);
		} catch (BusinessException e) {
			LOG.error("method getStoreUserMappingByClientUserId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public int queryStoreUserMappingCount(Map<String, Object> parameters)throws BusinessException {
		Integer count = null;
		try {
			count = storeUserMappingDao.queryStoreUserMappingCount(parameters);
		} catch (BusinessException e) {
			LOG.error("method queryStoreUserMappingCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<Store> queryStoreList(Map<String, Object> parameters)
			throws BusinessException {
		List<Store> list = null;
		try {
			list = storeUserMappingDao.queryStoreList(parameters);
		} catch (BusinessException e) {
			LOG.error("method queryStoreList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}



	@Override
	public int deleteUserAndStoreMapping(Map<String, Object> parameters) throws BusinessException {
			int currentMapppingisdelte;
			try {
				callplanningDao.isdeleteExistCallplanning(parameters);
				currentMapppingisdelte = storeUserMappingDao.currentMapppingisdelte(parameters);
			} catch (Exception e) {
				LOG.error("method deleteUserAndStoreMapping error, ", e);
				throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
			}
		return currentMapppingisdelte;
	}



	@Override
	public StoreUserMapping oldUserAndStoreMapping(Map<String, Object> parameters) throws BusinessException {
		
		return storeUserMappingDao.userAndStoreMapping(parameters);
	}

	@Override
	public int addStoreUserMapping(StoreUserMapping storeUserMapping) throws BusinessException {
		
		return storeUserMappingDao.add(storeUserMapping);
	}

	@Override
	public int updateStoreUserMapping(StoreUserMapping storeUserMapping) throws BusinessException {
		
		return storeUserMappingDao.updateStoreUserMapping(storeUserMapping);
	}

	@Override
	public String initStoreMappingData(Integer clientId, String storeId, String initData) throws BusinessException{
		String str = null;
		try {
			Map<String,Object> parames = new HashMap<String, Object>();
			parames.put("clientId", clientId);
			parames.put("storeId", storeId);
			parames.put("clientPositionIds", initData);
			str = storeUserMappingDao.initStoreMappingData(parames);
		} catch (BusinessException e) {
			LOG.error("method initStoreMappingData error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return str;
	}

	@Override
	public List<StoreUserMapping> getStoreUserMappingByStoreIdAndClientId(String storeId,Integer clientId) throws BusinessException{
		List<StoreUserMapping> ls = null;
		try {
			Map<String, Object> parames = new HashMap<String, Object>();
			parames.put("clientId", clientId);
			parames.put("storeId", storeId);
			ls = storeUserMappingDao.selectByParams(parames);
		} catch (BusinessException e) {
			LOG.error("method getStoreUserMappingByStoreId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public void deleteByClientUserId(Integer clientUserId) throws BusinessException {
		storeUserMappingDao.deleteByClientUserId(clientUserId);
	}

	@Override
	public void batchUpdateStoreUserMapping(String[] storeIds,Integer newClientUserId,Integer clientId) throws BusinessException {
		for (int i = 0; i < storeIds.length; i++) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("clientId", clientId);
			param.put("clientUserId2", newClientUserId);
			param.put("strStoreIds", storeIds[i]);
			StoreUserMapping oldUserAndStoreMapping = storeUserMappingDao.userAndStoreMapping(param);
			if(oldUserAndStoreMapping != null){
				oldUserAndStoreMapping.setIsDelete(Constants.IS_DELETE_FALSE);
				storeUserMappingDao.updateStoreUserMapping(oldUserAndStoreMapping);
			}else{
				StoreUserMapping newUserAndStoreMapping=new StoreUserMapping();
				String uuid = UUID.randomUUID ().toString ();
				newUserAndStoreMapping.setMappingId(uuid);
				newUserAndStoreMapping.setClientId(clientId);
				newUserAndStoreMapping.setClientUserId(newClientUserId);
				newUserAndStoreMapping.setStoreId(storeIds[i]);
				storeUserMappingDao.add(newUserAndStoreMapping);
			}
			
		}
	}

	@Override
	public void updateStoreUser(String hiddenStoreId,String oldStoreIdStrs, Integer clientUserId, Integer clientId) throws BusinessException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("clientId", clientId);
		params.put("clientUserId", clientUserId);
		String[] oldStoreIds = null;
		String[] newStoreIds = null;
		if(!oldStoreIdStrs.equals("") && oldStoreIdStrs!=null){
			oldStoreIds = oldStoreIdStrs.split(",");
		}
		if(!hiddenStoreId.equals("") && hiddenStoreId!=null){
			newStoreIds = hiddenStoreId.split(",");
		}
		if(oldStoreIds == null){
			if(newStoreIds != null){
				// 如果以前的Id为空,那么全部为新增
				batchUpdateStoreUserMapping(newStoreIds,clientUserId,clientId);						
			}
		}else{
			if(newStoreIds == null){
				// 如果新的Id为空,那么全部为删除			 
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("clientId", clientId);
				param.put("clientUserId", clientUserId);
				param.put("storeIds",StringUtils.join(oldStoreIds,","));
				deleteUserAndStoreMapping(param);					 		 				
			}else{
				/** 获取old中存在而new中不存在的人员，删除 **/
				String storeIds = ArrayUtil.arraySubtract2Str(oldStoreIds, newStoreIds);				
				if(storeIds != "" && storeIds.lastIndexOf(",")==storeIds.length()-1){
					storeIds = storeIds.substring(0, storeIds.length()-1);
				}
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("clientId", clientId);
				param.put("clientUserId", clientUserId);
				param.put("storeIds", storeIds);
				deleteUserAndStoreMapping(param);		
				/** 获取new中存在而old中不存在的人员，新增 **/
				String[] newIds = ArrayUtil.arraySubtract(newStoreIds, oldStoreIds);
				Map<String, Object> param2 = new HashMap<String, Object>();
				if(newIds!=null && newIds.length>0){
					batchUpdateStoreUserMapping(newIds,clientUserId,clientId);	
				}
			}
		}
		/** 获取old中存在而new中不存在的人员，删除 **/
		String storeIds = ArrayUtil.arraySubtract2Str(oldStoreIds, newStoreIds);	
		/**解除未来时间执行的拜访计划*/
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		propertyMap.put("clientUserId", clientUserId);
		propertyMap.put("clientId", clientId);
		propertyMap.put("storeIds", storeIds);
		callplanningDao.isdeleteExistCallplanning(propertyMap);
	}

	@Override
	public Map<String, StoreUserMapping> getStoreUserMappingMap(Integer clientId) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		return storeUserMappingDao.getStoreUserMappingMap(params);
	}
	
}
