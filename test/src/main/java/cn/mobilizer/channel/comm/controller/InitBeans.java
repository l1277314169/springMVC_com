package cn.mobilizer.channel.comm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.dao.OptionsDao;
import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.init.InitBusinessDefinitionBean;
import cn.mobilizer.channel.comm.init.InitOptionsBean;
import cn.mobilizer.channel.comm.utils.MemcachedUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.MemcachedEnum;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.ClientBusinessDefinitionDao;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;
import cn.mobilizer.channel.util.Constants;

@Controller
@RequestMapping(value = "/sysInit")
public class InitBeans extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2535363378936647155L;
	
	Map<String,Map<String,Map<String,String>>> optionMap = InitOptionsBean.optionMap;
	Map<String,Map<String,List<ClientBusinessDefinition>>> DEFINITION_MAP = InitBusinessDefinitionBean.DEFINITION_MAP;
	
	@Autowired
	private OptionsDao optionsDao;
	
	@Autowired
	private ClientBusinessDefinitionDao clientBusinessDefinitionDao;
	
	@RequestMapping(value = "/list")
	public String list(Model model) {
		return "/init/list";
	}
	
	@RequestMapping(value="/updateOption")
	@ResponseBody
	public Object updateOption() throws Exception{
		try {
			InitOptionsData();
			return new ResultMessage("success", "更新option成功。");
		}catch (Exception e) {
			log.info("更新option失败!");
			e.printStackTrace();
			return new ResultMessage("error", "更新option失败。");
		}
	}
	
	@RequestMapping(value="/updateDefinition")
	@ResponseBody
	public Object updateDefinition() throws Exception{
		try {
			InitDefinitionData();
			return new ResultMessage("success", "更新definition成功。");
		}catch (Exception e) {
			log.info("更新definition失败!");
			e.printStackTrace();
			return new ResultMessage("error", "更新definition失败。");
		}
	}
	
	@RequestMapping(value="/cleanMemcached/{key}")
	@ResponseBody
	public Object cleanMemcached(@PathVariable("key")String key) throws Exception{
		try {
			if(StringUtil.isEmptyString(key)){
				return new ResultMessage("error", "key不能为空。");
			}
			Object o = MemcachedUtil.getInstance().get(key);
			if(o == null) {
				return new ResultMessage("error", "memcached中没有"+key+"该key的缓存。");
			}
			MemcachedUtil.getInstance().remove(key);
			return new ResultMessage("success", "清除key:"+key+"成功。");
		}catch (Exception e) {
			log.info("清除key:"+key+"失败。");
			e.printStackTrace();
			return new ResultMessage("error", "清除key:"+key+"失败。");
		}
	}
	
	protected void InitOptionsData(){
		optionMap.clear();
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
	
	protected void InitDefinitionData(){
		DEFINITION_MAP.clear();
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
