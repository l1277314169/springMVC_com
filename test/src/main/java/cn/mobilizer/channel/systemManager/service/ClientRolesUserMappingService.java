package cn.mobilizer.channel.systemManager.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;

public interface ClientRolesUserMappingService {
   
	/**
	 * 
	 * @param clientRolesUserMapping
	 * @return
	 * @throws BusinessException
	 */
	public int addClientRolesUserMapping(ClientRolesUserMapping clientRolesUserMapping)throws BusinessException;
	
	/**
	 * 
	 * @param clientRolesUserMapping
	 * @return
	 * @throws BusinessException
	 */
	public int updateClientRolesUserMapping(ClientRolesUserMapping clientRolesUserMapping)throws BusinessException;
	/**
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientRolesUserMapping> getClientRolesUserMapping(Map<String, Object> parameters )throws BusinessException;
	/**
	 * 
	 * @return ClientRoles
	 */
	public List<ClientRolesUserMapping> findClientRolesListByClientUserId(Integer ClientUserId,Integer ClientId) throws BusinessException;
}
