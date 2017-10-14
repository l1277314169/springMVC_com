package cn.mobilizer.channel.specialTask.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.specialTask.dao.SpecialTaskDataDao;
import cn.mobilizer.channel.specialTask.dao.SpecialTaskParameterDao;
import cn.mobilizer.channel.specialTask.po.SpecialTaskData;
import cn.mobilizer.channel.specialTask.po.SpecialTaskDetailData;
import cn.mobilizer.channel.specialTask.po.SpecialTaskParameter;
import cn.mobilizer.channel.specialTask.service.SpecialTaskDetailDataService;
import cn.mobilizer.channel.util.Constants;
@Service
public class SpecialTaskDetailDataServiceImpl implements SpecialTaskDetailDataService {
	@Autowired
	private SpecialTaskParameterDao specialTaskParameterDao;

	@Override
	public List<SpecialTaskParameter> findDetailDataByObjId(Integer clientId,String specialTaskId, Byte objectType, String popId, Integer clientPositionId,Integer clientUserId,String specialTaskDataId) throws BusinessException {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("clientId", clientId);
		parameter.put("specialTaskId", specialTaskId);
		parameter.put("objectType", objectType);
		parameter.put("clientPositionId", clientPositionId);
		parameter.put("popId", popId);
		parameter.put("clientUserId", clientUserId);
		parameter.put("specialTaskDataId", specialTaskDataId);
		List<SpecialTaskParameter> specialTaskData = null;
		if(specialTaskDataId == null || specialTaskDataId.equals("")){
			return specialTaskData = specialTaskParameterDao.findSpecialTaskParameterByNotExecution(parameter);
		}
		if(objectType != null && objectType.equals(Constants.OBJ_CLIENTUSER)) {
			specialTaskData = specialTaskParameterDao.findClientUserDataByParameter(parameter);
		}else{
			specialTaskData = specialTaskParameterDao.findStoreDateByParameter(parameter);
		}
		return specialTaskData;
	}

}
