package cn.mobilizer.channel.importData.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.export.controller.ActionSupport;
import cn.mobilizer.channel.importData.validata.ImportDataValidataManager;
import cn.mobilizer.channel.importData.validata.ImportStoreValidata;
import cn.mobilizer.channel.importData.validata.ImportValidata;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;
import cn.mobilizer.channel.util.PropertiesHelper;

/**
 * @author liuyong
 */
@Controller
@RequestMapping(value = "/importData")
public class ImportDataController extends ActionSupport{
	
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ImportDataValidataManager importDataValidataManager;
	
	/**
	 * 导入模板下载
	 * @param res
	 */
	@RequestMapping(value="/downLoadExcelTemplate/{tableName}")
	public void downLoadExcelTemplate(@PathVariable("tableName")String tableName,HttpServletResponse resp){
		Integer clientId = getClientId();
		List<ClientBusinessDefinition> editList = channelCommService.getModBusinessDefinition(tableName, ChannelEnum.PAGE_TPYE.IMPORT.getKey(), clientId);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.AQUA.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
		XSSFCell cell = null;
		int cellNum = 0;
		for (ClientBusinessDefinition clientBusinessDefinition : editList) {
			cell = row.createCell(cellNum);
			cell.setCellStyle(style);
			cell.setCellValue(clientBusinessDefinition.getColumnDesc());
			cellNum++;
		}
		 
		resp.addHeader("Content-Disposition", "attachment;filename="+tableName+".xlsx");
		resp.setContentType("application/vnd.ms-excel");
		try {
			OutputStream out = resp.getOutputStream();
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{tableName}")
	@ResponseBody
	public Object importData(@PathVariable("tableName")String tableName,MultipartFile fileField, HttpServletRequest req) throws Exception{
		Integer clientId = getClientId();
		List<ClientBusinessDefinition> importDefinitionList = channelCommService.getModBusinessDefinition(tableName, ChannelEnum.PAGE_TPYE.IMPORT.getKey(), clientId);
		
		ImportValidata validataImpl = importDataValidataManager.getValidata(tableName);
		Map<String,Object> map = validataImpl.validata(importDefinitionList,fileField, clientId);
		
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
		for (ClientBusinessDefinition clientBusinessDefinition : importDefinitionList) {
			cell = row.createCell(cellNum);
			cell.setCellStyle(style);
			cell.setCellValue(clientBusinessDefinition.getColumnDesc());
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
		returnMap.put("successCount",successList.size());
		returnMap.put("errorCount", errStrList.size());	
		return returnMap;
	}
}
