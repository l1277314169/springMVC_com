package cn.mobilizer.channel.base.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.vo.ClientVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class ClientDao extends MyBatisDao {
	
	public ClientDao() {
		super("CLIENT");
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public Client findByClientName(Map<String, Object> params){
		List<Client> ls = super.queryForList ("selectByParams", params);
		if (ls != null && ls.size ()>0) {
			return ls.get (0);
		} else {
			return null;
		}
		
	}
	
	public List<Client> queryAll(){
		return super.queryForList("queryAllClient");
	}
	
	public List<ClientVO> queryClientVOAll(){
		return super.queryForList("queryAllClient");
	}
	
	public Client findClientByParams(Map<String, Object> params){
		return super.get("findClientByParams", params);
	}
	
	public List<Client> loadAllClient(){
		return super.queryForList("loadAllClient");
	}
	
}