package cn.mobilizer.channel.promotion.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.promotion.po.SurveyStoreDisplay;

public interface SurveyStoreDisplayService extends BasicService<SurveyStoreDisplay>{
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<SurveyStoreDisplay> selectByParams(Map<String,Object> params) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public Integer selectByParamCount(Map<String, Object> params) throws BusinessException;
	
	/**
	 * promotion导入批量更新
	 * @param surveyStoreDisplays
	 * @param clientId
	 * @throws BusinessException
	 */
	public void batchSaveSurveyStoreDisplay(List<SurveyStoreDisplay> surveyStoreDisplays,Integer clientId) throws BusinessException;

	public void updateDisplayType(Map<String, Object> params) throws BusinessException;
}
