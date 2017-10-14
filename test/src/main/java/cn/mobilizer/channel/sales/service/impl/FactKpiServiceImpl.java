package cn.mobilizer.channel.sales.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.promotion.po.SurveyStoreDisplay;
import cn.mobilizer.channel.sales.dao.FactKpiDao;
import cn.mobilizer.channel.sales.po.FactKpi;
import cn.mobilizer.channel.sales.service.FactKpiService;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;

@Service
public class FactKpiServiceImpl implements FactKpiService {
	
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private FactKpiDao factKpiDao;

	@Override
	public Map<String, Object> validata(MultipartFile file, Integer clientId,Integer clientUserId) {
		ExcelReader reader = new ExcelReader(file);
		Map<String,Object> map = new HashMap<String, Object>();
		/**将一个sheet里面数据分成多个批次导入：每次导入1000条数据*/
		Integer eachCount = 1000;
		Integer rowCount = reader.getRowNum(0)+1;
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		List<FactKpi> successList = new ArrayList<FactKpi>();
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
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
				endRowIndex = ((a+1)*eachCount);
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
					if(!MobiStringUtils.contains(ImportConstants.FACT_KPI_TITLE, titles[i])){
						map.put("UnknownColumnName","未知的列名："+titles[i]);
						map.put("successList", successList);
						map.put("errStrList", errStrList);
						map.put("errDataList", errDataList);
						return map;
					}
				}
			}
			List<FactKpi> subSheetFactKpis = new ArrayList<FactKpi>();
			for (int i = 1; i < values.size(); i++) {
				FactKpi factKpi = new FactKpi();
				String[] vv = values.get(i);
				boolean flag = true;
				for (int j = 0; j < vv.length; j++) {	
					if(flag){
						String kvalue = vv[j];//值
						String cvalue = null;//列名
						for (int k = 0; k < ImportConstants.FACT_KPI_TITLE.length; k++) {
							if(ImportConstants.FACT_KPI_TITLE[k].equals(ctitles[j])){
								cvalue = ImportConstants.FACT_KPI_COLUMN[k];
								break;
							}
						}
						if("storeName".equals(cvalue)){
							String kvalueStructure = vv[j+2];	//分部
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
									factKpi.setStoreId(store.getStoreId());
								}
							}
						}else if("salesAmount".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "销量目标不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								Boolean salesAmountFlag = kvalue.matches("-?[0-9]+.*[0-9]*");
								if(!salesAmountFlag){
									String errStr = "销量目标必须为数字";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("monthId".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "月份不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								Boolean monthIdFlag = kvalue.matches("[0-9]{6}");
								if(!monthIdFlag){
									String errStr = "月份格式不正确：如2015年5月格式为：201505";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}
						try {
							 PropertyDescriptor descriptor = new PropertyDescriptor(cvalue, factKpi.getClass());
							 Method setMethod = descriptor.getWriteMethod();
							 if(cvalue.equalsIgnoreCase("salesAmount")){
								 NumberFormat nf = NumberFormat.getInstance();
								 nf.setRoundingMode(RoundingMode.HALF_UP);		//设置四舍五入
								 nf.setMinimumFractionDigits(2);				//设置最小保留几位小数
								 nf.setMaximumFractionDigits(2);				//设置最大保留几位小数
								 String salesAmount = nf.format(new Double(kvalue)).replace(",", "");
								 setMethod.invoke(factKpi,new Double(salesAmount));
							 }else if(cvalue.equalsIgnoreCase("monthId")){
								 Integer monthId = new Integer(kvalue);
								 setMethod.invoke(factKpi,monthId);
							 }else{
								 setMethod.invoke(factKpi,kvalue);
							 }
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if(flag){
					//设置关联属性	
					factKpi.setClientId(clientId);
					factKpi.setClientUserId(clientUserId);
					factKpi.setKpiType((byte)1);
					subSheetFactKpis.add(factKpi);
					successList.add(factKpi);
				}
			}
			if(subSheetFactKpis!=null && subSheetFactKpis.size()>0){
				batchSaveFactKpis(subSheetFactKpis);
			}
		}
		map.put("successList", successList);
		map.put("errStrList", errStrList);
		map.put("errDataList", errDataList);
		return map;
	}

	@Override
	public void batchSaveFactKpis(List<FactKpi> factKpis) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
		factKpiDao.batchSaveFactkpi(factKpis);
		CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
	}

	@Override
	public List<FactKpi> selectByParams(Map<String, Object> params) {
		return factKpiDao.selectByParams(params);
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> params) {
		return factKpiDao.selectByParamCount(params);
	}
	
}
