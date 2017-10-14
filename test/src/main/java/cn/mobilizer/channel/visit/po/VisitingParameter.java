package cn.mobilizer.channel.visit.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VisitingParameter implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 5280912516409301667L;

	private Integer visitingParameterId;

    private Integer visitingTaskStepId;

    private Byte parameterType;

    private String parameterName;

    private String parameterNameEn;

    private Integer controlType;

    private String controlName;

    private Integer sequence;

    private BigDecimal maxValue;

    private BigDecimal minValue;

    private String defaultValue;

    private Byte scale;

    private String unit;

    private BigDecimal weight;

    private Byte isMustDo;

    private Byte isRemember;

    private Integer isVerify;
    
    private Byte isEditable;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getVisitingParameterId() {
        return visitingParameterId;
    }

    public void setVisitingParameterId(Integer visitingParameterId) {
        this.visitingParameterId = visitingParameterId;
    }

    public Integer getVisitingTaskStepId() {
        return visitingTaskStepId;
    }

    public void setVisitingTaskStepId(Integer visitingTaskStepId) {
        this.visitingTaskStepId = visitingTaskStepId;
    }

    public Byte getParameterType() {
        return parameterType;
    }

    public void setParameterType(Byte parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName == null ? null : parameterName.trim();
    }

    public String getParameterNameEn() {
        return parameterNameEn;
    }

    public void setParameterNameEn(String parameterNameEn) {
        this.parameterNameEn = parameterNameEn == null ? null : parameterNameEn.trim();
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

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    public Byte getScale() {
        return scale;
    }

    public void setScale(Byte scale) {
        this.scale = scale;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Byte getIsMustDo() {
        return isMustDo;
    }

    public void setIsMustDo(Byte isMustDo) {
        this.isMustDo = isMustDo;
    }

    public Byte getIsRemember() {
        return isRemember;
    }

    public void setIsRemember(Byte isRemember) {
        this.isRemember = isRemember;
    }

    public Integer getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(Integer isVerify) {
        this.isVerify = isVerify;
    }

    
	public Byte getIsEditable(){
		return isEditable;
	}

	
	public void setIsEditable(Byte isEditable){
		this.isEditable = isEditable;
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