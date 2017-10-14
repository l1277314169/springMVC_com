package cn.mobilizer.channel.image.po;

import java.util.Date;

public class ExecTimeLog {
	
    private Integer logId;

    private String keycode;

    private Date startDate;

    private Date endDate;

    private Integer intervalValue;

    private Date lastExecuted;

    private Integer lastExecTimes;

    private Byte lastExecStatus;

    private Integer clientId;

    private Byte isDelete;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode == null ? null : keycode.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getIntervalValue() {
        return intervalValue;
    }

    public void setIntervalValue(Integer intervalValue) {
        this.intervalValue = intervalValue;
    }

    public Date getLastExecuted() {
        return lastExecuted;
    }

    public void setLastExecuted(Date lastExecuted) {
        this.lastExecuted = lastExecuted;
    }

    public Integer getLastExecTimes() {
        return lastExecTimes;
    }

    public void setLastExecTimes(Integer lastExecTimes) {
        this.lastExecTimes = lastExecTimes;
    }

    public Byte getLastExecStatus() {
        return lastExecStatus;
    }

    public void setLastExecStatus(Byte lastExecStatus) {
        this.lastExecStatus = lastExecStatus;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}