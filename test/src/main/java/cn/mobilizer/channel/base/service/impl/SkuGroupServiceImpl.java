package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 

import cn.mobilizer.channel.base.service.SkuGroupSerivce;
import cn.mobilizer.channel.base.dao.SkuGroupDao;
import cn.mobilizer.channel.base.po.SkuGroup;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;
@Service
public class SkuGroupServiceImpl implements SkuGroupSerivce  {
	private static final Log LOG = LogFactory.getLog(SkuGroupServiceImpl.class);
	@Autowired
	private SkuGroupDao skuGroupDao;

	

	@Override
	public List<SkuGroup> getSkuGroupList(int clientId) throws BusinessException{
		Map<String, Object> parames = new HashMap<String, Object>();
		List<SkuGroup> list = null;
		try {
			parames.put("clientId", clientId);
			list = skuGroupDao.getSkuGroupList(parames);
		} catch (Exception e) {
			LOG.error("method getSkuGroupList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}



	@Override
	public List<SkuGroup> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public SkuGroup getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public SkuGroup getSkuGroup(int skuGroupId)throws BusinessException {
			SkuGroup sg = null;
		try {
			sg = skuGroupDao.getSkuGroup(skuGroupId);
		} catch (BusinessException e) {
			LOG.error("method getSkuGroup error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return sg;
	}

}
