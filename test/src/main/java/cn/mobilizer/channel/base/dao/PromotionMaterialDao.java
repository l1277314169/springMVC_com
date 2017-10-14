/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.PromotionMaterial;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class PromotionMaterialDao extends MyBatisDao {
	
	public PromotionMaterialDao() {
		super("PROMOTION_MATERIAL");
	}
	

	public Integer queryPromotionMaterialCount(Map<String, Object> params){
		return super.get("queryTotalCount", params);
	}
	
	public List<PromotionMaterial> queryPromotionMaterialList (Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}
	 
	public PromotionMaterial selectByPrimaryKey(Integer materialId){
		return super.get("selectByPrimaryKey", materialId);
	}
	
	public PromotionMaterial getPromotionMaterialByIdAndClientId(Map<String, Object> params){
		return super.get("getPromotionMaterialByIdAndClientId", params);
	}
}
