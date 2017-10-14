package cn.mobilizer.channel.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mobilizer.channel.api.vo.ResultEntity;
import cn.mobilizer.channel.api.vo.ResultEntityWithList;
import cn.mobilizer.channel.base.service.impl.MessageDetailImpl;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.sync.po.Constants;
import cn.mobilizer.channel.systemManager.po.Messages;
import cn.mobilizer.channel.systemManager.service.MessagesService;

@RestController
@RequestMapping(value = "/api/message")
public class MessageController implements Constants{
	@Autowired
	private MessagesService messagesService;
	@Autowired
	private MessageDetailImpl messageDetailImpl;
	
	@RequestMapping(value = "/getmessages", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntityWithList<Messages> getMessage(Integer clientId, Integer clientUserId,String lastMessageTime){
		ResultEntityWithList<Messages> re = new ResultEntityWithList<Messages>();
		if(clientId == null || clientUserId == null || StringUtil.isEmptyString(lastMessageTime)){
			re.setResultCode(RESULT_FAILED);
			re.setResultMSG(RESULT_FAILED_ILLEGAL_PARA);
			return re;
		}
		List<Messages> msgs = messagesService.getNewMessages(clientId, clientUserId, lastMessageTime);
		re.setResultCode(RESULT_SUCCESS);
		re.setResultMSG(RESULT_SUCCESS_DESC);
		re.setDataInfo(msgs);
		return re;
	}
	
	@RequestMapping(value = "/readmark", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResultEntity readMark(Integer clientId, Integer clientUserId,Integer messageId){
		int i = messageDetailImpl.readMark(clientUserId, messageId);
		ResultEntity re = new ResultEntity();
		if(i == 0){
			re.setResultCode(RESULT_FAILED);
			re.setResultMSG("没有找到相关记录！");
		}else{
			re.setResultCode(RESULT_SUCCESS);
			re.setResultMSG(RESULT_SUCCESS_DESC);
		}
		return re;
	}

}
