package cn.mobilizer.channel.ctTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.ctTask.service.CtVisitedDataService;

@Controller
@RequestMapping(value = "/ctVisitedData")
public class CtVisitedDataController extends BaseActionSupport{
	
	@Autowired
	private CtVisitedDataService ctVisitedDataService;
}
