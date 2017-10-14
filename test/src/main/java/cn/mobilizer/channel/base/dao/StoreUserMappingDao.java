package cn.mobilizer.channel.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class StoreUserMappingDao extends MyBatisDao{
	
	public StoreUserMappingDao(){
		super("STORE_USER_MAPPING");
	}

	public int add(StoreUserMapping storeUserMapping) {
		return super.insert("insertSelective", storeUserMapping);
		
	}
	
	public void updateByStoreId(Map<String, Object> parames) {
		super.update("updateByStoreId", parames);
	}
	
	public void deleteByStoreId(String storeId) {
		super.update("deleteByStoreId", storeId);
	}
	
	public void deleteByClientUserId(Integer clientUserId){
	    super.update("deleteByClientUserId", clientUserId);
	}
	
	public StoreUserMapping getStoreUserMapping(Map<String, Object> params) {
		return super.get("selectByStoreIdAndClientUserId", params);
	}

	public int updateStoreUserMapping(StoreUserMapping storeUserMapping) {
		return super.update("updateByPrimaryKeySelective", storeUserMapping);
		
	}
	
	public List<StoreUserMapping> selectByParams(Map<String, Object> parames) {
		return super.getList("selectByParams", parames);
	}
	
	public List<StoreUserMapping> getStoreUserMappingByStoreId(Map<String, Object> parames) {
		return super.getList ("getStoreUserMappingByStoreId", parames);
	}
	
	public List<StoreUserMapping>  selectStoreUserMappingByClientUserId(Map<String, Object> parames){
		return super.queryForList("selectStoreUserMappingByClientUserId", parames);
	}
	
	public int isdeleteByClientUserId(Map<String, Object> parames){
		return super.update("isdeleteUserStoreMapping", parames);
	}
	public void updateClientUser(Map<String, Object> parame) {
		super.update("updateClientUser", parame);
	}
	
	public List<StoreUserMapping> getStoreUserMappingList(Map<String, Object> params) {
		return super.getList("selectByStoreIdAndClientUserId", params);
	}

	public Integer queryStoreUserMappingCount(Map<String, Object> parameters) {
		return super.get("queryTotalCount", parameters);
	}

	public List<Store> queryStoreList(Map<String, Object> parameters) {
		return super.getList("queryStoreList", parameters);
	}
	
	public int currentMapppingisdelte(Map<String,Object> params){
		return super.update("currentMapppingisdelte", params);
	}

	public StoreUserMapping userAndStoreMapping(Map<String,Object> params){
		return super.get("userAndStoreMapping", params);
	}
	
	public String initStoreMappingData(Map<String,Object> params){
		return super.get("initStoreMappingData", params);
	}
	
	public void deleteByStoreIdAndUserId(Map<String,Object> params) {
		super.update("deleteByStoreIdAndUserId", params);
	}
	
	public void batchInsertStoreUserMapping(List<StoreUserMapping> storeUserMappings){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("storeUserMappings", storeUserMappings);
		super.insert("batchInsertStoreUserMapping", params);
	}
	
	public void deleteStoreUserMappingByStoreId(Map<String,Object> params){
		super.update("deleteStoreUserMappingByStoreId", params);
	}
	
	public Map<String, StoreUserMapping> getStoreUserMappingMap(Map<String,Object> params){
		
		return super.queryForMap("selectStoreUserMappingMap", params, "storeId");
	}	
	
	public int batchUpdateStoreUserMapping(List<StoreUserMapping> params){
		return super.update("batchUpdateStoreUserMapping",params);
	}
	
	public Map<String,StoreUserMapping> getReClientPosition(Integer clientId){
		return super.queryForMap("getReMappingClientPosition", clientId, "clientUserIdAndStoreNo");
	}
	
}