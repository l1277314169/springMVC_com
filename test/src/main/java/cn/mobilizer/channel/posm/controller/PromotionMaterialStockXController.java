package cn.mobilizer.channel.posm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.posm.po.Warehouse;
import cn.mobilizer.channel.posm.service.PromotionMaterialStockService;
import cn.mobilizer.channel.posm.service.WarehouseService;
import cn.mobilizer.channel.posm.vo.PromotionMaterialStockVo;
import cn.mobilizer.channel.util.DateTimeUtils;


@Controller
@RequestMapping(value ="promotionMaterialStock")
public class PromotionMaterialStockXController extends BaseActionSupport{
	
	
	
	@Autowired
	private PromotionMaterialStockService promotionMaterialStockService;
	@Autowired
	private WarehouseService warehouseService;
	


	
	@RequestMapping("/query")
	public Object showPromotionMaterialStockList(Model model, String mod, Integer page,	HttpServletRequest req,PromotionMaterialStockVo  promotionMaterialStockVo){
		if(promotionMaterialStockVo.getWarehouseId()!=null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("warehouseId",promotionMaterialStockVo.getWarehouseId() );
			params.put("materialCode", promotionMaterialStockVo.getMaterialCode());
			params.put("materialName",promotionMaterialStockVo.getMaterialName());
			params.put("clientId", super.getClientId());
			Integer count = promotionMaterialStockService.findpromotionMaterialStockVoCountByparam(params);
			int pagenum = page == null ? 1 : page;
			Page<PromotionMaterialStockVo> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
			pageParam.buildUrl(req);
			params.put("_start", pageParam.getStartRows());
			params.put("_size", pageParam.getPageSize ());
			List<PromotionMaterialStockVo> pms=promotionMaterialStockService.findpromotionMaterialStockVoByparam(params);
			pageParam.setItems(pms);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("promotionMaterialStockVo", promotionMaterialStockVo);
		}
		return "/posm/promotionMaterialStockList";
	}
	/**
	 *   库存明细导出
	 * @param file
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/importPosmStock")
	public void importPosmInOut(PromotionMaterialStockVo promotionMaterialStockVo,HttpServletResponse resp) throws UnsupportedEncodingException{	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("warehouseId",promotionMaterialStockVo.getWarehouseId() );
		params.put("materialCode", promotionMaterialStockVo.getMaterialCode());
		params.put("materialName",promotionMaterialStockVo.getMaterialName());
		params.put("clientId", super.getClientId());
		List<PromotionMaterialStockVo> posmList = promotionMaterialStockService.findpromotionMaterialStockVoByparam(params); //查询到的所有给excel 数据的list
		HSSFWorkbook wb=new HSSFWorkbook();
		String Excelname = "库存明细"; 
		resp.addHeader("Content-Disposition", "attachment;filename="+new String(Excelname.getBytes("gb2312"), "iso8859-1")+".xls");
		resp.setContentType("application/vnd.ms-excel");
		HSSFSheet wTSSheet = wb.createSheet("库存明细"); //名称
		//设计
				wTSSheet.setDefaultColumnWidth((short) 20);
				/**在wTSSheet值添加表头(第0行)*/
				HSSFRow row = wTSSheet.createRow((int)0);
				/**创建单元格，并设置表头，设置表头居中*/
				HSSFCellStyle headStyle = this.createHeaderStyle(wb); //设置表头样式
				HSSFCellStyle bodyStyle = this.createContentStyle(wb); // 设置正文样式
				HSSFCell posm = row.createCell(0);
				posm.setCellStyle(headStyle);
				String[] posmInOutHead = ImportConstants.PROMOTION_MATERIAL_STOCK; //表格的标题名称
				for (int i = 0; i < posmInOutHead.length; i++) {
					   //创建表中的行
					posm = row.createCell(i);
					posm.setCellValue(posmInOutHead[i]);
					posm.setCellStyle(headStyle);
				}
		
		if(posmList != null && !posmList.isEmpty()){
	
		for (int i = 0; i < posmList.size(); i++) {
			row = wTSSheet.createRow((int) i + 1);
			PromotionMaterialStockVo posmStockVo = posmList.get(i);  //创建行中的单元格
			HSSFCell createCell = row.createCell(0);
			//行的第一个位置 如果不为空给posmInOut.getHandler()
			row.createCell(0).setCellValue(posmStockVo.getStructureName()== null?"":posmStockVo.getStructureName());
			row.createCell(1).setCellValue(posmStockVo.getCity()== null?"":posmStockVo.getCity());
			row.createCell(2).setCellValue(posmStockVo.getDetails()== null?"":posmStockVo.getDetails());

			row.createCell(3).setCellValue(posmStockVo.getBrand() == null?"":posmStockVo.getBrand()); 
			row.createCell(4).setCellValue(posmStockVo.getSubCategory() == null?"":posmStockVo.getSubCategory()); 
			row.createCell(5).setCellValue(posmStockVo.getCategory() == null?"":posmStockVo.getCategory());   //
			row.createCell(6).setCellValue("");
			row.createCell(7).setCellValue(posmStockVo.getMaterialName()== null?"":posmStockVo.getMaterialName()+"["+posmStockVo.getBatch()+"]");
			row.createCell(8).setCellValue(posmStockVo.getBatch()==null?"":posmStockVo.getBatch());
			
				
			row.createCell(9).setCellValue(posmStockVo.getMaterialCode() == null?"":posmStockVo.getMaterialCode());
			row.createCell(10).setCellValue(posmStockVo.getSpec() == null?"":posmStockVo.getSpec());
			row.createCell(11).setCellValue (posmStockVo.getQuantity() == null?"":posmStockVo.getQuantity().toString());
			
			row.createCell(12).setCellValue(posmStockVo.getPrice() == null?"":posmStockVo.getPrice().toString());
			if(posmStockVo.getQuantity() != null&&posmStockVo.getPrice()!=null){
				row.createCell(13).setCellValue((Double) (posmStockVo.getPrice().doubleValue()*posmStockVo.getQuantity()));
			}
			
			row.createCell(14).setCellValue(posmStockVo.getLastUpdateTime() == null?"": DateTimeUtils.formatTime(posmStockVo.getLastUpdateTime(), "yyyy-MM-dd"));
			row.createCell(15).setCellValue(posmStockVo.getRemark() ==null?"":posmStockVo.getRemark());
		}
  		//输出Excel工作表
  		//FileOutputStream fos;
		OutputStream out;
		try {
			//fos = new FileOutputStream("d:\\"+System.currentTimeMillis()+".xls");//以时间标识
			out = resp.getOutputStream();
			wb.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
	
	/**
	 * 设置表头样式
	 * @param style
	 */
	private HSSFCellStyle createHeaderStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中
		
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 13);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style.setFont(font);
		return style;
	}
	
	/**
	 * 设置正文样式
	 * @param style
	 */
	private HSSFCellStyle createContentStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style.setFont(font);
		return style;
	}
	@RequestMapping(value="getWareHouseAjax")
	@ResponseBody
	public List<Warehouse>getWareHouseAjax(){
		
		try {
			List<Warehouse> warehouses = warehouseService.findWareHouseAjax(super.getClientId());
			if(warehouses!=null&&warehouses.size()>0){
				Warehouse warehouse = warehouses.get(0);
				warehouse.setSelected("true");
			}
			return warehouses;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param warehouseId		仓库编号
	 * @param materialId		物料编号
	 * @param quantity			数量
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	@ResponseBody
	@RequestMapping("/isQuantity")
	public ResultMessage isQuantity(Integer warehouseId ,Integer materialId,Integer quantity){
		if(promotionMaterialStockService.isQuantity(materialId, warehouseId, getClientId(), quantity)){
			return ResultMessage.POSM_MATERIAL_STOCK_SUCCESS;
		}else{
			return ResultMessage.POSM_MATERIAL_STOCK_ERROR;
		}
	}
	
	
	
	
}

