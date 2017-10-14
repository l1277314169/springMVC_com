package cn.mobilizer.channel.visit.po;

import java.util.Date;

public class VisitingTaskStepObject {
    private Integer objectId;

    private Integer visitingTaskStepId;

    private String target1Id;

    private String target2Id;

    private Integer sequence;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    /**扩展字段**/
    private String objectName;
    
    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getVisitingTaskStepId() {
        return visitingTaskStepId;
    }

    public void setVisitingTaskStepId(Integer visitingTaskStepId) {
        this.visitingTaskStepId = visitingTaskStepId;
    }

    public String getTarget1Id() {
        return target1Id;
    }

    public void setTarget1Id(String target1Id) {
        this.target1Id = target1Id == null ? null : target1Id.trim();
    }

    public String getTarget2Id() {
        return target2Id;
    }

    public void setTarget2Id(String target2Id) {
        this.target2Id = target2Id == null ? null : target2Id.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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
	
	public String getObjectName(){
		return objectName;
	}
	
	public void setObjectName(String objectName){
		this.objectName = objectName;
	}
}