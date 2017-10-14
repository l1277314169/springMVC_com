package cn.mobilizer.channel.ctTask.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.SkuService;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.ctTask.po.CtTask;
import cn.mobilizer.channel.ctTask.po.CtTaskObject;
import cn.mobilizer.channel.ctTask.service.CtTaskDataService;
import cn.mobilizer.channel.ctTask.service.CtTaskObjectService;
import cn.mobilizer.channel.ctTask.service.CtTaskParameterService;
import cn.mobilizer.channel.ctTask.service.CtTaskService;

@Controller
@RequestMapping(value = "/ctTaskParameter")
public class CtTaskParameterController extends BaseActionSupport{
	
	@Autowired
	private CtTaskParameterService cTaskParameterService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private CtTaskObjectService ctTaskObjectService;
	@Autowired
	private CtTaskService ctTaskService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private CtTaskDataService ctTaskDataService;
	
	@RequestMapping(value = "/query")
	public String ctTaskParameter(Model model, Integer page,Integer ctTaskId,HttpServletRequest req){
		Integer clientId = getClientId ();
		CtTask ctTask = ctTaskService.getCtTask(ctTaskId);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("ctTaskId", ctTaskId);
		List<CtTaskObject> ctTaskObjects = ctTaskObjectService.selectByParams(clientId,ctTaskId);
		for(CtTaskObject ctTaskObject : ctTaskObjects){
			if(ctTask.getTaskType().intValue() == 1){
				Sku sku = skuService.getSku(new Integer(ctTaskObject.getTarget1Id()));
				ctTaskObject.setObjectName(sku.getSkuName());
			}
		}		
		params.put("_orderby", "sequence");
		params.put("_order", "ASC");		
		model.addAttribute("ctTaskObjects", ctTaskObjects);		
		return "/ctTask/ctTaskParameterList";
	}	
	
	@RequestMapping(value = "/getOptionByName")
	@ResponseBody
	public List<Options> getStatus(String optionName){
		Integer clientId = getClientId();
		List<Options> ls = optionsService.findOptionsByOptionName(optionName, clientId);
		return ls;
	}
}
