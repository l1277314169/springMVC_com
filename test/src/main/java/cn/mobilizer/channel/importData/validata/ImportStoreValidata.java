package cn.mobilizer.channel.importData.validata;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.base.service.ChainService;
import cn.mobilizer.channel.base.service.ChannelService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;

@Service
public class ImportStoreValidata extends ImportValidata<Store> implements ImportDataService{
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
	
	@Override
	public Map<String,Object> validata(List<ClientBusinessDefinition> importDefinitionList,MultipartFile file, Integer clientId) {
		ExcelReader reader = new ExcelReader(file);
		Map<String,Object> map = new HashMap<String, Object>();
		/**将一个sheet里面数据分成多个批次导入：每次导入1000条数据*/
		Integer eachCount = 1000;
		Integer rowCount = reader.getRowNum(0)+1;
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		String[] definiTionTitles = new String[importDefinitionList.size()];
		String[] definiTionColumns = new String[importDefinitionList.size()];
		if(importDefinitionList != null){
			for(int i=0; i<importDefinitionList.size(); i++){
				definiTionTitles[i] = importDefinitionList.get(i).getColumnDesc();
				definiTionColumns[i] = importDefinitionList.get(i).getAttributeName();
			}
		}
		List<Store> successList = new ArrayList<Store>();
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
					if(!MobiStringUtils.contains(definiTionTitles, titles[i])){
						//ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						//rm.setMessage("存在不能识别的列名：" +titles[i]);
						map.put("UnknownColumnName",titles[i]);
					}
				}
			}
			List<Store> stores = new ArrayList<Store>();
			List<Options> optionsList = optionsService.findOptionsByOptionName("Hierarchy_storeType", clientId);
			Map<String,Integer> optionsMap = new HashMap<String, Integer>();
			for(Options options : optionsList){
				optionsMap.put(options.getOptionText(), options.getOptionValue());
			}
			for (int i = 1; i < values.size(); i++) {
				Store store = new Store();
				String[] vv = values.get(i);
				boolean flag = true;
				for (int j = 0; j < vv.length; j++) {	
					if(flag){
						String kvalue = vv[j];//值
						String cvalue = null;//列名
						for (int k = 0; k < definiTionTitles.length; k++) {
							if(definiTionTitles[k].equals(ctitles[j])){
								cvalue = definiTionColumns[k];
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
							params.put("searchName",kvalue);
							List<Channel>  channels = channelService.queryChannelList(params);
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
							params.put("searchName",kvalue);
							List<Chain>  chains = chainService.findChainName(params);
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
								String errStr = "未知的渠道";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}
						}else if("storeHierarchy".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								if(optionsMap.containsKey(cvalue)){
									store.setStoreType(optionsMap.get(cvalue));
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
						}else if("cm".equals(cvalue)){
							if(StringUtils.isNotEmpty(kvalue)){
								String errStr = "CM不能为空";
								errStrList.add(errStr);
								errDataList.add(values.get(i));
								flag = false;
								continue;
							}else{
								ClientUser clientUser = clientUserService.findByLoginName(kvalue, clientId);
								if(clientUser!=null){
									store.setClientUserId(clientUser.getClientUserId());
								}else{
									String errStr = "未知的CM";
									errStrList.add(errStr);
									errDataList.add(values.get(i));
									flag = false;
									continue;
								}
							}
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
					saveImportData(stores,clientId);
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
	public void saveImportData(List<Store> list, Integer clientId) {
		storeService.batchSaveStores(list);
	}
	
}
