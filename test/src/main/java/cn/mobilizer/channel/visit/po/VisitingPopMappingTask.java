package cn.mobilizer.channel.visit.po;

import java.io.Serializable;

import cn.mobilizer.channel.base.po.Client;

/**
 * 拜访对象(门店/经销商)
 */
public class VisitingPopMappingTask implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6286941604972792501L;
	private String mappingId; 
	private VisitingTask visitingTask;
	private String popId;
	private Client client; // 所属客户
	private int isDelete; // 删除标示

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public VisitingTask getVisitingTask() {
		return visitingTask;
	}

	public void setVisitingTask(VisitingTask visitingTask) {
		this.visitingTask = visitingTask;
	}

	public String getPopId() {
		return popId;
	}

	public void setPopId(String popId) {
		this.popId = popId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

}
