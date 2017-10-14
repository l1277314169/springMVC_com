package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author honger.liu
 *
 */
public interface ChannelService extends BasicService<Channel>{

	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Channel> findChannelsByClientId(Integer clientId) throws BusinessException;
	
	/**
	 * 查询组织结构树信息
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<TreeNodeVO> getTreeNodes(Integer clientId, Integer id) throws BusinessException;

	
	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public Integer queryChannelCount(Map<String, Object> searchParams) throws BusinessException;
	
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Channel> queryChannelList(Map<String, Object> searchParams) throws BusinessException;
	/**
	 * 增加
	 * @param chain
	 * @return
	 */
	public int addChannel(Channel channel);
	/**
	 * 修改
	 * @param chain
	 * @return
	 */
	public int update(Channel channel);
	/**
	 * 删除
	 * @param chainId
	 * @return
	 */
	public int deleteChannel(Integer channelId);
	/**
	 * 获取渠道对象
	 * @param chainId
	 * @return
	 */
	public Channel findByPrimaryKey(Integer channelId);
	/**
	 * 上级渠道
	 * @param Params
	 * @return
	 * @throws BusinessException
	 */
	public List<Channel> findChannelName(Object Params)throws BusinessException;
	
 	/**
 	 * 获取所有下级渠道
 	 * @param channelId
 	 * @return
 	 * @throws BusinessException
 	 */
	public String findChannelChild(Integer channelId)throws BusinessException;
	
	/**
	 * 全名称匹配渠道
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Channel> findChannelByChannelName(Map<String, Object> params)throws BusinessException;
	
	/**
	 * 全名称匹配一级渠道
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Channel> findFirstChannelByChannelName(Map<String, Object> params)throws BusinessException;
	
	/**
	 * 根据parentId与渠道全名称匹配
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<Channel> findChannelByChannelNameAndParentId(Map<String, Object> params) throws BusinessException;
	
	public Map<String,Channel> queryChannelByclientId(Integer id) throws BusinessException;

}
