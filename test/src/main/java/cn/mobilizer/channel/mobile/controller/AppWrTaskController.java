package cn.mobilizer.channel.mobile.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.wrTask.service.WrTaskDataService;
import cn.mobilizer.channel.wrTask.vo.WrTaskFinishCount;

@Controller
@RequestMapping(value = "/mobile/wrTask")
public class AppWrTaskController extends BaseActionSupport{
	
	@Autowired
	private WrTaskDataService wrTaskDataService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	
	@RequestMapping("/query/{clientUserId}/{token}")
	public String query(Model model,@PathVariable("token")String token,@PathVariable("clientUserId")Integer clientUserId){
		ClientUser clientUser = clientUserService.selectByPrimaryKey(clientUserId);
		Integer structureId = clientUser.getClientStructureId();
		String clientUserIds = channelCommService.findUserIdsByParentId(clientUserId);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);   				//查询昨天的数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId",clientUser.getClientId());
		params.put("structureId",structureId);
		params.put("startTime", DateUtil.getDayStart(cal.getTime()));
		params.put("endTime", DateUtil.getDayEnd(cal.getTime()));
		params.put("clientUserIds",clientUserIds);
		params.put("subAllStructureId",null);
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		params.put("_orderby", "finishHour");
		params.put("_order", "asc");
		List<WrTaskFinishCount> wrTaskFinishCounts = wrTaskDataService.getWrTaskDataByUserId(params);
		model.addAttribute("wrTaskFinishCounts", wrTaskFinishCounts);
		return "/mobile/wrTaskDataList";
	} 
}
