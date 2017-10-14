package cn.mobilizer.channel.wrTask.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.po.WrCustomer;
import cn.mobilizer.channel.wrTask.po.WrTask;

public interface WrCustomerService {
	
	/**分页查询
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public List<WrCustomer> selectByParams(Map<String,Object> param) throws BusinessException;
	
	public Integer selectByParamCount(Map<String,Object> param) throws BusinessException;
	
	public void deleteByPrimaryKey(Integer customerId) throws BusinessException;
	
    public int insert(WrCustomer record) throws BusinessException;

    public int insertSelective(WrCustomer record) throws BusinessException;

    public WrCustomer selectByPrimaryKey(Integer customerId,Integer clientId) throws BusinessException;

    public int updateByPrimaryKeySelective(WrCustomer record) throws BusinessException;

    public int updateByPrimaryKey(WrCustomer record) throws BusinessException;

    public List<WrCustomer> selectList(Integer clientId) throws BusinessException;
    
  //  public void  batchWrCustomer(Integer customerId,Integer clientId)throws BusinessException;
    
}
