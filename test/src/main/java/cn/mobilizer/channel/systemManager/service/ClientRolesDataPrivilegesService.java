package cn.mobilizer.channel.systemManager.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;

public interface ClientRolesDataPrivilegesService {
   
	/**
	 * 获取data权限
	 * @param clientUserId
	 * @return
	 * @throws BusinessException
	 */
	public List<String> getUserPermissionsByClientUserId(Integer clientUserId) throws BusinessException ;
	
	/**
	 * 获取data权限 
	 * @param clientUserId
	 * @return 返回结果为一个字符串用","号分隔。
	 * @throws BusinessException
	 */
	public String getUserPermissionsByClientUserId2String(Integer clientUserId) throws BusinessException ;
}
