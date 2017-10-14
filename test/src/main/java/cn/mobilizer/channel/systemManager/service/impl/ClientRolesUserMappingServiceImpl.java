package cn.mobilizer.channel.systemManager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.ClientRolesUserMappingDao;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;
import cn.mobilizer.channel.systemManager.service.ClientRolesUserMappingService;
@Service
public class ClientRolesUserMappingServiceImpl implements ClientRolesUserMappingService{
  
	@Autowired
	private ClientRolesUserMappingDao clientRolesUserMappingDao;
	@Override
	public int addClientRolesUserMapping(
			ClientRolesUserMapping clientRolesUserMapping)
			throws BusinessException {
		return clientRolesUserMappingDao.insert(clientRolesUserMapping);
	}

	@Override
	public int updateClientRolesUserMapping(
			ClientRolesUserMapping clientRolesUserMapping)
			throws BusinessException {
		return clientRolesUserMappingDao.update(clientRolesUserMapping);
	}

	@Override
	public List<ClientRolesUserMapping> getClientRolesUserMapping(Map<String, Object> parameters)
			throws BusinessException {
		return clientRolesUserMappingDao.selectByPrimaryKey(parameters);
	}
	@Override
	public List<ClientRolesUserMapping> findClientRolesListByClientUserId(Integer clientUserId,Integer clientId) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientUserId",clientUserId);
		params.put("clientId",clientId);
		return clientRolesUserMappingDao.getCleintRolesByClientUserId(params);
	}

}
