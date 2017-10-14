package cn.mobilizer.channel.systemManager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;

@Repository
public class ClientBusinessDefinitionDao extends MyBatisDao {
	
	public ClientBusinessDefinitionDao() {
		super("CLIENT_BUSINESS_DEFINITION");
	}
	
	public List<ClientBusinessDefinition> findListByParams(Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}
	
	
	public List<ClientBusinessDefinition> getListByParams(Map<String, Object> params) {
		return super.getList("findListByParams", params);
	}
	
}
