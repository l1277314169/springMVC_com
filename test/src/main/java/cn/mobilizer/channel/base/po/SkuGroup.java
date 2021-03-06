package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;

public class SkuGroup implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -3775225481015341174L;

	private Integer skuGroupId;

    private String groupName;

    private String groupNameEn;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getSkuGroupId() {
        return skuGroupId;
    }

    public void setSkuGroupId(Integer skuGroupId) {
        this.skuGroupId = skuGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupNameEn() {
        return groupNameEn;
    }

    public void setGroupNameEn(String groupNameEn) {
        this.groupNameEn = groupNameEn == null ? null : groupNameEn.trim();
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
}