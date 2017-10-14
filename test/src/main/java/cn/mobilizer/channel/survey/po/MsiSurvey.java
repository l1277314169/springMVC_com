package cn.mobilizer.channel.survey.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MsiSurvey implements Serializable{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 4592895984248383408L;

	private Integer msiSurveyId;
	
	private Integer msiSurveyTypeId;

    private String surveyNo;

    private String surveyName;

    private Date startDate;

    private Date endDate;

    private BigDecimal point;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private BigDecimal initialScore;
    
    private Integer cycleType;
    
    public BigDecimal getInitialScore() {
		return initialScore;
	}

	public void setInitialScore(BigDecimal initialScore) {
		this.initialScore = initialScore;
	}

    public Integer getMsiSurveyId() {
        return msiSurveyId;
    }

    public void setMsiSurveyId(Integer msiSurveyId) {
        this.msiSurveyId = msiSurveyId;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo == null ? null : surveyNo.trim();
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName == null ? null : surveyName.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
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

	public Integer getCycleType() {
		return cycleType;
	}

	public void setCycleType(Integer cycleType) {
		this.cycleType = cycleType;
	}

	public Integer getMsiSurveyTypeId() {
		return msiSurveyTypeId;
	}

	public void setMsiSurveyTypeId(Integer msiSurveyTypeId) {
		this.msiSurveyTypeId = msiSurveyTypeId;
	}
}