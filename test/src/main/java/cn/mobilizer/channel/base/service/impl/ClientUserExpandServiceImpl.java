/**
 * 
 */
package cn.mobilizer.channel.base.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientUserExpandDao;
import cn.mobilizer.channel.base.po.ClientUserExpand;
import cn.mobilizer.channel.base.service.ClientUserExpandService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;


/**
 * 用户业务处理
 */

@Service
public class ClientUserExpandServiceImpl implements ClientUserExpandService {

	private static final Log LOG = LogFactory.getLog(ClientUserExpandServiceImpl.class);
	@Autowired
	private ClientUserExpandDao clientUserExpandDao;
	@Override
	public int updateUserLoginInfo(Integer clientUserId, String loginToken, Date lastLoginTime, String msgPushToken, Integer clientId) throws BusinessException{
		Integer i = 0;
		try {
			ClientUserExpand clientUserExpand =  clientUserExpandDao.selectByPrimaryKey(clientUserId);
			if(clientUserExpand != null){
				clientUserExpand.setLoginToken(loginToken);
				clientUserExpand.setLastLoginTime(lastLoginTime);
				clientUserExpand.setMsgPushToken(msgPushToken);
				clientUserExpand.setLastUpdateTime(null);
				i = clientUserExpandDao.update(clientUserExpand);
			} else {
				clientUserExpand = new ClientUserExpand();
				clientUserExpand.setClientUserId(clientUserId);
				clientUserExpand.setLoginToken(loginToken);
				clientUserExpand.setLastLoginTime(lastLoginTime);
				clientUserExpand.setMsgPushToken(msgPushToken);
				clientUserExpand.setClientId(clientId);
				i = clientUserExpandDao.insert(clientUserExpand);
			}
		} catch (Exception e) {
			LOG.error("method updateUserLoginInfo error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		return i;
	}
	
	@Override
	public ClientUserExpand findByPrimaryKey(Integer clientUserExpandId)
			throws BusinessException {
		return clientUserExpandDao.selectByPrimaryKey(clientUserExpandId);
	}

	@Override
	public List<ClientUserExpand> getObjectList(Map<String, Object> parames){
		return null;
	}

	@Override
	public ClientUserExpand getObject(Map<String, Object> parames){
		return null;
	}

	@Override
	public int updateUserLastLoginTime(Integer clientUserId, Date lastLoginTime){
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientUserId", clientUserId);
			paramMap.put("lastLoginTime", lastLoginTime);
			return clientUserExpandDao.updateLastLoginTimeByclientUserId(paramMap);
		} catch (BusinessException e) {
			LOG.error("method updateUserLastLoginTime error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
	}

}
