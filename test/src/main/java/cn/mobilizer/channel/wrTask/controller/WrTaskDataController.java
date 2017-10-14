package cn.mobilizer.channel.wrTask.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.comm.init.InitOptionsBean;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.visit.service.VisitingTaskDetailDataService;
import cn.mobilizer.channel.wrTask.po.WrCustomer;
import cn.mobilizer.channel.wrTask.po.WrCustomerBrandMapping;
import cn.mobilizer.channel.wrTask.po.WrTaskData;
import cn.mobilizer.channel.wrTask.po.WrTaskDetailData;
import cn.mobilizer.channel.wrTask.po.WrTaskParameter;
import cn.mobilizer.channel.wrTask.po.WrWorkplace;
import cn.mobilizer.channel.wrTask.service.WrCustomerBrandMappingService;
import cn.mobilizer.channel.wrTask.service.WrCustomerService;
import cn.mobilizer.channel.wrTask.service.WrTaskDataService;
import cn.mobilizer.channel.wrTask.service.WrTaskDetailDataService;
import cn.mobilizer.channel.wrTask.service.WrTaskParameterService;
import cn.mobilizer.channel.wrTask.service.WrTaskService;
import cn.mobilizer.channel.wrTask.service.WrWorkPlaceService;

/**
 * @author LiuYong
 * @date 2015年6月5日 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/wrTaskData")
public class WrTaskDataController extends BaseActionSupport {
	 
	@Autowired
	private WrTaskDetailDataService wrTaskDetailDataService;
	@Autowired
	private WrTaskService wrTaskService;
	@Autowired
	private WrTaskDataService wrTaskDataService;
	@Autowired
	private WrTaskParameterService wrTaskParameterService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private WrWorkPlaceService wrWorkPlaceService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private WrCustomerService wrCustomerService;
	@Autowired
	private WrCustomerBrandMappingService wrCustomerBrandMappingService;
	@Autowired
	private BrandService brandService;
	
	/**
	 * 查看工作任务详情
	 * @param model
	 * @param finishDate
	 * @return
	 */
	@RequestMapping(value = "/showWrTaskDetail")
	public String showWrTaskDetail(Model model,String finishDate,Integer clientUserId,String wrTaskDataId){
		Integer clientId = getClientId();
		WrTaskData wrTaskData = wrTaskDataService.selectByPrimaryKey(wrTaskDataId);
		if(wrTaskData!=null && wrTaskData.getWrTaskId() != null){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("clientId", clientId);
			param.put("wrTaskId", wrTaskData.getWrTaskId());
			param.put("_orderby", "sequence");
			param.put("_order", "asc");
			List<WrTaskParameter> wrTaskParameters = wrTaskParameterService.selectByParams(param);
			model.addAttribute("wrTaskParameters", wrTaskParameters);
			
			//项目类型
			String projectName = optionsService.getOptionText("project_type", wrTaskData.getProjectType().byteValue(), clientId);
			model.addAttribute("projectName", projectName);
			WrWorkplace wrWorkplace = wrWorkPlaceService.selectByPrimaryKey(wrTaskData.getWorkplaceId());
			if(wrWorkplace!=null){
				model.addAttribute("workplace", wrWorkplace.getWorkplace());
			}
			if(wrWorkplace != null && wrWorkplace.getWorkplaceType() != null && wrWorkplace.getWorkplaceType().intValue() == 1){			//类型为1时需要关联门店
				Store store = storeService.selectByPrimaryKey(wrTaskData.getStoreId());
				if(store!=null){
					model.addAttribute("storeName", store.getStoreName());
				}
			}else{
				WrCustomer wrCustomer = wrCustomerService.selectByPrimaryKey(wrTaskData.getCustomerId(),clientId);
				if(wrCustomer!=null){
					model.addAttribute("customerName", wrCustomer.getCustomerName());
				}
				WrCustomerBrandMapping wrCustomerBrandMapping = wrCustomerBrandMappingService.getWrCustomerBrandMappingByCusId(clientId,wrCustomer.getCustomerId());
				if(wrCustomerBrandMapping!=null){
					Brand brand = brandService.getBrand(wrCustomerBrandMapping.getBrandId());
					model.addAttribute("brandName", brand.getBrandName());
				}
			}
		}		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("clientUserId", clientUserId);
		params.put("wrTaskDataId", wrTaskDataId);
		params.put("startTime", DateUtil.getDayStart(finishDate));
		params.put("endTime", DateUtil.getDayEnd(finishDate));
		List<WrTaskDetailData> wrTaskDetailDatas = wrTaskDetailDataService.selectByParams(params);
		Map<String,Object> parameterValueMap = null;
		if(wrTaskDetailDatas!=null){		
			Map<String,Map<String,Map<String,String>>> optionMap = InitOptionsBean.optionMap;
			parameterValueMap = new HashMap<String, Object>();
			for(WrTaskDetailData wrTaskDetailData : wrTaskDetailDatas){
				String value = channelCommService.getPoraControlValue(wrTaskDetailData.getControlType(), wrTaskDetailData.getOptionName(), wrTaskDetailData.getValue(), optionMap, clientId);	
				parameterValueMap.put(wrTaskDetailData.getWrTaskParameterId().toString(), value);	
			}
		}
		if(wrTaskData!=null){
			Store store = storeService.selectByPrimaryKey(wrTaskData.getStoreId());
			model.addAttribute("store", store);
		}
		model.addAttribute("parameterValueMap", parameterValueMap);
		model.addAttribute("finishDate", finishDate);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("clientId", clientId);
		model.addAttribute("wrTaskData", wrTaskData);
		return "/wrTask/showWrTaskDetail";
	}
}
