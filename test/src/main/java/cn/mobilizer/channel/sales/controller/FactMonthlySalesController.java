package cn.mobilizer.channel.sales.controller;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
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

import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.base.service.CategoryService;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.base.service.StoreUserMappingService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.sales.po.FactMonthlySales;
import cn.mobilizer.channel.sales.po.FactMonthlySalesSearchVo;
import cn.mobilizer.channel.sales.po.FactMonthlySalesVo;
import cn.mobilizer.channel.sales.service.FactMonthlySalesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.PropertiesHelper;

@Controller
@RequestMapping(value = "/factMonthlySales")
public class FactMonthlySalesController extends BaseActionSupport  {
	
	private static final long serialVersionUID = 2489597253448963758L;
	@Autowired
	private FactMonthlySalesService factMonthlySalesService;
	@Autowired
	private CityService cityService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private StoreUserMappingService storeUserMappingService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/query")
	public String query(Model model,Integer page,HttpServletRequest req,FactMonthlySalesSearchVo factMonthlySalesSearchVo) throws BusinessException {		 
		Map<String,Object> param = new HashMap<String,Object>();
		page = page==null?1:page;
		param.put("clientId",getClientId());
		String monthId = factMonthlySalesSearchVo.getMonthId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		if(StringUtils.isEmpty(factMonthlySalesSearchVo.getMonthId())){
			Calendar cal = Calendar.getInstance();
			Date date=new Date();		
	        cal.setTime(date);
			factMonthlySalesSearchVo.setMonthId(sdf.format(date));
			Integer year = cal.get(Calendar.YEAR);
			Integer month = cal.get(Calendar.MONTH)+1;
			if(month<10){
				monthId = year+"0"+month;
			}else{					
				monthId = year.toString() + month.toString();
			}			
		}
		ClientUser clientUser = clientUserService.selectByPrimaryKey(getCurrentUserId());
		String clientUserIds = channelCommService.getSubordinates(getClientId().toString());
		String subAllStructureId = channelCommService.getSubStructrueIds(clientUser.getClientStructureId());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		param.put("userIds",clientUserIds);
		param.put("structureIds",deptIds);
		param.put("monthId",new Integer(monthId));
		param.put("structureId",StringUtils.isEmpty(factMonthlySalesSearchVo.getStructureId())?null:factMonthlySalesSearchVo.getStructureId());
		param.put("channelId",StringUtils.isEmpty(factMonthlySalesSearchVo.getChannelId())?null:factMonthlySalesSearchVo.getChannelId());
		param.put("chainId",StringUtils.isEmpty(factMonthlySalesSearchVo.getChainId())?null:factMonthlySalesSearchVo.getChainId());
		param.put("pageStart",(page-1)*Page.DEFAULT_PAGE_SIZE);		
		param.put("pageSize",Page.DEFAULT_PAGE_SIZE);
		param.put("flag",ReportGlobal.ReportFlag.QUERY);
		try{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			List<?> lists = (List<?>) factMonthlySalesService.getEntityListByProcedure(param);		
			Page<FactMonthlySalesVo> pageParam = null;
			Integer count = 0;
			if(null!=lists && lists.size()>=2){
				List<FactMonthlySalesVo> vos = (List<FactMonthlySalesVo>) lists.get(ReportGlobal.Procedure.VALUE);
				List<Integer> ListCount = (List<Integer>) lists.get(ReportGlobal.Procedure.ITEMS);	
				count = ListCount.get(0);
				pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, page);									
				pageParam.setItems(vos);			
			}else{
				pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, page);							
				pageParam.setItems(new ArrayList<FactMonthlySalesVo>());			
			}
			pageParam.buildUrl(req);
			model.addAttribute("count", count);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("page", page);
			model.addAttribute("factMonthlySalesSearchVo", factMonthlySalesSearchVo);	
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);   
		}
		return "/factMonthlySales/factMonthlySalesList";
	}	
	
	@RequestMapping(value="/showImportDialog")
	public String showImportDialog(Model model){	
		model.addAttribute("clientId", getClientId());
		return "/factMonthlySales/importFactMonthlySales";
	}
	
	/**
     * <p>Description: 销量导入</p>
	 * @throws Exception
     */
	@RequestMapping(value = "import")
	@ResponseBody
	public Object importStore(MultipartFile fileField, HttpServletRequest req) throws Exception {
		 		
		Map<String,Object> map = factMonthlySalesService.importDataValidata(fileField, getClientId(), getCurrentUserId());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		List<String[]> errDataList = (List<String[]>) map.get("errDataList");
		List<String> errStrList = (List<String>) map.get("errStrList");
		List<Store> successList = (List<Store>) map.get("successList");
		if(errDataList!=null && errDataList.size()>0){
			/**创建一个Excel文件*/
			XSSFWorkbook  wb = new XSSFWorkbook();
			/**在we中创建一个sheet*/
			XSSFSheet wTSSheet = wb.createSheet("销量信息");
			/**在wTSSheet值添加表头(第0行)*/
			XSSFRow row = wTSSheet.createRow((int)0);
			/**创建单元格，并设置表头，设置表头居中,背景颜色*/
			XSSFCellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(HSSFColor.AQUA.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
			XSSFCell cell = row.createCell(0);  
			cell.setCellValue("错误提示");
			cell = row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue("客户自编号");	
			cell = row.createCell(2);
			cell.setCellStyle(style);
			cell.setCellValue("店铺编号");	
			cell = row.createCell(3);
			cell.setCellStyle(style);
			cell.setCellValue("店铺名称");
			cell = row.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue("大区");		
			cell = row.createCell(5);
			cell.setCellStyle(style);
			cell.setCellValue("分部");
			cell = row.createCell(6);
			cell.setCellStyle(style);
			cell.setCellValue("省份");
			cell = row.createCell(7);
			cell.setCellStyle(style);
			cell.setCellValue("城市");
			cell = row.createCell(8);
			cell.setCellStyle(style);
			cell.setCellValue("销售月份");
			cell = row.createCell(9);
			cell.setCellStyle(style);
			cell.setCellValue("产品条码");
			cell = row.createCell(10);
			cell.setCellStyle(style);
			cell.setCellValue("SKU");
			cell = row.createCell(11);
			cell.setCellStyle(style);
			cell.setCellValue("销量");
			cell = row.createCell(12);
			cell.setCellStyle(style);
			cell.setCellValue("含税销售额（元）");			
			if (errDataList != null && errDataList.size() >0) {
				for (int i = 0; i < errDataList.size(); i++) {
					row = wTSSheet.createRow((int) i + 1); 
					String[] cellValues = errDataList.get(i);
					for (int j = 0;j<cellValues.length+1; j++) {	
						  XSSFCell cellData = row.createCell(j);
						  if(j == 0){   								//批注加在第一列
							 /* HSSFPatriarch p=wTSSheet.createDrawingPatriarch();		 						
							  HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));					
							  comment.setString(new HSSFRichTextString(errStrList.get(i)));
							  comment.setAuthor("toad");					
							  cellData.setCellComment(comment);	  */
							  cellData.setCellValue(errStrList.get(i));
						  }else{							  
							  cellData.setCellValue(cellValues[j-1]);   //第一列是错误批注   j-1 
						  }										  
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
				  String excelName = "errDataMonthSalesExcel"+"_"+System.currentTimeMillis()+".xlsx";
				  returnMap.put("errDataExcelPath",excelName);
				  File file = null;				   
				  if(errDataExcelPath.indexOf("/")==0){
					  file = new File(fileMkdir.getPath()+"/"+excelName);					 
				  }else{
					 file = new File(fileMkdir.getPath()+"\\"+excelName);					
				  }				 
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
		returnMap.put("UnknownColumnName", map.get("UnknownColumnName"));
		returnMap.put("successCount",successList.size());
		returnMap.put("errorCount", errStrList.size());
		return returnMap;
	}
	
	/**
	 * 销量导出
	 * @param factMonthlySalesSearchVo
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value="/exportFactMonthlySales")	 
	public void exportFactMonthlySales(FactMonthlySalesSearchVo factMonthlySalesSearchVo,HttpServletResponse resp) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientId",getClientId());
		String monthId = factMonthlySalesSearchVo.getMonthId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		if(StringUtils.isEmpty(factMonthlySalesSearchVo.getMonthId())){
			Calendar cal = Calendar.getInstance();
			Date date=new Date();		
	        cal.setTime(date);
			factMonthlySalesSearchVo.setMonthId(sdf.format(date));
			Integer year = cal.get(Calendar.YEAR);
			Integer month = cal.get(Calendar.MONTH)+1;
			if(month<10){
				monthId = year+"0"+month;
			}else{					
				monthId = year.toString() + month.toString();
			}			
		}
		ClientUser clientUser = clientUserService.selectByPrimaryKey(getCurrentUserId());
		String clientUserIds = channelCommService.getSubordinates(getClientId().toString());
		String subAllStructureId = channelCommService.getSubStructrueIds(clientUser.getClientStructureId());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		param.put("userIds",clientUserIds);
		param.put("structureIds",deptIds);
		param.put("monthId",new Integer(monthId));
		param.put("structureId",StringUtils.isEmpty(factMonthlySalesSearchVo.getStructureId())?null:factMonthlySalesSearchVo.getStructureId());
		param.put("channelId",StringUtils.isEmpty(factMonthlySalesSearchVo.getChannelId())?null:factMonthlySalesSearchVo.getChannelId());
		param.put("chainId",StringUtils.isEmpty(factMonthlySalesSearchVo.getChainId())?null:factMonthlySalesSearchVo.getChainId());
		param.put("flag",ReportGlobal.ReportFlag.EXPORT);		
		List<FactMonthlySalesVo> factMonthlySalesVoList = null;
		try{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			factMonthlySalesVoList = (List<FactMonthlySalesVo>) factMonthlySalesService.getEntityListByProcedure(param);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE); 
		}
		ClientStructure clientStructure = clientStructureService.getClientStructureById(getClientStructureId());
		String structureName = null;
		if(clientStructure != null){
			structureName = clientStructure.getStructureName();
		}
		Map<String, String> columns = new HashMap<String,String>();
		columns.put("monthId", "月份编号");
		columns.put("storeNo", "门店编号");
		columns.put("storeName", "门店名称");
		columns.put("salesArea", "大区");
		columns.put("salesDivision", "分部");
		columns.put("province", "省");
		columns.put("city", "市");
		columns.put("channelName", "渠道");
		columns.put("chainName", "连锁(店铺渠道)");
		columns.put("distributorName", "经销商");
		columns.put("monthDesc", "月份");
		columns.put("barcode", "产品条码");
		columns.put("skuName", "sku名称");
		columns.put("salesVolume", "销售数量");
		columns.put("salesAmount", "销售金额");
		columns.put("sellingPrice", "零售");
		columns.put("retailPrice", "建议零售价");
		columns.put("price", "标准零售价");
		columns.put("salesman", "业代");
		columns.put("salesLeader", "业代主管");
		columns.put("promoter", "促销员(ba)");
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		Set<String> fields = columns.keySet();
		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCell cell = null;
		int cellNum = 0;
		for (String t : fields) {
			cell = row.createCell(cellNum);
			cell.setCellValue(columns.get(t));
			cellNum++;
		}
		if (factMonthlySalesVoList != null && factMonthlySalesVoList.size() >0) {
			for (FactMonthlySalesVo factMonthlySalesVo : factMonthlySalesVoList) {
				rowNum++;
				row = sheet.createRow(rowNum);
				cellNum = 0;
				for (String fieldName : fields) {
					PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, factMonthlySalesVo.getClass());
					Object result = descriptor.getReadMethod().invoke(factMonthlySalesVo);
					if(null!=result){
						cell = row.createCell(cellNum);
						cell.setCellValue(formatResult(result));
					}
					cellNum++;
				}
			}
		}
		String excelName = structureName+"--销量信息"+"_"+System.currentTimeMillis();
		resp.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(excelName+".xlsx","UTF-8"));
		resp.setContentType("application/vnd.ms-excel");
		try {
			OutputStream out = resp.getOutputStream();
			wb.write(out);
			out.close();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/showEditFactMonthlySales")
	public String showEditFactMonthlySales(Integer dataId, Model model){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("dataId", dataId);	
		FactMonthlySales factMonthlySales = factMonthlySalesService.getEntityByDataId(param);
		model.addAttribute("factMonthlySales", factMonthlySales);
		return "/factMonthlySales/showEditFactMonthlySales";
	}
	
	@RequestMapping(value="/showFactMonthlySales")
	public String showFactMonthlySales(Integer dataId, Model model){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("dataId", dataId);	
		FactMonthlySales factMonthlySales = factMonthlySalesService.getEntityByDataId(param);
		model.addAttribute("factMonthlySales", factMonthlySales);
		return "/factMonthlySales/showFactMonthlySales";
	}
	
	@RequestMapping(value="/showAddFactMonthlySales")
	public String showAddFactMonthlySales(){
		return "/factMonthlySales/showAddFactMonthlySales";
	}
	
	@RequestMapping(value="/updateFactMonthlySales")
	@ResponseBody
	public Object updateFactMonthlySales(FactMonthlySales factMonthlySales){		
		try{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			factMonthlySalesService.updateFactMonthlySales(factMonthlySales);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}				
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	@RequestMapping(value="/addFactMonthlySales")
	@ResponseBody
	public Object addFactMonthlySales(FactMonthlySales factMonthlySales){
		factMonthlySales.setClientId(getClientId());
		factMonthlySales.setClientUserId(getCurrentUserId());
		factMonthlySales.setOrderType(Constants.SALES_ORDER_TYPE_IMPORT);
		factMonthlySales.setLastUpdateBy(getCurrentUserId());
		try{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			factMonthlySalesService.addFactMonthlySales(factMonthlySales);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return ResultMessage.ADD_SUCCESS_RESULT;
	}	
	
	private String formatResult(Object t){
		if(t instanceof Date){
			return DateFormatUtils.format((Date)t, "yyyy-MM-dd");
		}
		return t.toString();
	}
	
	
	
	
	/**
	 * 费列罗:销量列表
	 * @param model
	 * @param page
	 * @param req
	 * @param factMonthlySalesSearchVo
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryForFerrero")
	public String queryForFerrero(Model model,Integer page,HttpServletRequest request) throws BusinessException {
		
		return "/factMonthlySales/factMonthlySalesListForFerrero";
	}
	
	/**
	 * 费列罗:弹出导入销量界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showFerreroImportDialog")
	public String showFerreroImportDialog(Model model){	
		model.addAttribute("clientId", getClientId());
		return "/factMonthlySales/showFerreroImportDialog";
	}
	
	/**
	 * 费列罗：导入销量数据
	 * @return
	 */
	@RequestMapping(value="/importFerrero",produces="application/json")
	@ResponseBody
	public Object importFerrero(MultipartFile fileField,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> message = new HashMap<String,Object>();
		try {
			if(null==fileField){
				message.put("errorMsg","导入模板不能为空");
			}else{
				//由于查询夸库了，不能在service层切换数据库，所有将此方法写在controller层
				Map<String, Store> mapStore = storeService.getStoreNoMap(super.getClientId());
				Map<String, Sku> mapSku = skuService.getSkuNoMap(super.getClientId());
				
				CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
				message = (Map<String,Object>)factMonthlySalesService.addFactMonthlySale(fileField,super.getClientId(),mapStore,mapSku,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.put("errorMsg","导入失败");
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return message;
	}
	
	/**
	 * 尤妮佳销量列表
	 * @return
	 */
	@RequestMapping(value = "unicharmQuery")
	public String unicharmQuery(){
		
		return "/factMonthlySales/factMonthlyUnicharmSalesList";
	}
	
	
	/**
	 * 尤妮佳销量导入
	 * @param fileField
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "importUnicharmSales")
	@ResponseBody
	public Object importUnicharmSales(MultipartFile fileField,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> message = new HashMap<String, Object>();
		try {
			if(null==fileField){
				message.put("errorMsg", "导入模板不能为空");
			}else{
				ExcelReader reader = new ExcelReader(fileField);
				List<String[]> values = reader.getAllData(0);
				//由于查询夸库了，不能在service层切换数据库，所有将此方法写在controller层
				Map<String, Store> mapStore = storeService.getStoreNoMap(super.getClientId());//根据门店编号获取门店信息
				Map<String, ClientStructure> mapDept = clientStructureService.getClientStructureMapByName(super.getClientId());//根据部门名称获取部门信息
				Map<String, ClientUser> mapClientUser = clientUserService.findClientUserBylogName(super.getClientId()); //获取用户信息
				Map<String, StoreUserMapping> mapStoreUser = storeUserMappingService.getStoreUserMappingMap(super.getClientId()); //门店人员信息 storeId key
				Map<String, Category> mapCategory = categoryService.getCategoryMap(super.getClientId()); //品类 key name
				CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
				message = factMonthlySalesService.addFactMonthlyUnicharmSales(values, mapStore, mapDept, mapClientUser, mapStoreUser, mapCategory,super.getClientId());
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
		return message;
	}
	
	
}
