package cn.mobilizer.channel.base.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LeftMenuVO implements Serializable {
	
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 1642627170652812019L;

	private Integer clientPrivilegeId;

    private String privCode;

    private String privName;

    private String privNameEn;

    private Integer sequence;

    private String url;

    private String cssClass;

    private Integer parentId;

    private Byte privType;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private List<LeftMenuVO> childMenus;
    
    public Integer getClientPrivilegeId() {
        return clientPrivilegeId;
    }

    public void setClientPrivilegeId(Integer clientPrivilegeId) {
        this.clientPrivilegeId = clientPrivilegeId;
    }

    public String getPrivCode() {
        return privCode;
    }

    public void setPrivCode(String privCode) {
        this.privCode = privCode == null ? null : privCode.trim();
    }

    public String getPrivName() {
        return privName;
    }

    public void setPrivName(String privName) {
        this.privName = privName == null ? null : privName.trim();
    }

    public String getPrivNameEn() {
        return privNameEn;
    }

    public void setPrivNameEn(String privNameEn) {
        this.privNameEn = privNameEn == null ? null : privNameEn.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass == null ? null : cssClass.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Byte getPrivType() {
        return privType;
    }

    public void setPrivType(Byte privType) {
        this.privType = privType;
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

	public List<LeftMenuVO> getChildMenus(){
		return childMenus;
	}

	public void setChildMenus(List<LeftMenuVO> childMenus){
		this.childMenus = childMenus;
	}
}