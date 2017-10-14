package cn.mobilizer.channel.sales.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.sales.po.FactKpi;
import cn.mobilizer.channel.sales.service.FactKpiService;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.PropertiesHelper;

@Controller
@RequestMapping(value="/factKpi")
public class FactKpiController extends BaseActionSupport{
	
	@Autowired
	private FactKpiService factKpiService;
	
	@RequestMapping(value="/query")
	public String query(Model model,Integer page,Integer monthId,String storeName,HttpServletRequest req){
		CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
		int clientId = getClientId();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("monthId", monthId);
		params.put("storeName", storeName);
		Integer count = factKpiService.selectByParamCount(params);
		int pagenum = page == null ? 1 : page;
		Page<FactKpi> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		params.put("_start", pageParam.getStartRows());
		params.put("_size", pageParam.getPageSize());
		List<FactKpi> factKpis = factKpiService.selectByParams(params);
		pageParam.setItems(factKpis);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("storeName", storeName);
		model.addAttribute("monthId", monthId);
		CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		return "/factKpi/factKpiList";
	}
	
	@RequestMapping(value="/showImportDialog")
	public String showImportDialog(Model model){	
		model.addAttribute("clientId", getClientId());
		return "/factKpi/importFactKpi";
	}
	
	/**
	 * 销量目标导入
	 * @param fileField
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/importFactKpi")
	@ResponseBody
	public Object importFactKpi(MultipartFile fileField, HttpServletRequest req) throws Exception{
		Integer clientId = getClientId();
		Integer clientUserId = getCurrentUserId();
		Map<String,Object> map = factKpiService.validata(fileField, clientId, clientUserId);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//错误提示文件导出
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.AQUA.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
		XSSFCell cell = null;
		int cellNum = 1;
		XSSFCell errCell = row.createCell(0);  
		errCell.setCellValue("错误提示");
		for (String title : ImportConstants.FACT_KPI_COLUMN) {
			cell = row.createCell(cellNum);
			cell.setCellValue(title);
			cellNum++;
		}
		List<String[]> errDataList = (List<String[]>) map.get("errDataList");
		List<String> errStrList = (List<String>) map.get("errStrList");
		List<Store> successList = (List<Store>) map.get("successList");
		
		if (errDataList != null && errDataList.size() >0) {
			for (int i = 0; i < errDataList.size(); i++) {
				row = sheet.createRow((int) i + 1); 
				String[] cellValues = errDataList.get(i);
				for (int j = 0;j<cellValues.length+1; j++) {	
					  XSSFCell cellData = row.createCell(j);
					  if(j == 0){   								//批注加在第一列
						  cellData.setCellValue(errStrList.get(i));
					  }else{							  
						  cellData.setCellValue(cellValues[j-1]);   //第一列是错误批注   j-1 
					  }										  
				}													 
			}
			try {
				  /**导出Excel文档名字*/
				 String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);		
				  File fileMkdir = new File(errDataExcelPath+getClientId());
				  if(!fileMkdir.exists()){
					  fileMkdir.mkdir();
				  }
				  String excelName = "errDataStoreExcel"+"_"+System.currentTimeMillis()+".xlsx";
				  returnMap.put("errDataExcelPath",excelName);
				  File file = new File(fileMkdir.getPath()+File.separator+excelName);		   
				  FileOutputStream fos = new FileOutputStream(file);			  
				  wb.write(fos);
				  fos.flush();
				  fos.close();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}else{
			returnMap.put("errDataExcelPath","");
		}
		if(map.containsKey("UnknownColumnName")){
			returnMap.put("UnknownColumnName",map.get("UnknownColumnName"));
		}
		returnMap.put("successCount",successList.size());
		returnMap.put("errorCount", errStrList.size());	
		return returnMap;
	}
}
