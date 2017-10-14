package cn.mobilizer.channel.visit.po;

import java.io.Serializable;
import java.util.Date;

public class VisitingPopMapping implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -8090670828297399468L;

	private String mappingId;

    private Integer visitingTaskId;

    private String popId;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId == null ? null : mappingId.trim();
    }

    public Integer getVisitingTaskId() {
        return visitingTaskId;
    }

    public void setVisitingTaskId(Integer visitingTaskId) {
        this.visitingTaskId = visitingTaskId;
    }

    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId == null ? null : popId.trim();
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