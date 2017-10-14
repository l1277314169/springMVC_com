package cn.mobilizer.channel.visit.vo;


public class CallPlanningQuery {
	private String startDate;
	private String endDate;
	private String clientUserName;
	private String clientStructureName;
	private String subordinates;
	private Integer structureId;
	private Integer clientPositionId;
	private Byte visitType;
	private Byte workType;
	private Integer enumValue;
	
	public Byte getWorkType() {
		return workType;
	}
	public void setWorkType(Byte workType) {
		this.workType = workType;
	}
	
	public Byte getVisitType() {
		return visitType;
	}
	public Integer getEnumValue() {
		return enumValue;
	}
	public void setEnumValue(Integer enumValue) {
		this.enumValue = enumValue;
	}
	public void setVisitType(Byte visitType) {
		this.visitType = visitType;
	}
	public Integer getStructureId() {
		return structureId;
	}
	public void setStructureId(Integer structureId) {
		this.structureId = structureId;
	}
	public CallPlanningQuery() {
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getClientUserName() {
		return clientUserName;
	}
	public void setClientUserName(String clientUserName) {
		this.clientUserName = clientUserName;
	}
	public String getClientStructureName() {
		return clientStructureName;
	}
	public void setClientStructureName(String clientStructureName) {
		this.clientStructureName = clientStructureName;
	}
	public Integer getClientPositionId() {
		return clientPositionId;
	}
	public void setClientPositionId(Integer clientPositionId) {
		this.clientPositionId = clientPositionId;
	}
	public String getSubordinates() {
		return subordinates;
	}
	public void setSubordinates(String subordinates) {
		this.subordinates = subordinates;
	}

}
