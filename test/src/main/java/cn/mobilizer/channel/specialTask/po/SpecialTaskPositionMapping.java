package cn.mobilizer.channel.specialTask.po;

import java.io.Serializable;
import java.util.Date;

public class SpecialTaskPositionMapping implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3295572114910886420L;

	private String mappingId;

    private String specialTaskId;

    private Integer clientPositionId;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId == null ? null : mappingId.trim();
    }

    public String getSpecialTaskId() {
        return specialTaskId;
    }

    public void setSpecialTaskId(String specialTaskId) {
        this.specialTaskId = specialTaskId == null ? null : specialTaskId.trim();
    }

    public Integer getClientPositionId() {
        return clientPositionId;
    }

    public void setClientPositionId(Integer clientPositionId) {
        this.clientPositionId = clientPositionId;
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