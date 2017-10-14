/**
 * 
 */
package cn.mobilizer.channel.base.dao;


import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.ClientUserExpand;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class ClientUserExpandDao extends MyBatisDao {
	
	public ClientUserExpandDao() {
		super("CLIENT_USER_EXPAND");
	}
	
	public Integer update(ClientUserExpand clientUserExpand){
		return super.update("updateByPrimaryKeySelective", clientUserExpand); 
	}
	
	public Integer updateLastLoginTimeByclientUserId(Map<String, Object> parames){
		return super.update("updateLastLoginTimeByclientUserId", parames); 
	}
	
	public ClientUserExpand selectByPrimaryKey(Integer clientUserExpandId){
		return super.get("selectByPrimaryKey", clientUserExpandId);
	}
	
	public Integer insert(ClientUserExpand clientUserExpand){
		return super.insert("insertSelective", clientUserExpand);
	}
	
}
