package cn.mobilizer.channel.survey.controller;

import java.io.File;
import java.io.IOException;
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

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.AttachmentDownload;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.AttachmentDownloadService;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.image.vo.ImageGlobal;
import cn.mobilizer.channel.image.vo.ZipSupport;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.survey.po.Survey;
import cn.mobilizer.channel.survey.po.SurveyBlock;
import cn.mobilizer.channel.survey.po.SurveyFeedback;
import cn.mobilizer.channel.survey.po.SurveyFeedbackDetail;
import cn.mobilizer.channel.survey.service.SurveyComplainService;
import cn.mobilizer.channel.survey.service.SurveyFeedbackService;
import cn.mobilizer.channel.survey.service.SurveyService;
import cn.mobilizer.channel.survey.vo.SurveyImageVo;
import cn.mobilizer.channel.survey.vo.SurveyListVo;
import cn.mobilizer.channel.survey.vo.SurveyParam;
import cn.mobilizer.channel.survey.vo.SurveyVo;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.PropertiesHelper;
import cn.mobilizer.channel.util.email.SimpleMailSender;


@Controller
@RequestMapping(value = "/appleSurvey")
public class AppleSurveyController extends BaseActionSupport{

	private static final long serialVersionUID = 7029108144611493103L;
	
	@Autowired
	private SurveyService surveyService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private CityService cityService;
	@Autowired
	private SurveyFeedbackService surveyFeedbackService;
	@Autowired
	private SurveyComplainService surveyComplainService;
    @Autowired
	private  AttachmentDownloadService attachmentDownloadService;
	protected Logger log = Logger.getLogger(this.getClass());
	
	
	/**
	 * 问卷列表页面
	 * @param model
	 * @param page
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, HttpServletRequest request,SurveyParam params) {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
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
		parameters.put("storeType", params.getArg_store_type());
		String subAllStructureId = channelCommService.getSubStructrueIds(getClientStructureId());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		@SuppressWarnings("unused")
		int clientUserId = getCurrentUserId();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**deptIds 通过页面传过来的部门(搜索列表选定的部门条件)获取该部门下所有符合权限的部门，是subStructureId的子集
		 * 从subAllStructureId中找出存在subStructureId中的部门
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		parameters.put("clientStructureId", deptIds);
		int pagenum = page == null ? 1 : page;
		Integer count = surveyService.selectAllItems(parameters);
		Page<ClientUser> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE,pagenum);
		pageParam.buildUrl(request);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize());

		List<SurveyListVo> surveys = surveyService.selectSurveyListVo(parameters);
			
		model.addAttribute("params",params);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("surveys", surveys);

		return "/survey/appleSurveyList";
	}
	
	/**
	 * 展示问卷Tab页面
	 * @param model
	 * @param surveyId
	 * @param feedbackId
	 * @param show
	 * @return
	 */
	@RequestMapping(value = "/showAppleSurveyMain/{storeId}")
	public String showSurveyMain(Model model,@PathVariable("storeId") String storeId,Integer show){
		//获取最新的问卷
		Survey survey = surveyService.getNewSurvey(super.getClientId());
		SurveyFeedback surveyFeedback = new SurveyFeedback();
		surveyFeedback.setFeedbackId(UUID.randomUUID().toString());
		surveyFeedback.setSurveyId(survey.getSurveyId());
		surveyFeedback.setClientId(super.getClientId());
		surveyFeedback.setSubmitBy(super.getCurrentUserId());
		surveyFeedback.setClientUserId(super.getCurrentUserId());
		surveyFeedback.setPopId(storeId);
		surveyFeedback.setStatus(Constants.APPLE_SURVEY_STATUS_INIT);
		surveyFeedback.setCreateTime(DateTimeUtils.getCurrentDate());
		surveyFeedback.setFeedbackDate(surveyFeedback.getCreateTime());
		surveyFeedback.setSubmitTime(surveyFeedback.getCreateTime());
		surveyFeedback.setLastUpdateTime(surveyFeedback.getCreateTime());
		surveyFeedback.setIsDelete(Constants.IS_DELETE_FALSE);
		surveyService.saveSurveyFeedback(surveyFeedback);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("surveyId", survey.getSurveyId());
		parameters.put("clientId", super.getClientId());
		List<SurveyBlock> blocks = surveyService.selectBySurveyId(parameters);
		model.addAttribute("blocks", blocks);
		model.addAttribute("surveyFeedback", surveyFeedback);
		model.addAttribute("feedbackId", surveyFeedback.getFeedbackId());
		model.addAttribute("show", show);
		return "/survey/showAppleSurveyMain";
	}
	
	/**
	 * 问卷修改
	 * @param model
	 * @param feedbackId
	 * @param show
	 * @return
	 */
	@RequestMapping(value = "/showEditAppleSurveyMain/{storeId}/{feedbackId}")
	public String showEditAppleSurveyMain(Model model,@PathVariable("storeId") String storeId,@PathVariable("feedbackId") String feedbackId,Integer show){
		//获取最新的问卷
		Survey survey = surveyService.getNewSurvey(super.getClientId());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("surveyId", survey.getSurveyId());
		parameters.put("clientId", super.getClientId());
		List<SurveyBlock> blocks = surveyService.selectBySurveyId(parameters);
		model.addAttribute("blocks", blocks);
		model.addAttribute("feedbackId", feedbackId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("show", show);
		return "/survey/showEditAppleSurveyMain";
	}
	
	
	/**
	 * 加载问卷信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showAddAppleSurvey/{blockId}")
	public String showAddSurvey(Model model,@PathVariable("blockId") Integer blockId,Integer show,String storeId) throws Exception {
		Store store = storeService.selectByPrimaryKey(storeId);
		City city = cityService.getCityById(store.getCityId());
		if(city!=null){
			store.setCityName(city.getCity());
		}		
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
		model.addAttribute("store", store);
		String ftl = null;
		if(null==show){
			ftl = "/survey/showAddAppleSurvey";
		}else{
			ftl = "/survey/showAppleSurvey";
		}
		return ftl;
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
		return "/survey/showAddAppleSurveyFeedback";
	}
	
	
	/**
	 * 新增问卷（主表数据）
	 * @return
	 */
	@RequestMapping(value = "/addAppleSurveyFeedback",produces="application/json")
	@ResponseBody
	public Object addSurveyFeedback(SurveyFeedback surveyFeedback){
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
			return ResultMessage.ADD_SUCCESS_RESULT;
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResultMessage.ADD_FAIL_RESULT;
		}
	}
	
	/**
	 * 填写问卷
	 * @return
	 */
	@RequestMapping(value = "/doAddAppleSurvey",produces="application/json")
	@ResponseBody
	public Object doAddSurvey(Model model,Integer surveyId,HttpServletRequest request,HttpServletResponse response,String feedbackId,Integer blockId) {
		try {
			@SuppressWarnings("unchecked")
			Enumeration<String> enu = request.getParameterNames();
			List<SurveyFeedbackDetail> detailList = new ArrayList<SurveyFeedbackDetail>();
			
			Date currDate = DateTimeUtils.getCurrentTime();
			//Set<Integer> surveySubs = new HashSet<Integer>();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
//				log.debug("doAddSurvey："+name);
				String[] temp = name.split("_");
				if(temp.length<3){
					continue;
				}
				String value = request.getParameter(name);
				Integer surveySubId = Integer.parseInt(temp[2]);
				
				//surveySubs.add(surveySubId);
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
			Integer item = surveyService.insertAppleSurveyFeedbackDetail(detailList,feedbackId,blockId,getClientId());
			log.info("save items: "+ item);
			return ResultMessage.UPDATE_SUCCESS_RESULT;
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
	@RequestMapping(value = "getAppleSurveyByName")
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
	@RequestMapping(value = "/getAppleSurvey/{surveyId}")
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
	@RequestMapping(value = "/getAppleSurveyFeedbackDetail/{feedbackId}",produces="application/json")
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
	@RequestMapping(value = "/deleteAppleSurvey/{feedbackId}",produces="application/json")
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
	@RequestMapping(value = "/checkAppleSurveyCycle",produces="application/json")
	@ResponseBody
	public Object checkSurveyCycle(Integer surveyId,String popId,Byte cycleType,String cycle){
		if(cycleType.equals(Constants.CYCLE_MONTH)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("surveyId", surveyId);
			params.put("storeNo", popId);
			params.put("feedbackDate", cycle);
			params.put("clientId", super.getClientId());
			
			boolean flag = surveyService.getSurveyCycle(params); 
			if(flag){
				return ResultMessage.CYCLE_SUCCESS;
			}else{
				return ResultMessage.CYCLE_FAIL;
			}
		}else{
			return ResultMessage.CYCLE_FAIL;
		}
	}
	
	/**
	 * 获取有问卷的门店
	 * @param model
	 * @param page
	 * @param req
	 * @param storeNo
	 * @param storeName
	 * @return
	 */
	@RequestMapping(value = "/queryAppleSurveyStore")
	public String queryStore(Model model, Integer page, HttpServletRequest req, String storeNo, String storeName, String storeType){
		Integer clientId = getClientId();
		String subAllStructureId = channelCommService.getSubStructrueIds(getClientStructureId());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		@SuppressWarnings("unused")
		int clientUserId = getCurrentUserId();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**deptIds 通过页面传过来的部门(搜索列表选定的部门条件)获取该部门下所有符合权限的部门，是subStructureId的子集
		 * 从subAllStructureId中找出存在subStructureId中的部门
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		if(!StringUtil.isEmptyString(deptIds) && deptIds.endsWith(",")){
			deptIds = deptIds.substring(0, deptIds.length()-1);
		}
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		if(!StringUtil.isEmptyString(subordinates) && subordinates.endsWith(",")){
			subordinates = subordinates.substring(0, subordinates.length()-1);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("storeName", storeName);
		parameters.put("storeNo", storeNo);
		parameters.put("storeType", storeType);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		int count = storeService.queryAppleStoreCount(parameters);
		int pagenum = page == null ? 1 : page;
		Page<Store> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		parameters.put("_orderby", "LAST_UPDATE_TIME");
		parameters.put("_order", "DESC");
		List<Store> list = storeService.queryAppleStoreList(parameters);
		pageParam.setItems(list);
		model.addAttribute("count", count);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		model.addAttribute("storeName", storeName);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeType", storeType);
		return "/survey/queryAppleSurveyStore";
	}
	
	/**
	 * 上传更改状态
	 * @param surveyFeedback
	 */
	@RequestMapping(value="/uploadAppleSurvey")
	@ResponseBody
	public Object uploadAppleSurvey(String feedbackId){
		SurveyFeedback surveyFeedback = surveyFeedbackService.selectByPrimaryKey(feedbackId);
		surveyFeedback.setStatus(Constants.APPLE_SURVEY_STATUS_UPLOAD);
		surveyFeedbackService.update(surveyFeedback);
		return ResultMessage.SAVE_SUCCESS_RESULT;
	}
	
	/**
	 * 审核
	 * @param surveyFeedback
	 */
	@RequestMapping(value="/aproveAppleSurvey")
	@ResponseBody
	public Object aproveAppleSurvey(String feedbackId,String aproveContent,Byte status){
		surveyComplainService.saveSurveyComplain(feedbackId, aproveContent, status, getCurrentUserId(), getClientId());
		return ResultMessage.SAVE_SUCCESS_RESULT;
	}
	
	/**
	 * 撤销申诉
	 * @param feedbackId
	 * @return
	 */
	@RequestMapping(value="revocationAprove")
	@ResponseBody
	public Object revocationAprove(String feedbackId){
		surveyComplainService.updateAproveStatus(feedbackId);
		return ResultMessage.SAVE_SUCCESS_RESULT;
	}
	
	@RequestMapping(value="/deleteAppleSurvey")
	@ResponseBody
	public Object deleteAppleSurvey(String feedbackId){
		SurveyFeedback surveyFeedback = surveyFeedbackService.selectByPrimaryKey(feedbackId);
		surveyFeedback.setIsDelete(Constants.IS_DELETE_TRUE);
		surveyFeedbackService.update(surveyFeedback);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	@RequestMapping("getSurveyFeedbackByStoreId")
	@ResponseBody
	public Object getSurveyFeedbackByStoreId(String storeId){
		return surveyFeedbackService.getSurveyFeedbackByStoreId(storeId);
	}  
	
	
	@RequestMapping(value = "/downloadImage")
	@ResponseBody
	public ResultMessage downloadImage(String email,Model model, Integer page, HttpServletRequest request,SurveyParam params) throws Exception  {
		OutputStream out = null;
		ResultMessage messsge = null;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONObject json =JSONObject.fromObject(params, jsonConfig);
		String filter=json.toString();	
		try {			 
			/*response.reset();
			response.setContentType("application/x-msdownload");
			out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("AppleCare.zip",ReportGlobal.CHARTSET))*/
			String fileName = "AppleCare_"+System.currentTimeMillis(); 
			AttachmentDownload attachmentDownload =new AttachmentDownload();
			attachmentDownload.setClientId(super.getClientId());
			attachmentDownload.setAttachmentId(UUID.randomUUID().toString());
			attachmentDownload.setEmail(email);
			attachmentDownload.setFilter(filter);
			attachmentDownload.setAttachmentName(fileName);
			attachmentDownload.setUrl(super.getClientId()+File.separator+"download"+File.separator+fileName+".zip");
		 	attachmentDownload.setCreateTime(DateTimeUtils.getCurrentTime());
		 	attachmentDownload.setInvalidTime(DateTimeUtils.addDays(DateTimeUtils.getCurrentTime(), 2));
			attachmentDownloadService.AddAttachmentDownload(attachmentDownload);			 	
		} catch (Exception e) {
			e.printStackTrace();
			messsge = new ResultMessage(ResultMessage.ERROR, "文件下载失败");
		}finally{
			if(null!=out){
				out.close();
			}
		}
		return messsge;
	}
	@RequestMapping(value = "/showPictureout")
	public Object showPictureout(){
		return "/survey/showPictureout";
	}
	
}
