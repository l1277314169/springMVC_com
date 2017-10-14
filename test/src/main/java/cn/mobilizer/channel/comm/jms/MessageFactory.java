package cn.mobilizer.channel.comm.jms;

import cn.mobilizer.channel.comm.jms.JmsConstant;;

/**
 * 消息工厂
 *
 */
public class MessageFactory {
	public static Message newPhotoCreateMessage(Integer photoId, String photoName) {
		Message message = new Message(photoId, JmsConstant.JMS_TYPE.CHANNELPLUS_PHOTO.name(),JmsConstant.EVENT_TYPE.CHANNELPLUS_PHOTO_CREATE_MSG.name());
		message.setAddition(photoName);
		return message;
	}
	
	public static Message newSysTestUpdateMessage(final Integer testId) {
		Message message = new Message(testId, JmsConstant.JMS_TYPE.SYS_TEST.name(),JmsConstant.EVENT_TYPE.SYS_TEST_UPDATE_MSG.name());
		return message;
	}

	public static Message newSysTestCreateMessage(final Integer testId){
		Message message = new Message(testId, JmsConstant.JMS_TYPE.SYS_TEST.name(), JmsConstant.EVENT_TYPE.SYS_TEST_CREATE_MSG.name());
		return message;
	}

}
