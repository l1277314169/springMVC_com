package cn.mobilizer.channel.survey.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.utils.json.JsonTool;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.dao.SurveyBlockDao;
import cn.mobilizer.channel.survey.dao.SurveyDao;
import cn.mobilizer.channel.survey.dao.SurveyFeedbackDao;
import cn.mobilizer.channel.survey.dao.SurveyFeedbackDetailDao;
import cn.mobilizer.channel.survey.dao.SurveyObjectDao;
import cn.mobilizer.channel.survey.dao.SurveyObjectGroupDao;
import cn.mobilizer.channel.survey.dao.SurveyParameterDao;
import cn.mobilizer.channel.survey.dao.SurveySubDao;
import cn.mobilizer.channel.survey.po.Survey;
import cn.mobilizer.channel.survey.po.SurveyBlock;
import cn.mobilizer.channel.survey.po.SurveyFeedback;
import cn.mobilizer.channel.survey.po.SurveyFeedbackDetail;
import cn.mobilizer.channel.survey.po.SurveyObject;
import cn.mobilizer.channel.survey.po.SurveyParameter;
import cn.mobilizer.channel.survey.po.SurveySub;
import cn.mobilizer.channel.survey.service.SurveyService;
import cn.mobilizer.channel.survey.vo.CollectionGroup;
import cn.mobilizer.channel.survey.vo.CollectionGroup.GroupBy;
import cn.mobilizer.channel.survey.vo.Head;
import cn.mobilizer.channel.survey.vo.SurveyExportVo;
import cn.mobilizer.channel.survey.vo.SurveyImageVo;
import cn.mobilizer.channel.survey.vo.SurveyListVo;
import cn.mobilizer.channel.survey.vo.SurveyVo;
import cn.mobilizer.channel.survey.vo.TheadCols;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;

@Service
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private SurveyDao surveyDao;
	@Autowired
	private SurveyParameterDao surveyParameterDao;
	@Autowired
	private SurveySubDao surveySubDao;
	@Autowired
	private SurveyObjectDao surveyObjectDao;
	@Autowired
	private SurveyFeedbackDao surveyFeedbackDao;
	@Autowired
	private SurveyBlockDao surveyBlockDao;
	@Autowired
	private SurveyFeedbackDetailDao surveyFeedbackDetailDao;
	@Autowired
	private SurveyObjectGroupDao surveyObjectGroupDao;
	@Autowired
	private StoreDao storeDao;
	 
	@Override
	public List<SurveyListVo> selectSurveyListVo(Map<String, Object> parameters) throws BusinessException{
		
		return surveyDao.selectSurveyListVo(parameters);
	}

	@Override
	public Integer selectAllItems(Map<String, Object> parameters) throws BusinessException {
		
		return surveyDao.selectAllItems(parameters);
	}

	@Override
	public List<SurveyParameter> selectBysubSurveyId(Map<String, Object> params) throws BusinessException {
		
		return surveyParameterDao.selectBysubSurveyId(params);
	}

	@SuppressWarnings("unchecked")
	public List<SurveyExportVo> getSurveyExportList(Integer surveyId,String feedbackId,Integer clientId) throws Exception{
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("surveyId", surveyId);
		params.put("clientId", clientId);
		List<SurveySub> subList = surveySubDao.getSurveySubBySurveyId(params);
		List<SurveyExportVo> exportVos = new ArrayList<SurveyExportVo>();
		
		for (SurveySub surveySub : subList) {
			
			/*if(surveySub.getSubSurveyId() != 11){
				continue;
			}*/
			
			SurveyVo surveyVo = new SurveyVo();
			surveyVo.setSurveySubId(surveySub.getSubSurveyId());
			surveyVo.setSurveySubName(surveySub.getSubSurveyTopic());
			surveyVo.setSubSurveyType(surveySub.getSubSurveyType());
			params.put("subSurveyId", surveySub.getSubSurveyId());
			
			List<SurveyParameter> parameters = surveyParameterDao.selectBysubSurveyId(params);
			
			String headJson = surveySub.getTheadCols();
			
			List<TheadCols> theadColsList = new ArrayList<TheadCols>();
			if(!StringUtil.isEmptyString(headJson)){
				theadColsList = (List<TheadCols>) JsonTool.transToList(headJson, TheadCols.class);
			}
			List<String> heads = new ArrayList<String>();
			for (TheadCols theadCols : theadColsList) {
				heads.add(theadCols.getColName());
			}
			for (SurveyParameter surveyParameter : parameters) {
				heads.add(surveyParameter.getParameterName());
			}
			
			SurveyExportVo surveyExportVo = new SurveyExportVo();
			surveyExportVo.setHeads(heads);
			surveyExportVo.setSheetName(surveySub.getSubSurveyTopic());
			
			List<SurveyObject> surveyObjects = surveyObjectDao.selectSurveyObjectList(params);
			
			params.put("feedbackId", feedbackId);
			List<SurveyFeedbackDetail> detaisList = surveyFeedbackDetailDao.selectSurveyFeedbackDetailList(params);
			Map<String, SurveyFeedbackDetail> detailMaps = this.getMapFeedbackDetail(detaisList);
			
			List<List<String>> dataList = new ArrayList<List<String>>();
			for (SurveyObject object : surveyObjects) {
				List<String> datas = new ArrayList<String>();
				for (TheadCols theadCols : theadColsList) {
					if(theadCols.getForeignCol().equals("objectNo")){
						datas.add(object.getObjectNo());
					}else if(theadCols.getForeignCol().equals("objectName")){
						datas.add(object.getObjectName());
					}
				}
				for (SurveyParameter surveyParameter : parameters) {
					String key = object.getObjectId()+"@"+surveyParameter.getSurveyParameterId();
					if(detailMaps.containsKey(key)){
						SurveyFeedbackDetail detail = detailMaps.get(key);
						//如果为图片，需要设置图片的地址
						if(this.isImages(detail.getValue())){
							String[] temp = detail.getValue().split(",");
							StringBuffer buffer = new StringBuffer();
							for (String img : temp) {
								buffer.append("http://channelplus.cn/uploadfiles/"+clientId+"/web/"+img+",");
							}
							if(buffer.toString().endsWith(",")){
								buffer = buffer.deleteCharAt(buffer.length()-1);
							}
							datas.add(buffer.toString());
						}else{
							datas.add(detail.getValue());
						}
					}else{
						datas.add("");
					}
				}
				dataList.add(datas);
			}
			surveyExportVo.setDataList(dataList);
			exportVos.add(surveyExportVo);
		}
		return exportVos;
	}
	
	
	private Map<String, SurveyFeedbackDetail> getMapFeedbackDetail(List<SurveyFeedbackDetail> details){
		Map<String, SurveyFeedbackDetail> detailMaps = new HashMap<String, SurveyFeedbackDetail>();
		for (SurveyFeedbackDetail surveyFeedbackDetail : details) {
			detailMaps.put(surveyFeedbackDetail.getObjectId()+"@"+surveyFeedbackDetail.getSurveyParameterId(), surveyFeedbackDetail);
		}
		return detailMaps;
	}
	
	
	private boolean isImages(String value){
		value = value.toLowerCase();
		if(value.endsWith(".jpg") || value.endsWith(".png")){
			return true;
		}else{
			return false;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SurveyVo> getSurveyVos(Map<String, Object> params) throws Exception {
		//SurveyBlock surveyBlock = this.selectBySurveyBlockPrimaryKey(params);
		
		List<SurveySub> subList = surveySubDao.getSurveySubByBlockId(params);
		List<SurveyVo> vos = new ArrayList<SurveyVo>();
		
		for (SurveySub surveySub : subList) {
			SurveyVo surveyVo = new SurveyVo();
			surveyVo.setSurveySubId(surveySub.getSubSurveyId());
			surveyVo.setSurveySubName(surveySub.getSubSurveyTopic());
			surveyVo.setSubSurveyType(surveySub.getSubSurveyType());
			params.put("subSurveyId", surveySub.getSubSurveyId());
			
			List<SurveyParameter> parameters = surveyParameterDao.selectBysubSurveyId(params);
			String headJson = surveySub.getTheadCols();
			
			List<TheadCols> theadColsList = new ArrayList<TheadCols>();
			if(!StringUtil.isEmptyString(headJson)){
				theadColsList = (List<TheadCols>) JsonTool.transToList(headJson, TheadCols.class);
			}
			Head heads = new Head();
			heads.setParameterList(parameters);
			heads.setTheadColsList(theadColsList);
			surveyVo.setHead(heads);
			
			TreeMap<String,List<SurveyObject>> objectMap = this.selectSurveyObjectVoList(params);
			surveyVo.setSurveyObjectVos(objectMap);
			
			List<SurveyObject> surveyObjects = surveyObjectDao.selectSurveyObject(params);
			surveyVo.setSurveyObjects(surveyObjects);
			
			vos.add(surveyVo);
		}
		return vos;
	}
	
	/**
	 * 获取参数
	 * @param params
	 * @return
	 */
	public Map<Integer, Integer> getSurveyParameterBySurveyId(Map<String, Object> params){
		List<SurveyParameter> parameters = surveyParameterDao.selectBysubSurveyId(params);
		Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
		for (SurveyParameter surveyParameter : parameters) {
			maps.put(surveyParameter.getSurveyParameterId(), surveyParameter.getControlType());
		}
		return maps;
	}
	
	
	private TreeMap<String,List<SurveyObject>> selectSurveyObjectVoList(Map<String, Object> params){
		List<SurveyObject> surveyObjects = surveyObjectDao.selectObjectBySubSurveyId(params);
		TreeMap<String,List<SurveyObject>> objectMap = CollectionGroup.group(surveyObjects, new GroupBy<String>() {
            @Override
            public String groupby(Object obj) {
            	SurveyObject d = (SurveyObject)obj;
                return d.getGroupName()+"@"+d.getParentId()+"@"+d.getGrade()+"@"+d.getSequence();
            }
        });
		return objectMap;
	}

	@Override
	public List<Survey> getSurveyByName(Map<String, Object> params) throws BusinessException {
		
		return surveyDao.getSurveyByName(params);
	}

	@Override
	public Survey getSurvey(Map<String, Object> params) throws BusinessException {
		
		return surveyDao.getSurvey(params);
	}

	@Override
	public void saveSurveyFeedback(SurveyFeedback surveyFeedback) throws BusinessException {
		surveyFeedbackDao.insert(surveyFeedback);
	}

	@Override
	public List<SurveyBlock> selectBySurveyId(Map<String, Object> params) throws BusinessException {
		
		return surveyBlockDao.selectBySurveyId(params);
	}

	@Override
	public SurveyBlock selectBySurveyBlockPrimaryKey(Map<String, Object> params) throws BusinessException {
		
		return surveyBlockDao.selectByPrimaryKey(params);
	}

	@Override
	public Integer insertSurveyFeedbackDetail(List<SurveyFeedbackDetail> details,String feedbackId,Integer blockId,Integer clientId) throws BusinessException {
		//更新问卷最后修改时间
		SurveyFeedback surveyFeedback = new SurveyFeedback();
		surveyFeedback.setFeedbackId(feedbackId);
		surveyFeedback.setLastUpdateTime(DateTimeUtils.getCurrentTime());
		surveyFeedbackDao.updateByPrimaryKeySelective(surveyFeedback);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("blockId", blockId);
		params.put("clientId",clientId);
		List<String> subs = surveySubDao.getSurveySubIdByBlockId(params);
		params.put("feedbackId", feedbackId);
		params.put("subs", subs);
		surveyFeedbackDetailDao.deleteSurveyFeedbackDetail(params);
		Integer item = 0;
		if(null!=details && !details.isEmpty()){
			item = surveyFeedbackDetailDao.batchInsert(details);
		}
		return item;
	}

	@Override
	public Integer insertAppleSurveyFeedbackDetail(List<SurveyFeedbackDetail> details, String feedbackId,Integer blockId,Integer clientId) throws BusinessException {
		//更新问卷最后修改时间
		SurveyFeedback surveyFeedback = new SurveyFeedback();
		surveyFeedback.setFeedbackId(feedbackId);
		surveyFeedback.setLastUpdateTime(DateTimeUtils.getCurrentTime());
		surveyFeedback.setStatus(Constants.APPLE_SURVEY_STATUS_SAVE);
		surveyFeedbackDao.updateByPrimaryKeySelective(surveyFeedback);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("blockId", blockId);
		params.put("clientId",clientId);
		List<String> subs = surveySubDao.getSurveySubIdByBlockId(params);
		params.put("feedbackId", feedbackId);
		params.put("subs", subs);
		surveyFeedbackDetailDao.deleteSurveyFeedbackDetail(params);
		Integer item = 0;
		if(null!=details && !details.isEmpty()){
			item = surveyFeedbackDetailDao.batchInsert(details);
		}
		return item;
	}

	@Override
	public List<SurveyFeedbackDetail> getSurveyFeedbackDetail(Map<String, Object> parameters) throws BusinessException {
		
		return surveyFeedbackDetailDao.getSurveyFeedbackDetail(parameters);
	}

	@Override
	public void deleteSurvey(String feedbackId) throws BusinessException {
		SurveyFeedback surveyFeedback = new SurveyFeedback();
		surveyFeedback.setFeedbackId(feedbackId);
		surveyFeedback.setIsDelete(Constants.IS_DELETE_TRUE);
		surveyFeedbackDao.updateByPrimaryKeySelective(surveyFeedback);
		surveyFeedbackDetailDao.deleteByFeedbackId(feedbackId);
	}

	@Override
	public Survey getNewSurvey(Integer clientId) throws BusinessException {
		
		return surveyDao.getNewSurvey(clientId);
	}

	@Override
	public boolean getSurveyCycle(Map<String, Object> params) throws BusinessException {
		Integer items = surveyDao.getSurveyCycle(params);
		if(null==items || items>0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<SurveyImageVo> selectSurveyImages(Map<String, Object> params) throws BusinessException {
		List<SurveyImageVo> images = surveyDao.selectSurveyImages(params);
		return images;
	}

	@Override
	public void saveColgateData(List<SurveyFeedbackDetail> Datablock3,List<SurveyFeedbackDetail> Datablock4,List<SurveyFeedbackDetail> Datablock5,List<SurveyFeedbackDetail> Datablock6, String feedbackId,Integer clientId,String storeNo,String visitor,Date feedbackDate,Integer surveyId,Integer clientUserId) {
		SurveyFeedback surveyFeedback = new SurveyFeedback();
		surveyFeedback.setFeedbackId(feedbackId);
		surveyFeedback.setClientId(clientId);
		surveyFeedback.setSubmitBy(clientUserId);
		surveyFeedback.setClientUserId(clientUserId);
		surveyFeedback.setFeedbackDate(feedbackDate);
		surveyFeedback.setVisitor(visitor);
		surveyFeedback.setSurveyId(surveyId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeNo", storeNo);
		params.put("isDelete",Constants.IS_DELETE_FALSE);
		List<Store> stores = storeDao.findStoreByStoreNo(params);
		Store store = null;
		if(stores != null && stores.size() > 0) {
			store = stores.get(0);
		}
		surveyFeedback.setPopId(store.getStoreId());
		surveyFeedback.setCreateTime(DateTimeUtils.getCurrentTime());
		surveyFeedback.setSubmitTime(surveyFeedback.getCreateTime());
		surveyFeedback.setLastUpdateTime(surveyFeedback.getCreateTime());
		surveyFeedback.setIsDelete(Constants.IS_DELETE_FALSE);
		saveSurveyFeedback(surveyFeedback);
		
		insertSurveyFeedbackDetail(Datablock3,feedbackId,3,clientId);
		insertSurveyFeedbackDetail(Datablock4,feedbackId,4,clientId);
		insertSurveyFeedbackDetail(Datablock5,feedbackId,5,clientId);
		insertSurveyFeedbackDetail(Datablock6,feedbackId,6,clientId);
	}
}
