package cn.mobilizer.channel.systemManager.service;

import java.util.List;
import java.util.Map;



import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;


public interface ClientBusinessDefinitionService {
  
	public List<ClientBusinessDefinition> findClientBusinessDefinitionByTabelName(Map<String, Object> parameters)throws BusinessException;
}
