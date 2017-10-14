package cn.mobilizer.channel.sales.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.report.vo.NumberFormatUtils;
import cn.mobilizer.channel.sales.dao.DimDayDao;
import cn.mobilizer.channel.sales.dao.DimWeekDao;
import cn.mobilizer.channel.sales.dao.FactMonthlyCbSalesDao;
import cn.mobilizer.channel.sales.dao.FactMonthlySales4FerreroDao;
import cn.mobilizer.channel.sales.dao.FactMonthlySalesDao;
import cn.mobilizer.channel.sales.po.DimDay;
import cn.mobilizer.channel.sales.po.DimWeek;
import cn.mobilizer.channel.sales.po.FactMonthlySales;
import cn.mobilizer.channel.sales.po.FactMonthlySales4Ferrero;
import cn.mobilizer.channel.sales.po.FactMonthlyCbSales;
import cn.mobilizer.channel.sales.service.FactMonthlySalesService;
import cn.mobilizer.channel.survey.vo.DefaultCollectionGroup;
import cn.mobilizer.channel.survey.vo.DefaultCollectionGroup.GroupBy;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;

@Service
public class FactMonthlySalesServiceImpl implements FactMonthlySalesService{

	@Autowired
	private FactMonthlySalesDao factMonthlySalesDao;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private StoreService storeServie;
	@Autowired
	private DimDayDao dimDayDao;
	@Autowired
	private FactMonthlySales4FerreroDao factMonthlySales4FerreroDao;
	@Autowired
	private DimWeekDao dimWeekDao;
	@Autowired
	private StoreService storeService;
	@Autowired
	private FactMonthlyCbSalesDao factMonthlyCbSalesDao;
	
	private static final Log LOG = LogFactory.getLog(FactMonthlySalesServiceImpl.class);

	@Override
	public List<FactMonthlySales> selectByParams(Map<String, Object> param)  throws BusinessException{
		List<FactMonthlySales> list = factMonthlySalesDao.selectByParams(param);		
		for(FactMonthlySales factMonthlySales : list){
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			if(factMonthlySales.getClientUserId() != null){
				ClientUser clientUser = clientUserService.findByPrimaryKey(factMonthlySales.getClientUserId());
				factMonthlySales.setClientUserName(clientUser.getName());
			}
			if(factMonthlySales.getClientStructureId() != null){
				ClientStructure clientStructure = clientStructureService.getClientStructureById(factMonthlySales.getClientStructureId());
				factMonthlySales.setClientStructureName(clientStructure.getStructureName());
			}
			if(factMonthlySales.getSkuId() != null){
				Sku sku = skuService.getSku(factMonthlySales.getSkuId());
				factMonthlySales.setSkuName(sku.getSkuName());
			}
			if(factMonthlySales.getLastUpdateBy() != null){
				ClientUser clientUser = clientUserService.findByPrimaryKey(factMonthlySales.getLastUpdateBy());
				factMonthlySales.setLastUpdateUserName(clientUser.getName());
			}
			if(factMonthlySales.getStoreId() != null){
				Store store = storeServie.selectByPrimaryKey(factMonthlySales.getStoreId());
				factMonthlySales.setStoreName(store.getStoreName());
			}
		}
		CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
		return list;
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param)  throws BusinessException{
		return factMonthlySalesDao.selectByParamCount(param);
	}

	@Override
	public FactMonthlySales getFactMonthlySales(Integer dataId)  throws BusinessException{
		return factMonthlySalesDao.selectByPrimaryKey(dataId);
	}

	@Override
	public String insert(FactMonthlySales factMonthlySales)  throws BusinessException{
		return factMonthlySalesDao.insert(factMonthlySales);
	}

	@Override
	public void saveFactMonthlySales(List<FactMonthlySales> FactMonthlySalesList)  throws BusinessException{					
		 for(FactMonthlySales factMonthlySales : FactMonthlySalesList){			
			 Map<String,Object> param = new HashMap<String, Object>();
			 param.put("monthId", factMonthlySales.getMonthId());
			 param.put("storeId", factMonthlySales.getStoreId());
			 param.put("clientStructureId", factMonthlySales.getClientStructureId());
			 param.put("clientId", factMonthlySales.getClientId()); 
			 try{
				 if(factMonthlySales.getSkuId() == null){				 //skuId为空的全品类，只能有一条数据
					List<FactMonthlySales> factMonthlySalesInfos = factMonthlySalesDao.getSkuIdNullEntityByParam(param); 
					if(factMonthlySalesInfos!=null && factMonthlySalesInfos.size() == 1){
						FactMonthlySales updateInfo = factMonthlySalesInfos.get(0);
						updateInfo.setSalesAmount(factMonthlySales.getSalesAmount());
						updateInfo.setSalesVolume(factMonthlySales.getSalesVolume());
						updateInfo.setLastUpdateBy(factMonthlySales.getClientUserId());
						factMonthlySalesDao.updateByPrimaryKeySelective(updateInfo);
					}else if(factMonthlySalesInfos == null || factMonthlySalesInfos.size() == 0){
						factMonthlySalesDao.insertSelective(factMonthlySales);
					}
				 }else{
					 param.put("skuId",factMonthlySales.getSkuId());
					 List<FactMonthlySales> factMonthlySalesInfos = factMonthlySalesDao.selectByParams(param); 
					 if(factMonthlySalesInfos!=null && factMonthlySalesInfos.size() == 1){
						FactMonthlySales updateInfo = factMonthlySalesInfos.get(0);
						updateInfo.setSalesAmount(factMonthlySales.getSalesAmount());
						updateInfo.setSalesVolume(factMonthlySales.getSalesVolume());
						updateInfo.setLastUpdateBy(factMonthlySales.getClientUserId());
						factMonthlySalesDao.updateByPrimaryKeySelective(updateInfo);
					 }else if(factMonthlySalesInfos == null || factMonthlySalesInfos.size() == 0){	
						 factMonthlySalesDao.insertSelective(factMonthlySales);
					 }
				 }
			 }catch(Exception ex){
				 LOG.error(ex);
				 continue;
			 }			 
		 }		
	}

	@Override
	public List<?> getEntityListByProcedure(Map<String, Object> param)  throws BusinessException{		
		return factMonthlySalesDao.getEntityListByProcedure(param);
	}

	@Override
	public FactMonthlySales getEntityByDataId(Map<String, Object> param)
			throws BusinessException {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
		FactMonthlySales factMonthlySales = factMonthlySalesDao.getEntityByDataId(param);
		CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		if(factMonthlySales.getClientStructureId() != null){
			ClientStructure clientStructure = clientStructureService.getClientStructureById(factMonthlySales.getClientStructureId());
			factMonthlySales.setClientStructureName(clientStructure.getStructureName());
		}
		if(factMonthlySales.getSkuId() != null){
			Sku sku = skuService.getSku(factMonthlySales.getSkuId());
			factMonthlySales.setSkuName(sku.getSkuName());
		}
		if(factMonthlySales.getStoreId() != null){
			Store store = storeServie.selectByPrimaryKey(factMonthlySales.getStoreId());
			factMonthlySales.setStoreName(store.getStoreName());
		}
		return factMonthlySales;
	}

	@Override
	public void updateFactMonthlySales(FactMonthlySales factMonthlySales) throws BusinessException {	
		factMonthlySalesDao.update(factMonthlySales);
	}

	@Override
	public void addFactMonthlySales(FactMonthlySales factMonthlySales) throws BusinessException {
		 Map<String,Object> param = new HashMap<String, Object>();
		 param.put("monthId", factMonthlySales.getMonthId());
		 param.put("storeId", factMonthlySales.getStoreId());
		 param.put("clientStructureId", factMonthlySales.getClientStructureId());
		 param.put("clientId", factMonthlySales.getClientId()); 
		 if(factMonthlySales.getSkuId() == null){				
				List<FactMonthlySales> factMonthlySalesInfos = factMonthlySalesDao.getSkuIdNullEntityByParam(param); 
				if(factMonthlySalesInfos!=null && factMonthlySalesInfos.size() == 1){
					FactMonthlySales updateInfo = factMonthlySalesInfos.get(0);
					updateInfo.setSalesAmount(factMonthlySales.getSalesAmount());
					updateInfo.setSalesVolume(factMonthlySales.getSalesVolume());
					updateInfo.setLastUpdateBy(factMonthlySales.getClientUserId());
					factMonthlySalesDao.update(updateInfo);
				}else if(factMonthlySalesInfos == null || factMonthlySalesInfos.size() == 0){
					factMonthlySalesDao.insert(factMonthlySales);
				}
			 }else{
				 param.put("skuId",factMonthlySales.getSkuId());
				 List<FactMonthlySales> factMonthlySalesInfos = factMonthlySalesDao.selectByParams(param); 
				 if(factMonthlySalesInfos!=null && factMonthlySalesInfos.size() == 1){
					FactMonthlySales updateInfo = factMonthlySalesInfos.get(0);
					updateInfo.setSalesAmount(factMonthlySales.getSalesAmount());
					updateInfo.setSalesVolume(factMonthlySales.getSalesVolume());
					updateInfo.setLastUpdateBy(factMonthlySales.getClientUserId());
					factMonthlySalesDao.update(updateInfo);
				 }else if(factMonthlySalesInfos == null || factMonthlySalesInfos.size() == 0){	
					 factMonthlySalesDao.insert(factMonthlySales);
				 }
			 }
	}

	@Override
	public Object addFactMonthlySale(MultipartFile fileField,Integer clientId,Map<String, Store> mapStore,Map<String, Sku> mapSku,HttpServletResponse response) throws BusinessException {
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		Map<String,Object> returnMap = new HashMap<String,Object>();
		ExcelReader Reader = new ExcelReader(fileField);
		List<String[]> values = Reader.getAllData(0);
		if(null==values || values.isEmpty() || values.size()<=1){
			returnMap.put("errorMsg", "导入数据不能为空");
			return returnMap;
		}else{
			String[] titles = values.get(0); //获取导入数据表头
			errDataList.add(titles);
			for (String title : titles) {
				if(StringUtils.isEmpty(title)){
					returnMap.put("errorMsg","列名不能为空：" +title);
					return returnMap;
				}
				if(!MobiStringUtils.contains(ImportConstants.FACT_MONTHLY_SALES_TITLE,title)){
					returnMap.put("errorMsg","存在不能识别的列名：" +title);
					return returnMap;
				}
			}
			
			String[] firstVal = values.get(1); //第一列的数据
			String firstRe = firstVal[0].replace("/", "-");
			if(StringUtil.isEmptyString(firstRe)){
				errStrList.add("开始日期不能为空");
				errDataList.add(firstVal);
			}
			Date sDate 	= DateTimeUtils.StringToDate(firstRe, DateTimeUtils.yyyyMMdd); //开始日期
			if(!DateTimeUtils.getWeek(sDate).equals("周一")){
				errStrList.add("开始日期必须为周一");
				errDataList.add(firstVal);
			}
			//根据dayId获取
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("yearId",DateTimeUtils.formatTime(sDate, DateTimeUtils.yyyy));
			
			Map<String, DimDay> mapDimDay = dimDayDao.getDimDayByYearId(params);
			
			Integer dayId = Integer.parseInt(DateTimeUtils.formatTime(sDate, DateTimeUtils.yyyyMMdd2));
			DimDay dimDay = mapDimDay.get(dayId);
			if(null==dimDay){
				errStrList.add("日期异常");
				errDataList.add(firstVal);
			}
			params.put("dayId", dayId);
			Map<String, FactMonthlySales4Ferrero> mapStoreAndSku = factMonthlySales4FerreroDao.getSalesByDayId(params);
			
			List<FactMonthlySales4Ferrero> insertSalesList = new ArrayList<FactMonthlySales4Ferrero>(); //需要insert的数据
			List<FactMonthlySales4Ferrero> updateSalesList = new ArrayList<FactMonthlySales4Ferrero>(); //已经存在需要更新的数据
			Date currDate = DateTimeUtils.getCurrentTime();
			//数据校验
			for (int i = 1; i < values.size(); i++) {
				String[] colVal = values.get(i);
				String reStartDate = colVal[0].replace("/", "-");
				String reEndDate = colVal[1].replace("/", "-");
				if(StringUtils.isEmpty(reStartDate)){
					errStrList.add("开始日期不能为空");
					errDataList.add(colVal);
					continue;
				}else if(StringUtils.isEmpty(reEndDate)){
					errStrList.add("结束日期不能为空");
					errDataList.add(colVal);
					continue;
				}
				Date startDate 	= DateTimeUtils.StringToDate(reStartDate, DateTimeUtils.yyyyMMdd); //开始日期
				Date endDate	= DateTimeUtils.StringToDate(reEndDate, DateTimeUtils.yyyyMMdd); //结束日期
				if(!DateTimeUtils.getWeek(startDate).equals("周一")){
					errStrList.add("开始日期必须为周一");
					errDataList.add(colVal);
					continue;
				}else if(!DateTimeUtils.getWeek(endDate).equals("周日")){
					errStrList.add("结束日期必须为周日");
					errDataList.add(colVal);
					continue;
				}
				//价格不能等于0
				if(StringUtil.isEmptyString(colVal[4])){
					errStrList.add("价格不能为空");
					errDataList.add(colVal);
					continue;
				}
				try {
					double price = Double.parseDouble(colVal[4]);
					if(price==0){
						errStrList.add("价格不能等于0");
						errDataList.add(colVal);
						continue;
					}
				} catch (Exception e) {
					errStrList.add(colVal[4]+"-不能识别价格");
					errDataList.add(colVal);
					continue;
				}
				//数量
				if(StringUtil.isEmptyString(colVal[5])){
					errStrList.add("数量不能为空");
					errDataList.add(colVal);
					continue;
				}else if(!StringUtil.isNumber(colVal[5])){
					errStrList.add("数量只能为数值类型");
					errDataList.add(colVal);
					continue;
				}
				if(StringUtil.isEmptyString(colVal[2])){
					errStrList.add("门店编号不能为空");
					errDataList.add(colVal);
					continue;
				}else if(!mapStore.containsKey(colVal[2])){
					errStrList.add("门店编号不存在");
					errDataList.add(colVal);
					continue;
				}
				if(StringUtil.isEmptyString(colVal[3])){
					errStrList.add("产品不能为空");
					errDataList.add(colVal);
					continue;
				}else if(!mapSku.containsKey(colVal[3])){
					errStrList.add("产品不存在");
					errDataList.add(colVal);
					continue;
				}
				
				FactMonthlySales4Ferrero fmsf = new FactMonthlySales4Ferrero();
				fmsf.setDayId(dayId);
				if(dimDay == null){
					errStrList.add("日期异常");
					errDataList.add(firstVal);
					continue;
				}else{
					fmsf.setMonthId(dimDay.getMonthId());
					fmsf.setWeekId(dimDay.getWeekId());
				}
				
				Store store = mapStore.get(colVal[2]);
				fmsf.setStoreId(store.getStoreId()); //根据门店编号获取门店ID
				fmsf.setClientStructureId(store.getClientStructureId());
				
				Sku sku = mapSku.get(colVal[3]);
				fmsf.setSkuId(sku.getSkuId());
				fmsf.setCategoryId(sku.getCategoryId());
				fmsf.setBrandId(sku.getBrandId());
				
				fmsf.setSalesVolume(new BigDecimal(colVal[5]));
				fmsf.setPrice(new BigDecimal(colVal[4]));
				
				BigDecimal amount = fmsf.getSalesVolume().multiply(fmsf.getPrice());
				fmsf.setSalesAmount(amount);
				fmsf.setLastUpdateBy(clientId);
				fmsf.setLastUpdateTime(currDate);
				fmsf.setClientId(clientId);
				fmsf.setIsDelete(Constants.IS_DELETE_FALSE);
				
				//判断该数据在这周期内是否存在，如果存在update，不存在insert
				StringBuffer bf = new StringBuffer(store.getStoreId()).append("@").append(sku.getSkuId());
				if(mapStoreAndSku.containsKey(bf.toString())){
					fmsf.setDataId(mapStoreAndSku.get(bf.toString()).getDataId());
					updateSalesList.add(fmsf);
				}else{
					insertSalesList.add(fmsf);
					mapStoreAndSku.put(bf.toString(),fmsf);
				}
			}
			
			if(!insertSalesList.isEmpty()){
				factMonthlySales4FerreroDao.batchAddSales(insertSalesList);
				//需要更新work表 dim_week
				DimWeek dimWeek = new DimWeek();
				dimWeek.setClientId(clientId);
				dimWeek.setWeekId(dimDay.getWeekId());
				dimWeek.setHasData(Constants.DATA_YES);
				dimWeekDao.updateByPrimaryKeySelective(dimWeek);
			}
			
			if(!updateSalesList.isEmpty()){
				factMonthlySales4FerreroDao.batchUpdateSales(updateSalesList);
			}
			returnMap.put("errorCount", errDataList.size() - 1);
			returnMap.put("successCount", insertSalesList.size());
			
			if (errStrList != null && !errStrList.isEmpty()) {
				String excelName = "errColgateFerreroExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
				Reader.importResult(response,errDataList,errStrList,clientId,excelName);
				returnMap.put("errDataExcelPath", excelName);
			}else{
				returnMap.put("errDataExcelPath", "");
			}
			return returnMap;
		}
	}

	@Override
	public Map<String, Object> importDataValidata(MultipartFile file,Integer clientId, Integer clientUserId) throws BusinessException {
		ExcelReader reader = new ExcelReader(file);
		Map<String,Object> map = new HashMap<String, Object>();
		/**将一个sheet里面数据分成多个批次导入：每次导入1000条数据*/
		Integer eachCount = 1000;
		Integer rowCount = reader.getRowNum(0)+1;
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		List<FactMonthlySales> successList = new ArrayList<FactMonthlySales>();
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		Map<String,Integer> cityMap = new HashMap<String, Integer>();
		String[] ctitles = null;
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
				endRowIndex = (a+1)*eachCount;
			}
			List<String[]> values = reader.getSubSheetData(0,beginRowIndex,endRowIndex);
			if(beginRowIndex==0){
				String[] titles = values.get(0);
				ctitles = values.get(0);
				//列名校验
				for (int i = 0; i < titles.length; i++) {
					if(StringUtils.isEmpty(titles[i])){
						map.put("UnknownColumnName","列名不能为空");
						map.put("successList", successList);
						map.put("errStrList", errStrList);
						map.put("errDataList", errDataList);
						return map;
					}
					if(!MobiStringUtils.contains(ImportConstants.FACT_MONTHLYSALES_TITLE, titles[i])){
						map.put("UnknownColumnName","未知的列名："+titles[i]);
						map.put("successList", successList);
						map.put("errStrList", errStrList);
						map.put("errDataList", errDataList);
						return map;
					}
				}
			}
			List<FactMonthlySales> factMonthlySaless = new ArrayList<FactMonthlySales>();
			for (int i = 1; i < values.size(); i++) {
				FactMonthlySales factMonthlySalesInfo = new FactMonthlySales();
				String[] vv = values.get(i);
				boolean flag = true;
				for (int j = 0; j < vv.length; j++) {	
					if(flag){
						String kvalue = vv[j];//值
						String cvalue = null;//列名
						for (int k = 0; k < ImportConstants.FACT_MONTHLYSALES_TITLE.length; k++) {
							if(ImportConstants.FACT_MONTHLYSALES_TITLE[k].equals(ctitles[j])){
								cvalue = ImportConstants.FACT_MONTHLYSALES_COLUMN[k];
								break;
							}
						}
						
						if("storeName".equals(cvalue)){
							String kvalueStructure = vv[j+2];//值
							Map<String,Object> params = new HashMap<String, Object>();
							params.put("clientId", clientId);
							params.put("name", kvalueStructure);
							ClientStructure clientStructure = clientStructureService.getRepeatClientStructureName(params);
							if(clientStructure == null){	
								String errStr = "未知的分部";
								errStrList.add(errStr);	
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
							if(clientStructure!=null){
								if(StringUtils.isEmpty(kvalue)){
									String errStr = "门店名称不能为空";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
								List<Store> stores = storeService.getStoreByFullName(clientId, kvalue,clientStructure.getClientStructureId());    //按照门店全名称匹配 
								if(stores == null || stores.size() == 0){
									String errStr = "未知的门店";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else if(stores.size()>1){
									String errStr = "存在有多个一样的门店";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else{
									Store store = stores.get(0);
									factMonthlySalesInfo.setStoreId(store.getStoreId());
								}
							}
						}else if("barcode".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								Map<String,Object> param = new HashMap<String,Object>();
								param.put("barcode", kvalue);
								List<Sku> skus = skuService.getSkuByBarcode(param);
								if(skus != null && skus.size()>1){
									String errStr = "存在有多个一样的产品条码";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else if(skus != null && skus.size() == 1){
									Sku sku = skus.get(0);
									factMonthlySalesInfo.setSkuId(sku.getSkuId());
								}
							}					
						}else if("clientStructureName".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "分部不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
							ClientStructure  clientStructure = clientStructureService.getClientStructureByName(clientId,kvalue);
							if(clientStructure == null){
								String errStr = "未知的分部";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								factMonthlySalesInfo.setClientStructureId(clientStructure.getClientStructureId());
							}
						}else if("monthId".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "销售月份不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}
						try {
							 PropertyDescriptor descriptor = new PropertyDescriptor(cvalue, factMonthlySalesInfo.getClass());
							 Method setMethod = descriptor.getWriteMethod();
							 setMethod.invoke(factMonthlySalesInfo,kvalue);
						} catch (Exception e) {
							e.printStackTrace();
							LOG.info(cvalue);
						}
					}
				}
				if(flag){
					//设置关联属性	
					
					if(StringUtils.isEmpty(factMonthlySalesInfo.getSalesAmount())){
						factMonthlySalesInfo.setSalesAmount("0");
					}else{
						Double salesAmount = new Double(0);
						Boolean salesAmountFlag = factMonthlySalesInfo.getSalesAmount().matches("-?[0-9]+.*[0-9]*");
						if(salesAmountFlag){
							try{
								salesAmount = new Double(factMonthlySalesInfo.getSalesAmount());
							}catch(Exception ex){	
								ex.printStackTrace();
							}
						}
						NumberFormat nf = NumberFormat.getInstance();
				        nf.setRoundingMode(RoundingMode.HALF_UP);		//设置四舍五入
				        nf.setMinimumFractionDigits(2);				//设置最小保留几位小数
				        nf.setMaximumFractionDigits(2);				//设置最大保留几位小数
						factMonthlySalesInfo.setSalesAmount(nf.format(salesAmount).replace(",", ""));
					}				
					factMonthlySalesInfo.setClientUserId(clientUserId);
					try{
						factMonthlySalesInfo.setOrderType(Constants.SALES_ORDER_TYPE_IMPORT);
					}catch(Exception ex){
						ex.printStackTrace();
					}
					factMonthlySalesInfo.setLastUpdateBy(clientUserId);	
					factMonthlySalesInfo.setClientId(clientId);									
					factMonthlySaless.add(factMonthlySalesInfo);
					successList.add(factMonthlySalesInfo);
				}
			}
			if(factMonthlySaless!=null && factMonthlySaless.size()>0){			
				try{
					CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);	
					saveFactMonthlySales(factMonthlySaless);
				}catch(Exception ex){
					ex.printStackTrace();
				}finally{
					CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE); 
				}		  
			}
		}
		map.put("successList", successList);
		map.put("errStrList", errStrList);
		map.put("errDataList", errDataList);
		return map;
	}

	@Override
	public Map<String,Object> addFactMonthlyUnicharmSales(List<String[]> values,
			Map<String, Store> mapStore, Map<String, ClientStructure> mapDept,
			Map<String, ClientUser> mapClientUser,
			Map<String, StoreUserMapping> mapStoreUser,
			Map<String, Category> mapCategory,Integer clientId) throws BusinessException {
		
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		if(null==values || values.isEmpty() || values.size()<=1){
			returnMap.put("errorMsg","导入数据不能为空");
			return returnMap;
		}
		
		String[] titles = values.get(0); //获取导入数据表头
		for (String title : titles) {
			if(StringUtils.isEmpty(title)){
				returnMap.put("errorMsg","列名不能为空");
				return returnMap;
			}else if(!MobiStringUtils.contains(ImportConstants.FACT_MONTHLY_SALES_UNICHARM_TITLE,title)){
				returnMap.put("errorMsg","存在不能识别的列名：" +title);
				return returnMap;
			}
		}
		
		errDataList.add(titles);
		
		List<FactMonthlyCbSales> insertList = new ArrayList<FactMonthlyCbSales>();
		List<FactMonthlyCbSales> updateList = new ArrayList<FactMonthlyCbSales>();
		
		String[] val = values.get(1);
		String tempMonth = val[11];
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("monthId",tempMonth); //取第一列的月份默认为当次导入的月份
		List<FactMonthlyCbSales> salesList = factMonthlyCbSalesDao.getSalesByMonth(params);
		Map<String, FactMonthlyCbSales> salesMaps = new HashMap<String, FactMonthlyCbSales>();
		for (FactMonthlyCbSales factMonthlyCbSales : salesList) {
			String tempKey = factMonthlyCbSales.getStoreId()+"_"+factMonthlyCbSales.getMonthId()+"_"+factMonthlyCbSales.getCategoryId();
			salesMaps.put(tempKey, factMonthlyCbSales);
		}
		
		//数据校验
		for (int i = 1; i < values.size(); i++) {
			String[] vals = values.get(i);
			String userName = vals[1];
			String storeNo = vals[2];
			String dept = vals[3];
			String category = vals[9];
			String sales = vals[10];
			String month = vals[11];
			if(StringUtil.isEmptyString(userName)){
				errStrList.add("PIC工号不能为空");
				errDataList.add(vals);
				continue;
			}else if(!mapClientUser.containsKey(userName)){
				errStrList.add("PIC工号不存在");
				errDataList.add(vals);
				continue;
			}
			if(StringUtil.isEmptyString(storeNo)){
				errStrList.add("门店ID不能为空");
				errDataList.add(vals);
				continue;
			}else if(!mapStore.containsKey(storeNo)){
				errStrList.add("门店ID不存在");
				errDataList.add(vals);
				continue;
			}
			if(StringUtil.isEmptyString(dept)){
				errStrList.add("大区不能为空");
				errDataList.add(vals);
				continue;
			}else if(!mapDept.containsKey(dept)){
				errStrList.add("大区不存在");
				errDataList.add(vals);
				continue;
			}
			if(StringUtil.isEmptyString(category)){
				errStrList.add("品类不能为空");
				errDataList.add(vals);
				continue;
			}else if(!mapCategory.containsKey(category)){
				errStrList.add("品类不存在");
				errDataList.add(vals);
				continue;
			}
			if(StringUtil.isEmptyString(sales)){
				errStrList.add("销量不能为空");
				errDataList.add(vals);
				continue;
			}else if(!StringUtil.isNumberAmount(sales)){
				errStrList.add("销量格式错误");
				errDataList.add(vals);
				continue;
			}
			if(StringUtil.isEmptyString(month)){
				errStrList.add("月份不能为空");
				errDataList.add(vals);
				continue;
			}else if(!StringUtil.isNumber(month) || month.length()!=6){
				errStrList.add("月份格式错误");
				errDataList.add(vals);
				continue;
			}else if(!month.equals(tempMonth)){
				errStrList.add("导入数据不能跨月，系统默认取第一条数据的月份");
				errDataList.add(vals);
				continue;
			}
			
			//门店大区关系
			Store store = mapStore.get(storeNo);
			ClientStructure clientStructure = mapDept.get(dept);
			if(!store.getClientStructureId().equals(clientStructure.getClientStructureId())){
				errStrList.add("门店大区关系不存在");
				errDataList.add(vals);
				continue;
			}
			
			//门店人员关系是否存在
			StoreUserMapping storeUserMapping = mapStoreUser.get(store.getStoreId());
			ClientUser clientUser = mapClientUser.get(userName);
			if(!storeUserMapping.getClientUserId().equals(clientUser.getClientUserId())){
				errStrList.add("门店人员关系不存在");
				errDataList.add(vals);
				continue;
			}
			
			Category _category = mapCategory.get(category);
			FactMonthlyCbSales fmc = new FactMonthlyCbSales();
			String key = store.getStoreId()+"_"+month+"_"+_category.getCategoryId();
			if(salesMaps.containsKey(key)){
				fmc = (FactMonthlyCbSales) salesMaps.get(key);
			}
			fmc.setMonthId(Integer.parseInt(month));
			fmc.setClientId(clientId);
			fmc.setClientUserId(clientUser.getClientUserId());
			fmc.setStoreId(store.getStoreId());
			fmc.setClientStructureId(clientStructure.getClientStructureId());
			fmc.setCategoryId(_category.getCategoryId());
			fmc.setOrderType((byte)4);
			BigDecimal decimal = new BigDecimal(sales);
			fmc.setSalesAmount(decimal);
			if(salesMaps.containsKey(key)){
				updateList.add(fmc);
			}else{
				insertList.add(fmc);
				salesMaps.put(key, fmc);
			}
		}
		
		if(null!=insertList && !insertList.isEmpty()){
			factMonthlyCbSalesDao.batchInsertSales(insertList);
		}
		if(null!=updateList && !updateList.isEmpty()){
			factMonthlyCbSalesDao.updateSales(updateList);
		}
		Integer successCount = insertList.size()+updateList.size();
		returnMap.put("successCount",successCount);
		returnMap.put("errorCount", errStrList.size());
		
		returnMap.put("errStrList", errStrList);
		returnMap.put("errDataList", errDataList);
		
		return returnMap;
	}
}
