package cn.mobilizer.channel.systemManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;
@Repository
public class ClientRolesUserMappingDao extends MyBatisDao{  
	
	public ClientRolesUserMappingDao(){
		super("CLIENT_ROLES_USER_MAPPING");
	}
	
	public int  insert(ClientRolesUserMapping clientRolesUserMapping){
		return super.insert("insertSelective", clientRolesUserMapping);
		
	}
	
	public int update(ClientRolesUserMapping clientRolesUserMapping){
		return super.update("updateByPrimaryKeySelective", clientRolesUserMapping);
		
	}
	
	public List<ClientRolesUserMapping> selectByPrimaryKey(Map<String, Object> parameters){
		return super.queryForList("selectByPrimaryKeyRoles", parameters);
	}
	
	public int updateIsdelete(Integer clientRolesUserMappingId){
		
		return super.update("updateIsdelete",clientRolesUserMappingId);
	}
	
	public ClientRolesUserMapping selectClientRolesUserMapping(Integer roleId,Integer clientUserId){
		ClientRolesUserMapping clientRolesUserMapping = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("roleId", roleId);
		parameters.put("clientUserId", clientUserId);
		List<ClientRolesUserMapping> ls = super.getList("selectClientRolesUserMapping", parameters);
		if(ls !=null && ls.size() >0){
			clientRolesUserMapping = ls.get(0);
		}
		return clientRolesUserMapping;
	}
	public List<ClientRolesUserMapping> getCleintRolesByClientUserId(Map<String, Object> parames){
		return super.queryForList("cleintRolesByClientUserId", parames);
	}

	
	public List<ClientRolesUserMapping> getClientRolesUserMappingByRoleId(Map<String, Object> params) {
		return super.getList("getClientRolesUserMappingByRoleId", params);
	}
	public int insertList(List<ClientRolesUserMapping> list){
		return super.insert("insertList", list);
	}
	
	public int insertGZRZList(List<Integer> list){
		return super.insert("insertGZRZList", list);
	}
	
	
}
