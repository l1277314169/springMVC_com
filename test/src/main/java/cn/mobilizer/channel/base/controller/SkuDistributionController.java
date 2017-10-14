package cn.mobilizer.channel.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.po.SkuDistribution;
import cn.mobilizer.channel.base.service.SkuDistributionService;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.util.Constants;

/**产品分销管理
 * @author Nany
 * 2014年12月18日下午4:52:57
 */

@Controller
@RequestMapping(value="/skuDistribution")
public class SkuDistributionController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7342172462632306257L;
	
	@Autowired
	private SkuDistributionService skuDistributionService;
	@Autowired
	private SkuService skuService;
	/**产品分销管理-查询列表
	 * @author Nany
	 * 2014年12月18日下午5:11:30
	 * @return
	 */
	@RequestMapping(value="/query")
	public String query(Model model,Integer channelId,String channel,Integer chainId,String chain,Integer page,HttpServletRequest req) {
		
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("channelId", channelId);
		parameters.put("chainId", chainId);
		int count = skuDistributionService.queryCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<SkuDistribution> pageParam = Page.page(count,Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<SkuDistribution> list = skuDistributionService.skuDistributionList(parameters);
		
		pageParam.setItems(list);
		model.addAttribute("channelName", channel);
		model.addAttribute("chainName", chain);
		model.addAttribute("channelId", channelId);
		model.addAttribute("chainId", chainId);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/skuDistributionList";
	}
	
	/**分销管理--新增也页面
	 *
	 * @author Nany
	 * 2014年12月24日下午2:44:20
	 * @param model
	 * @param page
	 * @param skuName
	 * @param skuNo
	 * @param categoryId
	 * @param category
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/showAddSkuDistribution")
	public String showAddSkuDistribution(Model model,Integer page,String skuName,String skuNo,Integer categoryId,String category,
			HttpServletRequest req) {
		int clientId = getClientId ();
		model.addAttribute("clientId", clientId);
		return "/base/showAddSkuDistribution";
	}
	/**分销管理--新增--保存
	 * @author Nany
	 * 2014年12月25日下午5:39:46
	 * @param mustHaveSku
	 * @return
	 */
	@RequestMapping(value="/addSkuDistribution")
	@ResponseBody
	public Object addSkuDistribution(SkuDistribution skuDistribution) {
		skuDistributionService.addSkuDistribution(skuDistribution);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	/**产品分销--编辑
	 * @author Nany
	 * 2014年12月29日下午2:19:46
	 * @return
	 */
	@RequestMapping(value="/showEditSkuDistribution")
	public String showEditSkuDistribution(Model model,Integer skuDistributionId,HttpServletRequest req) {
		Integer clientId = getClientId();
		SkuDistribution skuDistribution = skuDistributionService.findSkuDistributionByParmars(clientId,skuDistributionId);
		String[] skuStr =(skuDistribution.getSkuIds() == null || skuDistribution.getSkuIds().equals(""))?null:skuDistribution.getSkuIds().split(",");
		if(skuStr != null){
			model.addAttribute("skuIdsCount", skuStr.length);
		}
		model.addAttribute("skuStr", skuStr);
		model.addAttribute("skuIds", skuDistribution.getSkuIds());
		model.addAttribute("skuDistribution", skuDistribution);
		model.addAttribute("clientId", clientId);
		return "/base/showEditSkuDistribution";
	}
	
	/**产品分销管理--编辑--保存
	 * @author Nany
	 * 2015年1月15日上午9:46:14
	 * @param mustHaveSku
	 * @return
	 */
	@RequestMapping(value="/editSkuDistribution")
	@ResponseBody
	public Object editSkuDistribution(SkuDistribution skuDistribution){
		skuDistributionService.updateSkuDistribution(skuDistribution);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**产品分销管理--删除
	 * @author Nany
	 * 2015年1月15日上午10:21:37
	 * @param channelId
	 * @param chainId
	 * @return
	 */
	@RequestMapping(value="/deleteSkuDistribution")
	@ResponseBody
	public Object deleteSkuDistribution(Integer skuDistributionId) {
		Integer clientId = getClientId();
		skuDistributionService.deleteSkuDistribution(clientId,skuDistributionId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
//	/**
//	 * @author Nany
//	 * 2015年1月14日下午4:37:44
//	 * @param channelId
//	 * @return
//	 */
//	@RequestMapping(value="/getChannelbyChannelId")
//	@ResponseBody
//	public Object getChannelbyChannelId(Integer channelId) {
//		Channel channel = channelService.findByPrimaryKey(channelId);
//		Channel parentChannel = channelService.findByPrimaryKey(channel.getParentId());
//		return parentChannel;
//	}
	/**
	 * 关联sku
	 * @return
	 */
	@RequestMapping(value="/showRelationDialog")
	public String showRelationDialog(Model model,Integer page,String skuName,String skuNo,Integer categoryId,String category,Integer brandId,String brand,Integer skuDistributionId,
			HttpServletRequest req){
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("skuName", skuName);
		parameters.put("skuNo", skuNo);
		parameters.put("categoryId", categoryId);
		parameters.put("brandId", brandId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = skuService.querySkuCount (parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<Sku> pageParam = Page.page(count,Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		parameters.put("skuDistributionId", skuDistributionId);
		List<Sku> list = skuService.querySkuList (parameters);
		pageParam.setItems(list);
		
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("skuName", skuName);
		model.addAttribute("skuNo",skuNo);
		model.addAttribute("category",category);
		model.addAttribute("brand",brand);
		model.addAttribute("categoryId",categoryId);
		model.addAttribute("brandId",brandId);
		model.addAttribute("count",count);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/showRelationSkuDistribution";
	}
	/**
	 *关联所有sku
	 * @return
	 */
	@RequestMapping(value = "/relevanceAllSku")
	@ResponseBody
	public Object relevanceAllSku(Model model,Integer page,String skuName,String skuNo,Integer categoryId,String category,Integer brandId,String brand,
			HttpServletRequest req){
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("skuName", skuName);
		parameters.put("skuNo", skuNo);
		parameters.put("categoryId", categoryId);
		parameters.put("brandId", brandId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		String AllSkuId = skuService.selectAllSkuIds(parameters);
		return AllSkuId;
	}
	/**
	 * 分销导入
	 * @return
	 */
	@RequestMapping(value = "showImportDialog")
	public String showImportDialog(){
		return "base/importSkuDistribution";
	}
	/**
	 * 查看
	 * @return
	 */
	@RequestMapping(value = "/lookSkuDistribution")
	public String lookSkuDistribution(Model model,Integer page,String skuName,String skuNo,Integer categoryId,String category,Integer brandId,String brand,Integer skuDistributionId,HttpServletRequest req){
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("skuName", skuName);
		parameters.put("skuNo", skuNo);
		parameters.put("categoryId", categoryId);
		parameters.put("brandId", brandId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("lookSkuDistributionId", skuDistributionId);
		int count = skuService.querySkuCount (parameters);
		int pagenum = page == null ? 1 : page;
		Page<Sku> pageParam = Page.page(count,Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<Sku> list = skuService.querySkuList (parameters);
		pageParam.setItems(list);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("skuName", skuName);
		model.addAttribute("skuNo",skuNo);
		model.addAttribute("category",category);
		model.addAttribute("brand",brand);
		model.addAttribute("categoryId",categoryId);
		model.addAttribute("brandId",brandId);
		model.addAttribute("page", pageParam.getPage().toString());
		return "base/showLookSkuDistribution";
	}
	/**
	 * 验证分组名称唯一性
	 * @param groupName
	 * @return
	 */
	@RequestMapping(value = "/onlySkuDistribution")
	@ResponseBody
	public Object onlySkuDistribution(String groupName){
		int clientId = getClientId ();
		List<SkuDistribution> skuDistributionObj = skuDistributionService.onlySkuDistribution(clientId, groupName);
		if(skuDistributionObj != null && skuDistributionObj.size() > 0){
			return	ResultMessage.SKUDISTRIBUTION_GROUPNAME;
		}else{
			return ResultMessage.WEB_OPERATE_SUCCESS;
		}
	}

}
