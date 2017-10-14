package cn.mobilizer.channel.ctTask.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.ctTask.dao.CtTaskDao;
import cn.mobilizer.channel.ctTask.dao.CtTaskDataAttachmentDao;
import cn.mobilizer.channel.ctTask.dao.CtTaskDataDao;
import cn.mobilizer.channel.ctTask.dao.CtTaskDetailDataDao;
import cn.mobilizer.channel.ctTask.po.CtTask;
import cn.mobilizer.channel.ctTask.po.CtTaskData;
import cn.mobilizer.channel.ctTask.po.CtTaskDataAttachment;
import cn.mobilizer.channel.ctTask.po.CtTaskDetailData;
import cn.mobilizer.channel.ctTask.service.CtTaskDataService;
import cn.mobilizer.channel.ctTask.service.CtTaskService;
import cn.mobilizer.channel.ctTask.vo.CtTaskDataSerchVo;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;

@Service
public class CtTaskDataServiceImpl implements CtTaskDataService{

	@Autowired
	private CtTaskDataDao ctTaskDataDao;
	@Autowired
	private CtTaskDetailDataDao ctTaskDetailDataDao;
	@Autowired
	private CtTaskService ctTaskService;
	@Autowired 
	private CtTaskDao ctTaskDao;
	@Autowired
	private StoreService storeService;
	@Autowired
	private CtTaskDataAttachmentDao ctTaskDataAttachmentDao;

	@Override
	public List<CtTaskData> selectByParams(Map<String, Object> param) {
		return ctTaskDataDao.selectByParams(param);
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param) {
		return ctTaskDataDao.selectByParamCount(param);
	}

	@Override
	public String insert(CtTaskData ctTaskData) {		
		return ctTaskDataDao.insert(ctTaskData);
	}
	
	/**
	 *保存周期任务数据 
	 */
	@Override
	public void saveCtTaskData(CtTaskData ctTaskData) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("ctTaskId", ctTaskData.getCtTaskId());
		param.put("popId", ctTaskData.getPopId());
		param.put("clientId", ctTaskData.getClientId());
		param.put("clientUserId", ctTaskData.getClientUserId());
		CtTaskData ctTaskDataInfo = ctTaskDataDao.selectDataByPopIdAndTaskId(param);    //按最后创建时间排序查出最后周期的一条数据
		CtTask ctTask = ctTaskDao.selectByPrimaryKey(ctTaskData.getCtTaskId());		
		ctTaskData.setPopType(ctTask.getPopType());
		if(ctTaskDataInfo!=null){
			ctTaskService.getValidity(ctTask,ctTaskDataInfo.getStartTime());      //计算是否在同一个周期内   是则修改，否则新增
			String ctTaskDataId = null;
			if(!ctTask.getIsSaveSameCycle()){
				ctTaskData.setCtTaskDataId(UUID.randomUUID().toString());		       //不在同一周期则新增一个ctTaskData
				ctTaskDataId = insert(ctTaskData);
			}else{
				ctTaskDataId = ctTaskDataInfo.getCtTaskDataId();
			}
			for(CtTaskDetailData ctTaskDetailData : ctTaskData.getCtTaskDetailDatas()){
				ctTaskDetailData.setValue(ctTaskDetailData.getValue().trim());
				Map<String,Object> detailParam = new HashMap<String, Object>();
				detailParam.put("clientId",ctTaskDataInfo.getClientId());
				detailParam.put("ctTaskDataId",ctTaskDataInfo.getCtTaskDataId());
				detailParam.put("ctTaskParameterId", ctTaskDetailData.getCtTaskParameterId());
				if(!StringUtils.isEmpty(ctTaskDetailData.getTarget1Id())){
					detailParam.put("target1Id", ctTaskDetailData.getTarget1Id());
				}				
				CtTaskDetailData ctTaskDetailDataInfo = ctTaskDetailDataDao.getDetailDataByTarget1AndParamId(detailParam); //按最后修改时间排序查出最后周期的一条数据
				if(ctTaskDetailDataInfo!=null&&ctTask.getIsSaveSameCycle()){        //并且在同一周期内
					ctTaskDetailDataInfo.setValue(ctTaskDetailData.getValue());
					ctTaskDataInfo.setLastUpdateTime(DateTimeUtils.getCurrentTime());					//更新最后修改时间
					ctTaskDataInfo.setLastUpdateBy(ctTaskData.getLastUpdateBy());
					ctTaskDataInfo.setClientUserId(ctTaskData.getClientUserId());
					ctTaskDataInfo.setCreateBy(null);
					ctTaskDataDao.update(ctTaskDataInfo);
					ctTaskDetailDataDao.update(ctTaskDetailDataInfo);
				}else{																										
					ctTaskDetailData.setCtTaskDataId(ctTaskDataId);
					ctTaskDetailData.setCtTaskDetailDataId(UUID.randomUUID().toString());
					ctTaskDetailData.setClientId(ctTaskData.getClientId());
					if(StringUtils.isEmpty(ctTaskDetailData.getTarget1Id())){
						ctTaskDetailData.setTarget1Id(null);
					}
					ctTaskDetailDataDao.insert(ctTaskDetailData);
				}								
			}
				
			//图片附件
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("ctTaskDataId", ctTaskDataId);
			params.put("clientId", ctTaskData.getClientId());
			String attachments = ctTaskDataAttachmentDao.getAttachmentsByCtTaskDataId(params);
			String imageNames = ctTaskData.getImageNames();
			String[] oldAttachments = null;
			String[] newAttachments = null;
			if(StringUtils.isNotEmpty(attachments)){
				oldAttachments = attachments.split(",");
			}
			if(StringUtils.isNotEmpty(imageNames)){
				newAttachments = imageNames.split(",");
			}
			if(oldAttachments == null){
				if(newAttachments != null){
					// 如果以前的Id为空,那么全部为新增
					for(String imgName : newAttachments){
						CtTaskDataAttachment ctTaskDataAttachment = new CtTaskDataAttachment();
						ctTaskDataAttachment.setAttachmentId(UUID.randomUUID().toString());
						ctTaskDataAttachment.setCtTaskDataId(ctTaskDataId);
						ctTaskDataAttachment.setAttachment(imgName);
						ctTaskDataAttachment.setClientId(ctTaskData.getClientId());
						Map<String,Object> parameter = new HashMap<String,Object>();
						parameter.put("ctTaskDataId", ctTaskData.getCtTaskDataId());
						parameter.put("clientId", ctTaskData.getClientId());
						parameter.put("attachment",imgName);
						parameter.put("isDelete",Constants.IS_DELETE_TRUE);
						List<CtTaskDataAttachment> ctTaskDataAttachments = ctTaskDataAttachmentDao.getEntityByCtTaskDataId(parameter);
						if(ctTaskDataAttachments!=null && ctTaskDataAttachments.size()>0){
							ctTaskDataAttachmentDao.updateDelByCtTaskDataId(parameter);
						}else{
							ctTaskDataAttachmentDao.insertSelective(ctTaskDataAttachment);
						}
					}
				}
			}else{
				if(newAttachments == null){
					// 如果新的Id为空,那么全部为删除		
					Map<String,Object> parameter = new HashMap<String, Object>();
					parameter.put("ctTaskDataId", ctTaskDataId);
					parameter.put("clientId", ctTaskData.getClientId());
					parameter.put("attachments", attachments);
					ctTaskDataAttachmentDao.deleteAttachmentsByImageNames(parameter);
				}else{
					/** 获取old中存在而new中不存在的人员，删除 **/
					String delAttachments = ArrayUtil.arraySubtract2Str(oldAttachments, newAttachments);				
					delAttachments = StringUtil.removeStrComma(delAttachments);
					Map<String,Object> parameter = new HashMap<String, Object>();
					parameter.put("ctTaskDataId", ctTaskDataId);
					parameter.put("clientId", ctTaskData.getClientId());
					parameter.put("attachments", delAttachments);
					ctTaskDataAttachmentDao.deleteAttachmentsByImageNames(parameter);
					
					/** 获取new中存在而old中不存在的人员，新增 **/
					String[] addAttachments = ArrayUtil.arraySubtract(newAttachments, oldAttachments);
					if(addAttachments!=null){
						for(String imgName : addAttachments){
							CtTaskDataAttachment ctTaskDataAttachment = new CtTaskDataAttachment();
							ctTaskDataAttachment.setAttachmentId(UUID.randomUUID().toString());
							ctTaskDataAttachment.setCtTaskDataId(ctTaskDataId);
							ctTaskDataAttachment.setAttachment(imgName);
							ctTaskDataAttachment.setClientId(ctTaskData.getClientId());
							Map<String,Object> parameter2 = new HashMap<String,Object>();
							parameter2.put("ctTaskDataId", ctTaskData.getCtTaskDataId());
							parameter2.put("clientId", ctTaskData.getClientId());
							parameter2.put("attachment",imgName);
							parameter2.put("isDelete",Constants.IS_DELETE_TRUE);
							List<CtTaskDataAttachment> ctTaskDataAttachments = ctTaskDataAttachmentDao.getEntityByCtTaskDataId(parameter2);
							if(ctTaskDataAttachments!=null && ctTaskDataAttachments.size()>0){
								ctTaskDataAttachmentDao.updateDelByCtTaskDataId(parameter2);
							}else{
								ctTaskDataAttachmentDao.insertSelective(ctTaskDataAttachment);
							}
						}
					}
				}
			}
		}else{
			ctTaskData.setCtTaskDataId(UUID.randomUUID().toString());
			insert(ctTaskData);
			for(CtTaskDetailData ctTaskDetailData : ctTaskData.getCtTaskDetailDatas()){
				ctTaskDetailData.setValue(ctTaskDetailData.getValue().trim());
				ctTaskDetailData.setCtTaskDetailDataId(UUID.randomUUID().toString());
				ctTaskDetailData.setCtTaskDataId(ctTaskData.getCtTaskDataId());
				ctTaskDetailData.setClientId(ctTaskData.getClientId());
				if(StringUtils.isEmpty(ctTaskDetailData.getTarget1Id())){
					ctTaskDetailData.setTarget1Id(null);
				}
				ctTaskDetailDataDao.insert(ctTaskDetailData);
			}
			//图片附件
			if(StringUtils.isNotEmpty(ctTaskData.getImageNames())){
				for(String imgName : ctTaskData.getImageNames().split(",")){
					CtTaskDataAttachment ctTaskDataAttachment = new CtTaskDataAttachment();
					ctTaskDataAttachment.setAttachmentId(UUID.randomUUID().toString());
					ctTaskDataAttachment.setCtTaskDataId(ctTaskData.getCtTaskDataId());
					ctTaskDataAttachment.setAttachment(imgName);
					ctTaskDataAttachment.setClientId(ctTaskData.getClientId());
					ctTaskDataAttachmentDao.insertSelective(ctTaskDataAttachment);
				}
			}
		}
	}

	@Override
	public void updateCtTaskData(CtTaskData ctTaskData) {
		Date visitDate = null;
		try {
			visitDate = DateUtil.getDateByStr(ctTaskData.getVisitDate(), DateTimeUtils.yyyyMMdd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//当前选择的访问时间拼接时分秒
		Calendar cal = Calendar.getInstance();
        int hour=cal.get(Calendar.HOUR_OF_DAY); //小时
        int minute=cal.get(Calendar.MINUTE);//分           
        int second=cal.get(Calendar.SECOND);//秒
        Calendar visitCalendar = Calendar.getInstance();
        visitCalendar.setTime(visitDate);
        visitCalendar.set(Calendar.HOUR_OF_DAY, hour);
        visitCalendar.set(Calendar.MINUTE, minute);
        visitCalendar.set(Calendar.SECOND, second);
        int year = visitCalendar.get(Calendar.YEAR);   //年
		int month = visitCalendar.get(Calendar.MONTH);  //月
		int date = visitCalendar.get(Calendar.DATE);		//日
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(ctTaskData.getStartTime());
        startCalendar.set(Calendar.YEAR, year);
        startCalendar.set(Calendar.MONTH, month);
        startCalendar.set(Calendar.DATE, date);
		Store store = storeService.selectByPrimaryKey(ctTaskData.getPopId());
		if(store!=null){
			ctTaskData.setPopType(store.getStoreType().byteValue());
		}
		for(CtTaskDetailData ctTaskDetailData : ctTaskData.getCtTaskDetailDatas()){
			ctTaskDetailData.setCreateTime(visitCalendar.getTime());
			ctTaskDetailData.setLastUpdateTime(visitCalendar.getTime());
			ctTaskDetailData.setValue(ctTaskDetailData.getValue().trim());
			Map<String,Object> detailParam = new HashMap<String, Object>();
			detailParam.put("clientId",ctTaskData.getClientId());
			detailParam.put("ctTaskDataId",ctTaskData.getCtTaskDataId());
			detailParam.put("ctTaskParameterId", ctTaskDetailData.getCtTaskParameterId());
			if(!StringUtils.isEmpty(ctTaskDetailData.getTarget1Id())){
				detailParam.put("target1Id", ctTaskDetailData.getTarget1Id());
			}				
			CtTaskDetailData ctTaskDetailDataInfo = ctTaskDetailDataDao.getDetailDataByTarget1AndParamId(detailParam);  
			if(ctTaskDetailDataInfo!=null){    
				List<CtTaskDetailData> ctTaskDetailDataInfos = ctTaskDetailDataDao.findDetailDatasByTarget1AndParamid(detailParam);
				for(CtTaskDetailData detailData : ctTaskDetailDataInfos){
					detailData.setLastUpdateTime(new Date());
					detailData.setValue(ctTaskDetailData.getValue());
					ctTaskDetailDataDao.update(detailData);
				}
			}else{																										
				ctTaskDetailData.setCtTaskDataId(ctTaskData.getCtTaskDataId());
				ctTaskDetailData.setCtTaskDetailDataId(UUID.randomUUID().toString());
				ctTaskDetailData.setClientId(ctTaskData.getClientId());
				if(StringUtils.isEmpty(ctTaskDetailData.getTarget1Id())){
					ctTaskDetailData.setTarget1Id(null);
				}
				ctTaskDetailDataDao.insertSelective(ctTaskDetailData);
			}	
		}
		ctTaskData.setStartTime(startCalendar.getTime());
		ctTaskData.setEndTime(visitCalendar.getTime());
		ctTaskData.setClientUserId(null); 				//修改时不需要更新人员字段			
		ctTaskDataDao.update(ctTaskData);
		//图片附件
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ctTaskDataId", ctTaskData.getCtTaskDataId());
		params.put("clientId", ctTaskData.getClientId());
		String attachments = ctTaskDataAttachmentDao.getAttachmentsByCtTaskDataId(params);
		String imageNames = ctTaskData.getImageNames();
		String[] oldAttachments = null;
		String[] newAttachments = null;
		if(StringUtils.isNotEmpty(attachments)){
			oldAttachments = attachments.split(",");
		}
		if(StringUtils.isNotEmpty(imageNames)){
			newAttachments = imageNames.split(",");
		}
		if(oldAttachments == null){
			if(newAttachments != null){
				// 如果以前的Id为空,那么全部为新增
				for(String imgName : newAttachments){
					CtTaskDataAttachment ctTaskDataAttachment = new CtTaskDataAttachment();
					ctTaskDataAttachment.setAttachmentId(UUID.randomUUID().toString());
					ctTaskDataAttachment.setCtTaskDataId(ctTaskData.getCtTaskDataId());
					ctTaskDataAttachment.setAttachment(imgName);
					ctTaskDataAttachment.setClientId(ctTaskData.getClientId());
					ctTaskDataAttachmentDao.insertSelective(ctTaskDataAttachment);
				}
			}
		}else{
			if(newAttachments == null){
				// 如果新的Id为空,那么全部为删除		
				Map<String,Object> parameter = new HashMap<String, Object>();
				parameter.put("ctTaskDataId", ctTaskData.getCtTaskDataId());
				parameter.put("clientId", ctTaskData.getClientId());
				parameter.put("attachments", attachments);
				ctTaskDataAttachmentDao.deleteAttachmentsByImageNames(parameter);
			}else{
				/** 获取old中存在而new中不存在的人员，删除 **/
				String delAttachments = ArrayUtil.arraySubtract2Str(oldAttachments, newAttachments);				
				delAttachments = StringUtil.removeStrComma(delAttachments);
				Map<String,Object> parameter = new HashMap<String, Object>();
				parameter.put("ctTaskDataId", ctTaskData.getCtTaskDataId());
				parameter.put("clientId", ctTaskData.getClientId());
				parameter.put("attachments", delAttachments);
				ctTaskDataAttachmentDao.deleteAttachmentsByImageNames(parameter);
				
				/** 获取new中存在而old中不存在的人员，新增 **/
				String[] addAttachments = ArrayUtil.arraySubtract(newAttachments, oldAttachments);
				if(addAttachments!=null){
					for(String imgName : addAttachments){
						CtTaskDataAttachment ctTaskDataAttachment = new CtTaskDataAttachment();
						ctTaskDataAttachment.setAttachmentId(UUID.randomUUID().toString());
						ctTaskDataAttachment.setCtTaskDataId(ctTaskData.getCtTaskDataId());
						ctTaskDataAttachment.setAttachment(imgName);
						ctTaskDataAttachment.setClientId(ctTaskData.getClientId());
						Map<String,Object> parameter2 = new HashMap<String,Object>();
						parameter2.put("ctTaskDataId", ctTaskData.getCtTaskDataId());
						parameter2.put("clientId", ctTaskData.getClientId());
						parameter2.put("attachment",imgName);
						parameter2.put("isDelete",Constants.IS_DELETE_TRUE);
						List<CtTaskDataAttachment> ctTaskDataAttachments = ctTaskDataAttachmentDao.getEntityByCtTaskDataId(parameter2);
						if(ctTaskDataAttachments!=null && ctTaskDataAttachments.size()>0){
							ctTaskDataAttachmentDao.updateDelByCtTaskDataId(parameter2);
						}else{
							ctTaskDataAttachmentDao.insertSelective(ctTaskDataAttachment);
						}
					}
				}
			}
		}
	}

	@Override
	public CtTaskData getCtTaskDataById(String id) {
		CtTaskData ctTaskData = ctTaskDataDao.selectByPrimaryKey(id);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ctTaskDataId", ctTaskData.getCtTaskDataId());
		List<CtTaskDetailData> ctTaskDetailDatas = ctTaskDetailDataDao.selectByParams(param);
		ctTaskData.setCtTaskDetailDatas(ctTaskDetailDatas);
		param.put("clientId", ctTaskData.getClientId());
		param.put("isDelete",Constants.IS_DELETE_FALSE);
		List<CtTaskDataAttachment> ctTaskDataAttachments = ctTaskDataAttachmentDao.getEntityByCtTaskDataId(param);
		ctTaskData.setCtTaskDataAttachments(ctTaskDataAttachments);
		return ctTaskData;
	}

	/**
	 * 根据门店和任务获取数据
	 */
	@Override
	public CtTaskData selectDataByPopIdAndTaskId(CtTaskDataSerchVo ctTaskDataSerchVo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientUserId", ctTaskDataSerchVo.getClientUserId());
		param.put("popId", ctTaskDataSerchVo.getPopId());
		param.put("ctTaskId", ctTaskDataSerchVo.getCtTaskId());
		param.put("brandId", ctTaskDataSerchVo.getBrandId()==null?null:ctTaskDataSerchVo.getBrandId());
		param.put("categoryId", ctTaskDataSerchVo.getCategoryId()==null?null:ctTaskDataSerchVo.getCategoryId());
		param.put("skuSeriesId", ctTaskDataSerchVo.getSkuSeriesId()==null?null:ctTaskDataSerchVo.getSkuSeriesId());
		param.put("skuTypeId", ctTaskDataSerchVo.getSkuTypeId()==null?null:ctTaskDataSerchVo.getSkuTypeId());
		CtTask ctTask = ctTaskDao.selectByPrimaryKey(ctTaskDataSerchVo.getCtTaskId());
		param.put("taskType", ctTask.getTaskType());      //对象类型：产品相关、品牌相关....
		ctTaskService.getTaskCycle(ctTask,new Date());      // 根据当前时间按不同的周期类型获取当前周期的提报有效时间
		param.put("beginDate",ctTask.getValidFromDate());
		param.put("endDate", ctTask.getValidEndDate());
		CtTaskData ctTaskData = ctTaskDataDao.selectDataByPopIdAndTaskId(param);	
		if(ctTaskData!=null){
			Map<String,Object> param2 = new HashMap<String,Object>();
			param2.put("ctTaskDataId", ctTaskData.getCtTaskDataId());
			List<CtTaskDetailData> ctTaskDetailDatas = ctTaskDetailDataDao.selectByParams(param2);
			ctTaskData.setCtTaskDetailDatas(ctTaskDetailDatas);
			//图片附件
			param2.put("clientId", ctTaskData.getClientId());
			param2.put("isDelete",Constants.IS_DELETE_FALSE);
			List<CtTaskDataAttachment> ctTaskDataAttachments = ctTaskDataAttachmentDao.getEntityByCtTaskDataId(param2);
			ctTaskData.setCtTaskDataAttachments(ctTaskDataAttachments);
		}
		return ctTaskData;
	}

	@Override
	public int deleteCtTaskData(String ctTaskDataId) {
		return ctTaskDataDao.deleteByPrimaryKey(ctTaskDataId);
	}

	@Override
	public CtTaskData getCtTaskDataByStartTime(Integer clientId,Integer ctTaskId, String popId, Date beginDate, Date endDate) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientId",clientId);
		param.put("ctTaskId", ctTaskId);
		param.put("popId", popId);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		return ctTaskDataDao.selectDataByPopIdAndTaskId(param);
	}

	@Override
	public void saveColgateCtTaskData(CtTaskData ctTaskData,Date visitDate) {
		//当前选择的访问时间拼接时分秒
		Calendar cal = Calendar.getInstance();
        int hour=cal.get(Calendar.HOUR_OF_DAY); //小时
        int minute=cal.get(Calendar.MINUTE);//分           
        int second=cal.get(Calendar.SECOND);//秒
        Calendar visitCalendar = Calendar.getInstance();
        visitCalendar.setTime(visitDate);
        visitCalendar.set(Calendar.HOUR_OF_DAY, hour);
        visitCalendar.set(Calendar.MINUTE, minute);
        visitCalendar.set(Calendar.SECOND, second);
    	int year = visitCalendar.get(Calendar.YEAR);   //年
		int month = visitCalendar.get(Calendar.MONTH);  //月
		int date = visitCalendar.get(Calendar.DATE);		//日
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(ctTaskData.getStartTime());
        startCalendar.set(Calendar.YEAR, year);
        startCalendar.set(Calendar.MONTH, month);
        startCalendar.set(Calendar.DATE, date);
		Store store = storeService.selectByPrimaryKey(ctTaskData.getPopId());
		if(store!=null){
			ctTaskData.setPopType(store.getStoreType().byteValue());
		}
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("ctTaskId", ctTaskData.getCtTaskId());
		param.put("popId", ctTaskData.getPopId());
		param.put("clientId", ctTaskData.getClientId());
		param.put("clientUserId", ctTaskData.getClientUserId());
		ctTaskData.setCtTaskDataId(UUID.randomUUID().toString());	
		ctTaskData.setStartTime(startCalendar.getTime());
		ctTaskData.setEndTime(visitCalendar.getTime());
		insert(ctTaskData);              //高露洁用户新增以往周期的数据，所有创建时间、修改时间不能以数据库自动生成
		for(CtTaskDetailData ctTaskDetailData : ctTaskData.getCtTaskDetailDatas()){
			ctTaskDetailData.setValue(ctTaskDetailData.getValue().trim());
			ctTaskDetailData.setCtTaskDetailDataId(UUID.randomUUID().toString());
			ctTaskDetailData.setCtTaskDataId(ctTaskData.getCtTaskDataId());
			ctTaskDetailData.setClientId(ctTaskData.getClientId());
			if(StringUtils.isEmpty(ctTaskDetailData.getTarget1Id())){
				ctTaskDetailData.setTarget1Id(null);
			}
		}
		ctTaskDetailDataDao.batchCtTaskDetailDatas(ctTaskData.getCtTaskDetailDatas());
		//图片附件
		if(StringUtils.isNotEmpty(ctTaskData.getImageNames())){
			for(String imgName : ctTaskData.getImageNames().split(",")){
				CtTaskDataAttachment ctTaskDataAttachment = new CtTaskDataAttachment();
				ctTaskDataAttachment.setAttachmentId(UUID.randomUUID().toString());
				ctTaskDataAttachment.setCtTaskDataId(ctTaskData.getCtTaskDataId());
				ctTaskDataAttachment.setAttachment(imgName);
				ctTaskDataAttachment.setClientId(ctTaskData.getClientId());
				ctTaskDataAttachmentDao.insertSelective(ctTaskDataAttachment);
			}
		}
	}
}
