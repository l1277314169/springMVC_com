package cn.mobilizer.channel.specialTask.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;



public class SpecialTaskParameter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1614780663128071848L;

	private String specialTaskParameterId;

    private String specialTaskId;

    private String parameterName;

    private Integer controlType;

    private String controlName;

    private Integer sequence;

    private Byte isMustDo;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private String specialTaskName;
    
    private String value;
    

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSpecialTaskName() {
		return specialTaskName;
	}

	public void setSpecialTaskName(String specialTaskName) {
		this.specialTaskName = specialTaskName;
	}

	public String getSpecialTaskParameterId() {
        return specialTaskParameterId;
    }

    public void setSpecialTaskParameterId(String specialTaskParameterId) {
        this.specialTaskParameterId = specialTaskParameterId == null ? null : specialTaskParameterId.trim();
    }

    public String getSpecialTaskId() {
        return specialTaskId;
    }

    public void setSpecialTaskId(String specialTaskId) {
        this.specialTaskId = specialTaskId == null ? null : specialTaskId.trim();
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName == null ? null : parameterName.trim();
    }

    public Integer getControlType() {
        return controlType;
    }

    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName == null ? null : controlName.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Byte getIsMustDo() {
        return isMustDo;
    }

    public void setIsMustDo(Byte isMustDo) {
        this.isMustDo = isMustDo;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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