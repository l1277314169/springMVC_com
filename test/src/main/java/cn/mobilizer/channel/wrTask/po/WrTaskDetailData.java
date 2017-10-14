package cn.mobilizer.channel.wrTask.po;

import java.util.Date;

public class WrTaskDetailData {
    private String wrTaskDetailId;

    private String wrTaskDataId;

    private Integer wrTaskParameterId;

    private String value;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private Integer controlType;    //扩展字段
    
    private String optionName;		 //扩展字段

    public String getWrTaskDetailId() {
        return wrTaskDetailId;
    }

    public void setWrTaskDetailId(String wrTaskDetailId) {
        this.wrTaskDetailId = wrTaskDetailId == null ? null : wrTaskDetailId.trim();
    }

    public String getWrTaskDataId() {
        return wrTaskDataId;
    }

    public void setWrTaskDataId(String wrTaskDataId) {
        this.wrTaskDataId = wrTaskDataId == null ? null : wrTaskDataId.trim();
    }

    public Integer getWrTaskParameterId() {
        return wrTaskParameterId;
    }

    public void setWrTaskParameterId(Integer wrTaskParameterId) {
        this.wrTaskParameterId = wrTaskParameterId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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

	public Integer getControlType() {
		return controlType;
	}

	public void setControlType(Integer controlType) {
		this.controlType = controlType;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
}