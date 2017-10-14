package cn.mobilizer.channel.survey.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.POIExcelSupport;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.utils.json.JSONSupport;
import cn.mobilizer.channel.comm.utils.json.JsonTool;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.survey.po.Survey;
import cn.mobilizer.channel.survey.po.SurveyBlock;
import cn.mobilizer.channel.survey.po.SurveyFeedback;
import cn.mobilizer.channel.survey.po.SurveyFeedbackDetail;
import cn.mobilizer.channel.survey.po.SurveyRequestParamPo;
import cn.mobilizer.channel.survey.service.SurveyService;
import cn.mobilizer.channel.survey.vo.SurveyExportVo;
import cn.mobilizer.channel.survey.vo.SurveyListVo;
import cn.mobilizer.channel.survey.vo.SurveyParam;
import cn.mobilizer.channel.survey.vo.SurveyVo;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;

@Controller
@RequestMapping(value = "/survey")
public class SurveyController extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SurveyService surveyService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ChannelCommService channelCommService;

	protected Logger log = Logger.getLogger(this.getClass());

	/**
	 * 加载问卷清单
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @param surveyName
	 * @param surveyNo
	 * @return
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, HttpServletRequest request,SurveyParam params) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(null!=params && null == params.getFeedbackDate()){
			Date date = DateTimeUtils.getCurrentDate();
			params.setFeedbackDate(DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM));
		}
		
		if(null!=params && (null==params.getArg_start_date() || null==params.getArg_end_date())){
			Date startDate = DateTimeUtils.getFirstDayOfCurrentMonth();
			Date endDate = DateTimeUtils.getLastDayOfCurrentMonth();
			params.setArg_start_date(DateTimeUtils.formatTime(startDate, DateTimeUtils.yyyyMMdd));
			params.setArg_end_date(DateTimeUtils.formatTime(endDate, DateTimeUtils.yyyyMMdd));
		}
		if(null!=params && null==params.getArg_status()){
			params.setArg_status(0);
		}
		params.setArg_client_id(super.getClientId());
		String deptIds = super.getDeptIds(super.getClientStructureId());
		parameters.put("clientStructureId",deptIds);
		parameters.put("storeNo",params.getArg_store_no());
		parameters.put("clientId", super.getClientId());
		parameters.put("feedbackDate", params.getFeedbackDate());
		parameters.put("visitor", params.getArg_visitor());
		parameters.put("provinceId", params.getProvince_ids());
		parameters.put("cityId", params.getCity_ids());
		parameters.put("storeName", params.getArg_store_name());
		parameters.put("channelId", params.getArg_channel_ids());
		parameters.put("chainId", params.getArg_types());
		parameters.put("structureId", params.getArg_dept_ids());
		parameters.put("status", params.getArg_status());

		int pagenum = page == null ? 1 : page;
		Integer count = surveyService.selectAllItems(parameters);
		Page<ClientUser> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE,pagenum);
		pageParam.buildUrl(request);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize());

		List<SurveyListVo> surveys = surveyService.selectSurveyListVo(parameters);
		
		String arg_filter_user_ids = channelCommService.getSubordinates(getCurrentUserId().toString());
		String arg_filter_structure_ids = super.getDeptIds(getClientStructureId());
		params.setArg_filter_user_ids(arg_filter_user_ids);
		params.setArg_filter_structure_ids(arg_filter_structure_ids);
		
		model.addAttribute("params",params);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("surveys", surveys);
		model.addAttribute("clientId", super.getClientId());
		
		return "/survey/surveyList";
	}
	
	
	/**
	 * 展示问卷Tab页面
	 * @param model
	 * @param surveyId
	 * @param feedbackId
	 * @param show
	 * @return
	 */
	@RequestMapping(value = "/showSurveyMain/{surveyId}")
	public String showSurveyMain(Model model,@PathVariable("surveyId") Integer surveyId,String feedbackId,Integer show){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("surveyId", surveyId);
		parameters.put("clientId", super.getClientId());
		List<SurveyBlock> blocks = surveyService.selectBySurveyId(parameters);
		model.addAttribute("blocks", blocks);
		model.addAttribute("feedbackId", feedbackId);
		model.addAttribute("show", show);
		return "/survey/showSurveyMain";
	}
	
	/**
	 * 加载问卷信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showAddSurvey/{blockId}")
	public String showAddSurvey(Model model,@PathVariable("blockId") Integer blockId,Integer show) throws Exception {
		log.debug("blockId:" + blockId);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("blockId", blockId);
		parameters.put("clientId", super.getClientId());
		SurveyBlock surveyBlock = surveyService.selectBySurveyBlockPrimaryKey(parameters);
		List<SurveyVo> vos = surveyService.getSurveyVos(parameters);
		model.addAttribute("surveyId", surveyBlock.getSurveyId());
		model.addAttribute("vos", vos);
		model.addAttribute("clientId", super.getClientId());
		model.addAttribute("show", show);
		model.addAttribute("blockId", blockId);
		
		String ftl = null;
		if(null==show){
			ftl = "/survey/showAddSurvey";
		}else{
			ftl = "/survey/showSurvey";
		}
		return ftl;
	}
	
	/**
	 * 加载问卷信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showAddColgateSurvey/{blockId}")
	public String showAddColgateSurvey(Model model,@PathVariable("blockId") Integer blockId,Integer show) throws Exception {
		log.debug("blockId:" + blockId);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("blockId", blockId);
		parameters.put("clientId", super.getClientId());
		SurveyBlock surveyBlock = surveyService.selectBySurveyBlockPrimaryKey(parameters);
		List<SurveyVo> vos = surveyService.getSurveyVos(parameters);
		model.addAttribute("surveyId", surveyBlock.getSurveyId());
		model.addAttribute("vos", vos);
		model.addAttribute("clientId", super.getClientId());
		model.addAttribute("show", show);
		model.addAttribute("blockId", blockId);
		
		String ftl = null;
		if(null==show){
			ftl = "/survey/showAddColgateSurvey";
		}else{
			ftl = "/survey/showColgateSurvey";
		}
		return ftl;
	}
	
	/**
	 * 导出问卷详细数据
	 * @return
	 */
	@RequestMapping(value = "/exportDetail/{surveyId}")
	public void exportDetail(@PathVariable("surveyId") Integer surveyId,String feedbackId,String storeId,String feedbackDate,HttpServletRequest request,HttpServletResponse response) throws Exception{
		OutputStream out = null;
		try {
			Store store = storeService.getStore(storeId);
			response.reset();
			response.setContentType("application/x-msdownload");
			out = response.getOutputStream();
			 //文件名编码成UTF-8
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(store.getName()+"_"+feedbackDate+"_问卷详情.xlsx",ReportGlobal.CHARTSET));
			List<SurveyExportVo> exportVos = surveyService.getSurveyExportList(surveyId,feedbackId,super.getClientId());
			
			POIExcelSupport poi = new POIExcelSupport();
			for (SurveyExportVo vo : exportVos) {
				List<List<String>> values = vo.getDataList();
		        List<String> heards = vo.getHeads();
				String fileName = vo.getSheetName();
				fileName = fileName.replaceAll("：", "");
				poi.exportDefault(fileName,heards,values);
			}
			poi.workFlush(out);
		}finally{
			if(null!=out){
				out.close();
			}
		}
	}
	
	
	/**
	 * 弹出问卷采集窗口
	 * @return
	 */
	@RequestMapping(value = "/showAddSurveyFeedback")
	public String showAddSurveyFeedback(Model model){
		//获取最新的问卷
		Survey survey = surveyService.getNewSurvey(super.getClientId());
		Date date = DateTimeUtils.getCurrentDate();
		String month = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM);
		
		model.addAttribute("survey", survey);
		model.addAttribute("month", month);
		return "/survey/showAddSurveyFeedback";
	}
	
	@RequestMapping(value = "/showAddColgateSurveyFeedback")
	public String showAddColgateSurveyFeedback(Model model){
		//获取最新的问卷
		Survey survey = surveyService.getNewSurvey(super.getClientId());
		Date date = DateTimeUtils.getCurrentDate();
		String month = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM);
		
		model.addAttribute("survey", survey);
		model.addAttribute("month", month);
		return "/survey/showAddColgateSurveyFeedback";
	}
	
	
	/**
	 * 新增问卷（主表数据）
	 * @return
	 */
	@RequestMapping(value = "/addSurveyFeedback",produces="application/json")
	@ResponseBody
	public Object addSurveyFeedback(SurveyFeedback surveyFeedback){
		ResultMessage result = null;
		try {
			surveyFeedback.setFeedbackId(UUID.randomUUID().toString());
			surveyFeedback.setClientId(super.getClientId());
			surveyFeedback.setSubmitBy(super.getCurrentUserId());
			surveyFeedback.setClientUserId(super.getCurrentUserId());
			Store store = storeService.findStoreByStoreNo(surveyFeedback.getStoreNo(), super.getClientId());
			surveyFeedback.setPopId(store.getStoreId());
			surveyFeedback.setCreateTime(DateTimeUtils.getCurrentTime());
			surveyFeedback.setSubmitTime(surveyFeedback.getCreateTime());
			surveyFeedback.setLastUpdateTime(surveyFeedback.getCreateTime());
			surveyFeedback.setIsDelete(Constants.IS_DELETE_FALSE);
			surveyService.saveSurveyFeedback(surveyFeedback);
			result =  new ResultMessage(ResultMessage.SUCCESS,surveyFeedback.getFeedbackId());
		} catch (BusinessException e) {
			e.printStackTrace();
			result =  ResultMessage.ADD_FAIL_RESULT;
		}
		return result;
	}
	
	/**
	 * 新增问卷（主表数据）
	 * @return
	 */
	@RequestMapping(value = "/addColgateSurveyFeedback",produces="application/json")
	public String addColgateSurveyFeedback(Model model,SurveyFeedback surveyFeedback){
		surveyFeedback.setFeedbackId(UUID.randomUUID().toString());
		model.addAttribute("surveyId", surveyFeedback.getSurveyId());
		model.addAttribute("feedbackDate", surveyFeedback.getFeedbackDate());
		model.addAttribute("storeNo", surveyFeedback.getStoreNo());
		model.addAttribute("visitor", surveyFeedback.getVisitor());
		model.addAttribute("feedbackId", surveyFeedback.getFeedbackId());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("surveyId", surveyFeedback.getSurveyId());
		parameters.put("clientId", super.getClientId());
		List<SurveyBlock> blocks = surveyService.selectBySurveyId(parameters);
		model.addAttribute("blocks", blocks);
		model.addAttribute("clientId", super.getClientId());
		return "/survey/showColgateSurveyMain"; 
	}
	
	/**
	 * 填写问卷
	 * @return
	 */
	@RequestMapping(value = "/doAddSurvey",produces="application/json")
	@ResponseBody
	public Object doAddSurvey(Model model,HttpServletRequest request,HttpServletResponse response,String feedbackId,Integer blockId) {
		try {
			@SuppressWarnings("unchecked")
			Enumeration<String> enu = request.getParameterNames();
			List<SurveyFeedbackDetail> detailList = new ArrayList<SurveyFeedbackDetail>();
			
			Date currDate = DateTimeUtils.getCurrentTime();
//			Set<Integer> surveySubs = new HashSet<Integer>();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
//				log.debug("doAddSurvey："+name);
				String[] temp = name.split("_");
				if(temp.length<3){
					continue;
				}
				String value = request.getParameter(name);
				//控制判断
				/*if(StringUtil.isEmptyString(value)){
					continue;
				}*/
				Integer surveySubId = Integer.parseInt(temp[2]);
				
//				surveySubs.add(surveySubId);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId", super.getClientId());
				params.put("subSurveyId", surveySubId);
				
				SurveyFeedbackDetail detail = new SurveyFeedbackDetail();
				detail.setClientId(super.getClientId());
				detail.setFeedbackId(feedbackId);
				detail.setDetailId(UUID.randomUUID().toString());
				detail.setObjectId(Integer.parseInt(temp[0]));
				detail.setSurveyParameterId(Integer.parseInt(temp[1]));
				detail.setSubSurveyId(surveySubId);
				detail.setIsDelete(Constants.IS_DELETE_FALSE);
				detail.setLastUpdateTime(currDate);
				detail.setCreateTime(currDate);
				detail.setSubmitTime(currDate);
				
				detail.setValue(value);
				detailList.add(detail);
				log.debug("name="+name+",value="+value);
			}
//			log.info("list size: "+detailList.size());
			Integer item = surveyService.insertSurveyFeedbackDetail(detailList,feedbackId,blockId,super.getClientId());
			log.info("save items: "+ item);
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
	}
	
	/**
	 * 填写问卷
	 * @return
	 */
	@RequestMapping(value = "/doAddColgateSurvey",produces="application/json")
	@ResponseBody
	public Object doAddColgateSurvey(Model model,HttpServletRequest request,HttpServletResponse response,String feedbackId,Integer blockId) {
		try {
			@SuppressWarnings("unchecked")
			Enumeration<String> enu = request.getParameterNames();
			List<SurveyFeedbackDetail> detailList = new ArrayList<SurveyFeedbackDetail>();
			
			Date currDate = DateTimeUtils.getCurrentTime();
//			Set<Integer> surveySubs = new HashSet<Integer>();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
//				log.debug("doAddSurvey："+name);
				String[] temp = name.split("_");
				if(temp.length<3){
					continue;
				}
				String value = request.getParameter(name);
				if(StringUtil.isEmptyString(value)){
					continue;
				}
				Integer surveySubId = Integer.parseInt(temp[2]);
				
//				surveySubs.add(surveySubId);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId", super.getClientId());
				params.put("subSurveyId", surveySubId);
				
				SurveyFeedbackDetail detail = new SurveyFeedbackDetail();
				if(name.equals("99_5_2") || name.equals("248_5_2") || name.equals("249_5_2") || name.equals("250_5_2")){
					detail.setControlType(11);
				}
				detail.setClientId(super.getClientId());
				detail.setFeedbackId(feedbackId);
				detail.setDetailId(UUID.randomUUID().toString());
				detail.setObjectId(Integer.parseInt(temp[0]));
				detail.setSurveyParameterId(Integer.parseInt(temp[1]));
				detail.setSubSurveyId(surveySubId);
				detail.setIsDelete(Constants.IS_DELETE_FALSE);
				detail.setLastUpdateTime(currDate);
				detail.setCreateTime(currDate);
				detail.setSubmitTime(currDate);
				
				detail.setValue(value);
				detailList.add(detail);
			}
			return detailList;
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
	}
	
	/**
	 * 获取问卷<select2控件>
	 * @param surveyName
	 * @return
	 */
	@RequestMapping(value = "getSurveyByName")
	@ResponseBody
	public List<Survey> getSurveyByName(String q){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("surveyName", q);
		parameters.put("clientId", super.getClientId());
		List<Survey> surveys = surveyService.getSurveyByName(parameters);
		return surveys;
	}
	
	
	/**
	 * 获取问卷
	 * @param surveyName
	 * @return
	 */
	@RequestMapping(value = "/getSurvey/{surveyId}")
	@ResponseBody
	public Survey getSurvey(@PathVariable("surveyId")int surveyId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("surveyId", surveyId);
		parameters.put("clientId", super.getClientId());
		Survey survey = surveyService.getSurvey(parameters);
		return survey;
	}
	
	
	/**
	 * 获取答案
	 * @param feedbackId
	 * @return
	 */
	@RequestMapping(value = "/getSurveyFeedbackDetail/{feedbackId}",produces="application/json")
	@ResponseBody
	public List<SurveyFeedbackDetail> getSurveyFeedbackDetail(@PathVariable("feedbackId")String feedbackId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("feedbackId", feedbackId);
		parameters.put("clientId", super.getClientId());
		List<SurveyFeedbackDetail> survey = surveyService.getSurveyFeedbackDetail(parameters);
		return survey;
	}
	
	
	/**
	 * 删除问卷
	 * @param feedbackId
	 * @return
	 */
	@RequestMapping(value = "/deleteSurvey/{feedbackId}",produces="application/json")
	@ResponseBody
	public Object deleteSurvey(@PathVariable("feedbackId")String feedbackId){
		try {
			surveyService.deleteSurvey(feedbackId);
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResultMessage.DELETE_FAIL_RESULT;
		}
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	
	/**
	 * 验证当前周期内是否已经填写过问卷，一个店一个周期内只能填写一份问卷
	 * @return
	 */
	@RequestMapping(value = "/checkSurveyCycle",produces="application/json")
	@ResponseBody
	public Object checkSurveyCycle(Integer surveyId,String storeNo,Byte cycleType,String cycle){
		if(cycleType.equals(Constants.CYCLE_MONTH)){
			Store store = storeService.findStoreByStoreNo(storeNo, super.getClientId());
			if(null == store){
				return ResultMessage.CYCLE_FAIL;
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("surveyId", surveyId);
				params.put("storeId", store.getStoreId());
				params.put("feedbackDate", cycle);
				params.put("clientId", super.getClientId());
				
				boolean flag = surveyService.getSurveyCycle(params); 
				if(flag){
					return ResultMessage.CYCLE_SUCCESS;
				}else{
					return ResultMessage.CYCLE_FAIL;
				}
			}
		}else{
			return ResultMessage.CYCLE_FAIL;
		}
	}
	
	
	/**
	 * 弹出显示周期选择页面
	 * @return
	 */
	@RequestMapping(value = "/showExportCyclePage")
	public String showExportCyclePage(Model model,String dataId){
		Date date = DateTimeUtils.getCurrentDate();
		String feedbackDate =  DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMM);
		model.addAttribute("dataId", dataId);
		model.addAttribute("feedbackDate", feedbackDate);
		return "/survey/showExportCyclePage";
	} 
	
	@RequestMapping(value = "/showColgatPictureout")
	public Object showColgatPictureout(){
		return "/survey/showColgatPictureout";
	}
	
	/**
	 * 高露洁问卷保存
	 * @param model
	 * @param request
	 * @param response
	 * @param feedbackId
	 * @param blockId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/doAddColgateSurvey")
	@ResponseBody
	public Object doAddColgateSurvey(Model model,HttpServletRequest request,HttpServletResponse response,String feedbackId,Integer blockId,String blockId3,String blockId4,String blockId5,String blockId6,String storeNo,String visitor,String feedbackDate,Integer surveyId){
		try { 
			List<SurveyFeedbackDetail> block3 = JSONSupport.toList(blockId3, SurveyFeedbackDetail.class);
			List<SurveyFeedbackDetail> block4 = JSONSupport.toList(blockId4, SurveyFeedbackDetail.class);
			List<SurveyFeedbackDetail> block5 = JSONSupport.toList(blockId5, SurveyFeedbackDetail.class);
			List<SurveyFeedbackDetail> block6 = JSONSupport.toList(blockId6, SurveyFeedbackDetail.class);
			surveyService.saveColgateData(block3,block4,block5,block6,feedbackId, super.getClientId(),storeNo,visitor,DateUtil.getDateByStr(feedbackDate, DateUtil.dateTimeFormat),surveyId,getCurrentUserId());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
}
