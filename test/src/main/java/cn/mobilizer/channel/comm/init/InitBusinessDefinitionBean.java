package cn.mobilizer.channel.comm.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.systemManager.dao.ClientBusinessDefinitionDao;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;
import cn.mobilizer.channel.util.Constants;


public class InitBusinessDefinitionBean implements InitializingBean {
	private static final Log LOG = LogFactory.getLog(InitBusinessDefinitionBean.class);
	
	public static Map<String,Map<String,List<ClientBusinessDefinition>>> DEFINITION_MAP = new HashMap<String,Map<String,List<ClientBusinessDefinition>>>();
	
	@Autowired
	private ClientBusinessDefinitionDao clientBusinessDefinitionDao;
	
	@Override
	public void afterPropertiesSet() throws Exception{
		try {
			InitDefinitionData();
			LOG.info("<=======================初始化Definition完毕======================>!");
		} catch (Exception e) {
			LOG.info("初始化client_business_definition出错!");
			e.printStackTrace();
		}
	}

	public void InitDefinitionData(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("usedFlag", Constants.USED_FLAG_WEB);
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		List<ClientBusinessDefinition> ls = clientBusinessDefinitionDao.getListByParams(params);
		for ( ClientBusinessDefinition clientBusinessDefinition : ls ) {
			String key_1 = clientBusinessDefinition.getClientId() !=null ? clientBusinessDefinition.getClientId().toString() : null;
			String key_2 = clientBusinessDefinition.getTableName(); 
			if(key_1 != null && key_1 != ""){
				if(DEFINITION_MAP.containsKey(key_1)){
					Map<String,List<ClientBusinessDefinition>> map_1 = DEFINITION_MAP.get(key_1);
					if(map_1.containsKey(key_2)){
						List<ClientBusinessDefinition> list_2 = map_1.get(key_2);
						list_2.add(clientBusinessDefinition);
					}else {
						List<ClientBusinessDefinition> list_2 = new ArrayList<ClientBusinessDefinition>();
						list_2.add(clientBusinessDefinition);
						map_1.put(key_2, list_2);
					}
				} else {
					Map<String,List<ClientBusinessDefinition>> map_1 = new HashMap<String,List<ClientBusinessDefinition>>();
					List<ClientBusinessDefinition> list_2 = new ArrayList<ClientBusinessDefinition>();
					list_2.add(clientBusinessDefinition);
					map_1.put(key_2, list_2);
					DEFINITION_MAP.put(key_1, map_1);
				}
			} 
		}
	}
}
