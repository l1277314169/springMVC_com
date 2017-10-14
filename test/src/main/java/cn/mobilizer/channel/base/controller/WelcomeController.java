/**
 * 
 */
package cn.mobilizer.channel.base.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.autho.service.AuthService;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientUserExpandService;
import cn.mobilizer.channel.base.vo.LeftMenuVO;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.systemManager.po.ClientPrivileges;
import cn.mobilizer.channel.systemManager.service.ClientPrivilegesService;
import cn.mobilizer.channel.util.PasswordHelper;

/**
 *  登录跳转等控制层、登出
 * @author yeeda.tian
 *
 */
@Controller
public class WelcomeController extends BaseActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID	= 5261061579721745529L;
	
	@Autowired
	private ClientPrivilegesService clientPrivilegesService;
	@Autowired
	private ClientUserExpandService clientUserExpandService;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/layouts/header")
	public String header(Model model) throws Exception {
		ShiroUser user = getShiroUser();
		if(user != null) {
			model.addAttribute("loginName", user.loginName);
		}
		return "/layouts/header";
	}
	
	@RequestMapping(value = "/layouts/left")
	public String left(Model model) throws Exception {
		
		int clientUserId = getCurrentUserId();
		List<ClientPrivileges> cpList = clientPrivilegesService.getUserMenu(clientUserId);
		
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isPermitted ("testPer")){
			log.info ("用户拥有testPer权限");
		}
		model.addAttribute("cpList", cpList);
		return "/layouts/left";
	}
	
	@RequestMapping(value = "/layouts/ajaxLeft")
	@ResponseBody
	public List<LeftMenuVO> ajaxLeft(Model model) throws Exception {
		int clientUserId = getCurrentUserId();
		List<LeftMenuVO> menuList =  getCurrentUser().leftMenuVO;
		if(menuList == null || menuList.size() <= 0) {
			menuList = clientPrivilegesService.getMenusByClientUserId(clientUserId);
		}
		return menuList;
	}
	
	@RequestMapping(value = "/layouts/main")
	public String main(Model model,HttpServletRequest resquest) throws Exception {
		int clientId = getClientId();
//		int clientUserId = getCurrentUserId();
//		
//		//用于主动加载权限用
//		if(SecurityUtils.getSubject().isPermitted ("testPer")){
//			log.info ("/layouts/main-用户:"+clientUserId+"拥有testPer权限");
//		} else {
//			log.info ("/layouts/main-用户:"+clientUserId+"没有testPer权限");		
//		}
		
		String homePage = super.getHomePage();
		model.addAttribute("clientId",clientId);
		if(StringUtil.isEmptyString(homePage)){
			model.addAttribute("_root", resquest.getContextPath());
			return "/layouts/main";
		}else{
			model.addAttribute("_root", resquest.getContextPath()+homePage);
			return "redirect:"+homePage;
		}
	}
	
	@RequestMapping(value = "/welcome")
	public String welcome(Model model,HttpServletRequest resquest) throws Exception {
		int clientUserId = getCurrentUserId();
		ClientUser clientUser = getClientUser();
		Subject subject = SecurityUtils.getSubject();
		
		//用于主动加载权限用
		if(subject.isPermitted ("testPer")){
			log.info("/welcome-用户:"+clientUserId+"拥有testPer权限");
		} else {
			log.info("/welcome-用户:" + clientUserId + "没有testPer权限......");
			// 登录时,给每个客户都分配了testPer权限，如果没有表示获取权限有问题，主动重新发起获取权限的动作。
			//TODO-yeeda
			authService.clearCachedAuthorizationInfo(clientUserId);
		}
		
		List<LeftMenuVO> menuList =  getCurrentUser().leftMenuVO;
		if(menuList == null || menuList.size() <= 0) {
			menuList = clientPrivilegesService.getMenusByClientUserId(clientUserId);
		}
		
//		model.addAttribute("clientUser", clientUser);
//		model.addAttribute("menuList", menuList);
		
//		resquest.getSession().setAttribute("clientUser2", clientUser);
//		resquest.getSession().setAttribute("menuList2", menuList);
		
		/**判断密码是否为初始密码，并且是第一次登陆，如果是跳转到强制修改密码页面。**/
		if(clientUser.getLastLoginTime() == null && PasswordHelper.checkPasswd(clientUser.getLoginPwd(), clientUser.getSalt(), "8888")) {
			return "/updateClientUserPassword";
		}
//		ShiroUser user = (ShiroUser) WebUtils.getSession(resquest, Constants.SHIRO_USER);
//		if(null != user && null != user.loginType && user.loginType.trim().length() > 0){
//			return "redirect:"+user.loginType;
//		}
//		log.info("welcome:"+resquest.getRequestURL());
		
		model.addAttribute("clientId", super.getClientId());
		if(resquest.getRequestURL().indexOf("colgate.")!=-1){
			return "redirect:/colgate/home";
		}else if(resquest.getRequestURL().indexOf("apple.")!=-1){
			return "redirect:/apple/appleOverView";
		}
		
		
		String homePage = super.getHomePage();
		model.addAttribute("clientId",super.getClientId());
		if(StringUtil.isEmptyString(homePage)){
			model.addAttribute("_root", resquest.getContextPath());
			return "/welcome";
		}else{
			model.addAttribute("_root", resquest.getContextPath()+homePage);
			return "redirect:"+homePage;
		}
	}
	
	@RequestMapping(value = "/")
	public String root() throws Exception {
		System.out.println("redirect:/welcome");
		return "redirect:/welcome"; 
	}
	
	/**
     * <p>Description: 用户注销</p>
     */
	@RequestMapping(value="logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		log.debug("用户["+getCurrentUserId()+"]退出系统.");
//		String onLoginUrl = (String) WebUtils.getSession(req, Constants.ON_LOGIN_URL);
		SecurityUtils.getSubject().logout();
		log.info("logout:"+req.getRequestURL());
		if(req.getRequestURL().indexOf("mg.") != -1){
			return "redirect:/mg_login";
		}else if(req.getRequestURL().indexOf("colgate.")!=-1){
			return "redirect:/colgate_login";
		}else if(req.getRequestURL().indexOf("apple.")!=-1){
			return "redirect:/apple_login";
		}else if(req.getRequestURL().indexOf("ferrero.")!=-1){
			return "/ferrero_login";
		}else{
			return "redirect:/login";
		}
	}
	
	/**
	 * <p>Description: 用户注销</p>
	 */
	@RequestMapping(value="ajaxLogout")
	@ResponseBody
	public void ajaxLogout(HttpServletRequest req) {
		log.debug("用户["+getCurrentUserId()+"]退出系统.");
		SecurityUtils.getSubject().logout();
	}
	
	/**
	 * 获取左侧菜单
	 * @param model
	 * @param privCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMenus")
	public String getMenus(Model model,String privCode) throws Exception {
		int clientUserId = getCurrentUserId();
		List<LeftMenuVO> menuList =  getCurrentUser().leftMenuVO;
		if(menuList == null || menuList.size() <= 0) {
			menuList = clientPrivilegesService.getMenusByClientUserId(clientUserId);
		}
		ClientUser clientUser = getClientUser();
		model.addAttribute("privCode", privCode);
		model.addAttribute("menuList2", menuList);
		model.addAttribute("clientUser", clientUser);
		return "menu";
	}
	
}
