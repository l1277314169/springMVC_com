package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.PopSkuPriceGroupMappingDao;
import cn.mobilizer.channel.base.dao.SkuPriceGroupDao;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.PopSkuPriceGroupMapping;
import cn.mobilizer.channel.base.po.SkuPriceGroup;
import cn.mobilizer.channel.base.service.SkuPriceGroupService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
@Service
public class SkuPriceGroupServiceImpl implements SkuPriceGroupService{
	private static final Log LOG = LogFactory.getLog(SkuPriceGroupServiceImpl.class);
	
	@Autowired
	private SkuPriceGroupDao skuPriceGroupDao;
	@Autowired
	private PopSkuPriceGroupMappingDao popSkuPriceGroupMappingDao;
	@Override
	public List<SkuPriceGroup> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuPriceGroup getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SkuPriceGroup> findSkuPriceGroup(
			Map<String, Object> param) throws BusinessException {
		List<SkuPriceGroup> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = skuPriceGroupDao.querySkuPriceGroupList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method findSkuPriceGroup error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public int addSkuPriceGroup(SkuPriceGroup skuPriceGroup)
			throws BusinessException {
		try {
			skuPriceGroupDao.insert(skuPriceGroup);
			return  skuPriceGroup.getSkuPriceGroupId();
		} catch (BusinessException e) {
			LOG.error("method addSkuPriceGroup error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method addSkuPriceGroup error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
	}

	@Override
	public int updateSkuPriceGroup(SkuPriceGroup skuPriceGroup)
			throws BusinessException {
		
		return skuPriceGroupDao.update(skuPriceGroup);
	}

	@Override
	public int deleteSkuPriceGroup(int skuPriceGoupId) throws BusinessException {
		int result = 0;
		SkuPriceGroup skuPriceGroup = new SkuPriceGroup();
		try {
//			result = clientUserDao.delete(clientUserId);
			skuPriceGroup.setSkuPriceGroupId(skuPriceGoupId);
			skuPriceGroup.setIsDelete (Constants.IS_DELETE_TRUE);
			result = skuPriceGroupDao.update(skuPriceGroup);
		} catch (BusinessException e) {
			LOG.error("method deleteSkuPriceGroup error, ", e);
			throw e;
		} catch (Exception e) {
			LOG.error("method deleteSkuPriceGroup error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		return result;
	}

	@Override
	public Integer queryCount(Map<String, Object> searchParams)
			throws BusinessException {
		return skuPriceGroupDao.getCount(searchParams);
	}

	@Override
	public SkuPriceGroup getSkuPriceGroup(
			Map<String, Object> parmarter) {
		return skuPriceGroupDao.findSerlctOneSkuPrice(parmarter);
	}

	@Override
	public SkuPriceGroup ajaxValidation(Integer clientId, String groupName)
			throws BusinessException {
		Map<String,Object> paramteter = new HashMap<String,Object>();
		paramteter.put("clientId", clientId);
		paramteter.put("groupName", groupName);
		return skuPriceGroupDao.findSkuGroupName(paramteter);
	}


}
