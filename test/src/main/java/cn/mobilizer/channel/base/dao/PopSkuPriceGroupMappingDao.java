package cn.mobilizer.channel.base.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.PopSkuPriceGroupMapping;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class PopSkuPriceGroupMappingDao extends MyBatisDao{
  public PopSkuPriceGroupMappingDao(){
	  super("POP_SKU_PRICE_GROUP_MAPPING");
	  
  }
  
  public int insert(PopSkuPriceGroupMapping popSkuPriceGroupMapping){
	 return  super.insert("insertSelective", popSkuPriceGroupMapping);
  }
  
  public int update(PopSkuPriceGroupMapping popSkuPriceGroupMapping){
	  
	  return super.update("updateByPrimaryKeySelective", popSkuPriceGroupMapping);
  }
  

}
