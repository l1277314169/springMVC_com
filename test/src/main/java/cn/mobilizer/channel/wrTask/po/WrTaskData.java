package cn.mobilizer.channel.wrTask.po;

import java.util.Date;

public class WrTaskData {
    private String wrTaskDataId;

    private Integer clientUserId;

    private Integer workplaceId;

    private Date startDate;

    private Date endDate;

    private String storeId;

    private Integer customerId;

    private Integer brandId;

    private Integer projectType;

    private Integer wrTaskId;

    private String remark;

    private Byte status;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private String wrTaskName;
    
    private String workPlace;
    
    private Byte workplaceType;              //扩展字段
    
    private String inLongitude;
    
    private String inLatitude;

    public String getWrTaskDataId() {
        return wrTaskDataId;
    }

    public void setWrTaskDataId(String wrTaskDataId) {
        this.wrTaskDataId = wrTaskDataId == null ? null : wrTaskDataId.trim();
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public Integer getWorkplaceId() {
        return workplaceId;
    }

    public void setWorkplaceId(Integer workplaceId) {
        this.workplaceId = workplaceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getWrTaskId() {
        return wrTaskId;
    }

    public void setWrTaskId(Integer wrTaskId) {
        this.wrTaskId = wrTaskId;
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

	public String getWrTaskName() {
		return wrTaskName;
	}

	public void setWrTaskName(String wrTaskName) {
		this.wrTaskName = wrTaskName;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public Byte getWorkplaceType() {
		return workplaceType;
	}

	public void setWorkplaceType(Byte workplaceType) {
		this.workplaceType = workplaceType;
	}

	public String getInLongitude() {
		return inLongitude;
	}

	public void setInLongitude(String inLongitude) {
		this.inLongitude = inLongitude;
	}

	public String getInLatitude() {
		return inLatitude;
	}

	public void setInLatitude(String inLatitude) {
		this.inLatitude = inLatitude;
	}
}