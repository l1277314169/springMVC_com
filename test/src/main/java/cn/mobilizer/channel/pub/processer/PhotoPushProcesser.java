package cn.mobilizer.channel.pub.processer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.mobilizer.channel.comm.jms.Message;
import cn.mobilizer.channel.comm.jms.MessageProcesser;
import cn.mobilizer.channel.comm.utils.MessageUtils;


public class PhotoPushProcesser implements MessageProcesser {
	private static final Log LOG = LogFactory.getLog(PhotoPushProcesser.class);
	
	@Override
	public void process(Message message) {
		if (MessageUtils.isPhotoCreateMessage(message)) {
			Integer photoId = message.getObjectId();
			LOG.info("PhotoPushProcesser rush test :" + message);
		}
	}
}
