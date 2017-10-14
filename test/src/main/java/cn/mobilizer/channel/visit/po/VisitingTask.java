package cn.mobilizer.channel.visit.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class VisitingTask implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9055526602205975481L;

	private Integer visitingTaskId;

    private String visitingTaskNo;

    private String visitingTaskName;

    private String visitingTaskNameEn;

    private Byte visitingTaskType;

    private Integer clientPositionId;

    private Byte popType;

    private Integer popGroupId;
    
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    private Date startDate;
    
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    private Date endDate;

    private Byte startLocating;

    private Byte endLocating;

    private Byte startPic;

    private Byte endPic;

    private Byte taskPriority;

    private Byte isCombin;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    /**扩展字段**/
    private String clientPositionName;
    
    private String storeGroupName;
    
    public Integer getVisitingTaskId() {
        return visitingTaskId;
    }

    public void setVisitingTaskId(Integer visitingTaskId) {
        this.visitingTaskId = visitingTaskId;
    }

    public String getVisitingTaskNo() {
        return visitingTaskNo;
    }

    public void setVisitingTaskNo(String visitingTaskNo) {
        this.visitingTaskNo = visitingTaskNo == null ? null : visitingTaskNo.trim();
    }

    public String getVisitingTaskName() {
        return visitingTaskName;
    }

    public void setVisitingTaskName(String visitingTaskName) {
        this.visitingTaskName = visitingTaskName == null ? null : visitingTaskName.trim();
    }

    public String getVisitingTaskNameEn() {
        return visitingTaskNameEn;
    }

    public void setVisitingTaskNameEn(String visitingTaskNameEn) {
        this.visitingTaskNameEn = visitingTaskNameEn == null ? null : visitingTaskNameEn.trim();
    }

    public Byte getVisitingTaskType() {
        return visitingTaskType;
    }

    public void setVisitingTaskType(Byte visitingTaskType) {
        this.visitingTaskType = visitingTaskType;
    }

    public Integer getClientPositionId() {
        return clientPositionId;
    }

    public void setClientPositionId(Integer clientPositionId) {
        this.clientPositionId = clientPositionId;
    }

    public Byte getPopType() {
        return popType;
    }

    public void setPopType(Byte popType) {
        this.popType = popType;
    }

    public Integer getPopGroupId() {
        return popGroupId;
    }

    public void setPopGroupId(Integer popGroupId) {
        this.popGroupId = popGroupId;
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

    public Byte getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(Byte taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Byte getIsCombin() {
        return isCombin;
    }

    public void setIsCombin(Byte isCombin) {
        this.isCombin = isCombin;
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
	
	public String getClientPositionName(){
		return clientPositionName;
	}
	
	public void setClientPositionName(String clientPositionName){
		this.clientPositionName = clientPositionName;
	}

	public String getStoreGroupName(){
		return storeGroupName;
	}

	public void setStoreGroupName(String storeGroupName){
		this.storeGroupName = storeGroupName;
	}
}