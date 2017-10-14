package cn.mobilizer.channel.survey.service;

import cn.mobilizer.channel.survey.po.MsiSurveyType;

public interface MsiSurveyTypeService {
	
	public  MsiSurveyType findByMsiSurveyTypeByStoreId(Integer clientId,String storeId);
	
}
