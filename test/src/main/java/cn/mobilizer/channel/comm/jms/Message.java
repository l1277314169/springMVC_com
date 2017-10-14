package cn.mobilizer.channel.comm.jms;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6185479895446584994L;
	
	private Integer objectId;
	private String objectType;
	private String eventType;
	private String addition; // 无特定，可以灵活跟随信息

	@SuppressWarnings("unused")
	private Message() {
	}

	protected Message(Integer objectId, String objectType, String eventType) {
		this.objectId = objectId;
		this.objectType = objectType;
		this.eventType = eventType;
	}
	
	public boolean hasSysMessage() {
		if(JmsConstant.JMS_TYPE.SYS_TEST.name().equals(objectType)) {
			return true;
		}
		return false;
	}
	public boolean hasSysCreateMessage() {
		if(hasSysMessage() && JmsConstant.EVENT_TYPE.SYS_TEST_CREATE_MSG.name().equals(eventType)) {
			return true;
		}
		return false;
	}
	public boolean hasSysUpdateMessage() {
		if(hasSysMessage() && JmsConstant.EVENT_TYPE.SYS_TEST_UPDATE_MSG.name().equals(eventType)) {
			return true;
		}
		return false;
	}
	
	public boolean hasPhotoMessage() {
		if(JmsConstant.JMS_TYPE.CHANNELPLUS_PHOTO.name().equals(objectType)) {
			return true;
		}
		return false;
	}
	public boolean hasPhotoCreateMessage() {
		if(hasPhotoMessage() && JmsConstant.EVENT_TYPE.CHANNELPLUS_PHOTO_CREATE_MSG.name().equals(eventType)) {
			return true;
		}
		return false;
	}
	
	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public boolean isNullAddition() {
		return addition == null;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Message) {
			Message cc = (Message) obj;
			return objectId.equals(cc.getObjectId()) && eventType.equals(cc.getEventType());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Message_" + objectType + "_" + objectId + "_" + eventType + (this.addition != null ? "_" + this.addition : "");
	}
	
}
