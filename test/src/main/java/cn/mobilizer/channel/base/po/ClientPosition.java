package cn.mobilizer.channel.base.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ClientPosition implements Serializable {
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 3469855821551781925L;

	private Integer clientPositionId;

    private String positionNo;

    private String positionName;

    private String positionNameEn;

    private Integer parentId;

    private Integer sysPosition;

    private String remark;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;
    
    private String clientPositionIds;

    /** 对应options表的option_name=sys_positionType**/
    private String clinetPostionSysPosition;
    
    private List<Options> optionsList;
    
    private String parentName;
    
    public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getClientPositionId() {
        return clientPositionId;
    }

    public void setClientPositionId(Integer clientPositionId) {
        this.clientPositionId = clientPositionId;
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo == null ? null : positionNo.trim();
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? null : positionName.trim();
    }

    public String getPositionNameEn() {
        return positionNameEn;
    }

    public void setPositionNameEn(String positionNameEn) {
        this.positionNameEn = positionNameEn == null ? null : positionNameEn.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSysPosition() {
        return sysPosition;
    }

    public void setSysPosition(Integer sysPosition) {
        this.sysPosition = sysPosition;
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
	
	public String getClinetPostionSysPosition(){
		return OptionsConstants.CLINET_POSTION_SYSPOSITION;
	}

	public List<Options> getOptionsList(){
		return optionsList;
	}

	public void setOptionsList(List<Options> optionsList){
		this.optionsList = optionsList;
	}

	public String getClientPositionIds() {
		return clientPositionIds;
	}

	public void setClientPositionIds(String clientPositionIds) {
		this.clientPositionIds = clientPositionIds;
	}

}