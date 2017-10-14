package cn.mobilizer.channel.base.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.ChainService;
import cn.mobilizer.channel.base.service.ChannelService;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.ImportStoreService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.ProvinceService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;

@Service
public class ImportStoreServiceImpl implements ImportStoreService {
	
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ChainService chainService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private CityService cityService;
	@Autowired
	private ProvinceService provinceService;
	
	@Override
	public Map<String,Object> validataColgateStore(MultipartFile file, Integer clientId) {
		ExcelReader reader = new ExcelReader(file);
		Map<String,Object> map = new HashMap<String, Object>();
		/**将一个sheet里面数据分成多个批次导入：每次导入1000条数据*/
		Integer eachCount = 1000;
		Integer rowCount = reader.getRowNum(0)+1;
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		List<Store> successList = new ArrayList<Store>();
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		
		String[] ctitles = null;
		Map<String,ClientUser> loginNameMap = clientUserService.getCMByLoginName(clientId);
		
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
					if(!MobiStringUtils.contains(ImportConstants.STORE_COLGATE_TITLE, titles[i])){
						map.put("UnknownColumnName","未知的列名："+titles[i]);
						map.put("successList", successList);
						map.put("errStrList", errStrList);
						map.put("errDataList", errDataList);
						return map;
					}
				}
			}
			List<Store> stores = new ArrayList<Store>();
			List<Options> optionsList = optionsService.findOptionsByOptionName("store_type", clientId);
			Map<String,Integer> optionsMap = new HashMap<String, Integer>();
			for(Options options : optionsList){
				optionsMap.put(options.getOptionText(), options.getOptionValue());
			}
			for (int i = 1; i < values.size(); i++) {
				Store store = new Store();
				Set<String> list = new HashSet<String>(); 
				String[] vv = values.get(i);
				boolean flag = true;
				for (int j = 0; j < vv.length; j++) {	
					if(flag){
						String kvalue = vv[j];//值
						String cvalue = null;//列名
						for (int k = 0; k < ImportConstants.STORE_COLGATE_TITLE.length; k++) {
							if(ImportConstants.STORE_COLGATE_TITLE[k].equals(ctitles[j])){
								cvalue = ImportConstants.STORE_COLGATE_COLUMN[k];
								break;
							}
						}
						try {
							 PropertyDescriptor descriptor = new PropertyDescriptor(cvalue, store.getClass());
							 Method setMethod = descriptor.getWriteMethod();
							 setMethod.invoke(store,kvalue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if("storeNo".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "门店编号不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							} 
						}else if("structureName".equals(cvalue)){
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
								store.setClientStructureId(clientStructure.getClientStructureId());
							}
						}else if("channelName".equals(cvalue)){		//channelName 验证
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "渠道不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
							Map<String,Object> params = new HashMap<String, Object>();
							params.put("clientId", clientId);
							params.put("channelName",kvalue);
							List<Channel>  channels = channelService.findChannelByChannelName(params);
							if(channels != null && channels.size()>1){
								String errStr = "存在有多个一样的渠道";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else if(channels != null && channels.size() == 1){
								Channel channel = channels.get(0);
								store.setChannelId(channel.getChannelId());
							}else{
								String errStr = "未知的渠道";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("chainName".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "连锁不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
							Map<String,Object> params = new HashMap<String, Object>();
							params.put("clientId", clientId);
							params.put("chainName",kvalue);
							List<Chain>  chains = chainService.findChainByChainName(params);
							if(chains != null && chains.size()>1){
								String errStr = "存在有多个一样的连锁";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else if(chains != null && chains.size() == 1){
								Chain chain = chains.get(0);
								store.setChainId(chain.getChainId());
							}else{
								String errStr = "未知的连锁";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("storeTypeName".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								if(optionsMap.containsKey(kvalue)){
									store.setStoreType(optionsMap.get(kvalue));
								}else{
									String errStr = "未知的Store_Hierarchy";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}else{
								String errStr = "Store_Hierarchy不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("loginName_One".equals(cvalue)){
							if(!StringUtils.isEmpty(kvalue)){
								if(loginNameMap.containsKey(kvalue)  && loginNameMap.get(kvalue).getPositionName().equals("CM")){
									list.add(loginNameMap.get(kvalue).getClientUserId()+"&CM@"+vv[0]);
								}else{
									String errStr = "未知的CM";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("loginName_Two".equals(cvalue)){
							if(!StringUtils.isEmpty(kvalue)){
								if(loginNameMap.containsKey(kvalue) && loginNameMap.get(kvalue).getPositionName().equals("BM")){
									list.add(loginNameMap.get(kvalue).getClientUserId()+"&BM@"+vv[0]);
								}else{
									String errStr = "未知的BM";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("loginName_Three".equals(cvalue)){
							if(!StringUtils.isEmpty(kvalue)){
								if(loginNameMap.containsKey(kvalue) && loginNameMap.get(kvalue).getPositionName().equals("RD")){
									list.add(loginNameMap.get(kvalue).getClientUserId()+"&RD@"+vv[0]);
								}else{
									String errStr = "未知的RD";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}
						store.setList(new ArrayList<String>(list));
					}
				}
				if(flag){
					//设置关联属性	
					store.setClientId(clientId);
					stores.add(store);
					successList.add(store);
				}
			}
			if(stores!=null && stores.size()>0){			
				try{
					//入库
					storeService.batchSaveStores(stores,null);
				}catch(Exception ex){
					ex.printStackTrace();
				} 			  
			}
		}
		map.put("successList", successList);
		map.put("errStrList", errStrList);
		map.put("errDataList", errDataList);
		return map;
	}

	@Override
	public Map<String, Object> validataAppleStore(MultipartFile file,Integer clientId) {
		ExcelReader reader = new ExcelReader(file);
		Map<String,Object> map = new HashMap<String, Object>();
		/**将一个sheet里面数据分成多个批次导入：每次导入1000条数据*/
		Integer eachCount = 1000;
		Integer rowCount = reader.getRowNum(0)+1;
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		List<Store> successList = new ArrayList<Store>();
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		List<City> citys = cityService.getAll();
		Map<String,Integer> cityMap = new HashMap<String, Integer>();
		for (City c : citys) {
			cityMap.put(c.getCity(), c.getCityId());
		}
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
					if(!MobiStringUtils.contains(ImportConstants.STORE_APPLECARE_TITLE, titles[i])){
						map.put("UnknownColumnName","未知的列名："+titles[i]);
						map.put("successList", successList);
						map.put("errStrList", errStrList);
						map.put("errDataList", errDataList);
						return map;
					}
				}
			}
			List<Store> stores = new ArrayList<Store>();
			for (int i = 1; i < values.size(); i++) {
				Store store = new Store();
				String[] vv = values.get(i);
				boolean flag = true;
				for (int j = 0; j < vv.length; j++) {	
					if(flag){
						String kvalue = vv[j];//值
						String cvalue = null;//列名
						for (int k = 0; k < ImportConstants.STORE_APPLECARE_TITLE.length; k++) {
							if(ImportConstants.STORE_APPLECARE_TITLE[k].equals(ctitles[j])){
								cvalue = ImportConstants.STORE_APPLECARE_COLUMN[k];
								break;
							}
						}
						try {
							 PropertyDescriptor descriptor = new PropertyDescriptor(cvalue, store.getClass());
							 Method setMethod = descriptor.getWriteMethod();
							 setMethod.invoke(store,kvalue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if("storeType".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "轮次不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("storeNo".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "门店编号不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("cityName".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								store.setCityId(cityMap.get(kvalue));
							}
						}
						try {
							 PropertyDescriptor descriptor = new PropertyDescriptor(cvalue, store.getClass());
							 Method setMethod = descriptor.getWriteMethod();
							 if("storeType".equals(cvalue)){
								 setMethod.invoke(store,new Byte(kvalue));
							 }else{
								 setMethod.invoke(store,kvalue);
							 }
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if(flag){
					//设置关联属性	
					store.setClientStructureId(455);
					store.setClientId(clientId);
					stores.add(store);
					successList.add(store);
				}
			}
			if(stores!=null && stores.size()>0){			
				try{
					//入库
					storeService.batchSaveAppleStores(stores);
				}catch(Exception ex){
					ex.printStackTrace();
				} 			  
			}
		}
		map.put("successList", successList);
		map.put("errStrList", errStrList);
		map.put("errDataList", errDataList);
		return map;
	}

	@Override
	public Map<String, Object> validataBwybStore(MultipartFile file,Integer clientId) {
		ExcelReader reader = new ExcelReader(file);
		Map<String,Object> map = new HashMap<String, Object>();
		/**将一个sheet里面数据分成多个批次导入：每次导入1000条数据*/
		Integer eachCount = 1000;
		Integer rowCount = reader.getRowNum(0)+1;
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		List<Store> successList = new ArrayList<Store>();
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		List<City> citys = cityService.getAll();
		List<Province> provices = provinceService.getAll();
		Map<String,Integer> cityMap = new HashMap<String, Integer>();
		Map<String,String> storeNoMap = new HashMap<String, String>();
		for (City c : citys) {
			cityMap.put(c.getCity(), c.getCityId());
		}
		Map<String, Integer> provicesMap =  new HashMap<String, Integer>();
		for (Province province : provices) {
			provicesMap.put(province.getProvince(), province.getProvinceId());
		}
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
					if(!MobiStringUtils.contains(ImportConstants.STORE_BWYB_TITLE, titles[i])){
						map.put("UnknownColumnName","未知的列名："+titles[i]);
						map.put("successList", successList);
						map.put("errStrList", errStrList);
						map.put("errDataList", errDataList);
						return map;
					}
				}
			}
			List<Store> stores = new ArrayList<Store>();
			for (int i = 1; i < values.size(); i++) {
				Store store = new Store();
				String[] vv = values.get(i);
				boolean flag = true;
				for (int j = 0; j < vv.length; j++) {	
					if(flag){
						String kvalue = vv[j];//值
						String cvalue = null;//列名
						for (int k = 0; k < ImportConstants.STORE_BWYB_TITLE.length; k++) {
							if(ImportConstants.STORE_BWYB_TITLE[k].equals(ctitles[j])){
								cvalue = ImportConstants.STORE_BWYB_COLUMN[k];
								break;
							}
						}
						
						if("structureName".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "客户区域不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								ClientStructure  clientStructure = clientStructureService.getClientStructureByName(clientId,kvalue);
								if(clientStructure == null){
									String errStr = "未知的分部";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else{
									store.setClientStructureId(clientStructure.getClientStructureId());
								}
							}
						}else if("provinceName".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "省份不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								if(provicesMap.containsKey(kvalue)){
									store.setProvinceId(provicesMap.get(kvalue));
								}else{
									String errStr = "未知的省份名称："+kvalue;
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("cityName".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								if(cityMap.containsKey(kvalue)){
									store.setCityId(cityMap.get(kvalue));
								}else{
									String errStr = "未知的城市名称："+kvalue;
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}else{
								String errStr = "城市不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("firstChannelName".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "渠道不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								Map<String,Object> params = new HashMap<String, Object>();
								params.put("clientId", clientId);
								params.put("channelName",kvalue);
								List<Channel>  firstChannels = channelService.findFirstChannelByChannelName(params);
								if(firstChannels != null && firstChannels.size()>1){
									String errStr = "存在有多个一样的渠道";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else if(firstChannels != null && firstChannels.size() == 1){
									Channel channel = firstChannels.get(0);
									String kvalueSecondChannelName = vv[j+1];//值
									if(kvalueSecondChannelName.isEmpty()){
										String errStr = "客户渠道不能为空";
										errStrList.add(errStr);
										errDataList.add(values.get(i));
										flag = false;
										continue;
									}else{
										Map<String, Object> paramter = new HashMap<String, Object>();
										paramter.put("clientId", clientId);
										paramter.put("channelName", kvalueSecondChannelName);
										paramter.put("parentId", channel.getChannelId());
										List<Channel> secondChannels = channelService.findChannelByChannelNameAndParentId(paramter);
										if(secondChannels != null && secondChannels.size()>1){
											String errStr = "存在多个一样的客户聚道";
											errStrList.add(errStr);
											errDataList.add(values.get(i));
											flag = false;
											continue;
										}else if(secondChannels != null && secondChannels.size() == 1){
											Channel secondChannel = secondChannels.get(0);
											store.setChannelId(secondChannel.getChannelId());
										}else{
											String errStr = "未知的客户渠道";
											errStrList.add(errStr);
											errDataList.add(values.get(i));
											flag = false;
											continue;
										}
									}
								}else{
									String errStr = "未知的渠道";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("chainName".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "连锁名称不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								Map<String,Object> params = new HashMap<String, Object>();
								params.put("clientId", clientId);
								params.put("chainName",kvalue);
								List<Chain>  chains = chainService.findChainByChainName(params);
								if(chains != null && chains.size()>1){
									String errStr = "存在有多个一样的连锁";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else if(chains != null && chains.size() == 1){
									Chain chain = chains.get(0);
									store.setChainId(chain.getChainId());
								}else{
									String errStr = "未知的连锁";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("storeNo".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "门店编号不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("loginName".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "执行人员不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								ClientUser clientUser = clientUserService.findByLoginName(kvalue, clientId);
								if(clientUser!=null){
									store.setClientUserId(clientUser.getClientUserId());
								}else{
									String errStr = "未知的执行人员："+kvalue;
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						} 
						
						try {
							if(!cvalue.isEmpty()){
								 PropertyDescriptor descriptor = new PropertyDescriptor(cvalue, store.getClass());
								 Method setMethod = descriptor.getWriteMethod();
								 setMethod.invoke(store,kvalue);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if(flag){
					//设置关联属性	
					store.setClientId(clientId);
					stores.add(store);
					successList.add(store);
				}
			}
			if(stores!=null && stores.size()>0){			
				try{
					//入库
					storeService.batchSaveBwybStores(stores);
				}catch(Exception ex){
					ex.printStackTrace();
				} 			  
			}
		}
		map.put("successList", successList);
		map.put("errStrList", errStrList);
		map.put("errDataList", errDataList);
		return map;
	}

	@Override
	public Map<String, Object> validataWorkStore(MultipartFile file,Integer clientId) {
		ExcelReader reader = new ExcelReader(file);
		Map<String,Object> map = new HashMap<String, Object>();
		/**将一个sheet里面数据分成多个批次导入：每次导入1000条数据*/
		Integer eachCount = 1000;
		Integer rowCount = reader.getRowNum(0)+1;
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		List<Store> successList = new ArrayList<Store>();
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>(); 
		String[] ctitles = null;
		Map<String,String> storeAndCityNameMap = new HashMap<String, String>();
		List<Province> provices = provinceService.getAll();
		Map<String,Integer> proviceMap = new HashMap<String, Integer>();
		Map<String,Integer> cityMap = new HashMap<String, Integer>();
		for(Province province : provices){
			proviceMap.put(province.getProvince(), province.getProvinceId());
		}
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
					if(!MobiStringUtils.contains(ImportConstants.STORE_WORKLOG_TITLE, titles[i])){
						map.put("UnknownColumnName","未知的列名："+titles[i]);
						map.put("successList", successList);
						map.put("errStrList", errStrList);
						map.put("errDataList", errDataList);
						return map;
					}
				}
			}
			List<Store> stores = new ArrayList<Store>();
			for (int i = 1; i < values.size(); i++) {
				Store store = new Store();
				String[] vv = values.get(i);
				boolean flag = true;
				for (int j = 0; j < vv.length; j++) {	
					if(flag){
						String kvalue = vv[j];//值
						String cvalue = null;//列名
						for (int k = 0; k < ImportConstants.STORE_WORKLOG_TITLE.length; k++) {
							if(ImportConstants.STORE_WORKLOG_TITLE[k].equals(ctitles[j])){
								cvalue = ImportConstants.STORE_WORKLOG_COLUMN[k];
								break;
							}
						}
						
						if("storeName".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "门店名称不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								String cityName = vv[j+3];//值
								if(StringUtils.isEmpty(cityName)){
									String errStr = "城市名称不能为空";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else{
									if(storeAndCityNameMap.containsKey(kvalue+"_"+cityName)){
										String errStr = "同一个城市门店名称不能重复";
										errStrList.add(errStr);
										errDataList.add(values.get(i));
										flag = false;
										continue;
									}else{
										storeAndCityNameMap.put(kvalue+"_"+cityName, kvalue+"_"+cityName);
										Map<String,Object> params = new HashMap<String, Object>();
										params.put("clientId", clientId);
										params.put("storeName", kvalue);
										params.put("cityName", cityName);
										List<Store> queryStores = storeService.findStoreByStoreNameAndCityName(params);
										if(queryStores!=null && queryStores.size()>0){
											String errStr = "同一个城市门店名称不能重复";
											errStrList.add(errStr);
											errDataList.add(values.get(i));
											flag = false;
											continue;
										}
									}
								}
							} 
						}if("structureName".equals(cvalue)){
							if(StringUtils.isEmpty(kvalue)){
								String errStr = "所在部门不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								ClientStructure  clientStructure = clientStructureService.getClientStructureByName(clientId,kvalue);
								if(clientStructure == null){
									String errStr = "未知的分部";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else{
									store.setClientStructureId(clientStructure.getClientStructureId());
								}
							}
						}else if("channelName".equals(cvalue)){		//channelName 验证
							if(StringUtils.isNotEmpty(kvalue)){
								Map<String,Object> params = new HashMap<String, Object>();
								params.put("clientId", clientId);
								params.put("channelName",kvalue);
								List<Channel>  channels = channelService.findChannelByChannelName(params);
								if(channels != null && channels.size()>1){
									String errStr = "存在有多个一样的渠道";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else if(channels != null && channels.size() == 1){
									Channel channel = channels.get(0);
									store.setChannelId(channel.getChannelId());
								}else{
									String errStr = "未知的渠道："+kvalue;
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("chainName".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								Map<String,Object> params = new HashMap<String, Object>();
								params.put("clientId", clientId);
								params.put("chainName",kvalue);
								List<Chain>  chains = chainService.findChainByChainName(params);
								if(chains != null && chains.size()>1){
									String errStr = "存在有多个一样的连锁";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}else if(chains != null && chains.size() == 1){
									Chain chain = chains.get(0);
									store.setChainId(chain.getChainId());
								}else{
									String errStr = "未知的连锁："+kvalue;
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}else if("cityName".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								if(cityMap.containsKey(kvalue)){
									store.setCityId(cityMap.get(kvalue));
								}else{
									String errStr = "未知的城市名称："+kvalue;
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}else{
								String errStr = "城市不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("provinceName".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								if(proviceMap.containsKey(kvalue)){
									store.setProvinceId(proviceMap.get(kvalue));
									List<City> citys = cityService.queryCityByProvinceId(store.getProvinceId());
									cityMap = new HashMap<String, Integer>();
									for (City c : citys) {
										cityMap.put(c.getCity(), c.getCityId());
									}
								}else{
									String errStr = "未知的省份名称："+kvalue;
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
						}
						try {
							 PropertyDescriptor descriptor = new PropertyDescriptor(cvalue, store.getClass());
							 Method setMethod = descriptor.getWriteMethod();
							 setMethod.invoke(store,kvalue);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if(flag){
					//设置关联属性	
					store.setStoreId(UUID.randomUUID().toString());
					store.setClientId(clientId);
					stores.add(store);
					successList.add(store);
				}
			}
			if(stores!=null && stores.size()>0){			
				try{
					//入库
					storeService.batchSaveWorkLogStores(stores);
				}catch(Exception ex){
					ex.printStackTrace();
				} 			  
			}
		}
		map.put("successList", successList);
		map.put("errStrList", errStrList);
		map.put("errDataList", errDataList);
		return map;
	}
	
}
