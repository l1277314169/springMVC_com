package cn.mobilizer.channel.survey.po;

import java.util.Date;
import java.util.List;

public class SurveySub {
	private Integer subSurveyId;

	private Integer surveyId;

	private String subSurveyTopic;

	private Byte subSurveyType;

	private Byte isMustDo;

	private String theadCols;

	private Integer sequence;

	private String remark;

	private Integer clientId;

	private Date createTime;

	private Date lastUpdateTime;

	private Byte isDelete;

	private Integer blockId;

	private List<SurveyObject> surveyObjects;

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public List<SurveyObject> getSurveyObjects() {
		return surveyObjects;
	}

	public void setSurveyObjects(List<SurveyObject> surveyObjects) {
		this.surveyObjects = surveyObjects;
	}

	public Integer getSubSurveyId() {
		return subSurveyId;
	}

	public void setSubSurveyId(Integer subSurveyId) {
		this.subSurveyId = subSurveyId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getSubSurveyTopic() {
		return subSurveyTopic;
	}

	public void setSubSurveyTopic(String subSurveyTopic) {
		this.subSurveyTopic = subSurveyTopic == null ? null : subSurveyTopic
				.trim();
	}

	public Byte getSubSurveyType() {
		return subSurveyType;
	}

	public void setSubSurveyType(Byte subSurveyType) {
		this.subSurveyType = subSurveyType;
	}

	public Byte getIsMustDo() {
		return isMustDo;
	}

	public void setIsMustDo(Byte isMustDo) {
		this.isMustDo = isMustDo;
	}

	public String getTheadCols() {
		return theadCols;
	}

	public void setTheadCols(String theadCols) {
		this.theadCols = theadCols == null ? null : theadCols.trim();
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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