package cn.mobilizer.channel.systemManager.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class MobileVersion implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 5701024286059110726L;

	private Integer mobileVersionId;

    private String version;

    private String versionDesc;

    private String appCode;

    private String appLink;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enableDate;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private Integer mustUpdate;
    
    private String checkboxId;
    
    private Integer forAll;
	

	public Integer getForAll() {
		return forAll;
	}

	public void setForAll(Integer forAll) {
		this.forAll = forAll;
	}

	public String getCheckboxId() {
		return checkboxId;
	}

	public void setCheckboxId(String checkboxId) {
		this.checkboxId = checkboxId;
	}

	public Integer getMobileVersionId() {
        return mobileVersionId;
    }

    public void setMobileVersionId(Integer mobileVersionId) {
        this.mobileVersionId = mobileVersionId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc == null ? null : versionDesc.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public String getAppLink() {
        return appLink;
    }

    public void setAppLink(String appLink) {
        this.appLink = appLink == null ? null : appLink.trim();
    }

    public Date getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Date enableDate) {
        this.enableDate = enableDate;
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

	public Integer getMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(Integer mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

    
}