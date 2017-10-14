package cn.mobilizer.channel.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.report.po.DataVo;

public class ExcelWriter {

	private XSSFWorkbook wb = null;
	
	public ExcelWriter(String path){
		try {
			InputStream inp = new FileInputStream(path);
			wb = new XSSFWorkbook(inp);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 惠氏会员合同导出
	 * @throws IOException 
	 */
	public void exportForHS(List<DataInfo> dataList,String sheetName)  {
		try {
			XSSFSheet sheet = wb.getSheet(sheetName);
			for (int i = 0; i < dataList.size(); i++) {
				DataInfo dataInfo = dataList.get(i);
				List<String> keys = dataInfo.getHeads();
				for (int j = 0; j < dataInfo.getValues().size(); j++) {
					TreeMap<String, DataVo> map = dataInfo.getValues().get(j);
					Row row = sheet.createRow(j+4);
					int index = 0;
					for (String key : keys) {
						DataVo data = map.get(index+"@"+key);
						if(null==data){
							row.createCell(index).setCellValue("");
						}else{
							String val = data.getValue()==null?"":data.getValue()+"";
							row.createCell(index).setCellValue(val);
						}
						index++;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportForDefault(List<DataInfo> dataList,String sheetName) {
		try {
			XSSFSheet sheet = wb.getSheet(sheetName);
			for (int i = 0; i < dataList.size(); i++) {
				DataInfo dataInfo = dataList.get(i);
				List<String> keys = dataInfo.getHeads();
				for (int j = 0; j < dataInfo.getValues().size(); j++) {
					TreeMap<String, DataVo> map = dataInfo.getValues().get(j);
					Row row = sheet.createRow(j+1);
					int index = 0;
					for (String key : keys) {
						DataVo data = map.get(index+"@"+key);
						if(null==data){
							row.createCell(index).setCellValue("");
						}else{
							String val = data.getValue()==null?"":data.getValue()+"";
							row.createCell(index).setCellValue(val);
						}
						index++;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void workFlush(OutputStream out) throws Exception{
		try {
			wb.write(out);
		}finally{
			out.close();
		}
	}
	
}
