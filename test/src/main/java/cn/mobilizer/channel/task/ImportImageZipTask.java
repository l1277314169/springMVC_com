package cn.mobilizer.channel.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.mobilizer.channel.base.po.AttachmentDownload;
import cn.mobilizer.channel.base.service.AttachmentDownloadService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.image.service.ImageService;
import cn.mobilizer.channel.image.vo.ColgateImageVo;
import cn.mobilizer.channel.image.vo.ZipSupport;
import cn.mobilizer.channel.survey.service.SurveyService;
import cn.mobilizer.channel.survey.vo.SurveyImageVo;
import cn.mobilizer.channel.survey.vo.SurveyParam;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.PropertiesHelper;

public class ImportImageZipTask {

	private Log log = LogFactory.getLog(ImportImageZipTask.class);
	@Autowired
	private AttachmentDownloadService attachmentDownloadService;
	@Autowired
	private SurveyService surveyService;
	@Autowired
	private ImageService imageService;

	private static String importImageZip_enabled = null;

	static {
		importImageZip_enabled = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMPORTIMAGEZIP_ENABLED);
	}

	public void importImageZip() throws Exception {
		if (importImageZip_enabled.equals("true")) {
			process();
		}
	}

	private void process() throws Exception {
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			log.info("importImageZipTask begin.....");
			Map<String, Object> params1 = new HashMap<String, Object>();
			params1.put("status", 0);
			List<AttachmentDownload> attachmentDownloads = attachmentDownloadService .getEntitysByCreateidTime(params1);
			if (null != attachmentDownloads && !attachmentDownloads.isEmpty()) {
				for (AttachmentDownload attachmentDownload : attachmentDownloads) {
					int clientid = attachmentDownload.getClientId();
					String email = attachmentDownload.getEmail();
					String fileName = attachmentDownload.getAttachmentName();
					String filter = attachmentDownload.getFilter();
					JSONObject obj = JSONObject.fromObject(filter);// 将json字符串转换为json对象
					SurveyParam params = (SurveyParam) JSONObject.toBean(obj, SurveyParam.class);// 将建json对象转换为Model对象
					 
					if (clientid == 17) {
					 
						Map<String, Object> parameterApp = new HashMap<String, Object>();
						if (null != params && (null == params.getArg_start_date() || null == params .getArg_end_date())) {
							Date startDate = DateTimeUtils .getFirstDayOfCurrentMonth();
							Date endDate = DateTimeUtils .getLastDayOfCurrentMonth();
							params.setArg_start_date(DateTimeUtils.formatTime( 	startDate, DateTimeUtils.yyyyMMdd));
							params.setArg_end_date(DateTimeUtils.formatTime( endDate, DateTimeUtils.yyyyMMdd));
						}
						if (null != params && null == params.getArg_status()) {
							params.setArg_status(0);
						}
						params.setArg_client_id(clientid);
						parameterApp.put("storeNo", params.getArg_store_no());
						parameterApp.put("clientId", clientid);
						parameterApp .put("feedbackDate", params.getFeedbackDate());
						parameterApp.put("visitor", params.getArg_visitor());
						parameterApp.put("provinceId", params.getProvince_ids());
						parameterApp.put("cityId", params.getCity_ids());
						parameterApp.put("storeName", params.getArg_store_name());
						parameterApp .put("channelId", params.getArg_channel_ids());
						parameterApp.put("chainId", params.getArg_types());
						parameterApp.put("structureId", params.getArg_dept_ids());
						parameterApp.put("status", params.getArg_status());
						parameterApp.put("storeType", params.getArg_store_type());
						List<SurveyImageVo> imagesApp = surveyService .selectSurveyImages(parameterApp);
						boolean isOK = ZipSupport.getInstance().zipFileForAppleCare1(clientid, imagesApp,fileName);
						Map<String, Object> paramsAppId = new HashMap<String, Object>();
						paramsAppId.put("attachmentid", attachmentDownload.getAttachmentId());
						if (isOK) {
					    attachmentDownloadService.sendImageZipList( fileName, clientid, email, attachmentDownload,paramsAppId);
						}
					}else if (clientid == 15) {
							Map<String, Object> parametersCol = new HashMap<String, Object>();
							if (null != params && (null == params.getArg_start_date() || null == params .getArg_end_date())) {
								Date startDate = DateTimeUtils .getFirstDayOfCurrentMonth();
								Date endDate = DateTimeUtils .getLastDayOfCurrentMonth();
								params.setArg_start_date(DateTimeUtils .formatTime(startDate, DateTimeUtils.yyyyMMdd));
								params.setArg_end_date(DateTimeUtils .formatTime(endDate, DateTimeUtils.yyyyMMdd));
							}
							if (null != params && null == params.getArg_status()) {
								params.setArg_status(0);
							}
							params.setArg_client_id(clientid);
							parametersCol.put("storeNo", params.getArg_store_no());
							parametersCol.put("clientId", clientid);
							parametersCol.put("feedbackDate",params.getFeedbackDate());
							parametersCol.put("visitor", params.getArg_visitor());
							parametersCol.put("provinceId",params.getProvince_ids());
							parametersCol.put("cityId", params.getCity_ids());
							parametersCol.put("storeName", params.getArg_store_name());
							parametersCol.put("channelId", params.getArg_channel_ids());
							parametersCol.put("chainId", params.getArg_types());
							parametersCol.put("structureId", params.getArg_dept_ids());
							parametersCol.put("status", params.getArg_status());
							parametersCol.put("storeType", params.getArg_store_type());
							Map<String, List<ColgateImageVo>> imagesCol = imageService .selectSurveyImageList(parametersCol);
							boolean isOK1 = ZipSupport.getInstance().zipFileByNameColgate1(clientid, imagesCol, fileName);
							Map<String, Object> paramsCol = new HashMap<String, Object>();
							paramsCol.put("attachmentid", attachmentDownload.getAttachmentId());
							if (isOK1) {
							 attachmentDownloadService.sendImageZipList( fileName, clientid, email, attachmentDownload,paramsCol);
							}
						}

					}				
			 }
			log.info("importImageZipTask end.....");
		} finally {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
	}
}
