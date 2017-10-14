package cn.mobilizer.channel.comm.web;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.utils.web.BaseCommonAction;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;


/**
 * ACTION基类,提供一些Web层常用的公共的方法
 * @author yidan.tian
 * 
 */
public class BaseActionSupport extends BaseCommonAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6335785306515453401L;
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	
	/**
	 * 取出当前对象信息.
	 */
	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 取出Shiro中的当前用户.
	 */
	public ShiroUser getCurrentUser() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public Integer getCurrentUserId() {
		ShiroUser user = getCurrentUser();
		if(user != null) {
			return user.id;
		}
		return null;
	}
	
	/**
	 * 获取用户首页
	 * @return
	 */
	public String getHomePage(){
		ClientUser user = getCurrentUser().clientUser;
		if(user!=null){
			return user.getHomePage();
		}
		return null;
	}
	
	/**
	 * 取出Shiro中的当前用户clientId.
	 * @return
	 */
	public Integer getClientId(){
		ShiroUser user = getCurrentUser();
		if(user != null) {
			return user.clientId;
		}
		return null;
	}
	
	/**
	 * 取出Shiro中的当前用户loginName.
	 * @return
	 */
	public String getLoginName(){
		ShiroUser user = getCurrentUser();
		if(user != null) {
			return user.loginName;
		}
		return null;
	}
	
	/**
	 * 取出Shiro中的当前用户name.
	 * @return
	 */
	public String getUserName(){
		ShiroUser user = getCurrentUser();
		if(user != null) {
			return user.name;
		}
		return null;
	}
	
	/**
	 * 取出Shiro中的当前用户clientId.
	 * @return
	 */
	public String getClientName(){
		ShiroUser user = getCurrentUser();
		if(user != null) {
			return user.clientName;
		}
		return null;
	}
	
	/**
	 * 取出Shiro中的当前用户对象.
	 * @return
	 */
	public ClientUser getClientUser(){
		ShiroUser user = getCurrentUser();
		if(user != null) {
			return user.clientUser;
		}
		return null;
	}
	
	/**
	 * 取出Shiro中的当前用户所在部门.
	 * @return
	 */
	public Integer getClientStructureId(){
		ShiroUser user = getCurrentUser();
		if(user != null && user.clientUser != null && user.clientUser.getClientStructureId () != null) {
			return user.clientUser.getClientStructureId ();
		}
		return null;
	}
	
	/**
	 * 取出Shiro中的当前用户信息
	 * @return
	 */
	public ShiroUser getShiroUser(){
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * 发送Ajax请求结果json
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendAjaxResultByJson(String json, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendAjaxMsg(String msg, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(msg);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected String getDeptIds(String clientStructureId){
		CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subordinates 所有下级人员","号隔开**/
		//String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		if(deptIds.endsWith(",")){
			deptIds = deptIds.substring(0,deptIds.length()-1);
		}
		return deptIds;
	}
	
	
	protected String getDeptIds(Integer clientStructureId){
		CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subordinates 所有下级人员","号隔开**/
		//String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		if(deptIds.endsWith(",")){
			deptIds = deptIds.substring(0,deptIds.length()-1);
		}
		return deptIds;
	}
	
	
//	public void sendAjaxResultByJson(String json){
//		this.sendAjaxResultByJson(json,response);
//	}

	protected void setRequestAttribute(String key, Object value, HttpServletRequest request) {
		request.setAttribute(key, value);
	}
	
	public Map<String, Object> getBaseParams(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", getClientId());
		params.put("clientStructureId", getShiroUser().clientUser.getClientStructureId());
		//params.put("clientPositionId", getShiroUser().clientUser.getClientPositionId());
		params.put("clientUserId", getShiroUser().clientUser.getClientUserId());
		return params;
	}
}