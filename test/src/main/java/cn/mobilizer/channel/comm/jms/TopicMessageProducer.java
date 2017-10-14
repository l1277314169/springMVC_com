package cn.mobilizer.channel.comm.jms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

public class TopicMessageProducer {

	private static final Log log = LogFactory.getLog(TopicMessageProducer.class);

	private JmsTemplate template;

	private String destination;

	public void sendMsg(Message msg) {
		log.info(msg);
		String[] queues = MessageProtector.getInstance().getQueues(destination);
		if (null != queues) {
			for (String string : queues) {
				template.convertAndSend(string, msg);
			}
		}
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
