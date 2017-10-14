package cn.mobilizer.channel.base.service;

import java.util.Date;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.ClientUserExpand;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;
/**
 * @author yeeda
 *
 */
public interface ClientUserExpandService extends BasicService<ClientUserExpand>{
	
	/**
	 * 修改最后登陆时间
	 * @param clientUserId
	 * @param loginToken
	 * @param lastLoginTime
	 * @param msgPushToken
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public int updateUserLoginInfo(Integer clientUserId, String loginToken, Date lastLoginTime, String msgPushToken, Integer clientId) throws BusinessException;
	
	/**
	 * 
	 * @param clientUserId
	 * @return
	 */
	public int updateUserLastLoginTime(Integer clientUserId, Date lastLoginTime);
	
	public ClientUserExpand findByPrimaryKey(Integer clientUserExpandId) throws BusinessException;
}
