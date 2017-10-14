package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.SkuGroupMappingDao;
import cn.mobilizer.channel.base.po.SkuGroupMapping;
import cn.mobilizer.channel.base.service.SkuGroupMappigService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class SkuGroupMappigServiceImpl implements SkuGroupMappigService{
	private static final Log LOG = LogFactory.getLog(SkuGroupMappigServiceImpl.class);
	@Autowired 
	private SkuGroupMappingDao sgmDao;
	@Override
	public List<SkuGroupMapping> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuGroupMapping getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuGroupMapping getObject(Integer skuId)throws BusinessException {
		SkuGroupMapping sgm = null;
 		try {
 			sgm = sgmDao.getObject(skuId);
			
		} catch (BusinessException e) {
			LOG.error("method getObject error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return sgm;
	}

}
