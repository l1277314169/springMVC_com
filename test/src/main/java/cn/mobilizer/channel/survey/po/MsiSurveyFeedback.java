package cn.mobilizer.channel.survey.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MsiSurveyFeedback implements Serializable{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -1662560631844063350L;
    
	private String feedbackId;

    private Integer clientUserId;
   
    private String storeId;

    private Date feedbackDate;

    private Date startTime;

    private Date endTime;

    private Integer msiSurveyId;

    private String visitor;

    private String promoter;

    private String promoterNo;

    private BigDecimal score;

    private Byte status;    //0访问员数据，1审核员数据，2经销商数据

    private Integer clientId;
    
    private Date createTime;

    private Integer createBy;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private Integer onDuty;
    
    /**扩展字段**/
    private String createUserName;
    
    private String storeNo;
    
    private String storeName;
    
    private String addr;
    
    private BigDecimal point;
    
    private BigDecimal initialScore;
    
    private Byte dataType;
    
	public BigDecimal getInitialScore() {
		return initialScore;
	}

	public void setInitialScore(BigDecimal initialScore) {
		this.initialScore = initialScore;
	}

	public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId == null ? null : feedbackId.trim();
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getEndTime(){
		return endTime;
	}

	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}

	public Integer getMsiSurveyId() {
        return msiSurveyId;
    }

    public void setMsiSurveyId(Integer msiSurveyId) {
        this.msiSurveyId = msiSurveyId;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor == null ? null : visitor.trim();
    }

    public String getPromoter() {
        return promoter;
    }

    public void setPromoter(String promoter) {
        this.promoter = promoter == null ? null : promoter.trim();
    }

    public String getPromoterNo() {
        return promoterNo;
    }

    public void setPromoterNo(String promoterNo) {
        this.promoterNo = promoterNo == null ? null : promoterNo.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
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

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
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

	public String getStoreNo(){
		return storeNo;
	}
	
	public void setStoreNo(String storeNo){
		this.storeNo = storeNo;
	}

	public String getStoreName(){
		return storeName;
	}
	
	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	public String getAddr(){
		return addr;
	}

	public void setAddr(String addr){
		this.addr = addr;
	}
	
	public String getCreateUserName(){
		return createUserName;
	}
	
	public void setCreateUserName(String createUserName){
		this.createUserName = createUserName;
	}

	public BigDecimal getPoint(){
		return point;
	}
	
	public void setPoint(BigDecimal point){
		this.point = point;
	}

	public Integer getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(Integer onDuty) {
		this.onDuty = onDuty;
	}

	public Byte getDataType() {
		return dataType;
	}

	public void setDataType(Byte dataType) {
		this.dataType = dataType;
	}
	
}