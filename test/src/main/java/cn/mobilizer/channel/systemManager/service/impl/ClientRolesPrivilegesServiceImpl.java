package cn.mobilizer.channel.systemManager.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.ClientRolesPrivilegesDao;
import cn.mobilizer.channel.systemManager.po.ClientRolesPrivileges;
import cn.mobilizer.channel.systemManager.service.ClientRolesPrivilegesService;

@Service
public class ClientRolesPrivilegesServiceImpl implements ClientRolesPrivilegesService{

	@Autowired
	private ClientRolesPrivilegesDao clientRolesPrivilegesDao;
	
	@Override
	public ClientRolesPrivileges findClientRolesPrivilegesByCode(Map<String, Object> params) throws BusinessException {
		return clientRolesPrivilegesDao.findClientRolesPrivilegesByCode(params);
	}
	
}
