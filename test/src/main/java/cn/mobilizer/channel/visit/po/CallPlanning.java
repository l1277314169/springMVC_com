package cn.mobilizer.channel.visit.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class CallPlanning implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID	= 3283004644592008725L; 

	private String planningId;
	
    private Integer clientUserId;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date callDate;

    private String popId;

    private Byte popType;
    
    private Integer visitType;

    private Integer sequence;

    private Integer planningType;

    private String routeId;

    private Byte callStatus;
    
	private Integer clientId;

    private Date createTime;

    private Date submitTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    /**扩展字段**/
    private String popName;
    
    private Date inTime;

    private Date outTime;
    
    private Integer workType;
    
    private Integer enumValue;
    
	private String name;
	
	private String positionName;
	
	private String structureName;
	
	private String parentStructureName;
	
	private String parentName;
	
	private String storeNo;
	
	private String storeName;
	
	private String loginName;
	
	private String optionText;
	
	private Integer planedTimes;
	
	private Integer visitedTimes;
	
	private String week;
	
	private String showEdit;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date oldCallDate;
	
	private Integer isNotDeleteStore;
	

	public Integer getIsNotDeleteStore() {
		return isNotDeleteStore;
	}

	public void setIsNotDeleteStore(Integer isNotDeleteStore) {
		this.isNotDeleteStore = isNotDeleteStore;
	}

	public String getParentStructureName() {
		return parentStructureName;
	}

	public void setParentStructureName(String parentStructureName) {
		this.parentStructureName = parentStructureName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
    public Date getOldCallDate() {
		return oldCallDate;
	}

	public void setOldCallDate(Date oldCallDate) {
		this.oldCallDate = oldCallDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public Integer getPlanedTimes() {
		return planedTimes;
	}

	public void setPlanedTimes(Integer planedTimes) {
		this.planedTimes = planedTimes;
	}

	public Integer getVisitedTimes() {
		return visitedTimes;
	}

	public void setVisitedTimes(Integer visitedTimes) {
		this.visitedTimes = visitedTimes;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getShowEdit() {
		return showEdit;
	}

	public void setShowEdit(String showEdit) {
		this.showEdit = showEdit;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

	public Integer getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(Integer enumValue) {
		this.enumValue = enumValue;
	}

	public String getPlanningId() {
        return planningId;
    }

    public void setPlanningId(String planningId) {
        this.planningId = planningId == null ? null : planningId.trim();
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId == null ? null : popId.trim();
    }

    public Byte getPopType() {
        return popType;
    }

    public void setPopType(Byte popType) {
        this.popType = popType;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getPlanningType() {
        return planningType;
    }

    public void setPlanningType(Integer planningType) {
        this.planningType = planningType;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    public Byte getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(Byte callStatus) {
        this.callStatus = callStatus;
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

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
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
	
	public String getPopName(){
		return popName;
	}
	
	public void setPopName(String popName){
		this.popName = popName;
	}

	public Date getInTime(){
		return inTime;
	}

	public void setInTime(Date inTime){
		this.inTime = inTime;
	}

	public Integer getVisitType() {
		return visitType;
	}

	public void setVisitType(Integer visitType) {
		this.visitType = visitType;
	}

	public Date getOutTime(){
		return outTime;
	}

	public void setOutTime(Date outTime){
		this.outTime = outTime;
	}

}