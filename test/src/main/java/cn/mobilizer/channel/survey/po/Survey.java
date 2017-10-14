package cn.mobilizer.channel.survey.po;

import java.util.Date;

public class Survey {

	private Integer surveyId;

	private String surveyNo;

	private String surveyName;

	private Integer surveyGroup;

	private Date startDate;

	private Date endDate;

	private Byte startLocating;

	private Byte endLocating;

	private Byte startPic;

	private Byte endPic;

	private Byte cycleType;

	private Integer validFrom;

	private Integer validEnd;

	private String remark;

	private Integer clientId;

	private Date createTime;

	private Date lastUpdateTime;

	private Byte isDelete;

	private String id;

	private String name;
	
	private String visitor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
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

	public Integer getSurveyGroup() {
		return surveyGroup;
	}

	public void setSurveyGroup(Integer surveyGroup) {
		this.surveyGroup = surveyGroup;
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

	public Byte getStartLocating() {
		return startLocating;
	}

	public void setStartLocating(Byte startLocating) {
		this.startLocating = startLocating;
	}

	public Byte getEndLocating() {
		return endLocating;
	}

	public void setEndLocating(Byte endLocating) {
		this.endLocating = endLocating;
	}

	public Byte getStartPic() {
		return startPic;
	}

	public void setStartPic(Byte startPic) {
		this.startPic = startPic;
	}

	public Byte getEndPic() {
		return endPic;
	}

	public void setEndPic(Byte endPic) {
		this.endPic = endPic;
	}

	public Byte getCycleType() {
		return cycleType;
	}

	public void setCycleType(Byte cycleType) {
		this.cycleType = cycleType;
	}

	public Integer getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Integer validFrom) {
		this.validFrom = validFrom;
	}

	public Integer getValidEnd() {
		return validEnd;
	}

	public void setValidEnd(Integer validEnd) {
		this.validEnd = validEnd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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