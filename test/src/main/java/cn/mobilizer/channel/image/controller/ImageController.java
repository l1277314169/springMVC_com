package cn.mobilizer.channel.image.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.AttachmentDownload;
import cn.mobilizer.channel.base.service.AttachmentDownloadService;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.image.service.ImageService;
import cn.mobilizer.channel.image.vo.ColgateImageVo;
import cn.mobilizer.channel.image.vo.ImageGlobal;
import cn.mobilizer.channel.image.vo.ImageView;
import cn.mobilizer.channel.image.vo.ZipSupport;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.survey.service.SurveyService;
import cn.mobilizer.channel.survey.vo.SurveyParam;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;


@Controller
@RequestMapping(value = "/image")
public class ImageController extends BaseActionSupport{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private ImageService imageService;
	@Autowired
	private SurveyService surveyService;
	@Autowired
	private  AttachmentDownloadService attachmentDownloadService;
	
	/**
	 * 照片墙
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/query")
	public String query(Model model,String storeId,String startDate,String endDate,String storeNo) throws Exception{
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)){
			Date currDate = DateTimeUtils.getCurrentDate();
			Date currDate7 = DateTimeUtils.addDays(currDate, ReportGlobal.Day.A7);
			startDate = DateTimeUtils.formatTime(currDate, DateTimeUtils.yyyyMMdd);
			endDate = DateTimeUtils.formatTime(currDate7, DateTimeUtils.yyyyMMdd);
		}
		if(StringUtils.isNotEmpty(storeId) || StringUtils.isNotEmpty(storeNo)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId",super.getClientId());
			params.put("isDelete",Constants.IS_DELETE_FALSE);
			params.put("storeId", storeId);
			params.put("storeNo", storeNo);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			List<ImageView> images = imageService.getImages(params);
			model.addAttribute("images", images);
		}
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeNo", storeNo);
		return "/image/imageList";
	}
	
	/**
	 * 图片压缩下载
	 * @param model
	 * @param storeId
	 * @param startDate
	 * @param endDate
	 * @throws Exception 
	 */
	@RequestMapping(value = "/download")
	public void download(String emain,Model model,String storeId,String startDate,String endDate,String storeNo,HttpServletResponse response) throws Exception{
		OutputStream out = null;
		try {
			if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)){
				Date currDate = DateTimeUtils.getCurrentDate();
				Date currDate7 = DateTimeUtils.addDays(currDate, ReportGlobal.Day.A7);
				startDate = DateTimeUtils.formatTime(currDate, DateTimeUtils.yyyyMMdd);
				endDate = DateTimeUtils.formatTime(currDate7, DateTimeUtils.yyyyMMdd);
			}
			//if(StringUtils.isNotEmpty(storeId) || StringUtils.isNotEmpty(storeNo)){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId",super.getClientId());
				params.put("isDelete",Constants.IS_DELETE_FALSE);
				params.put("storeId", storeId);
				params.put("startDate", startDate);
				params.put("endDate", endDate);
				params.put("storeNo", storeNo);
				
				response.reset();
				response.setContentType("application/x-msdownload");
				out = response.getOutputStream();
				 //文件名编码成UTF-8
				response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(ImageGlobal.Global.ZIP_NAME+".zip",ReportGlobal.CHARTSET));
				
				List<ImageView> images = imageService.getImages(params);
				ZipSupport.getInstance().zipVisitingImage(images, ImageGlobal.Global.ZIP_NAME, out);
			//}
		} finally{
			if(null!=out){
				out.close();
			}
		}
	}
	
	/**
	 * web端图片压缩处理
	 */
	@RequestMapping(value = "/webImageProcess")
	public void webImageProcess() throws Exception{
		imageService.processWebImage();
	}
	
	/**
	 * 问卷照片查看
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/querySurveyImage")
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

		return "/image/surveyImageList";
	}
	
	/**
	 * 问卷照片查看
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/queryDefaultSurveyImage")
	public String queryDefaultSurveyImage(Model model,HttpServletRequest request,SurveyParam params) throws Exception{
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

		Map<String, List<ColgateImageVo>> images = imageService.selectSurveyDefaultImage(parameters);

		model.addAttribute("params",params);
		model.addAttribute("images", images);

		return "/image/surveyDefaultImageList";
	}
	
	
	/**
	 *图片压缩下载
	 * @throws Exception 
	 *//*
	@RequestMapping(value = "/downloadImage")
	public void downloadColgatImage(Model model,HttpServletRequest request,SurveyParam params,HttpServletResponse response) throws Exception{
		OutputStream out = null;
		try {
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

			response.reset();
			response.setContentType("application/x-download");
			out = response.getOutputStream();
			 //文件名编码成UTF-8
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("colgate.zip",ReportGlobal.CHARTSET));
			
			Map<String, List<ColgateImageVo>> images = imageService.selectSurveyImageList(parameters);
			
			ZipSupport.getInstance().zipFileByNameColgate(images, "colgate", params.getFeedbackDate(),out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		}
	}*/
	
	
	/**
	 *高露洁图片压缩下载
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadColgatImage")
	@ResponseBody
	public ResultMessage downloadColgatImage(String email,Model model, Integer page, HttpServletRequest request,SurveyParam params) throws Exception  {
		OutputStream out = null;
		ResultMessage messsge = null;	 
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONObject json =JSONObject.fromObject(params, jsonConfig);
		String filter=json.toString();	
		try {		
		//	String dateStr = DateTimeUtils.formatTime(DateTimeUtils.getCurrentDate(), DateTimeUtils.yyyyMMdd2);
			String fileName = "高露洁_"+System.currentTimeMillis();        
			AttachmentDownload attachmentDownload =new AttachmentDownload();
			attachmentDownload.setClientId(super.getClientId());
			attachmentDownload.setAttachmentId(UUID.randomUUID().toString());
			attachmentDownload.setEmail(email);
			attachmentDownload.setAttachmentName(fileName);
			attachmentDownload.setFilter(filter);
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
	
}