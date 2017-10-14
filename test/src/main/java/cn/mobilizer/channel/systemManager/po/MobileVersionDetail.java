package cn.mobilizer.channel.systemManager.po;

import java.io.Serializable;
import java.util.Date;

public class MobileVersionDetail implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5468376297052047246L;

	private Integer detailId;

    private Integer mobileVersionId;

    private Integer clientUserId;

    private Byte mustUpdate;

    private Integer isUpdated;

    private Date updatedTime;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    private Date upateTime;
    
    private String clientUserIds;

    public String getClientUserIds() {
		return clientUserIds;
	}

	public void setClientUserIds(String clientUserIds) {
		this.clientUserIds = clientUserIds;
	}

	public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getMobileVersionId() {
        return mobileVersionId;
    }

    public void setMobileVersionId(Integer mobileVersionId) {
        this.mobileVersionId = mobileVersionId;
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public Byte getMustUpdate() {
        return mustUpdate;
    }

    public void setMustUpdate(Byte mustUpdate) {
        this.mustUpdate = mustUpdate;
    }

    public Integer getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(Integer isUpdated) {
        this.isUpdated = isUpdated;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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

    public Date getUpateTime() {
        return upateTime;
    }

    public void setUpateTime(Date upateTime) {
        this.upateTime = upateTime;
    }
}