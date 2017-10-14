/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.vo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import cn.mobilizer.channel.base.po.WyethContractDetail;

public class ContractContent {
	private String storeNo;
	private String province;
	private String city;
	private String storeName;
	private String channelName;
	private String chainName;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date signDate;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date endDate;
	private String structureName;
	private Integer structureId;
	private String contractId;
	private String contractPic;
	private String contractNo;
	private Byte isDelete;
	private List<WyethContractDetail> wcds;
	private List<String> contractPics;
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

	private String acctType;

	private String bankName;

	private String bankAcct;

	private String acctHolder;
 
	private Integer clientId;
	private Integer lastUpdateBy;
	private String storeId;
	private Integer clientUserId;
	private String licenseType;
	
	public Byte getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}
	public String getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
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
		this.storeId = storeId;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Integer getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(Integer lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public List<String> getContractPics() {
		if(this.contractPic==null||this.contractPic.equals("")){
			return null;
		}
		return Arrays.asList(this.contractPic.split(","));
	}
	public void setContractPics(List<String> contractPics) {
		this.contractPics = contractPics;
	}
	public String getContractPic() {
		return contractPic;
	}
	public void setContractPic(String contractPic) {
		this.contractPic = contractPic;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getStructureName() {
		return structureName;
	}
	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChainName() {
		return chainName;
	}
	public void setChainName(String chainName) {
		this.chainName = chainName;
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
	public Integer getStructureId() {
		return structureId;
	}
	public void setStructureId(Integer structureId) {
		this.structureId = structureId;
	}
	
	public List<WyethContractDetail> getWcds() {
		return wcds;
	}
	public void setWcds(List<WyethContractDetail> wcds) {
		this.wcds = wcds;
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
		this.otherInvest = otherInvest;
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
	
}
