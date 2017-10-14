package cn.mobilizer.channel.survey.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MsiQuestion implements Serializable{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -3109132857232163226L;

	private Integer msiQuestionId;

    private Integer msiSurveyId;

    private String questionNo;

    private String question;

    private Byte questionType;

    private BigDecimal point;

    private Integer sequence;

    private String col1;

    private String col2;

    private String col3;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private Integer parentId;
    
    private Integer questionGroupId;
    
    private Integer isEditable;

    public Integer getMsiQuestionId() {
        return msiQuestionId;
    }

    public void setMsiQuestionId(Integer msiQuestionId) {
        this.msiQuestionId = msiQuestionId;
    }

    public Integer getMsiSurveyId() {
        return msiSurveyId;
    }

    public void setMsiSurveyId(Integer msiSurveyId) {
        this.msiSurveyId = msiSurveyId;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo == null ? null : questionNo.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public Byte getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Byte questionType) {
        this.questionType = questionType;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getQuestionGroupId() {
		return questionGroupId;
	}

	public void setQuestionGroupId(Integer questionGroupId) {
		this.questionGroupId = questionGroupId;
	}

	public Integer getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Integer isEditable) {
		this.isEditable = isEditable;
	}
}