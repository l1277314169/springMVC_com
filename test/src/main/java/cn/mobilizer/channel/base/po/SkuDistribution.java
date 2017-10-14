package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;

public class SkuDistribution implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8689933148077680276L;

	private Integer skuDistributionId;

    private String groupName;

    private Integer sequence;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    /**关联产品分销和终端映射*/
    private Integer channelId;
    
    private Integer chainId;
    
    private Integer distributorId;
    
    /**产品分销和产品映射*/
    private String skuIds;
    
    private String oldSkuIds;
	/**统计*/
    private Integer skuNumber;
    /**映射字段*/
    private String chainName;
    
    private String channelName;
    
    private String distributorName;

    public String getOldSkuIds() {
		return oldSkuIds;
	}

	public void setOldSkuIds(String oldSkuIds) {
		this.oldSkuIds = oldSkuIds;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getChainName() {
		return chainName;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getSkuNumber() {
		return skuNumber;
	}

	public void setSkuNumber(Integer skuNumber) {
		this.skuNumber = skuNumber;
	}

	public String getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(String skuIds) {
		this.skuIds = skuIds;
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

	public Integer getSkuDistributionId() {
        return skuDistributionId;
    }

    public void setSkuDistributionId(Integer skuDistributionId) {
        this.skuDistributionId = skuDistributionId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
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