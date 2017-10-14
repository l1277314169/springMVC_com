package cn.mobilizer.channel.systemManager.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.systemManager.po.ClientRoles;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**角色管理
 * @author Nany
 * 2014年12月8日下午3:11:53
 */
public interface ClientRolesService extends BasicService<ClientRoles>{

	/**获取查询的记录数
	 * @author Nany
	 * 2014年12月8日下午3:38:33
	 * @param parameters
	 * @return
	 */
	public int queryClientRoleCount(Map<String, Object> parameters);

	/**角色管理--新增--保存
	 * @author Nany
	 * 2014年12月8日下午5:46:29
	 * @param clientId
	 */
	public void addRoles(ClientRoles clientRole);

	/**角色管理--编辑
	 * @author Nany
	 * 2014年12月8日下午6:36:03
	 * @param roleId
	 * @param clientId
	 * @return
	 */
	public ClientRoles getClientRole(Integer roleId);

	
	/**角色管理--编辑--保存
	 * @author Nany
	 * 2014年12月8日下午7:06:05
	 * @param clientRole
	 */
	public void updateRole(ClientRoles clientRole);

	
	/**
	 * 获取角色
	 * @param clientUserId
	 * @return
	 * @throws BusinessException
	 */
	public List<String> getUserRolesByClientUserId(Integer clientUserId) throws BusinessException;

	public List<ClientRoles> findSystemRolesByClientId(Integer clientId);
	
	public List<ClientRoles> findSystemRolesByClientIdAndRoleLevel(Integer clientId,Byte level) throws BusinessException;

	/**新增角色时，根据英文吗，判断角色是否存在
	 * @author Nany
	 * 2015年1月4日上午11:55:56
	 * @param clientId
	 * @param roleNameEn
	 * @return
	 * @throws BusinessException
	 */
	public ClientRoles getClientRoleByRoleName(int clientId, String roleName) throws BusinessException;
	
	/**
	 * 修改角色的权限
	 * @param roleId
	 * @param checkedWebOld
	 * @param checkedWebNew
	 * @param checkedMobileOld
	 * @param checkedMobileNew
	 * @param checkedDataOld
	 * @param checkedDataNew
	 * @throws BusinessException
	 */
	public void updateRolePrivileges(Integer roleId,Integer clientId,String checkedWebOld,String checkedWebNew,
			String checkedMobileOld,String checkedMobileNew,String checkedDataOld,String checkedDataNew) throws BusinessException;
	/**
	 * 
	 * @param clientRole
	 * @throws BusinessException
	 */
	public void deleteRole(ClientRoles clientRole) throws BusinessException;
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientRoles> queryRolesByselectTwo(Map<String, Object> parameters)throws BusinessException;
	/**
	 * select2控件异步加载初始化数据
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public ClientRoles queryRolesById(Integer id)throws BusinessException;
	
	/**
	 * 查询用户的角色等级
	 * @param clientUserId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public Byte getUserRoleLevel(Integer clientUserId, Integer clientId) throws BusinessException;
	
	/**
	 * 角色为业务代表和业务主管
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientRoles> getRolesIsBusiness(Integer clientId)throws BusinessException;
	
	
	public Map<String, ClientRoles> selectAllRoles(Integer clientId);

}
