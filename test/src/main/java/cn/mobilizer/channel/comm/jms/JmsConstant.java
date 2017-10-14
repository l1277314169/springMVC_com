package cn.mobilizer.channel.comm.jms;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JmsConstant {
	
	private static Log log = LogFactory.getLog(JmsConstant.class);
	private static volatile JmsConstant instance = null;

	public static JmsConstant getInstance() {
		if (instance == null) {
			synchronized (JmsConstant.class) {
				if (instance == null) {
					instance = new JmsConstant();
					// instance.init();
				}
			}
		}
		return instance;
	}
	/**
	 * Jms消息类型
	 */
	public static enum JMS_TYPE {
		CHANNELPLUS_PHOTO,
		SYS_TEST;
	}

	/**
	 * Jms消息的事件类型 <br>
	 * <b>同一消息下的不同事件</b>
	 */
	public static enum EVENT_TYPE {
		/**创建*/
		SYS_TEST_CREATE_MSG,
		/**更新*/
		SYS_TEST_UPDATE_MSG,
		/**图片创建**/
		CHANNELPLUS_PHOTO_CREATE_MSG
	}

	public static void main(String[] args) {
	}
}
