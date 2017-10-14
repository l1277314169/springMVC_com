package cn.mobilizer.channel.wrTask.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.api.vo.WrTaskVO;
import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.vo.StoreSingle;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.wrTask.dao.WrTaskDao;
import cn.mobilizer.channel.wrTask.po.WrTask;
import cn.mobilizer.channel.wrTask.service.WrTaskService;

@Service
public class WrTaskServiceImpl implements WrTaskService{
	
	@Autowired
	private WrTaskDao WrTaskDao;
	@Autowired
	private StoreDao storeDao;

	@Override
	public List<WrTask> selectByParams(Map<String, Object> param)throws BusinessException {
		return WrTaskDao.selectByParams(param);
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param)throws BusinessException {
		return WrTaskDao.selectByParamCount(param);
	}

	@Override
	public int deleteByPrimaryKey(Integer wrTaskId) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(WrTask record) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(WrTask record) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public WrTask selectByPrimaryKey(Integer wrTaskId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(WrTask record)
			throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(WrTask record) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public WrTaskVO findStoreList(Integer clientId, Integer clientUserId,
			Integer cityId, String lastUpdateTime) throws BusinessException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("clientUserId", clientUserId);
		parameters.put("cityId", cityId);
		parameters.put("lastUpdateTime", lastUpdateTime);
		parameters.put("currentTime", DateTimeUtils.formatTime(new Date(), DateTimeUtils.yyyyMMddHHmmss));
		List<StoreSingle> storeList = storeDao.findStoreListByCityId(parameters);
		WrTaskVO wrTask = new WrTaskVO();
		wrTask.setCityId(cityId);
		wrTask.setStoreList(storeList);
		wrTask.setLastUpdateTime(DateTimeUtils.formatTime(new Date(), DateTimeUtils.yyyyMMddHHmmss));
		return wrTask;
	}

	@Override
	public List<WrTask> getWrTaskList(Integer clientId) throws BusinessException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		return WrTaskDao.getWrTaskList(parameters);
	}
	
}
