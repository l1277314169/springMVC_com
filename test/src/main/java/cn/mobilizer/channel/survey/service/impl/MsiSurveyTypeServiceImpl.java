package cn.mobilizer.channel.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.survey.dao.MsiSurveyTypeDao;
import cn.mobilizer.channel.survey.po.MsiSurveyType;
import cn.mobilizer.channel.survey.service.MsiSurveyTypeService;

@Service
public class MsiSurveyTypeServiceImpl implements MsiSurveyTypeService{
	
	@Autowired
	private MsiSurveyTypeDao msiSurveyTypeDao;

	@Override
	public MsiSurveyType findByMsiSurveyTypeByStoreId(Integer clientId,String storeId) {
		return msiSurveyTypeDao.findByMsiSurveyTypeByStoreId(clientId,storeId);
	}

}
