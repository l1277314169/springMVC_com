/**
 * 
 */
package cn.mobilizer.channel.systemManager.controller;

import java.util.Date;
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
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.ClientService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.MobileVersion;
import cn.mobilizer.channel.systemManager.po.MobileVersionDetail;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.systemManager.service.MobileVersionDetailService;
import cn.mobilizer.channel.systemManager.service.MobileVersionService;
import cn.mobilizer.channel.util.Constants;

/**
 * @author honger.liu
 * mobileVersion管理Controller
 * 2014-11-20 17:54:20
 */

@Controller
@RequestMapping(value = "/mobileVersion")
public class MobileVersionController extends BaseActionSupport {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1997444411661968749L;
	@Autowired
	private MobileVersionService mobileVersionService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientPositionService clientPositionService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private MobileVersionDetailService mobileVersionDetailService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	
	 /**
	  * 手机版本管理-查询列表
	  * @param model
	  * @param page
	  * @param version
	  * @param appcode
	  * @param appcodeId
	  * @param req
	  * @return
	  * @throws BusinessException
	  */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String version, String appcode,HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("version", version);
		parameters.put("appcode", appcode);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = mobileVersionService.queryMobileVersionCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<MobileVersion> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		parameters.put("_orderby", "LAST_UPDATE_TIME");
		parameters.put("_order", "DESC");
		List<MobileVersion> list = mobileVersionService.queryMobileVersionList(parameters);
		pageParam.setItems(list);
		
		/**获取部门**/
//		TODO-获取部门tree
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("appcode", appcode);
		model.addAttribute("version", version);
		model.addAttribute("appType" ,ChannelEnum.MOBILE_VERSION_TYPE.values());
		model.addAttribute("page", pageParam.getPage().toString());
		return "/systemManager/mobileVersionList";
	}
	
	
	/**
	 * 手机版本管理-新增页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showAddMobileVersion")
	public String showAddMobileVersion(Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			/**
			 * 获取sysPosition的options数据，暂时每次新增都从数据库中查一下，以后考虑放入缓存中
			 * optionName = sys_positionType 
			 */
			model.addAttribute("clientId" , clientId);
			model.addAttribute("appType" ,ChannelEnum.MOBILE_VERSION_TYPE.values());
			return "/systemManager/showAddMobileVersion";
		}
		return "/dialog/error";	
	}
//	
//	

	/**
	 * 手机版本管理-编辑页面
	 * @param channelId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showEditMobileVersion/{mobileVersionId}")
	public String editMobileVersion(@PathVariable("mobileVersionId")Integer mobileVersionId, Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			MobileVersionDetail mobileVersionDetail=mobileVersionDetailService.findMobileVersionDetailByMobileVersionId(mobileVersionId);
			MobileVersion mobileVersion = mobileVersionService.findByPrimaryKey(mobileVersionId);
			model.addAttribute("appType" ,ChannelEnum.MOBILE_VERSION_TYPE.values());
			model.addAttribute("mobileVersion" , mobileVersion);
			if(mobileVersionDetail != null){
				model.addAttribute("mobileVersionDetailList" , mobileVersionDetail.getClientUserIds().split(",").length);
				model.addAttribute("clientUserIds" , mobileVersionDetail.getClientUserIds());
			}
			model.addAttribute("clientId" , clientId);
			return "systemManager/showEditMobileVersion";
		}
		return "/dialog/error";	
	}
	
	/**
	 * 新增
	 * @param MobileVersion
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addMobileVersion")
	@ResponseBody
	public Object addMobileVersion(MobileVersion mobileVersion) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<addMobileVersion>");
		}
		if(mobileVersion != null && mobileVersion.getEnableDate() == null){
			mobileVersion.setEnableDate(new Date());
			
		}
		mobileVersionService.addMobileVersion(mobileVersion);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}

	/**
	 * 修改
	 * @param MobileVersion
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateMobileVersion", produces="application/json")
	@ResponseBody
	public Object updateMobileVersion(MobileVersion mobileVersion) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<updateMobileVersion>");
		}
		mobileVersionService.updateMobileVersion(mobileVersion);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**
	 * 删除
	 * @param mobileversionId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteMobileVersion/{mobileversionId}", produces="application/json")
	@ResponseBody	
	public Object deleteMobileversion(@PathVariable("mobileversionId")Integer mobileversionId) throws BusinessException  {
		if (log.isDebugEnabled()) {
			log.debug("start method<deleteMobileversion>");
		}
		mobileVersionService.deleteMobileVersion(mobileversionId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	/**
	 * 手机版本管理-新增-关联人员页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/relateClientUser")
	public String relateClientUser(Model model, Integer page, String searchName, Integer clientStructureId,Integer clientPositionId,String loginName, String structureSel, String structureName, HttpServletRequest req) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
			/**获取组织架构级别该部门的所有子部门**/
			String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
			/**subordinates 所有下级人员","号隔开**/
			String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
			/**subStructureId 从权限中获取所有部门","号隔开**/
			String subStructureId = getClientUser().getPermissionsData();
			if(subStructureId == null || subStructureId == "" ){
				subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
			}
			String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("name", searchName);
			parameters.put("loginName", loginName);
			parameters.put("clientPositionId", clientPositionId);
			parameters.put("subordinates", subordinates);
			parameters.put("subStructureId", deptIds);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			int count = clientUserService.queryClientUserCount(parameters);
			int pagenum = page == null ? 1 : page;
			Page<ClientUser> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize ());
			//parameters.put("_orderby", "CLIENT_USER_ID");
			//parameters.put("_order", "DESC");
			List<ClientUser> list = clientUserService.queryClientUserList(parameters);
			pageParam.setItems(list);
			List<ClientPosition> cpList = clientPositionService.findClientPositionsByClientId(clientId);
			model.addAttribute("cpList", cpList);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("searchName", searchName);
			model.addAttribute("loginName", loginName);
			model.addAttribute("structureName", structureName);
			model.addAttribute("searchClientStructureId", clientStructureId);
			model.addAttribute("structureSel", structureSel);
			model.addAttribute("clientPositionId", clientPositionId);
			model.addAttribute("page", pageParam.getPage().toString());
			return "/systemManager/relateClientUserList";
		}
		return "/dialog/error";	
	}
	
}
