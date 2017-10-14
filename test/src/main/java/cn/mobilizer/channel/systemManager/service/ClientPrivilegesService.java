package cn.mobilizer.channel.systemManager.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.vo.LeftMenuVO;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientPrivileges;


public interface ClientPrivilegesService {
	
	/**
	 * 获取web权限
	 * @param clientUserId
	 * @return
	 * @throws BusinessException
	 */
	public List<String> getUserPermissionsByClientUserId(Integer clientUserId) throws BusinessException ;
	
	/**
	 * 构造菜单
	 * @param clientUserId
	 * @return
	 * @throws BusinessException
	 */
	public List<LeftMenuVO> getMenusByClientUserId(Integer clientUserId) throws BusinessException ;
	
	/**
	 * 获取某个用户的左侧菜单项
	 * @param clientUserId
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientPrivileges> getUserMenu(Integer clientUserId) throws BusinessException;
	
	/**
	 * 获取某个用户的左侧菜单项-目前是两级菜单写死
	 * @param clientUserId
	 * @return
	 * @throws BusinessException
	 */
	Map<String, List<ClientPrivileges>> getMapUserMenu(Integer clientUserId) throws BusinessException;
	
	/**
	 * 通过角色id获取某个客户的web端所有权限树，并选中该角色的权限
	 * @param clientId
	 * @param roleId
	 * @return
	 */
	public List<TreeNodeVO> getWebTreeNode(Integer clientId,Integer roleId);
	
	/**
	 * 通过角色id获取某个客户的Mobile端所有权限树，并选中该角色的权限
	 * @param clientId
	 * @param roleId
	 * @return
	 */
	public List<TreeNodeVO> getMobileTreeNode(Integer clientId,Integer roleId);
	
	/**
	 * 通过角色id获取某个客户的data所有权限树，并选中该角色的权限
	 * @param clientId
	 * @param roleId
	 * @return
	 */
	public List<TreeNodeVO> getDataTreeNode(Integer clientId,Integer roleId);
}
