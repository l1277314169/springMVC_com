package cn.mobilizer.channel.visit.po;

import java.io.Serializable;
import java.util.Date;

public class VisitingTaskDetailData implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -6928523257379261871L;

	private String visitingTaskDetailDataId;

    private String visitingTaskDataId;

    private Integer visitingTaskStepId;

    private Integer objectId;

    private String target1Id;

    private String target2Id;

    private Integer visitingParameterId;

    private String value;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    /**扩展字段**/
    private String stepName;
    
    private Integer controlType; 
    
    private String controlName;
    
    public String getVisitingTaskDetailDataId() {
        return visitingTaskDetailDataId;
    }

    public void setVisitingTaskDetailDataId(String visitingTaskDetailDataId) {
        this.visitingTaskDetailDataId = visitingTaskDetailDataId == null ? null : visitingTaskDetailDataId.trim();
    }

    public String getVisitingTaskDataId() {
        return visitingTaskDataId;
    }

    public void setVisitingTaskDataId(String visitingTaskDataId) {
        this.visitingTaskDataId = visitingTaskDataId == null ? null : visitingTaskDataId.trim();
    }

    public Integer getVisitingTaskStepId() {
        return visitingTaskStepId;
    }

    public void setVisitingTaskStepId(Integer visitingTaskStepId) {
        this.visitingTaskStepId = visitingTaskStepId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
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

    public Integer getVisitingParameterId() {
        return visitingParameterId;
    }

    public void setVisitingParameterId(Integer visitingParameterId) {
        this.visitingParameterId = visitingParameterId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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

	public String getStepName(){
		return stepName;
	}

	public void setStepName(String stepName){
		this.stepName = stepName;
	}

	public Integer getControlType(){
		return controlType;
	}

	public String getControlName(){
		return controlName;
	}

	public void setControlType(Integer controlType){
		this.controlType = controlType;
	}

	public void setControlName(String controlName){
		this.controlName = controlName;
	}
}