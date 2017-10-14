package cn.mobilizer.channel.survey.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.survey.service.HistoryDataImportService;

/**
 * @author liuyong
 * 高露洁历史数据导入
 */
@Controller
@RequestMapping(value = "/historyDataImport")
public class HistoryDataImportController extends BaseActionSupport{
	
	private static final long serialVersionUID = 311121104922827837L;
	@Autowired
	private HistoryDataImportService historyDataImportService;
	
	@RequestMapping(value = "colgate")
	public String colgate(){
		return "/survey/historyDataImport";
	}
	
	@RequestMapping(value = "showImportDialog")
	public String showImportDialog(Model model){
		model.addAttribute("clientId", getClientId());
		return "/survey/importData";
	}
	
	/**
	 * 高露洁陈列面导入
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showDisPlayImportDialog")
	public String showDisPlayImportDialog(Model model){
		model.addAttribute("clientId", getClientId());
		return "/survey/importDisPlayData";
	}
	
	/**
	 * 高露洁陈列面导入
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showSupplementaryImportDialog")
	public String showSupplementaryImportDialog(Model model){
		model.addAttribute("clientId", getClientId());
		return "/survey/importSupplementaryData";
	}
	
	/**
	 * 高露洁门头照历史数据导入
	 * @param fileField
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/importColgateHistoryData")
	@ResponseBody
	public Object importHisToryData(MultipartFile fileField, HttpServletRequest req , HttpServletResponse resp,String month){
		int clientId = getClientId();
		int clientUserId = getCurrentUserId();
		Map<String, Object> resultMessage = null;
		if(fileField != null){
			try {
				resultMessage = new HashMap<String, Object>();
				String excelName = "errFerreroExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
				resultMessage.put("errDataExcelPath",excelName);
				historyDataImportService.ImportColgateHistoryData(fileField, req, resp, clientId,clientUserId,excelName,month);
			} catch (RuntimeException e) {
				e.printStackTrace();
				return resultMessage;
			}
		}
		return resultMessage;
	}
	
	@RequestMapping(value="importSupplementaryHistoryData")
	@ResponseBody
	public Object importSupplementaryHistoryData(MultipartFile fileField, HttpServletRequest req , HttpServletResponse resp,String month){
		int clientId = getClientId();
		int clientUserId = getCurrentUserId();
		Map<String, Object> resultMessage = null;
		if(fileField != null){
			try {
				resultMessage = new HashMap<String, Object>();
				String excelName = "errFerreroExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
				resultMessage.put("errDataExcelPath",excelName);			
				historyDataImportService.ImportColgateHistoryData(fileField, req, resp, clientId,clientUserId,excelName,month);
			} catch (RuntimeException e) {
				e.printStackTrace();
				return resultMessage;
			}
		}
		return resultMessage;
	}
	
	/**
	 * 高露洁货架陈列
	 * @param fileField
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/importColgateDisPlayHistoryData")
	@ResponseBody
	public Object importColgateDisPlayHistoryData(MultipartFile fileField, HttpServletRequest req , HttpServletResponse resp,String month){
		int clientId = getClientId();
		int clientUserId = getCurrentUserId();
		Map<String, Object> resultMessage = null;
		if(fileField !=null){
			try {
				resultMessage = new HashMap<String, Object>();
				String excelName = "errFerreroExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
				resultMessage.put("errDataExcelPath",excelName);
				historyDataImportService.saveImportColgateDisPlayHistoryData(fileField, req, resp, clientId,clientUserId,excelName,month);
			} catch (Exception e) {
				e.printStackTrace();
				return resultMessage;
			}
		}
		return resultMessage;
	}
	
	/**
	 * 高露洁货架陈列
	 * @param fileField
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/importColgateSupplementaryHistoryData")
	@ResponseBody
	public Object importColgateSupplementaryHistoryData(MultipartFile fileField, HttpServletRequest req , HttpServletResponse resp,String month){
		int clientId = getClientId();
		int clientUserId = getCurrentUserId();
		
		Map<String, Object> resultMessage = null;
		if(fileField !=null){
			try {
				resultMessage = new HashMap<String, Object>();
				String excelName = "errFerreroExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
				resultMessage.put("errDataExcelPath",excelName);
				historyDataImportService.ImportSupplementaryHistoryData(fileField, req, resp, clientId,clientUserId,excelName,month);
			} catch (Exception e) {
				e.printStackTrace();
				return resultMessage;
			}
		}
		return resultMessage;
	}
	
}
