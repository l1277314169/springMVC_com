package cn.mobilizer.channel.survey.po;

import java.util.Date;

import org.apache.log4j.Logger;

public class SurveyFeedbackDetail {

	protected Logger log = Logger.getLogger(this.getClass());
	
	private String detailId;

	private String feedbackId;

	private Integer subSurveyId;

	private Integer objectId;

	private Integer surveyParameterId;

	private String value;

	private String col1;

	private String col2;

	private String col3;

	private Integer clientId;

	private Date createTime;

	private Date submitTime;

	private Date lastUpdateTime;

	private Byte isDelete;

	private String objectNo;

	private String controlName;
	
	private int controlType;

	public String getControlName() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getObjectId());
		//buffer.append("_").append(getObjectNo());
		buffer.append("_").append(getSurveyParameterId());
		buffer.append("_").append(getSubSurveyId());
		this.controlName = buffer.toString();
		log.debug("====> name:"+this.controlName+",value:"+getValue());
		return controlName;
	}
	
	public int getControlType() {
		return controlType;
	}



	public void setControlType(int controlType) {
		this.controlType = controlType;
	}



	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId == null ? null : detailId.trim();
	}

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId == null ? null : feedbackId.trim();
	}

	public Integer getSubSurveyId() {
		return subSurveyId;
	}

	public void setSubSurveyId(Integer subSurveyId) {
		this.subSurveyId = subSurveyId;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public Integer getSurveyParameterId() {
		return surveyParameterId;
	}

	public void setSurveyParameterId(Integer surveyParameterId) {
		this.surveyParameterId = surveyParameterId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value == null ? null : value.trim();
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