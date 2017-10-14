package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.SkuDistribution;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class SkuDistributionDao extends MyBatisDao{
	public SkuDistributionDao() {
		super("SKU_DISTRIBUTION");
	}
	
	public int insert(SkuDistribution skuDistribution){
		super.insert("insertSelective", skuDistribution);
		return skuDistribution.getSkuDistributionId();
		
	}
	
	public int update(SkuDistribution skuDistribution){
		return super.update("updateByPrimaryKeySelective", skuDistribution);
		
	}
	
	public Integer getCount(Map<String, Object> parameters){
		return super.get("queryCount", parameters);
	}
	
	public List<SkuDistribution> queryByParmas(Map<String,Object> parameter){
		return super.queryForList("selectlistByMap", parameter);
	}
	
	public SkuDistribution findSkuDistributionById(Map<String,Object> parameter){
		return super.get("findSkuDistribution", parameter);
	}
	
	public int updateIsdelte(Map<String,Object> parameter){
		return super.update("updateIsdelte", parameter);
	}
	
	public List<SkuDistribution> findSkuDistributionGroupName(Map<String,Object> parameter){
		return super.queryForList("findSkuDistributionGroupName", parameter);
	}
}
