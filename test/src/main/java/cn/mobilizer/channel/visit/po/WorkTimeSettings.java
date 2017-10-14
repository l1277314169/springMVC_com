package cn.mobilizer.channel.visit.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class WorkTimeSettings {
    private String settingId;

    private Integer clientUserId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date workDate;

    private String storeId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date startTime;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date endTime;

    private Integer weekId;

    private Integer sequence;

    private Byte workTimeType;

    private String remark;

    private Byte status;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private String clientUserName;
    
    private String structureName;
    
    private String storeName;

    private String workType;
    
    private String week;
    /**扩展字段*/
    private Byte clientUserStatus;
    private Byte isActivation;
    
    public String clientPositionName;
    
    public String loginName;

    public String getClientPositionName() {
		return clientPositionName;
	}

	public void setClientPositionName(String clientPositionName) {
		this.clientPositionName = clientPositionName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Byte getClientUserStatus() {
		return clientUserStatus;
	}

	public void setClientUserStatus(Byte clientUserStatus) {
		this.clientUserStatus = clientUserStatus;
	}

	public Byte getIsActivation() {
		return isActivation;
	}

	public void setIsActivation(Byte isActivation) {
		this.isActivation = isActivation;
	}

    
    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId == null ? null : settingId.trim();
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getWeekId() {
        return weekId;
    }

    public void setWeekId(Integer weekId) {
        this.weekId = weekId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Byte getWorkTimeType() {
        return workTimeType;
    }

    public void setWorkTimeType(Byte workTimeType) {
        this.workTimeType = workTimeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

	public String getClientUserName() {
		return clientUserName;
	}

	public void setClientUserName(String clientUserName) {
		this.clientUserName = clientUserName;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
}