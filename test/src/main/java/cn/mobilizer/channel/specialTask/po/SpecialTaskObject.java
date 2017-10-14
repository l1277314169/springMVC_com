package cn.mobilizer.channel.specialTask.po;

import java.io.Serializable;
import java.util.Date;

public class SpecialTaskObject implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1837547845460995119L;

	private String specialTaskObjectId;

    private String specialTaskId;

    private Byte objectType;

    private String objectId;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    /**映射字段*/
    private String objectName;
    
    private Integer clientUserId;
    
    private Integer clientPositionId;
    
    private String userName;

    private String addr;

	private Integer stdClientUserId;
	
	private String specialTaskDataId;
    
	public String getSpecialTaskDataId() {
		return specialTaskDataId;
	}

	public void setSpecialTaskDataId(String specialTaskDataId) {
		this.specialTaskDataId = specialTaskDataId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
    public Integer getClientPositionId() {
		return clientPositionId;
	}

	public void setClientPositionId(Integer clientPositionId) {
		this.clientPositionId = clientPositionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStdClientUserId() {
		return stdClientUserId;
	}

	public void setStdClientUserId(Integer stdClientUserId) {
		this.stdClientUserId = stdClientUserId;
	}


    public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}


	public Integer getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getSpecialTaskObjectId() {
        return specialTaskObjectId;
    }

    public void setSpecialTaskObjectId(String specialTaskObjectId) {
        this.specialTaskObjectId = specialTaskObjectId == null ? null : specialTaskObjectId.trim();
    }

    public String getSpecialTaskId() {
        return specialTaskId;
    }

    public void setSpecialTaskId(String specialTaskId) {
        this.specialTaskId = specialTaskId == null ? null : specialTaskId.trim();
    }

    public Byte getObjectType() {
        return objectType;
    }

    public void setObjectType(Byte objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
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

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
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