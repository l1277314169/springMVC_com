package cn.mobilizer.channel.posm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum.POSM_IN_OUT_TYPE;
import cn.mobilizer.channel.comm.vo.Enum2Bean;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.po.PosmInOut;
import cn.mobilizer.channel.posm.po.Warehouse;
import cn.mobilizer.channel.posm.service.PosmInOutService;
import cn.mobilizer.channel.posm.service.PromotionMaterialStockService;
import cn.mobilizer.channel.posm.vo.PosmInOutExcelVo;
import cn.mobilizer.channel.util.DateTimeUtils;

@Controller

@RequestMapping(value = "posmInOut")
public class PosmInOutController extends BaseActionSupport {


	@Autowired
	private PosmInOutService posmInOutDaoService;
	
	@Autowired
	private PromotionMaterialStockService materialStockService;

	@RequestMapping("/query")
	public String quer(Model model,Byte billType, String warehouseNo, String warehouseName,String materialCode, String materialName, Integer page,
			HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtil.isEmptyString(warehouseNo)) {
			map.put("warehouseNo", "%" + warehouseNo + "%");
		}
		if (!StringUtil.isEmptyString(warehouseName)) {
			map.put("warehouseName", "%" + warehouseName + "%");
		}
		if (!StringUtil.isEmptyString(materialCode)) {
			map.put("materialCode", "%" + materialCode + "%");
		}
		if (!StringUtil.isEmptyString(materialName)) {
			map.put("materialName", "%" + materialName + "%");
		}
		if(null != billType){
			map.put("billType", billType);
		}

		Integer count = posmInOutDaoService.getPInOutListCount(map);
		int pagenum = page == null ? 1 : page;
		Page<PosmInOut> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE,
				pagenum);
		pageParam.buildUrl(req);
		map.put("_start", pageParam.getStartRows());
		map.put("_size", pageParam.getPageSize());

		List<PosmInOutExcelVo> list = posmInOutDaoService.getPosmInOuts(map);

		model.addAttribute("LIST", list);
		model.addAttribute("warehouseNo", warehouseNo);
		model.addAttribute("warehouseName", warehouseName);
		model.addAttribute("materialCode", materialCode);
		model.addAttribute("materialName", materialName);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("billType", billType);
		model.addAttribute("count", count);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/posm/outDetailsList";
	}

	@ResponseBody
	@RequestMapping(value = "/importPosmInOut", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public ResultMessage importPosmInOut(MultipartFile file,
			HttpServletRequest request, HttpServletResponse resp) {
		
		return posmInOutDaoService.saveInputPosmInOuts(file, getClientId(),
				getClientUser().getClientUserId(), request, resp);
	}

	/**
	 * 进出明细导出
	 * 
	 * @param file
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/importPosmInOut")
	public void importPosmInOut(String warehouseNo, String warehouseName,	String materialCode, String materialName,Byte billType,	HttpServletRequest request, HttpServletResponse resp)
			throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtil.isEmptyString(warehouseNo)) {
			map.put("warehouseNo", "%" + warehouseNo + "%");
		}
		if (!StringUtil.isEmptyString(warehouseName)) {
			map.put("warehouseName", "%" + warehouseName + "%");
		}
		if (!StringUtil.isEmptyString(materialCode)) {
			map.put("materialCode", "%" + materialCode + "%");
		}
		if (!StringUtil.isEmptyString(materialName)) {
			map.put("materialName", "%" + materialName + "%");
		}
		if(null != billType){
			map.put("billType", billType);
		}

//		List<PosmInOutExcelVo> posmList = posmInOutDaoService.selectPInOutExport(map); // 查询到的所有给excel 数据的list
		List<PosmInOutExcelVo> posmList = posmInOutDaoService.getPosmInOuts(map); // 查询到的所有给excel 数据的list
		/*
		 * Map<String, Object> warehouseMap = new HashMap<String, Object>();
		 * warehouseMap.put("materialId", posmList.get())
		 */

		if (posmList != null && !posmList.isEmpty()) {
			HSSFWorkbook wb = new HSSFWorkbook();
			String Excelname = "物料进出明细";
			resp.addHeader("Content-Disposition", "attachment;filename="
					+ new String(Excelname.getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			resp.setContentType("application/vnd.ms-excel");

			HSSFSheet wTSSheet = wb.createSheet("物料进出明细"); // 名称

			// 设计
			wTSSheet.setDefaultColumnWidth((short) 20);
			/** 在wTSSheet值添加表头(第0行) */
			HSSFRow row = wTSSheet.createRow((int) 0);
			/** 创建单元格，并设置表头，设置表头居中 */
			HSSFCellStyle headStyle = this.createHeaderStyle(wb); // 设置表头样式
			HSSFCellStyle bodyStyle = this.createContentStyle(wb); // 设置正文样式
			HSSFCell posm = row.createCell(0);
			posm.setCellStyle(headStyle);
			String[] posmInOutHead = ImportConstants.POSMINOUT_EXPORT; // 表格的标题名称
			for (int i = 0; i < posmInOutHead.length; i++) {
				// 创建表中的行
				posm = row.createCell(i);
				posm.setCellValue(posmInOutHead[i]);
				posm.setCellStyle(headStyle);
			}

			for (int i = 0; i < posmList.size(); i++) {
				row = wTSSheet.createRow((int) i + 1);
				PosmInOutExcelVo posmInOutVo = posmList.get(i); // 创建行中的单元格
				HSSFCell createCell = row.createCell(0);
				// 行的第一个位置 如果不为空给posmInOut.getHandler()
				row.createCell(0).setCellValue(
						posmInOutVo.getStructureName() == null ? ""
								: posmInOutVo.getStructureName());
				row.createCell(1).setCellValue(
						posmInOutVo.getCityName() == null ? "" : posmInOutVo
								.getCityName());
				row.createCell(2).setCellValue(
						posmInOutVo.getWarehouseNo() == null ? "" : posmInOutVo
								.getWarehouseNo());

				row.createCell(3).setCellValue(
						posmInOutVo.getBrand() == null ? "" : posmInOutVo
								.getBrand());
				row.createCell(4).setCellValue(
						posmInOutVo.getCategory() == null ? "" : posmInOutVo
								.getCategory());
				row.createCell(5).setCellValue(
						posmInOutVo.getMaterialName() == null ? ""
								: posmInOutVo.getMaterialName()); //
				row.createCell(6).setCellValue(
						posmInOutVo.getMaterialCode() == null ? ""
								: posmInOutVo.getMaterialCode());
				row.createCell(7).setCellValue(
						posmInOutVo.getBatch() == null ? "" : posmInOutVo
								.getBatch());

				if (posmInOutVo.getBillType().toString().equals("1")) {
					row.createCell(8).setCellValue("收进");
				} else {
					row.createCell(8).setCellValue("发出");
				}

				row.createCell(9).setCellValue(
						posmInOutVo.getSpec() == null ? "" : posmInOutVo
								.getSpec());
				if (posmInOutVo.getQuantity() instanceof Integer) {
					String quantity = posmInOutVo.getQuantity().toString();
					row.createCell(10).setCellValue(
							quantity == null ? "" : quantity); //
				} else {
					row.createCell(10).setCellValue("");
				}

				if (posmInOutVo.getInOutTime() instanceof Date) {
					String posDate = DateTimeUtils.formatTime(
							posmInOutVo.getInOutTime(), "yyyy-MM-dd");
					row.createCell(11).setCellValue(
							posDate == null ? "" : posDate);
				} else {
					row.createCell(11).setCellValue("");
				}
				row.createCell(12).setCellValue(
						posmInOutVo.getStoreNo() == null ? "" : posmInOutVo
								.getStoreNo());
				row.createCell(13).setCellValue(
						posmInOutVo.getChainName() == null ? "" : posmInOutVo
								.getChainName());
				row.createCell(14).setCellValue(
						posmInOutVo.getStoreName() == null ? "" : posmInOutVo
								.getStoreName());
				row.createCell(15).setCellValue(posmInOutVo.getReceiveWarehouseNo()
				 ==null?"":posmInOutVo.getReceiveWarehouseNo());
				row.createCell(16).setCellValue(
						posmInOutVo.getHandler() == null ? "" : posmInOutVo
								.getHandler());
				row.createCell(17).setCellValue(
						posmInOutVo.getRemark() == null ? "" : posmInOutVo
								.getRemark());
			}
			// 输出Excel工作表
			// FileOutputStream fos;
			OutputStream out;
			try {
				// fos = new
				// FileOutputStream("d:\\"+System.currentTimeMillis()+".xls");//以时间标识
				out = resp.getOutputStream();
				wb.write(out);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			HSSFWorkbook wb = new HSSFWorkbook();
			String Excelname = "物料进出明细";
			resp.addHeader("Content-Disposition", "attachment;filename="
					+ new String(Excelname.getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			resp.setContentType("application/vnd.ms-excel");

			HSSFSheet wTSSheet = wb.createSheet("物料进出明细"); // 名称

			// 设计
			wTSSheet.setDefaultColumnWidth((short) 20);
			/** 在wTSSheet值添加表头(第0行) */
			HSSFRow row = wTSSheet.createRow((int) 0);
			/** 创建单元格，并设置表头，设置表头居中 */
			HSSFCellStyle headStyle = this.createHeaderStyle(wb); // 设置表头样式
			HSSFCellStyle bodyStyle = this.createContentStyle(wb); // 设置正文样式
			HSSFCell posm = row.createCell(0);
			posm.setCellStyle(headStyle);
			String[] posmInOutHead = ImportConstants.POSMINOUT_EXPORT; // 表格的标题名称
			for (int i = 0; i < posmInOutHead.length; i++) {
				// 创建表中的行
				posm = row.createCell(i);
				posm.setCellValue(posmInOutHead[i]);
				posm.setCellStyle(headStyle);
			}
			OutputStream out;
			try {
				out = resp.getOutputStream();
				wb.write(out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "/showImportPosmInOut")
	public String showImportDialog(Model model) {
		String tempURL = "/download/import_template/0/user_import_template.xlsx";
		model.addAttribute("tempURL", tempURL);
		model.addAttribute("clientId", getClientId());
		return "posm/importPosmInOut";
	}

	/**
	 * 设置表头样式
	 * 
	 * @param style
	 */
	private HSSFCellStyle createHeaderStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中

		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 13);
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style.setFont(font);
		return style;
	}

	/**
	 * 设置正文样式
	 * 
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
	
	
	@RequestMapping(value = "/showAddPosmInOut")
	public String showAddClinetUser(Model model,String mod) throws BusinessException{
		return "posm/showAddPosmInOut";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBillType")
	public List<Enum2Bean> getStatus(){
		List<Enum2Bean> ls = new ArrayList<Enum2Bean>();
		for (POSM_IN_OUT_TYPE s :POSM_IN_OUT_TYPE.values()) {
			Enum2Bean e = new Enum2Bean();
			e.setId(s.getKey().intValue());
			e.setName(s.getName());
			ls.add(e);
		}
		return ls;
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResultMessage savePosmInOut(Integer warehouseId ,Integer outWarehouseId, String storeId,Integer materialId ,Integer quantity,String handler,String inOutTime,Byte billType,String remark){
		PosmInOutExcelVo excelVo = new PosmInOutExcelVo();
		if(!StringUtil.isBlank(inOutTime)){
			excelVo.setInOutTime(DateUtil.stringToDate(inOutTime,"yyyy-MM-dd"));
		}
		excelVo.setWarehouseId(warehouseId);// 仓库编号
		excelVo.setOutWarehouseId(outWarehouseId);//目标仓库
		excelVo.setStrStoreId(storeId); //门店编号
		excelVo.setQuantity(quantity);//数量
		excelVo.setRemark(remark);//备注
		excelVo.setHandler(handler); //运输负责人
		excelVo.setBillType(billType);	//收进发出
		return posmInOutDaoService.savePosmInOut(excelVo, materialId, getCurrentUserId(), getClientId(), "添加明细");
	}
	
	
	@ResponseBody
	@RequestMapping("/detelePostInOut")
	public ResultMessage deletePosmInOut(Integer inOutId){
		if(null != inOutId && inOutId > 0){
			return posmInOutDaoService.deletePosmInOut(inOutId, getCurrentUserId(), getClientId(),"明细删除操作");
		}
		return ResultMessage.WEB_OPERATE_FAIL;
	}
	
	
	 /**
	  * 
	  * @param warehouseid
	  * @param model
	  * @return
	  * @author：wei.peng
	  * @date 2015年10月12日
	  //功能封存  ， 代码勿删
	@RequestMapping(value="/showEditPosmInOut/{warehouseid}",produces="application/json")
	public String showEditPosmInOut(@PathVariable("warehouseid")Integer warehouseid ,Model model) {		
		PosmInOut inOut = posmInOutDaoService.getPosmInOutByKey(warehouseid);
		if(null != inOut){
			model.addAttribute("inOut",inOut) ;
		}
		return "posm/showEditPosmInOut";

	}
	*/
	
	
	
	/*
     * 修改页面
     */
	
	@RequestMapping(value="/showPosmInOut/{warehouseid}",produces="application/json")
	public String showPosmInOut(@PathVariable("warehouseid")Integer warehouseid ,Model model) {		
		PosmInOut inOut = posmInOutDaoService.getPosmInOutByKey(warehouseid);
		if(null != inOut){
			model.addAttribute("inOut",inOut) ;
		}
		return "posm/showPosmInOut";

	}
	
	
	/**
	 * 
	 * @param inOutId
	 * @param warehouseId
	 * @param outWarehouseId
	 * @param storeId
	 * @param materialId
	 * @param quantity
	 * @param handler
	 * @param inOutTime
	 * @param billType
	 * @param remark
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月12日
	//进出明细修修改。
	@ResponseBody
	@RequestMapping("/updataInOut")
	public Object updataInOut(Integer inOutId,Integer warehouseId ,Integer outWarehouseId, String storeId,Integer materialId ,Integer quantity,String handler,String inOutTime,Byte billType,String remark){
		PosmInOutExcelVo excelVo = new PosmInOutExcelVo();
		if(!StringUtil.isBlank(inOutTime)){
			excelVo.setInOutTime(DateUtil.stringToDate(inOutTime,"yyyy-MM-dd"));
		}
		excelVo.setWarehouseId(warehouseId);// 仓库编号
		excelVo.setOutWarehouseId(outWarehouseId);//目标仓库
		excelVo.setStrStoreId(storeId); //门店编号
		excelVo.setQuantity(quantity);//数量
		excelVo.setRemark(remark);//备注
		excelVo.setHandler(handler); //运输负责人
		excelVo.setBillType(billType);	//收进发出
		excelVo.setInOutTime(null);//时间
		PosmInOut posmInOut = excelVo.getPosmInOut(materialId, getCurrentUserId(), getClientId());		//修改之前的数据
		posmInOut.setInOutId(inOutId);
		PosmInOut inOut = posmInOutDaoService.getPosmInOutByKey(inOutId);			//修改之后的数据
		return null;
	}
		 */
}
