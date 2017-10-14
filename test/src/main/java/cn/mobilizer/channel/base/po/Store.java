package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Store implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 1638081086510314448L;

	private String storeId;

    private String storeNo;

    private String storeName;

    private String bannerPhoto;

    private Integer channelId;

    private Integer chainId;
    
    private String chainName;
    
    private Integer provinceId;
    
    private String provinceName;

    private Integer cityId;
    
    private String cityName;
    
    private Integer districtId;
    
	private String districtName;

    private String addr;

    private String longitude;

    private String latitude;

    private Integer clientStructureId;

    private Byte status;
    
    private String storeStatusText;

    private Integer creditPeriod;

    private BigDecimal creditLine;

    private Integer distributorId;
    
    private String distributorName;
    
    private Integer storeGroupId;
    
    private String storeGroupName;
    
    private String externalNo;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    private String contact;

    private String telephoneNo;

    private String mobileNo;

    private String fax;
    
    private List<ClientUser> users = new ArrayList<ClientUser>();
    
    /**关联中间表--人店映射表**/
    
    private String names;
    
    
	private Integer clientUserId;
    
   /**一个门店可能对应多个人员**/
    
    private String businessman;//业务员
    
    private String promotions;//促销员
    
    /**门店是否安排拜访*/
    
    private String visitYesOrNot;
    
    /**门店类型*/
    private Integer storeType;
    
    private String storeTypeName;
    
    private Byte callStatus;

	/**structureName-关联client_structure.structure_name**/
    
    private String structureName;
    
    private String upStructureName;
    
    private String channelName;
    
    private String upChannelName;
    
    private Date inTime;
    
    private String firstChannelName;       //一级渠道
    
    private String secondChannelName;	//二级渠道
    
    private Byte isActivation;
    
    private String storeNameAndaddr;
    
    private String loginName_One;
    
    private String loginName_Two;
    
    private String loginName_Three;
    private String keyName;
    private List<String> list;
    

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getLoginName_One() {
		return loginName_One;
	}

	public void setLoginName_One(String loginName_One) {
		this.loginName_One = loginName_One;
	}

	public String getLoginName_Two() {
		return loginName_Two;
	}

	public void setLoginName_Two(String loginName_Two) {
		this.loginName_Two = loginName_Two;
	}

	public String getLoginName_Three() {
		return loginName_Three;
	}

	public void setLoginName_Three(String loginName_Three) {
		this.loginName_Three = loginName_Three;
	}

	public String getStoreNameAndaddr() {
		return storeNameAndaddr;
	}

	public void setStoreNameAndaddr(String storeNameAndaddr) {
		this.storeNameAndaddr = storeNameAndaddr;
	}

	public Byte getIsActivation() {
		return isActivation;
	}

	public void setIsActivation(Byte isActivation) {
		this.isActivation = isActivation;
	}

	public Integer getPlanningType() {
		return planningType;
	}

	public void setPlanningType(Integer planningType) {
		this.planningType = planningType;
	}
	
	private Date outTime;
	
    private String id;
	
    private String name;
    
    //业务员门店维护是否选中
	
	private Integer isChecked;
	
    private Integer planningType;
    
    private Integer cnt; //根据城市和省统计的数量
    
    private String loginName;
    
	public String getFirstChannelName() {
		return firstChannelName;
	}

	public void setFirstChannelName(String firstChannelName) {
		this.firstChannelName = firstChannelName;
	}

	public String getSecondChannelName() {
		return secondChannelName;
	}

	public void setSecondChannelName(String secondChannelName) {
		this.secondChannelName = secondChannelName;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClientUser> getUsers() {
		return users;
	}

	public void setUsers(List<ClientUser> users) {
		this.users = users;
	}
	
   public Byte getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(Byte callStatus) {
		this.callStatus = callStatus;
	}
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo == null ? null : storeNo.trim();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public String getBannerPhoto() {
        return bannerPhoto;
    }

    public void setBannerPhoto(String bannerPhoto) {
        this.bannerPhoto = bannerPhoto == null ? null : bannerPhoto.trim();
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public Integer getClientStructureId() {
        return clientStructureId;
    }

    public void setClientStructureId(Integer clientStructureId) {
        this.clientStructureId = clientStructureId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(Integer creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public BigDecimal getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(BigDecimal creditLine) {
        this.creditLine = creditLine;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public Integer getStoreGroupId() {
        return storeGroupId;
    }

    public void setStoreGroupId(Integer storeGroupId) {
        this.storeGroupId = storeGroupId;
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
        this.lastUpdateTime = null;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo == null ? null : telephoneNo.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }
	
	public String getStructureName(){
		return structureName;
	}

	public void setStructureName(String structureName){
		this.structureName = structureName;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("storeName="+storeName);
		sb.append(",storeNo="+storeNo);
		sb.append(",addr="+addr);
		sb.append(",telephoneNo="+telephoneNo);
		return sb.toString();
	}


	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public Integer getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getChainName() {
		return chainName;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}
	
	public void addUser(ClientUser clientUser){
		users.add(clientUser);
	}
	
	public Date getInTime(){
		return inTime;
	}
	
	public Date getOutTime(){
		return outTime;
	}
	
	public void setInTime(Date inTime){
		this.inTime = inTime;
	}
	
	public void setOutTime(Date outTime){
		this.outTime = outTime;
	}

	public String getStoreStatusText() {
		return storeStatusText;
	}

	public void setStoreStatusText(String storeStatusText) {
		this.storeStatusText = storeStatusText;
	}
	
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getVisitYesOrNot() {
		return visitYesOrNot;
	}

	public void setVisitYesOrNot(String visitYesOrNot) {
		this.visitYesOrNot = visitYesOrNot;
	}
	public String getExternalNo() {
		return externalNo;
	}

	public void setExternalNo(String externalNo) {
		 this.externalNo = externalNo == null ? null : externalNo.trim();
	}

	public String getStoreGroupName() {
		return storeGroupName;
	}

	public void setStoreGroupName(String storeGroupName) {
		this.storeGroupName = storeGroupName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getStoreType() {
		return storeType;
	}

	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}

	public String getStoreTypeName() {
		return storeTypeName;
	}

	public void setStoreTypeName(String storeTypeName) {
		this.storeTypeName = storeTypeName;
	}

	public String getUpStructureName(){
		return upStructureName;
	}

	public void setUpStructureName(String upStructureName){
		this.upStructureName = upStructureName;
	}

	public String getUpChannelName(){
		return upChannelName;
	}
	
	public void setUpChannelName(String upChannelName){
		this.upChannelName = upChannelName;
	}

	public String getBusinessman(){
		return businessman;
	}
	
	public void setBusinessman(String businessman){
		this.businessman = businessman;
	}

	public String getPromotions(){
		return promotions;
	}

	public void setPromotions(String promotions){
		this.promotions = promotions;
	}

	public Integer getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}