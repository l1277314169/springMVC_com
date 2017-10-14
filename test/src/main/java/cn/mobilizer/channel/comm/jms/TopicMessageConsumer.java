package cn.mobilizer.channel.comm.jms;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TopicMessageConsumer {
	private static final Log log = LogFactory.getLog(TopicMessageConsumer.class);

	private List<MessageProcesser> processerList = new ArrayList<MessageProcesser>();

	private String destName;

	public void receive(Message m) {
		log.info(m + " -- " + destName);
		for (int i = 0; i < processerList.size(); i++) {
			try {
				processerList.get(i).process(m);
			} catch (Exception e) {
				log.info(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void setProcesserList(List<MessageProcesser> processerList) {
		this.processerList = processerList;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

}
