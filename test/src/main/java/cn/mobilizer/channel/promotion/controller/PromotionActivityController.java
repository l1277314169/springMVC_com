/**
 * 
 */
package cn.mobilizer.channel.promotion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.promotion.po.PromotionActivity;
import cn.mobilizer.channel.promotion.service.PromotionActivityService;
import cn.mobilizer.channel.util.Constants;

/**
 * @author honger.liu
 * PromotionActivity管理Controller
 * 2014-11-20 17:54:20
 */

@Controller
@RequestMapping(value = "/promotionActivity")
public class PromotionActivityController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1997444411661968749L;
	@Autowired
	private PromotionActivityService promotionActivityService;
	

//	/**
//	 * 异步加载树的子结点
//	 * @param id
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/getTreeNode", produces = "application/json; charset=UTF-8")
//	@ResponseBody
//	public Object listNodes(@RequestParam(value = "id",defaultValue="-1") Integer id) {
//		int clientId = getClientId ();
//		List<TreeNodeVO> list = promotionActivityService.getTreeNodes(clientId,id);
//		return list;
//	}
	
	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(){
		return "base/importClientUser";
	}
	
	 /**
	  * 促销管理-查询列表
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
	public String query(Model model, Integer page, String activityName, Integer activityType,  HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("activityName", activityName);
		parameters.put("activityType", activityType);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = promotionActivityService.queryPromotionActivityCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<PromotionActivity> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<PromotionActivity> list = promotionActivityService.queryPromotionActivityList(parameters);
		pageParam.setItems(list);
		
		/**获取部门**/
//		TODO-获取部门tree
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("activityName", activityName);
		model.addAttribute("activityType", activityType);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/promotion/promotionActivityList";
	}
	
	
//	/**
//	 * 渠道管理-新增页面
//	 * @param model
//	 * @return
//	 * @throws BusinessException
//	 */
//	@RequestMapping(value = "/showAddChannel")
//	public String showAddChannel(Model model) throws BusinessException{
//		ShiroUser user = getShiroUser ();
//		if(user != null && user.clientId != null) {
//			int clientId = user.clientId;
//			/**
//			 * 获取sysPosition的options数据，暂时每次新增都从数据库中查一下，以后考虑放入缓存中
//			 * optionName = sys_positionType 
//			 */
////			List<Channel> channel = channelService.findChannelName();
////			model.addAttribute("channel" , channel);
//			model.addAttribute("clientId" , user.clientId);
//			
//			return "base/showAddChannel";
//		}
//		return "/dialog/error";	
//	}
//	
//	
//
//	/**
//	 * 渠道管理-编辑页面
//	 * @param channelId
//	 * @param model
//	 * @return
//	 * @throws BusinessException
//	 */
//	@RequestMapping(value = "/showEditChannel/{channelId}")
//	public String editClientPosition(@PathVariable("channelId")Integer channelId, Model model) throws BusinessException{
//		ShiroUser user = getShiroUser ();
//		if(user != null && user.clientId != null) {
//			int clientId = user.clientId;
//			Channel channel = channelService.findByPrimaryKey(channelId);
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("grade", channel.getGrade());
//			List<Channel> channelList = channelService.findChannelName(parameters);
//			model.addAttribute("channel" , channel);
//			model.addAttribute("channelList" , channelList);
//			model.addAttribute("clientId" , clientId);
//			return "base/showEditChannel";
//		}
//		return "/dialog/error";	
//	}
//	
//	/**
//	 * 新增
//	 * @param channel
//	 * @return
//	 * @throws BusinessException
//	 */
//	@RequestMapping(value = "/addChannel", produces="application/json")
//	@ResponseBody
//	public Object addChannel(Channel channel) throws BusinessException{
//		if (log.isDebugEnabled()) {
//			log.debug("start method<addChannel>");
//		}
//		channelService.addChannel(channel);
//		return ResultMessage.ADD_SUCCESS_RESULT;
//	}
//	
//	
//	
//	/**
//	 * 修改
//	 * @param channel
//	 * @return
//	 * @throws BusinessException
//	 */
//	@RequestMapping(value = "/updateChannel", produces="application/json")
//	@ResponseBody
//	public Object updateChannel(Channel channel) throws BusinessException{
//		
//		if (log.isDebugEnabled()) {
//			log.debug("start method<updateChannel>");
//		}
//		channelService.update(channel);
//		return ResultMessage.UPDATE_SUCCESS_RESULT;
//	}
//	
//	/**
//	 * 删除
//	 * @param channelId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@RequestMapping(value = "/deleteChannel/{channelId}", produces="application/json")
//	@ResponseBody	
//	public Object deleteChannel(@PathVariable("channelId")Integer channelId) throws BusinessException  {
//		if (log.isDebugEnabled()) {
//			log.debug("start method<deleteChannel>");
//		}
	//clientPositionService.deleteClinetPostion(clientPositionId);
//	    channelService.deleteChannel(channelId);
//		return ResultMessage.DELETE_SUCCESS_RESULT;
//	}
//	/**
//	 * 上级渠道
//	 * @param gardeId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@RequestMapping(value = "/findForListGrade{gardeId}",produces="application/json")
//	@ResponseBody	
//	public List<Channel> addGrade(@PathVariable("gardeId")Integer gradeId){
//		if (log.isDebugEnabled()) {
//			log.debug("start method<addgrade>");
//		}
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("grade", gradeId);
//		List<Channel> parent = channelService.findChannelName(parameters);
//		return parent;
//	}
	
}
