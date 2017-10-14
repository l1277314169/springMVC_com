package cn.mobilizer.channel.posm.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.posm.po.PromotionMaterial;

public interface PromotionMaterialXService {
	/**
	 * 添加
	 * @param promotionMaterial
	 */
	public void addPromotionMaterial(PromotionMaterial promotionMaterial);
	
	/**
	 * 修改
	 * @param promotionMaterial
	 */
	public void updatePromotionMaterial(PromotionMaterial promotionMaterial);
	
	/**
	 * 删除
	 * @param materialId
	 */
	public void delPromotionMaterial(Integer materialId);
	
	/**
	 * 根据Id进行查找
	 * @param materialId
	 * @return
	 */
	public PromotionMaterial queryPromotionMaterialById(Integer materialId);
	
	/**
	 * 
	 * @param param
	 * @return 总条数
	 */
	public Integer queryPromotionMaterialByCount(Map<String,Object> param);
	
	public List<PromotionMaterial> queryPromotionMaterialList(Map<String,Object> param);
	
	public List<PromotionMaterial> queryPromotionMaterialByNames();
	
	/**
	 * 批量导入促销物料数据
	 * @param promotionMaterial
	 */
	public void batchSavePromotionMaterial(List<PromotionMaterial> promotionMaterial);
	
	public List<PromotionMaterial> batchOutPromotionMaterial(Map<String,Object> param);
	
	/**
	 * 根据物料名称模糊查询	
	 * @param clinteId	客户编号	
	 * @param name		物料名称
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月9日
	 */															
	public List<PromotionMaterial> queryPmListByClinteIdAndLikeName(Integer clinteId , String name);

}
