package cn.mobilizer.channel.specialTask.po;

import java.io.Serializable;
import java.util.Date;

public class SpecialTaskData implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6579076469462383139L;

	private String specialTaskDataId;

    private String specialTaskId;

    private Integer clientUserId;

    private Byte popType;

    private String popId;

    private Date execDate;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public String getSpecialTaskDataId() {
        return specialTaskDataId;
    }

    public void setSpecialTaskDataId(String specialTaskDataId) {
        this.specialTaskDataId = specialTaskDataId == null ? null : specialTaskDataId.trim();
    }

    public String getSpecialTaskId() {
        return specialTaskId;
    }

    public void setSpecialTaskId(String specialTaskId) {
        this.specialTaskId = specialTaskId == null ? null : specialTaskId.trim();
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public Byte getPopType() {
        return popType;
    }

    public void setPopType(Byte popType) {
        this.popType = popType;
    }

    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId == null ? null : popId.trim();
    }

    public Date getExecDate() {
        return execDate;
    }

    public void setExecDate(Date execDate) {
        this.execDate = execDate;
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