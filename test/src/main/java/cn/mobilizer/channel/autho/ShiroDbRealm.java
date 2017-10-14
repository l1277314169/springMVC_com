/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package cn.mobilizer.channel.autho;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.autho.service.MemCacheManagerService;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.vo.LeftMenuVO;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.Encodes;
import cn.mobilizer.channel.comm.utils.MemcachedUtil;
import cn.mobilizer.channel.comm.utils.SpringContextHolder;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.MemcachedEnum;
import cn.mobilizer.channel.systemManager.service.ClientPrivilegesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesService;
import cn.mobilizer.channel.util.Constants;

import com.google.common.base.Objects;

public class ShiroDbRealm extends AuthorizingRealm {
	private static final Log log = LogFactory.getLog(ShiroDbRealm.class);
	
	@Autowired
	protected ClientUserService clientUserService;
	@Autowired
	protected ClientPrivilegesService clientPrivilegesService;
	@Autowired
	protected ClientRolesService clientRolesService;
	@Autowired
	protected ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	protected ChannelCommService channelCommService;
	@Autowired
	protected MemCacheManagerService memCacheManagerService;


	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		LoginToken token = (LoginToken) authcToken;
		String userName = token.getUsername();
		String clientName = token.getClientName();
		
		userName = userName !=null ? userName.substring(0, userName.lastIndexOf(ShiroConstants.CONNECT_STR)) : null;
		if(StringUtil.isEmptyString(userName) || StringUtil.isEmptyString(clientName)){
			return null;
		}
		
		ClientUser user = clientUserService.findByLoginNameAndClientName(userName, clientName);
		if (user != null) {
			List<LeftMenuVO> menu = clientPrivilegesService.getMenusByClientUserId(user.getClientUserId());
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(new ShiroUser(user.getClientUserId(), user.getLoginName(), user.getName(), token.getClientId(), token.getClientName(),user,menu),
					user.getLoginPwd(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/**获取用户信息**/
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
//		ClientUser user = clientUserService.findByLoginName(shiroUser.loginName, shiroUser.clientId);
		ClientUser user= shiroUser.clientUser;
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if(user != null) {
			/**
			 * 获取用户角色信息
			 * 加上用户本身的角色-用户表的roles字段
			**/
//			List<String> roles = clientRolesService.getUserRolesByClientUserId(shiroUser.id);
//			roles.add ("testRol");
//			roles.addAll (user.getRoleList()); 
			
			List<String> permissions = new ArrayList<String>();
			/**获取用户web权限信息**/
			List<String> permissionsWeb = clientPrivilegesService.getUserPermissionsByClientUserId(shiroUser.id);
			/**获取用户数据权限信息**/
			List<String> permissionsData = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId(shiroUser.id);
			/**本部门数据权限**/
			String subStructrueIds  = "" ;
			/**判断是否有为0的数据权限，为0表示本部门权限**/
			for ( String string : permissionsData ) {
				if(string.equals(Constants.SELF_STRUCTURE_ID.toString())){
					subStructrueIds = channelCommService.getSubStructrueIds(user.getClientStructureId());
					break;
				}
			}
			/**合并重新赋值**/
			permissionsData = mergeDataPermissions(permissionsData, subStructrueIds);
			subStructrueIds = "";
			for(Iterator<String> it = permissionsData.iterator();it.hasNext();){
				if(!it.hasNext()) {
					subStructrueIds += it.next ();
				} else {
					subStructrueIds += it.next () + ",";
				}
			}
			/**把数据权限存入ShiroUser中方便使用**/
			shiroUser.clientUser.setPermissionsData(subStructrueIds);
			/**把数据权限存入Memcached中备份**/
			String key = MemcachedEnum.ClientUserPerData.getKey()+shiroUser.id;
			int sec = MemcachedEnum.ClientUserPerData.getSec();
			//如果Memcached中有值替换掉，确保每次登陆都为最新的权限。
			String str = MemcachedUtil.getInstance().get(key) == null? "": (String) MemcachedUtil.getInstance().get(key);
			if(StringUtil.isNotEmptyString(str)){
				MemcachedUtil.getInstance().replace(key, sec, subStructrueIds);
			} else {
				MemcachedUtil.getInstance().set(key, sec, subStructrueIds);
			}
			
			permissions.addAll(permissionsWeb);
			permissions.addAll(permissionsData);
			permissions.add ("testPer");//注意，该权限用作系统内的权限认证，不可以删除，否则有可能会导致登录失败，或者循环登录的情况。
			
			info.addRoles(user.getRoleList());
			info.addStringPermissions (permissions);
		}
		
		/**把信息放入Mem**/
		String name = getAuthorizationCacheName();
		Object key = getAuthorizationCacheKey(principals);
		if(key == null || StringUtil.isEmptyString(name)) {
			log.error("principals:"+principals+"获取key 或者getAuthorizationCacheName:"+name+"为空");
			return info;
		}
		Map<Object, Object> backingMap = new HashMap<Object,Object>();
		backingMap.put(key, info);
		MemCache<Object, Object> cache = new MemCache<Object, Object>(name, backingMap);
		MemCache<Object, Object> mem_cache = (MemCache<Object, Object>) memCacheManagerService.getCache(name);
		if(mem_cache != null){
			memCacheManagerService.updateCahce(name, cache);
		} else {
			memCacheManagerService.createCache(name, cache);
		}
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ShiroConstants.HASH_ALGORITHM);
		matcher.setHashIterations(ShiroConstants.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}


	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		
		private static final long serialVersionUID = -1373760761780840081L;
		public Integer id;
		public String loginName;
		public String name;
		public Integer clientId;
		public String clientName;
		public ClientUser clientUser;
//		public String loginType; //登录成功后返回地址
//		public String onLoginType; //错误返回的URL
		public List<LeftMenuVO> leftMenuVO;

		public ShiroUser(Integer id, String loginName, String name, Integer clientId, String clientName,ClientUser clientUser,List<LeftMenuVO> leftMenuVO) {
			this.id = id;
//			this.loginName = loginName;
			this.loginName = loginName+ShiroConstants.CONNECT_STR+clientId;
			this.name = name;
			this.clientId = clientId;
			this.clientName = clientName;
			this.clientUser = clientUser;
			this.leftMenuVO = leftMenuVO;
		}
		
		public ShiroUser(Integer id, String loginName,Integer clientId) {
			this.id = id;
//			this.loginName = loginName;
			this.loginName = loginName+ShiroConstants.CONNECT_STR+clientId;
			this.clientId = clientId;
		}
		
		public ShiroUser(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principals){
		super.clearCachedAuthorizationInfo(principals);
	}
	
	
	/**
	 * 合并权限
	 * @param arr1
	 * @param arr2 ","号隔开的字符串
	 * @return 返回Lsit<String>
	 */
	protected List<String> mergeDataPermissions(List<String> arr1,String arr2){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		List<String> list = new ArrayList<String>();
		if(arr1 !=null && arr1.size() >0 ) {
			for ( String str : arr1 ) {
				map.put(str, Boolean.TRUE);
			}
		}
		if(arr2 != null && arr2 != "") {
			String tmp_str[] =  arr2.split(",");
			for ( String str : tmp_str ) {
				map.put(str, Boolean.TRUE);
			}
		}
		for ( Entry<String, Boolean> e : map.entrySet() ) {
			list.add(e.getKey());
		}
		
		return list;
	}
}
