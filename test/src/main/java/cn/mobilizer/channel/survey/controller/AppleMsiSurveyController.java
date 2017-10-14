/**
 * 
 */
package cn.mobilizer.channel.survey.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.po.MsiQuestionGroup;
import cn.mobilizer.channel.survey.po.MsiSurvey;
import cn.mobilizer.channel.survey.po.MsiSurveyComplain;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedback;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackAttachment;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackDetail;
import cn.mobilizer.channel.survey.po.SurveyFeedbackDetail;
import cn.mobilizer.channel.survey.service.MsiQuestionGroupService;
import cn.mobilizer.channel.survey.service.MsiQuestionService;
import cn.mobilizer.channel.survey.service.MsiSurveyComplainService;
import cn.mobilizer.channel.survey.service.MsiSurveyFeedbackService;
import cn.mobilizer.channel.survey.service.MsiSurveyService;
import cn.mobilizer.channel.survey.vo.ExportMsiSurveyFeedbackVo;
import cn.mobilizer.channel.survey.vo.MsiQuestionVO;
import cn.mobilizer.channel.survey.vo.MsiSurveyFeedbackVO;
import cn.mobilizer.channel.survey.vo.SaveMsiSurveyComplainVo;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.FreemarkerUtil;
import cn.mobilizer.channel.util.PDFCoreSupport;
import cn.mobilizer.channel.util.PropertiesHelper;


/**
 * 暗访任务数据Controller
 * @author yeeda.tian 2015-6-10
 */
@Controller
@RequestMapping(value = "/appleMsiSurvey")
public class AppleMsiSurveyController extends BaseActionSupport {

	private static final long serialVersionUID = -6754251546465398819L;
	
	@Autowired
	private MsiSurveyFeedbackService msiSurveyFeedbackService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private MsiQuestionService msiQuestionService;
	@Autowired
	private MsiSurveyService msiSurveyService;
	@Autowired
	private MsiSurveyComplainService msiSurveyComplainService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private MsiQuestionGroupService msiQuestionGroupService;
	
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, HttpServletRequest req, String storeNo, String storeName,String visitor,Integer provinceId,Integer cityId,String startDate, String endDate,Integer clientStructureId) throws BusinessException{
		String reFtl = "/survey/appleMsiSurveyFeedbackList";
		if(startDate == null && endDate ==null) {
			Date today = new Date();
			endDate = DateUtil.formatDate(today, DateUtil.dateFormat);
			startDate = DateUtil.formatDate(DateUtil.dsDay_Date(today, -30),DateUtil.dateFormat);
		}
		
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		
		String deptIds = channelCommService.getSubStructrueIds(clientStructureId);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("msiSurveyId", new Integer(5));
		parameters.put("deptIds", deptIds);
		parameters.put("storeNo", storeNo);
		parameters.put("storeName", storeName);
		parameters.put("visitor", visitor);
		parameters.put("provinceId", provinceId);
		parameters.put("cityId", cityId);
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);
		int count = msiSurveyFeedbackService.queryAppleTotalCount(parameters);
		int pagenum = page == null ? 1 : page;
		Page<MsiSurveyFeedback> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<MsiSurveyFeedback> list = msiSurveyFeedbackService.findAppleListByParams(parameters);
		pageParam.setItems(list);
		
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("visitor", visitor);
		model.addAttribute("provinceId", provinceId);
		model.addAttribute("cityId", cityId);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		model.addAttribute("count", count);
		return reFtl;
	}
	
	@RequestMapping(value = "/queryStore")
	public String queryStores(Model model, Integer page, HttpServletRequest req, String storeNo, String storeName) throws BusinessException{
		String reFtl = "/survey/appleMsiStoreList";
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("storeNo", storeNo);
		parameters.put("storeName", storeName);
		int count = storeService.findStoreCountInMsi(parameters);
		int pagenum = page == null ? 1 : page;
		Page<Store> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<Store> list = storeService.findStoreListInMsi(parameters);
		pageParam.setItems(list);
		
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		model.addAttribute("count", count);
		return reFtl;
	}
	
	@RequestMapping(value = "/showAddAppleMsiSurveyData/{storeId}/{storeNo}/{storeName}")
	public String showAddMsiSurveyData(Model model, @PathVariable("storeId")String storeId, @PathVariable("storeNo")String storeNo,@PathVariable("storeName")String storeName) throws BusinessException{
		String reFtl = "/survey/showAddAppleMsiSurveyData";
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
//		Integer point = null;
		Integer msiSurveyId = null;
		List<MsiQuestionGroup> msiQuestionGroups = null;
		MsiSurvey msiSurvey = msiSurveyService.findAppleMsiSurveyListByType(clientId, storeId);
		if(msiSurvey == null){
			msiSurvey = msiSurveyService.findAppleMsiSurveyListByStore(clientId, storeId);
		}
		if(msiSurvey != null && msiSurvey.getMsiSurveyId() !=null) {
			msiSurveyId = msiSurvey.getMsiSurveyId();
			msiQuestionGroups = msiQuestionGroupService.findEntitysByMsiSurveyId(clientId, msiSurveyId, null);
			for(MsiQuestionGroup msiQuestionGroup : msiQuestionGroups){
				if(!msiQuestionGroup.getChildrenList().isEmpty()){
					for(MsiQuestionGroup childrenMsiQuestionGroup : msiQuestionGroup.getChildrenList()){
						List<MsiQuestionVO> msiQuestionVOList =  msiQuestionService.getMsiQuestionsByMsiSurveyIdAndParentId(msiSurveyId,null,childrenMsiQuestionGroup.getQuestionGroupId());
						childrenMsiQuestionGroup.setMsiQuestionVos(msiQuestionVOList);
					}
				}
			}
		}
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("msiQuestionGroups", msiQuestionGroups);
		model.addAttribute("msiSurvey", msiSurvey);
		model.addAttribute("msiSurveyId", msiSurveyId);
		return reFtl;
	}
	
	@RequestMapping(value = "/showEditAppleMsiSurveyData/{feedbackId}")
	public String showEditMsiSurveyData(Model model, @PathVariable("feedbackId")String feedbackId, String storeId, String storeNo,String storeName,String view) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackById(feedbackId);
		List<MsiSurveyFeedback> vmiMsiSurveyFeedbacks = msiSurveyFeedbackService.findAppleExamFeedbackByStoreId(clientId, storeId, 6);
		List<MsiSurveyFeedback> maintainMsiSurveyFeedbacks = msiSurveyFeedbackService.findAppleExamFeedbackByStoreId(clientId, storeId, 7);
		MsiSurvey msiSurvey = msiSurveyService.findAppleMsiSurveyListByType(clientId, storeId);
		if(msiSurvey == null){
			msiSurvey = msiSurveyService.findAppleMsiSurveyListByStore(clientId, storeId);
		}
		List<MsiQuestionGroup> msiQuestionGroups = null;
		if(msiSurveyFeedback != null) {
			msiQuestionGroups = msiQuestionGroupService.findEntitysByMsiSurveyId(clientId, msiSurveyFeedback.getMsiSurveyId(), null);
			for(MsiQuestionGroup msiQuestionGroup : msiQuestionGroups){
				if(!msiQuestionGroup.getChildrenList().isEmpty()){
					for(MsiQuestionGroup childrenMsiQuestionGroup : msiQuestionGroup.getChildrenList()){
						List<MsiQuestionVO> msiQuestionVOList =  msiQuestionService.getAppleMsiQuestionListWithChecked(clientId,msiSurveyFeedback.getMsiSurveyId(),feedbackId,null,childrenMsiQuestionGroup.getQuestionGroupId());
						childrenMsiQuestionGroup.setMsiQuestionVos(msiQuestionVOList);
					}
				}
			}
		}
		String reFtl = null;
		if(StringUtil.isEmptyString(view)){
			reFtl = "/survey/showEditAppleMsiSurveyData";
		}else{
			//查找审核员状态为2的数据
			MsiSurveyFeedback approvalMsiSurveyFeedback = msiSurveyFeedbackService.findApprovalDataByStoreIdAndDataType(clientId,storeId,msiSurveyFeedback.getMsiSurveyId(),Constants.APPLE_DATA_TYPE_APPROVAL);
			model.addAttribute("approvalMsiSurveyFeedback", approvalMsiSurveyFeedback);
			reFtl = "/survey/showAppleMsiSurveyData";
		}
		model.addAttribute("vmiMsiSurveyFeedbacks", vmiMsiSurveyFeedbacks);
		model.addAttribute("maintainMsiSurveyFeedbacks", maintainMsiSurveyFeedbacks);
		model.addAttribute("view", view);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("msiSurvey", msiSurvey);
		model.addAttribute("msiSurveyFeedback", msiSurveyFeedback);
		model.addAttribute("msiQuestionGroups", msiQuestionGroups);
		return reFtl;
	}
	
	/**
	 * 审核苹果问卷
	 * @param model
	 * @param feedbackId
	 * @param storeId
	 * @param storeNo
	 * @param storeName
	 * @param view
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/approvalMsiSurveyData/{feedbackId}")
	public String approvalMsiSurveyData(Model model, @PathVariable("feedbackId")String feedbackId, String storeId, String storeNo,String storeName,String view) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackById(feedbackId);       //访问员数据
		MsiSurveyFeedback approvalMsiSurveyFeedback = msiSurveyFeedbackService.findApprovalDataByStoreIdAndDataType(clientId,storeId,msiSurveyFeedback.getMsiSurveyId(),Constants.APPLE_DATA_TYPE_APPROVAL);   //审核员数据 状态为2
		List<MsiSurveyFeedback> vmiMsiSurveyFeedbacks = msiSurveyFeedbackService.findAppleExamFeedbackByStoreId(clientId, storeId, 6);
		List<MsiSurveyFeedback> maintainMsiSurveyFeedbacks = msiSurveyFeedbackService.findAppleExamFeedbackByStoreId(clientId, storeId, 7);
		MsiSurvey msiSurvey = msiSurveyService.findAppleMsiSurveyListByType(clientId, storeId);
		if(msiSurvey == null){
			msiSurvey = msiSurveyService.findAppleMsiSurveyListByStore(clientId, storeId);
		}
		List<MsiQuestionGroup> msiQuestionGroups = null;
		if(msiSurveyFeedback != null) {
			msiQuestionGroups = msiQuestionGroupService.findEntitysByMsiSurveyId(clientId, msiSurveyFeedback.getMsiSurveyId(), null);
			for(MsiQuestionGroup msiQuestionGroup : msiQuestionGroups){
				if(!msiQuestionGroup.getChildrenList().isEmpty()){
					for(MsiQuestionGroup childrenMsiQuestionGroup : msiQuestionGroup.getChildrenList()){
						List<MsiQuestionVO> msiQuestionVOList =  msiQuestionService.getAppleMsiQuestionListWithChecked(clientId,msiSurveyFeedback.getMsiSurveyId(),feedbackId,null,childrenMsiQuestionGroup.getQuestionGroupId());
						childrenMsiQuestionGroup.setMsiQuestionVos(msiQuestionVOList);
					}
				}
			}
		}
		
		String reFtl = null;
		if(StringUtil.isEmptyString(view)){
			reFtl = "/survey/showEditAppleApprovalMsiSurveyData";
		}else{
		}
		if(approvalMsiSurveyFeedback!=null){
			model.addAttribute("approvalMsiSurveyFeedback", approvalMsiSurveyFeedback);
		}
		model.addAttribute("vmiMsiSurveyFeedbacks", vmiMsiSurveyFeedbacks);
		model.addAttribute("maintainMsiSurveyFeedbacks", maintainMsiSurveyFeedbacks);
		model.addAttribute("view", view);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("msiSurvey", msiSurvey);
		model.addAttribute("msiSurveyFeedback", msiSurveyFeedback);
		model.addAttribute("msiQuestionGroups", msiQuestionGroups);
		return reFtl;
	}
	
	@RequestMapping(value = "/addMsiSurveyData")
	@ResponseBody
	public Object addMsiSurveyData(MsiSurveyFeedbackVO msiSurveyFeedbackVO,HttpServletRequest request) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		try {
			@SuppressWarnings("unchecked")
			Enumeration<String> enu = request.getParameterNames();
			List<MsiSurveyFeedbackDetail> detailList = new ArrayList<MsiSurveyFeedbackDetail>();
			Map<Integer, String> questionMap = new HashMap<Integer, String>();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				String[] temp = name.split("_");
				String value = request.getParameter(name);
				if(StringUtil.isEmptyString(value)){
					continue;
				}
				if(name.indexOf("checked_")>=0){
					if(value.equals("true")){
						Integer msiQuestionId = Integer.parseInt(temp[1]);
						MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
						Integer msiAnswerId = Integer.parseInt(temp[2]);
						detail.setMsiAnswerId(msiAnswerId);
						detail.setClientId(super.getClientId());
						detail.setDetailId(UUID.randomUUID().toString());
						detail.setMsiQuestionId(msiQuestionId);
						detail.setIsDelete(Constants.IS_DELETE_FALSE);
						detailList.add(detail);
					}
				}else if(name.indexOf("img_")>=0){
					String imageNames = value;
					if(StringUtil.isNotEmptyString(imageNames)){
						questionMap.put(new Integer(temp[1]), value);
					}
				}else if(temp.length==3){
					MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
					Integer msiQuestionId = Integer.parseInt(temp[0]);
					Integer msiAnswerId = Integer.parseInt(temp[1]);
					Integer msiSurveyParameterId = Integer.parseInt(temp[2]);
					detail.setMsiAnswerId(msiAnswerId);
					detail.setMsiQuestionId(msiQuestionId);
					detail.setCol1(value);
					detail.setCol2(msiSurveyParameterId.toString());
					detail.setClientId(super.getClientId());
					detailList.add(detail);
				}else if(temp.length == 2){
					MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
					Integer msiQuestionId = Integer.parseInt(temp[0]);
					detail.setMsiQuestionId(msiQuestionId);
					detail.setCol1(value);
					detail.setClientId(super.getClientId());
					detailList.add(detail);
				}
			}
			msiSurveyFeedbackVO.setMsiSurveyFeedbackDetails(detailList);
			msiSurveyFeedbackVO.setQuestionImgMap(questionMap);
			msiSurveyFeedbackVO.setDataType(Constants.APPLE_DATA_TYPE_VISIT);						//访问员数据
			msiSurveyFeedbackService.addAppleMsiSurveyFeedback(clientId,clientUserId,msiSurveyFeedbackVO);
			return ResultMessage.ADD_SUCCESS_RESULT;
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.ADD_FAIL_RESULT;
		}
	}
	
	@RequestMapping(value = "/updateMsiSurveyData")
	@ResponseBody
	public Object updateMsiSurveyData(Model model,HttpServletRequest request,HttpServletResponse response,MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException{
		
		try {
			@SuppressWarnings("unchecked")
			Enumeration<String> enu = request.getParameterNames();
			List<MsiSurveyFeedbackDetail> detailList = new ArrayList<MsiSurveyFeedbackDetail>();
			Map<Integer, String> questionMap = new HashMap<Integer, String>();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				String[] temp = name.split("_");
				String value = request.getParameter(name);
				if(StringUtil.isEmptyString(value)){
					continue;
				}
				if(name.indexOf("checked_")>=0){
					if(value.equals("true")){
						Integer msiQuestionId = Integer.parseInt(temp[1]);
						MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
						Integer msiAnswerId = Integer.parseInt(temp[2]);
						detail.setMsiAnswerId(msiAnswerId);
						detail.setClientId(super.getClientId());
						detail.setFeedbackId(msiSurveyFeedbackVO.getFeedbackId());
						detail.setDetailId(UUID.randomUUID().toString());
						detail.setMsiQuestionId(msiQuestionId);
						detail.setIsDelete(Constants.IS_DELETE_FALSE);
						detailList.add(detail);
					}
				}else if(name.indexOf("img_")>=0){
					questionMap.put(new Integer(temp[1]), value);
				}else if(temp.length==3){
					MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
					Integer msiQuestionId = Integer.parseInt(temp[0]);
					Integer msiAnswerId = Integer.parseInt(temp[1]);
					Integer msiSurveyParameterId = Integer.parseInt(temp[2]);
					detail.setMsiAnswerId(msiAnswerId);
					detail.setMsiQuestionId(msiQuestionId);
					detail.setCol1(value);
					detail.setCol2(msiSurveyParameterId.toString());
					detail.setClientId(super.getClientId());
					detail.setFeedbackId(msiSurveyFeedbackVO.getFeedbackId());
					detailList.add(detail);
				}else if(temp.length == 2){
					MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
					Integer msiQuestionId = Integer.parseInt(temp[0]);
					detail.setMsiQuestionId(msiQuestionId);
					detail.setCol1(value);
					detail.setClientId(super.getClientId());
					detail.setFeedbackId(msiSurveyFeedbackVO.getFeedbackId());
					detailList.add(detail); 
				}
			}
			msiSurveyFeedbackVO.setMsiSurveyFeedbackDetails(detailList);
			msiSurveyFeedbackVO.setQuestionImgMap(questionMap);
			int clientUserId = getCurrentUserId();
			int clientId = getClientId ();			
			msiSurveyFeedbackService.updateAppleMsiSurveyData(clientId,clientUserId,msiSurveyFeedbackVO);
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
	}
	
	/**
	 * 删除
	 * @param feedbackId
	 * @return
	 */
	@RequestMapping("/deleteMsiSurveyData/{feedbackId}")
	@ResponseBody
	public Object deleteMsiSurveyData(@PathVariable("feedbackId")String feedbackId){
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackById(feedbackId);
		MsiSurveyFeedback approvalMsiSurveyFeedback = msiSurveyFeedbackService.findApprovalDataByStoreIdAndDataType(getClientId(),msiSurveyFeedback.getStoreId(),msiSurveyFeedback.getMsiSurveyId(),Constants.APPLE_DATA_TYPE_APPROVAL);
		msiSurveyFeedbackService.deleteByPrimaryKey(feedbackId);
		if(approvalMsiSurveyFeedback!=null){
			msiSurveyFeedbackService.deleteByPrimaryKey(approvalMsiSurveyFeedback.getFeedbackId());   //删除审核数据
		}
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	@RequestMapping("/findMsiSurveyFeedbackByStoreId/{storeId}")
	@ResponseBody
	public Object findMsiSurveyFeedbackByStoreId(@PathVariable("storeId")String storeId){
		Integer clientId = getClientId();
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackByStoreId(clientId,storeId,5);
		if(msiSurveyFeedback!=null){
			return msiSurveyFeedback;
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "/approvalAppleMsiSurveyData")
	@ResponseBody
	public Object approvalAppleMsiSurveyData(MsiSurveyFeedbackVO msiSurveyFeedbackVO,HttpServletRequest request) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		MsiSurveyFeedback approvalMsiSurveyFeedback = msiSurveyFeedbackService.findApprovalDataByStoreIdAndDataType(clientId,msiSurveyFeedbackVO.getStoreId(),msiSurveyFeedbackVO.getMsiSurveyId(),Constants.APPLE_DATA_TYPE_APPROVAL);
		try {
			@SuppressWarnings("unchecked")
			Enumeration<String> enu = request.getParameterNames();
			List<MsiSurveyFeedbackDetail> detailList = new ArrayList<MsiSurveyFeedbackDetail>();
			Map<Integer, String> questionMap = new HashMap<Integer, String>();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				String[] temp = name.split("_");
				String value = request.getParameter(name);
				if(name.contains("init_")){                 //原始访问员填写的数据不保存
					continue;
				}
				if(StringUtil.isEmptyString(value)){
					continue;
				}
				if(name.indexOf("checked_")>=0){
					if(value.equals("true")){
						Integer msiQuestionId = Integer.parseInt(temp[1]);
						MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
						Integer msiAnswerId = Integer.parseInt(temp[2]);
						detail.setMsiAnswerId(msiAnswerId);
						detail.setClientId(super.getClientId());
						if(approvalMsiSurveyFeedback!=null){
							detail.setFeedbackId(approvalMsiSurveyFeedback.getFeedbackId());
						}
						detail.setDetailId(UUID.randomUUID().toString());
						detail.setMsiQuestionId(msiQuestionId);
						detail.setIsDelete(Constants.IS_DELETE_FALSE);
						detailList.add(detail);
					}
				}else if(temp.length==3){
					MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
					Integer msiQuestionId = Integer.parseInt(temp[0]);
					Integer msiAnswerId = Integer.parseInt(temp[1]);
					Integer msiSurveyParameterId = Integer.parseInt(temp[2]);
					detail.setMsiAnswerId(msiAnswerId);
					detail.setMsiQuestionId(msiQuestionId);
					detail.setCol1(value);
					detail.setCol2(msiSurveyParameterId.toString());
					detail.setClientId(super.getClientId());
					if(approvalMsiSurveyFeedback!=null){
						detail.setFeedbackId(approvalMsiSurveyFeedback.getFeedbackId());
					}
					detailList.add(detail);
				}else if(temp.length == 2){
					MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
					Integer msiQuestionId = Integer.parseInt(temp[0]);
					detail.setMsiQuestionId(msiQuestionId);
					detail.setCol1(value);
					detail.setClientId(super.getClientId());
					if(approvalMsiSurveyFeedback!=null){
						detail.setFeedbackId(approvalMsiSurveyFeedback.getFeedbackId());
					}
					detailList.add(detail); 
				}
			}
			msiSurveyFeedbackVO.setMsiSurveyFeedbackDetails(detailList);
			msiSurveyFeedbackVO.setQuestionImgMap(questionMap);
			if(approvalMsiSurveyFeedback!=null){
				msiSurveyFeedbackVO.setFeedbackId(approvalMsiSurveyFeedback.getFeedbackId());
				msiSurveyFeedbackService.updateAppleMsiSurveyData(clientId,clientUserId,msiSurveyFeedbackVO);
			}else{
				msiSurveyFeedbackVO.setDataType(Constants.APPLE_DATA_TYPE_APPROVAL);
				msiSurveyFeedbackService.addAppleMsiSurveyFeedback(clientId,clientUserId,msiSurveyFeedbackVO);
			}
			return ResultMessage.SAVE_SUCCESS_RESULT;
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.CHECK_FAIL_RESULT;
		}
	}
	
	@RequestMapping(value = "/showDetailAppleExamMsiSurveyData/{feedbackId}")
	public String showDetailAppleExamMsiSurveyData(Model model, @PathVariable("feedbackId")String feedbackId, String storeId) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId();
		Store store = storeService.findStoreByStoreId(storeId);
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackById(feedbackId);
		MsiSurvey msiSurvey = msiSurveyService.getMsiSurveyByStoreIdAndClientId(clientId, storeId);
		List<MsiQuestionVO> msiQuestionVOList = null;
		if(msiSurveyFeedback != null) {
			msiQuestionVOList = msiQuestionService.getDetailAppleExamMsiSurveyData(msiSurveyFeedback.getMsiSurveyId(),feedbackId);
		}
		String reFtl = "/survey/showDetailAppleExamMsiSurveyData";
		model.addAttribute("storeNo", store.getStoreNo());
		model.addAttribute("storeName", store.getStoreName());
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("msiSurvey", msiSurvey);
		model.addAttribute("msiSurveyFeedback", msiSurveyFeedback);
		model.addAttribute("msiQuestionVOList", msiQuestionVOList);
		return reFtl;
	}
	
	@RequestMapping("/exportPDF/{feedbackId}")
	public void exportPDF(HttpServletResponse resp,Model model, @PathVariable("feedbackId")String feedbackId, String storeId, String storeNo,String storeName){
		int clientUserId = getCurrentUserId();
		int clientId = getClientId();
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackById(feedbackId);
		MsiSurvey msiSurvey = msiSurveyService.findAppleMsiSurveyListByType(clientId, storeId);
		if(msiSurvey == null){
			msiSurvey = msiSurveyService.findAppleMsiSurveyListByStore(clientId, storeId);
		}
		List<MsiQuestionGroup> msiQuestionGroups = null;
		if(msiSurveyFeedback != null) {
			msiQuestionGroups = msiQuestionGroupService.findEntitysByMsiSurveyId(clientId, msiSurveyFeedback.getMsiSurveyId(), null);
			for(MsiQuestionGroup msiQuestionGroup : msiQuestionGroups){
				if(!msiQuestionGroup.getChildrenList().isEmpty()){
					for(MsiQuestionGroup childrenMsiQuestionGroup : msiQuestionGroup.getChildrenList()){
						List<MsiQuestionVO> msiQuestionVOList =  msiQuestionService.getAppleMsiQuestionListWithChecked(clientId,msiSurveyFeedback.getMsiSurveyId(),feedbackId,null,childrenMsiQuestionGroup.getQuestionGroupId());
						childrenMsiQuestionGroup.setMsiQuestionVos(msiQuestionVOList);
					}
				}
			}
		}
		MsiSurveyFeedback approvalMsiSurveyFeedback = msiSurveyFeedbackService.findApprovalDataByStoreIdAndDataType(clientId,storeId,msiSurveyFeedback.getMsiSurveyId(),Constants.APPLE_DATA_TYPE_APPROVAL);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(approvalMsiSurveyFeedback!=null){
			dataMap.put("approvalMsiSurveyFeedback", approvalMsiSurveyFeedback);
		}
		dataMap.put("clientId", clientId);
		dataMap.put("clientUserId", clientUserId);
		dataMap.put("storeId", storeId);
		dataMap.put("storeNo", storeNo);
		dataMap.put("storeName", storeName);
		dataMap.put("msiSurvey", msiSurvey);
		dataMap.put("msiSurveyFeedback", msiSurveyFeedback);
		dataMap.put("msiQuestionGroups", msiQuestionGroups);
		String channelPlus_domain =  PropertiesHelper.getInstance().getProperty(PropertiesHelper.CHANNELPLUS_DOMAIN);
		dataMap.put("domain", channelPlus_domain);
		try{
			String excelName = "问卷二期_"+System.currentTimeMillis();
			resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".pdf");
			resp.setContentType("application/vnd.ms-excel");
			PDFCoreSupport.createPDF(dataMap, "apple_care_spot_check.ftl", resp.getOutputStream());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 申诉
	 * @param model
	 * @param feedbackId
	 * @param storeId
	 * @param storeNo
	 * @param storeName
	 * @param view
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/appealMsiSurveyData/{feedbackId}")
	public String appealMsiSurveyData(Model model, @PathVariable("feedbackId")String feedbackId, String storeId, String storeNo,String storeName,String view) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackById(feedbackId);
		MsiSurveyFeedback approvalMsiSurveyFeedback = msiSurveyFeedbackService.findApprovalDataByStoreIdAndDataType(clientId,storeId,msiSurveyFeedback.getMsiSurveyId(),Constants.APPLE_DATA_TYPE_APPROVAL);
		MsiSurveyFeedback appealMsiSurveyFeedback = msiSurveyFeedbackService.findApprovalDataByStoreIdAndDataType(clientId,storeId,msiSurveyFeedback.getMsiSurveyId(),Constants.APPLE_DATA_TYPE_APPEAL);
		//List<MsiSurveyFeedback> vmiMsiSurveyFeedbacks = msiSurveyFeedbackService.findAppleExamFeedbackByStoreId(clientId, storeId, 6);
		//List<MsiSurveyFeedback> maintainMsiSurveyFeedbacks = msiSurveyFeedbackService.findAppleExamFeedbackByStoreId(clientId, storeId, 7);
		MsiSurvey msiSurvey = msiSurveyService.findAppleMsiSurveyListByType(clientId, storeId);
		if(msiSurvey == null){
			msiSurvey = msiSurveyService.findAppleMsiSurveyListByStore(clientId, storeId);
		}
		List<MsiQuestionGroup> msiQuestionGroups = null;
		if(msiSurveyFeedback != null) {
			msiQuestionGroups = msiQuestionGroupService.findEntitysByMsiSurveyId(clientId, msiSurveyFeedback.getMsiSurveyId(), null);
			for(MsiQuestionGroup msiQuestionGroup : msiQuestionGroups){
				if(!msiQuestionGroup.getChildrenList().isEmpty()){
					for(MsiQuestionGroup childrenMsiQuestionGroup : msiQuestionGroup.getChildrenList()){
						List<MsiQuestionVO> msiQuestionVOList =  msiQuestionService.getAppleMsiQuestionListWithChecked(clientId,msiSurveyFeedback.getMsiSurveyId(),feedbackId,null,childrenMsiQuestionGroup.getQuestionGroupId());
						childrenMsiQuestionGroup.setMsiQuestionVos(msiQuestionVOList);
					}
				}
			}
		}
		if(approvalMsiSurveyFeedback!=null){
			model.addAttribute("approvalMsiSurveyFeedback", approvalMsiSurveyFeedback);
		}
		if(appealMsiSurveyFeedback!=null){
			model.addAttribute("appealMsiSurveyFeedback", appealMsiSurveyFeedback);
		}
		//model.addAttribute("vmiMsiSurveyFeedbacks", vmiMsiSurveyFeedbacks);
		//model.addAttribute("maintainMsiSurveyFeedbacks", maintainMsiSurveyFeedbacks);
		model.addAttribute("view", view);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("msiSurvey", msiSurvey);
		model.addAttribute("msiSurveyFeedback", msiSurveyFeedback);
		model.addAttribute("msiQuestionGroups", msiQuestionGroups);
		return "/survey/appealAppleMsiSurveyData";
	}
	
	@RequestMapping(value = "/appealAppleMsiSurveyData")
	@ResponseBody
	public Object appealAppleMsiSurveyData(MsiSurveyFeedbackVO msiSurveyFeedbackVO,HttpServletRequest request) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		MsiSurveyFeedback approvalMsiSurveyFeedback = msiSurveyFeedbackService.findApprovalDataByStoreIdAndDataType(clientId,msiSurveyFeedbackVO.getStoreId(),msiSurveyFeedbackVO.getMsiSurveyId(),Constants.APPLE_DATA_TYPE_APPEAL);
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findApprovalDataByStoreIdAndDataType(clientId,msiSurveyFeedbackVO.getStoreId(),msiSurveyFeedbackVO.getMsiSurveyId(),Constants.APPLE_DATA_TYPE_VISIT);
		try {
			@SuppressWarnings("unchecked")
			Enumeration<String> enu = request.getParameterNames();
			List<MsiSurveyFeedbackDetail> detailList = new ArrayList<MsiSurveyFeedbackDetail>();
			Map<Integer, String> questionMap = new HashMap<Integer, String>();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				String[] temp = name.split("_");
				String value = request.getParameter(name);
				if(name.contains("init_")){                 //原始访问员填写的数据不保存
					continue;
				}
				if(StringUtil.isEmptyString(value)){
					continue;
				}
				if(name.indexOf("checked_")>=0){
					if(value.equals("true")){
						Integer msiQuestionId = Integer.parseInt(temp[1]);
						MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
						Integer msiAnswerId = Integer.parseInt(temp[2]);
						detail.setMsiAnswerId(msiAnswerId);
						detail.setClientId(super.getClientId());
						if(approvalMsiSurveyFeedback!=null){
							detail.setFeedbackId(approvalMsiSurveyFeedback.getFeedbackId());
						}
						detail.setDetailId(UUID.randomUUID().toString());
						detail.setMsiQuestionId(msiQuestionId);
						detail.setIsDelete(Constants.IS_DELETE_FALSE);
						detailList.add(detail);
					}
				}else if(name.indexOf("img_")>=0){
					questionMap.put(new Integer(temp[1]), value);
				}else if(temp.length==3){
					MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
					Integer msiQuestionId = Integer.parseInt(temp[0]);
					Integer msiAnswerId = Integer.parseInt(temp[1]);
					Integer msiSurveyParameterId = Integer.parseInt(temp[2]);
					detail.setMsiAnswerId(msiAnswerId);
					detail.setMsiQuestionId(msiQuestionId);
					detail.setCol1(value);
					detail.setCol2(msiSurveyParameterId.toString());
					detail.setClientId(super.getClientId());
					if(approvalMsiSurveyFeedback!=null){
						detail.setFeedbackId(approvalMsiSurveyFeedback.getFeedbackId());
					}
					detailList.add(detail);
				}else if(temp.length == 2){
					MsiSurveyFeedbackDetail detail = new MsiSurveyFeedbackDetail();
					Integer msiQuestionId = Integer.parseInt(temp[0]);
					detail.setMsiQuestionId(msiQuestionId);
					detail.setCol1(value);
					detail.setClientId(super.getClientId());
					if(approvalMsiSurveyFeedback!=null){
						detail.setFeedbackId(approvalMsiSurveyFeedback.getFeedbackId());
					}
					detailList.add(detail); 
				}
			}
			msiSurveyFeedbackVO.setMsiSurveyFeedbackDetails(detailList);
			msiSurveyFeedbackVO.setQuestionImgMap(questionMap);
			if(approvalMsiSurveyFeedback!=null){
				msiSurveyFeedbackVO.setFeedbackId(approvalMsiSurveyFeedback.getFeedbackId());
				msiSurveyFeedbackService.updateAppleMsiSurveyData(clientId,clientUserId,msiSurveyFeedbackVO);
				msiSurveyFeedback.setStatus((byte)1);				//申诉状态
				msiSurveyFeedbackService.updateMsiSurveyFeedback(msiSurveyFeedback);
			}else{
				msiSurveyFeedbackVO.setDataType(Constants.APPLE_DATA_TYPE_APPEAL);
				msiSurveyFeedback.setStatus((byte)1);				//申诉状态
				msiSurveyFeedbackService.addAppleMsiSurveyFeedback(clientId,clientUserId,msiSurveyFeedbackVO);
				msiSurveyFeedbackService.updateMsiSurveyFeedback(msiSurveyFeedback);
			}
			return ResultMessage.SAVE_SUCCESS_RESULT;
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.CHECK_FAIL_RESULT;
		}
	}
}
