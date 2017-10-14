package cn.mobilizer.channel.base.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.systemManager.dao.MessageDetailDao;
import cn.mobilizer.channel.systemManager.po.MessageDetails;

@Service
public class MessageDetailImpl {
	@Autowired
	MessageDetailDao messageDetailDao;
	
	public int readMark(int clientUserId, int messageId){
		int i = 0;
		MessageDetails md = messageDetailDao.getMessageDetailByClientUserIdAndMessageId(clientUserId, messageId);
		if(md != null){
			md.setIsRead((byte) 1);
			md.setReadTime(new Date());
			i = messageDetailDao.update(md);
		}
		return i;
	}

}
