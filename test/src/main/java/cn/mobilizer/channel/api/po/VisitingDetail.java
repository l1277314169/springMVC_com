package cn.mobilizer.channel.api.po;

public class VisitingDetail {
    private Integer clientUserId;//
    private String name;
    private String storeNo;
    private String storeName ;
    private String inTime; 
    private String outTime;
    private Integer planningType;
    private Integer callStatus;
    
	public Integer getPlanningType() {
		return planningType;
	}
	public void setPlanningType(Integer planningType) {
		this.planningType = planningType;
	}
	public Integer getClientUserId() {
		return clientUserId;
	}
	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	
	public Integer getCallStatus(){
		return callStatus;
	}
	
	public void setCallStatus(Integer callStatus){
		this.callStatus = callStatus;
	}
}
