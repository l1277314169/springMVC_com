package cn.mobilizer.channel.specialTask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.api.vo.ResultEntityWithObject;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.specialTask.po.SpecialTaskObject;
import cn.mobilizer.channel.specialTask.po.SpecialTaskParameter;
import cn.mobilizer.channel.specialTask.service.SpecialTaskDataService;
import cn.mobilizer.channel.specialTask.service.SpecialTaskDetailDataService;
import cn.mobilizer.channel.specialTask.service.SpecialTaskService;
import cn.mobilizer.channel.specialTask.vo.SpecialTaskVO;
@Controller
@RequestMapping(value = "/api/specialTaskTest")
public class SpecialTaskControllerTest extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8456143868592262252L;
	@Autowired
	private SpecialTaskService specialTaskService;

	/**
	 * 专项任务的新增
	 * @param specialTaskVO
	 * @return
	 */
	@RequestMapping(value = "/saveSpecialTask",produces = "application/json; charset=UTF-8" )
	public @ResponseBody ResultEntityWithObject<Boolean> saveSpecialTask(@RequestBody SpecialTaskVO specialTaskVO){
		String SpecialTaskId =	specialTaskService.addSpecialTask(specialTaskVO);
		if(SpecialTaskId != null && !SpecialTaskId.equals("")){
			return new ResultEntityWithObject<Boolean>(true);
		}else{
			return new ResultEntityWithObject<Boolean>(false);
		}
	}
}
