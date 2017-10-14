package cn.mobilizer.channel.log.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mobilizer.channel.base.po.ClientUserExpand;
import cn.mobilizer.channel.base.service.ClientUserExpandService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.log.po.Sms;
import cn.mobilizer.channel.log.service.SmsPushProcessor;
import cn.mobilizer.channel.log.service.SmsService;

public class SmsProcessorManager {

	@Autowired
	private Map<String, SmsPushProcessor> processorMap;
	@Autowired
	private ClientUserExpandService clientUserExpandService;
	@Autowired
	private SmsService smsService;

	public Map<String, SmsPushProcessor> getProcessorMap() {
		return processorMap;
	}

	public void setProcessorMap(Map<String, SmsPushProcessor> processorMap) {
		this.processorMap = processorMap;
	}

	public void smsProcesso(List<Sms> smsList) throws Exception {
		List<Sms> iosSmslist = new ArrayList<Sms>();
		List<Sms> androidSmsList = new ArrayList<Sms>();
		SmsPushProcessor processor = null;
		for (Sms sms : smsList) {
			try {
				ClientUserExpand clientUserExpand = clientUserExpandService.findByPrimaryKey(sms.getReceiverId());
				if (clientUserExpand==null||StringUtils.isEmpty(clientUserExpand.getMsgPushToken())) {
					continue;
				}
				String msgPushToken = clientUserExpand.getMsgPushToken();
				String[] strs = msgPushToken.split(",");
				int deviceType = new Integer(strs[0]).intValue(); // 设备类型    1：安卓，2:ios
				if (sms.getSmsType() == 2 && deviceType == 1) {
					androidSmsList.add(sms);
				} else if (sms.getSmsType() == 2 && deviceType == 2) {
					iosSmslist.add(sms);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		if (androidSmsList.size() != 0) {
			processor = processorMap.get("androidSms");
			processor.pushMessage(androidSmsList);
		}
		if (iosSmslist.size() != 0) {
			processor = processorMap.get("iosSms");
			processor.pushMessage(iosSmslist);
		}
	}
}
