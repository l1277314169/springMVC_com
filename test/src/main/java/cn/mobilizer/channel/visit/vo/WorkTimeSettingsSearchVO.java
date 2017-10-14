package cn.mobilizer.channel.visit.vo;

import java.io.Serializable;

/**排班计划查询条件
 * @author Nany
 * 2015年1月16日上午9:38:29
 */
public class WorkTimeSettingsSearchVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4122734649749384874L;
		
	private Integer clientUserId;
	
	private String clientUserName;

	private Integer clientStructureId;
	
	private String clientStructureName;
	
	private String workDate1;
	
	private String workDate2;
	
	private Byte status;
	
	private Byte isActivation;
	
	private Integer clientPositionId;

	
	public Integer getClientPositionId() {
		return clientPositionId;
	}

	public void setClientPositionId(Integer clientPositionId) {
		this.clientPositionId = clientPositionId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getIsActivation() {
		return isActivation;
	}

	public void setIsActivation(Byte isActivation) {
		this.isActivation = isActivation;
	}

	public Integer getClientUserId() {
		return clientUserId;
	}

	public String getWorkDate1() {
		return workDate1;
	}

	public void setWorkDate1(String workDate1) {
		this.workDate1 = workDate1;
	}

	public String getWorkDate2() {
		return workDate2;
	}

	public void setWorkDate2(String workDate2) {
		this.workDate2 = workDate2;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getClientUserName() {
		return clientUserName;
	}

	public void setClientUserName(String clientUserName) {
		this.clientUserName = clientUserName;
	}

	public Integer getClientStructureId() {
		return clientStructureId;
	}

	public void setClientStructureId(Integer clientStructureId) {
		this.clientStructureId = clientStructureId;
	}

	public String getClientStructureName() {
		return clientStructureName;
	}

	public void setClientStructureName(String clientStructureName) {
		this.clientStructureName = clientStructureName;
	}
}
