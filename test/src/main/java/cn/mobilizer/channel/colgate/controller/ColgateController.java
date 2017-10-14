package cn.mobilizer.channel.colgate.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.controller.LoginController;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.colgate.po.DownloadDataList;
import cn.mobilizer.channel.colgate.service.ColgateService;
import cn.mobilizer.channel.colgate.service.DownloadDataListService;
import cn.mobilizer.channel.colgate.vo.FilterVo;
import cn.mobilizer.channel.colgate.vo.MonthVo;
import cn.mobilizer.channel.colgate.vo.OverViewVo;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.utils.web.WebUtils;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.image.service.ImageService;
import cn.mobilizer.channel.image.vo.ColgateImageVo;
import cn.mobilizer.channel.survey.vo.SurveyParam;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;

@Controller
@RequestMapping(value = "/colgate")
public class ColgateController extends BaseActionSupport{

	private static final long serialVersionUID = -6830486192253981395L;
	
	@Autowired
	private ColgateService colgateService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private DownloadDataListService downloadDataListService;
	
	
	@Autowired 
	private LoginController loginController;
	
	
	@RequestMapping(value = "colgate_login")
	public String login(HttpServletRequest resquest, HttpServletResponse response){
		return "/colgate/login";
	}

	@RequestMapping(value = "overView")
	public String overView(Model model,FilterVo filterVo){
		if(null == filterVo.getArgMonthId()){
			StringBuilder builder = new StringBuilder();
			builder.append(DateTimeUtils.getCurrentYear());
			Integer month = DateTimeUtils.getCurrentMonth();
			if(month<10){
				builder.append("0").append(month);
			}else{
				builder.append(month);
			}
			filterVo.setArgMonthId(Integer.parseInt(builder.toString()));
		}
		if(StringUtil.isNotEmptyString(filterVo.getCity())){
			filterVo.setArgDeptIds(filterVo.getCity());
		}else if(StringUtil.isNotEmptyString(filterVo.getProvince())){
			filterVo.setArgDeptIds(super.getDeptIds(filterVo.getProvince()));
		}else if(StringUtil.isNotEmptyString(filterVo.getArgStructureId())){
			filterVo.setArgDeptIds(super.getDeptIds(filterVo.getArgStructureId()));
		}
		String argFilterUserIds = channelCommService.getSubordinates(getCurrentUserId().toString());
		String argFilterStructureIds = super.getDeptIds(getClientStructureId()+"");
		log.info("Brand: "+filterVo.getArgBrandName());
		filterVo.setArgFilterUserIds(argFilterUserIds);
		filterVo.setArgFilterStructureIds(argFilterStructureIds);
		List<MonthVo> months = new MonthVo().initDefault();
		model.addAttribute("months",months);
		model.addAttribute("filterVo",filterVo);
		return "/colgate/overView";
	}
	
	@RequestMapping(value = "loadOverViewContent")
	@ResponseBody
	public Object loadOverViewContent(FilterVo filterVo){
		List<OverViewVo> overViewVo = null;
		try {
			if(StringUtil.isNotEmptyString(filterVo.getCity())){
				filterVo.setArgDeptIds(filterVo.getCity());
			}else if(StringUtil.isNotEmptyString(filterVo.getProvince())){
				filterVo.setArgDeptIds(super.getDeptIds(filterVo.getProvince()));
			}else if(StringUtil.isNotEmptyString(filterVo.getArgStructureId())){
				filterVo.setArgDeptIds(super.getDeptIds(filterVo.getArgStructureId()));
			}
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			overViewVo = colgateService.loadOverView(filterVo);
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return overViewVo;
	}
	
	@RequestMapping(value = "reports")
	public String reports(){
		
		return "/colgate/reports";
	}
	
	
	@RequestMapping("rawdata")
	public String rawData(Model model,HttpServletRequest resquest, HttpServletResponse response) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", super.getClientId());
		List<DownloadDataList> dataList = downloadDataListService.loadRawDataList(params);
		model.addAttribute("dataList", dataList);
		model.addAttribute("clientId", super.getClientId());
		return "/colgate/rawdata";
	}
					
	@RequestMapping(value = "home")
	public String home(){
		
		return "/colgate/home";
	}
	
	@RequestMapping(value = "reportsFilter")
	public String reportsFilter(){
		
		return "/colgate/reportsFilter";
	}
	
	@RequestMapping(value = "dashboard")
	public String dashboard(Model model,FilterVo filterVo ,String type){
		if(null == filterVo.getArgMonthId()){
			StringBuilder builder = new StringBuilder();
			builder.append(DateTimeUtils.getCurrentYear());
			Integer month = DateTimeUtils.getCurrentMonth();
			if(month<10){
				builder.append("0").append(month);
			}else{
				builder.append(month);
			}
			filterVo.setArgMonthId(Integer.parseInt(builder.toString()));
		}
		if(StringUtil.isNotEmptyString(filterVo.getCity())){
			filterVo.setArgDeptIds(filterVo.getCity());
		}else if(StringUtil.isNotEmptyString(filterVo.getProvince())){
			filterVo.setArgDeptIds(super.getDeptIds(filterVo.getProvince()));
		}else if(StringUtil.isNotEmptyString(filterVo.getArgStructureId())){
			filterVo.setArgDeptIds(super.getDeptIds(filterVo.getArgStructureId()));
		}
		String argFilterUserIds = channelCommService.getSubordinates(getCurrentUserId().toString());
		String argFilterStructureIds = super.getDeptIds(getClientStructureId()+"");
		filterVo.setArgFilterUserIds(argFilterUserIds);
		filterVo.setArgFilterStructureIds(argFilterStructureIds);
		log.info("Brand: "+filterVo.getArgBrandName());
		List<MonthVo> months = new MonthVo().initDefault();
		model.addAttribute("type",type);
		model.addAttribute("months",months);
		model.addAttribute("filterVo",filterVo);
		return "/colgate/dashboard";
	}
	
	@RequestMapping(value = "/loadDashboardVo")
	@ResponseBody
	public Object loadDashboardVo(FilterVo filterVo){
		Object result;
		try {
			if(StringUtil.isNotEmptyString(filterVo.getCity())){
				filterVo.setArgDeptIds(filterVo.getCity());
			}else if(StringUtil.isNotEmptyString(filterVo.getProvince())){
				filterVo.setArgDeptIds(super.getDeptIds(filterVo.getProvince()));
			}else if(StringUtil.isNotEmptyString(filterVo.getArgStructureId())){
				filterVo.setArgDeptIds(super.getDeptIds(filterVo.getArgStructureId()));
			}
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			result = colgateService.loadColgateDashBoardData(filterVo);
		} finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return result;
	}
	
	
	@RequestMapping(value = "loadColagetRegions")
	@ResponseBody
	public List<ClientStructure> loadColagetRegions(String parentId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", super.getClientId());
		String[] parents  = parentId.split(",");
		params.put("parentIds", parents);
		List<ClientStructure> structures = colgateService.getStructureByParentId(params);
		return structures;
	}
	
	@RequestMapping(value = "getMonths")
	@ResponseBody
	public List<MonthVo> getMonths(){
		List<MonthVo> months = new MonthVo().initDefault();
		return months;
	}
	
	
	@RequestMapping("isCode")
	@ResponseBody
	public Boolean isCode(String key , String code , HttpServletRequest req){
		String str = (String) WebUtils.getSession(req,Constants.VERIFICATION_CODE+"_"+key);
		if(null != str){
			if(str.equalsIgnoreCase(code)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 问卷照片查看
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/gallery")
	public String querySurveyImage(Model model,HttpServletRequest request,SurveyParam params) throws Exception{
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

		Map<String, List<ColgateImageVo>> images = imageService.selectSurveyImageList(parameters);

		model.addAttribute("params",params);
		model.addAttribute("images", images);

		return "/colgate/gallrey";
	}
	
	
	@RequestMapping(value ="showInitRawData")
	public String showInitRawData(){
		
		return "/colgate/showInitRawData";
	}
	
	
	@RequestMapping(value ="initRawData")
	@ResponseBody
	public ResultMessage initRawData(String month) throws Exception{
		ResultMessage message = null;
		try {
			Date date = DateTimeUtils.toTime(month, DateTimeUtils.yyyyMM);
			downloadDataListService.createMainQuestionnaire(date);
			downloadDataListService.createSecondaryDisplayQuestionnaire(date);
			downloadDataListService.createSupplementaryQuestionnaire(date);
			message = new ResultMessage(ResultMessage.SUCCESS, month+"月数据生成成功！");
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.ERROR, "数据生成失败！"+e.getMessage());
		}
		return message;
	}

}
