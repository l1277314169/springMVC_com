package cn.mobilizer.channel.ctTask.vo;

import java.io.Serializable;
import java.util.Date;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.ctTask.po.CtTask;
import cn.mobilizer.channel.util.DateTimeUtils;

public class StoreTask extends CtTask implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer clientId;
	private String storeId; // 门店编号
	private String storeName; // 门店名称
	private String structureId; // 部门ID
	private String structureName; // 部门
	private String userNames; // 负责人
	private String userIds;
	private String taskCycle;  //周期
	private String storeNo;    //门店编号
	private Date lastUpdateTime;  //更新日期
	private Integer ctTaskId;
	private Integer provinceId;  //省
	private Integer cityId; 	
	private String startDate; 		//开始日期
	private String endDate;			//结束日期
	private Integer lastUpdateUserId;    
	private String lastUpdateUserName;   //最后更新人
	private String subAllStructureId;     //子部门
	private String  fax;
	private String addr;
	private String contact;
	private String telephoneNo;
	private Integer creditPeriod;
	private String provinceName;
	private String ctTaskDataId;
	private String workerUserName;			//手机端接口：周期任务历史数据提报人关键字筛选
	private Byte isMobileSearch;			//手机端接口查询
	private String clientUserName;
	private Date startTime;
	private Byte cycle;//周期类型
	private String showCycleDate;
	private Byte reportData;
	private String updateClientUserName;
	private String createClientUserName;      //提报人：龙妮佳代报的情况区分
	
	public String getShowCycleDate() {
		StringBuilder builder = new StringBuilder();
		if(StringUtil.isNotEmptyString(this.startDate)){
			Date date = DateTimeUtils.StringToDate(this.startDate, DateTimeUtils.yyyyMMdd);
			builder.append(DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMdd));
		}
		if(StringUtil.isNotEmptyString(this.endDate)){
			Date date = DateTimeUtils.StringToDate(this.endDate, DateTimeUtils.yyyyMMdd);
			builder.append(" ~ ").append(DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMdd));
		}
		this.showCycleDate = builder.toString();
		return showCycleDate;
	}

	public void setShowCycleDate(String showCycleDate) {
		this.showCycleDate = showCycleDate;
	}
	
	public Byte getReportData() {
		return reportData;
	}

	public void setReportData(Byte reportData) {
		this.reportData = reportData;
	}

	public Byte getCycle() {
		return cycle;
	}

	public void setCycle(Byte cycle) {
		this.cycle = cycle;
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

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStructureId() {
		return structureId;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getTaskCycle() {
		return taskCycle;
	}

	public void setTaskCycle(String taskCycle) {
		this.taskCycle = taskCycle;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	
	
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getCtTaskId() {
		return ctTaskId;
	}

	public void setCtTaskId(Integer ctTaskId) {
		this.ctTaskId = ctTaskId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
 
	public Integer getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Integer lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public String getSubAllStructureId() {
		return subAllStructureId;
	}

	public void setSubAllStructureId(String subAllStructureId) {
		this.subAllStructureId = subAllStructureId;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public Integer getCreditPeriod() {
		return creditPeriod;
	}

	public void setCreditPeriod(Integer creditPeriod) {
		this.creditPeriod = creditPeriod;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCtTaskDataId() {
		return ctTaskDataId;
	}

	public void setCtTaskDataId(String ctTaskDataId) {
		this.ctTaskDataId = ctTaskDataId;
	}

	public String getWorkerUserName() {
		return workerUserName;
	}

	public void setWorkerUserName(String workerUserName) {
		this.workerUserName = workerUserName;
	}

	public Byte getIsMobileSearch() {
		return isMobileSearch;
	}

	public void setIsMobileSearch(Byte isMobileSearch) {
		this.isMobileSearch = isMobileSearch;
	}

	public String getClientUserName() {
		return clientUserName;
	}

	public void setClientUserName(String clientUserName) {
		this.clientUserName = clientUserName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getUpdateClientUserName() {
		return updateClientUserName;
	}

	public void setUpdateClientUserName(String updateClientUserName) {
		this.updateClientUserName = updateClientUserName;
	}

	public String getCreateClientUserName() {
		return createClientUserName;
	}

	public void setCreateClientUserName(String createClientUserName) {
		this.createClientUserName = createClientUserName;
	}
	 
}
