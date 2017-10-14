package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;

public class PopSkuPriceGroupMapping implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6284036034006589462L;

	private Integer mappingId;

    private Integer channelId;

    private Integer chainId;

    private Integer distributorId;

    private Integer skuPriceGroupId;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    /**扩展字段*/
    private String skuPrices;
    
    private String oldSkuPrices;
    
    private String groupName;
    

	public String getOldSkuPrices() {
		return oldSkuPrices;
	}

	public void setOldSkuPrices(String oldSkuPrices) {
		this.oldSkuPrices = oldSkuPrices;
	}

	public String getSkuPrices() {
		return skuPrices;
	}

	public void setSkuPrices(String skuPrices) {
		this.skuPrices = skuPrices;
	}


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public Integer getMappingId() {
        return mappingId;
    }

    public void setMappingId(Integer mappingId) {
        this.mappingId = mappingId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
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