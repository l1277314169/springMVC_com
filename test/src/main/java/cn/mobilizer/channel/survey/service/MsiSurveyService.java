package cn.mobilizer.channel.survey.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.po.MsiSurvey;

/**
 * @author yeeda.tian
 *
 */
public interface MsiSurveyService {
	
	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public Integer queryMsiSurveyCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiSurvey> queryMsiSurveyList(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 通过clientId 和 门店id获取 该店的暗访问卷id
	 * @param clientId
	 * @param storeId
	 * @return MsiSurvey
	 * @throws BusinessException
	 */
	public MsiSurvey getMsiSurveyByStoreIdAndClientId(Integer clientId, String storeId) throws BusinessException;
	
	/**
	 * 通过门店与问卷类型获取苹果2考试问卷
	 * @return
	 * @throws BusinessException
	 */
	public MsiSurvey findAppleExamMsiSurveyByStoreAndType(Integer clientId,String storeId,Integer msiSurveyType) throws BusinessException;
	
	/**
	 * 通过clientId 和 门店id获取该店的苹果暗访问卷id
	 * @param clientId
	 * @param storeId
	 * @return MsiSurvey
	 * @throws BusinessException
	 */
	public MsiSurvey findAppleMsiSurveyListByStore(Integer clientId, String storeId) throws BusinessException;
	
	/**
	 * 通过问卷类型关系查询问卷
	 * @param clientId
	 * @param storeId
	 * @return
	 * @throws BusinessException
	 */
	public MsiSurvey findAppleMsiSurveyListByType(Integer clientId, String storeId) throws BusinessException;
	
	/**
	 * 根据门店获取暗访问卷未填写历史问卷
	 * @param storeId
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiSurvey> findHistoryMsiSurveyListByStore(String storeId) throws BusinessException;
	
	public MsiSurvey selectByPrimaryKey(Integer msiSurveyId);
}
