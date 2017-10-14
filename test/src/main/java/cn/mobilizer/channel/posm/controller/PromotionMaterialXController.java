package cn.mobilizer.channel.posm.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum.POSM_MATERIAL_TYPE;
import cn.mobilizer.channel.comm.vo.Enum2Bean;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.vo.ChannelEnum.POSM_IN_OUT_TYPE;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.po.PromotionMaterial;
import cn.mobilizer.channel.posm.service.PromotionMaterialXService;
import cn.mobilizer.channel.posm.vo.KeyValueVo;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.util.PropertiesHelper;


@Controller
@RequestMapping(value ="promotionMaterial")
public class PromotionMaterialXController extends BaseActionSupport{
	
	@Autowired
	private PromotionMaterialXService promotionMaterialXService;
	
	@RequestMapping(value="/query")
	public String query(Model model,Integer page,String materialCode,String materialName,HttpServletRequest req)throws BusinessException
	{
		int clientId = getClientId ();
		Map<String,Object> paramster=new HashMap<String,Object>();
		//paramster.put("clientId", clientId);
		paramster.put("materialCode", materialCode);
		paramster.put("materialName", materialName);
		paramster.put("clientId", clientId);
		paramster.put("isDelete",Constants.IS_DELETE_FALSE);
		int count=promotionMaterialXService.queryPromotionMaterialByCount(paramster);
		
		int pagenum= page ==null ?1:page;
		Page<PromotionMaterial> pageParam=Page.page(count,Page.DEFAULT_PAGE_SIZE,pagenum);
		pageParam.buildUrl(req);
		paramster.put("_start",pageParam.getStartRows());
		paramster.put("_size",pageParam.getPageSize());
		//paramster.put("_orderby","knowledgeId");
		//paramster.put("_order","DESC");
		List<PromotionMaterial> list=promotionMaterialXService.queryPromotionMaterialList(paramster);
		
		pageParam.setItems(list);
		model.addAttribute("pageParam",pageParam);
		model.addAttribute("page",pageParam.getPage().toString());
		model.addAttribute("materialCode", materialCode);
		model.addAttribute("materialName",materialName);
		return "/posm/promotionMaterialList" ;
	}

	@RequestMapping(value="/showAddPromotionMaterial")
	public String showAddPromotionMaterial()
	{
		return "/posm/showAddPromotionMaterial";
	}
	
	/**
	 * 增加促销物料
	 * @param promotionMateria
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/addPromotionMaterial",produces="application/json")
	@ResponseBody
	public Object addPromotionMaterial(PromotionMaterial promotionMateria)throws BusinessException
	{
		if(log.isDebugEnabled())
		{
			log.debug("start method<addPromotionMaterial>");
		}
		int clientId = getClientId();
		int clientUserId=getClientUser().getClientUserId();
		int lastUpdateBy=getCurrentUserId();
		promotionMateria.setClientId(clientId);
		promotionMateria.setCreateBy(clientUserId);
		promotionMateria.setLastUpdateBy(lastUpdateBy);
		promotionMaterialXService.addPromotionMaterial(promotionMateria);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	/**
	 * 删除促销物料
	 * @param materialId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/showDeletePromotionMaterial/{materialId}",produces="application/json")
	@ResponseBody
	public Object showDeletePromotionMaterial(@PathVariable("materialId")Integer materialId)throws BusinessException
	{
		if(log.isDebugEnabled())
		{
			log.debug("delete method<delPromotionMaterial>");
		}
		promotionMaterialXService.delPromotionMaterial(materialId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	/**
	 * 编辑促销物料
	 * @param materialId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/showEditPromotionMaterial/{materialId}")
	public Object showEditPromotionMaterial(@PathVariable("materialId")Integer materialId,Model model)throws BusinessException
	{
		PromotionMaterial promotionMaterial=promotionMaterialXService.queryPromotionMaterialById(materialId);
		model.addAttribute("promotionMaterial",promotionMaterial);
		return "/posm/showEditPromotionMaterial";
		
	}
	
	@RequestMapping(value="/editPromotionMaterial")
	@ResponseBody
	public Object editPromotionMaterial(PromotionMaterial promotionMaterial)throws BusinessException{
		if(log.isDebugEnabled())
		 {
			 log.debug("edit method<editPromotionMaterial>");
		 }
		 promotionMaterialXService.updatePromotionMaterial(promotionMaterial);
		 return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	/**
	 * 查看促销物料
	 * @param materialId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/showCheckPromotionMaterial/{materialId}")
	public Object showCheckPromotionMaterial(@PathVariable("materialId")Integer materialId,Model model)throws BusinessException
	{
		PromotionMaterial promotionMaterial=promotionMaterialXService.queryPromotionMaterialById(materialId);
		model.addAttribute("promotionMaterial",promotionMaterial);
	  return "/posm/showCheckPromotionMaterial";
	}
	
	/**
	 * 校验materialName
	 * @param materialName
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value="/queryPromotionMaterialByParm/{materialName}")
	public void queryPromotionMaterialByParm(@PathVariable("materialName")String materialName,HttpServletResponse res) throws IOException
	{
		String flag="error";
		List<PromotionMaterial> list=promotionMaterialXService.queryPromotionMaterialByNames();
		List<String> materialNameList=new ArrayList<String>();
		for (PromotionMaterial promotionMaterial : list) {
			String name=promotionMaterial.getMaterialName();
			materialNameList.add(name);
		}
		 if(materialNameList.contains(materialName))
		 {
			 flag="success";
		 }
		PrintWriter out=res.getWriter();
		out.print(flag);
		out.close();
		
	}
	/**
	 * 校验materialCode
	 * @param materialCode
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value="/queryPromotionMaterialmaterialCode/{materialCode}")
	public void queryPromotionMaterialmaterialCode(@PathVariable("materialCode")String materialCode,HttpServletResponse res)throws IOException
	{
		String flag="error";
		List<PromotionMaterial> list=promotionMaterialXService.queryPromotionMaterialByNames();
		List<String> materialCodeList=new ArrayList<String>();
		for (PromotionMaterial promotionMaterial : list) {
             String code=promotionMaterial.getMaterialCode();
             materialCodeList.add(code);
		}
		if(materialCodeList.contains(materialCode))
		{
			flag="success";
		}
		PrintWriter out=res.getWriter();
		out.print(flag);
		out.close();
	}
	/**
	 * 促销物料的导入
	 * @return
	 */
	@RequestMapping(value="/showImportDialog")
	public Object showImportDialog(Model model){
		model.addAttribute("clientId", getClientId());
		return "/posm/importPromotionMaterial";
	}
	@RequestMapping(value="/import")
	@ResponseBody
	public Object importPromotionMaterial(MultipartFile file, HttpServletRequest req)throws Exception{
		 ExcelReader reader=new ExcelReader(file);
		 Integer eachCount = 1000;
		 Integer rowCount = reader.getRowNum(0)+1;
		 int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		 
	     List<PromotionMaterial> list=promotionMaterialXService.queryPromotionMaterialByNames();
		 Map<String,Object> map=new HashMap<String,Object>();
		 List<PromotionMaterial> allPromotionMaterial=new ArrayList<PromotionMaterial>();
		 List<String> errStrList=new ArrayList<String>();
		 List<String[]> errDataList=new ArrayList<String[]>();
		 String[] ctitles=null;
		 
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
						if(!MobiStringUtils.contains(ImportConstants.PROMOTION_MATERIAL_TITLE, titles[i])){
							System.out.println("titles[i]="+titles[i]);
							ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
							rm.setMessage("存在不能识别的列名：" +titles[i]);
							return rm;
						}
					} 
				}
				   List<PromotionMaterial> subSheetPromotionMaterial=new ArrayList<PromotionMaterial>();
				   Integer clientId = getClientId();
				   for(int i=1;i<values.size();i++)
				   {
					   PromotionMaterial promotionMaterial=new PromotionMaterial();
					   Method[] methods=promotionMaterial.getClass().getMethods();
					   String[] value=values.get(i);
					   boolean flag = true;
					   for(int j=0;j<value.length;j++)
					   {
						   if(flag)
						   {
							   String kvalue=value[j];//值
							   String cvalue=null;//列名
							   for(int k=0;k<ImportConstants.PROMOTION_MATERIAL_TITLE.length; k++)
							   {
								   if(ImportConstants.PROMOTION_MATERIAL_TITLE[k].equals(ctitles[j])){
									   cvalue = ImportConstants.PROMOTION_MATERIAL_COLUMN[k];
										break;
								   } 
							   }
							   if("materialCode".equals(cvalue))
							    {
								  if(StringUtils.isEmpty(kvalue))
								  {
									  String errStr = "物料编号不能为空";
										errStrList.add(errStr);
										errDataList.add(values.get(i));
										flag = false;
										continue;
								  }else{
									//Map<String,PromotionMaterial> materialMap=new HashMap<String,PromotionMaterial>();
									  List<String> materiallist=new ArrayList<String>();
										for (PromotionMaterial promotionMaterial1 : list) {
								             String code=promotionMaterial1.getMaterialCode();
								             materiallist.add(code);
										}
										if(materiallist.contains(kvalue))
										{
											 String errStr = "物料编号已存在";
											 errStrList.add(errStr);
											 errDataList.add(values.get(i));
											 flag = false;
											 continue;
										} 
								  }
							  }else if("materialName".equals(cvalue))
						         {
								  if(StringUtils.isEmpty(kvalue))
								  {
									    String errStr = "物料名称不能为空";
										errStrList.add(errStr);
										errDataList.add(values.get(i));
										flag = false;
										continue;
								  } else{
									 List<String> materialNameList=new ArrayList<String>();
										for (PromotionMaterial promotionMaterial2 : list) {
								             String name=promotionMaterial2.getMaterialName();
								             materialNameList.add(name);
										}
										if(materialNameList.contains(kvalue))
										{
											 String errStr = "物料名称已存在";
											 errStrList.add(errStr);
											 errDataList.add(values.get(i));
											 flag = false;
											 continue;
										}
								  }
							  }
							   
							   for (int k = 0; k < methods.length; k++) {		
								   System.out.println(methods[k].getName());
									if(StringUtils.isNotEmpty(cvalue) && methods[k].getName().equalsIgnoreCase("set" + cvalue)){
										if(cvalue.equalsIgnoreCase("quantity")||cvalue.equalsIgnoreCase("usedTime")){
											continue;
										}else if(cvalue.equalsIgnoreCase("price")){
											if(StringUtils.isNotEmpty(kvalue)){
												methods[k].invoke(promotionMaterial, new Double(kvalue));
											}
										}else{
											methods[k].invoke(promotionMaterial, kvalue);
										}
										break;
									}
								}
						   }
					   }
					   if(flag){
							//设置关联属性	
							promotionMaterial.setClientId(clientId);
							subSheetPromotionMaterial.add(promotionMaterial);
							allPromotionMaterial.add(promotionMaterial);
						}
				   }
					if(subSheetPromotionMaterial!=null && subSheetPromotionMaterial.size()>0){			
						try{
							promotionMaterialXService.batchSavePromotionMaterial(subSheetPromotionMaterial);
						}catch(Exception ex){
							ex.printStackTrace();
						} 			  
					}
			}
			
			Map<String, String> columns = new HashMap<String,String>();
			for (int k = 0; k < ImportConstants.PROMOTION_MATERIAL_TITLE.length; k++) {
				columns.put(ImportConstants.PROMOTION_MATERIAL_COLUMN[k], ImportConstants.PROMOTION_MATERIAL_TITLE[k]);
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
			cell.setCellValue("品牌");	
			cell = row.createCell(2);
			cell.setCellStyle(style);
			cell.setCellValue("物料分类");	
			cell = row.createCell(3);
			cell.setCellStyle(style);
			cell.setCellValue("物料名称");
			cell = row.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue("数量(箱)");
			cell = row.createCell(5);
			cell.setCellStyle(style);
			cell.setCellValue("物料编号");		
			cell = row.createCell(6);
			cell.setCellStyle(style);
			cell.setCellValue("年份");
			cell = row.createCell(7);
			cell.setCellStyle(style);
			cell.setCellValue("有效时间");
			cell = row.createCell(8);
			cell.setCellStyle(style);
			cell.setCellValue("子分类");
			cell = row.createCell(9);
			cell.setCellStyle(style);
			cell.setCellValue("关键节点");
			cell = row.createCell(10);
			cell.setCellStyle(style);
			cell.setCellValue("单价");
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
					  File file1 = new File(fileMkdir.getPath()+File.separator+excelName);	
					  FileOutputStream fos = new FileOutputStream(file1);			  
					  wb.write(fos);
					  fos.flush();
					  fos.close();
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}else{
				 map.put("errDataExcelPath","");
			}
			map.put("successCount",allPromotionMaterial.size());
			map.put("errorCount", errStrList.size());		
			return map;
	} 
	
	//促销物料导出
	@RequestMapping(value="/exportPromotionMaterial")
	public void exportPromotionMaterial(Model model,Integer page,String materialCode,String materialName,HttpServletRequest req, HttpServletResponse resp)throws UnsupportedEncodingException
	{
		Map<String,Object> paramster=new HashMap<String,Object>();
		//paramster.put("clientId", clientId);
		paramster.put("materialCode", materialCode);
		paramster.put("materialName", materialName);
		paramster.put("clientId", getClientId());
		paramster.put("isDelete",Constants.IS_DELETE_FALSE);
	
//		List<PromotionMaterial> promotionMaterialList=promotionMaterialXService.batchOutPromotionMaterial(paramster);
		
		List<PromotionMaterial> promotionMaterialList=promotionMaterialXService.queryPromotionMaterialList(paramster);
	    if(promotionMaterialList!=null&&promotionMaterialList.size()>0)
	    {
				HSSFWorkbook wb=new HSSFWorkbook();
				String Excelname = "促销物料导出"; 
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
				String[] warehouseHead = ImportConstants.PROMOTION_MATERIAL_IMPORT_TITLE; //表格的标题名称
				for (int i = 0; i < warehouseHead.length; i++) {
					   //创建表中的行
					posm = row.createCell(i);
					posm.setCellValue(warehouseHead[i]);
					posm.setCellStyle(headStyle);
				}
				for (int i = 0; i < promotionMaterialList.size(); i++) {
					Integer materialId=promotionMaterialList.get(i).getMaterialId();
					row = wTSSheet.createRow((int) i + 1);
					PromotionMaterial promotionMaterial1= promotionMaterialList.get(i);  //创建行中的单元格
					HSSFCell createCell = row.createCell(0);
					//行的第一个位置 如果不为空给posmInOut.getHandler()
					row.createCell(0).setCellValue(promotionMaterial1.getBrand()== null?"":promotionMaterial1.getBrand());
					row.createCell(1).setCellValue(promotionMaterial1.getCategory()== null?"":promotionMaterial1.getCategory());
					row.createCell(2).setCellValue(promotionMaterial1.getMaterialName()== null?"":promotionMaterial1.getMaterialName());
					row.createCell(3).setCellValue(promotionMaterial1.getSubCategory() == null?"":promotionMaterial1.getSubCategory()); 
					row.createCell(4).setCellValue(promotionMaterial1.getSpec() == null?"":promotionMaterial1.getSpec());
					 if(promotionMaterial1.getPrice()==null)
					 {
						 row.createCell(5).setCellValue(""); 
					 }
					 else
					 {
					row.createCell(5).setCellValue(promotionMaterial1.getPrice());   
					 }
					if(promotionMaterial1.getQuantity()==null)
					 {
						 row.createCell(5).setCellValue("");  
					 }
					 else
					 {
					row.createCell(6).setCellValue(promotionMaterial1.getQuantity());
					 }
					row.createCell(7).setCellValue(promotionMaterial1.getMaterialCode()== null?"":promotionMaterial1.getMaterialCode());
					row.createCell(8).setCellValue(promotionMaterial1.getBatch()== null?"":promotionMaterial1.getBatch());
					if(promotionMaterial1.getLastUpdateTime()==null)
					{
						 row.createCell(9).setCellValue("");  
						}
					else
					{
					row.createCell(9).setCellValue(DateUtil.getDateTime(DateUtil.dateTimeFormat, promotionMaterial1.getLastUpdateTime()));
					}
					if( promotionMaterial1.getUsedTime()==null)
					{
						row.createCell(10).setCellValue("");  
						
					}
					else
					{
					row.createCell(10).setCellValue(DateUtil.getDateTime(DateUtil.dateTimeFormat, promotionMaterial1.getUsedTime()));
					}
					if(promotionMaterial1.getCalcFlag()==null)
					{ 
						row.createCell(11).setCellValue("");
					}
					else
					{
					row.createCell(11).setCellValue(promotionMaterial1.getCalcFlag());	 
					}
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
	@RequestMapping(value = "/materialByLikeName",produces="application/json")
	public List<KeyValueVo> getMaterialByLikeName(String name){
		List<PromotionMaterial> list = promotionMaterialXService.queryPmListByClinteIdAndLikeName(getClientId(), name);
		if(null != list && list.size() > 0){
			List<KeyValueVo> retList = new ArrayList<KeyValueVo>();
			for (PromotionMaterial material : list) {
				retList.add(new KeyValueVo(material.getMaterialName(), material.getMaterialId()));
			}
			return retList;
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMaterialByKey/{id}",produces="application/json")
	public KeyValueVo getMaterialByKey(@PathVariable("id") Integer id){
		PromotionMaterial promotionMaterial = promotionMaterialXService.queryPromotionMaterialById(id);
		if(null != promotionMaterial){
			return new KeyValueVo(promotionMaterial.getMaterialName(),promotionMaterial.getMaterialId());
		}
		return null;
	}

	
	@ResponseBody
	@RequestMapping(value = "/getType")
	public List<Enum2Bean> getStatus(){
		List<Enum2Bean> ls = new ArrayList<Enum2Bean>();
		for (POSM_MATERIAL_TYPE s :POSM_MATERIAL_TYPE.values()) {
			Enum2Bean e = new Enum2Bean();
			e.setId(s.getKey().intValue());
			e.setName(s.getName());
			ls.add(e);
		}
		return ls;
	}
}
