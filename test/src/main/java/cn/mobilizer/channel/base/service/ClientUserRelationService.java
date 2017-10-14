package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.ClientUserRelation;
import cn.mobilizer.channel.exception.BusinessException;

public interface ClientUserRelationService {
	/**
	 * 
	 * @return
	 */
	public String findChildClientUserByParentId(Integer parentId)throws BusinessException;
	/**
	 * 
	 * @param parames
	 * @return
	 * @throws BusinessException
	 */
	public ClientUserRelation getParentByClientUserId(Map<String,Object> parames)throws BusinessException;
	/**
	 * 
	 * @param clientUserRelation
	 * @return
	 * @throws BusinessException
	 */
	public int updateClientUserByParent(ClientUserRelation clientUserRelation)throws BusinessException;
	
	/**
	 * 
	 * @param clientId
	 * @param clientUserId 当前上一级
	 * @param clientUserId2 待更新上一级
	 * @param hiddenClientUserIds 选中的所有下级
	 * @throws BusinessException
	 */
	public void updateParentClientUser(Integer clientId,Integer clientUserId,Integer clientUserId2,String hiddenClientUserIds)throws BusinessException;
	/**
	 * 
	 * @param parames
	 * @return
	 * @throws BusinessException
	 */
	public ClientUserRelation findchildByParentId(Map<String,Object> parames)throws BusinessException;
	/**
	 * 获取直接下级 通过parentId 和 clientId
	 * @param parames
	 * @return
	 * @throws BusinessException
	 */
	public String getDirectChilds(Map<String,Object> parames)throws BusinessException;
 
}
