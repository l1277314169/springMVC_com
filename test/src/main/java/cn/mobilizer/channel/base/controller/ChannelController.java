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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.OptionsConstants;
import cn.mobilizer.channel.base.service.ChannelService;
import cn.mobilizer.channel.base.service.ClientService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;

/**
 * @author honger.liu
 * Channel管理Controller
 * 2014-11-20 17:54:20
 */

@Controller
@RequestMapping(value = "/channel")
public class ChannelController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1997444411661968749L;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ClientService clientService;

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
		List<TreeNodeVO> list = channelService.getTreeNodes(clientId,id);
		return list;
	}
	
	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(){
		return "base/importClientUser";
	}
	
	 /**
	  * 渠道管理-查询列表
	  * @param model
	  * @param page
	  * @param searchUserName
	  * @param searchUserNo
	  * @param searchClientStructureId
	  * @param req
	  * @return
	  * @throws BusinessException
	  */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String searchName, String searchNameEn,Integer parentId, String clientStructureId, HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("searchName", searchName);
		parameters.put("searchNameEn", searchNameEn);
		parameters.put("parentId", parentId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = channelService.queryChannelCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<Channel> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
//		parameters.put("_orderby", "LAST_UPDATE_TIME");
//		parameters.put("_order", "DESC");
		List<Channel> list = channelService.queryChannelList(parameters);
		pageParam.setItems(list);
		

		
		/**获取部门**/
//		TODO-获取部门tree
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("parentId", parentId);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("searchName", searchName);
		model.addAttribute("searchNameEn", searchNameEn);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/channelList";
	}
	
	
	/**
	 * 渠道管理-新增页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showAddChannel")
	public String showAddChannel(Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			/**
			 * 获取sysPosition的options数据，暂时每次新增都从数据库中查一下，以后考虑放入缓存中
			 * optionName = sys_positionType 
			 */
//			List<Channel> channel = channelService.findChannelName();
//			model.addAttribute("channel" , channel);
			model.addAttribute("clientId" , user.clientId);
			
			return "base/showAddChannel";
		}
		return "/dialog/error";	
	}
	
	

	/**
	 * 渠道管理-编辑页面
	 * @param channelId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showEditChannel/{channelId}")
	public String editClientPosition(@PathVariable("channelId")Integer channelId, Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			Channel channel = channelService.findByPrimaryKey(channelId);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			if(channel.getGrade()==null){
				parameters.put("grade", null);
			}else{
			parameters.put("grade", channel.getGrade()-1);
			}
			List<Channel> channelList = channelService.findChannelName(parameters);
			model.addAttribute("channel" , channel);
			model.addAttribute("channelList" , channelList);
			model.addAttribute("clientId" , clientId);
			return "base/showEditChannel";
		}
		return "/dialog/error";	
	}
	/**
	 * 查看
	 * @param chainId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showCheckChannel/{channelId}")
	public String checkChannel(@PathVariable("channelId")Integer channelId, Model model){
		Channel channel = channelService.findByPrimaryKey(channelId);
		if(channel != null){
			if(channel.getParentId() != null){
				Channel parent = channelService.findByPrimaryKey(channel.getParentId());
				model.addAttribute("parent" , parent);
			}
		}
		model.addAttribute("channel" , channel);
		return "base/showCheckChannel";
	}
	
	/**
	 * 新增
	 * @param channel
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addChannel", produces="application/json")
	@ResponseBody
	public Object addChannel(Channel channel) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<addChannel>");
		}
		channelService.addChannel(channel);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	
	
	/**
	 * 修改
	 * @param channel
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateChannel", produces="application/json")
	@ResponseBody
	public Object updateChannel(Channel channel) throws BusinessException{
		
		if (log.isDebugEnabled()) {
			log.debug("start method<updateChannel>");
		}
		channelService.update(channel);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**
	 * 删除
	 * @param channelId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteChannel/{channelId}", produces="application/json")
	@ResponseBody	
	public Object deleteChannel(@PathVariable("channelId")Integer channelId) throws BusinessException  {
		if (log.isDebugEnabled()) {
			log.debug("start method<deleteChannel>");
		}
//		clientPositionService.deleteClinetPostion(clientPositionId);
	    channelService.deleteChannel(channelId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	/**
	 * 上级渠道
	 * @param gardeId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/findForListGrade",produces="application/json")
	@ResponseBody	
	public List<Channel> addGrade(Integer gradeId,Integer channelId){
		if (log.isDebugEnabled()) {
			log.debug("start method<addgrade>");
		}
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(gradeId != null){
			parameters.put("grade", gradeId-1);
		}
		parameters.put("clientId", clientId);
		parameters.put("channelId", channelId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		List<Channel> parent = channelService.findChannelName(parameters);
		return parent;
	}	
}
