/**
 * 
 */
package cn.mobilizer.channel.visit.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.visit.po.VisitingTask;
import cn.mobilizer.channel.visit.service.VisitingTaskService;
import cn.mobilizer.channel.visit.vo.VisitingTaskVO;
import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.PromotionMaterial;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreGroup;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.service.CategoryService;
import cn.mobilizer.channel.base.service.ChannelService;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.PromotionMaterialService;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_DATA_CONTROL_TYPE;
import cn.mobilizer.channel.comm.vo.ChannelEnum.TASK_STEP_SELECT_TYPE;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_POP_TYPE;
import cn.mobilizer.channel.comm.vo.ChannelEnum.MOBILE_SORT_BY;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISITING_TASK_TYPE;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_TASK_STEP_TYPE;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;

/**
 * @author yeeda.tian
 * 拜访任务Controller
 * 2014-11-15 21:37:36
 */

@Controller
@RequestMapping(value = "/visit")
public class VisitingTaskController extends BaseActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8651502976076192726L;
	
	@Autowired
	private VisitingTaskService visitingTaskService;
	@Autowired
	private ClientPositionService clientPositionService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PromotionMaterialService promotionMaterialService;
	
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, Integer clientPositionId,String searchVisitingTaskName, HttpServletRequest req) throws BusinessException{
		
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("clientPositionId", clientPositionId);
		parameters.put("visitingTaskName", searchVisitingTaskName);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = visitingTaskService.queryVisitingTaskCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<VisitingTask> pageParam = Page.page(count, 10, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		parameters.put("_orderby", "LAST_UPDATE_TIME");
		parameters.put("_order", "DESC");
		List<VisitingTask> list = visitingTaskService.queryVisitingTaskList(parameters);
		pageParam.setItems(list);
		List<ClientPosition> cpList = clientPositionService.findClientPositionsByClientId(getClientId());
		
		model.addAttribute("cpList", cpList);
		model.addAttribute("pageParam", pageParam);
		parameters.put("searchVisitingTaskName", searchVisitingTaskName);
		model.addAttribute("page", pageParam.getPage().toString());
		
		return "/visit/visitingTaskList";
	}
	
	/**
	 * 打开添加任务页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showAddVisitingTask")
	public String showAddVisitingTask(Model model) throws BusinessException{
		/**用户信息**/
		int clientId = getClientId ();
		/**该用户所在企业的职位信息**/
		List<ClientPosition> cpList = clientPositionService.findClientPositionsByClientId (clientId);
		/**该用户所在企业的门店的分组信息**/
		List<StoreGroup> sgList = storeService.findStoreGroupsByClientId (clientId);
		
		/**该用户所在企业的渠道信息**/
//		List<Channel> clList = channelService.findChannelsByClientId (clientId);
//		model.addAttribute ("cpList", cpList);
		
		model.addAttribute ("sgList", sgList);
		/**终端类型**/
		model.addAttribute ("popType", VISIT_POP_TYPE.values());
		/**拜访任务类型**/
		model.addAttribute ("taskType", VISITING_TASK_TYPE.values());
		model.addAttribute ("cpList", cpList);
		model.addAttribute ("clientId", clientId);
		return "/visit/showAddVisitingTask";
	}
	
	@RequestMapping(value = "/showEditVisitingTask/{visitingTaskId}")
	public String showEditVisitingTask(Model model,@PathVariable("visitingTaskId")Integer visitingTaskId) throws BusinessException{
		/**用户信息**/
		int clientId = getClientId ();
		/**该用户所在企业的职位信息**/
		List<ClientPosition> cpList = clientPositionService.findClientPositionsByClientId (clientId);
		/**该用户所在企业的门店的分组信息**/
		List<StoreGroup> sgList = storeService.findStoreGroupsByClientId (clientId);
		
		/**通过visitingTaskId查询此拜访任务的相关数据**/
		VisitingTaskVO visitingTaskVO = visitingTaskService.findVisitingTaskVOByTaskId(visitingTaskId);
		
		model.addAttribute ("cpList", cpList);
		model.addAttribute ("sgList", sgList);
		/*终端类型*/
		model.addAttribute ("popType", VISIT_POP_TYPE.values());
		/*分组方式*/
		model.addAttribute ("sortBys", MOBILE_SORT_BY.values());
		/**拜访任务类型**/
		model.addAttribute ("taskType", VISITING_TASK_TYPE.values());
		/*拜访对象选择方式*/		
		model.addAttribute ("selectTypes", TASK_STEP_SELECT_TYPE.values());
		Map<String,String> controlTypeMap = new HashMap<String, String>();
		for(ChannelEnum.VISIT_DATA_CONTROL_TYPE item:ChannelEnum.VISIT_DATA_CONTROL_TYPE.values()){
			controlTypeMap.put(item.getKey()+"", item.getCnName());
		}
		model.addAttribute ("controlTypeMap", controlTypeMap);
		model.addAttribute ("clientId", clientId);
		model.addAttribute("visitingTaskVO", visitingTaskVO);
		return "/visit/showEditVisitingTask";
	}
	
	@RequestMapping(value = "/addVisitingTask", produces="application/json")
	@ResponseBody
	public Object addVisitingTask(VisitingTaskVO visitingTaskVO,Integer channelId) throws BusinessException{
		log.info ("新增-任务");
		visitingTaskService.saveVisitingTaskVO(visitingTaskVO,channelId);
		
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	@RequestMapping(value = "/addVisitingTaskPost")
	public String addVisitingTaskPost(VisitingTaskVO visitingTaskVO,Integer channelId) throws BusinessException{
		log.info ("新增-任务-post");
		visitingTaskService.saveVisitingTaskVO(visitingTaskVO,channelId);
		
		return "redirect:/visit/query";
	}
	
	@RequestMapping(value = "/updateVisitingTask", produces="application/json")
	@ResponseBody
	public Object updateVisitingTask(VisitingTaskVO visitingTaskVO,Integer channelId) throws BusinessException{
		log.info ("新增-任务");
		visitingTaskService.updateVisitingTaskVO(visitingTaskVO,channelId);
		
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	@RequestMapping("/showAddVisitingTaskStep/{step}")
	public String showAddVisitingTaskStep(Model model, HttpServletRequest req, HttpServletResponse Resp,@PathVariable("step")Integer step) throws BusinessException {
		log.info("step值："+step+"---------当前步骤："+(step+1));
		
		int s = (step != null && step >= 0) ? step : 0;
		/**步骤类型**/
		model.addAttribute ("stepType", VISIT_TASK_STEP_TYPE.values ());
		/**控件参数类型**/
		model.addAttribute ("controlType", VISIT_DATA_CONTROL_TYPE.values ());
		/**分组方式**/
		model.addAttribute ("sortBys", MOBILE_SORT_BY.values ());
		/**拜访对象选择方式**/
		model.addAttribute ("selectTypes", TASK_STEP_SELECT_TYPE.values ());
		model.addAttribute ("step", s);
		return "/visit/showAddVisitingTaskStep";
	}
	
	@RequestMapping("/showEditVisitingTaskStep/{step}")
	public String showEditVisitingTaskStep(Model model, HttpServletRequest req, HttpServletResponse Resp,@PathVariable("step")Integer step) throws BusinessException {
		log.info("step值："+step+"---------当前步骤："+(step+1));
		
		int s = (step != null && step >= 0) ? step : 0;
		/**步骤类型**/
		model.addAttribute ("stepType", VISIT_TASK_STEP_TYPE.values ());
		/**控件参数类型**/
		model.addAttribute ("controlType", VISIT_DATA_CONTROL_TYPE.values ());
		/**分组方式**/
		model.addAttribute ("sortBys", MOBILE_SORT_BY.values ());
		/**拜访对象选择方式**/
		model.addAttribute ("selectTypes", TASK_STEP_SELECT_TYPE.values ());
		model.addAttribute ("step", s);
		return "/visit/showEditVisitingTaskStep";
	}
	
	@RequestMapping("/showPopTypeParameter")
	public String showPopTypeParameter(Model model, Integer page, HttpServletRequest req, int key, String searchStoreName, String searchAddr) throws BusinessException {
		log.info("当前终端类型："+key);
		
		if(VISIT_POP_TYPE.STORE.getKey () == key) {
			int clientId = getClientId ();
//			int clientStructureId = getClientStructureId ();
			//获取所有的子部门
			//TODO
//			List<ClientStructure> structureIds = findClientStructuresByParentId();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
//			parameters.put("clientStructureId", clientStructureId);
			parameters.put("storeName", searchStoreName);
			parameters.put("addr", searchAddr);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			int count = storeService.queryPopStoreCount (parameters);
			
			int pagenum = page == null ? 1 : page;
			Page<Store> pageParam = Page.page(count, 10, pagenum);
			pageParam.buildUrl(req);
//			pageParam.buildJSONUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize ());
			List<Store> list = storeService.queryPopStoreList (parameters);
			pageParam.setItems(list);
			
			model.addAttribute("key", key);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("searchStoreName", searchStoreName);
			model.addAttribute("searchAddr", searchAddr);
			model.addAttribute("page", pageParam.getPage().toString());
			
			return "/visit/showPopStore";
		} else if (VISIT_POP_TYPE.DISTRIBUTOR.getKey () == key){
			return "/visit/showPopDistributor";
		} 
		return null;
	}
	
	@RequestMapping("/showStepTypeParameter")
	public String showStepTypeParameter(Model model, Integer page, HttpServletRequest req, Byte key, Integer step, Sku sku, Brand brand,Category category, PromotionMaterial promotionMaterial) throws BusinessException {
		log.info("当前终端类型："+key+"and 当前步骤："+step);
		if(VISIT_TASK_STEP_TYPE.RELATED_SKU.getKey ().equals (key)) {
			int clientId = getClientId ();
			String skuName = sku.getSkuName ();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("skuName", skuName);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			int count = skuService.querySkuCount (parameters);
			
			int pagenum = page == null ? 1 : page;
			Page<Sku> pageParam = Page.page(count, 10, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize ());
			List<Sku> list = skuService.querySkuList (parameters);
			pageParam.setItems(list);
			
			model.addAttribute("key", key);
			model.addAttribute("step", step);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("page", pageParam.getPage().toString());
			
			return "/visit/showPopSku";
		} else if (VISIT_TASK_STEP_TYPE.RELATED_BRAND.getKey ().equals (key)){
			int clientId = getClientId ();
			String brandName = brand.getBrandName ();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("brandName", brandName);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			int count = brandService.queryBrandCount (parameters);
			
			int pagenum = page == null ? 1 : page;
			Page<Brand> pageParam = Page.page(count, 10, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize ());
			List<Brand> list = brandService.queryBrandList (parameters);
			pageParam.setItems(list);
			
			model.addAttribute("key", key);
			model.addAttribute("step", step);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("page", pageParam.getPage().toString());
			
			return "/visit/showPopBrand";
		} else if (VISIT_TASK_STEP_TYPE.RELATED_CATEGORY.getKey ().equals (key)){
			int clientId = getClientId ();
			String categoryName = category.getCategoryName ();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("categoryName", categoryName);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			int count = categoryService.queryCategoryCount (parameters);
			
			int pagenum = page == null ? 1 : page;
			Page<Category> pageParam = Page.page(count, 10, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize ());
			List<Category> list = categoryService.queryCategoryList (parameters);
			pageParam.setItems(list);
			
			model.addAttribute("key", key);
			model.addAttribute("step", step);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("page", pageParam.getPage().toString());
			
			return "/visit/showPopCategory";
		} else if (VISIT_TASK_STEP_TYPE.RELATED_PROMOTION.getKey ().equals (key)){
			int clientId = getClientId ();
			String materialName = promotionMaterial.getMaterialName ();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("materialName", materialName);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			int count = promotionMaterialService.queryPromotionMaterialCount (parameters);
			
			int pagenum = page == null ? 1 : page;
			Page<PromotionMaterial> pageParam = Page.page(count, 10, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize ());
			List<PromotionMaterial> list = promotionMaterialService.queryPromotionMaterialList (parameters);
			pageParam.setItems(list);
			
			model.addAttribute("key", key);
			model.addAttribute("step", step);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("page", pageParam.getPage().toString());
			
			return "/visit/showPopPromotion";
		} 
		return null;
	}
	
	/**
	 * 查看拜访任务详细
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showVisitingTaskDetail/{visitingTaskId}")
	public String showVisitingTaskDetail(Model model,@PathVariable("visitingTaskId")Integer visitingTaskId) throws BusinessException{
		/**用户信息**/
		int clientId = getClientId ();
		/**获取任务主表数据**/
		VisitingTaskVO visitingTaskVO = visitingTaskService.findVisitingTaskVOByTaskId(visitingTaskId);
		
		/**终端类型**/
		model.addAttribute ("popType", VISIT_POP_TYPE.values ());
		/**拜访任务类型**/
		model.addAttribute ("taskType", VISITING_TASK_TYPE.values ());
		
		return "/visit/showVisitingTaskDetail";
	}
}
