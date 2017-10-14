package cn.mobilizer.channel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.api.vo.ResultEntity;
import cn.mobilizer.channel.api.vo.ResultEntityWithObject;
import cn.mobilizer.channel.visit.po.Attendance;
import cn.mobilizer.channel.visit.service.AttendanceService;

@RestController
@RequestMapping(value = "/api/event")
public class AttendenceController {
	@Autowired
	AttendanceService attendanceService;
	
	@RequestMapping(value = "/getattendance", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithObject<Attendance> getAttendence(Integer clientId, Integer clientUserId){
		Attendance att = attendanceService.getAttendance(clientId, clientUserId);
		ResultEntityWithObject<Attendance> re = new ResultEntityWithObject<Attendance>(true);
		re.setDataInfo(att);
		return re;
	}
	
	@RequestMapping(value = "/attendance", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithObject<Attendance> doAttendence(Integer clientId, Integer clientUserId, Integer type, String longitude, String latitude, String pics){
		Attendance att = attendanceService.doAttendance(clientId, clientUserId, type, longitude, latitude, pics);
		if(att != null){
			ResultEntityWithObject<Attendance> re = new ResultEntityWithObject<Attendance>(true);
			re.setDataInfo(att);
			return re;
		}else{
			ResultEntityWithObject<Attendance> ret = new ResultEntityWithObject<Attendance>(false);
			return ret;
		}
	}
	
	@RequestMapping(value = "/special", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithObject<String> doSpecial(Integer clientId, Integer clientUserId){
		return null;
	}
	
	@RequestMapping(value = "/attendanceRemark", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntity attendanceRemark(Integer clientId, Integer clientUserId, Integer attendanceId, String remark){
		Boolean re = attendanceService.attendanceRemark(clientId, clientUserId, attendanceId, remark);
		if(re)
			return new ResultEntity(true);
		else
			return new ResultEntity(false);
	}

}
