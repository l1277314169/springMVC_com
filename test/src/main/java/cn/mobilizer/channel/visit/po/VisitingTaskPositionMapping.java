package cn.mobilizer.channel.visit.po;

import java.io.Serializable;
import java.util.Date;

public class VisitingTaskPositionMapping implements Serializable  {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 4662057679585909997L;

	private Integer mappingId;

    private Integer visitingTaskId;

    private Integer clientPositionId;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getMappingId() {
        return mappingId;
    }

    public void setMappingId(Integer mappingId) {
        this.mappingId = mappingId;
    }

    public Integer getVisitingTaskId() {
        return visitingTaskId;
    }

    public void setVisitingTaskId(Integer visitingTaskId) {
        this.visitingTaskId = visitingTaskId;
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