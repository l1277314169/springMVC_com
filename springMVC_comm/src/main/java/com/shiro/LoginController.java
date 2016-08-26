package com.shiro;

import com.po.ActiveUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhonger on 2016/7/14.
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request) throws Exception{
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        //根据shiro返回的异常类路径判断，抛出指定异常信息
        String message = "";
        if(exceptionClassName!=null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                //最终会抛给异常处理器
                message="账号不存在";
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                message="密码错误";
            } else if("randomCodeError".equals(exceptionClassName)){
                message="验证码错误 ";
            }else if (ExcessiveAttemptsException.class.getName().equals(
                    exceptionClassName)){
                message="重复密码超次";
            }else {
                throw new Exception();//最终在异常处理器生成未知错误
            }
        }
        request.setAttribute("errorMessage",message);
        return "login";
    }
    @RequestMapping(value = "/welcom")
    public String index(Model model){
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        model.addAttribute("activeUser", activeUser);
        return "index";
    }

}
