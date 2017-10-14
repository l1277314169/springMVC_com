package cn.mobilizer.channel.survey.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MsiSurveyFeedbackDetail implements Serializable{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 7608439376769922082L;

	private String detailId;

    private String feedbackId;

    private Integer msiQuestionId;

    private Integer msiAnswerId;

    private BigDecimal score;

    private String col1;

    private String col2;

    private String col3;

    private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    /**扩展字段**/
	private Boolean checked;
	
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

    public Integer getMsiQuestionId() {
        return msiQuestionId;
    }

    public void setMsiQuestionId(Integer msiQuestionId) {
        this.msiQuestionId = msiQuestionId;
    }

    public Integer getMsiAnswerId() {
        return msiAnswerId;
    }

    public void setMsiAnswerId(Integer msiAnswerId) {
        this.msiAnswerId = msiAnswerId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
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

	public Boolean getChecked(){
		return checked;
	}

	public void setChecked(Boolean checked){
		this.checked = checked;
	}
}