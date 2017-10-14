package cn.mobilizer.channel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.api.vo.AppVersionResult;
import cn.mobilizer.channel.api.vo.ResultEntityWithObject;
import cn.mobilizer.channel.systemManager.po.MobileVersion;
import cn.mobilizer.channel.systemManager.service.MobileVersionDetailService;
import cn.mobilizer.channel.systemManager.service.MobileVersionService;


@RestController
@RequestMapping(value = "/api/mobile")
public class AppVersionController {
	@Autowired
	private MobileVersionService mobileVersionService;
	@Autowired
	private MobileVersionDetailService mobileVersionDetailService;
	//先取和自己相关的
	@RequestMapping(value = "/checkupdate", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithObject<AppVersionResult> checkAPPUpdate(Integer clientId, Integer clientUserId, Integer appCode, String appVersion){
		ResultEntityWithObject<AppVersionResult> result = new ResultEntityWithObject<AppVersionResult>();
		MobileVersion mv = mobileVersionService.findNewMobileVersion(clientId, clientUserId, appVersion, appCode);
		if(mv != null){
			AppVersionResult av = new AppVersionResult();
			av.setVersion(mv.getVersion());
			av.setUrl(mv.getAppLink());
			if(mv.getMustUpdate() == 1)
				av.setEnforce(true);
			if(mv.getMustUpdate() == 0)
				av.setEnforce(false);
			result.setDataInfo(av);
		}
		result.setResultCode(100);
		result.setResultMSG("Successful");
		return result;
	}

}
