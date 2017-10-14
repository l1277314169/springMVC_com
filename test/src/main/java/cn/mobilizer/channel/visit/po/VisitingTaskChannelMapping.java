package cn.mobilizer.channel.visit.po;

import java.io.Serializable;
import java.util.Date;

public class VisitingTaskChannelMapping implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 6590710696703673756L;

	private Integer mappingId;

    private Integer visitingTaskId;

    private Integer channelId;

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

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
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