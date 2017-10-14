package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

public interface StoreUserMappingService extends BasicService<StoreUserMapping>{

	public List<StoreUserMapping> getStoreUserMappingByStoreId(Integer clientId,String storeId) throws BusinessException;

	public List<StoreUserMapping> getStoreUserMappingByStoreIdAndClientId(String storeId, Integer clientId) throws BusinessException;
	
	public List<StoreUserMapping> getStoreUserMappingByStoreId(Integer clientId,String storeId, String initData) throws BusinessException;

	/**批量更换业务员
	 * @author Nany
	 * 2015年1月7日上午9:56:37
	 * @param clientUserId1
	 * @param clientUserId2
	 * @throws BusinessException
	 */
	public void updateClientUser(Integer clientId,Integer clientUserId1, Integer clientUserId2)throws BusinessException;

	public List<StoreUserMapping> getStoreUserMappingByClientUserId(
			Integer clientId, Integer clientUserId)throws BusinessException;

	
	/**获取人员关联的门店总数
	 * @author Nany
	 * 2015年2月9日上午10:13:26
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public int queryStoreUserMappingCount(Map<String, Object> parameters)throws BusinessException;

	public List<Store> queryStoreList(Map<String, Object> parameters)throws BusinessException;

	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public int deleteUserAndStoreMapping(Map<String, Object> parameters)throws BusinessException;

	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public StoreUserMapping oldUserAndStoreMapping(Map<String, Object> parameters)throws BusinessException;
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public int addStoreUserMapping(StoreUserMapping storeUserMapping)throws BusinessException;
	
	public int updateStoreUserMapping(StoreUserMapping storeUserMapping)throws BusinessException;
	
	/**
	 * 获取门店关联的人员信息-按职位过滤
	 * @param clientId
	 * @param storeId
	 * @param initData 目前是采用relation_value值来过滤，系统中存放的是需要过滤的职位id
	 * @return
	 * @throws BusinessException
	 */
	public String initStoreMappingData(Integer clientId, String storeId, String initData)throws BusinessException;
 	
	/**
	 * 解除用户门关系
	 * */
	public void deleteByClientUserId(Integer clientUserId)throws BusinessException;
	
	/**
	 * 门店人员关系批量维护
	 * @throws BusinessException
	 */
	public void batchUpdateStoreUserMapping(String[] storeIds,Integer clientUserId,Integer clientId) throws BusinessException;
	
	/**
	 * 批量维护门店与人员关系
	 * @param hiddenStoreId
	 * @param oldStoreIdStrs
	 * @param clientUserId
	 * @param clientId
	 * @throws BusinessException
	 */
	public void updateStoreUser(String hiddenStoreId,String oldStoreIdStrs,Integer clientUserId,Integer clientId) throws BusinessException;
	
	public Map<String, StoreUserMapping> getStoreUserMappingMap(Integer clientId) throws BusinessException;
	
}
