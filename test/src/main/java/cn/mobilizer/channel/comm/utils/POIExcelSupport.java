package cn.mobilizer.channel.comm.utils;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import cn.mobilizer.channel.report.po.DataVo;
import cn.mobilizer.channel.report.vo.ReportGlobal;

public class POIExcelSupport {
	
	private Workbook workbook;
	private final static int MAX_ROW = Integer.MAX_VALUE; //单个sheet最大写入行数，超过6w条数据需要新建一个sheet
	
	
	public POIExcelSupport(){
		workbook = new SXSSFWorkbook();
	}

	public void export(String title,List<String> heads,List<TreeMap<String, DataVo>> values){
		try {
			exportExcel(title, heads, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void exportDefault(String title,List<String> heads,List<List<String>> values) {
		try {
			exportExcelDefault(title, heads, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出报表到excel
	 * @param title 文件名称
	 * @param headers 报表表头
	 * @param values 报表元数据，数据需要为已经排序好的数据，跟表头的index对应
	 * @param out
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected void exportExcel(String title, List<String> heads,List<TreeMap<String, DataVo>> values) throws Exception {
		Sheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth((short) 20);
		
		this.setTitle(sheet, heads);
		CellStyle contentStyle = this.defaultStyle();//正文样式
		CellStyle red = this.red();
		CellStyle blue = this.blue();
		CellStyle yellow = this.yellow();
		CellStyle green = this.green();
		CellStyle black = this.black();
		CellStyle teal = this.teal();
		
		int index = 1;
		int sheetIndex = 1;
		if(null!=values){
			Iterator<?> it = values.iterator();
			while (it.hasNext()) {
				if(index>=MAX_ROW){
					sheet = workbook.createSheet(title+"_"+(sheetIndex));
					sheet.setDefaultColumnWidth((short) 20);
					this.setTitle(sheet, heads);
					sheetIndex++;
					index = 1;
				}
				Row row = sheet.createRow(index);
				row.setHeight((short)300);
				index++;
				TreeMap<String, DataVo> map = (TreeMap<String, DataVo>) it.next();
				Set<String> keys = map.keySet();
				int j = 0;
				for (String key : keys) {
					Cell cell = row.createCell(j);
					DataVo dataVo = map.get(key);
					if(dataVo.getHide()==ReportGlobal.HIDE){
						continue;
					}
					cell.setCellStyle(contentStyle);
					if(!StringUtil.isEmptyString(dataVo.getCellColName())){
						for (String str : keys) {
							String[] temp = str.split("@");
							if(temp[1].equals(dataVo.getCellColName())){
								DataVo dVo = map.get(str);
								Object val = dVo.getValue();
								if((String.valueOf(val)).equals(ReportGlobal.Color.RED)){
									cell.setCellStyle(red);
									break;
								}else if((String.valueOf(val)).equals(ReportGlobal.Color.BLUE)){
									cell.setCellStyle(blue);
									break;
								}else if((String.valueOf(val)).equals(ReportGlobal.Color.GREEN)){
									cell.setCellStyle(green);
									break;
								}else if((String.valueOf(val)).equals(ReportGlobal.Color.YELLOW)){
									cell.setCellStyle(yellow);
									break;
								}else if((String.valueOf(val)).equals(ReportGlobal.Color.BLACK)){
									cell.setCellStyle(black);
									break;
								}else if((String.valueOf(val)).equals(ReportGlobal.Color.TEAL)){
									cell.setCellStyle(teal);
									break;
								}
							}
						}
					}
					
					Object val = dataVo.getValue();
					if(null!= dataVo.getType() && dataVo.getType().equals(ReportGlobal.ExcelType.NUMBER)){
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if(val.equals("-")){
							val = "0";
						}
						cell.setCellValue(Double.parseDouble(val+""));
					}else if(null!=dataVo.getType() && dataVo.getType().equals(ReportGlobal.ExcelType.INT)){
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if(val.equals("-")){
							val = "0";
						}
						cell.setCellValue(Integer.parseInt(val+""));
					}else{
						cell.setCellValue(val+"");
					}
					j++;
				}
			}
		}
	}
	
	
	
	/**
	 * 导出报表到excel
	 * @param title 文件名称
	 * @param headers 报表表头
	 * @param values 报表元数据，数据需要为已经排序好的数据，跟表头的index对应
	 * @param out
	 * @throws Exception
	 */
	protected void exportExcelDefault(String title, List<String> heads,List<List<String>> values) throws Exception {
		Sheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth((short) 20);
		this.setTitle(sheet, heads);
		
		int index = 1;
		int sheetIndex = 1;
		CellStyle contentStyle = this.defaultStyle();//正文样式
		
		if(null!=values){
			Iterator<?> it = values.iterator();
			while (it.hasNext()) {
				if(index>=MAX_ROW){
					sheet = workbook.createSheet(title+"_"+(sheetIndex));
					sheet.setDefaultColumnWidth((short) 20);
					this.setTitle(sheet, heads);
					sheetIndex++;
					index = 1;
				}
				Row row = sheet.createRow(index);
				row.setHeight((short)300);
				index++;
				
				int j = 0;
				@SuppressWarnings("unchecked")
				List<String> vals = (List<String>) it.next();
				for (String val : vals) {
					Cell cell = row.createCell(j);
					cell.setCellStyle(contentStyle);
					cell.setCellValue(val+"");
					j++;
				}
			}
		}
	}
	
	
	public void workFlush(OutputStream out) throws Exception{
		try {
			workbook.write(out);
		}finally{
			out.close();
		}
	}

	/**
	 * 设置标题内容
	 * 
	 * @param workbook
	 * @param headers
	 * @param title
	 * @return
	 */
	private void setTitle(Sheet sheet,List<String> heads) {
		Row row = sheet.createRow(0);
		row.setHeight((short)500);
		CellStyle style = this.headerStyle();
		if (null != heads) {
			for (short i = 0; i < heads.size(); i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(heads.get(i));
			}
		}
	}
	
	/**
	 * 设置表头样式
	 * @param style
	 */
	private CellStyle headerStyle() {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中
		
		Font font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 13);
		
		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style.setFont(font);
		return style;
	}

	/**
	 * 设置正文样式
	 * @param style
	 */
	private CellStyle defaultStyle() {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		Font font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		style.setFont(font);
		return style;
	}
	
	
	/**
	 * 设置正文样式
	 * @param style
	 */
	private CellStyle createStyle(short color) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		style.setFillForegroundColor(color);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(font);
		return style;
	}
	
	
	/**
	 * 设置正文样式
	 * @param style
	 */
	private CellStyle red() {
		CellStyle style = createStyle(IndexedColors.RED.getIndex());
		return style;
	}
	
	private CellStyle green(){
		CellStyle style = createStyle(IndexedColors.GREEN.getIndex());
		return style;
	}
	
	private CellStyle blue(){
		CellStyle style = createStyle(IndexedColors.BLUE.getIndex());
		return style;
	}
	
	private CellStyle yellow(){
		CellStyle style = createStyle(IndexedColors.YELLOW.getIndex());
		return style;
	}
	
	private CellStyle black(){
		CellStyle style = createStyle(IndexedColors.BLACK.getIndex());
		return style;
	}
	
	private CellStyle teal(){
		CellStyle style = createStyle(IndexedColors.TEAL.getIndex());
		return style;
	}
	
}
