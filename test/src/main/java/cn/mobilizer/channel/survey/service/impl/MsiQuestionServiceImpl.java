package cn.mobilizer.channel.survey.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.dao.MsiQuestionDao;
import cn.mobilizer.channel.survey.dao.MsiSurveyFeedbackAttachmentDao;
import cn.mobilizer.channel.survey.dao.MsiSurveyFeedbackDao;
import cn.mobilizer.channel.survey.dao.MsiSurveyFeedbackDetailDao;
import cn.mobilizer.channel.survey.dao.MsiSurveyParameterDao;
import cn.mobilizer.channel.survey.dao.PhotoListDao;
import cn.mobilizer.channel.survey.po.MsiAnswer;
import cn.mobilizer.channel.survey.po.MsiQuestion;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedback;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackAttachment;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackDetail;
import cn.mobilizer.channel.survey.po.MsiSurveyParameter;
import cn.mobilizer.channel.survey.po.PhotoList;
import cn.mobilizer.channel.survey.service.MsiQuestionService;
import cn.mobilizer.channel.survey.vo.MsiQuestionVO;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class MsiQuestionServiceImpl implements MsiQuestionService {

	private static final Log LOG = LogFactory.getLog(MsiQuestionServiceImpl.class);

	@Autowired
	private MsiQuestionDao msiQuestionDao;
	@Autowired
	private MsiSurveyParameterDao msiSurveyParameterDao;
	@Autowired
	private MsiSurveyFeedbackDetailDao msiSurveyFeedbackDetailDao;
	@Autowired
	private PhotoListDao photoListDao;
	@Autowired
	private MsiSurveyFeedbackAttachmentDao msiSurveyFeedbackAttachmentDao;
	@Autowired
	private MsiSurveyFeedbackDao msiSurveyFeedbackDao;
	
	@Override
	public List<MsiQuestionVO> getMsiQuestionListByMsiSurveyId(Integer msiSurveyId) throws BusinessException{
		List<MsiQuestionVO> list = null;
		try {
			if (msiSurveyId != null) {
				list = msiQuestionDao.getMsiQuestionListByMsiSurveyId(msiSurveyId);
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiQuestionListByMsiSurveyId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<MsiQuestionVO> getAppleExamMsiQuestionByMsiSurveyId(Integer msiSurveyId) throws BusinessException {
		List<MsiQuestionVO> list = new ArrayList<MsiQuestionVO>();
		try {
			if (msiSurveyId != null) {
				List<MsiQuestionVO> msiQuestionVOs = msiQuestionDao.getAppleExamMsiQuestionAndAnswerByMsiSurveyId(msiSurveyId);
				List<Integer> indexs = new ArrayList<Integer>();
				int i;
				while(indexs.size() < 10){
		            i = 0+(int)(Math.random()*msiQuestionVOs.size());
		            if(!indexs.contains(i)){
		            	indexs.add(i);
		            }
		        }
				for(Integer index : indexs){
					list.add(msiQuestionVOs.get(index));
				}
				LOG.info("问卷考试题目数：===="+list.size());
				Map<String,Object> params = new HashMap<String, Object>();
				for(MsiQuestionVO msiQuestionVO : list){
					params.put("clientId", msiQuestionVO.getClientId());
					params.put("relationType", 2);
					params.put("relationId", msiQuestionVO.getMsiQuestionId());
					List<PhotoList> photoLists = photoListDao.selectEntityByTypeAndRelationId(params);
					msiQuestionVO.setPhotoList(photoLists);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiQuestionListByMsiSurveyId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<MsiQuestionVO> getMsiQuestionListWithChecked(Integer msiSurveyId,String feedbackId) throws BusinessException{
		List<MsiQuestionVO> list = null;
		try {
			if (msiSurveyId != null && StringUtil.isNotEmptyString(feedbackId)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("msiSurveyId", msiSurveyId);
				params.put("feedbackId", feedbackId);
				list = msiQuestionDao.getMsiQuestionListWithChecked(params);
				for(MsiQuestionVO msiQuestionVO : list){
					params.put("clientId", msiQuestionVO.getClientId());
					params.put("relationType", 2);
					params.put("relationId", msiQuestionVO.getMsiQuestionId());
					List<PhotoList> photoLists = photoListDao.selectEntityByTypeAndRelationId(params);
					msiQuestionVO.setPhotoList(photoLists);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiQuestionListWithChecked error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	
	@Override
	public List<MsiQuestionVO> getDetailAppleExamMsiSurveyData(Integer msiSurveyId, String feedbackId) throws BusinessException {
		String msiQuestionIds = msiQuestionDao.getMsiQuestionIdsByFeedbackId(feedbackId);
		List<MsiQuestionVO> list = null;
		try {
			if (msiSurveyId != null && StringUtil.isNotEmptyString(feedbackId)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("msiSurveyId", msiSurveyId);
				params.put("feedbackId", feedbackId);
				params.put("msiQuestionIds", msiQuestionIds);
				list = msiQuestionDao.getMsiQuestionListWithCheckedByIds(params);
				for(MsiQuestionVO msiQuestionVO : list){
					params.put("clientId", msiQuestionVO.getClientId());
					params.put("relationType", 2);
					params.put("relationId", msiQuestionVO.getMsiQuestionId());
					List<PhotoList> photoLists = photoListDao.selectEntityByTypeAndRelationId(params);
					msiQuestionVO.setPhotoList(photoLists);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiQuestionListWithChecked error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<MsiQuestionVO> getAppleMsiQuestionListWithChecked(Integer clientId,Integer msiSurveyId,String feedbackId,Integer parentQuestionId,Integer questionQroupId) throws BusinessException{
		List<MsiQuestionVO> list = null;
		List<MsiQuestionVO> approvalMsiQuestionVOs = null;   //审核人员填写的素具
		List<MsiQuestionVO> appealMsiQuestionVOs = null;    //申诉人填写的数据
		try {
			if (msiSurveyId != null && StringUtil.isNotEmptyString(feedbackId)) {
				MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackDao.findMsiSurveyFeedbackById(feedbackId);
				Map<String,Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("clientId", msiSurveyFeedback.getClientId());
				parameterMap.put("storeId", msiSurveyFeedback.getStoreId());
				parameterMap.put("msiSurveyId", msiSurveyFeedback.getMsiSurveyId());
				parameterMap.put("dataType", (byte)2);      //审核员数据
				MsiSurveyFeedback approvalMsiSurveyData = msiSurveyFeedbackDao.findApprovalDataByStoreIdAndDataType(parameterMap);   //获取审核人员填写的数据
				parameterMap.put("dataType", (byte)3);      //经销商申诉数据
				MsiSurveyFeedback appealMsiSurveyData = msiSurveyFeedbackDao.findApprovalDataByStoreIdAndDataType(parameterMap);   //获取审核人员填写的数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("msiSurveyId", msiSurveyId);
				params.put("feedbackId", feedbackId);
				params.put("parentId", parentQuestionId);
				params.put("questionQroupId", questionQroupId);
				list = msiQuestionDao.getAppleMsiQuestionListWithChecked(params);
				Map<String, Object> approvalParams = new HashMap<String, Object>();
				if(approvalMsiSurveyData!=null){
					approvalParams.put("msiSurveyId", msiSurveyId);
					approvalParams.put("feedbackId", approvalMsiSurveyData.getFeedbackId());
					approvalParams.put("parentId", parentQuestionId);
					approvalParams.put("questionQroupId", questionQroupId);
					approvalMsiQuestionVOs = msiQuestionDao.getAppleMsiQuestionListWithChecked(approvalParams);
				}
				if(appealMsiSurveyData!=null){
					approvalParams.put("msiSurveyId", msiSurveyId);
					approvalParams.put("feedbackId", appealMsiSurveyData.getFeedbackId());
					approvalParams.put("parentId", parentQuestionId);
					approvalParams.put("questionQroupId", questionQroupId);
					appealMsiQuestionVOs = msiQuestionDao.getAppleMsiQuestionListWithChecked(approvalParams);
				}
				for(MsiQuestionVO msiQuestion : list){
					if(approvalMsiSurveyData!=null){
						for(MsiQuestionVO appromsiQuestion : approvalMsiQuestionVOs){
							if(msiQuestion.getMsiQuestionId().intValue() == appromsiQuestion.getMsiQuestionId().intValue()){
								msiQuestion.setApprovalDataList(appromsiQuestion.getMsiAnswerList());
								msiQuestion.setCol3(appromsiQuestion.getCol1());     //文本框数据
							}
						}
					}
					if(appealMsiSurveyData!=null){
						for(MsiQuestionVO appealmsiQuestion : appealMsiQuestionVOs){
							if(msiQuestion.getMsiQuestionId().intValue() == appealmsiQuestion.getMsiQuestionId().intValue()){
								msiQuestion.setAppealDataList(appealmsiQuestion.getMsiAnswerList());
								msiQuestion.setCol3(appealmsiQuestion.getCol1());     //文本框数据
							}
						}
					}
					params.put("parentId", msiQuestion.getMsiQuestionId());
					List<MsiQuestionVO> childrenList = msiQuestionDao.getAppleMsiQuestionListWithChecked(params);
					if(!childrenList.isEmpty()){
						for(MsiQuestionVO msiQuestionVO : childrenList){
							for(MsiAnswer msiAnswer : msiQuestionVO.getMsiAnswerList()){
								if(StringUtil.isNotEmptyString(msiAnswer.getParameterIds())){
									List<MsiSurveyParameter> msiSurveyParameters = new ArrayList<MsiSurveyParameter>();
									Map<String,Object> msiSurveyParameterDataMap = new HashMap<String, Object>();
									for(String parameterId : msiAnswer.getParameterIds().split(",")){
										MsiSurveyParameter msiSurveyParameter = msiSurveyParameterDao.selectByPrimaryKey(new Integer(parameterId));
										msiSurveyParameters.add(msiSurveyParameter);
										Map<String,Object> parameter = new HashMap<String, Object>();
										parameter.put("feedbackId", feedbackId);
										parameter.put("msiQuestionId", msiAnswer.getMsiQuestionId());
										parameter.put("msiAnswerId", msiAnswer.getMsiAnswerId());
										parameter.put("msiSurveyParameterId", parameterId);
										MsiSurveyFeedbackDetail msiSurveyFeedbackDetail = msiSurveyFeedbackDetailDao.getMsiSurveyFeedbackDetailByParams(parameter);
										if(msiSurveyFeedbackDetail!=null){
											msiSurveyParameterDataMap.put(msiAnswer.getMsiAnswerId()+"_"+msiSurveyFeedbackDetail.getCol2(), msiSurveyFeedbackDetail.getCol1());
										}
									}
									msiAnswer.setMsiSurveyParameters(msiSurveyParameters);
									msiAnswer.setMsiSurveyParameterDataMap(msiSurveyParameterDataMap);
								}
							}
						}
					}
					Map<String, Object> paramAttachment = new HashMap<String, Object>();
					paramAttachment.put("feedbackId", feedbackId);
					paramAttachment.put("attachmentType", Constants.MSI_SURVEY_IMG);
					paramAttachment.put("msiQuestionId", msiQuestion.getMsiQuestionId());
					List<MsiSurveyFeedbackAttachment> msiSurveyFeedbackAttachments = msiSurveyFeedbackAttachmentDao.getMsiSurveyFeedbackAttachmentList(paramAttachment);
					msiQuestion.setMsiSurveyFeedbackAttachments(msiSurveyFeedbackAttachments);
					if(appealMsiSurveyData!=null){
						paramAttachment.put("feedbackId", appealMsiSurveyData.getFeedbackId());
						List<MsiSurveyFeedbackAttachment> appealMsiSurveyFeedbackAttachments = msiSurveyFeedbackAttachmentDao.getMsiSurveyFeedbackAttachmentList(paramAttachment);
						msiQuestion.setAppealSurveyFeedbackAttachments(appealMsiSurveyFeedbackAttachments);
					}
					msiQuestion.setChildrenList(childrenList);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiQuestionListWithChecked error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<MsiQuestionVO> getMsiQuestionsByMsiSurveyIdAndParentId(Integer msiSurveyId,Integer parentQuestionId,Integer questionQroupId) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("msiSurveyId", msiSurveyId);
		params.put("parentId", parentQuestionId);
		params.put("questionQroupId", questionQroupId);
		List<MsiQuestionVO> list = null;
		try {
			if (msiSurveyId != null) {
				list = msiQuestionDao.getMsiQuestionsByMsiSurveyIdAndParentId(params);
				for(MsiQuestionVO msiQuestion : list){
					params.put("parentId", msiQuestion.getMsiQuestionId());
					List<MsiQuestionVO> childrenList = msiQuestionDao.getMsiQuestionsByMsiSurveyIdAndParentId(params);
					if(!childrenList.isEmpty()){
						for(MsiQuestionVO msiQuestionVO : childrenList){
							for(MsiAnswer msiAnswer : msiQuestionVO.getMsiAnswerList()){
								if(StringUtil.isNotEmptyString(msiAnswer.getParameterIds())){
									List<MsiSurveyParameter> msiSurveyParameters = new ArrayList<MsiSurveyParameter>();
									for(String parameterId : msiAnswer.getParameterIds().split(",")){
										MsiSurveyParameter msiSurveyParameter = msiSurveyParameterDao.selectByPrimaryKey(new Integer(parameterId));
										msiSurveyParameters.add(msiSurveyParameter);
									}
									msiAnswer.setMsiSurveyParameters(msiSurveyParameters);
								}
							}
						}
					}
					msiQuestion.setChildrenList(childrenList);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiQuestionListByMsiSurveyId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	
	
}
