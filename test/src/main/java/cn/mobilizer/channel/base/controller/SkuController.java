/**
 * 
 */
package cn.mobilizer.channel.base.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.po.SkuGroup;
import cn.mobilizer.channel.base.po.SkuGroupMapping;
import cn.mobilizer.channel.base.po.SkuSeries;
import cn.mobilizer.channel.base.po.SkuType;
import cn.mobilizer.channel.base.po.Unit;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.service.CategoryService;
import cn.mobilizer.channel.base.service.SkuGroupMappigService;
import cn.mobilizer.channel.base.service.SkuGroupSerivce;
import cn.mobilizer.channel.base.service.SkuSeriesService;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.base.service.SkuTypeService;
import cn.mobilizer.channel.base.service.UnitService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.util.PropertiesHelper;

/**
 * @author yeeda.tian
 * Sku管理Controller
 * 2014年11月12日17:58:36
 */

@Controller
@RequestMapping(value = "/sku")
public class SkuController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1997444411661968749L;
	@Autowired
	private SkuService skuService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UnitService unitSerive;
	@Autowired
	private SkuGroupSerivce sgSerivce;
	@Autowired
	private SkuGroupMappigService sgmService;
	@Autowired
	private SkuGroupSerivce sgService;
	@Autowired
	private SkuTypeService skuTypeService;
	@Autowired
	private SkuSeriesService skuSeriesService;
	
	 /**
	  * 门店管理-查询列表
	  * @param model
	  * @param page
	  * @param searchUserName
	  * @param searchUserNo
	  * @param searchClientStructureId
	  * @param req
	  * @return
	  * @throws BusinessException
	  */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String searchSkuName,String searchSkuNo,Integer brandId,String brand,String category,Integer categoryId, 
			HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("skuName", searchSkuName);
		parameters.put("skuNo", searchSkuNo);
		parameters.put("brandId", brandId);
		parameters.put("categoryId", categoryId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = skuService.querySkuCount (parameters);	
		int pagenum = page == null ? 1 : page;
		Page<Sku> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<Sku> list = skuService.querySkuList (parameters);
		pageParam.setItems(list);
		
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("searchSkuName", searchSkuName);
		model.addAttribute("searchSkuNo",searchSkuNo);
		model.addAttribute("brand",brand);
		model.addAttribute("brandId",brandId);
		model.addAttribute("category",category);
		model.addAttribute("categoryId",categoryId);
		model.addAttribute("clientId",clientId);
		model.addAttribute("page", pageParam.getPage().toString());
		
		return "/base/skuList";
	}
	
//	@RequestMapping(value = "showImportDialog")
//	public String showImportDialog(Model model){
//		model.addAttribute("clientId", getClientId());
//		return "base/importSKU";
//	}
	
	/**
	 * 导入页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showImportDialog")
	public String showImportColgateDialog(Model model){
		int clientId = getClientId();
		model.addAttribute("clientId", clientId);
		return "base/importSKU";
	}
	/**
     * <p>Description: 产品导入或SKU价格导入</p>
	 * @throws Exception 
	 * 单位	客户名称	客户代码	地址	电话

     */
	@RequestMapping(value = "mixImport", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultMessage mixImport(MultipartFile file, HttpServletRequest request,HttpServletResponse resp,Integer clientId) throws Exception {
		ShiroUser shiroUser = getShiroUser();
		if(clientId != null && clientId.equals(Constants.COLGATE_CLIENTID)){
			return skuService.importColgate(file, request, resp);
		}else{
			return skuService.importSKU(file, request, resp,shiroUser);
		}
	}
//	/**
//	 * 高露洁导入
//	 * @param file
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="/importColgate")
//	@ResponseBody
//	public ResultMessage importColgate(MultipartFile file, HttpServletRequest request,HttpServletResponse resp){
//		ExcelReader reader = new ExcelReader(file);
//		//读取Excel文件第一个sheet的数据
//		List<String[]> values = reader.getAllData(0);
//		//取第一行表头
//		String[] titles = values.get(0);
//		
//		Map<String,Sku> skuMap = skuService.getSkuNoMap(Constants.COLGATE_CLIENTID);
//		
//		List<String[]> errorData = new ArrayList<String[]>();
//		List<String> errorMessage = new ArrayList<String>();
//		errorData.add(titles);
//		List<Sku> skuPricelist = new ArrayList<Sku>();
//		//列名校验
//		for (int i = 0; i < titles.length; i++) {
//			if(StringUtils.isEmpty(titles[i])){
//				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
//				rm.setMessage("列名不能为空");
//				return rm;
//			}
//			if(!MobiStringUtils.contains(ImportConstants.COLGATE_TITLES, titles[i])){
//				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
//				rm.setMessage("存在不能识别列：" +titles[i]);
//				return rm;
//			}
//		}
//		
//		out:for (int i = 1; i < values.size(); i++) {
//			Sku sku = new Sku();
//			sku.setClientId(Constants.COLGATE_CLIENTID);
//			String[] rowData = values.get(i);
//			for (int j = 0; j < rowData.length; j++) {
//				String cellValueSkuNo = rowData[0];
//				if(skuMap.containsKey(cellValueSkuNo)){
//					errorData.add(rowData);
//					errorMessage.add("SKU已存在");
//					continue out;
//				}else{
//					sku.setSkuNo(cellValueSkuNo);
//					String cellValueSkuPrice = rowData[1];
//					BigDecimal nkvalue;
//					try {
//						nkvalue = new BigDecimal(cellValueSkuPrice);
//						sku.setPrice(nkvalue);
//					} catch (Exception e) {
//						errorData.add(rowData);
//						errorMessage.add("RSP未能识别");
//						continue out;
//					}
//				}
//			}
//			skuPricelist.add(sku);
//		}
//		
//		/**插入SKU数据*/
//		if (null != skuPricelist && !skuPricelist.isEmpty()) {
//			skuService.insertlist(skuPricelist);
//		}
//		Map<String, Object> resultMessage = new HashMap<String, Object>();
//		resultMessage.put("errorCount", errorData.size() - 1);
//		resultMessage.put("successCount", skuPricelist.size());
//		if (errorMessage != null && !errorMessage.isEmpty()) {
//			this.importResult(request, resp, resultMessage,errorData,errorMessage);
//		}
//		ResultMessage rm = ResultMessage.IMPORT_SUCCESS_RESULT;
//		rm.setAttributes(resultMessage);
//		return rm;
//	}
	
	/**产品管理--新增产品
	 * @author Nany
	 * 2014年12月10日上午11:59:38s
	 * @return
	 */
	@RequestMapping(value="/showAddDialog")
	public String addDialog(Model model) {
		int clientId = getClientId();
		List<Unit> unitList = unitSerive.getUnitList(clientId);
		List<SkuGroup> sgList = sgSerivce.getSkuGroupList(clientId);
			
		model.addAttribute("clientId", clientId);
		model.addAttribute("unitList", unitList);
		model.addAttribute("sgList", sgList);
		return "base/showAddSkuList";
		
	}
	
	/**产品管理--新增--保存
	 * @author Nany
	 * 2014年12月10日下午4:50:36
	 * @returns
	 */
	@RequestMapping(value="/addSku")
	@ResponseBody
	public Object addSku(Sku sku) {
		int clientId = getClientId();
		skuService.addSku(clientId,sku);
		return ResultMessage.ADD_SUCCESS_RESULT;

	}
	
	/**产品管理--编辑
	 * @author Nany
	 * 2014年12月12日下午4:24:59
	 * @param skuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showEditSku/{skuId}",produces="application/json")
	public String editSku(@PathVariable("skuId")Integer skuId ,Model model) {
		int clientId = getClientId();
		Sku sku = skuService.getSku(skuId);
		List<Unit> unList = unitSerive.getUnitList(clientId);
		List<SkuGroup> sgList = sgSerivce.getSkuGroupList(clientId);
		
		SkuGroupMapping skuGroupMapping = sgmService.getObject(skuId);
		if(skuGroupMapping!=null && skuGroupMapping.getSkuGroupId()!=null){
			int skuGroupId = skuGroupMapping.getSkuGroupId();
			SkuGroup skuGroup = sgSerivce.getSkuGroup(skuGroupId);
			sku.setGroupName(skuGroup.getGroupName());
			sku.setMappingId(skuGroupMapping.getMappingId());
		}
		
		model.addAttribute("sku", sku);
		model.addAttribute("unList", unList);
		model.addAttribute("sgList", sgList);
		return "base/showEditSku";

	}
	
	/**产品管理--编辑--保存
	 * @author Nany
	 * 2014年12月12日下午6:22:40
	 * @param sku
	 * @return
	 */
	@RequestMapping(value="/updateSku")
	@ResponseBody
	public Object updateSku(Sku sku) {
		int clientId = getClientId();
		skuService.updateSku(sku,clientId);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**产品管理--删除
	 * @author Nany
	 * 2014年12月12日下午6:26:40
	 * @param skuId
	 * @return
	 */
	@RequestMapping(value="/deleteSku/{skuId}",produces="application/json")
	@ResponseBody
	public Object deleteSku(@PathVariable("skuId")Integer skuId) {
		int clientId = getClientId();
		skuService.deleteSku(clientId,skuId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	@RequestMapping(value="/showSkuDetail/{skuId}")
	public String showSkuDetail(@PathVariable("skuId")Integer skuId,Model model) {
		Sku sku = skuService.getSku(skuId);
		//产品品牌
		Brand brand = null;
		if(sku.getBrandId() != null){
			brand = brandService.getBrand(sku.getBrandId());
		}
		//产品品类
		Category category = null;
		if (sku.getCategoryId() != null) {
			category = categoryService.getCategory(sku.getCategoryId());
		}
		
		//单位
		Unit unit = null;
		if (sku.getUnitId() != null) {
			unit = unitSerive.getUnit(sku.getUnitId());
		}
		//产品类型
		SkuType skutype=null;
		if(sku.getskuTypeId()!=null){
			skutype=skuTypeService.selectByskutypeId(sku.getskuTypeId() );
			
		}
		//产品系列
		SkuSeries skuservice=null;
		if(sku.getSkuSeriesId()!=null){
			skuservice=skuSeriesService.selectbyskuseries(sku.getSkuSeriesId());
			
		}
		
		//产品分组
		SkuGroupMapping skuGroupMapping = sgmService.getObject(skuId);
		SkuGroup skuGroup = null;
		if(skuGroupMapping != null){
			Integer skuGroupId = skuGroupMapping.getSkuGroupId();
			skuGroup = sgService.getSkuGroup(skuGroupId);
		}
		
		model.addAttribute("sku", sku);
		model.addAttribute("brand", brand);
		model.addAttribute("category", category);
		model.addAttribute("unit", unit);
		model.addAttribute("skuGroup", skuGroup);
		model.addAttribute("skutype",skutype);
		model.addAttribute("skuservice",skuservice);
		return "/base/showSkuDetail";
	}
	
	/**
	 * 获取sku信息
	 * @param storeName
	 * @return
	 */ 
	@RequestMapping(value = "getSkuInfoByName")
	@ResponseBody
	public List<Sku> getSkuInfoByName(String q, Integer page){
		int clientId = getClientId (); 
		String skuName = q;		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("skuName", skuName);
		List<Sku> skus = skuService.getSkuByName(parameters);
 		return skus;
	}
	
	@RequestMapping(value="/getSku/{skuId}",produces="application/json")
	@ResponseBody
	public Sku getSku(@PathVariable("skuId")Integer skuId){
		Sku sku = skuService.getSku(skuId);
		return sku;
	}
	
	@RequestMapping(value="/getSkuAjax", produces="application/json")
	@ResponseBody
	public List<Sku> getSkuAjax(){
		Integer clientId = getClientId();
		List<Sku> skus = skuService.findSkuByClientId(clientId);
		return skus;
	}
	
	@RequestMapping(value="/getSkuToSelectTwo", produces="application/json")
	@ResponseBody
	public List<Sku> getSkuToSelectTwo(){
		int clientId = getClientId();
		return	skuService.selectSkuToSelectTwo(clientId);
	}
	
	
	
}
