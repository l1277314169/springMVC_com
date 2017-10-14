package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SkuPrice implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1906656722175830081L;

	private Integer skuPriceId;

    private Integer skuId;

    private BigDecimal price;

    private String remark;

    private Integer skuPriceGroupId;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    

	public Integer getSkuPriceId() {
        return skuPriceId;
    }

    public void setSkuPriceId(Integer skuPriceId) {
        this.skuPriceId = skuPriceId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSkuPriceGroupId() {
        return skuPriceGroupId;
    }

    public void setSkuPriceGroupId(Integer skuPriceGroupId) {
        this.skuPriceGroupId = skuPriceGroupId;
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