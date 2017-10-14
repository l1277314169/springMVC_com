package cn.mobilizer.channel.ctTask.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.ctTask.po.CtTaskData;
import cn.mobilizer.channel.ctTask.service.CtTaskDataService;
import cn.mobilizer.channel.ctTask.vo.CtTaskDataSerchVo;
import cn.mobilizer.channel.util.DateTimeUtils;

/**
 * @author LiuYong
 * @date 2015年5月29日 上午1:13:51
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ctTaskData")
public class CtTaskDataController extends BaseActionSupport{
	
	private static final long serialVersionUID = 6021198589807220390L;
	@Autowired
	private CtTaskDataService ctTaskDataService;
	
	/**
	 * 保存周期任务的数据
	 * @param model
	 * @param ctTaskData
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="saveCtTaskData")
	@ResponseBody
	public Object saveCtTaskParameter(Model model,@RequestBody CtTaskData ctTaskData) throws ParseException{
		Integer clientId = getClientId();
		Integer clientUserId = getCurrentUserId();
		ctTaskData.setClientId(clientId);		
		ctTaskData.setEndTime(DateTimeUtils.getCurrentTime());
		if(clientId.intValue()==13){
			ctTaskData.setLastUpdateBy(clientUserId);
			ctTaskData.setClientUserId(clientUserId);
			if(StringUtils.isNotEmpty(ctTaskData.getCtTaskDataId())){
				ctTaskDataService.updateCtTaskData(ctTaskData);
			}else{
				ctTaskDataService.saveColgateCtTaskData(ctTaskData, DateUtil.getDateByStr(ctTaskData.getVisitDate(), DateTimeUtils.yyyyMMdd));
			}
		}else{
			if(clientId!=7){
				ctTaskData.setLastUpdateBy(clientUserId);
				ctTaskData.setClientUserId(clientUserId);
			}else{
				ctTaskData.setCreateBy(clientUserId);
				ctTaskData.setLastUpdateBy(ctTaskData.getClientUserId());
			}
			ctTaskDataService.saveCtTaskData(ctTaskData);
		}
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	/**
	 * 高露洁需求：新增修改时如果
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="getCtTaskDataByStartTime")
	@ResponseBody
	public Map getCtTaskDataByStartTime(Model model,Integer ctTaskId,String popId,String visitDateStr) throws ParseException{
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(visitDateStr)){
			Date visitDate = DateUtil.getDateByStr(visitDateStr, DateTimeUtils.yyyyMMdd);
			Date beginDate = DateUtil.getBeginDateByCycle(ChannelEnum.CYCLE_TYPE.MONTH.getKey(),visitDate);
			Date endDate = DateUtil.getEndDateByCycle(ChannelEnum.CYCLE_TYPE.MONTH.getKey(),visitDate);
			CtTaskData ctTaskData = ctTaskDataService.getCtTaskDataByStartTime(getClientId(),ctTaskId,popId,beginDate,endDate);
			if(ctTaskData!=null){
				map.put("ctTaskDataId", ctTaskData.getCtTaskDataId());
				map.put("existCtTaskData", true);
			}else{
				map.put("existCtTaskData", false);
			}
		}
		return map;
	}
	
	/**
	 * 修改周期任务的数据
	 * @param model
	 * @param ctTaskData
	 * @return
	 */
	@RequestMapping(value="updateCtTaskData")
	@ResponseBody
	public Object updateCtTaskData(Model model,@RequestBody CtTaskData ctTaskData){
		Integer clientId = getClientId();
		Integer clientUserId = getCurrentUserId();
		ctTaskData.setClientUserId(null);
		ctTaskData.setCreateBy(null);
		ctTaskData.setLastUpdateBy(clientUserId);
		ctTaskData.setClientId(clientId);		
		ctTaskData.setEndTime(new Date());
		ctTaskDataService.updateCtTaskData(ctTaskData);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	@RequestMapping(value="deleteCtTaskData")
	@ResponseBody
	public Object deleteCtTaskData(String ctTaskDataId){
		ctTaskDataService.deleteCtTaskData(ctTaskDataId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
}
