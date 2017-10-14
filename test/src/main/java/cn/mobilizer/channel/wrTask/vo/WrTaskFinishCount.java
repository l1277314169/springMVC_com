package cn.mobilizer.channel.wrTask.vo;

import java.util.Date;

public class WrTaskFinishCount {
	
	private Date finishDate;			//工作所用小时
	
	private String userName;
	
	private String structureName;			
	
	private Integer finishCount;				//完成任务数
	
	private Double finishHour;         //工作所用小时
	
	private Integer clientUserId;
	
	private String wrTaskDataIds;

	public Double getFinishHour() {
		return finishHour;
	}

	public void setFinishHour(Double finishHour) {
		this.finishHour = finishHour;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public Integer getFinishCount() {
		return finishCount;
	}

	public void setFinishCount(Integer finishCount) {
		this.finishCount = finishCount;
	}

	public Integer getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getWrTaskDataIds() {
		return wrTaskDataIds;
	}

	public void setWrTaskDataIds(String wrTaskDataIds) {
		this.wrTaskDataIds = wrTaskDataIds;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
}
