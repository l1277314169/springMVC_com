package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.PromotionMaterial;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface PromotionMaterialService {

	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 */
	public Integer queryPromotionMaterialCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<PromotionMaterial> queryPromotionMaterialList(Map<String, Object> searchParams) throws BusinessException;
	
	
	public PromotionMaterial selectByPrimaryKey(Integer materialId) throws BusinessException;
	
	public PromotionMaterial getPromotionMaterialByIdAndClientId(Integer clientId,Integer materialId) throws BusinessException;
	
}
