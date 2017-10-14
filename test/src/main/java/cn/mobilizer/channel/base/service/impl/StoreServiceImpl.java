package cn.mobilizer.channel.base.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.dao.ChainDao;
import cn.mobilizer.channel.base.dao.ClientStructureDao;
import cn.mobilizer.channel.base.dao.ClientUserDao;
import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.dao.StoreGroupDao;
import cn.mobilizer.channel.base.dao.StoreUserMappingDao;
import cn.mobilizer.channel.base.exception.ImprotException;
import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreGroup;
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.base.vo.GeoVo;
import cn.mobilizer.channel.base.vo.GeoVo.Bounds;
import cn.mobilizer.channel.base.vo.GeoVo.Point;
import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.ctTask.vo.StoreTask;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.visit.dao.CallPlanningDao;

/**
 * 
* @ClassName: StoreServiceImpl 
* @Description: 吐槽下，一个Service 上千行代码 ， 有多少可以重复使用，建议优化下
* @author  pengwei
* @date 2015年10月9日 下午12:20:07 
*
 */
@Service
public class StoreServiceImpl implements StoreService {
	private static final Log LOG = LogFactory.getLog(StoreServiceImpl.class);
	
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private StoreGroupDao storeGroupDao;
	@Autowired
	private ClientStructureDao clientStructureDao;
	@Autowired
	private StoreUserMappingDao sumDao;
	@Autowired
	private ClientUserDao clientUserDao;
	@Autowired
	private CallPlanningDao callPlanningDao;
	@Autowired
	private ChainDao chainDao;
	@Autowired
	private StoreUserMappingDao storeUserMappingDao;
	
	
	@Override
	public Integer queryStoreCount(Map<String, Object> param) throws BusinessException{
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = storeDao.queryStoreCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryStoreCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}
	
	@Override
	public List<Store> queryStoreList(Map<String, Object> param) throws BusinessException{
		List<Store> list = null;
		
		try {
			if ((param != null) && (param.size() > 0)) {
				list = storeDao.queryStoreList(param);	
			}
		} catch (BusinessException e) {
			LOG.error("method queryStoreCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	
	@Override
	public Integer queryAppleStoreCount(Map<String, Object> param) throws BusinessException{
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = storeDao.queryAppleStoreCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryStoreCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}
	
	@Override
	public List<Store> queryAppleStoreList(Map<String, Object> param) throws BusinessException{
		List<Store> list = null;
		
		try {
			if ((param != null) && (param.size() > 0)) {
				list = storeDao.queryAppleStoreList(param);	
			}
		} catch (BusinessException e) {
			LOG.error("method queryStoreCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public ImportVO saveAll(List<Store> stores) {
		ImportVO ivo = new ImportVO();
		for (Store store : stores) {
			String id = storeDao.insert(store);
			if(!StringUtils.isEmpty(id)){
				if(store.getUsers().size() > 0){
					for (ClientUser clientuser : store.getUsers()) {
						StoreUserMapping sum = new StoreUserMapping();
						sum.setClientId(store.getClientId());
						sum.setClientUserId(clientuser.getClientUserId());
						sum.setStoreId(id);
						sumDao.add(sum);
					}
				}
			ivo.setResult(true);
			}else{
				ivo.setResult(false);
				ivo.setMsg("插入失败");
				throw new ImprotException("插入失败！");
			}
		}
		return ivo;
	}
	
	@Override
	public List<StoreGroup> findStoreGroupsByClientId(Integer clientId) throws BusinessException{
		List<StoreGroup> list = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("clientId", clientId);
			param.put("isDelete", Constants.IS_DELETE_FALSE);
			list = storeGroupDao.queryStoreGroupList(param);
		} catch (BusinessException e) {
			LOG.error("method findStoreGroupsByClientId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<Store> getObjectList(Map<String, Object> parames) {
		return storeDao.getListByParames(parames);
	}

	@Override
	public Store getObject(Map<String, Object> parames) {
		return storeDao.getOneByParames(parames);
	}

	@Override
	public Store findStoreByStoreId(String storeId)throws BusinessException {
			Store store = null;
		try {
			 store = storeDao.findStoreByStoreId(storeId);	
			
		} catch (BusinessException e) {
			LOG.error("method updateStore error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		
		return store;
	}

	@Override
	public void updateStore(Store store, String[] oldClientUserIds)throws BusinessException{
		Map<String, Object> parames = new HashMap<String, Object>();
		try {
			storeDao.updateStore(store);
			updateStoreUserMapping(store,oldClientUserIds);
		} catch (BusinessException e) {
			LOG.error("method updateStore error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		
		
	}

	private void updateStoreUserMapping(Store store, String[] oldClientUserIds){
		String str = "";
		String [] clientUserIds = null;
		String storeId = store.getStoreId();
		Integer clientId = store.getClientId();
		String businessman = store.getBusinessman();
		String promotions = store.getPromotions();
		str = StringUtil.joinWithComma(businessman, promotions);
		if(StringUtils.isNotEmpty(str)){
			clientUserIds = ArrayUtil.arrayUnique(str.split(","));
		}
		if(oldClientUserIds == null){
			if (clientUserIds != null) {
				insertStoreUserMapping(storeId,clientId,clientUserIds);
			}
		} else {
			if (clientUserIds == null) {
				sumDao.deleteByStoreId(storeId);
			} else {
				Map<String, Object> parames = new HashMap<String, Object>();
				parames.put("storeId", storeId);
				/**获取以前存在而目前不存在的，删除**/
				String deleteClientUserIds = ArrayUtil.arraySubtract2Str(oldClientUserIds,clientUserIds);
				if(deleteClientUserIds != null && deleteClientUserIds != ""){
					parames.put("deleteClientUserIds", deleteClientUserIds);
					sumDao.deleteByStoreIdAndUserId(parames);
				}
				/**获取目前存在而以前不存在的，新增**/
				String[] insertClientUserIds = ArrayUtil.arraySubtract(clientUserIds, oldClientUserIds);
				if(insertClientUserIds != null) {
					insertStoreUserMapping(storeId,clientId,insertClientUserIds);
				}
			}
		}
		
	}
	
	private void insertStoreUserMapping(String storeId, Integer clientId, String[] clientUserIds){
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("storeId", storeId);
		for (String clientUserId : clientUserIds) {
			parames.put("clientUserId", Integer.parseInt(clientUserId));
			List<StoreUserMapping> storeUserMappingList = sumDao.getStoreUserMappingList(parames);
			if (storeUserMappingList != null && storeUserMappingList.size() > 0) {
				StoreUserMapping storeUserMapping = null;
				storeUserMapping = storeUserMappingList.get(0);
				storeUserMapping.setIsDelete(Constants.IS_DELETE_FALSE);
				sumDao.updateStoreUserMapping(storeUserMapping);
			}else{
				StoreUserMapping storeUserMapping = new StoreUserMapping();
				String mappingId = UUID.randomUUID().toString();
				storeUserMapping.setStoreId(storeId);
				storeUserMapping.setClientUserId(Integer.parseInt(clientUserId));
				storeUserMapping.setClientId(clientId);
				storeUserMapping.setMappingId(mappingId);
				storeUserMapping.setIsDelete(Constants.IS_DELETE_FALSE);
				sumDao.add(storeUserMapping);
			}
		}
	}
	@Override
	public void deleteStore(String storeId)throws BusinessException {
		Map<String, Object> parames = new HashMap<String, Object>();
		try {
			Store store = storeDao.getStore(storeId);
			byte isDelete = 1;
			store.setIsDelete(isDelete);
			storeDao.deleteStore(store);
			parames.put("storeId",storeId);
			sumDao.updateByStoreId(parames);
		} catch (BusinessException e) {
			LOG.error("method deleteStore error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		
	}

	@Override
	public void addStore(Store store) throws BusinessException {
		try {
			String uuid = UUID.randomUUID ().toString ();
			store.setStoreId(uuid);
			String storeId = storeDao.insert(store);
			String businessman = store.getBusinessman();
			String promotions = store.getPromotions();
			
			Integer[] clientUserIds = getClientUserIds(businessman, promotions);
			if(clientUserIds !=null && clientUserIds.length > 0) {
				for (Integer clientUserId : clientUserIds) {
					String mappingId = UUID.randomUUID().toString();
					StoreUserMapping storeUserMapping = new StoreUserMapping();
					storeUserMapping.setClientId(store.getClientId());
					storeUserMapping.setStoreId(storeId);
					storeUserMapping.setClientUserId(clientUserId);
					storeUserMapping.setMappingId(mappingId);
					sumDao.add(storeUserMapping);
				}
			}
		} catch (Exception e) {
			LOG.error("method addStore error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
		
	}

	@Override
	public List<Store> getRelationStore(Integer clientUserId, Integer clientId, String visitDate, Byte taskType) {
		List<Store> ls = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("clientId", clientId);
			param.put("clientUserId", clientUserId);
			param.put("startDate", DateUtil.getDayStart(visitDate));
			param.put("endDate", DateUtil.getDayEnd(visitDate));
			param.put("taskType", taskType);
			ls = storeDao.getRelationStore(param);
		} catch (BusinessException e) {
			LOG.error("method getRelationStore error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public List<Store> findStoreByStoreNo(Map<String, Object> params)
			throws BusinessException {
		List<Store> list = null;
		try {
			list = storeDao.findStoreByStoreNo(params);
		} catch (BusinessException e) {
			LOG.error("method findStoreByStoreNo error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	
	public List<Store> findNoAddrStore(Integer clientId){
		return storeDao.findNoAddrStore(clientId);
	}
	
	public int updateStoreInfo(Store store){
		return storeDao.updateStore(store);
	}

	@Override
	public Integer queryPopStoreCount(Map<String, Object> param) throws BusinessException{
		int count = 0;
		try {
			count = storeDao.queryPopStoreCount(param);
		} catch (BusinessException e) {
			LOG.error("method queryPopStoreCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<Store> queryPopStoreList(Map<String, Object> param) throws BusinessException{
		List<Store> list = null;
		try {
			list = storeDao.queryPopStoreList(param);	
		} catch (BusinessException e) {
			LOG.error("method queryPopStoreList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<Store> queryForListForReport(Map<String, Object> parameters)
			throws BusinessException {
		List<Store> list = null;
		try {
			list = storeDao.queryForListForReport(parameters);
		} catch (BusinessException e) {
			LOG.error("method queryForListForReport error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public Store selectByPrimaryKey(String storeId) throws BusinessException{
		return storeDao.selectByPrimaryKey(storeId);
	}

	@Override
	public List<Store> getStoreCoordinatesList(Integer clientId) throws BusinessException{
		List<Store> list = null;
		try {
			list = storeDao.getStoreCoordinatesList(clientId);
		} catch (BusinessException e) {
			LOG.error("method getStoreCoordinatesList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<Store> replaceStoreList(Map<String, Object> parameters) throws BusinessException {
	
		return storeDao.replaceStoreList(parameters);
	}

	@Override
	public Integer replaceStore(Map<String, Object> parameters) throws BusinessException {
		
		return storeDao.replaceStoreCount(parameters);
	}

	@Override
	public List<Store> batchChoiceStoreUserList(Map<String, Object> parameters)throws BusinessException {
		List<Store> storeList = storeDao.batchChoiceStoreUserList(parameters);
		for(Store store : storeList){
			parameters.put("storeId", store.getStoreId());
			String names = storeDao.getBatchStoreUserNames(parameters);
			store.setNames(names);
		}
		return storeList;
	}

	@Override
	public Integer batchChoiceStoreUserStore(Map<String, Object> parameters)throws BusinessException {
		return storeDao.batchChoiceStoreUserCount(parameters);
	}

	@Override
	public String replaceAllClientUser(Map<String, Object> parameters) throws BusinessException {
	
		return storeDao.queryReplaceStoreList(parameters);
	}
	
	private Integer[] getClientUserIds(String businessman, String promotions) {
		Set<Integer> set = new TreeSet<Integer>();
		if(businessman != null && businessman !="") {
			String[] arr = businessman.split(",");
			for ( int i = 0 ; i < arr.length ; i++ ) {
				set.add(Integer.parseInt(arr[i]));
			}
		}
		if(promotions != null && promotions !="") {
			String[] arr = promotions.split(",");
			for ( int i = 0 ; i < arr.length ; i++ ) {
				set.add(Integer.parseInt(arr[i]));
			}
		}
		Integer[] re = new Integer[set.size()];
		int j = 0;
		for ( int i : set ) {
			re[j++] = i;
		}
		return re;
	}

	@Override
	public Store findStoreByStoreNo(String storeNo, Integer clientId) throws BusinessException{
		Store store = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("clientId", clientId);
			param.put("storeNo", storeNo);
			List<Store> list = storeDao.findStoreByStoreNo(param);
			if(list != null && list.size() > 0) {
				store = list.get(0);
			}
		} catch (BusinessException e) {
			LOG.error("method findStoreByStoreNo error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return store;
	}

	@Override
	public List<Store> getStoreInfoByName(Map<String, Object> parameters) throws BusinessException {
		
		return storeDao.getStoreInfoByName(parameters);
	}

	@Override
	public Store getStore(String storeId) {		
		return storeDao.getStoreById(storeId);
	}

	@Override
	public String getBatchCheckStoreUser(Map<String, Object> params)throws BusinessException {		
		return storeDao.getBatchCheckStoreUser(params);
	}
	
	@Override
	public List<Store> findStoreByClientId(Integer clientId) throws BusinessException{
		List<Store> list = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			params.put("_order", "DESC");
			list = storeDao.getListByParames(params);
		} catch (BusinessException e) {
			LOG.error("method findStoreByClientId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	
	@Override
	public List<Store> selectTaskDataByPositionId(StoreTask storeTask,Long pageStart,Integer pageSize)throws BusinessException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ctTaskId", storeTask.getCtTaskId());
		params.put("clientId", storeTask.getClientId());		
		params.put("storeNo", storeTask.getStoreNo()==null?null:storeTask.getStoreNo());
		params.put("storeName", storeTask.getStoreName()==null?null:storeTask.getStoreName());
		params.put("provinceId", storeTask.getProvinceId()==null?null:storeTask.getProvinceId());
		params.put("cityId", storeTask.getCityId()==null?null:storeTask.getCityId());		
		params.put("clientUserIds", storeTask.getUserIds());
		params.put("subAllStructureId", storeTask.getSubAllStructureId());	
		params.put("_start",pageStart);
		params.put("_size", pageSize);
		return storeDao.selectTaskDataByPositionId(params);	 
	}

	@Override
	public Integer selectTaskDataCountByPositionId(StoreTask storeTask)throws BusinessException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ctTaskId", storeTask.getCtTaskId());
		params.put("clientId", storeTask.getClientId());		
		params.put("storeNo", storeTask.getStoreNo()==null?null:storeTask.getStoreNo());
		params.put("storeName", storeTask.getStoreName()==null?null:storeTask.getStoreName());
		params.put("provinceId", storeTask.getProvinceId()==null?null:storeTask.getProvinceId());
		params.put("cityId", storeTask.getCityId()==null?null:storeTask.getCityId());
		params.put("clientUserIds", storeTask.getUserIds());
		params.put("subAllStructureId", storeTask.getSubAllStructureId());		 
		return storeDao.selectTaskDataCountByPositionId(params);	 
	}
	
	@Override
	public List<Store> getStoreTaskList(StoreTask storeTask,Long pageStart,Integer pageSize) throws BusinessException{	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ctTaskId", storeTask.getCtTaskId());
		params.put("clientId", storeTask.getClientId());		
		params.put("subAllStructureId", storeTask.getSubAllStructureId());	
		params.put("storeNo", storeTask.getStoreNo()==null?null:storeTask.getStoreNo());
		params.put("storeName", storeTask.getStoreName()==null?null:storeTask.getStoreName());
		params.put("provinceId", storeTask.getProvinceId()==null?null:storeTask.getProvinceId());
		params.put("cityId", storeTask.getCityId()==null?null:storeTask.getCityId());		
		params.put("_start",pageStart);
		params.put("_size", pageSize);
		return storeDao.getStoreTaskList(params);
	}

	@Override
	public Integer getStoreTaskCount(StoreTask storeTask) throws BusinessException{		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ctTaskId", storeTask.getCtTaskId());
		params.put("clientId", storeTask.getClientId());		
		params.put("storeNo", storeTask.getStoreNo()==null?null:storeTask.getStoreNo());
		params.put("storeName", storeTask.getStoreName()==null?null:storeTask.getStoreName());
		params.put("provinceId", storeTask.getProvinceId()==null?null:storeTask.getProvinceId());
		params.put("cityId", storeTask.getCityId()==null?null:storeTask.getCityId());		
		params.put("subAllStructureId", storeTask.getSubAllStructureId());		 
		return storeDao.getStoreTaskCount(params);
	}

	@Override
	public List<Store> getStoreByName(Map<String, Object> params) throws BusinessException {
		
		return storeDao.getStoreByName(params);
	}

	@Override
	public List<Store> getStoreByFullName(Integer clientId, String storeName,Integer clientStructureId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("clientStructureId", clientStructureId);
		params.put("storeName", storeName);
		return storeDao.getStoreByFullName(params);
	}

	@Override
	public List<Store> getGeoStoreList(Integer clientId,Bounds bounds,Integer zoom) throws BusinessException {
		List<Store> resultList = new ArrayList<Store>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		List<Store> list = null;
		if(zoom<=8){
			list = storeDao.loadStoreListGroupByProvince(params);
		}else if(zoom>8 && zoom<=12){
			list = storeDao.loadStoreListGroupByCity(params);
		}else{
			list = storeDao.loadStoreList(params);
		}
		GeoVo geoVo = new GeoVo();
		for (Store store : list) {
			Point point = geoVo.getPoint();
			point.setLat(new BigDecimal(store.getLatitude()));
			point.setLng(new BigDecimal(store.getLongitude()));
			if(geoVo.isPointInRect(point, bounds)){
				resultList.add(store);
			}
		}
		return resultList;
	}

	@Override
	public List<Store> loadStoreList(Map<String, Object> params) throws BusinessException {
		List<Store> stores = storeDao.loadStoreList(params);
		return stores;
	}

	@Override
	public Integer loadStoreListItems(Map<String, Object> params)
			throws BusinessException {
		
		return storeDao.loadStoreListItems(params);
	}
	
	@Override
	public Integer findStoreCountInMsi(Map<String, Object> params) throws BusinessException{
		int count = 0;
		try {
			if ((params != null) && (params.size() > 0)) {
				count = storeDao.findStoreCountInMsi(params);
			}
		} catch (BusinessException e) {
			LOG.error("method findStoreCountInMsi error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<Store> findStoreListInMsi(Map<String, Object> params) throws BusinessException{
		List<Store> list = null;
		try {
			list = storeDao.findStoreListInMsi(params);
		} catch (BusinessException e) {
			LOG.error("method findStoreListInMsi error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list; 
	}

	@Override
	public String getBatchStore(Map<String, Object> params) throws BusinessException {
		return storeDao.getBatchStore(params);
	}

	@Override
	public List<Store> getBatchUnStoreUser(Map<String, Object> params) throws BusinessException {
		List<Store> storeList = storeDao.getBatchUnStoreUser(params);
		for(Store store : storeList){
			params.put("storeId", store.getStoreId());
			String names = storeDao.getBatchStoreUserNames(params);
			store.setNames(names);
		}
		return storeList;
	}

	@Override
	public Integer getBatchUnStoreUserCount(Map<String, Object> params)throws BusinessException {
		return storeDao.getBatchUnStoreUserCount(params);
	}

	@Override
	public String getBatchStoreIdsUnStoreUser(Map<String, Object> params) throws BusinessException {
		return storeDao.getBatchStoreIdsUnStoreUser(params);
	}

	@Override
	public String getStoreByStoreName(Integer clientId,String storeName)
			throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeName", storeName);
		return storeDao.getStoreByStoreName(params);
	}

	@Override
	public List<Store> loadStoreToSelectTwo(Map<String, Object> params) {
		
		return storeDao.loadStoreListByClientId(params);
	}

	@Override
	public Integer getStoreItemsByStoreNo(String storeNo,Integer clientId) throws BusinessException {
		
		return storeDao.getStoreItemsByStoreNo(storeNo,clientId);
	}

	@Override
	public void batchSaveStores(List<Store> stores,List<String> clientUserIds) throws BusinessException {
		List<StoreUserMapping> updateStoreUserMappings = new ArrayList<StoreUserMapping>();
		List<StoreUserMapping> insertStoreUserMappings = new ArrayList<StoreUserMapping>();
		List<Store> insertstorelist = new ArrayList<Store>();
		List<Store> updatestorelist = new ArrayList<Store>();
		/**CM RM DM 查找*/
		Map<String,StoreUserMapping> reClientPositionMap = storeUserMappingDao.getReClientPosition(stores.get(0).getClientId());
		
		/**保存重复storeNo*/
		Map<String,String> reStoreNoMap = new HashMap<String,String>();
		Map<String,Object> parmarter = new HashMap<String,Object>();
		parmarter.put("clientId", stores.get(0).getClientId());
		Map<String,Store> storeMap = storeDao.getStoreNoMap(parmarter);
		if(stores != null && !stores.isEmpty()){
			for(Store store : stores){
				/**判断系统是否有此门店 没有门店就新增*/
				if(storeMap.containsKey(store.getStoreNo())){
					/**前面已拼接好门店的clientUserId+"&CM或者RM或者DM@"+storeNo**/
					if(store.getList() != null && !store.getList().isEmpty()){
						for (String  clientUserIdAndStore : store.getList()) {
							String[] position = clientUserIdAndStore.split("&");
							/**根据CM或者RM或者DM@"+storeNo查询数据库是否有一条数据 如果存在就修改*/
							if(reClientPositionMap.containsKey(position[1])){
								StoreUserMapping sum = reClientPositionMap.get(position[1]);
								sum.setClientUserId(Integer.parseInt(position[0]));
								updateStoreUserMappings.add(sum);
							/**再判断没有CM或者RM或者DM的数据 在新增人店关联表中*/
							}else{
								StoreUserMapping storeUserMapping  = new StoreUserMapping();
								String uuid = UUID.randomUUID().toString();
								storeUserMapping.setMappingId(uuid);
								storeUserMapping.setClientId(store.getClientId());
								storeUserMapping.setClientUserId(Integer.parseInt(position[0]));
								storeUserMapping.setStoreId(storeMap.get(store.getStoreNo()).getStoreId());
								insertStoreUserMappings.add(storeUserMapping);
							}
						}
					}
					store.setStoreId(storeMap.get(store.getStoreNo()).getStoreId());
					updatestorelist.add(store);
				}else{
					/**下面处理逻辑和上面差不多**/
					String storeUUid =UUID.randomUUID().toString();
					if(store.getList() != null && !store.getList().isEmpty()){
						for (String  clientUserIdAndStore : store.getList()) {
							String[] position = clientUserIdAndStore.split("&");
							if(reClientPositionMap.containsKey(position[1])){
								StoreUserMapping sum = reClientPositionMap.get(position[1]);
								sum.setClientUserId(Integer.parseInt(position[0]));
								updateStoreUserMappings.add(sum);
							}else{
								StoreUserMapping storeUserMapping  = new StoreUserMapping();
								String uuid = UUID.randomUUID().toString();
								storeUserMapping.setMappingId(uuid);
								storeUserMapping.setClientId(store.getClientId());
								storeUserMapping.setClientUserId(Integer.parseInt(position[0]));
								storeUserMapping.setStoreId(storeUUid);
								insertStoreUserMappings.add(storeUserMapping);
							}
						}
					}
					/**门店编号重复性的处理**/
					if(!reStoreNoMap.containsKey(store.getStoreNo())){
						store.setStoreId(storeUUid);
						insertstorelist.add(store);
						reStoreNoMap.put(store.getStoreNo(), storeUUid);
					}else{
						store.setStoreId(reStoreNoMap.get(store.getStoreNo()));
						updatestorelist.add(store);
					}
				}
			}
		}
		/**批量修改store*/
		if(updatestorelist != null && !updatestorelist.isEmpty()){
			storeDao.itemUpdateStore(updatestorelist);
		}
		/**批量新增store*/
		if(insertstorelist != null && !insertstorelist.isEmpty()){
			storeDao.batchSaveStores(insertstorelist);
		}
		/**已存在的门店人员关系CM、DM、RM 做更新操作**/
		if(updateStoreUserMappings != null && !updateStoreUserMappings.isEmpty()){
			storeUserMappingDao.batchUpdateStoreUserMapping(updateStoreUserMappings);
		}
		/**门店人员关联批量insert*/
		if(insertStoreUserMappings != null && !insertStoreUserMappings.isEmpty()){
			sumDao.batchInsertStoreUserMapping(insertStoreUserMappings);
		}
	}
	
	
	@Override
	public void batchSaveStores(List<Store> stores) throws BusinessException {
		List<StoreUserMapping> storeUserMappings = new ArrayList<StoreUserMapping>();
		for(Store store : stores){
			Integer clientId = store.getClientId();
			String storeId = "";
			Integer clientUserId = store.getClientUserId();
			Store queryStore = storeDao.getStoreByStoreNo(store.getStoreNo(),clientId);
			Boolean isUpdateFlag = false;
			if(queryStore ==null){
				storeId = UUID.randomUUID().toString();
				store.setStoreId(storeId);
			}else{
				storeId = queryStore.getStoreId();
				store.setStoreId(storeId);
				isUpdateFlag = true;
			}
			StoreUserMapping storeUserMapping = new StoreUserMapping();
			storeUserMapping.setMappingId(UUID.randomUUID().toString());
			storeUserMapping.setStoreId(storeId);
			storeUserMapping.setClientUserId(clientUserId);
			storeUserMapping.setClientId(clientId);
			if(isUpdateFlag){
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("storeId",storeId);
				params.put("clientId", clientId);
				params.put("clientUserId", clientUserId);
				StoreUserMapping mapping = sumDao.getStoreUserMapping(params);
				if(mapping==null){
					storeUserMappings.add(storeUserMapping);
				}
			}else{
				storeUserMappings.add(storeUserMapping);
			}
		}
		storeDao.batchSaveStores(stores);
		sumDao.batchInsertStoreUserMapping(storeUserMappings);
	}
	

	@Override
	public void batchSaveBwybStores(List<Store> stores) throws BusinessException {
		List<StoreUserMapping> storeUserMappings = new ArrayList<StoreUserMapping>();
		List<Store> updateStores = new ArrayList<Store>();
		List<Store> addStores = new ArrayList<Store>();
		for(Store store : stores){
			Integer clientId = store.getClientId();
			Integer clientUserId = store.getClientUserId();
			Store queryStore = storeDao.getStoreByStoreNo(store.getStoreNo(),clientId);
			if(queryStore!=null){
				store.setStoreId(queryStore.getStoreId());
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("storeId",queryStore.getStoreId());
				params.put("clientId", clientId);
				sumDao.deleteStoreUserMappingByStoreId(params);
				StoreUserMapping storeUserMapping = new StoreUserMapping();
				storeUserMapping.setMappingId(UUID.randomUUID().toString());
				storeUserMapping.setStoreId(queryStore.getStoreId());
				storeUserMapping.setClientUserId(clientUserId);
				storeUserMapping.setClientId(clientId);
				storeUserMappings.add(storeUserMapping);
				updateStores.add(store);
			}else{
				store.setStoreId(UUID.randomUUID().toString());
				StoreUserMapping storeUserMapping = new StoreUserMapping();
				storeUserMapping.setMappingId(UUID.randomUUID().toString());
				storeUserMapping.setStoreId(store.getStoreId());
				storeUserMapping.setClientUserId(clientUserId);
				storeUserMapping.setClientId(clientId);
				storeUserMappings.add(storeUserMapping);
				addStores.add(store);
			}
		}
		for(Store store : updateStores){
			try{
				storeDao.updateStore(store);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(addStores.size()>0){
			storeDao.batchSaveBwybStores(addStores);
		}
		sumDao.batchInsertStoreUserMapping(storeUserMappings);
	}

	@Override
	public void deleteCallPalnning(Store store,String oldBusinessman,String oldPromotions,Integer clientId) throws BusinessException {
		String[]  newStrPromotions = (store.getPromotions() == null || store.getPromotions().equals(""))?null:store.getPromotions().split(",");
		String[] oldStrPromotions = (oldPromotions == null || oldPromotions.equals(""))?null:oldPromotions.split(",");
		
		String[]  newStrBusinessman = (store.getBusinessman() == null || store.getBusinessman().equals(""))?null:store.getBusinessman().split(",");
		String[] oldStrBusinessman = (oldBusinessman == null || oldBusinessman.equals(""))?null:oldBusinessman.split(",");
		/**old存在 new不存在*/
		String mergeStrPromotions = ArrayUtil.arraySubtract2Str(oldStrPromotions,newStrPromotions);
		String mergeBusinessman = ArrayUtil.arraySubtract2Str(oldStrBusinessman,newStrBusinessman);
		
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		propertyMap.put("clientId", clientId);
		propertyMap.put("storeId", store.getStoreId());
		propertyMap.put("clientUserIds", mergeStrPromotions);
		if(mergeStrPromotions != null && !mergeStrPromotions.equals("")){
			callPlanningDao.batchIsdeleteExistCallplanning(propertyMap);
		}
		Map<String, Object> propertyMapBusinessman = new HashMap<String, Object>();
		propertyMapBusinessman.put("clientId", clientId);
		propertyMapBusinessman.put("storeId", store.getStoreId());
		propertyMapBusinessman.put("clientUserIds", mergeBusinessman);
		if(mergeBusinessman != null && !mergeBusinessman.equals("")){
			callPlanningDao.batchIsdeleteExistCallplanning(propertyMapBusinessman);
		}
		
	}

	@Override
	public Map<String, Store> getStoreNoMap(Integer clientId) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		return storeDao.getStoreNoMap(params);
	}

	@Override
	public Store getStoreByStoreTypeAndNo(String storeNo, Integer storeType) throws BusinessException {
		return storeDao.getStoreByStoreTypeAndNo(storeNo, storeType);
	}

	@Override
	public void batchSaveAppleStores(List<Store> stores) throws BusinessException {
		//为保持数据不重复，不宜用批量更新
		for(Store store : stores){
			Store queryStore = storeDao.getStoreByStoreTypeAndNo(store.getStoreNo(), store.getStoreType());
			if(queryStore==null){
				store.setStoreId(UUID.randomUUID().toString());
				storeDao.insert(store);
			}else{
				store.setStoreId(queryStore.getStoreId());
				storeDao.updateStore(store);
			}
		}
	}

	@Override
	public void batchSaveWorkLogStores(List<Store> stores) throws BusinessException {
		storeDao.batchSaveWorkLogStores(stores);
	}

	@Override
	public List<Store> findStoreByStoreNameAndCityName(Map<String,Object> params) throws BusinessException {
		return storeDao.findStoreByStoreNameAndCityName(params);
	}

	@Override
	public Object addFerreroStore(MultipartFile file,
			HttpServletRequest request, HttpServletResponse resp,Integer clientId)
			throws BusinessException {
		Map<String,Object> parmater = new HashMap<String,Object>();
		parmater.put("clientId", clientId);
		List<Store> storelist = new ArrayList<Store>();
		List<StoreUserMapping> storeUserMappinglist = new ArrayList<StoreUserMapping>();
		ExcelReader reader = new ExcelReader(file);
		List<String[]> values = reader.getAllData(0);
		Map<String,Store> storeMap = storeDao.getStoreColumnKey(parmater, Constants.STORENO);//门店
		Map<String,ClientStructure> clientStructureMap = clientStructureDao.getClientStructureMapByName(parmater);//部门
		Map<String,Chain> chainMap = chainDao.getChainMap(parmater);//连锁
		Map<String,ClientUser> clientNameMap = clientUserDao.findClientUserBylName(parmater);//人员
		List<String[]> errorData = new ArrayList<String[]>();
		List<String> errorStr = new ArrayList<String>();
		
		Set<String> set = new HashSet<String>();
		Set<String> rowSet = new HashSet<String>();
		//表头
		String[] titles = values.get(0);
		errorData.add(titles);
		//列名校验
		for (int i = 0; i < titles.length; i++) {
			if(StringUtils.isEmpty(titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("列名不能为空");
				return rm;
			}
			if(!MobiStringUtils.contains(ImportConstants.FERRERO_COLUMN, titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("存在不能识别的列名：" +titles[i]);
				return rm;
			}
		}
		//数据的校验
		out:for (int i = 1; i < values.size(); i++) {
			String[] rows = values.get(i);
			String storeNO = rows[0];
			String clientStructure = rows[1];
			String chain = rows[2];
			String storeName = rows[3];
			String clientUserName = rows[4];
			Store store =  new Store();
			if(StringUtils.isNotEmpty(storeNO) && set.contains(storeNO)){
				errorData.add(rows);
				errorStr.add(storeNO+"门店编号重复");
				continue;
			}else if(StringUtils.isNotEmpty(storeNO) && !StringUtil.isSpecial(storeNO)){
				errorData.add(rows);
				errorStr.add(storeNO+"门店编号存在特殊字符");
				continue;
			}else{
				set.add(storeNO);
			}
			if(StringUtils.isNotEmpty(storeNO) && storeMap.containsKey(storeNO)){
				errorData.add(rows);
				errorStr.add("门店编号已存在");
				continue;
			}
			if(StringUtils.isEmpty(clientStructure)){
				errorData.add(rows);
				errorStr.add("部门不能为空");
				continue;
			}
			if(!clientStructureMap.containsKey(clientStructure)){
				errorData.add(rows);
				errorStr.add(clientStructure+"-部门不存在系统");
				continue;
			}
			if(StringUtils.isEmpty(chain)){
				errorData.add(rows);
				errorStr.add("连锁不能为空");
				continue;
			}
			if(!chainMap.containsKey(chain)){
				errorData.add(rows);
				errorStr.add(chain+"-连锁不存在系统");
				continue;
			}
			if(StringUtils.isEmpty(storeName)){
				errorData.add(rows);
				errorStr.add("门店不能为空");
				continue;
			}
			if(StringUtils.isEmpty(clientUserName)){
				errorData.add(rows);
				errorStr.add("促销员不能为空");
				continue;
			}
			String[] clientUserNames = clientUserName.split(",");
			for (int j = 0; j < clientUserNames.length; j++) {
				if(!clientNameMap.containsKey(clientUserNames[j])){
					errorData.add(rows);
					errorStr.add(clientUserNames[j]+"-促销员不存在系统");
					continue out;
				}
			}
			String uuid = UUID.randomUUID().toString();
			store.setStoreId(uuid);
			store.setStoreNo(storeNO);
			store.setClientStructureId(clientStructureMap.get(clientStructure).getClientStructureId());
			store.setChainId(chainMap.get(chain).getChainId());
			store.setStoreName(storeName);
			store.setClientId(clientId);
			
			/**人员和门店关联*/
			for (int j = 0; j < clientUserNames.length; j++) {
				StringBuffer sb = new StringBuffer();
				sb.append(clientStructure).append(chain).append(storeName).append(clientUserNames[j]);
				StoreUserMapping sm = new StoreUserMapping();
				String mappingId = UUID.randomUUID().toString();
				sm.setMappingId(mappingId);
				sm.setClientUserId(clientNameMap.get(clientUserNames[j]).getClientUserId());
				sm.setStoreId(uuid);
				sm.setClientId(clientId);
				if(rowSet.contains(sb.toString())){
					errorData.add(rows);
					errorStr.add("重复数据不能导入");
					continue out;
				}else{
					storeUserMappinglist.add(sm);
					rowSet.add(sb.toString());
				}
				
			}
			storelist.add(store);
		}
		/**批量新增store*/
		if(storelist != null && !storelist.isEmpty()){
			storeDao.batchSaveBwybStores(storelist);
		}
		/**批量新增门店人员关联*/
		if(storeUserMappinglist != null && !storeUserMappinglist.isEmpty()){
			storeUserMappingDao.batchInsertStoreUserMapping(storeUserMappinglist);
		}
		Map<String, Object> resultMessage = new HashMap<String, Object>();
		resultMessage.put("errorCount", errorData.size() - 1);
		resultMessage.put("successCount", storelist.size());
		
		if (errorStr != null && !errorStr.isEmpty()) {
			String excelName = "errFerreroExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			reader.importResult(resp,errorData,errorStr,clientId,excelName);
			resultMessage.put("errDataExcelPath", excelName);
		}else{
			resultMessage.put("errDataExcelPath", "");
		}
		return resultMessage;
	}

	@Override
	public List<Store> queryStoreListByNumber(Map<String, Object> parmater)
			throws BusinessException {
		List<Store> stores=storeDao.queryStoreListByNumber(parmater);
		if(stores!=null&& stores.size()!=0){
			for (Store store : stores) {
				if(store.getStoreName().length()>8){
					store.setStoreName(store.getStoreName().substring(0, 8)+"...");		
				}
			}
		}
		return stores;
	}

	@Override
	public Map<String, Store> queryAllStore(Integer clientId)
			throws BusinessException {
		return storeDao.queryAllStore(clientId);
	}

	@Override
	public List<Store> queryStoresByClientId(Integer clientId,String storeName) {
		if(null != clientId && !StringUtil.isBlank(storeName)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			map.put("storeName", storeName);
			return storeDao.queryStoreByClientIdAndLinkeStroeName(map);
		}
		return null;
	}
	
	

	@Override
	public Map<String, Object> importstore(List<String[]> values,
			Map<String, ClientUser> mapUser,
			Map<String, ClientStructure> mapDept,
			Map<String, Channel> mapChannel, Map<String, Chain> mapChain,
			Integer clientId) throws BusinessException {
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>();
		List<StoreUserMapping> storeUserMappinglist = new ArrayList<StoreUserMapping>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Store> mapStore= storeDao.queryAllStoreNo(clientId);
		Set<String> set = new HashSet<String>();
		if (null == values || values.isEmpty() || values.size() <= 1) {
			returnMap.put("errorMsg", "导入数据不能为空");
			return returnMap;
		}
		String[] titles = values.get(0); //获取导入数据表头
		for (String title : titles) {
			if (StringUtils.isEmpty(title)) {
				returnMap.put("errorMsg", "列名不能为空");
				return returnMap;
			} else if (!MobiStringUtils.contains(
					ImportConstants.STORECOMMON_TITLE, title)) {
				returnMap.put("errorMsg", "存在不能识别的列名：" + title);
				return returnMap;
			}
		}

		errDataList.add(titles);

		Map<String,Object> wareMap = new HashMap<String,Object>();	
		wareMap.put("clientId", clientId.toString());
		//Map<String, Warehouse> mwar= warehouseDao.selectAllWarehouse(wareMap);
		List<Store> insertList = new ArrayList<Store>();
		//数据校验
		for (int i = 1; i < values.size(); i++) {
			String[] vals = values.get(i);
			String storeN = vals[0];
			String storeName = vals[1];
			String addr = vals[2];
			String channel=vals[3];   //渠道
			String chain=vals[4];     //连锁
			String structureName = vals[5]; //部门
			String clientUser=vals[6];      //人员
			String storeNo=storeN.toUpperCase();
			if(StringUtil.isEmptyString(storeName)){
				errStrList.add("门店名称不能为空");
				errDataList.add(vals);
				continue;
		   }else if(!StringUtil.isSpecial(storeName)){
				 errStrList.add("门店名称不能有特殊字符");
				 errDataList.add(vals);
				 continue;
		   }
			if(StringUtil.isEmptyString(addr)){
				errStrList.add("地址不能为空");
				errDataList.add(vals);
				continue;
		  }
		    if(!(channel.equals(""))&&channel!=null){
				if(!mapChannel.containsKey(channel)){
					 errStrList.add("渠道不存在");
					 errDataList.add(vals);
					 continue;
			    }
		   }
		    if(!(chain.equals(""))&&chain!=null){
		   if(!mapChain.containsKey(chain)){
				 errStrList.add("连锁不存在");
				 errDataList.add(vals);
				 continue;
		   }
		    }
			if (StringUtil.isEmptyString(structureName)) {
				errStrList.add("部门不能为空");
				errDataList.add(vals);
				continue;
			} else if (!mapDept.containsKey(structureName)) {
				errStrList.add("部门不存在");
				errDataList.add(vals);
				continue;
			}
			if(StringUtils.isEmpty(clientUser)){
				errDataList.add(vals);
				errStrList.add("促销员不能为空");
				continue;
			}
			boolean flag = false;
			String[] clientUserNames = clientUser.split(",");
			for (int j = 0; j < clientUserNames.length; j++) {
				if(!mapUser.containsKey(clientUserNames[j])){
					errDataList.add(vals);
					errStrList.add(clientUserNames[j]+"-负责人不存在系统");
					flag = true;
					continue ;
				}
			}
			if(flag){
				continue;
			}  
			if(!(StringUtil.isEmptyString(storeNo))){
			if(StringUtils.isNotEmpty(storeNo) && mapStore.containsKey(storeNo)){
				errDataList.add(vals);
				errStrList.add("门店编号已存在");
				continue;
			}
		    if(StringUtils.isNotEmpty(storeNo) && set.contains(storeNo)){
				errDataList.add(vals);
				errStrList.add(storeNo+"门店编号重复");
				continue;
			}else if(StringUtils.isNotEmpty(storeNo) && !StringUtil.isSpecial(storeNo)){
				errDataList.add(vals);
				errStrList.add(storeNo+"门店编号存在特殊字符");
				continue;
			}else{
				set.add(storeNo);
			}
			}
			String uuid = UUID.randomUUID().toString();
			ClientStructure getClientStructureId = mapDept.get(structureName);
			Channel getChannelId=mapChannel.get(channel);
			Chain getChainId=mapChain.get(chain);
			//ClientUser getClientUserId=mapUser.get(clientUser);
			Store store=new Store();
			if(storeN!=null){
			store.setStoreNo(storeN);	
			}
			store.setStoreName(storeName);
			store.setAddr(addr);
			store.setClientStructureId(getClientStructureId.getClientStructureId());
			if(getChannelId!=null){
			store.setChannelId(getChannelId.getChannelId());
			}
			if(getChainId!=null){
			store.setChainId(getChainId.getChainId());
			}
			
			
			store.setClientId(clientId);
		 	store.setStoreId(uuid);
		 	
			 
		 	/**人员和门店关联*/
			for (int j = 0; j < clientUserNames.length; j++) {
				StringBuffer sb = new StringBuffer();
				sb.append(structureName).append(chain).append(storeName).append(clientUserNames[j]);
				StoreUserMapping sm = new StoreUserMapping();
				String mappingId = UUID.randomUUID().toString();
				sm.setMappingId(mappingId);
				sm.setClientUserId(mapUser.get(clientUserNames[j]).getClientUserId());
				sm.setStoreId(uuid);
				sm.setClientId(clientId);
		
					storeUserMappinglist.add(sm);
				
			}
			 
		 	
			insertList.add(store);
			
		}
		if (null != insertList && !insertList.isEmpty()) {
			storeDao.insterStoreAll(insertList);
		}
		/**批量新增门店人员关联*/
		if(storeUserMappinglist != null && !storeUserMappinglist.isEmpty()){
			storeUserMappingDao.batchInsertStoreUserMapping(storeUserMappinglist);
		}
		    Integer successCount = insertList.size();
			returnMap.put("successCount", successCount);
			returnMap.put("errorCount", errStrList.size());
	
			returnMap.put("errStrList", errStrList);
			returnMap.put("errDataList", errDataList);
	
			
		
			return returnMap;
		}

	public Map<String, Object> importHuishiStore(List<String[]> values,
			Map<String, ClientUser> mapUser,
			Map<String, ClientStructure> mapDept,
			Map<String, Channel> mapChannel, Map<String, Chain> mapChain,
			Integer clientId) throws BusinessException {
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>();
		List<StoreUserMapping> storeUserMappinglist = new ArrayList<StoreUserMapping>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Store> mapStore= storeDao.queryAllStoreNo(clientId);
		Set<String> set = new HashSet<String>();
		if (null == values || values.isEmpty() || values.size() <= 1) {
			returnMap.put("errorMsg", "导入数据不能为空");
			return returnMap;
		}
		String[] titles = values.get(0); //获取导入数据表头
		for (String title : titles) {
			if (StringUtils.isEmpty(title)) {
				returnMap.put("errorMsg", "列名不能为空");
				return returnMap;
			} else if (!MobiStringUtils.contains(
					ImportConstants.STOREHUISHI_TITLE, title)) {
				returnMap.put("errorMsg", "存在不能识别的列名：" + title);
				return returnMap;
			}
		}

		errDataList.add(titles);

		Map<String,Object> wareMap = new HashMap<String,Object>();	
		wareMap.put("clientId", clientId.toString());
		//Map<String, Warehouse> mwar= warehouseDao.selectAllWarehouse(wareMap);
		List<Store> insertList = new ArrayList<Store>();
		//数据校验
		for (int i = 1; i < values.size(); i++) {
			String[] vals = values.get(i);
			String storeN = vals[0];
			String storeName = vals[1];
			String structureName = vals[2];
			String structureNamecity = vals[3];
			String chain=vals[4];     //连锁
			String clientUser = vals[5]; //人员
			String mobileNo=vals[6];      //手机号
			String mapDeptClty=null;
			Integer mapDeptCltyid1=0;
			String storeNo=storeN.toUpperCase();
			/*if(structureName!=null||(!structureName.equals(""))){*/
			 Map<String,Object> parmatercs = new HashMap<String,Object>();
			 parmatercs.put("structurename", structureName);
			 parmatercs.put("clientId", clientId);
			Integer mapDeptCltyid=clientStructureDao.getStructureidByparentname(parmatercs);
			 mapDeptClty=clientStructureDao.getSubStructure(mapDeptCltyid);
			 Map<String,Object> parmatercs1 = new HashMap<String,Object>();
			 parmatercs1.put("structurename", structureNamecity);
			 parmatercs1.put("clientId", clientId);
			 mapDeptCltyid1=clientStructureDao.getStructureidByparentname(parmatercs1);
		//	}
			if(StringUtil.isEmptyString(storeName)){
				errStrList.add("门店名称不能为空");
				errDataList.add(vals);
				continue;
		   }else if(!StringUtil.isSpecial(storeName)){
				 errStrList.add("门店名称不能有特殊字符");
				 errDataList.add(vals);
				 continue;
		   }
			if(StringUtil.isEmptyString(mobileNo)){
				 errStrList.add("手机不能为空");
				 errDataList.add(vals);
			     continue;
			}else if(!StringUtil.isMobile(mobileNo)){
				 errStrList.add("手机格式有误");
				 errDataList.add(vals);
			     continue;
			}else if(!StringUtil.isNumber(mobileNo)){
				 errStrList.add("手机格式有误");
				 errDataList.add(vals);
			     continue;
			}
			
		 //  if(!(chain.equals(""))&&chain!=null){
			if(StringUtil.isEmptyString(chain)){
				 errStrList.add("连锁不能为空");
				 errDataList.add(vals);
			     continue;
			}else if(!mapChain.containsKey(chain)){
					 errStrList.add("连锁不存在");
					 errDataList.add(vals);
					 continue;
			    }
		  //  }
			if (StringUtil.isEmptyString(structureName)) {
				errStrList.add(structureName+"部门不能为空");
				errDataList.add(vals);
				continue;
			} else if (!mapDept.containsKey(structureName)) {
				errStrList.add(structureName+"部门不存在");
				errDataList.add(vals);
				continue;
			}
			if (mapDeptCltyid1==null) {
				errStrList.add(structureNamecity+"城市不存在");
				errDataList.add(vals);
				continue;
			}
			if (StringUtil.isEmptyString(structureNamecity)) {
				errStrList.add(structureNamecity+"城市不能为空");
				errDataList.add(vals);
				continue;
			} else if (!mapDeptClty.contains(mapDeptCltyid1.toString())) {
				errStrList.add(structureNamecity+"城市不存在");
				errDataList.add(vals);
				continue;
			}
			if(StringUtils.isEmpty(clientUser)){
				errDataList.add(vals);
				errStrList.add("负责人不能为空");
				continue;
			}
			boolean flag = false;
			String[] clientUserNames = clientUser.split(",");
			for (int j = 0; j < clientUserNames.length; j++) {
				if(!mapUser.containsKey(clientUserNames[j])){
					errDataList.add(vals);
					errStrList.add(clientUserNames[j]+"-负责人不存在系统");
					flag = true;
					continue ;
				}
			}
			if(flag){
				continue;
			}  
			if((StringUtil.isEmptyString(storeNo))){
				errDataList.add(vals);
				errStrList.add("门店编号不能为空");
				continue;
			}
			if(StringUtils.isNotEmpty(storeNo) && mapStore.containsKey(storeNo)){
				errDataList.add(vals);
				errStrList.add("门店编号已存在");
				continue;
			}
		    if(StringUtils.isNotEmpty(storeNo) && set.contains(storeNo)){
				errDataList.add(vals);
				errStrList.add(storeNo+"门店编号重复");
				continue;
			}else if(StringUtils.isNotEmpty(storeNo) && !StringUtil.isSpecial(storeNo)){
				errDataList.add(vals);
				errStrList.add(storeNo+"门店编号存在特殊字符");
				continue;
			}else{
				set.add(storeNo);
			}
			
			String uuid = UUID.randomUUID().toString();
			ClientStructure getClientStructureId = mapDept.get(structureNamecity);
			Chain getChainId=mapChain.get(chain);
			//ClientUser getClientUserId=mapUser.get(clientUser);
			Store store=new Store();
			store.setStoreNo(storeN);	
			store.setStoreName(storeName);
			store.setMobileNo(mobileNo);
			store.setClientStructureId(getClientStructureId.getClientStructureId());
			if(getChainId!=null){
			store.setChainId(getChainId.getChainId());
			}
			store.setClientId(clientId);
		 	store.setStoreId(uuid);
		 	
			 
		 	/**人员和门店关联*/
			for (int j = 0; j < clientUserNames.length; j++) {
				StringBuffer sb = new StringBuffer();
				sb.append(structureName).append(chain).append(storeName).append(clientUserNames[j]);
				StoreUserMapping sm = new StoreUserMapping();
				String mappingId = UUID.randomUUID().toString();
				sm.setMappingId(mappingId);
				sm.setClientUserId(mapUser.get(clientUserNames[j]).getClientUserId());
				sm.setStoreId(uuid);
				sm.setClientId(clientId);
		
					storeUserMappinglist.add(sm);
				
			}
			 
		 	
			insertList.add(store);
			
		}
		if (null != insertList && !insertList.isEmpty()) {
			storeDao.insterStoreAll(insertList);
		}
		/**批量新增门店人员关联*/
		if(storeUserMappinglist != null && !storeUserMappinglist.isEmpty()){
			storeUserMappingDao.batchInsertStoreUserMapping(storeUserMappinglist);
		}
		    Integer successCount = insertList.size();
			returnMap.put("successCount", successCount);
			returnMap.put("errorCount", errStrList.size());
	
			returnMap.put("errStrList", errStrList);
			returnMap.put("errDataList", errDataList);
	
			
		
			return returnMap;
	}

	@Override
	public Map<String, Store> queryAllStoreKeyName(Integer clientId)
			throws BusinessException {
		
		return storeDao.queryAllStoreKeyName(clientId);
	}

	@Override
	public Map<String, Object> importColgate2Store(List<String[]> values,
			Map<String, Province> mapProvince,
			Map<String, ClientStructure> mapDept,
			Map<String, Channel> mapChannel,
			Map<String, Options> mapOptionsType,
			Map<String, Options> mapOptionsYue, Integer clientId)
			throws BusinessException {
		
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Store> mapStore= storeDao.queryAllBannerphoto(clientId);
		Set<String> set = new HashSet<String>();
		if (null == values || values.isEmpty() || values.size() <= 1) {
			returnMap.put("errorMsg", "导入数据不能为空");
			return returnMap;
		}
		String[] titles = values.get(0); //获取导入数据表头
		for (String title : titles) {
			if (StringUtils.isEmpty(title)) {
				returnMap.put("errorMsg", "列名不能为空");
				return returnMap;
			} else if (!MobiStringUtils.contains(
					ImportConstants.STORECOLGATE2_TITLE, title)) {
				returnMap.put("errorMsg", "存在不能识别的列名：" + title);
				return returnMap;
			}
		}

		errDataList.add(titles);

		Map<String,Object> wareMap = new HashMap<String,Object>();	
		wareMap.put("clientId", clientId.toString());
		List<Store> insertList = new ArrayList<Store>();
		List<Store> updateList = new ArrayList<Store>();
		//数据校验
		for (int i = 1; i < values.size(); i++) {
			String[] vals = values.get(i); 
			String bannerPhot = vals[0];           //计划编码
			String storeNo = vals[1];               //门店代码
			String storeName = vals[2];             //门店名字
			String clientStructure = vals[3]; 		//区域
			String province=vals[4];      			//省
			String fax = vals[5];			 		//市
			String addr=vals[6];      		 		//店铺地址
			String externalNo = vals[7];    		//客户编码_SAP
			String remark = vals[8];         		//客户名称
			String channel = vals[9];     			//零售环境
			String contact = vals[10];      		//开始时间
			String telephoneNo = vals[11];  		//结束时间
			String mobileno = vals[12];      		//陈列类型
			String storetype = vals[13];   	 		//月份
			String bannerPhoto=bannerPhot.toUpperCase();
			
			if(StringUtil.isEmptyString(storeNo)){
				errStrList.add("门店代码不能为空");
				errDataList.add(vals);
				continue;
			}
			if(StringUtil.isEmptyString(contact)){
				errStrList.add("开始时间不能为空");
				errDataList.add(vals);
				continue;
			}
			if(StringUtil.isEmptyString(telephoneNo)){
				errStrList.add("结束时间不能为空");
				errDataList.add(vals);
				continue;
			}
			if(!StringUtil.isEmptyString(contact)){
			if(!StringUtil.isEmptyString(telephoneNo)){
					Date date1=	DateUtil.toSimpleDate(contact);
					Date date=	DateUtil.toSimpleDate(telephoneNo);
					if(!DateUtil.isCompareTime(date1,date)){
						errStrList.add("开始时间必须早于结束时间");
						errDataList.add(vals);
						continue;
					}
					if(contact.contains("/")){
						contact=contact.replace("/", "-");
					}
					if(telephoneNo.contains("/")){
						telephoneNo=telephoneNo.replace("/", "-");
					}
					if(contact.length()>10){
						contact= contact.substring(0, 10);
					}
					if(telephoneNo.length()>10){
						telephoneNo=telephoneNo.substring(0, 10);
					}
				}
			}
			if(StringUtil.isEmptyString(storeName)){
				errStrList.add("门店名称不能为空");
				errDataList.add(vals);
				continue;
		   }else if(!StringUtil.isSpecial(storeName)){
				 errStrList.add("门店名称不能有特殊字符");
				 errDataList.add(vals);
				 continue;
		   }
			if (StringUtil.isEmptyString(clientStructure)) {
				errStrList.add("部门不能为空");
				errDataList.add(vals);
				continue;
			} else if (!mapDept.containsKey(clientStructure)) {
				errStrList.add("部门不存在");
				errDataList.add(vals);
				continue;
			}
			if(!(province.equals(""))&&province!=null){
				  if (!mapProvince.containsKey(province)) {
					errStrList.add("省份不存在");
					errDataList.add(vals);
					continue;
				  }
			}
		    if(!(channel.equals(""))&&channel!=null){
						if(!mapChannel.containsKey(channel)){
							 errStrList.add("渠道不存在");
							 errDataList.add(vals);
							 continue;
					    }
			}
		    if(StringUtil.isEmptyString(mobileno)){
				errStrList.add("陈列类型不能为空");
				errDataList.add(vals);
				continue;
		   }else if (!mapOptionsType.containsKey(mobileno)) {
				errStrList.add("陈列类型不存在");
				errDataList.add(vals);
				continue;
		   }
		    if(StringUtil.isEmptyString(storetype)){
				errStrList.add("月份不能为空");
				errDataList.add(vals);
				continue;
		   }else if (!mapOptionsYue.containsKey(storetype)) {
				errStrList.add("月份不存在");
				errDataList.add(vals);
				continue;
		   } 
			
			if((StringUtil.isEmptyString(bannerPhoto))){
				errDataList.add(vals);
				errStrList.add("计划编码不能为空");
				continue;
			}
		    if(StringUtils.isNotEmpty(bannerPhoto) && set.contains(bannerPhoto)){
				errDataList.add(vals);
				errStrList.add(bannerPhoto+"计划编码重复");
				continue;
			}else if(StringUtils.isNotEmpty(storeNo) && !StringUtil.isSpecial(bannerPhoto)){
				errDataList.add(vals);
				errStrList.add(bannerPhoto+"计划编码存在特殊字符");
				continue;
			}else{
				set.add(bannerPhoto);
			}
			String uuid = UUID.randomUUID().toString();
			ClientStructure getClientStructureId = mapDept.get(clientStructure);
			Province getProvinceId=mapProvince.get(province);
			Channel getChannelId=mapChannel.get(channel);
			Options getOptionsType=mapOptionsType.get(mobileno); 
			Store store=new Store();
			store.setBannerPhoto(bannerPhot);
			store.setStoreNo(storeNo);	
			store.setStoreName(storeName);
			store.setClientStructureId(getClientStructureId.getClientStructureId());
			if(getProvinceId!=null){
				store.setProvinceId(getProvinceId.getProvinceId());
			}
			if(!(fax.equals(""))||fax!=null){
				store.setFax(fax);	
			}
			if(!(addr.equals(""))||addr!=null){
				store.setAddr(addr);
			}
			if(!(externalNo.equals(""))||externalNo!=null){
				store.setExternalNo(externalNo);
			}
			if(!(remark.equals(""))||remark!=null){
				store.setRemark(remark);
			}
			if(getChannelId!=null){
				store.setChannelId(getChannelId.getChannelId());
			}
			store.setContact(contact);
			store.setTelephoneNo(telephoneNo);
			store.setMobileNo(getOptionsType.getOptionValue().toString());
			store.setStoreType(Integer.valueOf(storetype));
			store.setClientId(clientId);
		 	store.setStoreId(uuid);
		 	store.setStatus(Byte.valueOf("1"));
		 	store.setStoreGroupId(12);
            
		 	if(mapStore.containsKey(bannerPhoto)){
		 		 updateList.add(store);
		 	}else{
		 	     insertList.add(store);
		 	}
		}
		if (null != insertList && !insertList.isEmpty()) {
			storeDao.insterStoreAll(insertList);
		}else if(null != updateList && !updateList.isEmpty()){
			storeDao.updateStoreAll(updateList);
		}
		Integer successCount = insertList.size() + updateList.size();
		returnMap.put("successCount", successCount);
		returnMap.put("errorCount", errStrList.size());

		returnMap.put("errStrList", errStrList);
		returnMap.put("errDataList", errDataList);
	
		return returnMap;
	}

	@Override
	public Map<String, Store> queryAllStoreName(Integer clientId)
			throws BusinessException {
		
		return storeDao.queryAllStoreName(clientId);
	}

	@Override
	public List<Store> getUserMappingStoreList(Map<String, Object> params) throws BusinessException {
		
		return storeDao.getUserMappingStoreList(params);
	}

	
}
