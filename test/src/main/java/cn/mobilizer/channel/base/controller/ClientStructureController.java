/**
 * 
 */
package cn.mobilizer.channel.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.SkuType;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

/**
 * @author yeeda.tian
 * 组织架构管理Controller
 * 2014年11月13日18:51:53
 */

@Controller
@RequestMapping(value = "/clientStructure")
public class ClientStructureController extends BaseActionSupport {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8217752472331716108L;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private ChannelCommService channelCommService;
	
	/**
	 * 跳转组织架构管理页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/manage")
	public String manage(Model model) throws BusinessException{
		log.info ("组织架构管理页面");
		
		int clientId = getClientId ();
		String clientName = getClientName ();
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientName", clientName);
		return "/base/clientStructureManage";
	}
	
	/**
	 * 异步加载树的子结点
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getTreeNode", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object listNodes(@RequestParam(value = "id",defaultValue="-1") Integer id) {
		int clientId = getClientId ();
		List<TreeNodeVO> list = clientStructureService.getTreeNodes(clientId,id);
//		List<Object> list = clientStructureService.getTreeNodes4StringList(clientId,pId);
//		String treeNodes = clientStructureService.getTreeNodes4String(clientId,pId);
		return list;
	}
	
	/**
	 * 从数据权限中-异步加载-当前登录用户的-组织架构树
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getTreeNodeWithPer", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getTreeNodeWithPer() {
		int clientId = getClientId ();
		int clientUserId = getCurrentUserId();
		int clientStructureId = getClientStructureId();
		
//		/**获取该用户的所有数据权限**/
//		String dataPermissions= getCurrentUser().clientUser.getPermissionsData();
//		List<TreeNodeVO> list = clientStructureService.getTreeNodeWithPer(clientId,clientUserId,dataPermissions);
		
		/**获取该用户所在部门及其以下部门**/
		String subClientStructureIds= channelCommService.getSubStructrueIds(clientStructureId);
		List<TreeNodeVO> list = clientStructureService.getTreeNodeWithSle(clientId,clientUserId,subClientStructureIds);
//		List<Object> list = clientStructureService.getTreeNodes4StringList(clientId,pId);
//		String treeNodes = clientStructureService.getTreeNodes4String(clientId,pId);
		return list;
	}
	
	/**
	 * zTree-add
	 * @param pId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addStructureFromTree", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object addStructureFromTree(Integer pId, HttpServletRequest req) {
		int id = 0;
		if (pId != null) {
			int clientId = getClientId();
			ClientStructure clientStructure = new ClientStructure();
			if (pId > 0) {
				clientStructure.setParentId(pId);
			}
			clientStructure.setStructureName(Constants.ADD_NEW_STRUCTURE);
			clientStructure.setClientId(clientId);
			id = clientStructureService.addClientStructure(clientStructure);
		}
		return id;
	}
	
	
	/**
	 * zTree-update
	 * @param id
	 * @param name
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateStructureFromTree", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object updateStructureFromTree(Integer id, String name, HttpServletRequest req) {
		if(id != null && id > 0 && name != null && name !="") {
			ClientStructure clientStructure = new ClientStructure();
			
			clientStructure.setClientStructureId(id);
			clientStructure.setStructureName(name);
			clientStructureService.updateClientStructure(clientStructure);
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		} else {
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
		
		
	}
	/**
	 * 验证部门唯一性
	 * @return
	 */
	@RequestMapping(value = "/validateStructure")
	@ResponseBody
	public Object validateStructure(String name,String oldName){
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("name", name);
		
		ClientStructure dept = clientStructureService.getRepeatClientStructureName(parameters);
		if(dept != null && dept.getStructureName().equals(oldName)){
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		}
		if(dept != null){
			return ResultMessage.WEB_CLIENT_STRUCTURE;
		}else{
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		}
		
	}
	
	@RequestMapping(value = "/deleteStructureFromTree", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object deleteStructureFromTree(Integer id) {
		if(id != null && id > 0) {
			ClientStructure clientStructure = new ClientStructure();
			
			clientStructure.setClientStructureId(id);
			clientStructureService.deleteClientStructure(id);
			return ResultMessage.DELETE_SUCCESS_RESULT;
		} else {
			return ResultMessage.DELETE_FAIL_RESULT;
		}
	}
	
	/**
	 * 通过id查询ClientStructured对象
	 * @param id
	 * @return json格式的ClientStructured
	 */
	@RequestMapping(value = "/getJsonClientStructureById", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getJasonClientStructureById(Integer id){
		if(id != null && id > 0) {
			ClientStructure clientStructure = clientStructureService.getClientStructureById(id);
			return clientStructure;
		} else {
			return ResultMessage.ID_UNEXITE_RESULT;
		}
	}
	
	/**
	 * 页面修改ClientStructure数据
	 * @param clientStructure
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateClientStructure", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object updateClientStructure(ClientStructure clientStructure) throws BusinessException{
		try {
			if(clientStructure.getClientStructureId () != null) {
				clientStructureService.updateClientStructure(clientStructure);
			} else {
				return ResultMessage.UPDATE_FAIL_RESULT;
			}
			//TODO-部门领导人数据-已经改为上下级关系
			
		} catch (BusinessException e){
			log.error("method updateClientStructure error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	@RequestMapping(value = "/getClientStructurebyId", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getClientStructurebyId(Integer Id) {
		int clientId = getClientId();
		List<ClientStructure> list = clientStructureService.query(clientId);
		return list;
	}
}
