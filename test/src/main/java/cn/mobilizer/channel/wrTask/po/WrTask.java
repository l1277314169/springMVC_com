package cn.mobilizer.channel.wrTask.po;

import java.util.Date;

public class WrTask {
    private Integer wrTaskId;

    private String taskName;

    private Byte taskType;

    private Byte popType;

    private Byte isMustDo;

    private Byte groupBy;

    private Date startDate;

    private Date endDate;

    private Byte startLocating;

    private Byte endLocating;

    private Byte startPic;

    private Byte endPic;

    private Byte cycleType;

    private Integer validFrom;

    private Integer validEnd;

    private String objectFilter;

    private Integer wrTaskGroup;

    private Byte viewStyle;

    private Integer sequence;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getWrTaskId() {
        return wrTaskId;
    }

    public void setWrTaskId(Integer wrTaskId) {
        this.wrTaskId = wrTaskId;
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

    public Byte getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(Byte groupBy) {
        this.groupBy = groupBy;
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

    public Byte getStartLocating() {
        return startLocating;
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

    public Integer getWrTaskGroup() {
		return wrTaskGroup;
	}

	public void setWrTaskGroup(Integer wrTaskGroup) {
		this.wrTaskGroup = wrTaskGroup;
	}

	public Byte getViewStyle() {
        return viewStyle;
    }

    public void setViewStyle(Byte viewStyle) {
        this.viewStyle = viewStyle;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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
}