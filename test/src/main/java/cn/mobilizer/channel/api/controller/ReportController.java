package cn.mobilizer.channel.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.api.po.VisitingCount;
import cn.mobilizer.channel.api.po.VisitingDetail;
import cn.mobilizer.channel.api.service.impl.ReportService;
import cn.mobilizer.channel.api.vo.ResultEntityWithList;

@RestController
@RequestMapping(value = "/api/report")
public class ReportController {
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value = "/visitingCount", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithList<VisitingCount> getVisitingCount(int clientId,int clientUserId,String startTime,String endTime){
		List<VisitingCount> list = reportService.getVisitingCount(clientId, clientUserId, startTime, endTime);
		ResultEntityWithList<VisitingCount>  re = new ResultEntityWithList<VisitingCount>(true);
		re.setDataInfo(list);
		return re;
	}
	
	@RequestMapping(value = "/visitingDetail", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithList<VisitingDetail> getVisitingDetail(int clientId,int clientUserId,String startTime,String endTime){
		List<VisitingDetail> list = reportService.getVisitingDetail(clientId, clientUserId, startTime, endTime);
		ResultEntityWithList<VisitingDetail>  re = new ResultEntityWithList<VisitingDetail>(true);
		re.setDataInfo(list);
		return re;
	}

}
