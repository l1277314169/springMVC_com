package cn.mobilizer.channel.specialTask.po;

import java.io.Serializable;
import java.util.Date;

public class SpecialTaskDetailData implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7245908487834546088L;

	private String specialTaskDetailDataId;

    private String specialTaskDataId;

    private String parameterId;

    private String value;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public String getSpecialTaskDetailDataId() {
        return specialTaskDetailDataId;
    }

    public void setSpecialTaskDetailDataId(String specialTaskDetailDataId) {
        this.specialTaskDetailDataId = specialTaskDetailDataId == null ? null : specialTaskDetailDataId.trim();
    }

    public String getSpecialTaskDataId() {
        return specialTaskDataId;
    }

    public void setSpecialTaskDataId(String specialTaskDataId) {
        this.specialTaskDataId = specialTaskDataId == null ? null : specialTaskDataId.trim();
    }

    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId == null ? null : parameterId.trim();
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
}