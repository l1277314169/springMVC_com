package cn.mobilizer.channel.systemManager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.ClientPrivileges;

@Repository
public class ClientRolesModuleMappingDao extends MyBatisDao {
	public ClientRolesModuleMappingDao(){
		super("CLIENT_ROLES_MODULE_MAPPING");
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
}