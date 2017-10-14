package cn.mobilizer.channel.base.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.SkuDistributionPopMapping;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class SkuDistributionPopMappingDao extends MyBatisDao {
	public SkuDistributionPopMappingDao(){
		super("SKU_DISTRIBUTION_POP_MAPPING");
	}
	
	public int insert(SkuDistributionPopMapping skuDistributionPopMapping){
		return super.insert("insertSelective", skuDistributionPopMapping);
	}
	
	public int update(SkuDistributionPopMapping skuDistributionPopMapping){
		return super.update("updateBySkuDistributionId", skuDistributionPopMapping);
	}
	
	
}
