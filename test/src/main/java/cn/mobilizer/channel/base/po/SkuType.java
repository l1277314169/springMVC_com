package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;

public class SkuType  implements Serializable{
    private Integer skuTypeId;

    private Integer categoryId;

    private String skuTypeNo;

    private String skuTypeName;

    private Integer sequence;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getSkuTypeId() {
        return skuTypeId;
    }

    public void setSkuTypeId(Integer skuTypeId) {
        this.skuTypeId = skuTypeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSkuTypeNo() {
        return skuTypeNo;
    }

    public void setSkuTypeNo(String skuTypeNo) {
        this.skuTypeNo = skuTypeNo == null ? null : skuTypeNo.trim();
    }

    public String getSkuTypeName() {
        return skuTypeName;
    }

    public void setSkuTypeName(String skuTypeName) {
        this.skuTypeName = skuTypeName == null ? null : skuTypeName.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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