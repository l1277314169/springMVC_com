package cn.mobilizer.channel.base.po;

import java.math.BigDecimal;
import java.util.Date;

public class WyethContract {
	private String contractId;

	private String contractNo;

	private String storeId;

	private Integer clientUserId;

	private Date signDate;

	private Date startDate;

	private Date endDate;

	private String gift;

	private BigDecimal giftPrice;

	private String otherGift;

	private BigDecimal otherPrice;

	private String optionsOfInvest;

	private BigDecimal valOfShelf;

	private String otherInvest;

	private String hasInvoice;

	private String invoiceType;

	private String otherInvoice;

	private String licenseType;

	private String acctType;

	private String bankName;

	private String bankAcct;

	private String acctHolder;

	private Integer clientId;

	private Integer createBy;

	private Date createTime;

	private Integer lastUpdateBy;

	private Date lastUpdateTime;
	private String contractPic;
	private Byte isDelete;
	private String storeNo;
	private String keyName;
	private String storeName;
	public String getContractPic() {
		return contractPic;
	}

	public void setContractPic(String contractPic) {
		this.contractPic = contractPic;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId == null ? null : contractId.trim();
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo == null ? null : contractNo.trim();
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId == null ? null : storeId.trim();
	}

	public Integer getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
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

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift == null ? null : gift.trim();
	}

	public BigDecimal getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(BigDecimal giftPrice) {
		this.giftPrice = giftPrice;
	}

	public String getOtherGift() {
		return otherGift;
	}

	public void setOtherGift(String otherGift) {
		this.otherGift = otherGift == null ? null : otherGift.trim();
	}

	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}

	public String getOptionsOfInvest() {
		return optionsOfInvest;
	}

	public void setOptionsOfInvest(String optionsOfInvest) {
		this.optionsOfInvest = optionsOfInvest == null ? null : optionsOfInvest
				.trim();
	}

	public BigDecimal getValOfShelf() {
		return valOfShelf;
	}

	public void setValOfShelf(BigDecimal valOfShelf) {
		this.valOfShelf = valOfShelf;
	}

	public String getOtherInvest() {
		return otherInvest;
	}

	public void setOtherInvest(String otherInvest) {
		this.otherInvest = otherInvest == null ? null : otherInvest.trim();
	}

	public String getHasInvoice() {
		return hasInvoice;
	}

	public void setHasInvoice(String hasInvoice) {
		this.hasInvoice = hasInvoice == null ? null : hasInvoice.trim();
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType == null ? null : invoiceType.trim();
	}

	public String getOtherInvoice() {
		return otherInvoice;
	}

	public void setOtherInvoice(String otherInvoice) {
		this.otherInvoice = otherInvoice == null ? null : otherInvoice.trim();
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType == null ? null : licenseType.trim();
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType == null ? null : acctType.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct == null ? null : bankAcct.trim();
	}

	public String getAcctHolder() {
		return acctHolder;
	}

	public void setAcctHolder(String acctHolder) {
		this.acctHolder = acctHolder == null ? null : acctHolder.trim();
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
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