package cn.mobilizer.channel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.api.vo.ResultEntity;
import cn.mobilizer.channel.base.po.SystemFeedback;
import cn.mobilizer.channel.base.service.SystemFeedbackService;

@RestController
@RequestMapping(value = "/api/help")
public class FeedbackController {
	@Autowired
	private SystemFeedbackService systemFeedbackService;
	@RequestMapping(value = "/feedback", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntity doCallback(int clientId,int clientUserId,byte type, String content, String addImg, String addLog){
		SystemFeedback rb = new SystemFeedback();
		rb.setClientId(clientId);
		rb.setFeedbackType(type);
		rb.setFeedbackBy(clientUserId);
		rb.setContent(content);
		rb.setLogFile(addLog);
		rb.setPictures(addImg);
		int i = systemFeedbackService.insert(rb);
		ResultEntity re = null;
		if(i > 0){
			re = new ResultEntity(true);
		}else
			re = new ResultEntity(false);
		return re;
	}

}
