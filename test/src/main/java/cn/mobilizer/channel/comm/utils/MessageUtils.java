/**
 * 
 */
package cn.mobilizer.channel.comm.utils;

import cn.mobilizer.channel.comm.jms.JmsConstant;
import cn.mobilizer.channel.comm.jms.Message;


public class MessageUtils {

	/**
	 * @param msg
	 * @return
	 */
	public static boolean isSysCreateMessage(final Message msg){
		return JmsConstant.EVENT_TYPE.SYS_TEST_CREATE_MSG.name().equals(msg.getEventType());
	}
	
	/**
	 * @param msg
	 * @return
	 */
	public static boolean isSysUpdateMessage(final Message msg){
		return JmsConstant.EVENT_TYPE.SYS_TEST_UPDATE_MSG.name().equals(msg.getEventType());
	}
	
	/**
	 * @param msg
	 * @return
	 */
	public static boolean isPhotoCreateMessage(final Message msg){
		return JmsConstant.EVENT_TYPE.CHANNELPLUS_PHOTO_CREATE_MSG.name().equals(msg.getEventType());
	}
	
}
