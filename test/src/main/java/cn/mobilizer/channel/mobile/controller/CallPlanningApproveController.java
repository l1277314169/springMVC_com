package cn.mobilizer.channel.mobile.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientUserRelationService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.mobile.po.PendingSchedule;
import cn.mobilizer.channel.mobile.service.PendingScheduleService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.visit.service.CallPlanningService;
@Controller
@RequestMapping(value = "/mobile/callplanningApprove")
public class CallPlanningApproveController extends BaseActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1657535301685118064L;
	@Autowired
	 private ClientUserRelationService clientUserRelationService;
	@Autowired
	 private ClientUserService clientUserService;
	@Autowired
	 private PendingScheduleService pendingScheduleService;
	@Autowired
	private CallPlanningService callPlanningService;
	
	@Autowired
	private ChannelCommService channelCommService;
	
	
	@RequestMapping(value = "/subordinates/{clientUserId}/{token}")
	public String getSubordinates(Model model,@PathVariable("clientUserId")Integer clientUserId, @PathVariable("token")String token){
		String strClientUserId=channelCommService.getSubordinatesWithOutSelf(clientUserId+"");
		Map<String,Object> searchParams=new HashMap<String,Object>();
		ClientUser clientUser = clientUserService.selectByPrimaryKey(clientUserId);
		if(clientUser != null){
			List<Object> listWeekDate=getWeekDate();
			searchParams.put("clientId", clientUser.getClientId());
			searchParams.put("startDate", listWeekDate.get(0));
			searchParams.put("endDate", listWeekDate.get(6));
			searchParams.put("strClientUserId", strClientUserId);
			List<ClientUser> clientUserList=clientUserService.queryChildByClietnUserParentId(searchParams);
			model.addAttribute("clientUserList", clientUserList);
		}
		return "mobile/approve";
	}
	
	@RequestMapping(value = "/approveDetail/{clientUserId}/{clientId}")
	public String showApproveDetail(Model model,@PathVariable("clientUserId")Integer clientUserId,@PathVariable("clientId")Integer clientId){
		boolean databo=false;
		Map<String,Object> searchParams=new HashMap<String,Object>();
		searchParams.put("clientUserId", clientUserId);
		searchParams.put("clientId", clientId);
		
		List<Object> listWeekDate=getWeekDate();
		searchParams.put("startDate", listWeekDate.get(0));
		searchParams.put("endDate", listWeekDate.get(6));
		String[] strWeek=ImportConstants.WEEK_DATA;
		LinkedHashMap<String,List<PendingSchedule>> dateList=new LinkedHashMap<String,List<PendingSchedule>>();
		List<PendingSchedule> pendingSchedulelist=pendingScheduleService.findPendingScheduleByclientUserId(searchParams);
		if(pendingSchedulelist != null && pendingSchedulelist.size() > 0){
			databo = true;
			for (int i = 0; i < listWeekDate.size(); i++) {
				List<PendingSchedule> pendingSchedule = new ArrayList<PendingSchedule>();
				for (int j = 0; j < pendingSchedulelist.size(); j++) {
					SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
					String callDate = d.format(pendingSchedulelist.get(j).getCallDate());
					if(callDate != null && !callDate.equals("") && callDate.equals(listWeekDate.get(i))){
						pendingSchedule.add(pendingSchedulelist.get(j));
					}
				}
				Integer total = pendingSchedule.size();
				for (PendingSchedule ps : pendingSchedule) {
					if(ps.getWorkType() == 1){
						total=0;
						break;
					}
				}
				dateList.put("星期"+strWeek[i]+"("+listWeekDate.get(i)+")共"+total+"家", pendingSchedule);
			}
		}
		model.addAttribute("databo", databo);
		model.addAttribute("dateList", dateList);
		model.addAttribute("clientUserId", clientUserId);
		model.addAttribute("clientId", clientId);
		return "mobile/approve_detail";
	}
	/**
	 * 审批通过
	 * @param clientUserId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/successAudit/{clientUserId}/{clientId}")
	@ResponseBody	
	public ResultMessage successAudit(@PathVariable("clientUserId")Integer clientUserId,@PathVariable("clientId")Integer clientId)throws BusinessException{
		ResultMessage rm=null;
		Integer updateCallPlanningStatus = callPlanningService.updateCallPlanningStatus(clientUserId,clientId);
		if(updateCallPlanningStatus.equals(Constants.CHECK_SUCCESS)){
			return rm = ResultMessage.CHECK_SUCCESS_RESULT;
		}else{
			return rm = ResultMessage.CHECK_FAIL_RESULT;
		}
	}


	public static Calendar getDate(){
		Calendar cals = new GregorianCalendar();
		cals.setTime(new Date()); 
		int weekId=(9-cals.get(Calendar.DAY_OF_WEEK))%8;
		if(weekId==0){
			cals.add(Calendar.DAY_OF_WEEK,(9-cals.get(Calendar.DAY_OF_WEEK))%7); 
		}else{
			cals.add(Calendar.DAY_OF_WEEK,weekId); 
		}
		return cals; 
	}
		  
	public static List<Object> getWeekDate(){
		List<Object> list = new ArrayList<Object>();
		Calendar cal = getDate();
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
	    for (int i = 1; i < 8; i++) {
	    	list.add(d.format(cal.getTime())); 
	    	int year = cal.get(Calendar.YEAR);
	    	if(list.get(i-1).equals(year+"-12-31")){
	    		cal.add(Calendar.YEAR,1);
	    	}
	    	cal.roll(Calendar.DAY_OF_YEAR,1); 
	    }
		  return  list;
		}
	
	/**
	 * 审批不通过
	 * @param clientUserId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/failAudit/{clientUserId}/{clientId}")
	@ResponseBody	
	public ResultMessage failAudit(@PathVariable("clientUserId")Integer clientUserId,@PathVariable("clientId")Integer clientId)throws BusinessException{
		ResultMessage rm=null;
		Integer updatePendingScheduleStatus = pendingScheduleService.updatePendingScheduleStatus(clientUserId, clientId);
		if(updatePendingScheduleStatus.equals(Constants.CHECK_SUCCESS)){
			return  rm = ResultMessage.CHECK_SUCCESS_RESULT;
		}else{
			return rm = ResultMessage.CHECK_FAIL_RESULT;
		}
	}
}
