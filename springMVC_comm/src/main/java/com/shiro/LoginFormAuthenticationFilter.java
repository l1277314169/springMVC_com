package com.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuhonger on 2016/7/14.
 */
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        boolean contextRelative = true;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String successUrl = this.getSuccessUrl();
        if("".equals(successUrl)){
            successUrl = DEFAULT_SUCCESS_URL;
        }
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + successUrl);    //页面跳转
        return false;
    }
}
