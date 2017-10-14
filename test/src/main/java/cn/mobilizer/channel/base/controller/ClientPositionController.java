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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.OptionsConstants;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;

/**
 * @author yeeda.tian
 * 职位管理Controller
 */
@Controller
@RequestMapping(value = "/clientPosition")
public class ClientPositionController extends BaseActionSupport {


	private static final long	serialVersionUID	= 3331482324562372433L;
	
	@Autowired
	private ClientPositionService clientPositionService;
	@Autowired
	private OptionsService optionsService;
	
	/**
	 * 职位管理-查询列表
	 * @param model
	 * @param page
	 * @param searchPositionName
	 * @param req
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String searchPositionName, String positionNo,HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("positionName", searchPositionName);
		parameters.put("positionNo", positionNo);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = clientPositionService.queryClientPositionCount(parameters);
		
		
		int pagenum = page == null ? 1 : page;
		Page<ClientPosition> pageParam = Page.page(count, 10, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		parameters.put("_orderby", "CLIENT_POSITION_ID");
		parameters.put("_order", "DESC");
		List<ClientPosition> list = clientPositionService.queryClientPositionList(parameters);
		pageParam.setItems(list);

		model.addAttribute("positionNo", positionNo);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("searchPositionName", searchPositionName);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/clientPositionList";
	}
	
	/**
	 * 职位管理-新增页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showAddClinetPostion")
	public String showAddClinetPostion(Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			/**
			 * 获取sysPosition的options数据，暂时每次新增都从数据库中查一下，以后考虑放入缓存中
			 * optionName = sys_positionType 
			 */
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			List<ClientPosition> list = clientPositionService.queryClientPositionList(parameters);
			List<Options> optionsList = optionsService.findOptionsByOptionName (OptionsConstants.CLINET_POSTION_SYSPOSITION, clientId);
			model.addAttribute("optionsList" , optionsList);
			model.addAttribute("clientId" , user.clientId);
			model.addAttribute("list" , list);
			return "base/showAddClientPostion";
		}
		return "/dialog/error";	
	}
	
	/**
	 * 职位管理-编辑页面
	 * @param clientPositionId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showEditClientPosition/{clientPositionId}")
	public String editClientPosition(@PathVariable("clientPositionId")Integer clientPositionId, Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			Map<String, Object> parameters = new HashMap<String, Object>();
			ClientPosition clientPosition = clientPositionService.findClientPositionById(clientPositionId,clientId);
			parameters.put("clientId", clientId);
			parameters.put("positionId", clientPosition.getClientPositionId());
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			List<ClientPosition> optionlist = clientPositionService.queryClientPositionList(parameters);
			model.addAttribute("optionlist" , optionlist);
			model.addAttribute("clientPosition" , clientPosition);
			return "base/showEditClientPosition";
		}
		return "/dialog/error";	
	}
	/**
	 * 查看页面
	 * @param clientPositionId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showCheckClientPosition/{clientPositionId}")
	public String showCheckClientPosition(@PathVariable("clientPositionId")Integer clientPositionId, Model model)throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			ClientPosition clientPosition = clientPositionService.findClientPositionById(clientPositionId,clientId);
			model.addAttribute("clientPosition" , clientPosition);
			return "base/showCheckClientPosition";
		}
		return "/dialog/error";	
		
	}
	
	/**
	 * 新增
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addClientPosition", produces="application/json")
	@ResponseBody
	public Object addClientPosition(ClientPosition clientPosition) throws BusinessException{
		
		if (log.isDebugEnabled()) {
			log.debug("start method<addClientPosition>");
		}
		clientPositionService.addClientPosition(clientPosition);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	/**
	 * 修改
	 * @param clientPosition
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateClientPosition", produces="application/json")
	@ResponseBody
	public Object updateClientPosition(ClientPosition clientPosition) throws BusinessException{
		
		if (log.isDebugEnabled()) {
			log.debug("start method<addClientPosition>");
		}
		clientPositionService.updateClientPosition(clientPosition);
		
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**
	 * 删除
	 * @param clientPositionId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteClinetPostion/{clientPositionId}", produces="application/json")
	@ResponseBody	
	public Object deleteClinetPostion(@PathVariable("clientPositionId")Integer clientPositionId) throws BusinessException  {
		if (log.isDebugEnabled()) { 
			log.debug("start method<deleteClinetPostion>");
		}
//		clientPositionService.deleteClinetPostion(clientPositionId);
		clientPositionService.deleteClinetPostion(clientPositionId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	/**
	 * 异步获取职位信息
	 * @param clientPositionId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getClientPositionAjax", produces="application/json")
	@ResponseBody	
	public List<ClientPosition> getClientPositionAjax(Integer clientPositionId) throws BusinessException  {
		int clientId = getClientId ();
		List<ClientPosition> ls = clientPositionService.findClientPositionsByClientId(clientId);
		return ls;
	}
	
	/**
	 * 异步获取职位信息-拜访数据查询模块-通过有“手机登录权限”的人获取职位信息
	 * @param clientPositionId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getClientPositionByMobilePer", produces="application/json")
	@ResponseBody	
	public List<ClientPosition> getClientPositionByMobilePer(Integer clientPositionId) throws BusinessException  {
		int clientId = getClientId ();
		List<ClientPosition> ls = clientPositionService.findClientPositionByMobilePer (clientId);
		return ls;
	}
	
	
	@RequestMapping(value = "/verifyUserName")
	@ResponseBody
	public Object verifyUserName(String positionName){
		Integer clientId = getClientId();
		boolean isExist = clientPositionService.isExistUserName(positionName,clientId);
		if(isExist){
			return ResultMessage.VERIFYUSERNAME_FAIL_RESULT;
		}else{
			return ResultMessage.VERIFYUSERNAME_SUCCESS_RESULT;
		}
	}
	
	
	@RequestMapping(value = "/verifyUserNameForUpdate")
	@ResponseBody
	public Object verifyUserNameForUpdate(String positionName,String clientPositionId){
		Integer clientId = getClientId();
		boolean isExist = clientPositionService.isExistUserName(positionName,clientPositionId,clientId);
		if(isExist){
			return ResultMessage.VERIFYUSERNAME_FAIL_RESULT;
		}else{
			return ResultMessage.VERIFYUSERNAME_SUCCESS_RESULT;
		}
	}
	/**
	 * 职位多选  树 
	 * @return
	 */
	@RequestMapping(value = "/getClientPositionZtree")
	@ResponseBody
	public List<ClientPosition> getClientPositionZtree(){
		Integer clientId = getClientId();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId",clientId);
		List<ClientPosition> ztreeClientPosition = clientPositionService.getZtreeClientPosition(parameters);
		return ztreeClientPosition;
		
	}
	
	/**
	 * 角色为手机-业务代表和手机-业务代表主管下的职位 
	 * @return
	 */
	@RequestMapping(value = "/getClientPositionBusiness")
	@ResponseBody
	public List<ClientPosition> getClientPositionBusiness(){
		int clientId = getClientId();
		List<ClientPosition> clientPositionBusiness = clientPositionService.getClientPositionBusiness(clientId);
		return clientPositionBusiness;
	}
	
}
