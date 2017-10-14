package cn.mobilizer.channel.systemManager.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.ClientRolesPrivileges;

@Repository
public class ClientRolesPrivilegesDao extends MyBatisDao{
	
	public ClientRolesPrivilegesDao(){
		super("CLIENT_ROLES_PRIVILEGES");
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
	
	public ClientRolesPrivileges findClientRolesPrivilegesByCode(Map<String, Object> params){
		return super.get("findClientRolesPrivilegesByCode", params);
	}

}