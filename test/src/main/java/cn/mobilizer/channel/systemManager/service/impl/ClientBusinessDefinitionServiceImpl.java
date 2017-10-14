package cn.mobilizer.channel.systemManager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.ClientBusinessDefinitionDao;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;
import cn.mobilizer.channel.systemManager.service.ClientBusinessDefinitionService;
@Service
public class ClientBusinessDefinitionServiceImpl implements
		ClientBusinessDefinitionService {
   @Autowired
   private ClientBusinessDefinitionDao clientBusinessDefinitionDao;
	@Override
	public List<ClientBusinessDefinition> findClientBusinessDefinitionByTabelName(
			Map<String, Object> parameters) throws BusinessException {
		return clientBusinessDefinitionDao.findListByParams(parameters);
	}

}
