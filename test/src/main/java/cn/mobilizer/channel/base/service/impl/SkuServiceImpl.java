package cn.mobilizer.channel.base.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.dao.BrandDao;
import cn.mobilizer.channel.base.dao.CategoryDao;
import cn.mobilizer.channel.base.dao.SkuDao;
import cn.mobilizer.channel.base.dao.SkuGroupMappingDao;
import cn.mobilizer.channel.base.dao.UnitDao;
import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.po.SkuGroupMapping;
import cn.mobilizer.channel.base.po.Unit;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.base.vo.SkuPriceVO;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.util.PropertiesHelper;

@Service
public class SkuServiceImpl implements SkuService {
	private static final Log LOG = LogFactory.getLog(SkuServiceImpl.class);
	
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private SkuGroupMappingDao skuGroupMappingDao;
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private UnitDao unitDao;
	

	@Override
	public List<Sku> getObjectList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sku getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer querySkuCount(Map<String, Object> param) throws BusinessException{
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = skuDao.querySkuCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method querySkuCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<Sku> querySkuList(Map<String, Object> param) throws BusinessException{
		List<Sku> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = skuDao.querySkuList(param);
				for (Sku sku : list) {
					sku.getSkuId();
				}
			}
		} catch (BusinessException e) {
			LOG.error("method querySkuCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public boolean saveAll(List<Sku> list) throws Exception {
		boolean result = true;
		for (Sku sku : list) {
			int i = skuDao.insert(sku);
			if(i == 0){
				result = false;
				throw new Exception();
			}
		}
		return result;
	}

	@Override
	public void addSku(int clientId, Sku sku)throws BusinessException {
		try {
			Integer skuGroupId = null;
			sku.setClientId(clientId);
			int skuId = skuDao.addSku(sku);
			if(sku.getSkuGroupId()!=null){
				skuGroupId = sku.getSkuGroupId();
				SkuGroupMapping skuGroupMapping = new SkuGroupMapping();
				skuGroupMapping.setClientId(clientId);;
				skuGroupMapping.setSkuGroupId(skuGroupId);
				skuGroupMapping.setSkuId(skuId);
				skuGroupMappingDao.addSkuGroup(skuGroupMapping);
			}	
		} catch (BusinessException e) {
			LOG.error("method addSku error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}
		
	}

	@Override
	public Sku getSku(Integer skuId)throws BusinessException{
		Sku sku = null;
		Map<String, Object> parames = new HashMap<String, Object>();
		try {
			parames.put("skuId", skuId);
			sku = skuDao.getSku(parames);
		} catch (BusinessException e) {
			LOG.error("method getSku error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return sku;
	}

	@Override
	public void updateSku(Sku sku,int clientId)throws BusinessException {
		Map<String, Object> parames = new HashMap<String, Object>();
 		try {
// 			if (sku.getPrice() == null) {
// 				BigDecimal price = new BigDecimal(0);
// 				sku.setPrice(price);
//			}
// 			if (sku.getWeight() == null) {
// 				BigDecimal weight = new BigDecimal(0);
// 				sku.setWeight(weight);
//			}
// 			if (sku.getVolume() == null) {
// 				BigDecimal volume = new BigDecimal(0);
// 				sku.setVolume(volume);
//			}
			Integer skuGroupId = sku.getSkuGroupId();
			sku.setClientId(clientId);
			Integer skuId = sku.getSkuId();
			skuDao.updateSku(sku);
			/**通过skuId查询产品分组映射表，把该skuId所有记录设置为删除**/
			parames.put("skuId", skuId);
			skuGroupMappingDao.setIsDelete(parames);
			/**判断groupId是否空，不为空执行**/
			/**通过skuId+groupId 查询映射表是否有记录，如果有 update isdelete为0 反之 insert一条新的记录**/
			if(skuGroupId!=null){
				byte isDelete = Constants.IS_DELETE_FALSE;
				Map<String, Object> parames1 = new HashMap<String, Object>();
				parames1.put("skuGroupId", skuGroupId);
				parames1.put("skuId", skuId);
				SkuGroupMapping skuGroupMapping = skuGroupMappingDao.getSkuGroupMappingByParames(parames1);
				if(skuGroupMapping != null){
					skuGroupMapping.setIsDelete(isDelete);
					skuGroupMapping.setLastUpdateTime(null);
					skuGroupMappingDao.updateSkuGroup(skuGroupMapping);
				}else{
					SkuGroupMapping skuGroupMapping_1 = new SkuGroupMapping();
					skuGroupMapping_1.setSkuGroupId(skuGroupId);
					skuGroupMapping_1.setSkuId(skuId);
					skuGroupMapping_1.setClientId(sku.getClientId());
					skuGroupMappingDao.addSkuGroup(skuGroupMapping_1);
				}
				
			}
			
			
//			if( sku.getSkuGroupId()!= null){
//				skuGroupId = sku.getSkuGroupId();
//				parames.put("skuId", skuId);
//				List<SkuGroupMapping> ls = skuGroupMappingDao.getObjectList(parames);
//				if(ls!=null && ls.size()>0){
//					byte isDelete = 1;
//					for (SkuGroupMapping skuGroupMapping : ls) {
//						if(skuGroupMapping.getSkuGroupId()==sku.getSkuGroupId()){
//							skuGroupMapping.setIsDelete((byte)0);
//							skuGroupMappingDao.updateSkuGroupMapping(skuGroupMapping);
//						}else{
//							skuGroupMapping.setIsDelete(isDelete);
//							skuGroupMappingDao.updateSkuGroupMapping(skuGroupMapping);
//							SkuGroupMapping skuGroupMapping1 = new SkuGroupMapping();
//							skuGroupMapping1.setClientId(clientId);;
//							skuGroupMapping1.setSkuGroupId(skuGroupId);
//							skuGroupMapping1.setSkuId(skuId);
//							skuGroupMappingDao.addSkuGroup(skuGroupMapping1);
//						}
//						
//					}
//				}else{
//					SkuGroupMapping skuGroupMapping = new SkuGroupMapping();
//					skuGroupMapping.setClientId(clientId);;
//					skuGroupMapping.setSkuGroupId(skuGroupId);
//					skuGroupMapping.setSkuId(skuId);
//					skuGroupMappingDao.addSkuGroup(skuGroupMapping);
//				}
				
//			}else{
//				SkuGroupMapping skuGroupMapping = new SkuGroupMapping();
//				skuGroupMapping.setClientId(clientId);
//				skuGroupMapping.setIsDelete((byte) 1);
//				skuGroupMapping.setSkuId(skuId);
//				skuGroupMappingDao.updateSkuGroupMapping(skuGroupMapping);
//			}
		} catch (BusinessException e) {
			LOG.error("method updateSku error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		
	}

	@Override
	public void deleteSku(int clientId, Integer skuId)throws BusinessException {
			Sku sku = null;
		try {
			sku = this.getSku(skuId);
			byte isDelete = Constants.IS_DELETE_TRUE;
			sku.setSkuId(skuId);
			sku.setIsDelete(isDelete);
			skuDao.updateSku(sku);
			
			SkuGroupMapping skuGroupMapping = new SkuGroupMapping();
			skuGroupMapping.setIsDelete(isDelete);
			skuGroupMapping.setSkuId(skuId);
			skuGroupMapping.setClientId(sku.getClientId());
			skuGroupMappingDao.updateSkuGroupMapping(skuGroupMapping);
			
		} catch (BusinessException e) {
			LOG.error("method deleteSku error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
		
	}

	@Override
	public List<Sku> getSkuByBarcode(Map<String, Object> parameters)
			throws BusinessException {
		return skuDao.getSkuByBarcode(parameters);
	}

	@Override
	public List<Sku> getSkuByName(Map<String, Object> parames)
			throws BusinessException {
		return skuDao.getSkuByName(parames);
	}

	@Override
	public List<Sku> findSkuByClientId(Integer clientId)
			throws BusinessException {		
		List<Sku> list = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			params.put("_order", "DESC");
			list = skuDao.querySkuList(params);
		} catch (BusinessException e) {
			LOG.error("method findSkuByClientId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public String selectAllSkuIds(Map<String, Object> parameters)
			throws BusinessException {
		return skuDao.findAllSkuIds(parameters);
	}

	@Override
	public Sku getSkuByIdAndClientId(Integer clientId,Integer skuId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("clientId", clientId);
		param.put("skuId", skuId);
		return skuDao.getSkuByIdAndClientId(param);
	}

	@Override
	public List<SkuPriceVO> getSkuPriceJson(Integer clientId, Integer brandId,
			Integer categoryId, String skuName, String skuNo)
			throws BusinessException {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("clientId", clientId);
		param.put("brandId", brandId);
		param.put("categoryId", categoryId);
		param.put("skuName", skuName);
		param.put("skuNo", skuNo);
		return skuDao.findSkuIdAndPriceByParamter(param);
	}

	@Override
	public List<Sku> selectSkuToSelectTwo(Integer clientId)
			throws BusinessException {
		Map<String,Object> parmar = new HashMap<String,Object>();
		parmar.put("clientId", clientId);
		List<Sku> skuList = skuDao.selectBySelectTwo(parmar);
		return skuList;
	}

	@Override
	public Map<String,Sku> getSkuNoMap(Integer clientId) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		return skuDao.getSkuNoMap(params);
	}

	@Override
	public int insertlist(List<Sku> skulist) throws BusinessException {
		return skuDao.insertList(skulist);
	}

	@Override
	public ResultMessage importColgate(MultipartFile file,
			HttpServletRequest request, HttpServletResponse resp)
			throws BusinessException {
		ExcelReader reader = new ExcelReader(file);
		//读取Excel文件第一个sheet的数据
		List<String[]> values = reader.getAllData(0);
		//取第一行表头
		String[] titles = values.get(0);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", Constants.COLGATE_CLIENTID);
		Map<String,Sku> skuMap = skuDao.getSkuNoMap(params);
		
		List<String[]> errorData = new ArrayList<String[]>();
		List<String> errorMessage = new ArrayList<String>();
		errorData.add(titles);
		List<Sku> skuPricelist = new ArrayList<Sku>();
		int updateSize=0;
		//列名校验
		for (int i = 0; i < titles.length; i++) {
			if(StringUtils.isEmpty(titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("列名不能为空");
				return rm;
			}
			if(!MobiStringUtils.contains(ImportConstants.COLGATE_TITLES, titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("存在不能识别列：" +titles[i]);
				return rm;
			}
		}
		
		out:for (int i = 1; i < values.size(); i++) {
			Sku sku = new Sku();
			sku.setClientId(Constants.COLGATE_CLIENTID);
			String[] rowData = values.get(i);
			for (int j = 0; j < rowData.length; j++) {
				String cellValueSkuNo = rowData[0];
				String cellValueSkuPrice = rowData[1];
				if(skuMap.containsKey(cellValueSkuNo)){
					params.put("skuNo", cellValueSkuNo);
					BigDecimal nkvalue;
					try {
						nkvalue = new BigDecimal(cellValueSkuPrice);
						params.put("price", nkvalue);
					} catch (Exception e) {
						errorData.add(rowData);
						errorMessage.add("RSP未能识别");
						continue out;
					}
					skuDao.updatePrice(params);
					updateSize++; 
					continue out;
				}else{
					sku.setSkuNo(cellValueSkuNo);
					BigDecimal nkvalue;
					try {
						nkvalue = new BigDecimal(cellValueSkuPrice);
						sku.setPrice(nkvalue);
					} catch (Exception e) {
						errorData.add(rowData);
						errorMessage.add("RSP未能识别");
						continue out;
					}
				}
			}
			skuPricelist.add(sku);
		}
		/**插入SKU数据*/
		if (null != skuPricelist && !skuPricelist.isEmpty()) {
			skuDao.insertList(skuPricelist);
		}
		Map<String, Object> resultMessage = new HashMap<String, Object>();
		resultMessage.put("errorCount", errorData.size() - 1);
		resultMessage.put("successCount", skuPricelist.size()+updateSize);
		if (errorMessage != null && !errorMessage.isEmpty()) {
			this.importResult(request, resp, resultMessage,errorData,errorMessage);
		}
		ResultMessage rm = ResultMessage.IMPORT_SUCCESS_RESULT;
		rm.setAttributes(resultMessage);
		return rm;
	}

	@Override
	public ResultMessage importSKU(MultipartFile file,
			HttpServletRequest request, HttpServletResponse resp,ShiroUser shiroUser)
			throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", shiroUser.clientId);
		params.put("clientStructureId", shiroUser.clientUser.getClientStructureId());
		//params.put("clientPositionId", getShiroUser().clientUser.getClientPositionId());
		params.put("clientUserId", shiroUser.clientUser.getClientUserId());
		List<Brand> brands =brandDao.queryBrandList(params);
		List<Category> categories = categoryDao.queryCategoryList(params);
		List<Unit> units = unitDao.queryAll(params);
		
		List<Sku> skuList = new ArrayList<Sku>();
		ExcelReader reader = new ExcelReader(file);
		//读取Excel文件第一个sheet的数据
		List<String[]> values = reader.getAllData(0);
		
		//取第一行表头
		String[] titles = values.get(0);
		//列名校验
		for (int i = 0; i < titles.length; i++) {
			if(StringUtils.isEmpty(titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("列名不能为空");
				return rm;
			}
			if(!MobiStringUtils.contains(ImportConstants.SKU_TITLE, titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("存在不能识别列：" +titles[i]);
				return rm;
			}
		}
		
		//取values数据，从第二行开始
		//遍历每列数据
		for (int i = 1; i < values.size();i++) {
			Sku sku = new Sku();
			Method[] methods = sku.getClass().getMethods();
			String[] vv = values.get(i);
			//每列数据校验vv
			for (int j = 0; j < vv.length; j++) {
				String kvalue = vv[j];
				if(StringUtil.isEmptyString(kvalue)){
					ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
					rm.setMessage("内容不能为空");
					return rm;
				}
				String cvalue = null;
				for (int k = 0; k < ImportConstants.SKU_TITLE.length; k++) {
					if(ImportConstants.SKU_TITLE[k].equals(titles[j])){
						cvalue = ImportConstants.SKU_COLUMN[k];
						break;
					}
				}
				//关联数据进行校验
				if(ImportConstants.I_BRAND.equals(cvalue)){
					Brand b = null;
					for (Brand p : brands) {
						if(p.getBrandName().equals(kvalue)){
							b = p;
							break;
						}
					}
					if(b != null){
						sku.setBrandId(b.getBrandId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知品牌名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.I_CATEGORY.equals(cvalue)){
					Category cat = null;
					for (Category c : categories) {
						if(c.getCategoryName().equals(kvalue)){
							cat = c;
							break;
						}
					}
					if(cat != null){
						sku.setCategoryId(cat.getCategoryId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知品类名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.I_UNIT.equals(cvalue)){
					Unit uu = null;
					for (Unit d : units) {
						if(d.getSubUnitName().equals(kvalue)){
							uu = d;
							break;
						}
					}
					if(uu != null){
						sku.setUnitId(uu.getUnitId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知单位名称：" + kvalue);
						return rm;
					}
				}else{
					for (int k = 0; k < methods.length; k++) {
						if(methods[k].getName().equalsIgnoreCase("set" + cvalue)){
							Type[] ts = methods[k].getGenericParameterTypes(); 
							String xclass = ts[0].toString(); 
							if(xclass.equals("class java.math.BigDecimal")){
								BigDecimal nkvalue = new BigDecimal(kvalue);
								try {
									methods[k].invoke(sku, nkvalue);
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							} else
								try {
									methods[k].invoke(sku, kvalue);
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							break;
						}
					}
				}
				
			}
			
			sku.setClientId(shiroUser.clientId);
			skuList.add(sku);
		}
		try {
			if(this.saveAll(skuList))
				return ResultMessage.IMPORT_SUCCESS_RESULT;
			else
				return ResultMessage.IMPORT_FAIL_RESULT;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	private void importResult(HttpServletRequest req, HttpServletResponse resp,
			Map<String, Object> map,List<String[]> errDataList,List<String> strError) {
		XSSFWorkbook wb = new XSSFWorkbook();
		try {
			resp.addHeader("Content-Disposition", "attachment;filename="
					+ new String("导入结果查看".getBytes("gb2312"), "iso8859-1")
					+ ".xls");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		resp.setContentType("application/vnd.ms-excel");
		/** 在we中创建一个sheet */
		XSSFSheet wTSSheet = wb.createSheet("错误数据");
		/** 在wTSSheet值添加表头(第0行) */
		XSSFRow row = wTSSheet.createRow((int) 0);
		/** 创建单元格，并设置表头，设置表头居中 */
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
				row.createCell(j + 1).setCellValue(
						date[j] == null ? "" : date[j]);
			}
		}
		try {
			String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);
			File fileMkdir = new File(errDataExcelPath+Constants.COLGATE_CLIENTID);
			if (!fileMkdir.exists()) {
				fileMkdir.mkdirs();
			}
			String excelName = "errSKUExcel" + "_"
					+ System.currentTimeMillis() + ".xlsx";
			map.put("errDataExcelPath", excelName);
			File file = new File(fileMkdir.getPath() + File.separator
					+ excelName);
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
		} catch (IOException e) {
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
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style.setFont(font);
		return style;
	}
	
}
