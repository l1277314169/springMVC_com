package cn.mobilizer.channel.base.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.DistributorUserMapping;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class DistributorUserMappingDao extends MyBatisDao{
    public DistributorUserMappingDao(){
    	super("DISTRIBUTOR_USER_MAPPING");
    }
    
    public int insert(DistributorUserMapping distributorUserMapping){
    	 super.insert("insertSelective", distributorUserMapping);
    	return distributorUserMapping.getDistributorId();
    }
    
    public int update(DistributorUserMapping distributorUserMapping){
    	return super.update("updateByPrimaryKeySelective", distributorUserMapping);
   }
    public DistributorUserMapping getDistributor(Map<String,Object> params){
    	
    	return super.get("getDistributorByclientUserId", params);
    }
    
    public int isdeleteDistributorUserMapping(Map<String,Object> params){
    	
    	return super.update("isdeleteByclientUserId", params);
    }
}
