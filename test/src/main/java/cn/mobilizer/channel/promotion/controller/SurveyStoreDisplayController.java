package cn.mobilizer.channel.promotion.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.promotion.po.SurveyStoreDisplay;
import cn.mobilizer.channel.promotion.service.SurveyStoreDisplayService;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.util.PropertiesHelper;


/**
 * @author liuyong
 */
@Controller
@RequestMapping(value = "/surveyStoreDisplay")
public class SurveyStoreDisplayController extends BaseActionSupport{
	
	@Autowired
	private SurveyStoreDisplayService surveyStoreDisplayService;
	@Autowired
	private StoreService storeService;
	
	@RequestMapping(value="/query")
	public String query(Model model,Integer page,String planCode,String storeNo,HttpServletRequest req){
		int clientId = getClientId();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("planCode", planCode);
		params.put("storeNo",storeNo);
		Integer count = surveyStoreDisplayService.selectByParamCount(params);
		int pagenum = page == null ? 1 : page;		
		Page<SurveyStoreDisplay> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		params.put("_start", pageParam.getStartRows());
		params.put("_size", pageParam.getPageSize());
		List<SurveyStoreDisplay> surveyStoreDisplays = surveyStoreDisplayService.selectByParams(params);
		pageParam.setItems(surveyStoreDisplays);
		model.addAttribute("planCode", planCode);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("pageParam", pageParam);
		return "/promotion/surveyStoreDisplayList";
	}
	
	@RequestMapping(value="/showImportDialog")
	public String showImportDialog(Model model){	
		model.addAttribute("clientId", getClientId());
		return "/promotion/importSurveyStoreDisplay";
	}
	
	/**
	 * promotion导入
	 * @param fileField
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "import")
	@ResponseBody
	public Object importSurveyStoreDisplay(MultipartFile fileField, HttpServletRequest req) throws Exception {
		ExcelReader reader = new ExcelReader(fileField);
		/**将一个sheet里面数据分成多个批次导入：每次导入1000条数据*/
		Integer eachCount = 1000;
		Integer rowCount = reader.getRowNum(0)+1;
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<SurveyStoreDisplay> allSurveyStoreDisplays = new ArrayList<SurveyStoreDisplay>();
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		String[] ctitles = null;
		String datePatternStr = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		Pattern datePattern = Pattern.compile(datePatternStr);
		for(int a = 0;a < num ; a++){
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num-1)){
				if(rowCount%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = ((a+1)*eachCount);
			}
			List<String[]> values = reader.getSubSheetData(0,beginRowIndex,endRowIndex);
			if(beginRowIndex==0){
				String[] titles = values.get(0);
				ctitles = values.get(0);
				//列名校验
				for (int i = 0; i < titles.length; i++) {
					if(StringUtils.isEmpty(titles[i])){
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("列名不能为空");
						return rm;
					}
					if(!MobiStringUtils.contains(ImportConstants.SURVEY_STORE_DISPLAY_TITLE, titles[i])){
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("存在不能识别的列名：" +titles[i]);
						return rm;
					}
				} 
			}
			List<SurveyStoreDisplay> subSheetSurveyStoreDisplays = new ArrayList<SurveyStoreDisplay>();
			//数据校验
			//拼装数据
			Integer clientId = getClientId();
			for (int i = 1; i < values.size(); i++) {
				SurveyStoreDisplay surveyStoreDisplay = new SurveyStoreDisplay();
				Method[] methods = surveyStoreDisplay.getClass().getMethods();
				String[] vv = values.get(i);
				boolean flag = true;
				for (int j = 0; j < vv.length; j++) {	
					if(flag){
						String kvalue = vv[j];//值
						String cvalue = null;//列名
						for (int k = 0; k < ImportConstants.SURVEY_STORE_DISPLAY_TITLE.length; k++) {
							if(ImportConstants.SURVEY_STORE_DISPLAY_TITLE[k].equals(ctitles[j])){
								cvalue = ImportConstants.SURVEY_STORE_DISPLAY_COLUMN[k];
								break;
							}
						}
						
						if("storeNo".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "门店编号不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								Integer storeCount = storeService.getStoreItemsByStoreNo(kvalue,clientId);
								if(storeCount.intValue()>1){
									String errStr = "门店编号重复";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else if(storeCount.intValue()==0){
									String errStr = "门店编号不存在";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("planCode".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "Plan_Code不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("validFrom".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								kvalue = DateUtil.formatDate(DateUtil.getDateByStr(kvalue, "yyyy-MM-dd"), "yyyy-MM-dd");
								Matcher m = datePattern.matcher(kvalue);
								boolean dateFlag = m.matches();
								if(!dateFlag) {
									String errStr = "validFrom日期格式应为  yyyy-mm-dd";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("validTo".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								kvalue = DateUtil.formatDate(DateUtil.getDateByStr(kvalue, "yyyy-MM-dd"), "yyyy-MM-dd");
								Matcher m = datePattern.matcher(kvalue);
								boolean dateFlag = m.matches();
								if(!dateFlag) {
									String errStr = "validTo日期格式应为  yyyy-mm-dd";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}
						for (int k = 0; k < methods.length; k++) {				
							if(StringUtils.isNotEmpty(cvalue) && methods[k].getName().equalsIgnoreCase("set" + cvalue)){
								if(cvalue.equalsIgnoreCase("validFrom")||cvalue.equalsIgnoreCase("validTo")){
									if(StringUtils.isNotEmpty(kvalue)){
										methods[k].invoke(surveyStoreDisplay, DateUtil.getDateByStr(kvalue,DateTimeUtils.yyyyMMdd));
									}
								}else{
									methods[k].invoke(surveyStoreDisplay, kvalue);
								}
								break;
							}
						}
					}
				}	
				if(flag){
					//设置关联属性	
					surveyStoreDisplay.setClientId(clientId);
					subSheetSurveyStoreDisplays.add(surveyStoreDisplay);
					allSurveyStoreDisplays.add(surveyStoreDisplay);
				}
			}
			if(subSheetSurveyStoreDisplays!=null && subSheetSurveyStoreDisplays.size()>0){			
				try{
					surveyStoreDisplayService.batchSaveSurveyStoreDisplay(subSheetSurveyStoreDisplays,clientId);
					surveyStoreDisplayService.updateDisplayType(new HashMap<String, Object>());
				}catch(Exception ex){
					ex.printStackTrace();
				} 			  
			}
		}
		Map<String, String> columns = new HashMap<String,String>();
		for (int k = 0; k < ImportConstants.SURVEY_STORE_DISPLAY_TITLE.length; k++) {
			columns.put(ImportConstants.SURVEY_STORE_DISPLAY_COLUMN[k], ImportConstants.SURVEY_STORE_DISPLAY_TITLE[k]);
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum);
		/**创建单元格，并设置表头，设置表头居中,背景颜色*/
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.AQUA.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
		XSSFCell cell = row.createCell(0);  
		cell.setCellValue("错误提示");
		cell = row.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue("Plan_Code");	
		cell = row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue("Store_No");	
		cell = row.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue("Effective_From");
		cell = row.createCell(4);
		cell.setCellStyle(style);
		cell.setCellValue("Effective_To");		
		cell = row.createCell(5);
		cell.setCellStyle(style);
		cell.setCellValue("Display_Type");
		cell = row.createCell(6);
		cell.setCellStyle(style);
		cell.setCellValue("Promo_SKU");
		
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
				  String excelName = "errDataPromotionExcel"+"_"+System.currentTimeMillis()+".xlsx";
				  map.put("errDataExcelPath",excelName);
				  File file = new File(fileMkdir.getPath()+File.separator+excelName);		   
				  FileOutputStream fos = new FileOutputStream(file);			  
				  wb.write(fos);
				  fos.flush();
				  fos.close();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}else{
			 map.put("errDataExcelPath","");
		}
		map.put("successCount",allSurveyStoreDisplays.size());
		map.put("errorCount", errStrList.size());		
		return map;
	}
}
