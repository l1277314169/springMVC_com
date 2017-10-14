package cn.mobilizer.channel.survey.po;

import java.math.BigDecimal;
import java.util.Date;

public class SurveyParameter {
	
    private Integer surveyParameterId;

    private String parameterName;

    private Integer subSurveyId;

    private Integer parameterValue;

    private Integer controlType;

    private String optionName;

    private BigDecimal minValue;

    private BigDecimal maxValue;

    private String defaultValue;

    private Byte isVerify;
    
    private Byte isVisible;

    private Byte isEditable;
    
    private Byte isHidden;

    private String calcFormula;

    private Integer gotoQuestionId;

    private Integer sequence;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private String width;
    
    private Byte scale;
    
    public Byte getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Byte isHidden) {
		this.isHidden = isHidden;
	}

	public Byte getScale() {
		return scale;
	}

	public void setScale(Byte scale) {
		this.scale = scale;
	}

	public Byte getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Byte isVisible) {
		this.isVisible = isVisible;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Integer getSurveyParameterId() {
        return surveyParameterId;
    }

    public void setSurveyParameterId(Integer surveyParameterId) {
        this.surveyParameterId = surveyParameterId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName == null ? null : parameterName.trim();
    }

    public Integer getSubSurveyId() {
        return subSurveyId;
    }

    public void setSubSurveyId(Integer subSurveyId) {
        this.subSurveyId = subSurveyId;
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

    public String getCalcFormula() {
        return calcFormula;
    }

    public void setCalcFormula(String calcFormula) {
        this.calcFormula = calcFormula == null ? null : calcFormula.trim();
    }

    public Integer getGotoQuestionId() {
        return gotoQuestionId;
    }

    public void setGotoQuestionId(Integer gotoQuestionId) {
        this.gotoQuestionId = gotoQuestionId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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