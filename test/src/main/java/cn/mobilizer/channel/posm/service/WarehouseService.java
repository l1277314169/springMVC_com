package cn.mobilizer.channel.posm.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.po.Warehouse;

public interface WarehouseService {

	public List<Warehouse> queryWarehouseList(Map<String, Object> searchParams) throws BusinessException;
	
	public List<Warehouse> queryWarehouseListall(Map<String, Object> searchParams) throws BusinessException;
	
	public Integer queryWarehouseCount(Map<String, Object> searchParams) throws BusinessException;
	
	public boolean saveAll(List<Warehouse> list) throws Exception;
	
	public void addWarehouse(int clientId, Warehouse warehouse);
	
	public void updateWarehouse(int clientId, Warehouse warehouse);
	
	public Warehouse showWarehouse( int warehouseid)throws BusinessException;
	
	public void deleteWarehouse(int clientId, int warehouseid ); 
	
	public Warehouse findWarehouseName(String WarehouseName,Integer clientId)throws BusinessException;
 
	public Warehouse findWarehouseNo(String WarehouseNo,Integer clientId)throws BusinessException;
	
	public Map<String,Object> importWarehouse(List<String[]> values, Map<String, Province> mapProvince,Map<String, ClientStructure> mapDept,Map<String, City> mapCity, Map<String, ClientUser> mapClientUser,Integer clientId)throws BusinessException;

	public List<Warehouse> findWareHouseAjax(Integer clientId);
	
	/**
	 * 
	 * @param clientId		客户编号	
	 * @param name			仓库名称	（模糊查询）
	 * @return		两个参数都为NULL 返回NULL ， 必须制定一个参数
	 * @author：wei.peng
	 * @date 2015年10月9日
	 */
	public List<Warehouse> findWareHouseAjax(Integer clientId,String name);
	
	/**
	 * 			
	 * @param clientId			客户编号（必填）
	 * @param wareHouseId		仓库编号（必填）
	 * @return		
	 * @author：wei.peng
	 * @date 2015年10月9日
	 */
	public Warehouse findWareHouseByKey(Integer clientId,Integer wareHouseId);
}
