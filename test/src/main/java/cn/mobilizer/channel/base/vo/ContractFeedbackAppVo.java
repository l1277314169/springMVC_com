package cn.mobilizer.channel.base.vo;

import java.io.Serializable;
import java.util.Date;

import cn.mobilizer.channel.report.vo.NumberFormatUtils;

public class ContractFeedbackAppVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String feedbackId;
	private String storeCode;
	private String storeName;
	private Integer dataType;
	private String contractId;
	private Integer monthId;
	//private BigDecimal value;
	private Integer numOfGift;
	private Integer numOfMember;
	private Integer status;
	private Integer realNumOfGift; //实际礼品发放数量
	private Integer realNumOfMember; //实际会员数量
	//private BigDecimal totalAmount; //核销金额

	private String structureName;
	private String city;
	private String hasInvoice;
	private String invoiceType;
	private String acctType;
	private Integer clientId;

	private String bankName;
	private String bankAcct;
	private String acctHolder;
	private Date signDate;
	private String rateOfReview;
	private Integer num1OfUnqualified;
	private Integer num2OfUnqualified;
	private Integer numOfVerification;
	private Integer structureId;
	private String invoicePic;
	private String reason;
	private Date startDate;
	private Date endDate;
	private Double serviceCharge;	
	
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getInvoicePic() {
		return invoicePic;
	}

	public void setInvoicePic(String invoicePic) {
		this.invoicePic = invoicePic;
	}

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}
	public Integer getRealNumOfGift() {
		return realNumOfGift;
	}

	public void setRealNumOfGift(Integer realNumOfGift) {
		this.realNumOfGift = realNumOfGift;
	}

	public Integer getRealNumOfMember() {
		return realNumOfMember;
	}

	public void setRealNumOfMember(Integer realNumOfMember) {
		this.realNumOfMember = realNumOfMember;
	}

/*	public BigDecimal getTotalAmount() {
		totalAmount = new BigDecimal((realNumOfGift * 30) + (numOfVerification * 100));
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}*/

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}

	/*public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}*/

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHasInvoice() {
		return hasInvoice;
	}

	public void setHasInvoice(String hasInvoice) {
		this.hasInvoice = hasInvoice;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getAcctHolder() {
		return acctHolder;
	}

	public void setAcctHolder(String acctHolder) {
		this.acctHolder = acctHolder;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getRateOfReview() {
		return NumberFormatUtils.formatNumber(new Double(rateOfReview));
	}

	public void setRateOfReview(String rateOfReview) {
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

	public Integer getStructureId() {
		return structureId;
	}

	public void setStructureId(Integer structureId) {
		this.structureId = structureId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public Double getServiceCharge() {
		return new Double((numOfVerification*100)+(numOfGift*30));
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
}
