package cn.mobilizer.channel.wrTask.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.dao.WrTaskDao;
import cn.mobilizer.channel.wrTask.dao.WrTaskDataDao;
import cn.mobilizer.channel.wrTask.dao.WrWorkplaceDao;
import cn.mobilizer.channel.wrTask.po.WrTask;
import cn.mobilizer.channel.wrTask.po.WrTaskData;
import cn.mobilizer.channel.wrTask.po.WrWorkplace;
import cn.mobilizer.channel.wrTask.service.WrTaskDataService;
import cn.mobilizer.channel.wrTask.vo.WrTaskFinishCount;

@Service
public class WrTaskDataServiceImpl implements WrTaskDataService{
	
	@Autowired
	private WrTaskDataDao WrTaskDataDao;
	@Autowired
	private WrTaskDao wrTaskDao;
	@Autowired
	private WrWorkplaceDao wrWorkplaceDao;
	@Autowired
	private StoreDao storeDao;

	@Override
	public List<WrTaskData> selectByParams(Map<String, Object> param)throws BusinessException {
		List<WrTaskData> wrTaskDatas = WrTaskDataDao.selectByParams(param);
		for(WrTaskData wrTaskData : wrTaskDatas){
			WrTask wrTask = wrTaskDao.selectByPrimaryKey(wrTaskData.getWrTaskId());
			wrTaskData.setWrTaskName(wrTask.getTaskName());
			WrWorkplace wrWorkplace = wrWorkplaceDao.selectByPrimaryKey(wrTaskData.getWorkplaceId());
			wrTaskData.setWorkplaceType(wrWorkplace.getWorkplaceType());
			if(wrTaskData.getStoreId()!=null){
				Store store = storeDao.selectByPrimaryKey(wrTaskData.getStoreId());
				wrTaskData.setWorkPlace(store.getStoreName());
			}
		}
		return wrTaskDatas;
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param)throws BusinessException {
		return WrTaskDataDao.selectByParamCount(param);
	}

	@Override
	public List<WrTaskFinishCount> getWrTaskDataByUserId(Map<String, Object> param) throws BusinessException {
		return WrTaskDataDao.getWrTaskDataByUserId(param);
	}

	@Override
	public Integer getWrTaskDataCountByUserId(Map<String, Object> param) throws BusinessException {
		return  WrTaskDataDao.getWrTaskDataCountByUserId(param);
	}

	@Override
	public WrTaskData selectByPrimaryKey(String wrTaskDataId) throws BusinessException {
		return WrTaskDataDao.selectByPrimaryKey(wrTaskDataId);
	}
	
}
