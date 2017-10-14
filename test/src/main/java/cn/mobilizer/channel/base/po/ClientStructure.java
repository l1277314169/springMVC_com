package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;

public class ClientStructure implements Serializable {
	
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 885104860944277569L;

	private Integer clientStructureId;

    private String structureNo;

    private String structureName;

    private String structureNameEn;

    private Integer parentId;

    private String longitude;

    private String latitude;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private String parentStructureName;

    public Integer getClientStructureId() {
        return clientStructureId;
    }

    public void setClientStructureId(Integer clientStructureId) {
        this.clientStructureId = clientStructureId;
    }

    public String getStructureNo() {
        return structureNo;
    }

    public void setStructureNo(String structureNo) {
        this.structureNo = structureNo == null ? null : structureNo.trim();
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName == null ? null : structureName.trim();
    }

    public String getStructureNameEn() {
        return structureNameEn;
    }

    public void setStructureNameEn(String structureNameEn) {
        this.structureNameEn = structureNameEn == null ? null : structureNameEn.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

	
	public String getParentStructureName(){
		return parentStructureName;
	}

	
	public void setParentStructureName(String parentStructureName){
		this.parentStructureName = parentStructureName;
	}
}