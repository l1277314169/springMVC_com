package cn.mobilizer.channel.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.api.vo.WrTaskVO;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.wrTask.service.WrTaskService;
@Controller
@RequestMapping(value = "/api/wrTask")
public class WrTaskStoreController extends BaseActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7594676650533623036L;
	@Autowired
	private WrTaskService wrTaskService;
	
	@RequestMapping(value = "/query",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public WrTaskVO querStoreList(Integer clientId,Integer clientUserId,Integer cityId,String lastUpdateTime){
		WrTaskVO wrTaskVO = wrTaskService.findStoreList(clientId, clientUserId, cityId, lastUpdateTime);
		return wrTaskVO;
	}
}
