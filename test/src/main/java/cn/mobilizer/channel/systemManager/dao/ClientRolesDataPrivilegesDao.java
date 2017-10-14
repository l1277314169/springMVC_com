package cn.mobilizer.channel.systemManager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.ClientPrivileges;
import cn.mobilizer.channel.systemManager.po.ClientRolesDataPrivileges;

@Repository
public class ClientRolesDataPrivilegesDao extends MyBatisDao {
	public ClientRolesDataPrivilegesDao(){
		super("CLIENT_ROLES_DATA_PRIVILEGES");
	}
	
	public Integer deleteByRoleAndPrivileges(Map<String, Object> params) {
		return super.get("deleteByRoleAndPrivileges", params);
	}
	
	public Integer deleteByRoleId(Integer roleId) {
		return super.get("deleteByRoleId", roleId);
	}
	
	public Integer insertByRoleAndPrivileges(Map<String, Object> params) {
		return super.get("insertByRoleAndPrivileges", params);
	}
	
	public List<String> getUserPermissionsByClientUserId(Integer clientUserId) {
		return super.getList("getUserPermissionsByClientUserId", clientUserId);
	}
	
	public String getUserPermissionsByClientUserId2String(Integer clientUserId) {
		return super.get("getUserPermissionsByClientUserId2String", clientUserId);
	}
	
	public ClientRolesDataPrivileges getSelfDataPrivileges(Map<String, Object> paramMap) {
		return super.get("getSelfDataPrivileges", paramMap);
	}
}