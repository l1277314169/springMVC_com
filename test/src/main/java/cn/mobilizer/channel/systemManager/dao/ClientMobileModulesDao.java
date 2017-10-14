package cn.mobilizer.channel.systemManager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.ClientPrivileges;

@Repository
public class ClientMobileModulesDao extends MyBatisDao {
	public ClientMobileModulesDao(){
		super("CLIENT_MOBILE_MODULES");
	}
	
	public List<TreeNodeVO> getMobileTreeNode(Map<String, Object> paramMap) {
		return super.getList("getMobileTreeNode", paramMap);
	}
}