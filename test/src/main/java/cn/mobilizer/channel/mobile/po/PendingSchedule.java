package cn.mobilizer.channel.mobile.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class PendingSchedule implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5143649279748539483L;

	private String scheduleId;

    private Integer clientUserId;

    private Date callDate;

    private String popId;

    private Integer popType;

    private Integer sequence;

    private Byte planningType;

    private Byte visitType;
    
    private Integer workType;
    
    private Integer enumValue;

    private Byte status;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private String storeName;
    
    private String addr;
    
    private String soa;
    
    public String getSoa() {
		return soa;
	}

	public void setSoa(String soa) {
		this.soa = soa;
	}

	private String sob;
    

	public String getSob() {
		return sob;
	}

	public void setSob(String sob) {
		this.sob = sob;
	}


	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

	public Integer getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(Integer enumValue) {
		this.enumValue = enumValue;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId == null ? null : scheduleId.trim();
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId == null ? null : popId.trim();
    }

    public Integer getPopType() {
        return popType;
    }

    public void setPopType(Integer popType) {
        this.popType = popType;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Byte getPlanningType() {
        return planningType;
    }

    public void setPlanningType(Byte planningType) {
        this.planningType = planningType;
    }

    public Byte getVisitType() {
        return visitType;
    }

    public void setVisitType(Byte visitType) {
        this.visitType = visitType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}