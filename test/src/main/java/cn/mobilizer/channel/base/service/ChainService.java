package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface ChainService extends BasicService<Chain>{

	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public Integer queryChainCount(Map<String, Object> searchParams) throws BusinessException;
	
	
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Chain> queryChainList(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public List<Chain> queryChainList(Integer clientId) throws BusinessException;

	/**
	 * 增加
	 * @param chain
	 * @return
	 */
	public int addChain(Chain chain);
	/**
	 * 修改
	 * @param chain
	 * @return
	 */
	public int update(Chain chain);
	/**
	 * 删除
	 * @param chainId
	 * @return
	 */
	public int deleteChain(Integer chainId);
	

	/**
	 * 获取连锁对象
	 * @param chainId
	 * @return
	 */
	public Chain findByPrimaryKey(Integer chainId);
	
	/**
	 * 上级连锁
	 * @param Params
	 * @return
	 * @throws BusinessException
	 */
	public List<Chain> findChainName(Object Params)throws BusinessException;
	
	
	public List<TreeNodeVO> getTreeNodes(Integer clientId,Integer id,Integer channelId) throws BusinessException;

	public Chain getChainByChainId(Integer chainId, Integer clientId)throws BusinessException;
	
	/**
	 * 连锁查看
	 * @param chainId
	 * @return
	 * @throws BusinessException
	 */
	public Chain findChainRelateChannel(Integer chainId)throws BusinessException;
	
	/**
	 * 渠道全名称匹配
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<Chain> findChainByChainName(Map<String,Object> params) throws BusinessException;

	public Map<String,Chain> queryChainByclientId(Integer id) throws BusinessException;


}

