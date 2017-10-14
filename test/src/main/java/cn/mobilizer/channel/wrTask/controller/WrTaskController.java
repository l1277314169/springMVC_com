package cn.mobilizer.channel.wrTask.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.apache.bcel.generic.InstructionConstants.Clinit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.wrTask.po.WrTaskData;
import cn.mobilizer.channel.wrTask.service.WrTaskDataService;
import cn.mobilizer.channel.wrTask.service.WrTaskService;
import cn.mobilizer.channel.wrTask.vo.WrTaskFinishCount;

/**
 * @author LiuYong
 * @date 2015年6月5日 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/wrTask")
public class WrTaskController extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	 
	@Autowired
	private WrTaskDataService wrTaskDataService;
	@Autowired
	private WrTaskService wrTaskService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	
	/**
	 * 查询工作任务列表
	 * @param model
	 * @param page
	 * @param ctTaskGroup
	 * @param taskName
	 * @param req
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page,Integer structureId,String userName,String startTime,String endTime,HttpServletRequest req) throws BusinessException {
		Integer clientId = getClientId();
		Integer clientUserId = getCurrentUserId();
		ClientUser clientUser = getClientUser();
		structureId = structureId == null ? getClientStructureId() : structureId;
		Map<String, Object> params = new HashMap<String, Object>();
		if(!clientUser.getRoles().contains("admin")){
			String clientUserIds = channelCommService.getSubordinates(clientUserId.toString());
			String subAllStructureId = channelCommService.getSubStructrueIds(structureId);
			//**subStructureId 从权限中获取所有部门","号隔开**//*
			String subStructureId = getClientUser().getPermissionsData();
			if(subStructureId == null || subStructureId == "" ){
				subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
			}
			String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
			params.put("clientUserIds",clientUserIds);
			params.put("subAllStructureId",deptIds);
		}else{
			String subAllStructureId = channelCommService.getSubStructrueIds(structureId);
			params.put("clientUserIds",null);
			params.put("subAllStructureId",subAllStructureId);
		}
		params.put("clientId",clientId);
		params.put("structureId",structureId);
		params.put("userName", userName);
		params.put("startTime", DateUtil.getDayStart(startTime));
		params.put("endTime", DateUtil.getDayEnd(endTime));
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		params.put("_orderby", "finishHour");
		params.put("_order", "asc");
		Integer count = wrTaskDataService.getWrTaskDataCountByUserId(params);
		int pagenum = page == null ? 1 : page;	
		Page<WrTaskFinishCount> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		params.put("_start", pageParam.getStartRows());
		params.put("_size", pageParam.getPageSize());
		List<WrTaskFinishCount> wrTaskFinishCounts = wrTaskDataService.getWrTaskDataByUserId(params);
		pageParam.setItems(wrTaskFinishCounts);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("clientId",clientId);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("structureId", structureId);
		model.addAttribute("userName", userName);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		return "/wrTask/wrTaskList";
	}
	
	
	/**
	 * 查看工作任务详情
	 * @param model
	 * @param finishDate
	 * @return
	 */
	@RequestMapping(value = "/showWrTaskData")
	public String showWrTaskDetail(Model model,String finishDate,Integer clientUserId){
		ClientUser clientUser = clientUserService.selectByPrimaryKey(clientUserId);
		ClientStructure clientStructure = clientStructureService.getClientStructureById(clientUser.getClientStructureId());
		Integer clientId = getClientId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("clientUserId", clientUserId);
		params.put("startTime", DateUtil.getDayStart(finishDate));
		params.put("endTime", DateUtil.getDayEnd(finishDate));
		params.put("_orderby", "create_time");
		params.put("_order", "desc");
		List<WrTaskData> wrTaskDatas = wrTaskDataService.selectByParams(params);
		model.addAttribute("wrTaskDatas", wrTaskDatas);
		model.addAttribute("clientUser", clientUser);
		model.addAttribute("clientStructure", clientStructure);
		model.addAttribute("finishDate", finishDate);
		return "/wrTask/showWrTaskData";
	}
}
