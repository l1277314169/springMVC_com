package cn.mobilizer.channel.mobile.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.comm.init.InitOptionsBean;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.ctTask.po.CtTask;
import cn.mobilizer.channel.ctTask.po.CtTaskData;
import cn.mobilizer.channel.ctTask.po.CtTaskDetailData;
import cn.mobilizer.channel.ctTask.po.CtTaskObject;
import cn.mobilizer.channel.ctTask.po.CtTaskParameter;
import cn.mobilizer.channel.ctTask.service.CtTaskDataService;
import cn.mobilizer.channel.ctTask.service.CtTaskObjectService;
import cn.mobilizer.channel.ctTask.service.CtTaskParameterService;
import cn.mobilizer.channel.ctTask.service.CtTaskService;
import cn.mobilizer.channel.ctTask.vo.CtTaskDataSerchVo;
import cn.mobilizer.channel.ctTask.vo.StoreTask;
import cn.mobilizer.channel.util.Constants;

@Controller
@RequestMapping(value = "/mobile/ctTask")
public class AppCtTaskController extends BaseActionSupport  {
	
	@Autowired
	private CtTaskService ctTaskService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private CtTaskObjectService ctTaskObjectService;
	@Autowired
	private CtTaskParameterService ctTaskParameterService;
	@Autowired
	private CtTaskDataService ctTaskDataService;
	@Autowired
	private ClientUserService clientUserService;
	
	/**
	 * 显示门店历史数据列表
	 * @param model
	 * @param page
	 * @param storeTask
	 * @param req
	 * @return
	 */
	@RequestMapping("ctTaskDataQuery/{clientUserId}/{token}")
    public String showCtTask(Model model,@PathVariable("token")String token,@PathVariable("clientUserId")Integer clientUserId){
		ClientUser clientUser = clientUserService.selectByPrimaryKey(clientUserId);
		if(clientUser!=null){
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("clientId", clientUser.getClientId());
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			List<CtTask> ctTasks = ctTaskService.selectByParams(params);
			model.addAttribute("ctTasks", ctTasks);
			model.addAttribute("clientId", clientUser.getClientId());
			model.addAttribute("clientUserId", clientUserId);
		}
		model.addAttribute("clientUser", clientUser);
		return "/mobile/ctTaskDataQuery";
	}
	
	@RequestMapping("ctTaskDataList")
    public String showCtTask(Model model,StoreTask storeTask,Integer clientUserId,HttpServletRequest req){
		if(StringUtil.isEmptyString(storeTask.getStartDate())){
			storeTask.setStartDate("2015-01-01");     //如果没有填写时间默认这个时间
		}
		if(StringUtil.isEmptyString(storeTask.getEndDate())){
			storeTask.setEndDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		}
		Boolean flag = true;
		storeTask.setIsMobileSearch((byte)1);
		CtTask ctTask = ctTaskService.getCtTask(storeTask.getCtTaskId());	
		storeTask.setCycle(ctTask.getCycleType());
		storeTask.setClientId(storeTask.getClientId());
		if(StringUtils.isEmpty(storeTask.getWorkerUserName())){
			String clientUserIds = channelCommService.getSubordinates(clientUserId.toString());
			storeTask.setUserIds(clientUserIds);
			ClientUser clientUser = clientUserService.selectByPrimaryKey(clientUserId);
			/**获取组织架构级别该部门的所有子部门**/
			String subAllStructureId = channelCommService.getSubStructrueIds(clientUser.getClientStructureId());
			storeTask.setSubAllStructureId(subAllStructureId);
		}else{
			List<ClientUser> clientUsers = clientUserService.selectClientUserByName(storeTask.getClientId(),storeTask.getWorkerUserName());
			String userIds = "";
			if(clientUsers!=null && clientUsers.size()>0){
				for(ClientUser clientUser : clientUsers){
					String clientUserIds = StringUtil.removeStrComma(channelCommService.getSubordinates(clientUser.getClientUserId().toString()));
					if(StringUtils.isNotEmpty(clientUserIds)){
						userIds = userIds +","+clientUserIds;
					}
				}
			}
			String clientUserIds = channelCommService.getSubordinates(clientUserId.toString());
			//获取两个oldStoreIdStrs与storeIds的交集
			String userIds2 =StringUtil.stringIntersect(clientUserIds,userIds);
			userIds2 = StringUtil.removeStrComma(userIds2);
			if(StringUtils.isEmpty(userIds2)){
				flag = false;			//如果没有找到下级，则不存在该人员
			}else{
				storeTask.setUserIds(userIds2);
			}
		}
		List<StoreTask> storeTasks = new ArrayList<StoreTask>();
		if(flag){
			storeTasks = ctTaskService.selectTaskDataStore(storeTask,null,null,storeTask.getClientId());
		} 
		model.addAttribute("clientId", storeTask.getClientId());
		model.addAttribute("clientUserId", clientUserId);	
		model.addAttribute("storeTasks", storeTasks);	
		model.addAttribute("ctTask", ctTask);
		return "/mobile/ctTaskDataList";
	}
	
	@RequestMapping("ctTaskDataDetailList")
	public String showCtTaskDataDetail(Model model, Integer page,CtTaskDataSerchVo ctTaskDataSerchVo){
		CtTask ctTask = ctTaskService.getCtTask(ctTaskDataSerchVo.getCtTaskId());
		List<CtTaskObject> ctTaskObjects = ctTaskObjectService.selectTaskObjectBySku(ctTaskDataSerchVo);	
		List<CtTaskParameter> ctTaskParameters = ctTaskParameterService.selectByParams(ctTaskDataSerchVo.getClientId(),ctTaskDataSerchVo.getCtTaskId());
		model.addAttribute("ctTaskParameters", ctTaskParameters);	
		model.addAttribute("ctTaskObjects", ctTaskObjects);	
		model.addAttribute("ctTask", ctTask);	
		model.addAttribute("popId", ctTaskDataSerchVo.getPopId());	
		model.addAttribute("ctTaskDataSerchVo", ctTaskDataSerchVo);	
		//根据门店和任务获取数据
		CtTaskData ctTaskData = ctTaskDataService.getCtTaskDataById(ctTaskDataSerchVo.getCtTaskDataId());
		Map<String,Map<String,Map<String,String>>> optionMap = InitOptionsBean.optionMap;
		if(ctTaskData!=null){
			Map<String,Object> parameterMap = new HashMap<String, Object>();
			for(CtTaskDetailData ctTaskDetailData : ctTaskData.getCtTaskDetailDatas()){
				//将数据以“_”拼接，用参数和对象来确定对象的参数值，供页面展现
				CtTaskParameter ctTaskParameter = ctTaskParameterService.selectByPrimaryKey(ctTaskDetailData.getCtTaskParameterId());
				String value = channelCommService.getPoraControlValue(ctTaskParameter.getControlType(), ctTaskParameter.getOptionName(), ctTaskDetailData.getValue(), optionMap, ctTaskDataSerchVo.getClientId());	
				parameterMap.put(ctTaskDetailData.getCtTaskParameterId()+"_"+(ctTaskDetailData.getTarget1Id()==null?"":ctTaskDetailData.getTarget1Id()), value);	
			}
			model.addAttribute("parameterMap", parameterMap);
		}	
		return "/mobile/ctTaskDataDetailList";
	}
	
}
