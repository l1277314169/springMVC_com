package cn.mobilizer.channel.base.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.StoreGroupDao;
import cn.mobilizer.channel.base.po.StoreGroup;
import cn.mobilizer.channel.base.service.StoreGroupService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;

/**门店分组
 * @author Nany
 *
 */
@Service
public class StoreGroupServiceImpl implements StoreGroupService {

	private static final Log LOG = LogFactory.getLog(StoreServiceImpl.class);
	
	@Autowired
	private StoreGroupDao storeGroupDao;
	@Override
	public List<StoreGroup> getObjectList(Map<String, Object> parames) {
		List<StoreGroup> storeGroupList = storeGroupDao.queryStoreGroupList(parames);
		return  storeGroupList;
	
	}

	@Override
	public StoreGroup getObject(Map<String, Object> parames) {
		return null;
	}

	@Override
	public StoreGroup getStoreGroupByStoreGroupId(Integer storeGroupId)
			throws BusinessException {
		StoreGroup storeGroup = null;
		try {
			storeGroup = storeGroupDao.getStoreGroupByStoreGroupId(storeGroupId);
		} catch (BusinessException e) {
			LOG.error("method getStoreGroupByStoreGroupId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return storeGroup;
	}


}
