package cn.mobilizer.channel.colgate.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.mobilizer.channel.autho.LoginAuthenticationException;
import cn.mobilizer.channel.autho.LoginPermissionException;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.utils.web.WebUtils;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.log.po.UserActivityLog;
import cn.mobilizer.channel.log.service.ActivityLogService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.PasswordHelper;

@Controller
@RequestMapping(value = "/colgateZh")
public class ColgateZhController extends BaseActionSupport{
	
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -5811800686567454716L;
//	
//	@Autowired
//	private ActivityLogService activityLogService;
//
//	
//	
//	
//	@RequestMapping(value = "glogin")
//	public String login(String username, String clientName, Model model,HttpServletRequest resquest){
//		ClientUser clientUser = getClientUser();
//		
//		if(clientUser != null) {
////			model.addAttribute("loginName", user.loginName);
//			if(clientUser.getLastLoginTime() == null && PasswordHelper.checkPasswd(clientUser.getLoginPwd(), clientUser.getSalt(), "8888")) {
//				return "/updateClientUserPassword";
//			}
//			return "redirect:/welcome";
////			return "/welcome";
//		} else {
//			if(resquest.getRequestURL().indexOf("mg.") != -1){
//				return "/mg_login";
//			}else{
//				if(resquest.getRequestURL().toString().contains("loreal.")){
//					model.addAttribute("clientTag", "loreal");
//				}
//				return "/login";
//			}
//		}
//	}
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public String login(HttpServletRequest req, Model model) throws Exception {
//		ClientUser clientUser = getClientUser();
//		
//		if(clientUser != null) {
////			model.addAttribute("loginName", user.loginName);
//			if(clientUser.getLastLoginTime() == null && PasswordHelper.checkPasswd(clientUser.getLoginPwd(), clientUser.getSalt(), "8888")) {
//				return "/updateClientUserPassword";
//			}
//				return "redirect:/welcome";
////			return "/welcome";
//		} else {
//			if(req.getRequestURL().indexOf("mg.") != -1){
//				return "/mg_login";
//			}else{
//				if(req.getRequestURL().toString().contains("loreal.")){
//					model.addAttribute("clientTag", "loreal");
//				}
//				return "/login";
//			}
//		}
//	}
//	
//	
//	
//	/**
//	 * 登录失败处理
//	 * @param username
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(method = RequestMethod.POST)
//	public String fail(String username, String clientName, Model model,HttpServletRequest req) {
//		
//		UserActivityLog log = new UserActivityLog();
//		log.setLogType(Constants.LOG_TYPE_WEB);
//		log.setLogContent("登录使用的clientName:"+clientName+";登录使用的username:"+username+";");
//		
//		String loginError = null;
//		Object error = (Object) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
//		if(error != null){
//			if(error.equals (LoginAuthenticationException.class.getName())){
//				loginError = ErrorCodeMsg.CLIENT_NAME;
//				log.setResponseCode("Failed-客户名称错误！");
//			} else if (error.equals (LoginPermissionException.class.getName())){
//				loginError = ErrorCodeMsg.LOGIN_PERMISSION;
//				log.setResponseCode("Failed-没有登录权限！");
//			} else {
//				loginError = ErrorCodeMsg.USERNAME_OR_PASSWORD;
//				log.setResponseCode("Failed-登录名或者密码错误！");
//			}
//		}else {
//			if(getShiroUser() != null){
//				return "redirect:/welcome";
//			} else {
//				log.setResponseCode("Failed-未知错误！");
//			}
//		}
//		
//		activityLogService.insert(log);
//		
//		model.addAttribute("clientName", clientName);
//		model.addAttribute("username", username);
//		model.addAttribute("loginError", loginError);
//		
//		if(req.getRequestURL().indexOf("mg.") != -1){
//			return "/mg_login";
//		}else{
//			if(req.getRequestURL().toString().contains("loreal.")){
//				model.addAttribute("clientTag", "loreal");
//			}
//			return "/colgateZh/login";
//		}
//	}
//	
	
	
}
