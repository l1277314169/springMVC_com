package cn.mobilizer.channel.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.api.vo.ResultEntity;
import cn.mobilizer.channel.api.vo.ResultEntityWithList;
import cn.mobilizer.channel.base.po.AppCommand;
import cn.mobilizer.channel.base.service.AppCommandService;
import cn.mobilizer.channel.sync.po.Constants;
@RestController
@RequestMapping(value = "/api/command")
public class AppCommandController implements Constants{
	@Autowired
	private AppCommandService appCommandService;

	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithList<AppCommand> getUnexeCommand(Integer clientId, Integer clientUserId){
		List<AppCommand> list = appCommandService.getUnexeCommand(clientUserId);
		ResultEntityWithList<AppCommand> result = new ResultEntityWithList<AppCommand>(true);
		result.setDataInfo(list);
		return result;
	}
	
	@RequestMapping(value = "/exe", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntity exeCommand(Integer clientId, Integer clientUserId,Integer commandId, String exeResult){
		Integer count = appCommandService.exeCommand(commandId, exeResult);
		if(count > 0){
			return new ResultEntity(true);
		}else
			return new ResultEntity(false);
	}

}
