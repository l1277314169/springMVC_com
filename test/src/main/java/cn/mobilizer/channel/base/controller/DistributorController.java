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
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Distributor;
import cn.mobilizer.channel.base.po.DistributorUserMapping;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.DistributorService;
import cn.mobilizer.channel.base.service.DistributorUserMappingService;
import cn.mobilizer.channel.base.service.DistrictService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.ProvinceService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;

/**
 * @author honger.liu
 * distributor管理Controller
 * 2014-11-20 17:54:20
 */

@Controller
@RequestMapping(value = "/distributor")
public class DistributorController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1997444411661968749L;
	@Autowired
	private DistributorService distributorService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private OptionsService OptionService;
	@Autowired
	private CityService cityService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private DistributorUserMappingService distributorUserMappingService;
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
//		List<TreeNodeVO> list = channelService.getTreeNodes(clientId,id);
//		return list;
//	}
//	
	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(){
		return "base/importDistrbutor";
	}
	
	 /**
	  * 经销商管理-查询列表
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
	public String query(Model model, Integer page, String distributorName,Integer clientStructureId, String structureSel,String distributorNo,Integer status,  HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		String subStructureId = null;
		clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
		
//		if(!getClientUser().getRoles().contains("admin")) {
//			/**获取组织架构级别该部门的上级部门**/
//			subStructureId = channelCommService.getParentStructrueIds(clientStructureId);
//		} else {
//			subStructureId = clientStructureId.toString();
//		}
		/**获取该部门的所有下级 和直属的 上一级**/
		subStructureId = channelCommService.getDistributorStructrueIds(clientStructureId);
		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("distributorName", distributorName);
		parameters.put("distributorNo", distributorNo);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subStructureId", subStructureId);
		parameters.put("status", status);
		int count = distributorService.queryDistributorCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<Distributor> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<Distributor> list = distributorService.queryDistributorList(parameters);
		pageParam.setItems(list);
		
		/**获取部门**/
//		TODO-获取部门tree
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("searchClientStructureId", clientStructureId);
		model.addAttribute("distributorName", distributorName);
		model.addAttribute("structureSel", structureSel);
		model.addAttribute("distributorNo", distributorNo);
		model.addAttribute("status", status);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/distributorList";
	}
	
	
	/**
	 * 经销商管理-新增页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showAddDistributor")
	public String showAddChannel(Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			/**
			 * 获取sysPosition的options数据，暂时每次新增都从数据库中查一下，以后考虑放入缓存中
			 * optionName = sys_positionType 
			 */
			List<Province> listprovince = provinceService.getAll();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			parameters.put("optionName", "distributor_type");
			List<Options> optionList=OptionService.queryOptions(parameters);
			model.addAttribute("optionList" , optionList);
			model.addAttribute("listprovince", listprovince);
			model.addAttribute("clientId" , clientId);
			return "base/showAddDistributor";
		}
		return "/dialog/error";	
	}

	/**
	 * 经销商管理-编辑页面
	 * @param distributorId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showEditDistributor/{distributorId}")
	public String editDistributor(@PathVariable("distributorId")Integer distributorId, Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			Distributor distributor = distributorService.findDistributorByprimaryKey(distributorId);
			List<Province> listProvince=provinceService.getAll();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("distributorId", distributor.getDistributorId());
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			parameters.put("optionName", "distributor_type");
			List<Distributor> distributors = distributorService.queryDistributorList(parameters);
			List<Options> optionList=OptionService.queryOptions(parameters);
			model.addAttribute("optionList" , optionList);
			model.addAttribute("distributor" , distributor);
			model.addAttribute("distributors" , distributors);
			model.addAttribute("clientId" , clientId);
			model.addAttribute("listProvince" , listProvince);
			return "base/showEditDistributor";
		}
		return "/dialog/error";	
	}
	/**
	 * 查看
	 * @param distributorId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showCheckDistributor/{distributorId}")
	public String checkDistributor(@PathVariable("distributorId")Integer distributorId, Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = user.clientId;
			Distributor distributor = distributorService.findDistributorByprimaryKey(distributorId);
			Province province=provinceService.getPrivinceById(distributor.getProvinceId());
			if(province != null ){
				distributor.setProvince(province.getProvince());
			}
			City city=cityService.getCityById(distributor.getCityId());
			if(city != null){
				distributor.setCity(city.getCity());
			}
			District district=districtService.getDistrictById(distributor.getDistrictId());
			if(district != null){
				distributor.setDistrict(district.getDistrict());
			}
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("distributorId", distributor.getDistributorId());
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			parameters.put("optionName", "distributor_type");
			DistributorUserMapping findDistributorUserMapping = distributorUserMappingService.findDistributorUserMapping(parameters);
			if(findDistributorUserMapping != null){
				ClientUser clientUser = clientUserService.findFullUserInfoByPrimaryKey(findDistributorUserMapping.getClientUserId());
				if(clientUser != null){
					distributor.setClientUserName(clientUser.getName());
				}
			}
			
//			List<Options> optionList=OptionService.queryOptions(parameters);
//			model.addAttribute("optionList" , optionList);
			model.addAttribute("distributor" , distributor);
		
			return "base/showCheckDistributor";
		}
		return "/dialog/error";	
	}
	/**
	 * 新增
	 * @param distributor
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addDistributor", produces="application/json")
	@ResponseBody
	public Object addDistributor(Distributor distributor) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<addDistributor>");
		}
		distributorService.addDistributor(distributor);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	
	
	/**
	 * 修改
	 * @param distributor
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateDistributor", produces="application/json")
	@ResponseBody
	public Object updateDistributor(Distributor distributor) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<updateDistributor>");
		}
		distributorService.updateDistributor(distributor);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**
	 * 删除
	 * @param distributorId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteDistributor/{distributorId}", produces="application/json")
	@ResponseBody	
	public Object deleteDistributor(@PathVariable("distributorId")Integer distributorId) throws BusinessException  {
		if (log.isDebugEnabled()) {
			log.debug("start method<deleteDistributor>");
		}
		int clientId = getClientId();
		distributorService.deleteDistributor(distributorId,clientId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}

	


	
	/**异步加载树，所属经销商
	 * @author Nany
	 * 2014年12月17日下午5:17:17
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getTreeNode", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object listNodes(@RequestParam(value = "id",defaultValue="-1") Integer id) {
		int clientId = getClientId ();
		String subStructureId = null;
//		String subStructureId = getClientUser().getPermissionsData();
		if(!getClientUser().getRoles().contains("admin")) {
			/**获取该用户所在的部门及其所有上级部门**/
			Integer clientStructureId = getClientStructureId();
			subStructureId = channelCommService.getParentStructrueIds(clientStructureId);
		}else {
			Integer clientStructureId =  getClientStructureId();
//			subStructureId = getClientStructureId().toString();
		}
		
		List<TreeNodeVO> list = distributorService.getTreeNodes(clientId,id,subStructureId);
//		List<Object> list = clientStructureService.getTreeNodes4StringList(clientId,pId);
//		String treeNodes = clientStructureService.getTreeNodes4String(clientId,pId);
		return list;
	}
	
	/**
	 * 
	 * @param clientUserId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getParentClientUser/{clientUserId}", produces="application/json")
	@ResponseBody
	public ClientUser getParentClientUser(@PathVariable("clientUserId")Integer clientUserId) throws BusinessException{
		int clientId = getClientId ();
		ClientUser clientUser = null;
		if(clientUserId != null ){
			clientUser=distributorService.getParentClientUser(clientUserId,clientId);
		}
		return clientUser;
	}
	


}
