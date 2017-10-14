package cn.mobilizer.channel.survey.service;

import java.util.List;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.vo.MsiQuestionVO;

/**
 * @author yeeda.tian
 *
 */
public interface MsiQuestionService {
	
	/**
	 * 通过暗访问卷的id获取所有的问题和答案
	 * @param msiSurveyId 暗访问卷ID
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiQuestionVO> getMsiQuestionListByMsiSurveyId(Integer msiSurveyId) throws BusinessException;
	
	/**
	 * 苹果考试问卷随机获取10道问题
	 * @param msiSurveyId
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiQuestionVO> getAppleExamMsiQuestionByMsiSurveyId(Integer msiSurveyId) throws BusinessException;
	
	/**
	 * 通过msiSurveyId feedbackId 查询出某个问卷的所有选项值
	 * @param msiSurveyId
	 * @param feedbackId
	 * @return 问卷的所有选项-和选中的值
	 * @throws BusinessException
	 */
	public List<MsiQuestionVO> getMsiQuestionListWithChecked(Integer msiSurveyId,String feedbackId) throws BusinessException;
	
	/**
	 * 通过msiSurveyId feedbackId 查询出某个问卷的所有选项值
	 * @param msiSurveyId
	 * @param feedbackId
	 * @return 问卷的所有选项-和选中的值
	 * @throws BusinessException
	 */
	public List<MsiQuestionVO> getDetailAppleExamMsiSurveyData(Integer msiSurveyId,String feedbackId) throws BusinessException;
	
	
	/**
	 *  通过msiSurveyId feedbackId parentQuestionId questionQroupId 查询出某个苹果问卷的选项值
	 * @param msiSurveyId
	 * @param feedbackId
	 * @param parentQuestionId
	 * @param questionQroupId
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiQuestionVO> getAppleMsiQuestionListWithChecked(Integer clientId,Integer msiSurveyId,String feedbackId,Integer parentQuestionId,Integer questionQroupId) throws BusinessException;
	

	/**
	 * 通过暗访问卷的id与问题的parentId获取问题和答案
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiQuestionVO> getMsiQuestionsByMsiSurveyIdAndParentId(Integer msiSurveyId,Integer parentQuestionId,Integer questionQroupId) throws BusinessException;

}
