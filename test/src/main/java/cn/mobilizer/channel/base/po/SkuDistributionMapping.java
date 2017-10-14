package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;

public class SkuDistributionMapping implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -300651643633020108L;

	private Integer mappingId;

    private Integer skuDistributionId;

    private Integer skuId;

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

    public Integer getSkuDistributionId() {
        return skuDistributionId;
    }

    public void setSkuDistributionId(Integer skuDistributionId) {
        this.skuDistributionId = skuDistributionId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
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