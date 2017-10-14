package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientUserRelationDao;
import cn.mobilizer.channel.base.po.ClientUserRelation;
import cn.mobilizer.channel.base.service.ClientUserRelationService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
@Service
public class ClientUserRelationServiceImpl implements ClientUserRelationService{
	@Autowired
	private ClientUserRelationDao clientUserRelationDao;
	@Override
	public String findChildClientUserByParentId(Integer parentId) throws BusinessException {
		
		return clientUserRelationDao.queryClientUserByParentId(parentId);
	}
	@Override
	public ClientUserRelation getParentByClientUserId(Map<String,Object> parames) throws BusinessException {
		
		return clientUserRelationDao.getClientUserRelation(parames);
	}
	@Override
	public int updateClientUserByParent(ClientUserRelation clientUserRelation) throws BusinessException {
		
		return clientUserRelationDao.updateClientUserRelation(clientUserRelation);
	}
	@Override
	public void updateParentClientUser(Integer clientId,Integer clientUserId,Integer clientUserId2,String hiddenClientUserIds) throws BusinessException {
		//首先当前上级全部删除
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("clientUserId", clientUserId);
		parameters.put("hiddenClientUserIds", hiddenClientUserIds);
		clientUserRelationDao.updateAllIsdelete(parameters);
		//然后如果原来的删除再找回否则就新增
		String[] str=(hiddenClientUserIds != null && !hiddenClientUserIds.equals(""))?hiddenClientUserIds.split(","):null;
		if(str != null){
			for (int i = 0; i < str.length; i++) {
				/**通过用户 str[i] 查找出他的所有下一级
				 * clientUserId2 不能在 str[i]的下一级出现
				 * --避免上下级出现间接循环
				 * **/
				String clientUserChil = clientUserRelationDao.getSubordinatesWithOutSelf(str[i]);
				String[] subordinates = (clientUserChil == null || clientUserChil.equals(""))?null:clientUserChil.split(",");
				boolean flag = false;
				if(subordinates != null){
					for (int j = 0; j < subordinates.length; j++) {
						if(clientUserId2.equals(Integer.parseInt(subordinates[j]))){
							parameters.put("clientUserIds", str[i]);
							ClientUserRelation clientUserRelation = clientUserRelationDao.findClientUserRelationList(parameters).get(0);
							if(clientUserRelation != null){
								clientUserRelation.setIsDelete(Constants.IS_DELETE_FALSE);
								clientUserRelationDao.updateClientUserRelation(clientUserRelation);
							}
							flag = true;
							break;
						}
					}
				}
				if(clientUserId2 != Integer.parseInt(str[i]) && !flag){
					parameters.put("parentId", clientUserId2);
					parameters.put("clientUserIds", str[i]);
					ClientUserRelation clientUserRelation = clientUserRelationDao.findClientUserRelation(parameters);
					if(clientUserRelation != null){
						clientUserRelation.setIsDelete(Constants.IS_DELETE_FALSE);
						clientUserRelationDao.updateClientUserRelation(clientUserRelation);
					}else{
						ClientUserRelation newclientUserRelation = new ClientUserRelation();
						newclientUserRelation.setClientUserId(Integer.parseInt(str[i]));
						newclientUserRelation.setParentId(clientUserId2);
						newclientUserRelation.setClientId(clientId);
						clientUserRelationDao.insertSelective(newclientUserRelation);
					}
				}
			}
		}
	}
	@Override
	public ClientUserRelation findchildByParentId(Map<String, Object> parames) throws BusinessException {
		
		return clientUserRelationDao.findchildByParentId(parames);
	}
	@Override
	public String getDirectChilds(Map<String, Object> parames) throws BusinessException {
		
		return clientUserRelationDao.findDirectChilds(parames);
	}

	
	
	
}
