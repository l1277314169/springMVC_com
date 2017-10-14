package cn.mobilizer.channel.api.po;

import java.io.Serializable;
import java.util.Date;

public class AppDataChecklist  implements Serializable{
	
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -1579034969654151886L;
	
	private Long checklistId;

    private String tableName;

    private String pkDataId;

    private Integer slaveTableRows;

    private String appVersion;

    private String appPlatform;

    private Integer repeatTimes;

    private String log;

    private Byte status;
    
    private Integer clientUserId;

    private Integer clientId;

    private Date createTime;

    private Date lastUpdateTime;

    private Byte isDelete;

    public Long getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Long checklistId) {
        this.checklistId = checklistId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getPkDataId() {
        return pkDataId;
    }

    public void setPkDataId(String pkDataId) {
        this.pkDataId = pkDataId == null ? null : pkDataId.trim();
    }

    public Integer getSlaveTableRows() {
        return slaveTableRows;
    }

    public void setSlaveTableRows(Integer slaveTableRows) {
        this.slaveTableRows = slaveTableRows;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }

    public String getAppPlatform() {
        return appPlatform;
    }

    public void setAppPlatform(String appPlatform) {
        this.appPlatform = appPlatform == null ? null : appPlatform.trim();
    }

    public Integer getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(Integer repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log == null ? null : log.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
    
	public Integer getClientUserId(){
		return clientUserId;
	}

	public void setClientUserId(Integer clientUserId){
		this.clientUserId = clientUserId;
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