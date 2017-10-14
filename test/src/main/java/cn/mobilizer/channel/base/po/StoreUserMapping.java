package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;

public class StoreUserMapping implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mappingId;

    private String storeId;

    private Integer clientUserId;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    /**扩展字段**/
    private String name;
    
    private String clientUserIdAndStoreNo;
    
    public String getClientUserIdAndStoreNo() {
		return clientUserIdAndStoreNo;
	}

	public void setClientUserIdAndStoreNo(String clientUserIdAndStoreNo) {
		this.clientUserIdAndStoreNo = clientUserIdAndStoreNo;
	}

	public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId == null ? null : mappingId.trim();
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
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
	
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}