package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;




import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.SkuPrice;
import cn.mobilizer.channel.base.po.SkuPriceGroup;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class SkuPriceGroupDao extends MyBatisDao {
  public SkuPriceGroupDao(){
	  super("SKU_PRICE_GROUP");
  }
  
  public List<SkuPriceGroup> querySkuPriceGroupList (Map<String, Object> params) {
		return super.queryForList("selectByParams", params);
	}
  
  public Integer insert(SkuPriceGroup skuPriceGroup){
	  super.insert("insertSelective", skuPriceGroup);
	  return skuPriceGroup.getSkuPriceGroupId();
  }
  
  public SkuPriceGroup findSkuPriceGroupById(int id){
		return super.get ("selectByPrimaryKeyId", id);
	}
   
  public List<SkuPriceGroup>   findSkuIdListBySkuPriceGroup(Map<String, Object> params){
	  return super.queryForList("selectByPrimaryKeyIdSkuList", params);
  };
  
  public Integer update(SkuPriceGroup skuPriceGroup){
	  return super.update("updateByPrimaryKeySelective", skuPriceGroup);
  }
  
  public Integer getCount(Map<String, Object> params){
	 return super.get("getCount", params);
  }
  
  public SkuPriceGroup findSerlctOneSkuPrice(Map<String, Object> params){
	  return super.get("findSerlctOneSkuPrice", params);
  }
  
  public SkuPriceGroup findSkuGroupName(Map<String, Object> params){
	  return super.get("findSkuGroupName", params);
  }
}
