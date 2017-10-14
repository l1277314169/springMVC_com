package cn.mobilizer.channel.base.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.SkuDistributionMapping;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class SkuDistributionMappingDao extends MyBatisDao{
	public SkuDistributionMappingDao(){
		super("SKU_DISTRIBUTION_MAPPING");
	}
	
	public int insert(SkuDistributionMapping skuDistributionMapping){
		return super.insert("insertSelective", skuDistributionMapping);
	}
	
	public int update(SkuDistributionMapping skuDistributionMapping){
		return super.update("updateByPrimaryKeySelective", skuDistributionMapping);
	}
	
	public SkuDistributionMapping findSkuDistributionMappingByParmar(Map<String,Object> parmars){
		return super.get("selectOneByParmas", parmars);
	}
	
	public int updateIsdelete(Map<String,Object> parmars){
		return super.update("updateIsdelete", parmars);
		
	}
}
