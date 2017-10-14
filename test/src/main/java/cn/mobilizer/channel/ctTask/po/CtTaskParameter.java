package cn.mobilizer.channel.ctTask.po;

import java.math.BigDecimal;
import java.util.Date;

public class CtTaskParameter {
    private Integer ctTaskParameterId;

    private Integer ctTaskId;

    private String parameterName;

    private Integer controlType;

    private String optionName;

    private Integer sequence;

    private BigDecimal maxValue;

    private BigDecimal minValue;

    private String defaultValue;

    private Byte scale;

    private String unit;

    private Integer weight;

    private Byte isMustDo;

    private Byte isRemember;

    private Byte isVerify;

    private Byte isEditable;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getCtTaskParameterId() {
        return ctTaskParameterId;
    }

    public void setCtTaskParameterId(Integer ctTaskParameterId) {
        this.ctTaskParameterId = ctTaskParameterId;
    }

    public Integer getCtTaskId() {
        return ctTaskId;
    }

    public void setCtTaskId(Integer ctTaskId) {
        this.ctTaskId = ctTaskId;
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

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName == null ? null : optionName.trim();
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
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

    public Byte getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(Byte isVerify) {
        this.isVerify = isVerify;
    }

    public Byte getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Byte isEditable) {
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