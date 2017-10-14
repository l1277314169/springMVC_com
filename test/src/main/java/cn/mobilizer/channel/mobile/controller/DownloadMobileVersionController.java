package cn.mobilizer.channel.mobile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.service.ClientService;
import cn.mobilizer.channel.base.vo.ClientVO;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.systemManager.service.MobileVersionService;

@Controller
@RequestMapping(value = "/app/downloadPage")
public class DownloadMobileVersionController extends BaseActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6020471503460983765L;
	@Autowired
	private ClientService clientService;
	@Autowired
	private MobileVersionService mobileVersionService;
	
	
	@RequestMapping(value = "/{clientName}")
	public String showDownloadPage(Model model,@PathVariable("clientName")String clientName,Boolean isEnable){
		isEnable = isEnable == null ? true : isEnable;
		Map<String,Object> searchParams=new HashMap<String,Object>();
		searchParams.put("clientName", clientName);
		Client client = clientService.findByParams(searchParams);
		if(client != null){
			//fax 存放 token；如果没有token就不需要认证，直接获取下载链接
			if(StringUtil.isEmptyString(client.getFax())){
				String androidUrl = mobileVersionService.findByParams(client.getClientId(),isEnable, ChannelEnum.MOBILE_VERSION_TYPE.AD.getKey());
				String iosUrl = mobileVersionService.findByParams(client.getClientId(),isEnable, ChannelEnum.MOBILE_VERSION_TYPE.IP.getKey());
				model.addAttribute("authorise", true);
				model.addAttribute("androidUrl", androidUrl);
				model.addAttribute("iosUrl", iosUrl);
			} else {
				model.addAttribute("authorise", false);
			}
		}
		model.addAttribute("client", client);
		return "/app/download/showDownloadPage";
	}
	
	@RequestMapping(value = "/{clientName}/{token}")
	public String showDownloadPageTokden(Model model,@PathVariable("clientName")String clientName, @PathVariable("token")String token,Boolean isEnable){
		isEnable = isEnable == null ? true : isEnable;
		Map<String,Object> searchParams=new HashMap<String,Object>();
		searchParams.put("clientName", clientName);
		Client client = clientService.findByParams(searchParams);
		if(client != null){
			if(StringUtil.isEmptyString(client.getFax()) || client.getFax().equals(token)){
				String androidUrl = mobileVersionService.findByParams(client.getClientId(),isEnable,ChannelEnum.MOBILE_VERSION_TYPE.AD.getKey());
				String iosUrl = mobileVersionService.findByParams(client.getClientId(),isEnable,ChannelEnum.MOBILE_VERSION_TYPE.IP.getKey());
				model.addAttribute("authorise", true);
				model.addAttribute("androidUrl", androidUrl);
				model.addAttribute("iosUrl", iosUrl);
			} else {
				model.addAttribute("authorise", false);
			}
		}
		model.addAttribute("client", client);
		return "/app/download/showDownloadPage";
	}

	//	@RequestMapping(value = "/downloadPage/{clientId}/{token}/{version}")
//	public String showDownloadPageAddversion(Model model,@PathVariable("clientId")Integer clientId, @PathVariable("token")String token, @PathVariable("version")String version){
//		Map<String,Object> searchParams=new HashMap<String,Object>();
//		searchParams.put("clientId", clientId);
//		searchParams.put("token", token);
//		searchParams.put("version", version);
//		Client client = clientService.findByParams(searchParams);
//		
//		String android = mobileVersionService.findByParamsAndroid(searchParams);
//		String ios = mobileVersionService.findByParamsIos(searchParams);
//		model.addAttribute("client", client);
//		model.addAttribute("android", android);
//		model.addAttribute("ios", ios);
//		return "app/download/showDownloadPage";
//	}
	
	@RequestMapping(value = "/all")
	public String showAllDownloadPage(Model model,Boolean isEnable){
		isEnable = isEnable == null ? true : isEnable;
		List<ClientVO> ls = clientService.findClientVOAll();
		if(ls !=null && ls.size() > 0){
			for ( ClientVO clientVO : ls ) {
				String androidUrl = mobileVersionService.findByParams(clientVO.getClientId(),isEnable, ChannelEnum.MOBILE_VERSION_TYPE.AD.getKey());
				String iphoneUrl = mobileVersionService.findByParams(clientVO.getClientId(),isEnable, ChannelEnum.MOBILE_VERSION_TYPE.IP.getKey());
				String ipadUrl = mobileVersionService.findByParams(clientVO.getClientId(),isEnable, ChannelEnum.MOBILE_VERSION_TYPE.IPD.getKey());
				clientVO.setAndroidUrl(androidUrl);
				clientVO.setIphoneUrl(iphoneUrl);
				clientVO.setIpadUrl(ipadUrl);
			}
		}
		model.addAttribute("clientList", ls);
		return "app/download/showAllDownloadPage";
	}
}
