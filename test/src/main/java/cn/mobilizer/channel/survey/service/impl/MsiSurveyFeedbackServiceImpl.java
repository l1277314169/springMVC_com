package cn.mobilizer.channel.survey.service.impl;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.dao.MsiSurveyFeedbackAttachmentDao;
import cn.mobilizer.channel.survey.dao.MsiSurveyFeedbackDao;
import cn.mobilizer.channel.survey.dao.MsiSurveyFeedbackDetailDao;
import cn.mobilizer.channel.survey.po.MsiSurveyComplain;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedback;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackAttachment;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackDetail;
import cn.mobilizer.channel.survey.po.MsiSurveyFinishCount;
import cn.mobilizer.channel.survey.service.MsiSurveyComplainService;
import cn.mobilizer.channel.survey.service.MsiSurveyFeedbackService;
import cn.mobilizer.channel.survey.vo.ExportMsiSurveyFeedbackVo;
import cn.mobilizer.channel.survey.vo.MsiSurveyFeedbackVO;
import cn.mobilizer.channel.survey.vo.SaveMsiSurveyComplainVo;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class MsiSurveyFeedbackServiceImpl implements MsiSurveyFeedbackService {

	private static final Log LOG = LogFactory.getLog(MsiSurveyFeedbackServiceImpl.class);
	
	@Autowired
	private MsiSurveyFeedbackDao msiSurveyFeedbackDao;
	@Autowired
	private MsiSurveyFeedbackDetailDao msiSurveyFeedbackDetailDao;
	@Autowired
	private MsiSurveyFeedbackAttachmentDao msiSurveyFeedbackAttachmentDao;
	@Autowired
	private MsiSurveyComplainService msiSurveyComplainService;
	
	@Override
	public Integer queryMsiSurveyFeedbackCount(Map<String, Object> params) throws BusinessException{
		int count = 0;
		try {
			if ((params != null) && (params.size() > 0)) {
				count = msiSurveyFeedbackDao.queryMsiSurveyFeedbackCount(params);
			}
		} catch (BusinessException e) {
			LOG.error("method queryMsiSurveyFeedbackCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<MsiSurveyFeedback> queryMsiSurveyFeedbackList(Map<String, Object> params) throws BusinessException{
		List<MsiSurveyFeedback> list = null;
		try {
			if ((params != null) && (params.size() > 0)) {
				list = msiSurveyFeedbackDao.queryMsiSurveyFeedbackList(params);
			}
		} catch (BusinessException e) {
			LOG.error("method queryMsiSurveyFeedbackList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public String addMsiSurveyFeedback(Integer clientId, Integer clientUserId, MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException{
		String feedbackId ="";
		try {
			MsiSurveyFeedback msiSurveyFeedback = new MsiSurveyFeedback();
			BeanUtils.copyProperties(msiSurveyFeedbackVO,msiSurveyFeedback);
			msiSurveyFeedback.setFeedbackId(UUID.randomUUID().toString());
			msiSurveyFeedback.setCreateBy(msiSurveyFeedbackVO.getClientUserId());
			feedbackId = msiSurveyFeedbackDao.addMsiSurveyFeedback(msiSurveyFeedback);
			if(StringUtil.isNotEmptyString(feedbackId) && msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails() !=null && msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails().size() >0){
				List<MsiSurveyFeedbackDetail> ls = msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails();
				List<MsiSurveyFeedbackDetail> in_ls = new ArrayList<MsiSurveyFeedbackDetail>();
				for ( Iterator<MsiSurveyFeedbackDetail> iterator = ls.iterator() ; iterator.hasNext() ; ) {
					MsiSurveyFeedbackDetail msiSurveyFeedbackDetail = (MsiSurveyFeedbackDetail) iterator.next();
					if(msiSurveyFeedbackDetail !=null && msiSurveyFeedbackDetail.getChecked() !=null && msiSurveyFeedbackDetail.getChecked()){
						msiSurveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
						msiSurveyFeedbackDetail.setFeedbackId(feedbackId);
						msiSurveyFeedbackDetail.setClientId(clientId);
						in_ls.add(msiSurveyFeedbackDetail);
					}
				}
				if(in_ls !=null && in_ls.size() >0){
					msiSurveyFeedbackDetailDao.insertMsiSurveyFeedbackDetailBylist(in_ls);
				}
				
				/**图片入库**/
				String imageNames = msiSurveyFeedbackVO.getImageNames();
				if(StringUtil.isNotEmptyString(imageNames)){
//					List<String> images = Arrays.asList(imageNames.split(","));
					String[] images = imageNames.split(",");
					List<MsiSurveyFeedbackAttachment> atts = new ArrayList<MsiSurveyFeedbackAttachment>();
					for (String img : images) {
						MsiSurveyFeedbackAttachment attachment = new MsiSurveyFeedbackAttachment();
						attachment.setAttachment(img);
						attachment.setAttachmentId(UUID.randomUUID().toString());
						attachment.setClientId(clientId);
						attachment.setAttachmentType(Constants.MSI_SURVEY_IMG);
						attachment.setFeedbackId(feedbackId);
						atts.add(attachment);
					}
					
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("atts", atts);
					msiSurveyFeedbackAttachmentDao.insertMsiSurveyFeedbackAttachmentByArray(parameters);
					/*Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("feedbackId", feedbackId);
					parameters.put("array", Arrays.asList(imageNames.split(",")));
					parameters.put("clientId", clientId);
					parameters.put("path", "/"+clientId+"/web/");*/
				}
			}
		} catch (BusinessException e) {
			LOG.error("method addMsiSurveyFeedback error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return feedbackId;
	}

	@Override
	public MsiSurveyFeedback findMsiSurveyFeedbackById(String feedbackId) throws BusinessException{
		MsiSurveyFeedback msiSurveyFeedback = null;
		try {
			if (StringUtil.isNotEmptyString(feedbackId)) {
				msiSurveyFeedback = msiSurveyFeedbackDao.findMsiSurveyFeedbackById(feedbackId);
			}
		} catch (BusinessException e) {
			LOG.error("method findMsiSurveyFeedbackById error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return msiSurveyFeedback;
	}

	@Override
	public List<MsiSurveyFeedbackDetail> findMsiSurveyFeedbackDetailListByFeedbackId(String feedbackId) throws BusinessException{
		List<MsiSurveyFeedbackDetail> list = null;
		try {
			if (StringUtil.isNotEmptyString(feedbackId)) {
				list = msiSurveyFeedbackDetailDao.findMsiSurveyFeedbackDetailListByFeedbackId(feedbackId);
			}
		} catch (BusinessException e) {
			LOG.error("method findMsiSurveyFeedbackDetailListByFeedbackId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
		return list;
	}
	
	@Override
	public Integer updateMsiSurveyData(Integer clientId,Integer clientUserId,MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException{
		int i = 0;
		try {
			MsiSurveyFeedback msiSurveyFeedback = new MsiSurveyFeedback();
			BeanUtils.copyProperties(msiSurveyFeedbackVO,msiSurveyFeedback);
			String feedbackId = msiSurveyFeedback.getFeedbackId();
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("feedbackId", feedbackId);
			
			msiSurveyFeedbackDao.updateMsiSurveyFeedback(msiSurveyFeedback);
			if(StringUtil.isNotEmptyString(feedbackId)){
				/**处理问卷**/
				if(msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails() !=null && msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails().size() >0){
					/**删除原来的问卷答案**/
					i = msiSurveyFeedbackDetailDao.deleteByFeedbackId(feedbackId);
					List<MsiSurveyFeedbackDetail> ls = msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails();
					for ( Iterator<MsiSurveyFeedbackDetail> iterator = ls.iterator() ; iterator.hasNext() ; ) {
						MsiSurveyFeedbackDetail msiSurveyFeedbackDetail = (MsiSurveyFeedbackDetail) iterator.next();
						if(msiSurveyFeedbackDetail !=null && msiSurveyFeedbackDetail.getChecked() !=null && msiSurveyFeedbackDetail.getChecked()){
							parameters.put("msiQuestionId", msiSurveyFeedbackDetail.getMsiQuestionId());
							parameters.put("msiAnswerId", msiSurveyFeedbackDetail.getMsiAnswerId());
							MsiSurveyFeedbackDetail msfd = msiSurveyFeedbackDetailDao.getMsiSurveyFeedbackDetailByParams(parameters);
							if (msfd != null) {
								msfd.setIsDelete(Constants.IS_DELETE_FALSE);
								msfd.setLastUpdateTime(new Date());
								msiSurveyFeedbackDetailDao.updateMsiSurveyFeedbackDetail(msfd);
							} else {
								msiSurveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
								msiSurveyFeedbackDetail.setFeedbackId(feedbackId);
								msiSurveyFeedbackDetail.setClientId(clientId);
								msiSurveyFeedbackDetailDao.insertMsiSurveyFeedback(msiSurveyFeedbackDetail);
							}
						}
					}
				}
				/**处理图片**/
				String imageNames = msiSurveyFeedbackVO.getImageNames();
				/**删除原来的图片**/
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("feedbackId", feedbackId);
				params.put("attachmentType", Constants.MSI_SURVEY_IMG);
				msiSurveyFeedbackAttachmentDao.deleteByFeedbackId(params);				
				if(StringUtil.isNotEmptyString(imageNames)){
					for ( String image : imageNames.split(",") ) {
						parameters.put("feedbackId", feedbackId);
						parameters.put("attachment", image);
						parameters.put("attachmentType",Constants.MSI_SURVEY_IMG);
						MsiSurveyFeedbackAttachment msfa = msiSurveyFeedbackAttachmentDao.getMsiSurveyFeedbackAttachmentByParams(parameters);
						if (msfa != null) {
							msfa.setIsDelete(Constants.IS_DELETE_FALSE);
							msfa.setLastUpdateTime(null);
							msiSurveyFeedbackAttachmentDao.updateMsiSurveyFeedbackAttachment(msfa);
						} else {
							msfa = new MsiSurveyFeedbackAttachment();
							msfa.setAttachmentId(UUID.randomUUID().toString());
							msfa.setFeedbackId(feedbackId);
							msfa.setAttachment(image);
							msfa.setClientId(clientId);
							msfa.setIsDelete(Constants.IS_DELETE_FALSE);
							msiSurveyFeedbackAttachmentDao.insertMsiSurveyFeedbackAttachment(msfa);
						}
					}
				}
			}
		} catch (BusinessException e) {
			i = 0;
			LOG.error("method updateMsiSurveyData error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		return i;
	}
	
	
	
	@Override
	public String addAppleMsiSurveyFeedback(Integer clientId,Integer clientUserId, MsiSurveyFeedbackVO msiSurveyFeedbackVO)
			throws BusinessException {
		String feedbackId ="";
		try {
			MsiSurveyFeedback msiSurveyFeedback = new MsiSurveyFeedback();
			BeanUtils.copyProperties(msiSurveyFeedbackVO,msiSurveyFeedback);
			msiSurveyFeedback.setFeedbackId(UUID.randomUUID().toString());
			msiSurveyFeedback.setCreateBy(msiSurveyFeedbackVO.getClientUserId());
			feedbackId = msiSurveyFeedbackDao.addMsiSurveyFeedback(msiSurveyFeedback);
			if(StringUtil.isNotEmptyString(feedbackId) && msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails() !=null && msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails().size() >0){
				List<MsiSurveyFeedbackDetail> ls = msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails();
				List<MsiSurveyFeedbackDetail> in_ls = new ArrayList<MsiSurveyFeedbackDetail>();
				for ( Iterator<MsiSurveyFeedbackDetail> iterator = ls.iterator() ; iterator.hasNext() ; ) {
					MsiSurveyFeedbackDetail msiSurveyFeedbackDetail = (MsiSurveyFeedbackDetail) iterator.next();
					if(msiSurveyFeedbackDetail !=null){
						msiSurveyFeedbackDetail.setFeedbackId(feedbackId);
						msiSurveyFeedbackDetail.setClientId(clientId);
						msiSurveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
						in_ls.add(msiSurveyFeedbackDetail);
					}
				}
				if(in_ls !=null && in_ls.size() >0){
					msiSurveyFeedbackDetailDao.insertMsiSurveyFeedbackDetailBylist(in_ls);
				}
				
				/**图片入库**/
				Map<Integer, String> questionMap = msiSurveyFeedbackVO.getQuestionImgMap(); 
				for(Integer key : questionMap.keySet()){
					String imageNames = questionMap.get(key);
					if(StringUtil.isNotEmptyString(imageNames)){
//						List<String> images = Arrays.asList(imageNames.split(","));
						String[] images = imageNames.split(",");
						List<MsiSurveyFeedbackAttachment> atts = new ArrayList<MsiSurveyFeedbackAttachment>();
						for (String img : images) {
							MsiSurveyFeedbackAttachment attachment = new MsiSurveyFeedbackAttachment();
							attachment.setAttachment(img);
							attachment.setAttachmentId(UUID.randomUUID().toString());
							attachment.setClientId(clientId);
							attachment.setAttachmentType(Constants.MSI_SURVEY_IMG);
							attachment.setFeedbackId(feedbackId);
							attachment.setCol1(key.toString());
							atts.add(attachment);
						}
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("atts", atts);
						msiSurveyFeedbackAttachmentDao.insertMsiSurveyFeedbackAttachmentByArray(parameters);
					}
				}
			}
		} catch (BusinessException e) {
			LOG.error("method addMsiSurveyFeedback error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return feedbackId;
	}

	@Override
	public String addAppleExamSurveyFeedback(Integer clientId,Integer clientUserId, MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException {
		String feedbackId ="";
		try {
			MsiSurveyFeedback msiSurveyFeedback = new MsiSurveyFeedback();
			BeanUtils.copyProperties(msiSurveyFeedbackVO,msiSurveyFeedback);
			msiSurveyFeedback.setFeedbackId(UUID.randomUUID().toString());
			msiSurveyFeedback.setCreateBy(msiSurveyFeedbackVO.getClientUserId());
			feedbackId = msiSurveyFeedbackDao.addMsiSurveyFeedback(msiSurveyFeedback);
			if(StringUtil.isNotEmptyString(feedbackId) && msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails() !=null && msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails().size() >0){
				List<MsiSurveyFeedbackDetail> ls = msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails();
				List<MsiSurveyFeedbackDetail> in_ls = new ArrayList<MsiSurveyFeedbackDetail>();
				for ( Iterator<MsiSurveyFeedbackDetail> iterator = ls.iterator() ; iterator.hasNext() ; ) {
					MsiSurveyFeedbackDetail msiSurveyFeedbackDetail = (MsiSurveyFeedbackDetail) iterator.next();
					if(msiSurveyFeedbackDetail !=null){
						msiSurveyFeedbackDetail.setFeedbackId(feedbackId);
						msiSurveyFeedbackDetail.setClientId(clientId);
						msiSurveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
						in_ls.add(msiSurveyFeedbackDetail);
					}
				}
				if(in_ls !=null && in_ls.size() >0){
					msiSurveyFeedbackDetailDao.insertMsiSurveyFeedbackDetailBylist(in_ls);
				}
			}
		} catch (BusinessException e) {
			LOG.error("method addMsiSurveyFeedback error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return feedbackId;
	}

	@Override
	public Integer updateAppleMsiSurveyData(Integer clientId,Integer clientUserId,MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException{
		int i = 0;
		try {
			MsiSurveyFeedback msiSurveyFeedback = new MsiSurveyFeedback();
			BeanUtils.copyProperties(msiSurveyFeedbackVO,msiSurveyFeedback);
			String feedbackId = msiSurveyFeedback.getFeedbackId();
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("feedbackId", feedbackId);
			
			msiSurveyFeedbackDao.updateMsiSurveyFeedback(msiSurveyFeedback);
			if(StringUtil.isNotEmptyString(feedbackId)){
				/**处理问卷**/
				if(msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails() !=null && msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails().size() >0){
					/**删除原来的问卷答案**/
					i = msiSurveyFeedbackDetailDao.deleteByFeedbackId(feedbackId);
					List<MsiSurveyFeedbackDetail> ls = msiSurveyFeedbackVO.getMsiSurveyFeedbackDetails();
					for ( Iterator<MsiSurveyFeedbackDetail> iterator = ls.iterator() ; iterator.hasNext() ; ) {
						MsiSurveyFeedbackDetail msiSurveyFeedbackDetail = (MsiSurveyFeedbackDetail) iterator.next();
						if(msiSurveyFeedbackDetail !=null){
							parameters.put("msiQuestionId", msiSurveyFeedbackDetail.getMsiQuestionId());
							parameters.put("msiAnswerId", msiSurveyFeedbackDetail.getMsiAnswerId());
							parameters.put("msiSurveyParameterId", msiSurveyFeedbackDetail.getMsiAnswerId());
							MsiSurveyFeedbackDetail msfd = msiSurveyFeedbackDetailDao.getMsiSurveyFeedbackDetailByParams(parameters);
							if (msfd != null) {
								msfd.setIsDelete(Constants.IS_DELETE_FALSE);
								msfd.setLastUpdateTime(new Date());
								if(StringUtil.isNotEmptyString(msiSurveyFeedbackDetail.getCol1()) && !msiSurveyFeedbackDetail.getCol1().equals(msfd.getCol1())){
									msfd.setCol1(msiSurveyFeedbackDetail.getCol1());
								}
								msiSurveyFeedbackDetailDao.updateMsiSurveyFeedbackDetail(msfd);
							} else {
								msiSurveyFeedbackDetail.setDetailId(UUID.randomUUID().toString());
								msiSurveyFeedbackDetail.setFeedbackId(feedbackId);
								msiSurveyFeedbackDetail.setClientId(clientId);
								msiSurveyFeedbackDetailDao.insertMsiSurveyFeedback(msiSurveyFeedbackDetail);
							}
						}
					}
				}
				
				/**图片入库**/
				Map<Integer, String> questionMap = msiSurveyFeedbackVO.getQuestionImgMap(); 
				for(Integer key : questionMap.keySet()){
					String imageNames = questionMap.get(key);
					/**删除原来的图片**/
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("feedbackId", feedbackId);
					params.put("attachmentType", Constants.MSI_SURVEY_IMG);
					params.put("msiQuestionId",key.toString());
					msiSurveyFeedbackAttachmentDao.deleteByFeedbackId(params);
					if(StringUtil.isNotEmptyString(imageNames)){
						for ( String image : imageNames.split(",") ) {
							parameters.put("feedbackId", feedbackId);
							parameters.put("attachment", image);
							parameters.put("attachmentType",Constants.MSI_SURVEY_IMG);
							parameters.put("msiQuestionId", key.toString());
							MsiSurveyFeedbackAttachment msfa = msiSurveyFeedbackAttachmentDao.getMsiSurveyFeedbackAttachmentByParams(parameters);
							if (msfa != null) {
								msfa.setIsDelete(Constants.IS_DELETE_FALSE);
								msfa.setLastUpdateTime(null);
								msiSurveyFeedbackAttachmentDao.updateMsiSurveyFeedbackAttachment(msfa);
							} else {
								msfa = new MsiSurveyFeedbackAttachment();
								msfa.setAttachmentId(UUID.randomUUID().toString());
								msfa.setFeedbackId(feedbackId);
								msfa.setAttachment(image);
								msfa.setClientId(clientId);
								msfa.setCol1(key.toString());
								msfa.setIsDelete(Constants.IS_DELETE_FALSE);
								msiSurveyFeedbackAttachmentDao.insertMsiSurveyFeedbackAttachment(msfa);
							}
						}
					}
				}
			}
		} catch (BusinessException e) {
			i = 0;
			LOG.error("method updateMsiSurveyData error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		return i;
	}

	@Override
	public List<MsiSurveyFeedbackAttachment> getMsiSurveyFeedbackAttachmentList(String feedbackId,Byte attachmentType) throws BusinessException{
		List<MsiSurveyFeedbackAttachment> list = null;
		try {
			if (StringUtil.isNotEmptyString(feedbackId)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("feedbackId", feedbackId);
				params.put("attachmentType", attachmentType);
				list = msiSurveyFeedbackAttachmentDao.getMsiSurveyFeedbackAttachmentList(params);
			}
		} catch (BusinessException e) {
			LOG.error("method getMsiSurveyFeedbackAttachmentList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public void saveMsiSurveyComplain(Integer clientId, Integer clientUserId,SaveMsiSurveyComplainVo saveMsiSurveyComplainVo) throws BusinessException {
		if(saveMsiSurveyComplainVo!=null){
			if(saveMsiSurveyComplainVo.getMsiSurveyComplains()!=null && saveMsiSurveyComplainVo.getMsiSurveyComplains().size()>0){
				for(MsiSurveyComplain msiSurveyComplain : saveMsiSurveyComplainVo.getMsiSurveyComplains()){
					if(StringUtils.isEmpty(msiSurveyComplain.getComplainContent())){
						continue;
					}
					MsiSurveyComplain msiSurveyComplainInfo = msiSurveyComplainService.getEntityByFeedbackIdAndMsiQuestionId(msiSurveyComplain.getFeedbackId(), msiSurveyComplain.getMsiQuestionId());
					if(msiSurveyComplainInfo!=null){
						msiSurveyComplainInfo.setComplainContent(msiSurveyComplain.getComplainContent());
						msiSurveyComplainInfo.setComplainBy(clientUserId);
						msiSurveyComplainInfo.setStatus(Constants.MSI_SURVEY_COMPLAIN);
						msiSurveyComplainService.updateMsiSurveyComplain(msiSurveyComplainInfo);
					}else{
						msiSurveyComplain.setComplainId(UUID.randomUUID().toString());
						msiSurveyComplain.setComplainBy(clientUserId);
						msiSurveyComplain.setStatus(Constants.MSI_SURVEY_COMPLAIN);
						msiSurveyComplain.setClientId(clientId);
						msiSurveyComplainService.insertSelective(msiSurveyComplain);
					}
				}
			}
			/**处理图片**/
			String imageNames = saveMsiSurveyComplainVo.getImageNames();
			/**删除原来的图片**/
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("feedbackId", saveMsiSurveyComplainVo.getFeedbackId());
			params.put("attachmentType", Constants.MSI_SURVEY_COMPLAIN_IMG);
			msiSurveyFeedbackAttachmentDao.deleteByFeedbackId(params);
			Map<String, Object> parameters = new HashMap<String, Object>();
			if(StringUtil.isNotEmptyString(imageNames)){
				for ( String image : imageNames.split(",") ) {
					parameters.put("attachment", image);
					parameters.put("attachmentType",Constants.MSI_SURVEY_COMPLAIN_IMG);
					MsiSurveyFeedbackAttachment msfa = msiSurveyFeedbackAttachmentDao.getMsiSurveyFeedbackAttachmentByParams(parameters);
					if (msfa != null) {
						msfa.setIsDelete(Constants.IS_DELETE_FALSE);
						msfa.setLastUpdateTime(null);
						msiSurveyFeedbackAttachmentDao.updateMsiSurveyFeedbackAttachment(msfa);
					} else {
						msfa = new MsiSurveyFeedbackAttachment();
						msfa.setAttachmentId(UUID.randomUUID().toString());
						msfa.setFeedbackId(saveMsiSurveyComplainVo.getFeedbackId());
						msfa.setAttachment(image);
						msfa.setAttachmentType(Constants.MSI_SURVEY_COMPLAIN_IMG);
						msfa.setClientId(clientId);
						msfa.setIsDelete(Constants.IS_DELETE_FALSE);
						msiSurveyFeedbackAttachmentDao.insertMsiSurveyFeedbackAttachment(msfa);
					}
				}
			}
		}
	}

	@Override
	public List<MsiSurveyFeedback> getMsiSurveyFeedbackByPresentCycle(Integer cycleType,Integer clientId,Integer clientUserId,Integer msiSurveyId,String storeId) {
		Map<String,Object> params = getCycle(cycleType, new Date());
		params.put("clientId", clientId);
		params.put("clientUserId", clientUserId);
		params.put("msiSurveyId", msiSurveyId);
		params.put("storeId", storeId);
		return msiSurveyFeedbackDao.getEntityByCycleDate(params);
	}
	
	@Override
	public int deleteByPrimaryKey(String feedBackId) throws BusinessException {
		return msiSurveyFeedbackDao.deleteByPrimaryKey(feedBackId);
	}

	/** 
	 * 根据Date和任务类型的计算周期
	 */
	@Override
	public Map<String,Object> getCycle(Integer cycleType,Date date){
		Calendar cal = Calendar.getInstance();	     
		cal.setTime(DateUtil.getDayStart(date));
		
        Calendar beginDate=Calendar.getInstance();		 
    	beginDate.setTime(DateUtil.getDayStart(date));
    	
   	 	Calendar endDate=Calendar.getInstance();
   	 	endDate.setTime(DateUtil.getDayStart(date));
 		if(cycleType.intValue() == 2){				 			     
	        int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;   //系统当前日期的索引
	        if (currentDateIndex <= 0)								//日期索引为0时，代表星期日
	        	 currentDateIndex = 7;
	        beginDate.add(Calendar.DATE,-currentDateIndex+1);
    	    endDate.add(Calendar.DATE, 7-currentDateIndex);
		}else if(cycleType.intValue() == 1){				  		         
	         int maxDate = cal.getActualMaximum(Calendar.DATE);
	         beginDate.set(Calendar.DATE, 1);
		 	 endDate.set(Calendar.DATE, maxDate);
		}
		endDate.set(Calendar.DATE, endDate.get(Calendar.DATE)+1); 
		endDate.set(Calendar.SECOND, endDate.get(Calendar.SECOND)-1);     //日期减一秒为 2015-mm-dd:59:59:9
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("beginDate", beginDate.getTime());
		map.put("endDate", endDate.getTime());
		return map;
	}

	@Override
	public List<?> exportMsiSurveyFeedback(ExportMsiSurveyFeedbackVo exportMsiSurveyFeedbackVo) throws BusinessException{
		String msiSurveyIds = msiSurveyFeedbackDao.getMsiSurveyIdsByparams(exportMsiSurveyFeedbackVo.getClientId());
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", exportMsiSurveyFeedbackVo.getClientId());
		params.put("clientUserIds", exportMsiSurveyFeedbackVo.getClientUserIds());
		params.put("structureIds", exportMsiSurveyFeedbackVo.getStructureIds());
		params.put("deptIds", exportMsiSurveyFeedbackVo.getDeptIds());
		params.put("storeNo", exportMsiSurveyFeedbackVo.getStoreNo());
		params.put("storeName", exportMsiSurveyFeedbackVo.getStoreName());
		params.put("promoter", exportMsiSurveyFeedbackVo.getPromoter());
		params.put("provinceId", exportMsiSurveyFeedbackVo.getProvinceId());
		params.put("cityId", exportMsiSurveyFeedbackVo.getCityId());
		params.put("startTime", exportMsiSurveyFeedbackVo.getStartDate());
		params.put("endTime", exportMsiSurveyFeedbackVo.getEndDate());
		params.put("msiSurveyIds", msiSurveyIds);
		return msiSurveyFeedbackDao.exportMsiSurveyFeedback(params);
	}

	@Override
	public List<MsiSurveyFinishCount> getMsiSurveyFeedbackCountByStoreId(Integer clientId, String storeId) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeId", storeId);
		params.put("msiSurveyType", new Integer(6));
		List<MsiSurveyFinishCount> msiSurveyFinishCounts = new ArrayList<MsiSurveyFinishCount>();
		MsiSurveyFinishCount checkSurveyFinishCount = msiSurveyFeedbackDao.getMsiSurveyFeedbackCountByStoreId(params);
		msiSurveyFinishCounts.add(checkSurveyFinishCount);
		params.put("msiSurveyType", new Integer(7));
		MsiSurveyFinishCount maintainSurveyFinishCount = msiSurveyFeedbackDao.getMsiSurveyFeedbackCountByStoreId(params);
		msiSurveyFinishCounts.add(maintainSurveyFinishCount);
		return msiSurveyFinishCounts;
	}

	@Override
	public MsiSurveyFinishCount getMsiSurveyFeedbackCountByStoreIdAnd(Integer clientId, String storeId, Integer surveyType) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeId", storeId);
		params.put("msiSurveyType", surveyType);
		MsiSurveyFinishCount surveyFinishCount = msiSurveyFeedbackDao.getMsiSurveyFeedbackCountByStoreId(params);
		return surveyFinishCount;
	}

	@Override
	public MsiSurveyFeedback findApprovalDataByStoreIdAndDataType(Integer clientId, String storeId, Integer msiSurveyId,byte dataType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeId", storeId);
		params.put("msiSurveyId", msiSurveyId);
		params.put("dataType", dataType);      //审核员数据
		return msiSurveyFeedbackDao.findApprovalDataByStoreIdAndDataType(params);
	}

	@Override
	public MsiSurveyFeedback findMsiSurveyFeedbackByStoreId(Integer clientId,String storeId, Integer msiSurveyId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeId", storeId);
		params.put("msiSurveyId", msiSurveyId);
		return msiSurveyFeedbackDao.findMsiSurveyFeedbackByStoreId(params);
	}

	@Override
	public List<MsiSurveyFeedback> findAppleExamFeedbackByStoreId(Integer clientId,String storeId, Integer msiSurveyId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeId", storeId);
		params.put("msiSurveyId", msiSurveyId);
		return msiSurveyFeedbackDao.findAppleExamFeedbackByStoreId(params);
	}
	
	@Override
	public Integer queryAppleTotalCount(Map<String, Object> params) throws BusinessException{
		int count = 0;
		try {
			if ((params != null) && (params.size() > 0)) {
				count = msiSurveyFeedbackDao.queryAppleTotalCount(params);
			}
		} catch (BusinessException e) {
			LOG.error("method queryMsiSurveyFeedbackCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<MsiSurveyFeedback> findAppleListByParams(Map<String, Object> params) throws BusinessException{
		List<MsiSurveyFeedback> list = null;
		try {
			if ((params != null) && (params.size() > 0)) {
				list = msiSurveyFeedbackDao.findAppleListByParams(params);
			}
		} catch (BusinessException e) {
			LOG.error("method queryMsiSurveyFeedbackList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public void updateMsiSurveyFeedback(MsiSurveyFeedback msiSurveyFeedback) throws BusinessException {
		msiSurveyFeedbackDao.updateMsiSurveyFeedback(msiSurveyFeedback);
	}
	
}
