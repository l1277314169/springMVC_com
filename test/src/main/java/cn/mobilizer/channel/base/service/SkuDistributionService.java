package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.SkuDistribution;
import cn.mobilizer.channel.exception.BusinessException;

public interface SkuDistributionService {
	/**
	 * 查询列表
	 * @param parmars
	 * @return
	 * @throws BusinessException
	 */
	public List<SkuDistribution> skuDistributionList(Map<String,Object> parmars)throws BusinessException;
	/**
	 * 查询记录数
	 * @param parmars
	 * @return
	 * @throws BusinessException
	 */
	public Integer queryCount(Map<String,Object> parmars)throws BusinessException;
	/**
	 * 新增
	 * @param skuDistribution
	 * @return
	 * @throws BusinessException
	 */
	public int addSkuDistribution(SkuDistribution skuDistribution)throws BusinessException;
	/**
	 * 编辑
	 * @param skuDistribution
	 * @return
	 * @throws BusinessException
	 */
	public int updateSkuDistribution(SkuDistribution skuDistribution)throws BusinessException;
	/**
	 * 查找对象
	 * @throws BusinessException
	 */
	public SkuDistribution findSkuDistributionByParmars(Integer clientId,Integer skuDistributionId)throws BusinessException;
	/**
	 * 删除
	 * @param skuDistribution
	 * @return
	 */
	public int deleteSkuDistribution(Integer clientId,Integer skuDistributionId)throws BusinessException;
	/**
	 * 验证分组名称唯一性
	 * @param clientId
	 * @param groupName
	 * @return
	 * @throws BusinessException
	 */
	public List<SkuDistribution> onlySkuDistribution(Integer clientId,String groupName)throws BusinessException;
}
