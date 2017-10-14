package cn.mobilizer.channel.autho.service;


public interface AuthService {
	/**
	 * 
	 * @param clientUserId
	 */
	public void cleanAuth(Integer clientUserId);
	
	/**
	 * 清除某个用户的权限信息-重新加载用户权限缓存
	 * @param id
	 */
	public void clearCachedAuthorizationInfo(Integer id);
}
