package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;

public class Unit implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -1855786540425982591L;

	private Integer unitId;

    private String unitGroupName;

    private String subUnitName;

    private String subUnitNameEn;

    private Byte subUnitStatus;

    private String unitName;

    private String unitNameEn;

    private Integer unitStatus;

    private String superUnitName;

    private String superUnitNameEn;

    private Integer superUnitStatus;

    private Integer toSubUnit;

    private Integer superToSubUnit;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitGroupName() {
        return unitGroupName;
    }

    public void setUnitGroupName(String unitGroupName) {
        this.unitGroupName = unitGroupName == null ? null : unitGroupName.trim();
    }

    public String getSubUnitName() {
        return subUnitName;
    }

    public void setSubUnitName(String subUnitName) {
        this.subUnitName = subUnitName == null ? null : subUnitName.trim();
    }

    public String getSubUnitNameEn() {
        return subUnitNameEn;
    }

    public void setSubUnitNameEn(String subUnitNameEn) {
        this.subUnitNameEn = subUnitNameEn == null ? null : subUnitNameEn.trim();
    }

    public Byte getSubUnitStatus() {
        return subUnitStatus;
    }

    public void setSubUnitStatus(Byte subUnitStatus) {
        this.subUnitStatus = subUnitStatus;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getUnitNameEn() {
        return unitNameEn;
    }

    public void setUnitNameEn(String unitNameEn) {
        this.unitNameEn = unitNameEn == null ? null : unitNameEn.trim();
    }

    public Integer getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(Integer unitStatus) {
        this.unitStatus = unitStatus;
    }

    public String getSuperUnitName() {
        return superUnitName;
    }

    public void setSuperUnitName(String superUnitName) {
        this.superUnitName = superUnitName == null ? null : superUnitName.trim();
    }

    public String getSuperUnitNameEn() {
        return superUnitNameEn;
    }

    public void setSuperUnitNameEn(String superUnitNameEn) {
        this.superUnitNameEn = superUnitNameEn == null ? null : superUnitNameEn.trim();
    }

    public Integer getSuperUnitStatus() {
        return superUnitStatus;
    }

    public void setSuperUnitStatus(Integer superUnitStatus) {
        this.superUnitStatus = superUnitStatus;
    }

    public Integer getToSubUnit() {
        return toSubUnit;
    }

    public void setToSubUnit(Integer toSubUnit) {
        this.toSubUnit = toSubUnit;
    }

    public Integer getSuperToSubUnit() {
        return superToSubUnit;
    }

    public void setSuperToSubUnit(Integer superToSubUnit) {
        this.superToSubUnit = superToSubUnit;
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
}