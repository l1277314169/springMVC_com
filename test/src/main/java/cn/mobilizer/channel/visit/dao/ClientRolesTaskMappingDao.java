package cn.mobilizer.channel.visit.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.ClientRolesTaskMapping;

@Repository
public class ClientRolesTaskMappingDao extends MyBatisDao {
	
	public ClientRolesTaskMappingDao() {
		super("CLIENT_ROLES_TASK_MAPPING");
	}
	
    public int deleteByPrimaryKey(Integer mappingId){
    	return super.delete ("deleteByPrimaryKey", mappingId);
    }

    public int insert(ClientRolesTaskMapping record){
    	return super.insert("insertSelective", record);
    }

    public ClientRolesTaskMapping selectByPrimaryKey(Integer mappingId){
    	return super.get("selectByPrimaryKey", mappingId);
    }

    public int updateByPrimaryKeySelective(ClientRolesTaskMapping record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(ClientRolesTaskMapping record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public String getClientRolesIdsByTaskId(Map<String,Object> params){
		return super.get("getClientRolesIdsByTaskId",params);
	}
	
	public int currentMapppingisdelte(Map<String,Object> params){
		return super.update("currentMapppingisdelte", params);
	}
}