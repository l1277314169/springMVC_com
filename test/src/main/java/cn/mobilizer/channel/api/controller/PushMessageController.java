package cn.mobilizer.channel.api.controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.api.vo.ResultEntity;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientUserExpandService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.comm.utils.StringUtil;

@RestController
@RequestMapping(value = "/api/push")
public class PushMessageController {
	private static final Log LOG = LogFactory.getLog(PushMessageController.class);
			
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientUserExpandService clientUserExpandService;
	
	@RequestMapping(value = "/bind", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntity doCallback(Integer bindType, Integer clientId,Integer clientUserId,String baiduChannelId, String baiduUserId, String deviceType){ 
		ResultEntity re = new ResultEntity(false);
//		ClientUser user = clientUserService.findByPrimaryKey(clientUserId);
		String msgPushToken = null;
		if(deviceType ==null || deviceType ==""){
			deviceType = "1";
		}
		
//		if(user == null){
//			re.setResultMSG("用户不存在！");
//			return re;
//		}
		if(bindType == 1){
			if(StringUtil.isEmptyString(baiduChannelId) || StringUtil.isEmptyString(baiduUserId)){
				re.setResultMSG("绑定参数错误！");
				return re;
			}
			//绑定前面加1-android，2-ios
//			user.setBaiduInfo(deviceType+","+baiduUserId+","+baiduChannelId);
			msgPushToken = deviceType+","+baiduUserId+","+baiduChannelId;
		}else if(bindType == 0){
//			user.setBaiduInfo("");
			msgPushToken = "";
		}
//		clientUserService.updatePassword(user);
		clientUserExpandService.updateUserLoginInfo(clientUserId, null, new Date(), msgPushToken, clientId);
		return new ResultEntity(true);
	}

}
