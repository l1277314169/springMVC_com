package cn.mobilizer.channel.comm.jms;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MessageProtector {
	private static MessageProtector instance;
	private final Map<String, String[]> topicMap = new HashMap<String, String[]>();

	private void init() {

		String[] activeMqChannelplus = new String[] {"ActiveMQ.MOBI_CHANNELPLUS.web"};
		topicMap.put("ActiveMQ.MOBI_CHANNELPLUS", activeMqChannelplus);

		//增加图片的消息
		String[] activeMqChannelplusPhoto = new String[] {"ActiveMQ.MOBI_CHANNELPLUS_PHOTO.web"};
		topicMap.put("ActiveMQ.MOBI_CHANNELPLUS_PHOTO", activeMqChannelplusPhoto);

	}

	public static MessageProtector getInstance() {
		if (instance == null) {
			MessageProtector protector = new MessageProtector();
			protector.init();
			instance = protector;
		}
		return instance;
	}

	public String[] getQueues(String topic) {
		return topicMap.get(topic);
	}

}
