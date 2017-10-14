package cn.mobilizer.channel.comm.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.base.dao.OptionsDao;
import cn.mobilizer.channel.base.po.Options;


public class InitOptionsBean implements InitializingBean {
	private static final Log LOG = LogFactory.getLog(InitOptionsBean.class);
	
	public static Map<String,Map<String,Map<String,String>>> optionMap = new HashMap<String,Map<String,Map<String,String>>>();
	
	@Autowired
	private OptionsDao optionsDao;
	
	@Override
	public void afterPropertiesSet() throws Exception{
		try {
			InitOptionsData();
			LOG.info("<=======================初始化Options完毕======================>!");
		} catch (Exception e) {
			LOG.info("初始化Options表出错!");
			e.printStackTrace();
		}
	}

	public void InitOptionsData(){
		List<Options> options = optionsDao.findAllOptions();
		for ( Options option : options ) {
			String key_1 = option.getClientId().toString();
			String key_2 = option.getOptionName(); 
			String key_3 = option.getOptionValue().toString(); 
			String value_3 = option.getOptionText(); 
			
			if(key_1 != null && key_1!= ""){
				if(optionMap.containsKey(key_1)){
					Map<String,Map<String,String>> map_1 = optionMap.get(key_1);
					if(map_1.containsKey(key_2)){
						Map<String,String> map_2 = map_1.get(key_2);
						map_2.put(key_3, value_3);
					}else {
						Map<String,String> map_2 = new HashMap<String,String>();
						map_2.put(key_3, value_3);
						map_1.put(key_2, map_2);
					}
				} else {
					Map<String,Map<String,String>> map_1 = new HashMap<String,Map<String,String>>();
					Map<String,String> map_2 = new HashMap<String,String>();
					map_2.put(key_3, value_3);
					map_1.put(key_2, map_2);
					optionMap.put(key_1, map_1);
				}
			} 
		}
	}
}
