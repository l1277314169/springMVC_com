package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.SkuPrice;
import cn.mobilizer.channel.base.po.SkuPriceGroup;
import cn.mobilizer.channel.base.vo.SkuPriceVO;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author honger.liu
 *
 */
public interface SkuPriceService extends BasicService<SkuPrice>{
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<SkuPrice> findSkuPriceByClientId(Integer clientId) throws BusinessException;
	
	/**
	 * 查询组织结构树信息
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<TreeNodeVO> getTreeNodes(Integer skuPriceId, Integer id) throws BusinessException;

	
	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public Integer querySkuPriceCount(Map<String, Object> searchParams) throws BusinessException;
	
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<SkuPrice> querySkuPriceList(Map<String, Object> searchParams) throws BusinessException;
	/**
	 * 增加
	 * @param skuPrice
	 * @return
	 */
	public int addSkuPrice(SkuPrice skuPrice);
	/**
	 * 修改
	 * @param skuPrice
	 * @return
	 */
	public int updateSkuPrice(SkuPrice skuPrice);
	/**
	 * 删除
	 * @param skuPriceId
	 * @return
	 */
	public int deleteSkuPrice(Integer skuPriceId);
	/**
	 * 获取产品价格对象
	 * @param skuPriceId
	 * @return
	 */
	public SkuPriceGroup findByPrimaryKey(Integer skuPriceId);

	public SkuPrice skuPriceList(Integer skuPriceGroupId,Integer clientId);
	/**
	 * skuId和price组合
	 * @param parmarter
	 * @return
	 */
	public List<SkuPriceVO> skuPriceVOList(Map<String,Object> parmarter);
}
