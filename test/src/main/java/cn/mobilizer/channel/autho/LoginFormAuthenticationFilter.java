package cn.mobilizer.channel.autho;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.service.ClientService;
import cn.mobilizer.channel.base.service.ClientUserExpandService;
import cn.mobilizer.channel.log.po.UserActivityLog;
import cn.mobilizer.channel.log.service.ActivityLogService;
import cn.mobilizer.channel.util.Constants;

public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {
	public static final String CLIENT_NAME_PARAM = "clientName";

	private static final Log log = LogFactory.getLog(LoginFormAuthenticationFilter.class);
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientUserExpandService clientUserExpandService;
	@Autowired
	private ActivityLogService activityLogService;
	
	private String clientNameParam = CLIENT_NAME_PARAM;

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		String clientName = getClientName(request);
		Integer clientId = checkClientName(clientName);
		
		LoginToken token = createToken(request, response, clientId);
		try {
			/*cn.mobilizer.channel.comm.utils.web.WebUtils.	//
					setSession((HttpServletRequest)request, Constants.ON_LOGIN_URL,token.getOnLoginType());*/
			
			if(clientId == -1) {
				throw new LoginAuthenticationException("客户名称错误！");
			}
			
			Subject subject = getSubject(request, response);
			subject.login(token);
			
			ShiroUser user = (ShiroUser) subject.getPrincipal();
//			user.loginType = token.getLoginType();
			if(!user.clientUser.getRoles().contains("user")){
				SecurityUtils.getSubject().logout();
				throw new LoginPermissionException("该用户没有登录权限！");
			}
			/*cn.mobilizer.channel.comm.utils.web.WebUtils.	//
					setSession((HttpServletRequest)request, Constants.SHIRO_USER, user);*/
			
			return onLoginSuccess(token, subject, request, response);
		} catch (LoginPermissionException e) {
			return onLoginFailure(token, e, request, response); 
		} catch (LoginAuthenticationException e) {
			return onLoginFailure(token, e, request, response); 
		} catch (AuthenticationException e) {
			return onLoginFailure(token, e, request, response); 
		}
	}
	
/*
	@Override
	protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception{
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (isLoginRequest (request, response)) {
			if (isLoginSubmission (request, response)) {
				if (log.isTraceEnabled ()) {
					log.trace ("Login submission detected.  Attempting to execute login.");
				}
				if (cn.mobilizer.channel.comm.utils.web.WebUtils.isAjaxRequest (httpRequest)) {
					cn.mobilizer.channel.comm.utils.web.WebUtils.sendAjaxMsg ("您尚未登录或登录时间过长,请重新登录!", httpRequest, httpResponse);
				} else {
					return executeLogin (request, response);
				}
			} else {
				if (log.isTraceEnabled ()) {
					log.trace ("Login page view.");
				}
				// allow them to see the login page ;)
				if (cn.mobilizer.channel.comm.utils.web.WebUtils.isAjaxRequest (httpRequest)) {
					cn.mobilizer.channel.comm.utils.web.WebUtils.sendAjaxMsg ("您尚未登录或登录时间过长,请重新登录!", httpRequest, httpResponse);
				} else {
					return true;
				}
				return true;
			}
		} else {
			if (log.isTraceEnabled ()) {
				log.trace ("Attempting to access a path which requires authentication.  Forwarding to the " + "Authentication url [" + getLoginUrl () + "]");
			}
			if (cn.mobilizer.channel.comm.utils.web.WebUtils.isAjaxRequest (httpRequest)) {
				cn.mobilizer.channel.comm.utils.web.WebUtils.sendAjaxMsg ("您尚未登录或登录时间过长,请重新登录!", httpRequest, httpResponse);
			} else {
				saveRequestAndRedirectToLogin (request, response);
				return false;
			}
		}
		return false;
	}*/

	/**
	 * <pre>
	 * 		根据 clientName 查询  Client
	 * 		如果有 Client 返回 Client 编号
	 * 		没有返回 -1
	 * 
	 * </pre>
	 * @param clientName
	 * @return
	 */
	private Integer checkClientName(String clientName){
//		Client client = clientDao.findByClientName(clientName);
		Client client = clientService.findByClientName(clientName);
		if(client != null)
			return client.getClientId();
		else
			return -1;
		
	}

	/**
	 * 填充 LoginToken   重新创建
	 * 		return new LoginToken();  
	 * 	<pre>
	 * 		username		用户名
	 * 		password		密码
	 * 		clientName		客户名
	 * 		rememberMe		
	 * 		host			IP 地址
	 * </pre>
	 */
	@Override
	protected LoginToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String clientName = getClientName(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		LoginToken loginToken = new LoginToken(username, password.toCharArray(), rememberMe, host, clientName);
		try {
			loginToken.setLoginType(request.getParameter("loginType"));
			loginToken.setOnLoginType(request.getParameter("onLoginType"));
		} catch (Exception e) {
		}
		return loginToken;
	}
	
	protected LoginToken createToken(ServletRequest request, ServletResponse response, Integer clientId) {
		String username = getUsername(request);
		String password = getPassword(request);
		String clientName = getClientName(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		LoginToken loginToken = new LoginToken(username, password.toCharArray(), rememberMe, host, clientName, clientId);
		try {
			loginToken.setLoginType(request.getParameter("loginType"));
			loginToken.setOnLoginType(request.getParameter("onLoginType"));
		} catch (Exception e) {
		}
		return loginToken;
	}
	
	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {

		super.setFailureAttribute(request, ae);
	}

	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
        issueSuccessRedirect(request, response);
        UserActivityLog log = new UserActivityLog();
        
        ShiroUser user = (ShiroUser) subject.getPrincipal();
		log.setLogType(Constants.LOG_TYPE_WEB);
		log.setLogContent("登录使用的clientName:"+user.clientName+";登录使用的username:"+user.loginName+";");
		log.setClientId(user.clientId);
		log.setClientUserId(user.clientUser.getClientUserId());
		log.setResponseCode("Success");
		Date lastLoginTime = null;
		if(user.clientUser.getClientUserExpandId() != null && user.clientUser.getLastLoginTime() != null) {
			lastLoginTime = new Date();
		}
		clientUserExpandService.updateUserLoginInfo(user.id, null, lastLoginTime, null, user.clientId);
		activityLogService.insert(log);
        //we handled the success redirect directly, prevent the chain from continuing:
//        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
//        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) || request.getParameter("ajax") == null) {// 不是ajax请求
//            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
//        } else {
//            httpServletRequest.getRequestDispatcher("/login/timeout/success").forward(httpServletRequest, httpServletResponse);
//        }
        return false;
        
    }
	
	public String getClientNameParam(){
		return clientNameParam;
	}
	
	public void setClientNameParam(String clientNameParam){
		this.clientNameParam = clientNameParam;
	}

	protected String getClientName(ServletRequest request) {
        return WebUtils.getCleanParam(request, getClientNameParam());
    }
}
