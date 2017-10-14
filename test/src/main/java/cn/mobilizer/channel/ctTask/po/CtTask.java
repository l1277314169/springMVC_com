package cn.mobilizer.channel.ctTask.po;

import java.util.Date;

public class CtTask {
    private Integer ctTaskId;

    private String taskName;

    private Byte taskType;

    private Byte popType;

    private Byte isMustDo;

    private Byte groupBy;

    private String startDate;

    private String endDate;

    private Byte startLocating;

    private Byte endLocating;

    private Byte startPic;

    private Byte endPic;

    private Byte cycleType;

    private Integer validFrom;

    private Integer validEnd;

    private String objectFilter;

    private Integer ctTaskGroup;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private CtTaskData ctTaskData;
    
    private Boolean isInCycel;   

    private Date validFromDate;           //周期开始时间        
    
    private Date validEndDate;				//周期结束时间    
    
    private Date beforeValidEndDate;    //上一次周期的结束时间
    
    private Boolean isSaveSameCycle;	//保存时区分是否在同一周期
    
	public Integer getCtTaskId() {
        return ctTaskId;
    }

    public void setCtTaskId(Integer ctTaskId) {
        this.ctTaskId = ctTaskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Byte getTaskType() {
        return taskType;
    }

    public void setTaskType(Byte taskType) {
        this.taskType = taskType;
    }

    public Byte getPopType() {
        return popType;
    }

    public void setPopType(Byte popType) {
        this.popType = popType;
    }

    public Byte getIsMustDo() {
        return isMustDo;
    }

    public void setIsMustDo(Byte isMustDo) {
        this.isMustDo = isMustDo;
    }

    public Byte getStartLocating() {
        return startLocating;
    }

    public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setStartLocating(Byte startLocating) {
        this.startLocating = startLocating;
    }

    public Byte getEndLocating() {
        return endLocating;
    }

    public void setEndLocating(Byte endLocating) {
        this.endLocating = endLocating;
    }

    public Byte getStartPic() {
        return startPic;
    }

    public void setStartPic(Byte startPic) {
        this.startPic = startPic;
    }

    public Byte getEndPic() {
        return endPic;
    }

    public void setEndPic(Byte endPic) {
        this.endPic = endPic;
    }

    public Byte getCycleType() {
        return cycleType;
    }

    public void setCycleType(Byte cycleType) {
        this.cycleType = cycleType;
    }

    public Integer getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Integer validFrom) {
        this.validFrom = validFrom;
    }

    public Integer getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(Integer validEnd) {
        this.validEnd = validEnd;
    }

    public String getObjectFilter() {
        return objectFilter;
    }

    public void setObjectFilter(String objectFilter) {
        this.objectFilter = objectFilter == null ? null : objectFilter.trim();
    } 

    public Integer getCtTaskGroup() {
		return ctTaskGroup;
	}

	public void setCtTaskGroup(Integer ctTaskGroup) {
		this.ctTaskGroup = ctTaskGroup;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

	public CtTaskData getCtTaskData() {
		return ctTaskData;
	}

	public void setCtTaskData(CtTaskData ctTaskData) {
		this.ctTaskData = ctTaskData;
	}

	public Byte getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(Byte groupBy) {
		this.groupBy = groupBy;
	}

	public Boolean getIsInCycel() {
		return isInCycel;
	}

	public void setIsInCycel(Boolean isInCycel) {
		this.isInCycel = isInCycel;
	}

	public Date getValidFromDate() {
		return validFromDate;
	}

	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public Date getBeforeValidEndDate() {
		return beforeValidEndDate;
	}

	public void setBeforeValidEndDate(Date beforeValidEndDate) {
		this.beforeValidEndDate = beforeValidEndDate;
	}

	public Boolean getIsSaveSameCycle() {
		return isSaveSameCycle;
	}

	public void setIsSaveSameCycle(Boolean isSaveSameCycle) {
		this.isSaveSameCycle = isSaveSameCycle;
	}
}