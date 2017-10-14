package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreGroup;
import cn.mobilizer.channel.base.vo.GeoVo.Bounds;
import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.ctTask.vo.StoreTask;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface StoreService extends BasicService<Store>{

	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 */
	public Integer queryStoreCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Store> queryStoreList(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 苹果门店获取查询的总记录数
	 * @param searchParams
	 * @return
	 */
	public Integer queryAppleStoreCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Store> queryAppleStoreList(Map<String, Object> searchParams) throws BusinessException;
	
	public ImportVO saveAll(List<Store> stores);
	/**
	 * 根据clientId获取终端分组信息
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public List<StoreGroup> findStoreGroupsByClientId(Integer clientId)throws BusinessException;

	
	/**编辑，数据回显
	*@author Nany
	   2014年11月27日下午4:22:18
	 * @param storeId
	 * @return
	 */
	public Store findStoreByStoreId(String storeId)throws BusinessException;

	
	/**门店管理-编辑-保存
	*@author Nany
	   2014年11月27日下午6:13:58
	 * @param store
	 */
	public void updateStore(Store store,String[] oldClientUserIds)throws BusinessException;

	/**门店管理-删除
	*@author Nany
	   2014年11月27日下午6:42:01
	 */
	public void deleteStore(String storeId)throws BusinessException;

	/**门店管理--新增--保存
	 * @author Nany
	 * 2014年12月16日下午3:45:00
	 * @param store
	 */
	public void addStore(Store store)throws BusinessException;
	
	/**
	 * 取人店关系对应
	 * @param clientUserId
	 * @param clientId
	 * @param visitDate
	 * @param taskType
	 * @return
	 */
	public List<Store> getRelationStore(Integer clientUserId, Integer clientId, String visitDate, Byte taskType);

	public List<Store> findStoreByStoreNo(Map<String, Object> params)throws BusinessException;
	
	public Store findStoreByStoreNo(String storeNo, Integer clientId)throws BusinessException;
	
	public List<Store> findNoAddrStore(Integer clientId);

	public int updateStoreInfo(Store store);
	
	/**
	 * 拜访任务管理-获取查询的总记录数
	 * @param searchParams
	 * @return
	 */
	public Integer queryPopStoreCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 拜访任务管理-分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Store> queryPopStoreList(Map<String, Object> searchParams) throws BusinessException;

	public List<Store> queryForListForReport(Map<String, Object> parameters)throws BusinessException;
	
	public Store selectByPrimaryKey(String storeId)throws BusinessException;
	
	public List<Store> getStoreCoordinatesList(Integer clientId) throws BusinessException;
	
	public List<Store> replaceStoreList(Map<String, Object> parameters)throws BusinessException;
	
	public Integer replaceStore(Map<String, Object> parameters)throws BusinessException;
	
	public List<Store> batchChoiceStoreUserList(Map<String, Object> parameters)throws BusinessException;
	
	public Integer batchChoiceStoreUserStore(Map<String, Object> parameters)throws BusinessException;
	/**
	 * 替换所有人员
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public String replaceAllClientUser(Map<String, Object> parameters)throws BusinessException;
	
	/**
	 * 根据门店名称模糊查询门店信息
	 * @param storeName
	 * @return
	 * @throws BusinessException
	 */
	public List<Store> getStoreInfoByName(Map<String, Object> parameters) throws BusinessException;

	public Store getStore(String storeId);
	
	public String getBatchCheckStoreUser(Map<String, Object> params) throws BusinessException;
	
	public List<Store> findStoreByClientId(Integer clientId) throws BusinessException;
	
	/**
	 * 根据门店与周期任务关系查询所有门店
	 * @param storeTask
	 * @return List<Store>
	 * @throws BusinessException
	 */
	public List<Store> getStoreTaskList(StoreTask storeTask,Long pageStart,Integer pageSize) throws BusinessException;
	
	public Integer getStoreTaskCount(StoreTask storeTask) throws BusinessException;
	
	/**
	 * 根据职位与周期任务关系查询所有门店
	 * @param storeTask
	 * @return List<Store>
	 * @throws BusinessException
	 */
	public List<Store> selectTaskDataByPositionId(StoreTask storeTask,Long pageStart,Integer pageSize) throws BusinessException;
	
	public Integer selectTaskDataCountByPositionId(StoreTask storeTask) throws BusinessException;
	
	public List<Store> getStoreByName(Map<String, Object> params) throws BusinessException;
		
	
	public List<Store> getGeoStoreList(Integer clientId,Bounds bounds,Integer zoom) throws BusinessException;
	
	public List<Store> loadStoreList(Map<String, Object> params) throws BusinessException;
	
	/**
	 * 根据门店全名称查找门店
	 * @param params
	 * @return
	 */
	public List<Store> getStoreByFullName(Integer clientId,String storeName,Integer clientStructureId);
	
	public Integer loadStoreListItems(Map<String, Object> params) throws BusinessException;
	
	/**
	 * 获取查询的总记录数--门店表、门店人员映射表、人员表关联查询，目前用于暗访查询门店的数据列表中
	 * @param searchParams
	 * @return
	 */
	public Integer findStoreCountInMsi(Map<String, Object> params) throws BusinessException;
	
	/**
	 *  查询列表-门店表、门店人员映射表、人员表关联查询，目前用于暗访查询门店的数据列表中
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<Store> findStoreListInMsi(Map<String, Object> params) throws BusinessException;
	
	public String getBatchStore(Map<String, Object> params) throws BusinessException;
	
	/**
	 * 获取未关联的门店
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<Store> getBatchUnStoreUser(Map<String, Object> params) throws BusinessException;
	
	/**
	 * 获取未关联人员的门店
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public Integer getBatchUnStoreUserCount(Map<String, Object> params) throws BusinessException;
	
	/**
	 * 获取所有未关联人员的门店Id
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public String getBatchStoreIdsUnStoreUser(Map<String, Object> params) throws BusinessException;
	/**
	 * 
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public String getStoreByStoreName(Integer clientId,String storeName)throws BusinessException;
	/**
	 * 加载门店编号
	 * @param params
	 * @return
	 */
	public List<Store> loadStoreToSelectTwo(Map<String, Object> params);
	
	/**
	 * 根据门店编号查询总记录数
	 * @param storeNo
	 * @return
	 * @throws BusinessException
	 */
	public Integer getStoreItemsByStoreNo(String storeNo,Integer clientId) throws BusinessException;
	
	/**
	 * 高露洁Excel批量导入门店数据
	 * @param stores
	 * @throws BusinessException
	 */
	public void batchSaveStores(List<Store> stores,List<String> clientUserIds) throws BusinessException;
	
	
	/**
	 * Excel批量导入门店数据
	 * @param stores
	 * @throws BusinessException
	 */
	public void batchSaveStores(List<Store> stores) throws BusinessException;
	/**
	 * Excel批量导入百威英博门店数据
	 * @param stores
	 * @throws BusinessException
	 */
	public void batchSaveBwybStores(List<Store> stores) throws BusinessException;
	
	/**
	 * 删除未拜访的计划
	 * @param store
	 * @throws BusinessException
	 */
	public void deleteCallPalnning(Store store,String oldBusinessman,String oldPromotions,Integer clientId)throws BusinessException;
	
	/**
	 * 获取所有门店编号
	 * @param clientId
	 * @throws BusinessException
	 */
	public Map<String, Store> getStoreNoMap(Integer clientId) throws BusinessException;
	
	/**
	 * Apple客户，根据轮次与编号查找门店
	 * @param storeNo
	 * @param storeType
	 * @return
	 * @throws BusinessException
	 */
	public Store getStoreByStoreTypeAndNo(String storeNo,Integer storeType) throws BusinessException;
	
	/**
	 * 批量保存Apple门店
	 * @param stores
	 * @throws BusinessException
	 */
	public void batchSaveAppleStores(List<Store> stores) throws BusinessException;
	
	/**
	 * 批量保存工作日志门店
	 * @param stores
	 * @throws BusinessException
	 */
	public void batchSaveWorkLogStores(List<Store> stores) throws BusinessException;
	
	/**
	 * 通过门店名称与城市查询门店
	 * @param stores
	 * @return
	 * @throws BusinessException
	 */
	public List<Store> findStoreByStoreNameAndCityName(Map<String,Object> params) throws BusinessException;
	
	/**
	 * 费列罗导入
	 * @param file
	 * @param request
	 * @param resp
	 * @return
	 * @throws BusinessException
	 */
	public Object addFerreroStore(MultipartFile file, HttpServletRequest request,HttpServletResponse resp,Integer clientId)throws BusinessException;
	/**
	 * 高露洁通过门店编号查询门店
	 * @param parmater
	 * @return
	 */
	public List<Store> queryStoreListByNumber(Map<String, Object> parmater)throws BusinessException;
	
	public Map<String, Store> queryAllStore(Integer clientId)throws BusinessException;
	
	/**
	 * 根据 门店名称模糊查询
	 * @param clientId		客户编号	
	 * @param storeName		门店名称
	 * @return	
	 * @author：wei.peng
	 * @date 2015年10月9日
	 */
	public List<Store> queryStoresByClientId(Integer clientId,String storeName);
    
	public Map<String,Object> importstore(List<String[]> values,Map<String,ClientUser> mapUser , Map<String, ClientStructure> mapDept,   Map<String, Channel> mapChannel,Map<String, Chain> mapChain, Integer clientId)throws BusinessException;

	public Map<String, Store> queryAllStoreKeyName(Integer clientId)throws BusinessException;

	public Map<String,Object> importHuishiStore(List<String[]> values,Map<String,ClientUser> mapUser , Map<String, ClientStructure> mapDept,   Map<String, Channel> mapChannel,Map<String, Chain> mapChain, Integer clientId)throws BusinessException;
	
	public Map<String,Object> importColgate2Store(List<String[]> values,Map<String, Province> mapProvince, Map<String, ClientStructure> mapDept,   Map<String, Channel> mapChannel,Map<String,Options> mapOptionsType,Map<String,Options> mapOptionsYue, Integer clientId)throws BusinessException;

	public Map<String, Store> queryAllStoreName(Integer clientId)throws BusinessException;

	public List<Store> getUserMappingStoreList(Map<String, Object> params) throws BusinessException;
	
}
