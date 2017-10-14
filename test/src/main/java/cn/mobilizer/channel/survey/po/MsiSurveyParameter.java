package cn.mobilizer.channel.survey.po;

import java.math.BigDecimal;
import java.util.Date;

public class MsiSurveyParameter {
    private Integer msiSurveyParameterId;

    private Integer msiQuestionId;

    private String parameterName;

    private Integer parameterValue;

    private Integer controlType;

    private String optionName;

    private BigDecimal minValue;

    private BigDecimal maxValue;

    private String defaultValue;

    private Byte scale;

    private Byte isVerify;

    private Byte isEditable;

    private Byte isVisible;

    private Byte isHidden;

    private Integer sequence;

    private String col1;

    private String col2;

    private String col3;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getMsiSurveyParameterId() {
        return msiSurveyParameterId;
    }

    public void setMsiSurveyParameterId(Integer msiSurveyParameterId) {
        this.msiSurveyParameterId = msiSurveyParameterId;
    }

    public Integer getMsiQuestionId() {
        return msiQuestionId;
    }

    public void setMsiQuestionId(Integer msiQuestionId) {
        this.msiQuestionId = msiQuestionId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName == null ? null : parameterName.trim();
    }

    public Integer getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(Integer parameterValue) {
        this.parameterValue = parameterValue;
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

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
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

    public Byte getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Byte isVisible) {
        this.isVisible = isVisible;
    }

    public Byte getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Byte isHidden) {
        this.isHidden = isHidden;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1 == null ? null : col1.trim();
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2 == null ? null : col2.trim();
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3 == null ? null : col3.trim();
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