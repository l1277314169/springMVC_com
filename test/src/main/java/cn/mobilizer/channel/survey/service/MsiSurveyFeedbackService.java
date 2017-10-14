package cn.mobilizer.channel.survey.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedback;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackAttachment;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackDetail;
import cn.mobilizer.channel.survey.po.MsiSurveyFinishCount;
import cn.mobilizer.channel.survey.vo.ExportMsiSurveyFeedbackVo;
import cn.mobilizer.channel.survey.vo.MsiSurveyFeedbackVO;
import cn.mobilizer.channel.survey.vo.SaveMsiSurveyComplainVo;

/**
 * @author yeeda.tian
 *
 */
public interface MsiSurveyFeedbackService {
	
	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public Integer queryMsiSurveyFeedbackCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiSurveyFeedback> queryMsiSurveyFeedbackList(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 
	 * @param msiSurveyFeedback
	 * @return
	 * @throws BusinessException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public String addMsiSurveyFeedback(Integer clientId, Integer clientUserId, MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException;
	
	/**
	 * 苹果问卷考试保存
	 * @param clientId
	 * @param clientUserId
	 * @param msiSurveyFeedbackVO
	 * @return
	 * @throws BusinessException
	 */
	public String addAppleExamSurveyFeedback(Integer clientId, Integer clientUserId, MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException;
	
	/**
	 * 苹果2问卷添加
	 * @param clientId
	 * @param clientUserId
	 * @param msiSurveyFeedbackVO
	 * @return
	 * @throws BusinessException
	 */
	public String addAppleMsiSurveyFeedback(Integer clientId, Integer clientUserId, MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException;
	
	/**
	 * by ID 查询
	 * @param feedbackId
	 * @return
	 * @throws BusinessException
	 */
	public MsiSurveyFeedback findMsiSurveyFeedbackById(String feedbackId) throws BusinessException;
	
	/**
	 * 通过feedbackId 查看detail数据
	 * @param feedbackId
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiSurveyFeedbackDetail> findMsiSurveyFeedbackDetailListByFeedbackId(String feedbackId) throws BusinessException;
	
	/**
	 * 
	 * @param clientId
	 * @param clientUserId
	 * @param msiSurveyFeedbackVO
	 * @return
	 * @throws BusinessException
	 */
	public Integer updateMsiSurveyData(Integer clientId, Integer clientUserId, MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException;
	
	/**
	 * 苹果2问卷修改
	 * @param clientId
	 * @param clientUserId
	 * @param msiSurveyFeedbackVO
	 * @return
	 * @throws BusinessException
	 */
	public Integer updateAppleMsiSurveyData(Integer clientId, Integer clientUserId, MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException;
	
	/**
	 * 查询问卷的图片列表
	 * @param feedbackId
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiSurveyFeedbackAttachment> getMsiSurveyFeedbackAttachmentList(String feedbackId,Byte attachmentType) throws BusinessException;
	
	/**
	 * 保存暗访申诉
	 * @param clientId
	 * @param clientUserId
	 * @param saveMsiSurveyComplainVo
	 * @throws BusinessException
	 */
	public void saveMsiSurveyComplain(Integer clientId, Integer clientUserId,SaveMsiSurveyComplainVo saveMsiSurveyComplainVo) throws BusinessException;
	

	/**
	 * 根据周期类型查询当前周期所填写的数据
	 * @param msiSurveyId
	 * @return
	 */
	public List<MsiSurveyFeedback> getMsiSurveyFeedbackByPresentCycle(Integer cycleType,Integer clientId,Integer clientUserId,Integer msiSurveyId,String storeId);
	
	/**
	 * 根据周期类型返回周期的开始时间和结束时间
	 * @param cycleType
	 * @param date
	 * @return
	 */
	public Map<String,Object> getCycle(Integer cycleType,Date date);
	
	/**
	 * 删除暗访问卷数据
	 * @param feedBackId
	 * @return
	 * @throws BusinessException
	 */
	public int deleteByPrimaryKey(String feedBackId) throws BusinessException;
	
	/**
	 * 暗访问卷导出
	 * @param exportCtTaskDataSerchVo
	 * @return
	 */
	public List<?> exportMsiSurveyFeedback(ExportMsiSurveyFeedbackVo exportMsiSurveyFeedbackVo) throws BusinessException;

	/**
	 * 获取苹果考试问卷完成次数
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiSurveyFinishCount> getMsiSurveyFeedbackCountByStoreId(Integer clientId,String storeId) throws BusinessException;
	
	/**
	 * 根据问卷类型获取苹果考试问卷完成次数
	 * @param clientId
	 * @param storeId
	 * @param surveyType
	 * @return
	 * @throws BusinessException
	 */
	public MsiSurveyFinishCount getMsiSurveyFeedbackCountByStoreIdAnd(Integer clientId,String storeId,Integer surveyType) throws BusinessException;

	/**
	 * 根据状态查找指定门店苹果2审核或经销商申诉的数据
	 * @param clientId
	 * @param storeId
	 * @param msiSurveyId
	 * @return
	 */
	public MsiSurveyFeedback findApprovalDataByStoreIdAndDataType(Integer clientId,String storeId,Integer msiSurveyId,byte dataType);

	/**
	 * 查找苹果2门店问卷数据
	 * @param clientId
	 * @param storeId
	 * @param msiSurveyId
	 * @return
	 */
	public MsiSurveyFeedback findMsiSurveyFeedbackByStoreId(Integer clientId,String storeId,Integer msiSurveyId);
	
	/**
	 * 查找苹果2 考试问卷
	 * @param clientId
	 * @param storeId
	 * @param msiSurveyId
	 * @return
	 */
	public List<MsiSurveyFeedback> findAppleExamFeedbackByStoreId(Integer clientId,String storeId,Integer msiSurveyId);
	
	/**
	 * 苹果二查询方法
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public Integer queryAppleTotalCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 苹果二查询方法
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<MsiSurveyFeedback> findAppleListByParams(Map<String, Object> params) throws BusinessException;
	
	/**
	 * 修改
	 * @param msiSurveyFeedback
	 * @throws BusinessException
	 */
	public void updateMsiSurveyFeedback(MsiSurveyFeedback msiSurveyFeedback) throws BusinessException;
}
