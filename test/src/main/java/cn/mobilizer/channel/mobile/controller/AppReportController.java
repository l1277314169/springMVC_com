package cn.mobilizer.channel.mobile.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.mobile.po.ClientUserStoreScore;
import cn.mobilizer.channel.mobile.service.ClientUserStoreScoreService;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Controller
@RequestMapping(value = "/mobile/report")
public class AppReportController extends BaseActionSupport  {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5491870907599607035L;
	
	@Autowired
	private ClientUserStoreScoreService clientUserStoreScoreService;
	@Autowired
	private ChannelCommService channelCommService;
	
	@RequestMapping(value = "/getStoreScoreDetail")
	public String query(Model model, Integer clientId, Integer clientUserId, String clientUserIds, String storeNo, Byte scoreType, String monthId, Boolean isAll) throws BusinessException{
		List<ClientUserStoreScore> scoreList = null;
		try {
//			String clientUserIds = channelCommService.getSubordinates(clientUserId.toString());
			CustomerContextHolder.setCustomerType(CustomerContextHolder.REPORT);
			scoreList = clientUserStoreScoreService.getStoreScoreList(clientId, clientUserId, clientUserIds, storeNo, scoreType, monthId);
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		} catch (BusinessException e) {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			log.error("method getStoreScoreDetail error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		} finally {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		model.addAttribute("scoreList", scoreList);
		return "/mobile/showStoreScore";
	}	
}
