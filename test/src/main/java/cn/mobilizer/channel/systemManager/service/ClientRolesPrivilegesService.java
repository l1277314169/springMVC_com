package cn.mobilizer.channel.systemManager.service;

import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientRolesPrivileges;

public interface ClientRolesPrivilegesService {
	
	
	/**
	 * 根据privilege_code查找用户角色是否有这个权限
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public ClientRolesPrivileges findClientRolesPrivilegesByCode(Map<String, Object> params) throws BusinessException;
}
