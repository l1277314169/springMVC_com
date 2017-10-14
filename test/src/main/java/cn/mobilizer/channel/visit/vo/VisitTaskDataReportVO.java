package cn.mobilizer.channel.visit.vo;

public class VisitTaskDataReportVO {
	private Integer clientStructureId;//部门id
	private String structureName;//部门名称
	private String structureNameEn;//部门名称英文
	private Integer clientPositionId;//职位id
	private String positionName;//职位名称
	private String positionNameEn;//职位名称英文
	private String clientUserId;//用户id
	private String loginName;//登录名
	private String mobileNo;//手机
	private String name;//用户名
	private Integer plannedPops;//计划拜访数
	private Integer canceledPops;//取消拜访数
	private Integer tempPlanned;//临时计划拜访数
	private Integer tempVisited;//临时实际拜访数
	private Integer visitedStores;//实际拜访数
	private String firstInTime;//首次进店时间
	private String lastOutTime;//最后出店时间
	private Integer visitedMinutes;//店内时间
	private Integer avgVisitedMinutes;   //店内工作平均时间
	private Integer totalMinutes;//总时间
	private Integer locateMatch;//定位吻合
	private Integer locateMismatch;//定位不吻合
	private Integer noLocate;//没有定位
	 
	
	public Integer getTempPlanned() {
		return tempPlanned;
	}
	public void setTempPlanned(Integer tempPlanned) {
		this.tempPlanned = tempPlanned;
	}
	public Integer getClientStructureId() {
		return clientStructureId;
	}
	public void setClientStructureId(Integer clientStructureId) {
		this.clientStructureId = clientStructureId;
	}
	public String getStructureName() {
		return structureName;
	}
	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}
	public String getStructureNameEn() {
		return structureNameEn;
	}
	public void setStructureNameEn(String structureNameEn) {
		this.structureNameEn = structureNameEn;
	}
	public Integer getClientPositionId() {
		return clientPositionId;
	}
	public void setClientPositionId(Integer clientPositionId) {
		this.clientPositionId = clientPositionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPositionNameEn() {
		return positionNameEn;
	}
	public void setPositionNameEn(String positionNameEn) {
		this.positionNameEn = positionNameEn;
	}
	public String getClientUserId() {
		return clientUserId;
	}
	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPlannedPops() {
		return plannedPops;
	}
	public void setPlannedPops(Integer plannedPops) {
		this.plannedPops = plannedPops;
	}
	public Integer getVisitedStores() {
		return visitedStores;
	}
	public void setVisitedStores(Integer visitedStores) {
		this.visitedStores = visitedStores;
	}
	public String getFirstInTime() {
		return firstInTime;
	}
	public void setFirstInTime(String firstInTime) {
		this.firstInTime = firstInTime;
	}
	public String getLastOutTime() {
		return lastOutTime;
	}
	public void setLastOutTime(String lastOutTime) {
		this.lastOutTime = lastOutTime;
	}
	public Integer getVisitedMinutes() {
		return visitedMinutes;
	}
	public void setVisitedMinutes(Integer visitedMinutes) {
		this.visitedMinutes = visitedMinutes;
	}
	public Integer getTotalMinutes() {
		return totalMinutes;
	}
	public void setTotalMinutes(Integer totalMinutes) {
		this.totalMinutes = totalMinutes;
	}
	public Integer getLocateMatch() {
		return locateMatch;
	}
	public void setLocateMatch(Integer locateMatch) {
		this.locateMatch = locateMatch;
	}
	public Integer getLocateMismatch() {
		return locateMismatch;
	}
	public void setLocateMismatch(Integer locateMismatch) {
		this.locateMismatch = locateMismatch;
	}
	public Integer getNoLocate() {
		return noLocate;
	}
	public void setNoLocate(Integer noLocate) {
		this.noLocate = noLocate;
	}
	
	public Integer getCanceledPops(){
		return canceledPops;
	}
	
	public Integer getTempVisited(){
		return tempVisited;
	}
	
	public void setCanceledPops(Integer canceledPops){
		this.canceledPops = canceledPops;
	}
	
	public void setTempVisited(Integer tempVisited){
		this.tempVisited = tempVisited;
	}
	public Integer getAvgVisitedMinutes() {
		return avgVisitedMinutes;
	}
	public void setAvgVisitedMinutes(Integer avgVisitedMinutes) {
		this.avgVisitedMinutes = avgVisitedMinutes;
	}
}
