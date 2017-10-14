package cn.mobilizer.channel.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.vo.StoreSearchVO;
import cn.mobilizer.channel.base.vo.StoreSingle;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.exception.BusinessException;
@Repository
public class StoreDao extends MyBatisDao {

	public StoreDao() {
		super("STORE");
	}

	public Integer queryStoreCount(Map<String, Object> params) {
		return super.get("queryTotalCount", params);
	}

	public List<Store> queryStoreList(Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}

	public Integer queryAppleStoreCount(Map<String, Object> params) {
		return super.get("queryAppleTotalCount", params);
	}

	public List<Store> queryAppleStoreList(Map<String, Object> params) {
		return super.queryForList("findAppleListByParams", params);
	}

	public boolean saveAll(List<Store> storeList) {
		boolean re = true;
		for (Store store : storeList) {
			int i = super.insert("insertSelective", store);
			if (i == 0) {
				re = false;
				break;
			}
		}
		return re;
	}

	// 根据参数获取列表，可以适用所有基础数据查询
	public List<Store> getListByParames(Map<String, Object> parames) {
		return super.queryForList("selectByParams", parames);
	}

	// 根据参数获取列表，可以适用所有基础数据查询
	public Store getOneByParames(Map<String, Object> parames) {
		return super.get("selectByParams", parames);
	}

	public Store findStoreByStoreId(String storeId) {
		return super.get("selectByStoreId", storeId);
	}

	public Store selectByPrimaryKey(String storeId) {
		return super.get("selectByPrimaryKey", storeId);
	}

	public int updateStore(Store store) {
		return super.update("updateByPrimaryKeySelective", store);
	}

	public void deleteStore(Store store) {
		super.update("updateByPrimaryKeySelective", store);
	}

	public String insert(Store store) {
		super.insert("insertSelective", store);
		return store.getStoreId();
	}

	public List<Store> getRelationStore(Map<String, Object> params) {
		return super.queryForList("getRelationStore", params);
	}

	public List<Store> findStoreByStoreNo(Map<String, Object> params) {
		return super.queryForList("findStoreByStoreNo", params);
	}

	public List<Store> findNoAddrStore(Integer clientId) {
		return super.getList("findNoAddrStore", clientId);
	}

	public List<Store> queryPopStoreList(Map<String, Object> param) {
		return super.getList("selectByParams", param);
	}

	public Integer queryPopStoreCount(Map<String, Object> params) {
		return super.get("queryPopStoreCount", params);
	}

	public List<Store> queryForListForReport(Map<String, Object> parames) {
		return super.queryForListForReport("findListByParams", parames);
	}

	public Store getStore(String storeId) {
		return super.get("selectByPrimaryKey", storeId);
	}

	public List<Store> getStoreCoordinatesList(Integer clientId) {
		return super.getList("getStoreCoordinatesList", clientId);
	}

	public Integer replaceStoreCount(Map<String, Object> parames) {
		return super.get("replaceStoreCount", parames);
	}

	public List<Store> replaceStoreList(Map<String, Object> parames) {
		return super.queryForList("replaceStoreList", parames);
	}

	public Integer batchChoiceStoreUserCount(Map<String, Object> parames) {
		return super.get("batchChoiceStoreUserCount", parames);
	}

	public List<Store> batchChoiceStoreUserList(Map<String, Object> parames) {
		return super.queryForList("batchChoiceStoreUserList", parames);
	}

	public String queryReplaceStoreList(Map<String, Object> parames) {

		return super.get("queryReplaceStoreList", parames);
	}

	public List<Store> getStoreInfoByName(Map<String, Object> parames) {
		return super.getList("getStoreInfoByName", parames);
	}

	public List<Store> getStoreByNameAndTaskId(Map<String, Object> parames) {
		return super.getList("getStoreByNameAndTaskId", parames);
	}

	public List<Store> getStoreByName(Map<String, Object> parames)
			throws BusinessException {
		return super.getList("getStoreByName", parames);
	}

	public Store getStoreById(String storeId) {
		return super.get("getStoreById", storeId);
	}

	public String getBatchCheckStoreUser(Map<String, Object> params) {
		return super.get("getBatchCheckStoreUser", params);
	}

	public List<Store> getStoreTaskList(Map<String, Object> params) {
		return super.queryForList("selectStoreTask", params);
	}

	public Integer getStoreTaskCount(Map<String, Object> parames) {
		return super.get("selectStoreTaskCount", parames);
	}

	public List<Store> selectTaskDataByPositionId(Map<String, Object> params) {
		return super.queryForList("getStoreTaskByPosition", params);
	}

	public Integer selectTaskDataCountByPositionId(Map<String, Object> params) {
		return super.get("getStoreTaskCountByPosition", params);
	}

	public List<Store> getStoreByFullName(Map<String, Object> params) {
		return super.queryForList("getStoreByFullName", params);
	}

	public List<Store> loadStoreList(Map<String, Object> params) {
		return super.getList("loadStoreList", params);
	}

	public Integer loadStoreListItems(Map<String, Object> params) {
		return super.get("loadStoreListItems", params);
	}

	public List<Store> loadStoreListGroupByCity(Map<String, Object> params) {
		return super.getList("loadStoreListGroupByCity", params);
	}

	public List<Store> loadStoreListGroupByProvince(Map<String, Object> params) {
		return super.getList("loadStoreListGroupByProvince", params);
	}

	public List<StoreSingle> findStoreListByCityId(Map<String, Object> params) {
		return super.queryForList("findStoreListByCity", params);
	}

	public Integer findStoreCountInMsi(Map<String, Object> params) {
		return super.get("findStoreCountInMsi", params);
	}

	public List<Store> findStoreListInMsi(Map<String, Object> params) {
		return super.queryForList("findStoreListInMsi", params);
	}

	public String getBatchStore(Map<String, Object> params) {
		return super.get("getBatchStore", params);
	}

	public String getBatchStoreUserNames(Map<String, Object> params) {
		return super.get("getBatchStoreUserNames", params);
	}

	public List<Store> getBatchUnStoreUser(Map<String, Object> params) {
		return super.queryForList("getBatchUnStoreUser", params);
	}

	public Integer getBatchUnStoreUserCount(Map<String, Object> params) {
		return super.get("getBatchUnStoreUserCount", params);
	}

	public String getBatchStoreIdsUnStoreUser(Map<String, Object> params) {
		return super.get("getBatchStoreIdsUnStoreUser", params);
	}

	public String getStoreByStoreName(Map<String, Object> params) {
		return super.get("getStoreByStoreName", params);
	}

	/**
	 * 
	 * @param params
	 * @return
	 * <pre>
	 * 		 where s.is_delete = 0 and s.client_id = #{clientId}
	 * </pre>
	 * @author：wei.peng
	 * @date 2015年10月9日
	 */
	public List<Store> loadStoreListByClientId(Map<String, Object> params) {
		return super.getList("loadStoreListByClientId", params);
	}

	public Integer getStoreItemsByStoreNo(String storeNo, Integer clientId)
			throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeNo", storeNo);
		params.put("clientId", clientId);
		return super.get("getStoreItemsByStoreNo", params);
	}

	public Store getStoreByStoreNo(String storeNo, Integer clientId)
			throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeNo", storeNo);
		params.put("clientId", clientId);
		return super.get("getStoreByStoreNo", params);
	}

	public void batchSaveStores(List<Store> stores) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stores", stores);
		super.insert("batchSaveStores", params);
	}

	public void batchSaveBwybStores(List<Store> stores) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stores", stores);
		super.insert("batchSaveBwybStores", params);
	}

	public void batchSaveWorkLogStores(List<Store> stores) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stores", stores);
		super.insert("batchSaveWorkLogStores", params);
	}

	public Map<String, Store> getStoreNoMap(Map<String, Object> params) {
		return super.queryForMap("getStoreNoMap", params, "storeNo");
	}

	public Store getStoreByStoreTypeAndNo(String storeNo, Integer storeType)
			throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeNo", storeNo);
		params.put("storeType", storeType);
		return super.get("getStoreByStoreTypeAndNo", params);
	}

	public Map<String, Store> getStoreNameList(Map<String, Object> params) {
		return super.queryForMap("getStoreNameMap", params, "storeName");
	}

	public Map<String, Store> getStoreNameLoginNameMap(
			Map<String, Object> params) {
		return super.queryForMap("getStoreNameLoginNameMap", params,
				"storeName");
	}

	public List<Store> findStoreByStoreNameAndCityName(
			Map<String, Object> params) {
		return super.queryForList("findStoreByStoreNameAndCityName", params);
	}

	public List<String> getStoreIdMap(Map<String, Object> params) {
		List<String> list = super.getList("getStoreIdMap", params);
		return list;
	}

	public List<Store> getStoreIdList(Map<String, Object> params) {
		List<Store> list = super.getList("getStoreIdList", params);
		return list;
	}

	public Map<String, Store> getStoreColumnKey(Map<String, Object> params,
			String mapKey) {
		return super.queryForMap("getStoreNoKey", params, mapKey);
	}

	public int itemUpdateStore(List<Store> params) {
		return super.update("itemInsertStore", params);
	}

	public List<Store> findStoreByNameAndNo(Integer clientId, String storeNo,
			String storeName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeNo", storeNo);
		params.put("storeName", storeName);
		return super.queryForList("findStoreByNameAndNo", params);
	}

	public Map<String, Store> getReClientPosition(Integer clientId) {
		return super.queryForMap("getReClientPosition", clientId, "storeNo");
	}

	public List<Store> queryStoreListByNumber(Map<String, Object> parmater) {
		return super.queryForList("queryStoreListByNumber", parmater);
	}

	public Map<String, Store> queryAllStore(Integer clientId) {
		return super.queryForMap("queryAllStore", clientId, "storeNo");
	}
	public Map<String, Store> queryAllBannerphoto(Integer clientId) {
		return super.queryForMap("queryAllStoreBannerPhoto", clientId, "bannerPhoto");
	}
	public Map<String, Store> queryAllStoreNo(Integer clientId) {
		return super.queryForMap("queryAllStoreBannerPhoto", clientId, "storeNo");
	}

	public String findStoreNameValueByKey(Map<String, Object> parmarter) {
		return super.get("findStoreNameValueByKey", parmarter);
	}
	
	/**
	 * 
	 * @param map
	 * @return	
	 * 	<pre>
	 * 		返回：门店编号，门店名称，客户名称
	 * 		如有需要返回更多数据可以在，sql 中添加，查询关联关系（门店找关联客户）
	 * </pre>
	 * @author wei.peng
	 * @date 2015年9月24日
	 */
	public Map<String, StoreSearchVO> querStoreSearchVo(Map<String, Object> map,String key){
		return super.queryForMap("querStoreSearchVo", map,key);
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月9日
	 * <pre>
	 * 		最终执行SQL 
	 * 		AND se.`client_id` = ${clientId} AND store_Name like CONCAT('%',#{storeName},'%')
	 * </pre>
	 */
	public List<Store> queryStoreByClientIdAndLinkeStroeName(Map<String, Object> map){
		return super.getList("queryStoreByClientIdAndLinkeStroeName",map);
	}
	
	
	public void insterStoreAll(List<Store> storeList) {
		for (Store store : storeList) {
			super.insert("insertSelective", store);
		}

	}

	public Map<String, Store> queryAllStoreKeyName(Integer clientId) {
		return super.queryForMap("queryAllStoreKeyName", clientId, "keyName");
	}
	public void updateStoreAll(List<Store> storeList) {
		for (Store store : storeList) {
			super.insert("updateByPrimarySelective", store);
		}

	}

	public Map<String, Store> queryAllStoreName(Integer clientId) {
		
		return super.queryForMap("queryAllStoreName", clientId, "storeName");
	}
	
	public List<Store> getUserMappingStoreList(Map<String, Object> params){
		
		return super.getList("getUserMappingStoreList",params);
	}
	
}
