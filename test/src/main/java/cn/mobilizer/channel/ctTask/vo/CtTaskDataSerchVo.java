package cn.mobilizer.channel.ctTask.vo;

public class CtTaskDataSerchVo {
	private Integer clientUserId;
	private Integer ctTaskId;
	private String popId;
	private String taskCycle; 			//任务周期
	private Integer categoryId;
	private Integer brandId;
	private Integer skuTypeId;		//sku类型
	private Integer skuSeriesId;    //系列
	private String category;
	private String brand;
	private String skuType;
	private String skuSeries;
	private Byte taskType;
	private Integer clientId;
	private String objectName;       //对象名称
	private String ctTaskDataId;
	private String beginDate;  		//月份
	
	public Integer getClientUserId() {
		return clientUserId;
	}
	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}
	public Integer getCtTaskId() {
		return ctTaskId;
	}
	public void setCtTaskId(Integer ctTaskId) {
		this.ctTaskId = ctTaskId;
	}
	public String getPopId() {
		return popId;
	}
	public void setPopId(String popId) {
		this.popId = popId;
	}
	public String getTaskCycle() {
		return taskCycle;
	}
	public void setTaskCycle(String taskCycle) {
		this.taskCycle = taskCycle;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getSkuTypeId() {
		return skuTypeId;
	}
	public void setSkuTypeId(Integer skuTypeId) {
		this.skuTypeId = skuTypeId;
	}
	public Integer getSkuSeriesId() {
		return skuSeriesId;
	}
	public void setSkuSeriesId(Integer skuSeriesId) {
		this.skuSeriesId = skuSeriesId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSkuType() {
		return skuType;
	}
	public void setSkuType(String skuType) {
		this.skuType = skuType;
	}
	public String getSkuSeries() {
		return skuSeries;
	}
	public void setSkuSeries(String skuSeries) {
		this.skuSeries = skuSeries;
	}
	public Byte getTaskType() {
		return taskType;
	}
	public void setTaskType(Byte taskType) {
		this.taskType = taskType;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getCtTaskDataId() {
		return ctTaskDataId;
	}
	public void setCtTaskDataId(String ctTaskDataId) {
		this.ctTaskDataId = ctTaskDataId;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
}
