package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Distributor;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;



public interface DistributorService extends BasicService<Distributor>{
	
	/**
	 * 查询分页
	 * @return
	 */
	public List<Distributor> queryDistributorList(Map<String, Object> param);

	/**
	 * 获取对象
	 * @param distributorId
	 * @return
	 */
	public Distributor findDistributorByprimaryKey(Integer distributorId);

	/**
	 * 获取总记录数
	 * @param param
	 * @return
	 */
	
	public Integer queryDistributorCount(Map<String, Object> param);
	/**
	 * 
	 * 增加
	 * @return
	 */
	public int addDistributor(Distributor distributor);
	/**
	 * 修改
	 * @param distributor
	 * @return
	 */
	public int updateDistributor(Distributor distributor);
	/**
	 * 删除
	 * @param distributorId
	 * @return
	 */
	public int deleteDistributor(Integer distributorId,Integer clientId);

	/**查询组织结构树信息
	 * @author Nany
	 * 2014年12月17日下午5:17:59
	 * @param clientId
	 * @param id
	 * @return
	 */
	public List<TreeNodeVO> getTreeNodes(int clientId, Integer id, String subStructureId)throws BusinessException;
	
	/**
	 * 根据clientUserId获取该用户的领导
	 * @param clientUserId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public ClientUser getParentClientUser(Integer clientUserId, Integer clientId) throws BusinessException;
	
}
