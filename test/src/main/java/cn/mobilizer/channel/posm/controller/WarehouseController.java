package cn.mobilizer.channel.posm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.ProvinceService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.po.Warehouse;
import cn.mobilizer.channel.posm.service.WarehouseService;
import cn.mobilizer.channel.posm.vo.KeyValueVo;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ExcelReader;

@Controller
@RequestMapping(value = "/warehouse")
public class WarehouseController extends BaseActionSupport{

	  
	/**
	 * 
	 * 刘黎明
	 */
	private static final long serialVersionUID = 1L;
    @Autowired
	private WarehouseService warehouseService;
    @Autowired
    private ClientStructureService clientStructureService;
    @Autowired
    private ClientUserService clientUserService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ProvinceService provinceService;
    /*
     * 查询 
     * 刘黎明
     */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page,HttpServletRequest req,String warehouseName,String warehouseNo) throws BusinessException{
		
	int clientId = getClientId ();
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("clientId", clientId);
	parameters.put("WarehouseNo",warehouseNo);
	parameters.put("WarehouseName", warehouseName);
	parameters.put("isDelete", Constants.IS_DELETE_FALSE);
	int count =warehouseService.queryWarehouseCount(parameters);
	int pagenum = page == null ? 1 : page;
	
	Page<Warehouse> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
	pageParam.buildUrl(req);
	parameters.put("_start", pageParam.getStartRows());
	parameters.put("_size", pageParam.getPageSize ());
	List<Warehouse> list = warehouseService.queryWarehouseList(parameters);
	pageParam.setItems(list);	
	
	model.addAttribute("pageParam", pageParam);
	model.addAttribute("clientId",clientId);
	model.addAttribute("warehouseNo", warehouseNo);
	model.addAttribute("warehouseName", warehouseName);
	model.addAttribute("page", pageParam.getPage().toString());
	return "/posm/warehouseList";
	}
	  /*
     * 新增
     * 刘黎明
     */
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public ResultMessage add(Warehouse warehouse) {
		int clientId = getClientId();
		int createBy =getCurrentUserId();
		warehouse.setCreateBy(createBy);
		warehouse.setClientId(clientId);	
		warehouseService.addWarehouse(clientId, warehouse);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	 /*
     * 新增页面
     * 刘黎明
     */
	
	@RequestMapping(value = "/showAddwarehouse")
	public String ShowAddwarehouse(Model model){
		int clientId = getClientId();
		model.addAttribute("clientId", clientId);
		return "/posm/showAddwarehouseList";
	}
	
	/**
	 * 导入页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/importWarehouse")
	public String showImportWarehouseDialog(Model model){
		int clientId = getClientId();
		model.addAttribute("clientId", clientId);
		return "posm/importWarehouseList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "mixImport", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public Object mixImport(MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> message = new HashMap<String, Object>();
			try {
				if(null==file){
					message.put("errorMsg", "导入模板不能为空");
				}else{
					ExcelReader reader = new ExcelReader(file);
					List<String[]> values = reader.getAllData(0);
					//由于查询夸库了，不能在service层切换数据库，所有将此方法写在controller层
				 
					Map<String, ClientStructure> mapDept = clientStructureService.getClientStructureMapByName(super.getClientId());//根据部门名称获取部门信息
					Map<String, ClientUser> mapClientUser = clientUserService.findClientUserBylogName(super.getClientId()); //获取用户信息	
					Map<String, Object> params1 = new HashMap<String, Object>();
					params1.put("isDelete", 0);
					Map<String, City> mapCity =cityService.selectAllCity(params1);		
					Map<String, Province> mapProvince =provinceService.selectAllprovince(params1);
					message = warehouseService.importWarehouse(values,mapProvince, mapDept,mapCity, mapClientUser, super.getClientId());
					List<String> errStrList = (List<String>) message.get("errStrList");
					List<String[]> errDataList = (List<String[]>) message.get("errDataList"); 
					if(null!=errDataList && !errDataList.isEmpty() && errDataList.size()>1){
						String excelName = "errUnicharmSalesExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
						reader.importResult(response, errDataList, errStrList, super.getClientId(), excelName);
						message.put("errDataExcelPath",excelName);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.put("errorMsg", "导入失败");
			}finally{
				CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			}
			return  message;
		}
	/**
	 * 仓库导出
	 * @param file
	 * @param request
	 * @param resp
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/exportBtnWarehouse")
	public void exportBtnWarehouse(String warehouseNo,String warehouseName , HttpServletRequest request,HttpServletResponse resp) throws UnsupportedEncodingException {	
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(null != warehouseNo && warehouseNo.trim().length()>0){
			map.put("WarehouseNo", warehouseNo);
		}
		if(null != warehouseName && warehouseName.trim().length() > 0){
			map.put("WarehouseName", warehouseName);
		}
		map.put("clientId", getClientId());
		List<Warehouse> posmList = warehouseService.queryWarehouseListall(map);  //查询到的所有给excel 数据的list
		/*Map<String, Object> warehouseMap = new HashMap<String, Object>();
		warehouseMap.put("materialId", posmList.get())*/
		
		if(posmList != null && !posmList.isEmpty()){
		HSSFWorkbook wb=new HSSFWorkbook();
		String Excelname = "仓库导出"; 
		resp.addHeader("Content-Disposition", "attachment;filename="+new String(Excelname.getBytes("gb2312"), "iso8859-1")+".xls");
		resp.setContentType("application/vnd.ms-excel");
		
		HSSFSheet wTSSheet = wb.createSheet("计划数据"); //名称
		
		//设计
		wTSSheet.setDefaultColumnWidth((short) 20);
		/**在wTSSheet值添加表头(第0行)*/
		HSSFRow row = wTSSheet.createRow((int)0);
		/**创建单元格，并设置表头，设置表头居中*/
		HSSFCellStyle headStyle = this.createHeaderStyle(wb); //设置表头样式
		HSSFCellStyle bodyStyle = this.createContentStyle(wb); // 设置正文样式
		HSSFCell posm = row.createCell(0);
		posm.setCellStyle(headStyle);
		String[] warehouseHead = ImportConstants.WAREHOUSE_TITLE; //表格的标题名称
		for (int i = 0; i < warehouseHead.length; i++) {
			   //创建表中的行
			posm = row.createCell(i);
			posm.setCellValue(warehouseHead[i]);
			posm.setCellStyle(headStyle);
		}
		
		for (int i = 0; i < posmList.size(); i++) {
			row = wTSSheet.createRow((int) i + 1);
			Warehouse warehouse = posmList.get(i);  //创建行中的单元格
			HSSFCell createCell = row.createCell(0);
			//行的第一个位置 如果不为空给posmInOut.getHandler()
			row.createCell(0).setCellValue(warehouse.getWarehouseNo()== null?"":warehouse.getWarehouseNo());
			row.createCell(1).setCellValue(warehouse.getWarehouseName()== null?"":warehouse.getWarehouseName());
			row.createCell(2).setCellValue(warehouse.getStructureName()== null?"":warehouse.getStructureName());
//			row.createCell(3).setCellValue(warehouse.getProvince() == null?"":warehouse.getProvince()); 
			row.createCell(4-1).setCellValue(warehouse.getCity() == null?"":warehouse.getCity()); 
			row.createCell(5-1).setCellValue(warehouse.getCity() == null?"":warehouse.getCity()); 
			row.createCell(6-1).setCellValue(warehouse.getAddr() == null?"":warehouse.getAddr());   
			row.createCell(7-1).setCellValue(warehouse.getArea()== null?"":warehouse.getArea().toString());
			row.createCell(8-1).setCellValue(warehouse.getContact()== null?"":warehouse.getContact());
			row.createCell(9-1).setCellValue(warehouse.getTelephoneNo()== null?"":warehouse.getTelephoneNo());
			
			 
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


	
	
	 /*
     * 修改页面
     * 刘黎明
     */
	@RequestMapping(value="/showEditwarehouse/{warehouseid}",produces="application/json")
	public String showEditwarehouse(@PathVariable("warehouseid")Integer warehouseid ,Model model) {		
		Warehouse warehouse = warehouseService.showWarehouse(warehouseid);
		model.addAttribute("warehous",warehouse) ;
		return "/posm/showeditwarehouseList";

	}
	 /*
     * 修改
     * 刘黎明
     */
	@RequestMapping(value = "/updateWarehouse")
	@ResponseBody
	public ResultMessage updateWarehouse(Warehouse warehouse) {
		int clientId = getClientId();
		warehouse.setClientId(clientId);		 
		warehouseService.updateWarehouse(clientId, warehouse);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	
	/*
     * 显示
     * 刘黎明
     */
	
	@RequestMapping(value="/showWarehouseDetail/{warehouseid}")
	public String showWarehouseDetail(@PathVariable("warehouseid")Integer warehouseid,Model model) {
		Warehouse warehouse = warehouseService.showWarehouse(warehouseid);
		model.addAttribute("warehous",warehouse) ;
		return "/posm/showwarehouseList";
		
	}
	
	/*
     * 删除
     * 刘黎明
     */
	@RequestMapping(value = "/deleteWarehouse/{warehouseId}",produces="application/json")
	@ResponseBody
	public Object deleteWarehouse(@PathVariable("warehouseId")Integer warehouseId) {
		int clientId = getClientId();
		warehouseService.deleteWarehouse(clientId, warehouseId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	/*
     * 仓库名称
     * 刘黎明
     */
	@RequestMapping(value ="/findForwarehouseName/{warehousename}",produces="application/json")
	@ResponseBody
	public Warehouse findForwarehouseName(@PathVariable("warehousename")String warehousename){
		int clientId = getClientId();
		Warehouse warehouse=warehouseService.findWarehouseName(warehousename, clientId);
		return warehouse;
	}
	/*
     * 仓库编号
     * 刘黎明
     */
	@RequestMapping(value ="/findForwarehouseNo/{warehouseno}",produces="application/json")
	@ResponseBody
	public Warehouse findForwarehouseNo(@PathVariable("warehouseno")String warehouseno){
		int clientId = getClientId();
		Warehouse warehouse=warehouseService.findWarehouseNo(warehouseno, clientId);
		return warehouse;
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
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
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

	/**
	 * 
	 * @param name		仓库名称模糊查询
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月9日
	 */
	@ResponseBody
	@RequestMapping(value = "/getWarehousesByLikeName",produces="application/json")
	public List<KeyValueVo> getWarehousesByLikeName(String name){
		List<Warehouse> list = warehouseService.findWareHouseAjax(getClientId(), name);
		if(null != list && list.size() > 0){
			List<KeyValueVo> retList = new ArrayList<KeyValueVo>();
			for (Warehouse warehouse : list) {
				retList.add(new KeyValueVo(warehouse.getWarehouseName(), warehouse.getWarehouseId()));
			}
			return retList;
		}
		return null;
	}
	
	/**
	 * 
	 * @param warehousesId
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月9日
	 */
	@ResponseBody
	@RequestMapping(value="/getWarehousesByKey/{warehousesId}",produces="application/json")
	public KeyValueVo getWarehousesByKey(@PathVariable("warehousesId")Integer warehousesId) {
		if(null != warehousesId){
			Warehouse warehouse = warehouseService.findWareHouseByKey(getClientId(), warehousesId);
			if(null != warehouse){
				return new KeyValueVo(warehouse.getWarehouseName(),warehouse.getWarehouseId());
			}
		}
		return null;
	}
	
}
	
	
	
	
	
	

