package cn.mobilizer.channel.wrTask.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.dao.WrTaskDetailDataDao;
import cn.mobilizer.channel.wrTask.dao.WrTaskParameterDao;
import cn.mobilizer.channel.wrTask.po.WrTaskDetailData;
import cn.mobilizer.channel.wrTask.po.WrTaskParameter;
import cn.mobilizer.channel.wrTask.service.WrTaskDetailDataService;

@Service
public class WrTaskDetailDataServiceImpl implements WrTaskDetailDataService {
	
	@Autowired
	private WrTaskDetailDataDao wrTaskDetailDataDao;
	@Autowired
	private WrTaskParameterDao wrTaskParameterDao;

	@Override
	public List<WrTaskDetailData> selectByParams(Map<String, Object> param)throws BusinessException {
		List<WrTaskDetailData> wrTaskDetailDatas = wrTaskDetailDataDao.selectByParams(param);
		for(WrTaskDetailData wrTaskDetailData : wrTaskDetailDatas){
			WrTaskParameter wrTaskParameter = wrTaskParameterDao.selectByPrimaryKey(wrTaskDetailData.getWrTaskParameterId());
			if(wrTaskParameter!=null){
				wrTaskDetailData.setControlType(wrTaskParameter.getControlType());
				wrTaskDetailData.setOptionName(wrTaskParameter.getOptionName());
			}
		}
		return wrTaskDetailDatas;
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param)throws BusinessException {
		return wrTaskDetailDataDao.selectByParamCount(param);
	}

}
