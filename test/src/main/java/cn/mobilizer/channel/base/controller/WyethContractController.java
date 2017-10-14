/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.base.po.WyethContract;
import cn.mobilizer.channel.base.po.WyethContractDetail;
import cn.mobilizer.channel.base.po.WyethContractFeedback;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.base.service.WyethContractDetailService;
import cn.mobilizer.channel.base.service.WyethContractFeedbackService;
import cn.mobilizer.channel.base.service.WyethContractService;
import cn.mobilizer.channel.base.vo.ContractContent;
import cn.mobilizer.channel.base.vo.ContractFeedbackVo;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.export.controller.ActionSupport;
import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.survey.po.MsiQuestionGroup;
import cn.mobilizer.channel.survey.po.MsiSurvey;
import cn.mobilizer.channel.survey.po.MsiSurveyFeedback;
import cn.mobilizer.channel.survey.vo.MsiQuestionVO;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ExcelWriter;
import cn.mobilizer.channel.util.PDFCoreSupport;
import cn.mobilizer.channel.util.PropertiesHelper;

@Controller
@RequestMapping("/contract")
public class WyethContractController extends ActionSupport {
	
	private static final long serialVersionUID = 2020939935602777984L;
	@Autowired
	private StoreService storeService;
	@Autowired
	private WyethContractService wyethContractService;
	@Autowired
	private WyethContractFeedbackService wyethContractFeedbackService;
	@Autowired
	private WyethContractDetailService wyethContractDetailService;
	@RequestMapping(value = "/query")
	public String query(Model model, String mod, Integer page,
			HttpServletRequest req,ContractContent contractContent) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeNo", contractContent.getStoreNo());
		params.put("storeName", contractContent.getStoreName());
		params.put("structureId", super.getDeptIds(contractContent.getStructureId()));
		params.put("clientId", super.getClientId());
		params.put("city", contractContent.getCity());
		params.put("signDate", DateUtil.formatDate(contractContent.getSignDate(),DateUtil.dateFormat));
		params.put("startDate", DateUtil.formatDate(contractContent.getStartDate(),DateUtil.dateTimeFormat));
		params.put("endDate", DateUtil.formatDate(contractContent.getEndDate(),DateUtil.dateTimeFormat));
		String subStructureId = super.getDeptIds(super.getClientStructureId());
		params.put("subStructureId", subStructureId);
		params.put("clientUserId", super.getCurrentUserId());
		boolean flag = super.getSubject().isPermitted("C2F1");//核销权限
		
		Integer count = wyethContractService.findContractCountByparam(params,flag);
		int pagenum = page == null ? 1 : page;	
		Page<ContractContent> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		params.put("_start", pageParam.getStartRows());
		params.put("_size", pageParam.getPageSize ());
		params.put("_orderby", "a.sign_date");
		params.put("_order", "desc");
		List<ContractContent> wcs=wyethContractService.findContractsByparam(params,flag);
		pageParam.setItems(wcs);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("contractContent", contractContent);
		
		return "/base/contractList";

	}

	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(Model model) {
		model.addAttribute("clientId", getClientId());
		return "base/importContract";
	}

	@RequestMapping(value = "/showImportDialog2")
	public String showImportDialog2(Model model) {
		model.addAttribute("clientId", getClientId());
		return "base/importContractFeedback";
	}
/**
 * 协议导入
 * @param fileField
 * @param request
 * @param response
 * @return
 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "import", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public Object importWyethContract(MultipartFile fileField,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> message = new HashMap<String,Object>();
		try {
			if(null==fileField){
				message.put("errorMsg","导入模板不能为空");
			}else{
				
				message = (Map<String,Object>)wyethContractService.addContract(request,fileField,super.getClientId(),response,super.getCurrentUserId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.put("errorMsg","导入失败");
		}
		return message;
	}
	
/**
 * 合同照片上传
 * @param contractId
 * @param model
 * @return
 */
	@RequestMapping(value="/showUploadContract/{contractId}")
	public String showUploadContract(@PathVariable("contractId")String contractId,Model model){
		Map<String, Object>params=new HashMap<String, Object>();
		params.put("clientId", super.getClientId());
		params.put("contractId", contractId);
		ContractContent cc=wyethContractService.findContractPicByparam(params);
		cc.setClientId(super.getClientId());
		model.addAttribute("contractId", contractId);
		model.addAttribute("cc", cc);
		return "/base/uploadContract";
	}

	@RequestMapping(value="/contractChecked")
	@ResponseBody
	public Object contractChecked(Model model,String feedbackId,Byte status){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", super.getClientId());
		params.put("feedbackId", feedbackId);
		WyethContractFeedback wyethContractFeedback = wyethContractFeedbackService.findBycontractId(feedbackId);
		wyethContractFeedback.setStatus(status);
		wyethContractFeedbackService.updateByPrimaryKeySelective(wyethContractFeedback);
		return ResultMessage.SAVE_SUCCESS_RESULT;

	}
	
	
	
	@RequestMapping(value ="showContract")
	public String showContract(String contractId) throws Exception{
		
		return "base/showContract";
	}
	/**
	 * 删除合同
	 * @param contractId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteContract/{contractId}")
	@ResponseBody	
	public Object deleteContract(@PathVariable("contractId")String contractId) {
		try {
			
			wyethContractService.deleteContract(contractId,super.getCurrentUserId(),super.getClientId());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.WEB_OPERATE_FAIL;
		}
		return ResultMessage.WEB_OPERATE_SUCCESS;
	}
	
	
	
	@RequestMapping(value="/checkedQuery")
	public String checkedQuery(Model model,Integer page,HttpServletRequest req,ContractFeedbackVo contractFeedbackVo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", getClientId());
		params.put("bankAcct", contractFeedbackVo.getBankAcct());
		params.put("storeName", contractFeedbackVo.getStoreName());
		params.put("storeCode", contractFeedbackVo.getStoreCode());
		params.put("acctHolder", contractFeedbackVo.getAcctHolder());
		params.put("bankName", contractFeedbackVo.getBankName());
		params.put("structureId", super.getDeptIds(contractFeedbackVo.getStructureId()));
		params.put("city", contractFeedbackVo.getCity());
		params.put("monthId", contractFeedbackVo.getMonthId());
		params.put("status", contractFeedbackVo.getStatus());
		
		String subStructureId = super.getDeptIds(super.getClientStructureId());
		params.put("subStructureId", subStructureId);
		params.put("clientUserId", super.getCurrentUserId());
		
		boolean flag = super.getSubject().isPermitted("C2F1");//核销权限
		Integer count = wyethContractService.findCheckedWyethContractCount(params,flag);
		int pagenum = page == null ? 1 : page;	
		Page<ContractFeedbackVo> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		params.put("_start", pageParam.getStartRows());
		params.put("_size", pageParam.getPageSize ());
		params.put("_orderby", "d.month_id");
		params.put("_order", "desc");
		List<ContractFeedbackVo> contractFeedbackVos = wyethContractService.findCheckedWyethContract(params,flag);
		pageParam.setItems(contractFeedbackVos);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("contractFeedbackVo", contractFeedbackVo);
		return "/base/checkedContractList";
	}

	/**
	 * 总数据导出
	 * @param response
	 * @param contractContent
	 * @throws Exception
	 */
	@RequestMapping(value ="export")
	public void export(HttpServletResponse response,HttpServletRequest request,ContractContent contractContent) throws Exception{
		OutputStream out = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", super.getClientId());
			params.put("storeNo", contractContent.getStoreNo());
			params.put("storeName", contractContent.getStoreName());
			params.put("structureId", super.getDeptIds(contractContent.getStructureId()));
			params.put("clientId", super.getClientId());
			params.put("city", contractContent.getCity());
			params.put("signDate", DateUtil.formatDate(contractContent.getSignDate(),DateUtil.dateFormat));
			params.put("startDate", DateUtil.formatDate(contractContent.getStartDate(),DateUtil.dateTimeFormat));
			params.put("endDate", DateUtil.formatDate(contractContent.getEndDate(),DateUtil.dateTimeFormat));
			String subStructureId = super.getDeptIds(super.getClientStructureId());
			params.put("subStructureId", subStructureId);
			params.put("clientUserId", super.getCurrentUserId());
			
			boolean flag = super.getSubject().isPermitted("C2F1");//核销权限
			List<DataInfo> dataList = wyethContractService.selectExportData(params,flag);
			response.reset();
			response.setContentType("application/x-msdownload");
			out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("总数据导出.xlsx",ReportGlobal.CHARTSET));
			String path = request.getSession().getServletContext().getRealPath("/") +"/export_template/contract_export_HSALL_template.xlsx";
			ExcelWriter writer = new ExcelWriter(path);
			writer.exportForHS(dataList,"总数据导出");
			writer.workFlush(out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		}
	}
	
	/**
	 * 导出合同数据
	 * @param response
	 * @param contractContent
	 * @throws Exception
	 */
	@RequestMapping(value ="exportHT")
	public void exportHT(HttpServletResponse response,HttpServletRequest request,ContractContent contractContent) throws Exception{
		OutputStream out = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", super.getClientId());
			params.put("storeNo", contractContent.getStoreNo());
			params.put("storeName", contractContent.getStoreName());
			params.put("structureId", contractContent.getStructureId());
			params.put("clientId", super.getClientId());
			params.put("city", contractContent.getCity());
			params.put("signDate", DateUtil.formatDate(contractContent.getSignDate(),DateUtil.dateFormat));
			params.put("startDate", DateUtil.formatDate(contractContent.getStartDate(),DateUtil.dateTimeFormat));
			params.put("endDate", DateUtil.formatDate(contractContent.getEndDate(),DateUtil.dateTimeFormat));
			String subStructureId = super.getDeptIds(super.getClientStructureId());
			params.put("subStructureId", subStructureId);
			params.put("clientUserId", super.getCurrentUserId());
			
			boolean flag = super.getSubject().isPermitted("C2F1");//核销权限
			List<DataInfo> dataList = wyethContractService.selectExportHTData(params,flag);
			response.reset();
			response.setContentType("application/x-msdownload");
			out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("合同导出.xlsx",ReportGlobal.CHARTSET));
			String path = request.getSession().getServletContext().getRealPath("/") +"/export_template/contract_export_HSHT_template.xlsx";
			ExcelWriter writer = new ExcelWriter(path);
			writer.exportForDefault(dataList,"合同导出");
			writer.workFlush(out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		}
	}
	
	
	/**
	 * 合同照片上传
	 * @param contractId
	 * @param contractPic
	 * @return
	 */
	@RequestMapping(value = "/uploadContract")
	@ResponseBody	
	public Object uploadContract(String contractId,String contractPic ) {
		try {
			
			wyethContractService.uploadContractImages(contractPic, contractId, super.getCurrentUserId(), super.getClientId());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.ERROR;
		}
		return ResultMessage.SUCCESS;
	}
	
	@RequestMapping(value="/showUploadInvoice/{feedbackId}")
	public String showUploadContractAndInvoice(@PathVariable("feedbackId")String feedbackId,Model model){
		Map<String, Object>params=new  HashMap<String, Object>();
		params.put("feedbackId", feedbackId);
		WyethContractFeedback wc=wyethContractFeedbackService.findInvoicePicByParmas(params);
		model.addAttribute("clientId", super.getClientId());
		model.addAttribute("feedbackId", feedbackId);
		model.addAttribute("wc", wc);
		return "/base/uploadInvoice";
	}
	/**
	 * 发票照片导入
	 * @param feedbackId
	 * @param invoicePic
	 * @return
	 */
	@RequestMapping(value = "/uploadInvoice")
	@ResponseBody	
	public Object uploadInvoice(String feedbackId,String invoicePic ) {
		try {
			wyethContractFeedbackService.uploadInvoiceImages( feedbackId,invoicePic,super.getCurrentUserId(), super.getClientId() );
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.ERROR;
		}
		return ResultMessage.SUCCESS;
	}
	/**
	 * 编辑合同
	 * @param contractId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showEditContract/{contractId}")
	public String showEditContract(@PathVariable("contractId")String contractId,Model model){
		Map<String, Object>params=new  HashMap<String, Object>();
		params.put("contractId", contractId);
		params.put("clientId", super.getClientId());
		List<WyethContractDetail>wcds= wyethContractDetailService.queryWyethContractDetailByContractId(contractId);
		ContractContent wc=wyethContractService.getContractsByparam(params);
		model.addAttribute("wc", wc);
		model.addAttribute("wcds", wcds);
		return "/base/showEditContract";
	}
	@RequestMapping(value="/updateContract")
	@ResponseBody
	public Object updateContract(ContractContent cc){
		try {
			cc.setClientId(super.getClientId());
			cc.setLastUpdateBy(super.getCurrentUserId());
			wyethContractService.updateContract(cc);
		} catch (BusinessException e) {
			
			e.printStackTrace();
			return ResultMessage.ERROR;
		}
		return ResultMessage.SUCCESS;
	
	}
	/**
	 * 核销修改
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/showEditFeedback/{feedbackId}")
	public String checkedQuery(@PathVariable("feedbackId") String feedbackId,Model model,HttpServletRequest req){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", getClientId());
		params.put("feedbackId", feedbackId);
		ContractFeedbackVo contractFeedbackVo= wyethContractService.findCheckedWyethContractById(params);
		model.addAttribute("s", contractFeedbackVo);

		return "/base/showEditFeedback";
	}
	@RequestMapping(value="/updateFeeback")
	@ResponseBody
	public Object updateFeeback(WyethContractFeedback contractFeedback){
			try {
				String rateOfReviews = contractFeedback.getRateOfReviews();
				if(rateOfReviews!=null){
					String substring = rateOfReviews.substring(0, rateOfReviews.length()-1);
					double parseDouble = Double.parseDouble(substring);
					double a=parseDouble/(double)100;
					contractFeedback.setRateOfReview(new BigDecimal(a));
				}
				contractFeedback.setLastUpdateBy(super.getCurrentUserId());
				contractFeedback.setStatus((byte)0);
				contractFeedback.setReason("");
				wyethContractFeedbackService.updateFeeback(contractFeedback);
			} catch (Exception e) {
				
				e.printStackTrace();
				return ResultMessage.ERROR;
			}
		
		return ResultMessage.SUCCESS;
	}
	
	@RequestMapping(value="/appeal/{feedbackId}")
	@ResponseBody
	public Object appeal(@PathVariable("feedbackId")String feedbackId,String reason,Model model){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", super.getClientId());
		params.put("feedbackId", feedbackId);
		WyethContractFeedback wyethContractFeedback = wyethContractFeedbackService.findBycontractId(feedbackId);
		wyethContractFeedback.setStatus((byte)2);  						//申诉状态为2  
		wyethContractFeedback.setReason(reason);
		wyethContractFeedbackService.updateByPrimaryKeySelective(wyethContractFeedback);
		return ResultMessage.SAVE_SUCCESS_RESULT;
	}
	
	@RequestMapping(value="/batchCheked")
	@ResponseBody
	public Object batchCheked(String feedBackIds){
		wyethContractFeedbackService.batchChecked(feedBackIds);
		return ResultMessage.SAVE_SUCCESS_RESULT;
	}
	
	/**
	 * 批量删除核销
	 * @param feedBackIds
	 * @return
	 */
	@RequestMapping(value="/batchDel")
	@ResponseBody
	public Object batchDel(String feedBackIds){
		wyethContractFeedbackService.batchDel(feedBackIds);
		return ResultMessage.SAVE_SUCCESS_RESULT;
	}
	
	/**
	 * 批量删除合同
	 * @param contractIds
	 * @return
	 */
	@RequestMapping(value="/batchDelContract")
	@ResponseBody
	public Object batchDelContract(String contractIds){
		wyethContractService.batchDelContract(contractIds);
		return ResultMessage.SAVE_SUCCESS_RESULT;
	}
	/**
	 * 核销导出
	 * @param response
	 * @param request
	 * @param contractContent
	 * @throws Exception
	 */
	@RequestMapping(value ="exportHXHS")
	public void exportHXHS(HttpServletResponse response,HttpServletRequest request,ContractFeedbackVo contractFeedbackVo)throws Exception{
		OutputStream out = null;
		try
		{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", getClientId());
		params.put("bankAcct", contractFeedbackVo.getBankAcct());
		params.put("storeName", contractFeedbackVo.getStoreName());
		params.put("storeCode", contractFeedbackVo.getStoreCode());
		params.put("acctHolder", contractFeedbackVo.getAcctHolder());
		params.put("bankName", contractFeedbackVo.getBankName());
		params.put("structureId", super.getDeptIds(contractFeedbackVo.getStructureId()));
		params.put("city", contractFeedbackVo.getCity());
		params.put("monthId", contractFeedbackVo.getMonthId());
		params.put("status", contractFeedbackVo.getStatus());
		String subStructureId = super.getDeptIds(super.getClientStructureId());
		params.put("subStructureId", subStructureId);
		params.put("clientUserId", super.getCurrentUserId());
		
		boolean flag = super.getSubject().isPermitted("C2F1");//核销权限
		
		List<DataInfo> dataList = wyethContractService.selectExportHSHXData(params,flag);
		response.reset();
		response.setContentType("application/x-msdownload");
		out = response.getOutputStream();
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("核销导出.xlsx",ReportGlobal.CHARTSET));
		String path = request.getSession().getServletContext().getRealPath("/") +"/export_template/contract_export_HSHX_template.xlsx";
		ExcelWriter writer = new ExcelWriter(path);
		writer.exportForDefault(dataList,"核销导出");
		writer.workFlush(out);
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		if(null!=out){
			out.close();
		}
	}
	}
	@RequestMapping("/exportPDF/{contractId}")
	public void exportPDF(HttpServletResponse resp,Model model,@PathVariable("contractId")String contractId){
		
		Map<String, Object>params=new  HashMap<String, Object>();
		params.put("contractId", contractId);
		params.put("clientId",super.getClientId());
		ContractContent wc= wyethContractService.findContractByPrimaryKey(params);
		params.put("dataType",2);
		params.put("_orderby", "month_id");
		List<WyethContractDetail> wcds =wyethContractDetailService.findContractDetailByParams(params);
		params.clear();
		params.put("contractId", contractId);
		params.put("clientId",super.getClientId());
		params.put("dataType",1);
		params.put("_orderby", "month_id");
		List<WyethContractDetail> wcds2 =wyethContractDetailService.findContractDetailByParams(params);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Integer money=0;
		Integer money2=0;
		if(wcds!=null){
			for (WyethContractDetail wyethContractDetail : wcds) {
				double d = wyethContractDetail.getValue().doubleValue();
				money+=(int)d*100;
			}
		}
		if(wcds2!=null){
			for (WyethContractDetail wyethContractDetail : wcds2) {
				double d = wyethContractDetail.getValue().doubleValue();
				money2+=(int)d*30;
			}
		}
			dataMap.put("money", money);
			dataMap.put("wcds", wcds);
			dataMap.put("wc", wc);
			dataMap.put("money2", money2);
			dataMap.put("wcds2", wcds2);
		try{
			String excelName = "联合会员招募项目合作协议_"+System.currentTimeMillis();
			resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".pdf");
			resp.setContentType("application/vnd.ms-excel");
			dataMap.put("contextPath", "http://www.channelplus.cn/images/");
			PDFCoreSupport.createPDF(dataMap, "wyeth_contract.ftl", resp.getOutputStream());
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
