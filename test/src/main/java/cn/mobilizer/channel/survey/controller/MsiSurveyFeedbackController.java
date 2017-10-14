/**
 * 
 */
package cn.mobilizer.channel.survey.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.mobilizer.channel.survey.po.MsiSurvey;
import cn.mobilizer.channel.survey.po.MsiSurveyComplain;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedback;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedbackAttachment;
import cn.mobilizer.channel.survey.po.MsiSurveyType;
import cn.mobilizer.channel.survey.service.MsiQuestionService;
import cn.mobilizer.channel.survey.service.MsiSurveyComplainService;
import cn.mobilizer.channel.survey.service.MsiSurveyFeedbackService;
import cn.mobilizer.channel.survey.service.MsiSurveyService;
import cn.mobilizer.channel.survey.service.MsiSurveyTypeService;
import cn.mobilizer.channel.survey.vo.ExportMsiSurveyFeedbackVo;
import cn.mobilizer.channel.survey.vo.MsiQuestionVO;
import cn.mobilizer.channel.survey.vo.MsiSurveyFeedbackVO;
import cn.mobilizer.channel.survey.vo.SaveMsiSurveyComplainVo;
import cn.mobilizer.channel.util.Constants;


/**
 * 暗访任务数据Controller
 * @author yeeda.tian 2015-6-10
 */
@Controller
@RequestMapping(value = "/msiSurveyFeedback")
public class MsiSurveyFeedbackController extends BaseActionSupport {


	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6832956525931054754L;
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
	
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, HttpServletRequest req, String storeNo, String storeName,String promoter,Integer provinceId,Integer cityId,String startDate, String endDate,Integer clientStructureId) throws BusinessException{
		String reFtl = "/survey/msiSurveyFeedbackList";
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
		parameters.put("deptIds", deptIds);
		parameters.put("storeNo", storeNo);
		parameters.put("storeName", storeName);
		parameters.put("promoter", promoter);
		parameters.put("provinceId", provinceId);
		parameters.put("cityId", cityId);
		parameters.put("startDate", DateUtil.getDayStart(startDate));
		parameters.put("endDate", DateUtil.getDayEnd(endDate));
		int count = msiSurveyFeedbackService.queryMsiSurveyFeedbackCount(parameters);
		int pagenum = page == null ? 1 : page;
		Page<MsiSurveyFeedback> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<MsiSurveyFeedback> list = msiSurveyFeedbackService.queryMsiSurveyFeedbackList(parameters);
		pageParam.setItems(list);
		
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("promoter", promoter);
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
		String reFtl = "/survey/msiStoreList";
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
	
	@RequestMapping(value = "/showAddMsiSurveyData/{storeId}/{storeNo}/{storeName}")
	public String showAddMsiSurveyData(Model model, @PathVariable("storeId")String storeId, @PathVariable("storeNo")String storeNo,@PathVariable("storeName")String storeName) throws BusinessException{
		String reFtl = "/survey/showAddMsiSurveyData";
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
//		Integer point = null;
		Integer msiSurveyId = null;
		List<MsiQuestionVO> msiQuestionVOList = null;
		MsiSurvey msiSurvey = msiSurveyService.getMsiSurveyByStoreIdAndClientId(clientId, storeId);
		if(msiSurvey != null && msiSurvey.getMsiSurveyId() !=null) {
			List<MsiSurveyFeedback> msiSurveyFeedbacks = msiSurveyFeedbackService.getMsiSurveyFeedbackByPresentCycle(msiSurvey.getCycleType(),clientId,clientUserId,msiSurvey.getMsiSurveyId(),storeId);
			if(msiSurveyFeedbacks!=null && msiSurveyFeedbacks.size()>0 && msiSurvey.getMsiSurveyId().intValue()==1){
				MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbacks.get(0);
				return "redirect:/msiSurveyFeedback/showEditMsiSurveyData/"+msiSurveyFeedback.getFeedbackId()+"?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName; 
			} 
			msiQuestionVOList = msiQuestionService.getMsiQuestionListByMsiSurveyId(msiSurvey.getMsiSurveyId());
			msiSurveyId = msiSurvey.getMsiSurveyId();
			if(msiSurvey.getMsiSurveyTypeId().intValue()==Constants.MSI_SURVEY_TYPE_COMMON){			//普通店问卷 
				String promoterNoStrs = "";
				if(msiSurveyFeedbacks!=null && msiSurveyFeedbacks.size()<5){
					for(MsiSurveyFeedback msiSurveyFeedback : msiSurveyFeedbacks){
						promoterNoStrs = promoterNoStrs+","+msiSurveyFeedback.getPromoterNo();
					}
					promoterNoStrs = StringUtil.removeStrComma(promoterNoStrs);
					//获取已经填写数据的工号
					String userNoStrs = clientUserService.selectUserNoByStoreUser(clientId,storeId);
					promoterNoStrs = promoterNoStrs==null?"":promoterNoStrs;
					userNoStrs = userNoStrs==null?"":userNoStrs;
					//获取userNos中存在的，而promoterNos中不存在的Id
					String subUserNoStrs = ArrayUtil.arraySubtract2Str(userNoStrs.split(","),promoterNoStrs.split(","));
					//去掉前后的“,”
					subUserNoStrs = StringUtil.removeStrComma(subUserNoStrs);
					String userNoStr = promoterNoStrs+","+subUserNoStrs;
					userNoStr = StringUtil.removeStrComma(userNoStr);
					String[] userNos = userNoStr.split(",");
					String[] subUserNos;
					if(StringUtils.isEmpty(subUserNoStrs)){
						subUserNos = new String[]{};
					}else{
						subUserNos = subUserNoStrs.split(",");
					}
					for(int i = 0; i<subUserNos.length; i++){
						MsiSurveyFeedback msiSurveyFeedbackInfo = new MsiSurveyFeedback();
						ClientUser clientUser = clientUserService.selectClientUserByUserNo(clientId,subUserNos[i]);
						promoterNoStrs=promoterNoStrs+","+subUserNos[i];
						if(clientUser!=null){
							msiSurveyFeedbackInfo.setPromoter(clientUser.getName());
						}
						msiSurveyFeedbackInfo.setPromoterNo(subUserNos[i]);
						msiSurveyFeedbackInfo.setMsiSurveyId(msiSurveyId);
						msiSurveyFeedbackInfo.setStoreId(storeId);
						msiSurveyFeedbacks.add(msiSurveyFeedbackInfo);
					}
					if(msiSurveyFeedbacks.size()>5){   //只取5个
						msiSurveyFeedbacks = msiSurveyFeedbacks.subList(0, 5);
					}
					//如果不足五个工号则自动生成虚拟人员
					List<String> list = new ArrayList<String>();
					list.add("促销员A");
					list.add("促销员B");
					list.add("促销员C");
					list.add("促销员D");
					list.add("促销员E");
					promoterNoStrs = StringUtil.removeStrComma(userNoStr);
					int j = list.size()-(StringUtils.isEmpty(promoterNoStrs)?0:promoterNoStrs.split(",").length);
					for(int i = 0; i<j; i++){
						MsiSurveyFeedback msiSurveyFeedbackInfo = new MsiSurveyFeedback();
						msiSurveyFeedbackInfo.setPromoter(list.get(i));
						msiSurveyFeedbackInfo.setMsiSurveyId(msiSurveyId);
						msiSurveyFeedbackInfo.setStoreId(storeId);
						msiSurveyFeedbacks.add(msiSurveyFeedbackInfo);
					}
				}else{
					msiSurveyFeedbacks = msiSurveyFeedbacks.subList(0, 5); 
				}
				model.addAttribute("msiSurveyFeedbacks", msiSurveyFeedbacks);
				reFtl = "/survey/showGeneralAddMsiSurveyData";
			}
		}
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("msiQuestionVOList", msiQuestionVOList);
		model.addAttribute("msiSurvey", msiSurvey);
		model.addAttribute("msiSurveyId", msiSurveyId);
		return reFtl;
	}
	
	@RequestMapping(value = "/showAddHistoryMsiSurveyData/{storeId}/{storeNo}/{storeName}/{msiSurveyId}")
	public String showAddHistoryMsiSurveyData(Model model, @PathVariable("storeId")String storeId, @PathVariable("msiSurveyId")Integer msiSurveyId,@PathVariable("storeName")String storeName,@PathVariable("storeNo")String storeNo) throws BusinessException{
		String reFtl = "/survey/showAddMsiSurveyData";
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
//		Integer point = null;
		List<MsiQuestionVO> msiQuestionVOList = null;
		MsiSurvey msiSurvey = msiSurveyService.selectByPrimaryKey(msiSurveyId);
		if(msiSurvey != null && msiSurvey.getMsiSurveyId() !=null) {
			List<MsiSurveyFeedback> msiSurveyFeedbacks = msiSurveyFeedbackService.getMsiSurveyFeedbackByPresentCycle(msiSurvey.getCycleType(),clientId,clientUserId,msiSurvey.getMsiSurveyId(),storeId);
			msiQuestionVOList = msiQuestionService.getMsiQuestionListByMsiSurveyId(msiSurvey.getMsiSurveyId());
			msiSurveyId = msiSurvey.getMsiSurveyId();
			if(msiSurvey.getMsiSurveyTypeId().intValue()==Constants.MSI_SURVEY_TYPE_COMMON){			//普通店问卷 
				String promoterNoStrs = "";
				if(msiSurveyFeedbacks!=null && msiSurveyFeedbacks.size()<5){
					for(MsiSurveyFeedback msiSurveyFeedback : msiSurveyFeedbacks){
						promoterNoStrs = promoterNoStrs+","+msiSurveyFeedback.getPromoterNo();
					}
					promoterNoStrs = StringUtil.removeStrComma(promoterNoStrs);
					//获取已经填写数据的工号
					String userNoStrs = clientUserService.selectUserNoByStoreUser(clientId,storeId);
					promoterNoStrs = promoterNoStrs==null?"":promoterNoStrs;
					userNoStrs = userNoStrs==null?"":userNoStrs;
					//获取userNos中存在的，而promoterNos中不存在的Id
					String subUserNoStrs = ArrayUtil.arraySubtract2Str(userNoStrs.split(","),promoterNoStrs.split(","));
					//去掉前后的“,”
					subUserNoStrs = StringUtil.removeStrComma(subUserNoStrs);
					String userNoStr = promoterNoStrs+","+subUserNoStrs;
					userNoStr = StringUtil.removeStrComma(userNoStr);
					String[] userNos = userNoStr.split(",");
					String[] subUserNos;
					if(StringUtils.isEmpty(subUserNoStrs)){
						subUserNos = new String[]{};
					}else{
						subUserNos = subUserNoStrs.split(",");
					}
					for(int i = 0; i<subUserNos.length; i++){
						MsiSurveyFeedback msiSurveyFeedbackInfo = new MsiSurveyFeedback();
						ClientUser clientUser = clientUserService.selectClientUserByUserNo(clientId,subUserNos[i]);
						promoterNoStrs=promoterNoStrs+","+subUserNos[i];
						if(clientUser!=null){
							msiSurveyFeedbackInfo.setPromoter(clientUser.getName());
						}
						msiSurveyFeedbackInfo.setPromoterNo(subUserNos[i]);
						msiSurveyFeedbackInfo.setMsiSurveyId(msiSurveyId);
						msiSurveyFeedbackInfo.setStoreId(storeId);
						msiSurveyFeedbacks.add(msiSurveyFeedbackInfo);
					}
					if(msiSurveyFeedbacks.size()>5){   //只取5个
						msiSurveyFeedbacks = msiSurveyFeedbacks.subList(0, 5);
					}
					//如果不足五个工号则自动生成虚拟人员
					List<String> list = new ArrayList<String>();
					list.add("促销员A");
					list.add("促销员B");
					list.add("促销员C");
					list.add("促销员D");
					list.add("促销员E");
					promoterNoStrs = StringUtil.removeStrComma(userNoStr);
					int j = list.size()-(StringUtils.isEmpty(promoterNoStrs)?0:promoterNoStrs.split(",").length);
					for(int i = 0; i<j; i++){
						MsiSurveyFeedback msiSurveyFeedbackInfo = new MsiSurveyFeedback();
						msiSurveyFeedbackInfo.setPromoter(list.get(i));
						msiSurveyFeedbackInfo.setMsiSurveyId(msiSurveyId);
						msiSurveyFeedbackInfo.setStoreId(storeId);
						msiSurveyFeedbacks.add(msiSurveyFeedbackInfo);
					}
				}else{
					msiSurveyFeedbacks = msiSurveyFeedbacks.subList(0, 5); 
				}
				model.addAttribute("msiSurveyFeedbacks", msiSurveyFeedbacks);
				reFtl = "/survey/showGeneralAddMsiSurveyData";
			}
		}
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("msiQuestionVOList", msiQuestionVOList);
		model.addAttribute("msiSurvey", msiSurvey);
		model.addAttribute("msiSurveyId", msiSurveyId);
		return reFtl;
	}
	
	@RequestMapping(value = "/showSubEditMsiSurveyData")
	public String showSubEditMsiSurveyData(Model model,String storeId,Integer msiSurveyId,String feedbackId,String promoterNo){
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		MsiSurveyFeedback msiSurveyFeedback = null;
		List<MsiQuestionVO> msiQuestionVOList = null;
		List<MsiSurveyFeedbackAttachment> attachmentList= null;
		Store store = storeService.selectByPrimaryKey(storeId);
		StringBuffer attString = new StringBuffer();
		String retStr="";
		MsiSurvey msiSurvey = msiSurveyService.getMsiSurveyByStoreIdAndClientId(clientId, storeId);
		if(StringUtil.isNotEmptyString(feedbackId)){
			retStr = "/survey/showSubEditMsiSurveyData";
			msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackById(feedbackId);
			msiQuestionVOList = msiQuestionService.getMsiQuestionListWithChecked(msiSurveyFeedback.getMsiSurveyId(),feedbackId);
			attachmentList = msiSurveyFeedbackService.getMsiSurveyFeedbackAttachmentList(feedbackId,Constants.MSI_SURVEY_IMG);
			if(null!=attachmentList && !attachmentList.isEmpty()){
				for (MsiSurveyFeedbackAttachment att : attachmentList) {
					attString.append(att.getAttachment()).append(",");
				}
			}
			if(attString.toString().endsWith(",")){
				attString.deleteCharAt(attString.length()-1);
			}
		}else{
			retStr = "/survey/showSubAddMsiSurveyData";
			if(msiSurvey != null && msiSurvey.getMsiSurveyId() !=null) {
				msiQuestionVOList = msiQuestionService.getMsiQuestionListByMsiSurveyId(msiSurvey.getMsiSurveyId());
			}
		}
		if(StringUtil.isNotEmptyString(promoterNo)){
			ClientUser clientUser = clientUserService.selectClientUserByUserNo(clientId,promoterNo);
			model.addAttribute("clientUser", clientUser);
		}
		model.addAttribute("msiSurvey", msiSurvey);
		model.addAttribute("store", store);
		model.addAttribute("attString", attString);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("msiSurveyFeedback", msiSurveyFeedback);
		model.addAttribute("msiQuestionVOList", msiQuestionVOList);
		model.addAttribute("attachmentList", attachmentList);
		return retStr;
	}
	
	@RequestMapping(value = "/showEditMsiSurveyData/{feedbackId}")
	public String showEditMsiSurveyData(Model model, @PathVariable("feedbackId")String feedbackId, String storeId, String storeNo,String storeName,String view) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackById(feedbackId);
		MsiSurvey msiSurvey = msiSurveyService.getMsiSurveyByStoreIdAndClientId(clientId, storeId);
		List<MsiQuestionVO> msiQuestionVOList = null;
		List<MsiSurveyFeedbackAttachment> attachmentList= null;
		StringBuffer attString = new StringBuffer();
		if(msiSurveyFeedback != null) {
//			List<MsiSurveyFeedbackDetail> list = msiSurveyFeedbackService.findMsiSurveyFeedbackDetailListByFeedbackId(feedbackId);
			msiQuestionVOList = msiQuestionService.getMsiQuestionListWithChecked(msiSurveyFeedback.getMsiSurveyId(),feedbackId);
			attachmentList = msiSurveyFeedbackService.getMsiSurveyFeedbackAttachmentList(feedbackId,Constants.MSI_SURVEY_IMG);
			if(null!=attachmentList && !attachmentList.isEmpty()){
				for (MsiSurveyFeedbackAttachment att : attachmentList) {
					attString.append(att.getAttachment()).append(",");
				}
			}
			if(attString.toString().endsWith(",")){
				attString.deleteCharAt(attString.length()-1);
			}
		}
		String reFtl = null;
		if(StringUtil.isEmptyString(view)){
			reFtl = "/survey/showEditMsiSurveyData";
		}else{
			reFtl = "/survey/showMsiSurveyData";
		}
		model.addAttribute("attString", attString);
		model.addAttribute("view", view);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("msiSurvey", msiSurvey);
		model.addAttribute("msiSurveyFeedback", msiSurveyFeedback);
		model.addAttribute("msiQuestionVOList", msiQuestionVOList);
		model.addAttribute("attachmentList", attachmentList);
		return reFtl;
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
		List<MsiQuestionVO> msiQuestionVOList = null;
		List<MsiSurveyFeedbackAttachment> attachmentList= null;     //申诉图片
		List<MsiSurveyFeedbackAttachment> attachmentList2= null; 	//问卷图片
		StringBuffer attString = new StringBuffer();
		if(msiSurveyFeedback != null) {
			msiQuestionVOList = msiQuestionService.getMsiQuestionListWithChecked(msiSurveyFeedback.getMsiSurveyId(),feedbackId);
			attachmentList = msiSurveyFeedbackService.getMsiSurveyFeedbackAttachmentList(feedbackId,Constants.MSI_SURVEY_COMPLAIN_IMG);
			attachmentList2 = msiSurveyFeedbackService.getMsiSurveyFeedbackAttachmentList(feedbackId,Constants.MSI_SURVEY_IMG);
			if(null!=attachmentList && !attachmentList.isEmpty()){
				for (MsiSurveyFeedbackAttachment att : attachmentList) {
					attString.append(att.getAttachment()).append(",");
				}
			}
			if(attString.toString().endsWith(",")){
				attString.deleteCharAt(attString.length()-1);
			}
			List<MsiSurveyComplain> msiSurveyComplains = msiSurveyComplainService.getEntityByFeedbackId(feedbackId);
			Map<String,Object> parameterMap = new HashMap<String, Object>();
			for(MsiSurveyComplain msiSurveyComplain : msiSurveyComplains){
				//将数据以“_”拼接，用参数和对象来确定对象的参数值，供页面展现
				parameterMap.put(msiSurveyComplain.getMsiQuestionId().toString(), msiSurveyComplain);	
			}
			model.addAttribute("parameterMap", parameterMap);
		}
		model.addAttribute("attString", attString);
		model.addAttribute("view", view);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("msiSurveyFeedback", msiSurveyFeedback);
		model.addAttribute("msiQuestionVOList", msiQuestionVOList);
		model.addAttribute("attachmentList", attachmentList);
		model.addAttribute("attachmentList2", attachmentList2);
		return "/survey/appealMsiSurveyData";
	}
	
	@RequestMapping(value = "/approvalMsiSurveyData/{feedbackId}")
	public String approvalMsiSurveyData(Model model, @PathVariable("feedbackId")String feedbackId, String storeId, String storeNo,String storeName,String view) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		MsiSurveyFeedback msiSurveyFeedback = msiSurveyFeedbackService.findMsiSurveyFeedbackById(feedbackId);
		List<MsiQuestionVO> msiQuestionVOList = null;
		List<MsiSurveyFeedbackAttachment> attachmentList= null;     //申诉图片
		List<MsiSurveyFeedbackAttachment> attachmentList2= null; 	//问卷图片
		StringBuffer attString = new StringBuffer();
		if(msiSurveyFeedback != null) {
			msiQuestionVOList = msiQuestionService.getMsiQuestionListWithChecked(msiSurveyFeedback.getMsiSurveyId(),feedbackId);
			attachmentList = msiSurveyFeedbackService.getMsiSurveyFeedbackAttachmentList(feedbackId,Constants.MSI_SURVEY_COMPLAIN_IMG);
			attachmentList2 = msiSurveyFeedbackService.getMsiSurveyFeedbackAttachmentList(feedbackId,Constants.MSI_SURVEY_IMG);
			if(null!=attachmentList && !attachmentList.isEmpty()){
				for (MsiSurveyFeedbackAttachment att : attachmentList) {
					attString.append(att.getAttachment()).append(",");
				}
			}
			if(attString.toString().endsWith(",")){
				attString.deleteCharAt(attString.length()-1);
			}
			List<MsiSurveyComplain> msiSurveyComplains = msiSurveyComplainService.getEntityByFeedbackId(feedbackId);
			Map<String,Object> parameterMap = new HashMap<String, Object>();
			for(MsiSurveyComplain msiSurveyComplain : msiSurveyComplains){
				//将数据以“_”拼接，用参数和对象来确定对象的参数值，供页面展现
				parameterMap.put(msiSurveyComplain.getMsiQuestionId().toString(), msiSurveyComplain);	
			}
			model.addAttribute("parameterMap", parameterMap);
		}
		model.addAttribute("attString", attString);
		model.addAttribute("view", view);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("storeName", storeName);
		model.addAttribute("msiSurveyFeedback", msiSurveyFeedback);
		model.addAttribute("msiQuestionVOList", msiQuestionVOList);
		model.addAttribute("attachmentList", attachmentList);
		model.addAttribute("attachmentList2", attachmentList2);
		return "/survey/approvalMsiSurveyData";
	}
	
	@RequestMapping(value = "/addMsiSurveyData")
	@ResponseBody
	public Object addMsiSurveyData(MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		msiSurveyFeedbackService.addMsiSurveyFeedback(clientId,clientUserId,msiSurveyFeedbackVO);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	@RequestMapping(value = "/updateMsiSurveyData")
	@ResponseBody
	public Object updateMsiSurveyData(MsiSurveyFeedbackVO msiSurveyFeedbackVO) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		msiSurveyFeedbackService.updateMsiSurveyData(clientId,clientUserId,msiSurveyFeedbackVO);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	@RequestMapping(value="/saveMsiSurveyData")
	@ResponseBody
	public Map<String,Object> saveMsiSurveyData(MsiSurveyFeedbackVO msiSurveyFeedbackVO,String feedbackId,Integer msiSurveyId,String storeId){
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		Map<String,Object> map = new HashMap<String, Object>();
		MsiSurvey msiSurvey = msiSurveyService.getMsiSurveyByStoreIdAndClientId(clientId, storeId);
		List<MsiSurveyFeedback> msiSurveyFeedbacks = msiSurveyFeedbackService.getMsiSurveyFeedbackByPresentCycle(msiSurvey.getCycleType(),clientId,clientUserId,msiSurvey.getMsiSurveyId(),storeId);
		if(StringUtils.isEmpty(feedbackId)){
			feedbackId = msiSurveyFeedbackService.addMsiSurveyFeedback(clientId,clientUserId,msiSurveyFeedbackVO);
			map.put("feedbackId", feedbackId);
		}else{
			msiSurveyFeedbackService.updateMsiSurveyData(clientId,clientUserId,msiSurveyFeedbackVO);
		}
		return map;
	}
	
	@RequestMapping("/saveMsiSurveyComplain")
	@ResponseBody
	public Object saveMsiSurveyComplain(Model model,@RequestBody SaveMsiSurveyComplainVo saveMsiSurveyComplainVo) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId();
		msiSurveyFeedbackService.saveMsiSurveyComplain(clientId, clientUserId, saveMsiSurveyComplainVo);
		return ResultMessage.SAVE_SUCCESS_RESULT;
	}
	
	@RequestMapping("/updateMsiSurveyComplainStatus")
	@ResponseBody
	public Object updateMsiSurveyComplainStatus(String complainId,Byte status) throws BusinessException{
		int clientUserId = getCurrentUserId();
		int clientId = getClientId();
		msiSurveyComplainService.updateMsiSurveyComplainStatus(clientId, clientUserId, complainId,status);
		return ResultMessage.SAVE_SUCCESS_RESULT;
	}
	
	@RequestMapping("/deleteMsiSurveyData/{feedbackId}")
	@ResponseBody
	public Object deleteMsiSurveyData(@PathVariable("feedbackId")String feedbackId){
		msiSurveyFeedbackService.deleteByPrimaryKey(feedbackId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	@RequestMapping("/findHistoryMsiSurveyList/{storeId}")
	public String findHistoryMsiSurveyList(Model model,@PathVariable("storeId")String storeId){
		Store store = storeService.selectByPrimaryKey(storeId);
		List<MsiSurvey> msiSurveys = msiSurveyService.findHistoryMsiSurveyListByStore(storeId);
		model.addAttribute("msiSurveys", msiSurveys);
		model.addAttribute("store", store);
		return "/survey/historyMsiSurveyList";
	}
	
	@RequestMapping(value = "/exportMsiSurveyFeedback")
	public void exportMsiSurveyFeedback(ExportMsiSurveyFeedbackVo exportMsiSurveyFeedbackVo,HttpServletResponse resp) throws IOException{
		String deptIds = channelCommService.getSubStructrueIds(exportMsiSurveyFeedbackVo.getClientStructureId());
		String clientUserIds = channelCommService.getSubordinates(getCurrentUserId().toString());
		String subAllStructureId = channelCommService.getSubStructrueIds(getClientStructureId());
		exportMsiSurveyFeedbackVo.setClientId(getClientId());
		exportMsiSurveyFeedbackVo.setClientUserIds(clientUserIds);
		exportMsiSurveyFeedbackVo.setStructureIds(subAllStructureId);
		exportMsiSurveyFeedbackVo.setDeptIds(deptIds);
		List<?> lists = (List<?>)msiSurveyFeedbackService.exportMsiSurveyFeedback(exportMsiSurveyFeedbackVo);
		List<String> questionStrs = (List<String>) lists.get(0);										//获取第一个结果集
		String questionStr = questionStrs.get(0).replace("`","");		
		String questionSheetName = questionStr.split("@")[0]; 											//获取问题选项sheetName
		String[] questionTitles = questionStr.substring(questionStr.indexOf("@")+1, questionStr.length()).split(",");	//获取问题选项	
		List<Map> questionDataList = (List<Map>) lists.get(1);											 //获取第二个结果集
		
		/**创建一个Excel文件*/
		XSSFWorkbook  wb = new XSSFWorkbook();
		/**导出Excel文档名字*/
		String excelName = "问卷数据_"+System.currentTimeMillis();
		resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xlsx");
		resp.setContentType("application/vnd.ms-excel");
		
		/**在we中创建问题选项sheet*/
		XSSFSheet questionSheet = wb.createSheet(questionSheetName);
		/**在wTSSheet值添加表头(第0行)*/
		XSSFRow questionSheetRow = questionSheet.createRow((int)0);
		/**创建单元格，并设置表头，设置表头居中,背景颜色*/
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillBackgroundColor(HSSFColor.YELLOW.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		for(int i=0;i<questionTitles.length;i++){
			String cellTitle = questionTitles[i];
			XSSFCell cell = questionSheetRow.createCell(i);  
			cell.setCellStyle(style);
			cell.setCellValue(cellTitle);
		}
		if (questionDataList != null && questionDataList.size() >0) {
			for (int i = 0; i < questionDataList.size(); i++) {
				questionSheetRow = questionSheet.createRow((int) i + 1); 
				for(int j=0;j<questionTitles.length;j++){
					String cellTitle = questionTitles[j];
					questionSheetRow.createCell(j).setCellValue((questionDataList.get(i).get(cellTitle))==null?"":(questionDataList.get(i).get(cellTitle).toString())); 
				}
			}
			
		}
		
		List<String> questionScoreStrs = (List<String>) lists.get(2);										//获取第三个结果集
		String questionScoreStr = questionScoreStrs.get(0).replace("`","");	
		String questionScoreSheetName = questionScoreStr.split("@")[0]; 
		String[] questionScoreTitles = questionStr.substring(questionScoreStr.indexOf("@")+1, questionScoreStr.length()).split(",");	//获取问题分值	
		List<Map> questionScoreDataList = (List<Map>) lists.get(3);	
		
		/**在we中创建问题分值sheet*/
		XSSFSheet questionScoreSheet = wb.createSheet(questionScoreSheetName);
		/**在wTSSheet值添加表头(第0行)*/
		XSSFRow questionScoreRow = questionScoreSheet.createRow((int)0);
		/**创建单元格，并设置表头，设置表头居中,背景颜色*/
		for(int i=0;i<questionScoreTitles.length;i++){
			String cellTitle = questionScoreTitles[i];
			XSSFCell cell = questionScoreRow.createCell(i);  
			cell.setCellStyle(style);
			cell.setCellValue(cellTitle);
		}
		if (questionScoreDataList != null && questionScoreDataList.size() >0) {
			for (int i = 0; i < questionScoreDataList.size(); i++) {
				questionScoreRow = questionScoreSheet.createRow((int) i + 1); 
				for(int j=0;j<questionScoreTitles.length;j++){
					String cellTitle = questionScoreTitles[j];
					questionScoreRow.createCell(j).setCellValue((questionScoreDataList.get(i).get(cellTitle))==null?"":(questionScoreDataList.get(i).get(cellTitle).toString())); 
				}
			}
			
		}
		try {
			OutputStream out = resp.getOutputStream();
			wb.write(out);
			out.close();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}
