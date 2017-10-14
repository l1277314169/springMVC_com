package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;

public class SkuSeries  implements Serializable{
    private Integer skuSeriesId;

    private Integer brandId;

    private String skuSeriesNo;

    private String skuSeriesName;

    private Integer sequence;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getSkuSeriesId() {
        return skuSeriesId;
    }

    public void setSkuSeriesId(Integer skuSeriesId) {
        this.skuSeriesId = skuSeriesId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getSkuSeriesNo() {
        return skuSeriesNo;
    }

    public void setSkuSeriesNo(String skuSeriesNo) {
        this.skuSeriesNo = skuSeriesNo == null ? null : skuSeriesNo.trim();
    }

    public String getSkuSeriesName() {
        return skuSeriesName;
    }

    public void setSkuSeriesName(String skuSeriesName) {
        this.skuSeriesName = skuSeriesName == null ? null : skuSeriesName.trim();
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