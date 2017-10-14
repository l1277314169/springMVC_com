package cn.mobilizer.channel.survey.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MsiAnswer implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 7976005015671269447L;

	private Integer msiAnswerId;

    private Integer msiQuestionId;

    private String answerNo;

    private String answer;

    private Byte isReset;

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
    
    private Byte checked;
    
    private String parameterIds;
    
    private List<MsiSurveyParameter> msiSurveyParameters;
    
    private Map<String, Object> msiSurveyParameterDataMap;

    public Integer getMsiAnswerId() {
        return msiAnswerId;
    }

    public void setMsiAnswerId(Integer msiAnswerId) {
        this.msiAnswerId = msiAnswerId;
    }

    public Integer getMsiQuestionId() {
        return msiQuestionId;
    }

    public void setMsiQuestionId(Integer msiQuestionId) {
        this.msiQuestionId = msiQuestionId;
    }

    public String getAnswerNo() {
        return answerNo;
    }

    public void setAnswerNo(String answerNo) {
        this.answerNo = answerNo == null ? null : answerNo.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Byte getIsReset() {
        return isReset;
    }

    public void setIsReset(Byte isReset) {
        this.isReset = isReset;
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

	public Byte getChecked(){
		return checked;
	}

	public void setChecked(Byte checked){
		this.checked = checked;
	}

	public String getParameterIds() {
		return parameterIds;
	}

	public void setParameterIds(String parameterIds) {
		this.parameterIds = parameterIds;
	}

	public List<MsiSurveyParameter> getMsiSurveyParameters() {
		return msiSurveyParameters;
	}

	public void setMsiSurveyParameters(List<MsiSurveyParameter> msiSurveyParameters) {
		this.msiSurveyParameters = msiSurveyParameters;
	}

	public Map<String, Object> getMsiSurveyParameterDataMap() {
		return msiSurveyParameterDataMap;
	}

	public void setMsiSurveyParameterDataMap(
			Map<String, Object> msiSurveyParameterDataMap) {
		this.msiSurveyParameterDataMap = msiSurveyParameterDataMap;
	}

}