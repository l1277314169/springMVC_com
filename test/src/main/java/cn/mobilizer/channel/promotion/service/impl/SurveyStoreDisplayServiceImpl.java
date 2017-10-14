package cn.mobilizer.channel.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.promotion.dao.SurveyStoreDisplayDao;
import cn.mobilizer.channel.promotion.po.SurveyStoreDisplay;
import cn.mobilizer.channel.promotion.service.SurveyStoreDisplayService;

@Service
public class SurveyStoreDisplayServiceImpl implements SurveyStoreDisplayService {
	
	@Autowired
	private SurveyStoreDisplayDao surveyStoreDisplayDao;
	@Autowired
	private OptionsService optionsService;
	
	@Override
	public List<SurveyStoreDisplay> getObjectList(Map<String, Object> parames) {
		return null;
	}

	@Override
	public SurveyStoreDisplay getObject(Map<String, Object> parames) {
		return null;
	}

	@Override
	public void batchSaveSurveyStoreDisplay(List<SurveyStoreDisplay> surveyStoreDisplays,Integer clientId)throws BusinessException {
		List<Options> optionsList = optionsService.findOptionsByOptionName("display_type", clientId);
		Map<String,Integer> optionsMap = new HashMap<String, Integer>();
		for(Options options : optionsList){
			optionsMap.put(options.getOptionText(), options.getOptionValue());
		}
		for(SurveyStoreDisplay surveyStoreDisplay : surveyStoreDisplays){
			if(StringUtils.isNotEmpty(surveyStoreDisplay.getDisplayTypeDesc())){
				surveyStoreDisplay.setDisplayType(optionsMap.get(surveyStoreDisplay.getDisplayTypeDesc()));
			}
		}
		surveyStoreDisplayDao.batchSaveSurveyStoreDisplay(surveyStoreDisplays);
	}

	@Override
	public List<SurveyStoreDisplay> selectByParams(Map<String, Object> params)
			throws BusinessException {
		return surveyStoreDisplayDao.selectByParams(params);
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> params)
			throws BusinessException {
		return surveyStoreDisplayDao.selectByParamCount(params);
	}

	@Override
	public void updateDisplayType(Map<String, Object> params)
			throws BusinessException {
		surveyStoreDisplayDao.updateDisplayType(params);
	}

}
