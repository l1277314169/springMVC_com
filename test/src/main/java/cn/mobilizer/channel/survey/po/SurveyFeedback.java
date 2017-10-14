package cn.mobilizer.channel.survey.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SurveyFeedback {
	
    private String feedbackId;

    private String popId;

    private Integer clientUserId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date feedbackDate;

    private Integer surveyId;

    private Date inTime;

    private String inPic;

    private String inLongitude;

    private String inLatitude;

    private Byte inLocatingType;

    private Date outTime;

    private String outPic;

    private String outLongitude;

    private String outLatitude;

    private String remark;

    private Byte status;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Integer submitBy;

    private Date lastUpdateTime;

    private Byte isDelete;

    private String visitor;
    
    private String storeNo;
    
    private String feedbackNo;
    
    public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId == null ? null : feedbackId.trim();
    }

    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId == null ? null : popId.trim();
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getInPic() {
        return inPic;
    }

    public void setInPic(String inPic) {
        this.inPic = inPic == null ? null : inPic.trim();
    }

    public String getInLongitude() {
        return inLongitude;
    }

    public void setInLongitude(String inLongitude) {
        this.inLongitude = inLongitude == null ? null : inLongitude.trim();
    }

    public String getInLatitude() {
        return inLatitude;
    }

    public void setInLatitude(String inLatitude) {
        this.inLatitude = inLatitude == null ? null : inLatitude.trim();
    }

    public Byte getInLocatingType() {
        return inLocatingType;
    }

    public void setInLocatingType(Byte inLocatingType) {
        this.inLocatingType = inLocatingType;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getOutPic() {
        return outPic;
    }

    public void setOutPic(String outPic) {
        this.outPic = outPic == null ? null : outPic.trim();
    }

    public String getOutLongitude() {
        return outLongitude;
    }

    public void setOutLongitude(String outLongitude) {
        this.outLongitude = outLongitude == null ? null : outLongitude.trim();
    }

    public String getOutLatitude() {
        return outLatitude;
    }

    public void setOutLatitude(String outLatitude) {
        this.outLatitude = outLatitude == null ? null : outLatitude.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public Integer getSubmitBy() {
        return submitBy;
    }

    public void setSubmitBy(Integer submitBy) {
        this.submitBy = submitBy;
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

	public String getFeedbackNo() {
		return feedbackNo;
	}

	public void setFeedbackNo(String feedbackNo) {
		this.feedbackNo = feedbackNo;
	}
}