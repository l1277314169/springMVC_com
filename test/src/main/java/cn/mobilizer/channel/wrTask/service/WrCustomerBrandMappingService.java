package cn.mobilizer.channel.wrTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.po.WrCustomerBrandMapping;

public interface WrCustomerBrandMappingService {
	
	/**分页查询
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public List<WrCustomerBrandMapping> selectByParams(Map<String,Object> param) throws BusinessException;
	
	public Integer selectByParamCount(Map<String,Object> param) throws BusinessException;
	
	public void deleteByPrimaryKey(Integer mappingId) throws BusinessException;
	
    public void insert(WrCustomerBrandMapping record) throws BusinessException;

    public void insertSelective(WrCustomerBrandMapping record) throws BusinessException;

    public WrCustomerBrandMapping selectByCustomerId(Integer customerid) throws BusinessException;

    public void  updateByPrimaryKeySelective(WrCustomerBrandMapping record) throws BusinessException;

    public void updateByPrimaryKey(WrCustomerBrandMapping record) throws BusinessException;
    
    public WrCustomerBrandMapping getWrCustomerBrandMappingByCusId(Integer clientId,Integer customerId);
    
	public void batchCustomerAndBrandMapping(String[] brandIds,Integer customerId,Integer clientId);
	
	public int deleteCustomerAndBrandMapping(Map<String, Object> parameters);
	
	public void updateCustomerBrandMapping(String newbrandIds,String oldbrandIds, Integer customerId, Integer clientId);
}
