package cn.mobilizer.channel.log.service;

import java.util.List;

import cn.mobilizer.channel.log.po.Sms;

public interface SmsPushProcessor {
	
	public void pushMessage(List<Sms> sms) throws Exception;
	
}
