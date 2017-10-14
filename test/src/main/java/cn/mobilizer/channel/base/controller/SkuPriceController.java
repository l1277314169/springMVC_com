/**
 * 
 */
package cn.mobilizer.channel.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.po.PopSkuPriceGroupMapping;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.po.SkuPriceGroup;
import cn.mobilizer.channel.base.service.CategoryService;
import cn.mobilizer.channel.base.service.ChainService;
import cn.mobilizer.channel.base.service.ChannelService;
import cn.mobilizer.channel.base.service.DistributorService;
import cn.mobilizer.channel.base.service.PopSkuPriceGroupMappingService;
import cn.mobilizer.channel.base.service.SkuPriceGroupService;
import cn.mobilizer.channel.base.service.SkuPriceService;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.base.vo.SkuPriceVO;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;

/**
 * @author honger.liu
 * skuPrice管理Controller
 * 2014-11-20 17:54:20 
 */ 

@Controller
@RequestMapping(value = "/skuPrice")
public class SkuPriceController extends BaseActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4843013838738514575L;
	@Autowired
	private SkuPriceService skuPriceService;
	@Autowired
	private SkuPriceGroupService skuPriceGroupService;
	@Autowired
	private DistributorService distributorService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ChainService chainService;
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SkuService skuService;
	
	@Autowired
	private PopSkuPriceGroupMappingService popSkuPriceGroupMappingService;

//	/**
//	 * 异步加载树的子结点
//	 * @param id
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/getTreeNode", produces = "application/json; charset=UTF-8")
//	@ResponseBody
////	public Object listNodes(@RequestParam(value = "id",defaultValue="-1") Integer id) {
////		int clientId = getClientId ();
////		List<TreeNodeVO> list = chainService.getTreeNodes(clientId,id);
////		return list;
////	}
	
	
	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(){
		return "base/importClientUser";
	}
	
	 /**
	  * 价格管理-查询列表
	  * @param model
	  * @param page
	  * @param searchName
	  * @param req
	  * @return
	  * @throws BusinessException
	  */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page,Integer channelId,String channelName,Integer chainId,String chainName,Integer distributorId,String distributorName, HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("channelId", channelId);
		parameters.put("chainId", chainId);
		parameters.put("distributorId", distributorId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = skuPriceGroupService.queryCount(parameters);
		int pagenum = page == null ? 1 : page;
		Page<SkuPriceGroup> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
//		parameters.put("_orderby", "LAST_UPDATE_TIME");
//		parameters.put("_order", "DESC");
		List<SkuPriceGroup> list = skuPriceGroupService.findSkuPriceGroup(parameters);
		pageParam.setItems(list);
		List<SkuPriceGroup> skuPriceGroup=skuPriceGroupService.findSkuPriceGroup(parameters);
		/**获取部门**/
//		TODO-获取部门tree
		model.addAttribute("skuPriceGroup", skuPriceGroup);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("channelId", channelId);
		model.addAttribute("distributorId", distributorId);
		model.addAttribute("chainId", chainId);
		model.addAttribute("channelName", channelName);
		model.addAttribute("chainName", chainName);
		model.addAttribute("distributorName", distributorName);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/skuPriceList";
	}
	
	/**
	 * 价格管理-新增页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showAddSkuPrice")
	public String showAddSkuPrice(Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			int clientId = getClientId ();
			model.addAttribute("clientId", clientId);
			return "base/showAddSkuPrice";
		}
		return "/dialog/error";	
	}
	
	

	/**
	 * 价格管理-编辑页面
	 * @param skuPriceId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showEditSkuPrice/{skuPriceGroupId}")
	public String editSkuPrice(@PathVariable("skuPriceGroupId")Integer skuPriceGroupId, Model model) throws BusinessException{
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			int clientId = getClientId ();
			parameters.put("clientId", clientId);
			parameters.put("skuPriceGroupId", skuPriceGroupId);
			SkuPriceGroup skuPriceGroup = skuPriceGroupService.getSkuPriceGroup(parameters);
			List<SkuPriceVO> skuPriceVOList = skuPriceService.skuPriceVOList(parameters);
			JSONArray fromObject = JSONArray.fromObject(skuPriceVOList);
			skuPriceGroup.setSkuIdAndPrices(fromObject.toString());
			String[] skuStr =(skuPriceGroup.getSkuNumber() == null || skuPriceGroup.getSkuNumber().equals(""))?null:skuPriceGroup.getSkuNumber().split(",");
			if(skuStr != null){
				model.addAttribute("skuIdsCount", skuStr.length);
			}
			model.addAttribute("skuStr", skuStr);
			model.addAttribute("clientId", clientId);
			model.addAttribute("skuPriceGroup", skuPriceGroup);
			return "base/showEditSkuPrice";
		}
		return "/dialog/error";	
	}
	/**
	 * 新增
	 * @param SkuPrice
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addSkuPrice", produces="application/json")
	@ResponseBody
	public Object addSkuPrice(PopSkuPriceGroupMapping popSkuPriceGroupMapping) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<addSkuPrice>");
		}
		popSkuPriceGroupMappingService.addSkuPriceList(popSkuPriceGroupMapping);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}

	/**
	 * 修改
	 * @param SkuPrice
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateSkuPrice", produces="application/json")
	@ResponseBody
	public Object updateSkuPrice(PopSkuPriceGroupMapping popSkuPriceGroupMapping) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<updateSkuPrice>");
		}
		popSkuPriceGroupMappingService.updateSkuPriceGroupMapping(popSkuPriceGroupMapping);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**
	 * 删除
	 * @param skuPriceId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteSkuPrice")
	@ResponseBody	
	public Object deleteSkuPrice(Integer skuPriceId) throws BusinessException  {
		if (log.isDebugEnabled()) {
			log.debug("start method<deleteSkuPrice>");
		}
		skuPriceGroupService.deleteSkuPriceGroup(skuPriceId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	/**
	 * 关联产品价格
	 * @param page
	 * @param model
	 * @param brandId
	 * @param brandName
	 * @param categoryId
	 * @param categoryName
	 * @param skuName
	 * @param skuId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/showSkuPriceRelationDialog")
	public String showSkuPriceRelationDialog(Integer skuPriceGroupId,Integer page,Model model,String skuNo,Integer brandId,String brand,Integer categoryId,String category,String  skuName,Integer skuId,String addSkuPrice, HttpServletRequest req ){
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("categoryId", categoryId);
		parameters.put("brandId", brandId);
		parameters.put("skuName", skuName);
		parameters.put("skuId", skuId);
		parameters.put("skuNo", skuNo);
		parameters.put("clientId", clientId);
		parameters.put("skuPriceGroupId", skuPriceGroupId);
		int count = skuService.querySkuCount(parameters);
		int pagenum = page == null ? 1 : page;
		Page<Sku> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<Sku> skuList=skuService.querySkuList(parameters);
		pageParam.setItems(skuList);
		model.addAttribute("pageParam",pageParam);
		model.addAttribute("skuNo",skuNo);
		model.addAttribute("skuName",skuName);
		model.addAttribute("brand",brand);
		model.addAttribute("brandId",brandId);
		model.addAttribute("category",category);
		model.addAttribute("categoryId",categoryId);
		model.addAttribute("count",count);
		model.addAttribute("addSkuPrice",addSkuPrice);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/showSkuPriceRelationDialog";
	}
	/**
	 * 选中所有产品
	 * @return
	 */
	@RequestMapping(value = "/relevanceAllSku")
	@ResponseBody
	public List<SkuPriceVO> relevanceAllSku(String skuNo,Integer brandId,String brand,Integer categoryId,String category,String  skuName,Integer skuId){
		int clientId = getClientId();
		List<SkuPriceVO> skuPriceJson = skuService.getSkuPriceJson(clientId, brandId, categoryId, skuName, skuNo);
		return skuPriceJson;
		
	}
	/**
	 * 远程验证分组名称
	 * @return
	 */
	@RequestMapping(value = "/ajaxSkuPrice")
	@ResponseBody
	public Boolean ajaxSkuPrice(String groupName,String oldGroupName){
		int clientId = getClientId();
		if(oldGroupName != null && !"".equals(oldGroupName) && groupName.equals(oldGroupName)){
			return true;
		}
		SkuPriceGroup skuPriceGroup = skuPriceGroupService.ajaxValidation(clientId, groupName);
		if(skuPriceGroup != null){
			return false;
		}else{
			return true;
		}
		
	}
}
