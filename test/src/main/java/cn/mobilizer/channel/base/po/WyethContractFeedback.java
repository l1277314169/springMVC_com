package cn.mobilizer.channel.base.po;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WyethContractFeedback {
    private String feedbackId;

    private String contractId;

    private String contractNo;

    private Integer monthId;

    private Integer numOfGift;

    private Integer numOfMember;
    private BigDecimal rateOfReview;

    private Integer num1OfUnqualified;

    private Integer num2OfUnqualified;

    private Integer numOfVerification;
    private Byte status;

    private String reason;

    private String permitPic;

    private String contractPic;

    private String invoicePic;

    private Integer confirmBy;

    private Integer createBy;

    private Date createTime;

    private Integer lastUpdateBy;

    private Date lastUpdateTime;

    private Byte isDelete;
    private String storeNo;
    private String storeName;
    private Integer value;
    private Byte dataType;
    private String rateOfReviews;
    private List<String> invoicePics;
    
    public String getRateOfReviews() {
		return rateOfReviews;
	}

	public void setRateOfReviews(String rateOfReviews) {
		this.rateOfReviews = rateOfReviews;
	}

	public List<String> getInvoicePics() {
		if(this.invoicePic==null||this.invoicePic.equals("")){
			return null;
		}
    	return Arrays.asList(this.invoicePic.split(","));
	
	}

	public void setInvoicePics(List<String> invoicePics) {
		this.invoicePics = invoicePics;
	}

	public BigDecimal getRateOfReview() {
		return rateOfReview;
	}

	public void setRateOfReview(BigDecimal rateOfReview) {
		this.rateOfReview = rateOfReview;
	}

	public Integer getNum1OfUnqualified() {
		return num1OfUnqualified;
	}

	public void setNum1OfUnqualified(Integer num1OfUnqualified) {
		this.num1OfUnqualified = num1OfUnqualified;
	}

	public Integer getNum2OfUnqualified() {
		return num2OfUnqualified;
	}

	public void setNum2OfUnqualified(Integer num2OfUnqualified) {
		this.num2OfUnqualified = num2OfUnqualified;
	}

	public Integer getNumOfVerification() {
		return numOfVerification;
	}

	public void setNumOfVerification(Integer numOfVerification) {
		this.numOfVerification = numOfVerification;
	}

	public Byte getDataType() {
		return dataType;
	}

	public void setDataType(Byte dataType) {
		this.dataType = dataType;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	
    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId == null ? null : feedbackId.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public Integer getNumOfGift() {
        return numOfGift;
    }

    public void setNumOfGift(Integer numOfGift) {
        this.numOfGift = numOfGift;
    }

    public Integer getNumOfMember() {
        return numOfMember;
    }

    public void setNumOfMember(Integer numOfMember) {
        this.numOfMember = numOfMember;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getPermitPic() {
        return permitPic;
    }

    public void setPermitPic(String permitPic) {
        this.permitPic = permitPic == null ? null : permitPic.trim();
    }

    public String getContractPic() {
        return contractPic;
    }

    public void setContractPic(String contractPic) {
        this.contractPic = contractPic == null ? null : contractPic.trim();
    }

    public String getInvoicePic() {
        return invoicePic;
    }

    public void setInvoicePic(String invoicePic) {
        this.invoicePic = invoicePic == null ? null : invoicePic.trim();
    }

    public Integer getConfirmBy() {
        return confirmBy;
    }

    public void setConfirmBy(Integer confirmBy) {
        this.confirmBy = confirmBy;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Integer lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
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