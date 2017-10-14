package cn.mobilizer.channel.systemManager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.ClientPrivileges;

@Repository
public class ClientPrivilegesDao extends MyBatisDao {
	public ClientPrivilegesDao(){
		super("CLIENT_PRIVILEGES");
	}
	
	public List<String> getUserPermissionsByClientUserId(Integer clientUserId) {
		return super.getList ("getUserPermissionsByClientUserId", clientUserId);
	}
	
	public List<ClientPrivileges> getUserMenu(Integer clientUserId) {
		return super.getList ("getUserMenu", clientUserId);
	}
	
	public List<TreeNodeVO> getWebTreeNode(Map<String, Object> paramMap) {
		return super.getList("getWebTreeNode", paramMap);
	}
}