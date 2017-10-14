package cn.mobilizer.channel.autho.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.autho.ShiroDbRealm;
import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.autho.service.AuthService;
import cn.mobilizer.channel.comm.utils.SpringContextHolder;

@Service
public class AuthServiceImpl implements AuthService{
	private static final Log log = LogFactory.getLog(AuthServiceImpl.class);
	
	@Resource
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
	
	@Override
	public synchronized void cleanAuth(Integer clientUserId){
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
		} catch (Exception e) {
			log.error("getShiroFilter from shiroFilterFactoryBean error!", e);
			throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
		}
		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
		manager.getFilterChains().clear();
		log.info("清空用户:" + clientUserId + "的权限控制");
	}
	
	@Override
	public void clearCachedAuthorizationInfo(Integer id){
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		ShiroDbRealm realm = (ShiroDbRealm)securityManager.getRealms().iterator().next();
		realm.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
		realm.isPermitted(SecurityUtils.getSubject().getPrincipals(), "testPer");
		log.info("重新加载用户："+id+"的权限");
	}
}
