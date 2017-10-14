package cn.mobilizer.channel.api.po;

public class VisitingCount {
    private Integer clientUserId;//
    private String name;   //--人名
    private String visitedDate;//                                        --拜访日期(如果没有计划和拜访，则为参数开始时间)
    private Integer plannedTimes;       //                           --计划次数
    private Integer tmpPlannedTimes; //                        --临时计划数
    private Integer visitedTimes;//                                    --实际拜访数
    private Integer visitedByPlanned;//
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
	public String getVisitedDate() {
		return visitedDate;
	}
	public void setVisitedDate(String visitedDate) {
		this.visitedDate = visitedDate;
	}
	public Integer getPlannedTimes() {
		return plannedTimes;
	}
	public void setPlannedTimes(Integer plannedTimes) {
		this.plannedTimes = plannedTimes;
	}
	public Integer getTmpPlannedTimes() {
		return tmpPlannedTimes;
	}
	public void setTmpPlannedTimes(Integer tmpPlannedTimes) {
		this.tmpPlannedTimes = tmpPlannedTimes;
	}
	public Integer getVisitedTimes() {
		return visitedTimes;
	}
	public void setVisitedTimes(Integer visitedTimes) {
		this.visitedTimes = visitedTimes;
	}
	public Integer getVisitedByPlanned() {
		return visitedByPlanned;
	}
	public void setVisitedByPlanned(Integer visitedByPlanned) {
		this.visitedByPlanned = visitedByPlanned;
	}
}
