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
        //����shiro���ص��쳣��·���жϣ��׳�ָ���쳣��Ϣ
        String message = "";
        if(exceptionClassName!=null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                //���ջ��׸��쳣������
                message="�˺Ų�����";
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                message="�������";
            } else if("randomCodeError".equals(exceptionClassName)){
                message="��֤����� ";
            }else if (ExcessiveAttemptsException.class.getName().equals(
                    exceptionClassName)){
                message="�ظ����볬��";
            }else {
                throw new Exception();//�������쳣����������δ֪����
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
