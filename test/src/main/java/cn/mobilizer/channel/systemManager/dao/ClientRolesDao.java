package cn.mobilizer.channel.systemManager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.ClientRoles;

/**角色管理
 * @author Nany
 * 2014年12月8日下午3:48:53
 */
@Repository
public class ClientRolesDao extends MyBatisDao{
	
	public ClientRolesDao(){
		super("CLIENT_ROLES");
	}

	public Integer queryClientRoleCount(Map<String, Object> params) {
		return super.get("queryTotalCount", params);
		
	}
	
	public List<ClientRoles> getObjectList(Map<String, Object> params){
		return super.queryForList("getObjectList", params);
	}

	public List<ClientRoles> getClientRoleList(Map<String, Object> parames) {

		return super.queryForList("findListByParams", parames);

	}
	public List<ClientRoles> getClientRoleList1(Map<String, Object> parames) {
		return super.queryForList("findListByParams1", parames);

	}

	
	public void addRole(ClientRoles clientRole) {
		
		super.insert("insertSelective", clientRole);
	}


	public ClientRoles getClientRole(Integer roleId) {
		return super.get("selectByPrimaryKey", roleId);

	}

	public void updateRole(ClientRoles clientRole) {
		super.update("updateByPrimaryKeySelective", clientRole);
		
	}

	
	public List<String> getUserRolesByClientUserId(Integer clientUserId) {
		return super.getList ("getUserRolesByClientUserId", clientUserId);
	}

	public ClientRoles getClientRoleByRoleName(Map<String, Object> params) {
		return super.get("getClientRoleByRoleName", params);
	}
	
	public ClientRoles getWebAppRoles(String webIdAppId){
		return super.get("getWebAppRoles", webIdAppId);
		
	}
	
	public List<ClientRoles> findRolesbyselectTwo(Map<String, Object> params){
		
		return super.queryForList("findRolesbyselectTwo", params);
	}
	
	public ClientRoles getClientRolesById(Integer id){
		return super.get("getRolesById", id);
	}
	
	public List<ClientRoles> findClientRolesByUserId(Map<String, Object> params) {
		return super.queryForList("findClientRolesByUserId", params);
	}
	
	public List<ClientRoles> findSystemRolesByClientIdAndRoleLevel(Map<String, Object> params) {
		return super.queryForList("findSystemRolesByClientIdAndRoleLevel", params);
	}
	
	/**
	 * 根据 client_id 查询所有角色
	 * @param params ： client_id
	 * @return	
	 * <pre> 
	 * 		（ 查询返回角色整体信息）	
	 * 		返回结果集中的所有数据都是可用状态
	 * 			Map ， key:roleName (角色名称)
	 * 	</pre>
	 */
	public Map<String,ClientRoles> getClientRolesMapByName(Map<String, Object> params){
		return super.queryForMap("getObjectList", params, "roleName");
	}
	
	/**
	 * 根据 ClientID 查询所有角色名称
	 * @param params ClientID
	 * @return 返回  MAP , key : roleName (角色名称)
	 * <pre>
	 * 		根据ClientID 查询角色， 返回角色名集合
	 * 		key ： 角色名称
	 * 		value ： 角色编号， 角色名称
	 * </pre>
	 */
	public Map<String,ClientRoles> getRoleNameMyClientID(Map<String, Object> params){
		return super.queryForMap("getRoleNameMyClientID",params, "roleName");
	}
	
	public List<ClientRoles> getBusinessRoles(Map<String, Object> params){
		return super.queryForList("getBusinessRoles", params);
	}
	
	public Map<String,ClientRoles> getClientRolesMapByid(Map<String, Object> params){
		return super.queryForMap("selectByParam", params, "roleName");
	}
}
