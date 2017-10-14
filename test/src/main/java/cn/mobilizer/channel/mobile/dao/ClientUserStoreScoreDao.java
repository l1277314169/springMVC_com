package cn.mobilizer.channel.mobile.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.mobile.po.ClientUserStoreScore;

@Repository
public class ClientUserStoreScoreDao extends MyBatisDao{
	public ClientUserStoreScoreDao(){
		super("REPORT");
	}
	
	public List<ClientUserStoreScore> getStoreScoreList(Map<String, Object> searchParams){
		return super.getList("getStoreScoreList", searchParams);
	}
}
