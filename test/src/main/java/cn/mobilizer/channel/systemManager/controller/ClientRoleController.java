package cn.mobilizer.channel.systemManager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_POP_TYPE;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientRoles;
import cn.mobilizer.channel.systemManager.service.ClientPrivilegesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesService;
import cn.mobilizer.channel.util.Constants;

/**角色管理
 * @author Nany
 * 2014年12月8日下午2:27:00
 */
@Controller
@RequestMapping(value="/clientRoles")
public class ClientRoleController  extends BaseActionSupport{

	private static final long serialVersionUID = -8328496360219498889L;
	
	@Autowired 
	private ClientRolesService clientRoleService;
	@Autowired 
	private ClientPrivilegesService clientPrivilegesService;
	@Autowired
	private ClientRolesService clientRolesService;
	
	/**查询角色
	 * @author Nany
	 * 2014年12月8日下午2:29:07
	 * @return
	 */
	@RequestMapping(value = "query")
	public String query(Model model, Integer page, String searchClientRoleName, HttpServletRequest req) {
		int clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("roleName",searchClientRoleName);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = clientRoleService.queryClientRoleCount(parameters);
		//分页
		int pagenum = page == null ? 1 : page;
		Page<ClientRoles> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		parameters.put("_orderby", "LAST_UPDATE_TIME");
		parameters.put("_order", "DESC");
		List<ClientRoles> clientRoleList = clientRoleService.getObjectList(parameters);
		pageParam.setItems(clientRoleList);
		model.addAttribute("pageParam",pageParam );
		model.addAttribute("searchClientRoleName", searchClientRoleName);
		model.addAttribute("page", pageParam.getPage().toString());	
		
		return "/systemManager/clientRoleList";
	
	}
	
	@RequestMapping("/showRoleParameter")
	public String showPopTypeParameter(Model model, Integer page, HttpServletRequest req, String searchAddr) throws BusinessException {
		Integer clientId = getClientId();
		List<ClientRoles> clientRoles = clientRoleService.findSystemRolesByClientId(clientId);
		model.addAttribute("clientRoles", clientRoles);
		return "/visit/showClientRoles";
	}
	
	/**角色导入
	 * @author Nany
	 * 2015年1月12日上午10:19:35
	 * @return
	 */
	@RequestMapping(value="/showImportDialog")
	public String showImportDialog() {
		return "/systemManager/importClientRoles";
	}
	/**角色管理--新增
	 * @author Nany
	 * 2014年12月8日下午5:44:13
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showAddRole")
	public String  showAddRole(Model model) {
		int clientId = getClientId();
		model.addAttribute("clientId", clientId);
		return "/systemManager/showAddRoleList";
	}
	
	
	/**角色管理--新增--保存
	 * @author Nany
	 * 2014年12月8日下午5:44:00
	 * @return
	 */
	@RequestMapping(value="/addClientRole")
	@ResponseBody
	public Object addRole(ClientRoles clientRole) {
		int clientId = getClientId();
		clientRole.setClientId(clientId);
		clientRoleService.addRoles(clientRole);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	/**角色管理--编辑
	 * @author Nany
	 * 2014年12月8日下午6:35:45
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showEditRole/{roleId}", produces="application/json")
	public String showEditRole(@PathVariable("roleId")Integer roleId ,Model model) {
		int clientId = getClientId();
		ClientRoles clientRole = clientRoleService.getClientRole(roleId);
		
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientRole", clientRole);
		return "/systemManager/showEditRoleList";
	}
	
	/**角色管理--编辑--保存
	 * @author Nany
	 * 2014年12月8日下午7:05:43
	 * @param clientRole
	 * @return
	 */
	@RequestMapping(value="/updateClientRole")
	@ResponseBody
	public Object updateRole(ClientRoles clientRole) {
		int clientId = getClientId();
		clientRole.setClientId(clientId);
		clientRoleService.updateRole(clientRole);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
		
		

	/**角色管理--删除(改变状态)
	 * @author Nany
	 * 2014年12月8日下午7:40:54
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/deleteClientRole/{roleId}", produces="application/json")
	@ResponseBody
	public Object deleteRole(@PathVariable("roleId")Integer roleId) {
		int clientId = getClientId();
		ClientRoles clientRole = clientRoleService.getClientRole(roleId);
		clientRole.setIsDelete(Constants.IS_DELETE_TRUE);
		clientRole.setClientId(clientId);
		clientRoleService.deleteRole(clientRole);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	
	/**新增验证角色是否已经存在
	 * @author Nany
	 * 2015年1月4日上午11:46:49
	 * @param roleNameEn
	 * @return
	 */
	@RequestMapping(value="/validateRoleName")
	@ResponseBody
	public Object validateRoleName(String roleName) {
		int clientId = getClientId();
		ClientRoles clientRole = clientRoleService.getClientRoleByRoleName(clientId,roleName);
		if(clientRole != null ){
			return ResultMessage.NO_EXITE_FAIL;
		}else{
			return ResultMessage.NO_EXITE_SUCCESS;
		}
	}
	
	/**
	 * 弹出角色分配权限的页面
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/showRolePrivileges/{roleId}")
	public Object showRolePrivileges(@PathVariable("roleId")Integer roleId,Model model){
		
		model.addAttribute("roleId",roleId);
		return "/systemManager/showRolePrivileges";
	}
	
	/**
	 * 异步加载web权限树
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getWebTreeNode/{roleId}", produces = "application/json")
	@ResponseBody
	public Object getWebTreeNode(@PathVariable(value = "roleId") Integer roleId) {
		int clientId = getClientId ();
		List<TreeNodeVO> list = clientPrivilegesService.getWebTreeNode(clientId,roleId);
		
		return list;
	}
	
	/**
	 * 异步加载mobile权限树
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMobileTreeNode/{roleId}", produces = "application/json")
	@ResponseBody
	public Object getMobileTreeNode(@PathVariable(value = "roleId") Integer roleId) {
		int clientId = getClientId ();
		List<TreeNodeVO> list = clientPrivilegesService.getMobileTreeNode(clientId,roleId);
		
		return list;
	}
	
	/**
	 * 异步加载Data权限树
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDataTreeNode/{roleId}", produces = "application/json")
	@ResponseBody
	public Object getDataTreeNode(@PathVariable(value = "roleId") Integer roleId) {
		int clientId = getClientId ();
		List<TreeNodeVO> list = clientPrivilegesService.getDataTreeNode(clientId,roleId);
		
		return list;
	}
	
	@RequestMapping(value = "/updateRolePrivileges", produces="application/json")
	@ResponseBody
	public Object updateRolePrivileges(Integer roleId,String checkedWebOld,String checkedWebNew,
			String checkedMobileOld,String checkedMobileNew,String checkedDataOld,String checkedDataNew) {
		int clientId = getClientId();
		clientRoleService.updateRolePrivileges(roleId,clientId,checkedWebOld,checkedWebNew,checkedMobileOld,checkedMobileNew,checkedDataOld,checkedDataNew);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	/**
	 * 异步加载角色
	 * @param clientUserId
	 * @param q
	 * @param page
	 * @param childId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getRolesByselectTwo", produces="application/json")
	@ResponseBody
	public List<ClientRoles> getRolesByselectTwo(Integer clientUserId,String q) throws BusinessException{
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		String name = q;
		parameters.put("name",name);
		parameters.put("clientId",clientId);
		List<ClientRoles> list=clientRoleService.queryRolesByselectTwo(parameters);
		return list;
	}
	/**
	 * 
	 * 初始化select2数据
	 * @param rolesId
	 * @return
	 */
	@RequestMapping(value="/getRolesById/{rolesId}",produces="application/json")
	@ResponseBody
	public ClientRoles getRolesById(@PathVariable("rolesId")Integer rolesId) {
		int clientId = getClientId();
		ClientRoles clientRoles = clientRoleService.queryRolesById(rolesId);
		return clientRoles;
	}
	
	/**
	 * 异步获取角色数据
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getClientRolesAjax", produces="application/json")
	@ResponseBody	
	public List<ClientRoles> getClientRolesAjax() throws BusinessException  {
		int clientId = getClientId ();
		int clientUserId = getCurrentUserId();
		/**获取当前用户的角色等级**/
		Byte level = clientRolesService.getUserRoleLevel(clientUserId, clientId);
		List<ClientRoles> ls = clientRolesService.findSystemRolesByClientIdAndRoleLevel(clientId,level);
		return ls;
	}
	
	/**
	 * 异步获取角色为业务代表和业务主管
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getRolesBusiness", produces="application/json")
	@ResponseBody
	public List<ClientRoles> getRolesBusiness(){
		int clientId = getClientId ();
		List<ClientRoles> rolesIsBusiness = clientRolesService.getRolesIsBusiness(clientId);
		return rolesIsBusiness;
	}
}
