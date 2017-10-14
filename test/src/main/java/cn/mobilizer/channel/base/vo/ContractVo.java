/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import cn.mobilizer.channel.base.po.WyethContractDetail;

public class ContractVo {
	private String storeName;
	private String storeNo;
	private String storeId;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date signDate;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date endDate;
	private String contractId;
	private String gift;
	private BigDecimal giftPrice;
	private String otherGift;
	private BigDecimal otherPrice;
	private String optionsOfInvest;
	private String hasInvoice;
	private String otherInvest;
	private String invoiceType;
	private String otherInvoice;
	private String acctType;
	private String bankName;
	private String bankAcct;
	private String acctHolder;
	private String licenseType;
	private String contractPic;
	private String permitPic;
	private List<WyethContractDetail> WyethContractDetails;
	
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getContractPic() {
		return contractPic;
	}
	public void setContractPic(String contractPic) {
		this.contractPic = contractPic;
	}
	public String getPermitPic() {
		return permitPic;
	}
	public void setPermitPic(String permitPic) {
		this.permitPic = permitPic;
	}
	public String getOtherInvest() {
		return otherInvest;
	}
	public void setOtherInvest(String otherInvest) {
		this.otherInvest = otherInvest;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getGift() {
		return gift;
	}
	public void setGift(String gift) {
		this.gift = gift;
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
		this.otherGift = otherGift;
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
		this.optionsOfInvest = optionsOfInvest;
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
	public String getOtherInvoice() {
		return otherInvoice;
	}
	public void setOtherInvoice(String otherInvoice) {
		this.otherInvoice = otherInvoice;
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
	public String getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	public List<WyethContractDetail> getWyethContractDetails() {
		return WyethContractDetails;
	}
	public void setWyethContractDetails(
			List<WyethContractDetail> wyethContractDetails) {
		WyethContractDetails = wyethContractDetails;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
}
