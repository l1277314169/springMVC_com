package cn.mobilizer.channel.wrTask.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.report.vo.ArraysUtils;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.wrTask.dao.WrCustomerBrandMappingDao;
import cn.mobilizer.channel.wrTask.po.WrCustomerBrandMapping;
import cn.mobilizer.channel.wrTask.service.WrCustomerBrandMappingService;

@Service
public class WrCustomerBrandMappingServiceImpl implements WrCustomerBrandMappingService{

	private static final Log LOG = LogFactory.getLog(WrCustomerBrandMappingServiceImpl.class);
	@Autowired
	private WrCustomerBrandMappingDao wrCustomerBrandMappingDao;
	
	@Override
	public List<WrCustomerBrandMapping> selectByParams(Map<String, Object> param)
			throws BusinessException {
	   List<WrCustomerBrandMapping> list=wrCustomerBrandMappingDao.queryWrCustomerBrandMappingList(param);
		return list;
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param)
			throws BusinessException {
		int count=wrCustomerBrandMappingDao.queryTotalByParam(param);
		return count;
	}

	@Override
	public void deleteByPrimaryKey(Integer mappingId) throws BusinessException {
		try{
			wrCustomerBrandMappingDao.deleteByPrimaryKey(mappingId);
			}catch (BusinessException e) {
				LOG.error("method delwrCustomerBrandMapping error, ", e);
				throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
			}	}

	@Override
	public void insert(WrCustomerBrandMapping record) throws BusinessException {
		wrCustomerBrandMappingDao.insert(record);
	}

	@Override
	public void insertSelective(WrCustomerBrandMapping record)
			throws BusinessException {
		wrCustomerBrandMappingDao.insertSelective(record);
	}

	@Override
	public WrCustomerBrandMapping selectByCustomerId(Integer customerid)throws BusinessException {
		return wrCustomerBrandMappingDao.selectByCustomerId(customerid);
	}

	@Override
	public void updateByPrimaryKeySelective(WrCustomerBrandMapping record)
			throws BusinessException {
		wrCustomerBrandMappingDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public void updateByPrimaryKey(WrCustomerBrandMapping record)
			throws BusinessException {
		wrCustomerBrandMappingDao.updateByPrimaryKey(record);
	}

	@Override
	public WrCustomerBrandMapping getWrCustomerBrandMappingByCusId(Integer clientId, Integer customerId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("customerId", customerId);
		return wrCustomerBrandMappingDao.getWrCustomerBrandMappingByCusId(params);
	}

	@Override
	public void batchCustomerAndBrandMapping(String[] brandIds,
			Integer customerId, Integer clientId) throws BusinessException{
		    for(int i=0;i<brandIds.length;i++)
		    {
		    	Map<String,Object> param=new HashMap<String,Object>();
		    	param.put("customerId",customerId);
		    	param.put("clientId", clientId);
		    	param.put("brandIds", brandIds[i]);
		    	WrCustomerBrandMapping oldCustomerBrandMapping=wrCustomerBrandMappingDao.wrCustomerBrandMapping(param);
		    	if(oldCustomerBrandMapping != null)
		    	{
		    		oldCustomerBrandMapping.setIsDelete(Constants.IS_DELETE_FALSE);
		    		wrCustomerBrandMappingDao.updateByPrimaryKeySelective(oldCustomerBrandMapping);
		    	}
		    	else
		    	{
		    		WrCustomerBrandMapping newCustomerBrandMapping=new WrCustomerBrandMapping();
		    		newCustomerBrandMapping.setBrandId(Integer.parseInt(brandIds[i]));
		    		newCustomerBrandMapping.setCustomerId(customerId);
		    		newCustomerBrandMapping.setClientId(clientId);
		    		wrCustomerBrandMappingDao.insertSelective(newCustomerBrandMapping);
		    	}
		    }
		
	}

	@Override
	public int deleteCustomerAndBrandMapping(Map<String, Object> parameters) throws BusinessException {
		int currentMapppingisdelte;
		try {
			currentMapppingisdelte = wrCustomerBrandMappingDao.currentMapppingisdelte(parameters);
		} catch (Exception e) {
			LOG.error("method deleteWrCustomerAndBrandMapping error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
	     return currentMapppingisdelte;
	}

	@Override
	public void updateCustomerBrandMapping(String newbrandIds, String oldbrandIds,
			Integer customerId, Integer clientId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("customerId", customerId);
		params.put("clientId",clientId);
		String[] oldBrandId = null;
		String[] newBrandId = null;
		if(oldbrandIds!=null && !oldbrandIds.equals("")){
			
			oldBrandId =oldbrandIds.split(",");
		}
		 if(newbrandIds !=null && !newbrandIds.equals(""))
		 {
			 newBrandId = newbrandIds.split(",");
		 }	
		  if(oldBrandId==null)
		  {
			 
			  if(newBrandId!=null)
			  {
				// 如果以前的品牌Id为空,那么全部为新增
				  batchCustomerAndBrandMapping(newBrandId,customerId,clientId);
			  }
		  }else
		  {
			  if(newBrandId==null)
			  {
				// 如果新的品牌Id为空,那么全部为删除
				  Map<String,Object> param=new HashMap<String,Object>();
				  param.put("customerId",customerId);
				  param.put("clientId", clientId);
				  param.put("brandIds",StringUtils.join(oldBrandId,","));
				  deleteCustomerAndBrandMapping(param);
			  }else{
				  /** 获取old中存在而new中不存在的品牌，删除 **/ 
				  String brandId=ArrayUtil.arraySubtract2Str(oldBrandId,newBrandId);
				  if(brandId!="" && brandId.lastIndexOf(",")==brandId.length()-1)
				  {
					  brandId=brandId.substring(0,brandId.length()-1);
				  }
				  Map<String,Object> parames=new HashMap<String,Object>();
				  parames.put("customerId",customerId);
				  parames.put("clientId",clientId);
				  parames.put("brandIds", brandId);
				  deleteCustomerAndBrandMapping(parames);
				  /** 获取new中存在而old中不存在的品牌，新增 **/
				  String[] newIds = ArrayUtil.arraySubtract(newBrandId, oldBrandId);
				  if(newIds!=null && newIds.length>0)
				  {
					  batchCustomerAndBrandMapping(newIds,customerId,clientId);
				  }
			  }
		  }
		}
	}
