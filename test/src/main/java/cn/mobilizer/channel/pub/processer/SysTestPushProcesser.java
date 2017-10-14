package cn.mobilizer.channel.pub.processer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.mobilizer.channel.comm.jms.Message;
import cn.mobilizer.channel.comm.jms.MessageProcesser;
import cn.mobilizer.channel.comm.utils.MessageUtils;


public class SysTestPushProcesser implements MessageProcesser {
	private static final Log LOG = LogFactory.getLog(SysTestPushProcesser.class);
	
	@Override
	public void process(Message message) {
		if (MessageUtils.isSysCreateMessage(message) || MessageUtils.isSysUpdateMessage(message)) {
			Integer testId = message.getObjectId();
			LOG.info("SysTestPushProcesser rush test :" + message);
		}
	}
}
