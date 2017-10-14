package cn.mobilizer.channel.specialTask.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.specialTask.dao.SpecialTaskDataDao;
import cn.mobilizer.channel.specialTask.po.SpecialTaskObject;
import cn.mobilizer.channel.specialTask.service.SpecialTaskDataService;
import cn.mobilizer.channel.util.Constants;
@Service
public class SpecialTaskDataServiceImpl implements SpecialTaskDataService{
	@Autowired
	private SpecialTaskDataDao specialTaskDataDao;
	@Override
	public List<SpecialTaskObject> findSpecialTaskDataObje(Integer clientId,
			String specialTaskId, Byte objectType,Integer clientPositionId) throws BusinessException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("specialTaskId", specialTaskId);
		parameters.put("objectType", objectType);
		parameters.put("clientPositionId", clientPositionId);
		List<SpecialTaskObject> selectSpecialTaskDataList = null;
		if(objectType != null && objectType.equals(Constants.OBJ_STORE)){
			selectSpecialTaskDataList = specialTaskDataDao.selectSpecialTaskDataRelationStoreList(parameters);
		}else{
			selectSpecialTaskDataList = specialTaskDataDao.selectSpecialTaskDataRelationClientUserList(parameters);
		}
		return selectSpecialTaskDataList;
	}

}
