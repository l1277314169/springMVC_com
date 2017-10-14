package cn.mobilizer.channel.wrTask.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.wrTask.dao.WrCustomerDao;
import cn.mobilizer.channel.wrTask.po.WrCustomer;
import cn.mobilizer.channel.wrTask.service.WrCustomerService;

@Service
public class WrCustomerServiceImpl implements WrCustomerService{
	
	private static final Log LOG = LogFactory.getLog(WrCustomerServiceImpl.class);
	@Autowired
	private WrCustomerDao wrTaskServiceDao;

	@Override
	public List<WrCustomer> selectByParams(Map<String, Object> param)
			throws BusinessException {
		List<WrCustomer> list=wrTaskServiceDao.queryWrCustomerListByParam(param);
		return list; 
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param)
			throws BusinessException {
		int count=wrTaskServiceDao.queryTotal(param);
		return count;
	}

	@Override
	public void deleteByPrimaryKey(Integer customerId) throws BusinessException {
		try{
			wrTaskServiceDao.deleteByPrimaryKey(customerId);
			}catch (BusinessException e) {
				LOG.error("method delWrCustomer error, ", e);
				throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
			}
	}

	@Override
	public int insert(WrCustomer record) throws BusinessException {
		wrTaskServiceDao.insert(record);
		return record.getCustomerId();
	}

	@Override
	public int insertSelective(WrCustomer record) throws BusinessException {
		wrTaskServiceDao.insertSelective(record);
		return record.getCustomerId();
	}

	@Override
	public WrCustomer selectByPrimaryKey(Integer customerId,Integer clientId) throws BusinessException {
		return wrTaskServiceDao.selectByPrimaryKey(customerId,clientId);
	}

	@Override
	public int updateByPrimaryKeySelective(WrCustomer record)
			throws BusinessException {
		wrTaskServiceDao.updateByPrimaryKeySelective(record);
		return record.getCustomerId();
	}

	@Override
	public int updateByPrimaryKey(WrCustomer record) throws BusinessException {
		wrTaskServiceDao.updateByPrimaryKey(record);
		return record.getCustomerId();
	}

	@Override
	public List<WrCustomer> selectList(Integer clientId)
			throws BusinessException {
		List<WrCustomer> list=wrTaskServiceDao.selectList(clientId);
		return list;
	}
	/*@Override
	public void  batchWrCustomer(Integer customerId,Integer clientId)throws BusinessException
	{
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("customerId",customerId);
		param.put("clientId", clientId);
		wrTaskServiceDao.batchWrCustomer(param);
	}*/
}
