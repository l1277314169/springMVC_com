package cn.mobilizer.channel.util;

/**
 * ClassName:ExcelReader.java
 * Description:Excel数据读取工具类，POI实现，支持Excel2007及以上版本
 **/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.report.vo.ReportGlobal;

public class ExcelReader {
	private Workbook wb = null;
	private List<String[]> dataList = new ArrayList<String[]>(100);

	public ExcelReader(String path) {
		try {
			InputStream inp = new FileInputStream(path);
			wb = WorkbookFactory.create(inp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public ExcelReader(MultipartFile file) {
		try {
			//输入流
			InputStream inp = file.getInputStream();
			wb = WorkbookFactory.create(inp);
			System.out.println(wb);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 获取所有sheet数据<除了第一sheet需要有title以外，其余的sheet只能有数据，不能有表头>
	 * @return
	 */
	public List<String[]> getAllData(){
		int number = wb.getNumberOfSheets();
		List<String[]> lists = new ArrayList<String[]>(100);
		for (int i = 0; i < number; i++) {
			lists.addAll(getAllData(i));
		}
		return lists;
	}
	
	/**
	 * 根据sheetIndex获取数据
	 * @param sheetIndex
	 * @return
	 */
	public List<String[]> getAllData(int sheetIndex){
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Integer rowNum = sheet.getLastRowNum();
		return getSubSheetData(sheetIndex,0,rowNum);
	}
	
	
	/**
	 * 取Excel所有数据，包含header
	 * 
	 * @return List<String[]>
	 */
	/*public List<String[]> getAllData(int sheetIndex) {
		int columnNum = 0;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		if (sheet.getRow(0) != null) {
			columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
		}
		if (columnNum > 0) {
			Integer rowNum = sheet.getLastRowNum();
			for (int j = 0; j <= rowNum; j++) {
				Row  row = sheet.getRow(j);
				String[] singleRow = new String[columnNum];
				int n = 0;
				for (int i = 0; i < columnNum; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
						break;
					// 数值
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							Date date = cell.getDateCellValue();
							String dateString = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMddHHmmss);
							singleRow[n] = String.valueOf(dateString);
						} else {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String temp = cell.getStringCellValue();
							// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
							if (temp.indexOf(".") > -1) {
								singleRow[n] = String.valueOf(new Double(temp)).trim();
							} else {
								singleRow[n] = temp.trim();
							}
						}
						break;
					case Cell.CELL_TYPE_STRING:
						singleRow[n] = cell.getStringCellValue().trim();
						break;
					case Cell.CELL_TYPE_ERROR:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_FORMULA:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						singleRow[n] = cell.getStringCellValue();
						if (singleRow[n] != null) {
							singleRow[n] = singleRow[n].replaceAll("#N/A", "").trim();
						}
						break;
					default:
						singleRow[n] = "";
						break;
					}
					n++;
				}
				dataList.add(singleRow);
			}
		}
		return dataList;
	}*/
	
	/**
	 * 根据sheetIndex 取Excel从beginRowNum到endRowNum的数据
	 * 
	 * @return List<String[]>
	 */
	public List<String[]> getSubSheetData(int sheetIndex,Integer beginRowNum,Integer endRowNum) {
		List<String[]> subDataList = new ArrayList<String[]>(100);
		int columnNum = 0;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		if (sheet.getRow(0) != null) {
			columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
		}
		if (columnNum > 0) {
			for (int j = beginRowNum; j <= endRowNum; j++) {
				Row  row = sheet.getRow(j);
				String[] singleRow = new String[columnNum];
				int n = 0;
				for (int i = 0; i < columnNum; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
						break;
					// 数值
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							Date date = cell.getDateCellValue();
							String dateString = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMddHHmmss);
							singleRow[n] = String.valueOf(dateString);
						} else {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String temp = cell.getStringCellValue();
							// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
							if (temp.indexOf(".") > -1) {
								singleRow[n] = String.valueOf(new Double(temp)).trim();
							} else {
								singleRow[n] = temp.trim();
							}
						}
						break;
					case Cell.CELL_TYPE_STRING:
						singleRow[n] = cell.getStringCellValue().trim();
						break;
					case Cell.CELL_TYPE_ERROR:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_FORMULA:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						singleRow[n] = cell.getStringCellValue();
						if (singleRow[n] != null) {
							singleRow[n] = singleRow[n].replaceAll("#N/A", "").trim();
						}
						break;
					default:
						singleRow[n] = "";
						break;
					}
					n++;
				}
				subDataList.add(singleRow);
			}
		}
		return subDataList;
	}

	/**
	 * 返回Excel最大行index值，实际行数要加1
	 * 
	 * @return
	 */
	public int getRowNum(int sheetIndex) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		return sheet.getLastRowNum();
	}

	/**
	 * 返回数据的列数
	 * 
	 * @return
	 */
	public int getColumnNum(int sheetIndex) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = sheet.getRow(0);
		if (row != null && row.getLastCellNum() > 0) {
			return row.getLastCellNum();
		}
		return 0;
	}

	/**
	 * 获取某一行数据
	 * 
	 * @param rowIndex
	 *            计数从0开始，rowIndex为0代表header行
	 * @return
	 */
	public String[] getRowData(int sheetIndex, int rowIndex) {
		String[] dataArray = null;
		if (rowIndex > this.getColumnNum(sheetIndex)) {
			return dataArray;
		} else {
			dataArray = new String[this.getColumnNum(sheetIndex)];
			return this.dataList.get(rowIndex);
		}

	}

	/**
	 * 获取某一列数据
	 * 
	 * @param colIndex
	 * @return
	 */
	public String[] getColumnData(int sheetIndex, int colIndex) {
		String[] dataArray = null;
		if (colIndex > this.getColumnNum(sheetIndex)) {
			return dataArray;
		} else {
			if (this.dataList != null && this.dataList.size() > 0) {
				dataArray = new String[this.getRowNum(sheetIndex) + 1];
				int index = 0;
				for (String[] rowData : dataList) {
					if (rowData != null) {
						dataArray[index] = rowData[colIndex];
						index++;
					}
				}
			}
		}
		return dataArray;

	}
	
	/**
	 * excel导入出错时，用于回写出错提示信息
	 * @param response
	 * @param errDataList 出错的列
	 * @param strError 出错的错误提示信息
	 * @param clientId 客户ID
	 * @return 回写的excel的名称
	 * @throws IOException 
	 */
	public void importResultForHS(String path,HttpServletResponse response,List<String[]> errDataList,List<String> strError,Integer clientId,String excelName)  {
		InputStream inp = null; 
		FileOutputStream fos =  null;
		try {
			String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);
			inp = new FileInputStream(new File(path));
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			String name = URLEncoder.encode("导入结果查看.xlsx",ReportGlobal.CHARTSET);
			response.addHeader("Content-Disposition", "attachment;filename="+name);
			response.setContentType("application/vnd.ms-excel");
			XSSFSheet sheet = wb.getSheet("协议录入错误信息");
			
			for (int i = 0; i < errDataList.size(); i++) {
				Row row = sheet.createRow(i+4);
				row.createCell(0).setCellValue(strError.get(i));
				String[] date = errDataList.get(i);
				for (int j = 0; j < date.length; j++) {
					row.createCell(j + 1).setCellValue(date[j] == null ? "" : date[j]);
				}
			}
			
			File fileMkdir = new File(errDataExcelPath+clientId);
			if (!fileMkdir.exists()) {
				fileMkdir.mkdirs();
			}
			File file = new File(fileMkdir.getPath() + File.separator + excelName);
			fos = new FileOutputStream(file);
			wb.write(fos);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				inp.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * excel导入出错时，用于回写出错提示信息
	 * @param response
	 * @param errDataList 出错的列
	 * @param strError 出错的错误提示信息
	 * @param clientId 客户ID
	 * @return 回写的excel的名称
	 * @throws IOException 
	 */
	public void importResultForHS2(String path,HttpServletResponse response,List<String[]> errDataList,List<String> strError,Integer clientId,String excelName)  {
		InputStream inp = null; 
		FileOutputStream fos =  null;
		try {
			String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);
			inp = new FileInputStream(new File(path));
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			String name = URLEncoder.encode("导入结果查看.xlsx",ReportGlobal.CHARTSET);
			response.addHeader("Content-Disposition", "attachment;filename="+name);
			response.setContentType("application/vnd.ms-excel");
			XSSFSheet sheet = wb.getSheet("Sheet1");
			
			for (int i = 0; i < errDataList.size(); i++) {
				Row row = sheet.createRow(i+1);
				row.createCell(0).setCellValue(strError.get(i));
				String[] date = errDataList.get(i);
				for (int j = 0; j < date.length; j++) {
					row.createCell(j + 1).setCellValue(date[j] == null ? "" : date[j]);
				}
			}
			
			File fileMkdir = new File(errDataExcelPath+clientId);
			if (!fileMkdir.exists()) {
				fileMkdir.mkdirs();
			}
			File file = new File(fileMkdir.getPath() + File.separator + excelName);
			fos = new FileOutputStream(file);
			wb.write(fos);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				inp.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * excel导入出错时，用于回写出错提示信息
	 * @param response
	 * @param errDataList 出错的列
	 * @param strError 出错的错误提示信息
	 * @param clientId 客户ID
	 * @return 回写的excel的名称
	 */
	public void importResult(HttpServletResponse response,List<String[]> errDataList,List<String> strError,Integer clientId,String excelName) {
		XSSFWorkbook wb = new XSSFWorkbook();
		/*String excelName = null;
		if(clientId.equals(8)){
			excelName = "errWorkLogExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
		}else{
			excelName = "errWorkTimeSettingsExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
		}*/
		FileOutputStream fos =  null;
		try {
			String name = URLEncoder.encode("导入结果查看.xlsx",ReportGlobal.CHARTSET);
			response.addHeader("Content-Disposition", "attachment;filename="+name);
			response.setContentType("application/vnd.ms-excel");
			XSSFSheet wTSSheet = wb.createSheet("错误数据");
			XSSFRow row = wTSSheet.createRow((int) 0);
			XSSFCellStyle style = this.createHeaderStyle(wb);
			XSSFCell cell = row.createCell(0);
			String[] errDataArray = errDataList.get(0);
			cell = row.createCell(0);
			cell.setCellValue("错误信息");
			cell.setCellStyle(style);
			for (int i = 0; i < errDataArray.length; i++) {
				cell = row.createCell(i + 1);
				cell.setCellValue(errDataArray[i]);
				cell.setCellStyle(style);
			}
			for (int i = 1; i < errDataList.size(); i++) {
				row = wTSSheet.createRow((int) i);
				String[] date = errDataList.get(i);
				for (int j = 0; j < date.length; j++) {
					row.createCell(0).setCellValue(strError.get(i - 1));
					row.createCell(j + 1).setCellValue(date[j] == null ? "" : date[j]);
				}
			}
			
			String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);
			File fileMkdir = new File(errDataExcelPath+clientId);
			if (!fileMkdir.exists()) {
				fileMkdir.mkdirs();
			}
			File file = new File(fileMkdir.getPath() + File.separator + excelName);
			fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 设置表头样式
	 * 
	 * @param style
	 */
	private XSSFCellStyle createHeaderStyle(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中

		XSSFFont font = wb.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 13);

		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style.setFont(font);
		return style;
	}
	
}
